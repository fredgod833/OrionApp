package com.openclassrooms.mddapi.repository;

import com.openclassrooms.mddapi.model.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, Integer> {
    @Modifying
    @Query(
        value = "SELECT * FROM mdd.topics WHERE id IN (:listTopicId)", nativeQuery = true
    )
    List<Topic> findTopicsSubscription(List<String> listTopicId);

    @Modifying
    @Query(
        value = "SELECT * FROM mdd.topics WHERE id NOT IN (:listTopicId)", nativeQuery = true
    )
    List<Topic> findTopicsNoneSubscription(List<String> listTopicId);

}


