package com.af.system.service;

import com.af.system.entity.Dept;

import java.util.List;

/**
 * @author AF
 * @date 2021/5/19 20:25
 */
public interface DeptService {

    /**
     * 查询部门信息
     * @param dept
     * @return
     */
    List<Dept> selectDeptList(Dept dept);

    /**
     * 修改部门
     * @param dept
     * @return
     */
    int updateDept(Dept dept);

    /**
     * 删除部门
     * @param deptId
     * @return
     */
    int removeDept(long deptId);

    /**
     * 创建部门
     * @param dept
     * @return
     */
    int createDept(Dept dept);

    /**
     * 检查部门是否存在子部门
     * @param deptId
     * @return
     */
    boolean checkChildren(long deptId);

    /**
     * 检查部门是否还有用户
     * @param deptId
     * @return
     */
    boolean checkUserExist(long deptId);
}
