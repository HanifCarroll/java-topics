package com.hanifcarroll.topics.Topic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Long> {

    List<Topic> findAllByOrderByCreatedAtDesc();

    List<Topic> findAllByOrderByUpdatedAtDesc();
}
