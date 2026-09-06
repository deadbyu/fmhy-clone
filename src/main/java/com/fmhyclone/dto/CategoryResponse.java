package com.fmhyclone.dto;

public record CategoryResponse(
    Long id,
    String name,
    String description,
    String icon,
    String slug,
    Integer displayOrder
) {}
