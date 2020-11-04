package com.f4w.job;

import com.f4w.dto.annotation.ArticleType;
import com.f4w.dto.enums.ArticleTypeEnum;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@DependsOn("springContextUtils")
public class HandlerCommentProcessor implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //获取所有策略注解的Bean
        Map<String, Object> orderStrategyMap = applicationContext.getBeansWithAnnotation(ArticleType.class);
        orderStrategyMap.forEach((k,v)->{
            Class<CommentStrategy> orderStrategyClass = (Class<CommentStrategy>) v.getClass();
            ArticleTypeEnum type = orderStrategyClass.getAnnotation(ArticleType.class).value();
            //将class加入map中,type作为key
            CommentContext.commentStrategyBeanMap.put(type.getCode(),orderStrategyClass);
        });
    }
}
