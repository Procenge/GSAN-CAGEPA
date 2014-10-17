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

package gcom.operacional;

import gcom.cadastro.localidade.GerenciaRegional;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
@ControleAlteracao()
public class SistemaEsgoto
				extends ObjetoTransacao {

	public static final int ATRIBUTOS_SISTEMA_ESGOTO_INSERIR = 824;

	public static final int ATRIBUTOS_SISTEMA_ESGOTO_ATUALIZAR = 841;

	public static final int ATRIBUTOS_SISTEMA_ESGOTO_REMOVER = 835;

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_SISTEMA_ESGOTO_INSERIR, ATRIBUTOS_SISTEMA_ESGOTO_ATUALIZAR, ATRIBUTOS_SISTEMA_ESGOTO_REMOVER})
	private Integer codigo;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_SISTEMA_ESGOTO_INSERIR, ATRIBUTOS_SISTEMA_ESGOTO_ATUALIZAR, ATRIBUTOS_SISTEMA_ESGOTO_REMOVER})
	private String descricao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_SISTEMA_ESGOTO_INSERIR, ATRIBUTOS_SISTEMA_ESGOTO_ATUALIZAR, ATRIBUTOS_SISTEMA_ESGOTO_REMOVER})
	private String descricaoAbreviada;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_SISTEMA_ESGOTO_INSERIR, ATRIBUTOS_SISTEMA_ESGOTO_ATUALIZAR, ATRIBUTOS_SISTEMA_ESGOTO_REMOVER})
	private Short indicadorUso;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	@ControleAlteracao(value = FiltroSistemaEsgoto.TIPOTRATAMENTO, funcionalidade = {ATRIBUTOS_SISTEMA_ESGOTO_INSERIR, ATRIBUTOS_SISTEMA_ESGOTO_ATUALIZAR, ATRIBUTOS_SISTEMA_ESGOTO_REMOVER})
	private gcom.operacional.SistemaEsgotoTratamentoTipo sistemaEsgotoTratamentoTipo;

	@ControleAlteracao(value = FiltroSistemaEsgoto.DIVISAOESGOTO, funcionalidade = {ATRIBUTOS_SISTEMA_ESGOTO_INSERIR, ATRIBUTOS_SISTEMA_ESGOTO_ATUALIZAR, ATRIBUTOS_SISTEMA_ESGOTO_REMOVER})
	private gcom.operacional.DivisaoEsgoto divisaoEsgoto;

	@ControleAlteracao(value = FiltroSistemaEsgoto.GERENCIA_REGIONAL, funcionalidade = {ATRIBUTOS_SISTEMA_ESGOTO_INSERIR, ATRIBUTOS_SISTEMA_ESGOTO_ATUALIZAR, ATRIBUTOS_SISTEMA_ESGOTO_REMOVER})
	private GerenciaRegional gerenciaRegional;

	private String descricaoComId;

	/** full constructor */
	public SistemaEsgoto(String descricao, String descricaoAbreviada, Short indicadorUso, Date ultimaAlteracao,
							gcom.operacional.SistemaEsgotoTratamentoTipo sistemaEsgotoTratamentoTipo,
							gcom.operacional.DivisaoEsgoto divisaoEsgoto) {

		this.descricao = descricao;
		this.descricaoAbreviada = descricaoAbreviada;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.sistemaEsgotoTratamentoTipo = sistemaEsgotoTratamentoTipo;
		this.divisaoEsgoto = divisaoEsgoto;
	}

	/** default constructor */
	public SistemaEsgoto() {

	}

	/** minimal constructor */
	public SistemaEsgoto(String descricao, String descricaoAbreviada,
							gcom.operacional.SistemaEsgotoTratamentoTipo sistemaEsgotoTratamentoTipo,
							gcom.operacional.DivisaoEsgoto divisaoEsgoto) {

		this.descricao = descricao;
		this.descricaoAbreviada = descricaoAbreviada;
		this.sistemaEsgotoTratamentoTipo = sistemaEsgotoTratamentoTipo;
		this.divisaoEsgoto = divisaoEsgoto;
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

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public gcom.operacional.SistemaEsgotoTratamentoTipo getSistemaEsgotoTratamentoTipo(){

		return this.sistemaEsgotoTratamentoTipo;
	}

	public void setSistemaEsgotoTratamentoTipo(gcom.operacional.SistemaEsgotoTratamentoTipo sistemaEsgotoTratamentoTipo){

		this.sistemaEsgotoTratamentoTipo = sistemaEsgotoTratamentoTipo;
	}

	public gcom.operacional.DivisaoEsgoto getDivisaoEsgoto(){

		return this.divisaoEsgoto;
	}

	public void setDivisaoEsgoto(gcom.operacional.DivisaoEsgoto divisaoEsgoto){

		this.divisaoEsgoto = divisaoEsgoto;
	}

	public GerenciaRegional getGerenciaRegional(){

		return gerenciaRegional;
	}

	public void setGerenciaRegional(GerenciaRegional gerenciaRegional){

		this.gerenciaRegional = gerenciaRegional;
	}

	public String getDescricaoComId(){

		if(Integer.valueOf(this.getId()).compareTo(10) == -1){
			descricaoComId = "000" + getId() + " - " + getDescricao();
		}else if(Integer.valueOf(this.getId()).compareTo(100) == -1){
			descricaoComId = "00" + getId() + " - " + getDescricao();
		}else if(Integer.valueOf(this.getId()).compareTo(1000) == -1){
			descricaoComId = "0" + getId() + " - " + getDescricao();
		}else{
			descricaoComId = getId() + " - " + getDescricao();
		}

		return descricaoComId;
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"id", "descricao", "sistemaEsgotoTratamentoTipo.id", "divisaoEsgoto.id"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"ID", "Descricao", "Tipo Trat", "Div Esgo"};
		return labels;
	}

	public Filtro retornaFiltro(){

		FiltroSistemaEsgoto filtroSistemaEsgoto = new FiltroSistemaEsgoto();

		filtroSistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSistemaEsgoto.ID, this.getId()));
		filtroSistemaEsgoto.adicionarCaminhoParaCarregamentoEntidade(FiltroSistemaEsgoto.DIVISAOESGOTO);
		filtroSistemaEsgoto.adicionarCaminhoParaCarregamentoEntidade(FiltroSistemaEsgoto.TIPOTRATAMENTO);

		return filtroSistemaEsgoto;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getId() + "";
	}

	public void setCodigo(Integer codigo){

		this.codigo = codigo;
	}

	public Integer getCodigo(){

		return codigo;
	}

}
