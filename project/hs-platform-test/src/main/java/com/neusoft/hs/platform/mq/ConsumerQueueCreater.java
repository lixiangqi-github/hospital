package com.neusoft.hs.platform.mq;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.HandlerAdapter;
import org.springframework.amqp.rabbit.listener.adapter.MessagingMessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.invocation.InvocableHandlerMethod;
import org.springframework.stereotype.Component;

import com.neusoft.hs.platform.exception.HsException;
import com.neusoft.hs.platform.log.LogUtil;
import com.neusoft.hs.platform.mq.model.Foo;
import com.neusoft.hs.platform.mq.receiver.ReceiverDynamicService;

@Component
public class ConsumerQueueCreater {

	@Value("${custom.spring.rabbitmq.message.timeout}")
	private int messageTimeout;

	@Autowired
	private RabbitAdmin rabbitAdmin;

	@Autowired
	private TopicExchange exchange;

	@Autowired
	private ConnectionFactory connectionFactory;

	@Autowired
	private DefaultMessageHandlerMethodFactory myHandlerMethodFactory;

	@Autowired
	private ReceiverDynamicService receiverService;

	public void init() {
		try {

			LogUtil.log(ConsumerQueueCreater.class, "初始化监听");

			Queue queue = queueCritical();

			Binding binding = BindingBuilder.bind(queue).to(exchange).with(routingKey());
			rabbitAdmin.declareBinding(binding);

			SimpleMessageListenerContainer listenerContainer = listenerContainer();
			listenerContainer.start();

			LogUtil.log(ConsumerQueueCreater.class, "完成监听");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Queue queueCritical() throws HsException {
		Map<String, Object> args = new HashMap<String, Object>();
		args.put("x-message-ttl", messageTimeout);
		Queue queue = new Queue(consumerQueueName(), true, false, false, args);
		rabbitAdmin.declareQueue(queue);
		return queue;
	}

	private SimpleMessageListenerContainer listenerContainer()
			throws HsException, NoSuchMethodException, SecurityException {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(this.consumerQueueName());

		Method method = receiverService.getClass().getMethod("receiveFooQueue", Foo.class);
		MessagingMessageListenerAdapter listenerAdapter = new MessagingMessageListenerAdapter(receiverService, method);

		InvocableHandlerMethod invocableHandlerMethod = myHandlerMethodFactory
				.createInvocableHandlerMethod(receiverService, method);
		listenerAdapter.setHandlerMethod(new HandlerAdapter(invocableHandlerMethod));

		container.setMessageListener(listenerAdapter);
		return container;
	}

	private String consumerQueueName() throws HsException {
		return "queue.foo";
	}

	private String routingKey() {
		return "queue.foo";
	}
}
