package com.f4w.mapper;

import com.f4w.entity.SysRole;
import com.f4w.dto.SysRoleDto;
import com.f4w.utils.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SysRoleMapper extends BaseMapper<SysRole> {

    @Select("select * from sys_role")
    @Results(
            id = "base",
            value = {
                    @Result(property = "roleName", column = "role_name"),
                    @Result(property = "remark", column = "remark"),
                    @Result(property = "createUserId", column = "create_user_id"),
                    @Result(property = "createTime", column = "create_time"),
                    @Result(property = "ctime", column = "ctime"),
                    @Result(property = "mtime", column = "mtime"),
                    @Result(property = "delete", column = "delete"),
                    @Result(property = "id", column = "id"),
            }
    )
    SysRole findAll();
    @Select("select * from sys_role")
    @Results(
            id = "baseDto",
            value = {
                    @Result(property = "roleName", column = "role_name"),
                    @Result(property = "remark", column = "remark"),
                    @Result(property = "createUserId", column = "create_user_id"),
                    @Result(property = "createTime", column = "create_time"),
                    @Result(property = "ctime", column = "ctime"),
                    @Result(property = "mtime", column = "mtime"),
                    @Result(property = "delete", column = "delete"),
                    @Result(property = "id", column = "id"),
            }
    )
    SysRoleDto findDtoAll();

    @Select("select *\n" +
            "from sys_role r left join sys_user_role sr on r.id=sr.role_id " +
            "where sr.user_id=#{userId}")
    @ResultMap("baseDto")
    List<SysRoleDto> getRolesByUserId(Integer userId);

}
