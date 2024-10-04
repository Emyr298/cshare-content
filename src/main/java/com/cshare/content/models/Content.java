package com.cshare.content.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Content {
    @Id
    @Column("id")
    private String id;
    
    @Column("title")
    private String title;
    
    @Column("description")
    private String description;
    
    @Column("user_id")
    private String userId;
    
    @Column("status")
    private ContentStatus status = ContentStatus.DRAFT;
}
