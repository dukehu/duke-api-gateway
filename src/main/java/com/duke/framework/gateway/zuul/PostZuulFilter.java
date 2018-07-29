package com.duke.framework.gateway.zuul;

import com.netflix.zuul.ZuulFilter;

/**
 * Created duke on 2018/7/29
 */
public class PostZuulFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        return null;
    }
}
