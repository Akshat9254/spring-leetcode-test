package com.clone.leetcode.discuss.dto;

import com.clone.leetcode.discuss.model.PostTagName;
import lombok.Builder;

@Builder
public record PostTagDto(PostTagName name, Integer numberOfPosts) {
}
