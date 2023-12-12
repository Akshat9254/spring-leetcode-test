package com.clone.leetcode.discuss.dto;

import java.util.UUID;

public record AddReplyToCommentRequest(UUID commentId, CommentDto reply) {
}
