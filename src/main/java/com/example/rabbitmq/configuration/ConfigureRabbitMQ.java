package com.example.rabbitmq.configuration;

import com.example.rabbitmq.consumer.ReceiveMessageHandler;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigureRabbitMQ {

    static final String topicExchangeName = "exchangeName";

    static final String queueName = "queueName";

    static final String routingKey = "route1.#";

    @Bean
    Queue createQueue(){
        return new Queue(queueName, false);
    }

    @Bean
    TopicExchange exchange(){
        return new TopicExchange(topicExchangeName);
    }

    @Bean
    Binding binding(Queue q , TopicExchange exchange){
        return BindingBuilder.bind(q).to(exchange).with(routingKey);
    }


    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter messageListenerAdapter){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(messageListenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter messageListenerAdapter(ReceiveMessageHandler receiveMessageHandler){
        return new MessageListenerAdapter(receiveMessageHandler, "handleMessage");
    }
}
