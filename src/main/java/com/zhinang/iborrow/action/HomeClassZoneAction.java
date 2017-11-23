package com.zhinang.iborrow.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zhinang.iborrow.entity.HomeClassZone;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.service.HomeClassZoneService;
import com.zhinang.iborrow.util.PropertyUtil;
import com.zhinang.iborrow.util.PageUtil;
import com.zhinang.iborrow.util.ResponseUtil;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.zhinang.iborrow.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.stereotype.Controller;

@Controller
public class HomeClassZoneAction extends ActionSupport implements ModelDriven<HomeClassZone>, ServletRequestAware {
	private static final long serialVersionUID = 1L;
	private HomeClassZone homeClassZone = new HomeClassZone();

	@Resource
	private HomeClassZoneService homeClassZoneService;

	private HttpServletRequest request;
	private String mainPage;
	private int homeClassZoneId;
	private String ids;
	private List<HomeClassZone> homeClassZoneList = new ArrayList<HomeClassZone>();

    private String keyword;

	private int currentPage;
	private int totalPage;
	private String pageCode;

	@Override
    public HomeClassZone getModel() {
		return this.homeClassZone;
	}

	public String getMainPage() {
		return this.mainPage;
	}

	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}

	public int getHomeClassZoneId() {
		return this.homeClassZoneId;
	}

	public void setHomeClassZoneId(int homeClassZoneId) {
		this.homeClassZoneId = homeClassZoneId;
	}

	public String getIds() {
		return this.ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public List<HomeClassZone> getHomeClassZoneList() {
		return this.homeClassZoneList;
	}

	public void setHomeClassZoneList(List<HomeClassZone> homeClassZoneList) {
		this.homeClassZoneList = homeClassZoneList;
	}

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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

        if (StringUtil.isNotEmpty(keyword)) {
            homeClassZone.setName(keyword);
        }

		int pageSize = Integer.parseInt(PropertyUtil.getPropertyByName2("constant.properties", "adminpagesize"));
		if (currentPage == 0) {
			currentPage = 1;
		}
		PageBean pageBean = new PageBean(currentPage, pageSize);
		long count = homeClassZoneService.getHomeClassZoneCount(homeClassZone);
		totalPage = (int) (count / pageSize);
		if ((count % pageSize) > 0) {
			totalPage++;
		}
		pageCode = PageUtil.getPagination(request.getContextPath() + "/HomeClassZone_list.action", count, currentPage,
				pageSize, StringUtil.isNotEmpty(keyword) ? "keyword=" + keyword : "");

		homeClassZoneList = homeClassZoneService.findHomeClassZoneList(homeClassZone, pageBean);
		mainPage = "home_class_zone.jsp";
		return "list";
	}

	public String delete() throws Exception {
		JSONObject result = new JSONObject();
		HomeClassZone e = homeClassZoneService.findHomeClassZoneById(homeClassZoneId);
		homeClassZoneService.deleteHomeClassZone(e);
		refreshHomeData();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

	public String deleteList() throws Exception {
		JSONObject result = new JSONObject();
		String[] idsStr = ids.split(",");
		for (int i = 0; i < idsStr.length; i++) {
			HomeClassZone e = homeClassZoneService.findHomeClassZoneById(Integer.parseInt(idsStr[i]));
			homeClassZoneService.deleteHomeClassZone(e);
		}
		refreshHomeData();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

	public String save() throws Exception {
		homeClassZoneService.saveHomeClassZone(homeClassZone);
		refreshHomeData();
		return "success";
	}

	public void refreshHomeData() {
		InitAction.refreshHomeClassZone();
	}

	@Override
    public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
}