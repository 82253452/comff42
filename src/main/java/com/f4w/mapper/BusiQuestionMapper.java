package com.f4w.mapper;

import com.f4w.entity.BusiQuestion;
import com.f4w.dto.BusiQuestionDto;
import com.f4w.utils.BaseMapper;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface BusiQuestionMapper extends BaseMapper<BusiQuestion> {

    @Select("select * from busi_question")
    @Results(
            id = "base",
            value = {
                    @Result(property = "id", column = "id"),
                    @Result(property = "title", column = "title"),
                    @Result(property = "questions", column = "questions"),
                    @Result(property = "ctime", column = "ctime"),
                    @Result(property = "mtime", column = "mtime"),
                    @Result(property = "delete", column = "delete"),
                    @Result(property = "status", column = "status"),
                    @Result(property = "right", column = "right"),
                    @Result(property = "appId", column = "app_id"),
                    @Result(property = "uid", column = "uid"),
            }
    )
    BusiQuestion findAll();

    @Select("select * from busi_question")
    @Results(
            id = "baseDto",
            value = {
                    @Result(property = "id", column = "id"),
                    @Result(property = "title", column = "title"),
                    @Result(property = "questions", column = "questions"),
                    @Result(property = "ctime", column = "ctime"),
                    @Result(property = "mtime", column = "mtime"),
                    @Result(property = "delete", column = "delete"),
                    @Result(property = "status", column = "status"),
                    @Result(property = "right", column = "right"),
                    @Result(property = "appId", column = "app_id"),
                    @Result(property = "uid", column = "uid"),
            }
    )
    BusiQuestionDto findDtoAll();

    @Select("<script>" +
            "select  * from busi_question where title like '%' #{title} '%' " +
            "<if test='appId!=null'>" +
            "AND app_id=#{appId}" +
            "</if>" +
            "</script>")
    @ResultMap("baseDto")
    List<BusiQuestionDto> getOneListQuestion(@Param("title") String title, @Param("uid") Integer uid, @Param("appId") String appId);

}
