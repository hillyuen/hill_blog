package com.hillyuen.web.controller.front;

import com.hillyuen.model.domain.User;
import com.hillyuen.model.enums.BlogProperties;
import com.hillyuen.service.PostService;
import com.hillyuen.model.domain.Post;
import com.hillyuen.model.domain.Tag;
import com.hillyuen.model.dto.HaloConst;
import com.hillyuen.service.TagService;
import com.hillyuen.service.UserService;
import com.hillyuen.web.controller.core.BaseController;
import org.apache.commons.lang3.StringUtils;
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

/**
 * @author : Hill_Yuen
 * @date : 2018/4/26
 */
@Controller
@RequestMapping(value = "/tags")
public class FrontTagController extends BaseController {

    @Autowired
    private TagService tagService;

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    /**
     * 标签
     *
     * @return 模板路径/themes/{theme}/tags
     */
    @GetMapping
    public String tags(Model model) {
        User user = userService.findUser();
        model.addAttribute("user",user);
        return this.render("tags");
    }

    /**
     * 根据标签路径查询所有文章
     *
     * @param tagUrl 标签路径
     * @param model  model
     * @return String
     */
    @GetMapping(value = "{tagUrl}")
    public String tags(Model model,
                       @PathVariable("tagUrl") String tagUrl) {
        User user = userService.findUser();
        model.addAttribute("user",user);
        return this.tags(model, tagUrl, 1);
    }

    /**
     * 根据标签路径查询所有文章 分页
     *
     * @param model  model
     * @param tagUrl 标签路径
     * @param page   页码
     * @return String
     */
    @GetMapping(value = "{tagUrl}/page/{page}")
    public String tags(Model model,
                       @PathVariable("tagUrl") String tagUrl,
                       @PathVariable("page") Integer page) {
        Tag tag = tagService.findByTagUrl(tagUrl);
        if(null==tag){
            return this.renderNotFound();
        }
        Sort sort = new Sort(Sort.Direction.DESC, "postDate");
        Integer size = 10;
        if (!StringUtils.isBlank(HaloConst.OPTIONS.get(BlogProperties.INDEX_POSTS.getProp()))) {
            size = Integer.parseInt(HaloConst.OPTIONS.get(BlogProperties.INDEX_POSTS.getProp()));
        }
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Post> posts = postService.findPostsByTags(tag, pageable);
        User user = userService.findUser();
        model.addAttribute("user",user);
        model.addAttribute("posts", posts);
        model.addAttribute("tag", tag);
        return this.render("tag");
    }
}
