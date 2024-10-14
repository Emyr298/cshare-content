package com.cshare.content.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;

import com.cshare.content.core.storage.FileStorage;
import com.cshare.content.models.Content;
import com.cshare.content.models.ContentResource;
import com.cshare.content.repositories.ContentResourceRepository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuples;

@Service
@RequiredArgsConstructor
public class ContentResourceService {
    private final ContentService contentService;
    private final ContentResourceRepository repository;
    private final FileStorage fileStorage;

    @Value("${cshare.content.temp-file-path}")
    private String tempFilePath;

    @Value("${cshare.content.bucket-path}")
    private String bucketPath;

    public Flux<ContentResource> getResources(String userId, String contentId) {
        Mono<Content> contentCheck = contentService.getContentOfUser(userId, contentId);
        Flux<ContentResource> resources = repository.findByContentId(UUID.fromString(contentId));
        return contentCheck.thenMany(resources);
    }

    public Mono<ContentResource> createResource(String userId, String contentId, Mono<FilePart> fileMono) {
        Mono<Content> contentCheck = contentService.getContentOfUser(userId, contentId);
        var fileProcessing = fileMono
            .flatMap(filePart -> 
                saveTempFile(filePart))
            .flatMap(path -> fileStorage.uploadFile(
                        UUID.randomUUID().toString(),
                        path.toFile()
                    ).map(url -> Tuples.of(url, path))
            ).flatMap(pair -> {
                ContentResource resource = ContentResource.builder()
                    .contentId(UUID.fromString(contentId))
                    .url(pair.getT1().toString())
                    .build();
                return repository.save(resource)
                    .map(resourceDB -> Tuples.of(resourceDB, pair.getT2()));
            });
            
        return contentCheck
            .then(fileProcessing)
            .doOnSuccess(pair -> {
                deleteTempFile(pair.getT2())
                    .subscribeOn(Schedulers.boundedElastic())
                    .subscribe();
            })
            .map(pair -> pair.getT1());
    }

    private Mono<Path> saveTempFile(FilePart filePart) {
        String uuid = UUID.randomUUID().toString();
        Path path = Paths.get(tempFilePath, uuid);
        return filePart.transferTo(path).then(Mono.just(path));
    }

    private Mono<Void> deleteTempFile(Path path) {
        return Mono.fromRunnable(() -> {
            path.toFile().delete();
        });
    }
}
