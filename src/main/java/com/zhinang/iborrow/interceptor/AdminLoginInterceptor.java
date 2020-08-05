package com.zhinang.iborrow.interceptor;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.zhinang.iborrow.constant.Constant;

import java.util.Map;

public class AdminLoginInterceptor extends MethodFilterInterceptor {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected String doIntercept(ActionInvocation invocation) throws Exception {

        ActionContext ctx = invocation.getInvocationContext();
        Map<String, Object> session = ctx.getSession();
        if (session.containsKey(Constant.key_value.CURRENT_ADMIN)) {
            return invocation.invoke();
        }

        return "adminLogin";
    }
}
