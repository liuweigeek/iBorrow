package com.zhinang.iborrow.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zhinang.iborrow.entity.VipIntegral;
import com.zhinang.iborrow.service.VipIntegralService;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.stereotype.Controller;

@Controller
public class VipIntegralAction extends ActionSupport implements ModelDriven<VipIntegral>, ServletRequestAware {
	private static final long serialVersionUID = 1L;
	private VipIntegral vipIntegral = new VipIntegral();

	@Resource
	private VipIntegralService vipIntegralService;
	
	private int currentPage;
	private int totalPage;
	private String pageCode;
	
	@SuppressWarnings("unused")
	private HttpServletRequest request;

	@Override
    public VipIntegral getModel() {
		return this.vipIntegral;
	}

	public void setVipIntegralService(VipIntegralService vipIntegralService) {
		this.vipIntegralService = vipIntegralService;
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

	@Override
    public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
}