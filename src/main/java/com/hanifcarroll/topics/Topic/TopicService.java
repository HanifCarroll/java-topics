package com.hanifcarroll.topics.Topic;

import com.hanifcarroll.topics.Comment.Comment;
import com.hanifcarroll.topics.Comment.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;

@Service
public class TopicService {

    private TopicRepository topicRepository;
    private CommentRepository commentRepository;

    public TopicService(TopicRepository topicRepository, CommentRepository commentRepository) {
        this.topicRepository = topicRepository;
        this.commentRepository = commentRepository;
    }

    public Topic saveTopic(String title, String author, String description) {
        Topic newTopic = new Topic();

        newTopic.setTitle(title.trim());
        newTopic.setAuthor(author.trim());
        newTopic.setDescription(description);

        return topicRepository.save(newTopic);
    }

    public Page<Topic> findPaginated(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Topic> topicList;
        List<Topic> allTopics = topicRepository.findAllByOrderByCreatedAtDesc();

        if (allTopics.size() < startItem) {
            topicList = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, allTopics.size());
            topicList = allTopics.subList(startItem, toIndex);
        }

        Page<Topic> topicPage = new PageImpl<Topic>(topicList, PageRequest.of(currentPage, pageSize), allTopics.size());

        return topicPage;
    }

    public Topic findById(Long id) {
        return topicRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public void addCommentToTopic(String author, String body, Topic topic) {

        Comment newComment = new Comment(author.trim(), body, topic);
        commentRepository.save(newComment);
    }
}
