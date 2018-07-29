package com.duke.framework.gateway.zuul;

import com.duke.framework.gateway.utils.WebUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created duke on 2018/7/29
 */
public class PreZuulFilter extends ZuulFilter {

    private final ResourceServerTokenServices tokenServices;

    public PreZuulFilter(ResourceServerTokenServices tokenServices) {
        this.tokenServices = tokenServices;
    }

    @Override
    public String filterType() {
        // 前置过滤器
        return "pre";
    }

    @Override
    public int filterOrder() {
        // 优先级为0，数字越大，优先级越低
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        // 是否执行此过滤器
        return true;
    }

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();

        HttpServletRequest request = requestContext.getRequest();
        HttpServletResponse response = requestContext.getResponse();

        // todo 后期将WebUtils提到duke-framework里面
        String accessToken = WebUtils.extract(request, "access_token");

        if (!ObjectUtils.isEmpty(accessToken)) {
            // 需要登陆的接口
            if (checkRequestUrl(request)) {
                try {
                    OAuth2Authentication auth = tokenServices.loadAuthentication(accessToken);

                    if (auth != null) {
                        requestContext.addZuulRequestHeader("Authorization", "Bearer" + " " + accessToken);
                        return null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                requestContext.addZuulRequestHeader("Authorization", "Bearer" + " " + accessToken);
            }
        } else {

        }
        return null;
    }

    private boolean checkRequestUrl(HttpServletRequest request) {
        return (new AntPathRequestMatcher("/api/*/nologin/**").matches(request))
                || (new AntPathRequestMatcher("/**/v2/api-docs/**").matches(request));
    }
}
