package com.af.system.mapper;

import com.af.system.entity.Dept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author AF
 * @date 2021/5/19 17:34
 */
@Mapper
@Repository
public interface DeptMapper {

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
    int removeDept(@Param("deptId") long deptId);

    /**
     * 创建部门
     * @param dept
     * @return
     */
    int createDept(Dept dept);

    /**
     * 检查是否存在下级部门
     * @param deptId
     * @return
     */
    int checkChildren(@Param("deptId") long deptId);

    /**
     * 检查部门是否还存在用户
     * @param deptId
     * @return
     */
    int checkUserExist(@Param("deptId") long deptId);
}
