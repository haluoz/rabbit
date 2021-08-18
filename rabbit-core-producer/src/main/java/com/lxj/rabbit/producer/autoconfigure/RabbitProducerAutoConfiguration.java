package com.lxj.rabbit.producer.autoconfigure;

import com.lxj.rabbit.task.annotation.EnableElasticJob;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * spring自动装配
 * @author Xingjing.Li
 * @since 2021/8/3
 */
@Configuration
@EnableElasticJob
@ComponentScan({"com.lxj.rabbit.producer.*"})
public class RabbitProducerAutoConfiguration {

}
