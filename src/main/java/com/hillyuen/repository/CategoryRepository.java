package com.hillyuen.repository;

import com.hillyuen.model.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <pre>
 *     分类持久层
 * </pre>
 *
 * @author : Hill_Yuen
 * @date : 2017/11/30
 */
public interface CategoryRepository extends JpaRepository<Category, Long> {

    /**
     * 根据分类目录路径查询，用于验证是否已经存在该路径
     *
     * @param cateUrl cateUrl 文章url
     * @return Category
     */
    Category findCategoryByCateUrl(String cateUrl);

    /**
     * 通过分类名查找分类
     * @param cateName
     * @return
     */
    Category findCategoryByCateName(String cateName);
}
