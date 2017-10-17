package cn.cityworks.bpm.config;

import com.google.common.collect.Lists;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.AbstractProcessEngineAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import javax.sql.DataSource;
import java.io.IOException;

import java.io.Serializable;
import java.util.List;

/**
 * create by afterloe on 2017/10/14
 */
@Configuration
public class AiaConfiguration extends AbstractProcessEngineAutoConfiguration implements Serializable {

    @Autowired
    private AiaGroupSession aiaGroupSession;
    @Autowired
    private AiaUserSession aiaUserSession;

    @Bean
    public SpringProcessEngineConfiguration springProcessEngineConfiguration( DataSource dataSource,
            PlatformTransactionManager transactionManager, SpringAsyncExecutor springAsyncExecutor) throws IOException {
        SpringProcessEngineConfiguration configuration = this
                .baseSpringProcessEngineConfiguration(dataSource, transactionManager, springAsyncExecutor);
        List<SessionFactory> factories = Lists.newArrayList();
        factories.add(aiaGroupSession);
        factories.add(aiaUserSession);
        configuration.setDbIdentityUsed(false);
        configuration.setCustomSessionFactories(factories);
        return configuration;
    }
}
