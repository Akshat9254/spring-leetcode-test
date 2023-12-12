package com.clone.leetcode.discuss.repository;

import com.clone.leetcode.discuss.model.Activity;
import com.clone.leetcode.discuss.model.ReactionCounter;
import com.clone.leetcode.discuss.model.ReactionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReactionCounterRepository extends JpaRepository<ReactionCounter, UUID> {
    Optional<ReactionCounter> findByActivityAndReactionType(Activity postId, ReactionType reactionType);
}
