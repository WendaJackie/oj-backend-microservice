package com.yupi.yuojbackendjudgeservice.rabbitmq;

import com.rabbitmq.client.Channel;
import com.yupi.yuojbackendjudgeservice.judge.JudgeService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class MyMessageConsumer {

    @Resource
    private JudgeService judgeService;

    // RabbitListener指定监听哪个队列
    // MANUAL表示消息手动确认
    // 指定程序监听的消息队列和确认机制
    @SneakyThrows
    @RabbitListener(queues = {"code_queue"}, ackMode = "MANUAL")
    public void receiveMessage(String message, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        log.info("receiveMessage message = {}", message);
        long questionSubmitId = Long.parseLong(message);
        try {
            judgeService.doJudge(questionSubmitId);
            channel.basicAck(deliveryTag, false);
            // Ack -> acknowledged，表示收到
        } catch (Exception e) {
            // 失败后重新处理入队，也可以放到死信队列里
            // 一般情况下不要让他重新入队！（最后一个参数设置为false），如果最后一个参数设置为true，则就重新入队了
            // 但是在本项目了，重新入队会造成无限重新入队的情况发生，消息队列就会发生阻塞（一直往里塞！）
            channel.basicNack(deliveryTag, false, false);
        }
    }

}