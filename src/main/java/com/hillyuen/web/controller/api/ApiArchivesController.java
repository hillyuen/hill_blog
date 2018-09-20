package com.hillyuen.web.controller.api;

import com.hillyuen.model.dto.Archive;
import com.hillyuen.model.dto.Result;
import com.hillyuen.model.enums.ResponseStatus;
import com.hillyuen.service.PostService;
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
@RequestMapping(value = "/api/archives")
public class ApiArchivesController {

    @Autowired
    private PostService postService;

    /**
     * 根据年份归档
     *
     * @return Result
     */
    @GetMapping(value = "/year")
    public Result archivesYear() {
        List<Archive> archives = postService.findPostGroupByYear();
        if (null != archives || archives.size() > 0) {
            return new Result(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS.getMsg(), archives);
        } else {
            return new Result(ResponseStatus.EMPTY.getCode(), ResponseStatus.EMPTY.getMsg());
        }
    }

    /**
     * 根据月份归档
     *
     * @return Result
     */
    @GetMapping(value = "/year/month")
    public Result archivesYearAndMonth() {
        List<Archive> archives = postService.findPostGroupByYearAndMonth();
        if (null != archives || archives.size() > 0) {
            return new Result(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS.getMsg(), archives);
        } else {
            return new Result(ResponseStatus.EMPTY.getCode(), ResponseStatus.EMPTY.getMsg());
        }
    }
}
