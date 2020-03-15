package com.atguigu.activemq.queue;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JmsConsumer {
    public static final String ACTIVEMQ_URL = "tcp://192.168.25.138:61616";
    public static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("2号消费者");
        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = activeMQConnectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageConsumer messageConsumer = session.createConsumer(queue);
        //receive方法是重载方法 一个可以设置时间等待，一个可以不设置时间。
     /*   while (true) {
            TextMessage message = (TextMessage) messageConsumer.receive(5000);
            if (message != null) {
                System.out.println("consumer...." + message.getText());
            } else {
                break;
            }

        }
        messageConsumer.close();
        session.close();
        connection.close();*/
        //2.监听器
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (null != message) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("MessageListener..." + textMessage.getText());
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.in.read();
        messageConsumer.close();
        session.close();
        connection.close();
        //如果是两个消费者会遵循轮训的算法，平分队列的消息

    }
}
