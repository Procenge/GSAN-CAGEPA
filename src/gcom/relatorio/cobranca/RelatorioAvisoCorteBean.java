
package gcom.relatorio.cobranca;

import gcom.cobranca.bean.EmitirAvisoCobrancaHelper;
import gcom.relatorio.RelatorioBean;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class RelatorioAvisoCorteBean
				implements RelatorioBean {

	private static final int RELATORIO_DETAIL_3_COLUNAS = 3;

	// Primeira Aba

	private JRBeanCollectionDataSource arrayJRDetail1;

	private ArrayList<Object> arrayRelatorioAvisoCorteDetail1Bean;

	private String inscricao1;

	private String matricula1;

	private String roteiro1;

	private String hm1;

	private String programa1;

	private String cliente1;

	private String endereco1;

	private String bairro1;

	private String numero1;

	private String dataComparecimento1;

	private String dataDebitoAnterior1;

	private String debitosAnteriores1;

	private String valorTotalDebito1;

	private String representacaoNumericaCodBarraFormatada1;

	private String representacaoNumericaCodBarraSemDigito1;

	private String dataPermanenciaRegistro1;

	private Integer numeroPagina1;

	// Segunda Aba

	private JRBeanCollectionDataSource arrayJRDetail2;

	private ArrayList<Object> arrayRelatorioAvisoCorteDetail2Bean;

	private String inscricao2;

	private String matricula2;

	private String roteiro2;

	private String hm2;

	private String programa2;

	private String cliente2;

	private String endereco2;

	private String bairro2;

	private Integer numeroPagina2;

	public String getDataComparecimento1(){

		return dataComparecimento1;
	}

	public void setDataComparecimento1(String dataComparecimento1){

		this.dataComparecimento1 = dataComparecimento1;
	}

	public String getDataComparecimento2(){

		return dataComparecimento2;
	}

	public void setDataComparecimento2(String dataComparecimento2){

		this.dataComparecimento2 = dataComparecimento2;
	}

	private String numero2;

	private String dataComparecimento2;

	private String dataDebitoAnterior2;

	private String debitosAnteriores2;

	private String valorTotalDebito2;

	private String representacaoNumericaCodBarraFormatada2;

	private String representacaoNumericaCodBarraSemDigito2;

	private String dataPermanenciaRegistro2;

	public RelatorioAvisoCorteBean(EmitirAvisoCobrancaHelper emitirAvisoCobrancaHelper1,
									EmitirAvisoCobrancaHelper emitirAvisoCobrancaHelper2) {

		if(emitirAvisoCobrancaHelper1 != null){
			this.inscricao1 = emitirAvisoCobrancaHelper1.getInscricao() != null ? emitirAvisoCobrancaHelper1.getInscricao() : "";
			this.matricula1 = emitirAvisoCobrancaHelper1.getMatricula() != null ? emitirAvisoCobrancaHelper1.getMatricula() : "";
			this.roteiro1 = emitirAvisoCobrancaHelper1.getRoteiro() != null ? emitirAvisoCobrancaHelper1.getRoteiro() : "";
			this.hm1 = emitirAvisoCobrancaHelper1.getHidrometro() != null ? emitirAvisoCobrancaHelper1.getHidrometro() : "";
			this.programa1 = emitirAvisoCobrancaHelper1.getAcaoCobranca() != null ? emitirAvisoCobrancaHelper1.getAcaoCobranca() : "";
			this.cliente1 = emitirAvisoCobrancaHelper1.getNomeCliente() != null ? emitirAvisoCobrancaHelper1.getNomeCliente() : "";
			this.endereco1 = emitirAvisoCobrancaHelper1.getEndereco() != null ? emitirAvisoCobrancaHelper1.getEndereco() : "";
			this.bairro1 = emitirAvisoCobrancaHelper1.getBairro() != null ? emitirAvisoCobrancaHelper1.getBairro() : "";
			this.numero1 = "1"; // Retirar isso
			this.dataDebitoAnterior1 = emitirAvisoCobrancaHelper1.getDataDebitoAnterior() != null ? emitirAvisoCobrancaHelper1
							.getDataDebitoAnterior() : "";
			this.debitosAnteriores1 = emitirAvisoCobrancaHelper1.getValorDebitosAnteriores() != null ? Util
							.formatarMoedaReal(emitirAvisoCobrancaHelper1.getValorDebitosAnteriores()) : "";
			this.valorTotalDebito1 = emitirAvisoCobrancaHelper1.getValorTotal() != null ? Util.formatarMoedaReal(emitirAvisoCobrancaHelper1
							.getValorTotal()) : "";
			this.dataComparecimento1 = emitirAvisoCobrancaHelper1.getDataComparecimento().toString();
			this.representacaoNumericaCodBarraFormatada1 = emitirAvisoCobrancaHelper1.getRepresentacaoNumericaCodBarraFormatada();
			this.representacaoNumericaCodBarraSemDigito1 = emitirAvisoCobrancaHelper1.getRepresentacaoNumericaCodBarraSemDigito();

			this.arrayRelatorioAvisoCorteDetail1Bean = new ArrayList<Object>();

			this.arrayRelatorioAvisoCorteDetail1Bean.addAll(this.gerarDetail(emitirAvisoCobrancaHelper1.getMesAno(),
							emitirAvisoCobrancaHelper1.getVencimento(), emitirAvisoCobrancaHelper1.getValor()));

			this.arrayJRDetail1 = new JRBeanCollectionDataSource(this.arrayRelatorioAvisoCorteDetail1Bean);
			this.dataPermanenciaRegistro1 = emitirAvisoCobrancaHelper1.getDataImpressao() != null ? emitirAvisoCobrancaHelper1
							.getDataImpressao() : "";

			this.numeroPagina1 = emitirAvisoCobrancaHelper1.getNumeroPagina();
		}

		if(emitirAvisoCobrancaHelper2 != null){
			this.inscricao2 = emitirAvisoCobrancaHelper2.getInscricao() != null ? emitirAvisoCobrancaHelper2.getInscricao() : "";
			this.matricula2 = emitirAvisoCobrancaHelper2.getMatricula() != null ? emitirAvisoCobrancaHelper2.getMatricula() : "";
			this.roteiro2 = emitirAvisoCobrancaHelper2.getRoteiro() != null ? emitirAvisoCobrancaHelper2.getRoteiro() : "";
			this.hm2 = emitirAvisoCobrancaHelper2.getHidrometro() != null ? emitirAvisoCobrancaHelper2.getHidrometro() : "";
			this.programa2 = emitirAvisoCobrancaHelper2.getAcaoCobranca() != null ? emitirAvisoCobrancaHelper2.getAcaoCobranca() : "";
			this.cliente2 = emitirAvisoCobrancaHelper2.getNomeCliente() != null ? emitirAvisoCobrancaHelper2.getNomeCliente() : "";
			this.endereco2 = emitirAvisoCobrancaHelper2.getEndereco() != null ? emitirAvisoCobrancaHelper2.getEndereco() : "";
			this.bairro2 = emitirAvisoCobrancaHelper2.getBairro() != null ? emitirAvisoCobrancaHelper2.getBairro() : "";
			this.numero2 = "2";
			this.dataDebitoAnterior2 = emitirAvisoCobrancaHelper2.getDataDebitoAnterior() != null ? emitirAvisoCobrancaHelper2
							.getDataDebitoAnterior() : "";
			this.debitosAnteriores2 = emitirAvisoCobrancaHelper2.getValorDebitosAnteriores() != null ? Util
							.formatarMoedaReal(emitirAvisoCobrancaHelper2.getValorDebitosAnteriores()) : "";
			this.valorTotalDebito2 = emitirAvisoCobrancaHelper2.getValorTotal() != null ? Util.formatarMoedaReal(emitirAvisoCobrancaHelper2
							.getValorTotal()) : "";
			this.dataComparecimento2 = emitirAvisoCobrancaHelper2.getDataComparecimento().toString();
			this.representacaoNumericaCodBarraFormatada2 = emitirAvisoCobrancaHelper2.getRepresentacaoNumericaCodBarraFormatada() != null ? emitirAvisoCobrancaHelper2
							.getRepresentacaoNumericaCodBarraFormatada()
							: "";
			this.representacaoNumericaCodBarraSemDigito2 = emitirAvisoCobrancaHelper2.getRepresentacaoNumericaCodBarraSemDigito() != null ? emitirAvisoCobrancaHelper2
							.getRepresentacaoNumericaCodBarraSemDigito()
							: "000000";
			this.arrayRelatorioAvisoCorteDetail2Bean = new ArrayList<Object>();
			this.arrayRelatorioAvisoCorteDetail2Bean.addAll(this.gerarDetail(emitirAvisoCobrancaHelper2.getMesAno(),
							emitirAvisoCobrancaHelper2.getVencimento(), emitirAvisoCobrancaHelper2.getValor()));
			this.arrayJRDetail2 = new JRBeanCollectionDataSource(this.arrayRelatorioAvisoCorteDetail2Bean);
			this.dataPermanenciaRegistro2 = emitirAvisoCobrancaHelper2.getDataImpressao() != null ? emitirAvisoCobrancaHelper2
							.getDataImpressao() : "";
			this.numeroPagina2 = emitirAvisoCobrancaHelper2.getNumeroPagina();
		}
	}

	public String getInscricao1(){

		return inscricao1;
	}

	public void setInscricao1(String inscricao1){

		this.inscricao1 = inscricao1;
	}

	public String getMatricula1(){

		return matricula1;
	}

	public void setMatricula1(String matricula1){

		this.matricula1 = matricula1;
	}

	public String getRoteiro1(){

		return roteiro1;
	}

	public void setRoteiro1(String roteiro1){

		this.roteiro1 = roteiro1;
	}

	public String getHm1(){

		return hm1;
	}

	public void setHm1(String hm1){

		this.hm1 = hm1;
	}

	public String getPrograma1(){

		return programa1;
	}

	public void setPrograma1(String programa1){

		this.programa1 = programa1;
	}

	public String getCliente1(){

		return cliente1;
	}

	public void setCliente1(String cliente1){

		this.cliente1 = cliente1;
	}

	public String getEndereco1(){

		return endereco1;
	}

	public void setEndereco1(String endereco1){

		this.endereco1 = endereco1;
	}

	public String getBairro1(){

		return bairro1;
	}

	public void setBairro1(String bairro1){

		this.bairro1 = bairro1;
	}

	public String getNumero1(){

		return numero1;
	}

	public void setNumero1(String numero1){

		this.numero1 = numero1;
	}

	public String getInscricao2(){

		return inscricao2;
	}

	public void setInscricao2(String inscricao2){

		this.inscricao2 = inscricao2;
	}

	public String getMatricula2(){

		return matricula2;
	}

	public void setMatricula2(String matricula2){

		this.matricula2 = matricula2;
	}

	public String getRoteiro2(){

		return roteiro2;
	}

	public void setRoteiro2(String roteiro2){

		this.roteiro2 = roteiro2;
	}

	public String getHm2(){

		return hm2;
	}

	public void setHm2(String hm2){

		this.hm2 = hm2;
	}

	public String getPrograma2(){

		return programa2;
	}

	public void setPrograma2(String programa2){

		this.programa2 = programa2;
	}

	public String getCliente2(){

		return cliente2;
	}

	public void setCliente2(String cliente2){

		this.cliente2 = cliente2;
	}

	public String getEndereco2(){

		return endereco2;
	}

	public void setEndereco2(String endereco2){

		this.endereco2 = endereco2;
	}

	public String getBairro2(){

		return bairro2;
	}

	public void setBairro2(String bairro2){

		this.bairro2 = bairro2;
	}

	public String getNumero2(){

		return numero2;
	}

	public void setNumero2(String numero2){

		this.numero2 = numero2;
	}

	public JRBeanCollectionDataSource getArrayJRDetail1(){

		return arrayJRDetail1;
	}

	public void setArrayJRDetail1(JRBeanCollectionDataSource arrayJRDetail1){

		this.arrayJRDetail1 = arrayJRDetail1;
	}

	public JRBeanCollectionDataSource getArrayJRDetail2(){

		return arrayJRDetail2;
	}

	public void setArrayJRDetail2(JRBeanCollectionDataSource arrayJRDetail2){

		this.arrayJRDetail2 = arrayJRDetail2;
	}

	public String getDebitosAnteriores1(){

		return debitosAnteriores1;
	}

	public void setDebitosAnteriores1(String debitosAnteriores1){

		this.debitosAnteriores1 = debitosAnteriores1;
	}

	public String getValorTotalDebito1(){

		return valorTotalDebito1;
	}

	public void setValorTotalDebito1(String valorTotalDebito1){

		this.valorTotalDebito1 = valorTotalDebito1;
	}

	public String getDebitosAnteriores2(){

		return debitosAnteriores2;
	}

	public void setDebitosAnteriores2(String debitosAnteriores2){

		this.debitosAnteriores2 = debitosAnteriores2;
	}

	public String getValorTotalDebito2(){

		return valorTotalDebito2;
	}

	public void setValorTotalDebito2(String valorTotalDebito2){

		this.valorTotalDebito2 = valorTotalDebito2;
	}

	public String getRepresentacaoNumericaCodBarraFormatada1(){

		return representacaoNumericaCodBarraFormatada1;
	}

	public ArrayList<Object> getArrayRelatorioAvisoCorteDetail1Bean(){

		return arrayRelatorioAvisoCorteDetail1Bean;
	}

	public void setArrayRelatorioAvisoCorteDetail1Bean(ArrayList<Object> arrayRelatorioAvisoCorteDetail1Bean){

		this.arrayRelatorioAvisoCorteDetail1Bean = arrayRelatorioAvisoCorteDetail1Bean;
	}

	public ArrayList<Object> getArrayRelatorioAvisoCorteDetail2Bean(){

		return arrayRelatorioAvisoCorteDetail2Bean;
	}

	public void setArrayRelatorioAvisoCorteDetail2Bean(ArrayList<Object> arrayRelatorioAvisoCorteDetail2Bean){

		this.arrayRelatorioAvisoCorteDetail2Bean = arrayRelatorioAvisoCorteDetail2Bean;
	}

	public void setRepresentacaoNumericaCodBarraFormatada1(String representacaoNumericaCodBarraFormatada1){

		this.representacaoNumericaCodBarraFormatada1 = representacaoNumericaCodBarraFormatada1;
	}

	public String getRepresentacaoNumericaCodBarraSemDigito1(){

		return representacaoNumericaCodBarraSemDigito1;
	}

	public void setRepresentacaoNumericaCodBarraSemDigito1(String representacaoNumericaCodBarraSemDigito1){

		this.representacaoNumericaCodBarraSemDigito1 = representacaoNumericaCodBarraSemDigito1;
	}

	public String getRepresentacaoNumericaCodBarraFormatada2(){

		return representacaoNumericaCodBarraFormatada2;
	}

	public void setRepresentacaoNumericaCodBarraFormatada2(String representacaoNumericaCodBarraFormatada2){

		this.representacaoNumericaCodBarraFormatada2 = representacaoNumericaCodBarraFormatada2;
	}

	public String getRepresentacaoNumericaCodBarraSemDigito2(){

		return representacaoNumericaCodBarraSemDigito2;
	}

	public void setRepresentacaoNumericaCodBarraSemDigito2(String representacaoNumericaCodBarraSemDigito2){

		this.representacaoNumericaCodBarraSemDigito2 = representacaoNumericaCodBarraSemDigito2;
	}

	public String getDataDebitoAnterior1(){

		return dataDebitoAnterior1;
	}

	public void setDataDebitoAnterior1(String dataDebitoAnterior1){

		this.dataDebitoAnterior1 = dataDebitoAnterior1;
	}

	public String getDataDebitoAnterior2(){

		return dataDebitoAnterior2;
	}

	public void setDataDebitoAnterior2(String dataDebitoAnterior2){

		this.dataDebitoAnterior2 = dataDebitoAnterior2;
	}

	public String getDataPermanenciaRegistro1(){

		return dataPermanenciaRegistro1;
	}

	public void setDataPermanenciaRegistro1(String dataPermanenciaRegistro1){

		this.dataPermanenciaRegistro1 = dataPermanenciaRegistro1;
	}

	public String getDataPermanenciaRegistro2(){

		return dataPermanenciaRegistro2;
	}

	public void setDataPermanenciaRegistro2(String dataPermanenciaRegistro2){

		this.dataPermanenciaRegistro2 = dataPermanenciaRegistro2;
	}

	private Collection<Object> gerarDetail(Collection<String> collMesAno, Collection<String> collVencimento,
					Collection<BigDecimal> collValor){

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

		return colecaoDetail;
	}

	private Collection<String[]> gerarLinhasDetail(Collection<String> collString){

		Collection<String[]> retorno = new ArrayList<String[]>();
		String[] item = new String[RELATORIO_DETAIL_3_COLUNAS];
		int countLine = 0;
		boolean preencheuTodos = false;

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
				preencheuTodos = true;
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

	public void setNumeroPagina1(Integer numeroPagina1){

		this.numeroPagina1 = numeroPagina1;
	}

	public Integer getNumeroPagina1(){

		return numeroPagina1;
	}

	public void setNumeroPagina2(Integer numeroPagina2){

		this.numeroPagina2 = numeroPagina2;
	}

	public Integer getNumeroPagina2(){

		return numeroPagina2;
	}

}
