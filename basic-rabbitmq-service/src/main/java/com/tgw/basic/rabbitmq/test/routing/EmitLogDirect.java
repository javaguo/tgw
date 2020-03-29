package com.tgw.basic.rabbitmq.test.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

public class EmitLogDirect {
    private static final Log LOG = LogFactory.getLog(EmitLogDirect.class);

    private static final String EXCHANGE_NAME = "ex_logs_direct";
    private static final String[] SEVERITIES = { "info", "warning", "error" };

    public static void main(String[] argv) throws java.io.IOException, TimeoutException {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置ip
        factory.setHost("localhost");
        //创建连接
        Connection connection = factory.newConnection();
        //创建频道
        Channel channel = connection.createChannel();
        // 声明转发器的类型
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        //发送6条消息
        for (int i = 0; i < 6; i++)
        {
            String severity = getSeverity();
            String message = severity + "_log :" + UUID.randomUUID().toString();
            // 发布消息至转发器，指定routingkey
            channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }

        channel.close();
        connection.close();
    }

    /**
     * 随机产生一种日志类型
     *
     * @return
     */
    private static String getSeverity()
    {
        Random random = new Random();
        int ranVal = random.nextInt(3);
        return SEVERITIES[ranVal];
    }
}