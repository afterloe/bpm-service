package cn.cityworks.bpm.routers.v1;

import cn.cityworks.bpm.domain.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.Map;
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
     * 获取任务详情
     *
     * @param taskId
     * @param uid
     * @return
     */
    @RequestMapping(value = "/{taskId}", method = RequestMethod.GET)
    public ResponseDTO getTask(@PathVariable(value = "taskId") String taskId
            , @RequestParam(value = "uid") String uid) {
        Object data = taskService.getTask(taskId, uid);
        return ResponseDTO.build(data);
    }

    /**
     * 完成任务
     *
     * @param taskId
     * @return
     */
    @RequestMapping(value = "complete/{taskId}", method = RequestMethod.PUT)
    public ResponseDTO completeTask(@PathVariable(value = "taskId") String taskId
            , @RequestParam Map variables) {
        Object data = taskService.completeTask(taskId, variables);
        return ResponseDTO.build(data);
    }

    /**
     * 签收任务
     *
     * @param taskId
     * @return
     */
    @RequestMapping(value = "claim/{taskId}", method = RequestMethod.PUT)
    public ResponseDTO claimTask(@PathVariable(value = "taskId") String taskId
            , @RequestParam(value = "uid") String uid) {
        Object data = taskService.claimTask(taskId, uid);
        return ResponseDTO.build(data);
    }

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
    public ResponseDTO listTaskByUserId(@RequestParam(value = "userId", required =false) String userId
            , @PathVariable(value = "userId", required = false) String userId_Path
            , @RequestParam(value = "page", required = false, defaultValue = "0") int page
            , @RequestParam(value = "number", required = false, defaultValue = "50") int number) {
        Object data = taskService.listTaskByUserId(Optional.ofNullable(userId_Path).orElse(userId), page, number);
        return ResponseDTO.build(data);
    }

    /**
     * 获取指定人的可签收的任务数量
     *
     * @param userId
     * @param userId_Path
     * @return
     */
    @RequestMapping(value = {"count/{userId}", "count"}, method = RequestMethod.GET)
    public ResponseDTO countByCanSignTask(@PathVariable(value = "userId", required = false) String userId
            , @RequestParam(value = "userId", required =false) String userId_Path) {
        Object data = taskService.countByCanSignTask(Optional.ofNullable(userId_Path).orElse(userId));
        return ResponseDTO.build(data);
    }

    /**
     * 获取指定组的可签收任务列表
     *
     * @param groupId
     * @param groupId_Path
     * @param page
     * @param number
     * @return
     */
    @RequestMapping(value = {"group/{groupId}/list", "group/list"}, method = RequestMethod.GET)
    public ResponseDTO listCanSignTaskByGroup(@RequestParam(value = "page", required = false) String groupId
            , @PathVariable(value = "groupId", required = false) String groupId_Path
            , @RequestParam(value = "page", required = false, defaultValue = "0") int page
            , @RequestParam(value = "number", required = false, defaultValue = "50") int number) {
        Object data = taskService.listCanSignTaskByGroup(
                Optional.ofNullable(groupId_Path).orElse(groupId), page, number);
        return ResponseDTO.build(data);
    }
}
