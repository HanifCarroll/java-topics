package com.hanifcarroll.topics.Topic;

import org.springframework.stereotype.Controller;
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
    public Topic createTopic(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("author") String author
    ) {
        Topic newTopic = new Topic();
        newTopic.setTitle(title);
        newTopic.setAuthor(author);
        newTopic.setDescription(description);

        topicRepository.save(newTopic);

        return newTopic;
    }

    @GetMapping({"/new", "/new/"})
    public String getNewPage() {
        return "new-post";
    }

    @GetMapping({"", "/"})
    public List<Topic> getTopics() {
        return topicRepository.findAllByOrderByCreatedAtDesc();
    }

    @GetMapping({"/topics/{id}", "/topics/{id}/"})
    public Topic getTopic(@PathVariable("id") Long id) {
        return topicRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
