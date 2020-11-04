package com.f4w.weapp.handler;

import lombok.extern.slf4j.Slf4j;
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
public class UnsubscribeHandler implements WxMpMessageHandler {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService wxMpService,
                                    WxSessionManager sessionManager) {
        log.info("用户取消关注公众号-{}",wxMessage.getFromUser());
        stringRedisTemplate.opsForSet().remove(String.format(WEIXIN_OPEN_USER_APPID, wxMpService.getWxMpConfigStorage().getAppId()), wxMessage.getFromUser());
        return null;
    }

}
