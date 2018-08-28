package com.duke.framework.gateway.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created duke on 2018/6/20
 */
@Component
@Primary
public class ApiGatewaySwaggerResourcesProvider implements SwaggerResourcesProvider {

    private final static Logger LOGGER = LoggerFactory.getLogger(ApiGatewaySwaggerResourcesProvider.class);

    @Autowired
    private RouteLocator routeLocator;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> swaggerResources = new ArrayList<>();

        List<Route> routes = routeLocator.getRoutes();

        routes.forEach(route -> {
            String replaceLocation = route.getFullPath().replace("**", "v2/api-docs");

            swaggerResources.add(swaggerResource(route.getId(), replaceLocation));

            LOGGER.info("path：{} location：{}", route.getFullPath(), route.getLocation());
        });
        return swaggerResources;
    }

    private SwaggerResource swaggerResource(String id, String fullPath) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(id);
        swaggerResource.setLocation(fullPath);
        swaggerResource.setSwaggerVersion("2.0");
        return swaggerResource;
    }
}
