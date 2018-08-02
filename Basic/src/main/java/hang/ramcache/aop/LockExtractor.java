package hang.ramcache.aop;

import com.hang.ramcache.lock.ChainLock;
import com.hang.ramcache.lock.LockUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author ZhangHang
 * @create 2018-01-25 18:35
 **/
public class LockExtractor {
    private static final Logger logger= LoggerFactory.getLogger(LockExtractor.class);
    private HashMap<Integer,IsLocked> lockArgs=new HashMap<Integer, IsLocked>();

    public LockExtractor(){
    }

    public static LockExtractor valueOf(Method method){
        LockExtractor result=new LockExtractor();
        Annotation[][] annos=method.getParameterAnnotations();

        for(int i=0;i<annos.length;i++){
            IsLocked isLocked=null;
            Annotation[] arr$=annos[i];
            int len$=arr$.length;

            for(int i$=0;i$<len$;i$++){
                Annotation anno=arr$[i$];
                if(anno instanceof IsLocked){
                    isLocked= (IsLocked) anno;
                    break;
                }
            }

            if(isLocked!=null){
                result.lockArgs.put(i,isLocked);
            }
        }
        return result;
    }

    public ChainLock getLock(Object[] args) throws IllegalAccessException {
        ArrayList<Object> lockObjs=new ArrayList<Object>();
        Iterator i$=this.lockArgs.entrySet().iterator();

        while (true){
            while (true){
                IsLocked isLocked;
                Object arg;
                do{
                    if(!i$.hasNext()){
                        if(lockObjs.isEmpty()){
                            if(logger.isDebugEnabled()){
                                logger.debug("没有获得锁目标");
                            }

                            return null;
                        }

                        if(logger.isDebugEnabled()){
                            logger.debug("锁目标为:{}", Arrays.toString(lockObjs.toArray()));
                        }

                        return LockUtils.getLock(lockObjs.toArray());
                    }

                    Map.Entry<Integer,IsLocked> entry= (Map.Entry<Integer, IsLocked>) i$.next();
                    isLocked=entry.getValue();
                    arg=args[((Integer)entry.getKey()).intValue()];
                }while (arg==null);

                if(!isLocked.element()){
                    lockObjs.add(arg);
                }else {
                    Iterator i$$;
                    Object obj;
                    if(arg instanceof Collection){
                        i$$=((Collection)arg).iterator();

                        while (i$$.hasNext()){
                            obj=i$$.next();
                            if(obj!=null){
                                lockObjs.add(obj);
                            }
                        }
                    }else if(arg.getClass().isArray()){
                        for (int i=0;i< Array.getLength(arg);i++){
                            obj=Array.get(arg,i);
                            if(obj!=null){
                                lockObjs.add(obj);
                            }
                        }
                    }else if(arg instanceof Map){
                        i$$=((Map)arg).values().iterator();

                        while (i$$.hasNext()){
                            obj=i$$.next();
                            if(obj!=null){
                                lockObjs.add(obj);
                            }
                        }
                    }else {
                        logger.error("不支持的类型[{}]",args.getClass().getName());
                    }
                }
            }
        }
    }
}
