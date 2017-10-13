package cn.cityworks.bpm.demo.routes.v1;

import cn.cityworks.bpm.demo.domain.ResponseDTO;
import cn.cityworks.bpm.demo.services.BPMService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
     * 获取待办事项表单数据
     *
     * @return
     */
    @RequestMapping(value = "postItem", method = RequestMethod.GET)
    public ResponseDTO listTask() {
        Object data = bpmService.getProcessFormDataByStart(processId);
        return ResponseDTO.build(data);
    }

    /**
     * 上报事件
     *
     * @return
     */
    @RequestMapping(value = "postItem", method = RequestMethod.POST)
    public ResponseDTO startProcess(@RequestParam Map<String, String> subSystem) {
        Object data = bpmService.startProcess(processId);
        return ResponseDTO.build(data);
    }
}
