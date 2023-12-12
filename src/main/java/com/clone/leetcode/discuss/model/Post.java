package com.clone.leetcode.discuss.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity(name = "Post")
@Table(name = "posts")
public class Post extends Activity {
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", length = 10000, nullable = false)
    private String content;

    @Column(name = "category", nullable = false)
    @Enumerated(EnumType.STRING)
    private PostCategory category;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PostTag> tags = new HashSet<>();
}
