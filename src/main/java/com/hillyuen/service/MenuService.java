package com.hillyuen.service;

import com.hillyuen.model.domain.Menu;

import java.util.List;
import java.util.Optional;

/**
 * @author : Hill_Yuen
 * @date : 2018/1/24
 */
public interface MenuService {

    /**
     * 新增/修改菜单
     *
     * @param menu menu
     * @return Menu
     */
    Menu saveByMenu(Menu menu);

    /**
     * 查询所有菜单
     *
     * @return List
     */
    List<Menu> findAllMenus();

    /**
     * 删除菜单
     *
     * @param menuId menuId
     * @return Menu
     */
    Menu removeByMenuId(Long menuId);

    /**
     * 根据编号查询菜单
     *
     * @param menuId menuId
     * @return Optional
     */
    Optional<Menu> findByMenuId(Long menuId);
}
