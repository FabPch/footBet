//package com.yalafoot.bet.security;
//
//import com.yalafoot.bet.constants.AppConstants;
//import com.yalafoot.bet.exception.CustomException;
//import com.yalafoot.bet.utils.AppUtils;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.GenericFilterBean;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//@Component
//public class BasicAuthFilter implements Filter {
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        String verifAuth = null;
//        boolean auth = false;
//        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
//
//        if (servletRequest != null){
//            verifAuth = AppUtils.getCookie(httpServletRequest, AppConstants.STALINGRAD);
//        }
//
//        if (verifAuth != null){
//            HttpSession session = ((HttpServletRequest) servletRequest).getSession(false);
//            if (session != null && session.getAttribute(AppConstants.STALINGRAD) != null && session.getAttribute(AppConstants.STALINGRAD).toString().equalsIgnoreCase(verifAuth)){
//                auth = true;
//            }
//        }
//
//        if (auth){
//            filterChain.doFilter(servletRequest, servletResponse);
//        } else {
//            throw new CustomException("Access denied", HttpStatus.FORBIDDEN);
//        }
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//}
