package com.f4w.carAdminController;

import com.f4w.dto.AdminUserDto;
import com.f4w.dto.annotation.PermisionRole;
import com.f4w.dto.enums.RoleEnum;
import com.f4w.dto.req.CommonPageReq;
import com.f4w.entity.SysRole;
import com.f4w.entity.SysUser;
import com.f4w.entity.SysUserRole;
import com.f4w.mapper.SysRoleMapper;
import com.f4w.mapper.SysUserMapper;
import com.f4w.mapper.SysUserRoleMapper;
import com.f4w.utils.ShowException;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

import static com.f4w.utils.SystemErrorEnum.USER_DEL_ERROR;

/**
 * @Author: yp
 * @Date: 2020/9/8 10:57
 */
@RestController
@RequestMapping("/adminUser")
public class AdminUserController {
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @GetMapping("/list")
    @PermisionRole
    public PageInfo<AdminUserDto> list(CommonPageReq req) {
        List<AdminUserDto> list = sysUserMapper.getAdminList(req);
        list.forEach(e -> e.setRoles(sysUserRoleMapper.queryUseRole(e.getId())));
        PageInfo<AdminUserDto> page = PageInfo.of(list);
        return page;
    }

    @PostMapping
    public int add(@RequestBody SysUser sysUser) {
        int i = sysUserMapper.insertSelective(sysUser);
        return i;
    }

    @PutMapping
    @Transactional(rollbackFor = Exception.class)
    public int update(@RequestBody AdminUserDto sysUser) {
        int i = sysUserMapper.updateByPrimaryKeySelective(sysUser);
        if (CollectionUtils.isNotEmpty(sysUser.getRoles())) {
            sysUserRoleMapper.delete(SysUserRole.builder().userId(sysUser.getId()).build());
            sysUser.getRoles().forEach(r -> sysUserRoleMapper.insertSelective(SysUserRole.builder().userId(sysUser.getId()).roleId(r.getId()).build()));
        }
        return i;
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable Integer id) throws ShowException {
        if (id == 1) {
            throw new ShowException(USER_DEL_ERROR);
        }
        int i = sysUserMapper.deleteByPrimaryKey(id);
        return i;
    }

    @GetMapping("/roles")
    public List<SysRole> roles() {
        List<SysRole> sysRoles = sysRoleMapper.selectAll();
        return sysRoles;
    }

    @GetMapping("/changeRole")
    public void changeRole(Integer id, Integer[] roles) {
        sysUserRoleMapper.delete(SysUserRole.builder().userId(id).build());
        Arrays.stream(roles).forEach(r -> sysUserRoleMapper.insertSelective(SysUserRole.builder().userId(id).roleId(r).build()));
    }

}
