package cn.cityworks.bpm.services;

import java.io.Serializable;

/**
 * create by afterloe on 2017/10/17
 */
public interface History extends Serializable {

    /**
     * 通过流程id获取 该流程下的历史活动任务
     *
     * @param processId
     * @return
     */
    Object getProcess(String processId);

    /**
     * 通过业务key 获取工作流实例
     *
     * @param businessKey
     * @return
     */
    Object listByBusinessKey(String businessKey);
}
