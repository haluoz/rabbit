package com.lxj.rabbit.producer.mapper;

import com.lxj.rabbit.producer.entity.BrokerMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author Xingjing.Li
 * @since 2021/8/16
 */
@Mapper
public interface BrokerMessageMapper {
    int insert(BrokerMessage brokerMessage);
    int insertSelective(BrokerMessage brokerMessage);
    int updateByPrimaryKey(BrokerMessage brokerMessage);
    int updateByPrimaryKeySelective(BrokerMessage brokerMessage);
    void changeBrokerMessageStatus(@Param("messageId") String messageId, @Param("messageStatus") String messageStatus);
    BrokerMessage selectByPrimaryKey(String messageId);
    List<BrokerMessage> queryBrokerMessageStatus(@Param("messageStatus") String messageStatus);
    List<BrokerMessage> queryBrokerMessageStatus4TimeOut(@Param("messageStatus") String messageStatus);
    int update4TryCount(@Param("messageId") String messageId, @Param("updateTime") Date updateTime);
}
