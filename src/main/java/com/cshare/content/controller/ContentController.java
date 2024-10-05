package com.cshare.content.controller;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cshare.content.models.Content;
import com.cshare.content.services.ContentService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/content")
@RequiredArgsConstructor
public class ContentController {
    private final ContentService contentService;
    
    @GetMapping("/{userId}")
    public Flux<Content> getContentByUser(
        @PathVariable String userId,
        @RequestParam(required = false) LocalDateTime fromTime,
        @RequestParam(required = false) LocalDateTime toTime
    ) {
        return contentService.findContentByUser(userId, fromTime, toTime);
    }

    @GetMapping("/{userId}/drafts")
    public Flux<Content> getDrafts(
        @PathVariable String userId
    ) {
        return contentService.findDrafts(userId);
    }
}
