
package gcom.relatorio.cobranca;

import gcom.relatorio.RelatorioBean;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * [UC3023] Manter Boleto Bancário
 * 
 * @author Hebert Falcão
 * @date 12/10/2011
 */
public class RelatorioBoletosBancariosBean
				implements RelatorioBean {

	// 1.3.1. Arrecadador
	private String codigoAgenteArrecadador;

	private String nomeAgenteArrecadador;

	// 1.3.2. Número do Boleto Bancário
	private String numeroSequencialBoletoBancario;

	// 1.3.3. Imóvel
	private String idImovel;

	// 1.3.4. Nosso Número
	private String nossoNumero;

	// 1.3.5. Data de Entrada
	private String dataEntradaBoletoBancario;

	// 1.3.6. Data de Vencimento
	private String dataVencimentoBoletoBancario;

	// 1.3.7. Valor do Boleto
	private BigDecimal valorDebitoBoletoBancario;

	// 1.3.8. Situação Atual
	private String idSituacaoBoletoBancario;

	private String descricaoSituacaoBoletoBancario;

	private String descricaoAbreviadaSituacaoBoletoBancario;

	// 1.3.9. Data do Pagamento
	private String dataPagamentoBoletoBancario;

	// 1.3.10. Data do Crédito
	private String dataCreditoBoletoBancario;

	// 1.3.11. Valor Pago
	private BigDecimal valorPagoBoletoBancario;

	// 1.3.12. Valor Tarifa
	private BigDecimal valorTarifaBoletoBancario;

	// 1.3.13. Data do Cancelamento
	private String dataCancelamentoBoletoBancario;

	// 1.3.14. Motivo do Cancelamento
	private String descricaoMotivoCancelamentoBoletoBancario;

	// 1.3.15. Descrição Tipo do Documento
	private String descricaoDocumentoTipoBoletoBancario;

	// 1.3.16.
	private JRBeanCollectionDataSource colecaoRelBolBancDetalheContaBean1;

	private ArrayList arrayRelatorioBoletosBancariosDetalheContaBean1;

	private JRBeanCollectionDataSource colecaoRelBolBancDetalheContaBean2;

	private ArrayList arrayRelatorioBoletosBancariosDetalheContaBean2;

	private JRBeanCollectionDataSource colecaoRelBolBancDetalheContaBean3;

	private ArrayList arrayRelatorioBoletosBancariosDetalheContaBean3;

	private BigDecimal totalValorExcedenteConta;

	// 1.3.17.
	private JRBeanCollectionDataSource colecaoRelBolBancDetalheGuiaBean;

	private ArrayList arrayRelatorioBoletosBancariosDetalheGuiaBean;

	public String getCodigoAgenteArrecadador(){

		return codigoAgenteArrecadador;
	}

	public void setCodigoAgenteArrecadador(String codigoAgenteArrecadador){

		this.codigoAgenteArrecadador = codigoAgenteArrecadador;
	}

	public String getNomeAgenteArrecadador(){

		return nomeAgenteArrecadador;
	}

	public void setNomeAgenteArrecadador(String nomeAgenteArrecadador){

		this.nomeAgenteArrecadador = nomeAgenteArrecadador;
	}

	public String getNumeroSequencialBoletoBancario(){

		return numeroSequencialBoletoBancario;
	}

	public void setNumeroSequencialBoletoBancario(String numeroSequencialBoletoBancario){

		this.numeroSequencialBoletoBancario = numeroSequencialBoletoBancario;
	}

	public String getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}

	public String getNossoNumero(){

		return nossoNumero;
	}

	public void setNossoNumero(String nossoNumero){

		this.nossoNumero = nossoNumero;
	}

	public String getDataEntradaBoletoBancario(){

		return dataEntradaBoletoBancario;
	}

	public void setDataEntradaBoletoBancario(String dataEntradaBoletoBancario){

		this.dataEntradaBoletoBancario = dataEntradaBoletoBancario;
	}

	public String getDataVencimentoBoletoBancario(){

		return dataVencimentoBoletoBancario;
	}

	public void setDataVencimentoBoletoBancario(String dataVencimentoBoletoBancario){

		this.dataVencimentoBoletoBancario = dataVencimentoBoletoBancario;
	}

	public BigDecimal getValorDebitoBoletoBancario(){

		return valorDebitoBoletoBancario;
	}

	public void setValorDebitoBoletoBancario(BigDecimal valorDebitoBoletoBancario){

		this.valorDebitoBoletoBancario = valorDebitoBoletoBancario;
	}

	public String getIdSituacaoBoletoBancario(){

		return idSituacaoBoletoBancario;
	}

	public void setIdSituacaoBoletoBancario(String idSituacaoBoletoBancario){

		this.idSituacaoBoletoBancario = idSituacaoBoletoBancario;
	}

	public String getDescricaoSituacaoBoletoBancario(){

		return descricaoSituacaoBoletoBancario;
	}

	public void setDescricaoSituacaoBoletoBancario(String descricaoSituacaoBoletoBancario){

		this.descricaoSituacaoBoletoBancario = descricaoSituacaoBoletoBancario;
	}

	public String getDescricaoDocumentoTipoBoletoBancario(){

		return descricaoDocumentoTipoBoletoBancario;
	}

	public void setDescricaoDocumentoTipoBoletoBancario(String descricaoDocumentoTipoBoletoBancario){

		this.descricaoDocumentoTipoBoletoBancario = descricaoDocumentoTipoBoletoBancario;
	}

	public String getDataPagamentoBoletoBancario(){

		return dataPagamentoBoletoBancario;
	}

	public void setDataPagamentoBoletoBancario(String dataPagamentoBoletoBancario){

		this.dataPagamentoBoletoBancario = dataPagamentoBoletoBancario;
	}

	public String getDataCreditoBoletoBancario(){

		return dataCreditoBoletoBancario;
	}

	public void setDataCreditoBoletoBancario(String dataCreditoBoletoBancario){

		this.dataCreditoBoletoBancario = dataCreditoBoletoBancario;
	}

	public BigDecimal getValorPagoBoletoBancario(){

		return valorPagoBoletoBancario;
	}

	public void setValorPagoBoletoBancario(BigDecimal valorPagoBoletoBancario){

		this.valorPagoBoletoBancario = valorPagoBoletoBancario;
	}

	public BigDecimal getValorTarifaBoletoBancario(){

		return valorTarifaBoletoBancario;
	}

	public void setValorTarifaBoletoBancario(BigDecimal valorTarifaBoletoBancario){

		this.valorTarifaBoletoBancario = valorTarifaBoletoBancario;
	}

	public String getDataCancelamentoBoletoBancario(){

		return dataCancelamentoBoletoBancario;
	}

	public void setDataCancelamentoBoletoBancario(String dataCancelamentoBoletoBancario){

		this.dataCancelamentoBoletoBancario = dataCancelamentoBoletoBancario;
	}

	public String getDescricaoMotivoCancelamentoBoletoBancario(){

		return descricaoMotivoCancelamentoBoletoBancario;
	}

	public void setDescricaoMotivoCancelamentoBoletoBancario(String descricaoMotivoCancelamentoBoletoBancario){

		this.descricaoMotivoCancelamentoBoletoBancario = descricaoMotivoCancelamentoBoletoBancario;
	}

	public JRBeanCollectionDataSource getColecaoRelBolBancDetalheContaBean1(){

		return colecaoRelBolBancDetalheContaBean1;
	}

	public void setColecaoRelBolBancDetalheContaBean1(
					Collection<RelatorioBoletosBancariosDetalheContaBean> colecaoRelBolBancDetalheContaBean1){

		if(!Util.isVazioOrNulo(colecaoRelBolBancDetalheContaBean1)){
			this.arrayRelatorioBoletosBancariosDetalheContaBean1 = new ArrayList();
			this.arrayRelatorioBoletosBancariosDetalheContaBean1.addAll(colecaoRelBolBancDetalheContaBean1);
			this.colecaoRelBolBancDetalheContaBean1 = new JRBeanCollectionDataSource(this.arrayRelatorioBoletosBancariosDetalheContaBean1);
		}
	}

	public JRBeanCollectionDataSource getColecaoRelBolBancDetalheGuiaBean(){

		return colecaoRelBolBancDetalheGuiaBean;
	}

	public void setColecaoRelBolBancDetalheGuiaBean(Collection<RelatorioBoletosBancariosDetalheGuiaBean> colecaoRelBolBancDetalheGuiaBean){

		this.arrayRelatorioBoletosBancariosDetalheGuiaBean = new ArrayList();
		this.arrayRelatorioBoletosBancariosDetalheGuiaBean.addAll(colecaoRelBolBancDetalheGuiaBean);
		this.colecaoRelBolBancDetalheGuiaBean = new JRBeanCollectionDataSource(this.arrayRelatorioBoletosBancariosDetalheGuiaBean);
	}

	public String getDescricaoAbreviadaSituacaoBoletoBancario(){

		return descricaoAbreviadaSituacaoBoletoBancario;
	}

	public void setDescricaoAbreviadaSituacaoBoletoBancario(String descricaoAbreviadaSituacaoBoletoBancario){

		this.descricaoAbreviadaSituacaoBoletoBancario = descricaoAbreviadaSituacaoBoletoBancario;
	}

	public JRBeanCollectionDataSource getColecaoRelBolBancDetalheContaBean2(){

		return colecaoRelBolBancDetalheContaBean2;
	}

	public void setColecaoRelBolBancDetalheContaBean2(
					Collection<RelatorioBoletosBancariosDetalheContaBean> colecaoRelBolBancDetalheContaBean2){

		if(!Util.isVazioOrNulo(colecaoRelBolBancDetalheContaBean2)){
			this.arrayRelatorioBoletosBancariosDetalheContaBean2 = new ArrayList();
			this.arrayRelatorioBoletosBancariosDetalheContaBean2.addAll(colecaoRelBolBancDetalheContaBean2);
			this.colecaoRelBolBancDetalheContaBean2 = new JRBeanCollectionDataSource(this.arrayRelatorioBoletosBancariosDetalheContaBean2);
		}
	}

	public JRBeanCollectionDataSource getColecaoRelBolBancDetalheContaBean3(){

		return colecaoRelBolBancDetalheContaBean3;
	}

	public void setColecaoRelBolBancDetalheContaBean3(
					Collection<RelatorioBoletosBancariosDetalheContaBean> colecaoRelBolBancDetalheContaBean3){

		if(!Util.isVazioOrNulo(colecaoRelBolBancDetalheContaBean3)){
			this.arrayRelatorioBoletosBancariosDetalheContaBean3 = new ArrayList();
			this.arrayRelatorioBoletosBancariosDetalheContaBean3.addAll(colecaoRelBolBancDetalheContaBean3);
			this.colecaoRelBolBancDetalheContaBean3 = new JRBeanCollectionDataSource(this.arrayRelatorioBoletosBancariosDetalheContaBean3);
		}
	}

	public BigDecimal getTotalValorExcedenteConta(){

		return totalValorExcedenteConta;
	}

	public void setTotalValorExcedenteConta(BigDecimal totalValorExcedenteConta){

		this.totalValorExcedenteConta = totalValorExcedenteConta;
	}

}
