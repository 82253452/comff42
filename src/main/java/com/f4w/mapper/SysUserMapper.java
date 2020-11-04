package com.f4w.mapper;

import com.f4w.dto.AdminUserDto;
import com.f4w.dto.req.CommonPageReq;
import com.f4w.utils.BaseMapper;
import com.f4w.dto.BusiAppDto;
import com.f4w.entity.BusiApp;
import com.f4w.entity.SysUser;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("select * from busi_app")
    @Results(
            id = "base",
            value = {
                    @Result(property = "id", column = "id"),
                    @Result(property = "appId", column = "app_id")
            }
    )
    BusiApp findAll();

    @Select("select * from busi_app")
    @Results(
            id = "baseDto",
            value = {
                    @Result(property = "id", column = "id"),
                    @Result(property = "appId", column = "app_id")
            }
    )
    BusiAppDto findDtoAll();

    @Select("select * from sys_user where `delete` = 0 order by mtime desc ")
    List<SysUser> getList(CommonPageReq req);

    @Select("<script>\n" +
            "select su.* from sys_user su\n" +
            "    <if test='transId!=null and transId != &quot;&quot; '>\n" +
            "        right join (select * from trans_company_user where trans_id = #{transId}) tcu on su.id = tcu.user_id\n" +
            "    </if>\n" +
            " where 1=1 \n" +
            "    <if test='name!=null and name != &quot;&quot; '>\n" +
            "        AND nickname like CONCAT('%',#{name},'%')\n" +
            "    </if>\n" +
            "    and su.delete = 0\n" +
            " order by su.mtime desc \n" +
            "</script>")
    List<AdminUserDto> getAdminList(CommonPageReq req);
}
