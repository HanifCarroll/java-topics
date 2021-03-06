package com.hanifcarroll.topics.Topic;

import com.hanifcarroll.topics.BaseEntity;
import com.hanifcarroll.topics.Comment.Comment;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "topic")
public class Topic extends BaseEntity {

    @NotBlank
    @Column(name = "title")
    @Size(max = 150)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "author")
    @Size(max = 20)
    private String author;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "topic")
    @OrderBy("createdAt ASC")
    private Set<Comment> comments = new HashSet<>();

    public Topic() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public int countComments() {
        return this.comments.size();
    }

    @PrePersist
    public void preInsert() {
        if (this.author.trim().equals("")) {
            this.author = "Anonymous";
        }
    }
}
