import java.sql.Time;
import java.sql.Timestamp;

public class Medicao {

	private Timestamp dataMedicao;
	private Time horaMedicao;
	private float valorMedicaoTemperatura;
	private float valorMedicaoHumidade;
	private int id;
	
	public Medicao(Timestamp dataMedicao, Time horaMedicao, 
		float valorMedicaoTemperatura, float valorMedicaoHumidade, int id) {
		this.dataMedicao = dataMedicao;
		this.horaMedicao = horaMedicao;
		this.valorMedicaoHumidade = valorMedicaoHumidade;
		this.valorMedicaoTemperatura = valorMedicaoTemperatura;
		this.id = id;
	}

	public Medicao(Object object, Object object2, Object object3, Object object4) {
		
	}

	public void setDataMedicao(Timestamp dataMedicao) {
		this.dataMedicao = dataMedicao;
	}

	public void setHoraMedicao(Time horaMedicao) {
		this.horaMedicao = horaMedicao;
	}

	public void setValorMedicaoTemperatura(float valorMedicaoTemperatura) {
		this.valorMedicaoTemperatura = valorMedicaoTemperatura;
	}

	public void setValorMedicaoHumidade(float valorMedicaoHumidade) {
		this.valorMedicaoHumidade = valorMedicaoHumidade;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Timestamp getDataMedicao() {
		return dataMedicao;
	}

	public Time getHoraMedicao() {
		return horaMedicao;
	}

	public float getValorMedicaoTemperatura() {
		return valorMedicaoTemperatura;
	}

	public float getValorMedicaoHumidade() {
		return valorMedicaoHumidade;
	}

	public int getId() {
		return id;
	}
	
	
}
