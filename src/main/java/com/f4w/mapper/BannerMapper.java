package com.f4w.mapper;

import com.f4w.dto.req.CommonPageReq;
import com.f4w.entity.Banner;
import com.f4w.utils.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface BannerMapper extends BaseMapper<Banner> {

    @Select("select * from banner where `delete` = 0 order by mtime desc ")
    List<Banner> getList(CommonPageReq req);
}
