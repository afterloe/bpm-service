package cn.cityworks.bpm.demo.config;

import cn.cityworks.bpm.demo.dao.impl.AiaUserManagerImpl;
import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.UserIdentityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class AiaUserSession implements Serializable, SessionFactory {

    @Autowired
    private AiaUserManagerImpl aiaUserManager;

    @Override
    public Class<?> getSessionType() {
        return UserIdentityManager.class;
    }

    @Override
    public Session openSession() {
        return aiaUserManager;
    }
}
