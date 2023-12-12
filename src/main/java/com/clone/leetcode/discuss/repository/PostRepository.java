package com.clone.leetcode.discuss.repository;

import com.clone.leetcode.discuss.model.Post;
import com.clone.leetcode.discuss.model.PostTagName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
    List<Post> findByTagsIn(Collection<PostTagName> tags);
    List<PostTagName> findDistinctTagsBy();
}
