package com.lpras.jmsamq.service.impl;

import com.lpras.jmsamq.model.Email;
import com.lpras.jmsamq.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.*;

@Service("messageService")
public class MessageServiceImpl implements MessageService {
    private final Logger logger = LoggerFactory.getLogger(MessageService.class);
    @Autowired
    private Queue queue;

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Email email;

    @Override
    public String publishMessage(String message) {
        try {
            email.setBody(message);
            logger.info(email.toString());
            jmsTemplate.send(queue, new MessageCreator() {
                /*public Message createMessage(Session session) throws JMSException {
                    MapMessage mapMessage = session.createMapMessage();
                    mapMessage.setObject("email", email.toString());
                    return mapMessage;
                }*/
                public Message createMessage(Session session) throws JMSException {
                    return session.createObjectMessage(email);
                }
            });



            //jmsTemplate.convertAndSend(queue, message);
        } catch (Exception jmse) {
            logger.error(jmse.getMessage(), jmse.getCause());
        }
        return message;
    }

    public void sendEmptyMessage() {
        logger.info("Producer sends empty message");
        jmsTemplate.send(queue, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                Message msg = session.createMessage();
                msg.setBooleanProperty("isPayloadEmpty", true);
                return msg;
            }});
    }
}
