package com.zhinang.iborrow.service.impl;

import com.zhinang.iborrow.dao.BaseDao;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.Topic;
import com.zhinang.iborrow.service.TopicService;
import com.zhinang.iborrow.util.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service("topicService")
public class TopicServiceImpl implements TopicService {

    @Resource
    private BaseDao<Topic> baseDao;

    @Override
    public void saveTopic(Topic topic) {
        baseDao.merge(topic);
    }

    @Override
    public void deleteTopic(Topic topic) {
        baseDao.delete(topic);
    }

    @Override
    public List<Topic> findTopicList(Topic topic, PageBean pageBean) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("from Topic t");
        if (topic != null) {
            if (StringUtil.isNotEmpty(topic.getTitle())) {
                hql.append(" and t.title like ?");
                param.add("%" + topic.getTitle() + "%");
            }
            if (StringUtil.isNotEmpty(topic.getContent())) {
                hql.append(" and t.content like ?");
                param.add("%" + topic.getContent() + "%");
            }
            if (topic.getPublishTime() != null) {
                hql.append(" and t.publishTime = ?");
                param.add(topic.getPublishTime());
            }
        }
        if (pageBean != null) {
            return baseDao.find(hql.toString().replaceFirst("and", "where"), param, pageBean);
        } else {
            return baseDao.find(hql.toString().replaceFirst("and", "where"), param);
        }
    }

    @Override
    public Long getTopicCount(Topic topic) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("select count(*) from Topic t");
        if (topic != null) {
            if (StringUtil.isNotEmpty(topic.getTitle())) {
                hql.append(" and t.title like ?");
                param.add("%" + topic.getTitle() + "%");
            }
            if (StringUtil.isNotEmpty(topic.getContent())) {
                hql.append(" and t.content like ?");
                param.add("%" + topic.getContent() + "%");
            }
            if (topic.getPublishTime() != null) {
                hql.append(" and t.publishTime = ?");
                param.add(topic.getPublishTime());
            }
        }
        return baseDao.count(hql.toString().replaceFirst("and", "where"), param);
    }

    @Override
    public Topic findTopicById(int id) {
        return baseDao.get(Topic.class, id);
    }
}