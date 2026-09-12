package com.fmhyclone.controller;

import com.fmhyclone.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.fmhyclone.dto.TagRequest;
import com.fmhyclone.dto.TagResponse;

@RestController 
@RequestMapping ("/api/v1/admin/tags")
public class AdminTagController {

    private final TagService tagService;

    public AdminTagController(TagService tagService){
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<TagResponse> createTag(@RequestBody TagRequest request){
        return ResponseEntity.ok(tagService.createTag(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id){
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }


    
}
