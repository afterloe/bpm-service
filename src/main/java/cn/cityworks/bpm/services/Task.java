package cn.cityworks.bpm.services;

import java.io.Serializable;

/**
 * create by afterloe on 2017/10/17
 */
public interface Task extends Serializable, Tools {

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
}
