package com.f4w.mapper;

import com.f4w.dto.req.CommonPageReq;
import com.f4w.entity.Banner;
import com.f4w.entity.BusiApp;
import com.f4w.dto.BusiAppDto;
import com.f4w.utils.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BusiAppMapper extends BaseMapper<BusiApp> {

    @Select("select * from busi_app")
    @Results(
            id = "base",
            value = {
                    @Result(property = "id", column = "id"),
                    @Result(property = "pageId", column = "page_id"),
                    @Result(property = "name", column = "name"),
                    @Result(property = "appId", column = "app_id"),
                    @Result(property = "appSecret", column = "app_secret"),
                    @Result(property = "ctime", column = "ctime"),
                    @Result(property = "mtime", column = "mtime"),
                    @Result(property = "delete", column = "delete"),
                    @Result(property = "status", column = "status"),
            }
    )
    BusiApp findAll();
    @Select("select * from busi_app")
    @Results(
            id = "baseDto",
            value = {
                    @Result(property = "id", column = "id"),
                    @Result(property = "pageId", column = "page_id"),
                    @Result(property = "name", column = "name"),
                    @Result(property = "appId", column = "app_id"),
                    @Result(property = "appSecret", column = "app_secret"),
                    @Result(property = "ctime", column = "ctime"),
                    @Result(property = "mtime", column = "mtime"),
                    @Result(property = "delete", column = "delete"),
                    @Result(property = "status", column = "status"),
            }
    )
    BusiAppDto findDtoAll();

    @Select("<script>\n" +
            "select * from busi_app where `delete` = 0 \n" +
            "<if test='type!=null and type != &quot;&quot; '>\n" +
            "    and mini_program_info = #{type}\n" +
            "</if>\n" +
            "order by mtime desc \n" +
            "</script>")
    List<BusiApp> getList(CommonPageReq req);
}
