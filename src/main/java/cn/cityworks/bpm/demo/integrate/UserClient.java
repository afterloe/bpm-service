package cn.cityworks.bpm.demo.integrate;

import cn.cityworks.bpm.demo.integrate.fallback.UserClientFallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "${summary.user:user-maneger}", fallbackFactory = UserClientFallbackFactory.class)
public interface UserClient {

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    Map users();

    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    Map listGroupsByName(@RequestParam(value = "groupName") String groupName
            , @RequestParam(required = false, value = "page", defaultValue = "0") int page
            , @RequestParam(required = false, value = "size", defaultValue = "50") int number);

    @RequestMapping(value = "/groups/{groupId}/one", method = RequestMethod.GET)
    Map getGroups(@PathVariable(value = "groupId", required = false) String groupId);

    @RequestMapping(value = "/groups", method = RequestMethod.GET)
    Map listGroups(@RequestParam(required = false, value = "page", defaultValue = "0") int page
            , @RequestParam(required = false, value = "size", defaultValue = "50") int number);
}
