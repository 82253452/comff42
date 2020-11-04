package com.f4w.job;

import com.f4w.service.TalentPoolService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@JobHandler(value = "talentPoolJob")
@Service
public class TalentPoolJob extends IJobHandler {
    @Resource
    private TalentPoolService talentPoolService;

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        talentPoolService.start();
        return new ReturnT<>("ok");
    }
}
