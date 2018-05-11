import java.sql.Time;
import java.sql.Timestamp;

public class Medicao {

	private Timestamp date;
	private Time time;
	private float temperature;
	private float humidity;
	private int id;
	
	public Medicao(Timestamp dataMedicao, Time horaMedicao, float valorMedicaoTemperatura, float valorMedicaoHumidade, int id) {
		this.date = dataMedicao;
		this.time = horaMedicao;
		this.humidity = valorMedicaoHumidade;
		this.temperature = valorMedicaoTemperatura;
		this.id = id;
	}

	public Medicao(Object dataMedicao, Object horaMedicao, Object valorMedicaoTemperatura, Object valorMedicaoHumidade) {
		this.date = Timestamp.valueOf(dataMedicao.toString());
		this.time = Time.valueOf(horaMedicao.toString());
		this.humidity = Float.valueOf(valorMedicaoHumidade.toString());
		this.temperature = Float.valueOf(valorMedicaoTemperatura.toString());
	}

	public void setDataMedicao(Timestamp dataMedicao) {
		this.date = dataMedicao;
	}

	public void setHoraMedicao(Time horaMedicao) {
		this.time = horaMedicao;
	}

	public void setValorMedicaoTemperatura(float valorMedicaoTemperatura) {
		this.temperature = valorMedicaoTemperatura;
	}

	public void setValorMedicaoHumidade(float valorMedicaoHumidade) {
		this.humidity = valorMedicaoHumidade;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getDataMedicao() {
		return date;
	}

	public Time getHoraMedicao() {
		return time;
	}

	public float getValorMedicaoTemperatura() {
		return temperature;
	}

	public float getValorMedicaoHumidade() {
		return humidity;
	}

	public int getId() {
		return id;
	}
	
	
}
