package com.imooc.o2o.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

public class CodeUtil {
    public static boolean checkVerifyCode(HttpServletRequest request){
//        获取用户输入的验证码
        String verifyCodeActual = HttpServletRequestUtil.getString(request, "verifyCodeActual");
//        获取图片的验证码
        String verifyCodeExpected = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (verifyCodeActual==null || !verifyCodeExpected.equals(verifyCodeActual)){
            System.out.println(verifyCodeActual);
            return false;
        }
        else
            return true;
    }
}
