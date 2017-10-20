package cn.cityworks.bpm.routers.v1;

import cn.cityworks.bpm.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

/**
 * create by afterloe on 2017/10/20
 */
@RestController
@RequestMapping("/v1/process")
public class Process implements Serializable {

    @Autowired
    private cn.cityworks.bpm.services.Process processService;

    /**
     * 获取流程详细信息
     *
     * @param processId
     * @param uid
     * @return
     */
    @RequestMapping(value = "/{processId}", method = RequestMethod.GET)
    public ResponseDTO getProcess(@PathVariable(value = "processId") String processId
            , @RequestParam(value = "uid") String uid) {
        Object data = processService.getProcess(processId);
        return ResponseDTO.build(data);
    }

    /**
     * 通过业务key 获取工作流实例
     *
     * @param businessKey
     * @param uid
     * @return
     */
    @RequestMapping(value = "list/byBusinessKey/{businessKey}", method = RequestMethod.GET)
    public ResponseDTO listByBusinessKey(@PathVariable(value = "businessKey") String businessKey
            , @RequestParam(value = "uid") String uid) {
        Object data = processService.listByBusinessKey(businessKey);
        return ResponseDTO.build(data);
    }
}