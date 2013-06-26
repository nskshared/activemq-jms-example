package br.com.alexandre;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Sender {

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

        // Session without transaction
        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Destination
        final Destination destination = session.createQueue("MYQUEUE");

        // Message Producer
        final MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
 
        // TextMessage
        final TextMessage message = session.createTextMessage();
        message.setText("Mensagem " + System.currentTimeMillis());
        
        // Send
        producer.send(message);

        // Closing
        session.close();
        connection.close();
	}

}
