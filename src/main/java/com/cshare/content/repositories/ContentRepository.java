package com.cshare.content.repositories;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;

import com.cshare.content.models.Content;
import com.cshare.content.models.ContentStatus;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface ContentRepository extends ReactiveSortingRepository<Content, UUID> {
    Mono<Content> findById(UUID id);

    Flux<Content> findByUserId(UUID userId);

    @Query("""
            SELECT * FROM content
            WHERE user_id = :userId
                    AND status = :status::content_status
                    AND (published_at BETWEEN :from AND :to)
            LIMIT :limit
        """)
    Flux<Content> findByUserIdAndStatusAndPublishedAtBetweenLimit(
        UUID userId,
        ContentStatus status,
        LocalDateTime from,
        LocalDateTime to,
        Integer limit
    );

    Flux<Content> findByUserIdAndStatusOrderByCreatedAt(UUID userId, ContentStatus status, Pageable pageable);
}
