package com.f4w.weapp.handler;

import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

import static com.f4w.utils.Constant.Cachekey.WEIXIN_OPEN_USER_APPID;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
@Slf4j
public class SubscribeHandler implements WxMpMessageHandler {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) throws WxErrorException {
        log.info("用户关注公众号-{}",wxMessage.getFromUser());
        stringRedisTemplate.opsForSet().add(String.format(WEIXIN_OPEN_USER_APPID, weixinService.getWxMpConfigStorage().getAppId()), wxMessage.getFromUser());
        return null;
    }


}
