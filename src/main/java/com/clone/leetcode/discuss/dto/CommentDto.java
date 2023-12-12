package com.clone.leetcode.discuss.dto;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record CommentDto(UUID id,
                         String text,
                         Instant createdAt,
                         Instant updatedAt) {
}
