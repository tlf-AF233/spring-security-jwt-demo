package com.af.system.aspectj;

import com.af.security.util.JwtTokenUtils;
import com.af.system.annotation.DataScope;
import com.af.system.entity.BaseEntity;
import com.af.system.entity.Role;
import com.af.system.entity.User;
import com.af.system.service.UserService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 切面类
 * @author AF
 * @date 2021/5/19 20:32
 */
@Aspect
@Component
public class DataScopeAspect {

    /**
     * 本部门权限
     */
    private final int DATA_SCOPE_DEPT       =   1;

    /**
     * 本部门及以下权限
     */
    private final int DATA_SCOPE_DEPT_CHILD =   2;

    /**
     * 自定义权限
     */
    private final int DATA_SCOPE_CUSTOM     =   3;

    /**
     * 所有权限
     */
    private final int DATA_SCOPE_ALL        =   4;

    /**
     * 仅自己权限
     */
    private final int DATA_SCOPE_SELF       =   5;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private UserService userService;

    /**
     * 切点
     */
    @Pointcut("@annotation(com.af.system.annotation.DataScope)")
    public void DataScopePointCut() {
    }

    /**
     * 过滤权限
     * @param joinPoint
     */
    @Before("DataScopePointCut()")
    public void dataScopeFilter(JoinPoint joinPoint) {
        DataScope annotation = getAnnotation(joinPoint);
        if (annotation == null) {
            return;
        }
        // 如果有管理员权限则不需要过滤
        String token = jwtTokenUtils.getToken(request);
        if (jwtTokenUtils.getRoles(token).contains("admin")) {
            return;
        }
        // 获取当前登录的用户
        User currentUser = userService.selectUserByUsername(jwtTokenUtils.getUsername(token));
        dataScopeHandle(currentUser, joinPoint, annotation.deptAlias(), annotation.userAlias());
    }

    private void dataScopeHandle(User user, JoinPoint joinPoint, String deptAlias, String userAlias) {
        StringBuilder sql = new StringBuilder();
        // 遍历当前用户具有的角色
        for (Role role : user.getRoleList()) {

            // 如果其中有一个角色具有所有权限，则重置 sql 并中断循环
            if (DATA_SCOPE_ALL == role.getDataScope()) {
                sql = new StringBuilder();
                break;
            } else if (DATA_SCOPE_DEPT == role.getDataScope()) {
                // 如果具有该部门的权限，则添加 sql 语句 dept_id = user.getDeptId()
                sql.append(String.format(" OR %s.dept_id = %d", deptAlias, user.getDeptId()));
            } else if (DATA_SCOPE_DEPT_CHILD == role.getDataScope()) {
                // 如果具有该部门及其以下的权限
                sql.append(String.format(" OR %s.dept_id IN " +
                        "( select dept_id " +
                        "from sys_dept " +
                        "where dept_id = %d OR find_in_set(%d, dept_pids)) ", deptAlias, user.getDeptId(), user.getDeptId()));
            } else if (DATA_SCOPE_CUSTOM == role.getDataScope()) {
                // 如果权限为自定义
                sql.append(String.format(" OR %s.dept_id IN " +
                        "( select dept_id " +
                        "from sys_role_dept " +
                        "where role_id = %d)", deptAlias, role.getRoleId()));
            } else if (DATA_SCOPE_SELF == role.getDataScope()) {
                // 如果只有自己的权限，则无法查询部门信息，只能查询个人信息
                if ("".equals(userAlias)) {
                    // 该参数为空，说明执行的业务为查询部门，所以不需要处理
                    sql.append(" OR 0 = 1");
                } else {
                    sql.append(String.format(" OR %s.user_id = %d", userAlias, user.getUserId()));
                }
            }
        }

        // sql 语句拼接完毕后，存入 params 中
        if (!"".equals(sql.toString())) {
            Object entity = joinPoint.getArgs()[0];
            if (entity instanceof BaseEntity) {
                ((BaseEntity) entity).getParams().put("dataScope", "AND (" + sql.substring(4) + ")");
            }
        }
    }

    /**
     * 获取方法上的注解
     * @param joinPoint
     * @return
     */
    private DataScope getAnnotation(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        if (signature.getMethod() != null) {
            return signature.getMethod().getAnnotation(DataScope.class);
        }
        return null;
    }
}
