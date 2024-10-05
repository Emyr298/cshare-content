package com.cshare.content.models;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Content {
    @Id
    @Column("id")
    private UUID id;
    
    @Column("title")
    private String title;
    
    @Column("description")
    private String description;
    
    @Column("user_id")
    private UUID userId;
    
    @Column("status")
    private ContentStatus status = ContentStatus.DRAFT;
}
