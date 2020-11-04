package com.f4w.service;

import com.alibaba.fastjson.JSONObject;
import com.f4w.entity.ProductOrder;
import com.f4w.mapper.ProductOrderMapper;
import com.f4w.mapper.SysUserMapper;
import com.f4w.utils.ShowException;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import groovy.util.logging.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * @author admin
 */
@Service
@Slf4j
public class OrderService {
    @Resource
    private SysUserMapper sysUserMapper;
    @Resource
    private ProductOrderMapper productOrderMapper;

    public void finallyOrder(WxPayOrderNotifyResult notifyResult) throws ShowException {
        ProductOrder productOrder = Optional.ofNullable(productOrderMapper.selectOne(ProductOrder.builder().orderNo(notifyResult.getOutTradeNo()).build())).orElseThrow(() -> new ShowException("未找到订单"));
        //修改订单状态
        if (productOrder.getStatus() == 1) {
            throw new ShowException("订单已经付款");
        }
        productOrder.setStatus(1);
        productOrderMapper.updateByPrimaryKey(productOrder);
    }
}
