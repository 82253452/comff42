package com.f4w.carAdminController;

import com.f4w.dto.OrderInfoDto;
import com.f4w.dto.annotation.PermisionRole;
import com.f4w.dto.enums.OrderStatusEnum;
import com.f4w.dto.req.OrderPageReq;
import com.f4w.entity.Order;
import com.f4w.mapper.OrderMapper;
import com.f4w.mapper.SysRoleMapper;
import com.f4w.mapper.TransCompanyMapper;
import com.f4w.utils.ShowException;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Optional;

/**
 * @Author: yp
 * @Date: 2020/9/8 10:57
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Resource
    private OrderMapper orderMapper;

    @GetMapping("/list")
    @PermisionRole
    public PageInfo<OrderInfoDto> list(OrderPageReq req) {
        PageInfo<OrderInfoDto> page = PageInfo.of(orderMapper.getAdminList(req));
        return page;
    }

    @GetMapping("/detail")
    public OrderInfoDto selectById(String id) {
        OrderInfoDto orderInfoDto = orderMapper.selectById(id);
        return orderInfoDto;
    }

    @PutMapping
    public int update(@RequestBody Order order) {
        return orderMapper.updateByPrimaryKeySelective(order);
    }

    @DeleteMapping("/{id}")
    public int delete(@PathVariable String id) {
        return orderMapper.deleteByPrimaryKey(id);
    }

    @GetMapping("/orderToDriver")
    public void orderToDriver(@NotNull Integer orderId, @NotNull Integer userId) throws ShowException {
        Order order = Optional.ofNullable(orderMapper.selectByPrimaryKey(orderId)).orElseThrow(() -> new ShowException("订单id错误"));
        if (order.getStatus() != 0) {
            throw new ShowException("订单状态错误");
        }
        order.setStatus(OrderStatusEnum.WAITING_SHIPMENT.getCode());
        order.setReceiveUserId(userId);
        orderMapper.updateByPrimaryKeySelective(order);
    }
}
