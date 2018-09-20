package com.hillyuen.web.controller.core;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : Hill_Yuen
 * @date : 2017/12/26
 */
@Slf4j
@Controller
public class CommonController implements ErrorController {

    private static final String ERROR_PATH = "/error";

    /**
     * 渲染404，500
     *
     * @param request request
     * @return String
     */
    @GetMapping(value = ERROR_PATH)
    public String handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == 404) {
            return "forward:/404";
        } else {
            return "forward:/500";
        }
    }

    /**
     * 渲染404页面
     *
     * @param model model
     * @return String
     */
    @GetMapping(value = "/404")
    public String fourZeroFour(HttpServletResponse response) {
        // 设置状态为404
        response.setStatus(404);
        return "common/404";
    }

    /**
     * 渲染500页面
     *
     * @param model model
     * @return String
     */
    @GetMapping(value = "/500")
    public String fiveZeroZero(HttpServletResponse response) {
        // 设置状态为500
        response.setStatus(500);
        return "common/500";
    }

    /**
     * Returns the path of the error page.
     *
     * @return the error path
     */
    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }
}
