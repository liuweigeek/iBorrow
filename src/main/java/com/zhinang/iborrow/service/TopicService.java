package com.zhinang.iborrow.service;

import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.Topic;

import java.util.List;

public abstract interface TopicService {
    public abstract void saveTopic(Topic topic);

    public abstract void deleteTopic(Topic topic);

    public abstract List<Topic> findTopicList(Topic topic, PageBean pageBean);

    public abstract Long getTopicCount(Topic topic);

    public abstract Topic findTopicById(int id);
}