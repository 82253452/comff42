package com.f4w.service;

import com.f4w.entity.TalentPool;
import com.f4w.mapper.TalentPoolMapper;
import com.f4w.utils.Constant;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeUtils;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.FormatUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TalentPoolService {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private TalentPoolMapper talentPoolMapper;


    private static HashMap<String, String> map = new HashMap<>();

    static {
        map.put("openid", "oyjRmtzRRyJTMawlMkz3Tks7gVKA");
        map.put("MemberType", "4");
        map.put("CNZZDATA4602977", "cnzz_eid%3D450587285-1586929200-http%253A%252F%252Fwww.dingzhourencai.com%252F%26ntime%3D1586929200");
        map.put("leixing", "Company");
        map.put("yhm", "123bohua");
        map.put("UserName", "123bohua");
        map.put("mima", "asdzxc123%40");
        map.put("PassWord", "16641ef7c7603748fbf8c24fdd80b884");
        map.put("PassWordx", "asdzxc123%40");
        map.put("ASPSESSIONIDSCSCQTBT", "EJJNGIEDIPNLLLEAPLDANFBM");
        map.put("RealName", "%B1%A3%B6%A8%B2%A9%BB%AA%D0%C5%CF%A2%D7%C9%D1%AF%D3%D0%CF%DE%B9%AB%CB%BE");
        map.put("CompanyName", "%B1%A3%B6%A8%B2%A9%BB%AA%D0%C5%CF%A2%D7%C9%D1%AF%D3%D0%CF%DE%B9%AB%CB%BE");
        map.put("cna", "WC4FFY0lvyUCASvgL2CKA9p8");
        map.put("UM_distinctid", "1717c74f5c15fc-01672e0e889156-153f6554-1fa400-1717c74f5c27ad");
        map.put("UserType", "Company");
        map.put("ComId", "43402");
        map.put("tongxingzheng", "145bc269a39952b54608430175460f96");
    }

    @SneakyThrows
    public void start() {
        for (int i = 1; i <= 500; i++) {
            Document document = Jsoup.connect("http://www.dingzhourencai.com/rencai.asp?Page=" + i + "&Position_b=&Position_s=&Qualification=&sex=&City=&County=&ValidityDate=&keyword=").get();
            String pattern = "href=\"jl.asp\\?perid=(.*?)\"";
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(document.html());
            while (m.find()) {
                String pid = m.group(1);
                if (StringUtils.isBlank(pid) || stringRedisTemplate.opsForSet().isMember(Constant.Cachekey.TALENT_POOL, pid)) {
                    continue;
                }
                stringRedisTemplate.opsForSet().add(Constant.Cachekey.TALENT_POOL, m.group(1));
                saveDetail(m.group(1));
            }
        }
    }

    @SneakyThrows
    private void saveDetail(String pid) {
        Document document = Jsoup.connect("http://www.dingzhourencai.com/jl.asp?perid=" + pid).cookies(map).get();
        Element body = document.body();
        try {
            //姓名
            Element baseInfo = body.child(1);
            Elements tdInfo = baseInfo.getElementsByAttributeValue("valign", "top");
            String name = tdInfo.first().child(0).child(0).text();
            //基本信息
            String base = tdInfo.text();
            //毕业院校
            String sc = getPartern(baseInfo.html(), "毕业院校：(.*?)<br>");
            //所学专业
            String major = getPartern(baseInfo.html(), "所学专业：(.*?)<br>");
            //更新时间
            String utime = getPartern(baseInfo.html(), "更新时间:(.*?)&nbsp;");
            //学历
            String edu = getPartern(body.html(), "学历：(.*?)<br>");
            //id
            String tid = getPartern(document.html(), "\"tel.asp\\?action=tel&amp;id=(.*?)\"");
            //手机号
            Document phoneHtml = Jsoup.connect("http://www.dingzhourencai.com/tel.asp?action=tel&id=" + tid).cookies(map).get();
            String phone = getPartern(phoneHtml.html(), "document.write\\((.*?)\\)");
            talentPoolMapper.insertSelective(TalentPool.builder()
                    .name(name)
                    .phone(phone)
                    .sc(sc)
                    .pid(pid)
                    .tid(tid)
                    .base(base)
                    .education(edu)
                    .major(major)
                    .updateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(utime))
                    .build());
        } catch (Exception e) {
            System.out.println("解析html异常---" + pid);
            System.out.println(e.getMessage());
        }
    }

    private static String getPartern(String content, String partern) {
        String render = "";
        Pattern p = Pattern.compile(partern);
        Matcher m = p.matcher(content);
        if (m.find()) {
            render = m.group(1);
        }
        return render;
    }

}
