package com.f4w.aop;

import com.f4w.dto.SysRoleDto;
import com.f4w.dto.annotation.InjectCompanyId;
import com.f4w.dto.annotation.InjectTransId;
import com.f4w.dto.annotation.InjectUserId;
import com.f4w.dto.enums.RoleEnum;
import com.f4w.entity.Company;
import com.f4w.entity.SysUser;
import com.f4w.entity.TransCompany;
import com.f4w.mapper.*;
import com.f4w.utils.JWTUtils;
import com.f4w.utils.ShowException;
import com.f4w.utils.SystemErrorEnum;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.f4w.utils.Constant.TOKEN;


/**
 * @author admin
 */
@Aspect
@Component
public class PermisionRoleAspect {

    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private TransCompanyMapper transCompanyMapper;
    @Resource
    private CompanyMapper companyMapper;
    @Resource
    private JWTUtils jwtUtils;
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private TransCompanyUserMapper transCompanyUserMapper;


    @Pointcut("@annotation(com.f4w.dto.annotation.PermisionRole)")
    private void permisionRoleIntercept() {
    }

    @Before(value = "permisionRoleIntercept()")
    public void methodAround(JoinPoint joinPoint) throws ShowException, IllegalAccessException {
        SysUser sysUser = getCurrentUser();
        Object[] args = joinPoint.getArgs();
        List<SysRoleDto> roleDtos = sysRoleMapper.getRolesByUserId(sysUser.getId());
        if (roleDtos.stream().anyMatch(r -> r.getRoleName().equals(RoleEnum.ADMIN.getCode()))) {
            return;
        }
        handleTransAnno(roleDtos, sysUser, args);
        handlePersonAnno(roleDtos, sysUser, args);
    }

    private void handleTransAnno(List<SysRoleDto> roleDtos, SysUser sysUser, Object[] args) throws ShowException, IllegalAccessException {
        if (roleDtos.stream().anyMatch(r -> r.getRoleName().equals(RoleEnum.TRANS.getCode()))) {
            TransCompany transCompany = transCompanyMapper.selectOne(TransCompany.builder().userId(sysUser.getId()).build());
            if (transCompany == null) {
                throw new ShowException("无权限");
            }
            for (Object arg : args) {
                for (Field declaredField : arg.getClass().getDeclaredFields()) {
                    if (Arrays.stream(declaredField.getAnnotations()).anyMatch(e -> e.annotationType().equals(InjectTransId.class))) {
                        declaredField.setAccessible(true);
                        declaredField.set(arg, transCompany.getId());
                        declaredField.setAccessible(false);
                    }
                }
            }
        }
    }

    private void handlePersonAnno(List<SysRoleDto> roleDtos, SysUser sysUser, Object[] args) throws ShowException, IllegalAccessException {
        if (roleDtos.stream().anyMatch(r -> r.getRoleName().equals(RoleEnum.PERSON.getCode()) || r.getRoleName().equals(RoleEnum.COMPANY.getCode()))) {
            for (Object arg : args) {
                for (Field declaredField : arg.getClass().getDeclaredFields()) {
                    if (Arrays.stream(declaredField.getAnnotations()).anyMatch(e -> e.annotationType().equals(InjectUserId.class))) {
                        declaredField.setAccessible(true);
                        declaredField.set(arg, sysUser.getId());
                        declaredField.setAccessible(false);
                    }
                }
            }
        }
    }

    public SysUser getCurrentUser() throws ShowException {
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader(TOKEN);
        if (StringUtils.isBlank(token)) {
            throw new ShowException(SystemErrorEnum.AUTH_TOKEN);
        }
        Object uid = jwtUtils.parseBody(token).get("uid");
        return Optional.ofNullable(sysUserMapper.selectByPrimaryKey(uid)).orElseThrow(() -> new ShowException(SystemErrorEnum.AUTH_TOKEN));
    }
}

