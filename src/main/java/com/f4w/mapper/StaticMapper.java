package com.f4w.mapper;

import com.f4w.dto.resp.OrderDaysResp;
import com.f4w.dto.resp.StaticResp;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author admin
 */
public interface StaticMapper {

    /**
     * 首页统计数据
     *
     * @return
     */
    @Select("select (select count(1) from `order` where status = 0) preOrderNum,\n" +
            "       (select count(1) from `order`)                  totalOrderNum,\n" +
            "       (select count(1) from `driver`)                 driverNum,\n" +
            "       (select count(1) from `trans_company`)          transNum\n" +
            "from dual")
    StaticResp getIndexStatic();

    /**
     * 订单增长数
     *
     * @return
     */
    @Select("select count(*) num, date_format(ctime, '%Y-%m-%d') time\n" +
            "from (select * from `order` order by ctime desc) o\n" +
            "group by date_format(ctime, '%Y-%m-%d')")
    List<OrderDaysResp> getOrderOfDays();

    /**
     * 用户增长数
     *
     * @return
     */
    @Select("select count(*) num, date_format(ctime, '%Y-%m-%d') time\n" +
            "from (select * from `sys_user` order by ctime desc) o\n" +
            "group by date_format(ctime, '%Y-%m-%d')")
    List<OrderDaysResp> getUserOfDays();

    /**
     * 司机增长数
     *
     * @return
     */
    @Select("select count(*) num, date_format(ctime, '%Y-%m-%d') time\n" +
            "from (select * from `driver` order by ctime desc) o\n" +
            "group by date_format(ctime, '%Y-%m-%d')")
    List<OrderDaysResp> getDriverOfDays();
}
