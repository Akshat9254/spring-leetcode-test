package com.clone.leetcode.discuss.repository;

import com.clone.leetcode.discuss.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ActivityRepository extends JpaRepository<Activity, UUID> {
}
