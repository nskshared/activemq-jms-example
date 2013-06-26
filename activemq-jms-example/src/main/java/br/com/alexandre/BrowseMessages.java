package br.com.alexandre;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class BrowseMessages {

	/**
	 * Utilize vm://localhost para broker embarcado
	 * Utilize tcp://localhost:61616 para broker externo
	 */
	@SuppressWarnings("rawtypes")
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

        // Queue Browser
        final QueueBrowser queueBrowser = session.createBrowser((Queue) destination);
        
        final Enumeration enumeration = queueBrowser.getEnumeration();
        
        // Elements
        while (enumeration.hasMoreElements()) {
        	 final Object element = enumeration.nextElement();
        	 if (element instanceof TextMessage) {
        		 final TextMessage textMessage = (TextMessage) element;
                 String text = textMessage.getText();
                 System.out.println(text);
        	 }
        }
        
        // Closing
        session.close();
        connection.close();
	}

}
