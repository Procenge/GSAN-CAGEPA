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

package gcom.cobranca;

import gcom.cobranca.parcelamento.ParcelamentoSituacaoEspecial;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
@ControleAlteracao()
public class ResolucaoDiretoria
				extends ObjetoTransacao {

	public static final int ATRIBUTOS_RESOLUCAO_DIRETORIA_ATUALIZAR = 145; // Operacao.OPERACAO_RESOLUCAO_DIRETORIA_ATUALIZAR;

	public static final int ATRIBUTOS_RESOLUCAO_DIRETORIA_REMOVER = 146; // Operacao.OPERACAO_RESOLUCAO_DIRETORIA_REMOVER;

	// TODO Remover est� constante e criar um indicador
	public static final Integer PRC = 101;

	private static final long serialVersionUID = 1L;

	public Filtro retornaFiltro(){

		FiltroResolucaoDiretoria filtroResolucaoDiretoria = new FiltroResolucaoDiretoria();
		filtroResolucaoDiretoria.adicionarParametro(new ParametroSimples(FiltroResolucaoDiretoria.CODIGO, this.getId()));
		filtroResolucaoDiretoria.adicionarCaminhoParaCarregamentoEntidade(FiltroResolucaoDiretoria.RESOLUCAO_DIRETORIA_LAYOUT);

		return filtroResolucaoDiretoria;
	}

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_RESOLUCAO_DIRETORIA_ATUALIZAR, ATRIBUTOS_RESOLUCAO_DIRETORIA_REMOVER})
	private String numeroResolucaoDiretoria;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_RESOLUCAO_DIRETORIA_ATUALIZAR, ATRIBUTOS_RESOLUCAO_DIRETORIA_REMOVER})
	private String descricaoAssunto;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_RESOLUCAO_DIRETORIA_ATUALIZAR, ATRIBUTOS_RESOLUCAO_DIRETORIA_REMOVER})
	private Date dataVigenciaInicio;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_RESOLUCAO_DIRETORIA_ATUALIZAR, ATRIBUTOS_RESOLUCAO_DIRETORIA_REMOVER})
	private Date dataVigenciaFim;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_RESOLUCAO_DIRETORIA_ATUALIZAR, ATRIBUTOS_RESOLUCAO_DIRETORIA_REMOVER})
	private Short indicadorParcelamentoUnico;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_RESOLUCAO_DIRETORIA_ATUALIZAR, ATRIBUTOS_RESOLUCAO_DIRETORIA_REMOVER})
	private Short indicadorUtilizacaoLivre;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_RESOLUCAO_DIRETORIA_ATUALIZAR, ATRIBUTOS_RESOLUCAO_DIRETORIA_REMOVER})
	private Short indicadorDescontoSancoes;

	@ControleAlteracao(value = FiltroResolucaoDiretoria.RESOLUCAO_DIRETORIA_LAYOUT, funcionalidade = {ATRIBUTOS_RESOLUCAO_DIRETORIA_ATUALIZAR, ATRIBUTOS_RESOLUCAO_DIRETORIA_REMOVER})
	private ResolucaoDiretoriaLayout resolucaoDiretoriaLayout;

	private Set<ResolucaoDiretoriaGrupo> resolucaoDiretoriaGrupos;

	private Set<ParcelamentoSituacaoEspecial> parcelamentosSituacaoEspecial;

	private Set<ResolucaoDiretoriaParametrosPagamentoAVista> resolucaoDiretoriaCondicoesPagtoAVista;

	private Set<ResolucaoDiretoriaParametrosValorEntrada> resolucaoDiretoriaCondicoesValorEntrada;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_RESOLUCAO_DIRETORIA_ATUALIZAR, ATRIBUTOS_RESOLUCAO_DIRETORIA_REMOVER})
	private Short indicadorEmissaoAssuntoConta = 2;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_RESOLUCAO_DIRETORIA_ATUALIZAR, ATRIBUTOS_RESOLUCAO_DIRETORIA_REMOVER})
	private Short indicadorTrataMediaAtualizacaoMonetaria = 2;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_RESOLUCAO_DIRETORIA_ATUALIZAR, ATRIBUTOS_RESOLUCAO_DIRETORIA_REMOVER})
	private Short indicadorCobrarDescontosArrasto = 2;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_RESOLUCAO_DIRETORIA_ATUALIZAR, ATRIBUTOS_RESOLUCAO_DIRETORIA_REMOVER})
	private Short indicadorArrasto = 2;

	public Short getIndicadorParcelamentoUnico(){

		return indicadorParcelamentoUnico;
	}

	public void setIndicadorParcelamentoUnico(Short indicadorParcelamentoUnico){

		this.indicadorParcelamentoUnico = indicadorParcelamentoUnico;
	}

	public Short getIndicadorUtilizacaoLivre(){

		return indicadorUtilizacaoLivre;
	}

	public void setIndicadorUtilizacaoLivre(Short indicadorUtilizacaoLivre){

		this.indicadorUtilizacaoLivre = indicadorUtilizacaoLivre;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	/** full constructor */
	public ResolucaoDiretoria(String numeroResolucaoDiretoria, String descricaoAssunto, Date dataVigenciaInicio, Date dataVigenciaFim,
								Date ultimaAlteracao, Short indicadorParcelamentoUnico, Short indicadorUtilizacaoLivre,
								Short indicadorDescontoSancoes, ResolucaoDiretoriaLayout resolucaoDiretoriaLayout) {

		this.numeroResolucaoDiretoria = numeroResolucaoDiretoria;
		this.descricaoAssunto = descricaoAssunto;
		this.dataVigenciaInicio = dataVigenciaInicio;
		this.dataVigenciaFim = dataVigenciaFim;
		this.ultimaAlteracao = ultimaAlteracao;
		this.indicadorParcelamentoUnico = indicadorParcelamentoUnico;
		this.indicadorUtilizacaoLivre = indicadorUtilizacaoLivre;
		this.indicadorDescontoSancoes = indicadorDescontoSancoes;
		this.resolucaoDiretoriaLayout = resolucaoDiretoriaLayout;
	}

	/** default constructor */
	public ResolucaoDiretoria() {

	}

	public ResolucaoDiretoria(Integer id) {

		this.id = id;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getNumeroResolucaoDiretoria(){

		return this.numeroResolucaoDiretoria;
	}

	public void setNumeroResolucaoDiretoria(String numeroResolucaoDiretoria){

		this.numeroResolucaoDiretoria = numeroResolucaoDiretoria;
	}

	public String getDescricaoAssunto(){

		return this.descricaoAssunto;
	}

	public void setDescricaoAssunto(String descricaoAssunto){

		this.descricaoAssunto = descricaoAssunto;
	}

	public Date getDataVigenciaInicio(){

		return this.dataVigenciaInicio;
	}

	public void setDataVigenciaInicio(Date dataVigenciaInicio){

		this.dataVigenciaInicio = dataVigenciaInicio;
	}

	public Date getDataVigenciaFim(){

		return this.dataVigenciaFim;
	}

	public void setDataVigenciaFim(Date dataVigenciaFim){

		this.dataVigenciaFim = dataVigenciaFim;
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
	 * @return Retorna o campo indicadorDescontoSancoes.
	 */
	public Short getIndicadorDescontoSancoes(){

		return indicadorDescontoSancoes;
	}

	/**
	 * @param indicadorDescontoSancoes
	 *            O indicadorDescontoSancoes a ser setado.
	 */
	public void setIndicadorDescontoSancoes(Short indicadorDescontoSancoes){

		this.indicadorDescontoSancoes = indicadorDescontoSancoes;
	}

	/**
	 * @return the resolucaoDiretoriaLayout
	 */
	public ResolucaoDiretoriaLayout getResolucaoDiretoriaLayout(){

		return resolucaoDiretoriaLayout;
	}

	/**
	 * @param resolucaoDiretoriaLayout
	 *            the resolucaoDiretoriaLayout to set
	 */
	public void setResolucaoDiretoriaLayout(ResolucaoDiretoriaLayout resolucaoDiretoriaLayout){

		this.resolucaoDiretoriaLayout = resolucaoDiretoriaLayout;
	}

	public Set<ResolucaoDiretoriaGrupo> getResolucaoDiretoriaGrupos(){

		return resolucaoDiretoriaGrupos;
	}

	public void setResolucaoDiretoriaGrupos(Set<ResolucaoDiretoriaGrupo> resolucaoDiretoriaGrupos){

		this.resolucaoDiretoriaGrupos = resolucaoDiretoriaGrupos;
	}

	public Set<ParcelamentoSituacaoEspecial> getParcelamentosSituacaoEspecial(){

		return parcelamentosSituacaoEspecial;
	}

	public void setParcelamentosSituacaoEspecial(Set<ParcelamentoSituacaoEspecial> parcelamentosSituacaoEspecial){

		this.parcelamentosSituacaoEspecial = parcelamentosSituacaoEspecial;
	}

	public Short getIndicadorEmissaoAssuntoConta(){

		return indicadorEmissaoAssuntoConta;
	}

	public void setIndicadorEmissaoAssuntoConta(Short indicadorEmissaoAssuntoConta){

		this.indicadorEmissaoAssuntoConta = indicadorEmissaoAssuntoConta;
	}

	public Short getIndicadorTrataMediaAtualizacaoMonetaria(){

		return indicadorTrataMediaAtualizacaoMonetaria;
	}

	public void setIndicadorTrataMediaAtualizacaoMonetaria(Short indicadorTrataMediaAtualizacaoMonetaria){

		this.indicadorTrataMediaAtualizacaoMonetaria = indicadorTrataMediaAtualizacaoMonetaria;
	}

	public Short getIndicadorCobrarDescontosArrasto(){

		return indicadorCobrarDescontosArrasto;
	}

	public void setIndicadorCobrarDescontosArrasto(Short indicadorCobrarDescontosArrasto){

		this.indicadorCobrarDescontosArrasto = indicadorCobrarDescontosArrasto;
	}

	public String getDescricaoAssuntoAbreviada(){

		String retorno = "";

		if(descricaoAssunto != null && descricaoAssunto.length() >= 3){

			retorno = descricaoAssunto.substring(0, 3);

		}

		return retorno;

	}

	public Set<ResolucaoDiretoriaParametrosPagamentoAVista> getResolucaoDiretoriaCondicoesPagtoAVista(){

		return resolucaoDiretoriaCondicoesPagtoAVista;
	}

	public void setResolucaoDiretoriaCondicoesPagtoAVista(
					Set<ResolucaoDiretoriaParametrosPagamentoAVista> resolucaoDiretoriaCondicoesPagtoAVista){

		this.resolucaoDiretoriaCondicoesPagtoAVista = resolucaoDiretoriaCondicoesPagtoAVista;
	}

	public Set<ResolucaoDiretoriaParametrosValorEntrada> getResolucaoDiretoriaCondicoesValorEntrada(){

		return resolucaoDiretoriaCondicoesValorEntrada;
	}

	public void setResolucaoDiretoriaCondicoesValorEntrada(
					Set<ResolucaoDiretoriaParametrosValorEntrada> resolucaoDiretoriaCondicoesValorEntrada){

		this.resolucaoDiretoriaCondicoesValorEntrada = resolucaoDiretoriaCondicoesValorEntrada;
	}

	public void setIndicadorArrasto(Short indicadorArrasto){

		this.indicadorArrasto = indicadorArrasto;
	}

	public Short getIndicadorArrasto(){

		return indicadorArrasto;
	}


}
