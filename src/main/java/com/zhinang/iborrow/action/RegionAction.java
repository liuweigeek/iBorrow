package com.zhinang.iborrow.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.Region;
import com.zhinang.iborrow.service.RegionService;
import com.zhinang.iborrow.util.PageUtil;
import com.zhinang.iborrow.util.PropertyUtil;
import com.zhinang.iborrow.util.ResponseUtil;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class RegionAction extends ActionSupport implements ModelDriven<Region>, ServletRequestAware {
    private static final long serialVersionUID = 1L;
    private Region region = new Region();

    private List<Region> regionList;

    private int regionId;

    @Resource
    private RegionService regionService;

    private int currentPage;
    private int totalPage;
    private String pageCode;

    private HttpServletRequest request;

    @Override
    public Region getModel() {
        return region;
    }

    public List<Region> getRegionList() {
        return regionList;
    }

    public void setRegionList(List<Region> regionList) {
        this.regionList = regionList;
    }

    public int getRegionId() {
        return regionId;
    }

    public void setRegionId(int regionId) {
        this.regionId = regionId;
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

    public void setRegionService(RegionService regionService) {
        this.regionService = regionService;
    }

    public String list() throws Exception {
        int pageSize = Integer.parseInt(PropertyUtil.getPropertyByName2("constant.properties", "adminpagesize"));
        if (currentPage == 0) {
            currentPage = 1;
        }
        PageBean pageBean = new PageBean(currentPage, pageSize);
        long count = regionService.getRegionCount(region);
        totalPage = (int) (count / pageSize);
        if ((count % pageSize) > 0) {
            totalPage++;
        }
        pageCode = PageUtil.getPagination(request.getContextPath() + "/Region_list.action", count, currentPage,
                pageSize, null);

        regionList = regionService.findRegionList(null, pageBean);
        return null;
    }

    public String listByParentId() throws Exception {
        JSONObject result = new JSONObject();
        List<Region> regions = regionService.findRegionByParentId(regionId);
        result.put("regions", regions);
        ResponseUtil.write(ServletActionContext.getResponse(), result);
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