package com.clone.leetcode.user.converter;

import com.clone.leetcode.user.dto.UserDto;
import com.clone.leetcode.user.model.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserToUserDtoConverter implements Converter<User, UserDto> {
    @Override
    public UserDto convert(User source) {
        return UserDto.builder()
                .email(source.getEmail())
                .name(source.getName())
                .avatarUrl(source.getAvatarUrl())
                .build();
    }
}
