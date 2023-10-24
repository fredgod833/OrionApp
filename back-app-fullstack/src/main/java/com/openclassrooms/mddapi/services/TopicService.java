package com.openclassrooms.mddapi.services;

import java.util.List;
import com.openclassrooms.mddapi.models.Topic;

public interface TopicService {

    public List<Topic> getAllTopics();

    public Topic findById(Long id);

}
