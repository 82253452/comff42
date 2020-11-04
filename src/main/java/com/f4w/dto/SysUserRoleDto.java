package com.f4w.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import com.f4w.entity.SysUserRole;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * createBy:2018-11-06 20:32:50
 *
 * @author yp
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
public class SysUserRoleDto extends SysUserRole {

}
