package com.f4w.utils;

import org.apache.commons.collections4.MapUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 */
public class R extends HashMap {
    private static final long serialVersionUID = 1L;

    public R() {
        put("code", 1000);
        put("msg", "操作成功");
        put("show", false);
    }

    public static R error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(500, msg);
    }

    public static R error(int code, String msg) {
        return error(code, msg, false);
    }

    public static R error(int code, String msg, boolean show) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        r.put("show", show);
        return r;
    }

    public static R ok(String msg) {
        return ok(1000, msg);
    }

    public static R ok(Object obj) {
        return ok(1000, "success").put("data", obj);
    }

    public static R ok(int code, String msg) {
        return ok(code, msg, false);
    }

    public static R ok(int code, String msg, boolean show) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        r.put("show", show);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public Boolean isOk() {
        if ("1000".equals(MapUtils.getString(this, "code"))) {
            return true;
        }
        return false;
    }

    public String getMsg() {
        return MapUtils.getString(this, "msg");
    }

    public Integer getCode() {
        return MapUtils.getInteger(this, "code");
    }

    public R validate(String... key) {
        if (key.length == 0) {
            return R.ok();
        } else {
            for (String s : key) {
                if ("".equals(MapUtils.getString(this, s, ""))) {
                    return R.error(Constant.PARAMS_UNDEFINED, Constant.PARAMS_UNDEFINED_MESSAGE + s);
                }
            }

        }
        return R.ok();
    }

    public static R validate(Map map, String... key) {
        if (key.length == 0) {
            return R.ok();
        } else {
            for (String s : key) {
                if ("".equals(MapUtils.getString(map, s, ""))) {
                    return R.error(Constant.PARAMS_UNDEFINED, Constant.PARAMS_UNDEFINED_MESSAGE + s);
                }
            }

        }
        return R.ok();
    }

    public static R renderError(String content) {
        return ok(1000, "返回成功", false).put("data", content);
    }

    public static R renderSuccess(String name, Object o) {
        Map<String, Object> map = new HashMap();
        map.put(name, o);
        return ok(1000, "返回成功", false).put("data", map);
    }

    public R renderPut(String name, Object o) {
        ((Map) super.get("data")).put(name, o);
        return this;
    }

    public static R renderSuccess(boolean isShow) {
        return ok(1000, "返回成功", isShow);
    }
}
