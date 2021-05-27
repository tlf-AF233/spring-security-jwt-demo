package com.af.system.service.impl;

import com.af.system.annotation.DataScope;
import com.af.system.entity.Dept;
import com.af.system.mapper.DeptMapper;
import com.af.system.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author AF
 * @date 2021/5/19 20:25
 */
@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    /**
     * 查询部门信息
     * @param dept
     * @return
     */
    @DataScope(deptAlias = "d")
    @Override
    public List<Dept> selectDeptList(Dept dept) {
        return buildTree(deptMapper.selectDeptList(dept));
    }

    /**
     * 修改部门信息
     * @param dept
     * @return
     */
    @Override
    public int updateDept(Dept dept) {
        return deptMapper.updateDept(dept);
    }

    /**
     * 删除部门
     * @param deptId
     * @return
     */
    @Override
    public int removeDept(long deptId) {
        return deptMapper.removeDept(deptId);
    }

    /**
     * 创建部门
     * @param dept
     * @return
     */
    @Override
    public int createDept(Dept dept) {
        return deptMapper.createDept(dept);
    }

    /**
     * 检查是否存在子部门
     * @param deptId
     * @return
     */
    @Override
    public boolean checkChildren(long deptId) {
        return deptMapper.checkChildren(deptId) == 1;
    }

    /**
     * 检查部门是否还有用户
     * @param deptId
     * @return
     */
    @Override
    public boolean checkUserExist(long deptId) {
        return deptMapper.checkUserExist(deptId) == 1;
    }

    /**
     * 构建树型结构
     * @param deptList
     * @return
     */
    public List<Dept> buildTree(List<Dept> deptList) {
        // 存储所有部门的编号
        List<Long> deptIds = deptList.stream().map(Dept::getDeptId).collect(Collectors.toList());
        List<Dept> result = new ArrayList<>();
        for (Dept dept : deptList) {
            // 找到顶级结点，遍历之
            if (!deptIds.contains(dept.getDeptPid())) {
                recurDeptList(deptList, dept);
                result.add(dept);
            }
        }

        return result.isEmpty() ? deptList : result;
    }

    /**
     * 递归对部门集合进行处理
     * 将子节点挂到该结点的父节点上
     * @param deptList
     * @param dept
     */
    private void recurDeptList(List<Dept> deptList, Dept dept) {
        List<Dept> children = getChildren(deptList, dept);
        dept.setChildren(children);
        for (Dept d : children) {
            if (hasChildren(deptList, d)) {
                recurDeptList(deptList, d);
            }
        }
    }

    /**
     * 判断是否具有子节点
     * @param deptList
     * @param dept
     * @return
     */
    private boolean hasChildren(List<Dept> deptList, Dept dept) {
        return getChildren(deptList, dept).size() != 0;
    }

    /**
     * 获取子部门集合
     * @param deptList 所有集合
     * @param dept  父节点
     * @return
     */
    private List<Dept> getChildren(List<Dept> deptList, Dept dept) {
        List<Dept> result = new ArrayList<>();
        for (Dept d : deptList) {
            if (d.getDeptPid().equals(dept.getDeptId())) {
                result.add(d);
            }
        }
        return result;
    }

}
