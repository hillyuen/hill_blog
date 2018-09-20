package com.hillyuen.web.controller.front;

import com.hillyuen.model.domain.Gallery;
import com.hillyuen.model.domain.Post;
import com.hillyuen.model.domain.User;
import com.hillyuen.model.enums.CommentStatus;
import com.hillyuen.model.enums.MonthChainese;
import com.hillyuen.model.enums.PostType;
import com.hillyuen.service.CommentService;
import com.hillyuen.service.GalleryService;
import com.hillyuen.service.PostService;
import com.hillyuen.service.UserService;
import com.hillyuen.utils.CommentUtil;
import com.hillyuen.web.controller.core.BaseController;
import com.hillyuen.model.domain.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author : Hill_Yuen
 * @date : 2018/4/26
 */
@Controller
public class FrontPageController extends BaseController {

    @Autowired
    private GalleryService galleryService;

    @Autowired
    private PostService postService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;


    /**
     * 跳转到图库页面
     *
     * @return 模板路径/themes/{theme}/gallery
     */
    @GetMapping(value = "/pic")
    public String gallery(Model model) {
        List<Gallery> galleries = galleryService.findAllGalleries();
        model.addAttribute("galleries", galleries);
        return this.render("gallery");
    }


    /**
     * 关于
     * @return
     */
    @GetMapping("about")
    private String about(Model model) {
        /** 指向about自定义页面 **/
        Post post = postService.findByPostUrl("about", PostType.POST_TYPE_PAGE.getDesc());
        Sort sort = new Sort(Sort.Direction.DESC,"commentDate");
        Pageable pageable = PageRequest.of(0,999,sort);
        Page<Comment> comments = commentService.findCommentsByPostAndCommentStatus(post,pageable, CommentStatus.PUBLISHED.getCode());
        if(null==post){
            return this.renderNotFound();
        }
        model.addAttribute("post", post);
        model.addAttribute("comments", CommentUtil.getComments(comments.getContent()));
        model.addAttribute("commentsCount", comments.getTotalElements());
        postService.updatePostView(post);
        return this.render("page");
    }


    /**
     * 关于
     * @return
     */
    @GetMapping("links")
    private String links(Model model) {
        /** 指向links自定义页面 **/
        Post post = postService.findByPostUrl("links", PostType.POST_TYPE_PAGE.getDesc());
        Sort sort = new Sort(Sort.Direction.DESC,"commentDate");
        Pageable pageable = PageRequest.of(0,999,sort);
        Page<Comment> comments = commentService.findCommentsByPostAndCommentStatus(post,pageable, CommentStatus.PUBLISHED.getCode());
        if(null==post){
            return this.renderNotFound();
        }
        model.addAttribute("post", post);
        model.addAttribute("comments", CommentUtil.getComments(comments.getContent()));
        model.addAttribute("commentsCount", comments.getTotalElements());
        postService.updatePostView(post);
        return this.render("page");
    }

    /**
     * 渲染自定义页面
     *
     * @param postUrl 页面路径
     * @param model   model
     * @return 模板路径/themes/{theme}/post
     */
    @GetMapping(value = "/p/{postUrl}")
    public String getPage(@PathVariable(value = "postUrl") String postUrl, Model model) {
        User user = userService.findUser();
        model.addAttribute("user",user);
        Post post = postService.findByPostUrl(postUrl, PostType.POST_TYPE_PAGE.getDesc());

        Sort sort = new Sort(Sort.Direction.DESC,"commentDate");
        Pageable pageable = PageRequest.of(0,999,sort);
        Page<Comment> comments = commentService.findCommentsByPostAndCommentStatus(post,pageable, CommentStatus.PUBLISHED.getCode());
        if(null==post){
            return this.renderNotFound();
        }
        model.addAttribute("post", post);
        model.addAttribute("comments", CommentUtil.getComments(comments.getContent()));
        model.addAttribute("commentsCount", comments.getTotalElements());
        postService.updatePostView(post);
        return this.render("page");
    }
}
