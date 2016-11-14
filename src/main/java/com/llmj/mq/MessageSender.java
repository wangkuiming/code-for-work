/**
 * 
 */
package com.llmj.mq;

/**
 * MQ消息发送接口
 * 
 * @Title: MessageSender.java
 * @Description:
 * @author gaomingjie
 * @date 2016年10月21日上午10:12:48
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface MessageSender {

	void sendMessage(final String message);
}
