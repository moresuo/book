package com.yhk.utils;

import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class WebUtils {
    public static <T>  T copyParamToBean(Map value, T bean){
        try {
            BeanUtils.populate(bean,value);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }
    public static int parseInt(String str,int defaultValue){

       if(str==null||str==""){
           return defaultValue;
       }else{
           return Integer.parseInt(str);
       }
    }
}
