package com.f4w.mapper;

import com.f4w.dto.req.CommonPageReq;
import com.f4w.dto.req.TalentPoolReq;
import com.f4w.entity.TalentPool;
import com.f4w.utils.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TalentPoolMapper extends BaseMapper<TalentPool> {

    @Select({"<script>",
            "select * from talent_pool where 1=1",
            "<when test='edu!=null'>",
            "AND education = #{edu}",
            "</when>",
            "<when test='phone!=null'>",
            "AND phone = #{phone}",
            "</when>",
            "<when test='sc!=null'>",
            "AND sc = #{sc}",
            "</when>",
            "<when test='major!=null'>",
            "AND major = #{major}",
            "</when>",
            "and `delete` =0",
            " order by update_time desc",
            "</script>"
    })
    List<TalentPool> selectAllByPage(TalentPoolReq req);
    @Select("select distinct sc from talent_pool where sc is not null and sc !=''")
    List<String> selectScs();

    @Select("select distinct education from talent_pool where education is not null and education !=''")
    List<String> selectEdus();

    @Select("select distinct major from talent_pool where major is not null and major !=''")
    List<String> selectMajors();

    @Select("select * from talent_pool where `delete` = 0 order by mtime desc ")
    List<TalentPool> getList(CommonPageReq req);
}
