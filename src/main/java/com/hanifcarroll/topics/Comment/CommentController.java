package com.hanifcarroll.topics.Comment;

import com.hanifcarroll.topics.Topic.Topic;
import com.hanifcarroll.topics.Topic.TopicRepository;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentRepository commentRepository;
    private final TopicRepository topicRepository;

    public CommentController(CommentRepository commentRepository, TopicRepository topicRepository) {
        this.commentRepository = commentRepository;
        this.topicRepository = topicRepository;
    }

    @PostMapping({"", "/"})
    public Comment createComment(
            @RequestParam("body") String body,
            @RequestParam("author") String author,
            @RequestParam("topicId") Long topicId
    ) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(EntityNotFoundException::new);

        Comment newComment = new Comment();
        newComment.setBody(body);
        newComment.setAuthor(author);
        newComment.setTopic(topic);

        commentRepository.save(newComment);

        return newComment;
    }

    @GetMapping({"/{id}", "/{id}/"})
    public Comment getComment(@PathVariable("id") Long id) {
        return commentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
