package com.f4w.dto.resp;

import com.f4w.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Author: yp
 * @Date: 2020/9/22 19:13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResp {
    private Integer uid;
    private String userName;
    private String token;
    private List<String> roles;
    private SysUser userInfo;
}
