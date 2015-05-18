package gcom.faturamento.bean;

import java.io.Serializable;


public class FiltroContaSimularCalculoHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idImovel;

	private Integer periodoReferenciaFaturamentoInicial;

	private Integer periodoReferenciaFaturamentoFinal;

	private String idsCategorias;

	private Integer idLigacaoAguaSituacao;

	private Integer idLigacaoEsgotoSituacao;

	private Integer idFaturamentoGrupo;

	private Integer idConsumoTarifa;

	private Integer idConsumoTarifaRecalcular;

	private Integer idConsumoTarifaVigenciaRecalcular;

	public FiltroContaSimularCalculoHelper() {

	}

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public Integer getPeriodoReferenciaFaturamentoInicial(){

		return periodoReferenciaFaturamentoInicial;
	}

	public void setPeriodoReferenciaFaturamentoInicial(Integer periodoReferenciaFaturamentoInicial){

		this.periodoReferenciaFaturamentoInicial = periodoReferenciaFaturamentoInicial;
	}

	public Integer getPeriodoReferenciaFaturamentoFinal(){

		return periodoReferenciaFaturamentoFinal;
	}

	public void setPeriodoReferenciaFaturamentoFinal(Integer periodoReferenciaFaturamentoFinal){

		this.periodoReferenciaFaturamentoFinal = periodoReferenciaFaturamentoFinal;
	}

	public String getIdsCategorias(){

		return idsCategorias;
	}

	public void setIdsCategorias(String idCategorias){

		this.idsCategorias = idCategorias;
	}

	public Integer getIdLigacaoAguaSituacao(){

		return idLigacaoAguaSituacao;
	}

	public void setIdLigacaoAguaSituacao(Integer idLigacaoAguaSituacao){

		this.idLigacaoAguaSituacao = idLigacaoAguaSituacao;
	}

	public Integer getIdLigacaoEsgotoSituacao(){

		return idLigacaoEsgotoSituacao;
	}

	public void setIdLigacaoEsgotoSituacao(Integer idLigacaoEsgotoSituacao){

		this.idLigacaoEsgotoSituacao = idLigacaoEsgotoSituacao;
	}

	public Integer getIdFaturamentoGrupo(){

		return idFaturamentoGrupo;
	}

	public void setIdFaturamentoGrupo(Integer idFaturamentoGrupo){

		this.idFaturamentoGrupo = idFaturamentoGrupo;
	}

	public Integer getIdConsumoTarifa(){

		return idConsumoTarifa;
	}

	public void setIdConsumoTarifa(Integer idConsumoTarifa){

		this.idConsumoTarifa = idConsumoTarifa;
	}

	public Integer getIdConsumoTarifaRecalcular(){

		return idConsumoTarifaRecalcular;
	}

	public void setIdConsumoTarifaRecalcular(Integer idConsumoTarifaRecalcular){

		this.idConsumoTarifaRecalcular = idConsumoTarifaRecalcular;
	}

	public Integer getIdConsumoTarifaVigenciaRecalcular(){

		return idConsumoTarifaVigenciaRecalcular;
	}

	public void setIdConsumoTarifaVigenciaRecalcular(Integer idConsumoTarifaVigenciaRecalcular){

		this.idConsumoTarifaVigenciaRecalcular = idConsumoTarifaVigenciaRecalcular;
	}



}
