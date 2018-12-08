package com.hanifcarroll.topics.Topic;

import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicRepository topicRepository;

    public TopicController(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    @PostMapping({"", "/"})
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

    @GetMapping({"", "/"})
    public List<Topic> getTopics() {
        return topicRepository.findAllByOrderByCreatedAtDesc();
    }

    @GetMapping({"/{id}", "/{id}/"})
    public Topic getTopic(@PathVariable("id") Long id) {
        return topicRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }
}
