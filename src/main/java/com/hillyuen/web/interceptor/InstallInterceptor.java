package com.hillyuen.web.interceptor;

import com.hillyuen.model.enums.BlogProperties;
import com.hillyuen.model.dto.HaloConst;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author : Hill_Yuen
 * @date : 2018/1/28
 */
@Component
public class InstallInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        if (StringUtils.equals("true", HaloConst.OPTIONS.get(BlogProperties.IS_INSTALL.getProp()))) {
            return true;
        }
        response.sendRedirect("/install");
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
