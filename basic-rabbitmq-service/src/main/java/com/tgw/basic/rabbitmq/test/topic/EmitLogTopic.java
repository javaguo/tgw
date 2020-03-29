package com.tgw.basic.rabbitmq.test.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.UUID;

public class EmitLogTopic {
    private static final Log LOG = LogFactory.getLog(EmitLogTopic.class);

    private static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] argv) throws Exception
    {
        // 创建连接和频道
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //指定topic的转发器
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");

        String[] routing_keys = new String[] { "kernel.info", "cron.warning","auth.info", "kernel.critical" };

        for (String routing_key : routing_keys)
        {
            String msg = UUID.randomUUID().toString();
            channel.basicPublish(EXCHANGE_NAME, routing_key, null, msg.getBytes());
            System.out.println(" [x] Sent routingKey = "+routing_key+" ,msg = " + msg + ".");
        }

        channel.close();
        connection.close();
    }
}