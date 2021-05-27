package com.af.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * @author AF
 * @date 2021/5/12 23:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Dept extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long deptId;

    private Long deptPid;

    private String deptPids;

    @NotBlank(message = "请填写部门名称")
    private String deptName;

    @NotBlank(message = "请填写部门领导名")
    private String deptLeader;

    @NotBlank(message = "请填写部门简介")
    private String deptDesc;

    private Integer orderNum;

    private Integer deptStatus;

    private List<Dept> children;
}
