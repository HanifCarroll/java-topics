package com.hanifcarroll.topics.Topic;

import com.hanifcarroll.topics.Comment.Comment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Controller
public class TopicController {

    private final TopicRepository topicRepository;

    public TopicController(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @PostMapping({"/topics", "/topics/"})
    public String createTopic(
            @Valid Topic topic,
            BindingResult bindingResult,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("author") String author,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            return "new-topic";
        }

        Topic newTopic = new Topic();
        newTopic.setTitle(title.trim());
        newTopic.setAuthor(author.trim());
        newTopic.setDescription(description);

        topicRepository.save(newTopic);

        redirectAttributes.addFlashAttribute("topic", newTopic);
        redirectAttributes.addFlashAttribute("success", "Topic created");
        return "redirect:/topics/" + newTopic.getId();
    }

    @GetMapping({"/new", "/new/"})
    public String getNewPage(Model model) {
        model.addAttribute("topic", new Topic());

        return "new-topic";
    }

    @GetMapping({"", "/", "/topics", "/topics/"})
    public String getTopics(Model model) {
        List<Topic> topics = topicRepository.findAllByOrderByCreatedAtDesc();

        model.addAttribute("topics", topics);

        return "index";
    }

    @GetMapping({"/topics/{id}", "/topics/{id}/"})
    public String getTopic(@PathVariable("id") Long id, Model model) {

        Topic topic = topicRepository.findById(id).orElseThrow(EntityNotFoundException::new);

        model.addAttribute("topic", topic);
        model.addAttribute("comment", new Comment());
        return "show-topic";
    }
}
