package com.clone.leetcode.discuss.service;

import com.clone.leetcode.discuss.dto.ReactionDetails;
import com.clone.leetcode.discuss.model.Comment;
import com.clone.leetcode.discuss.model.Post;
import com.clone.leetcode.discuss.model.PostCategory;
import com.clone.leetcode.discuss.model.PostTag;
import com.clone.leetcode.discuss.model.Reaction;
import com.clone.leetcode.discuss.model.ReactionCounter;
import com.clone.leetcode.discuss.model.ReactionType;
import com.clone.leetcode.discuss.repository.CommentRepository;
import com.clone.leetcode.discuss.repository.PostRepository;
import com.clone.leetcode.discuss.repository.PostTagRepository;
import com.clone.leetcode.discuss.repository.ReactionCounterRepository;
import com.clone.leetcode.system.exception.ResourceNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostTagRepository postTagRepository;
    private final ReactionCounterRepository reactionCounterRepository;
    private final CommentRepository commentRepository;

    public List<PostCategory> findAllPostCategories() {
        return Arrays.stream(PostCategory.values()).toList();
    }

    @Transactional(readOnly = true)
    public Page<Post> findAll(Integer pageNumber, Integer pageSize, String sortBy, Direction sortDirection) {
        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        return postRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Post findById(@NonNull final UUID postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("post", "id", postId));
    }

    @Transactional
    public Post save(@NonNull final Post post) {
        Set<PostTag> tags = post.getTags();
        post.setTags(new HashSet<>());
        Post savedPost = postRepository.save(post);
        if(tags != null) {
            tags.forEach((tag) -> addTag(post.getId(), tag));
        }
        return savedPost;
    }

    public Map<ReactionType, ReactionDetails> getReactions(@NonNull final UUID postId) {
        Post post = findById(postId);
        Map<ReactionType, ReactionDetails> reactions = new HashMap<>();
        Arrays.stream(ReactionType.values()).forEach(type -> {
            Optional<ReactionCounter> reactionCounter = reactionCounterRepository
                    .findByActivityAndReactionType(post, type);
            int count = reactionCounter.isPresent() ? reactionCounter.get().getCount() : 0;
            reactions.put(type, new ReactionDetails(count, false));
        });
        return reactions;
    }

    @Transactional
    public void addTag(@NonNull final UUID postId, @NonNull final PostTag tag) {
        Post post = findById(postId);
        PostTag postTag = postTagRepository.findByName(tag.getName()).orElse(tag);
        post.getTags().add(postTag);
        postTag.getPosts().add(post);
        postRepository.save(post);
    }

    @Transactional
    public void addReaction(@NonNull final UUID postId, @NonNull final ReactionType reactionType) {
        Post post = findById(postId);
        Reaction reaction = Reaction.builder().type(reactionType).build();
        post.getReactions().add(reaction);
        reaction.setActivity(post);

        ReactionCounter reactionCounter = reactionCounterRepository
                .findByActivityAndReactionType(post, reactionType)
                .orElse(new ReactionCounter());

        reactionCounter.setActivity(post);
        reactionCounter.setReactionType(reactionType);
        reactionCounter.incrementCount();

        reactionCounterRepository.save(reactionCounter);
        postRepository.save(post);
    }

    @Transactional(readOnly = true)
    public Page<Comment> findComments(@NonNull final UUID postId, Integer pageNumber, Integer pageSize) {
        Post post = findById(postId);
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return commentRepository.findByActivityOrderByCreatedAtDesc(post, pageable);
    }

    public void addComment(@NonNull final UUID postId, final Comment comment) {
        Post post = findById(postId);
        post.getComments().add(comment);
        comment.setActivity(post);
        postRepository.save(post);
    }
}
