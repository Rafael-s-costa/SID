public class Medicao {
	
	private String date;
	private String time;
	private String temperature;
	private String humidity;
	private String id;
	
	public Medicao(Object date, Object time, Object temperature, Object humidity) {
		super();
		String aux = date.toString();
		String[] aux1 = aux.split("/");
		aux = aux1[2] + "-" + aux1[1] + "-" + aux1[0];
		this.date = aux;
		this.time = time.toString();
		this.temperature = temperature.toString();
		this.humidity = humidity.toString();
		
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public String InsertStatement() {
		return "INSERT INTO HumidadeTemperatura(IDMedição, DataMedição, HoraMedição, ValorMediçãoTemperatura, ValorMediçãoHumidade) "
				+ "VALUES ('" + id +"', '" + date + "', '" + time + "', '" + temperature + "', '" + humidity + "');";
	}
	
}
