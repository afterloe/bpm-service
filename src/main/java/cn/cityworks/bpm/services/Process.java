package cn.cityworks.bpm.services;

import java.io.Serializable;

public interface Process extends Serializable {

    /**
     * 获取流程详细信息
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
