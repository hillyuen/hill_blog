package com.hillyuen.web.controller.core;

import cn.hutool.core.date.DateUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.servlet.ServletUtil;
import com.hillyuen.model.domain.*;
import com.hillyuen.model.dto.HaloConst;
import com.hillyuen.model.dto.LogsRecord;
import com.hillyuen.model.enums.BlogProperties;
import com.hillyuen.service.*;
import com.hillyuen.utils.CommonUtils;
import com.hillyuen.utils.HillUtils;
import freemarker.template.Configuration;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : Hill_Yuen
 * @date : 2018/1/28
 */
@Slf4j
@Controller
@RequestMapping(value = "/install")
public class InstallController {

    @Autowired
    private OptionsService optionsService;

    @Autowired
    private UserService userService;

    @Autowired
    private LogsService logsService;

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private MenuService menuService;

    @Autowired
    private Configuration configuration;

    /**
     * 渲染安装页面
     *
     * @param model model
     * @return 模板路径
     */
    @GetMapping
    public String install(Model model) {
        try {
            if (StringUtils.equals("true", HaloConst.OPTIONS.get(BlogProperties.IS_INSTALL.getProp()))) {
                model.addAttribute("isInstall", true);
            } else {
                model.addAttribute("isInstall", false);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return "common/install";
    }

    /**
     * 执行安装
     *
     * @param blogTitle       博客标题
     * @param blogUrl         博客网址
     * @param userName        用户名
     * @param userDisplayName 用户名显示名
     * @param userEmail       用户邮箱
     * @param userPwd         用户密码
     * @param request         request
     * @return true：安装成功，false：安装失败
     */
    @PostMapping(value = "/do")
    @ResponseBody
    public boolean doInstall(@RequestParam("blogTitle") String blogTitle,
                             @RequestParam("blogUrl") String blogUrl,
                             @RequestParam("userName") String userName,
                             @RequestParam("userDisplayName") String userDisplayName,
                             @RequestParam("userEmail") String userEmail,
                             @RequestParam("userPwd") String userPwd,
                             HttpServletRequest request) {
        try {
            if (StringUtils.equals("true", HaloConst.OPTIONS.get(BlogProperties.IS_INSTALL.getProp()))) {
                return false;
            }
            //创建新的用户
            User user = new User();
            user.setUserName(userName);
            if (StringUtils.isBlank(userDisplayName)) {
                userDisplayName = userName;
            }
            user.setUserDisplayName(userDisplayName);
            user.setUserEmail(userEmail);
            user.setUserPass(SecureUtil.md5(userPwd));
            userService.saveByUser(user);

            //默认分类
            Category category = new Category();
            category.setCateName("未分类");
            category.setCateUrl("default");
            category.setCateDesc("未分类");
            categoryService.saveByCategory(category);

            optionsService.saveOption(BlogProperties.IS_INSTALL.getProp(), "true");

            //保存博客标题和博客地址设置
            optionsService.saveOption(BlogProperties.BLOG_TITLE.getProp(), blogTitle);
            optionsService.saveOption(BlogProperties.BLOG_URL.getProp(), blogUrl);

            //设置默认主题
            optionsService.saveOption(BlogProperties.THEME.getProp(), "anatole");

            //建立网站时间
            optionsService.saveOption(BlogProperties.BLOG_START.getProp(), CommonUtils.getSimpleDate(new Date()));

            //默认评论系统
            optionsService.saveOption(BlogProperties.COMMENT_SYSTEM.getProp(), "native");

            //默认不配置邮件系统
            optionsService.saveOption(BlogProperties.SMTP_EMAIL_ENABLE.getProp(), "false");

            //新评论，审核通过，回复，都默认设置不通知
            optionsService.saveOption(BlogProperties.NEW_COMMENT_NOTICE.getProp(), "false");
            optionsService.saveOption(BlogProperties.COMMENT_PASS_NOTICE.getProp(), "false");
            optionsService.saveOption(BlogProperties.COMMENT_REPLY_NOTICE.getProp(), "false");

            //更新日志
            logsService.saveByLogs(
                    new Logs(
                            LogsRecord.INSTALL,
                            "安装成功。安装时间：" + CommonUtils.getSimpleDate(new Date()),
                            ServletUtil.getClientIP(request),
                            DateUtil.date()
                    )
            );

            Menu menuIndex = new Menu();
            menuIndex.setMenuName("首页");
            menuIndex.setMenuUrl("/");
            menuIndex.setMenuSort(1);
            menuIndex.setMenuIcon("");
            menuService.saveByMenu(menuIndex);

            Menu menuArchive = new Menu();
            menuArchive.setMenuName("归档");
            menuArchive.setMenuUrl("/archives");
            menuArchive.setMenuSort(2);
            menuArchive.setMenuIcon("");
            menuService.saveByMenu(menuArchive);

            Menu menuAbout = new Menu();
            menuAbout.setMenuName("关于");
            menuAbout.setMenuUrl("/about");
            menuAbout.setMenuSort(3);
            menuAbout.setMenuIcon("");
            menuService.saveByMenu(menuAbout);

            HaloConst.OPTIONS.clear();
            HaloConst.OPTIONS = optionsService.findAllOptions();

            configuration.setSharedVariable("options", optionsService.findAllOptions());
            configuration.setSharedVariable("user", userService.findUser());
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }
}
