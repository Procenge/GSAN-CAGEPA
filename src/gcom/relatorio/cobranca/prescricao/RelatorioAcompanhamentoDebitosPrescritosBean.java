
package gcom.relatorio.cobranca.prescricao;

import gcom.relatorio.RelatorioBean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class RelatorioAcompanhamentoDebitosPrescritosBean
				implements RelatorioBean {

	private String matriculaFormatada;

	private Integer idImovel;

	private String inscricao;

	private String descricaoLigacaoAguaSituacao;

	private String descricaoLigacaoEsgotoSituacao;

	private String descricaoImovelPerfil;

	private JRBeanCollectionDataSource beansCategorias;

	private ArrayList arraySubRelatorioCategoriasDetailBean;

	private String enderecoImovel;

	private BigDecimal valorTotalContasMarcadas;

	private Integer quantidadeContasMarcadas;

	private BigDecimal valorTotalGuiasPrestacaoMarcadas;

	private Integer quantidadeGuiasPrestacaoMarcadas;

	private BigDecimal valorTotalContasDesmarcadas;

	private Integer quantidadeContasDesmarcadas;

	private BigDecimal valorTotalGuiasPrestacaoDesmarcadas;

	private Integer quantidadeGuiasPrestacaoDesmarcadas;

	private JRBeanCollectionDataSource beansContas;

	private ArrayList arraySubRelatorioContasDetailBean;

	private JRBeanCollectionDataSource beansGuias;

	private ArrayList arraySubRelatorioGuiaPagamentoDetailBean;

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

	public String getEnderecoImovel(){

		return enderecoImovel;
	}

	public void setEnderecoImovel(String enderecoImovel){

		this.enderecoImovel = enderecoImovel;
	}

	public String getDescricaoLigacaoAguaSituacao(){

		return descricaoLigacaoAguaSituacao;
	}

	public void setDescricaoLigacaoAguaSituacao(String descricaoLigacaoAguaSituacao){

		this.descricaoLigacaoAguaSituacao = descricaoLigacaoAguaSituacao;
	}

	public String getDescricaoLigacaoEsgotoSituacao(){

		return descricaoLigacaoEsgotoSituacao;
	}

	public void setDescricaoLigacaoEsgotoSituacao(String descricaoLigacaoEsgotoSituacao){

		this.descricaoLigacaoEsgotoSituacao = descricaoLigacaoEsgotoSituacao;
	}

	public String getDescricaoImovelPerfil(){

		return descricaoImovelPerfil;
	}

	public void setDescricaoImovelPerfil(String descricaoImovelPerfil){

		this.descricaoImovelPerfil = descricaoImovelPerfil;
	}

	public JRBeanCollectionDataSource getBeansCategorias(){

		return beansCategorias;
	}

	public void setBeansCategorias(JRBeanCollectionDataSource beansCategorias){

		this.beansCategorias = beansCategorias;
	}

	public ArrayList getArraySubRelatorioCategoriasDetailBean(){

		return arraySubRelatorioCategoriasDetailBean;
	}

	public void setArraySubRelatorioCategoriasDetailBean(ArrayList arraySubRelatorioCategoriasDetailBean){

		this.arraySubRelatorioCategoriasDetailBean = arraySubRelatorioCategoriasDetailBean;
	}

	public BigDecimal getValorTotalContasMarcadas(){

		return valorTotalContasMarcadas;
	}

	public void setValorTotalContasMarcadas(BigDecimal valorTotalContasMarcadas){

		this.valorTotalContasMarcadas = valorTotalContasMarcadas;
	}

	public Integer getQuantidadeContasMarcadas(){

		return quantidadeContasMarcadas;
	}

	public void setQuantidadeContasMarcadas(Integer quantidadeContasMarcadas){

		this.quantidadeContasMarcadas = quantidadeContasMarcadas;
	}

	public BigDecimal getValorTotalGuiasPrestacaoMarcadas(){

		return valorTotalGuiasPrestacaoMarcadas;
	}

	public void setValorTotalGuiasPrestacaoMarcadas(BigDecimal valorTotalGuiasPrestacaoMarcadas){

		this.valorTotalGuiasPrestacaoMarcadas = valorTotalGuiasPrestacaoMarcadas;
	}

	public Integer getQuantidadeGuiasPrestacaoMarcadas(){

		return quantidadeGuiasPrestacaoMarcadas;
	}

	public void setQuantidadeGuiasPrestacaoMarcadas(Integer quantidadeGuiasPrestacaoMarcadas){

		this.quantidadeGuiasPrestacaoMarcadas = quantidadeGuiasPrestacaoMarcadas;
	}

	public BigDecimal getValorTotalContasDesmarcadas(){

		return valorTotalContasDesmarcadas;
	}

	public void setValorTotalContasDesmarcadas(BigDecimal valorTotalContasDesmarcadas){

		this.valorTotalContasDesmarcadas = valorTotalContasDesmarcadas;
	}

	public Integer getQuantidadeContasDesmarcadas(){

		return quantidadeContasDesmarcadas;
	}

	public void setQuantidadeContasDesmarcadas(Integer quantidadeContasDesmarcadas){

		this.quantidadeContasDesmarcadas = quantidadeContasDesmarcadas;
	}

	public BigDecimal getValorTotalGuiasPrestacaoDesmarcadas(){

		return valorTotalGuiasPrestacaoDesmarcadas;
	}

	public void setValorTotalGuiasPrestacaoDesmarcadas(BigDecimal valorTotalGuiasPrestacaoDesmarcadas){

		this.valorTotalGuiasPrestacaoDesmarcadas = valorTotalGuiasPrestacaoDesmarcadas;
	}

	public Integer getQuantidadeGuiasPrestacaoDesmarcadas(){

		return quantidadeGuiasPrestacaoDesmarcadas;
	}

	public void setQuantidadeGuiasPrestacaoDesmarcadas(Integer quantidadeGuiasPrestacaoDesmarcadas){

		this.quantidadeGuiasPrestacaoDesmarcadas = quantidadeGuiasPrestacaoDesmarcadas;
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

	public void setarBeansCategorias(Collection colecaoDetail){

		this.arraySubRelatorioCategoriasDetailBean = new ArrayList();
		this.arraySubRelatorioCategoriasDetailBean.addAll(colecaoDetail);
		this.beansCategorias = new JRBeanCollectionDataSource(this.arraySubRelatorioCategoriasDetailBean);
	}
}