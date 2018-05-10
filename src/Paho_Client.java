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
	final String NOMEDB = "SensorDB";
	final String NOMECOL = "Medicoes";
	final String TOPIC = "iscte_sid_2016_S1";
	String datapassagem = "datapassagem", horapassagem = "horapassagem",
			valormedicaotemperatura = "valormedicaotemperatura", valormedicaohumidade = "valormedicaohumidade",
			migrado = "migrado";

	public Paho_Client(String ipaddress, String clientID) {
		try {
			client = new MqttClient(ipaddress, clientID);
			client.connect();
			client.setCallback(this);
			//client.subscribe("iscte_sid_2016_S1");
			client.subscribe(TOPIC);
		} catch (MqttException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void connectionLost(Throwable arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deliveryComplete(IMqttDeliveryToken arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		ArrivedData.add(message.toString());
		System.out.println(message.toString());
		try {
			// parametros passados
			mongodb = new MongoClient("localhost", 27017);
			MongoDatabase db = mongodb.getDatabase(NOMEDB);// nome da base de dados no mongo
			MongoCollection<Document> colecao = db.getCollection(NOMECOL);// nome da colecao
			Document novo = new Document();

			JSONParser parser = new JSONParser();
			JSONObject obj = (JSONObject) parser.parse(message.toString());

			novo.put(datapassagem, obj.get(datapassagem));
			novo.put(horapassagem, obj.get(horapassagem));
			novo.put(valormedicaotemperatura, obj.get(valormedicaotemperatura));
			novo.put(valormedicaohumidade, obj.get(valormedicaohumidade));
			novo.put(migrado, 0);

			colecao.insertOne(novo);
			mongodb.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] a){
		Paho_Client app = new Paho_Client("tcp://iot.eclipse.org:1883", "testestetsetse");
		while(true);
	}

}
