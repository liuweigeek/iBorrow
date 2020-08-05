package com.zhinang.iborrow.constant;

import com.zhinang.iborrow.util.PropertyUtil;

import java.io.File;

public class Constant {

    public static final String IMG_PATH = PropertyUtil.getPropertyByName2("constant.properties", "upload_disk")
            + ":" + File.separator + "iborrow"
            + File.separator + "images"
            + File.separator;

    //存储键值对
    public interface key_value {

        String CURRENT_USER = "currentUser";
        String CURRENT_ADMIN = "currentAdmin";
        String USER_TYPE = "userType";
        String ADMIN_NICKNAME = "adminNickname";
        String ADMIN_PHONE = "adminPhone";
        String ADMIN_HEADIMG = "adminHeadImg";

        int BORROW_LIMIT = 5;

        String FROM_URL = "fromUrl";
    }

    //用户类型
    public interface userType {

        int ADMIN = 101;    //超级管理员
        int LIBRARIAN = 102;    //图书管理员
        int FREE_ACCOUNT = 103;    //免费用户
        int ONLINE_VIP = 104;    //线上会员
        int OFFLINE_VIP = 105;    //线下会员
        int UNIVERSAL_VIP = 106;    //通用会员
    }

    //产品类型
    public interface productType {

        int BOOK = 201;    //书籍
        int FANDOU = 202;    //凡豆机器人
        int TALKING_PEN = 203;    //点读笔
        int BOTH = 204;    //凡豆机器人与点读笔皆支持
    }

    //装订类型
    public interface bingding {

        int PAPERBACK = 250;    //平装
        int HARDCOVER = 251;    //精装
    }

    //订单类型
    public interface OrderType {

        int BORROW = 301;    //借阅订单
        int REFUND = 302;    //退还订单
    }

    public interface PayType {
        int VIPCARD = 501;
        int DEPOSIT = 502;
    }

    //订单状态
    public interface OrderStatus {

        int UNPAID = 401;    //未支付
        int UNSHIPPED = 402;    //未发货
        int RECEIVE = 403;    //已收货
        int ON_THE_WAY = 404;    //已发货（在途中）
        int ORDER_FINISH = 405;    //订单流程全部完成
    }

    //微信
    public interface weixin {

        String TOKEN = "iborrow";    //微信口令

        String WX_PAY_MCH_ID = "xxxxxx";    //微信支付商户ID
        String WX_PAY_API_KEY = "xxxxxxxxxxxxxxxx";    //微信支付API Key

        String WX_LOGIN_CALLBACK_URL = "http://xxx.com";
        String WX_PAY_NOTIFY_URL = "http://xxx.com/weixinPayNotify";
        String APPID = "xxxxxx";
        String APPSECRET = "xxxxxxxxxxxxxxxx";
    }

}