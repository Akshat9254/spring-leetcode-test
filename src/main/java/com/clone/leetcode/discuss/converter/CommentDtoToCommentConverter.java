package com.clone.leetcode.discuss.converter;

import com.clone.leetcode.discuss.dto.CommentDto;
import com.clone.leetcode.discuss.model.Comment;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CommentDtoToCommentConverter implements Converter<CommentDto, Comment> {
    @Override
    public Comment convert(CommentDto source) {
        return Comment.builder()
                .text(source.text())
                .build();
    }
}
