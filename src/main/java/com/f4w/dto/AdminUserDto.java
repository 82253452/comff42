package com.f4w.dto;

import com.f4w.entity.SysRole;
import com.f4w.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * @author admin
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AdminUserDto extends SysUser {
    private List<SysRole> roles;
}
