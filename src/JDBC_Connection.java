//Importa��es para Sybase
import java.sql.*;
import sybase.jdbc4.sqlanywhere.*;
//Importa��es Java
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;


public class JDBC_Connection extends Thread {

	long PERIOCITY = 5000;
	MongoClient mongodb;
	MongoCollection<Document> colecao;
	MongoDatabase db;
	ArrayList<Medicao> listMedicao = new ArrayList<Medicao>();
	final String NOMEDB = "SensorDB";
	final String NOMECOL = "Medicoes";
	

	public JDBC_Connection() {

		mongodb = new MongoClient("localhost", 27017);
		db = mongodb.getDatabase(NOMEDB);// nome da base de dados no mongo
		colecao = db.getCollection(NOMECOL);// nome da colecao
		System.out.println(db.getCollection(NOMECOL).count());
	}

	public void run() {
		while(true) {
			MongoCursor<Document> cursor = colecao.find().iterator();
			try {
				while (cursor.hasNext()) {
					Document str = cursor.next();

					if (str.get("migrado").equals(0)) {
						System.out.println(str.get("migrado").getClass());

						
						Medicao m = new Medicao(str.get("datapassagem"), str.get("horapassagem"),
								str.get("valormedicaotemperatura"), str.get("valormedicaohumidade"));// Cria objecto

						System.out.println(str);
						listMedicao.add(m); // Adiciona objecto � lista

						Bson newValue = new Document("migrado", 1);// update migrado
						Bson updateOperationDocument = new Document("$set", newValue);
						colecao.updateOne(str, updateOperationDocument);
						
						System.out.println("Teste");//blabla

					}
				}
			} finally {
				cursor.close();
			}

			System.out.println("VAI LER LISTA");
			/*for (Medicao m : listMedicao) { // percorre a lista e vai mandar a lista para o sybase \\
				System.out.println(m.getDataMedicao());
			}*/

			// CONECTO AO SYBASE
			// ENVIO OS ELEMENTOS DA LISTA DE OBJETOS AO SYBASE
			// ??PASSO OS ELEMENTOS DA LISTA PARA UMA AUXILIAR E LIMPO A ORIGINAL???
			try {
				Thread.sleep(PERIOCITY);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}



public static void main(String[] args) {
	JDBC_Connection conn = new JDBC_Connection();
	conn.start();
}

}
