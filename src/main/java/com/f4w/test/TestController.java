package com.f4w.test;


import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class TestController {

        final static String userid="430493";
    final static String secret="1ac56fd6-faba-4007-b332-baf3e2edb48a";
//    final static String userid = "21012";
//    final static String secret = "ded15192-6275-4cc4-be22-d2c2f5a7adee";
    final static String url = "http://openapi.xiguaji.com/v3/MBizHArticle/GetBizDetailInfo";

    public static void main(String[] args) {
        String ids = "huanqiucheping," +
                "hqqcsbgzh," +
                "qcyyj123," +
                "huanqiuweiip," +
                "miaodou2017," +
                "qcr0505," +
                "gh_04cd73023290," +
                "i-fefc," +
                "andingdongcha," +
                "Auto-Daily ," +
                "ycdy9540," +
                "yichehao," +
                "gbngzs," +
                "chezhubidu," +
                "lookarTV," +
                "qichang_abr," +
                "autobizreview," +
                "qctt_app," +
                "laoxiebaihua," +
                "ttqc2014," +
                "dazhongkanche," +
                "iauto-ilife," +
                "qichejianghu001," +
                "wupeipindao," +
                "autoconsumers," +
                "automobile-news," +
                "iAUTO2010," +
                "inqiche ," +
                "iauto2006," +
                "suv_daka," +
                "dgchezhi," +
                "xinnengyuanche," +
                "iyourdaily," +
                "iyourcar," +
                "dear_auto," +
                "djcars," +
                "xingyuanauto," +
                "gongfuauto," +
                "autochelizi," +
                "xchuxing," +
                "shujincm," +
                "cheshihongdian," +
                "GreatautoMCN," +
                "holoauto," +
                "fchc2014," +
                "myxcar ," +
                "autohomeweixin," +
                "pcautoweixin1 ," +
                "wscs18nian," +
                "wubache," +
                "yicheweixin," +
                "auto_sina," +
                "sohu_auto," +
                "auto_163_com," +
                "qqauto," +
                "wts_news18a," +
                "d1evwx," +
                "diandongbang2014," +
                "EVcentury ," +
                "EVSHIJIE," +
                "qichezhiliang," +
                "china_ap," +
                "autoworld-china," +
                "autofan_1986," +
                "TRENDS-CAR," +
                "auto-paiqiguan," +
                "marketingauto," +
                "amchejing," +
                "chedegangauto," +
                "luobobaogao," +
                "autowk," +
                "laosijichupin," +
                "qiyanclub," +
                "qsjnmc," +
                "onion-automobile," +
                "cardianping," +
                "cheshi1112," +
                "autocul," +
                "eastfacemedia," +
                "gaoshiguanshi," +
                "dripcar," +
                "emaonews," +
                "marketing-auto," +
                "orbcar," +
                "eautocar ," +
                "jiedawang-zhi," +
                "ex-thunder," +
                "cheyitiao," +
                "AutoLook," +
                "auto-travel," +
                "world-is-here ," +
                "che-bole," +
                "Motortime_News," +
                "qichehentinghua," +
                "daogeshuoche," +
                "youcheclub," +
                "autofang," +
                "Eautofan," +
                "allgoodcars ," +
                "qichebatan," +
                "qcsj678," +
                "tichebang," +
                "gh_6ee1b243998d," +
                "ss-ylz," +
                "auto-carkol," +
                "CNWAUTO," +
                "enginedaily," +
                "auto-first," +
                "FuturAuto," +
                "qcqc365," +
                "miaodongche30," +
                "baiautoshow," +
                "wawapingche," +
                "beitaishuoche," +
                "chexunchaichefang," +
                "autocheche," +
                "cheshendaka," +
                "cheshiqingtan," +
                "dabiaoche," +
                "chegemen," +
                "dianjuglobal," +
                "aijiayou8," +
                "diandonggo," +
                "glxsl2017," +
                "bachedao," +
                "leningrander2408," +
                "theNANstudio," +
                "man-wuxian," +
                "qiyumoment," +
                "autoblock," +
                "acegear," +
                "autoreport," +
                "CarBingoWX," +
                "aichelianbowang," +
                "qichenvmotou," +
                "shizhiqiche," +
                "sskanche," +
                "weixinlukuang," +
                "speecar," +
                "mrycj0522," +
                "jincar818," +
                "autotopnet," +
                "chenshushifen," +
                "Car_evaluation," +
                "jianyuecheping," +
                "HDautoshow," +
                "qichetanMotorTalk," +
                "Mcarchejie," +
                "linlingolinlingo," +
                "qichezuweixin," +
                "chexuncom ," +
                "qcds100," +
                "dingdingshuoche," +
                "xingcheshixian," +
                "qchgcj," +
                "cheyiquan2016," +
                "chaojishijia-car," +
                "GeekCar," +
                "BAO-CarStudio," +
                "juccbb," +
                "kf12gang," +
                "chezhaocha," +
                "chezaocha," +
                "gh_d002a1e13897," +
                "DailyCarNews," +
                "yuexing9818," +
                "cmm445," +
                "gzwctv," +
                "gzwcjs ," +
                "chetanhui," +
                "aichaiche," +
                "yycy2014," +
                "zhongshujiadao," +
                "gdtvczd," +
                "czshzz," +
                "yougushidedian," +
                "TopCarCn," +
                "aitecar," +
                "CZMDaShu," +
                "LHYQ2017," +
                "kaixinddc," +
                "GNCSTV," +
                "jsfamily520," +
                "kuaichebao," +
                "qichefun," +
                "lgq_itoyz," +
                "xiaobaimaiche," +
                "xinchepingwang," +
                "zhixingdongli," +
                "Ultimate-Auto," +
                "say_auto," +
                "Point_of_information," +
                "wowoauto," +
                "myautolive," +
                "aimaiche," +
                "gh_8f22151dd94c," +
                "topsellingcar," +
                "Auto-Video," +
                "panggeauto," +
                "autolab," +
                "speedweekly," +
                "autocarweekly," +
                "cheshijie_autoworld," +
                "autoos," +
                "cartracks," +
                "AutoTong," +
                "zhechezhime," +
                "SeleCar," +
                "BusinessCar01," +
                "ChampSaySo," +
                "Dr-Che," +
                "gasgooweb," +
                "JMQL1000," +
                "auto_car," +
                "CAD-Daily," +
                "lamacheping," +
                "xinnengauto," +
                "autolife2014 ," +
                "cartek2014," +
                "jiashipai," +
                "MalaAuto," +
                "myautolife," +
                "wangchuan_acsc," +
                "chexingshenghuo," +
                "autoask," +
                "yundui8," +
                "mycardaily," +
                "lapingkuche," +
                "yuguo20141021," +
                "pcchegulu," +
                "Scfaiart," +
                "cqshangbaocs," +
                "bofanshuoche," +
                "qichezhoulei," +
                "xianwaibang," +
                "cheshicheshi_," +
                "xinchebuluo," +
                "gh_d58c112aee63," +
                "double-lineline," +
                "Harbincar," +
                "enrichstudio," +
                "xingyining911," +
                "qcxlj024," +
                "chaweizhang," +
                "lyg13306911127," +
                "cheweidu," +
                "YmAuto," +
                "myautotime," +
                "liyangpingche," +
                "gh_9493ed82c5c4," +
                "aichemigz," +
                "Nicehaoche," +
                "zhinengqiche," +
                "AM-automedia," +
                "evochina," +
                "Ozeldushe," +
                "ZPshuo," +
                "hey-stone," +
                "goodcargoodlife," +
                "dishaolife," +
                "MMMCars," +
                "cheguluhua1314," +
                "idongche," +
                "mrthirteen-studio," +
                "CPZJun," +
                "leimeng8234," +
                "gh_622a70cffd1c," +
                "autocustomV8," +
                "lingxichetan," +
                "aichelts," +
                "tzjtgb," +
                "qishi8_cn," +
                "s0452e," +
                "hrb925," +
                "djken-924," +
                "AutoZine," +
                "axzjwx," +
                "carfanr," +
                "lukacar," +
                "qichecar818," +
                "youshiqiche," +
                "CarBuyer," +
                "l1528614376," +
                "tcgf88," +
                "lijianhongCAR," +
                "zizhuche," +
                "cheshierwozhidao," +
                "cqkslcm," +
                "chediandi," +
                "cheyunwang," +
                "Walking_in_Auto_Life," +
                "gh_663156fecbff," +
                "wenwuchedao," +
                "autochejie," +
                "mebuycar," +
                "chetanxiaogang," +
                "gh_4ac5f3061384," +
                "kabawanche," +
                "qicheqingbaozu," +
                "yourcheclub," +
                "Auto_Vision," +
                "carclub1999," +
                "tsqiche008," +
                "xihache," +
                "yihaocar," +
                "Che-YuYu," +
                "nbweekly," +
                "thepapernews," +
                "zqbcyol," +
                "huanqiu-com," +
                "guangzhoudaily," +
                "nddaily," +
                "NF_Daily," +
                "dskbdskb," +
                "qianjiangwanbao," +
                "xinwanbao," +
                "bandaochenbaowx," +
                "xbf_23186688," +
                "ctdsbgfwx," +
                "sywb8110110," +
                "ngzbnews," +
                "hangzhoudaily," +
                "shxwcb," +
                "ifeng-news," +
                "new-weekly," +
                "jndsb123," +
                "southernweekly," +
                "qiluwanbao002," +
                "yzwb20102806," +
                "hbjs_87311111," +
                "lswbwx," +
                "hbrbgfwx," +
                "bjnews_xjb," +
                "cdsb86612222," +
                "whcjrb," +
                "hsb88880000," +
                "dalianwanbao," +
                "ncwb_wx," +
                "sywb88," +
                "chinanewsweekly," +
                "hebeiqnb," +
                "ourcecn," +
                "autoeeo," +
                "jjbd21," +
                "wuxiaobopd," +
                "tancaijing," +
                "nbdnews," +
                "kongfuf," +
                "wallstreetcn," +
                "jjrbwx," +
                "aicjnews," +
                "Guokr42," +
                "zealertech," +
                "chaping321," +
                "huxiu_com," +
                "kejimx," +
                "coollabs," +
                "wow36kr," +
                "tangdaoya," +
                "guofen1225," +
                "keji3000," +
                "CSDNnews," +
                "appsolution," +
                "apptoday," +
                "damipingce," +
                "cas-iop," +
                "bxt_thu," +
                "goodjiyou," +
                "wangjiong2015," +
                "bibacps," +
                "CaptainWuya," +
                "hbrchinese," +
                "businessweek," +
                "cmochina," +
                "qspyq2015," +
                "zhenghedao," +
                "FCChinese," +
                "qyj128," +
                "guanlijiqiao," +
                "guanlizhihui," +
                "ichuangyebang," +
                "chazuomba," +
                "mantousxy," +
                "luojisw," +
                "Linkedln-China," +
                "v_danshen," +
                "Notesman," +
                "jiangchacha0314," +
                "read01," +
                "zhichangbianjibu," +
                "sinaentertainment," +
                "bayesay," +
                "dushetv," +
                "txent," +
                "bzdashijian," +
                "hizddy," +
                "dsfysweixin," +
                "duliyumovie," +
                "qw_plr," +
                "youzzu," +
                "girlnba," +
                "juziyule," +
                "hahahabzc," +
                "qiubai2005," +
                "aaaacopys," +
                "aaaaggm," +
                "adquan_2012," +
                "digitaling," +
                "DDYY5555," +
                "Domarketing-001," +
                "newmarketingcn," +
                "SeventeenPR," +
                "wannengdedashu," +
                "love-haituansucaihao," +
                "guohun1," +
                "open163," +
                "gh_be00dab5380b," +
                "duhaoshu," +
                "youshucc," +
                "DJ00123987," +
                "shidian3650," +
                "lifeweek," +
                "vistaweek," +
                "thefair2," +
                "newfhm," +
                "shenyefachi," +
                "ktmakeup," +
                "Miss_shopping_li," +
                "bazaar-china," +
                "instachina," +
                "cosmochina," +
                "shaofeidu," +
                "GQZHIZU," +
                "flightclub," +
                "kaishi09," +
                "haibao_cn," +
                "ellechina," +
                "sisterinlaw," +
                "woshitongdao," +
                "iiilass," +
                "dashu259," +
                "xzbqr123," +
                "knowyourself2015," +
                "see020," +
                "foodvideo," +
                "mingshuo-media," +
                "zhuhaishijian," +
                "ZXzuoshouhan," +
                "halosoftrice," +
                "liboqingbujia," +
                "bjwx0100," +
                "sh360touch," +
                "gz0020," +
                "gzbaishitong," +
                "gh_91eaa42e025a," +
                "iiidaily," +
                "big322," +
                "hereinuk," +
                "daaimaomikong," +
                "iiiher," +
                "zhenjing2012," +
                "instachina1," +
                "tucaoxingjun," +
                "yitiaotv," +
                "yedu127," +
                "sdvideos," +
                "ergengshipin," +
                "iliangcang," +
                "chanchuangyi," +
                "wudaoone," +
                "cj10141234," +
                "inszine," +
                "gouminwang," +
                "niangao-mama," +
                "milima666," +
                "kaishujianggushi," +
                "diqiuzhishiju," +
                "haoqi238," +
                "zhihuribao," +
                "feizhengchang123," +
                "zhangzhaozhong45," +
                "xingqiuyanjiusuo," +
                "feekr_trip," +
                "dili360," +
                "trendstraveler," +
                "jiesu1228," +
                "HUPUtiyu,";
        Map map = new HashMap();
        String[] split = ids.split(",");
        List<DataDemo> list = new ArrayList<>();
        for (int i = 0; i < split.length; i++) {
            map.put("WechatId", split[i]);
            String post = post(url, JSONObject.toJSONString(map));
            if (StringUtils.isBlank(post)) {
                System.out.println("'返回数据为空'" + i);
                continue;
            }
            try {
                JSONObject jsonObject = JSONObject.parseObject(post);
                DataDemo dataDemo = jsonObject.toJavaObject(DataDemo.class);
                dataDemo.setId(split[i]);
                list.add(dataDemo);
                System.out.println(dataDemo.toString());
                System.out.println(i);
            } catch (Exception e) {
                System.out.println("数据解析异常i-----" + post);
            }
        }
        // 写法1
        String fileName = "/Users/zlsx/mnt/data/noModleWrite" + System.currentTimeMillis() + ".xlsx";
        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName, DataDemo.class).sheet("模板").doWrite(list);

    }


    /**
     * 发送HttpPost请求
     *
     * @param strURL 服务地址
     * @param params json字符串,例如: "{ \"id\":\"12345\" }"
     * @return 成功:返回json字符串<br/>
     */
    public static String post(String strURL, String params) {
        String checksum = GenCheckSum(params, secret);
        BufferedReader reader = null;
        try {
            URL url = new URL(strURL);// 创建连接
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            // connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式

            connection.setRequestProperty("userid", userid);
            connection.setRequestProperty("checksum", checksum);

            connection.connect();
            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream(), "UTF-8"); // utf-8编码
            out.append(params);
            out.flush();
            out.close();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            String res = "";
            while ((line = reader.readLine()) != null) {
                res += line;
            }
            reader.close();

            return res;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return "error"; // 自定义错误信息
    }

    /**
     * 使用md5的算法进行加密
     */
    public static String md5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        int len = md5code.length();
        for (int i = 0; i < 32 - len; i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

    public static String GenCheckSum(String body, String secretkey) {
        String s = body + secretkey;
        String sign = md5(s);
        sign = sign.substring(14, 18);
        return sign.toLowerCase();
    }
}





