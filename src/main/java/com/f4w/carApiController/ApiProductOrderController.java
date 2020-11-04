package com.f4w.carApiController;

import com.f4w.annotation.CurrentUser;
import com.f4w.annotation.NotTokenIntecerpt;
import com.f4w.dto.req.CommonPageReq;
import com.f4w.entity.Product;
import com.f4w.entity.ProductOrder;
import com.f4w.entity.SysUser;
import com.f4w.mapper.ProductMapper;
import com.f4w.mapper.ProductOrderMapper;
import com.f4w.service.OrderService;
import com.f4w.utils.Constant;
import com.f4w.utils.IPUtils;
import com.f4w.utils.ShowException;
import com.github.binarywang.wxpay.bean.notify.WxPayNotifyResponse;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.constant.WxPayConstants;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

/**
 * @Author: yp
 * @Date: 2020/9/8 10:57
 */
@RestController
@RequestMapping("/api/productOrder")
public class ApiProductOrderController {
    @Resource
    private ProductOrderMapper mapper;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private WxPayService wxPayService;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Value("${wx.pay.notifyUrl}")
    private String notifyUrl;
    @Resource
    private OrderService orderService;

    /**
     * 兑换商品
     *
     * @param req
     * @return
     */
    @GetMapping("/submit")
    public Object submit(@CurrentUser SysUser sysUser, CommonPageReq req) throws ShowException, WxPayException {
        if (req.getId() == null) {
            throw new ShowException("订单id不能为空");
        }
        Product product = Optional.ofNullable(productMapper.selectByPrimaryKey(req.getId())).orElseThrow(() -> new ShowException("商品id错误"));
        if (product.getStatus() != 1) {
            throw new ShowException("商品已经下架");
        }
        if (0 == product.getType() && sysUser.getIntegral() < product.getPrice().intValue()) {
            throw new ShowException("积分不足");
        }
        if (1 == product.getType() && product.getPrice().compareTo(sysUser.getAmount()) < 0) {
            throw new ShowException("余额不足");
        }
        String orderNo = UUID.randomUUID().toString().replaceAll("-", "");
        mapper.insertSelective(ProductOrder.builder()
                .productId(product.getId())
                .orderNo(orderNo)
                .orderType(product.getType())
                .status(0)
                .userId(sysUser.getId())
                .originalPrice(product.getPrice())
                .amount(product.getPrice())
                .des(product.getName())
                .build());
        if (1 == product.getType()) {
            return wxPayService.createOrder(WxPayUnifiedOrderRequest
                    .newBuilder()
                    .totalFee(product.getPrice().multiply(new BigDecimal("100")).toBigInteger().intValue())
                    .body(product.getName())
                    .outTradeNo(orderNo)
                    .tradeType(WxPayConstants.TradeType.JSAPI)
                    .spbillCreateIp(IPUtils.getIpAddr())
                    .openid(stringRedisTemplate.opsForHash().get(String.format(Constant.USER_OPEN_ID, "wx91fad2501e704f40"), sysUser.getId().toString()).toString())
                    .notifyUrl(notifyUrl)
                    .build());
        }
        return null;
    }

    /**
     * 支付回调
     *
     * @param xmlData
     * @return
     * @throws ShowException
     * @throws WxPayException
     */
    @PostMapping("/notify")
    @NotTokenIntecerpt
    public String notify(@RequestBody String xmlData) {
        try {
            final WxPayOrderNotifyResult notifyResult = wxPayService.parseOrderNotifyResult(xmlData);
            orderService.finallyOrder(notifyResult);
        } catch (Exception e) {
            e.printStackTrace();
            return WxPayNotifyResponse.fail(e.getMessage());
        }
        return WxPayNotifyResponse.success("成功");
    }
}
