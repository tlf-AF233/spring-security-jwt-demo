package com.af.system.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author AF
 * @date 2021/5/12 23:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    private Long menuId;

    @NotBlank(message = "名称不能为空")
    private String menuName;

    private Long menuPid;

    private Integer orderNum;

    private String menuUrl;

    private String menuCode;

    @NotNull(message = "类型必须选择")
    private Integer menuType;

    private Integer menuStatus;

    private String menuIcon;

    private List<Menu> children;

}
