package cn.cityworks.bpm.demo.integrate;

import cn.cityworks.bpm.demo.integrate.fallback.RoleClientFallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(name = "${summary.role:role-store}", fallbackFactory = RoleClientFallbackFactory.class)
public interface RoleClient {

    @RequestMapping(value = "/roles", method = RequestMethod.GET)
    Map roles();
}
