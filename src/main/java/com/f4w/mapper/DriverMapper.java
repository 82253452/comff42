package com.f4w.mapper;

import com.f4w.dto.req.CommonPageReq;
import com.f4w.entity.Driver;
import com.f4w.utils.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DriverMapper extends BaseMapper<Driver> {

    @Select("select * from driver where `delete` = 0 order by mtime desc ")
    List<Driver> getList(CommonPageReq req);
}
