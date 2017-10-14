package cn.cityworks.bpm.demo.config;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import cn.cityworks.bpm.demo.dao.impl.AiaUserManagerImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.activiti.engine.IdentityService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * create by afterloe on 2017/10/13
 */
@Configuration
public class Init implements Serializable {

    @Bean
    @Scope("prototype")
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        return objectMapper;
    }

    @Bean
    private IdentityService usersAndGroupsInitializer() {
        AiaUserManagerImpl aiaUserManager = new AiaUserManagerImpl();
        return aiaUserManager;
    }
}
