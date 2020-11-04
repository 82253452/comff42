package com.f4w.job;

import com.f4w.dto.req.JobInfoReq;
import com.f4w.entity.BusiApp;
import com.f4w.entity.Wxmp;
import com.f4w.mapper.WxmpMapper;
import com.f4w.utils.JobException;
import com.f4w.utils.SpringContextUtils;
import com.f4w.weapp.WxOpenService;
import lombok.Data;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.material.WxMediaImgUploadResult;
import me.chanjar.weixin.mp.bean.material.WxMpMaterial;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.UUID;

@Data
public abstract class CommentStrategy {
    private Integer type;

    private WxOpenService wxOpenService;

    public WxmpMapper wxmpMapper;

    public CommentStrategy() {
        wxOpenService = SpringContextUtils.getBean(WxOpenService.class);
        wxmpMapper = SpringContextUtils.getBean(WxmpMapper.class);
    }


    public abstract String dealHtml(JobInfoReq jobinfo, BusiApp busiApp, Wxmp wxmp) throws JobException;

    public String dealHeadAndFooter(JobInfoReq jobinfo, BusiApp busiApp, Wxmp wxmp) throws JobException {
        String s = this.dealHtml(jobinfo, busiApp, wxmp);
        if (StringUtils.isNotBlank(busiApp.getHeaderText())) {
            s = busiApp.getHeaderText() + s;
        }
        if (StringUtils.isNotBlank(busiApp.getFooterText())) {
            s += busiApp.getFooterText();
        }
        return s;
    }

    public abstract Wxmp findWxmp(JobInfoReq jobinfo) throws JobException;

    /**
     * 图片遮罩
     *
     * @param background
     * @throws IOException
     */
    public void coverImg(BufferedImage background) throws IOException {
        Graphics2D bgG2 = (Graphics2D) background.getGraphics();

        //遮罩层大小
        int coverWidth = background.getWidth();
        int coverHeight = background.getHeight();
        //遮罩层位置
        int coverX = 0;
        int coverY = background.getHeight() - coverHeight;

        //创建黑色遮罩层
        BufferedImage cover = new BufferedImage(coverWidth, coverHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D coverG2 = (Graphics2D) cover.getGraphics();
        coverG2.setColor(Color.BLACK);
        coverG2.fillRect(0, 0, coverWidth, coverHeight);
        coverG2.dispose();

        //开启透明度
        bgG2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.5f));
        //描绘
        bgG2.drawImage(cover, coverX, coverY, coverWidth, coverHeight, null);
        //结束透明度
        bgG2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        bgG2.dispose();

    }

    public String imageCoverUpload(String appId, String thumbnail) throws JobException {
        if (StringUtils.isBlank(thumbnail)) {
            return "";
        }
        File file = null;
        try {
            file = File.createTempFile(UUID.randomUUID().toString(), ".png");
            URL url = new URL(thumbnail);
            BufferedImage read = ImageIO.read(url);
            coverImg(read);
            playCoverImg(read);
            ImageIO.write(read, "png", file);
            imgScale(file, 0.8);
            WxMpMaterial wxMpMaterial = new WxMpMaterial();
            wxMpMaterial.setFile(file);
            wxMpMaterial.setName("media");
            WxMediaImgUploadResult result = wxOpenService
                    .getWxOpenComponentService()
                    .getWxMpServiceByAppid(appId)
                    .getMaterialService()
                    .mediaImgUpload(file);
            return result.getUrl();
        } catch (IOException | WxErrorException e) {
            e.printStackTrace();
            throw new JobException("图片上传失败");
        } finally {
            if (file != null) {
                file.deleteOnExit();
            }
        }

    }


    public String imageUpload(String appId, String thumbnail) throws JobException{
        if (StringUtils.isBlank(thumbnail)) {
            return "";
        }
        File file = null;
        try {
            file = File.createTempFile(UUID.randomUUID().toString(), ".png");
            URL url = new URL(thumbnail);
            BufferedImage read = ImageIO.read(url);
            ImageIO.write(read, "png", file);
            imgScale(file, 0.8);
            WxMpMaterial wxMpMaterial = new WxMpMaterial();
            wxMpMaterial.setFile(file);
            wxMpMaterial.setName("media");
            WxMediaImgUploadResult result = wxOpenService
                    .getWxOpenComponentService()
                    .getWxMpServiceByAppid(appId)
                    .getMaterialService()
                    .mediaImgUpload(file);
            return result.getUrl();
        } catch (IOException | WxErrorException e) {
            e.printStackTrace();
            throw new JobException("图片上传失败");
        } finally {
            if (file != null) {
                file.deleteOnExit();
            }
        }
    }

    /**
     * 图片合成播放按钮
     */
    public void playCoverImg(BufferedImage background) throws JobException {
        URL url = null;
        try {
            url = new URL("https://kan-jian.oss-cn-beijing.aliyuncs.com/topic/20200512/20200512181329_egsp.png");
            BufferedImage play = ImageIO.read(url);
            int playWidth = play.getWidth();
            int playHeight = play.getHeight();

            Graphics2D bgG2 = (Graphics2D) background.getGraphics();
            int bgWidth = background.getWidth();
            int bgHeight = background.getHeight();

            int startX = (bgWidth - playWidth) / 2;
            int startY = (bgHeight - playHeight) / 2;
            bgG2.drawImage(play, startX, startY, playWidth, playHeight, null);
            bgG2.dispose();
        } catch (IOException e) {
            e.printStackTrace();
            throw new JobException("图片合成失败");
        }

    }

    public static void imgScale(File file, double size) throws IOException {
        //判断大小，如果小于指定大小，不压缩；如果大于等于指定大小，压缩
        if (file.length() <= size * 1024 * 1024) {
            return;
        }
        //按照比例进行缩小
        Thumbnails.of(file).scale(0.9).toFile(file);//按比例缩小
        imgScale(file, size);
    }


    public String replaceStr(String str, String rep) {
        return str.replaceFirst("\\{(.*?)\\}", rep);
    }

}
