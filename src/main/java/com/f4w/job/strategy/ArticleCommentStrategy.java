package com.f4w.job.strategy;

import com.f4w.dto.annotation.ArticleType;
import com.f4w.dto.enums.ArticleTypeEnum;
import com.f4w.dto.req.JobInfoReq;
import com.f4w.entity.BusiApp;
import com.f4w.entity.Wxmp;
import com.f4w.job.CommentStrategy;
import com.f4w.utils.JobException;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@DependsOn("springContextUtils")
@ArticleType(ArticleTypeEnum.ARTICLE)
public class ArticleCommentStrategy extends CommentStrategy {
    @Override
    public String dealHtml(JobInfoReq jobinfo, BusiApp busiApp, Wxmp wxmp) throws JobException {
        Document doc = Jsoup.parse(wxmp.getContent());
        Element body = doc.body();
        //处理 image
        Elements src = body.getElementsByAttribute("src");
        for (Element s : src) {
            try {
                s.attr("src", imageUpload(busiApp.getAppId(), s.attr("src")));
            } catch (Exception e) {
                throw new JobException("内容图失败--{" + wxmp.getTitle() + "}");
            }
        }
        return body.html();
    }

    @Override
    public Wxmp findWxmp(JobInfoReq jobinfo) throws JobException {
        Wxmp wxmp = Optional.ofNullable(wxmpMapper.findWxmpByType(1, jobinfo.getColumn())).orElseThrow(() -> new JobException("查询文章数据为空--" + jobinfo.getColumn()));
        return wxmp;
    }
}
