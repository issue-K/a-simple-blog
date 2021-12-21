package com.cl.smyblog.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AdminLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        if( !uri.startsWith("/admin") ){
            for(int i=0;i<=100;i++)
                System.out.println("紧急大失误");
        }

        if( request.getSession().getAttribute("loginUser")==null ){
            System.out.println("成功拦截"+uri);
            request.getSession().setAttribute("errorMsg","请登录");
            response.sendRedirect(request.getContextPath()+"/admin/login");
            return false;
        }else{
            request.getSession().removeAttribute("erroMsg");
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
