package com.f4w;

import com.f4w.job.WechatPushArticleJob;
import com.f4w.mapper.BusiAppMapper;
import com.f4w.mapper.SysUserMapper;
import com.xxl.job.core.biz.model.ReturnT;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import tk.mybatis.spring.annotation.MapperScan;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author admin
 */
@SpringBootApplication
@MapperScan(basePackages = "com.f4w.mapper")
@RestController
@EnableAspectJAutoProxy
@EnableAsync
public class FrameApplication {

    @Resource
    private WechatPushArticleJob wechatPushArticleJob;

    public static void main(String[] args) {
        SpringApplication.run(FrameApplication.class, args);
    }

    @RequestMapping("/test")
    public void test(String param) throws Exception {
//        ReturnT<String> execute = wechatPushArticleJob.execute(param);
    }
}

