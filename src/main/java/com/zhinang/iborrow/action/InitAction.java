package com.zhinang.iborrow.action;

import com.zhinang.iborrow.entity.HomeClassZone;
import com.zhinang.iborrow.entity.HomeZone;
import com.zhinang.iborrow.entity.Navi;
import com.zhinang.iborrow.entity.ProductType;
import com.zhinang.iborrow.service.HomeClassZoneService;
import com.zhinang.iborrow.service.HomeZoneService;
import com.zhinang.iborrow.service.NaviService;
import com.zhinang.iborrow.service.ProductTypeService;
import com.zhinang.iborrow.util.PropertyUtil;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

@Component
public class InitAction implements ServletContextListener, ApplicationContextAware {
    private static ApplicationContext applicationContext;
    private static ServletContext application;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        InitAction.applicationContext = applicationContext;
    }

    public ServletContext getApplication() {
        return application;
    }

    public void setApplication(ServletContext application) {
        InitAction.application = application;
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

    //@Override
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        InitAction.application = servletContextEvent.getServletContext();
        String webpagename = PropertyUtil.getPropertyByName2("constant.properties", "webpagename");
        application.setAttribute("webpagename", webpagename);
        refreshData();
    }

    public static void refreshHomeZone() {
        HomeZoneService homeZoneService = (HomeZoneService) applicationContext.getBean("homeZoneService");
        List<HomeZone> homeZoneList = homeZoneService.findHomeZoneList(null, null);
        application.setAttribute("homeZoneList", homeZoneList);
    }

    public static void refreshHomeClassZone() {
        HomeClassZoneService homeClassZoneService = (HomeClassZoneService) applicationContext.getBean("homeClassZoneService");
        List<HomeClassZone> homeClassZoneList = homeClassZoneService.findHomeClassZoneList(null, null);
        application.setAttribute("homeClassZoneList", homeClassZoneList);
    }

    public static void refreshProductType() {
        ProductTypeService productTypeService = (ProductTypeService) applicationContext.getBean("productTypeService");
        List<ProductType> productTypeList = productTypeService.findProductTypeList(null, null);
        application.setAttribute("productTypeList", productTypeList);
    }

    public static void refreshNavi() {
        NaviService naviService = (NaviService) applicationContext.getBean("naviService");
        List<Navi> naviList = naviService.findNaviList(null, null);
        application.setAttribute("naviList", naviList);
    }

    public static void refreshData() {

        HomeZoneService homeZoneService = (HomeZoneService) applicationContext.getBean("homeZoneService");
        List<HomeZone> homeZoneList = homeZoneService.findHomeZoneList(null, null);
        application.setAttribute("homeZoneList", homeZoneList);

        HomeClassZoneService homeClassZoneService = (HomeClassZoneService) applicationContext.getBean("homeClassZoneService");
        List<HomeClassZone> homeClassZoneList = homeClassZoneService.findHomeClassZoneList(null, null);
        application.setAttribute("homeClassZoneList", homeClassZoneList);

        ProductTypeService productTypeService = (ProductTypeService) applicationContext.getBean("productTypeService");
        List<ProductType> productTypeList = productTypeService.findProductTypeList(null, null);
        application.setAttribute("productTypeList", productTypeList);

        NaviService naviService = (NaviService) applicationContext.getBean("naviService");
        List<Navi> naviList = naviService.findNaviList(null, null);
        application.setAttribute("naviList", naviList);

    }
}