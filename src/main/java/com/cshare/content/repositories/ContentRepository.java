package com.cshare.content.repositories;

import java.util.UUID;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.cshare.content.models.Content;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ContentRepository extends ReactiveCrudRepository<Content, UUID> {
    Mono<Content> findById(UUID id);
    Flux<Content> findByUserId(UUID userId);
}
