
package gcom.cobranca;

import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;
import gcom.util.tabelaauxiliar.abreviada.TabelaAuxiliarAbreviada;

public class AcaoCobrancaEfeito
				extends TabelaAuxiliarAbreviada {

	public static final Integer NA0_SE_APLICA = Integer.valueOf(1);

	public static final Integer CORTE = Integer.valueOf(2);

	public static final Integer SUPRESSAO = Integer.valueOf(3);

	public static final Integer NEGATIVACAO_SPC_SP = Integer.valueOf(4);

	public static final Integer NEGATIVACAO_SERASA = Integer.valueOf(5);

	public static final Integer NEGATIVACAO_SPC_BR = Integer.valueOf(6);

	private static final long serialVersionUID = 1L;

	/*
	 * private Integer id;
	 * private String descricao;
	 * private String descricaoAbreviada;
	 * private Date ultimaAlteracao;
	 * public Integer getId(){
	 * return id;
	 * }
	 * public void setId(Integer id){
	 * this.id = id;
	 * }
	 * public String getDescricao(){
	 * return descricao;
	 * }
	 * public void setDescricao(String descricao){
	 * this.descricao = descricao;
	 * }
	 * public String getDescricaoAbreviada(){
	 * return descricaoAbreviada;
	 * }
	 * public void setDescricaoAbreviada(String descricaoAbreviada){
	 * this.descricaoAbreviada = descricaoAbreviada;
	 * }
	 * public Date getUltimaAlteracao(){
	 * return ultimaAlteracao;
	 * }
	 * public void setUltimaAlteracao(Date ultimaAlteracao){
	 * this.ultimaAlteracao = ultimaAlteracao;
	 * }
	 * public Short getIndicadorUso(){
	 * return indicadorUso;
	 * }
	 * public void setIndicadorUso(Short indicadorUso){
	 * this.indicadorUso = indicadorUso;
	 * }
	 * public Short indicadorUso;
	 */
	@Override
	public Filtro retornaFiltro(){

		FiltroAcaoCobrancaEfeito filtro = new FiltroAcaoCobrancaEfeito();
		filtro.adicionarParametro(new ParametroSimples(FiltroAcaoCobrancaEfeito.ID, this.getId()));

		return filtro;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		String retorno = null;

		if(this.getId() != null && this.getDescricao() != null){
			retorno = this.getId() + " - " + this.getDescricao();
		}else{
			retorno = null;
		}

		return retorno;
	}

}
