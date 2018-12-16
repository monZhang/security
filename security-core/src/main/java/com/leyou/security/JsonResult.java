package com.leyou.security;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JsonResult {

    private String msg;

    private Object obj;

    public JsonResult(String msg, Object obj) {
        this.msg = msg;
        this.obj = obj;
    }

    public static JsonResult fail() {
        return new JsonResult("操作失败！！", null);
    }

    public static JsonResult fail(Object obj) {
        return new JsonResult("操作失败！！", obj);
    }

    public static JsonResult ok() {
        return new JsonResult("操作成功！！", null);
    }

    public static JsonResult ok(Object obj) {
        return new JsonResult("操作成功！！", obj);
    }

    public static JsonResult build(String msg) {
        return new JsonResult(msg, null);
    }

    public static JsonResult build(String msg, Object obj) {
        return new JsonResult(msg, obj);
    }


}
