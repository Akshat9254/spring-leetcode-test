package com.clone.leetcode.discuss.controller;


import com.clone.leetcode.discuss.converter.CommentDtoToCommentConverter;
import com.clone.leetcode.discuss.converter.CommentToCommentDtoConverter;
import com.clone.leetcode.discuss.converter.PostToPostDtoConverter;
import com.clone.leetcode.discuss.converter.PostToPostSummaryDtoConverter;
import com.clone.leetcode.discuss.dto.AddCommentToPostRequest;
import com.clone.leetcode.discuss.dto.AddReactionToPostRequest;
import com.clone.leetcode.discuss.dto.CommentDto;
import com.clone.leetcode.discuss.dto.PostDto;
import com.clone.leetcode.discuss.dto.PostSummaryDto;
import com.clone.leetcode.discuss.dto.ReactionDetails;
import com.clone.leetcode.discuss.model.Comment;
import com.clone.leetcode.discuss.model.Post;
import com.clone.leetcode.discuss.model.PostCategory;
import com.clone.leetcode.discuss.model.ReactionType;
import com.clone.leetcode.discuss.service.PostService;
import com.clone.leetcode.system.response.PaginatedResponse;
import com.clone.leetcode.system.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("${api.endpoint.baseUrl}/discuss/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final PostToPostSummaryDtoConverter postToPostSummaryDtoConverter;
    private final PostToPostDtoConverter postToPostDtoConverter;
    private final CommentToCommentDtoConverter commentToCommentDtoConverter;
    private final CommentDtoToCommentConverter commentDtoToCommentConverter;

    @GetMapping("/categories")
    @ResponseStatus(HttpStatus.OK)
    public Response<List<PostCategory>> fetchPostCategories() {
        List<PostCategory> postCategories = postService.findAllPostCategories();
        return new Response<>(true, HttpStatus.OK.value(),
                "Fetch all Post Categories Success", postCategories);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PaginatedResponse<PostSummaryDto> fetchAllPosts(
            @RequestParam(name = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize,
            @RequestParam(name = "sortBy", required = false, defaultValue = "createdAt") String sortBy,
            @RequestParam(name = "direction", required = false, defaultValue = "DESC") Direction sortDirection
    ) {
        Page<Post> postPage = postService.findAll(pageNumber - 1, pageSize, sortBy, sortDirection);
        List<PostSummaryDto> postDtos = postPage.getContent().stream()
                .map(postToPostSummaryDtoConverter::convert).toList();
        return new PaginatedResponse<>(
                true,
                HttpStatus.OK.value(),
                "Find all Posts Success",
                postPage.getTotalPages(),
                postPage.getTotalElements(),
                postPage.getNumber() + 1,
                postPage.getSize(),
                postDtos
        );
    }

    @GetMapping("/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public Response<PostDto> fetchPostById(@PathVariable("postId") UUID postId) {
        Post post = postService.findById(postId);
        PostDto postDto = postToPostDtoConverter.convert(post);
        return new Response<>(true, HttpStatus.OK.value(),
                "Find Post by Id Success", postDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Response<PostDto> savePost(@RequestBody final Post post) {
        Post savedPost = postService.save(post);
        PostDto postDto = postToPostDtoConverter.convert(savedPost);
        return new Response<>(true, HttpStatus.CREATED.value(),
                "Post created successfully", postDto);
    }

    @GetMapping("/{postId}/reactions")
    public Response<Map<ReactionType, ReactionDetails>> getReactions(@PathVariable("postId") UUID postId) {
        Map<ReactionType, ReactionDetails> reactions = postService.getReactions(postId);
        return new Response<>(true, HttpStatus.OK.value(),
                "Get Reactions by postId Success", reactions);
    }

    @PutMapping("/reaction")
    @ResponseStatus(HttpStatus.OK)
    public Response<Void> addReaction(@RequestBody AddReactionToPostRequest request) {
        postService.addReaction(request.postId(), request.reactionType());
        return new Response<>(true, HttpStatus.OK.value(),
                String.format("%s added", request.reactionType()), null);
    }

    @GetMapping("/{postId}/comments")
    public PaginatedResponse<CommentDto> fetchComments(
            @PathVariable("postId") UUID postId,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize
    ) {
        Page<Comment> commentPage = postService.findComments(postId, pageNumber - 1, pageSize);
        List<CommentDto> commentDtos = commentPage.getContent().stream()
                .map(commentToCommentDtoConverter::convert).toList();
        return new PaginatedResponse<>(
                true,
                HttpStatus.OK.value(),
                "Fetch Post Comments Success",
                commentPage.getTotalPages(),
                commentPage.getTotalElements(),
                commentPage.getNumber() + 1,
                commentPage.getSize(),
                commentDtos
        );
    }

    @PutMapping("/comment")
    @ResponseStatus(HttpStatus.OK)
    public Response<Void> addComment(@RequestBody AddCommentToPostRequest request) {
        Comment comment = commentDtoToCommentConverter.convert(request.comment());
        postService.addComment(request.postId(), comment);
        return new Response<>(true, HttpStatus.OK.value(),
                "Comment added", null);
    }
}
