package com.f4w.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.f4w.annotation.NotTokenIntecerpt;
import com.f4w.entity.BusiApp;
import com.f4w.entity.BusiWCitys;
import com.f4w.entity.BusiWProvience;
import com.f4w.mapper.BusiAppMapper;
import com.f4w.mapper.BusiWCitysMapper;
import com.f4w.mapper.BusiWProvienceMapper;
import com.f4w.mapper.SysUserMapper;
import com.f4w.utils.ForeseenException;
import com.f4w.utils.Pinyin4j;
import com.f4w.utils.R;
import com.f4w.utils.Result;
import com.f4w.weapp.WxOpenService;
import com.google.gson.JsonObject;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.open.bean.result.WxOpenMaPageListResult;
import me.chanjar.weixin.open.bean.result.WxOpenResult;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.io.FileUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("common")
@NotTokenIntecerpt
public class CommonAPI {

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private WxOpenService wxOpenService;

    @Resource
    private BusiAppMapper busiAppMapper;
    @Resource
    private BusiWCitysMapper busiWCitysMapper;
    @Resource
    private BusiWProvienceMapper busiWProvienceMapper;

    /**
     * 获取七牛token
     *
     * @return
     */
    @GetMapping("/testC")
    public R testC() {
//        List<BusiWCitys> list = busiWCitysMapper.selectAll();
//        List<BusiWProvience> list = busiWProvienceMapper.selectAll();
//        Pinyin4j p = new Pinyin4j();
//        JSONArray jsonArray = new JSONArray();
//        JSONObject r = new JSONObject();
//        list.forEach(e -> {
//            String name = e.getName();
//            if (name.split("\\.").length > 1) {
//                name = name.split("\\.")[1];
//            }
//            try {
//                String s1 = p.toPinYinUppercaseInitials(name);
//                JSONObject j = new JSONObject();
//                j.put("name", name);
//                j.put("id", e.getId());
////                j.put("num", e.getCityNum());
////                j.put("pid", e.getProvinceId());
//                if (jsonArray.size() == 0) {
//                    JSONObject o = new JSONObject();
//                    o.put("title", s1);
//                    o.put("k", s1);
//                    JSONArray item = new JSONArray();
//                    item.add(j);
//                    o.put("items", item);
//                    jsonArray.add(o);
//                }
//                for (int i = 0; i < jsonArray.size(); i++) {
//                    if (s1.equals(jsonArray.getJSONObject(i).get("k"))) {
//                        jsonArray.getJSONObject(i).getJSONArray("items").add(j);
//                        break;
//                    }
//                    if (i == jsonArray.size() - 1) {
//                        JSONObject o = new JSONObject();
//                        o.put("title", s1);
//                        o.put("k", s1);
//                        JSONArray item = new JSONArray();
//                        item.add(j);
//                        o.put("items", item);
//                        jsonArray.add(o);
//                        break;
//                    }
//                }
//            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
//                badHanyuPinyinOutputFormatCombination.printStackTrace();
//            }
//        });
//        jsonArray.sort((o1, o2) -> {
//            return JSONObject.parseObject(o1.toString()).getString("k").compareTo(JSONObject.parseObject(o2.toString()).getString("k"));
//        });
//        System.out.println(jsonArray);
        List<BusiWCitys> list = busiWCitysMapper.selectAll();
        JSONObject r = new JSONObject();
        list.forEach(e -> {
            JSONObject j = new JSONObject();
            String name = e.getName();
            if (name.split("\\.").length > 1) {
                name = name.split("\\.")[1];
            }
            j.put("name", name);
            j.put("num", e.getCityNum());

            if (r.get(e.getProvinceId().toString()) == null) {
                JSONArray a = new JSONArray();
                a.add(j);
                JSONObject t = new JSONObject();
                t.put("items", a);
                r.put(e.getProvinceId().toString(), t);
            } else {
                JSONArray a = r.getJSONObject(e.getProvinceId().toString()).getJSONArray("items");
                a.add(j);
            }
        });
        System.out.println(r);
        return null;
    }

    public static void main(String[] args) throws BadHanyuPinyinOutputFormatCombination {

    }

    /**
     * 获取七牛token
     * laohuangli
     *
     * @return
     */
    @GetMapping("qiniuToken")
    public R qiniuToken() {
        String accessKey = "QGwnW_OYnSO3QR2osJ2mwqManZECHvFZ1tPq8bAv";
        String secretKey = "xQzpa36UhxHWj6PXHgNJJeGiFQifbOxVLDZ5zWue";
        String bucket = "avatar";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket, null, 3600, new StringMap()
                .put("returnBody", " {\"key\": $(key), \"hash\": $(etag), \"w\": $(imageInfo.width), \"h\": $(imageInfo.height)}"));
        return R.renderSuccess("uptoken", upToken);
    }

    /**
     * 获取七牛token
     * laohuangli
     *
     * @return
     */
    @GetMapping("ypQiniuToken")
    public Result ypQiniuToken() throws ForeseenException {
        String accessKey = "yAKiOwwpqTz23aqui0nUY-HJOCRpXsy8wTE94TK9";
        String secretKey = "YuyGHpSygf4rqQtRyijAulllibUtZ7P-sghjYu7w";
        String bucket = "supe";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket, null, 3600, new StringMap()
                .put("returnBody", " {\"key\": $(key), \"hash\": $(etag), \"w\": $(imageInfo.width), \"h\": $(imageInfo.height)}"));
        return Result.ok(upToken);
    }


    @GetMapping("setDomain")
    public R setDomain(String appId) throws WxErrorException {
        String domain = "https://www.cxduo.com";
        String domain2 = "https://mass.zhihuizhan.net";
        List<String> webViewDomain = new ArrayList<>();
        webViewDomain.add(domain2);
        List<String> requestdomainList = new ArrayList<>();
        requestdomainList.add(domain);
        requestdomainList.add(domain2);
        List<String> wsrequestdomainList = new ArrayList<>();
        wsrequestdomainList.add(domain);
        wsrequestdomainList.add(domain2);
        List<String> uploaddomainList = new ArrayList<>();
        uploaddomainList.add(domain);
        uploaddomainList.add(domain2);
        List<String> downloaddomainList = new ArrayList<>();
        downloaddomainList.add(domain);
        downloaddomainList.add(domain2);
        wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(appId).setWebViewDomain("add", webViewDomain);
        wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(appId).modifyDomain("add", requestdomainList, wsrequestdomainList, uploaddomainList, downloaddomainList);
        return R.renderSuccess(false);
    }

    @GetMapping("undocodeaudit")
    public R undocodeaudit(String appId) throws WxErrorException {
        BusiApp busiApp = new BusiApp();
        busiApp.setAppId(appId);
        busiApp = busiAppMapper.selectOne(busiApp);
        if (2 == busiApp.getStatus()) {
            WxOpenResult wxOpenResult = wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(appId).undoCodeAudit();
            if (!wxOpenResult.isSuccess()) {
                return R.error(1001, wxOpenResult.getErrmsg());
            }
            busiApp.setStatus(1);
            busiApp = busiAppMapper.selectOne(busiApp);
        }
        return R.renderSuccess(false);
    }

    /**
     * 获取体验二维码
     *
     * @param appId
     * @return
     */
    @GetMapping("/getTestQrcode")
    public byte[] getTestQrcode(String appId) {
        try {
            File file = wxOpenService
                    .getWxOpenComponentService()
                    .getWxMaServiceByAppid(appId)
                    .getTestQrcode(getPageList(appId).get(0), new HashMap<>());
            return FileUtils.readFileToByteArray(file);
        } catch (IOException | WxErrorException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<String> getPageList(String appId) throws WxErrorException {
        WxOpenMaPageListResult wxOpenMaPageListResult = wxOpenService.getWxOpenComponentService().getWxMaServiceByAppid(appId).getPageList();
        if (!"0".equals(wxOpenMaPageListResult.getErrcode())) {
            return null;
        }
        return wxOpenMaPageListResult.getPageList();
    }

    @PostMapping("login")
    public R login(@RequestParam Map<String, String> map) {
//        try {
//            WxMaJscode2SessionResult wxMaJscode2SessionResult = wxMaUserService.getSessionInfo(map.get("code"));
//            WxMaUserInfo wxMaUserInfo = wxMaUserService.getUserInfo(wxMaJscode2SessionResult.getSessionKey(), map.get("encryptedData"), map.get("iv"));
//            SysUser sysUser = new SysUser();
//            sysUser.setOpenid(wxMaUserInfo.getOpenId());
//            sysUser = sysUserMapper.selectOne(sysUser);
//            if (sysUser != null) {
//                sysUser.setGender(Integer.valueOf(wxMaUserInfo.getGender()));
//                sysUser.setAvatarurl(wxMaUserInfo.getAvatarUrl());
//                sysUserMapper.updateByPrimaryKeySelective(sysUser);
//            } else {
//                sysUser = new SysUser();
//                sysUser.setAvatarurl(wxMaUserInfo.getAvatarUrl());
//                sysUser.setNickname(wxMaUserInfo.getNickName());
//                sysUser.setCity(wxMaUserInfo.getCity());
//                sysUser.setProvince(wxMaUserInfo.getProvince());
//                sysUser.setCountry(wxMaUserInfo.getCountry());
//                sysUser.setLanguage(wxMaUserInfo.getLanguage());
//                sysUser.setGender(Integer.valueOf(wxMaUserInfo.getGender()));
//                sysUser.setOpenid(wxMaUserInfo.getOpenId());
//                sysUserMapper.insertSelective(sysUser);
//            }
//            return R.renderSuccess("token", token);
//        } catch (WxErrorException e) {
//            e.printStackTrace();
//        }
        return R.error();
    }
}
