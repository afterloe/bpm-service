package cn.cityworks.bpm.demo.services;

import java.io.Serializable;
import java.util.Map;

/**
 * create by afterloe on 2017/10/13
 */
public interface BPMService extends Serializable{

    /**
     * 获取启动的流程实力
     *
     * @param processId
     * @return
     */
    Object getProcessFormDataByStart(String processId);

    /**
     * 启动流程
     *
     * @param processId
     * @param formData
     * @return
     */
    Object startProcess(String processId, Map formData, String token);

    /**
     * 获取待办事件列表
     *
     * @param token
     * @return
     */
    Object listTask(String token);
}
