package com.zhinang.iborrow.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zhinang.iborrow.entity.User;
import com.zhinang.iborrow.entity.Voucher;
import com.zhinang.iborrow.service.UserService;
import com.zhinang.iborrow.service.VoucherService;
import com.zhinang.iborrow.util.ResponseUtil;
import com.zhinang.iborrow.util.UserUtil;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class VoucherAction extends ActionSupport implements ModelDriven<Voucher>, ServletRequestAware {
    private static final long serialVersionUID = 1L;
    private Voucher voucher = new Voucher();

    @Resource
    private VoucherService voucherService;
    @Resource
    private UserService userService;

    private String mainPage;

    String voucherCode;
    int voucherCount;
    int voucherDay;

    private List<Voucher> voucherList = new ArrayList<Voucher>();

    private int currentPage;
    private int totalPage;
    private String pageCode;

    private HttpServletRequest request;

    @Override
    public Voucher getModel() {
        return this.voucher;
    }

    public void setVoucherService(VoucherService voucherService) {
        this.voucherService = voucherService;
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

    public String getVoucherCode() {
        return voucherCode;
    }

    public void setVoucherCode(String voucherCode) {
        this.voucherCode = voucherCode;
    }

    public int getVoucherCount() {
        return voucherCount;
    }

    public void setVoucherCount(int voucherCount) {
        this.voucherCount = voucherCount;
    }

    public int getVoucherDay() {
        return voucherDay;
    }

    public void setVoucherDay(int voucherDay) {
        this.voucherDay = voucherDay;
    }

    public List<Voucher> getVoucherList() {
        return voucherList;
    }

    public void setVoucherList(List<Voucher> voucherList) {
        this.voucherList = voucherList;
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
        voucherList = voucherService.findVoucherList(null, null);
        mainPage = "vipcard.jsp";
        return "list";
    }

    public String delete() throws Exception {
        return null;
    }

    public String deleteList() throws Exception {
        return null;
    }

    public String save() throws Exception {
        voucherService.saveVoucher(voucher);
        return "save";
    }

    public String activate() throws Exception {
        JSONObject result = new JSONObject();
        if (voucherService.existVoucherWithCode(voucherCode)) {
            if (voucherService.findVoucherByCode(voucherCode).getIsValid()) {
                Voucher e = voucherService.findVoucherByCode(voucherCode);

                User currentUser = UserUtil.getUserFromSession(request);

                Date expireTime = currentUser.getExpirationTime();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(expireTime);
                calendar.add(Calendar.DAY_OF_YEAR, e.getDay());
                currentUser.setExpirationTime(calendar.getTime());
                userService.saveUser(currentUser);
                e.setIsValid(false);
                voucherService.saveVoucher(e);
                result.put("success", true);
            } else {
                result.put("success", false);
                result.put("error", "此代金券已被使用");
            }
        } else {
            result.put("success", false);
            result.put("error", "代金券不存在");
        }
        ResponseUtil.write(ServletActionContext.getResponse(), result);
        return null;
    }

	/*public String createNew() throws Exception {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) + 1);
		Date endDate = calendar.getTime();
		for (int i = 0; i < 200; i++) {
			Voucher voucher = new Voucher();
			String code = StringUtil.getRandomString().replace("-", "").substring(0, 15);
			voucher.setCode(code);
			voucher.setDay(30);
			voucher.setEndDate(endDate);
			voucher.setIsValid(true);
			voucherService.saveVoucher(voucher);
		}

		for (int i = 0; i < 200; i++) {
			Voucher voucher = new Voucher();
			String code = StringUtil.getRandomString().replace("-", "").substring(0, 15);
			voucher.setCode(code);
			voucher.setDay(90);
			voucher.setEndDate(endDate);
			voucher.setIsValid(true);
			voucherService.saveVoucher(voucher);
		}
		
		for (int i = 0; i < 50; i++) {
			Voucher voucher = new Voucher();
			String code = StringUtil.getRandomString().replace("-", "").substring(0, 15);
			voucher.setCode(code);
			voucher.setDay(370);
			voucher.setEndDate(endDate);
			voucher.setIsValid(true);
			voucherService.saveVoucher(voucher);
		}
		
		return SUCCESS;
	}*/

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}