
package gcom.atendimentopublico.registroatendimento;

import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;
import gcom.util.tabelaauxiliar.abreviada.TabelaAuxiliarAbreviada;

import org.apache.commons.lang.builder.ToStringBuilder;

public class SolicitacaoTipoEspecificacaoMensagem
				extends TabelaAuxiliarAbreviada {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	/*
	 * private Integer id;
	 * private String descricao;
	 * private String descricaoAbreviada;
	 * private Short indicadorUso;
	 * private Date ultimaAlteracao;
	 */

	private String descricaoComId;

	/** full constructor */
	/*
	 * public SolicitacaoTipoEspecificacaoMensagem(String descricao, String descricaoAbreviada,
	 * Short indicadorUso, Date ultimaAlteracao) {
	 * this.descricao = descricao;
	 * this.descricaoAbreviada = descricaoAbreviada;
	 * this.indicadorUso = indicadorUso;
	 * this.ultimaAlteracao = ultimaAlteracao;
	 * }
	 *//** default constructor */
	/*
	 * public SolicitacaoTipoEspecificacaoMensagem() {
	 * }
	 *//** minimal constructor */
	/*
	 * public SolicitacaoTipoEspecificacaoMensagem(String descricao, String descricaoAbreviada) {
	 * this.descricao = descricao;
	 * this.descricaoAbreviada = descricaoAbreviada;
	 * }
	 * public Integer getId(){
	 * return this.id;
	 * }
	 * public void setId(Integer id){
	 * this.id = id;
	 * }
	 * public String getDescricao(){
	 * return this.descricao;
	 * }
	 * public void setDescricao(String descricao){
	 * this.descricao = descricao;
	 * }
	 * public String getDescricaoAbreviada(){
	 * return this.descricaoAbreviada;
	 * }
	 * public void setDescricaoAbreviada(String descricaoAbreviada){
	 * this.descricaoAbreviada = descricaoAbreviada;
	 * }
	 * public Short getIndicadorUso(){
	 * return this.indicadorUso;
	 * }
	 * public void setIndicadorUso(Short indicadorUso){
	 * this.indicadorUso = indicadorUso;
	 * }
	 * public Date getUltimaAlteracao(){
	 * return this.ultimaAlteracao;
	 * }
	 * public void setUltimaAlteracao(Date ultimaAlteracao){
	 * this.ultimaAlteracao = ultimaAlteracao;
	 * }
	 */

	public String getDescricaoComId(){

		if(Integer.valueOf(this.getId()).compareTo(10) == -1){
			descricaoComId = "0" + getId() + " - " + getDescricaoAbreviada();
		}else{
			descricaoComId = getId() + " - " + getDescricaoAbreviada();
		}

		return descricaoComId;
	}

	public Filtro retornaFiltro(){

		FiltroSolicitacaoTipoEspecificacaoMensagem filtroSolicitacaoTipoEspecificacaoMensagem = new FiltroSolicitacaoTipoEspecificacaoMensagem();

		filtroSolicitacaoTipoEspecificacaoMensagem.adicionarParametro(new ParametroSimples(FiltroSolicitacaoTipoEspecificacaoMensagem.ID,
						this.getId()));

		return filtroSolicitacaoTipoEspecificacaoMensagem;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

}
