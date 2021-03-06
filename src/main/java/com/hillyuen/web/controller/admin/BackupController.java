package com.hillyuen.web.controller.admin;

import com.hillyuen.model.dto.Result;
import com.hillyuen.model.domain.Post;
import com.hillyuen.model.domain.User;
import com.hillyuen.model.dto.BackupDto;
import com.hillyuen.model.dto.HaloConst;
import com.hillyuen.model.enums.PostType;
import com.hillyuen.model.enums.ResultCode;
import com.hillyuen.service.MailService;
import com.hillyuen.service.PostService;
import com.hillyuen.utils.HillUtils;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ZipUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author : Hill_Yuen
 * @date : 2018/1/21
 */
@Slf4j
@Controller
@RequestMapping(value = "/admin/backup")
public class BackupController {

    @Autowired
    private PostService postService;

    @Autowired
    private MailService mailService;


    /**
     * 渲染备份页面
     *
     * @param model model
     * @return 模板路径admin/admin_backup
     */
    @GetMapping
    public String backup(@RequestParam(value = "type",defaultValue = "resources") String type,Model model) {
        List<BackupDto> backups = null;
        if(StringUtils.equals(type,"resources")){
            backups = HillUtils.getBackUps("resources");
        }else if(StringUtils.equals(type,"databases")){
            backups = HillUtils.getBackUps("databases");
        }else if(StringUtils.equals(type,"posts")){
            backups = HillUtils.getBackUps("posts");
        }else{
            backups = new ArrayList<>();
        }
        model.addAttribute("backups", backups);
        model.addAttribute("type", type);
        return "admin/admin_backup";
    }

    /**
     * 执行备份
     *
     * @param type 备份类型
     * @return Result
     */
    @GetMapping(value = "doBackup")
    @ResponseBody
    public Result doBackup(@RequestParam("type") String type) {
        if (StringUtils.equals("resources", type)) {
            return this.backupResources();
        } else if (StringUtils.equals("databases", type)) {
            return this.backupDatabase();
        } else if (StringUtils.equals("posts", type)) {
            return this.backupPosts();
        } else {
            return new Result(ResultCode.FAIL.getCode(), "备份失败！");
        }
    }

    /**
     * 备份数据库
     *
     * @return 重定向到/admin/backup
     */
    public Result backupDatabase() {
        try {
            if(HillUtils.getBackUps("databases").size()>10){
                FileUtil.del(System.getProperties().getProperty("user.home") + "/halo/backup/databases/");
            }
            String srcPath = System.getProperties().getProperty("user.home") + "/halo/";
            String distName = "databases_backup_" + HillUtils.getStringDate("yyyyMMddHHmmss");
            //压缩文件
            ZipUtil.zip(srcPath + "halo.mv.db", System.getProperties().getProperty("user.home") + "/halo/backup/databases/" + distName + ".zip");
            log.info("当前时间："+DateUtil.now()+"，执行了数据库备份。");
            return new Result(ResultCode.SUCCESS.getCode(), "备份成功！");
        } catch (Exception e) {
            log.error("备份数据库失败：{}", e.getMessage());
            return new Result(ResultCode.FAIL.getCode(), "备份失败！");
        }
    }

    /**
     * 备份资源文件 重要
     *
     * @return Result
     */
    public Result backupResources() {
        try {
            if(HillUtils.getBackUps("resources").size()>10){
                FileUtil.del(System.getProperties().getProperty("user.home") + "/halo/backup/resources/");
            }
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            String srcPath = path.getAbsolutePath();
            String distName = "resources_backup_" + HillUtils.getStringDate("yyyyMMddHHmmss");
            //执行打包
            ZipUtil.zip(srcPath, System.getProperties().getProperty("user.home") + "/halo/backup/resources/" + distName + ".zip");
            log.info("当前时间："+DateUtil.now()+"，执行了资源文件备份。");
            return new Result(ResultCode.SUCCESS.getCode(), "备份成功！");
        } catch (Exception e) {
            log.error("备份资源文件失败：{}", e.getMessage());
            return new Result(ResultCode.FAIL.getCode(), "备份失败！");
        }
    }

    /**
     * 备份文章，导出markdown文件
     *
     * @return Result
     */
    public Result backupPosts() {
        List<Post> posts = postService.findAllPosts(PostType.POST_TYPE_POST.getDesc());
        posts.addAll(postService.findAllPosts(PostType.POST_TYPE_PAGE.getDesc()));
        try {
            if(HillUtils.getBackUps("posts").size()>10){
                FileUtil.del(System.getProperties().getProperty("user.home") + "/halo/backup/posts/");
            }
            //打包好的文件名
            String distName = "posts_backup_" + HillUtils.getStringDate("yyyyMMddHHmmss");
            String srcPath = System.getProperties().getProperty("user.home") + "/halo/backup/posts/" + distName;
            for (Post post : posts) {
                HillUtils.postToFile(post.getPostContentMd(), srcPath, post.getPostTitle() + ".md");
            }
            //打包导出好的文章
            ZipUtil.zip(srcPath, srcPath + ".zip");
            FileUtil.del(srcPath);
            log.info("当前时间："+DateUtil.now()+"，执行了文章备份。");
            return new Result(ResultCode.SUCCESS.getCode(), "备份成功！");
        } catch (Exception e) {
            log.error("备份文章失败：{}", e.getMessage());
            return new Result(ResultCode.FAIL.getCode(), "备份失败！");
        }
    }

    /**
     * 删除备份
     *
     * @param fileName 文件名
     * @param type     备份类型
     * @return Result
     */
    @GetMapping(value = "delBackup")
    @ResponseBody
    public Result delBackup(@RequestParam("fileName") String fileName,
                                @RequestParam("type") String type) {
        String srcPath = System.getProperties().getProperty("user.home") + "/halo/backup/" + type + "/" + fileName;
        try {
            FileUtil.del(srcPath);
            return new Result(ResultCode.SUCCESS.getCode(), "删除成功！");
        } catch (Exception e) {
            return new Result(ResultCode.FAIL.getCode(), "删除失败！");
        }
    }

    /**
     * 将备份发送到邮箱
     *
     * @param fileName 文件名
     * @param type     备份类型
     * @return Result
     */
    @GetMapping(value = "sendToEmail")
    @ResponseBody
    public Result sendToEmail(@RequestParam("fileName") String fileName,
                                  @RequestParam("type") String type,
                                  HttpSession session) {
        String srcPath = System.getProperties().getProperty("user.home") + "/halo/backup/" + type + "/" + fileName;
        User user = (User) session.getAttribute(HaloConst.USER_SESSION_KEY);
        if (null == user.getUserEmail() || StringUtils.equals(user.getUserEmail(), "")) {
            return new Result(ResultCode.FAIL.getCode(), "博主邮箱没有配置！");
        }
        if (StringUtils.equals(HaloConst.OPTIONS.get("smtp_email_enable"), "false")) {
            return new Result(ResultCode.FAIL.getCode(), "发信邮箱没有配置！");
        }
        new EmailToAdmin(srcPath, user).start();
        return new Result(ResultCode.SUCCESS.getCode(), "邮件发送成功！");
    }

    class EmailToAdmin extends Thread {
        private String srcPath;
        private User user;

        public EmailToAdmin(String srcPath, User user) {
            this.srcPath = srcPath;
            this.user = user;
        }

        @Override
        public void run() {
            File file = new File(srcPath);
            Map<String, Object> content = new HashMap<>();
            try {
                content.put("fileName", file.getName());
                content.put("createAt", HillUtils.getCreateTime(srcPath));
                content.put("size", HillUtils.parseSize(file.length()));
                mailService.sendAttachMail(user.getUserEmail(), "有新的备份！", content, "common/mail/mail_attach.ftl", srcPath);
            } catch (Exception e) {
                log.error("邮件服务器未配置：{}", e.getMessage());
            }
        }
    }
}
