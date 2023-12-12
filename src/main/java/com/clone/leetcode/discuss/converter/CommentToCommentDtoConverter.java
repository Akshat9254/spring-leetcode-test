package com.clone.leetcode.discuss.converter;

import com.clone.leetcode.discuss.dto.CommentDto;
import com.clone.leetcode.discuss.model.Comment;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CommentToCommentDtoConverter implements Converter<Comment, CommentDto> {
    @Override
    public CommentDto convert(Comment source) {
        return CommentDto.builder()
                .id(source.getId())
                .text(source.getText())
                .createdAt(source.getCreatedAt())
                .updatedAt(source.getUpdatedAt())
                .build();
    }
}
