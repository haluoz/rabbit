package com.lxj.rabbit.producer.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.lxj.rabbit.producer.broker.RabbitBroker;
import com.lxj.rabbit.producer.constant.BrokerMessageStatus;
import com.lxj.rabbit.producer.entity.BrokerMessage;
import com.lxj.rabbit.producer.service.MessageStoreService;
import com.lxj.rabbit.task.annotation.ElasticJobConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Xingjing.Li
 * @since 2021/8/18
 */
@Component
@Slf4j
@ElasticJobConfig(
        name = "com.lxj.rabbit.producer.task.RetryMessageDataFlowJob",
        cron = "0/10 * * * * ?",
        description = "可靠性投递消息补偿任务",
        overwrite = true,
        shardingTotalCount = 1
)
public class RetryMessageDataFlowJob implements DataflowJob<BrokerMessage> {

    @Autowired
    private MessageStoreService messageStoreService;
    @Autowired
    private RabbitBroker rabbitBroker;

    private final static int MAX_RETRY = 3;
    @Override
    public List<BrokerMessage> fetchData(ShardingContext shardingContext) {
        List<BrokerMessage> list = messageStoreService.fetchTimeOutMessage4Retry(BrokerMessageStatus.SENDING);
        log.info("抓起数据集合{}",list.size());
        return list;
    }

    @Override
    public void processData(ShardingContext shardingContext, List<BrokerMessage> data) {
        data.forEach(brokerMessage -> {
            String messageId = brokerMessage.getMessageId();
            if (brokerMessage.getTryCount() >= MAX_RETRY) {
                this.messageStoreService.failure(messageId);
                log.warn("消息最终发送失败，消息id {}", messageId);
            }else {
                //重发消息的时候更新重试次数
                messageStoreService.updateTryCount(messageId);
                rabbitBroker.relianceSend(brokerMessage.getMessage());
            }
        });
    }
}
