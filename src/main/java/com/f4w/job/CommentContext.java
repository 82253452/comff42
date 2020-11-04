package com.f4w.job;

import com.f4w.utils.JobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class CommentContext {

    @Autowired
    private ApplicationContext applicationContext;
    //存放所有策略类Bean的map
    public static Map<Integer, Class<CommentStrategy>> commentStrategyBeanMap = new HashMap<>();

    public CommentStrategy getCommentStrategy(Integer type) throws JobException {
        Class<CommentStrategy> strategyClass = commentStrategyBeanMap.get(type);
        if (strategyClass == null) throw new JobException("没有该类型");
        //从容器中获取对应的策略Bean
        CommentStrategy bean = applicationContext.getBean(strategyClass);
        bean.setType(type);
        return bean;
    }
}
