package cn.cityworks.bpm.demo.routes.v1;

import cn.cityworks.bpm.demo.domain.ResponseDTO;
import cn.cityworks.bpm.demo.services.BPMService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Map;

/**
 * create by afterloe on 2017/10/13
 */
@RestController
@RequestMapping("/v1/bpm")
public class DaPengBPM implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(DaPengBPM.class);

    @Autowired
    private BPMService bpmService;
    @Value("${bpm.process.id:myProcess_1:1:4}")
    private String processId;

    /**
     * 获取待办事项
     *
     * @return
     */
    public ResponseDTO listTask(@RequestHeader("access-token") String access_token) {
        Object data = bpmService.listTask(access_token);
        return ResponseDTO.build(data);
    }

    /**
     * 获取事件表单类型列表
     *
     * @return
     */
    @RequestMapping(value = "postItem", method = RequestMethod.GET)
    public ResponseDTO listTaskFormData() {
        Object data = bpmService.getProcessFormDataByStart(processId);
        return ResponseDTO.build(data);
    }

    /**
     * 上报事件
     *
     * @return
     */
    @RequestMapping(value = "postItem", method = RequestMethod.POST)
    public ResponseDTO startProcess(@RequestParam Map<String, String> subSystem
        , @RequestHeader("access-token") String access_token) {
        Object data = bpmService.startProcess(processId, subSystem, access_token);
        return ResponseDTO.build(data);
    }
}
