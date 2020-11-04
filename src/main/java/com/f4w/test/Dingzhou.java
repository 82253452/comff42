package com.f4w.test;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dingzhou {
    private static HashMap<String, String> map = new HashMap<>();
    private static Set<String> list = new HashSet();
    private static Set<String> error = new HashSet();
    private static List<PersonData> datas = new ArrayList<>();

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

    public static void main(String[] args) {
        getIds();
        getDetail();
        toExcel();
    }

    @SneakyThrows
    private static Set getIds() {
        for (int i = 1; i <= 1; i++) {
            Document document = Jsoup.connect("http://www.dingzhourencai.com/rencai.asp?Page=" + i + "&Position_b=&Position_s=&Qualification=&sex=&City=&County=&ValidityDate=&keyword=").get();
            String pattern = "href=\"jl.asp\\?perid=(.*?)\"";
            Pattern p = Pattern.compile(pattern);
            Matcher m = p.matcher(document.html());
            while (m.find()) {
                list.add(m.group(1));
            }
        }
        return list;
    }

    @SneakyThrows
    private static void getDetail() {
        for (String pid : list) {
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
                String sc = "";
                String pattern = "毕业院校：(.*?)<br>";
                Pattern p = Pattern.compile(pattern);
                Matcher m = p.matcher(baseInfo.html());
                if (m.find()) {
                    sc = m.group(1);
                }
                String tid = getPartern(document.html(), "\"tel.asp\\?action=tel&amp;id=(.*?)\"");
                //手机号
                Document phoneHtml = Jsoup.connect("http://www.dingzhourencai.com/tel.asp?action=tel&id=" + tid).cookies(map).get();
                String phone = getPartern(phoneHtml.html(), "document.write\\((.*?)\\)");
                //所学专业
                String major = getPartern(baseInfo.html(), "所学专业：(.*?)<br>");
                //更新时间
                String utime = getPartern(baseInfo.html(), "更新时间:(.*?)&nbsp;");
                //学历
                String edu = getPartern(body.html(), "学历：(.*?)<br>");
                System.out.println(major);
                System.out.println(utime);
                System.out.println(edu);
                datas.add(PersonData.builder().name(name).phone(phone).sc(sc).base(base).id(tid).build());
            } catch (Exception e) {
                error.add(pid);
                System.out.println("解析html异常---" + pid);
                System.out.println(e.getMessage());
            }
        }
    }

    private static void toExcel() {
        String fileName = "/Users/zlsx/Documents/定州人才网" + System.currentTimeMillis() + ".xlsx";
        EasyExcel.write(fileName, PersonData.class).sheet("模板").doWrite(datas);
        System.out.println("异常数据");
        System.out.println(JSON.toJSONString(error));
    }

    private static String getPartern(String content, String partern) {
        String render = "";
        String pattern = partern;
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(content);
        if (m.find()) {
            render = m.group(1);
        }
        return render;
    }

}
