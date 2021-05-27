package com.af.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author AF
 * @date 2021/5/21 16:18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRole {

    private Long userId;

    private Long roleId;
}
