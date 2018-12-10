package com.hanifcarroll.topics.Topic;

import com.hanifcarroll.topics.Comment.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
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

        Topic newTopic = topicService.saveTopic(title, author, description);

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
    public String getTopics(
            Model model,
            @RequestParam("page")Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size
    ) {
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(10);

        Page<Topic> topicPage = topicService.findPaginated(PageRequest.of(currentPage -1, pageSize));

        model.addAttribute("topicPage", topicPage);

        int totalPages = topicPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "index";
    }

    @GetMapping({"/topics/{id}", "/topics/{id}/"})
    public String getTopic(@PathVariable("id") Long id, Model model) {

        Topic topic = topicService.findById(id);

        model.addAttribute("topic", topic);
        model.addAttribute("comment", new Comment());
        return "show-topic";
    }

    @PostMapping({"/topics/{id}", "/topics/{id}/"})
    public String createComment(
            @Valid Comment comment,
            BindingResult bindingResult,
            Model model,
            @RequestParam("body") String body,
            @RequestParam("author") String author,
            @PathVariable("id") Long topicId,
            RedirectAttributes redirectAttributes
    ) {
        Topic topic = topicService.findById(topicId);

        if (bindingResult.hasErrors()) {
            model.addAttribute("topic", topic);
            return "show-topic";
        }

        topicService.addCommentToTopic(author, body, topic);

        redirectAttributes.addFlashAttribute("success", "Comment created");
        return "redirect:/topics/" + topicId;
    }
}
