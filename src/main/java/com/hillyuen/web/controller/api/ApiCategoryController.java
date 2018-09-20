package com.hillyuen.web.controller.api;

import com.hillyuen.model.dto.Result;
import com.hillyuen.model.domain.Category;
import com.hillyuen.model.enums.ResponseStatus;
import com.hillyuen.service.CategoryService;
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
@RequestMapping(value = "/api/categories")
public class ApiCategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 获取所有分类
     *
     * @return Result
     */
    @GetMapping
    public Result categories() {
        List<Category> categories = categoryService.findAllCategories();
        if (null != categories && categories.size() > 0) {
            return new Result(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS.getMsg(), categories);
        } else {
            return new Result(ResponseStatus.EMPTY.getCode(), ResponseStatus.EMPTY.getMsg());
        }
    }

    /**
     * 获取单个分类的信息
     *
     * @param cateUrl 分类路径
     * @return Result
     */
    @GetMapping(value = "/{cateUrl}")
    public Result categories(@PathVariable("cateUrl") String cateUrl) {
        Category category = categoryService.findByCateUrl(cateUrl);
        if (null != category) {
            return new Result(ResponseStatus.SUCCESS.getCode(), ResponseStatus.SUCCESS.getMsg(), category);
        } else {
            return new Result(ResponseStatus.EMPTY.getCode(), ResponseStatus.EMPTY.getMsg());
        }
    }
}
