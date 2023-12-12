package com.clone.leetcode.discuss.dto;

import com.clone.leetcode.discuss.model.ReactionType;

import java.util.UUID;

public record AddReactionToCommentRequest(UUID commentId, ReactionType reactionType) {
}
