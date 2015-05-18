/**
 * 
 */

package gcom.gui.atendimentopublico.ordemservico;

/**
 * @author isilva
 */
public class OSDadosInterrupcaoHelper {

	private String motivoInterrupcao;

	private String km;

	private String inicioInterrupcao;

	private String fimInterrupcao;

	public String getMotivoInterrupcao(){

		return motivoInterrupcao;
	}

	public void setMotivoInterrupcao(String motivoInterrupcao){

		this.motivoInterrupcao = motivoInterrupcao;
	}

	public String getKm(){

		return km;
	}

	public void setKm(String km){

		this.km = km;
	}

	public String getInicioInterrupcao(){

		return inicioInterrupcao;
	}

	public void setInicioInterrupcao(String inicioInterrupcao){

		this.inicioInterrupcao = inicioInterrupcao;
	}

	public String getFimInterrupcao(){

		return fimInterrupcao;
	}

	public void setFimInterrupcao(String fimInterrupcao){

		this.fimInterrupcao = fimInterrupcao;
	}


}
