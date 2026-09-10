package com.fmhyclone.controller;

import com.fmhyclone.dto.LinkResponse;
import com.fmhyclone.service.LinkService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/links")
public class LinkController {

    private final LinkService linkService;

    public LinkController(LinkService linkService) {
        this.linkService = linkService;
    }

    // 1. Get paginated links by Category Slug
    @GetMapping("/category/{slug}")
    public ResponseEntity<Page<LinkResponse>> getLinksByCategory(
            @PathVariable("slug") String categorySlug,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(linkService.getLinksByCategory(categorySlug, pageable));
    }

    // 2. Get paginated links by Tag Slug
    @GetMapping("/tag/{slug}")
    public ResponseEntity<Page<LinkResponse>> getLinksByTag(
            @PathVariable("slug") String tagSlug,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(linkService.getLinksByTag(tagSlug, pageable));
    }

    // 3. Search through links (title, description, or tag names)
    @GetMapping("/search")
    public ResponseEntity<Page<LinkResponse>> searchLinks(
            @RequestParam("q") String query,
            @PageableDefault(size = 20) Pageable pageable) {
        return ResponseEntity.ok(linkService.search(query, pageable));
    }
}
