package com.zhinang.iborrow.service;

import com.zhinang.iborrow.entity.Comment;
import com.zhinang.iborrow.entity.PageBean;

import java.util.List;

public abstract interface CommentService {
    public abstract void saveComment(Comment comment);

    public abstract void deleteComment(Comment comment);

    public abstract List<Comment> findCommentList(Comment comment, PageBean pageBean);

    public abstract Long getCommentCount(Comment comment);

    public abstract Comment findCommentById(int id);
}