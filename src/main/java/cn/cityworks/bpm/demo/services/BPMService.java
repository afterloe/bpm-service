package cn.cityworks.bpm.demo.services;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Map;

/**
 * create by afterloe on 2017/10/13
 */
public interface BPMService extends Serializable{

    /**
     * 获取我的任务
     *
     * @param token
     * @return
     */
    Object myTask(String token);

    /**
     * 获取表单数据结构
     *
     * @param processId
     * @return
     */
    Object getFromDataList(String processId);

    /**
     * 启动流程
     *
     * @param processId
     * @param formData
     * @return
     */
    Object reportSupervisionIncident(String processId, Map formData, String token);

    /**
     * 获取待办事件列表
     *
     * @param token
     * @return
     */
    Object listTask(String token);

    /**
     * 查看任务详情
     *
     * @param taskId
     * @return
     */
    Object getTaskForm(String taskId);

    /**
     * 完成任务
     *
     * @param token
     * @param formData
     * @return
     */
    Object completeTask(String token, String taskId, Map formData);
}
