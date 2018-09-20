package com.hillyuen.web.controller.api;

import com.hillyuen.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Hill_Yuen
 * @date : 2018/6/6
 */
@RestController
@RequestMapping(value = "/api/comments")
public class ApiCommentController {

    @Autowired
    private CommentService commentService;
}
