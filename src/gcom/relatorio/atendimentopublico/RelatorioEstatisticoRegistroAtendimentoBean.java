
package gcom.relatorio.atendimentopublico;

import gcom.relatorio.RelatorioBean;

import java.math.BigDecimal;

public class RelatorioEstatisticoRegistroAtendimentoBean
				implements RelatorioBean {

	private Boolean ehUnidadeSuperior;

	private Integer idUnidadeSuperior;

	private String descricaoUnidadeSuperior;

	private Integer idUnidadeAtendimento;

	private String descricaoUnidadeAtendimento;

	private Integer idTipoSolicitacao;

	private String descricaoTipoSolicitacao;

	private Integer idEspecificacao;

	private String descricaoEspecificacao;

	private Integer quantidade;

	private BigDecimal percentual;

	public RelatorioEstatisticoRegistroAtendimentoBean(Integer idUnidadeSuperior, String descricaoUnidadeSuperior,
														Integer idUnidadeAtendimento, String descricaoUnidadeAtendimento,
														Integer idTipoSolicitacao, String descricaoTipoSolicitacao,
														Integer idEspecificacao, String descricaoEspecificacao, Integer quantidade,
														BigDecimal percentual) {

		super();
		this.idUnidadeSuperior = idUnidadeSuperior;
		this.descricaoUnidadeSuperior = descricaoUnidadeSuperior;
		this.idUnidadeAtendimento = idUnidadeAtendimento;
		this.descricaoUnidadeAtendimento = descricaoUnidadeAtendimento;
		this.idTipoSolicitacao = idTipoSolicitacao;
		this.descricaoTipoSolicitacao = descricaoTipoSolicitacao;
		this.idEspecificacao = idEspecificacao;
		this.descricaoEspecificacao = descricaoEspecificacao;
		this.quantidade = quantidade;
		this.percentual = percentual;
		ehUnidadeSuperior = false;
	}

	public Integer getIdUnidadeAtendimento(){

		return idUnidadeAtendimento;
	}

	public void setIdUnidadeAtendimento(Integer idUnidadeAtendimento){

		this.idUnidadeAtendimento = idUnidadeAtendimento;
	}

	public String getDescricaoUnidadeAtendimento(){

		return descricaoUnidadeAtendimento;
	}

	public void setDescricaoUnidadeAtendimento(String descricaoUnidadeAtendimento){

		this.descricaoUnidadeAtendimento = descricaoUnidadeAtendimento;
	}

	public Integer getIdEspecificacao(){

		return idEspecificacao;
	}

	public void setIdEspecificacao(Integer idEspecificacao){

		this.idEspecificacao = idEspecificacao;
	}

	public Integer getQuantidade(){

		return quantidade;
	}

	public void setQuantidade(Integer quantidade){

		this.quantidade = quantidade;
	}

	public Integer getIdUnidadeSuperior(){

		return idUnidadeSuperior;
	}

	public void setIdUnidadeSuperior(Integer idUnidadeSuperior){

		this.idUnidadeSuperior = idUnidadeSuperior;
	}

	public String getDescricaoUnidadeSuperior(){

		return descricaoUnidadeSuperior;
	}

	public void setDescricaoUnidadeSuperior(String descricaoUnidadeSuperior){

		this.descricaoUnidadeSuperior = descricaoUnidadeSuperior;
	}

	public Boolean getEhUnidadeSuperior(){

		return ehUnidadeSuperior;
	}

	public void setEhUnidadeSuperior(Boolean ehUnidadeSuperior){

		this.ehUnidadeSuperior = ehUnidadeSuperior;
	}

	public String getDescricaoEspecificacao(){

		return descricaoEspecificacao;
	}

	public void setDescricaoEspecificacao(String descricaoEspecificacao){

		this.descricaoEspecificacao = descricaoEspecificacao;
	}

	public BigDecimal getPercentual(){

		return percentual;
	}

	public void setPercentual(BigDecimal percentual){

		this.percentual = percentual;
	}

	public Integer getIdTipoSolicitacao(){

		return idTipoSolicitacao;
	}

	public void setIdTipoSolicitacao(Integer idTipoSolicitacao){

		this.idTipoSolicitacao = idTipoSolicitacao;
	}

	public String getDescricaoTipoSolicitacao(){

		return descricaoTipoSolicitacao;
	}

	public void setDescricaoTipoSolicitacao(String descricaoTipoSolicitacao){

		this.descricaoTipoSolicitacao = descricaoTipoSolicitacao;
	}

}
