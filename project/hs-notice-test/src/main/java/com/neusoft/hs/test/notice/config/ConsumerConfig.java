package com.neusoft.hs.test.notice.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;

import com.neusoft.hs.notice.config.MQConstant;

@Configuration
@EnableRabbit
public class ConsumerConfig implements RabbitListenerConfigurer {

	@Value("${custom.visit.rabbitmq.message.timeout}")
	private int messageTimeout;

	@Value("${test.custom.inspect.orderExecute.notice.queue}")
	private String inspectOrderExecuteNoticeQueue;
	
	@Value("${test.custom.inspect.orderExecute.notice.routing}")
	private String inspectOrderExecuteNoticeRoutingKey;

	@Bean
	RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
		return new RabbitAdmin(connectionFactory);
	}

	@Bean
	Queue queueCreateVisit(RabbitAdmin rabbitAdmin) {

		Map<String, Object> args = new HashMap<String, Object>();
		args.put("x-message-ttl", messageTimeout);
		Queue queue = new Queue(MQConsumerConstant.VisitCreateQueue, true, false, false, args);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}

	@Bean
	Queue queueIntoWardVisit(RabbitAdmin rabbitAdmin) {

		Map<String, Object> args = new HashMap<String, Object>();
		args.put("x-message-ttl", messageTimeout);
		Queue queue = new Queue(MQConsumerConstant.VisitIntoWardQueue, true, false, false, args);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}

	@Bean
	Queue queueInspectOrderExecuteNotice(RabbitAdmin rabbitAdmin) {

		Map<String, Object> args = new HashMap<String, Object>();
		args.put("x-message-ttl", messageTimeout);
		Queue queue = new Queue(inspectOrderExecuteNoticeQueue, true, false, false, args);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}

	@Bean
	TopicExchange exchangeVisit(RabbitAdmin rabbitAdmin) {
		TopicExchange topicExchange = new TopicExchange(MQConstant.VisitExchange);
		rabbitAdmin.declareExchange(topicExchange);
		return topicExchange;
	}

	@Bean
	TopicExchange exchangeInspectOrderExecuteNotice(RabbitAdmin rabbitAdmin) {
		TopicExchange topicExchange = new TopicExchange(MQConstant.OrderExecuteNoticeExchange);
		rabbitAdmin.declareExchange(topicExchange);
		return topicExchange;
	}

	@Bean
	Binding bindingExchangeCreateVisit(Queue queueCreateVisit, TopicExchange exchangeVisit,
			RabbitAdmin rabbitAdmin) {
		Binding binding = BindingBuilder.bind(queueCreateVisit).to(exchangeVisit)
				.with(MQConstant.VisitCreateRoutingKey);
		rabbitAdmin.declareBinding(binding);
		return binding;
	}

	@Bean
	Binding bindingExchangeIntoWardVisit(Queue queueIntoWardVisit, TopicExchange exchangeVisit,
			RabbitAdmin rabbitAdmin) {
		Binding binding = BindingBuilder.bind(queueIntoWardVisit).to(exchangeVisit)
				.with(MQConstant.VisitIntoWardRoutingKey);
		rabbitAdmin.declareBinding(binding);
		return binding;
	}

	@Bean
	Binding bindingInspectOrderExecuteNotice(Queue queueInspectOrderExecuteNotice,
			TopicExchange exchangeInspectOrderExecuteNotice, RabbitAdmin rabbitAdmin) {
		Binding binding = BindingBuilder.bind(queueInspectOrderExecuteNotice)
				.to(exchangeInspectOrderExecuteNotice).with(inspectOrderExecuteNoticeRoutingKey);
		rabbitAdmin.declareBinding(binding);
		return binding;
	}

	@Bean
	public DefaultMessageHandlerMethodFactory myHandlerMethodFactory() {
		DefaultMessageHandlerMethodFactory factory = new DefaultMessageHandlerMethodFactory();
		factory.setMessageConverter(new MappingJackson2MessageConverter());
		return factory;
	}

	@Override
	public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
		registrar.setMessageHandlerMethodFactory(myHandlerMethodFactory());
	}
}
