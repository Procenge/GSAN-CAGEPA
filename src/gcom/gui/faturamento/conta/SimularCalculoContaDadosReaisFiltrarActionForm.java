
package gcom.gui.faturamento.conta;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * [UC3156] Simular Cálculo da Conta Dados Reais
 * 
 * @author Anderson Italo
 * @date 21/09/2014
 */
public class SimularCalculoContaDadosReaisFiltrarActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	
	private String idImovel;

	private String inscricaoImovel;

	private String periodoReferenciaFaturamentoInicial;

	private String periodoReferenciaFaturamentoFinal;

	private String[] idCategoria;

	private String idLigacaoAguaSituacao;

	private String idLigacaoEsgotoSituacao;

	private String idFaturamentoGrupo;

	private String idConsumoTarifa;

	
	public String getIdImovel(){

		return idImovel;
	}


	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}


	public String getInscricaoImovel(){

		return inscricaoImovel;
	}


	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}


	public String getPeriodoReferenciaFaturamentoInicial(){

		return periodoReferenciaFaturamentoInicial;
	}

	public void setPeriodoReferenciaFaturamentoInicial(String periodoReferenciaFaturamentoInicial){

		this.periodoReferenciaFaturamentoInicial = periodoReferenciaFaturamentoInicial;
	}

	public String getPeriodoReferenciaFaturamentoFinal(){

		return periodoReferenciaFaturamentoFinal;
	}

	public void setPeriodoReferenciaFaturamentoFinal(String periodoReferenciaFaturamentoFinal){

		this.periodoReferenciaFaturamentoFinal = periodoReferenciaFaturamentoFinal;
	}

	public String[] getIdCategoria(){

		return idCategoria;
	}

	public void setIdCategoria(String[] idCategoria){

		this.idCategoria = idCategoria;
	}

	public String getIdLigacaoAguaSituacao(){

		return idLigacaoAguaSituacao;
	}

	public void setIdLigacaoAguaSituacao(String idLigacaoAguaSituacao){

		this.idLigacaoAguaSituacao = idLigacaoAguaSituacao;
	}

	public String getIdLigacaoEsgotoSituacao(){

		return idLigacaoEsgotoSituacao;
	}

	public void setIdLigacaoEsgotoSituacao(String idLigacaoEsgotoSituacao){

		this.idLigacaoEsgotoSituacao = idLigacaoEsgotoSituacao;
	}

	public String getIdFaturamentoGrupo(){

		return idFaturamentoGrupo;
	}

	public void setIdFaturamentoGrupo(String idFaturamentoGrupo){

		this.idFaturamentoGrupo = idFaturamentoGrupo;
	}

	public String getIdConsumoTarifa(){

		return idConsumoTarifa;
	}

	public void setIdConsumoTarifa(String idConsumoTarifa){

		this.idConsumoTarifa = idConsumoTarifa;
	}

}
