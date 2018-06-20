package com.duke.framework.gateway.web.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created duke on 2018/6/20
 */
@Api(description = "#######")
@RestController
public class TestController {

    @ApiImplicitParams({
            @ApiImplicitParam()
    })
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String test(@RequestParam(value = "duke", required = false) String name) {
        return null;
    }

}
