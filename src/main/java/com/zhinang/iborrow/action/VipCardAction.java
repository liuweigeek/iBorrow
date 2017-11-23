package com.zhinang.iborrow.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zhinang.iborrow.constant.Constant;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.Payment;
import com.zhinang.iborrow.entity.User;
import com.zhinang.iborrow.entity.VipCard;
import com.zhinang.iborrow.service.PaymentService;
import com.zhinang.iborrow.service.UserService;
import com.zhinang.iborrow.service.VipCardService;
import com.zhinang.iborrow.util.PropertyUtil;
import com.zhinang.iborrow.util.PageUtil;
import com.zhinang.iborrow.util.ResponseUtil;
import com.zhinang.iborrow.util.StringUtil;
import com.zhinang.iborrow.util.UserUtil;

import net.sf.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.stereotype.Controller;

@Controller
public class VipCardAction extends ActionSupport implements ModelDriven<VipCard>, ServletRequestAware {
	private static final long serialVersionUID = 1L;
	private VipCard vipCard = new VipCard();

	@Resource
	private VipCardService vipCardService;
	@Resource
	private UserService userService;
	@Resource
	private PaymentService paymentService;
	
	private String mainPage;
	
	private List<VipCard> vipCardList = new ArrayList<VipCard>();
	
	private File vipCardBackground;
	private String vipCardBackgroundFileName;
	
	private int vipCardId;
	private String tradeNum;
	
	private int currentPage;
	private int totalPage;
	private String pageCode;
	
	private HttpServletRequest request;

	@Override
    public VipCard getModel() {
		return this.vipCard;
	}

	public void setVipCardService(VipCardService vipCardService) {
		this.vipCardService = vipCardService;
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

	public List<VipCard> getVipCardList() {
		return vipCardList;
	}

	public void setVipCardList(List<VipCard> vipCardList) {
		this.vipCardList = vipCardList;
	}

	public File getVipCardBackground() {
		return vipCardBackground;
	}

	public void setVipCardBackground(File vipCardBackground) {
		this.vipCardBackground = vipCardBackground;
	}

	public String getVipCardBackgroundFileName() {
		return vipCardBackgroundFileName;
	}

	public void setVipCardBackgroundFileName(String vipCardBackgroundFileName) {
		this.vipCardBackgroundFileName = vipCardBackgroundFileName;
	}
	
	public int getVipCardId() {
		return vipCardId;
	}

	public void setVipCardId(int vipCardId) {
		this.vipCardId = vipCardId;
	}

	public String getTradeNum() {
		return tradeNum;
	}

	public void setTradeNum(String tradeNum) {
		this.tradeNum = tradeNum;
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
		int pageSize = Integer.parseInt(PropertyUtil.getPropertyByName2("constant.properties", "adminpagesize"));
		if (currentPage == 0) {
			currentPage = 1;
		}
		PageBean pageBean = new PageBean(currentPage, pageSize);
		long count = vipCardService.getVipCardCount(vipCard);
		totalPage = (int) (count / pageSize);
		if ((count % pageSize) > 0) {
			totalPage++;
		}
		pageCode = PageUtil.getPagination(request.getContextPath() + "/VipCard_list.action", count, currentPage,
				pageSize, null);
		
		vipCardList = vipCardService.findVipCardList(null, pageBean);
		mainPage = "vipcard.jsp";
		return "list";
	}

	public String delete() throws Exception {
		JSONObject result = new JSONObject();
		VipCard e = vipCardService.findVipCardById(vipCardId);
		vipCardService.deleteVipCard(e);
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

	public String deleteList() throws Exception {
		return null;
	}

	public String save() throws Exception {
		if (vipCardBackground != null) {
			String imageName = StringUtil.getRandomString();
			/*String realPath = ServletActionContext.getServletContext().getRealPath("/images/vipcard");*/
			String realPath =  Constant.IMG_PATH + "vipcard";
			String imageFile = imageName + vipCardBackgroundFileName
					.substring(vipCardBackgroundFileName.lastIndexOf("."), vipCardBackgroundFileName.length());
			File saveFile = new File(realPath, imageFile);
			FileUtils.copyFile(vipCardBackground, saveFile);
			vipCard.setBackground("/iborrow/images/vipcard/" + imageFile);
		}

		vipCardService.saveVipCard(vipCard);
		return "save";
	}
	
	//判断VipId是否已存在注册
	private boolean vipIdIsExist(String vipId) {
		return userService.existUserWithVipId(vipId);
	}

	private String createVipId() {
		String vipId;
		do {
			vipId = StringUtil.getRandomStringVipId();
		} while(vipIdIsExist(vipId));

		return vipId;
	}
	
	public String activate() throws Exception {
		User currentUser = UserUtil.getUserFromSession(request);

		Payment currentPayment = paymentService.findPaymentByoutTradeNo(tradeNum);

		JSONObject result = new JSONObject();
		if (currentPayment.getFinish()) {
			//延长会员到期时间
			VipCard currentCard = vipCardService.findVipCardById(vipCardId);
			Date openTime = new Date();
			currentUser.setOpenTime(openTime);
			Date expireTime = currentUser.getExpirationTime();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(expireTime);
			if (System.currentTimeMillis() > calendar.getTimeInMillis()) {
				calendar.setTimeInMillis(System.currentTimeMillis());
			}
			calendar.add(Calendar.DAY_OF_YEAR, currentCard.getDay());
			currentUser.setExpirationTime(calendar.getTime());
			if (StringUtil.isEmpty(currentUser.getVipId())) {
				currentUser.setVipId(createVipId());
			}
			userService.saveUser(currentUser);

			String endTime = new SimpleDateFormat("yyyy年MM月dd日").format(currentUser.getExpirationTime());
			result.put("endTime", endTime);
			result.put("success", true);
		
		} else {
			result.put("success",false);
		}
		//判断是否需要填写推荐人
		if (null == currentUser.getReferee() || null == currentUser.getReferee().getId()) {
			result.put("isFirst", true);
		} else {
			result.put("isFirst", false);
		}
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

	//确认会员卡购买结果
    public String confirmActivate() throws Exception {
        User currentUser = UserUtil.getUserFromSession(request);
        Payment currentPayment = paymentService.findPaymentByoutTradeNo(tradeNum);

        JSONObject result = new JSONObject();
        if (currentPayment.getFinish()) {

            String endTime = new SimpleDateFormat("yyyy年MM月dd日").format(currentUser.getExpirationTime());
            result.put("endTime", endTime);
            result.put("success", true);

        } else {
            result.put("success",false);
        }
        //判断是否需要填写推荐人
        if ((null == currentUser.getReferee() || null == currentUser.getReferee().getId())
				&& StringUtil.isEmpty(currentUser.getVipId())) {
            result.put("isFirst", true);
        } else {
            result.put("isFirst", false);
        }
        ResponseUtil.write(ServletActionContext.getResponse(), result);
        return null;
    }

	public String showList() throws Exception {
		vipCardList = vipCardService.findVipCardList(null, null);
		return "showList";
	}

	@Override
    public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
}