//package com.f4w.api;
//
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.f4w.entity.juhe.DictionaryResult;
//import com.f4w.entity.juhe.HuangLiResult;
//import com.f4w.entity.news.NewsResult;
//import com.f4w.utils.HttpUtils;
//import com.f4w.utils.R;
//import com.github.kevinsawicki.http.HttpRequest;
//import com.jayway.jsonpath.Configuration;
//import com.jayway.jsonpath.JsonPath;
//import com.jayway.jsonpath.ReadContext;
//import org.apache.commons.lang3.StringUtils;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import retrofit2.Call;
//
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.atomic.AtomicInteger;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//@RestController
//@RequestMapping("/third")
//public class ThirdAPI {
//    private static ReadContext xiehouyuContext;
//    private static ReadContext ciContext;
//    private static ReadContext wordContext;
//    private static ReadContext idiomContext;
//
//    static {
//        try {
//            xiehouyuContext = JsonPath.parse(Configuration.defaultConfiguration().jsonProvider().parse(new ClassPathResource("data/xiehouyu.json").getInputStream(), "utf-8"));
//            ciContext = JsonPath.parse(Configuration.defaultConfiguration().jsonProvider().parse(new ClassPathResource("data/ci.json").getInputStream(), "utf-8"));
//            wordContext = JsonPath.parse(Configuration.defaultConfiguration().jsonProvider().parse(new ClassPathResource("data/word.json").getInputStream(), "utf-8"));
//            idiomContext = JsonPath.parse(Configuration.defaultConfiguration().jsonProvider().parse(new ClassPathResource("data/idiom.json").getInputStream(), "utf-8"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @GetMapping("/xhzd/query")
//    public R getArticleList(String word, String type) throws IOException {
//        if (StringUtils.isNoneBlank(type, word)) {
//            List<Map> r = null;
//            if ("xiehouyu".equalsIgnoreCase(type)) {
//                r = xiehouyuContext.read("$..[?(@.riddle =~ /.*?" + word + ".*?/i)]");
//            } else if ("idiom".equalsIgnoreCase(type)) {
//                r = idiomContext.read("$..[?(@.word == '" + word + "')]");
//            } else if ("word".equalsIgnoreCase(type)) {
//                if (word.length() == 1) {
//                    r = wordContext.read("$..[?(@.word == '" + word + "')]");
//                } else
//                    r = ciContext.read("$..[?(@.ci == '" + word + "')]");
//            }
//            return R.renderSuccess("data", r);
//        }
//        return R.renderError("无查询结果");
//    }
//
//    @GetMapping("/news/topHeadlines")
//    public R topHeadlines(Integer page, Integer pageSize, String country, String sources, String category, String q) throws IOException {
//        Call<NewsResult> result = HttpUtils.NewsService().topHeadlines(page, pageSize, country, category, sources, q);
//        NewsResult render = result.execute().body();
//        if (null == render) {
//            return R.renderError("无查询结果");
//        }
//        return R.renderSuccess("data", render);
//    }
//
//    @GetMapping("/news/everything")
//    public R everything(Integer page, Integer pageSize, String language, String q) throws IOException {
//        Call<NewsResult> result = HttpUtils.NewsService().everything(1, 10, q, language, null, null, null, null, null);
//        NewsResult render = result.execute().body();
//        if (null == render) {
//            return R.renderError("无查询结果");
//        }
//        return R.renderSuccess("data", render);
//    }
//
//    @GetMapping("/laohuangli")
//    public R laohuangli(String date) throws IOException {
//        Call<HuangLiResult> result = HttpUtils.JuheService().laohuangli(date);
//        HuangLiResult render = result.execute().body();
//        if (null == render) {
//            return R.renderError("无查询结果");
//        }
//        return R.renderSuccess("data", render);
//    }
//
//    @GetMapping("/nameRate")
//    public R nameRates(String fName, String name) {
//        JSONObject s = nameRate(fName, name);
//        return R.renderSuccess("data", s);
//    }
//
//    @GetMapping("/nameMathing")
//    public R nameMath(String fName, String name) {
//        JSONObject s = nameMathing(fName, name);
//        return R.renderSuccess("data", s);
//    }
//
//    @GetMapping("/CooKArticleDetail")
//    public R CooKArticleDetail(String id) {
//        if (StringUtils.isBlank(id)) {
//            return R.error();
//        }
//        HttpRequest request = HttpRequest.get("https://m.meishij.net/huodong/zt_detail.php?id=" + id);
//        String s = request.body();
//        Document doc = Jsoup.parse(s);
//        Elements topImg = doc.select(".zt2018_main .topimgw img");
//        Elements title = doc.select(".zt2018_main .zttitlew");
//        Elements content = doc.select("html body .zt2018_main .ztcontentw div > *");
//        Elements contentF = new Elements();
//        Elements contentD = new Elements();
//        AtomicInteger f = new AtomicInteger();
//        content.forEach(c -> {
//            if ("div".equalsIgnoreCase(c.tagName())) {
//                f.set(1);
//                return;
//            }
//            if (!"p".equalsIgnoreCase(c.tagName())) {
//                return;
//            }
//            if (f.get() == 0) {
//                contentF.add(c);
//            } else {
//                contentD.add(c);
//            }
//        });
//        Elements newsList = doc.select("html body .zt2018_main .ztcontentw .nzt_cpitem_style1 a");
//        JSONArray jsonArray = new JSONArray();
//        newsList.forEach(t -> {
//            JSONObject jsonObject = new JSONObject();
//            jsonObject.put("url", t.attr("href"));
//            jsonObject.put("image", t.select(".cpimgw img").attr("src"));
//            jsonObject.put("title", t.select(".cpn").text());
//            jsonArray.add(jsonObject);
//        });
//        JSONObject result = new JSONObject();
//        result.put("title", title.text());
//        result.put("topImg", topImg.attr("src"));
//        result.put("content", content.toString());
//        result.put("contentF", contentF.toString());
//        result.put("contentD", contentD.toString());
//        result.put("newsList", jsonArray);
//        return R.renderSuccess("data", result);
//    }
//
//    @GetMapping("/CooKVideo")
//    public R CooKVideo(String id) {
//        if (StringUtils.isBlank(id)) {
//            return R.error();
//        }
//        HttpRequest request = HttpRequest.get("https://apph5.meishi.cc/article/article_detail.php?id=" + id);
//        String s = request.body();
//        Document doc = Jsoup.parse(s);
//        String vSrc = doc.select(".topimgw2 video").first().attr("src");
//        JSONArray contentImg = new JSONArray();
//        String title = doc.selectFirst(".main .title").text();
//        Elements imagesE = doc.select(".main .artical_content p img");
//        imagesE.forEach(e -> {
//            contentImg.add(e.attr("src"));
//        });
//        JSONObject result = new JSONObject();
//        result.put("vSrc", vSrc);
//        result.put("contentImg", contentImg);
//        result.put("title", title);
//        return R.renderSuccess("data", result);
//    }
//
//    @GetMapping("/CooKArticleNews")
//    public R CooKArticleNews(String id) {
//        if (StringUtils.isBlank(id)) {
//            return R.error();
//        }
//        HttpRequest request = HttpRequest.get("https://www.meishij.net/news.php?id=" + id);
//        String s = request.body();
//        Document doc = Jsoup.parse(s);
//        String img = doc.select(".main_w .main .cp_header .cp_headerimg_w img").get(0).attr("src");
//        String name = doc.select(".main_w .main .cp_header .cp_main_info_w .title a").text();
//        String title = doc.select(".main_w .main .cp_body .measure h2").first().text();
//        Elements gongxiaoE = doc.select(".main_w .main .cp_header .cp_main_info_w .yj_tags a");
//        JSONArray gongxiao = new JSONArray();
//        gongxiaoE.forEach(e -> {
//            gongxiao.add(e.text());
//        });
//        Elements zle = doc.select(".main_w .main .cp_body .materials_box .zl li");
//        Elements fle = doc.select(".main_w .main .cp_body .materials_box .fuliao li");
//        JSONArray yongliaoZ = new JSONArray();
//        JSONArray yongliaoF = new JSONArray();
//        zle.forEach(e -> {
//            JSONObject jsonObject = new JSONObject();
//            String zImg = e.selectFirst("img").attr("src");
//            String zName = e.selectFirst("h4 a").text();
//            String zNum = e.selectFirst("h4 span").text();
//            jsonObject.put("zImg", zImg);
//            jsonObject.put("zName", zName);
//            jsonObject.put("zNum", zNum);
//            yongliaoZ.add(jsonObject);
//        });
//        fle.forEach(e -> {
//            JSONObject jsonObject = new JSONObject();
//            String zName = e.selectFirst("h4 a").text();
//            String zNum = e.selectFirst("span").text();
//            jsonObject.put("zName", zName);
//            jsonObject.put("zNum", zNum);
//            yongliaoF.add(jsonObject);
//        });
//        Elements stempE = doc.select(".main_w .main .cp_body .measure .editnew .content");
//        JSONArray stemp = new JSONArray();
//        stempE.forEach(e -> {
//            JSONObject jsonObject = new JSONObject();
//            final String[] text = {""};
//            Elements textE = e.select(".c");
//            textE.forEach(e2 -> {
//                text[0] += e2.text() + "\n";
//            });
//            String sImg = e.select("img").attr("src");
//            jsonObject.put("text", text[0]);
//            jsonObject.put("img", sImg);
//            stemp.add(jsonObject);
//        });
//        JSONObject result = new JSONObject();
//        result.put("img", img);
//        result.put("name", name);
//        result.put("title", title);
//        result.put("yongliaoZ", yongliaoZ);
//        result.put("yongliaoF", yongliaoF);
//        result.put("stemp", stemp);
//        return R.renderSuccess("data", result);
//    }
//
//    @GetMapping("/CarKouBei")
//    public R CarKouBei(@RequestParam Map<String, String> map) {
//        final String[] url = {"http://koubei.bitauto.com/tree/xuanche/?"};
////        if (StringUtils.isNotBlank(l)) {
////            url += "&l=" + l;
////        }
//        map.keySet().forEach(k -> {
//            if (StringUtils.isNotBlank(map.get(k))) {
//                url[0] += "&" + k + "=" + map.get(k);
//            }
//        });
//        HttpRequest request = HttpRequest.get(url[0]);
//        String s = request.body();
//        Document doc = Jsoup.parse(s);
//        Elements liE = doc.select(".container .img-layout");
//        JSONArray result = new JSONArray();
//        liE.forEach(e -> {
//            JSONObject jsonObject = new JSONObject();
//            String img = e.select("img").attr("src");
//            String num = e.select(".p-list .sm_txt").text();
//            String title = e.select(".p-list a").text();
//            String money = e.select(".p-list .info").text();
//            jsonObject.put("money", money);
//            jsonObject.put("img", img);
//            jsonObject.put("num", num);
//            jsonObject.put("title", title);
//            result.add(jsonObject);
//        });
//        return R.renderSuccess("data", result);
//    }
//
//    @GetMapping("/HomeCat")
//    public R HomeCat() {
//        HttpRequest request = HttpRequest.get("https://tuku.jia.com/tags/list_a/");
//        String s = request.body();
//        Document doc = Jsoup.parse(s);
//        Elements dlE = doc.select(".beauty_wrap .beauty_sel dl");
//        JSONArray jsonArray = new JSONArray();
//        dlE.forEach(e -> {
//            JSONObject j = new JSONObject();
//            JSONArray a = new JSONArray();
//            String head = e.selectFirst("dt").text();
//            Elements dd_span = e.select("dd a");
//            dd_span.forEach(ss -> {
//                JSONObject jj = new JSONObject();
//                String c = ss.text();
//                String url = ss.attr("href");
//                String title = ss.attr("title");
//                jj.put("text", c);
//                jj.put("url", url);
//                jj.put("title", title);
//                a.add(jj);
//            });
//            j.put("head", head);
//            j.put("list", a);
//            jsonArray.add(j);
//        });
//        return R.renderSuccess("data", jsonArray);
//    }
//
//    @GetMapping("/HomeImgs")
//    public R HomeImgs(Integer i, String paramA, String paramB, String paramC, String paramD) {
//        String u = "https://tuku.jia.com/tags/list_a/";
//        int ii = 0;
//        if (!StringUtils.isAllBlank(paramA, paramB, paramC, paramD)) {
//            String param = "";
//            if (StringUtils.isNotBlank(paramA)) {
//                ii++;
//                param += "-" + paramA;
//            }
//            if (StringUtils.isNotBlank(paramB)) {
//                ii++;
//                param += "-" + paramB;
//            }
//            if (StringUtils.isNotBlank(paramC)) {
//                ii++;
//                param += "-" + paramC;
//            }
//            if (StringUtils.isNotBlank(paramD)) {
//                ii++;
//                param += "-" + paramD;
//            }
//            u += "list" + ii + param + "/";
//        }
//        Integer page = i / 50;
//        if (page > 0) {
//            u += "/p" + i + "/";
//        }
//
//        HttpRequest request = HttpRequest.get(u);
//        String s = request.body();
//        Document doc = Jsoup.parse(s);
//        Elements liE = doc.select(".beauty_wrap .beauty_list ul li");
//        JSONArray jsonArray = new JSONArray();
//        Element e = liE.get(i % 50);
//        String url = e.selectFirst(".beauty_info a").attr("href");
//        if (StringUtils.isNotBlank(url)) {
//            HttpRequest request2 = HttpRequest.get(url);
//            String ss = request2.body();
//            Document docs = Jsoup.parse(ss);
//            Elements imgsE = docs.select(".main-content .thumbnail-box ul li a img");
//            Integer size = imgsE.size();
//            imgsE.forEach(f -> {
//                JSONObject jsonObject = new JSONObject();
//                String title = f.attr("title");
//                String src = f.attr("src");
//                jsonObject.put("title", title);
//                jsonObject.put("src", src);
//                jsonObject.put("size", size);
//                jsonArray.add(jsonObject);
//            });
//        }
//        return R.renderSuccess("data", jsonArray);
//    }
//
//    @GetMapping("/goodsHello")
//    public R goodsHello() {
//        HttpRequest request = HttpRequest.get("http://www.siandian.com");
//        String s = request.body("gbk");
//        Document doc = Jsoup.parse(s);
//        Elements dList = doc.selectFirst(".tagList").select(" .tagBody");
//        JSONArray result = new JSONArray();
//        dList.forEach(e -> {
//            JSONObject j = new JSONObject();
//            JSONArray a = new JSONArray();
//            String title = e.select(".tagIcotxt").text();
//            e.select(".tagContent a").forEach(ee -> {
//                JSONObject jt = new JSONObject();
//                String text = ee.text();
//                String url = ee.attr("href");
//                jt.put("text", text);
//                jt.put("url", url);
//                a.add(jt);
//            });
//            j.put("title", title);
//            j.put("list", a);
//            result.add(j);
//        });
//        return R.renderSuccess("data", result);
//    }
//
//    @GetMapping("/goodsHelloList")
//    public R goodsHelloList(String next, String url) {
//        String base = "http://www.siandian.com";
//        if (StringUtils.isNotBlank(next)) {
//            if (next.split("/").length > 1) {
//                base += next;
//            } else {
//                base += url + next;
//            }
//        } else {
//            base += url;
//        }
//        HttpRequest request = HttpRequest.get(base);
//        String s = request.body("gbk");
//        Document doc = Jsoup.parse(s);
//        Elements dList = doc.select(".bodyMain .bodyMainBody .infoListUL li");
//        JSONArray result = new JSONArray();
//        dList.forEach(e -> {
//            JSONObject j = new JSONObject();
//            JSONArray a = new JSONArray();
//            String title = e.select("a").text();
//            String urls = e.select("a").attr("href");
//            String date = e.select("span").text();
//            j.put("title", title);
//            j.put("url", urls);
//            j.put("date", date);
//            result.add(j);
//        });
//        String nextPage = doc.select(".bodyMain .bodyMainBody .pagelist .thisclass").next().select("a").attr("href");
//        return R.renderSuccess("data", result).renderPut("next", nextPage);
//    }
//
//    @GetMapping("/goodsHelloContent")
//    public R goodsHelloContent() {
//        HttpRequest request = HttpRequest.get("http://www.siandian.com/qinghua/23288.html");
//        String s = request.body("gbk");
//        Document doc = Jsoup.parse(s);
//        String title = doc.select(".articleBody .shareBox h2").text();
//        String content = doc.select(".articleBody .articleContent .articleText p").outerHtml();
//        JSONObject result = new JSONObject();
//        result.put("title", title);
//        result.put("content", content);
//        return R.renderSuccess("data", result);
//    }
//
//    @GetMapping("/XSList")
//    public R XSList() {
//        HttpRequest request = HttpRequest.get("http://www.6pingm.cc/zuixin/");
//        String s = request.body("gbk");
//        Document doc = Jsoup.parse(s);
//        Elements select = doc.select(".box .index_middle .index_middle_c li");
//        JSONArray jsonArray = new JSONArray();
//        select.forEach(e -> {
//            JSONObject jsonObject = new JSONObject();
//            String href = e.select("span a").attr("href");
//            String text = e.select("span a").text();
//            String source = e.select("p").text();
//            String img = e.select("a img").attr("src");
//            jsonObject.put("text", text);
//            jsonObject.put("href", href);
//            jsonObject.put("source", source);
//            jsonObject.put("img", img);
//            jsonArray.add(jsonObject);
//        });
//        return R.renderSuccess("data", jsonArray);
//    }
//
//    @GetMapping("/XSContent")
//    public R XSContent(String url) {
//        HttpRequest request = HttpRequest.get("http://www.6pingm.cc/v/144863.html");
//        String s = request.body("gbk");
//        Document doc = Jsoup.parse(s);
//        JSONObject jsonObject = new JSONObject();
//        String text = doc.select(".cnav h3 span").text();
//        jsonObject.put("text", text);
//        String urd = doc.select("#showpagephoto").html();
//        String pattern = "'youku','(.*?)'";
//        Pattern r = Pattern.compile(pattern);
//        Matcher m = r.matcher(urd);
//        if (m.find()) {
//            jsonObject.put("src", m.group(1));
//        }
//        return R.renderSuccess("data", jsonObject);
//    }
//
//    private JSONObject nameMathing(String fName, String sName) {
//        Map<String, String> data = new HashMap<>();
//        data.put("xingx", fName);
//        data.put("mingx", sName);
//        HttpRequest request = HttpRequest.post("http://www.sheup.com/xingmingpeidui.php");
//        String s = request.form(data, "gbk").body("gbk");
//        Document doc = Jsoup.parse(s);
//        Elements cList = doc.select(".crights .c_1");
//        String num = cList.get(1).select(".c1_text p").get(1).select("font").text();
//        String title = cList.get(1).select(".c1_text p").get(2).select("strong").text();
//        String text = cList.get(1).select(".c1_text p").get(3).text();
//        String qianshi = cList.get(1).select(".c1_text p").get(4).text();
//        String qianshiText = cList.get(1).select(".c1_text p").get(5).text();
//        JSONObject result = new JSONObject();
//        result.put("num", num);
//        result.put("title", title);
//        result.put("text", text);
//        result.put("qianshi", qianshi);
//        result.put("qianshiText", qianshiText);
//        return result;
//    }
//
//    private JSONObject nameRate(String fName, String sName) {
//        Map<String, String> data = new HashMap<>();
//        data.put("xingx", fName);
//        data.put("mingx", sName);
//        HttpRequest request = HttpRequest.post("http://www.sheup.com/xingming_dafen.php");
//        String s = request.form(data, "gbk").body("gbk");
//        Document doc = Jsoup.parse(s);
//        String dafen = doc.select(".crights .c_dafen .dafen_right .dr_ass span").text();
//        Elements cList = doc.select(".crights .c_1");
//        Elements pList = cList.get(1).select("p");
//        JSONObject result = new JSONObject();
//        //打分
//        result.put("dafen", dafen);
//        //姓名分析
//        Elements pFir = cList.get(2).select(".c1_text");
//        Elements nameTr = pFir.select("table tr");
//        Elements firstName = nameTr.get(1).select("td");
//        result.put("firstName", firstName.get(0).text());
//        result.put("firstNameFan", firstName.get(1).text());
//        result.put("firstNamePin", firstName.get(2).text());
//        result.put("firstNameWuxing", firstName.get(3).text());
//        result.put("firstNameBihua", firstName.get(4).text());
//        result.put("firstNameJieShi", firstName.get(5).text());
//        Elements name = nameTr.get(2).select("td");
//        result.put("name", name.get(0).text());
//        result.put("nameFan", name.get(1).text());
//        result.put("namePin", name.get(2).text());
//        result.put("nameWuxing", name.get(3).text());
//        result.put("nameBihua", name.get(4).text());
//        result.put("nameJieShi", name.get(5).text());
//        //命格
//        Elements minge = pFir.select("p strong");
//        result.put("tiange", minge.get(0).text());
//        result.put("renge", minge.get(1).text());
//        result.put("dige", minge.get(2).text());
//        result.put("waige", minge.get(3).text());
//        result.put("zonge", minge.get(4).text());
//        //详解
//        final String[] xiangjie = {""};
//        AtomicInteger i = new AtomicInteger();
//        pList.forEach(e -> {
//            if (i.get() == pList.size() - 1) {
//                return;
//            }
//            xiangjie[0] += e.text() + "\n";
//            i.getAndIncrement();
//        });
//        result.put("xiangjie", xiangjie[0]);
//        return result;
//    }
//
//    public static void main(String[] args) {
//        List<Map> result = xiehouyuContext.read("$..[?(@.riddle =~ /.*?刀.*?/i)]");
//        result.forEach(e -> {
//            System.out.println(e);
//        });
//    }
//
//
//}
