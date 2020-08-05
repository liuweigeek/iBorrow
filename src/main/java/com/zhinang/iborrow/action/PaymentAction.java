package com.zhinang.iborrow.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zhinang.iborrow.constant.Constant;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.Payment;
import com.zhinang.iborrow.entity.User;
import com.zhinang.iborrow.entity.WeixinPayData;
import com.zhinang.iborrow.entity.WeixinPrePayData;
import com.zhinang.iborrow.service.PaymentService;
import com.zhinang.iborrow.service.UserService;
import com.zhinang.iborrow.util.PageUtil;
import com.zhinang.iborrow.util.PropertyUtil;
import com.zhinang.iborrow.util.ResponseUtil;
import com.zhinang.iborrow.util.StringUtil;
import com.zhinang.iborrow.util.UserUtil;
import com.zhinang.iborrow.util.WeixinPayUtil;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PaymentAction extends ActionSupport implements ModelDriven<Payment>, ServletRequestAware {
    private static final long serialVersionUID = 1L;

    private Payment payment = new Payment();

    private Float money;
    private String bodyStr;
    private Integer payType;

    private int vipCardDay;

    private int currentPage;
    private int totalPage;
    private String pageCode;

    private String keyword;
    private String timeranger;

    @Resource
    private UserService userService;
    @Resource
    private PaymentService paymentService;

    private List<Payment> paymentList = new ArrayList<>();

    private String mainPage;

    private HttpServletRequest request;

    @Override
    public Payment getModel() {
        return this.payment;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public String getMainPage() {
        return mainPage;
    }

    public void setMainPage(String mainPage) {
        this.mainPage = mainPage;
    }

    public Float getMoney() {
        return money;
    }

    public void setMoney(Float money) {
        this.money = money;
    }

    public String getBodyStr() {
        return bodyStr;
    }

    public void setBodyStr(String bodyStr) {
        this.bodyStr = bodyStr;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public int getVipCardDay() {
        return vipCardDay;
    }

    public void setVipCardDay(int vipCardDay) {
        this.vipCardDay = vipCardDay;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getPageCode() {
        return pageCode;
    }

    public void setPageCode(String pageCode) {
        this.pageCode = pageCode;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getTimeranger() {
        return timeranger;
    }

    public void setTimeranger(String timeranger) {
        this.timeranger = timeranger;
    }

    public String delete() throws Exception {
        return null;
    }

    public String deleteList() throws Exception {
        return null;
    }

    public String list() throws Exception {

        if (StringUtil.isNotEmpty(keyword)) {
            User e = new User();
            e.setNickname(keyword);
            payment.setUser(e);
            payment.setTransaction_id(keyword);
            payment.setOut_trade_no(keyword);
        }

        if (StringUtil.isNotEmpty(timeranger)) {
            String[] timeStartEnd = timeranger.split(" - ");

            if (timeStartEnd.length == 2) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(timeStartEnd[0]);
                payment.setTime_start(date);
                date = sdf.parse(timeStartEnd[1]);
                payment.setTime_expire(date);
            }
        }

        int pageSize = Integer.parseInt(PropertyUtil.getPropertyByName2("constant.properties", "adminpagesize"));
        if (currentPage == 0) {
            currentPage = 1;
        }
        PageBean pageBean = new PageBean(currentPage, pageSize);
        long count = paymentService.getPaymentCount(payment);
        totalPage = (int) (count / pageSize);
        if ((count % pageSize) > 0) {
            totalPage++;
        }
        String newParams = (StringUtil.isNotEmpty(keyword) ? "&keyword=" + keyword : "")
                + (StringUtil.isNotEmpty(timeranger) ? "&timeranger=" + timeranger : "");
        pageCode = PageUtil.getPagination(request.getContextPath() + "/Payment_list.action", count, currentPage,
                pageSize, newParams.replaceFirst("&", ""));

        paymentList = paymentService.findPaymentList(payment, pageBean);
        mainPage = "payment.jsp";
        return "list";
    }

    public String showList() throws Exception {
        User currentUser = UserUtil.getUserFromSession(request);
        paymentList.addAll(currentUser.getPayments());

        return "show_list";
    }

    public String buy() throws Exception {
        User currentUser = UserUtil.getUserFromSession(request);

        JSONObject result = new JSONObject();

        boolean isSuccess = false;

        if (payType == Constant.PayType.DEPOSIT) {
            if (money > 1000) {
                result.put("success", false);
                result.put("errMsg", "请先绑定手机号");
                ResponseUtil.write(ServletActionContext.getResponse(), result);
                return null;
            }
        }

        String nonceStr = WeixinPayUtil.getRandomStr();
        String outTradeNo = WeixinPayUtil.getRandomStr();

        payment.setFinish(false);
        payment.setTotal_fee(BigInteger.valueOf((long) (money * 100)));
        payment.setNonce_str(nonceStr);
        payment.setSpbill_create_ip(ServletActionContext.getRequest().getRemoteAddr());
        payment.setOut_trade_no(outTradeNo);
        /*SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分");*/
        Date time_start = new Date();
        payment.setTime_start(time_start);
        payment.setBody(bodyStr);

        JSONObject attachJson = new JSONObject();
        switch (payType) {
            case Constant.PayType.VIPCARD:
                attachJson.put("payType", Constant.PayType.VIPCARD);
                attachJson.put("day", vipCardDay);
                break;
            case Constant.PayType.DEPOSIT:
                attachJson.put("payType", Constant.PayType.DEPOSIT);
                attachJson.put("money", money);
                break;
            default:
                result.put("success", false);
                result.put("errMsg", "支付类型错误，请稍后重试");
                ResponseUtil.write(ServletActionContext.getResponse(), result);
                return null;
        }
        payment.setAttach(attachJson.toString());

        payment.setUser(currentUser);
        paymentService.savePayment(payment);

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("appid", Constant.weixin.APPID);
        map.put("mch_id", Constant.weixin.WX_PAY_MCH_ID);
        map.put("nonce_str", payment.getNonce_str());
        map.put("body", payment.getBody());
        map.put("out_trade_no", payment.getOut_trade_no());
        map.put("total_fee", payment.getTotal_fee());
        map.put("spbill_create_ip", payment.getSpbill_create_ip());
        map.put("notify_url", Constant.weixin.WX_PAY_NOTIFY_URL);
        map.put("trade_type", "JSAPI");
        map.put("openid", currentUser.getWx_openid());

        WeixinPrePayData weixinPrePayData = WeixinPayUtil.sentPost(map, Constant.weixin.WX_PAY_API_KEY);
        String prepay_id = weixinPrePayData.getPrepay_id();

        isSuccess = StringUtil.isNotEmpty(prepay_id);

        String timeStamp = Long.toString(System.currentTimeMillis());

        map = new HashMap<String, Object>();
        map.put("appId", Constant.weixin.APPID);
        map.put("timeStamp", timeStamp);
        map.put("nonceStr", nonceStr);
        map.put("package", "prepay_id=" + prepay_id);
        map.put("signType", "MD5");

        WeixinPayData weixinPayData = new WeixinPayData(timeStamp, nonceStr,
                WeixinPayUtil.sign(map, Constant.weixin.WX_PAY_API_KEY));

        result.put("success", isSuccess);
        result.put("out_trade_no", payment.getOut_trade_no());
        result.put("appId", Constant.weixin.APPID);
        result.put("timeStamp", weixinPayData.getTimeStamp());
        result.put("nonceStr", weixinPayData.getNonceStr());
        result.put("package", "prepay_id=" + prepay_id);
        result.put("signType", "MD5");
        result.put("paySign", weixinPayData.getPaySign());

        ResponseUtil.write(ServletActionContext.getResponse(), result);

        return null;
    }

	/*public String convert() throws Exception {
	    paymentList = paymentService.findPaymentList(null,  null);
	    for (Payment p : paymentList) {
            if (p.getId() <= 130) {
                if (StringUtil.isNotEmpty(p.getTime_start())) {
                    Date startdate = new Date();
                    startdate.setTime(Long.parseLong(p.getTime_start()));
                    p.setDate_start(startdate);
                }
                if (StringUtil.isNotEmpty(p.getTime_expire())) {
                    Date expiredate = new Date();
                    expiredate.setTime(Long.parseLong(p.getTime_expire()));
                    p.setDate_expire(expiredate);
                }
                paymentService.savePayment(p);
            }
        }
        return SUCCESS;
    }*/

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}