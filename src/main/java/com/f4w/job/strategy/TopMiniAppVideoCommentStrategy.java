package com.f4w.job.strategy;

import com.f4w.dto.annotation.ArticleType;
import com.f4w.dto.enums.ArticleTypeEnum;
import com.f4w.dto.req.JobInfoReq;
import com.f4w.entity.BusiApp;
import com.f4w.entity.Wxmp;
import com.f4w.job.CommentStrategy;
import com.f4w.utils.JobException;
import org.apache.commons.lang3.RandomUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@DependsOn("springContextUtils")
@ArticleType(ArticleTypeEnum.TOP_MINIAPP_VIDEO)
public class TopMiniAppVideoCommentStrategy extends CommentStrategy {
    @Override
    public String dealHtml(JobInfoReq jobinfo, BusiApp busiApp, Wxmp wxmp) throws JobException {
        String img = imageCoverUpload(busiApp.getAppId(), wxmp.getThumbnail());
        String[] miniAppIdArray = jobinfo.getMiniAppId().split(",");
        String miniAppId = miniAppIdArray[RandomUtils.nextInt(0, miniAppIdArray.length)];
        return "<p>" +
                "<a class=\"weapp_text_link\"" +
                " style=\"font-size:14px;display:block;width:100%!important;" +
                "height: 180px!important;\n" +
                "box-shadow: #dddddd 2px 2px 8px;" +
                "    display:block;\n" +
                "    border-radius: 8px;" +
                "    background-color: #f7f7f7;\n" +
                "    background-image:url(" + img + ");\n" +
                "    background-repeat: no-repeat;\n" +
                "    background-size: 100% 100%;\n" +
                "    background-position: 0px 23px;\n" +
                "    color: #888;" +
                "\" " +
                "data-miniprogram-appid=\"" + miniAppId + "\" " +
                "data-miniprogram-path=\"" + replaceStr(jobinfo.getMiniAppPath(), wxmp.getVideoId()) + "\" " +
                "data-miniprogram-nickname=\"" + busiApp.getNickName() + "\" href=\"\" " +
                "data-miniprogram-type=\"text\" " +
                "data-miniprogram-servicetype=\"\">" +
                busiApp.getNickName() + "（点击播放视频）" +
                "</a>" +
                "</p>";
    }

    @Override
    public Wxmp findWxmp(JobInfoReq jobinfo) throws JobException {
        Wxmp wxmp = Optional.ofNullable(wxmpMapper.findWxmpTopByType(0, jobinfo.getColumn())).orElse(wxmpMapper.findWxmpByType(0, jobinfo.getColumn()));
        return wxmp;
    }
}
