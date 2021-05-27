package com.af.system.service;

import com.af.system.entity.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author AF
 * @date 2021/5/18 20:31
 */
public interface MenuService {

    /**
     * 根据用户 id 查找所属的权限字符串
     * @param userId
     * @return
     */
    List<String> selectPermByUserId(long userId);

    /**
     * 查询所有权限
     * @param menu
     * @return
     */
    List<Menu> selectMenuList(Menu menu);

    /**
     * 根据当前角色 id 查询菜单
     * @param menu
     * @param userId
     * @return
     */
    List<Menu> selectMenuListByUserId(Menu menu, long userId);

    /**
     * 更新菜单
     * @param menu
     * @return
     */
    int updateMenu(Menu menu);

    /**
     * 删除菜单
     * @param menuId
     * @return
     */
    int deleteMenu(long menuId);

    /**
     * 查看菜单详情
     * @param menuId
     * @return
     */
    Menu selectMenuById(long menuId);

    /**
     * 判断是否有子菜单
     * @param menuId
     * @return
     */
    boolean checkChildren(long menuId);

    /**
     * 获取路由菜单
     * @param userId
     * @return
     */
    List<Menu> selectRouterMenuByUserId(@Param("userId") long userId);

    /**
     * 检查菜单名称是否重复
     * @param menuName
     * @return
     */
    boolean checkMenuName(String menuName);
}
