package com.clone.leetcode.discuss.converter;

import com.clone.leetcode.discuss.dto.PostSummaryDto;
import com.clone.leetcode.discuss.model.Post;
import com.clone.leetcode.discuss.model.PostTag;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PostToPostSummaryDtoConverter implements Converter<Post, PostSummaryDto> {
    @Override
    public PostSummaryDto convert(Post source) {
        return PostSummaryDto.builder()
                .id(source.getId())
                .title(source.getTitle())
                .category(source.getCategory())
                .tags(source.getTags().stream().map(PostTag::getName).toList())
                .createdAt(source.getCreatedAt())
                .updatedAt(source.getUpdatedAt())
                .build();
    }
}
