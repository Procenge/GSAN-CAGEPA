
package gcom.relatorio.cobranca.cobrancaadministrativa;

import gcom.relatorio.RelatorioBean;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * [UC3060] Manter Imóvel Cobrança Administrativa
 * 
 * @author Carlos Chrystian Ramos
 * @date 22/02/2013
 */
public class RelatorioImoveisEmCobrancaAdministrativaBean
				implements RelatorioBean {

	// 1.3.1. Imóvel
	private String idImovel;

	// 1.3.2. Empresa
	private String nomeEmpresa;

	// 1.3.3. Dt.Implantação da Cobrança.
	private String dataImplantacaoCobranca;

	// 1.3.4. Dt.Retirada Cobrança
	private String dataRetiradaCobranca;

	// 1.3.5. Motivo da Retirada
	private String descricaoMotivoRetiradaCobranca;

	// 1.3.6. Data.Adimplência
	private String dataAdimplencia;

	// 1.3.7. Motivo Adimplência
	private String descricaoMotivoAdimplencia;

	// 1.3.8. Qtd. Débitos
	private String quantidadeItensMarcados;

	// 1.3.9. Valor Débitos
	private String valorItensMarcados;

	// 1.3.10. Qtde. Itens Remuneráveis
	private String quantidadeItensRemuneraveis;

	// 1.3.11. Valor Itens Remuneráveis
	private String valorItensRemuneraveis;

	// 1.3.10. Situação Atual Debito
	private String descricaoSituacaoAtualDebito;

	// Armazena a coleção detalhe de conta
	private JRBeanCollectionDataSource colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheContaBean;

	private ArrayList arrayRelatorioImoveisEmCobrancaAdministrativaDetalheContaBean;

	// Armazena a coleção detalhe de Guia de Pagamento
	private JRBeanCollectionDataSource colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheGuiaBean;

	private ArrayList arrayRelatorioImoveisEmCobrancaAdministrativaDetalheGuiaBean;

	// Armazena a coleção detalhe da Remuneração Imóvel
	private JRBeanCollectionDataSource colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean;

	private ArrayList arrayRelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean;

	// Armazena a coleção detalhe da Situação Débito
	private JRBeanCollectionDataSource colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean;

	private ArrayList arrayRelatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean;

	// Armazena a coleção detalhe de Debito a Cobrar
	private JRBeanCollectionDataSource colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheDebitoBean;

	private ArrayList arrayRelatorioImoveisEmCobrancaAdministrativaDetalheDebitoBean;

	// Valor Total Débito Conta
	private String valorTotalDebitoConta;

	// Valor Total Débito Guia
	private String valorTotalDebitoGuia;

	// Valor Total Débito a Cobrar
	private String valorTotalDebitoACobrar;

	// 1.3.12.1. Percentual de Remuneração Padrão do Imóvel
	private String percentualRemuneracaoPadrao;

	// Valor parcial das Contas
	private String valorParcialContas;

	// Valor Parcial das Guias
	private String valorParcialGuias;

	// Valor Parcial dos Débito a Cobrar
	private String valorParcialDebitoACobrar;

	// Nome
	private String nome;

	// cpf/cnpj
	private String cpfCnpj;

	// fone
	private String telefone;

	// endereco
	private String endereco;

	public String getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}

	public String getNomeEmpresa(){

		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa){

		this.nomeEmpresa = nomeEmpresa;
	}

	public String getDataImplantacaoCobranca(){

		return dataImplantacaoCobranca;
	}

	public void setDataImplantacaoCobranca(String dataImplantacaoCobranca){

		this.dataImplantacaoCobranca = dataImplantacaoCobranca;
	}

	public String getDataRetiradaCobranca(){

		return dataRetiradaCobranca;
	}

	public void setDataRetiradaCobranca(String dataRetiradaCobranca){

		this.dataRetiradaCobranca = dataRetiradaCobranca;
	}


	public String getDescricaoMotivoRetiradaCobranca(){

		return descricaoMotivoRetiradaCobranca;
	}

	public void setDescricaoMotivoRetiradaCobranca(String descricaoMotivoRetiradaCobranca){

		this.descricaoMotivoRetiradaCobranca = descricaoMotivoRetiradaCobranca;
	}

	public String getDataAdimplencia(){

		return dataAdimplencia;
	}

	public void setDataAdimplencia(String dataAdimplencia){

		this.dataAdimplencia = dataAdimplencia;
	}

	public String getDescricaoMotivoAdimplencia(){

		return descricaoMotivoAdimplencia;
	}

	public void setDescricaoMotivoAdimplencia(String descricaoMotivoAdimplencia){

		this.descricaoMotivoAdimplencia = descricaoMotivoAdimplencia;
	}

	/**
	 * @return the quantidadeItensMArcados
	 */
	public String getQuantidadeItensMarcados(){

		return quantidadeItensMarcados;
	}

	/**
	 * @param quantidadeItensMArcados
	 *            the quantidadeItensMArcados to set
	 */
	public void setQuantidadeItensMarcados(String quantidadeItensMarcados){

		this.quantidadeItensMarcados = quantidadeItensMarcados;
	}

	/**
	 * @return the valorItensMarcados
	 */
	public String getValorItensMarcados(){

		return valorItensMarcados;
	}

	/**
	 * @param valorItensMarcados
	 *            the valorItensMarcados to set
	 */
	public void setValorItensMarcados(String valorItensMarcados){

		this.valorItensMarcados = valorItensMarcados;
	}

	/**
	 * @return the quantidadeItensRemuneraveis
	 */
	public String getQuantidadeItensRemuneraveis(){

		return quantidadeItensRemuneraveis;
	}

	/**
	 * @param quantidadeItensRemuneraveis
	 *            the quantidadeItensRemuneraveis to set
	 */
	public void setQuantidadeItensRemuneraveis(String quantidadeItensRemuneraveis){

		this.quantidadeItensRemuneraveis = quantidadeItensRemuneraveis;
	}

	/**
	 * @return the valorItensRemuneraveis
	 */
	public String getValorItensRemuneraveis(){

		return valorItensRemuneraveis;
	}

	/**
	 * @param valorItensRemuneraveis
	 *            the valorItensRemuneraveis to set
	 */
	public void setValorItensRemuneraveis(String valorItensRemuneraveis){

		this.valorItensRemuneraveis = valorItensRemuneraveis;
	}

	public String getDescricaoSituacaoAtualDebito(){

		return descricaoSituacaoAtualDebito;
	}

	public void setDescricaoSituacaoAtualDebito(String descricaoSituacaoAtualDebito){

		this.descricaoSituacaoAtualDebito = descricaoSituacaoAtualDebito;
	}

	public JRBeanCollectionDataSource getColecaoRelatorioImoveisEmCobrancaAdministrativaDetalheContaBean(){

		return colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheContaBean;
	}

	public void setColecaoRelatorioImoveisEmCobrancaAdministrativaDetalheContaBean(
					Collection<RelatorioImoveisEmCobrancaAdministrativaDetalheContaBean> colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheContaBean){

		if(!Util.isVazioOrNulo(colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheContaBean)){
			this.arrayRelatorioImoveisEmCobrancaAdministrativaDetalheContaBean = new ArrayList();
			this.arrayRelatorioImoveisEmCobrancaAdministrativaDetalheContaBean
							.addAll(colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheContaBean);
			this.colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheContaBean = new JRBeanCollectionDataSource(
							this.arrayRelatorioImoveisEmCobrancaAdministrativaDetalheContaBean);
		}
	}

	public JRBeanCollectionDataSource getColecaoRelatorioImoveisEmCobrancaAdministrativaDetalheGuiaBean(){

		return colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheGuiaBean;
	}

	public void setColecaoRelatorioImoveisEmCobrancaAdministrativaDetalheGuiaBean(
					Collection<RelatorioImoveisEmCobrancaAdministrativaDetalheGuiaBean> colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheGuiaBean){

		this.arrayRelatorioImoveisEmCobrancaAdministrativaDetalheGuiaBean = new ArrayList();
		this.arrayRelatorioImoveisEmCobrancaAdministrativaDetalheGuiaBean
						.addAll(colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheGuiaBean);
		this.colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheGuiaBean = new JRBeanCollectionDataSource(
						this.arrayRelatorioImoveisEmCobrancaAdministrativaDetalheGuiaBean);
	}

	public JRBeanCollectionDataSource getColecaoRelatorioImoveisEmCobrancaAdministrativaDetalheDebitoBean(){

		return colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheDebitoBean;
	}

	public void setColecaoRelatorioImoveisEmCobrancaAdministrativaDetalheDebitoBean(
					Collection<RelatorioImoveisEmCobrancaAdministrativaDetalheDebitoBean> colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheDebitoBean){

		this.arrayRelatorioImoveisEmCobrancaAdministrativaDetalheDebitoBean = new ArrayList();
		this.arrayRelatorioImoveisEmCobrancaAdministrativaDetalheDebitoBean
						.addAll(colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheDebitoBean);
		this.colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheDebitoBean = new JRBeanCollectionDataSource(
						this.arrayRelatorioImoveisEmCobrancaAdministrativaDetalheDebitoBean);
	}

	public JRBeanCollectionDataSource getColecaoRelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean(){

		return colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean;
	}

	public void setColecaoRelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean(
					Collection<RelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean> colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean){

		this.arrayRelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean = new ArrayList();
		this.arrayRelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean
						.addAll(colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean);
		this.colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean = new JRBeanCollectionDataSource(
						this.arrayRelatorioImoveisEmCobrancaAdministrativaDetalheRemuneracaoBean);
	}

	public JRBeanCollectionDataSource getColecaoRelatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean(){

		return colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean;
	}

	public void setColecaoRelatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean(
					Collection<RelatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean> colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean){

		this.arrayRelatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean = new ArrayList();
		this.arrayRelatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean
						.addAll(colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean);
		this.colecaoRelatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean = new JRBeanCollectionDataSource(
						this.arrayRelatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean);
	}

	public ArrayList getArrayRelatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean(){

		return arrayRelatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean;
	}

	public void setArrayRelatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean(
					ArrayList arrayRelatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean){

		this.arrayRelatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean = arrayRelatorioImoveisEmCobrancaAdministrativaDetalheSituacaoBean;
	}

	public String getValorTotalDebitoConta(){

		return valorTotalDebitoConta;
	}

	public void setValorTotalDebitoConta(String valorTotalDebitoConta){

		this.valorTotalDebitoConta = valorTotalDebitoConta;
	}

	public String getValorTotalDebitoGuia(){

		return valorTotalDebitoGuia;
	}

	public void setValorTotalDebitoGuia(String valorTotalDebitoGuia){

		this.valorTotalDebitoGuia = valorTotalDebitoGuia;
	}

	public String getPercentualRemuneracaoPadrao(){

		return percentualRemuneracaoPadrao;
	}

	public void setPercentualRemuneracaoPadrao(String percentualRemuneracaoPadrao){

		this.percentualRemuneracaoPadrao = percentualRemuneracaoPadrao;
	}

	public String getValorParcialContas(){

		return valorParcialContas;
	}

	public void setValorParcialContas(String valorParcialContas){

		this.valorParcialContas = valorParcialContas;
	}

	public String getValorParcialGuias(){

		return valorParcialGuias;
	}

	public void setValorParcialGuias(String valorParcialGuias){

		this.valorParcialGuias = valorParcialGuias;
	}

	/**
	 * @return the valorTotalDebitoACobrar
	 */
	public String getValorTotalDebitoACobrar(){

		return valorTotalDebitoACobrar;
	}

	/**
	 * @param valorTotalDebitoACobrar
	 *            the valorTotalDebitoACobrar to set
	 */
	public void setValorTotalDebitoACobrar(String valorTotalDebitoACobrar){

		this.valorTotalDebitoACobrar = valorTotalDebitoACobrar;
	}

	/**
	 * @return the valorParcialDebitoACobrar
	 */
	public String getValorParcialDebitoACobrar(){

		return valorParcialDebitoACobrar;
	}

	/**
	 * @param valorParcialDebitoACobrar
	 *            the valorParcialDebitoACobrar to set
	 */
	public void setValorParcialDebitoACobrar(String valorParcialDebitoACobrar){

		this.valorParcialDebitoACobrar = valorParcialDebitoACobrar;
	}

	public String getCpfCnpj(){

		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj){

		this.cpfCnpj = cpfCnpj;
	}

	public String getTelefone(){

		return telefone;
	}

	public void setTelefone(String telefone){

		this.telefone = telefone;
	}

	public String getEndereco(){

		return endereco;
	}

	public void setEndereco(String endereco){

		this.endereco = endereco;
	}

	public String getNome(){

		return nome;
	}

	public void setNome(String nome){

		this.nome = nome;
	}

	public RelatorioImoveisEmCobrancaAdministrativaBean(String idImovel, String valorTotalDebitoConta, String nome, String cpfCnpj,
														String telefone, String endereco) {

		super();
		this.idImovel = idImovel;
		this.valorTotalDebitoConta = valorTotalDebitoConta;
		this.nome = nome;
		this.cpfCnpj = cpfCnpj;
		this.telefone = telefone;
		this.endereco = endereco;
	}

	public RelatorioImoveisEmCobrancaAdministrativaBean() {

	}

}
