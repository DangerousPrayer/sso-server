package com.jerryl.auth.service.aop;

import com.jerryl.auth.common.Status;
import com.jerryl.auth.common.exception.BaseException;
import com.jerryl.auth.service.PrivilegeService;
import com.jerryl.auth.service.aop.privilege.Privilege;
import com.jerryl.auth.service.dto.SessionUser;
import com.jerryl.auth.util.RedisUtil;
import com.jerryl.auth.util.SessionUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by liuruijie on 2017/4/7.
 * 验证权限
 */
@Service
@Aspect
public class PrivilegeChecker {
    @Autowired
    PrivilegeService privilegeService;

    /**
     * 拦截privilege注解的方法
     * 根据其参数进行权限验证
     */
    @Around("@annotation(privilege)")
    public Object checkPrivilege(ProceedingJoinPoint joinPoint, Privilege privilege) throws Throwable {
        //获取请求头中的认证信息
        HttpServletRequest request = SessionUtil.getCurrentRequest();
        String authStr = request.getHeader("authorization");
        if(authStr == null){
            throw new BaseException("haven't login yet!", Status.NO_LOGIN);
        }

        authStr = authStr.split(" ")[1];
        if(authStr == null){
            throw new BaseException("haven't login yet!", Status.NO_LOGIN);
        }
        //解码出token
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] bytes = decoder.decodeBuffer(authStr);
        String token = new String(bytes);

        //根据token找到user
        SessionUser user = (SessionUser) RedisUtil.get(token);
        if (user==null){
            throw new BaseException("haven't login yet!", Status.NO_LOGIN);
        }
        //校验权限
        if(!privilegeService.checkPrivilege(user
                , privilege.moduleType().getValue()
                , privilege.actionType().getValue())){
            throw new BaseException("access denied!", Status.NO_PRIVILEGE);
        }
        return joinPoint.proceed();
    }
}
