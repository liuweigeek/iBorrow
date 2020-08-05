package com.zhinang.iborrow.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zhinang.iborrow.constant.Constant;
import com.zhinang.iborrow.entity.Navi;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.Topic;
import com.zhinang.iborrow.service.NaviService;
import com.zhinang.iborrow.service.TopicService;
import com.zhinang.iborrow.util.PageUtil;
import com.zhinang.iborrow.util.PropertyUtil;
import com.zhinang.iborrow.util.ResponseUtil;
import com.zhinang.iborrow.util.StringUtil;
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
public class NaviAction extends ActionSupport implements ModelDriven<Navi>, ServletRequestAware {
    private static final long serialVersionUID = 1L;
    private Navi navi = new Navi();

    @Resource
    private NaviService naviService;

    @Resource
    private TopicService topicService;

    private HttpServletRequest request;
    private String mainPage;
    private int naviId;
    private String ids;
    private List<Topic> topicList = new ArrayList<Topic>();
    private File naviBackground;
    private String naviBackgroundFileName;

    private int currentPage;
    private int totalPage;
    private String pageCode;

    @Override
    public Navi getModel() {
        return this.navi;
    }

    public void setNaviService(NaviService naviService) {
        this.naviService = naviService;
    }

    public void setTopicService(TopicService topicService) {
        this.topicService = topicService;
    }

    public String getMainPage() {
        return this.mainPage;
    }

    public void setMainPage(String mainPage) {
        this.mainPage = mainPage;
    }

    public int getNaviId() {
        return this.naviId;
    }

    public void setNaviId(int naviId) {
        this.naviId = naviId;
    }

    public String getIds() {
        return this.ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public List<Topic> getTopicList() {
        return this.topicList;
    }

    public void setTopicList(List<Topic> topicList) {
        this.topicList = topicList;
    }

    public File getNaviBackground() {
        return this.naviBackground;
    }

    public void setNaviBackground(File naviBackground) {
        this.naviBackground = naviBackground;
    }

    public String getNaviBackgroundFileName() {
        return this.naviBackgroundFileName;
    }

    public void setNaviBackgroundFileName(String naviBackgroundFileName) {
        this.naviBackgroundFileName = naviBackgroundFileName;
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
        long count = naviService.getNaviCount(navi);
        totalPage = (int) (count / pageSize);
        if ((count % pageSize) > 0) {
            totalPage++;
        }
        pageCode = PageUtil.getPagination(request.getContextPath() + "/Navi_list.action", count, currentPage,
                pageSize, null);

        topicList = topicService.findTopicList(null, pageBean);
        mainPage = "navi.jsp";
        return "list";
    }

    public String delete() throws Exception {
        JSONObject result = new JSONObject();
        Navi e = naviService.findNaviById(naviId);
        naviService.deleteNavi(e);
        refreshIndexData();
        result.put("success", true);
        ResponseUtil.write(ServletActionContext.getResponse(), result);
        return null;
    }

    public String deleteList() throws Exception {
        JSONObject result = new JSONObject();
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            Navi e = naviService.findNaviById(Integer.parseInt(idsStr[i]));
            naviService.deleteNavi(e);
        }
        refreshIndexData();
        result.put("success", true);
        ResponseUtil.write(ServletActionContext.getResponse(), result);
        return null;
    }

    public String save() throws Exception {
        if (naviBackground != null) {
            String imageName = StringUtil.getRandomString();
            /*String realPath = ServletActionContext.getServletContext().getRealPath("/images/navi");*/
            String realPath = Constant.IMG_PATH + "navi";
            String imageFile = imageName + naviBackgroundFileName
                    .substring(naviBackgroundFileName.lastIndexOf("."), naviBackgroundFileName.length());
            File saveFile = new File(realPath, imageFile);
            FileUtils.copyFile(naviBackground, saveFile);
            navi.setBackground("/iborrow/images/navi/" + imageFile);
        }

        naviService.saveNavi(navi);
        refreshIndexData();
        return "save";
    }

    public void refreshIndexData() {
        InitAction.refreshNavi();
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}