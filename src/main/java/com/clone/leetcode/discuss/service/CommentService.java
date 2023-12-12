package com.clone.leetcode.discuss.service;

import com.clone.leetcode.discuss.dto.ReactionDetails;
import com.clone.leetcode.discuss.model.Comment;
import com.clone.leetcode.discuss.model.Reaction;
import com.clone.leetcode.discuss.model.ReactionCounter;
import com.clone.leetcode.discuss.model.ReactionType;
import com.clone.leetcode.discuss.repository.CommentRepository;
import com.clone.leetcode.discuss.repository.ReactionCounterRepository;
import com.clone.leetcode.system.exception.ResourceNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ReactionCounterRepository reactionCounterRepository;
    public void addReply(final UUID commentId, final Comment reply) {
        Comment comment = findById(commentId);
        comment.getReplies().add(reply);
        reply.setActivity(comment);
        commentRepository.save(comment);
    }

    public Comment findById(@NonNull final UUID commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("comment", "id", commentId));
    }

    public Page<Comment> findReplies(UUID commentId, int pageNumber, Integer pageSize) {
        Comment comment = findById(commentId);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return commentRepository.findByActivityOrderByCreatedAtDesc(comment, pageable);
    }

    public Map<ReactionType, ReactionDetails> getReactions(UUID commentId) {
        Comment comment = findById(commentId);
        Map<ReactionType, ReactionDetails> reactions = new HashMap<>();
        Arrays.stream(ReactionType.values()).forEach(type -> {
            Optional<ReactionCounter> reactionCounter = reactionCounterRepository
                    .findByActivityAndReactionType(comment, type);
            int count = reactionCounter.isPresent() ? reactionCounter.get().getCount() : 0;
            reactions.put(type, new ReactionDetails(count, false));
        });
        return reactions;
    }

    public void addReaction(UUID commentId, ReactionType reactionType) {
        Comment comment = findById(commentId);
        Reaction reaction = Reaction.builder().type(reactionType).build();
        comment.getReactions().add(reaction);
        reaction.setActivity(comment);

        ReactionCounter reactionCounter = reactionCounterRepository
                .findByActivityAndReactionType(comment, reactionType)
                .orElse(new ReactionCounter());

        reactionCounter.setActivity(comment);
        reactionCounter.setReactionType(reactionType);
        reactionCounter.incrementCount();

        reactionCounterRepository.save(reactionCounter);
        commentRepository.save(comment);
    }
}
