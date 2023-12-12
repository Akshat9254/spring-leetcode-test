package com.clone.leetcode.discuss.repository;

import com.clone.leetcode.discuss.model.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReactionRepository extends JpaRepository<Reaction, UUID> {
}
