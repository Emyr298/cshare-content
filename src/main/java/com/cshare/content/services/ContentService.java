package com.cshare.content.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cshare.content.models.Content;
import com.cshare.content.repositories.ContentRepository;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class ContentService {
    private final ContentRepository contentRepository;
    
    public Flux<Content> findContentByUser(String userId) {
        return contentRepository.findByUserId(UUID.fromString(userId));
    }
}
