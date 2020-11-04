package com.f4w.controller;

import com.f4w.annotation.NotTokenIntecerpt;
import com.f4w.dto.req.LoginReq;
import com.f4w.dto.resp.UserResp;
import com.f4w.entity.SysUser;
import com.f4w.mapper.SysMenuMapper;
import com.f4w.mapper.SysRoleMapper;
import com.f4w.mapper.SysUserMapper;
import com.f4w.utils.JWTUtils;
import com.f4w.utils.R;
import com.f4w.utils.ShowException;
import com.f4w.utils.SystemErrorEnum;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("userLogin")
@NotTokenIntecerpt
public class LoginController {
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private JWTUtils jwtUtils;

    @PostMapping("login")
    public R login(@RequestBody Map<String, String> data) {
        if (R.validate(data, "username", "password").isOk()) {
            String username = data.get("username");
            String password = data.get("password");
            SysUser sysUser = new SysUser();
            sysUser.setUserName(username);
            sysUser = sysUserMapper.selectOne(sysUser);
            if (null == sysUser) {
                return R.error(1001, "账号不存在");
            }
            password = DigestUtils.md5Hex(password);
            System.out.println(StringUtils.equals(password, sysUser.getPassword()));
            if (StringUtils.equals(password, sysUser.getPassword())) {
                Map key = new HashMap(2);
                key.put("uid", sysUser.getId());
                key.put("username", sysUser.getUserName());
                String token = jwtUtils.creatKey(key);
                Map render = new HashMap();
                render.put("token", token);
                return R.ok().put("data", render);
            }
        }
        return R.error(1002, "账号或密码错误");
    }

    @PostMapping("/userLogin")
    public UserResp login(@Validated @RequestBody LoginReq req) throws ShowException {
        SysUser sysUser = Optional.ofNullable(sysUserMapper.selectOne(SysUser.builder().userName(req.getUsername()).build())).orElseThrow(() -> new ShowException(SystemErrorEnum.USER_ERROR));
        if (!StringUtils.equals(DigestUtils.md5Hex(req.getPassword()), sysUser.getPassword())) {
            throw new ShowException(SystemErrorEnum.USER_ERROR);
        }
        return UserResp.builder().uid(sysUser.getId().intValue()).userName(sysUser.getUserName()).token(jwtUtils.creatKey(sysUser)).build();
    }
}
