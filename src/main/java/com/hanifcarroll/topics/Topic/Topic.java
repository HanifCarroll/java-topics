package com.hanifcarroll.topics.Topic;

import com.hanifcarroll.topics.BaseEntity;
import com.hanifcarroll.topics.Comment.Comment;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "topic")
public class Topic extends BaseEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "author")
    private String author;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "topic")
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
}
