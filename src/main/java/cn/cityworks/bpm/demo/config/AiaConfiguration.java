package cn.cityworks.bpm.demo.config;

import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.engine.impl.persistence.entity.GroupIdentityManager;
import org.activiti.engine.impl.persistence.entity.UserIdentityManager;
import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;
import java.io.IOException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * create by afterloe on 2017/10/14
 */
@Configuration
public class AiaConfiguration extends AbstractProcessEngineAutoConfiguration implements Serializable {

    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration( DataSource dataSource,
            PlatformTransactionManager transactionManager, SpringAsyncExecutor springAsyncExecutor) throws IOException {
        SpringProcessEngineConfiguration configuration = this
                .baseSpringProcessEngineConfiguration(dataSource, transactionManager, springAsyncExecutor);
        Map<Class< ? >, SessionFactory> sessionFactories = new HashMap<>();
        sessionFactories.put(GroupIdentityManager.class, new AiaGroupSession());
        sessionFactories.put(UserIdentityManager.class, new AiaUserSession());
        configuration.setDbIdentityUsed(false);
        return configuration;
    }
}
