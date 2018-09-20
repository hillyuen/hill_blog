package com.hillyuen.web.controller.api;

import com.hillyuen.model.dto.Result;
import com.hillyuen.model.enums.ResponseStatus;
import com.hillyuen.model.domain.Tag;
import com.hillyuen.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/api/tags")
public class ApiTagController {

    @Autowired
    private TagService tagService;

    /**
     * 获取所有标签
     *
     * @return Result
     */
    @GetMapping
    public Result tags() {
        List<Tag> tags = tagService.findAllTags();
        if (null != tags && tags.size() > 0) {
            return new Result(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS.getMsg(), tags);
        } else {
            return new Result(ResponseStatus.EMPTY.getCode(), ResponseStatus.EMPTY.getMsg());
        }
    }

    /**
     * 获取单个标签的信息
     *
     * @param tagUrl tagUrl
     * @return Result
     */
    @GetMapping(value = "/{tagUrl}")
    public Result tags(@PathVariable("tagUrl") String tagUrl) {
        Tag tag = tagService.findByTagUrl(tagUrl);
        if (null != tag) {
            return new Result(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS.getMsg(), tag);
        } else {
            return new Result(ResponseStatus.NOTFOUND.getCode(), ResponseStatus.NOTFOUND.getMsg());
        }
    }
}
