package com.zhinang.iborrow.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zhinang.iborrow.entity.VipOrder;
import com.zhinang.iborrow.service.VipOrderService;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class VipOrderAction extends ActionSupport implements ModelDriven<VipOrder>, ServletRequestAware {
    private static final long serialVersionUID = 1L;
    private VipOrder vipOrder = new VipOrder();

    @Resource
    private VipOrderService vipOrderService;

    private int currentPage;
    private int totalPage;
    private String pageCode;

    @SuppressWarnings("unused")
    private HttpServletRequest request;

    @Override
    public VipOrder getModel() {
        return this.vipOrder;
    }

    public void setVipOrderService(VipOrderService vipOrderService) {
        this.vipOrderService = vipOrderService;
    }

    public String list() throws Exception {
        return null;
    }

    public String delete() throws Exception {
        return null;
    }

    public String deleteList() throws Exception {
        return null;
    }

    public String save() throws Exception {
        return null;
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

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}