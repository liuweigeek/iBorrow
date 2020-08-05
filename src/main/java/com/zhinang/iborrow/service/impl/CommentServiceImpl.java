package com.zhinang.iborrow.service.impl;

import com.zhinang.iborrow.dao.BaseDao;
import com.zhinang.iborrow.entity.Comment;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.service.CommentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

    @Resource
    private BaseDao<Comment> baseDao;

    @Override
    public void saveComment(Comment comment) {
        baseDao.merge(comment);
    }

    @Override
    public void deleteComment(Comment comment) {
        baseDao.delete(comment);
    }

    @Override
    public List<Comment> findCommentList(Comment comment, PageBean pageBean) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("from Comment");
        if (comment != null) {
            if (comment.getUser() != null) {
                hql.append(" and user = ?");
                param.add(comment.getUser());
            }
            if (comment.getProduct() != null) {
                hql.append(" and product = ?");
                param.add(comment.getProduct());
            }
        }

        if (pageBean != null) {
            return baseDao.find(hql.toString().replaceFirst("and", "where"), param, pageBean);
        } else {
            return baseDao.find(hql.toString().replaceFirst("and", "where"), param);
        }
    }

    @Override
    public Long getCommentCount(Comment comment) {
        List<Object> param = new LinkedList<Object>();
        StringBuffer hql = new StringBuffer("select count(*) from Comment");
        if (comment != null) {
            if (comment.getUser() != null) {
                hql.append(" and user = ?");
                param.add(comment.getUser());
            }
            if (comment.getProduct() != null) {
                hql.append(" and product = ?");
                param.add(comment.getProduct());
            }
        }
        return baseDao.count(hql.toString().replaceFirst("and", "where"), param);
    }

    @Override
    public Comment findCommentById(int id) {
        return baseDao.get(Comment.class, id);
    }
}