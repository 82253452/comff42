package com.f4w.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;
import javax.persistence.*;

/**
 * @author admin
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`sys_user_role`")
@EqualsAndHashCode(callSuper = true)
public class SysUserRole extends BaseEntity {

    private Integer roleId;
    private Integer userId;

}
