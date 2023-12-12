package com.clone.leetcode.discuss.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "PostTag")
@Table(
        name = "post_tag",
        uniqueConstraints = @UniqueConstraint(name = "post_tag_name_unique", columnNames = "name")
)
public class PostTag {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    private PostTagName name;

    @ManyToMany(mappedBy = "tags")
    List<Post> posts = new ArrayList<>();
    public Integer getNumberOfPosts() {
        return posts.size();
    }
}
