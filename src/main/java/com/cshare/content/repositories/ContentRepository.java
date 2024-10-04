package com.cshare.content.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.cshare.content.models.Content;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ContentRepository extends ReactiveCrudRepository<Content, String> {
    Mono<Content> findById(String id);
    
    Flux<Content> findByUserId(String userId);
}
