package com.f4w.job.strategy;

import com.f4w.dto.annotation.ArticleType;
import com.f4w.dto.enums.ArticleTypeEnum;
import com.f4w.dto.req.JobInfoReq;
import com.f4w.entity.BusiApp;
import com.f4w.entity.Wxmp;
import com.f4w.job.CommentStrategy;
import com.f4w.utils.JobException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@ArticleType(ArticleTypeEnum.TOP_VIDEO)
public class TopVideoCommentStrategy extends CommentStrategy {
    @Override
    public String dealHtml(JobInfoReq jobinfo,BusiApp busiApp, Wxmp wxmp) throws JobException {
        return "<iframe frameborder=\"0\" width=\"640\" height=\"498\" src=\"https://v.qq.com/iframe/player.html?vid=" + wxmp.getVideoId() + "&tiny=0&auto=0\" allowfullscreen></iframe>";
    }

    @Override
    public Wxmp findWxmp(JobInfoReq jobinfo) throws JobException {
        Wxmp wxmp = Optional.ofNullable(wxmpMapper.findWxmpTopByType(0, jobinfo.getColumn())).orElse(wxmpMapper.findWxmpByType(0, jobinfo.getColumn()));
        return wxmp;
    }
}
