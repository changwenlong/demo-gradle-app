package com.jadyer.demo.comm.util;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * JSR303验证工具
 * @version v1.4
 * @history v1.4-->简化例外属性的判断方式
 * @history v1.3-->增加一些常用的pojo配置例子
 * @history v1.2-->部分细节优化及增加描述：验证对象若其父类的属性也有验证注解则会一并验证
 * @history v1.1-->增加将验证的错误信息存入Map<String,String>后返回的<code>validateToMap()<code>方法
 * @history v1.0-->新建
 * Created by 玄玉<http://jadyer.cn/> on 2015/06/09 23:25.
 */
public final class ValidatorUtil {
    private static Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    private ValidatorUtil() {}


    /**
     * 验证对象中的属性的值是否符合注解定义
     * @param obj 需要验证的对象，若其父类的属性也有验证注解则会一并验证
     * @return 返回空字符串""表示验证通过，否则返回错误信息
     */
    public static String validate(Object obj){
        return validate(obj, new String[]{});
    }


    /**
     * 验证对象中的属性的值是否符合注解定义
     * @param obj             需要验证的对象，若其父类的属性也有验证注解则会一并验证
     * @param exceptFieldName 不需要验证的属性
     * @return 返回空字符串""表示验证通过，否则返回错误信息
     */
    public static String validate(Object obj, String... exceptFieldName){
        StringBuilder sb = new StringBuilder();
        if(null == obj){
            return "被验证对象不能为null";
        }
        Set<ConstraintViolation<Object>> validateSet = validator.validate(obj);
        for(ConstraintViolation<Object> constraintViolation : validateSet){
            String field = constraintViolation.getPropertyPath().toString();
            String message = constraintViolation.getMessage();
            if(!StringUtils.equalsAnyIgnoreCase(field, exceptFieldName)){
                //id:最小不能小于1
                //name:不能为空
                sb.append(field).append(": ").append(message).append("\r\n");
            }
        }
        if(StringUtils.isNotBlank(sb)){
            sb.insert(0, "\r\n");
        }
        return sb.toString();
    }


    /**
     * 验证对象中的属性的值是否符合注解定义
     * @param obj 需要验证的对象，若其父类的属性也有验证注解则会一并验证
     * @return 返回空Map<String, String>(not null)表示验证通过，否则会将各错误字段作为key放入Map,value为错误信息
     */
    public static Map<String, String> validateToMap(Object obj){
        return validateToMap(obj, new String[]{});
    }


    /**
     * 验证对象中的属性的值是否符合注解定义
     * @param obj             需要验证的对象，若其父类的属性也有验证注解则会一并验证
     * @param exceptFieldName 不需要验证的属性
     * @return 返回空Map<String, String>(not null)表示验证通过，否则会将各错误字段作为key放入Map,value为错误信息
     */
    public static Map<String, String> validateToMap(Object obj, String... exceptFieldName){
        Map<String, String> resultMap = new HashMap<>();
        if(null == obj){
            throw new NullPointerException("被验证对象不能为null");
        }
        Set<ConstraintViolation<Object>> validateSet = validator.validate(obj);
        for(ConstraintViolation<Object> constraintViolation : validateSet){
            String field = constraintViolation.getPropertyPath().toString();
            String message = constraintViolation.getMessage();
            if(!StringUtils.equalsAnyIgnoreCase(field, exceptFieldName)){
                resultMap.put(field, message);
            }
        }
        return resultMap;
    }
}