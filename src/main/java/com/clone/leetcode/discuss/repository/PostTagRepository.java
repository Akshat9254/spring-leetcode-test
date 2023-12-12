package com.clone.leetcode.discuss.repository;

import com.clone.leetcode.discuss.model.Post;
import com.clone.leetcode.discuss.model.PostTag;
import com.clone.leetcode.discuss.model.PostTagName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PostTagRepository extends JpaRepository<PostTag, UUID> {
    Optional<PostTag> findByName(PostTagName name);
}
