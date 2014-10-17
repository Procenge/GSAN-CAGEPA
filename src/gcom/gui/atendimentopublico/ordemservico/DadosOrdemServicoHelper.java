
package gcom.gui.atendimentopublico.ordemservico;

import java.io.Serializable;

public class DadosOrdemServicoHelper
				implements Serializable {

	private Integer idOrdemServico;

	private Integer idServicoTipo;

	private String descricaoServicoTipo;

	private Integer idImovel;

	private Integer idLocalidade;

	private String descricaoLocalidade;

	private Integer codigoSetor;

	private String descricaoSetor;

	private Integer numeroQuadra;

	private String descricaoAbreviada;

	private String dataGeracao;

	private Integer idUnidadeGeracao;

	private String descricaoUnidadeGeracao;

	private String dataEncerramento;

	private Integer idUnidadeEncerramento;

	private String descricaoUnidadeEncerramento;

	private Integer idMotivoEncerramento;

	private String motivoEncerramento;

	public Integer getIdOrdemServico(){

		return idOrdemServico;
	}

	public void setIdOrdemServico(Integer idOrdemServico){

		this.idOrdemServico = idOrdemServico;
	}

	public Integer getIdServicoTipo(){

		return idServicoTipo;
	}

	public void setIdServicoTipo(Integer idServicoTipo){

		this.idServicoTipo = idServicoTipo;
	}

	public String getDescricaoServicoTipo(){

		return descricaoServicoTipo;
	}

	public void setDescricaoServicoTipo(String descricaoServicoTipo){

		this.descricaoServicoTipo = descricaoServicoTipo;
	}

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public Integer getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(Integer idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public String getDescricaoLocalidade(){

		return descricaoLocalidade;
	}

	public void setDescricaoLocalidade(String descricaoLocalidade){

		this.descricaoLocalidade = descricaoLocalidade;
	}

	public Integer getCodigoSetor(){

		return codigoSetor;
	}

	public void setCodigoSetor(Integer codigoSetor){

		this.codigoSetor = codigoSetor;
	}

	public String getDescricaoSetor(){

		return descricaoSetor;
	}

	public void setDescricaoSetor(String descricaoSetor){

		this.descricaoSetor = descricaoSetor;
	}

	public Integer getNumeroQuadra(){

		return numeroQuadra;
	}

	public void setNumeroQuadra(Integer numeroQuadra){

		this.numeroQuadra = numeroQuadra;
	}

	public String getDescricaoAbreviada(){

		return descricaoAbreviada;
	}

	public void setDescricaoAbreviada(String descricaoAbreviada){

		this.descricaoAbreviada = descricaoAbreviada;
	}

	public String getDataGeracao(){

		return dataGeracao;
	}

	public void setDataGeracao(String dataGeracao){

		this.dataGeracao = dataGeracao;
	}

	public Integer getIdUnidadeGeracao(){

		return idUnidadeGeracao;
	}

	public void setIdUnidadeGeracao(Integer idUnidadeGeracao){

		this.idUnidadeGeracao = idUnidadeGeracao;
	}

	public String getDescricaoUnidadeGeracao(){

		return descricaoUnidadeGeracao;
	}

	public void setDescricaoUnidadeGeracao(String descricaoUnidadeGeracao){

		this.descricaoUnidadeGeracao = descricaoUnidadeGeracao;
	}

	public String getDataEncerramento(){

		return dataEncerramento;
	}

	public void setDataEncerramento(String dataEncerramento){

		this.dataEncerramento = dataEncerramento;
	}

	public Integer getIdUnidadeEncerramento(){

		return idUnidadeEncerramento;
	}

	public void setIdUnidadeEncerramento(Integer idUnidadeEncerramento){

		this.idUnidadeEncerramento = idUnidadeEncerramento;
	}

	public String getDescricaoUnidadeEncerramento(){

		return descricaoUnidadeEncerramento;
	}

	public void setDescricaoUnidadeEncerramento(String descricaoUnidadeEncerramento){

		this.descricaoUnidadeEncerramento = descricaoUnidadeEncerramento;
	}

	public Integer getIdMotivoEncerramento(){

		return idMotivoEncerramento;
	}

	public void setIdMotivoEncerramento(Integer idMotivoEncerramento){

		this.idMotivoEncerramento = idMotivoEncerramento;
	}

	public String getMotivoEncerramento(){

		return motivoEncerramento;
	}

	public void setMotivoEncerramento(String motivoEncerramento){

		this.motivoEncerramento = motivoEncerramento;
	}

}
