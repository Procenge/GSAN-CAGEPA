
package gcom.relatorio.cobranca;

import gcom.cobranca.bean.CobrancaDocumentoItemHelper;
import gcom.cobranca.bean.EmitirArquivoPdfAvisoCorteHelper;
import gcom.relatorio.RelatorioBean;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class RelatorioAvisoCorteModelo4Bean
				implements RelatorioBean {

	private static final int RELATORIO_DETAIL_3_COLUNAS = 1;

	// Primeira Aba

	private JRBeanCollectionDataSource arrayJRDetail1;

	private ArrayList<Object> arrayRelatorioOrdemCorteDetail1Bean;

	private String descricaoLocalidade1;

	private String matriculaImovel1;

	private String inscricaoImovel1;

	private int sequencialImpressao1;

	private String nomeClienteUsuario1;

	private String enderecoImovel1;

	private String bairroImovel1;

	private String dataTextoCliente1;

	private ArrayList<Object> colecaoContas1;

	private String dataEmissao1;

	private Integer idOSP1;

	private String numeroHidrometro1;

	private Integer numeroPagina1;

	private String valorDocumento1;

	// Segunda Aba

	private JRBeanCollectionDataSource arrayJRDetail2;

	private ArrayList<Object> arrayRelatorioOrdemCorteDetail2Bean;

	private String descricaoLocalidade2;

	private String matriculaImovel2;

	private String inscricaoImovel2;

	private int sequencialImpressao2;

	private String nomeClienteUsuario2;

	private String enderecoImovel2;

	private String bairroImovel2;

	private String dataTextoCliente2;

	private ArrayList<Object> colecaoContas2;

	private String dataEmissao2;

	private Integer idOSP2;

	private String numeroHidrometro2;

	private Integer numeroPagina2;

	private String valorDocumento2;

	private String representacaoNumericaCodBarra1;

	private String representacaoNumericaCodBarra2;

	private String representacaoNumericaCodBarraSemDigito1;

	private String representacaoNumericaCodBarraSemDigito2;

	public RelatorioAvisoCorteModelo4Bean(EmitirArquivoPdfAvisoCorteHelper helper1, EmitirArquivoPdfAvisoCorteHelper helper2) {

		if(helper1 != null){

			this.descricaoLocalidade1 = helper1.getDescricaoLocalidade();
			this.matriculaImovel1 = helper1.getMatriculaImovel();
			this.inscricaoImovel1 = helper1.getInscricaoImovel();
			this.sequencialImpressao1 = helper1.getSequencialImpressao();
			this.nomeClienteUsuario1 = helper1.getNomeClienteUsuario();
			this.enderecoImovel1 = helper1.getEnderecoFormatado();
			this.bairroImovel1 = helper1.getBairroImovel();
			this.dataTextoCliente1 = helper1.getDataTextoClienteFormatada();
			this.dataEmissao1 = helper1.getDataEmissaoFormatada();
			this.idOSP1 = helper1.getIdOSP();
			this.numeroHidrometro1 = helper1.getNumeroHidrometro();
			this.numeroPagina1 = helper1.getNumeroPagina();
			this.valorDocumento1 = helper1.getValorDocumentoFormatado();
			this.representacaoNumericaCodBarra1 = helper1.getRepresentacaoNumericaCodBarra();
			this.representacaoNumericaCodBarraSemDigito1 = helper1.getRepresentacaoNumericaCodBarraSemDigito();

			this.arrayRelatorioOrdemCorteDetail1Bean = new ArrayList<Object>();
			this.arrayRelatorioOrdemCorteDetail1Bean.addAll(this.gerarDetail(helper1.getColecaoItemConta()));
			this.arrayJRDetail1 = new JRBeanCollectionDataSource(this.arrayRelatorioOrdemCorteDetail1Bean);
		}

		if(helper2 != null){

			this.descricaoLocalidade2 = helper2.getDescricaoLocalidade();
			this.matriculaImovel2 = helper2.getMatriculaImovel();
			this.inscricaoImovel2 = helper2.getInscricaoImovel();
			this.sequencialImpressao2 = helper2.getSequencialImpressao();
			this.nomeClienteUsuario2 = helper2.getNomeClienteUsuario();
			this.enderecoImovel2 = helper2.getEnderecoFormatado();
			this.bairroImovel2 = helper2.getBairroImovel();
			this.dataTextoCliente2 = helper2.getDataTextoClienteFormatada();
			this.dataEmissao2 = helper2.getDataEmissaoFormatada();
			this.idOSP2 = helper2.getIdOSP();
			this.numeroHidrometro2 = helper2.getNumeroHidrometro();
			this.numeroPagina2 = helper2.getNumeroPagina();
			this.valorDocumento2 = helper2.getValorDocumentoFormatado();
			this.representacaoNumericaCodBarra2 = helper2.getRepresentacaoNumericaCodBarra();
			this.representacaoNumericaCodBarraSemDigito2 = helper2.getRepresentacaoNumericaCodBarraSemDigito();

			this.arrayRelatorioOrdemCorteDetail2Bean = new ArrayList<Object>();
			this.arrayRelatorioOrdemCorteDetail2Bean.addAll(this.gerarDetail(helper2.getColecaoItemConta()));
			this.arrayJRDetail2 = new JRBeanCollectionDataSource(this.arrayRelatorioOrdemCorteDetail2Bean);

		}

	}

	private Collection<Object> gerarDetail(Collection<CobrancaDocumentoItemHelper> itemConta){

		Collection<Object> colecaoDetail = new ArrayList<Object>();

		Collection<String> collStringFatura = new ArrayList<String>();
		Collection<String> collStringValor = new ArrayList<String>();
		Collection<String> collStringDataVencimento = new ArrayList<String>();

		Iterator<CobrancaDocumentoItemHelper> iter = itemConta.iterator();

		for(int i = 1; i <= 13; i++){

			if(iter.hasNext()){
				CobrancaDocumentoItemHelper helper = iter.next();
				collStringFatura.add(helper.getAnoMesReferenciaContaFormatada());
				collStringValor.add(Util.formataBigDecimal(helper.getValorItemCobrado(), 2, true));
				collStringDataVencimento.add(helper.getDataVencimentoFormatada());
			}else{
				// Add Branco para ter um total de 13.
				collStringFatura.add(" ");
				collStringValor.add(" ");
				collStringDataVencimento.add(" ");
			}
		}

		Object[] linhasFatura = this.gerarLinhasDetail(collStringFatura).toArray();
		Object[] linhasValorItem = this.gerarLinhasDetail(collStringValor).toArray();
		Object[] linhasDataVencimento = this.gerarLinhasDetail(collStringDataVencimento).toArray();

		for(int i = 0; i < linhasFatura.length; i++){

			String[] linhaFatura = (String[]) linhasFatura[i];
			String[] linhaValor = (String[]) linhasValorItem[i];
			String[] linhaDataVencimento = (String[]) linhasDataVencimento[i];

			Object relatorio = new SubRelatorioAvisoCorteModelo5(linhaFatura, linhaDataVencimento, linhaValor);
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
				// item[1] = null;
				// item[2] = null;
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
			/*
			 * if(item[1] == null){
			 * item[1] = "";
			 * }
			 * if(item[2] == null){
			 * item[2] = "";
			 * }
			 */
			retorno.add(item);
		}

		return retorno;
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
	 * @return the descricaoLocalidade1
	 */
	public String getDescricaoLocalidade1(){

		return descricaoLocalidade1;
	}

	/**
	 * @param descricaoLocalidade1
	 *            the descricaoLocalidade1 to set
	 */
	public void setDescricaoLocalidade1(String descricaoLocalidade1){

		this.descricaoLocalidade1 = descricaoLocalidade1;
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
	 * @return the nomeClienteUsuario1
	 */
	public String getNomeClienteUsuario1(){

		return nomeClienteUsuario1;
	}

	/**
	 * @param nomeClienteUsuario1
	 *            the nomeClienteUsuario1 to set
	 */
	public void setNomeClienteUsuario1(String nomeClienteUsuario1){

		this.nomeClienteUsuario1 = nomeClienteUsuario1;
	}

	/**
	 * @return the enderecoImovel1
	 */
	public String getEnderecoImovel1(){

		return enderecoImovel1;
	}

	/**
	 * @param enderecoImovel1
	 *            the enderecoImovel1 to set
	 */
	public void setEnderecoImovel1(String enderecoImovel1){

		this.enderecoImovel1 = enderecoImovel1;
	}

	/**
	 * @return the bairroImovel1
	 */
	public String getBairroImovel1(){

		return bairroImovel1;
	}

	/**
	 * @param bairroImovel1
	 *            the bairroImovel1 to set
	 */
	public void setBairroImovel1(String bairroImovel1){

		this.bairroImovel1 = bairroImovel1;
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

	/**
	 * @return the idOSP1
	 */
	public Integer getIdOSP1(){

		return idOSP1;
	}

	/**
	 * @param idOSP1
	 *            the idOSP1 to set
	 */
	public void setIdOSP1(Integer idOSP1){

		this.idOSP1 = idOSP1;
	}

	/**
	 * @return the numeroHidrometro1
	 */
	public String getNumeroHidrometro1(){

		return numeroHidrometro1;
	}

	/**
	 * @param numeroHidrometro1
	 *            the numeroHidrometro1 to set
	 */
	public void setNumeroHidrometro1(String numeroHidrometro1){

		this.numeroHidrometro1 = numeroHidrometro1;
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
	 * @return the descricaoLocalidade2
	 */
	public String getDescricaoLocalidade2(){

		return descricaoLocalidade2;
	}

	/**
	 * @param descricaoLocalidade2
	 *            the descricaoLocalidade2 to set
	 */
	public void setDescricaoLocalidade2(String descricaoLocalidade2){

		this.descricaoLocalidade2 = descricaoLocalidade2;
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
	 * @return the nomeClienteUsuario2
	 */
	public String getNomeClienteUsuario2(){

		return nomeClienteUsuario2;
	}

	/**
	 * @param nomeClienteUsuario2
	 *            the nomeClienteUsuario2 to set
	 */
	public void setNomeClienteUsuario2(String nomeClienteUsuario2){

		this.nomeClienteUsuario2 = nomeClienteUsuario2;
	}

	/**
	 * @return the enderecoImovel2
	 */
	public String getEnderecoImovel2(){

		return enderecoImovel2;
	}

	/**
	 * @param enderecoImovel2
	 *            the enderecoImovel2 to set
	 */
	public void setEnderecoImovel2(String enderecoImovel2){

		this.enderecoImovel2 = enderecoImovel2;
	}

	/**
	 * @return the bairroImovel2
	 */
	public String getBairroImovel2(){

		return bairroImovel2;
	}

	/**
	 * @param bairroImovel2
	 *            the bairroImovel2 to set
	 */
	public void setBairroImovel2(String bairroImovel2){

		this.bairroImovel2 = bairroImovel2;
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

	/**
	 * @return the idOSP2
	 */
	public Integer getIdOSP2(){

		return idOSP2;
	}

	/**
	 * @param idOSP2
	 *            the idOSP2 to set
	 */
	public void setIdOSP2(Integer idOSP2){

		this.idOSP2 = idOSP2;
	}

	/**
	 * @return the numeroHidrometro2
	 */
	public String getNumeroHidrometro2(){

		return numeroHidrometro2;
	}

	/**
	 * @param numeroHidrometro2
	 *            the numeroHidrometro2 to set
	 */
	public void setNumeroHidrometro2(String numeroHidrometro2){

		this.numeroHidrometro2 = numeroHidrometro2;
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

	/**
	 * @return the arrayRelatorioOrdemCorteDetail1Bean
	 */
	public ArrayList<Object> getArrayRelatorioOrdemCorteDetail1Bean(){

		return arrayRelatorioOrdemCorteDetail1Bean;
	}

	/**
	 * @param arrayRelatorioOrdemCorteDetail1Bean
	 *            the arrayRelatorioOrdemCorteDetail1Bean to set
	 */
	public void setArrayRelatorioOrdemCorteDetail1Bean(ArrayList<Object> arrayRelatorioOrdemCorteDetail1Bean){

		this.arrayRelatorioOrdemCorteDetail1Bean = arrayRelatorioOrdemCorteDetail1Bean;
	}

	/**
	 * @return the arrayRelatorioOrdemCorteDetail2Bean
	 */
	public ArrayList<Object> getArrayRelatorioOrdemCorteDetail2Bean(){

		return arrayRelatorioOrdemCorteDetail2Bean;
	}

	/**
	 * @param arrayRelatorioOrdemCorteDetail2Bean
	 *            the arrayRelatorioOrdemCorteDetail2Bean to set
	 */
	public void setArrayRelatorioOrdemCorteDetail2Bean(ArrayList<Object> arrayRelatorioOrdemCorteDetail2Bean){

		this.arrayRelatorioOrdemCorteDetail2Bean = arrayRelatorioOrdemCorteDetail2Bean;
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


}
