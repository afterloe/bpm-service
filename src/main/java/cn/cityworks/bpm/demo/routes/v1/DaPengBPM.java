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
import java.util.Optional;

/**
 * create by afterloe on 2017/10/13
 */
@RestController
@RequestMapping("/v1/daPeng")
public class DaPengBPM implements Serializable {

    private static final Logger LOGGER = LoggerFactory.getLogger(DaPengBPM.class);

    @Autowired
    private BPMService bpmService;
    @Value("${bpm.process.id:supervisionIncident:1:4}")
    private String processId;

    /**
     * 获取督办事件表单
     *
     * @return
     */
    @RequestMapping(value = "supervisionIncident", method = RequestMethod.GET)
    public ResponseDTO getFromDataList(@RequestParam(required = false) String processId) {
        if (null == processId) {
            processId = this.processId;
        }
        Object data = bpmService.getFromDataList(processId);
        return ResponseDTO.build(data);
    }

    /**
     * 上报督办事件
     *
     * @return
     */
    @RequestMapping(value = "supervisionIncident", method = RequestMethod.POST)
    public ResponseDTO reportSupervisionIncident(@RequestParam Map<String, String> taskForm
            , @RequestHeader("access-token") String access_token) {
        Object data = bpmService.reportSupervisionIncident(processId, taskForm, access_token);
        return ResponseDTO.build(data);
    }

    /**
     * 获取待办事项列表
     *
     * @return
     */
    @RequestMapping(value = "taskList", method = RequestMethod.GET)
    public ResponseDTO listTask(@RequestHeader("access-token") String access_token
            , @RequestParam(value = "page", required = false, defaultValue = "0") int page
            , @RequestParam(value = "number", required = false, defaultValue = "50") int number) {
        Object data = bpmService.listTask(access_token);
        return ResponseDTO.build(data);
    }

    /**
     * 完成任务
     *
     * @param taskForm
     * @param access_token
     * @return
     */
    @RequestMapping(value = {"task/{taskId}", "task"}, method = RequestMethod.PUT)
    public ResponseDTO completeTask(@PathVariable(value = "taskId", required = false) String taskId
            , @RequestParam Map<String, String> taskForm
            , @RequestHeader("access-token") String access_token) {
        Object data = bpmService.completeTask(access_token, taskId, taskForm);
        return ResponseDTO.build(data);
    }

    /**
     * 任务详情
     *
     * @param taskId_Path
     * @param taskId
     * @return
     */
    @RequestMapping(value = {"task/{taskId}", "task"}, method = RequestMethod.GET)
    public ResponseDTO getTaskForm(
            @PathVariable(value = "taskId", required = false) String taskId_Path,
            @RequestParam(value = "taskId", required =false) String taskId) {
        Object data = bpmService.getTaskForm(Optional.ofNullable(taskId_Path).orElse(taskId));
        return ResponseDTO.build(data);
    }
}
