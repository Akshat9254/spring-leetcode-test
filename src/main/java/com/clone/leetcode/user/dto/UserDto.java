package com.clone.leetcode.user.dto;

import lombok.Builder;

@Builder
public record UserDto(String email, String name, String avatarUrl) {
}
