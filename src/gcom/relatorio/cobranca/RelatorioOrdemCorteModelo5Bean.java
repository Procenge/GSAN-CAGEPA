
package gcom.relatorio.cobranca;

import gcom.cobranca.bean.CobrancaDocumentoItemHelper;
import gcom.cobranca.bean.EmitirDocumentoOrdemCorteModelo4e5Helper;
import gcom.relatorio.RelatorioBean;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class RelatorioOrdemCorteModelo5Bean
				implements RelatorioBean {

	private static final int RELATORIO_DETAIL_1_COLUNAS = 1;

	// Primeira Aba

	private JRBeanCollectionDataSource arrayJRDetail1;

	private ArrayList<Object> arrayRelatorioOrdemCorteDetail1Bean;

	private String matricula1;

	private String dataApresentacaoCorte1;

	private String dataEmissao1;

	private String sequencial1;

	private String dataEntrega1;

	private String nomeUsuario1;

	private String inscricao1;

	private String enderecoImovel1;

	private String numeroOsp1;

	private String numeroHidrometro1;

	private String localizacaoHidrometro1;

	private String diametroHidrometro1;

	private Integer numeroPagina1;

	private String valorDocumento1;

	// Segunda Aba

	private JRBeanCollectionDataSource arrayJRDetail2;

	private ArrayList<Object> arrayRelatorioOrdemCorteDetail2Bean;

	private String matricula2;

	private String dataEmissao2;

	private String dataApresentacaoCorte2;

	private String sequencial2;

	private String dataEntrega2;

	private String nomeUsuario2;

	private String inscricao2;

	private String enderecoImovel2;

	private String numeroOsp2;

	private String numeroHidrometro2;

	private String localizacaoHidrometro2;

	private String diametroHidrometro2;

	private Integer numeroPagina2;

	private String valorDocumento2;

	private String servicoTipo1;

	private String servicoTipo2;

	public RelatorioOrdemCorteModelo5Bean(EmitirDocumentoOrdemCorteModelo4e5Helper helper1, EmitirDocumentoOrdemCorteModelo4e5Helper helper2) {

		if(helper1 != null){
			this.matricula1 = Util.completarStringComValorEsquerda(
							Util.retornaMatriculaImovelFormatadaParametrizada(helper1.getIdImovel()), "0", 9);
			this.dataEmissao1 = Util.formatarData(helper1.getDataEmissao());
			this.dataEntrega1 = Util.formatarData(helper1.getDataEntrega());
			this.sequencial1 = String.valueOf(helper1.getSequencialImpressao());
			this.nomeUsuario1 = helper1.getNomeClienteUsuario();
			this.inscricao1 = helper1.getInscricao();
			this.enderecoImovel1 = helper1.getEnderecoFormatado();
			this.numeroOsp1 = helper1.getIdOrdemServicoFormatado();
			this.numeroHidrometro1 = helper1.getNumeroHidrometroFormatado();
			this.localizacaoHidrometro1 = helper1.getLocalHistalacaoHidrometroFormatado();
			this.diametroHidrometro1 = helper1.getDiametroHidrometroFormatado();
			this.valorDocumento1 = Util.formataBigDecimal(helper1.getValorDocumento(), 2, true);
			this.valorDocumento1 = Util.completarStringComValorEsquerda(valorDocumento1, "*", 10);
			this.numeroPagina1 = helper1.getNumeroPagina();
			this.dataApresentacaoCorte1 = helper1.getDataApresentacaoCorteFormatado();
			this.servicoTipo1 = helper1.getIdTipoServico();

			this.arrayRelatorioOrdemCorteDetail1Bean = new ArrayList<Object>();
			this.arrayRelatorioOrdemCorteDetail1Bean.addAll(this.gerarDetail(helper1.getColecaoItemConta()));
			this.arrayJRDetail1 = new JRBeanCollectionDataSource(this.arrayRelatorioOrdemCorteDetail1Bean);

		}

		if(helper2 != null){
			this.matricula2 = Util.completarStringComValorEsquerda(
							Util.retornaMatriculaImovelFormatadaParametrizada(helper2.getIdImovel()), "0", 9);
			this.dataEmissao2 = Util.formatarData(helper2.getDataEmissao());
			this.dataEntrega2 = Util.formatarData(helper2.getDataEntrega());
			this.sequencial2 = String.valueOf(helper2.getSequencialImpressao());
			this.nomeUsuario2 = helper2.getNomeClienteUsuario();
			this.inscricao2 = helper2.getInscricao();
			this.enderecoImovel2 = helper2.getEnderecoFormatado();
			this.numeroOsp2 = helper2.getIdOrdemServicoFormatado();
			this.numeroHidrometro2 = helper2.getNumeroHidrometroFormatado();
			this.localizacaoHidrometro2 = helper2.getLocalHistalacaoHidrometroFormatado();
			this.diametroHidrometro2 = helper2.getDiametroHidrometroFormatado();
			this.valorDocumento2 = Util.formataBigDecimal(helper2.getValorDocumento(), 2, true);
			this.valorDocumento2 = Util.completarStringComValorEsquerda(valorDocumento2, "*", 10);
			this.numeroPagina2 = helper2.getNumeroPagina();
			this.dataApresentacaoCorte2 = helper2.getDataApresentacaoCorteFormatado();
			this.servicoTipo2 = helper2.getIdTipoServico();

			this.arrayRelatorioOrdemCorteDetail2Bean = new ArrayList<Object>();
			this.arrayRelatorioOrdemCorteDetail2Bean.addAll(this.gerarDetail(helper2.getColecaoItemConta()));
			this.arrayJRDetail2 = new JRBeanCollectionDataSource(this.arrayRelatorioOrdemCorteDetail2Bean);

		}
	}

	private List<Object> gerarDetail(Collection<CobrancaDocumentoItemHelper> itemConta){

		List<Object> colecaoDetail = new ArrayList<Object>();

		List<String> collStringConta = new ArrayList<String>();
		List<String> collStringValor = new ArrayList<String>();
		List<String> collStringSituacao = new ArrayList<String>();

		Iterator<CobrancaDocumentoItemHelper> iter = itemConta.iterator();

		for(int i = 1; i <= 13; i++){

			if(iter.hasNext()){
				CobrancaDocumentoItemHelper helper = iter.next();
				collStringConta.add(helper.getAnoMesReferenciaConta());
				BigDecimal valorItemCobrado = helper.getValorItemCobrado();
				if(valorItemCobrado == null){
					valorItemCobrado = BigDecimal.ZERO;
				}
				BigDecimal valorAcrescimos = helper.getValorAcrescimos();
				if(valorAcrescimos == null){
					valorAcrescimos = BigDecimal.ZERO;
				}

				BigDecimal valorTotal = valorItemCobrado.add(valorAcrescimos);

				collStringValor.add(Util.formataBigDecimal(valorTotal, 2, true));

				collStringSituacao.add(helper.getSituacao());
			}else{
				// Add Branco para ter um total de 13.
				collStringConta.add(" ");
				collStringValor.add(" ");
				collStringSituacao.add(" ");
			}
		}

		Object[] linhasMesAno = this.gerarLinhasDetail(collStringConta).toArray();
		Object[] linhasValorItem = this.gerarLinhasDetail(collStringValor).toArray();
		Object[] linhasSituacao = this.gerarLinhasDetail(collStringSituacao).toArray();

		for(int i = 0; i < linhasMesAno.length; i++){

			String[] linhaMesAno = (String[]) linhasMesAno[i];
			String[] linhaVencimento = (String[]) linhasValorItem[i];
			String[] linhaValor = (String[]) linhasSituacao[i];

			Object relatorio = new SubRelatorioOrdemCorteModelo5(linhaMesAno, linhaVencimento, linhaValor);
			colecaoDetail.add(relatorio);

		}

		return colecaoDetail;
	}

	private Collection<String[]> gerarLinhasDetail(Collection<String> collString){

		Collection<String[]> retorno = new ArrayList<String[]>();
		String[] item = new String[RELATORIO_DETAIL_1_COLUNAS];
		int countLine = 0;

		for(String valor : collString){
			if((countLine + 1) <= RELATORIO_DETAIL_1_COLUNAS){
				item[countLine] = valor;
			}

			if((countLine + 1) == RELATORIO_DETAIL_1_COLUNAS){
				String[] linhaValor = new String[RELATORIO_DETAIL_1_COLUNAS];

				for(int i = 1; i <= RELATORIO_DETAIL_1_COLUNAS; i++){
					linhaValor[i - 1] = item[i - 1];
				}

				retorno.add(linhaValor);
				item[0] = null;
				countLine = 0;
			}else{
				countLine++;
			}
		}

		if(countLine != 0){

			if(item[0] == null){
				item[0] = "";
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

	public String getMatricula1(){

		return matricula1;
	}

	public void setMatricula1(String matricula1){

		this.matricula1 = matricula1;
	}

	public String getDataEmissao1(){

		return dataEmissao1;
	}

	public void setDataEmissao1(String dataEmissao1){

		this.dataEmissao1 = dataEmissao1;
	}

	public String getSequencial1(){

		return sequencial1;
	}

	public void setSequencial1(String sequencial1){

		this.sequencial1 = sequencial1;
	}

	public String getDataEntrega1(){

		return dataEntrega1;
	}

	public void setDataEntrega1(String dataEntrega1){

		this.dataEntrega1 = dataEntrega1;
	}

	public String getNomeUsuario1(){

		return nomeUsuario1;
	}

	public void setNomeUsuario1(String nomeUsuario1){

		this.nomeUsuario1 = nomeUsuario1;
	}

	public String getInscricao1(){

		return inscricao1;
	}

	public void setInscricao1(String inscricao1){

		this.inscricao1 = inscricao1;
	}

	public String getEnderecoImovel1(){

		return enderecoImovel1;
	}

	public void setEnderecoImovel1(String enderecoImovel1){

		this.enderecoImovel1 = enderecoImovel1;
	}

	public String getNumeroOsp1(){

		return numeroOsp1;
	}

	public void setNumeroOsp1(String numeroOsp1){

		this.numeroOsp1 = numeroOsp1;
	}

	public String getNumeroHidrometro1(){

		return numeroHidrometro1;
	}

	public void setNumeroHidrometro1(String numeroHidrometro1){

		this.numeroHidrometro1 = numeroHidrometro1;
	}

	public String getLocalizacaoHidrometro1(){

		return localizacaoHidrometro1;
	}

	public void setLocalizacaoHidrometro1(String localizacaoHidrometro1){

		this.localizacaoHidrometro1 = localizacaoHidrometro1;
	}

	public String getDiametroHidrometro1(){

		return diametroHidrometro1;
	}

	public void setDiametroHidrometro1(String diametroHidrometro1){

		this.diametroHidrometro1 = diametroHidrometro1;
	}

	public Integer getNumeroPagina1(){

		return numeroPagina1;
	}

	public void setNumeroPagina1(Integer numeroPagina1){

		this.numeroPagina1 = numeroPagina1;
	}

	public JRBeanCollectionDataSource getArrayJRDetail2(){

		return arrayJRDetail2;
	}

	public void setArrayJRDetail2(JRBeanCollectionDataSource arrayJRDetail2){

		this.arrayJRDetail2 = arrayJRDetail2;
	}

	public String getMatricula2(){

		return matricula2;
	}

	public void setMatricula2(String matricula2){

		this.matricula2 = matricula2;
	}

	public String getDataEmissao2(){

		return dataEmissao2;
	}

	public void setDataEmissao2(String dataEmissao2){

		this.dataEmissao2 = dataEmissao2;
	}

	public String getSequencial2(){

		return sequencial2;
	}

	public void setSequencial2(String sequencial2){

		this.sequencial2 = sequencial2;
	}

	public String getDataEntrega2(){

		return dataEntrega2;
	}

	public void setDataEntrega2(String dataEntrega2){

		this.dataEntrega2 = dataEntrega2;
	}

	public String getNomeUsuario2(){

		return nomeUsuario2;
	}

	public void setNomeUsuario2(String nomeUsuario2){

		this.nomeUsuario2 = nomeUsuario2;
	}

	public String getInscricao2(){

		return inscricao2;
	}

	public void setInscricao2(String inscricao2){

		this.inscricao2 = inscricao2;
	}

	public String getEnderecoImovel2(){

		return enderecoImovel2;
	}

	public void setEnderecoImovel2(String enderecoImovel2){

		this.enderecoImovel2 = enderecoImovel2;
	}

	public String getNumeroOsp2(){

		return numeroOsp2;
	}

	public void setNumeroOsp2(String numeroOsp2){

		this.numeroOsp2 = numeroOsp2;
	}

	public String getNumeroHidrometro2(){

		return numeroHidrometro2;
	}

	public void setNumeroHidrometro2(String numeroHidrometro2){

		this.numeroHidrometro2 = numeroHidrometro2;
	}

	public String getLocalizacaoHidrometro2(){

		return localizacaoHidrometro2;
	}

	public void setLocalizacaoHidrometro2(String localizacaoHidrometro2){

		this.localizacaoHidrometro2 = localizacaoHidrometro2;
	}

	public String getDiametroHidrometro2(){

		return diametroHidrometro2;
	}

	public void setDiametroHidrometro2(String diametroHidrometro2){

		this.diametroHidrometro2 = diametroHidrometro2;
	}

	public Integer getNumeroPagina2(){

		return numeroPagina2;
	}

	public void setNumeroPagina2(Integer numeroPagina2){

		this.numeroPagina2 = numeroPagina2;
	}

	public String getValorDocumento1(){

		return valorDocumento1;
	}

	public void setValorDocumento1(String valorDocumento1){

		this.valorDocumento1 = valorDocumento1;
	}

	public String getValorDocumento2(){

		return valorDocumento2;
	}

	public void setValorDocumento2(String valorDocumento2){

		this.valorDocumento2 = valorDocumento2;
	}

	public String getDataApresentacaoCorte1(){

		return dataApresentacaoCorte1;
	}

	public void setDataApresentacaoCorte1(String dataApresentacaoCorte1){

		this.dataApresentacaoCorte1 = dataApresentacaoCorte1;
	}

	public String getDataApresentacaoCorte2(){

		return dataApresentacaoCorte2;
	}

	public void setDataApresentacaoCorte2(String dataApresentacaoCorte2){

		this.dataApresentacaoCorte2 = dataApresentacaoCorte2;
	}

	public String getServicoTipo1(){

		return servicoTipo1;
	}

	public void setServicoTipo1(String servicoTipo1){

		this.servicoTipo1 = servicoTipo1;
	}

	public String getServicoTipo2(){

		return servicoTipo2;
	}

	public void setServicoTipo2(String servicoTipo2){

		this.servicoTipo2 = servicoTipo2;
	}


}
