package com.clone.leetcode.discuss.dto;

import com.clone.leetcode.discuss.model.ReactionType;

import java.util.Map;

public record ReactionDto(Map<ReactionType, ReactionDetails> reactions) {
}
