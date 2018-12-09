package com.hanifcarroll.topics.Topic;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Controller
public class TopicController {

    private final TopicRepository topicRepository;

    public TopicController(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @PostMapping({"/topics", "/topics/"})
    public String createTopic(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("author") String author,
            Model model
    ) {
        Topic newTopic = new Topic();
        newTopic.setTitle(title.trim());
        newTopic.setAuthor(author.trim());
        newTopic.setDescription(description.trim());

        topicRepository.save(newTopic);

        model.addAttribute("topic", newTopic);

        return "show-topic";
    }

    @GetMapping({"/new", "/new/"})
    public String getNewPage() {
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

        return "show-topic";
    }
}
