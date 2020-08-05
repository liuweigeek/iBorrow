package com.zhinang.iborrow.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zhinang.iborrow.entity.Comment;
import com.zhinang.iborrow.entity.Product;
import com.zhinang.iborrow.entity.User;
import com.zhinang.iborrow.service.CommentService;
import com.zhinang.iborrow.service.ProductService;
import com.zhinang.iborrow.service.UserService;
import com.zhinang.iborrow.util.ResponseUtil;
import com.zhinang.iborrow.util.UserUtil;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class CommentAction extends ActionSupport implements ModelDriven<Comment>, ServletRequestAware {
    private static final long serialVersionUID = 1L;
    private Comment comment = new Comment();

    private Product product = new Product();

    private Integer productId;

    @Resource
    private CommentService commentService;
    @Resource
    private UserService userService;
    @Resource
    private ProductService productService;

    private List<Comment> commentList = new ArrayList<Comment>();

    private int currentPage;
    private int totalPage;
    private String pageCode;

    private HttpServletRequest request;

    @Override
    public Comment getModel() {
        return this.comment;
    }

    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
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
        return null;
    }

    public String delete() throws Exception {
        return null;
    }

    public String deleteList() throws Exception {
        return null;
    }

    public String save() throws Exception {
        User currentUser = UserUtil.getUserFromSession(request);
        product = productService.findProductById(productId);

        comment.setUser(currentUser);
        comment.setTime(new Date());
        comment.setProduct(product);
        commentService.saveComment(comment);

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