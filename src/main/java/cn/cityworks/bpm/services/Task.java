package cn.cityworks.bpm.services;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * create by afterloe on 2017/10/17
 */
public interface Task extends Serializable, Tools {

    /**
     * 获取任务详情
     *
     * @param taskId
     * @param uid
     * @return
     */
    Object getTask(String taskId, String uid);

    /**
     * 签收任务
     *
     * @param taskId
     * @param uid
     * @return
     */
    Object claimTask(String taskId, String uid);

    /**
     * 完成任务
     *
     * @param taskId
     * @param variables
     * @return
     */
    Object completeTask(String taskId, Map variables);

    /**
     * 获取指定人的任务列表
     *
     * @param userId
     * @param page
     * @param number
     * @return
     */
    Object listTaskByUserId(String userId, int page, int number);

    /**
     * 获取指定人可以签收的任务列表
     *
     * @param userId
     * @return
     */
    Object countByCanSignTask(String userId);

    /**
     * 获取指定组的可签收任务列表
     *
     * @param groupKey
     * @param page
     * @param number
     * @return
     */
    Object listCanSignTaskByGroup(String groupKey, int page, int number);

    /**
     * 输出task信息
     */
    Function<org.activiti.engine.task.Task, Map> toString = task -> {
        Map result = new LinkedHashMap();
        result.put("name", task.getName());
        result.put("dueDate", task.getDueDate());
        result.put("localVariables", task.getTaskLocalVariables());
        result.put("processVariables",task.getProcessVariables());
        result.put("assignee", task.getAssignee());
        result.put("owner", task.getOwner());
        result.put("description", task.getDescription());
        result.put("createTime", task.getCreateTime());
        return result;
    };
}
