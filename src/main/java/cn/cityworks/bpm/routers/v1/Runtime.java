package cn.cityworks.bpm.routers.v1;

import cn.cityworks.bpm.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Map;

/**
 * create by afterloe on 2017/10/17
 */
@RestController
@RequestMapping("/v1/runtime")
public class Runtime implements Serializable {

    @Autowired
    private cn.cityworks.bpm.services.Runtime runtimeService;

    /**
     * 启动流程
     *
     * @param processData
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseDTO startProcess(@RequestParam Map<String, String> processData) {
        Object data = runtimeService.startProcess(processData);
        return ResponseDTO.build(data);
    }

    /**
     * 获取所有启动的流程
     *
     * @param page
     * @param number
     * @return
     */
    @RequestMapping(value = "list/active", method = RequestMethod.GET)
    public ResponseDTO listActive(@RequestParam(value = "page", required = false, defaultValue = "0") int page
            , @RequestParam(value = "number", required = false, defaultValue = "50") int number) {
        Object data = runtimeService.listActive(page, number);
        return ResponseDTO.build(data);
    }

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
        Object data = runtimeService.getProcess(processId);
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
        Object data = runtimeService.listByBusinessKey(businessKey);
        return ResponseDTO.build(data);
    }
}
