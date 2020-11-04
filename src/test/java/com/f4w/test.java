package com.f4w;

import com.f4w.job.WechatPushArticleJob;
import com.f4w.utils.JobException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class test {
    @Resource
    private WechatPushArticleJob wechatPushArticleJob;

    @Test
    public void test() throws JobException {
        wechatPushArticleJob.execute("{\"type\":\"1\",\"time\":\"2019-01-28 08:05:41\",\"num\":\"5\",\"appId\":\"wx72d6e2a25d7b3bfa\"," +
                "\"column\":\"1\",\"comment\":\"false\",\"types\":\"5-4-2-3-1\",\"isPush\":\"true\",\"topNum\":\"2\"," +
                "\"contentSourceUrl\":\"www.baidu.com\",\"miniAppId\":\"wx66b6bc6daa7a51ec\",\"miniAppPath\":\"/pages/play/play\"}");
    }

//    @Test
//    public void testThrow() throws JobException {
//        throw new JobException("yichangcheshi ");
//    }
}
