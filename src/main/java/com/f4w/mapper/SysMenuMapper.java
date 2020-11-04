package com.f4w.mapper;

import com.f4w.entity.SysMenu;
import com.f4w.dto.SysMenuDto;
import com.f4w.utils.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SysMenuMapper extends BaseMapper<SysMenu> {

    @Select("select * from sys_menu")
    @Results(
            id = "base",
            value = {
                    @Result(property = "parentId", column = "parent_id"),
                    @Result(property = "name", column = "name"),
                    @Result(property = "url", column = "url"),
                    @Result(property = "perms", column = "perms"),
                    @Result(property = "type", column = "type"),
                    @Result(property = "icon", column = "icon"),
                    @Result(property = "orderNum", column = "order_num"),
                    @Result(property = "ctime", column = "ctime"),
                    @Result(property = "mtime", column = "mtime"),
                    @Result(property = "delete", column = "delete"),
                    @Result(property = "id", column = "id"),
            }
    )
    SysMenu findAll();

    @Select("select * from sys_menu")
    @Results(
            id = "baseDto",
            value = {
                    @Result(property = "parentId", column = "parent_id"),
                    @Result(property = "name", column = "name"),
                    @Result(property = "url", column = "url"),
                    @Result(property = "perms", column = "perms"),
                    @Result(property = "type", column = "type"),
                    @Result(property = "icon", column = "icon"),
                    @Result(property = "orderNum", column = "order_num"),
                    @Result(property = "ctime", column = "ctime"),
                    @Result(property = "mtime", column = "mtime"),
                    @Result(property = "delete", column = "delete"),
                    @Result(property = "id", column = "id"),
            }
    )
    SysMenuDto findDtoAll();

    @Select("select *\n" +
            "from sys_menu me\n" +
            "left join sys_role_menu ro on me.id = ro.role_id\n" +
            "where ro.role_id in(select sr.role_id from sys_user u\n" +
            "left join sys_user_role sr on u.id = sr.user_id where u.id = #{userId})")
    @ResultMap("base")
    List<SysMenuDto> getMenuByUserId(Integer userId);
}
