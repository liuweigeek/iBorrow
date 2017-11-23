package com.zhinang.iborrow.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.ProductType;
import com.zhinang.iborrow.service.ProductTypeService;
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
public class ProductTypeAction extends ActionSupport implements ModelDriven<ProductType>, ServletRequestAware {
	private static final long serialVersionUID = 1L;
	private ProductType productType = new ProductType();

	@Resource
	private ProductTypeService productTypeService;

	private HttpServletRequest request;
	private String mainPage;
	private int productTypeId;
	private String keyword;
	private String ids;
	private List<ProductType> productTypeList = new ArrayList<ProductType>();
	
	private int currentPage;
	private int totalPage;
	private String pageCode;

	@Override
    public ProductType getModel() {
		return this.productType;
	}

	public String getMainPage() {
		return this.mainPage;
	}

	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}

	public int getProductTypeId() {
		return this.productTypeId;
	}

	public void setProductTypeId(int productTypeId) {
		this.productTypeId = productTypeId;
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

	public List<ProductType> getProductTypeList() {
		return this.productTypeList;
	}

	public void setProductTypeList(List<ProductType> productTypeList) {
		this.productTypeList = productTypeList;
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
            productType.setTitle(keyword);
        }

		int pageSize = Integer.parseInt(PropertyUtil.getPropertyByName2("constant.properties", "adminpagesize"));
		if (currentPage == 0) {
			currentPage = 1;
		}
		PageBean pageBean = new PageBean(currentPage, pageSize);
		long count = productTypeService.getProductTypeCount(productType);
		totalPage = (int) (count / pageSize);
		if ((count % pageSize) > 0) {
			totalPage++;
		}
		pageCode = PageUtil.getPagination(request.getContextPath() + "/ProductType_list.action", count, currentPage,
				pageSize, StringUtil.isNotEmpty(keyword) ? "keyword=" + keyword : "");

		productTypeList = productTypeService.findProductTypeList(productType, pageBean);
		mainPage = "product_type.jsp";
		return "list";
	}

	public String listByKeyword() throws Exception {
		JSONObject result = new JSONObject();
		List<ProductType> types = productTypeService.findProductTypeByKeyWord(keyword);
		
		for (int i = 0; i < types.size(); i++) {
			types.get(i).setProducts(null);
		}
		
		result.put("types", types);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

	public String delete() throws Exception {
		JSONObject result = new JSONObject();
		ProductType e = productTypeService.findProductTypeById(productTypeId);
		productTypeService.deleteProductType(e);
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

	public String deleteList() throws Exception {
		JSONObject result = new JSONObject();
		String[] idsStr = ids.split(",");
		for (int i = 0; i < idsStr.length; i++) {
			ProductType e = productTypeService.findProductTypeById(Integer.parseInt(idsStr[i]));
			productTypeService.deleteProductType(e);
		}
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

	public String save() throws Exception {
		productTypeService.saveProductType(productType);
		return "success";
	}

	@Override
    public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
}