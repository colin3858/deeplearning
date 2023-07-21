package com.reggie.demo.filter;

import com.alibaba.fastjson.JSON;
import com.reggie.demo.common.R;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *
 */
@WebFilter(filterName = "logincheckfilter",urlPatterns = "/*")
public class logincheckfilter implements Filter {
    public static final AntPathMatcher ANT_PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse)  servletResponse;
        String requestURI=request.getRequestURI();
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };
        boolean check=this.check(urls,requestURI);
        if(check){
            filterChain.doFilter(request,response);
            return;
        }
        if(request.getSession().getAttribute("employee") !=null){
            filterChain.doFilter(request,response);
            return;
        }
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;


    }

    /**
     *
     * @param urls
     * @param requestURI
     * @return
     */

    private boolean check(String[] urls, String requestURI) {
        for(String url:urls){
            boolean match=ANT_PATH_MATCHER.match(url,requestURI);
            if(match){
                return true;

            }

        }
        return false;


    }
}
