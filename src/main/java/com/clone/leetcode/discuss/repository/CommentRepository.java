package com.clone.leetcode.discuss.repository;

import com.clone.leetcode.discuss.model.Activity;
import com.clone.leetcode.discuss.model.Comment;
import com.clone.leetcode.discuss.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
    Page<Comment> findByActivityOrderByCreatedAtDesc(Activity activity, Pageable pageable);
}
