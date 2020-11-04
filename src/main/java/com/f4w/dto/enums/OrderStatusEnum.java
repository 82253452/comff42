package com.f4w.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * @author admin
 */
@Getter
@ToString
@RequiredArgsConstructor
public enum OrderStatusEnum {
    INIT(0, "待派单"),
    WAITING_SHIPMENT(1, "待派送"),
    IN_TRANSIT(2, "派送中"),
    WAITING_RECEIVING(3, "司机"),
    FINISH(4, "已完结"),
    CANCEL(5, "派送取消"),
    EXCEPTION(6, "派送异常"),
    OVERTIME(7, "派送超时"),
    DEL(8, "订单取消");

    private final Integer code;
    private final String description;
}
