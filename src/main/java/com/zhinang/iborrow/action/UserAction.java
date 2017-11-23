package com.zhinang.iborrow.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zhinang.iborrow.constant.Constant;
import com.zhinang.iborrow.entity.*;
import com.zhinang.iborrow.service.PaymentService;
import com.zhinang.iborrow.service.UserService;
import com.zhinang.iborrow.util.AuthUtil;
import com.zhinang.iborrow.util.PropertyUtil;
import com.zhinang.iborrow.util.PageUtil;
import com.zhinang.iborrow.util.ResponseUtil;
import com.zhinang.iborrow.util.StringUtil;
import com.zhinang.iborrow.util.UserUtil;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import org.apache.http.client.ClientProtocolException;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.stereotype.Controller;

@Controller
public class UserAction extends ActionSupport implements ModelDriven<User>, ServletRequestAware, ServletResponseAware {
	private static final long serialVersionUID = 1L;
	private User user = new User();

	@Resource
	private UserService userService;
	@Resource
	private PaymentService paymentService;
	private HttpServletRequest request;
	private HttpServletResponse response;

	private String mainPage;
	private int depositNum;
	private String tradeNum;
	private String refereeVipId;

    interface ErrType{
        int NO_PHONE = 1;
        int NOT_VIP = 2;
        int DEPOSIT_NOT_ENOUGH = 3;
    }

	private int userId;
	private int newDeposit;

    private String keyword;
	
	private List<User> userList = new ArrayList<User>();
	private List<Product> favoriteList = new ArrayList<>();
	private List<Product> cartList = new ArrayList<>();
	private List<Order> borrowedList = new ArrayList<>();
	private List<User> recommendeds = new ArrayList<User>();
	
	private int currentPage;
	private int totalPage;
	private String pageCode;
	
	private String adminPhone;
	private String adminPassword;

	@Override
    public User getModel() {
		return user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	public void setPaymentService(PaymentService paymentService) {
		this.paymentService = paymentService;
	}

	public String getMainPage() {
		return mainPage;
	}

	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}
	
	public int getDepositNum() {
		return depositNum;
	}

	public void setDepositNum(int depositNum) {
		this.depositNum = depositNum;
	}

	public String getTradeNum() {
		return tradeNum;
	}

	public void setTradeNum(String tradeNum) {
		this.tradeNum = tradeNum;
	}

	public String getRefereeVipId() {
		return refereeVipId;
	}

	public void setRefereeVipId(String refereeVipId) {
		this.refereeVipId = refereeVipId;
	}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getNewDeposit() {
        return newDeposit;
    }

    public void setNewDeposit(int newDeposit) {
        this.newDeposit = newDeposit;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
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

	public List<Order> getBorrowedList() {
		return borrowedList;
	}

	public void setBorrowedList(List<Order> borrowedList) {
		this.borrowedList = borrowedList;
	}

	public List<User> getRecommendeds() {
		return recommendeds;
	}

	public void setRecommendeds(List<User> recommendeds) {
		this.recommendeds = recommendeds;
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

	public String getAdminPhone() {
		return adminPhone;
	}

	public void setAdminPhone(String adminPhone) {
		this.adminPhone = adminPhone;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	//获取用户信息
	@SuppressWarnings("deprecation")
	public void requestUserInfo() throws IOException {
		String backUrl = Constant.weixin.WX_LOGIN_CALLBACK_URL + "/User_wxCallBack.action";

		String url = "https://open.weixin.qq.com/connect/oauth2/authorize?"
				+ "appid=" + Constant.weixin.APPID
				+ "&redirect_uri=" + URLEncoder.encode(backUrl)
				+ "&response_type=code"
				+ "&scope=snsapi_base"
				+ "&state=STATE#wechat_redirect";

		response.sendRedirect(url);
	}

	//获取用户全部信息
    public void requestFullUserInfo() throws IOException {
        String backUrl = Constant.weixin.WX_LOGIN_CALLBACK_URL + "/User_wxFullInfoCallBack.action";

        String url = "https://open.weixin.qq.com/connect/oauth2/authorize?"
                + "appid=" + Constant.weixin.APPID
                + "&redirect_uri=" + URLEncoder.encode(backUrl)
                + "&response_type=code"
                + "&scope=snsapi_userinfo"
                + "&state=STATE#wechat_redirect";

        response.sendRedirect(url);
    }

	//微信登录回调
	public String wxCallBack() throws ClientProtocolException, IOException, ServletException {
		String code = request.getParameter("code");
		if (StringUtil.isNotEmpty(code)) {
			String url = "https://api.weixin.qq.com/sns/oauth2/access_token?"
					+ "appid=" + Constant.weixin.APPID
					+ "&secret=" + Constant.weixin.APPSECRET
					+ "&code=" + code
					+ "&grant_type=authorization_code";
			JSONObject jsonObject = AuthUtil.doGetJson(url);
			String openid = jsonObject.getString("openid");

			if (!userIsExist(openid)) {
				return "getFullInfo";
			} else {
				if (loginWithSuccess(openid)) {
					return "callBack";
				} else {
					return "getFullInfo";
				}
            }
        } else {
			return "getFullInfo";
		}
	}

    //微信登录回调
    public String wxFullInfoCallBack() throws ClientProtocolException, IOException, ServletException {
        String code = request.getParameter("code");
        if (StringUtil.isNotEmpty(code)) {
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?"
                    + "appid=" + Constant.weixin.APPID
                    + "&secret=" + Constant.weixin.APPSECRET
                    + "&code=" + code
                    + "&grant_type=authorization_code";
            JSONObject jsonObject = AuthUtil.doGetJson(url);
            String openid = jsonObject.getString("openid");
            String token = jsonObject.getString("access_token");
            String infoUrl = "https://api.weixin.qq.com/sns/userinfo?"
                    + "access_token=" + token
                    + "&openid=" + openid
                    + "&lang=zh_CN";
            JSONObject userInfo = AuthUtil.doGetJson(infoUrl);

            if (!userIsExist(userInfo)) {
                register(userInfo);
            }
            login(userInfo);

			/*request.getRequestDispatcher("User_showIndex").forward(request, response);*/
        }
        return "callBack";
    }

    /*public boolean needUpdateUserInfo(String openId) {
		User currentUser = userService.getUserByWeixinOpenId(openId);
		if (StringUtil.isEmpty(currentUser.getUpdateTime().toString()) {
			return true;
		}
		if ((System.currentTimeMillis() - currentUser.getUpdateTime().getTime()) / 1000 > 2592000) {
			return true;
		}
		return false;
	}*/

    //判断用户是否已注册
    public boolean userIsExist(String openid) {
        return userService.existUserWithWeixinOpenId(openid);
    }

	//判断用户是否已注册
	public boolean userIsExist(JSONObject userInfo) {
		return userService.existUserWithWeixinOpenId(userInfo.getString("openid"));
	}
	
	//注册
	public void register(JSONObject userInfo) {
		user.setWx_openid(userInfo.getString("openid"));
		user.setNickname(userInfo.getString("nickname"));
		user.setHeadimgurl(userInfo.getString("headimgurl"));
		user.setRegTime(new Date());
		user.setUpdateTime(new Date());
		user.setExpirationTime(new Date(0));
		user.setIntegral(0);
		user.setDeposit(0);
		user.setBorrowSum(0);
		user.setType(Constant.userType.FREE_ACCOUNT);
		userService.saveUser(user);
	}

	//登录
	public void login(JSONObject userInfo) {
		HttpSession session = request.getSession();
		user = userService.getUserByWeixinOpenId(userInfo.getString("openid"));
		user.setHeadimgurl(userInfo.getString("headimgurl"));
		user.setLoginTime(new Date());
		user.setUpdateTime(new Date());
		userService.saveUser(user);
		session.setAttribute(Constant.key_value.CURRENT_USER, user.getId());
		session.setAttribute(Constant.key_value.USER_TYPE, user.getType());
	}

    //登录
    public boolean loginWithSuccess(String openid) {
        HttpSession session = request.getSession();
        user = userService.getUserByWeixinOpenId(openid);
        if (needGetNewHeadimg(user)) {
        	return false;
		} else {
			user.setLoginTime(new Date());
			userService.saveUser(user);
			session.setAttribute(Constant.key_value.CURRENT_USER, user.getId());
			session.setAttribute(Constant.key_value.USER_TYPE, user.getType());
			return true;
		}
	}

	public boolean needGetNewHeadimg(User user) {
    	if (user.getUpdateTime() == null || user.getUpdateTime().toString().equals("")) {
    		return true;
		}
    	return (int)((user.getUpdateTime().getTime() - System.currentTimeMillis())/86400000) > 30;
	}

	//判断VipId是否已存在注册
	public boolean vipIdIsExist(String vipId) {
		return userService.existUserWithVipId(vipId);
	}

	public String createVipId() {
		String vipId;
		do {
			vipId = StringUtil.getRandomStringVipId();
		} while(vipIdIsExist(vipId));

		return vipId;
	}

	//获取用户列表
	public String list() throws Exception {

        if (StringUtil.isNotEmpty(keyword)) {
            user.setNickname(keyword);
        }

		int pageSize = Integer.parseInt(PropertyUtil.getPropertyByName2("constant.properties", "adminpagesize"));
		if (currentPage == 0) {
			currentPage = 1;
		}
		PageBean pageBean = new PageBean(currentPage, pageSize);
		long count = userService.getUserCount(user);
		totalPage = (int) (count / pageSize);
		if ((count % pageSize) > 0) {
			totalPage++;
		}
		pageCode = PageUtil.getPagination(request.getContextPath() + "/User_list.action", count, currentPage,
				pageSize, StringUtil.isNotEmpty(keyword) ? "keyword=" + keyword : "");

		userList = userService.findUserList(user, pageBean);
		mainPage = "user.jsp";
		return "list";
	}

	//显示首页
	public String showIndex() throws Exception {
		User currentUser = UserUtil.getUserFromSession(request);
		cartList.addAll(currentUser.getCartProducts());
		favoriteList.addAll(currentUser.getFavoriteProducts());
		return "showIndex";
	}
	
	//显示收藏页面
	public String showFavorite() throws Exception {
		User currentUser = UserUtil.getUserFromSession(request);

		favoriteList.addAll(currentUser.getFavoriteProducts());
		cartList.addAll(currentUser.getCartProducts());
		return "showFavorite";
	}

	//显示借阅车界面
	public String showCart() throws Exception {
		User currentUser = UserUtil.getUserFromSession(request);
		cartList.addAll(currentUser.getCartProducts());
		return "showCart";
	}

	//显示借阅车界面
	public String showBorrowed() throws Exception {
		User currentUser = UserUtil.getUserFromSession(request);

		borrowedList.clear();
		Set<Order> borrowedSet = currentUser.getOrders();
		Iterator<Order> iterator =  borrowedSet.iterator();
		while(iterator.hasNext()) {
			Order e = iterator.next();
			if (Constant.OrderType.BORROW == e.getOrderType() && Constant.OrderStatus.ORDER_FINISH != e.getStatus()) {
				borrowedList.add(e);
			}
		}
		return "showBorrowed";
	}

	//显示个人中心页面
	public String showUserCenter() throws Exception {
		user = UserUtil.getUserFromSession(request);
		return "showUserCenter";
	}

	//显示个人资料页面
	public String showUserInfo() throws Exception {
		user = UserUtil.getUserFromSession(request);
		recommendeds.addAll(user.getRecommendeds());
		return "showUserInfo";
	}
	
	//添加手机号与电子邮箱
	public String addPhoneAndEmail() throws Exception {
		User currentUser = UserUtil.getUserFromSession(request);

		currentUser.setPhone(user.getPhone());
		currentUser.setEmail(user.getEmail());
		currentUser.setUpdateTime(new Date());
		userService.saveUser(currentUser);
		return "addPhoneSuccess";
	}

	public String checkAddToCart() throws Exception {
        User currentUser = UserUtil.getUserFromSession(request);
        JSONObject result = new JSONObject();
        if (userIsVip(currentUser)) {
            if (userHasPhoneNum(currentUser)) {
                if(currentUser.getDeposit() >= 300) {
                    result.put("success", true);
                } else {
                    result.put("success", false);
                    result.put("errType", ErrType.DEPOSIT_NOT_ENOUGH);
                    result.put("errMsg", "押金不足300，请前往个人中心页面充值押金");
                }
            } else {
                result.put("success", false);
                result.put("errType", ErrType.NO_PHONE);
                result.put("errMsg", "请先绑定手机号");
            }
        } else {
            result.put("success", false);
            result.put("errType", ErrType.NOT_VIP);
            result.put("errMsg", "请先购买会员卡");
        }

        ResponseUtil.write(ServletActionContext.getResponse(), result);
        return null;
    }

	//判断用户是否有手机号
	public String hasPhoneNum() throws Exception {
		User currentUser = UserUtil.getUserFromSession(request);
		JSONObject result = new JSONObject();
		if (userHasPhoneNum(currentUser)) {
			result.put("success", true);
		} else {
			result.put("success", false);
		}
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

	//根据id查找用户是否有手机号
	public boolean userHasPhoneNum(User user) {
		return StringUtil.isNotEmpty(user.getPhone());
	}

	//判断用户是否为会员
	public String isVip() throws Exception {
		User currentUser = UserUtil.getUserFromSession(request);
		JSONObject result = new JSONObject();
		if (userIsVip(currentUser)) {
			result.put("success", true);
		} else {
			result.put("success", false);
		}
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

	//根据查找用户是否为会员
	public boolean userIsVip(User user) {
		Date expirationTime = user.getExpirationTime();
		Date currentTime = new Date();
		return (expirationTime.getTime() - currentTime.getTime()) > 0;
	}

	//检查押金
	public String checkDeposit() throws Exception {
		User currentUser = UserUtil.getUserFromSession(request);
		
		JSONObject result = new JSONObject();
		if(currentUser.getDeposit() >= 300) {
			result.put("success", true);
		} else {
			result.put("success", false);
		}
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}
	//充值押金
	public String rechargeDeposit() throws Exception {
		User currentUser = UserUtil.getUserFromSession(request);

		Payment currentPayment = paymentService.findPaymentByoutTradeNo(tradeNum);
		JSONObject result = new JSONObject();
		
		if (currentPayment.getFinish()) {
			currentUser.setDeposit(currentUser.getDeposit() + depositNum);
			userService.saveUser(currentUser);
			result.put("success", true);
		} else {
			result.put("success",false);
		}
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

	//确认押金充值结果
    public String confirmRechargeDeposit() throws Exception {
        Payment currentPayment = paymentService.findPaymentByoutTradeNo(tradeNum);
        JSONObject result = new JSONObject();

        if (currentPayment.getFinish()) {
            result.put("success", true);
        } else {
            result.put("success",false);
        }
        ResponseUtil.write(ServletActionContext.getResponse(), result);
        return null;
    }

	//所需押金
	public String needsDeposit() throws Exception {
		User currentUser = UserUtil.getUserFromSession(request);
		
		int mixDeposit = Integer.parseInt(PropertyUtil.getPropertyByName2("constant.properties","borrow_deposit"));
		int needs = mixDeposit - currentUser.getDeposit();
		JSONObject result = new JSONObject();
		result.put("success", true);
		result.put("needs", needs);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}
	
	public String setReferee() throws Exception {
		
		JSONObject result = new JSONObject();
		
		if (userService.existUserWithVipId(refereeVipId)) {

			User currentUser = UserUtil.getUserFromSession(request);
			
			User referee = userService.getUserByVipId(refereeVipId);
			
			if(currentUser.getId() != referee.getId()) {
				currentUser.setReferee(referee);
				Date currentUserExpirationTime = currentUser.getExpirationTime();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(currentUserExpirationTime);
				calendar.add(Calendar.MONTH, 1);
				currentUser.setExpirationTime(calendar.getTime());
				userService.saveUser(currentUser);

				referee = userService.getUserByVipId(refereeVipId);
				Date refereeExpirationTime = referee.getExpirationTime();
				calendar.setTime(refereeExpirationTime);
				calendar.add(Calendar.MONTH, 1);
				referee.setExpirationTime(calendar.getTime());
				userService.saveUser(referee);

				result.put("success", true);
			} else {
				result.put("success", false);
				result.put("errorMsg", "不可设自己为推荐人");
			}
		} else {
			result.put("success", false);
			result.put("errorMsg", "会员号不存在");
		}

		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

	public String addDeposit() throws Exception {
	    User e = userService.findUserById(userId);
	    e.setDeposit(e.getDeposit() + newDeposit);
	    userService.saveUser(e);

        JSONObject result = new JSONObject();
        result.put("success", true);
        ResponseUtil.write(ServletActionContext.getResponse(), result);
        return null;
	}

    public String reduceDeposit() throws Exception {

	    JSONObject result = new JSONObject();

        User e = userService.findUserById(userId);

        boolean allFinish = true;

        for (Order order : e.getOrders()) {
            if (order.getStatus() != Constant.OrderStatus.ORDER_FINISH) {
                allFinish = false;
                break;
            }
        }

        if (!allFinish) {
            result.put("errorMsg", "该用户当前有未完成的订单");
        } else if (e.getDeposit() < newDeposit) {
            result.put("errorMsg", "退还押金不可大于" + e.getDeposit() + "元！");
        } else if (e.getExpirationTime().getTime() > System.currentTimeMillis()) {
            result.put("errorMsg", "该用户会员权限未到期！");
        } else {
            e.setDeposit(e.getDeposit() - newDeposit);
            userService.saveUser(e);
            result.put("success", true);
        }

        ResponseUtil.write(ServletActionContext.getResponse(), result);
        return null;
    }

	public String adminLogin() throws Exception {
		user.setPhone(adminPhone);
		user.setPassword(adminPassword);
		User currentUser = userService.login(user);
		
		JSONObject result = new JSONObject();
		if (currentUser != null) {
			HttpSession session = request.getSession();
			session.setAttribute(Constant.key_value.CURRENT_ADMIN, currentUser.getId());
            session.setAttribute(Constant.key_value.ADMIN_NICKNAME, currentUser.getNickname());
            session.setAttribute(Constant.key_value.ADMIN_PHONE, currentUser.getPhone());
            session.setAttribute(Constant.key_value.ADMIN_HEADIMG, currentUser.getHeadimgurl());
            session.setAttribute(Constant.key_value.USER_TYPE, currentUser.getType());

            currentUser.setLoginTime(new Date());
            userService.saveUser(currentUser);
			result.put("success", true);
		} else {
			result.put("success", false);
		}
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

    //添加手机号与密码
    public String addPhoneAndPassword() throws Exception {
        User currentUser = userService.findUserById(userId);

        currentUser.setPhone(adminPhone);
        currentUser.setPassword(adminPassword);
        userService.saveUser(currentUser);
        JSONObject result = new JSONObject();
        result.put("success", true);
        ResponseUtil.write(ServletActionContext.getResponse(), result);
        return null;
    }

    public String setAdmin() throws Exception {
        JSONObject result = new JSONObject();

	    User currentUser = UserUtil.getAdminFromSession(request);
        if (currentUser.getType() == Constant.userType.ADMIN) {
            User e = userService.findUserById(userId);
            if (StringUtil.isNotEmpty(e.getPhone()) && StringUtil.isNotEmpty(e.getPassword())) {
                e.setType(Constant.userType.LIBRARIAN);
                userService.saveUser(e);
                result.put("success", true);
            } else {
                result.put("success", false);
                result.put("errMsg", "请先为当前用户设置手机号与密码");
            }
        } else {
            result.put("success", false);
            result.put("errMsg", "无权限操作，当前登录用户不是超级管理员");
        }
        ResponseUtil.write(ServletActionContext.getResponse(), result);
        return null;
    }

    public String cancelAdmin() throws Exception {
        JSONObject result = new JSONObject();

        User currentUser = UserUtil.getAdminFromSession(request);
        if (currentUser.getType() == Constant.userType.ADMIN) {
            User e = userService.findUserById(userId);
            e.setType(Constant.userType.FREE_ACCOUNT);
            userService.saveUser(e);
            result.put("success", true);
        } else {
            result.put("success", false);
            result.put("errMsg", "无权限操作，当前登录用户不是超级管理员");
        }
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

	@Override
    public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}

}