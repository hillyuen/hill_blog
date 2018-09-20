package com.hillyuen.web.controller.front;

import com.hillyuen.model.domain.*;
import com.hillyuen.model.dto.Archive;
import com.hillyuen.model.enums.MonthChainese;
import com.hillyuen.model.enums.PostStatus;
import com.hillyuen.service.*;
import com.hillyuen.utils.CommentUtil;
import com.hillyuen.web.controller.core.BaseController;
import com.hillyuen.model.enums.CommentStatus;
import com.hillyuen.model.enums.PostType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author : Hill_Yuen
 * @date : 2018/4/26
 */
@Slf4j
@Controller
@RequestMapping(value = "/archives")
public class FrontArchiveController extends BaseController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    /**
     * 文章归档
     *
     * @param model model
     * @return 模板路径
     */
    @GetMapping
    public String archives(Model model) {
        User user = userService.findUser();
        model.addAttribute("user",user);
        return this.archives(model, 1);
    }

    /**
     * 文章归档分页
     *
     * @param model model
     * @param page  page 当前页码
     * @return 模板路径/themes/{theme}/archives
     */
    @GetMapping(value = "page/{page}")
    public String archives(Model model,
                           @PathVariable(value = "page") Integer page) {

        //所有文章数据，分页，material主题适用
        Sort sort = new Sort(Sort.Direction.DESC, "postDate");
        Pageable pageable = PageRequest.of(page - 1, 5, sort);
        Page<Post> posts = postService.findPostByStatus(PostStatus.PUBLISHED.getCode(), PostType.POST_TYPE_POST.getDesc(), pageable);
        if (null == posts) {
            return this.renderNotFound();
        }
        model.addAttribute("posts", posts);
        /** 以下兼容ghost主题 包含分类，标签，年月归档 ghost主题不用上面接口的数据 **/
        List<Archive> archives = postService.findPostGroupByYearAndMonth();
        model.addAttribute("archives", archives);
        List<Category> categories = categoryService.findAllCategories();
        model.addAttribute("categories", categories);
        List<Tag> tags = tagService.findAllTags();
        model.addAttribute("tags", tags);

        return this.render("archives");
    }

    /**
     * 文章归档，根据年月
     *
     * @param model model
     * @param year  year 年份
     * @param month month 月份
     * @return 模板路径/themes/{theme}/archives
     */
    @GetMapping(value = "{year}/{month}")
    public String archives(Model model,
                           @PathVariable(value = "year") String year,
                           @PathVariable(value = "month") String month) {
        Page<Post> posts = postService.findPostByYearAndMonth(year, month, null);
        if (null == posts) {
            return this.renderNotFound();
        }
        model.addAttribute("posts", posts);
        return this.render("archives");
    }

    /**
     * 渲染文章详情
     *
     * @param postUrl 文章路径名
     * @param model   model
     * @return 模板路径/themes/{theme}/post
     */
    @GetMapping(value = "{postUrl}")
    public String getPost(@PathVariable String postUrl, Model model) {
        Post post = postService.findByPostUrl(postUrl, PostType.POST_TYPE_POST.getDesc());
        if (null == post || !post.getPostStatus().equals(PostStatus.PUBLISHED.getCode())) {
            return this.renderNotFound();
        }
        //获得当前文章的发布日期
        Date postDate = post.getPostDate();
        //查询当前文章日期之前的所有文章
        List<Post> beforePosts = postService.findByPostDateBefore(postDate);
        //查询当前文章日期之后的所有文章
        List<Post> afterPosts = postService.findByPostDateAfter(postDate);

        if (null != beforePosts && beforePosts.size() > 0) {
            model.addAttribute("beforePost", beforePosts.get(beforePosts.size() - 1));
        }
        if (null != afterPosts && afterPosts.size() > 0) {
            model.addAttribute("afterPost", afterPosts.get(afterPosts.size() - 1));
        }
        Sort sort = new Sort(Sort.Direction.DESC, "commentDate");
        Pageable pageable = PageRequest.of(0, 999, sort);
        Page<Comment> comments = commentService.findCommentsByPostAndCommentStatus(post, pageable, CommentStatus.PUBLISHED.getCode());

        /** 显示中文月份 **/
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(postDate);
        Integer month  = calendar.get(Calendar.MONTH) + 1;
        model.addAttribute("month", MonthChainese.getChainsesMonth(month+""));

        model.addAttribute("post", post);
        model.addAttribute("comments", CommentUtil.getComments(comments.getContent()));
        model.addAttribute("commentsCount", comments.getTotalElements());
        postService.updatePostView(post);
        return this.render("post");
    }
}
