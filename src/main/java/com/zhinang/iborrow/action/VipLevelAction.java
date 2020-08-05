package com.zhinang.iborrow.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zhinang.iborrow.entity.VipLevel;
import com.zhinang.iborrow.service.VipLevelService;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class VipLevelAction extends ActionSupport implements ModelDriven<VipLevel>, ServletRequestAware {
    private static final long serialVersionUID = 1L;
    private VipLevel vipLevel = new VipLevel();

    @Resource
    private VipLevelService vipLevelService;

    private int currentPage;
    private int totalPage;
    private String pageCode;

    @SuppressWarnings("unused")
    private HttpServletRequest request;

    @Override
    public VipLevel getModel() {
        return this.vipLevel;
    }

    public void setVipLevelService(VipLevelService vipLevelService) {
        this.vipLevelService = vipLevelService;
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