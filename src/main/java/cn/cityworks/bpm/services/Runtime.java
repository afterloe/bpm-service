package cn.cityworks.bpm.services;

import java.io.Serializable;
import java.util.Map;

/**
 * create by afterloe on 2017/10/17
 */
public interface Runtime extends Serializable, Tools {

    /**
     * 获取所有启动的流程
     *
     * @param page
     * @param number
     * @return
     */
    Object listActive(int page, int number);

    /**
     * 启动流程
     * 
     * @param processData
     * @return
     */
    Object startProcess(Map processData);

    /**
     * 获取流程详细信息
     *
     * @param processId
     * @return
     */
    Object getProcess(String processId);
}
