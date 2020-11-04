package com.f4w.weapp;

import com.f4w.weapp.handler.*;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.util.http.apache.DefaultApacheHttpClientBuilder;
import me.chanjar.weixin.open.api.impl.WxOpenInRedisConfigStorage;
import me.chanjar.weixin.open.api.impl.WxOpenMessageRouter;
import me.chanjar.weixin.open.api.impl.WxOpenServiceImpl;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

@Component
@Slf4j
@EnableConfigurationProperties({WeiXinOpenConfig.class})
public class WxOpenService extends WxOpenServiceImpl {
    private static int connectionRequestTimeout = 13000;//从连接池获取链接的超时时间设置,默认3000ms
    private static int connectionTimeout = 15000;//建立链接的超时时间,默认5000ms.(由于使用了连接池,这个参数没有实际意义)
    private static int soTimeout = 120000;//连接池socket超时时间,默认5000ms
    private static int idleConnTimeout = 60000;//空闲链接的超时时间,默认60000ms
    private static int checkWaitTime = 60000;//空闲链接的检测周期,默认60000ms
    private static int maxConnPerHost = 10;//每路最大连接数,默认10
    private static int maxTotalConn = 50;//连接池最大连接数,默认50


    @Resource
    private WeiXinOpenConfig weiXinOpenConfig;
    @Resource
    private SubscribeHandler subscribeHandler;
    @Resource
    private UnsubscribeHandler unsubscribeHandler;
    @Resource
    private AuditFailHandler auditFailHandler;
    @Resource
    private AuditSuccessHandler auditSuccessHandler;
    @Resource
    private MsgHandler msgHandler;
    @Resource
    private RedisProperties redisProperies;
    private static JedisPool pool;


    @PostConstruct
    public void init() {
        WxOpenInRedisConfigStorage wxOpenInMemoryConfigStorage = new WxOpenInRedisConfigStorage(getJedisPool());
        wxOpenInMemoryConfigStorage.setComponentAppId(weiXinOpenConfig.getComponentAppId());
        wxOpenInMemoryConfigStorage.setComponentAppSecret(weiXinOpenConfig.getComponentSecret());
        wxOpenInMemoryConfigStorage.setComponentToken(weiXinOpenConfig.getComponentToken());
        wxOpenInMemoryConfigStorage.setComponentAesKey(weiXinOpenConfig.getComponentAesKey());
        DefaultApacheHttpClientBuilder clientBuilder = DefaultApacheHttpClientBuilder.get();
        clientBuilder.setConnectionRequestTimeout(connectionRequestTimeout);
        clientBuilder.setConnectionTimeout(connectionTimeout);
        clientBuilder.setSoTimeout(soTimeout);
        clientBuilder.setIdleConnTimeout(idleConnTimeout);
        clientBuilder.setCheckWaitTime(checkWaitTime);
        clientBuilder.setMaxConnPerHost(maxConnPerHost);
        clientBuilder.setMaxTotalConn(maxTotalConn);
        wxOpenInMemoryConfigStorage.setApacheHttpClientBuilder(clientBuilder);
        setWxOpenConfigStorage(wxOpenInMemoryConfigStorage);
    }

    @Bean
    public WxOpenMessageRouter wxOpenMessageRouter() {
        WxOpenMessageRouter wxOpenMessageRouter = new WxOpenMessageRouter(this);
        wxOpenMessageRouter.rule().handler((wxMpXmlMessage, map, wxMpService, wxSessionManager) -> {
            log.info("接收到 {} 公众号请求消息，内容：{}", wxMpService.getWxMpConfigStorage().getAppId(), wxMpXmlMessage);
            return null;
        }).next();
        // 关注事件
        wxOpenMessageRouter
                .rule()
                .async(false)
                .msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.SUBSCRIBE)
                .handler(subscribeHandler)
                .end();

        // 取消关注事件
        wxOpenMessageRouter
                .rule()
                .async(false)
                .msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.UNSUBSCRIBE)
                .handler(unsubscribeHandler)
                .end();

        wxOpenMessageRouter
                .rule()
                .async(false)
                .msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.WEAPP_AUDIT_SUCCESS)
                .handler(auditSuccessHandler)
                .end();

        wxOpenMessageRouter
                .rule()
                .async(false)
                .msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.WEAPP_AUDIT_FAIL)
                .handler(auditFailHandler)
                .end();

        wxOpenMessageRouter.rule().async(false).msgType(WxConsts.XmlMsgType.TEXT).handler(msgHandler).end();

        return wxOpenMessageRouter;
    }

    private JedisPool getJedisPool() {
        if (pool == null) {
            pool = new JedisPool(new GenericObjectPoolConfig(), redisProperies.getHost(), redisProperies.getPort(), 2000, redisProperies.getPassword());
        }
        return pool;
    }

    public static void download(String urlString, String filename, String savePath) throws Exception {
        // 构造URL
        URL url = new URL(urlString);
        // 打开连接
        URLConnection con = url.openConnection();
        //设置请求超时为5s
        con.setConnectTimeout(5 * 1000);
        // 输入流
        InputStream is = con.getInputStream();

        // 1K的数据缓冲
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流
        File sf = new File(savePath);
        if (!sf.exists()) {
            sf.mkdirs();
        }
        OutputStream os = new FileOutputStream(sf.getPath() + "\\" + filename);
        // 开始读取
        while ((len = is.read(bs)) != -1) {
            os.write(bs, 0, len);
        }
        // 完毕，关闭所有链接
        os.close();
        is.close();
    }


}
