package com.zhinang.iborrow.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zhinang.iborrow.constant.Constant;
import com.zhinang.iborrow.entity.HomeClassZone;
import com.zhinang.iborrow.entity.HomeClassify;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.Product;
import com.zhinang.iborrow.entity.User;
import com.zhinang.iborrow.service.HomeClassZoneService;
import com.zhinang.iborrow.service.HomeClassifyService;
import com.zhinang.iborrow.service.ProductService;
import com.zhinang.iborrow.service.UserService;
import com.zhinang.iborrow.util.PageUtil;
import com.zhinang.iborrow.util.PropertyUtil;
import com.zhinang.iborrow.util.ResponseUtil;
import com.zhinang.iborrow.util.StringUtil;
import com.zhinang.iborrow.util.UserUtil;
import net.sf.json.JSONObject;
import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeClassifyAction extends ActionSupport implements ModelDriven<HomeClassify>, ServletRequestAware {
    private static final long serialVersionUID = 1L;
    private HomeClassify homeClassify = new HomeClassify();

    @Resource
    private HomeClassifyService homeClassifyService;
    @Resource
    private HomeClassZoneService homeClassZoneService;
    @Resource
    private UserService userService;
    @Resource
    private ProductService productService;

    private HttpServletRequest request;
    private String mainPage;
    private File classifycover;
    private String classifycoverFileName;
    private int homeClassifyId;
    private String keyword;
    private String ids;
    private String pageCode;
    private int currentPage;
    private int totalPage;
    private List<HomeClassify> homeClassifyList = new ArrayList<HomeClassify>();
    private List<HomeClassZone> homeClassZoneList = new ArrayList<HomeClassZone>();
    private List<Product> favoriteList = new ArrayList<>();
    private List<Product> cartList = new ArrayList<>();
    private List<Product> productList = new ArrayList<>();

    //region setter and getter
    @Override
    public HomeClassify getModel() {
        return this.homeClassify;
    }

    public void setHomeClassifyService(HomeClassifyService homeClassifyService) {
        this.homeClassifyService = homeClassifyService;
    }

    public void setHomeClassZoneService(HomeClassZoneService homeClassZoneService) {
        this.homeClassZoneService = homeClassZoneService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public HomeClassify getHomeClassify() {
        return this.homeClassify;
    }

    public void setHomeClassify(HomeClassify homeClassify) {
        this.homeClassify = homeClassify;
    }

    public String getMainPage() {
        return this.mainPage;
    }

    public void setMainPage(String mainPage) {
        this.mainPage = mainPage;
    }

    public File getClassifycover() {
        return this.classifycover;
    }

    public void setClassifycover(File classifycover) {
        this.classifycover = classifycover;
    }

    public String getClassifycoverFileName() {
        return this.classifycoverFileName;
    }

    public void setClassifycoverFileName(String classifycoverFileName) {
        this.classifycoverFileName = classifycoverFileName;
    }

    public int getHomeClassifyId() {
        return this.homeClassifyId;
    }

    public void setHomeClassifyId(int homeClassifyId) {
        this.homeClassifyId = homeClassifyId;
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

    public String getPageCode() {
        return pageCode;
    }

    public void setPageCode(String pageCode) {
        this.pageCode = pageCode;
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

    public List<HomeClassify> getHomeClassifyList() {
        return this.homeClassifyList;
    }

    public void setHomeClassifyList(List<HomeClassify> homeClassifyList) {
        this.homeClassifyList = homeClassifyList;
    }

    public List<HomeClassZone> getHomeClassZoneList() {
        return this.homeClassZoneList;
    }

    public void setHomeClassZoneList(List<HomeClassZone> homeClassZoneList) {
        this.homeClassZoneList = homeClassZoneList;
    }

    public List<Product> getFavoriteList() {
        return favoriteList;
    }

    public void setFavoriteList(List<Product> favoriteList) {
        this.favoriteList = favoriteList;
    }

    public List<Product> getCartList() {
        return cartList;
    }

    public void setCartList(List<Product> cartList) {
        this.cartList = cartList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    //endregion

    public String list() throws Exception {
        if (StringUtil.isNotEmpty(keyword)) {
            homeClassify.setName(keyword);
        }

        int pageSize = Integer.parseInt(PropertyUtil.getPropertyByName2("constant.properties", "adminpagesize"));
        if (currentPage == 0) {
            currentPage = 1;
        }
        PageBean pageBean = new PageBean(currentPage, pageSize);
        long count = homeClassifyService.getHomeClassifyCount(homeClassify);
        totalPage = (int) (count / pageSize);
        if ((count % pageSize) > 0) {
            totalPage++;
        }
        pageCode = PageUtil.getPagination(request.getContextPath() + "/HomeClassify_list.action", count, currentPage,
                pageSize, StringUtil.isNotEmpty(keyword) ? "keyword=" + keyword : "");

        homeClassifyList = homeClassifyService.findHomeClassifyList(homeClassify, pageBean);
        homeClassZoneList = homeClassZoneService.findHomeClassZoneList(null, null);
        mainPage = "home_classify.jsp";
        return "list";
    }

    public String delete() throws Exception {
        JSONObject result = new JSONObject();
        HomeClassify e = homeClassifyService.findHomeClassifyById(homeClassifyId);
        List<Product> products = e.getProducts();
        for (Product p : products) {
            p.getHomeClassifies().remove(e);
            productService.saveProduct(p);
        }
        homeClassifyService.deleteHomeClassify(e);
        refreshHomeData();
        result.put("success", true);
        ResponseUtil.write(ServletActionContext.getResponse(), result);
        return null;
    }

    public String deleteList() throws Exception {
        JSONObject result = new JSONObject();
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            HomeClassify e = homeClassifyService.findHomeClassifyById(Integer.parseInt(idsStr[i]));
            List<Product> products = e.getProducts();
            for (Product p : products) {
                p.getHomeClassifies().remove(e);
                productService.saveProduct(p);
            }
            homeClassifyService.deleteHomeClassify(e);
        }
        refreshHomeData();
        result.put("success", true);
        ResponseUtil.write(ServletActionContext.getResponse(), result);
        return null;
    }

    public String save() throws Exception {
        if (classifycover != null) {
            String imageName = StringUtil.getRandomString();
            String realPath = Constant.IMG_PATH + "homeClassify_cover";
            //String realPath = ServletActionContext.getServletContext().getRealPath("/images/homeClassify_cover");
            String imageFile = imageName + classifycoverFileName
                    .substring(classifycoverFileName.lastIndexOf("."), classifycoverFileName.length());
            File saveFile = new File(realPath, imageFile);
            FileUtils.copyFile(classifycover, saveFile);
            homeClassify.setCover("/iborrow/images/homeClassify_cover/" + imageFile);
        } else if ((homeClassify.getId() != null) && (homeClassify.getId() != 0)) {
            HomeClassify e = homeClassifyService.findHomeClassifyById(homeClassify.getId());
            homeClassify.setCover(e.getCover());
        } else {
            homeClassify.setCover(null);
        }

        homeClassifyService.saveHomeClassify(homeClassify);
        refreshHomeData();
        return "success";
    }

    public String show() throws Exception {
        User currentUser = UserUtil.getUserFromSession(request);
        cartList.addAll(currentUser.getCartProducts());
        favoriteList.addAll(currentUser.getFavoriteProducts());
        int pageSize = Integer.parseInt(PropertyUtil.getPropertyByName2("constant.properties", "pagesize"));
        if (currentPage == 0) {
            currentPage = 1;
        }
        PageBean pageBean = new PageBean(currentPage, pageSize);
        homeClassify = homeClassifyService.findHomeClassifyById(homeClassifyId);

        long count = productService.getProductCountByHomeClassify(homeClassify);
        totalPage = (int) (count / pageSize);
        if ((count % pageSize) > 0) {
            totalPage++;
        }
        productList = productService.findProductListByHomeClassify(homeClassify, pageBean);


		/*homeClassify.getHomeClassItems().clear();
		homeClassify.getHomeClassItems().addAll(homeClassItemList);*/
		/*for (int i = 0; i < homeClassItemList.size(); i++) {
			homeClassify.getHomeClassItems().add(homeClassItemList.get(i));
		}*/

        return "show";
    }

    public String listByKeyword() throws Exception {
        JSONObject result = new JSONObject();
        List<HomeClassify> homeClassifies = homeClassifyService.findHomeClassifyByKeyWord(keyword);

        for (int i = 0; i < homeClassifies.size(); i++) {
            homeClassifies.get(i).setProducts(null);
            homeClassifies.get(i).setHomeClassZone(null);
        }

        result.put("homeClassifies", homeClassifies);
        ResponseUtil.write(ServletActionContext.getResponse(), result);
        return null;
    }

    public void refreshHomeData() {
        InitAction.refreshHomeClassZone();
        ;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}