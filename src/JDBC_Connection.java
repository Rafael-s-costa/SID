import java.sql.*;

import java.util.*;

import org.bson.Document;
import org.bson.conversions.Bson;

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
	final String DBURL = "jdbc:sqlanywhere:uid=dba;pwd=sql;database=MonitorizaçãoDeBaseDeDados;";
	//links=tcpip(host=127.0.0.1)
	Connection con;


	public JDBC_Connection() {

		mongodb = new MongoClient("localhost", 27017);
		db = mongodb.getDatabase(NOMEDB);// nome da base de dados no mongo
		colecao = db.getCollection(NOMECOL);// nome da colecao
		//System.setProperty("java.library.path","C:\\Program Files\\SQL Anywhere 12\\Bin64");
		try {
			con = DriverManager.getConnection(DBURL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * TO-DO:
	 * -Mudar comando find;
	 * -Criar segunda ligação para atualizar estado de migração;
	 * 
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		while(true) {
			MongoCursor<Document> cursor = colecao.find().iterator();
			try {
				while (cursor.hasNext()) {
					Document str = cursor.next();
					if (str.get("migrado").equals(1)) {
						Medicao m = new Medicao(str.get("datapassagem"), str.get("horapassagem"),
								str.get("valormedicaotemperatura"), str.get("valormedicaohumidade"));// Cria objecto

						listMedicao.add(m); // Adiciona objecto ï¿½ lista

						Bson newValue = new Document("migrado", 1);// update migrado
						Bson updateOperationDocument = new Document("$set", newValue);
						colecao.updateOne(str, updateOperationDocument);
					}
				}

				for (Medicao m : listMedicao) { // percorre a lista e vai mandar a lista para o sybase \\
					

					Statement stmt = con.createStatement();

					ResultSet rs = stmt.executeQuery("select (isnull(max(IDMedição),0) + 1) from HumidadeTemperatura;");
					String id;
					while(rs.next()) {
						id = rs.getString(1);
						m.setId(id);
					}
					System.out.println(m.getDate());
					stmt.executeUpdate(m.InsertStatement());
					rs.close();
					stmt.close();
					//con.close();
				}
				listMedicao = new ArrayList<Medicao>();

				// CONECTO AO SYBASE
				// ENVIO OS ELEMENTOS DA LISTA DE OBJETOS AO SYBASE
				// ??PASSO OS ELEMENTOS DA LISTA PARA UMA AUXILIAR E LIMPO A ORIGINAL???	

			} catch (SQLException e) {
				e.printStackTrace();
				//listMedicao = new ArrayList<Medicao>();
			}finally {
				cursor.close();
			}
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
