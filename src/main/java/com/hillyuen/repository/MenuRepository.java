package com.hillyuen.repository;

import com.hillyuen.model.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <pre>
 *     菜单持久层
 * </pre>
 *
 * @author : Hill_Yuen
 * @date : 2018/1/24
 */
public interface MenuRepository extends JpaRepository<Menu, Long> {
}
