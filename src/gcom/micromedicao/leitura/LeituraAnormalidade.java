/*
 * Copyright (C) 2007-2007 the GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
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
 * Foundation, Inc., 59 Temple Place – Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araújo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cláudio de Andrade Lira
 * Denys Guimarães Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabíola Gomes de Araújo
 * Flávio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Júnior
 * Homero Sampaio Cavalcanti
 * Ivan Sérgio da Silva Júnior
 * José Edmar de Siqueira
 * José Thiago Tenório Lopes
 * Kássia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Márcio Roberto Batista da Silva
 * Maria de Fátima Sampaio Leite
 * Micaela Maria Coelho de Araújo
 * Nelson Mendonça de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrêa Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araújo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sávio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos de Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; versão 2 da
 * Licença.
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.micromedicao.leitura;

import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.ErroRepositorioException;
import gcom.util.RepositorioUtilHBM;
import gcom.util.SistemaException;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Hibernate CodeGenerator
 */
@ControleAlteracao()
public class LeituraAnormalidade
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	/**
	 * identifier field
	 */
	private Integer id;

	/**
	 * persistent field
	 */
	private String descricao;

	/**
	 * persistent field
	 */
	private String descricaoAbreviada;

	/**
	 * persistent field
	 */
	private Short indicadorRelativoHidrometro;

	/**
	 * nullable persistent field
	 */
	private Short indicadorImovelSemHidrometro;

	/**
	 * nullable persistent field
	 */
	private Short indicadorSistema;

	/**
	 * persistent field
	 */
	private Short indicadorEmissaoOrdemServico;

	/**
	 * nullable persistent field
	 */
	private Short indicadorUso;

	/**
	 * nullable persistent field
	 */
	// private Short servCdservico;

	/**
	 * nullable persistent field
	 */
	private Short indicadorPerdaTarifaSocial;

	/**
	 * nullable persistent field
	 */
	private Date ultimaAlteracao;

	/**
	 * nullable persistent field
	 */
	private String mensagemContaLeituraAnormalidade;

	/**
	 * nullable persistent field
	 */
	private String mensagemSugestaoManutencaoLeituraAnormalidade;

	/**
	 * nullable persistent field
	 */
	private String mensagemSugestaoPrevencaoLeituraAnormalidade;

	/**
	 * nullable persistent field
	 */
	private Short numeroIncidenciasGeracaoOrdemServico;

	/**
	 * nullable persistent field
	 */
	private Short indicadorAceiteLeitura;

	/**
	 * nullable persistent field
	 */
	private Short indicadorListagemAnormalidadeRelatorios;

	/**
	 * nullable persistent field
	 */
	private Short indicadorRetencaoConta;

	/**
	 * nullable persistent field
	 */
	private Short indicadorIsencaoAgua;

	/**
	 * nullable persistent field
	 */
	private Short indicadorIsencaoEsgoto;

	/**
	 * nullable persistent field
	 */
	private Short indicadorCreditoConsumo;

	/**
	 * persistent field
	 */
	private gcom.micromedicao.leitura.LeituraAnormalidadeLeitura leituraAnormalidadeLeituraComleitura;

	/**
	 * persistent field
	 */
	private gcom.micromedicao.leitura.LeituraAnormalidadeLeitura leituraAnormalidadeLeituraSemleitura;

	/**
	 * persistent field
	 */
	private gcom.micromedicao.leitura.LeituraAnormalidadeConsumo leituraAnormalidadeConsumoComleitura;

	/**
	 * persistent field
	 */
	private gcom.micromedicao.leitura.LeituraAnormalidadeConsumo leituraAnormalidadeConsumoSemleitura;

	private gcom.atendimentopublico.ordemservico.ServicoTipo servicoTipo;

	private gcom.cobranca.DocumentoTipo documentoTipo;

	private String codigoConstante;

	// CONSTANTES

	public static Integer NORMAL;

	public static Integer HIDROMETRO_SUBSTITUIDO;

	public static Integer HIDROMETRO_PARADO;

	public static Integer HIDROMETRO_PARADO_SEM_CONSUMO;

	public static Integer VAZAMENTO_APOS_HIDROMETRO;

	public static Integer VAZAMENTO_INTERNO;

	public static Integer ALTO_CONSUMO_SEM_JUSTIFICATIVA;

	public static Integer LEITURA_NAO_REALIZADA;

	public static Integer LIGADO_CLANDESTINO_AGUA;

	public static Integer LIGADO_CLANDESTINO_ESGOTO;

	public static Integer LIGADO_CLANDESTINO_AGUA_ESGOTO;

	public static Integer LEITURA_IGUAL_ANTERIOR;

	public static Integer HIDROMETRO_INVERTIDO;

	/**
	 * full constructor
	 * 
	 * @param descricao
	 *            Descrição do parâmetro
	 * @param descricaoAbreviada
	 *            Descrição do parâmetro
	 * @param indicadorRelativoHidrometro
	 *            Descrição do parâmetro
	 * @param indicadorImovelSemHidrometro
	 *            Descrição do parâmetro
	 * @param indicadorSistema
	 *            Descrição do parâmetro
	 * @param indicadorEmissaoOrdemServico
	 *            Descrição do parâmetro
	 * @param indicadorUso
	 *            Descrição do parâmetro
	 * @param servCdservico
	 *            Descrição do parâmetro
	 * @param indicadorPerdaTarifaSocial
	 *            Descrição do parâmetro
	 * @param ultimaAlteracao
	 *            Descrição do parâmetro
	 * @param leituraAnormalidadeLeituraComleitura
	 *            Descrição do parâmetro
	 * @param leituraAnormalidadeLeituraSemleitura
	 *            Descrição do parâmetro
	 * @param leituraAnormalidadeConsumoComleitura
	 *            Descrição do parâmetro
	 * @param leituraAnormalidadeConsumoSemleitura
	 *            Descrição do parâmetro
	 */
	public LeituraAnormalidade(
								String descricao,
								String descricaoAbreviada,
								Short indicadorRelativoHidrometro,
								Short indicadorImovelSemHidrometro,
								Short indicadorSistema,
								Short indicadorEmissaoOrdemServico,
								Short indicadorUso,
								// Short servCdservico,
								Short indicadorPerdaTarifaSocial, Date ultimaAlteracao,
								gcom.micromedicao.leitura.LeituraAnormalidadeLeitura leituraAnormalidadeLeituraComleitura,
								gcom.micromedicao.leitura.LeituraAnormalidadeLeitura leituraAnormalidadeLeituraSemleitura,
								gcom.micromedicao.leitura.LeituraAnormalidadeConsumo leituraAnormalidadeConsumoComleitura,
								gcom.micromedicao.leitura.LeituraAnormalidadeConsumo leituraAnormalidadeConsumoSemleitura,
								gcom.atendimentopublico.ordemservico.ServicoTipo servicoTipo, String mensagemContaLeituraAnormalidade,
								String mensagemSugestaoManutencaoLeituraAnormalidade, String mensagemSugestaoPrevencaoLeituraAnormalidade,
								Short numeroIncidenciasGeracaoOrdemServico, Short indicadorAceiteLeitura,
								Short indicadorListagemAnormalidadeRelatorios, Short indicadorRetencaoConta, Short indicadorRetencaoAgua,
								Short indicadorRetencaoEsgoto, Short indicadorCreditoConsumo, gcom.cobranca.DocumentoTipo documentoTipo) {

		this.descricao = descricao;
		this.descricaoAbreviada = descricaoAbreviada;
		this.indicadorRelativoHidrometro = indicadorRelativoHidrometro;
		this.indicadorImovelSemHidrometro = indicadorImovelSemHidrometro;
		this.indicadorSistema = indicadorSistema;
		this.indicadorEmissaoOrdemServico = indicadorEmissaoOrdemServico;
		this.indicadorUso = indicadorUso;
		// this.servCdservico = servCdservico;
		this.indicadorPerdaTarifaSocial = indicadorPerdaTarifaSocial;
		this.ultimaAlteracao = ultimaAlteracao;
		this.leituraAnormalidadeLeituraComleitura = leituraAnormalidadeLeituraComleitura;
		this.leituraAnormalidadeLeituraSemleitura = leituraAnormalidadeLeituraSemleitura;
		this.leituraAnormalidadeConsumoComleitura = leituraAnormalidadeConsumoComleitura;
		this.leituraAnormalidadeConsumoSemleitura = leituraAnormalidadeConsumoSemleitura;
		this.servicoTipo = servicoTipo;
		this.mensagemContaLeituraAnormalidade = mensagemContaLeituraAnormalidade;
		this.mensagemSugestaoManutencaoLeituraAnormalidade = mensagemSugestaoManutencaoLeituraAnormalidade;
		this.mensagemSugestaoPrevencaoLeituraAnormalidade = mensagemSugestaoPrevencaoLeituraAnormalidade;
		this.numeroIncidenciasGeracaoOrdemServico = numeroIncidenciasGeracaoOrdemServico;
		this.indicadorAceiteLeitura = indicadorAceiteLeitura;
		this.indicadorListagemAnormalidadeRelatorios = indicadorListagemAnormalidadeRelatorios;
		this.indicadorRetencaoConta = indicadorRetencaoConta;
		this.indicadorIsencaoAgua = indicadorRetencaoAgua;
		this.indicadorIsencaoEsgoto = indicadorRetencaoEsgoto;
		this.indicadorCreditoConsumo = indicadorCreditoConsumo;
		this.documentoTipo = documentoTipo;
	}

	/**
	 * @return Retorna o campo servicoTipo.
	 */
	public gcom.atendimentopublico.ordemservico.ServicoTipo getServicoTipo(){

		return servicoTipo;
	}

	/**
	 * @param servicoTipo
	 *            O servicoTipo a ser setado.
	 */
	public void setServicoTipo(gcom.atendimentopublico.ordemservico.ServicoTipo servicoTipo){

		this.servicoTipo = servicoTipo;
	}

	/**
	 * default constructor
	 */
	public LeituraAnormalidade() {

	}

	public LeituraAnormalidade(Integer id) {

		this.id = id;
	}

	/**
	 * minimal constructor
	 * 
	 * @param descricao
	 *            Descrição do parâmetro
	 * @param descricaoAbreviada
	 *            Descrição do parâmetro
	 * @param indicadorRelativoHidrometro
	 *            Descrição do parâmetro
	 * @param indicadorEmissaoOrdemServico
	 *            Descrição do parâmetro
	 * @param leituraAnormalidadeLeituraComleitura
	 *            Descrição do parâmetro
	 * @param leituraAnormalidadeLeituraSemleitura
	 *            Descrição do parâmetro
	 * @param leituraAnormalidadeConsumoComleitura
	 *            Descrição do parâmetro
	 * @param leituraAnormalidadeConsumoSemleitura
	 *            Descrição do parâmetro
	 */
	public LeituraAnormalidade(String descricao, String descricaoAbreviada, Short indicadorRelativoHidrometro,
								Short indicadorEmissaoOrdemServico,
								gcom.micromedicao.leitura.LeituraAnormalidadeLeitura leituraAnormalidadeLeituraComleitura,
								gcom.micromedicao.leitura.LeituraAnormalidadeLeitura leituraAnormalidadeLeituraSemleitura,
								gcom.micromedicao.leitura.LeituraAnormalidadeConsumo leituraAnormalidadeConsumoComleitura,
								gcom.micromedicao.leitura.LeituraAnormalidadeConsumo leituraAnormalidadeConsumoSemleitura) {

		this.descricao = descricao;
		this.descricaoAbreviada = descricaoAbreviada;
		this.indicadorRelativoHidrometro = indicadorRelativoHidrometro;
		this.indicadorEmissaoOrdemServico = indicadorEmissaoOrdemServico;
		this.leituraAnormalidadeLeituraComleitura = leituraAnormalidadeLeituraComleitura;
		this.leituraAnormalidadeLeituraSemleitura = leituraAnormalidadeLeituraSemleitura;
		this.leituraAnormalidadeConsumoComleitura = leituraAnormalidadeConsumoComleitura;
		this.leituraAnormalidadeConsumoSemleitura = leituraAnormalidadeConsumoSemleitura;
	}

	/**
	 * Retorna o valor de id
	 * 
	 * @return O valor de id
	 */
	public Integer getId(){

		return this.id;
	}

	/**
	 * Seta o valor de id
	 * 
	 * @param id
	 *            O novo valor de id
	 */
	public void setId(Integer id){

		this.id = id;
	}

	/**
	 * Retorna o valor de descricao
	 * 
	 * @return O valor de descricao
	 */
	public String getDescricao(){

		return this.descricao;
	}

	/**
	 * Seta o valor de descricao
	 * 
	 * @param descricao
	 *            O novo valor de descricao
	 */
	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	/**
	 * Retorna o valor de descricaoAbreviada
	 * 
	 * @return O valor de descricaoAbreviada
	 */
	public String getDescricaoAbreviada(){

		return this.descricaoAbreviada;
	}

	/**
	 * Seta o valor de descricaoAbreviada
	 * 
	 * @param descricaoAbreviada
	 *            O novo valor de descricaoAbreviada
	 */
	public void setDescricaoAbreviada(String descricaoAbreviada){

		this.descricaoAbreviada = descricaoAbreviada;
	}

	/**
	 * Retorna o valor de indicadorRelativoHidrometro
	 * 
	 * @return O valor de indicadorRelativoHidrometro
	 */
	public Short getIndicadorRelativoHidrometro(){

		return this.indicadorRelativoHidrometro;
	}

	/**
	 * Seta o valor de indicadorRelativoHidrometro
	 * 
	 * @param indicadorRelativoHidrometro
	 *            O novo valor de indicadorRelativoHidrometro
	 */
	public void setIndicadorRelativoHidrometro(Short indicadorRelativoHidrometro){

		this.indicadorRelativoHidrometro = indicadorRelativoHidrometro;
	}

	/**
	 * Retorna o valor de indicadorImovelSemHidrometro
	 * 
	 * @return O valor de indicadorImovelSemHidrometro
	 */
	public Short getIndicadorImovelSemHidrometro(){

		return this.indicadorImovelSemHidrometro;
	}

	/**
	 * Seta o valor de indicadorImovelSemHidrometro
	 * 
	 * @param indicadorImovelSemHidrometro
	 *            O novo valor de indicadorImovelSemHidrometro
	 */
	public void setIndicadorImovelSemHidrometro(Short indicadorImovelSemHidrometro){

		this.indicadorImovelSemHidrometro = indicadorImovelSemHidrometro;
	}

	/**
	 * Retorna o valor de indicadorSistema
	 * 
	 * @return O valor de indicadorSistema
	 */
	public Short getIndicadorSistema(){

		return this.indicadorSistema;
	}

	/**
	 * Seta o valor de indicadorSistema
	 * 
	 * @param indicadorSistema
	 *            O novo valor de indicadorSistema
	 */
	public void setIndicadorSistema(Short indicadorSistema){

		this.indicadorSistema = indicadorSistema;
	}

	/**
	 * Retorna o valor de indicadorEmissaoOrdemServico
	 * 
	 * @return O valor de indicadorEmissaoOrdemServico
	 */
	public Short getIndicadorEmissaoOrdemServico(){

		return this.indicadorEmissaoOrdemServico;
	}

	/**
	 * Seta o valor de indicadorEmissaoOrdemServico
	 * 
	 * @param indicadorEmissaoOrdemServico
	 *            O novo valor de indicadorEmissaoOrdemServico
	 */
	public void setIndicadorEmissaoOrdemServico(Short indicadorEmissaoOrdemServico){

		this.indicadorEmissaoOrdemServico = indicadorEmissaoOrdemServico;
	}

	/**
	 * Retorna o valor de indicadorUso
	 * 
	 * @return O valor de indicadorUso
	 */
	public Short getIndicadorUso(){

		return this.indicadorUso;
	}

	/**
	 * Seta o valor de indicadorUso
	 * 
	 * @param indicadorUso
	 *            O novo valor de indicadorUso
	 */
	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	// /**
	// * Retorna o valor de servCdservico
	// *
	// * @return O valor de servCdservico
	// */
	// public Short getServCdservico() {
	// return this.servCdservico;
	// }
	//
	// /**
	// * Seta o valor de servCdservico
	// *
	// * @param servCdservico
	// * O novo valor de servCdservico
	// */
	// public void setServCdservico(Short servCdservico) {
	// this.servCdservico = servCdservico;
	// }

	/**
	 * Retorna o valor de indicadorPerdaTarifaSocial
	 * 
	 * @return O valor de indicadorPerdaTarifaSocial
	 */
	public Short getIndicadorPerdaTarifaSocial(){

		return this.indicadorPerdaTarifaSocial;
	}

	/**
	 * Seta o valor de indicadorPerdaTarifaSocial
	 * 
	 * @param indicadorPerdaTarifaSocial
	 *            O novo valor de indicadorPerdaTarifaSocial
	 */
	public void setIndicadorPerdaTarifaSocial(Short indicadorPerdaTarifaSocial){

		this.indicadorPerdaTarifaSocial = indicadorPerdaTarifaSocial;
	}

	/**
	 * Retorna o valor de ultimaAlteracao
	 * 
	 * @return O valor de ultimaAlteracao
	 */
	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	/**
	 * Seta o valor de ultimaAlteracao
	 * 
	 * @param ultimaAlteracao
	 *            O novo valor de ultimaAlteracao
	 */
	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	/**
	 * Retorna o valor de leituraAnormalidadeLeituraComleitura
	 * 
	 * @return O valor de leituraAnormalidadeLeituraComleitura
	 */
	public gcom.micromedicao.leitura.LeituraAnormalidadeLeitura getLeituraAnormalidadeLeituraComleitura(){

		return this.leituraAnormalidadeLeituraComleitura;
	}

	/**
	 * Seta o valor de leituraAnormalidadeLeituraComleitura
	 * 
	 * @param leituraAnormalidadeLeituraComleitura
	 *            O novo valor de leituraAnormalidadeLeituraComleitura
	 */
	public void setLeituraAnormalidadeLeituraComleitura(
					gcom.micromedicao.leitura.LeituraAnormalidadeLeitura leituraAnormalidadeLeituraComleitura){

		this.leituraAnormalidadeLeituraComleitura = leituraAnormalidadeLeituraComleitura;
	}

	/**
	 * Retorna o valor de leituraAnormalidadeLeituraSemleitura
	 * 
	 * @return O valor de leituraAnormalidadeLeituraSemleitura
	 */
	public gcom.micromedicao.leitura.LeituraAnormalidadeLeitura getLeituraAnormalidadeLeituraSemleitura(){

		return this.leituraAnormalidadeLeituraSemleitura;
	}

	/**
	 * Seta o valor de leituraAnormalidadeLeituraSemleitura
	 * 
	 * @param leituraAnormalidadeLeituraSemleitura
	 *            O novo valor de leituraAnormalidadeLeituraSemleitura
	 */
	public void setLeituraAnormalidadeLeituraSemleitura(
					gcom.micromedicao.leitura.LeituraAnormalidadeLeitura leituraAnormalidadeLeituraSemleitura){

		this.leituraAnormalidadeLeituraSemleitura = leituraAnormalidadeLeituraSemleitura;
	}

	/**
	 * Retorna o valor de leituraAnormalidadeConsumoComleitura
	 * 
	 * @return O valor de leituraAnormalidadeConsumoComleitura
	 */
	public gcom.micromedicao.leitura.LeituraAnormalidadeConsumo getLeituraAnormalidadeConsumoComleitura(){

		return this.leituraAnormalidadeConsumoComleitura;
	}

	/**
	 * Seta o valor de leituraAnormalidadeConsumoComleitura
	 * 
	 * @param leituraAnormalidadeConsumoComleitura
	 *            O novo valor de leituraAnormalidadeConsumoComleitura
	 */
	public void setLeituraAnormalidadeConsumoComleitura(
					gcom.micromedicao.leitura.LeituraAnormalidadeConsumo leituraAnormalidadeConsumoComleitura){

		this.leituraAnormalidadeConsumoComleitura = leituraAnormalidadeConsumoComleitura;
	}

	/**
	 * Retorna o valor de leituraAnormalidadeConsumoSemleitura
	 * 
	 * @return O valor de leituraAnormalidadeConsumoSemleitura
	 */
	public gcom.micromedicao.leitura.LeituraAnormalidadeConsumo getLeituraAnormalidadeConsumoSemleitura(){

		return this.leituraAnormalidadeConsumoSemleitura;
	}

	/**
	 * Seta o valor de leituraAnormalidadeConsumoSemleitura
	 * 
	 * @param leituraAnormalidadeConsumoSemleitura
	 *            O novo valor de leituraAnormalidadeConsumoSemleitura
	 */
	public void setLeituraAnormalidadeConsumoSemleitura(
					gcom.micromedicao.leitura.LeituraAnormalidadeConsumo leituraAnormalidadeConsumoSemleitura){

		this.leituraAnormalidadeConsumoSemleitura = leituraAnormalidadeConsumoSemleitura;
	}

	public String getMensagemContaLeituraAnormalidade(){

		return mensagemContaLeituraAnormalidade;
	}

	public void setMensagemContaLeituraAnormalidade(String mensagemContaLeituraAnormalidade){

		this.mensagemContaLeituraAnormalidade = mensagemContaLeituraAnormalidade;
	}

	public String getMensagemSugestaoManutencaoLeituraAnormalidade(){

		return mensagemSugestaoManutencaoLeituraAnormalidade;
	}

	public void setMensagemSugestaoManutencaoLeituraAnormalidade(String mensagemSugestaoManutencaoLeituraAnormalidade){

		this.mensagemSugestaoManutencaoLeituraAnormalidade = mensagemSugestaoManutencaoLeituraAnormalidade;
	}

	public String getMensagemSugestaoPrevencaoLeituraAnormalidade(){

		return mensagemSugestaoPrevencaoLeituraAnormalidade;
	}

	public void setMensagemSugestaoPrevencaoLeituraAnormalidade(String mensagemSugestaoPrevencaoLeituraAnormalidade){

		this.mensagemSugestaoPrevencaoLeituraAnormalidade = mensagemSugestaoPrevencaoLeituraAnormalidade;
	}

	public Short getIndicadorAceiteLeitura(){

		return indicadorAceiteLeitura;
	}

	public void setIndicadorAceiteLeitura(Short indicadorAceiteLeitura){

		this.indicadorAceiteLeitura = indicadorAceiteLeitura;
	}

	public Short getIndicadorRetencaoConta(){

		return indicadorRetencaoConta;
	}

	public void setIndicadorRetencaoConta(Short indicadorRetencaoConta){

		this.indicadorRetencaoConta = indicadorRetencaoConta;
	}

	public Short getIndicadorIsencaoAgua(){

		return indicadorIsencaoAgua;
	}

	public void setIndicadorIsencaoAgua(Short indicadorRetencaoAgua){

		this.indicadorIsencaoAgua = indicadorRetencaoAgua;
	}

	public Short getIndicadorIsencaoEsgoto(){

		return indicadorIsencaoEsgoto;
	}

	public void setIndicadorIsencaoEsgoto(Short indicadorRetencaoEsgoto){

		this.indicadorIsencaoEsgoto = indicadorRetencaoEsgoto;
	}

	public Short getIndicadorCreditoConsumo(){

		return indicadorCreditoConsumo;
	}

	public void setIndicadorCreditoConsumo(Short indicadorCreditoConsumo){

		this.indicadorCreditoConsumo = indicadorCreditoConsumo;
	}

	public gcom.cobranca.DocumentoTipo getDocumentoTipo(){

		return documentoTipo;
	}

	public void setDocumentoTipo(gcom.cobranca.DocumentoTipo documentoTipo){

		this.documentoTipo = documentoTipo;
	}

	public Short getNumeroIncidenciasGeracaoOrdemServico(){

		return numeroIncidenciasGeracaoOrdemServico;
	}

	public void setNumeroIncidenciasGeracaoOrdemServico(Short numeroIncidenciasGeracaoOrdemServico){

		this.numeroIncidenciasGeracaoOrdemServico = numeroIncidenciasGeracaoOrdemServico;
	}

	public Short getIndicadorListagemAnormalidadeRelatorios(){

		return indicadorListagemAnormalidadeRelatorios;
	}

	public void setIndicadorListagemAnormalidadeRelatorios(Short indicadorListagemAnormalidadeRelatorios){

		this.indicadorListagemAnormalidadeRelatorios = indicadorListagemAnormalidadeRelatorios;
	}

	public String getCodigoConstante(){

		return codigoConstante;
	}

	public void setCodigoConstante(String codigoConstante){

		this.codigoConstante = codigoConstante;
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"descricao"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Descricao"};
		return labels;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Filtro retornaFiltro(){

		FiltroLeituraAnormalidade filtroLeituraAnormalidade = new FiltroLeituraAnormalidade();

		filtroLeituraAnormalidade.adicionarParametro(new ParametroSimples(FiltroLeituraAnormalidade.ID, this.getId()));
		filtroLeituraAnormalidade.adicionarCaminhoParaCarregamentoEntidade("servicoTipo");
		filtroLeituraAnormalidade.adicionarCaminhoParaCarregamentoEntidade("leituraAnormalidadeConsumoSemleitura");
		filtroLeituraAnormalidade.adicionarCaminhoParaCarregamentoEntidade("leituraAnormalidadeConsumoComleitura");
		filtroLeituraAnormalidade.adicionarCaminhoParaCarregamentoEntidade("leituraAnormalidadeLeituraSemleitura");
		filtroLeituraAnormalidade.adicionarCaminhoParaCarregamentoEntidade("leituraAnormalidadeLeituraComleitura");

		return filtroLeituraAnormalidade;
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getId() + "";
	}

	/**
	 * Este método foi criado para inicializar as constantes. A sua implementação visa utilizar a
	 * solução dada para casos em que haja constantes com descrições diferentes mas que utilizavam o
	 * mesmo valor em clientes distintos.
	 * 
	 * @author Hugo Lima
	 * @date 25/07/2012
	 */
	public static void inicializarConstantes(){

		NORMAL = LeituraAnormalidadeEnum.NORMAL.getId();

		HIDROMETRO_SUBSTITUIDO = LeituraAnormalidadeEnum.HIDROMETRO_SUBSTITUIDO.getId();

		HIDROMETRO_PARADO = LeituraAnormalidadeEnum.HIDROMETRO_PARADO.getId();

		HIDROMETRO_PARADO_SEM_CONSUMO = LeituraAnormalidadeEnum.HIDROMETRO_PARADO_SEM_CONSUMO.getId();

		VAZAMENTO_APOS_HIDROMETRO = LeituraAnormalidadeEnum.VAZAMENTO_APOS_HIDROMETRO.getId();

		VAZAMENTO_INTERNO = LeituraAnormalidadeEnum.VAZAMENTO_INTERNO.getId();

		ALTO_CONSUMO_SEM_JUSTIFICATIVA = LeituraAnormalidadeEnum.ALTO_CONSUMO_SEM_JUSTIFICATIVA.getId();

		LEITURA_NAO_REALIZADA = LeituraAnormalidadeEnum.LEITURA_NAO_REALIZADA.getId();

		LIGADO_CLANDESTINO_AGUA = LeituraAnormalidadeEnum.LIGADO_CLANDESTINO_AGUA.getId();

		LIGADO_CLANDESTINO_ESGOTO = LeituraAnormalidadeEnum.LIGADO_CLANDESTINO_ESGOTO.getId();

		LIGADO_CLANDESTINO_AGUA_ESGOTO = LeituraAnormalidadeEnum.LIGADO_CLANDESTINO_AGUA_ESGOTO.getId();

		LEITURA_IGUAL_ANTERIOR = LeituraAnormalidadeEnum.LEITURA_IGUAL_ANTERIOR.getId();

		HIDROMETRO_INVERTIDO = LeituraAnormalidadeEnum.HIDROMETRO_INVERTIDO.getId();
	}

	/**
	 * Este enum foi criado para dar suporte ao carregamento de constantes da classe em tempo de
	 * execução. As constantes foram criadas aqui como atributos do enum, o que resolveu o problema
	 * das constantes com descrições diferentes mas que utilizavam o mesmo
	 * valor. Caso a constante não seja utilizada por um determinado cliente será atribuído -1 ao
	 * seu valor.
	 * 
	 * @author Hugo Lima
	 * @date 25/07/2012
	 */
	public static enum LeituraAnormalidadeEnum {
		NORMAL, HIDROMETRO_SUBSTITUIDO, HIDROMETRO_PARADO, HIDROMETRO_PARADO_SEM_CONSUMO, VAZAMENTO_APOS_HIDROMETRO, VAZAMENTO_INTERNO,
		ALTO_CONSUMO_SEM_JUSTIFICATIVA, LEITURA_NAO_REALIZADA, LIGADO_CLANDESTINO_AGUA, LIGADO_CLANDESTINO_ESGOTO,
		LIGADO_CLANDESTINO_AGUA_ESGOTO, LEITURA_IGUAL_ANTERIOR, HIDROMETRO_INVERTIDO;

		private Integer id = -1;

		private LeituraAnormalidadeEnum() {

			try{
				LeituraAnormalidade leituraAnormalidade = RepositorioUtilHBM.getInstancia().pesquisarPorCodigo(name(),
								LeituraAnormalidade.class);

				if(leituraAnormalidade != null){
					id = leituraAnormalidade.getId();
				}
			}catch(ErroRepositorioException e){
				e.printStackTrace();
				throw new SistemaException(e, e.getMessage());
			}
		}

		public Integer getId(){

			return id;
		}
	}

	@Override
	public boolean equals(Object other){

		if((this == other)){
			return true;
		}
		if(!(other instanceof LeituraAnormalidade)){
			return false;
		}
		LeituraAnormalidade castOther = (LeituraAnormalidade) other;

		return (this.getId().equals(castOther.getId()));
	}

}
