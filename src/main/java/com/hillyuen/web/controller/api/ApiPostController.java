package com.hillyuen.web.controller.api;

import com.hillyuen.model.dto.HaloConst;
import com.hillyuen.model.dto.Result;
import com.hillyuen.model.enums.BlogProperties;
import com.hillyuen.model.enums.PostStatus;
import com.hillyuen.model.enums.ResponseStatus;
import com.hillyuen.service.PostService;
import com.hillyuen.model.domain.Post;
import com.hillyuen.model.enums.PostType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : Hill_Yuen
 * @date : 2018/6/6
 */
@RestController
@RequestMapping(value = "/api/posts")
public class ApiPostController {

    @Autowired
    private PostService postService;

    /**
     * 获取文章列表 分页
     *
     * @param page 页码
     * @return Result
     */
    @GetMapping(value = "/page/{page}")
    public Result posts(@PathVariable(value = "page") Integer page) {
        Sort sort = new Sort(Sort.Direction.DESC, "postDate");
        Integer size = 10;
        if (!StringUtils.isBlank(HaloConst.OPTIONS.get(BlogProperties.INDEX_POSTS.getProp()))) {
            size = Integer.parseInt(HaloConst.OPTIONS.get(BlogProperties.INDEX_POSTS.getProp()));
        }
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Post> posts = postService.findPostByStatus(PostStatus.PUBLISHED.getCode(), PostType.POST_TYPE_POST.getDesc(), pageable);
        if (null == posts) {
            return new Result(ResponseStatus.EMPTY.getCode(), ResponseStatus.EMPTY.getMsg());
        }
        return new Result(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS.getMsg(), posts);
    }

    @GetMapping(value = "/hot")
    public Result hotPosts() {
        List<Post> posts = postService.hotPosts();
        if (null != posts && posts.size() > 0) {
            return new Result(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS.getMsg(), posts);
        } else {
            return new Result(ResponseStatus.EMPTY.getCode(), ResponseStatus.EMPTY.getMsg());
        }
    }

    /**
     * 获取单个文章信息
     *
     * @param postUrl 文章路径
     * @return Result
     */
    @GetMapping(value = "/{postUrl}")
    public Result posts(@PathVariable(value = "postUrl") String postUrl) {
        Post post = postService.findByPostUrl(postUrl, PostType.POST_TYPE_POST.getDesc());
        if (null != post) {
            return new Result(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS.getMsg(), post);
        } else {
            return new Result(ResponseStatus.NOTFOUND.getCode(), ResponseStatus.NOTFOUND.getMsg());
        }
    }
}
