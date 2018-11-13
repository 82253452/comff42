package com.f4w.mapper;

import com.f4w.entity.SysUserRole;
import com.f4w.dto.SysUserRoleDto;
import com.f4w.utils.BaseMapper;
import org.apache.ibatis.annotations.*;

public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    @Select("select * from sys_user_role")
    @Results(
            id = "base",
            value = {
                    @Result(property = "id", column = "id"),
                    @Result(property = "roleId", column = "role_id"),
                    @Result(property = "userId", column = "user_id"),
                    @Result(property = "ctime", column = "ctime"),
                    @Result(property = "mtime", column = "mtime"),
                    @Result(property = "delete", column = "delete"),
            }
    )
    SysUserRole findAll();
    @Select("select * from sys_user_role")
    @Results(
            id = "baseDto",
            value = {
                    @Result(property = "id", column = "id"),
                    @Result(property = "roleId", column = "role_id"),
                    @Result(property = "userId", column = "user_id"),
                    @Result(property = "ctime", column = "ctime"),
                    @Result(property = "mtime", column = "mtime"),
                    @Result(property = "delete", column = "delete"),
            }
    )
    SysUserRoleDto findDtoAll();

}
