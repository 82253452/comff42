package com.f4w.service;

import com.f4w.dto.req.JobInfoReq;
import com.f4w.entity.BusiApp;
import com.f4w.job.CommentContext;
import com.f4w.mapper.BusiAppMapper;
import com.f4w.mapper.WxmpMapper;
import com.f4w.weapp.WxOpenService;
import com.github.kevinsawicki.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import org.apache.commons.collections4.MapUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.HashMap;
import java.util.Map;

import static com.f4w.utils.Constant.Cachekey.ALERT_MESSAGE_APPID;
import static com.f4w.utils.Constant.Cachekey.SEND_MESSAGE_OPENID;

@Slf4j
@Service
public class PushUtils {

    @Resource
    private BusiAppMapper busiAppMapper;
    @Resource
    private WxOpenService wxOpenService;
    @Resource
    private WxmpMapper wxmpMapper;
    @Resource
    private CommentContext commentContext;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void sendWx(JobInfoReq jobinfo, String msg) {
        BusiApp busiApp = busiAppMapper.selectOne(BusiApp.builder().appId(jobinfo.getAppId()).build());
        WxMpKefuMessage.WxArticle article = new WxMpKefuMessage.WxArticle();
        article.setUrl("https://mass.zhihuizhan.net//#/unemp/alert/" + jobinfo.getAppId());
        article.setTitle(busiApp.getNickName() + "-告警");
        article.setDescription(msg);
        article.setPicUrl(busiApp.getHeadImg());
        stringRedisTemplate.opsForList().range(SEND_MESSAGE_OPENID, 0, 10).forEach(id -> {
            try {
                wxOpenService.getWxOpenComponentService().getWxMpServiceByAppid(ALERT_MESSAGE_APPID).getKefuService().sendKefuMessage(
                        WxMpKefuMessage
                                .NEWS()
                                .addArticle(article)
                                .toUser(id)
                                .build());
            } catch (WxErrorException e) {
                e.printStackTrace();
                log.info("发送告警失败");
            }
        });
    }

    public void sendToServerJiang(JobInfoReq jobinfo, String msg) {
        BusiApp busiApp = busiAppMapper.selectOne(BusiApp.builder().appId(jobinfo.getAppId()).build());
        String desp = "### ["+msg+"](https://mass.zhihuizhan.net//#/unemp/alert/" + jobinfo.getAppId() + ")";
        Map map = new HashMap();
        map.put("text", busiApp.getNickName() + "-告警");
        map.put("desp", desp);
        HttpRequest.post("https://sc.ftqq.com/SCU99607T6071905d4792bd7ae799714df94506bd5ecf234b1eb28.send").form(map).body();
    }

    public void sendToJISHIDA(JobInfoReq jobinfo, String msg) {
        BusiApp busiApp = busiAppMapper.selectOne(BusiApp.builder().appId(jobinfo.getAppId()).build());
        String desp = "### ["+msg+"](https://mass.zhihuizhan.net//#/unemp/alert/" + jobinfo.getAppId() + ")";
        Map map = new HashMap();
        map.put("key", "af004a1efcdd4050967c4f75de5301bf");
        map.put("head", busiApp.getNickName() + "-告警");
        map.put("body", desp);
        HttpRequest.post("http://push.ijingniu.cn/send").form(map).body();
    }

}
