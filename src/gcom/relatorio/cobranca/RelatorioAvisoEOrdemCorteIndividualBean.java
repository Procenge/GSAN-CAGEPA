
package gcom.relatorio.cobranca;

import gcom.relatorio.RelatorioBean;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * [UC3052] Gerar e Emitir Aviso de Corte e Ordem de Corte Individual
 * 
 * @author Hebert Falcão
 * @date 16/03/2012
 */
public class RelatorioAvisoEOrdemCorteIndividualBean
				implements RelatorioBean {

	private static final int RELATORIO_DETAIL_3_COLUNAS = 3;

	private JRBeanCollectionDataSource arrayJRDetail1;

	private ArrayList<Object> arrayRelatorioDetailBean1;

	private JRBeanCollectionDataSource arrayJRDetail2;

	private ArrayList<Object> arrayRelatorioDetailBean2;

	private JRBeanCollectionDataSource arrayJRDetail3;

	private ArrayList<Object> arrayRelatorioDetailBean3;

	private String matricula;

	private String inscricao;

	private String endereco;

	private String cliente;

	private String numDocumentoAvisoCorte;

	private String numDocumentoCorte;

	private String debitosAnteriores;

	private String valorTotalDebito;

	private String representacaoNumericaCodBarraFormatada;

	private String representacaoNumericaCodBarraSemDigito;

	private String dataDebitoAnterior;

	private String numeroItens;

	private String localInstalacao;

	private String hidrometro;

	private String numeroOrdemServico;

	public String getMatricula(){

		return matricula;
	}

	public void setMatricula(String matricula){

		this.matricula = matricula;
	}

	public String getInscricao(){

		return inscricao;
	}

	public void setInscricao(String inscricao){

		this.inscricao = inscricao;
	}

	public String getEndereco(){

		return endereco;
	}

	public void setEndereco(String endereco){

		this.endereco = endereco;
	}

	public String getCliente(){

		return cliente;
	}

	public void setCliente(String cliente){

		this.cliente = cliente;
	}

	public String getNumDocumentoAvisoCorte(){

		return numDocumentoAvisoCorte;
	}

	public void setNumDocumentoAvisoCorte(String numDocumentoAvisoCorte){

		this.numDocumentoAvisoCorte = numDocumentoAvisoCorte;
	}

	public String getNumDocumentoCorte(){

		return numDocumentoCorte;
	}

	public void setNumDocumentoCorte(String numDocumentoCorte){

		this.numDocumentoCorte = numDocumentoCorte;
	}

	public String getDebitosAnteriores(){

		return debitosAnteriores;
	}

	public void setDebitosAnteriores(String debitosAnteriores){

		this.debitosAnteriores = debitosAnteriores;
	}

	public String getValorTotalDebito(){

		return valorTotalDebito;
	}

	public void setValorTotalDebito(String valorTotalDebito){

		this.valorTotalDebito = valorTotalDebito;
	}

	public String getRepresentacaoNumericaCodBarraFormatada(){

		return representacaoNumericaCodBarraFormatada;
	}

	public void setRepresentacaoNumericaCodBarraFormatada(String representacaoNumericaCodBarraFormatada){

		this.representacaoNumericaCodBarraFormatada = representacaoNumericaCodBarraFormatada;
	}

	public String getRepresentacaoNumericaCodBarraSemDigito(){

		return representacaoNumericaCodBarraSemDigito;
	}

	public void setRepresentacaoNumericaCodBarraSemDigito(String representacaoNumericaCodBarraSemDigito){

		this.representacaoNumericaCodBarraSemDigito = representacaoNumericaCodBarraSemDigito;
	}

	public String getDataDebitoAnterior(){

		return dataDebitoAnterior;
	}

	public void setDataDebitoAnterior(String dataDebitoAnterior){

		this.dataDebitoAnterior = dataDebitoAnterior;
	}

	public String getNumeroItens(){

		return numeroItens;
	}

	public void setNumeroItens(String numeroItens){

		this.numeroItens = numeroItens;
	}

	public String getLocalInstalacao(){

		return localInstalacao;
	}

	public void setLocalInstalacao(String localInstalacao){

		this.localInstalacao = localInstalacao;
	}

	public String getHidrometro(){

		return hidrometro;
	}

	public void setHidrometro(String hidrometro){

		this.hidrometro = hidrometro;
	}

	public String getNumeroOrdemServico(){

		return numeroOrdemServico;
	}

	public void setNumeroOrdemServico(String numeroOrdemServico){

		this.numeroOrdemServico = numeroOrdemServico;
	}

	public void gerarDetail(Collection<String> collMesAno, Collection<String> collVencimento, Collection<BigDecimal> collValor){

		Collection<Object> colecaoDetail = new ArrayList<Object>();
		Collection<String> collStringValor = new ArrayList<String>();

		for(BigDecimal valor : collValor){
			collStringValor.add(Util.formatarMoedaReal(valor));
		}

		Object[] linhasMesAno = this.gerarLinhasDetail(collMesAno).toArray();
		Object[] linhasVencimento = this.gerarLinhasDetail(collVencimento).toArray();
		Object[] linhasValor = this.gerarLinhasDetail(collStringValor).toArray();

		for(int i = 0; i < linhasMesAno.length; i++){
			String[] linhaMesAno = (String[]) linhasMesAno[i];
			String[] linhaVencimento = (String[]) linhasVencimento[i];
			String[] linhaValor = (String[]) linhasValor[i];

			Object relatorio = new RelatorioAvisoCorteDetailBean(linhaMesAno, linhaVencimento, linhaValor);
			colecaoDetail.add(relatorio);
		}

		this.arrayRelatorioDetailBean1 = new ArrayList<Object>();
		this.arrayRelatorioDetailBean1.addAll(colecaoDetail);
		this.arrayJRDetail1 = new JRBeanCollectionDataSource(this.arrayRelatorioDetailBean1);

		this.arrayRelatorioDetailBean2 = new ArrayList<Object>();
		this.arrayRelatorioDetailBean2.addAll(colecaoDetail);
		this.arrayJRDetail2 = new JRBeanCollectionDataSource(this.arrayRelatorioDetailBean2);

		this.arrayRelatorioDetailBean3 = new ArrayList<Object>();
		this.arrayRelatorioDetailBean3.addAll(colecaoDetail);
		this.arrayJRDetail3 = new JRBeanCollectionDataSource(this.arrayRelatorioDetailBean3);
	}

	private Collection<String[]> gerarLinhasDetail(Collection<String> collString){

		Collection<String[]> retorno = new ArrayList<String[]>();
		String[] item = new String[RELATORIO_DETAIL_3_COLUNAS];
		int countLine = 0;

		for(String valor : collString){
			if((countLine + 1) <= RELATORIO_DETAIL_3_COLUNAS){
				item[countLine] = valor;
			}

			if((countLine + 1) == RELATORIO_DETAIL_3_COLUNAS){
				String[] linhaValor = new String[RELATORIO_DETAIL_3_COLUNAS];

				for(int i = 1; i <= RELATORIO_DETAIL_3_COLUNAS; i++){
					linhaValor[i - 1] = item[i - 1];
				}

				retorno.add(linhaValor);
				item[0] = null;
				item[1] = null;
				item[2] = null;
				countLine = 0;
			}else{
				countLine++;
			}
		}

		if(countLine != 0){
			if(item[0] == null){
				item[0] = "";
			}

			if(item[1] == null){
				item[1] = "";
			}

			if(item[2] == null){
				item[2] = "";
			}

			retorno.add(item);
		}

		return retorno;
	}

	public JRBeanCollectionDataSource getArrayJRDetail1(){

		return arrayJRDetail1;
	}

	public void setArrayJRDetail1(JRBeanCollectionDataSource arrayJRDetail1){

		this.arrayJRDetail1 = arrayJRDetail1;
	}

	public ArrayList<Object> getArrayRelatorioDetailBean1(){

		return arrayRelatorioDetailBean1;
	}

	public void setArrayRelatorioDetailBean1(ArrayList<Object> arrayRelatorioDetailBean1){

		this.arrayRelatorioDetailBean1 = arrayRelatorioDetailBean1;
	}

	public JRBeanCollectionDataSource getArrayJRDetail2(){

		return arrayJRDetail2;
	}

	public void setArrayJRDetail2(JRBeanCollectionDataSource arrayJRDetail2){

		this.arrayJRDetail2 = arrayJRDetail2;
	}

	public ArrayList<Object> getArrayRelatorioDetailBean2(){

		return arrayRelatorioDetailBean2;
	}

	public void setArrayRelatorioDetailBean2(ArrayList<Object> arrayRelatorioDetailBean2){

		this.arrayRelatorioDetailBean2 = arrayRelatorioDetailBean2;
	}

	public JRBeanCollectionDataSource getArrayJRDetail3(){

		return arrayJRDetail3;
	}

	public void setArrayJRDetail3(JRBeanCollectionDataSource arrayJRDetail3){

		this.arrayJRDetail3 = arrayJRDetail3;
	}

	public ArrayList<Object> getArrayRelatorioDetailBean3(){

		return arrayRelatorioDetailBean3;
	}

	public void setArrayRelatorioDetailBean3(ArrayList<Object> arrayRelatorioDetailBean3){

		this.arrayRelatorioDetailBean3 = arrayRelatorioDetailBean3;
	}

}
