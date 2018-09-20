package com.hillyuen.web.controller.api;

import com.hillyuen.model.dto.Result;
import com.hillyuen.model.enums.ResponseStatus;
import com.hillyuen.service.PostService;
import com.hillyuen.model.domain.Post;
import com.hillyuen.model.enums.PostType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Hill_Yuen
 * @date : 2018/6/6
 */
@RestController
@RequestMapping(value = "/api/pages")
public class ApiPageController {

    @Autowired
    private PostService postService;

    /**
     * 获取单个页面
     *
     * @return Result
     */
    @GetMapping(value = "/{postUrl}")
    public Result pages(@PathVariable(value = "postUrl") String postUrl) {
        Post post = postService.findByPostUrl(postUrl, PostType.POST_TYPE_PAGE.getDesc());
        if (null != post) {
            return new Result(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS.getMsg(), post);
        } else {
            return new Result(ResponseStatus.NOTFOUND.getCode(), ResponseStatus.NOTFOUND.getMsg());
        }
    }
}
