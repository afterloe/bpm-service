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
@RequestMapping("/v1/task")
public class Task implements Serializable {

    @Autowired
    private cn.cityworks.bpm.services.Task taskService;

    /**
     * 获取指定人的任务列表
     *
     * @param userId
     * @param userId_Path
     * @param page
     * @param number
     * @return
     */
    @RequestMapping(value = {"list/{userId}", "list/who"}, method = RequestMethod.GET)
    public ResponseDTO listTaskByUserId(@PathVariable(value = "userId", required = false) String userId
            , @RequestParam(value = "userId", required =false) String userId_Path
            , @RequestParam(value = "page", required = false, defaultValue = "0") int page
            , @RequestParam(value = "number", required = false, defaultValue = "50") int number) {
        Object data = taskService.listTaskByUserId(Optional.ofNullable(userId_Path).orElse(userId), page, number);
        return ResponseDTO.build(data);
    }

    @RequestMapping(value = {"count/{userId}", "count"}, method = RequestMethod.GET)
    public ResponseDTO countByCanSignTask(@PathVariable(value = "userId", required = false) String userId
            , @RequestParam(value = "userId", required =false) String userId_Path) {
        Object data = taskService.countByCanSignTask(Optional.ofNullable(userId_Path).orElse(userId));
        return ResponseDTO.build(data);
    }
}
