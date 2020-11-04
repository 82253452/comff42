package com.f4w.mapper;

import com.f4w.dto.OrderInfoDto;
import com.f4w.dto.req.CarTypeReq;
import com.f4w.dto.req.CommonPageReq;
import com.f4w.dto.req.OrderIndexReq;
import com.f4w.dto.req.OrderPageReq;
import com.f4w.entity.CarType;
import com.f4w.entity.Order;
import com.f4w.utils.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author admin
 */
public interface OrderMapper extends BaseMapper<Order> {


    /**
     * car type list
     *
     * @param req
     * @return
     */
    @Select("<script>\n" +
            "select o.*, \n" +
            "       " +
            "title,\n" +
            "       type,\n" +
            "       car_length,\n" +
            "       car_width,\n" +
            "       car_height,\n" +
            "       carrying_capacity,\n" +
            "       img\n" +
            "from `order` o\n" +
            "         left join car_type ct on o.product_id = ct.id\n" +
            "where o.delete = 0\n" +
            "<if test='status!=null and status != &quot;&quot; '>\n" +
            "    " +
            "and o.status = #{status}\n" +
            "</if>\n" +
            "\n" +
            "order by o.mtime desc\n" +
            "</script>")
    List<OrderInfoDto> getList(CommonPageReq req);

    @Select("select o.*, \n" +
            "       " +
            "title,\n" +
            "       type,\n" +
            "       car_length,\n" +
            "       car_width,\n" +
            "       car_height,\n" +
            "       carrying_capacity,\n" +
            "       img\n" +
            "from `order` o\n" +
            "         left join car_type ct on o.product_id = ct.id\n" +
            "where o.delete = 0\n" +
            "and o.id = #{id}\n" +
            "order by o.mtime desc")
    OrderInfoDto selectById(String id);

    @Select("<script>\n" +
            "select o.*, \n" +
            "       " +
            "title,\n" +
            "       type,\n" +
            "       car_length,\n" +
            "       car_width,\n" +
            "       car_height,\n" +
            "       carrying_capacity,\n" +
            "       img\n" +
            "from `order` o\n" +
            "         left join car_type ct on o.product_id = ct.id\n" +
            "         <if test='transId!=null and transId != &quot;&quot; '>\n" +
            "            right join (select user_id from trans_company_user where trans_id = '') tcu on o.user_id = tcu.user_id\n" +
            "        " +
            "    </if>\n" +
            "where o.delete = 0\n" +
            "<if test='status!=null and status != &quot;&quot; '>\n" +
            "    and o.status = #{status}\n" +
            "</if>\n" +
            "<if test='userId!=null and userId != &quot;&quot; '>\n" +
            "    and o.user_id = #{userId}\n" +
            "</if>\n" +
            "order by o.mtime desc\n" +
            "</script>")
    List<OrderInfoDto> getAdminList(OrderPageReq req);

    @Select("<script>\n" +
            "select o.*, \n" +
            "       " +
            "title,\n" +
            "       type,\n" +
            "       car_length,\n" +
            "       car_width,\n" +
            "       car_height,\n" +
            "       carrying_capacity,\n" +
            "       img\n" +
            "from `order` o\n" +
            "         left join car_type ct on o.product_id = ct.id\n" +
            "where o.delete = 0\n" +
            "<if test='type!=null'>\n" +
            " and o.order_type=#{type}\n" +
            "</if>\n" +
            "<if test='reginFrom!=null'>\n" +
            " and o.address_district_from=#{reginFrom[2],javaType=java.lang.String,jdbcType=VARCHAR}\n" +
            "</if>\n" +
            "<if test='reginTo!=null'>\n" +
            " and o.address_district_to=#{reginTo[2],javaType=java.lang.String,jdbcType=VARCHAR}\n" +
            "</if>\n" +
            "<if test='keyWrod!=null'>\n" +
            " and 1=1\n" +
            "</if>\n" +
            " and o.status = 0\n" +
            "order by o.mtime desc\n" +
            "</script>")
    List<OrderInfoDto> getIndexList(OrderIndexReq req);

    @Select("<script>\n" +
            "select o.*, \n" +
            "       " +
            "title,\n" +
            "       type,\n" +
            "       car_length,\n" +
            "       car_width,\n" +
            "       car_height,\n" +
            "       carrying_capacity,\n" +
            "       img\n" +
            "from `order` o\n" +
            "         left join car_type ct on o.product_id = ct.id\n" +
            "where o.delete = 0\n" +
            "<if test='status!=null'>\n" +
            " and o.status = #{status}\n" +
            "</if>\n" +
            " and (o.receive_user_id = #{userId} or o.user_id = #{userId})\n" +
            "order by o.mtime desc\n" +
            "</script>")
    List<OrderInfoDto> getStatusList(CommonPageReq req);

    @Select("<script>\n" +
            "select o.*, \n" +
            "       " +
            "title,\n" +
            "       type,\n" +
            "       car_length,\n" +
            "       car_width,\n" +
            "       car_height,\n" +
            "       carrying_capacity,\n" +
            "       img\n" +
            "from `order` o\n" +
            "         left join car_type ct on o.product_id = ct.id\n" +
            "where o.delete = 0\n" +
            " and (o.status = 4 or o.status = 5 or o.status = 6 or o.status = 7)\n" +
            " and (o.receive_user_id = #{userId} or o.user_id = #{userId})\n" +
            "order by o.mtime desc\n" +
            "</script>")
    List<OrderInfoDto> getFinashList(CommonPageReq req);
}
