package cn.cityworks.bpm.services;

import java.io.Serializable;

/**
 * create by afterloe on 2017/10/17
 */
public interface Runtime extends Serializable {

    Object listActive(int page, int number);
}
