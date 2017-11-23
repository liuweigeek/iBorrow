package com.zhinang.iborrow.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zhinang.iborrow.constant.Constant;
import com.zhinang.iborrow.entity.Order;
import com.zhinang.iborrow.entity.OrderItem;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.Product;
import com.zhinang.iborrow.entity.User;
import com.zhinang.iborrow.entity.UserAddress;
import com.zhinang.iborrow.service.OrderItemService;
import com.zhinang.iborrow.service.OrderService;
import com.zhinang.iborrow.service.ProductService;
import com.zhinang.iborrow.service.UserAddressService;
import com.zhinang.iborrow.service.UserService;
import com.zhinang.iborrow.util.PropertyUtil;
import com.zhinang.iborrow.util.PageUtil;
import com.zhinang.iborrow.util.ResponseUtil;
import com.zhinang.iborrow.util.StringUtil;
import com.zhinang.iborrow.util.UserUtil;

import net.sf.json.JSONObject;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.stereotype.Controller;

@Controller
public class OrderAction extends ActionSupport implements ModelDriven<Order>, ServletRequestAware {
	private static final long serialVersionUID = 1L;
	private Order order = new Order();

	@Resource
	private OrderService orderService;
	@Resource
	private OrderItemService orderItemService;
	@Resource
	private UserService userService;
	@Resource
	private UserAddressService userAddressService;
	@Resource
	private ProductService productService;
	
	private int orderId;
	private String num;
	
	private List<Product> productList = new ArrayList<>();
	private List<UserAddress> userAddressList = new ArrayList<>();
	private List<Order> orderList = new ArrayList<>();
	
	private String mainPage;
	private String ids;
	private String counts;
	private int userId;
	private int userAddressId;

    private String keyword;
    private String timeranger;

	private Integer checkIntact;
	private String damage;
	
	private int currentPage;
	private int totalPage;
	private String pageCode;
	
	private HttpServletRequest request;

	@Override
    public Order getModel() {
		return this.order;
	}
	
	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setUserAddressService(UserAddressService userAddressService) {
		this.userAddressService = userAddressService;
	}
	
	public void setOrderItemService(OrderItemService orderItemService) {
		this.orderItemService = orderItemService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public List<Product> getProductList() {
		return productList;
	}

	public void setProductList(List<Product> productList) {
		this.productList = productList;
	}

	public List<UserAddress> getUserAddressList() {
		return userAddressList;
	}

	public void setUserAddressList(List<UserAddress> userAddressList) {
		this.userAddressList = userAddressList;
	}

	public List<Order> getOrderList() {
		return orderList;
	}

	public void setOrderList(List<Order> orderList) {
		this.orderList = orderList;
	}

	public String getMainPage() {
		return mainPage;
	}

	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getCounts() {
		return counts;
	}

	public void setCounts(String counts) {
		this.counts = counts;
	}

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserAddressId() {
		return userAddressId;
	}

	public void setUserAddressId(int userAddressId) {
		this.userAddressId = userAddressId;
	}

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getTimeranger() {
        return timeranger;
    }

    public void setTimeranger(String timeranger) {
        this.timeranger = timeranger;
    }

    public Integer getCheckIntact() {
		return checkIntact;
	}

	public void setCheckIntact(Integer checkIntact) {
		this.checkIntact = checkIntact;
	}

	public String getDamage() {
		return damage;
	}

	public void setDamage(String damage) {
		this.damage = damage;
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

	//检测库存和用户可借阅数量
	public String checkRemainAndValid() throws Exception {
		User currentUser = UserUtil.getUserFromSession(request);

		boolean isOk = true;
		String errorMsg = "";
		
		String[] idsStr = ids.split(",");
		String[] countsStr = counts.split(",");

		JSONObject result=new JSONObject();
		
		for (int i = 0; i < idsStr.length; i++) {
			Product e = productService.findProductById(Integer.parseInt(idsStr[i]));
			if (e.getRemainder() < Integer.parseInt(countsStr[i])) {
				isOk = false;
				errorMsg = "『" + e.getName() + "』 库存不足";
				break;
			}
		}
		
		int count = 0;
		for (int i = 0; i < countsStr.length; i++) {
			count += Integer.parseInt(countsStr[i]);
		}
		int currentCanBorrow = Constant.key_value.BORROW_LIMIT - currentUser.getBorrowSum();
		if (count > currentCanBorrow) {
			isOk = false;
			errorMsg = "当前最多可借" + currentCanBorrow + "本书籍";
		}

		if (isOk) {
			result.put("success", true);
		} else {
			result.put("success", false);
			result.put("errorMsg", errorMsg);
		}
		
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;

	}
	
	//确认订单
	public String queryOrder() throws Exception {
		User currentUser = UserUtil.getUserFromSession(request);
		userAddressList.addAll(currentUser.getUserAddresses());
		String[] idsStr = ids.split(",");
		String[] countsStr = counts.split(",");

		for (int i = 0; i < idsStr.length; i++) {
			Product e = productService.findProductById(Integer.parseInt(idsStr[i]));
			for(int j = 0; j < Integer.parseInt(countsStr[i]); j++) {
				productList.add(e);
			}
		}
		return "query_order";
	}

	//用户下单
	public String addToOrder() throws Exception {
		User currentUser = UserUtil.getUserFromSession(request);

		String[] idsStr = ids.split(",");
		
		UserAddress currentUserAddress = userAddressService.findUserAddressById(userAddressId);

		JSONObject result=new JSONObject();
		Order order = new Order();
		order.setUser(currentUser);
		order.setCreateTime(new Date(System.currentTimeMillis()));
		order.setOrderType(Constant.OrderType.BORROW);
		order.setStatus(Constant.OrderStatus.UNSHIPPED);
		order.setUserAddress(currentUserAddress);
		
		for (int i = 0; i < idsStr.length; i++) {
			Product e = productService.findProductById(Integer.parseInt(idsStr[i]));
			currentUser.getCartProducts().remove(e);
			e.setRemainder(e.getRemainder() - 1);
			productService.saveProduct(e);
			OrderItem orderItem = new OrderItem();
			orderItem.setProduct(e);
			orderItem.setReturned(false);
			order.getOrderItems().add(orderItem);
		}
		
		orderService.saveOrder(order);
		
		currentUser.setBorrowSum(currentUser.getBorrowSum() + idsStr.length);
		/*currentUser.getCartProducts().clear();*/
		userService.saveUser(currentUser);

		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

	//管理员发货
	public String shipping() throws Exception {
		Order e = orderService.findOrderById(orderId);
		e.setExpressNum(num);
		e.setStatus(Constant.OrderStatus.ON_THE_WAY);
		orderService.saveOrder(e);
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}
	
	//用户确认收货
	public String userReceived() throws Exception {
		Order e = orderService.findOrderById(orderId);
		e.setStatus(Constant.OrderStatus.RECEIVE);
		
		if(null != this.getCheckIntact() && this.getCheckIntact() == 1) {
			e.setIntact(true);
		} else {
			e.setIntact(false);
		}
		
		if(StringUtil.isNotEmpty(damage)) {
			e.setDamageInfo(damage);
		} else {
			e.setDamageInfo("无");
		}

		orderService.saveOrder(e);
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}
	
	//管理员收到归还书籍
	public String adminReceived() throws Exception {
		Order e = orderService.findOrderById(orderId);
		
		int count = e.getOrderItems().size();
		User user = userService.findUserById(e.getUser().getId());
		user.setBorrowSum(user.getBorrowSum() - count);
		userService.saveUser(user);
		
		Iterator<OrderItem> iter = e.getOrderItems().iterator();
		while(iter.hasNext()) {
			int productId = iter.next().getProduct().getId();
			Product product = productService.findProductById(productId);
			product.setRemainder(product.getRemainder() + 1);
			productService.saveProduct(product);
		}
		e.setFinishTime(new Date());
		e.setStatus(Constant.OrderStatus.ORDER_FINISH);
		orderService.saveOrder(e);
		JSONObject result = new JSONObject();
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

	//还书
	public String refund() throws Exception {
		User currentUser = UserUtil.getUserFromSession(request);
		
		JSONObject result=new JSONObject();
		Order order = new Order();
		order.setUser(currentUser);
		order.setCreateTime(new Date(System.currentTimeMillis()));
		order.setOrderType(Constant.OrderType.REFUND);
		order.setStatus(Constant.OrderStatus.ON_THE_WAY);

		String[] idsStr = ids.split(",");
		for(int i = 0; i < idsStr.length; i++) {
			OrderItem e = orderItemService.findOrderItemById(Integer.parseInt(idsStr[i]));
			e.setReturned(true);
			order.getOrderItems().add(e);
		}
		orderService.saveOrder(order);
		
		currentUser = userService.findUserById(currentUser.getId());
		Iterator<Order> iterOrders = currentUser.getOrders().iterator();
		while(iterOrders.hasNext()){
			Order curr = iterOrders.next();
			if(curr.getOrderType() == Constant.OrderType.BORROW) {
				Iterator<OrderItem> iterOrderItems = curr.getOrderItems().iterator();
				boolean allReturn = true;
				while(iterOrderItems.hasNext()) {
					if(!iterOrderItems.next().getReturned()) {
						allReturn = false;
						break;
					}
				}
				if(allReturn) {
				    curr.setFinishTime(new Date());
					curr.setStatus(Constant.OrderStatus.ORDER_FINISH);
				}
				orderService.saveOrder(curr);
			}
		}
		
		result.put("success", true);
		ResponseUtil.write(ServletActionContext.getResponse(), result);
		return null;
	}

    //用户下单
    public String addToOrderFromAdmin() throws Exception {
        User currentUser = userService.findUserById(userId);

        String[] idsStr = ids.split(",");

        JSONObject result=new JSONObject();
        Order order = new Order();
        order.setUser(currentUser);
        order.setCreateTime(new Date(System.currentTimeMillis()));
        order.setOrderType(Constant.OrderType.BORROW);
        order.setStatus(Constant.OrderStatus.RECEIVE);

        for (int i = 0; i < idsStr.length; i++) {
            Product e = productService.findProductById(Integer.parseInt(idsStr[i]));
            currentUser.getCartProducts().remove(e);
            e.setRemainder(e.getRemainder() - 1);
            productService.saveProduct(e);
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(e);
            orderItem.setReturned(false);
            order.getOrderItems().add(orderItem);
        }

        orderService.saveOrder(order);

        currentUser.setBorrowSum(currentUser.getBorrowSum() + idsStr.length);
		/*currentUser.getCartProducts().clear();*/
        userService.saveUser(currentUser);

        result.put("success", true);
        ResponseUtil.write(ServletActionContext.getResponse(), result);
        return null;
    }

	public String list() throws Exception {

        if (StringUtil.isNotEmpty(keyword)) {
            User e = new User();
            e.setNickname(keyword);
            order.setUser(e);
        }

        if (StringUtil.isNotEmpty(timeranger)) {
            String[] timeStartEnd = timeranger.split(" - ");

            if (timeStartEnd.length == 2) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(timeStartEnd[0]);
                order.setCreateTime(date);
                date = sdf.parse(timeStartEnd[1]);
                order.setFinishTime(date);
            }
        }

		int pageSize = Integer.parseInt(PropertyUtil.getPropertyByName2("constant.properties", "adminpagesize"));
		if (currentPage == 0) {
			currentPage = 1;
		}
		PageBean pageBean = new PageBean(currentPage, pageSize);
		long count = orderService.getOrderCount(order);
		totalPage = (int) (count / pageSize);
		if ((count % pageSize) > 0) {
			totalPage++;
		}
        String newParams = (StringUtil.isNotEmpty(keyword) ? "&keyword=" + keyword : "")
                + (StringUtil.isNotEmpty(timeranger) ? "&timeranger=" + timeranger : "");
		pageCode = PageUtil.getPagination(request.getContextPath() + "/Order_list.action", count, currentPage, pageSize,
				"orderType=" + order.getOrderType() + newParams);

		orderList = orderService.findOrderList(order, pageBean);
		mainPage = "order.jsp";
		return "list";
	}

    public String refundList() throws Exception {

	    order.setOrderType(Constant.OrderType.REFUND);
        if (StringUtil.isNotEmpty(keyword)) {
            User e = new User();
            e.setNickname(keyword);
            order.setUser(e);
        }

        if (StringUtil.isNotEmpty(timeranger)) {
            String[] timeStartEnd = timeranger.split(" - ");

            if (timeStartEnd.length == 2) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(timeStartEnd[0]);
                order.setCreateTime(date);
                date = sdf.parse(timeStartEnd[1]);
                order.setFinishTime(date);
            }
        }

        int pageSize = Integer.parseInt(PropertyUtil.getPropertyByName2("constant.properties", "adminpagesize"));
        if (currentPage == 0) {
            currentPage = 1;
        }
        PageBean pageBean = new PageBean(currentPage, pageSize);
        long count = orderService.getOrderCount(order);
        totalPage = (int) (count / pageSize);
        if ((count % pageSize) > 0) {
            totalPage++;
        }
        String newParams = (StringUtil.isNotEmpty(keyword) ? "&keyword=" + keyword : "")
                + (StringUtil.isNotEmpty(timeranger) ? "&timeranger=" + timeranger : "");
        pageCode = PageUtil.getPagination(request.getContextPath() + "/Order_refundList.action", count, currentPage, pageSize,
                newParams.replaceFirst("&", ""));

        orderList = orderService.findOrderList(order, pageBean);
        mainPage = "refund_order.jsp";
        return "list";
    }

    public String borrowList() throws Exception {

        order.setOrderType(Constant.OrderType.BORROW);

        if (StringUtil.isNotEmpty(keyword)) {
            User e = new User();
            e.setNickname(keyword);
            order.setUser(e);
        }

        if (StringUtil.isNotEmpty(timeranger)) {
            String[] timeStartEnd = timeranger.split(" - ");

            if (timeStartEnd.length == 2) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date date = sdf.parse(timeStartEnd[0]);
                order.setCreateTime(date);
                date = sdf.parse(timeStartEnd[1]);
                order.setFinishTime(date);
            }
        }

        int pageSize = Integer.parseInt(PropertyUtil.getPropertyByName2("constant.properties", "adminpagesize"));
        if (currentPage == 0) {
            currentPage = 1;
        }
        PageBean pageBean = new PageBean(currentPage, pageSize);
        long count = orderService.getOrderCount(order);
        totalPage = (int) (count / pageSize);
        if ((count % pageSize) > 0) {
            totalPage++;
        }
        String newParams = (StringUtil.isNotEmpty(keyword) ? "&keyword=" + keyword : "")
                + (StringUtil.isNotEmpty(timeranger) ? "&timeranger=" + timeranger : "");
        pageCode = PageUtil.getPagination(request.getContextPath() + "/Order_borrowList.action", count, currentPage, pageSize,
                newParams.replaceFirst("&", ""));

        orderList = orderService.findOrderList(order, pageBean);
        mainPage = "borrow_order.jsp";
        return "list";
    }
	
	public String showlist() throws Exception {
		User currentUser = UserUtil.getUserFromSession(request);
		orderList.clear();
		orderList.addAll(currentUser.getOrders());
		return "showlist";
	}
	
	public String showDetail() throws Exception {
		order = orderService.findOrderById(orderId);
		return "showDetail";
	}

	public String print() throws Exception {
		order = orderService.findOrderById(orderId);
		return "print";
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
