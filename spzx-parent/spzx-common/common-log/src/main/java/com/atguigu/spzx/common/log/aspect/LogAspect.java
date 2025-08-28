package com.atguigu.spzx.common.log.aspect;

import com.atguigu.spzx.common.log.annotation.Log;
import com.atguigu.spzx.common.log.service.AsyncOperLogService;
import com.atguigu.spzx.common.log.utils.LogUtil;
import com.atguigu.spzx.model.entity.system.SysOperLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
//@Slf4j
public class LogAspect {            // 环绕通知切面类定义

    @Autowired
    private AsyncOperLogService operLogService ;

    //环绕通知
    @Around(value = "@annotation(sysLog)")
    public Object doAroundAdvice(ProceedingJoinPoint joinPoint , Log sysLog) {

//        String title = sysLog.title();
//        int businessType = sysLog.businessType();
//        System.out.println("title: " + title + " ::businessType: " + businessType);

        SysOperLog sysOperLog = new SysOperLog();
        LogUtil.beforeHandleLog(sysLog , joinPoint , sysOperLog) ;
        //业务方法
        Object proceed = null;
        try {
            proceed = joinPoint.proceed();              // 执行业务方法
           // System.out.println("在业务方法之后执行...");
            LogUtil.afterHandlLog(sysLog , proceed , sysOperLog , 0 , null) ;
        } catch (Throwable e) {                         // 代码执行进入到catch中，业务方法执行产生异常
            // 业务方法执行产生异常
            e.printStackTrace();                                // 打印异常信息
            LogUtil.afterHandlLog(sysLog , proceed , sysOperLog , 1 , e.getMessage()) ;
            throw new RuntimeException();
        }
        // 保存日志数据
        operLogService.saveSysOperLog(sysOperLog);

        // 返回执行结果
        return proceed ;
    }
}