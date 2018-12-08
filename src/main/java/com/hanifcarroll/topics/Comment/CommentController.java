package com.hanifcarroll.topics.Comment;

import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentRepository commentRepository;

    public CommentController (CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @PostMapping({"", "/"})
    public Comment createComment(
            @RequestParam("body") String body,
            @RequestParam("author") String author
    ) {
        Comment newComment = new Comment();
        newComment.setBody(body);
        newComment.setAuthor(author);

        commentRepository.save(newComment);

        return newComment;
    }

    @GetMapping({"/{id}", "/{id}/"})
    public Comment getComment(@PathVariable("id") Long id) {
        return commentRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
