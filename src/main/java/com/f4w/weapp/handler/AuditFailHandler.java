package com.f4w.weapp.handler;

import com.f4w.entity.BusiApp;
import com.f4w.mapper.BusiAppMapper;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Component
public class AuditFailHandler implements WxMpMessageHandler {

  @Resource
  private BusiAppMapper busiAppMapper;

  @Override
  public WxMpXmlOutMessage handle(
      WxMpXmlMessage wxMessage,
      Map<String, Object> context,
      WxMpService wxMpService,
      WxSessionManager sessionManager) {
    BusiApp busiApp = new BusiApp();
    busiApp.setAppId(wxMpService.getWxMpConfigStorage().getAppId());
    busiApp = busiAppMapper.selectOne(busiApp);
    busiApp.setStatus(4);
    busiApp.setAuditMsg(wxMessage.getFailReason());
    busiAppMapper.updateByPrimaryKey(busiApp);
    return null;
  }
}
