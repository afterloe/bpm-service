package cn.cityworks.bpm.services;

import java.io.Serializable;

/**
 * create by afterloe on 2017/10/17
 */
public interface Repository extends Serializable {

    /**
     * 获取已经部署的流程列表
     *
     * @param page
     * @param number
     * @return
     */
    Object listDeveloperProcess(int page, int number);
}
