package com.hillyuen.web.controller.api;

import com.hillyuen.model.dto.Result;
import com.hillyuen.model.enums.ResponseStatus;
import com.hillyuen.model.domain.Link;
import com.hillyuen.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author : Hill_Yuen
 * @date : 2018/6/6
 */
@RestController
@RequestMapping(value = "/api/links")
public class ApiLinkController {

    @Autowired
    private LinkService linkService;

    /**
     * 获取所有友情链接
     *
     * @return Result
     */
    @GetMapping
    public Result links() {
        List<Link> links = linkService.findAllLinks();
        if (null != links && links.size() > 0) {
            return new Result(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS.getMsg(), links);
        } else {
            return new Result(ResponseStatus.EMPTY.getCode(), ResponseStatus.EMPTY.getMsg());
        }
    }
}
