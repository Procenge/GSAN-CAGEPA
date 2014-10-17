
package gcom.gui.cadastro.imovel;

import org.apache.struts.action.ActionForm;

public class AtualizarContratoDemandaConsumoActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String idImovel;

	private String idImovelAux;

	private String inscricaoImovel;

	private String ligacaoAguaSituacao;

	private String ligacaoEsgotoSituacao;

	private String enderecoImovel;

	private String tarifaConsumoAtual;

	private String numeroContrato;

	private String mesAnoFaturamentoInicial;

	private String mesAnoFaturamentoFinal;

	private String idTarifaConsumo;

	private String consumoFixo;

	private String emailgestorContrato;

	public String getIdImovel(){

		return idImovel;
	}

	public String getIdImovelAux(){

		return idImovelAux;
	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public String getLigacaoAguaSituacao(){

		return ligacaoAguaSituacao;
	}

	public String getLigacaoEsgotoSituacao(){

		return ligacaoEsgotoSituacao;
	}

	public String getEnderecoImovel(){

		return enderecoImovel;
	}

	public String getTarifaConsumoAtual(){

		return tarifaConsumoAtual;
	}

	public String getNumeroContrato(){

		return numeroContrato;
	}

	public String getMesAnoFaturamentoInicial(){

		return mesAnoFaturamentoInicial;
	}

	public String getMesAnoFaturamentoFinal(){

		return mesAnoFaturamentoFinal;
	}

	public String getIdTarifaConsumo(){

		return idTarifaConsumo;
	}

	public String getConsumoFixo(){

		return consumoFixo;
	}

	public String getEmailgestorContrato(){

		return emailgestorContrato;
	}

	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}

	public void setIdImovelAux(String idImovelAux){

		this.idImovelAux = idImovelAux;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	public void setLigacaoAguaSituacao(String ligacaoAguaSituacao){

		this.ligacaoAguaSituacao = ligacaoAguaSituacao;
	}

	public void setLigacaoEsgotoSituacao(String ligacaoEsgotoSituacao){

		this.ligacaoEsgotoSituacao = ligacaoEsgotoSituacao;
	}

	public void setEnderecoImovel(String enderecoImovel){

		this.enderecoImovel = enderecoImovel;
	}

	public void setTarifaConsumoAtual(String tarifaConsumoAtual){

		this.tarifaConsumoAtual = tarifaConsumoAtual;
	}

	public void setNumeroContrato(String numeroContrato){

		this.numeroContrato = numeroContrato;
	}

	public void setMesAnoFaturamentoInicial(String mesAnoFaturamentoInicial){

		this.mesAnoFaturamentoInicial = mesAnoFaturamentoInicial;
	}

	public void setMesAnoFaturamentoFinal(String mesAnoFaturamentoFinal){

		this.mesAnoFaturamentoFinal = mesAnoFaturamentoFinal;
	}

	public void setIdTarifaConsumo(String idTarifaConsumo){

		this.idTarifaConsumo = idTarifaConsumo;
	}

	public void setConsumoFixo(String consumoFixo){

		this.consumoFixo = consumoFixo;
	}

	
	public void setEmailgestorContrato(String emailgestorContrato){

		this.emailgestorContrato = emailgestorContrato;
	}


}
