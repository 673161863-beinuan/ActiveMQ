package com.atguigu.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class JmsProduce {
    public static final String ACTIVEMQ_URL = "tcp://192.168.25.138:61616";
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException {
        //1.创建连接，指定服务器的URl地址，使用默认的用户名和密码
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.创建连接
        Connection connection = activeMQConnectionFactory.createConnection();
        //开启
        connection.start();
        //创建session会话
        //两个参数 第一个参数是否开启事务 /第二个叫签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageProducer messageProducer = session.createProducer(queue);
        for (int i = 1; i <= 6; i++) {
            //创建发送内容
            TextMessage textMessage = session.createTextMessage("queue" + i);
            //发送出去
            messageProducer.send(textMessage);
        }
        //关闭资源
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("消息发送完毕...");
    }

}
