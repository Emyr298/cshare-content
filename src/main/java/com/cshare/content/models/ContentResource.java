package com.cshare.content.models;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ContentResource {
    @Id
    @Column("id")
    private UUID id;
    
    @Column("content_id")
    private UUID contentId;

    @Column("url")
    private String url;

    @Column("created_at")
    private LocalDateTime createdAt;
}
