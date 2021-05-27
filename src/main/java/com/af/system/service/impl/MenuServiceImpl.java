package com.af.system.service.impl;

import com.af.system.entity.Menu;
import com.af.system.mapper.MenuMapper;
import com.af.system.mapper.UserRoleMapper;
import com.af.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author AF
 * @date 2021/5/18 20:33
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    /**
     * 根据用户 id 查找所属的权限字符串
     * @param userId
     * @return
     */
    @Override
    public List<String> selectPermByUserId(long userId) {
        return menuMapper.selectPermByUserId(userId);
    }

    /**
     * 查询所有权限
     * @param menu
     * @return
     */
    @Override
    public List<Menu> selectMenuList(Menu menu) {
        return buildTree(menuMapper.selectMenuList(menu));
    }

    /**
     * 根据当前角色 id 查询菜单
     * @param menu
     * @param userId
     * @return
     */
    @Override
    public List<Menu> selectMenuListByUserId(Menu menu, long userId) {
        // 管理员
        if (userRoleMapper.selectUserRoleByUserId(userId).contains(1L)) {
            return selectMenuList(menu);
        }
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        menu.setParams(params);
        return buildTree(menuMapper.selectMenuListByUserId(menu));
    }

    /**
     * 删除菜单
     * @param menuId
     * @return
     */
    @Override
    public int deleteMenu(long menuId) {
        return menuMapper.deleteMenu(menuId);
    }

    /**
     * 查看菜单详情
     * @param menuId
     * @return
     */
    @Override
    public Menu selectMenuById(long menuId) {
        return menuMapper.selectMenuById(menuId);
    }

    /**
     * 判断是否有子菜单
     * @param menuId
     * @return
     */
    @Override
    public boolean checkChildren(long menuId) {
        return menuMapper.checkChildren(menuId) == 1;
    }

    /**
     * 更新菜单
     * @param menu
     * @return
     */
    @Override
    public int updateMenu(Menu menu) {
        return menuMapper.updateMenu(menu);
    }

    /**
     * 获取路由菜单
     * @param userId
     * @return
     */
    @Override
    public List<Menu> selectRouterMenuByUserId(long userId) {
        return buildTree(menuMapper.selectRouterMenuByUserId(userId));
    }

    /**
     * 检查菜单名称是否重复
     * @param menuName
     * @return
     */
    @Override
    public boolean checkMenuName(String menuName) {
        return menuMapper.checkMenuName(menuName) == 1;
    }

    /**
     * 构建树形结构
     * @param menus
     * @return
     */
    public List<Menu> buildTree(List<Menu> menus) {
        List<Long> menuIds =
                    menus.stream()
                        .map(Menu::getMenuId)
                        .collect(Collectors.toList());
        List<Menu> result = new ArrayList<>();
        for (Menu menu : menus) {
            if (!menuIds.contains(menu.getMenuPid())) {
                recurList(menus, menu);
                result.add(menu);
            }
        }
        return result.isEmpty() ? menus : result;
    }

    /**
     * 递归处理
     * @param menus
     * @param menu
     */
    private void recurList(List<Menu> menus, Menu menu) {
        List<Menu> children = getChildren(menus, menu);
        menu.setChildren(children);
        for (Menu m : children) {
            if (hasChildren(menus, menu)) {
                recurList(menus, m);
            }
        }
    }

    /**
     * 获取孩子结点
     * @param menus
     * @param menu
     * @return
     */
    private List<Menu> getChildren(List<Menu> menus, Menu menu) {
        List<Menu> children = new ArrayList<>();
        for (Menu m : menus) {
            if (m.getMenuPid().equals(menu.getMenuId())) {
                children.add(m);
            }
        }
        return children;
    }

    private boolean hasChildren(List<Menu> menus, Menu menu) {
        return getChildren(menus, menu).size() > 0;
    }
}
