package com.fmhyclone.dto;

public record CategoryRequest(
    String name,
    String description,
    String icon,
    String slug,
    Integer displayOrder
) {}
