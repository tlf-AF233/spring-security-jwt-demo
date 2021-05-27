package com.af.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author AF
 * @date 2021/5/25 22:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDept {

    private Long roleId;

    private Long deptId;
}
