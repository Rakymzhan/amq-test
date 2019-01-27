import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Author: Rakymzhan A. Kenzhegul
 * Created: 27.01.2019 13:50
 */
public class MessageSender {

    private static String url = "tcp://localhost:61616";
    private static String subject = "TEST_JMS_MESSAGE";

    public static void main(String[] args) {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        Connection conn = null;
        try {
            conn = connectionFactory.createConnection();
            conn.start();
            Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue(subject);
            MessageProducer producer = session.createProducer(destination);
            TextMessage message = session.createTextMessage("Hello! I'm sending a test message via ActiveMQ");
            producer.send(message);
            System.out.println("Message sent: '" + message.getText() + "'");

        } catch (JMSException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (JMSException ex) {
                ex.printStackTrace();
            }
        }
    }
}
