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

import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

@ControleAlteracao()
/** @author Hibernate CodeGenerator */
public class SetorAbastecimento
				extends ObjetoTransacao {

	/** INSERIR SETOR ABASTECIMENTO */
	public static final int OPERACAO_SETOR_ABASTECIMENTO_INSERIR = 1513;

	/** ATUALIZAR SETOR ABASTECIMENTO */
	public static final int OPERACAO_SETOR_ABASTECIMENTO_ATUALIZAR = 1515;

	/** REMOVER SETOR ABASTECIMENTO */
	public static final int OPERACAO_SETOR_ABASTECIMENTO_REMOVER = 25845;

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** identifier field */
	private Integer codigoSetorAbastecimento;

	@ControleAlteracao(funcionalidade = {OPERACAO_SETOR_ABASTECIMENTO_INSERIR, OPERACAO_SETOR_ABASTECIMENTO_ATUALIZAR, OPERACAO_SETOR_ABASTECIMENTO_REMOVER})
	/** nullable persistent field */
	private String descricao;

	@ControleAlteracao(funcionalidade = {OPERACAO_SETOR_ABASTECIMENTO_INSERIR, OPERACAO_SETOR_ABASTECIMENTO_ATUALIZAR, OPERACAO_SETOR_ABASTECIMENTO_REMOVER})
	/** nullable persistent field */
	private String descricaoAbreviada;

	@ControleAlteracao(funcionalidade = {OPERACAO_SETOR_ABASTECIMENTO_INSERIR, OPERACAO_SETOR_ABASTECIMENTO_ATUALIZAR, OPERACAO_SETOR_ABASTECIMENTO_REMOVER})
	/** persistent field */
	private short indicadorUso;

	@ControleAlteracao(funcionalidade = {OPERACAO_SETOR_ABASTECIMENTO_INSERIR, OPERACAO_SETOR_ABASTECIMENTO_ATUALIZAR, OPERACAO_SETOR_ABASTECIMENTO_REMOVER})
	/** persistent field */
	private Date ultimaAlteracao;

	@ControleAlteracao(value = FiltroSetorAbastecimento.SISTEMA_ABASTECIMENTO, funcionalidade = {OPERACAO_SETOR_ABASTECIMENTO_INSERIR, OPERACAO_SETOR_ABASTECIMENTO_ATUALIZAR, OPERACAO_SETOR_ABASTECIMENTO_REMOVER})
	/** persistent field */
	private gcom.operacional.SistemaAbastecimento sistemaAbastecimento;

	@ControleAlteracao(value = FiltroSetorAbastecimento.DISTRITO_OPERACIONAL, funcionalidade = {OPERACAO_SETOR_ABASTECIMENTO_INSERIR, OPERACAO_SETOR_ABASTECIMENTO_ATUALIZAR, OPERACAO_SETOR_ABASTECIMENTO_REMOVER})
	/** persistent field */
	private DistritoOperacional distritoOperacional;

	@ControleAlteracao(value = FiltroSetorAbastecimento.ZONA_ABASTECIMENTO, funcionalidade = {OPERACAO_SETOR_ABASTECIMENTO_INSERIR, OPERACAO_SETOR_ABASTECIMENTO_ATUALIZAR, OPERACAO_SETOR_ABASTECIMENTO_REMOVER})
	/** persistent field */
	private gcom.operacional.ZonaAbastecimento zonaAbastecimento;

	/** full constructor */
	public SetorAbastecimento(String descricao, String descricaoAbreviada, short indicadorUso, Date ultimaAlteracao,
								gcom.operacional.SistemaAbastecimento sistemaAbastecimento) {

		this.descricao = descricao;
		this.descricaoAbreviada = descricaoAbreviada;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.sistemaAbastecimento = sistemaAbastecimento;
	}

	/** default constructor */
	public SetorAbastecimento() {

	}

	/** minimal constructor */
	public SetorAbastecimento(short indicadorUso, Date ultimaAlteracao, gcom.operacional.SistemaAbastecimento sistemaAbastecimento) {

		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.sistemaAbastecimento = sistemaAbastecimento;
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

	public short getIndicadorUso(){

		return this.indicadorUso;
	}

	public void setIndicadorUso(short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public gcom.operacional.SistemaAbastecimento getSistemaAbastecimento(){

		return this.sistemaAbastecimento;
	}

	public void setSistemaAbastecimento(gcom.operacional.SistemaAbastecimento sistemaAbastecimento){

		this.sistemaAbastecimento = sistemaAbastecimento;
	}

	public Integer getCodigoSetorAbastecimento(){

		return codigoSetorAbastecimento;
	}

	public void setCodigoSetorAbastecimento(Integer codigoSetorAbastecimento){

		this.codigoSetorAbastecimento = codigoSetorAbastecimento;
	}

	public DistritoOperacional getDistritoOperacional(){

		return distritoOperacional;
	}

	public void setDistritoOperacional(DistritoOperacional distritoOperacional){

		this.distritoOperacional = distritoOperacional;
	}

	public gcom.operacional.ZonaAbastecimento getZonaAbastecimento(){

		return zonaAbastecimento;
	}

	public void setZonaAbastecimento(gcom.operacional.ZonaAbastecimento zonaAbastecimento){

		this.zonaAbastecimento = zonaAbastecimento;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	@Override
	public Filtro retornaFiltro(){

		FiltroSetorAbastecimento filtroSetorAbastecimento = new FiltroSetorAbastecimento();
		filtroSetorAbastecimento.adicionarCaminhoParaCarregamentoEntidade(FiltroSetorAbastecimento.DISTRITO_OPERACIONAL);
		filtroSetorAbastecimento.adicionarCaminhoParaCarregamentoEntidade(FiltroSetorAbastecimento.ZONA_ABASTECIMENTO);
		filtroSetorAbastecimento.adicionarCaminhoParaCarregamentoEntidade(FiltroSetorAbastecimento.SISTEMA_ABASTECIMENTO);
		filtroSetorAbastecimento.adicionarParametro(new ParametroSimples(filtroSetorAbastecimento.ID, this.getId()));
		return filtroSetorAbastecimento;
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"descricao"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Descricao"};
		return labels;
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getId() + "";
	}

	public String getIndicadorUsoFormatado(){

		String retorno = "";
		if(this.indicadorUso == new Short("1")){

			retorno = "Ativo";
		}else{

			retorno = "Inativo";
		}

		return retorno;
	}

	public String getDescricaoComCodigo(){

		String descricaoComCodigo = null;

		Integer codigoSetorAbastecimento = this.getCodigoSetorAbastecimento();
		String descricao = this.getDescricao();

		if(codigoSetorAbastecimento != null && codigoSetorAbastecimento.compareTo(10) == -1){
			descricaoComCodigo = "0" + codigoSetorAbastecimento + " - " + descricao;
		}else if(codigoSetorAbastecimento != null){
			descricaoComCodigo = codigoSetorAbastecimento + " - " + descricao;
		}else{
			descricaoComCodigo = " - " + descricao;
		}

		return descricaoComCodigo;
	}

}
