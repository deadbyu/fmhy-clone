package com.fmhyclone.dto;

public record LoginRequest(
        String username,
        String password
) {}