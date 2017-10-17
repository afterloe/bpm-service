package cn.cityworks.bpm.routers.v1;

import cn.cityworks.bpm.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

/**
 * create by afterloe on 2017/10/17
 */
@RestController
@RequestMapping("/v1/repository")
public class Repository implements Serializable {

    @Autowired
    private cn.cityworks.bpm.services.Repository repositoryService;

    /**
     * 获取已经部署的流程列表
     *
     * @param page
     * @param number
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public ResponseDTO listDeveloperProcess(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page
            , @RequestParam(value = "number", required = false, defaultValue = "50") int number) {
        Object data = repositoryService.listDeveloperProcess(page, number);
        return ResponseDTO.build(data);
    }
}
