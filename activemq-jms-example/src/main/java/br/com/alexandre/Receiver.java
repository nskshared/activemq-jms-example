package br.com.alexandre;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.Message;

public class Receiver {

	/**
	 * Utilize vm://localhost para broker embarcado
	 * Utilize tcp://localhost:61616 para broker externo
	 */
	public static void main(String[] args) throws JMSException {
		// Connection Factory
        final ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");

        // Connection
        final Connection connection = connectionFactory.createConnection();
        connection.start();

        // Session
        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Queue
        final Destination destination = session.createQueue("MYQUEUE");

        // Message Consumer
        final MessageConsumer consumer = session.createConsumer(destination);

        // Message
        final Message message = (Message) consumer.receive(1000);
        
        if (message instanceof TextMessage) {
            final TextMessage textMessage = (TextMessage) message;
            String text = textMessage.getText();
            System.out.println(text);
        } 

        // Closing
        consumer.close();
        session.close();
        connection.close();

	}

}
