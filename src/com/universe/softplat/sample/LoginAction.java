package com.universe.softplat.sample;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

public class LoginAction extends DispatchAction{
	
	private static Map userMap = new HashMap();
	
	public static void initUser(Properties pro){
		int userCount = Integer.parseInt(pro.getProperty("login.userCount"));
		for(int i = 0;i<userCount;i++){
	        String userName = pro.getProperty("login.user"+(i+1)+".userName");
	        String password = pro.getProperty("login.user"+(i+1)+".password");
	        String role = pro.getProperty("login.user"+(i+1)+".role");
	        if(userName != null && !"".equals(userName)){
	        	userMap.put(userName, new UserObject(userName,password,role));
	        }
		}
	}

    public ActionForward login(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse)
        throws IOException
    {
        String userName = httpservletrequest.getParameter("j_username");
        String password = httpservletrequest.getParameter("j_password");
        
        userName = userName == null?null:userName.trim();
        password = password == null?null:password.trim();
        
        boolean ispass = false;
        if(userName == null || "".equals(userName))
        	ispass = false;
        if(password == null || "".equals(password)){
        	ispass = false;
        }else{
        	if(verifyUser(userName, password)){
	            httpservletrequest.getSession().setAttribute("uid", userName);
	            ispass = true;
        	}else
        		ispass = false;
        }
        if(ispass){
        	return actionmapping.findForward("success");
        }else{
        	httpservletrequest.setAttribute("result","loginFail");
        	return actionmapping.findForward("false");
        }
    }
    
    public ActionForward logout(ActionMapping actionmapping, ActionForm actionform, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse) throws IOException
	{
    	httpservletrequest.getSession().invalidate();
	    return actionmapping.findForward("false");
	}

    /**
     * 登陆校验
     */
    private static boolean verifyUser(String userName,String password){
    	UserObject userObject = (UserObject)userMap.get(userName);
    	if(userObject == null)
    		return false;
    	return password.equals(userObject.getPassword());
    }
    /**
     * 角色校验
     */    
    public static boolean isAdmin(HttpServletRequest httpservletrequest){
    	Object userName = httpservletrequest.getSession().getAttribute("uid");
    	if(userName == null)
    		return false;
    	Object userObject = userMap.get(userName);
    	if(userObject == null)
    		return false;
    	return ((UserObject)userObject).isAdmin();
    }
}
