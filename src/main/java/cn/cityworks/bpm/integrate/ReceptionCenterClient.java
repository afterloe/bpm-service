package cn.cityworks.bpm.integrate;

import cn.cityworks.bpm.domain.ResponseDTO;
import cn.cityworks.bpm.domain.UserVO;
import cn.cityworks.bpm.integrate.fallback.ReceptionCenterFallbackFactory;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * create by afterloe on 2017/9/1
 */
@FeignClient(name = "${sso.userManager.id:reception-center}", fallbackFactory = ReceptionCenterFallbackFactory.class)
public interface ReceptionCenterClient {

    @RequestMapping(value = "/member/who", method = RequestMethod.GET)
    ResponseDTO<UserVO> who(@RequestParam("access_token") String access_token);

    @RequestMapping(value = "/member/permissionCode", method = RequestMethod.GET)
    ResponseDTO<List> permissionCode(@RequestParam("accessToken") String access_token);
}
