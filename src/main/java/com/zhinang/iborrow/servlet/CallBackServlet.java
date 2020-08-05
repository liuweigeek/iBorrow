package com.zhinang.iborrow.servlet;

import com.zhinang.iborrow.entity.User;
import com.zhinang.iborrow.service.UserService;
import com.zhinang.iborrow.util.AuthUtil;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/callBack"})
public class CallBackServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Resource
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String code = request.getParameter("code");
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wxf90507f75ee1c390&secret=d49f8155064795ed7778767939cc1865&code="
                + code + "&grant_type=authorization_code";
        JSONObject jsonObject = AuthUtil.doGetJson(url);
        String openid = jsonObject.getString("openid");
        String token = jsonObject.getString("access_token");
        String infoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + token + "&openid=" + openid
                + "&lang=zh_CN";
        JSONObject userInfo = AuthUtil.doGetJson(infoUrl);
        if (!userIsExist(userInfo)) {
            register(userInfo);
        }
        request.setAttribute("wxUserInfo", userInfo);
        request.getRequestDispatcher("/loginSuccess.jsp").forward(request, response);
    }

    public boolean userIsExist(JSONObject userInfo) {
        return this.userService.existUserWithWeixinOpenId(userInfo.getString("openid"));
    }

    public void register(JSONObject userInfo) {
        User user = new User();
        this.userService.saveUser(user);
    }

}