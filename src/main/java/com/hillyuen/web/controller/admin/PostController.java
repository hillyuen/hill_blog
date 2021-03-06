package com.hillyuen.web.controller.admin;

import cn.hutool.core.date.DateUtil;
import cn.hutool.extra.servlet.ServletUtil;
import cn.hutool.http.HtmlUtil;
import com.hillyuen.model.domain.*;
import com.hillyuen.model.dto.HaloConst;
import com.hillyuen.model.dto.Result;
import com.hillyuen.model.dto.LogsRecord;
import com.hillyuen.model.enums.BlogProperties;
import com.hillyuen.model.enums.PostStatus;
import com.hillyuen.model.enums.PostType;
import com.hillyuen.model.enums.ResultCode;
import com.hillyuen.service.CategoryService;
import com.hillyuen.service.LogsService;
import com.hillyuen.service.PostService;
import com.hillyuen.service.TagService;
import com.hillyuen.utils.HillUtils;
import com.hillyuen.web.controller.core.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

/**
 * @author : Hill_Yuen
 * @date : 2017/12/10
 */
@Slf4j
@Controller
@RequestMapping(value = "/admin/posts")
public class PostController extends BaseController {

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TagService tagService;

    @Autowired
    private LogsService logsService;

    @Autowired
    private HttpServletRequest request;

    /**
     * 去除html，htm后缀，以及将空格替换成-
     *
     * @param url url
     * @return String
     */
    private static String urlFilter(String url) {
        if (null != url) {
            final boolean urlEndsWithHtmlPostFix = url.endsWith(".html") || url.endsWith(".htm");
            if (urlEndsWithHtmlPostFix) {
                return url.substring(0, url.lastIndexOf("."));
            }
        }
        return StringUtils.replaceAll(url, " ", "-");
    }

    /**
     * 处理后台获取文章列表的请求
     *
     * @param model model
     * @param page  当前页码
     * @param size  每页显示的条数
     * @return 模板路径admin/admin_post
     */
    @GetMapping
    public String posts(Model model,
                        @RequestParam(value = "status", defaultValue = "0") Integer status,
                        @RequestParam(value = "page", defaultValue = "0") Integer page,
                        @RequestParam(value = "size", defaultValue = "10") Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "postDate");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Post> posts = postService.findPostByStatus(status, PostType.POST_TYPE_POST.getDesc(), pageable);
        model.addAttribute("posts", posts);
        model.addAttribute("publishCount", postService.findPostByStatus(PostStatus.PUBLISHED.getCode(), PostType.POST_TYPE_POST.getDesc(), pageable).getTotalElements());
        model.addAttribute("draftCount", postService.findPostByStatus(PostStatus.DRAFT.getCode(), PostType.POST_TYPE_POST.getDesc(), pageable).getTotalElements());
        model.addAttribute("trashCount", postService.findPostByStatus(PostStatus.RECYCLE.getCode(), PostType.POST_TYPE_POST.getDesc(), pageable).getTotalElements());
        model.addAttribute("status", status);
        return "admin/admin_post";
    }

    /**
     * 模糊查询文章
     *
     * @param model   Model
     * @param keyword keyword 关键字
     * @param page    page 当前页码
     * @param size    size 每页显示条数
     * @return 模板路径admin/admin_post
     */
    @PostMapping(value = "/search")
    public String searchPost(Model model,
                             @RequestParam(value = "keyword") String keyword,
                             @RequestParam(value = "page", defaultValue = "0") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size) {
        try {
            //排序规则
            Sort sort = new Sort(Sort.Direction.DESC, "postId");
            Pageable pageable = PageRequest.of(page, size, sort);
            model.addAttribute("posts", postService.searchPosts(keyword, pageable));
        } catch (Exception e) {
            log.error("未知错误：{}", e.getMessage());
        }
        return "admin/admin_post";
    }

    /**
     * 处理预览文章的请求
     *
     * @param postId 文章编号
     * @param model  model
     * @return 模板路径/themes/{theme}/post
     */
    @GetMapping(value = "/view")
    public String viewPost(@PathParam("postId") Long postId, Model model) {
        Optional<Post> post = postService.findByPostId(postId);
        model.addAttribute("post", post.get());
        return this.render("post");
    }

    /**
     * 处理跳转到新建文章页面
     *
     * @return 模板路径admin/admin_editor
     */
    @GetMapping(value = "/new")
    public String newPost() {
        return "admin/admin_post_md_editor";
    }

    /**
     * 添加文章
     *
     * @param post     Post实体
     * @param cateList 分类列表
     * @param tagList  标签列表
     * @param session  session
     */
    @PostMapping(value = "/new/push")
    @ResponseBody
    public Result pushPost(@ModelAttribute Post post, @RequestParam("cateList") List<String> cateList, @RequestParam("tagList") String tagList, HttpSession session) {
        User user = (User) session.getAttribute(HaloConst.USER_SESSION_KEY);
        String msg = "发表成功";
        try {
            //提取摘要
            int postSummary = 50;
            if (StringUtils.isNotEmpty(BlogProperties.POST_SUMMARY.getProp()) && StringUtils.isNotBlank(HaloConst.OPTIONS.get(BlogProperties.POST_SUMMARY.getProp()))) {
                postSummary = Integer.parseInt(HaloConst.OPTIONS.get(BlogProperties.POST_SUMMARY.getProp()));
            }
            //文章摘要
            String summaryText = HtmlUtil.cleanHtmlTag(post.getPostContent());
            if (summaryText.length() > postSummary) {
                String summary = summaryText.substring(0, postSummary);
                post.setPostSummary(summary);
            } else {
                post.setPostSummary(summaryText);
            }
            //添加文章时，添加文章时间和修改文章时间为当前时间，修改文章时，只更新修改文章时间
            if (null != post.getPostId()) {
                Post oldPost = postService.findByPostId(post.getPostId()).get();
                post.setPostDate(oldPost.getPostDate());
                post.setPostUpdate(DateUtil.date());
                post.setPostViews(oldPost.getPostViews());
                msg = "更新成功";
            } else {
                post.setPostDate(DateUtil.date());
                post.setPostUpdate(DateUtil.date());
            }
            post.setUser(user);
            List<Category> categories = categoryService.strListToCateList(cateList);
            post.setCategories(categories);
            if (StringUtils.isNotEmpty(tagList)) {
                List<Tag> tags = tagService.strListToTagList(StringUtils.deleteWhitespace(tagList));
                post.setTags(tags);
            }
            post.setPostUrl(urlFilter(post.getPostUrl()));
            postService.saveByPost(post);
            logsService.saveByLogs(new Logs(LogsRecord.PUSH_POST, post.getPostTitle(), ServletUtil.getClientIP(request), DateUtil.date()));
            return new Result(ResultCode.SUCCESS.getCode(), msg);
        } catch (Exception e) {
            log.error("保存文章失败：{}", e.getMessage());
            return new Result(ResultCode.FAIL.getCode(), "保存失败");
        }
    }


    /**
     * 自动保存文章为草稿
     *
     * @param postId 文章编号
     * @param postTitle 文章标题
     * @param postUrl 文章路径
     * @param postContentMd 文章内容
     * @param postType 文章类型
     * @param session session
     * @return Result
     */
    @PostMapping(value = "/new/autoPush")
    @ResponseBody
    public Result autoPushPost(@RequestParam(value = "postId", defaultValue = "0") Long postId,
                                   @RequestParam(value = "postTitle") String postTitle,
                                   @RequestParam(value = "postUrl") String postUrl,
                                   @RequestParam(value = "postContentMd") String postContentMd,
                                   @RequestParam(value = "postType", defaultValue = "post") String postType,
                                   HttpSession session) {
        Post post = null;
        User user = (User) session.getAttribute(HaloConst.USER_SESSION_KEY);
        if (postId == 0) {
            post = new Post();
        } else {
            post = postService.findByPostId(postId).get();
        }
        try {
            if (StringUtils.isEmpty(postTitle)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                post.setPostTitle("草稿：" + dateFormat.format(DateUtil.date()));
            } else {
                post.setPostTitle(postTitle);
            }
            if (StringUtils.isEmpty(postUrl)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                post.setPostUrl(dateFormat.format(DateUtil.date()));
            } else {
                post.setPostUrl(postUrl);
            }
            post.setPostId(postId);
            post.setPostStatus(1);
            post.setPostContentMd(postContentMd);
            post.setPostType(postType);
            post.setPostDate(DateUtil.date());
            post.setPostUpdate(DateUtil.date());
            post.setUser(user);
        } catch (Exception e) {
            log.error("未知错误：", e.getMessage());
            return new Result(ResultCode.FAIL.getCode(), "保存失败");
        }
        return new Result(ResultCode.SUCCESS.getCode(), "保存成功", postService.saveByPost(post));
    }


    /**
     * 处理移至回收站的请求
     *
     * @param postId 文章编号
     * @return 重定向到/admin/posts
     */
    @GetMapping("/throw")
    public String moveToTrash(@RequestParam("postId") Long postId, @RequestParam("status") Integer status) {
        try {
            postService.updatePostStatus(postId, PostStatus.RECYCLE.getCode());
            log.info("编号为" + postId + "的文章已被移到回收站");
        } catch (Exception e) {
            log.error("未知错误：{}", e.getMessage());
        }
        return "redirect:/admin/posts?status=" + status;
    }

    /**
     * 处理文章为发布的状态
     *
     * @param postId 文章编号
     * @return 重定向到/admin/posts
     */
    @GetMapping("/revert")
    public String moveToPublish(@RequestParam("postId") Long postId,
                                @RequestParam("status") Integer status) {
        try {
            postService.updatePostStatus(postId, PostStatus.PUBLISHED.getCode());
            log.info("编号为" + postId + "的文章已改变为发布状态");
        } catch (Exception e) {
            log.error("未知错误：{}", e.getMessage());
        }
        return "redirect:/admin/posts?status=" + status;
    }

    /**
     * 处理删除文章的请求
     *
     * @param postId 文章编号
     * @return 重定向到/admin/posts
     */
    @GetMapping(value = "/remove")
    public String removePost(@PathParam("postId") Long postId, @PathParam("postType") String postType) {
        try {
            Optional<Post> post = postService.findByPostId(postId);
            postService.removeByPostId(postId);
            logsService.saveByLogs(new Logs(LogsRecord.REMOVE_POST, post.get().getPostTitle(), ServletUtil.getClientIP(request), DateUtil.date()));
        } catch (Exception e) {
            log.error("未知错误：{}", e.getMessage());
        }
        if (StringUtils.equals(PostType.POST_TYPE_POST.getDesc(), postType)) {
            return "redirect:/admin/posts?status=2";
        }
        return "redirect:/admin/page";
    }

    /**
     * 跳转到编辑文章页面
     *
     * @param postId 文章编号
     * @param model  model
     * @return 模板路径admin/admin_editor
     */
    @GetMapping(value = "/edit")
    public String editPost(@PathParam("postId") Long postId, Model model) {
        Optional<Post> post = postService.findByPostId(postId);
        model.addAttribute("post", post.get());
        return "admin/admin_post_md_editor";
    }

    /**
     * 更新所有摘要
     *
     * @param postSummary 文章摘要字数
     * @return Result
     */
    @GetMapping(value = "/updateSummary")
    @ResponseBody
    public Result updateSummary(@PathParam("postSummary") Integer postSummary) {
        try {
            postService.updateAllSummary(postSummary);
        } catch (Exception e) {
            log.error("更新摘要失败：{}", e.getMessage());
            e.printStackTrace();
            return new Result(ResultCode.FAIL.getCode(), "更新失败！");
        }
        return new Result(ResultCode.SUCCESS.getCode(), "所有文章摘要更新成功！");
    }

    /**
     * 验证文章路径是否已经存在
     *
     * @param postUrl 文章路径
     * @return true：不存在，false：已存在
     */
    @GetMapping(value = "/checkUrl")
    @ResponseBody
    public boolean checkUrlExists(@PathParam("postUrl") String postUrl) {
        postUrl = urlFilter(postUrl);
        Post post = postService.findByPostUrl(postUrl, PostType.POST_TYPE_POST.getDesc());
        return null != post;
    }

    /**
     * 将所有文章推送到百度
     *
     * @param baiduToken baiduToken
     * @return Result
     */
    @GetMapping(value = "/pushAllToBaidu")
    @ResponseBody
    public Result pushAllToBaidu(@PathParam("baiduToken") String baiduToken) {
        if (StringUtils.isEmpty(baiduToken)) {
            return new Result(ResultCode.FAIL.getCode(), "百度推送Token为空！");
        }
        String blogUrl = HaloConst.OPTIONS.get(BlogProperties.BLOG_URL.getProp());
        List<Post> posts = postService.findAllPosts(PostType.POST_TYPE_POST.getDesc());
        StringBuilder urls = new StringBuilder();
        for (Post post : posts) {
            urls.append(blogUrl);
            urls.append("/archives/");
            urls.append(post.getPostUrl());
            urls.append("\n");
        }
        String result = HillUtils.baiduPost(blogUrl, baiduToken, urls.toString());
        if (StringUtils.isEmpty(result)) {
            return new Result(ResultCode.FAIL.getCode(), "推送所有文章成功！");
        }
        return new Result(ResultCode.SUCCESS.getCode(), "推送成功！");
    }
}
