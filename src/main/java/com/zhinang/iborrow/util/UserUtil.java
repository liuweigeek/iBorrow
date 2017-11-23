package com.zhinang.iborrow.util;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

import com.zhinang.iborrow.constant.Constant;
import com.zhinang.iborrow.entity.User;
import com.zhinang.iborrow.service.UserService;

@Component
public class UserUtil {
	
	@Resource
	private UserService userService;
	private static UserUtil userUtil;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@PostConstruct
	public void init() {
		userUtil = this;
		userUtil.userService = this.userService;
	}

	public static User getUserFromSession(HttpServletRequest request) {
		/*return GetUserAction.getUserFromSession(request);*/
		HttpSession session = request.getSession();
		int userId = (int) session.getAttribute(Constant.key_value.CURRENT_USER);
		return userUtil.userService.findUserById(userId);
	}

    public static User getAdminFromSession(HttpServletRequest request) {
		/*return GetUserAction.getUserFromSession(request);*/
        HttpSession session = request.getSession();
        int userId = (int) session.getAttribute(Constant.key_value.CURRENT_ADMIN);
        return userUtil.userService.findUserById(userId);
    }
	
}
