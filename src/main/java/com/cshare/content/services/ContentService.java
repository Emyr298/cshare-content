package com.cshare.content.services;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cshare.content.models.Content;
import com.cshare.content.models.ContentStatus;
import com.cshare.content.repositories.ContentRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;
    private final Integer CONTENT_LIMIT = 5;
    
    public Flux<Content> findContentByUser(String userId, LocalDateTime from, LocalDateTime to) {
        if (from == null) from = LocalDateTime.ofEpochSecond(0, 0, ZoneOffset.UTC);
        if (to == null) to = LocalDateTime.now();

        return contentRepository
            .findByUserIdAndStatusAndPublishedAtBetweenLimit(
                UUID.fromString(userId),
                ContentStatus.DRAFT,
                from,
                to,
                CONTENT_LIMIT
            );
    }
}
