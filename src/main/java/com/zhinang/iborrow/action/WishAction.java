package com.zhinang.iborrow.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.User;
import com.zhinang.iborrow.entity.Wish;
import com.zhinang.iborrow.service.UserService;
import com.zhinang.iborrow.service.WishService;
import com.zhinang.iborrow.util.PageUtil;
import com.zhinang.iborrow.util.PropertyUtil;
import com.zhinang.iborrow.util.ResponseUtil;
import com.zhinang.iborrow.util.StringUtil;
import com.zhinang.iborrow.util.UserUtil;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WishAction extends ActionSupport implements ModelDriven<Wish>, ServletRequestAware {
    private static final long serialVersionUID = 1L;
    private Wish wish = new Wish();


    @Resource
    private WishService wishService;
    @Resource
    private UserService userService;

    private String mainPage;
    private String wishText;
    private String wishPhone;

    private List<Wish> wishList = new ArrayList<Wish>();

    private String keyword;

    private int currentPage;
    private int totalPage;
    private String pageCode;

    private HttpServletRequest request;

    @Override
    public Wish getModel() {
        return this.wish;
    }

    public void setWishService(WishService wishService) {
        this.wishService = wishService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public String getMainPage() {
        return mainPage;
    }

    public void setMainPage(String mainPage) {
        this.mainPage = mainPage;
    }

    public String getWishText() {
        return wishText;
    }

    public void setWishText(String wishText) {
        this.wishText = wishText;
    }

    public String getWishPhone() {
        return wishPhone;
    }

    public void setWishPhone(String wishPhone) {
        this.wishPhone = wishPhone;
    }

    public WishService getWishService() {
        return wishService;
    }

    public List<Wish> getWishList() {
        return wishList;
    }

    public void setWishList(List<Wish> wishList) {
        this.wishList = wishList;
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
            wish.setText(keyword);
        }

        int pageSize = Integer.parseInt(PropertyUtil.getPropertyByName2("constant.properties", "adminpagesize"));
        if (currentPage == 0) {
            currentPage = 1;
        }
        PageBean pageBean = new PageBean(currentPage, pageSize);
        long count = wishService.getWishCount(wish);
        totalPage = (int) (count / pageSize);
        if ((count % pageSize) > 0) {
            totalPage++;
        }
        pageCode = PageUtil.getPagination(request.getContextPath() + "/Wish_list.action", count, currentPage,
                pageSize, StringUtil.isNotEmpty(keyword) ? "keyword=" + keyword : "");

        wishList = wishService.findWishList(null, pageBean);
        mainPage = "wish.jsp";
        return "list";
    }

    public String delete() throws Exception {
        return null;
    }

    public String deleteList() throws Exception {
        return null;
    }

    public String save() throws Exception {
        User currentUser = UserUtil.getUserFromSession(request);

        wish.setUser(currentUser);
        wish.setPhone(wishPhone);
        wish.setText(wishText);
        wishService.saveWish(wish);

        JSONObject result = new JSONObject();
        result.put("success", true);
        ResponseUtil.write(ServletActionContext.getResponse(), result);
        return null;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}