package com.clone.leetcode.discuss.converter;

import com.clone.leetcode.discuss.dto.PostDto;
import com.clone.leetcode.discuss.dto.PostTagDto;
import com.clone.leetcode.discuss.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class PostToPostDtoConverter implements Converter<Post, PostDto> {
    private final PostTagToPostTagDtoConverter postTagToPostTagDtoConverter;

    @Override
    public PostDto convert(Post source) {
        Set<PostTagDto> tagsDto = source.getTags() == null ? null : source.getTags().stream()
                .map(postTagToPostTagDtoConverter::convert).collect(Collectors.toSet());
        return PostDto.builder()
                .id(source.getId())
                .title(source.getTitle())
                .content(source.getContent())
                .category(source.getCategory())
                .tags(tagsDto)
                .createdAt(source.getCreatedAt())
                .updatedAt(source.getUpdatedAt())
                .build();
    }
}
