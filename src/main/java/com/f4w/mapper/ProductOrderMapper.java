package com.f4w.mapper;

import com.f4w.dto.OrderInfoDto;
import com.f4w.dto.req.CommonPageReq;
import com.f4w.entity.Order;
import com.f4w.entity.ProductOrder;
import com.f4w.utils.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author admin
 */
public interface ProductOrderMapper extends BaseMapper<ProductOrder> {

    @Select("<script>\n" +
            "select o.* \n" +
            "from `product_order` o\n" +
            "where o.delete = 0\n" +
            "order by o.mtime desc\n" +
            "</script>")
    List<ProductOrder> getList(CommonPageReq req);
}
