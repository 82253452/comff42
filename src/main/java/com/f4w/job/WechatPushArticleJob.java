package com.f4w.job;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.f4w.dto.req.JobInfoReq;
import com.f4w.entity.BusiApp;
import com.f4w.mapper.BusiAppMapper;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.TimeZone;

@Slf4j
@JobHandler(value = "weArticleJobHandler")
@Service
public class WechatPushArticleJob extends IJobHandler {
    @Resource
    private BusiAppMapper busiAppMapper;
    @Resource
    private MessageService messageService;

    @Override
    public ReturnT<String> execute(String s) {
        log.info("执行任务");
        DateTime now = DateTime.now();
        List<BusiApp> busiApps = busiAppMapper.select(BusiApp.builder().miniProgramInfo(1).build());
        busiApps.forEach(app -> {
            TimeZone.setDefault(TimeZone.getTimeZone("GMT+8"));
            JobInfoReq jobinfo = JSON.parseObject(app.getMessageParam(), JobInfoReq.class);
            log.debug(JSONObject.toJSONString(jobinfo));
            if (app.getAutoMessage() == 1 && new DateTime(jobinfo.getTime()).getMinuteOfDay() == now.getMinuteOfDay()) {
                messageService.sendMessage(jobinfo);
            }
        });
        return IJobHandler.SUCCESS;
    }

}
