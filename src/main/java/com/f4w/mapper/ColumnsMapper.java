package com.f4w.mapper;

import com.f4w.dto.req.ColumnsReq;
import com.f4w.dto.req.CommonPageReq;
import com.f4w.entity.Columns;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface ColumnsMapper extends Mapper<Columns> {
    @Select("select * from columns")
    List<Columns> selectByPage(ColumnsReq request);

    @Select("select * from columns where `delete` = 0 order by mtime desc ")
    List<Columns> getList(CommonPageReq req);
}
