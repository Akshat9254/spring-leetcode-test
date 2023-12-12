package com.clone.leetcode.system.response;


import java.util.List;

public record PaginatedResponse<T>(Boolean success,
                                   Integer statusCode,
                                   String message,
                                   Integer totalPages,
                                   Long totalElements,
                                   Integer pageNumber,
                                   Integer pageSize,
                                   List<T> content
                              ) {
}
