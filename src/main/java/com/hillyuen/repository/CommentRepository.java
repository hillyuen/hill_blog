package com.hillyuen.repository;

import com.hillyuen.model.domain.Comment;
import com.hillyuen.model.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * <pre>
 *     评论持久层
 * </pre>
 *
 * @author : Hill_Yuen
 * @date : 2018/1/22
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    /**
     * 根据评论状态查询所有评论 分页
     *
     * @param status   文章状态
     * @param pageable 分页信息
     * @return Page
     */
    Page<Comment> findCommentsByCommentStatus(Integer status, Pageable pageable);

    /**
     * 根据评论状态查询所有评论 不分页
     *
     * @param status 文章状态
     * @return List
     */
    List<Comment> findCommentsByCommentStatus(Integer status);

    /**
     * 根据文章查询评论
     *
     * @param post     post
     * @param pageable pageable
     * @return Page
     */
    Page<Comment> findCommentsByPost(Post post, Pageable pageable);

    /**
     * 根据文章和评论状态查询评论
     *
     * @param post     post
     * @param pageable pageable
     * @param status   status
     * @return Page
     */
    Page<Comment> findCommentsByPostAndCommentStatus(Post post, Pageable pageable, Integer status);

    /**
     * 查询最新的前五条评论
     *
     * @return List
     */
    @Query(value = "SELECT * FROM halo_comment ORDER BY comment_date DESC LIMIT 5", nativeQuery = true)
    List<Comment> findTopFive();

}
