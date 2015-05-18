package gcom.relatorio.faturamento.conta;

import java.io.Serializable;


public class DadosConsumoAnteriorHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer anoMesReferencia;

	private Integer leitura;

	private Integer idLeituraAnormalidade;

	private String descricaoAbreviadaConsumAnormalidade;

	private Integer consumoMedido;

	private Integer consumoFaturado;

	public DadosConsumoAnteriorHelper() {

	}

	public Integer getAnoMesReferencia(){

		return anoMesReferencia;
	}

	public void setAnoMesReferencia(Integer anoMesReferencia){

		this.anoMesReferencia = anoMesReferencia;
	}

	public Integer getLeitura(){

		return leitura;
	}

	public void setLeitura(Integer leitura){

		this.leitura = leitura;
	}

	public Integer getIdLeituraAnormalidade(){

		return idLeituraAnormalidade;
	}

	public void setIdLeituraAnormalidade(Integer idLeituraAnormalidade){

		this.idLeituraAnormalidade = idLeituraAnormalidade;
	}

	public String getDescricaoAbreviadaConsumAnormalidade(){

		return descricaoAbreviadaConsumAnormalidade;
	}

	public void setDescricaoAbreviadaConsumAnormalidade(String descricaoAbreviadaConsumAnormalidade){

		this.descricaoAbreviadaConsumAnormalidade = descricaoAbreviadaConsumAnormalidade;
	}

	public Integer getConsumoMedido(){

		return consumoMedido;
	}

	public void setConsumoMedido(Integer consumoMedido){

		this.consumoMedido = consumoMedido;
	}

	public Integer getConsumoFaturado(){

		return consumoFaturado;
	}

	public void setConsumoFaturado(Integer consumoFaturado){

		this.consumoFaturado = consumoFaturado;
	}


}
