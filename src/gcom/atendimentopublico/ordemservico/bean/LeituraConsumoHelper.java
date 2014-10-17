/**
 * 
 */

package gcom.atendimentopublico.ordemservico.bean;

import java.io.Serializable;

/**
 * @author isilva
 */
public class LeituraConsumoHelper
				implements Serializable {

	private Integer anoMesReferenciaMedicaoHistorico;

	private Integer leituraAtualFaturamentoMedicaoHistorico;

	private Integer numeroConsumoFaturadoMesConsumoHistorico;

	private String descricaoConsumoAnormalidade;

	private Integer idLeituraAnormalidade;

	public LeituraConsumoHelper(Integer anoMesReferenciaMedicaoHistorico, Integer leituraAtualFaturamentoMedicaoHistorico,
								Integer numeroConsumoFaturadoMesConsumoHistorico, String descricaoConsumoAnormalidade,
								Integer idLeituraAnormalidade) {

		super();
		this.anoMesReferenciaMedicaoHistorico = anoMesReferenciaMedicaoHistorico;
		this.leituraAtualFaturamentoMedicaoHistorico = leituraAtualFaturamentoMedicaoHistorico;
		this.numeroConsumoFaturadoMesConsumoHistorico = numeroConsumoFaturadoMesConsumoHistorico;
		this.descricaoConsumoAnormalidade = descricaoConsumoAnormalidade;
		this.idLeituraAnormalidade = idLeituraAnormalidade;
	}

	public Integer getAnoMesReferenciaMedicaoHistorico(){

		return anoMesReferenciaMedicaoHistorico;
	}

	public void setAnoMesReferenciaMedicaoHistorico(Integer anoMesReferenciaMedicaoHistorico){

		this.anoMesReferenciaMedicaoHistorico = anoMesReferenciaMedicaoHistorico;
	}

	public Integer getLeituraAtualFaturamentoMedicaoHistorico(){

		return leituraAtualFaturamentoMedicaoHistorico;
	}

	public void setLeituraAtualFaturamentoMedicaoHistorico(Integer leituraAtualFaturamentoMedicaoHistorico){

		this.leituraAtualFaturamentoMedicaoHistorico = leituraAtualFaturamentoMedicaoHistorico;
	}

	public Integer getNumeroConsumoFaturadoMesConsumoHistorico(){

		return numeroConsumoFaturadoMesConsumoHistorico;
	}

	public void setNumeroConsumoFaturadoMesConsumoHistorico(Integer numeroConsumoFaturadoMesConsumoHistorico){

		this.numeroConsumoFaturadoMesConsumoHistorico = numeroConsumoFaturadoMesConsumoHistorico;
	}

	public String getDescricaoConsumoAnormalidade(){

		return descricaoConsumoAnormalidade;
	}

	public void setDescricaoConsumoAnormalidade(String descricaoConsumoAnormalidade){

		this.descricaoConsumoAnormalidade = descricaoConsumoAnormalidade;
	}

	public Integer getIdLeituraAnormalidade(){

		return idLeituraAnormalidade;
	}

	public void setIdLeituraAnormalidade(Integer idLeituraAnormalidade){

		this.idLeituraAnormalidade = idLeituraAnormalidade;
	}

}
