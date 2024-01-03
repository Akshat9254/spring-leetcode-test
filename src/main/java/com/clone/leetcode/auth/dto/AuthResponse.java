package com.clone.leetcode.auth.dto;

import com.clone.leetcode.user.dto.UserDto;

public record AuthResponse(UserDto user, String token) {
}
