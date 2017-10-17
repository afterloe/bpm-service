package cn.cityworks.bpm.services;

import java.io.Serializable;

/**
 * create by afterloe on 2017/10/17
 */
public interface Runtime extends Serializable {

    /**
     * 获取所有启动的流程
     *
     * @param page
     * @param number
     * @return
     */
    Object listActive(int page, int number);
}
