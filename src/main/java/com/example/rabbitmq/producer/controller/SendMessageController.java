package com.example.rabbitmq.producer.controller;

import com.example.rabbitmq.configuration.ConfigureRabbitMQ;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendMessageController {


    private final RabbitTemplate rabbitTemplate;

    public SendMessageController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/api/send")
    public String sendMessage(@RequestParam("message") String message ){

        rabbitTemplate.convertAndSend(ConfigureRabbitMQ.topicExchangeName,"route1.route",message);
        return "Message sent! -> "+ message;

    }
}
