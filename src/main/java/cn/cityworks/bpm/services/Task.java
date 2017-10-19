package cn.cityworks.bpm.services;

import java.io.Serializable;
import java.util.Map;

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
}
