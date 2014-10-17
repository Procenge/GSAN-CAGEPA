
package gcom.atendimentopublico.registroatendimento;

import gcom.util.filtro.Filtro;
import gcom.util.tabelaauxiliar.abreviada.TabelaAuxiliarAbreviada;

import java.io.Serializable;
import java.util.Date;

public class AtendimentoIncompletoMotivo
				extends TabelaAuxiliarAbreviada
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * private Integer id;
	 * private String descricaoMotivo;
	 * private String descricaoAbreviadaMotivo;
	 * private Short indicadorUso;
	 * private Date ultimaAlteracao;
	 * public Integer getId(){
	 * return id;
	 * }
	 * public void setId(Integer id){
	 * this.id = id;
	 * }
	 * public String getDescricaoMotivo(){
	 * return descricaoMotivo;
	 * }
	 * public void setDescricaoMotivo(String descricaoMotivo){
	 * this.descricaoMotivo = descricaoMotivo;
	 * }
	 * public String getDescricaoAbreviadaMotivo(){
	 * return descricaoAbreviadaMotivo;
	 * }
	 * public void setDescricaoAbreviadaMotivo(String descricaoAbreviadaMotivo){
	 * this.descricaoAbreviadaMotivo = descricaoAbreviadaMotivo;
	 * }
	 * public Short getIndicadorUso(){
	 * return indicadorUso;
	 * }
	 * public void setIndicadorUso(Short indicadorUso){
	 * this.indicadorUso = indicadorUso;
	 * }
	 * public Date getUltimaAlteracao(){
	 * return this.ultimaAlteracao;
	 * }
	 */
	public Filtro retornaFiltro(){

		return null;
	}

	/*
	 * public void setUltimaAlteracao(Date ultimaAlteracao){
	 * this.ultimaAlteracao = ultimaAlteracao;
	 * }
	 */
	public String[] retornaCamposChavePrimaria(){

		return null;
	}

	@Override
	public Date getUltimaAlteracao(){

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUltimaAlteracao(Date ultimaAlteracao){

		// TODO Auto-generated method stub

	}

}
