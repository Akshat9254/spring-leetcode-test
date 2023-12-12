package com.clone.leetcode.system.exception;

import lombok.NonNull;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(@NonNull final String resource,
                                     @NonNull final String field,
                                     @NonNull final Object value) {
        super(String.format("%s not found with %s: %s", resource, field, value));
    }
}
