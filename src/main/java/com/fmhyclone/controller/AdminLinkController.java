package com.fmhyclone.controller;

import com.fmhyclone.dto.CategoryRequest;
import com.fmhyclone.dto.CategoryResponse;
import com.fmhyclone.service.LinkService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.fmhyclone.dto.LinkRequest;
import com.fmhyclone.dto.LinkResponse;

@RestController
@RequestMapping ("/api/v1/admin/links") 
public class AdminLinkController {
    
    private final LinkService linkService;

    public AdminLinkController(LinkService linkService){
        this.linkService = linkService;
    }

    @GetMapping("path")
    public ResponseEntity<LinkResponse> createLink(@RequestBody LinkRequest request) {
        return ResponseEntity.ok(linkService.createLink(request));
    }


    @PostMapping("/{id}")
    public ResponseEntity<LinkResponse> updateLink(@PathVariable Long id, @RequestBody LinkRequest request){
        return ResponseEntity.ok(linkService.updateLink(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLink(@PathVariable Long id){
        linkService.deleteLink(id);
        return ResponseEntity.noContent().build();
    }
}   
