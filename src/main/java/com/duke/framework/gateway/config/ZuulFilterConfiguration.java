package com.duke.framework.gateway.config;

import com.duke.framework.gateway.zuul.PostZuulFilter;
import com.duke.framework.gateway.zuul.PreZuulFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

/**
 * Created duke on 2018/7/29
 */
@Configuration
@AutoConfigureBefore(ZuulFilterConfiguration.class)
public class ZuulFilterConfiguration {

    @Autowired
    private ResourceServerProperties resourceServerProperties;

    @Bean
    public ResourceServerTokenServices tokenServices(LoadBalancerClient loadBalancerClient,
                                                     ResourceServerProperties resourceServerProperties) {
        return new UserInfoTokenServices(resourceServerProperties.getUserInfoUri(), resourceServerProperties.getClientId());
    }

    @Bean
    public PreZuulFilter preZuulFilter(ResourceServerTokenServices tokenServices) {
        return new PreZuulFilter(tokenServices);
    }

    @Bean
    public PostZuulFilter postZuulFilter() {
        return new PostZuulFilter();
    }
}
