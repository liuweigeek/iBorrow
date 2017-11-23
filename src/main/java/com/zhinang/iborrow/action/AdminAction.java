package com.zhinang.iborrow.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zhinang.iborrow.constant.Constant;
import com.zhinang.iborrow.entity.User;
import com.zhinang.iborrow.service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.stereotype.Controller;

@Controller
public class AdminAction extends ActionSupport implements ModelDriven<User>, ServletRequestAware {
	private static final long serialVersionUID = 1L;
	private User user = new User();

	@Resource
	private UserService userService;
	
	private HttpServletRequest request;
	
	@Override
	public User getModel() {
		return user;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public String execute() {
		HttpSession session = request.getSession();
		if (null != session.getAttribute(Constant.key_value.CURRENT_ADMIN)) {
			int adminId = (int) session.getAttribute(Constant.key_value.CURRENT_ADMIN);			
			User currentAdmin = userService.findUserById(adminId);
			if (currentAdmin != null &&
                    (currentAdmin.getType() == Constant.userType.ADMIN ||
                        currentAdmin.getType() == Constant.userType.LIBRARIAN)) {
				return SUCCESS;
			} else {
				return INPUT;
			}
		} else {
			return INPUT;
		}
	}
	
	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
}