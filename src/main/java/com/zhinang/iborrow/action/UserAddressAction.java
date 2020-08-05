package com.zhinang.iborrow.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.Region;
import com.zhinang.iborrow.entity.User;
import com.zhinang.iborrow.entity.UserAddress;
import com.zhinang.iborrow.service.RegionService;
import com.zhinang.iborrow.service.UserAddressService;
import com.zhinang.iborrow.service.UserService;
import com.zhinang.iborrow.util.PageUtil;
import com.zhinang.iborrow.util.PropertyUtil;
import com.zhinang.iborrow.util.ResponseUtil;
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
public class UserAddressAction extends ActionSupport implements ModelDriven<UserAddress>, ServletRequestAware {

    private static final long serialVersionUID = 1L;

    private UserAddress userAddress = new UserAddress();

    @Resource
    private UserAddressService userAddressService;
    @Resource
    private UserService userService;
    @Resource
    private RegionService regionService;

    private int userAddressId;
    private String mainPage;

    private List<UserAddress> userAddressList = new ArrayList<>();

    private Integer isFirstChoice;

    private int currentPage;
    private int totalPage;
    private String pageCode;

    private HttpServletRequest request;

    @Override
    public UserAddress getModel() {
        return userAddress;
    }

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }

    public void setUserAddressService(UserAddressService userAddressService) {
        this.userAddressService = userAddressService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public int getUserAddressId() {
        return userAddressId;
    }

    public void setUserAddressId(int userAddressId) {
        this.userAddressId = userAddressId;
    }

    public String getMainPage() {
        return mainPage;
    }

    public void setMainPage(String mainPage) {
        this.mainPage = mainPage;
    }

    public List<UserAddress> getUserAddressList() {
        return userAddressList;
    }

    public void setUserAddressList(List<UserAddress> userAddressList) {
        this.userAddressList = userAddressList;
    }

    public Integer getIsFirstChoice() {
        return isFirstChoice;
    }

    public void setIsFirstChoice(Integer isFirstChoice) {
        this.isFirstChoice = isFirstChoice;
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

    public String save() throws Exception {
        User currentUser = UserUtil.getUserFromSession(request);

        userAddress.setUser(currentUser);
        Region region = regionService.findRegionById(userAddress.getRegion3().getId());
        String regionName = convertToRegionName(region);
        userAddress.setRegionName(regionName);

        if (null != this.getIsFirstChoice() && this.getIsFirstChoice() == 1) {
            List<UserAddress> userAddresses = new ArrayList<>();
            userAddresses.addAll(currentUser.getUserAddresses());
            for (UserAddress e : userAddresses) {
                e.setFirstChoice(false);
                userAddressService.saveUserAddress(e);
            }
            userAddress.setFirstChoice(true);
        } else {
            if (currentUser.getUserAddresses().size() > 0) {
                userAddress.setFirstChoice(false);
            } else {
                userAddress.setFirstChoice(true);
            }

        }

        userAddressService.saveUserAddress(userAddress);
        return "save";
    }

    public String showDetail() throws Exception {
        userAddress = userAddressService.findUserAddressById(userAddressId);
        return "show_detail";
    }

    private String convertToRegionName(Region region) {
        if (null != region.getParentId() && region.getParentId() != 0) {
            Region parent = regionService.findRegionById(region.getParentId());
            return convertToRegionName(parent) + " " + region.getRegionName();
        }
        return "";
    }

    public String list() throws Exception {
        int pageSize = Integer.parseInt(PropertyUtil.getPropertyByName2("constant.properties", "adminpagesize"));
        if (currentPage == 0) {
            currentPage = 1;
        }
        PageBean pageBean = new PageBean(currentPage, pageSize);
        long count = userAddressService.getUserAddressCount(userAddress);
        totalPage = (int) (count / pageSize);
        if ((count % pageSize) > 0) {
            totalPage++;
        }
        pageCode = PageUtil.getPagination(request.getContextPath() + "/UserAddress_list.action", count, currentPage,
                pageSize, null);

        userAddressList = userAddressService.findUserAddressList(userAddress, pageBean);
        mainPage = "userAddress.jsp";
        return "list";
    }

    public String showList() throws Exception {
        User currentUser = UserUtil.getUserFromSession(request);
        if (currentUser != null) {
            userAddressList.addAll(currentUser.getUserAddresses());
        }

		/*for(int i = 0; i < userAddressList.size(); i++) {
			Region region = userAddressList.get(i).getRegion();
			String regionName = "";
			while(region.getParentId() != 0) {
				regionName = region.getRegionName() + regionName;
				region = regionService.findRegionById(region.getParentId());
			}
			userAddressList.get(i).setRegionName(regionName);
		}*/
        return "list";
    }

    public String delete() throws Exception {
        User currentUser = UserUtil.getUserFromSession(request);
        userAddress = userAddressService.findUserAddressById(userAddressId);

        JSONObject result = new JSONObject();

        if (userAddress.getUser().getId().equals(currentUser.getId())) {
            userAddress.setUser(null);
            userAddressService.saveUserAddress(userAddress);

            if (userAddress.getFirstChoice()) {
                currentUser = UserUtil.getUserFromSession(request);
                if (currentUser.getUserAddresses().size() != 0) {
                    List<UserAddress> userAddresses = new ArrayList<>();
                    userAddresses.addAll(currentUser.getUserAddresses());
                    for (UserAddress e : userAddresses) {
                        if (e.getId() != userAddressId) {
                            e.setFirstChoice(true);
                            userAddressService.saveUserAddress(e);
                            break;
                        }
                    }

                }
            }
            result.put("success", true);
        } else {
            result.put("success", false);
            result.put("errMsg", "收货地址不存在");
        }

        ResponseUtil.write(ServletActionContext.getResponse(), result);
        return null;
    }

    public String deleteList() throws Exception {
        return null;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}