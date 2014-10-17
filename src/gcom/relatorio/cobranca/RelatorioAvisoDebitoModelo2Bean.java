
package gcom.relatorio.cobranca;

import gcom.cobranca.bean.CobrancaDocumentoItemHelper;
import gcom.cobranca.bean.EmitirRelatorioAvisoDebitoHelper;
import gcom.relatorio.RelatorioBean;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class RelatorioAvisoDebitoModelo2Bean
				implements RelatorioBean {

	// Primeira Página

	private JRBeanCollectionDataSource arrayJRDetail1;

	private ArrayList<Object> arrayRelatorioAvisoDebitoDetailBean1;

	private String matriculaImovel1;

	private String inscricaoImovel1;

	private int sequencialImpressao1;

	private String dataTextoCliente1;

	private ArrayList<Object> colecaoContas1;

	private String dataEmissao1;

	private Integer numeroSequencialDocumento1;

	private String valorDocumentoCorrigido1;

	private Integer numeroPagina1;

	private String valorDocumento1;

	private JRBeanCollectionDataSource beansVerso1;

	private ArrayList arrayVersoDetailBean1;

	private String quantidadeContasEmDebito1;

	// Segunda Página

	private JRBeanCollectionDataSource arrayJRDetail2;

	private ArrayList<Object> arrayRelatorioAvisoDebitoDetailBean2;

	private String matriculaImovel2;

	private String inscricaoImovel2;

	private int sequencialImpressao2;

	private String dataTextoCliente2;

	private ArrayList<Object> colecaoContas2;

	private String dataEmissao2;

	private Integer numeroSequencialDocumento2;

	private String valorDocumentoCorrigido2;

	private Integer numeroPagina2;

	private String valorDocumento2;

	private String representacaoNumericaCodBarra1;

	private String representacaoNumericaCodBarra2;

	private String representacaoNumericaCodBarraSemDigito1;

	private String representacaoNumericaCodBarraSemDigito2;

	private JRBeanCollectionDataSource beansVerso2;

	private ArrayList arrayVersoDetailBean2;

	private String quantidadeContasEmDebito2;

	public RelatorioAvisoDebitoModelo2Bean(EmitirRelatorioAvisoDebitoHelper helper1, EmitirRelatorioAvisoDebitoHelper helper2) {

		if(helper1 != null){

			this.matriculaImovel1 = helper1.getMatriculaImovel();
			this.inscricaoImovel1 = helper1.getInscricaoImovel();
			this.sequencialImpressao1 = helper1.getSequencialImpressao();
			this.dataTextoCliente1 = helper1.getDataTextoClienteFormatada();
			this.dataEmissao1 = helper1.getDataEmissaoFormatada();
			this.numeroSequencialDocumento1 = helper1.getNumeroSequenciaDocumento();
			this.valorDocumentoCorrigido1 = obterValorTotalDebitosCorrigidoFormatado(helper1.getValorTotalDebitosCorrigidos());
			this.numeroPagina1 = helper1.getNumeroPagina();
			this.valorDocumento1 = helper1.getValorDocumentoFormatado();
			this.representacaoNumericaCodBarra1 = helper1.getRepresentacaoNumericaCodBarra();
			this.representacaoNumericaCodBarraSemDigito1 = helper1.getRepresentacaoNumericaCodBarraSemDigito();
			this.numeroPagina1 = helper1.getNumeroPagina();

			if(helper1.getQuantidadeContasEmDebito() != null){

				this.quantidadeContasEmDebito1 = helper1.getQuantidadeContasEmDebito().toString();
			}

			this.arrayRelatorioAvisoDebitoDetailBean1 = new ArrayList<Object>();
			this.arrayRelatorioAvisoDebitoDetailBean1.addAll(this.gerarDetailSubRelatorioAvisoDebitoModelo2(helper1.getColecaoItemConta(),
							this.numeroSequencialDocumento1));
			this.arrayJRDetail1 = new JRBeanCollectionDataSource(this.arrayRelatorioAvisoDebitoDetailBean1);

			// VERSO DO AVISO (Segunda Página)
			RelatorioAvisoDebitoModelo2SubVersoBean beanVerso1 = new RelatorioAvisoDebitoModelo2SubVersoBean();
			beanVerso1.setNomeClienteEntrega(helper1.getNomeClienteUsuario());
			beanVerso1.setEnderecoClienteEntrega(helper1.getEnderecoFormatado());
			beanVerso1.setInscricao(helper1.getInscricaoImovel());
			beanVerso1.setMatricula(helper1.getMatriculaImovel());

			Collection colecaoBeanSubRelatorioVerso = new ArrayList<RelatorioAvisoDebitoModelo2SubVersoBean>();
			colecaoBeanSubRelatorioVerso.add(beanVerso1);
			this.setarBeansSubRelatorioVerso1(colecaoBeanSubRelatorioVerso);
		}

		if(helper2 != null){

			this.matriculaImovel2 = helper2.getMatriculaImovel();
			this.inscricaoImovel2 = helper2.getInscricaoImovel();
			this.sequencialImpressao2 = helper2.getSequencialImpressao();
			this.dataTextoCliente2 = helper2.getDataTextoClienteFormatada();
			this.dataEmissao2 = helper2.getDataEmissaoFormatada();
			this.numeroSequencialDocumento2 = helper2.getNumeroSequenciaDocumento();
			this.valorDocumentoCorrigido2 = obterValorTotalDebitosCorrigidoFormatado(helper2.getValorTotalDebitosCorrigidos());
			this.numeroPagina2 = helper2.getNumeroPagina();
			this.valorDocumento2 = helper2.getValorDocumentoFormatado();
			this.representacaoNumericaCodBarra2 = helper2.getRepresentacaoNumericaCodBarra();
			this.representacaoNumericaCodBarraSemDigito2 = helper2.getRepresentacaoNumericaCodBarraSemDigito();

			if(helper2.getQuantidadeContasEmDebito() != null){

				this.quantidadeContasEmDebito2 = helper2.getQuantidadeContasEmDebito().toString();
			}

			this.arrayRelatorioAvisoDebitoDetailBean2 = new ArrayList<Object>();
			this.arrayRelatorioAvisoDebitoDetailBean2
				.addAll(this.gerarDetailSubRelatorioAvisoDebitoModelo2(helper2.getColecaoItemConta(),
			this.numeroSequencialDocumento2));
			this.arrayJRDetail2 = new JRBeanCollectionDataSource(this.arrayRelatorioAvisoDebitoDetailBean2);

			// VERSO DO AVISO (Segunda Página)
			RelatorioAvisoDebitoModelo2SubVersoBean beanVerso2 = new RelatorioAvisoDebitoModelo2SubVersoBean();
			beanVerso2.setNomeClienteEntrega(helper2.getNomeClienteUsuario());
			beanVerso2.setEnderecoClienteEntrega(helper2.getEnderecoFormatado());
			beanVerso2.setInscricao(helper2.getInscricaoImovel());
			beanVerso2.setMatricula(helper2.getMatriculaImovel());

			Collection colecaoBeanSubRelatorioVerso = new ArrayList<RelatorioAvisoDebitoModelo2SubVersoBean>();
			colecaoBeanSubRelatorioVerso.add(beanVerso2);
			this.setarBeansSubRelatorioVerso2(colecaoBeanSubRelatorioVerso);

		}

	}

	private Collection<Object> gerarDetailSubRelatorioAvisoDebitoModelo2(Collection<CobrancaDocumentoItemHelper> itensConta,
					Integer numeroSequencialDocumento){

		List<Object> colecaoContasDetail = new ArrayList<Object>();
		String referenciaContaMaisAntiga = null;
		Iterator<CobrancaDocumentoItemHelper> iter = itensConta.iterator();

		for(int i = 1; i <= 10; i++){

			if(iter.hasNext()){

				CobrancaDocumentoItemHelper helper = iter.next();

				if(i == 1 && itensConta.size() == 10){

					referenciaContaMaisAntiga = helper.getAnoMesReferenciaConta();
				}

				if(i == 10){

					colecaoContasDetail.add(0, new SubRelatorioAvisoDebitoModelo2(helper.getAnoMesReferenciaConta(),
									referenciaContaMaisAntiga, Util.formataBigDecimal(helper.getValorItemCobrado(), 2, true),
									numeroSequencialDocumento.toString()));
				}else{

					colecaoContasDetail.add(new SubRelatorioAvisoDebitoModelo2(helper.getAnoMesReferenciaConta(), helper
									.getDataVencimentoFormatada(), Util.formataBigDecimal(helper.getValorItemCobrado(), 2, true),
									numeroSequencialDocumento.toString()));
				}
			}
		}

		return colecaoContasDetail;
	}

	/**
	 * @return the arrayJRDetail1
	 */
	public JRBeanCollectionDataSource getArrayJRDetail1(){

		return arrayJRDetail1;
	}

	/**
	 * @param arrayJRDetail1
	 *            the arrayJRDetail1 to set
	 */
	public void setArrayJRDetail1(JRBeanCollectionDataSource arrayJRDetail1){

		this.arrayJRDetail1 = arrayJRDetail1;
	}

	/**
	 * @return the matriculaImovel1
	 */
	public String getMatriculaImovel1(){

		return matriculaImovel1;
	}

	/**
	 * @param matriculaImovel1
	 *            the matriculaImovel1 to set
	 */
	public void setMatriculaImovel1(String matriculaImovel1){

		this.matriculaImovel1 = matriculaImovel1;
	}

	/**
	 * @return the inscricaoImovel
	 */
	public String getInscricaoImovel1(){

		return inscricaoImovel1;
	}

	/**
	 * @param inscricaoImovel
	 *            the inscricaoImovel to set
	 */
	public void setInscricaoImovel1(String inscricaoImovel1){

		this.inscricaoImovel1 = inscricaoImovel1;
	}

	/**
	 * @return the sequencialImpressao1
	 */
	public int getSequencialImpressao1(){

		return sequencialImpressao1;
	}

	/**
	 * @param sequencialImpressao1
	 *            the sequencialImpressao1 to set
	 */
	public void setSequencialImpressao1(int sequencialImpressao1){

		this.sequencialImpressao1 = sequencialImpressao1;
	}

	/**
	 * @return the dataTextoCliente1
	 */
	public String getDataTextoCliente1(){

		return dataTextoCliente1;
	}

	/**
	 * @param dataTextoCliente1
	 *            the dataTextoCliente1 to set
	 */
	public void setDataTextoCliente1(String dataTextoCliente1){

		this.dataTextoCliente1 = dataTextoCliente1;
	}

	/**
	 * @return the colecaoContas1
	 */
	public ArrayList<Object> getColecaoContas1(){

		return colecaoContas1;
	}

	/**
	 * @param colecaoContas1
	 *            the colecaoContas1 to set
	 */
	public void setColecaoContas1(ArrayList<Object> colecaoContas1){

		this.colecaoContas1 = colecaoContas1;
	}

	/**
	 * @return the dataEmissao1
	 */
	public String getDataEmissao1(){

		return dataEmissao1;
	}

	/**
	 * @param dataEmissao1
	 *            the dataEmissao1 to set
	 */
	public void setDataEmissao1(String dataEmissao1){

		this.dataEmissao1 = dataEmissao1;
	}

	public Integer getNumeroSequencialDocumento1(){

		return numeroSequencialDocumento1;
	}

	public void setNumeroSequencialDocumento1(Integer numeroSequencialDocumento1){

		this.numeroSequencialDocumento1 = numeroSequencialDocumento1;
	}

	public String getValorDocumentoCorrigido1(){

		return valorDocumentoCorrigido1;
	}

	public void setValorDocumentoCorrigido1(String valorDocumentoCorrigido1){

		this.valorDocumentoCorrigido1 = valorDocumentoCorrigido1;
	}

	/**
	 * @return the numeroPagina1
	 */
	public Integer getNumeroPagina1(){

		return numeroPagina1;
	}

	/**
	 * @param numeroPagina1
	 *            the numeroPagina1 to set
	 */
	public void setNumeroPagina1(Integer numeroPagina1){

		this.numeroPagina1 = numeroPagina1;
	}

	/**
	 * @return the arrayJRDetail2
	 */
	public JRBeanCollectionDataSource getArrayJRDetail2(){

		return arrayJRDetail2;
	}

	/**
	 * @param arrayJRDetail2
	 *            the arrayJRDetail2 to set
	 */
	public void setArrayJRDetail2(JRBeanCollectionDataSource arrayJRDetail2){

		this.arrayJRDetail2 = arrayJRDetail2;
	}

	/**
	 * @return the matriculaImovel2
	 */
	public String getMatriculaImovel2(){

		return matriculaImovel2;
	}

	/**
	 * @param matriculaImovel2
	 *            the matriculaImovel2 to set
	 */
	public void setMatriculaImovel2(String matriculaImovel2){

		this.matriculaImovel2 = matriculaImovel2;
	}

	/**
	 * @return the inscricaoImovel2
	 */
	public String getInscricaoImovel2(){

		return inscricaoImovel2;
	}

	/**
	 * @param inscricaoImovel2
	 *            the inscricaoImovel2 to set
	 */
	public void setInscricaoImovel2(String inscricaoImovel2){

		this.inscricaoImovel2 = inscricaoImovel2;
	}

	/**
	 * @return the sequencialImpressao2
	 */
	public int getSequencialImpressao2(){

		return sequencialImpressao2;
	}

	/**
	 * @param sequencialImpressao2
	 *            the sequencialImpressao2 to set
	 */
	public void setSequencialImpressao2(int sequencialImpressao2){

		this.sequencialImpressao2 = sequencialImpressao2;
	}

	/**
	 * @return the dataTextoCliente2
	 */
	public String getDataTextoCliente2(){

		return dataTextoCliente2;
	}

	/**
	 * @param dataTextoCliente2
	 *            the dataTextoCliente2 to set
	 */
	public void setDataTextoCliente2(String dataTextoCliente2){

		this.dataTextoCliente2 = dataTextoCliente2;
	}

	/**
	 * @return the colecaoContas2
	 */
	public ArrayList<Object> getColecaoContas2(){

		return colecaoContas2;
	}

	/**
	 * @param colecaoContas2
	 *            the colecaoContas2 to set
	 */
	public void setColecaoContas2(ArrayList<Object> colecaoContas2){

		this.colecaoContas2 = colecaoContas2;
	}

	/**
	 * @return the dataEmissao2
	 */
	public String getDataEmissao2(){

		return dataEmissao2;
	}

	/**
	 * @param dataEmissao2
	 *            the dataEmissao2 to set
	 */
	public void setDataEmissao2(String dataEmissao2){

		this.dataEmissao2 = dataEmissao2;
	}

	public Integer getNumeroSequencialDocumento2(){

		return numeroSequencialDocumento2;
	}

	public void setNumeroSequencialDocumento2(Integer numeroSequencialDocumento2){

		this.numeroSequencialDocumento2 = numeroSequencialDocumento2;
	}



	
	public String getValorDocumentoCorrigido2(){

		return valorDocumentoCorrigido2;
	}

	public void setValorDocumentoCorrigido2(String valorDocumentoCorrigido2){

		this.valorDocumentoCorrigido2 = valorDocumentoCorrigido2;
	}

	/**
	 * @return the numeroPagina2
	 */
	public Integer getNumeroPagina2(){

		return numeroPagina2;
	}

	/**
	 * @param numeroPagina2
	 *            the numeroPagina2 to set
	 */
	public void setNumeroPagina2(Integer numeroPagina2){

		this.numeroPagina2 = numeroPagina2;
	}

	/**
	 * @return the valorDocumento1
	 */
	public String getValorDocumento1(){

		return valorDocumento1;
	}

	/**
	 * @param valorDocumento1
	 *            the valorDocumento1 to set
	 */
	public void setValorDocumento1(String valorDocumento1){

		this.valorDocumento1 = valorDocumento1;
	}

	/**
	 * @return the valorDocumento2
	 */
	public String getValorDocumento2(){

		return valorDocumento2;
	}

	/**
	 * @param valorDocumento2
	 *            the valorDocumento2 to set
	 */
	public void setValorDocumento2(String valorDocumento2){

		this.valorDocumento2 = valorDocumento2;
	}

	public String getRepresentacaoNumericaCodBarra1(){

		return representacaoNumericaCodBarra1;
	}

	public void setRepresentacaoNumericaCodBarra1(String representacaoNumericaCodBarra1){

		this.representacaoNumericaCodBarra1 = representacaoNumericaCodBarra1;
	}

	public String getRepresentacaoNumericaCodBarra2(){

		return representacaoNumericaCodBarra2;
	}

	public void setRepresentacaoNumericaCodBarra2(String representacaoNumericaCodBarra2){

		this.representacaoNumericaCodBarra2 = representacaoNumericaCodBarra2;
	}

	public String getRepresentacaoNumericaCodBarraSemDigito1(){

		return representacaoNumericaCodBarraSemDigito1;
	}

	public void setRepresentacaoNumericaCodBarraSemDigito1(String representacaoNumericaCodBarraSemDigito1){

		this.representacaoNumericaCodBarraSemDigito1 = representacaoNumericaCodBarraSemDigito1;
	}

	public String getRepresentacaoNumericaCodBarraSemDigito2(){

		return representacaoNumericaCodBarraSemDigito2;
	}

	public void setRepresentacaoNumericaCodBarraSemDigito2(String representacaoNumericaCodBarraSemDigito2){

		this.representacaoNumericaCodBarraSemDigito2 = representacaoNumericaCodBarraSemDigito2;
	}

	public JRBeanCollectionDataSource getBeansVerso1(){

		return beansVerso1;
	}

	public void setBeansVerso1(JRBeanCollectionDataSource beansVerso1){

		this.beansVerso1 = beansVerso1;
	}

	public ArrayList getArrayVersoDetailBean1(){

		return arrayVersoDetailBean1;
	}

	public void setArrayVersoDetailBean1(ArrayList arrayVersoDetailBean1){

		this.arrayVersoDetailBean1 = arrayVersoDetailBean1;
	}

	public JRBeanCollectionDataSource getBeansVerso2(){

		return beansVerso2;
	}

	public void setBeansVerso2(JRBeanCollectionDataSource beansVerso2){

		this.beansVerso2 = beansVerso2;
	}

	public ArrayList getArrayVersoDetailBean2(){

		return arrayVersoDetailBean2;
	}

	public void setArrayVersoDetailBean2(ArrayList arrayVersoDetailBean2){

		this.arrayVersoDetailBean2 = arrayVersoDetailBean2;
	}

	public void setarBeansSubRelatorioVerso1(Collection colecaoDetail){

		this.arrayVersoDetailBean1 = new ArrayList();
		this.arrayVersoDetailBean1.addAll(colecaoDetail);
		this.beansVerso1 = new JRBeanCollectionDataSource(this.arrayVersoDetailBean1);
	}

	public void setarBeansSubRelatorioVerso2(Collection colecaoDetail){

		this.arrayVersoDetailBean2 = new ArrayList();
		this.arrayVersoDetailBean2.addAll(colecaoDetail);
		this.beansVerso2 = new JRBeanCollectionDataSource(this.arrayVersoDetailBean2);
	}

	private String obterValorTotalDebitosCorrigidoFormatado(BigDecimal valorDebitosCorrigido){

		String retorno = "**************";

		if(valorDebitosCorrigido != null){

			retorno = Util.completarStringComValorEsquerda(Util.formataBigDecimal(valorDebitosCorrigido, 2, true), "*", 14);
		}

		return retorno;
	}

	public String getQuantidadeContasEmDebito1(){

		return quantidadeContasEmDebito1;
	}

	public void setQuantidadeContasEmDebito1(String quantidadeContasEmDebito1){

		this.quantidadeContasEmDebito1 = quantidadeContasEmDebito1;
	}

	public String getQuantidadeContasEmDebito2(){

		return quantidadeContasEmDebito2;
	}

	public void setQuantidadeContasEmDebito2(String quantidadeContasEmDebito2){

		this.quantidadeContasEmDebito2 = quantidadeContasEmDebito2;
	}

	public ArrayList<Object> getArrayRelatorioAvisoDebitoDetailBean1(){

		return arrayRelatorioAvisoDebitoDetailBean1;
	}

	public void setArrayRelatorioAvisoDebitoDetailBean1(ArrayList<Object> arrayRelatorioAvisoDebitoDetailBean1){

		this.arrayRelatorioAvisoDebitoDetailBean1 = arrayRelatorioAvisoDebitoDetailBean1;
	}

	public ArrayList<Object> getArrayRelatorioAvisoDebitoDetailBean2(){

		return arrayRelatorioAvisoDebitoDetailBean2;
	}

	public void setArrayRelatorioAvisoDebitoDetailBean2(ArrayList<Object> arrayRelatorioAvisoDebitoDetailBean2){

		this.arrayRelatorioAvisoDebitoDetailBean2 = arrayRelatorioAvisoDebitoDetailBean2;
	}

}
