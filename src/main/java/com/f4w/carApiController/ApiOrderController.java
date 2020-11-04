package com.f4w.carApiController;

import com.alibaba.fastjson.JSONObject;
import com.f4w.annotation.CurrentUser;
import com.f4w.dto.OrderInfoDto;
import com.f4w.dto.SysRoleDto;
import com.f4w.dto.enums.OrderStatusEnum;
import com.f4w.dto.req.CommonPageReq;
import com.f4w.dto.req.OrderIndexReq;
import com.f4w.dto.req.OrderPageReq;
import com.f4w.dto.req.OrderReq;
import com.f4w.entity.Order;
import com.f4w.entity.SysUser;
import com.f4w.mapper.OrderMapper;
import com.f4w.mapper.SysRoleMapper;
import com.f4w.utils.ForeseenException;
import com.f4w.utils.ShowException;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.f4w.utils.Constant.ORDER_ADDRESS;

/**
 * @Author: yp
 * @Date: 2020/9/8 10:57
 */
@RestController
@RequestMapping("/api/order")
public class ApiOrderController {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private SysRoleMapper sysRoleMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 上报订单位置
     *
     * @return
     * @throws ForeseenException
     */
    @GetMapping("/uploadAddress")
    public void uploadAddress(String id, String address) throws ForeseenException {
        stringRedisTemplate.opsForList().rightPush(String.format(ORDER_ADDRESS, id), address);
    }

    /**
     * 抢单
     *
     * @param req
     * @return
     * @throws ForeseenException
     */
    @GetMapping("/person/list")
    public PageInfo<OrderInfoDto> personList(OrderIndexReq req) throws ForeseenException {
        PageInfo<OrderInfoDto> page = PageInfo.of(orderMapper.getIndexList(req));
        return page;
    }

    /**
     * 抢单
     *
     * @param req
     * @return
     * @throws ForeseenException
     */
    @GetMapping("/company/list")
    public PageInfo<OrderInfoDto> companyList(OrderIndexReq req) throws ForeseenException {
        PageInfo<OrderInfoDto> page = PageInfo.of(orderMapper.getIndexList(req));
        return page;
    }

    /**
     * 抢单
     *
     * @param req
     * @return
     * @throws ForeseenException
     */
    @PostMapping("/index/list")
    public PageInfo<OrderInfoDto> indexList(@RequestBody OrderIndexReq req) throws ForeseenException {
        PageInfo<OrderInfoDto> page = PageInfo.of(orderMapper.getIndexList(req));
        return page;
    }

    /**
     * 查询我的订单
     *
     * @param req
     * @return
     * @throws ForeseenException
     */
    @GetMapping("/status/list")
    public PageInfo<OrderInfoDto> statusList(@CurrentUser SysUser sysUser, CommonPageReq req) throws ForeseenException {
        req.setUserId(sysUser.getId());
        PageInfo<OrderInfoDto> page = PageInfo.of(orderMapper.getStatusList(req));
        return page;
    }

    /**
     * 完成得单子
     *
     * @param req
     * @return
     * @throws ForeseenException
     */
    @GetMapping("/finash/list")
    public PageInfo<OrderInfoDto> fianshList(@CurrentUser SysUser sysUser, CommonPageReq req) throws ForeseenException {
        req.setUserId(sysUser.getId());
        PageInfo<OrderInfoDto> page = PageInfo.of(orderMapper.getFinashList(req));
        return page;
    }

    /**
     * 关闭订单
     *
     * @param req
     * @return
     * @throws ForeseenException
     */
    @GetMapping("/close")
    public void closeOrder(@CurrentUser SysUser sysUser, CommonPageReq req) throws ForeseenException {
        Order order = Optional.ofNullable(orderMapper.selectByPrimaryKey(req.getId())).orElseThrow(() -> new ShowException("订单查询错误"));
        if (!order.getUserId().equals(sysUser.getId())) {
            throw new ShowException("不可关闭的订单");
        }
        order.setDelete(1);
        orderMapper.updateByPrimaryKeySelective(order);
    }

    /**
     * 关闭订单
     *
     * @param req
     * @return
     * @throws ForeseenException
     */
    @GetMapping("/stop")
    public void stopOrder(@CurrentUser SysUser sysUser, CommonPageReq req) throws ForeseenException {
        Order order = Optional.ofNullable(orderMapper.selectByPrimaryKey(req.getId())).orElseThrow(() -> new ShowException("订单查询错误"));
        if (!order.getReceiveUserId().equals(sysUser.getId())) {
            throw new ShowException("不可操作的订单");
        }
        order.setStatus(1);
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @GetMapping("/detail")
    public OrderInfoDto selectById(String id) throws ForeseenException {
        OrderInfoDto orderInfoDto = orderMapper.selectById(id);
        return orderInfoDto;
    }

    @PutMapping
    public int update(@RequestBody Order order) throws ForeseenException {
        return orderMapper.updateByPrimaryKeySelective(order);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable String id) throws ForeseenException {
        return orderMapper.deleteByPrimaryKey(id);
    }

    @PostMapping("/submit")
    public void submit(@CurrentUser SysUser sysUser, @RequestBody OrderReq orderReq) throws ForeseenException {
        orderMapper.insertSelective(Order.builder()
                .phone(orderReq.getAddressFrom().getUser().getPhone())
                .orderNo(UUID.randomUUID().toString().replaceAll("-", ""))
                .productId(orderReq.getCarTypeId())
                .addressFrom(orderReq.getAddressFrom().getLocation().getName())
                .addressTo(orderReq.getAddressTo().getLocation().getName())
                .des(orderReq.getDes())
                .latitudeFrom(orderReq.getAddressFrom().getLocation().getLatitude())
                .latitudeTo(orderReq.getAddressTo().getLocation().getLatitude())
                .longitudeFrom(orderReq.getAddressFrom().getLocation().getLongitude())
                .longitudeTo(orderReq.getAddressTo().getLocation().getLongitude())
                .amount(orderReq.getAmount())
                .userId(sysUser.getId())
                .originalPrice(orderReq.getAmount())
                .userName(orderReq.getAddressFrom().getUser().getName())
                .discharge(orderReq.getDischarge())
                .deliveryTimeStart(DateTime.parse(orderReq.getTime()[0]).toDate())
                .deliveryTimeEnd(DateTime.parse(orderReq.getTime()[1]).toDate())
                .driverTop(orderReq.getDriverTop())
                .addressToDetail(orderReq.getAddressTo().getLocation().getAddress())
                .addressFromDetail(orderReq.getAddressFrom().getLocation().getAddress())
                .remark(orderReq.getRemark())
                .addressRoute(JSONObject.toJSONString(orderReq.getAddressRoute()))
                .addressFromHome(orderReq.getAddressFrom().getUser().getAddress())
                .addressToHome(orderReq.getAddressTo().getUser().getAddress())
                .receiveName(orderReq.getAddressTo().getUser().getName())
                .receivePhone(orderReq.getAddressTo().getUser().getPhone())
                .productName(orderReq.getCarTypeName())
                .addressProvinceFrom(orderReq.getAddressFrom().getLocation().getInfo().getProvince())
                .addressCityFrom(orderReq.getAddressFrom().getLocation().getInfo().getCity())
                .addressDistrictFrom(orderReq.getAddressFrom().getLocation().getInfo().getDistrict())
                .addressProvinceTo(orderReq.getAddressTo().getLocation().getInfo().getProvince())
                .addressCityTo(orderReq.getAddressTo().getLocation().getInfo().getCity())
                .addressDistrictTo(orderReq.getAddressTo().getLocation().getInfo().getDistrict())
                .status(0)
                .build());
    }

    @GetMapping("/receiveOrder")
    public void receiveOrder(@CurrentUser SysUser sysUser, String id) throws ForeseenException {
        Order order = Optional.ofNullable(orderMapper.selectByPrimaryKey(id)).orElseThrow(() -> new ShowException("订单id错误"));
        if (order.getStatus() == 0) {
            order.setReceiveUserId(sysUser.getId());
        }
        if (order.getStatus() < 4) {
            order.setStatus(order.getStatus() + 1);
            orderMapper.updateByPrimaryKeySelective(order);
        }
    }
}
