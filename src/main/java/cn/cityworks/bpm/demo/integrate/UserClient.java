package cn.cityworks.bpm.demo.integrate;

import cn.cityworks.bpm.demo.integrate.fallback.UserClientFallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@FeignClient(name = "${summary.user:user-maneger}", fallbackFactory = UserClientFallbackFactory.class)
public interface UserClient {

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    Map users();
}
