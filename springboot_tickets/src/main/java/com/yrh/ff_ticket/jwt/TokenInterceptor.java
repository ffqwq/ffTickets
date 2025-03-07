package com.yrh.ff_ticket.jwt;

import com.alibaba.fastjson.JSONObject;
import com.yrh.ff_ticket.util.TokenUtil;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//token 访问数据库 路径 拦截器
@Component
public class TokenInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object handler)throws Exception{
        if(request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        response.setCharacterEncoding("utf-8");
        String token = request.getHeader("token");
        String user_type=request.getHeader("user_type");
        if(user_type==null||user_type.equals(""))return false;
        if(token != null){
            Pair<Boolean, String> result = TokenUtil.verify(token);
            if(result.a && result.b!=null &&  result.b.equals(user_type)){
                return true;
            }
        }
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try{
            JSONObject json = new JSONObject();
            json.put("data",null);
            json.put("success",false);
            json.put("errorMsg","登录状态异常");
            response.getWriter().append(json.toJSONString());
        }catch (Exception e){
            e.printStackTrace();
            response.sendError(500);
            return false;
        }
        return false;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
    }
}