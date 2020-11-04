package com.f4w.mapper;

import com.f4w.dto.WxmpDto;
import com.f4w.dto.req.CommonPageReq;
import com.f4w.entity.Wxmp;
import com.f4w.utils.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

public interface WxmpMapper extends BaseMapper<Wxmp> {

    @Select("select * from wxmp")
    @Results(
            id = "base",
            value = {
                    @Result(property = "type", column = "type"),
                    @Result(property = "title", column = "title"),
                    @Result(property = "thumbnail", column = "thumbnail"),
                    @Result(property = "summary", column = "summary"),
                    @Result(property = "videoId", column = "video_id"),
                    @Result(property = "auther", column = "auther"),
                    @Result(property = "columnId", column = "column_id"),
                    @Result(property = "ctime", column = "ctime"),
                    @Result(property = "status", column = "status"),
                    @Result(property = "mtime", column = "mtime"),
                    @Result(property = "delete", column = "delete"),
            }
    )
    Wxmp findAll();

    @Select("select * from wxmp")
    @Results(
            id = "baseDto",
            value = {
                    @Result(property = "type", column = "type"),
                    @Result(property = "title", column = "title"),
                    @Result(property = "thumbnail", column = "thumbnail"),
                    @Result(property = "summary", column = "summary"),
                    @Result(property = "videoId", column = "video_id"),
                    @Result(property = "auther", column = "auther"),
                    @Result(property = "columnId", column = "column_id"),
                    @Result(property = "ctime", column = "ctime"),
                    @Result(property = "status", column = "status"),
                    @Result(property = "mtime", column = "mtime"),
                    @Result(property = "delete", column = "delete"),
            }
    )
    WxmpDto findDtoAll();

    @Select("select column_id from wxmp group by column_id order by  column_id")
    List<String> findColumns();

    @Delete("update wxmp wx set del ='1'  WHERE wx.id IN (select t.id from (SELECT max(id) as id FROM wxmp where del =0  GROUP BY title HAVING count(*) > 1) t)")
    void deleteDuplicates();

    @Delete("update wxmp wx set del ='1'  WHERE wx.id = #{id}")
    void deleteById(Integer id);

    @Select({"<script>",
            "select * from wxmp where 1=1",
            "<when test='title!=null'>",
            "AND title like CONCAT('%',#{title},'%')",
            "</when>",
            "<when test='column!=null'>",
            "AND column_id = #{column}",
            "</when>",
            "<when test='del!=null'>",
            "AND del = #{del}",
            "</when>",
            "<when test='type!=null'>",
            "AND type = #{type}",
            "</when>",
            "<when test='status!=null'>",
            "AND status = #{status}",
            "</when>",
            "order by ctime desc",
            "</script>"
    })
    @ResultMap(value = "base")
    List<Wxmp> selectAllByPage(Map map);

    @Select("select * from wxmp where column_id = ${columnId} and type = ${type} and del=0 order by id desc limit 1")
    Wxmp findWxmpByType(@Param("type") Integer type, @Param("columnId") Integer columnId);

    @Select("select * from wxmp where column_id = ${columnId} and type = ${type} and del=0 and is_top = 1 order by id desc limit 1")
    Wxmp findWxmpTopByType(@Param("type") Integer type, @Param("columnId") Integer columnId);

    @Select({"<script>",
            "select * from wxmp where 1=1",
            "<when test='title!=null'>",
            "AND title like CONCAT('%',#{title},'%')",
            "</when>",
            "<when test='column!=null'>",
            "AND column_id = #{column}",
            "</when>",
            "<when test='del!=null'>",
            "AND del = #{del}",
            "</when>",
            "<when test='type!=null'>",
            "AND type = #{type}",
            "</when>",
            "<when test='status!=null'>",
            "AND status = #{status}",
            "</when>",
            "and to_days(ctime) = to_days(now())",
            "order by ctime desc",
            "</script>"
    })
    @ResultMap(value = "base")
    List<Wxmp> selectCurrentByPage(Map map);

    @Select("select * from wxmp where `delete` = 0 order by mtime desc ")
    List<Wxmp> getList(CommonPageReq req);
}
