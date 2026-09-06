package com.fmhyclone.dto;

import java.util.Set;

public record LinkResponse(
    Long id,
    String title,
    String url,
    String description,
    String status,
    CategoryResponse category,
    Set<TagResponse> tags
){}