
package gcom.cadastro.imovel.bean;

/**
 * Retorna os dados de medicao obtido no historico de um imovel
 * 
 * @author Hugo Lima
 * @date 22/03/2012
 */
public class ImovelDadosMedicaoHistoricoHelper {

	private Integer leituraAnterior;

	private Integer leituraAtual;

	private String dataLeituraAnterior;

	private String dataLeituraAtual;

	private Integer quantidadeDiasConsumo;

	private Integer idSituacaoLeituraAtual;

	private Integer idAnormalidadeLeitura;

	private Integer consumoMedioMes;

	public ImovelDadosMedicaoHistoricoHelper() {

	}

	public ImovelDadosMedicaoHistoricoHelper(Integer leituraAnterior, Integer leituraAtual, String dataLeituraAnterior,
												String dataLeituraAtual, Integer quantidadeDiasConsumo, Integer idSituacaoLeituraAtual,
												Integer idAnormalidadeLeitura, Integer consumoMedioMes) {

		this.leituraAnterior = leituraAnterior;
		this.leituraAtual = leituraAtual;
		this.dataLeituraAnterior = dataLeituraAnterior;
		this.dataLeituraAtual = dataLeituraAtual;
		this.quantidadeDiasConsumo = quantidadeDiasConsumo;
		this.idSituacaoLeituraAtual = idSituacaoLeituraAtual;
		this.idAnormalidadeLeitura = idAnormalidadeLeitura;
		this.consumoMedioMes = consumoMedioMes;
	}

	public Integer getLeituraAnterior(){

		return leituraAnterior;
	}

	public void setLeituraAnterior(Integer leituraAnterior){

		this.leituraAnterior = leituraAnterior;
	}

	public Integer getLeituraAtual(){

		return leituraAtual;
	}

	public void setLeituraAtual(Integer leituraAtual){

		this.leituraAtual = leituraAtual;
	}

	public String getDataLeituraAnterior(){

		return dataLeituraAnterior;
	}

	public void setDataLeituraAnterior(String dataLeituraAnterior){

		this.dataLeituraAnterior = dataLeituraAnterior;
	}

	public String getDataLeituraAtual(){

		return dataLeituraAtual;
	}

	public void setDataLeituraAtual(String dataLeituraAtual){

		this.dataLeituraAtual = dataLeituraAtual;
	}

	public Integer getQuantidadeDiasConsumo(){

		return quantidadeDiasConsumo;
	}

	public void setQuantidadeDiasConsumo(Integer quantidadeDiasConsumo){

		this.quantidadeDiasConsumo = quantidadeDiasConsumo;
	}

	public Integer getIdSituacaoLeituraAtual(){

		return idSituacaoLeituraAtual;
	}

	public void setIdSituacaoLeituraAtual(Integer idSituacaoLeituraAtual){

		this.idSituacaoLeituraAtual = idSituacaoLeituraAtual;
	}

	public Integer getIdAnormalidadeLeitura(){

		return idAnormalidadeLeitura;
	}

	public void setIdAnormalidadeLeitura(Integer idAnormalidadeLeitura){

		this.idAnormalidadeLeitura = idAnormalidadeLeitura;
	}

	public Integer getConsumoMedioMes(){

		return consumoMedioMes;
	}

	public void setConsumoMedioMes(Integer consumoMedioMes){

		this.consumoMedioMes = consumoMedioMes;
	}

}
