package com.zhinang.iborrow.action;

import com.opensymphony.xwork2.ActionSupport;
import com.zhinang.iborrow.constant.Constant;
import com.zhinang.iborrow.entity.Payment;
import com.zhinang.iborrow.entity.User;
import com.zhinang.iborrow.service.PaymentService;
import com.zhinang.iborrow.service.UserService;
import com.zhinang.iborrow.util.StringUtil;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Controller
public class WeiXinPayNotifyAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {
    private static final long serialVersionUID = 1L;

    private HttpServletRequest request;
    private HttpServletResponse response;

    @Resource
    private PaymentService paymentService;
    @Resource
    private UserService userService;

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String execute() throws Exception {

        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = null;
        try {
            ServletInputStream servletInputStream = request.getInputStream(); // 获取网络上的流数据
            inputStreamReader = new InputStreamReader(servletInputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            stringBuilder = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) { // 从流里读取全部数据
                stringBuilder.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bufferedReader.close();
            inputStreamReader.close();
        }

        XMLSerializer xmlSerializer = new XMLSerializer(); // 把接受到的xml转换成json，需要xom.jar支持
        JSON json = xmlSerializer.read(stringBuilder + "");
        // System.out.println(json.toString(1));

        JSONObject jsonObject = ((JSONObject) json);
        String result_code = jsonObject.getString("result_code");
        if (result_code.equals("SUCCESS")) { // 交易成功后执行的
            String xml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                    + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml>";
            response.setCharacterEncoding("UTF-8"); // 以utf8的编码方式返回给调用者。

			/*response.getWriter().print(
					"<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg></xml>");*/

            /**
             * 注意：在struts2中要返回xml，要注意一下2点。 1、return 不能返回任何参数，例如：SUCCESS
             * 2、在struts2的配置文件中，action中不能有任何的参数
             */
            PrintWriter writer = response.getWriter();
            writer.write(xml);
            writer.close();

            Payment payment = paymentService.findPaymentByoutTradeNo(jsonObject.getString("out_trade_no"));
            if (!payment.getFinish()) {
                payment.setFinish(true);
                payment.setFee_type(jsonObject.getString("fee_type"));
                payment.setSign(jsonObject.getString("sign"));
                payment.setTrade_type(jsonObject.getString("trade_type"));
                payment.setTransaction_id(jsonObject.getString("transaction_id"));
                payment.setBankType(jsonObject.getString("bank_type"));
                Date time_expire = new SimpleDateFormat("yyyyMMddHHmmss").parse(jsonObject.getString("time_end"));
                payment.setTime_expire(time_expire);

                paymentService.savePayment(payment);

                JSONObject attachJson = JSONObject.fromObject(payment.getAttach());

                int payType = attachJson.getInt("payType");
                switch (payType) {
                    case Constant.PayType.VIPCARD:
                        int day = attachJson.getInt("day");
                        activateVipCard(payment.getUser(), day);
                        break;
                    case Constant.PayType.DEPOSIT:
                        int money = attachJson.getInt("money");
                        rechargeDeposit(payment.getUser(), money);
                        break;
                }
            }

        } else { // 交易失败后执行的
            System.out.println("支付失败");
        }

        return null;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }

    private void activateVipCard(User user, int day) {

        User currentUser = userService.findUserById(user.getId());

        Date openTime = new Date();
        currentUser.setOpenTime(openTime);
        Date expireTime = currentUser.getExpirationTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(expireTime);
        if (System.currentTimeMillis() > calendar.getTimeInMillis()) {
            calendar.setTimeInMillis(System.currentTimeMillis());
        }
        calendar.add(Calendar.DAY_OF_YEAR, day);
        currentUser.setExpirationTime(calendar.getTime());
        if (StringUtil.isEmpty(currentUser.getVipId())) {
            currentUser.setVipId(createVipId());
        }
        userService.saveUser(currentUser);

        String endTime = new SimpleDateFormat("yyyy年MM月dd日").format(currentUser.getExpirationTime());

/*		if (null == currentUser.getReferee() || null == currentUser.getReferee().getId()) {
			result.put("isFirst", true);
		} else {
			result.put("isFirst", false);
		}*/
    }

    private String createVipId() {
        String vipId;
        do {
            vipId = StringUtil.getRandomStringVipId();
        } while (vipIdIsExist(vipId));

        return vipId;
    }

    //判断VipId是否已存在注册
    private boolean vipIdIsExist(String vipId) {
        return userService.existUserWithVipId(vipId);
    }

    //充值押金
    private void rechargeDeposit(User user, int money) {
        User currentUser = userService.findUserById(user.getId());

        currentUser.setDeposit(currentUser.getDeposit() + money);
        userService.saveUser(currentUser);

    }

    @Override
    public void setServletResponse(HttpServletResponse response) {
        this.response = response;
    }
}