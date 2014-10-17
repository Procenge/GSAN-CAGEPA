
package gcom.relatorio.faturamento;

import gcom.relatorio.RelatorioBean;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * [UC0188] Manter Guia de Pagamento
 * 
 * @author Hugo Lima
 * @date 12/10/2011
 */
public class RelatorioManterGuiaPagamentoBean
				implements RelatorioBean {

	private String idGuiaPagamento;

	private String idImovel;

	private String idIncricaoImovelFormatada;

	private String dsSituacaoAgua;

	private String dsSituacaoEsgoto;

	private String dsSituacaoAtual;

	private String nmClienteUsuario;

	private String nmClienteResponsavel;

	private String idCliente;

	private String nmCliente;

	private String nuDocumentoIdentificacao;

	private String dsProfissao;

	private String dsRamoAtividade;

	private String dataInclusao;

	private String dsDocumentoTipo;

	private String idDocumentoTipo;

	private String nuValorTotal;

	private String nuValorPago;

	private String nuValorPendente;

	private Short nuValorTotalPrestacao;

	private Short nuValorPagoPrestacao;

	private Short nuValorPendentePrestacao;

	private JRBeanCollectionDataSource colecaoRelGuiPagDetalhePrestacaoBean;

	private ArrayList arrayRelGuiPagDetalhePrestacaoBean;

	public String getIdGuiaPagamento(){

		return idGuiaPagamento;
	}

	public void setIdGuiaPagamento(String idGuiaPagamento){

		this.idGuiaPagamento = idGuiaPagamento;
	}

	public String getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}

	public String getIdIncricaoImovelFormatada(){

		return idIncricaoImovelFormatada;
	}

	public void setIdIncricaoImovelFormatada(String idIncricaoImovelFormatada){

		this.idIncricaoImovelFormatada = idIncricaoImovelFormatada;
	}

	public String getDsSituacaoAgua(){

		return dsSituacaoAgua;
	}

	public void setDsSituacaoAgua(String dsSituacaoAgua){

		this.dsSituacaoAgua = dsSituacaoAgua;
	}

	public String getDsSituacaoEsgoto(){

		return dsSituacaoEsgoto;
	}

	public void setDsSituacaoEsgoto(String dsSituacaoEsgoto){

		this.dsSituacaoEsgoto = dsSituacaoEsgoto;
	}

	public String getDsSituacaoAtual(){

		return dsSituacaoAtual;
	}

	public void setDsSituacaoAtual(String dsSituacaoAtual){

		this.dsSituacaoAtual = dsSituacaoAtual;
	}

	public String getNmClienteUsuario(){

		return nmClienteUsuario;
	}

	public void setNmClienteUsuario(String nmClienteUsuario){

		this.nmClienteUsuario = nmClienteUsuario;
	}

	public String getNmClienteResponsavel(){

		return nmClienteResponsavel;
	}

	public void setNmClienteResponsavel(String nmClienteResponsavel){

		this.nmClienteResponsavel = nmClienteResponsavel;
	}

	public String getIdCliente(){

		return idCliente;
	}

	public void setIdCliente(String idCliente){

		this.idCliente = idCliente;
	}

	public String getNmCliente(){

		return nmCliente;
	}

	public void setNmCliente(String nmCliente){

		this.nmCliente = nmCliente;
	}

	public String getNuDocumentoIdentificacao(){

		return nuDocumentoIdentificacao;
	}

	public void setNuDocumentoIdentificacao(String nuDocumentoIdentificacao){

		this.nuDocumentoIdentificacao = nuDocumentoIdentificacao;
	}

	public String getDsProfissao(){

		return dsProfissao;
	}

	public void setDsProfissao(String dsProfissao){

		this.dsProfissao = dsProfissao;
	}

	public String getDsRamoAtividade(){

		return dsRamoAtividade;
	}

	public void setDsRamoAtividade(String dsRamoAtividade){

		this.dsRamoAtividade = dsRamoAtividade;
	}

	public String getDataInclusao(){

		return dataInclusao;
	}

	public void setDataInclusao(String dataInclusao){

		this.dataInclusao = dataInclusao;
	}

	public String getDsDocumentoTipo(){

		return dsDocumentoTipo;
	}

	public void setDsDocumentoTipo(String dsDocumentoTipo){

		this.dsDocumentoTipo = dsDocumentoTipo;
	}

	public String getIdDocumentoTipo(){

		return idDocumentoTipo;
	}

	public void setIdDocumentoTipo(String idDocumentoTipo){

		this.idDocumentoTipo = idDocumentoTipo;
	}

	public String getNuValorTotal(){

		return nuValorTotal;
	}

	public void setNuValorTotal(String nuValorTotal){

		this.nuValorTotal = nuValorTotal;
	}

	public String getNuValorPago(){

		return nuValorPago;
	}

	public void setNuValorPago(String nuValorPago){

		this.nuValorPago = nuValorPago;
	}

	public String getNuValorPendente(){

		return nuValorPendente;
	}

	public void setNuValorPendente(String nuValorPendente){

		this.nuValorPendente = nuValorPendente;
	}

	public Short getNuValorTotalPrestacao(){

		return nuValorTotalPrestacao;
	}

	public void setNuValorTotalPrestacao(Short nuValorTotalPrestacao){

		this.nuValorTotalPrestacao = nuValorTotalPrestacao;
	}

	public Short getNuValorPagoPrestacao(){

		return nuValorPagoPrestacao;
	}

	public void setNuValorPagoPrestacao(Short nuValorPagoPrestacao){

		this.nuValorPagoPrestacao = nuValorPagoPrestacao;
	}

	public Short getNuValorPendentePrestacao(){

		return nuValorPendentePrestacao;
	}

	public void setNuValorPendentePrestacao(Short nuValorPendentePrestacao){

		this.nuValorPendentePrestacao = nuValorPendentePrestacao;
	}

	public ArrayList getArrayRelGuiPagDetalhePrestacaoBean(){

		return arrayRelGuiPagDetalhePrestacaoBean;
	}

	public void setArrayRelGuiPagDetalhePrestacaoBean(ArrayList arrayRelGuiPagDetalhePrestacaoBean){

		this.arrayRelGuiPagDetalhePrestacaoBean = arrayRelGuiPagDetalhePrestacaoBean;
	}

	public JRBeanCollectionDataSource getColecaoRelGuiPagDetalhePrestacaoBean(){

		return colecaoRelGuiPagDetalhePrestacaoBean;
	}

	public void setColecaoRelGuiPagDetalhePrestacaoBean(
					Collection<RelatorioManterGuiaPagamentoPrestacoesBean> colecaoRelatorioManterGuiaPagamentoPrestacoesBean){

		if(!Util.isVazioOrNulo(colecaoRelatorioManterGuiaPagamentoPrestacoesBean)){
			this.arrayRelGuiPagDetalhePrestacaoBean = new ArrayList();
			this.arrayRelGuiPagDetalhePrestacaoBean.addAll(colecaoRelatorioManterGuiaPagamentoPrestacoesBean);
			this.colecaoRelGuiPagDetalhePrestacaoBean = new JRBeanCollectionDataSource(this.arrayRelGuiPagDetalhePrestacaoBean);
		}
	}

}
