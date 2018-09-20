package com.hillyuen.service.impl;

import com.hillyuen.model.domain.Category;
import com.hillyuen.repository.CategoryRepository;
import com.hillyuen.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author : Hill_Yuen
 * @date : 2017/11/30
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * 保存/修改分类目录
     *
     * @param category 分类目录
     * @return Category
     */
    @Override
    public Category saveByCategory(Category category) {
        return categoryRepository.save(category);
    }

    /**
     * 根据编号移除分类目录
     *
     * @param cateId 分类目录编号
     * @return Category
     */
    @Override
    public Category removeByCateId(Long cateId) {
        Optional<Category> category = this.findByCateId(cateId);
        categoryRepository.delete(category.get());
        return category.get();
    }

    /**
     * 查询所有分类目录
     *
     * @return List
     */
    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    /**
     * 根据编号查询分类目录
     *
     * @param cateId 分类编号
     * @return Category
     */
    @Override
    public Optional<Category> findByCateId(Long cateId) {
        return categoryRepository.findById(cateId);
    }

    /**
     * 根据分类目录路径查询，用于验证是否已经存在该路径
     *
     * @param cateUrl cateUrl
     * @return Category
     */
    @Override
    public Category findByCateUrl(String cateUrl) {
        return categoryRepository.findCategoryByCateUrl(cateUrl);
    }

    @Override
    public List<Category> strListToCateList(List<String> strings) {
        List<Category> categories = new ArrayList<>();
        if (null == strings) {
            Category defaultCat = categoryRepository.findCategoryByCateName("未分类");
            categories.add(defaultCat);
            return categories;
        }
        Optional<Category> category = null;
        for (String str : strings) {
            category = findByCateId(Long.parseLong(str));
            categories.add(category.get());
        }
        if (categories == null || categories.size() == 0) {
            categories = new ArrayList<>();
            Category defaultCat = categoryRepository.findCategoryByCateName("未分类");
            categories.add(defaultCat);
        }
        return categories;
    }
}
