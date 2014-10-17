
package gcom.cobranca;

import gcom.interceptor.ControleAlteracao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;
import gcom.util.tabelaauxiliar.abreviada.TabelaAuxiliarAbreviada;

@ControleAlteracao()
public class BoletoBancarioMotivoCancelamento
				extends TabelaAuxiliarAbreviada {

	public static final int OPERACAO_BOLETO_BANCARIO_CANCELAR = 302466;

	private static final long serialVersionUID = 1L;

	@ControleAlteracao(funcionalidade = {OPERACAO_BOLETO_BANCARIO_CANCELAR})
	/*
	 * private Integer id;
	 * private String descricao;
	 * private String descricaoAbreviada;
	 * private Short indicadorUso;
	 * private Date ultimaAlteracao;
	 */
	// Contantes
	public final static Integer CANCELADO_REJEICAO_BANCO = new Integer("1");

	/**
	 * @param id
	 * @param descricao
	 * @param descricaoAbreviada
	 * @param indicadorUso
	 * @param ultimaAlteracao
	 */
	/*
	 * public BoletoBancarioMotivoCancelamento(Integer id, String descricao, String
	 * descricaoAbreviada, Short indicadorUso,
	 * Date ultimaAlteracao) {
	 * this.id = id;
	 * this.descricao = descricao;
	 * this.descricaoAbreviada = descricaoAbreviada;
	 * this.indicadorUso = indicadorUso;
	 * this.ultimaAlteracao = ultimaAlteracao;
	 * }
	 */
	/** default constructor */
	public BoletoBancarioMotivoCancelamento() {

	}

	/**
	 * @return the id
	 */
	/*
	 * public Integer getId(){
	 * return id;
	 * }
	 *//**
	 * @param id
	 *            the id to set
	 */
	/*
	 * public void setId(Integer id){
	 * this.id = id;
	 * }
	 *//**
	 * @return the descricao
	 */
	/*
	 * public String getDescricao(){
	 * return descricao;
	 * }
	 *//**
	 * @param descricao
	 *            the descricao to set
	 */
	/*
	 * public void setDescricao(String descricao){
	 * this.descricao = descricao;
	 * }
	 *//**
	 * @return the descricaoAbreviada
	 */
	/*
	 * public String getDescricaoAbreviada(){
	 * return descricaoAbreviada;
	 * }
	 *//**
	 * @param descricaoAbreviada
	 *            the descricaoAbreviada to set
	 */
	/*
	 * public void setDescricaoAbreviada(String descricaoAbreviada){
	 * this.descricaoAbreviada = descricaoAbreviada;
	 * }
	 *//**
	 * @return the indicadorUso
	 */
	/*
	 * public Short getIndicadorUso(){
	 * return indicadorUso;
	 * }
	 *//**
	 * @param indicadorUso
	 *            the indicadorUso to set
	 */
	/*
	 * public void setIndicadorUso(Short indicadorUso){
	 * this.indicadorUso = indicadorUso;
	 * }
	 *//**
	 * @return the ultimaAlteracao
	 */
	/*
	 * public Date getUltimaAlteracao(){
	 * return ultimaAlteracao;
	 * }
	 *//**
	 * @param ultimaAlteracao
	 *            the ultimaAlteracao to set
	 */
	/*
	 * public void setUltimaAlteracao(Date ultimaAlteracao){
	 * this.ultimaAlteracao = ultimaAlteracao;
	 * }
	 */
	public String getDescricaoComId(){

		String descricaoComId = null;

		Integer id = this.getId();
		String descricao = this.getDescricao();

		if(id != null && id.compareTo(10) == -1){
			descricaoComId = "0" + id + " - " + descricao;
		}else if(id != null){
			descricaoComId = id + " - " + descricao;
		}else{
			descricaoComId = " - " + descricao;
		}

		return descricaoComId;
	}

	public Filtro retornaFiltro(){

		FiltroBoletoBancarioMotivoCancelamento filtroMotivo = new FiltroBoletoBancarioMotivoCancelamento();
		filtroMotivo.adicionarParametro(new ParametroSimples(FiltroBoletoBancarioMotivoCancelamento.ID, this.getId()));

		return filtroMotivo;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

}
