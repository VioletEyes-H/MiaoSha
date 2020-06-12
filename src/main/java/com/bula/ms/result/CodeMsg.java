package com.bula.ms.result;

public class CodeMsg {



    private int code;
    private String msg;
    //通用的错误码
    public static final CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static final CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static final CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");
    public static final CodeMsg REQUEST_ILLEGAL = new CodeMsg(500102, "请求非法");
    public static final CodeMsg VERIFY_CODE_FAIL = new CodeMsg(500103,"验证码获取失败");
    public static final CodeMsg ACCESS_LIMIT = new CodeMsg(500104,"请求频繁");
    //登录模块 5002XX
    public static final CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session不存在或者已经失效");
    public static final CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "登录密码不能为空");
    public static final CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "手机号不能为空");
    public static final CodeMsg MOBILE_ERROR = new CodeMsg(500213, "手机号格式错误");
    public static final CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500214, "手机号不存在");
    public static final CodeMsg PASSWORD_ERROR = new CodeMsg(500215, "密码错误");
    public static final CodeMsg NOT_LOGIN = new CodeMsg(500216, "请先登录");

    //商品模块 5003XX

    //订单模块 5004XX
    public static final CodeMsg ORDER_NOT_FOUND = new CodeMsg(500400, "订单不存在");

    //秒杀模块 5005XX
    public static final CodeMsg MIAO_SHA_COUNT_OVER = new CodeMsg(500500, "商品已经秒杀完毕");
    public static final CodeMsg REPEATE_MIAOSHA = new CodeMsg(500501, "不能重复秒杀");
    public static final CodeMsg MIAO_SHA_OVER = new CodeMsg(500502, "秒杀已结束");
    public static final CodeMsg MIAO_SHA_NOT_START = new CodeMsg(500503, "秒杀还没开始");
    public static final CodeMsg MIAO_SHA_FAIL = new CodeMsg(500504,"秒杀失败");
    public static final CodeMsg MIAO_SHA_VERIFY_CODE_FAIL = new CodeMsg(500505,"验证码错误，秒杀失败");

    private CodeMsg() {
    }

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "CodeMsg [code=" + code + ", msg=" + msg + "]";
    }

    public CodeMsg fillArgs(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }
}
