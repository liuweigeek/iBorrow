package com.zhinang.iborrow.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zhinang.iborrow.constant.Constant;
import com.zhinang.iborrow.entity.Comment;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.Product;
import com.zhinang.iborrow.entity.ProductType;
import com.zhinang.iborrow.entity.PutHistory;
import com.zhinang.iborrow.entity.User;
import com.zhinang.iborrow.service.ProductService;
import com.zhinang.iborrow.service.ProductTypeService;
import com.zhinang.iborrow.service.PutHistoryService;
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
import java.util.Date;
import java.util.List;

@Controller
public class ProductAction extends ActionSupport implements ModelDriven<Product>, ServletRequestAware {
    private static final long serialVersionUID = 1L;
    private Product product = new Product();

    @Resource
    private ProductService productService;
    @Resource
    private UserService userService;
    @Resource
    private PutHistoryService putHistoryService;

    @Resource
    private ProductTypeService productTypeService;
    private HttpServletRequest request;
    private String mainPage;
    private List<File> cover;
    private List<String> coverFileName;
    private List<String> coverContentType;
    private int productId;
    private int newInventory;
    private String ids;

    private int currentPage;
    private int totalPage;
    private String pageCode;

    private List<Product> productList = new ArrayList<Product>();
    private List<ProductType> productTypeList = new ArrayList<ProductType>();
    private List<Comment> commentList = new ArrayList<Comment>();
    private List<Product> cartList = new ArrayList<Product>();
    private List<Product> favoriteList = new ArrayList<Product>();

    private List<Integer> productTypeId;

    private String keyword;

    private Integer hasAudio;

    //region setter and getter
    @Override
    public Product getModel() {
        return product;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getMainPage() {
        return mainPage;
    }

    public void setMainPage(String mainPage) {
        this.mainPage = mainPage;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setPutHistoryService(PutHistoryService putHistoryService) {
        this.putHistoryService = putHistoryService;
    }

    public void setProductTypeService(ProductTypeService productTypeService) {
        this.productTypeService = productTypeService;
    }

    public List<File> getCover() {
        return cover;
    }

    public void setCover(List<File> cover) {
        this.cover = cover;
    }

    public List<String> getCoverFileName() {
        return coverFileName;
    }

    public void setCoverFileName(List<String> coverFileName) {
        this.coverFileName = coverFileName;
    }

    public List<String> getCoverContentType() {
        return coverContentType;
    }

    public void setCoverContentType(List<String> coverContentType) {
        this.coverContentType = coverContentType;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getNewInventory() {
        return newInventory;
    }

    public void setNewInventory(int newInventory) {
        this.newInventory = newInventory;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
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

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public List<ProductType> getProductTypeList() {
        return productTypeList;
    }

    public void setProductTypeList(List<ProductType> productTypeList) {
        this.productTypeList = productTypeList;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public List<Product> getCartList() {
        return cartList;
    }

    public void setCartList(List<Product> cartList) {
        this.cartList = cartList;
    }

    public List<Product> getFavoriteList() {
        return favoriteList;
    }

    public void setFavoriteList(List<Product> favoriteList) {
        this.favoriteList = favoriteList;
    }

    public Integer getHasAudio() {
        return hasAudio;
    }

    public void setHasAudio(Integer hasAudio) {
        this.hasAudio = hasAudio;
    }

    public List<Integer> getProductTypeId() {
        return productTypeId;
    }

    public void setProductTypeId(List<Integer> productTypeId) {
        this.productTypeId = productTypeId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    //endregion

    public String list() throws Exception {

        if (StringUtil.isNotEmpty(keyword)) {
            product.setName(keyword);
        }

        int pageSize = Integer.parseInt(PropertyUtil.getPropertyByName2("constant.properties", "adminpagesize"));
        if (currentPage == 0) {
            currentPage = 1;
        }
        PageBean pageBean = new PageBean(currentPage, pageSize);
        long count = productService.getProductCount(product);
        totalPage = (int) (count / pageSize);
        if ((count % pageSize) > 0) {
            totalPage++;
        }
        pageCode = PageUtil.getPagination(request.getContextPath() + "/Product_list.action", count, currentPage,
                pageSize, StringUtil.isNotEmpty(keyword) ? "keyword=" + keyword : "");

        productList = productService.findProductList(product, pageBean);
        productTypeList = productTypeService.findProductTypeList(null, null);
        mainPage = "product.jsp";
        return "list";
    }

    public String delete() throws Exception {
        JSONObject result = new JSONObject();
        Product e = productService.findProductById(productId);
        productService.deleteProduct(e);
        result.put("success", true);
        ResponseUtil.write(ServletActionContext.getResponse(), result);
        return null;
    }

    public String deleteList() throws Exception {
        JSONObject result = new JSONObject();
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            Product e = productService.findProductById(Integer.parseInt(idsStr[i]));
            productService.deleteProduct(e);
        }
        result.put("success", true);
        ResponseUtil.write(ServletActionContext.getResponse(), result);
        return null;
    }

    public String editIntroduction() throws Exception {
        product = productService.findProductById(productId);
        return "editIntroduction";
    }

    public String saveIntroduction() throws Exception {
        Product e = productService.findProductById(product.getId());
        e.setIntroduction(product.getIntroduction());
        productService.saveProduct(e);
        return "success";
    }

    public String save() throws Exception {

        if (null != cover && cover.size() > 0) {
            List<String> currentCovers = product.getCovers();
            for (int i = 0; i < product.getCovers().size(); i++) {
                product.getCovers().remove(currentCovers.get(i));
            }
            for (int i = 0; i < cover.size(); i++) {
                String currentFileName = coverFileName.get(i);
                String imageName = StringUtil.getRandomString();
                /*String realPath = ServletActionContext.getServletContext().getRealPath("/images/product_cover");*/
                String realPath = Constant.IMG_PATH + "product_cover";
                String imageFile = imageName + currentFileName.substring(currentFileName.lastIndexOf("."),
                        currentFileName.length());
                File saveFile = new File(realPath, imageFile);
                FileUtils.copyFile(cover.get(i), saveFile);
                product.getCovers().add("/iborrow/images/product_cover/" + imageFile);
            }
        } else if ((product.getId() != null) && (product.getId() != 0)) {
            Product e = productService.findProductById(product.getId());
            product.setCovers(e.getCovers());
        } else {
            product.getCovers().clear();
        }

		/*if (StringUtil.isEmpty(product.getIntroduction())) {
			if ((product.getId() != null) && (product.getId() != 0)) {
				Product e = productService.findProductById(product.getId());
				product.setIntroduction(e.getIntroduction());
			}
		}*/

        if (null != productTypeId && productTypeId.size() > 0) {
            for (int i = 0; i < productTypeId.size(); i++) {
                ProductType e = productTypeService.findProductTypeById(productTypeId.get(i));
                product.getProductTypes().add(e);
            }
        }

        if (null != this.getHasAudio() && this.getHasAudio() == 1) {
            product.setAudio(true);
        } else {
            product.setAudio(false);
        }

        product.setStar(0);
        if (product.getInventory() == null) {
            product.setInventory(0);
        }
        product.setRemainder(product.getInventory());
        productService.saveProduct(product);

        Product e = productService.findProductByNameAndNumber(product.getName(), product.getNumber());
        if (product != null) {
            PutHistory newPutHistory = new PutHistory();
            newPutHistory.setProduct(e);
            newPutHistory.setCount(e.getInventory());
            newPutHistory.setPutTime(new Date());
            putHistoryService.savePutHistory(newPutHistory);
        }

        return "success";
    }

    public String modify() throws Exception {
        product = productService.findProductById(productId);
        //productTypeList = productTypeService.findProductTypeList(null, null);
        return "modify";
    }

    public String add() throws Exception {
        //productTypeList = productTypeService.findProductTypeList(null, null);
        return "add";
    }

    public String show() throws Exception {
        User currentUser = UserUtil.getUserFromSession(request);
        cartList.addAll(currentUser.getCartProducts());
        favoriteList.addAll(currentUser.getFavoriteProducts());
        product = productService.findProductById(this.productId);
        if (null != product.getComment()) {
            commentList.addAll(product.getComment());
        }
        return "show";
    }

    public String addInventory() throws Exception {
        Product e = productService.findProductById(productId);
        e.setInventory((e.getInventory() == null ? 0 : e.getInventory()) + newInventory);
        e.setRemainder((e.getRemainder() == null ? 0 : e.getRemainder()) + newInventory);
        productService.saveProduct(e);

        PutHistory newPutHistory = new PutHistory();
        newPutHistory.setProduct(e);
        newPutHistory.setCount(newInventory);
        newPutHistory.setPutTime(new Date());
        putHistoryService.savePutHistory(newPutHistory);

        JSONObject result = new JSONObject();
        result.put("success", true);
        ResponseUtil.write(ServletActionContext.getResponse(), result);
        return null;
    }

    public String addToCart() throws Exception {
        User currentUser = UserUtil.getUserFromSession(request);
        Product e = productService.findProductById(productId);
        currentUser.getCartProducts().add(e);
        userService.saveUser(currentUser);

        JSONObject result = new JSONObject();
        result.put("success", true);
        ResponseUtil.write(ServletActionContext.getResponse(), result);
        return null;
    }

    public String addToFavorite() throws Exception {
        User currentUser = UserUtil.getUserFromSession(request);
        Product e = productService.findProductById(productId);
        currentUser.getFavoriteProducts().add(e);
        userService.saveUser(currentUser);

        JSONObject result = new JSONObject();
        result.put("success", true);
        ResponseUtil.write(ServletActionContext.getResponse(), result);
        return null;
    }

    public String removeFromCart() throws Exception {
        User currentUser = UserUtil.getUserFromSession(request);

        List<Product> myCarts = new ArrayList<>();
        myCarts.addAll(currentUser.getCartProducts());

        for (int i = 0; i < myCarts.size(); i++) {
            if (myCarts.get(i).getId() == productId) {
                currentUser.getCartProducts().remove(myCarts.get(i));
            }
        }

        userService.saveUser(currentUser);
        JSONObject result = new JSONObject();
        result.put("success", true);
        ResponseUtil.write(ServletActionContext.getResponse(), result);
        return null;
    }

    public String removeFromFavorite() throws Exception {
        User currentUser = UserUtil.getUserFromSession(request);

        List<Product> myFavorites = new ArrayList<>();
        myFavorites.addAll(currentUser.getFavoriteProducts());

        for (int i = 0; i < myFavorites.size(); i++) {
            if (myFavorites.get(i).getId() == productId) {
                currentUser.getFavoriteProducts().remove(myFavorites.get(i));
            }
        }

        userService.saveUser(currentUser);
        JSONObject result = new JSONObject();
        result.put("success", true);
        ResponseUtil.write(ServletActionContext.getResponse(), result);
        return null;
    }

    public String search() throws Exception {
        User currentUser = UserUtil.getUserFromSession(request);
        cartList.addAll(currentUser.getCartProducts());
        favoriteList.addAll(currentUser.getFavoriteProducts());

        int pageSize = Integer.parseInt(PropertyUtil.getPropertyByName2("constant.properties", "pagesize"));
        if (currentPage == 0) {
            currentPage = 1;
        }
        PageBean pageBean = new PageBean(currentPage, pageSize);
        long count = productService.getProductCount(keyword);
        totalPage = (int) (count / pageSize);
        if ((count % pageSize) > 0) {
            totalPage++;
        }
        productList = productService.searchProductList(keyword, pageBean);
        return "show_list";
    }

    /**
     * 在后台管理用户列表中添加借阅时搜索书籍
     *
     * @return
     * @throws Exception
     */
    public String searchFromUser() throws Exception {
        productList = productService.searchProductListInStock(keyword, null);
        JSONObject result = new JSONObject();
        if (productList.size() > 0) {
            result.put("success", true);
            List<Product> products = new ArrayList<>();
            for (Product e : productList) {
                e = simplifyProduct(e);
                products.add(e);
            }
            result.put("products", products);
        } else {
            result.put("errorMsg", "无匹配项");
        }
        ResponseUtil.write(ServletActionContext.getResponse(), result);
        return null;
    }

    /**
     * 简化Product，去除级联
     *
     * @param product
     * @return
     */
    public Product simplifyProduct(Product product) {
        Product e = new Product();
        e.setId(product.getId());
        e.setName(product.getName());
        e.setManufacturer(product.getManufacturer());
        e.setAuthor(product.getAuthor());
        e.setCovers(product.getCovers());
        return e;
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}