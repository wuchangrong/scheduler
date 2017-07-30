package com.universe.softplat.sample;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.universe.softplat.scheduler.util.ProjectConfigHelper;

public class AccountFilter
    implements Filter
{

    public AccountFilter()
    {
    }

    public void destroy()
    {
    }

    public void doFilter(ServletRequest servletrequest, ServletResponse servletresponse, FilterChain filterchain)
        throws IOException, ServletException{
        servletrequest.setCharacterEncoding(ProjectConfigHelper.PROJECT_CODINGKIND);
        HttpServletRequest httpservletrequest = (HttpServletRequest)servletrequest;
        HttpServletResponse httpservletresponse = (HttpServletResponse)servletresponse;
        HttpSession httpsession = httpservletrequest.getSession();
        String s = (String)httpsession.getAttribute("uid");
        String s1 = httpservletrequest.getRequestURI();
        String s2 = s1.substring(s1.indexOf("/", 1), s1.length());
        if((s == null || "".equals(s)) && !s2.endsWith("/login.jsp") && !s2.endsWith("/Scheduler-CheckLogin.do")){
            httpsession.setAttribute("uid", "");
            httpservletrequest.getRequestDispatcher("/softplat/pages/sample/login.jsp").forward(httpservletrequest, httpservletresponse);
        }else{
        	AccountHolder.setAccessorAccount(s);
            filterchain.doFilter(servletrequest, servletresponse);
        }
    }

    public void init(FilterConfig filterconfig)
        throws ServletException
    {
    }
}
