package com.clone.leetcode.system;

import com.clone.leetcode.discuss.model.Post;
import com.clone.leetcode.discuss.model.PostCategory;
import com.clone.leetcode.discuss.model.PostTag;
import com.clone.leetcode.discuss.model.PostTagName;
import com.clone.leetcode.discuss.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class DBInitializer implements CommandLineRunner {
    private final PostRepository postRepository;

    @Override
    public void run(String... args) {
        insertPosts();
    }

    private void insertPosts() {
        PostTag googleTag = PostTag.builder().name(PostTagName.GOOGLE).build();
        PostTag amazonTag = PostTag.builder().name(PostTagName.AMAZON).build();
        PostTag onlineAssessmentTag = PostTag.builder().name(PostTagName.ONLINE_ASSESSMENT).build();

        Post post1 = Post.builder()
                .title("Post 1")
                .content("Post 1 content")
                .category(PostCategory.INTERVIEW_EXPERIENCE)
                .tags(Set.of(googleTag, amazonTag))
                .build();

        Post post2 = Post.builder()
                .title("Post 2")
                .content("Post 2 content")
                .category(PostCategory.INTERVIEW_EXPERIENCE)
                .tags(Set.of(googleTag, onlineAssessmentTag))
                .build();

        Post post3 = Post.builder()
                .title("Post 3")
                .content("Post 3 content")
                .category(PostCategory.INTERVIEW_EXPERIENCE)
                .tags(Set.of(onlineAssessmentTag, googleTag))
                .build();

        Post post4 = Post.builder()
                .title("Google L3 Interview Experience")
                .content("Post 4 content")
                .category(PostCategory.INTERVIEW_EXPERIENCE)
                .tags(Set.of(googleTag))
                .build();

        Post post5 = Post.builder()
                .title("Meta - System Design")
                .content("Post 5 content")
                .category(PostCategory.INTERVIEW_EXPERIENCE)
                .tags(Set.of(amazonTag))
                .build();

        Post post6 = Post.builder()
                .title("Placements hiring 2024 batch")
                .content("Post 6 content")
                .category(PostCategory.INTERVIEW_EXPERIENCE)
                .tags(Set.of(onlineAssessmentTag))
                .build();

        Post post7 = Post.builder()
                .title("Meta Phone Screen E4")
                .content("Post 7 content")
                .category(PostCategory.INTERVIEW_EXPERIENCE)
                .tags(Set.of(onlineAssessmentTag, amazonTag, googleTag))
                .build();

        Post post8 = Post.builder()
                .title("Rethinking LeetCode Streaks: Quality Over Quantity")
                .content("Post 8 content")
                .category(PostCategory.INTERVIEW_EXPERIENCE)
                .tags(Set.of(googleTag, amazonTag))
                .build();

        Post post9 = Post.builder()
                .title("E4/E5 Phone Screen Interview")
                .content("Post 9 content")
                .category(PostCategory.INTERVIEW_EXPERIENCE)
                .tags(Set.of())
                .build();

        Post post10 = Post.builder()
                .title("Google Online Assessment Questions")
                .content("Post 10 content")
                .category(PostCategory.INTERVIEW_EXPERIENCE)
                .tags(Set.of(onlineAssessmentTag, amazonTag))
                .build();

        postRepository.saveAll(List.of(post1, post2, post3, post4, post5,
                post6, post7, post8, post9, post10));

    }
}
