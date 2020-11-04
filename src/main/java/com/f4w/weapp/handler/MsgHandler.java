package com.f4w.weapp.handler;

import com.f4w.dto.BusiQuestionDto;
import com.f4w.entity.BusiApp;
import com.f4w.mapper.BusiAppMapper;
import com.f4w.mapper.BusiQuestionMapper;
import com.f4w.weapp.WxOpenService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpKefuService;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.open.bean.message.WxOpenXmlMessage;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.f4w.utils.Constant.REPLAY_REQUESTION;
import static com.f4w.utils.Constant.REPLAY_REQUESTION_GLOABLE;

@Component
@Slf4j
public class MsgHandler implements WxMpMessageHandler {

    @Resource
    protected WxOpenService wxOpenService;
    @Resource
    private BusiAppMapper busiAppMapper;
    @Resource
    private BusiQuestionMapper busiQuestionMapper;

    @Override
    public WxMpXmlOutMessage handle(
            WxMpXmlMessage wxMessage,
            Map<String, Object> context,
            WxMpService weixinService,
            WxSessionManager sessionManager) {
        log.info("msg handler");
        try {
            String render = renderQeustion(wxMessage, weixinService);
//            render = buildPust(weixinService.getWxMpConfigStorage().getAppId(), wxMessage);
            log.info("render---{}", render);
            if (StringUtils.isBlank(render)) {
                return null;
            }
            return WxMpXmlOutMessage.TEXT().content(render)
                    .fromUser(wxMessage.getToUser())
                    .toUser(wxMessage.getFromUser())
                    .build();
        } catch (Exception e) {
            log.error("返回消息异常---{}", e.getMessage());
        }
        return null;
    }

    private String renderQeustion(WxMpXmlMessage wxMessage, WxMpService weixinService) throws Exception {
        BusiApp busiApp = Optional.ofNullable(busiAppMapper.selectOne(BusiApp.builder().appId(weixinService.getWxMpConfigStorage().getAppId()).build())).orElseThrow(() -> new Exception("没有appid"));
        List<BusiQuestionDto> list = null;
        String render = "暂时未上传，请留言课程名称，或添加QQ171947004，私聊呦";
        if (REPLAY_REQUESTION == busiApp.getReplay()) {
            list = busiQuestionMapper.getOneListQuestion(wxMessage.getContent(), busiApp.getUid(), busiApp.getAppId());
        } else if (REPLAY_REQUESTION_GLOABLE == busiApp.getReplay()) {
            list = busiQuestionMapper.getOneListQuestion(wxMessage.getContent(), busiApp.getUid(), null);
        } else {
            render = "没有回复，请联系管理员后台配置";
        }
        if (CollectionUtils.isNotEmpty(list)) {
            render = buildQuestion(list);
        }
        if (render.getBytes().length >= 2048) {
            render = "返回内容过多，请换个关键词试试！";
        }
        return render;
    }

    private String buildPust(String appId, WxMpXmlMessage inMessage) {
        String out = "";
        if (StringUtils.equalsAnyIgnoreCase(appId, "wxd101a85aa106f53e", "wx570bc396a51b8ff8")) {
            try {
                log.info("测试content--{}" + inMessage.getContent());
                if (StringUtils.equals(inMessage.getMsgType(), "text")) {
                    if (StringUtils.equals(inMessage.getContent(), "TESTCOMPONENT_MSG_TYPE_TEXT")) {
                        out = WxOpenXmlMessage.wxMpOutXmlMessageToEncryptedXml(
                                WxMpXmlOutMessage.TEXT().content("TESTCOMPONENT_MSG_TYPE_TEXT_callback")
                                        .fromUser(inMessage.getToUser())
                                        .toUser(inMessage.getFromUser())
                                        .build(),
                                wxOpenService.getWxOpenConfigStorage()
                        );
                    } else if (StringUtils.startsWith(inMessage.getContent(), "QUERY_AUTH_CODE:")) {
                        String msg = inMessage.getContent().replace("QUERY_AUTH_CODE:", "") + "_from_api";
                        WxMpKefuMessage kefuMessage = WxMpKefuMessage.TEXT().content(msg).toUser(inMessage.getFromUser()).build();
                        wxOpenService.getWxOpenComponentService().getWxMpServiceByAppid(appId).getKefuService().sendKefuMessage(kefuMessage);
                    }
                } else if (StringUtils.equals(inMessage.getMsgType(), "event")) {
                    WxMpKefuMessage kefuMessage = WxMpKefuMessage.TEXT().content(inMessage.getEvent() + "from_callback").toUser(inMessage.getFromUser()).build();
                    wxOpenService.getWxOpenComponentService().getWxMpServiceByAppid(appId).getKefuService().sendKefuMessage(kefuMessage);
                    out = "success";
                }
            } catch (WxErrorException e) {
                log.error("callback", e);
            }
        }
        return out;
    }

    private String buildQuestion(List<BusiQuestionDto> list) {
        final String[] out = {""};
        list.forEach(m -> {
            out[0] += m.toString();
        });
        return out[0];
    }

}
