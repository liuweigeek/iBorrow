package com.zhinang.iborrow.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zhinang.iborrow.entity.HomeZone;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.Product;
import com.zhinang.iborrow.service.HomeZoneService;
import com.zhinang.iborrow.service.ProductService;
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
public class HomeZoneAction extends ActionSupport implements ModelDriven<HomeZone>, ServletRequestAware {
	private static final long serialVersionUID = 1L;
	private HomeZone homeZone = new HomeZone();

	@Resource
	private HomeZoneService homeZoneService;
	@Resource
    private ProductService productService;

	private HttpServletRequest request;
	private String mainPage;
	private int homeZoneId;
    private String keyword;
	private String ids;
	private List<HomeZone> homeZoneList = new ArrayList<HomeZone>();
	
	private int currentPage;
	private int totalPage;
	private String pageCode;

	@Override
    public HomeZone getModel() {
		return this.homeZone;
	}

    public void setHomeZoneService(HomeZoneService homeZoneService) {
        this.homeZoneService = homeZoneService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public String getMainPage() {
		return this.mainPage;
	}

	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}

	public int getHomeZoneId() {
		return this.homeZoneId;
	}

	public void setHomeZoneId(int homeZoneId) {
		this.homeZoneId = homeZoneId;
	}

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getIds() {
		return this.ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public List<HomeZone> getHomeZoneList() {
		return this.homeZoneList;
	}

	public void setHomeZoneList(List<HomeZone> homeZoneList) {
		this.homeZoneList = homeZoneList;
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
            homeZone.setName(keyword);
        }

		int pageSize = Integer.parseInt(PropertyUtil.getPropertyByName2("constant.properties", "adminpagesize"));
		if (currentPage == 0) {
			currentPage = 1;
		}
		PageBean pageBean = new PageBean(currentPage, pageSize);
		long count = homeZoneService.getHomeZoneCount(homeZone);
		totalPage = (int) (count / pageSize);
		if ((count % pageSize) > 0) {
			totalPage++;
		}
		pageCode = PageUtil.getPagination(request.getContextPath() + "/HomeZone_list.action", count, currentPage,
				pageSize, StringUtil.isNotEmpty(keyword) ? "keyword=" + keyword : "");

		homeZoneList = homeZoneService.findHomeZoneList(homeZone, pageBean);
		mainPage = "home_zone.jsp";
		return "list";
	}

	public String delete() throws Exception {
		JSONObject result = new JSONObject();
		HomeZone e = homeZoneService.findHomeZoneById(homeZoneId);
        List<Product> products = e.getProducts();
        for (Product p : products) {
            p.getHomeZones().remove(e);
            productService.saveProduct(p);
        }
		homeZoneService.deleteHomeZone(e);
		refreshHomeData();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

	public String deleteList() throws Exception {
		JSONObject result = new JSONObject();
		String[] idsStr = ids.split(",");
		for (int i = 0; i < idsStr.length; i++) {
			HomeZone e = homeZoneService.findHomeZoneById(Integer.parseInt(idsStr[i]));
            List<Product> products = e.getProducts();
            for (Product p : products) {
                p.getHomeZones().remove(e);
                productService.saveProduct(p);
            }
			homeZoneService.deleteHomeZone(e);
		}
		refreshHomeData();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

	public String save() throws Exception {
		homeZoneService.saveHomeZone(homeZone);
		refreshHomeData();
		return "success";
	}

	public String listByKeyword() throws Exception {
		JSONObject result = new JSONObject();
		List<HomeZone> homezones = homeZoneService.findHomeZoneByKeyWord(keyword);

		for (int i = 0; i < homezones.size(); i++) {
			homezones.get(i).setProducts(null);
		}

		result.put("homezones", homezones);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

	public void refreshHomeData() {
		InitAction.refreshHomeZone();
	}

	@Override
    public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
}