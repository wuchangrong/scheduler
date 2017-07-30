package com.universe.softplat.scheduler.server;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.universe.softplat.sample.LoginAction;
import com.universe.softplat.scheduler.SchedularManager;
import com.universe.softplat.scheduler.SchedularManagerFactory;
import com.universe.softplat.scheduler.dao.DBAccess;

public class ConfigSchedular extends HttpServlet{
    /**
	 * 字段功能描述
	 */
	private static final long serialVersionUID = 1L;
	//自定义Job配置路径
	private static String jobConfigFilePath;
	//quartz支撑库数据源
	private static String quartzDSName = "";
	//quartz支撑库类型
	private static String quartzDBType = "";
	
    private boolean performShutdown;
    private SchedularManager schedularManager;	
	
	public static String getJobConfigFilePath(){
		return ConfigSchedular.jobConfigFilePath;
	}
	
	public static String getQuartzDSName(){
		return quartzDSName;
	}
	
	public static String getQuartzDBType(){
		return quartzDBType;
	}
		
	public ConfigSchedular(){
        performShutdown = true;
        schedularManager = null;
    }

    public void init(ServletConfig cfg) throws ServletException{
        super.init(cfg);
        log("\u521D\u59CB\u5316\u8C03\u5EA6\u5668...");
        try{
        	//Job配置路径
        	ConfigSchedular.jobConfigFilePath = cfg.getServletContext().getRealPath("")+ cfg.getInitParameter("job-config-file");
            //quartz配置路径
        	String quartzConfigFile = cfg.getInitParameter("quartz-config-file");
            String shutdownPref = cfg.getInitParameter("shutdown-on-unload");
            if(shutdownPref != null)
                performShutdown = Boolean.valueOf(shutdownPref).booleanValue();
            if(quartzConfigFile != null){
                FileInputStream fileInputStream = new FileInputStream(new File(cfg.getServletContext().getRealPath("") + quartzConfigFile));
                Properties pro = new Properties();
                pro.load(fileInputStream);
                schedularManager = SchedularManagerFactory.getSchedularManager();
                schedularManager.initial(pro);
                //取出数据源名称和类型
                ConfigSchedular.quartzDSName = pro.getProperty("org.quartz.jobStore.dataSource");
                String dbDelegateClass = pro.getProperty("org.quartz.jobStore.driverDelegateClass").toUpperCase();
                System.out.println("dbDelegateClass==="+dbDelegateClass);
                if(dbDelegateClass.indexOf("DB2") >0 )
                	ConfigSchedular.quartzDBType = DBAccess.DB2;
                else if(dbDelegateClass.indexOf("ORACLE")>0)
                	ConfigSchedular.quartzDBType = DBAccess.ORACLE;
                else if(dbDelegateClass.indexOf("STDJDBC")>0)
                    ConfigSchedular.quartzDBType = DBAccess.MYSQL;                
                else
                	throw new RuntimeException("暂不支持该类数据库");
                System.out.println("dbtype==="+ConfigSchedular.quartzDBType);
                //用户管理
                LoginAction.initUser(pro);
            }else{
                throw new ServletException("\u521D\u59CB\u5316\u8C03\u5EA6\u5668\u51FA\u9519\uFF1A\u6CA1\u6709\u914D\u7F6E\u8C03\u5EA6\u5668\u7684\u914D\u7F6E\u6587\u4EF6");
            }
            String startOnLoad = cfg.getInitParameter("start-schedular-on-load");
            if(startOnLoad == null || Boolean.valueOf(startOnLoad).booleanValue()){
                schedularManager.start();
                log("\u8C03\u5EA6\u5668\u5DF2\u542F\u52A8");
            }else{
                log("\u8C03\u5EA6\u5668\u521D\u59CB\u5316\u4E86\uFF0C\u4F46\u662F\u8FD8\u6CA1\u542F\u52A8");
            }
        }
        catch(Exception e)
        {
            log("\u521D\u59CB\u5316\u8C03\u5EA6\u5668\u51FA\u9519" + e.toString());
            throw new ServletException(e);
        }
    }

    public void destroy()
    {
        if(!performShutdown)
            return;
        try
        {
            if(schedularManager != null)
                schedularManager.destroy();
        }
        catch(Exception e)
        {
            log("\u5173\u95ED\u8C03\u5EA6\u5668\u51FA\u9519: " + e.toString());
            e.printStackTrace();
        }
        log("\u5173\u95ED\u8C03\u5EA6\u5668\u6210\u529F");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        response.sendError(403);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        response.sendError(403);
    }
}
