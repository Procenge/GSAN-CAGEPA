
package gcom.relatorio.atendimentopublico;

import java.util.ArrayList;
import java.util.Collection;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import gcom.relatorio.RelatorioBean;

/**
 * [UC0XXX] Relatório Resumo Registro Atendimento
 * 
 * @author Anderson Italo
 * @date 16/03/2011
 */
public class RelatorioResumoRegistroAtendimentoBean
				implements RelatorioBean {

	// dados do detalhe
	private String idTipoSolicitacao;

	private String descricaoTipoSolicitacao;

	private String idEspecificacao;

	private String descricaoEspecificacao;

	private String idUnidadeAtendimento;

	private String descricaoUnidadeAtendimento;

	private String quantidadePorTipoSolicitacao;

	private String quantidadePorEspecificacao;

	private String quantidadePorUnidade;

	private String percentualPorTipoSolicitacao;

	private String percentualPorEspecificacao;

	private String imprimirTipoSolicitacao;

	private String imprimirUnidadeAtendimento;

	private String imprimirTotalizadores;

	private JRBeanCollectionDataSource beansTotalizadores;

	private ArrayList arrayRelatorioResumoRegistroAtendimentoDetailBean;

	public String getIdTipoSolicitacao(){

		return idTipoSolicitacao;
	}

	public void setIdTipoSolicitacao(String idTipoSolicitacao){

		this.idTipoSolicitacao = idTipoSolicitacao;
	}

	public String getDescricaoTipoSolicitacao(){

		return descricaoTipoSolicitacao;
	}

	public void setDescricaoTipoSolicitacao(String descricaoTipoSolicitacao){

		this.descricaoTipoSolicitacao = descricaoTipoSolicitacao;
	}

	public String getIdEspecificacao(){

		return idEspecificacao;
	}

	public void setIdEspecificacao(String idEspecificacao){

		this.idEspecificacao = idEspecificacao;
	}

	public String getDescricaoEspecificacao(){

		return descricaoEspecificacao;
	}

	public void setDescricaoEspecificacao(String descricaoEspecificacao){

		this.descricaoEspecificacao = descricaoEspecificacao;
	}

	public String getIdUnidadeAtendimento(){

		return idUnidadeAtendimento;
	}

	public void setIdUnidadeAtendimento(String idUnidadeAtendimento){

		this.idUnidadeAtendimento = idUnidadeAtendimento;
	}

	public String getDescricaoUnidadeAtendimento(){

		return descricaoUnidadeAtendimento;
	}

	public void setDescricaoUnidadeAtendimento(String descricaoUnidadeAtendimento){

		this.descricaoUnidadeAtendimento = descricaoUnidadeAtendimento;
	}

	public String getQuantidadePorTipoSolicitacao(){

		return quantidadePorTipoSolicitacao;
	}

	public void setQuantidadePorTipoSolicitacao(String quantidadePorTipoSolicitacao){

		this.quantidadePorTipoSolicitacao = quantidadePorTipoSolicitacao;
	}

	public String getQuantidadePorEspecificacao(){

		return quantidadePorEspecificacao;
	}

	public void setQuantidadePorEspecificacao(String quantidadePorEspecificacao){

		this.quantidadePorEspecificacao = quantidadePorEspecificacao;
	}

	public String getQuantidadePorUnidade(){

		return quantidadePorUnidade;
	}

	public void setQuantidadePorUnidade(String quantidadePorUnidade){

		this.quantidadePorUnidade = quantidadePorUnidade;
	}

	public String getPercentualPorTipoSolicitacao(){

		return percentualPorTipoSolicitacao;
	}

	public void setPercentualPorTipoSolicitacao(String percentualPorTipoSolicitacao){

		this.percentualPorTipoSolicitacao = percentualPorTipoSolicitacao;
	}

	public String getPercentualPorEspecificacao(){

		return percentualPorEspecificacao;
	}

	public void setPercentualPorEspecificacao(String percentualPorEspecificacao){

		this.percentualPorEspecificacao = percentualPorEspecificacao;
	}

	public String getImprimirTipoSolicitacao(){

		return imprimirTipoSolicitacao;
	}

	public void setImprimirTipoSolicitacao(String imprimirTipoSolicitacao){

		this.imprimirTipoSolicitacao = imprimirTipoSolicitacao;
	}

	public String getImprimirUnidadeAtendimento(){

		return imprimirUnidadeAtendimento;
	}

	public void setImprimirUnidadeAtendimento(String imprimirUnidadeAtendimento){

		this.imprimirUnidadeAtendimento = imprimirUnidadeAtendimento;
	}

	public JRBeanCollectionDataSource getBeansTotalizadores(){

		return beansTotalizadores;
	}

	public void setBeansTotalizadores(JRBeanCollectionDataSource beansTotalizadores){

		this.beansTotalizadores = beansTotalizadores;
	}

	public ArrayList getArrayRelatorioResumoRegistroAtendimentoDetailBean(){

		return arrayRelatorioResumoRegistroAtendimentoDetailBean;
	}

	public void setArrayRelatorioResumoRegistroAtendimentoDetailBean(ArrayList arrayRelatorioResumoRegistroAtendimentoDetailBean){

		this.arrayRelatorioResumoRegistroAtendimentoDetailBean = arrayRelatorioResumoRegistroAtendimentoDetailBean;
	}

	public void setarBeansTotalizadores(Collection colecaoDetail){

		this.arrayRelatorioResumoRegistroAtendimentoDetailBean = new ArrayList();
		this.arrayRelatorioResumoRegistroAtendimentoDetailBean.addAll(colecaoDetail);
		this.beansTotalizadores = new JRBeanCollectionDataSource(this.arrayRelatorioResumoRegistroAtendimentoDetailBean);
	}

	public String getImprimirTotalizadores(){

		return imprimirTotalizadores;
	}

	public void setImprimirTotalizadores(String imprimirTotalizadores){

		this.imprimirTotalizadores = imprimirTotalizadores;
	}
}
