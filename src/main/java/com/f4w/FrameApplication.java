package com.f4w;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author admin
 */
@SpringBootApplication
@MapperScan(basePackages = "com.f4w.mapper")
@RestController
@EnableAspectJAutoProxy
@EnableAsync
public class FrameApplication {
    public static void main(String[] args) {
        SpringApplication.run(FrameApplication.class, args);
    }

}

