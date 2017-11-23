package com.zhinang.iborrow.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.zhinang.iborrow.constant.Constant;

public class UserLoginInterceptor extends MethodFilterInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		
		ActionContext ctx = invocation.getInvocationContext();
		Map<String, Object> session = ctx.getSession();
		if (session.containsKey(Constant.key_value.CURRENT_USER)) {
			return invocation.invoke();
		}
		
		/*HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		System.out.println("doIntercept...............");
		if (session != null) {
			User user = UserUtil.getUserFromSession(request);
			if (user != null && user.getId() != null) {
				return invocation.invoke();
			}
		}*/
		
		return Action.LOGIN;
	}
}
