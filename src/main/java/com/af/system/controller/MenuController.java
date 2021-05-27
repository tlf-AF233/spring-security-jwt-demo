package com.af.system.controller;

import com.af.security.enums.ErrorCode;
import com.af.security.modal.ResponseVO;
import com.af.security.util.JwtTokenUtils;
import com.af.security.util.ResponseUtils;
import com.af.system.entity.Menu;
import com.af.system.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author AF
 * @date 2021/5/25 14:53
 */
@Api(tags = "菜单接口")
@RestController
@RequestMapping("/sys/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    /**
     * 查询该用户可见的菜单
     * @param menu
     * @return
     */
    @ApiOperation("查询该用户可见的菜单")
    @PreAuthorize("@ps.hasPerm('sys:menu:query')")
    @GetMapping("/list")
    public ResponseVO<Object> selectMenuList(Menu menu) {
        long userId = jwtTokenUtils.getId(jwtTokenUtils.getToken(request));
        return ResponseUtils.success(menuService.selectMenuListByUserId(menu, userId));
    }

    /**
     * 更新菜单
     * @param menu
     * @return
     */
    @ApiOperation("更新菜单")
    @PreAuthorize("@ps.hasPerm('sys:menu:edit')")
    @PutMapping
    public ResponseVO<Object> updateMenu(@RequestBody Menu menu) {
        if (menuService.checkMenuName(menu.getMenuName())) {
            return ResponseUtils.error(ErrorCode.MENU_EDIT_FAILED, "菜单名称：" + menu.getMenuName() + " 已经存在！");
        }
        return ResponseUtils.success(menuService.updateMenu(menu));
    }

    /**
     * 删除菜单
     * @param menuId
     * @return
     */
    @ApiOperation("删除菜单")
    @PreAuthorize("@ps.hasPerm('sys:menu:remove')")
    @DeleteMapping("/{menuId}")
    public ResponseVO<Object> deleteMenu(@PathVariable("menuId") long menuId) {
        if (menuService.checkChildren(menuId)) {
            return ResponseUtils.error(ErrorCode.MENU_EDIT_FAILED, "该菜单还具有子菜单，无法删除");
        }
        return ResponseUtils.success(menuService.deleteMenu(menuId));
    }

    /**
     * 查看菜单详情
     * @param menuId
     * @return
     */
    @ApiOperation("查看菜单详情")
    @PreAuthorize("@ps.hasPerm('sys:menu:query')")
    @GetMapping("/{menuId}")
    public ResponseVO<Object> selectMenuById(@PathVariable("menuId") long menuId) {
        return ResponseUtils.success(menuService.selectMenuById(menuId));
    }

}
