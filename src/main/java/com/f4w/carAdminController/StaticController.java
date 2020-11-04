package com.f4w.carAdminController;

import com.f4w.dto.resp.OrderDaysResp;
import com.f4w.dto.resp.StaticResp;
import com.f4w.mapper.StaticMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: yp
 * @Date: 2020/9/24 17:23
 */
@RestController
@RequestMapping("/static")
public class StaticController {
    @Resource
    private StaticMapper staticMapper;

    @GetMapping("/index")
    public StaticResp index() {
        return staticMapper.getIndexStatic();
    }

    @GetMapping("/orderLine")
    public List<OrderDaysResp> orderLine() {
        return staticMapper.getOrderOfDays();
    }

    @GetMapping("/driverLine")
    public List<OrderDaysResp> driverLine() {
        return staticMapper.getDriverOfDays();
    }

    @GetMapping("/userLine")
    public List<OrderDaysResp> userLine() {
        return staticMapper.getUserOfDays();
    }
}
