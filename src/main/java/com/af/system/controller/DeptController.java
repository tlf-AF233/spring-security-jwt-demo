package com.af.system.controller;

import com.af.security.enums.ErrorCode;
import com.af.security.modal.ResponseVO;
import com.af.security.util.ResponseUtils;
import com.af.system.entity.Dept;
import com.af.system.service.DeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author AF
 * @date 2021/5/19 22:00
 */
@Api(tags = "部门接口")
@RestController
@RequestMapping("/sys/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    /**
     * 查询部门
     * @param dept
     * @return
     */
    @ApiOperation("查询部门")
    @PreAuthorize("@ps.hasPerm('sys:dept:query')")
    @GetMapping("/list")
    public ResponseVO<Object> selectDeptList(Dept dept) {
        return ResponseUtils.success(deptService.selectDeptList(dept));
    }

    /**
     * 修改部门
     * @param dept
     * @return
     */
    @ApiOperation("修改部门")
    @PreAuthorize("@ps.hasPerm('sys:dept:edit')")
    @PutMapping
    public ResponseVO<Object> updateDept(@RequestBody Dept dept) {
        return ResponseUtils.success(deptService.updateDept(dept));
    }

    /**
     * 删除部门
     * @param deptId
     * @return
     */
    @ApiOperation("删除部门")
    @PreAuthorize("@ps.hasPerm('sys:dept:remove')")
    @DeleteMapping("/{deptId}")
    public ResponseVO<Object> removeDept(@PathVariable("deptId") long deptId) {
        if (deptService.checkChildren(deptId)) {
            return ResponseUtils.error(ErrorCode.DEPT_DELETED_FAILED, "还存在子级部门！");
        }
        if (deptService.checkUserExist(deptId)) {
            return ResponseUtils.error(ErrorCode.DEPT_DELETED_FAILED, "还存在用户！");
        }
        return ResponseUtils.success(deptService.removeDept(deptId));
    }

    /**
     * 创建部门
     * @param dept
     * @return
     */
    @ApiOperation("创建部门")
    @PreAuthorize("@ps.hasPerm('sys:dept:add')")
    @PostMapping
    public ResponseVO<Object> createDept(@Validated @RequestBody Dept dept) {
        return ResponseUtils.success(deptService.createDept(dept));
    }
}
