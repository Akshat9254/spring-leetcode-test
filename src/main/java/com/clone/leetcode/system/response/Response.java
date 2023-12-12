package com.clone.leetcode.system.response;

public record Response<T>(Boolean success,
                          Integer statusCode,
                          String message,
                          T data) {
}
