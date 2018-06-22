package com.jadyer.demo.sdk.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具类
 * -----------------------------------------------------------------------------------------------------------
 * 【遗留问题（来自Alibaba Java Coding Guidelines的提示）】
 * 没有回收自定义的ThreadLocal变量
 * 尤其在线程池场景下，线程经常会被复用，如果不清理自定义的ThreadLocal变量
 * 可能会影响后续业务逻辑和造成内存泄露等问题。尽量在代理中使用try-finally块进行回收
 * -----------------------------------------------------------------------------------------------------------
 * @version v2.3
 * @history v2.3-->修复获取logger方法中，可能会获取到其它线程绑定的logger，导致获取到的不是想要的logger
 * @history v2.2-->优化Log获取为显式指定所要获取的Log,未指定时默认取上一次的Log,没有上一次的则取defaultLog
 * @history v2.1-->新增多线程情景下的日志集中打印功能
 * @history v2.0-->新增日志的数据库保存和邮件发送功能
 * @history v1.0-->通过<code>java.lang.ThreadLocal</code>实现日志记录器
 * -----------------------------------------------------------------------------------------------------------
 * Created by 玄玉<http://jadyer.cn/> on 2012/11/18 18:19.
 */
public final class LogUtil {
    private LogUtil(){}

    private static ThreadLocal<Logger> currentLoggerMap = new ThreadLocal<>();

    private static Logger defaultLogger = LoggerFactory.getLogger("defaultLogger");

    /**
     * 获取当前线程中的日志记录器
     */
    public static Logger getLogger() {
        Logger logger = currentLoggerMap.get();
        if(null == logger){
            return defaultLogger;
        }else{
            return logger;
        }
    }
}