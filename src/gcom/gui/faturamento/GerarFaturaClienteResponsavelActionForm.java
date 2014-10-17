
package gcom.gui.faturamento;

import org.apache.struts.validator.ValidatorActionForm;

public class GerarFaturaClienteResponsavelActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = -1037970610816261671L;

	private String periodoReferenciaContasInicial;

	private String periodoReferenciaContasFinal;

	private String periodoVencimentoContasInicial;

	private String periodoVencimentoContasFinal;

	private String indicadorContasRevisao;

	private String idClienteFiltro;

	private String nomeClienteFiltro;

	private String[] motivosRevisao;

	private String dataVencimento;

	private String[] clientes;

	private String[] clientesSelecionados;

	private String[] contas;

	public String getDataVencimento(){

		return dataVencimento;
	}

	public void setDataVencimento(String dataVencimento){

		this.dataVencimento = dataVencimento;
	}

	public String[] getClientes(){

		return clientes;
	}

	public void setClientes(String[] clientes){

		this.clientes = clientes;
	}

	public String[] getClientesSelecionados(){

		return clientesSelecionados;
	}

	public void setClientesSelecionados(String[] clientesSelecionados){

		this.clientesSelecionados = clientesSelecionados;
	}

	public String getPeriodoReferenciaContasInicial(){

		return periodoReferenciaContasInicial;
	}

	public void setPeriodoReferenciaContasInicial(String periodoReferenciaContasInicial){

		this.periodoReferenciaContasInicial = periodoReferenciaContasInicial;
	}

	public String getPeriodoReferenciaContasFinal(){

		return periodoReferenciaContasFinal;
	}

	public void setPeriodoReferenciaContasFinal(String periodoReferenciaContasFinal){

		this.periodoReferenciaContasFinal = periodoReferenciaContasFinal;
	}

	public String getPeriodoVencimentoContasInicial(){

		return periodoVencimentoContasInicial;
	}

	public void setPeriodoVencimentoContasInicial(String periodoVencimentoContasInicial){

		this.periodoVencimentoContasInicial = periodoVencimentoContasInicial;
	}

	public String getIndicadorContasRevisao(){

		return indicadorContasRevisao;
	}

	public void setIndicadorContasRevisao(String indicadorContasRevisao){

		this.indicadorContasRevisao = indicadorContasRevisao;
	}

	public String[] getMotivosRevisao(){

		return motivosRevisao;
	}

	public void setMotivosRevisao(String[] motivosRevisao){

		this.motivosRevisao = motivosRevisao;
	}

	public String getPeriodoVencimentoContasFinal(){

		return periodoVencimentoContasFinal;
	}

	public void setPeriodoVencimentoContasFinal(String periodoVencimentoContasFinal){

		this.periodoVencimentoContasFinal = periodoVencimentoContasFinal;
	}

	public String[] getContas(){

		return contas;
	}

	public void setContas(String[] contas){

		this.contas = contas;
	}

	public String getIdClienteFiltro(){

		return idClienteFiltro;
	}

	public void setIdClienteFiltro(String idClienteFiltro){

		this.idClienteFiltro = idClienteFiltro;
	}

	public String getNomeClienteFiltro(){

		return nomeClienteFiltro;
	}

	public void setNomeClienteFiltro(String nomeClienteFiltro){

		this.nomeClienteFiltro = nomeClienteFiltro;
	}

}
