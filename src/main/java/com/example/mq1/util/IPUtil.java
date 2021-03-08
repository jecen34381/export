package com.example.mq1.util;

import javax.servlet.http.HttpServletRequest;
import java.util.StringTokenizer;

public class IPUtil {
	public static String getClientIP(HttpServletRequest request){
		String ip = request.getHeader("X-Forwarded-For");
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		    ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		    ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		    ip = request.getRemoteAddr();
		}
		StringTokenizer st = new StringTokenizer(ip, ",");
		while(st.hasMoreElements()){
			String str = (String)st.nextElement();
			if("unknown".equalsIgnoreCase(str)){
				ip = str;
			};
		}
		return ip;
	}
}
