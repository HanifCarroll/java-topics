package com.hanifcarroll.topics.Comment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hanifcarroll.topics.BaseEntity;
import com.hanifcarroll.topics.Topic.Topic;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "comment")
public class Comment extends BaseEntity {

    @Column(name = "author")
    private String author;

    @Column(name = "body")
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    @NotNull
    @JsonIgnore
    private Topic topic;

    public Comment() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}