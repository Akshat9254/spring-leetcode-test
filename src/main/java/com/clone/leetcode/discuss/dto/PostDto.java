package com.clone.leetcode.discuss.dto;

import com.clone.leetcode.discuss.model.PostCategory;
import lombok.Builder;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

@Builder
public record PostDto(UUID id,
                      String title,
                      String content,
                      PostCategory category,
                      Set<PostTagDto> tags,
                      Instant createdAt,
                      Instant updatedAt) {
}
