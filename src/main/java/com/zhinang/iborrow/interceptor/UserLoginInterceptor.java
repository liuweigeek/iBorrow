package com.zhinang.iborrow.interceptor;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.zhinang.iborrow.constant.Constant;
import com.zhinang.iborrow.util.StringUtil;
import org.apache.struts2.StrutsStatics;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

public class UserLoginInterceptor extends MethodFilterInterceptor {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected String doIntercept(ActionInvocation invocation) throws Exception {

        ActionContext ctx = invocation.getInvocationContext();
        Map<String, Object> sessionMap = ctx.getSession();
        if (sessionMap.containsKey(Constant.key_value.CURRENT_USER)) {
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
        HttpServletRequest request = (HttpServletRequest) ctx.get(StrutsStatics.HTTP_REQUEST);
        String fromUrl = request.getRequestURI() + (StringUtil.isNotEmpty(request.getQueryString()) ? "?" + request.getQueryString() : "");

        HttpSession session = request.getSession();
        session.setAttribute(Constant.key_value.FROM_URL, fromUrl);
		/*String actionName = invocation.getInvocationContext().getName();
		Map parameters = invocation.getInvocationContext().getParameters();*/
        return Action.LOGIN;
    }
}
