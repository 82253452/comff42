package com.f4w.mapper;

import com.f4w.dto.req.CommonPageReq;
import com.f4w.entity.Product;
import com.f4w.utils.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductMapper extends BaseMapper<Product> {

    @Select("<script>\n" +
            "select * from product where `delete` = 0 \n" +
            "<if test='type!=null'>\n" +
            " and type = #{type}\n" +
            "</if>\n" +
            "order by mtime desc \n" +
            "</script>")
    List<Product> getList(CommonPageReq req);
}
