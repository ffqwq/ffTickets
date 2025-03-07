package com.yrh.ff_ticket.role;

import com.yrh.ff_ticket.payload.response.DataResponse;
import com.yrh.ff_ticket.util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.misc.Pair;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class RoleAspect {

    /**
     * 目标方法
     */
    @Pointcut("@annotation(com.yrh.ff_ticket.role.RoleCheck)")
    private void permission() {

    }

    /**
     * 目标方法调用之前执行
     */
    @Before("permission()")
    public void doBefore() {
    }

    /**
     * 目标方法调用之后执行
     */
    @After("permission()")
    public void doAfter() {
    }

    /**
     * 环绕
     * 会将目标方法封装起来
     * 具体验证业务数据
     */
    @Around("permission()")
    public Object doAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert attributes != null;
        HttpServletRequest request = attributes.getRequest();

        String token=request.getHeader("token");
        String user_type = request.getHeader("user_type");

        Method method = ((MethodSignature) proceedingJoinPoint.getSignature()).getMethod();
        RoleCheck roleCheck = method.getAnnotation(RoleCheck.class);
        if(user_type==null||user_type.equals(""))return new DataResponse<>(false,"无权限！");
        if(token != null){
            Pair<Boolean, String> result = TokenUtil.verify(token);
            if(result.a && result.b!=null &&  result.b.equals(user_type)){
                String value = roleCheck.value();
                if(value.equals("user")||result.b.equals(value)){
                    return proceedingJoinPoint.proceed();//执行方法
                }
            }
        }
        return new DataResponse<>(false,"无权限！");
    }
}
