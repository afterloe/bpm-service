package cn.cityworks.bpm.routers.v1;

import cn.cityworks.bpm.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Optional;

/**
 * create by afterloe on 2017/10/17
 */
@RestController
@RequestMapping("/v1/history")
public class History implements Serializable {

    @Autowired
    private cn.cityworks.bpm.services.History historyService;

    /**
     * 通过流程id获取 该流程下的历史活动任务
     *
     * @param processId
     * @param processId_Path
     * @return
     */
    @RequestMapping(value = {"/{processId}", "/"}, method = RequestMethod.GET)
    public ResponseDTO getProcess(@PathVariable(value = "processId", required = false) String processId
            , @RequestParam(value = "processId", required =false) String processId_Path) {
        Object data = historyService.getProcess(Optional.ofNullable(processId_Path).orElse(processId));
        return ResponseDTO.build(data);
    }
}
