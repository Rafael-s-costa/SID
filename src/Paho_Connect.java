import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.mongodb.*;

public class Paho_Connect {

	
    public static void main(String[] args) {

        String topic        = "iscte_sid_2016_S1";
    	//String topic        = "sid_lab_2018";
        //String content      = "{“temperature”:”34”, “humidity”: “35”, “date”: “33/22/1002”, “time”: “21:10:55”}";
    	//String content      = "{\"temperature\":\"34\", \"humidity\": \"35\", \"date\": \"33/22/1002\", \"time\": \"21:10:55\"}";
        String content      = "{\"valormedicaotemperatura\":\"30\", \"valormedicaohumidade\": \"10\", \"datapassagem\": \"13/5/2018\", \"horapassagem\": \"10:16:00\"}";
        int qos             = 0;
        String broker       = "tcp://iot.eclipse.org:1883";
        String clientId     = "JavaSample";
        MemoryPersistence persistence = new MemoryPersistence();
        while(true){
        try {
            MqttClient sampleClient = new MqttClient(broker, clientId, persistence);
            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            System.out.println("Connecting to broker: "+broker);
            sampleClient.connect(connOpts);
            System.out.println("Connected");
            System.out.println("Publishing message: "+content);
            MqttMessage message = new MqttMessage(content.getBytes());
            message.setQos(qos);
            sampleClient.publish(topic, message);
            System.out.println("Message published");
            System.out.println(topic + " " + message);
            sampleClient.disconnect();
            System.out.println("Disconnected");
            Thread.sleep(5000);
            //System.exit(0);
        } catch(MqttException me) {
            System.out.println("reason "+me.getReasonCode());
            System.out.println("msg "+me.getMessage());
            System.out.println("loc "+me.getLocalizedMessage());
            System.out.println("cause "+me.getCause());
            System.out.println("excep "+me);
            me.printStackTrace();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        }
    }
	
}
