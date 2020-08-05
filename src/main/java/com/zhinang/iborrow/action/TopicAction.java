package com.zhinang.iborrow.action;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zhinang.iborrow.entity.PageBean;
import com.zhinang.iborrow.entity.Topic;
import com.zhinang.iborrow.service.TopicService;
import com.zhinang.iborrow.util.PageUtil;
import com.zhinang.iborrow.util.PropertyUtil;
import com.zhinang.iborrow.util.ResponseUtil;
import com.zhinang.iborrow.util.StringUtil;
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
public class TopicAction extends ActionSupport implements ModelDriven<Topic>, ServletRequestAware {
    private static final long serialVersionUID = 1L;
    private Topic topic = new Topic();

    @Resource
    private TopicService topicService;

    private HttpServletRequest request;
    private String mainPage;
    private int topicId;
    private String ids;
    private List<Topic> topicList = new ArrayList<Topic>();

    private String keyword;

    private int currentPage;
    private int totalPage;
    private String pageCode;

    @Override
    public Topic getModel() {
        return this.topic;
    }

    public Topic getTopic() {
        return this.topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
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

    public int getTopicId() {
        return this.topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
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

    public String delete() throws Exception {
        JSONObject result = new JSONObject();
        Topic e = topicService.findTopicById(topicId);
        topicService.deleteTopic(e);
        result.put("success", true);
        ResponseUtil.write(ServletActionContext.getResponse(), result);
        return null;
    }

    public String deleteList() throws Exception {
        JSONObject result = new JSONObject();
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
            Topic e = topicService.findTopicById(Integer.parseInt(idsStr[i]));
            topicService.deleteTopic(e);
        }
        result.put("success", true);
        ResponseUtil.write(ServletActionContext.getResponse(), result);
        return null;
    }

    public String save() throws Exception {
        if (topic.getId() != null && topic.getId() != 0) {
            Topic e = topicService.findTopicById(topic.getId());
            topic.setPublishTime(e.getPublishTime());
        } else {
            topic.setPublishTime(new Date());
        }
        topic.setModifyTime(new Date());
        topicService.saveTopic(topic);
        return "save";
    }

    public String list() throws Exception {

        if (StringUtil.isNotEmpty(keyword)) {
            topic.setTitle(keyword);
        }

        int pageSize = Integer.parseInt(PropertyUtil.getPropertyByName2("constant.properties", "adminpagesize"));
        if (currentPage == 0) {
            currentPage = 1;
        }
        PageBean pageBean = new PageBean(currentPage, pageSize);
        long count = topicService.getTopicCount(topic);
        totalPage = (int) (count / pageSize);
        if ((count % pageSize) > 0) {
            totalPage++;
        }

        pageCode = PageUtil.getPagination(request.getContextPath() + "/Topic_list.action", count, currentPage,
                pageSize, StringUtil.isNotEmpty(keyword) ? "keyword=" + keyword : "");

        topicList = topicService.findTopicList(topic, pageBean);
        mainPage = "topic.jsp";
        return "list";
    }

    public String show() throws Exception {
        //topicList = topicService.findTopicList(null, null);
        topic = topicService.findTopicById(topicId);
        return "show";
    }

    public String modify() throws Exception {
        topic = topicService.findTopicById(topicId);
        return "modify";
    }

    @Override
    public void setServletRequest(HttpServletRequest request) {
        this.request = request;
    }
}