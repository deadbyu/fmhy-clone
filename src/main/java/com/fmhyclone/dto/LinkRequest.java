package com.fmhyclone.dto;

import java.util.Set;

public record LinkRequest(
    String title,
    String url,
    String description,
    String status, 
    Long categoryId,
    Set<Long> tagIds 
) {}
