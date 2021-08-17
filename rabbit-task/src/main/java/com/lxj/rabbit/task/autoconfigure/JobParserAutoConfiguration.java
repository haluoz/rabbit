package com.lxj.rabbit.task.autoconfigure;

import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.lxj.rabbit.task.parser.ElasticJobConfigParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 前置elastic.job.zk
 * 下的namespace和serverLists不配置就不加载
 * @author Xingjing.Li
 * @since 2021/8/17
 */
@Configuration
@ConditionalOnProperty(prefix = "elastic.job.zk", name = {"namespace", "serverLists"}, matchIfMissing = false)
@EnableConfigurationProperties(JobZookeeperProperties.class)
@Slf4j
public class JobParserAutoConfiguration {

    @Bean(initMethod = "init")
    public ZookeeperRegistryCenter registryCenter(JobZookeeperProperties jobZookeeperProperties){
        ZookeeperConfiguration zookeeperConfiguration = new ZookeeperConfiguration(
                jobZookeeperProperties.getServerLists(), jobZookeeperProperties.getNamespace());
        zookeeperConfiguration.setBaseSleepTimeMilliseconds(jobZookeeperProperties.getBaseSleepTimeMilliseconds());
        zookeeperConfiguration.setConnectionTimeoutMilliseconds(jobZookeeperProperties.getConnectionTimeoutMilliseconds());
        zookeeperConfiguration.setSessionTimeoutMilliseconds(jobZookeeperProperties.getSessionTimeoutMilliseconds());
        zookeeperConfiguration.setBaseSleepTimeMilliseconds(jobZookeeperProperties.getBaseSleepTimeMilliseconds());
        zookeeperConfiguration.setMaxRetries(jobZookeeperProperties.getMaxRetries());
        zookeeperConfiguration.setDigest(jobZookeeperProperties.getDigest());
        log.info("初始化job注册中心配置成功, address : {}, namespace : {}",
                jobZookeeperProperties.getServerLists(), jobZookeeperProperties.getNamespace());
        return new ZookeeperRegistryCenter(zookeeperConfiguration);
    }

    @Bean
    public ElasticJobConfigParser elasticJobConfigParser(JobZookeeperProperties jobZookeeperProperties, ZookeeperRegistryCenter registryCenter){
        return new ElasticJobConfigParser(jobZookeeperProperties, registryCenter);
    }
}
