package cn.cityworks.bpm.config;

import cn.cityworks.bpm.dao.impl.AiaGroupManagerImpl;
import org.activiti.engine.impl.interceptor.Session;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupIdentityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * create by afterloe on 2017/10/14
 */
@Component
public class AiaGroupSession implements Serializable, SessionFactory {

    @Autowired
    private AiaGroupManagerImpl aiaGroupManager;

    @Override
    public Class<?> getSessionType() {
        return GroupIdentityManager.class;
    }

    @Override
    public Session openSession() {
        return aiaGroupManager;
    }
}
