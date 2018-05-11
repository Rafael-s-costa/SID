import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.*;

import org.bson.Document;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class Paho_Client implements MqttCallback {

	private Vector ArrivedData = new Vector();
	private MqttClient client;
	private MongoClient mongodb;
	final String NOMEDB = "SensorDB"; // HumidadeTemperatura e SensorDB
	final String NOMECOL = "Medicoes"; // HumidadeTemp e Medicoes
	//final String TOPIC = "sid_lab_2018";
	final String TOPIC = "iscte_sid_2016_S1";
	String temperature = "valormedicaotemperatura", humidity = "valormedicaohumidade", date = "datapassagem", time = "horapassagem", migrado = "migrado";
	private long interval;
	private ArrayList<Document> transfer_list = new ArrayList<>();
	
	
	public Paho_Client(String ipaddress, String clientID) {
		try {
			client = new MqttClient(ipaddress, clientID);
			client.connect();
			client.setCallback(this);
			client.subscribe(TOPIC);
			interval = System.currentTimeMillis();
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void connectionLost(Throwable arg0) {
		System.out.println("Connection was lost");

	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		ArrivedData.add(message.toString());
		//String messageReceived = message.toString().replace("“", "\"");
		//String messageToParse = messageReceived.replace("”", "\"");
		System.out.println(message.toString());
		try {
			// parametros passados
			mongodb = new MongoClient("localhost", 27017);
			MongoDatabase db = mongodb.getDatabase(NOMEDB);// nome da base de dados no mongo
			MongoCollection<Document> colecao = db.getCollection(NOMECOL);// nome da colecao
			Document novo = new Document();

			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(message.toString());
			
			novo.put(temperature, obj.get(temperature));
			novo.put(humidity, obj.get(humidity));
			novo.put(date, obj.get(date));
			novo.put(time, obj.get(time));
			novo.put(migrado, 0);
			
			transfer_list.add(novo);
			  
			if (System.currentTimeMillis() > interval + 30000)
			  {
				for(Document d : transfer_list) {
					colecao.insertOne(d);
				}
			    transfer_list = new ArrayList<>();
			    interval = System.currentTimeMillis();
			    mongodb.close();
			  }
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("erro de parse");
		}
	}

	public static void main(String[] a) {
		Paho_Client app = new Paho_Client("tcp://iot.eclipse.org:1883", "testestetsetse");
	}

}
