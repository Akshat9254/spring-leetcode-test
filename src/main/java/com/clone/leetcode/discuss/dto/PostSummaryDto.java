package com.clone.leetcode.discuss.dto;

import com.clone.leetcode.discuss.model.PostCategory;
import com.clone.leetcode.discuss.model.PostTagName;
import lombok.Builder;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Builder
public record PostSummaryDto(UUID id,
                             String title,
                             PostCategory category,
                             List<PostTagName> tags,
                             Instant createdAt,
                             Instant updatedAt) {
}
