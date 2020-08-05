package com.zhinang.iborrow.entity;

import com.zhinang.iborrow.util.WeixinPayUtil;

public class WeixinPrePayData {

    private String return_code = "";
    private String return_msg = "";
    private String appid = "";
    private String mch_id = "";
    private String nonce_str = "";
    private String sign = "";
    private String result_code = "";
    private String prepay_id = "";
    private String trade_type = "";

    public WeixinPrePayData(String str) {

        this.return_code = WeixinPayUtil.extract(str, "return_code");
        this.return_msg = WeixinPayUtil.extract(str, "return_msg");
        this.appid = WeixinPayUtil.extract(str, "appid");
        this.mch_id = WeixinPayUtil.extract(str, "mch_id");
        this.nonce_str = WeixinPayUtil.extract(str, "nonce_str");
        this.sign = WeixinPayUtil.extract(str, "sign");
        this.result_code = WeixinPayUtil.extract(str, "result_code");
        this.prepay_id = WeixinPayUtil.extract(str, "prepay_id");
        this.trade_type = WeixinPayUtil.extract(str, "trade_type");

    }

    @Override
    public String toString() {
        return "WeixinPayData [return_code=" + return_code + ", return_msg=" + return_msg + ", appid=" + appid + ", mch_id="
                + mch_id + ", nonce_str=" + nonce_str + ", sign=" + sign + ", result_code=" + result_code
                + ", prepay_id=" + prepay_id + ", trade_type=" + trade_type + "]";
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

}
