package com.banzhuan.util;

import java.util.ArrayList;  
import java.util.List;  
  
import javax.servlet.http.Cookie;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;
  
public class CookieUtil {   
  
	public static String getCookie(HttpServletRequest request, String key) {  
        Cookie[] cookies = request.getCookies();  
        if (cookies != null) {  
            for (Cookie cookie : cookies) {  
                if (key.equals(cookie.getName())) {  
                    String value = cookie.getValue();  
                    //System.out.println("cookieValue: " + cookie.getValue());  
                    return value;  
                }  
            }  
        }  
        return null;  
    }  
    
	public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String key) {  
        Cookie[] cookies = request.getCookies();   
        if (cookies != null) {  
            for (Cookie cookie : cookies) {  
                if (key.equals(cookie.getName())) {  
                	cookie.setMaxAge(0);
                	response.addCookie(cookie);
                }  
            }  
        }  
    }  
    
    
  
}  
