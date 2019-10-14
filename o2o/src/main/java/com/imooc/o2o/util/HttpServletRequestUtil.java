package com.imooc.o2o.util;

import javax.servlet.http.HttpServletRequest;

public class HttpServletRequestUtil {
    //将根据键将请求体中对应的值转换成我们需要的类型
    public static int getInt(HttpServletRequest request,String key){
        try {
            return Integer.decode(request.getParameter(key));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static long getLong(HttpServletRequest request,String key){
        try {
            return Long.valueOf(request.getParameter(key));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public static double getDouble(HttpServletRequest request,String key){
        try {
            return Double.valueOf(request.getParameter(key));
        } catch (NumberFormatException e) {
            return -1d;
        }
    }

    public static Boolean getBoolean(HttpServletRequest request,String key){
        try {
            return Boolean.valueOf(request.getParameter(key));
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static String getString(HttpServletRequest request,String key){
        try {
            String string = request.getParameter(key);
            if (string!=null){
                string=string.trim();
            }
            if ("".equals(string)){
                string=null;
            }
            return string;
        } catch (Exception e) {
            return null;
        }
    }
}
