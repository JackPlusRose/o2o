package com.imooc.o2o.controller.checkingajax;

import com.imooc.o2o.util.CodeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(path = "/checkingAjax")
public class CheckingAjax {
    @RequestMapping(path = "/codeChecking",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> codeChecking(HttpServletRequest request){
        HashMap<String, Object> modelMap = new HashMap<String, Object>();
        if(!CodeUtil.checkVerifyCode(request)){
            modelMap.put("success",false);
            modelMap.put("errMsg","验证码输入错误!");
            return modelMap;
        }else{
            modelMap.put("success",true);
            return modelMap;
        }
    }
}
