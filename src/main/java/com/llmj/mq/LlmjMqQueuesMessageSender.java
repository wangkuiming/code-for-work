package com.llmj.mq;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * MQ消息发送
 * 
 * @Title: LlmjMqQueuesMessageSender.java
 * @Description:
 * @author gaomingjie
 * @date 2016年10月21日上午10:12:48
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class LlmjMqQueuesMessageSender implements MessageSender {
	public static Logger LOGGER = LoggerFactory
	        .getLogger(LlmjMqQueuesMessageSender.class);

	private JmsTemplate jmsTemplate;

	public LlmjMqQueuesMessageSender(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	@Override
	public void sendMessage(final String message) {
		LOGGER.info("notify-mq-msg:[" + message + "]");
		try {
			jmsTemplate.send("llmjQueueCmsAll", new MessageCreator() {
				@Override
				public Message createMessage(Session session)
			            throws JMSException {
					return session.createTextMessage(message);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("notify-mq-error,", e);
		}
	}
}