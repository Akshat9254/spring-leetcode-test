package com.clone.leetcode.discuss.controller;

import com.clone.leetcode.discuss.converter.CommentDtoToCommentConverter;
import com.clone.leetcode.discuss.converter.CommentToCommentDtoConverter;
import com.clone.leetcode.discuss.dto.AddReactionToCommentRequest;
import com.clone.leetcode.discuss.dto.AddReplyToCommentRequest;
import com.clone.leetcode.discuss.dto.CommentDto;
import com.clone.leetcode.discuss.dto.ReactionDetails;
import com.clone.leetcode.discuss.model.Comment;
import com.clone.leetcode.discuss.model.ReactionType;
import com.clone.leetcode.discuss.service.CommentService;
import com.clone.leetcode.system.response.PaginatedResponse;
import com.clone.leetcode.system.response.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
@RequestMapping("${api.endpoint.baseUrl}/discuss/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final CommentToCommentDtoConverter commentToCommentDtoConverter;
    private final CommentDtoToCommentConverter commentDtoToCommentConverter;

    @PutMapping("/reply")
    @ResponseStatus(HttpStatus.OK)
    public Response<Void> addReply(@RequestBody AddReplyToCommentRequest request) {
        Comment comment = commentDtoToCommentConverter.convert(request.reply());
        commentService.addReply(request.commentId(), comment);
        return new Response<>(true, HttpStatus.OK.value(), "Reply added", null);
    }

    @GetMapping("/{commentId}/replies")
    @ResponseStatus(HttpStatus.OK)
    public PaginatedResponse<CommentDto> fetchReplies(
            @PathVariable("commentId") UUID commentId,
            @RequestParam(name = "pageNumber", required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = "5") Integer pageSize
    ) {
        Page<Comment> commentPage = commentService.findReplies(commentId, pageNumber - 1, pageSize);
        List<CommentDto> commentDtos = commentPage.getContent().stream()
                .map(commentToCommentDtoConverter::convert).toList();
        return new PaginatedResponse<>(
                true,
                HttpStatus.OK.value(),
                "Fetch Comment Replies Success",
                commentPage.getTotalPages(),
                commentPage.getTotalElements(),
                commentPage.getNumber() + 1,
                commentPage.getSize(),
                commentDtos
        );
    }

    @GetMapping("/{commentId}/reactions")
    public Response<Map<ReactionType, ReactionDetails>> getReactions(
            @PathVariable("commentId") UUID commentId) {
        Map<ReactionType, ReactionDetails> reactions = commentService.getReactions(commentId);
        return new Response<>(true, HttpStatus.OK.value(),
                "Get Reactions by postId Success", reactions);
    }

    @PutMapping("/reaction")
    @ResponseStatus(HttpStatus.OK)
    public Response<Void> addReaction(@RequestBody AddReactionToCommentRequest request) {
        commentService.addReaction(request.commentId(), request.reactionType());
        return new Response<>(true, HttpStatus.OK.value(),
                String.format("%s added", request.reactionType()), null);
    }
}
