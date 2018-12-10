package com.hanifcarroll.topics.Comment;

import com.hanifcarroll.topics.Topic.Topic;
import com.hanifcarroll.topics.Topic.TopicRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentRepository commentRepository;
    private final TopicRepository topicRepository;

    public CommentController(CommentRepository commentRepository, TopicRepository topicRepository) {
        this.commentRepository = commentRepository;
        this.topicRepository = topicRepository;
    }

    @PostMapping({"", "/"})
    public String createComment(
            @Valid Comment comment,
            BindingResult bindingResult,
            @RequestParam("body") String body,
            @RequestParam("author") String author,
            @RequestParam("topicId") Long topicId,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        Topic topic = topicRepository.findById(topicId).orElseThrow(EntityNotFoundException::new);

        if (bindingResult.hasErrors()) {
            model.addAttribute("comment", comment);
            model.addAttribute("topic", topic);
            return "show-topic";
        }


        Comment newComment = new Comment();
        newComment.setBody(body);
        newComment.setAuthor(author.trim());
        newComment.setTopic(topic);

        commentRepository.save(newComment);

        redirectAttributes.addFlashAttribute("success", "Comment created");
        return "redirect:/topics/" + topicId;
    }
}
