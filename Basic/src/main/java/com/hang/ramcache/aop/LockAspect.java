package com.hang.ramcache.aop;

import com.hang.ramcache.lock.ChainLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author ZhangHang
 * @create 2018-01-22 20:33
 **/
@Aspect(name = "Aspect")
public class LockAspect {
    private static final Logger logger= LoggerFactory.getLogger(LockAspect.class);
    private ConcurrentHashMap<Method,LockExtractor> extractors=new ConcurrentHashMap<Method, LockExtractor>();

    public LockAspect(){}

    @Around("@annotation(autoLocked)")
    public Object execute(ProceedingJoinPoint pjb, AutoLocked autoLocked) throws Throwable{
        Signature sign=pjb.getSignature();
        if(!(sign instanceof MethodSignature)){
            logger.error("不支持的拦截切面:{}",sign);
            return pjb.proceed(pjb.getArgs());
        }else {
            Method method=((MethodSignature)sign).getMethod();
            LockExtractor extractor=this.extractors.get(method);
            if(extractor==null){
                extractor=this.createLockExtractor(method);
            }

            Object[] args=pjb.getArgs();
            ChainLock lock=extractor.getLock(args);
            if(lock==null){
                return pjb.proceed(args);
            }else {
                lock.lock();

                Object var8;
                try {
                    var8=pjb.proceed(args);
                }finally {
                    lock.unlock();
                }

                return var8;
            }
        }
    }

    private LockExtractor createLockExtractor(Method method){
        LockExtractor result=LockExtractor.valueOf(method);
        LockExtractor prev=this.extractors.putIfAbsent(method,result);
        return prev==null?result:prev;
    }
}
