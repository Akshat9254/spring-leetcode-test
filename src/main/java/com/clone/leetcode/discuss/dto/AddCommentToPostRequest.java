package com.clone.leetcode.discuss.dto;

import java.util.UUID;

public record AddCommentToPostRequest(UUID postId, CommentDto comment) {
}
