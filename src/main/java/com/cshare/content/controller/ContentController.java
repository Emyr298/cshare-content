package com.cshare.content.controller;

import org.springframework.web.bind.annotation.RequestMapping;
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
    
    public Flux<Content> getContentByUser() {
        return contentService.findContentByUser("2");
    }
}
