package com.clone.leetcode.discuss.converter;

import com.clone.leetcode.discuss.dto.PostTagDto;
import com.clone.leetcode.discuss.model.PostTag;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PostTagToPostTagDtoConverter implements Converter<PostTag, PostTagDto> {
    @Override
    public PostTagDto convert(PostTag source) {
        return PostTagDto.builder()
                .name(source.getName())
                .numberOfPosts(source.getNumberOfPosts())
                .build();
    }
}
