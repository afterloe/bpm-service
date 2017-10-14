package cn.cityworks.bpm.demo.config;

import cn.cityworks.bpm.demo.dao.impl.AiaUserManagerImpl;
import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

public class AiaSession implements Serializable, SessionFactory {

    @Autowired
    private AiaUserManagerImpl aiaUserManager;

    @Override
    public Class<?> getSessionType() {
        return null;
    }

    @Override
    public Session openSession() {
        return null;
    }
}
