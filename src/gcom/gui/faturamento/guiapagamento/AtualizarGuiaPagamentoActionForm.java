
package gcom.gui.faturamento.guiapagamento;

import org.apache.struts.action.ActionForm;

/**
 * [UC0188] Manter Guia de Pagamento
 * 
 * @author Anderson Italo
 * @date 27/10/2011
 */
public class AtualizarGuiaPagamentoActionForm
				extends ActionForm {

	private String idRegistroAtualizacao;

	private String[] idRegistrosRemocao;

	private String idImovel;

	private String codigoCliente;

	private String inscricaoImovel;

	private String nomeClienteUsuario;

	private String nomeClienteResponsavel;

	private String situacaoAgua;

	private String situacaoEsgoto;

	private String cpf;

	private String nomeCliente;

	private String profissao;

	private String ramoAtividade;

	private String[] dataVencimento;

	private String[] numeroPrestacao;

	public String getCodigoCliente(){

		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente){

		this.codigoCliente = codigoCliente;
	}

	public String getCpf(){

		return cpf;
	}

	public void setCpf(String cpf){

		this.cpf = cpf;
	}

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

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getNomeClienteUsuario(){

		return nomeClienteUsuario;
	}

	public void setNomeClienteUsuario(String nomeClienteUsuario){

		this.nomeClienteUsuario = nomeClienteUsuario;
	}

	public String getProfissao(){

		return profissao;
	}

	public void setProfissao(String profissao){

		this.profissao = profissao;
	}

	public String getRamoAtividade(){

		return ramoAtividade;
	}

	public void setRamoAtividade(String ramoAtividade){

		this.ramoAtividade = ramoAtividade;
	}

	public String getSituacaoAgua(){

		return situacaoAgua;
	}

	public void setSituacaoAgua(String situacaoAgua){

		this.situacaoAgua = situacaoAgua;
	}

	public String getSituacaoEsgoto(){

		return situacaoEsgoto;
	}

	public void setSituacaoEsgoto(String situacaoEsgoto){

		this.situacaoEsgoto = situacaoEsgoto;
	}

	public String getIdRegistroAtualizacao(){

		return idRegistroAtualizacao;
	}

	public void setIdRegistroAtualizacao(String idRegistroAtualizacao){

		this.idRegistroAtualizacao = idRegistroAtualizacao;
	}

	public String[] getIdRegistrosRemocao(){

		return idRegistrosRemocao;
	}

	public void setIdRegistrosRemocao(String[] idRegistrosRemocao){

		this.idRegistrosRemocao = idRegistrosRemocao;
	}

	public String getNomeClienteResponsavel(){

		return nomeClienteResponsavel;
	}

	public void setNomeClienteResponsavel(String nomeClienteResponsavel){

		this.nomeClienteResponsavel = nomeClienteResponsavel;
	}

	public String[] getDataVencimento(){

		return dataVencimento;
	}

	public void setDataVencimento(String[] dataVencimento){

		this.dataVencimento = dataVencimento;
	}
	
	public String[] getNumeroPrestacao(){

		return numeroPrestacao;
	}

	public void setNumeroPrestacao(String[] numeroPrestacao){

		this.numeroPrestacao = numeroPrestacao;
	}

	// retorna uma array onde o índice é o número da parcela
	public String[] getDataVencimentoPorPrestacao(){

		String[] retorno = null;

		if(dataVencimento != null && numeroPrestacao != null){
			retorno = new String[dataVencimento.length];

			for(int i = 0; i < dataVencimento.length; i++){
				retorno[Integer.parseInt(numeroPrestacao[i])] = dataVencimento[i];
			}
		}
		return retorno;
	}


	public void reset(){

		this.setCodigoCliente(null);
		this.setCpf(null);
		this.setNomeCliente(null);
		this.setProfissao(null);
		this.setRamoAtividade(null);
		this.setIdImovel(null);
		this.setInscricaoImovel(null);
		this.setNomeClienteResponsavel(null);
		this.setNomeClienteUsuario(null);
		this.setSituacaoAgua(null);
		this.setSituacaoEsgoto(null);
	}

}
