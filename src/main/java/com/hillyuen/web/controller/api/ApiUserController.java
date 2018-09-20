package com.hillyuen.web.controller.api;

import com.hillyuen.model.domain.User;
import com.hillyuen.model.dto.Result;
import com.hillyuen.model.enums.ResponseStatus;
import com.hillyuen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : Hill_Yuen
 * @date : 2018/6/6
 */
@RestController
@RequestMapping(value = "/api/user")
public class ApiUserController {

    @Autowired
    private UserService userService;

    /**
     * 获取博主信息
     *
     * @return Result
     */
    @GetMapping
    public Result user() {
        User user = userService.findUser();
        return new Result(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS.getMsg(), user);
    }
}
