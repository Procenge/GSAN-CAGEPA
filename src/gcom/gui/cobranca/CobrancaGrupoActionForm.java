
package gcom.gui.cobranca;

import gcom.cobranca.CobrancaGrupo;
import gcom.fachada.Fachada;

import org.apache.struts.validator.ValidatorActionForm;

public class CobrancaGrupoActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String id;

	private String descricao;

	private String descAbreviada;

	private String indicadorUso;

	private String anoMesReferencia;

	private String ultimaAlteracao;

	private String tipoPesquisa;

	private String atualizar;

	private String[] idRegistrosRemocao;

	public String[] getIdRegistrosRemocao(){

		return idRegistrosRemocao;
	}

	public void setIdRegistrosRemocao(String[] idRegistrosRemocao){

		this.idRegistrosRemocao = idRegistrosRemocao;
	}

	public String getAtualizar(){

		return atualizar;
	}

	public void setAtualizar(String atualizar){

		this.atualizar = atualizar;
	}

	public String getTipoPesquisa(){

		return tipoPesquisa;
	}

	public void setTipoPesquisa(String tipoPesquisa){

		this.tipoPesquisa = tipoPesquisa;
	}

	public String getId(){

		return id;
	}

	public void setId(String id){

		this.id = id;
	}

	public String getDescricao(){

		return descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	public String getDescAbreviada(){

		return descAbreviada;
	}

	public void setDescAbreviada(String descAbreviada){

		this.descAbreviada = descAbreviada;
	}

	public String getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public String getAnoMesReferencia(){

		return anoMesReferencia;
	}

	public void setAnoMesReferencia(String anoMesReferencia){

		this.anoMesReferencia = anoMesReferencia;
	}

	public String getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(String ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public CobrancaGrupo setFormValues(CobrancaGrupo cobrancaGrupo){

		Fachada fachada = Fachada.getInstancia();

		cobrancaGrupo.setId(new Integer(getId()));
		cobrancaGrupo.setDescricao(descricao);
		cobrancaGrupo.setDescricaoAbreviada(descAbreviada);
		cobrancaGrupo.setAnoMesReferencia(Integer.parseInt(anoMesReferencia));
		cobrancaGrupo.setIndicadorUso(Short.parseShort(indicadorUso));

		return cobrancaGrupo;
	}

}
