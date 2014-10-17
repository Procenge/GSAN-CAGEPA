
package gcom.relatorio.cobranca.prescricao;

import gcom.relatorio.RelatorioBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class RelatorioDebitosPrescritosBean
				implements RelatorioBean {

	private String idLocalidade;

	private String nomeLocalidade;

	private String matriculaFormatada;

	private Integer idImovel;

	private String inscricao;

	private String idLigacaoAguaSituacao;

	private String idLigacaoEsgotoSituacao;

	private String categoria;

	private String enderecoImovel;

	private BigDecimal valorTotalContas;

	private Integer quantidadeContas;

	private BigDecimal valorTotalGuias;

	private Integer quantidadeGuias;

	private JRBeanCollectionDataSource beansContas;

	private ArrayList arraySubRelatorioContasDetailBean;

	private JRBeanCollectionDataSource beansGuias;

	private ArrayList arraySubRelatorioGuiaPagamentoDetailBean;

	public String getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(String idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public String getNomeLocalidade(){

		return nomeLocalidade;
	}

	public void setNomeLocalidade(String nomeLocalidade){

		this.nomeLocalidade = nomeLocalidade;
	}

	public String getMatriculaFormatada(){

		return matriculaFormatada;
	}

	public void setMatriculaFormatada(String matriculaFormatada){

		this.matriculaFormatada = matriculaFormatada;
	}

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public String getInscricao(){

		return inscricao;
	}

	public void setInscricao(String inscricao){

		this.inscricao = inscricao;
	}

	public String getIdLigacaoAguaSituacao(){

		return idLigacaoAguaSituacao;
	}

	public void setIdLigacaoAguaSituacao(String idLigacaoAguaSituacao){

		this.idLigacaoAguaSituacao = idLigacaoAguaSituacao;
	}

	public String getIdLigacaoEsgotoSituacao(){

		return idLigacaoEsgotoSituacao;
	}

	public void setIdLigacaoEsgotoSituacao(String idLigacaoEsgotoSituacao){

		this.idLigacaoEsgotoSituacao = idLigacaoEsgotoSituacao;
	}

	public String getCategoria(){

		return categoria;
	}

	public void setCategoria(String categoria){

		this.categoria = categoria;
	}

	public String getEnderecoImovel(){

		return enderecoImovel;
	}

	public void setEnderecoImovel(String enderecoImovel){

		this.enderecoImovel = enderecoImovel;
	}

	public BigDecimal getValorTotalContas(){

		return valorTotalContas;
	}

	public void setValorTotalContas(BigDecimal valorTotalContas){

		this.valorTotalContas = valorTotalContas;
	}

	public Integer getQuantidadeContas(){

		return quantidadeContas;
	}

	public void setQuantidadeContas(Integer quantidadeContas){

		this.quantidadeContas = quantidadeContas;
	}

	public BigDecimal getValorTotalGuias(){

		return valorTotalGuias;
	}

	public void setValorTotalGuias(BigDecimal valorTotalGuias){

		this.valorTotalGuias = valorTotalGuias;
	}

	public Integer getQuantidadeGuias(){

		return quantidadeGuias;
	}

	public void setQuantidadeGuias(Integer quantidadeGuias){

		this.quantidadeGuias = quantidadeGuias;
	}

	public JRBeanCollectionDataSource getBeansContas(){

		return beansContas;
	}

	public void setBeansContas(JRBeanCollectionDataSource beansContas){

		this.beansContas = beansContas;
	}

	public ArrayList getArraySubRelatorioContasDetailBean(){

		return arraySubRelatorioContasDetailBean;
	}

	public void setArraySubRelatorioContasDetailBean(ArrayList arraySubRelatorioContasDetailBean){

		this.arraySubRelatorioContasDetailBean = arraySubRelatorioContasDetailBean;
	}

	public JRBeanCollectionDataSource getBeansGuias(){

		return beansGuias;
	}

	public void setBeansGuias(JRBeanCollectionDataSource beansGuias){

		this.beansGuias = beansGuias;
	}

	public ArrayList getArraySubRelatorioGuiaPagamentoDetailBean(){

		return arraySubRelatorioGuiaPagamentoDetailBean;
	}

	public void setArraySubRelatorioGuiaPagamentoDetailBean(ArrayList arraySubRelatorioGuiaPagamentoDetailBean){

		this.arraySubRelatorioGuiaPagamentoDetailBean = arraySubRelatorioGuiaPagamentoDetailBean;
	}

	public void setarBeansContas(Collection colecaoDetail){

		this.arraySubRelatorioContasDetailBean = new ArrayList();
		this.arraySubRelatorioContasDetailBean.addAll(colecaoDetail);
		this.beansContas = new JRBeanCollectionDataSource(this.arraySubRelatorioContasDetailBean);
	}

	public void setarBeansGuias(Collection colecaoDetail){

		this.arraySubRelatorioGuiaPagamentoDetailBean = new ArrayList();
		this.arraySubRelatorioGuiaPagamentoDetailBean.addAll(colecaoDetail);
		this.beansGuias = new JRBeanCollectionDataSource(this.arraySubRelatorioGuiaPagamentoDetailBean);
	}
}