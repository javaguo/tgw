package com.tgw.basic.rabbitmq.test.releaseSubscription;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeoutException;

/**
 * Created by lee on 2018/8/5.
 */
public class EmitLog
{
    private final static String EXCHANGE_NAME = "ex_log";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置ip
        factory.setHost("localhost");
        //创建连接
        Connection connection = factory.newConnection();
        //创建频道
        Channel channel = connection.createChannel();
        // 声明转发器和类型
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout" );




        for( int i=0;i<100;i++ ){
            //创建发送的数据
            String message = new Date().toLocaleString()+" : log something";
            message = i+"-->"+message;
             // 往转发器上发送消息
             channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes());
            System.out.println(" ["+i+"] Sent '" + message + "'");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        channel.close();
        connection.close();

    }

}