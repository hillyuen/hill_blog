package com.hillyuen.utils;

import com.hillyuen.model.domain.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Hill_Yuen
 * @date : 2018/7/12
 */
public class CommentUtil {

    /**
     * 获取组装好的评论
     *
     * @param commentsRoot commentsRoot
     * @return List
     */
    public static List<Comment> getComments(List<Comment> commentsRoot) {
        List<Comment> commentsResult = new ArrayList<>();

        for (int i = 0; i < commentsRoot.size(); i++) {
            if (commentsRoot.get(i).getCommentParent() == 0) {
                commentsResult.add(commentsRoot.get(i));
            }
        }

        for (Comment comment : commentsResult) {
            comment.setChildComments(getChild(comment.getCommentId(), commentsRoot));
        }
        return commentsResult;
    }

    /**
     * 获取评论的子评论
     *
     * @param id           评论编号
     * @param commentsRoot commentsRoot
     * @return List
     */
    private static List<Comment> getChild(Long id, List<Comment> commentsRoot) {
        List<Comment> commentsChild = new ArrayList<>();
        for (Comment comment : commentsRoot) {
            if (comment.getCommentParent() != 0) {
                if (comment.getCommentParent().equals(id)) {
                    commentsChild.add(comment);
                }
            }
        }
        for (Comment comment : commentsChild) {
            if (comment.getCommentParent() != 0) {
                comment.setChildComments(getChild(comment.getCommentId(), commentsRoot));
            }
        }
        if (commentsChild.size() == 0) {
            return null;
        }
        return commentsChild;
    }
}
