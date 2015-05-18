/*
 * Copyright (C) 2007-2007 the GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 *
 * This file is part of GSAN, an integrated service management system for Sanitation
 *
 * GSAN is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License.
 *
 * GSAN is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place � Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa � software livre; voc� pode redistribu�-lo e/ou
 * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers�o 2 da
 * Licen�a.
 * Este programa � distribu�do na expectativa de ser �til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
 * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
 * PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais
 * detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
 * junto com este programa; se n�o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.faturamento;

import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
@ControleAlteracao()
public class FaturamentoGrupo
				extends ObjetoTransacao {

	/** identifier field */
	private static final long serialVersionUID = 1L;

	public static final int OPERACAO_ENCERRAR_FATURAMENTO = 323708;

	private Integer id;

	/** persistent field */
	private String descricao;

	/** persistent field */
	private String descricaoAbreviada;

	/** nullable persistent field */
	private Short indicadorUso;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = OPERACAO_ENCERRAR_FATURAMENTO)
	private Integer anoMesReferencia;

	/** nullable persistent field */
	private Short diaVencimento;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/** nullable persistent field */
	private Short indicadorVencimentoMesFatura;

	private Short diaVencimentoDebitoAutomatico;

	private Short diaVencimentoEntregaAlternativa;

	/** full constructor */
	public FaturamentoGrupo(String descricao, String descricaoAbreviada, Short indicadorUso, Integer anoMesReferencia, Short diaVencimento,
							Date ultimaAlteracao, Short diaVencimentoDebitoAutomatico, Short diaVencimentoEntregaAlternativa ) {

		this.descricao = descricao;
		this.descricaoAbreviada = descricaoAbreviada;
		this.indicadorUso = indicadorUso;
		this.anoMesReferencia = anoMesReferencia;
		this.diaVencimento = diaVencimento;
		this.ultimaAlteracao = ultimaAlteracao;
		this.diaVencimentoDebitoAutomatico = diaVencimentoDebitoAutomatico;
		this.diaVencimentoEntregaAlternativa = diaVencimentoEntregaAlternativa;
		
	}

	/** default constructor */
	public FaturamentoGrupo() {

	}

	/** minimal constructor */
	public FaturamentoGrupo(String descricao, String descricaoAbreviada) {

		this.descricao = descricao;
		this.descricaoAbreviada = descricaoAbreviada;
	}

	public FaturamentoGrupo(Integer id) {

		this.id = id;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getDescricao(){

		return this.descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	public String getDescricaoAbreviada(){

		return this.descricaoAbreviada;
	}

	public void setDescricaoAbreviada(String descricaoAbreviada){

		this.descricaoAbreviada = descricaoAbreviada;
	}

	public Short getIndicadorUso(){

		return this.indicadorUso;
	}

	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public Integer getAnoMesReferencia(){

		return this.anoMesReferencia;
	}

	public void setAnoMesReferencia(Integer anoMesReferencia){

		this.anoMesReferencia = anoMesReferencia;
	}

	public Short getDiaVencimento(){

		return this.diaVencimento;
	}

	public void setDiaVencimento(Short diaVencimento){

		this.diaVencimento = diaVencimento;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	/**
	 * Retorna o valor de mesAno
	 * 
	 * @return O valor de mesAno
	 */
	public String getMesAno(){

		// o metodo serve para transformar o AnoMesReferencia do banco
		// em mes/Ano para demonstra�ao para o usuario.
		// Ex.: 200508 para 08/2005
		String mesAno = null;

		String mes = null;
		String ano = null;

		if(this.anoMesReferencia != null && !this.anoMesReferencia.toString().trim().equalsIgnoreCase("")){
			String anoMes = this.anoMesReferencia.toString();

			mes = anoMes.substring(4, 6);
			ano = anoMes.substring(0, 4);
			mesAno = mes + "/" + ano;
		}
		return mesAno;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Short getIndicadorVencimentoMesFatura(){

		return indicadorVencimentoMesFatura;
	}

	public void setIndicadorVencimentoMesFatura(Short indicadorVencimentoMesFatura){

		this.indicadorVencimentoMesFatura = indicadorVencimentoMesFatura;
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"id", "anoMesReferencia"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Id", "Referencia"};
		return labels;
	}

	public Filtro retornaFiltro(){

		FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();
		filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.ID, this.getId()));

		return filtroFaturamentoGrupo;
	}

	public String getDescricaoParaRegistroTransacao(){

		return getId() + "";
	}

	@Override
	public boolean equals(Object obj){

		if(this == obj) return true;
		if(obj == null) return false;
		if(getClass() != obj.getClass()) return false;
		FaturamentoGrupo other = (FaturamentoGrupo) obj;
		if(id == null){
			if(other.id != null) return false;
		}else if(!id.equals(other.id)) return false;
		return true;
	}

	public Short getDiaVencimentoDebitoAutomatico(){

		return diaVencimentoDebitoAutomatico;
	}

	public void setDiaVencimentoDebitoAutomatico(Short diaVencimentoDebitoAutomatico){

		this.diaVencimentoDebitoAutomatico = diaVencimentoDebitoAutomatico;
	}

	public Short getDiaVencimentoEntregaAlternativa(){

		return diaVencimentoEntregaAlternativa;
	}

	public void setDiaVencimentoEntregaAlternativa(Short diaVencimentoEntregaAlternativa){

		this.diaVencimentoEntregaAlternativa = diaVencimentoEntregaAlternativa;
	}

}
