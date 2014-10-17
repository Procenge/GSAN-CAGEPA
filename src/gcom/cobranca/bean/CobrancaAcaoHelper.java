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

package gcom.cobranca.bean;

import gcom.seguranca.acesso.usuario.Usuario;

import java.io.Serializable;

/**
 * [UC0312] Inserir Ação de Cobrança
 * 
 * @author Sávio Luiz
 * @date 04/12/2007
 */
public class CobrancaAcaoHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/** nullable persistent field */
	private String descricaoCobrancaAcao;

	/** nullable persistent field */
	private String indicadorObrigatoriedade;

	/** nullable persistent field */
	private String indicadorRepeticao;

	/** nullable persistent field */
	private String indicadorSuspensaoAbastecimento;

	/** nullable persistent field */
	private String numeroDiasValidade;

	/** nullable persistent field */
	private String numeroDiasMinimoAcaoPrecedente;

	/** persistent field */
	private String qtdDiasRealizacao;

	/** nullable persistent field */
	private String indicadorUso;

	/** nullable persistent field */
	private String ultimaAlteracao;

	/** nullable persistent field */
	private String indicadorCobrancaDebACobrar;

	/** nullable persistent field */
	private String indicadorGeracaoTaxa;

	/** nullable persistent field */
	private String ordemRealizacao;

	/** nullable persistent field */
	private String indicadorAcrescimoImpontualidade;

	/** persistent field */
	private String idCobrancaAcaoPredecessora;

	/** persistent field */
	private String idLigacaoEsgotoSituacao;

	/** persistent field */
	private String idDocumentoTipo;
	
	/** persistent field */
	private String idCobrancaSituacao;

	/** persistent field */
	private String idLigacaoAguaSituacao;

	/** persistent field */
	private String idServicoTipo;

	/** persistent field */
	private String idCobrancaCriterio;

	/** persistent field */
	private String indicadorCronograma;

	/** persistent field */
	private String indicadorBoletim;

	/** persistent field */
	private String indicadorDebito;

	/** persistent field */
	private String numeroDiasVencimento;

	/** persistent field */
	private String descricaoCobrancaCriterio;

	/** persistent field */
	private String descricaoServicoTipo;

	/** persistent field */
	private Usuario usuarioLogado;

	private String acaoCobrancaEfeito;

	private String idPrimeiraResolucaoDiretoria;

	private String idSegundaResolucaoDiretoria;

	private String idTerceiraResolucaoDiretoria;

	private String indicadorConsideraCreditoRealizar;

	private String indicadorConsideraGuiaPagamento;

	private String negativador;

	private String indicadorCPFCNPJ;

	private String indicadorCEP;

	private String indicadorEmpresaObrigatoria;

	/** full constructor */
	public CobrancaAcaoHelper(String descricaoCobrancaAcao, String indicadorObrigatoriedade, String indicadorRepeticao,
								String indicadorSuspensaoAbastecimento, String numeroDiasValidade, String qtdDiasRealizacao,
								String numeroDiasMinimoAcaoPrecedente, String indicadorUso, String indicadorCobrancaDebACobrar,
								String indicadorGeracaoTaxa, String ordemRealizacao, String indicadorAcrescimoImpontualidade,
								String idCobrancaAcaoPredecessora, String idLigacaoEsgotoSituacao, String idDocumentoTipo,
								String idLigacaoAguaSituacao, String idServicoTipo, String idCobrancaCriterio, String indicadorCronograma,
								String indicadorBoletim, String indicadorDebito, String numeroDiasVencimento,
								String descricaoCobrancaCriterio, String descricaoServicoTipo, String acaoCobrancaEfeito,
								Usuario usuarioLogado, String idPrimeiraResolucaoDiretoria, String idSegundaResolucaoDiretoria,
								String idTerceiraResolucaoDiretoria, String indicadorConsideraCreditoRealizar,
								String indicadorConsideraGuiaPagamento, String negativador, String indicadorCPFNCPJ, String indicadorCEP,
								String indicadorEmpresaObrigatoria, String idCobrancaSituacao) {

		this.descricaoCobrancaAcao = descricaoCobrancaAcao;
		this.indicadorObrigatoriedade = indicadorObrigatoriedade;
		this.indicadorRepeticao = indicadorRepeticao;
		this.indicadorSuspensaoAbastecimento = indicadorSuspensaoAbastecimento;
		this.numeroDiasValidade = numeroDiasValidade;
		this.qtdDiasRealizacao = qtdDiasRealizacao;
		this.numeroDiasMinimoAcaoPrecedente = numeroDiasMinimoAcaoPrecedente;
		this.indicadorUso = indicadorUso;
		this.indicadorCobrancaDebACobrar = indicadorCobrancaDebACobrar;
		this.indicadorGeracaoTaxa = indicadorGeracaoTaxa;
		this.ordemRealizacao = ordemRealizacao;
		this.indicadorAcrescimoImpontualidade = indicadorAcrescimoImpontualidade;
		this.idCobrancaAcaoPredecessora = idCobrancaAcaoPredecessora;
		this.idLigacaoEsgotoSituacao = idLigacaoEsgotoSituacao;
		this.idDocumentoTipo = idDocumentoTipo;
		this.idLigacaoAguaSituacao = idLigacaoAguaSituacao;
		this.idServicoTipo = idServicoTipo;
		this.idCobrancaCriterio = idCobrancaCriterio;
		this.indicadorCronograma = indicadorCronograma;
		this.indicadorBoletim = indicadorBoletim;
		this.indicadorDebito = indicadorDebito;
		this.numeroDiasVencimento = numeroDiasVencimento;
		this.descricaoCobrancaCriterio = descricaoCobrancaCriterio;
		this.descricaoServicoTipo = descricaoServicoTipo;
		this.acaoCobrancaEfeito = acaoCobrancaEfeito;
		this.usuarioLogado = usuarioLogado;
		this.idPrimeiraResolucaoDiretoria = idPrimeiraResolucaoDiretoria;
		this.idSegundaResolucaoDiretoria = idSegundaResolucaoDiretoria;
		this.idTerceiraResolucaoDiretoria = idTerceiraResolucaoDiretoria;
		this.indicadorConsideraCreditoRealizar = indicadorConsideraCreditoRealizar;
		this.indicadorConsideraGuiaPagamento = indicadorConsideraGuiaPagamento;
		this.negativador = negativador;
		this.indicadorCPFCNPJ = indicadorCPFNCPJ;
		this.indicadorCEP = indicadorCEP;
		this.indicadorEmpresaObrigatoria = indicadorEmpresaObrigatoria;
		this.idCobrancaSituacao = idCobrancaSituacao;

	}

	public String getAcaoCobrancaEfeito(){

		return acaoCobrancaEfeito;
	}

	public void setAcaoCobrancaEfeito(String acaoCobrancaEfeito){

		this.acaoCobrancaEfeito = acaoCobrancaEfeito;
	}

	/**
	 * @return Retorna o campo descricaoCobrancaAcao.
	 */
	public String getDescricaoCobrancaAcao(){

		return descricaoCobrancaAcao;
	}

	/**
	 * @param descricaoCobrancaAcao
	 *            O descricaoCobrancaAcao a ser setado.
	 */
	public void setDescricaoCobrancaAcao(String descricaoCobrancaAcao){

		this.descricaoCobrancaAcao = descricaoCobrancaAcao;
	}

	/**
	 * @return Retorna o campo descricaoCobrancaCriterio.
	 */
	public String getDescricaoCobrancaCriterio(){

		return descricaoCobrancaCriterio;
	}

	/**
	 * @param descricaoCobrancaCriterio
	 *            O descricaoCobrancaCriterio a ser setado.
	 */
	public void setDescricaoCobrancaCriterio(String descricaoCobrancaCriterio){

		this.descricaoCobrancaCriterio = descricaoCobrancaCriterio;
	}

	/**
	 * @return Retorna o campo descricaoServicoTipo.
	 */
	public String getDescricaoServicoTipo(){

		return descricaoServicoTipo;
	}

	/**
	 * @param descricaoServicoTipo
	 *            O descricaoServicoTipo a ser setado.
	 */
	public void setDescricaoServicoTipo(String descricaoServicoTipo){

		this.descricaoServicoTipo = descricaoServicoTipo;
	}

	/**
	 * @return Retorna o campo idCobrancaAcaoPredecessora.
	 */
	public String getIdCobrancaAcaoPredecessora(){

		return idCobrancaAcaoPredecessora;
	}

	/**
	 * @param idCobrancaAcaoPredecessora
	 *            O idCobrancaAcaoPredecessora a ser setado.
	 */
	public void setIdCobrancaAcaoPredecessora(String idCobrancaAcaoPredecessora){

		this.idCobrancaAcaoPredecessora = idCobrancaAcaoPredecessora;
	}

	/**
	 * @return Retorna o campo idCobrancaCriterio.
	 */
	public String getIdCobrancaCriterio(){

		return idCobrancaCriterio;
	}

	/**
	 * @param idCobrancaCriterio
	 *            O idCobrancaCriterio a ser setado.
	 */
	public void setIdCobrancaCriterio(String idCobrancaCriterio){

		this.idCobrancaCriterio = idCobrancaCriterio;
	}

	/**
	 * @return Retorna o campo idDocumentoTipo.
	 */
	public String getIdDocumentoTipo(){

		return idDocumentoTipo;
	}

	/**
	 * @param idDocumentoTipo
	 *            O idDocumentoTipo a ser setado.
	 */
	public void setIdDocumentoTipo(String idDocumentoTipo){

		this.idDocumentoTipo = idDocumentoTipo;
	}

	public String getIdCobrancaSituacao(){

		return idCobrancaSituacao;
	}

	public void setIdCobrancaSituacao(String idCobrancaSituacao){

		this.idCobrancaSituacao = idCobrancaSituacao;
	}

	/**
	 * @return Retorna o campo idLigacaoAguaSituacao.
	 */
	public String getIdLigacaoAguaSituacao(){

		return idLigacaoAguaSituacao;
	}

	/**
	 * @param idLigacaoAguaSituacao
	 *            O idLigacaoAguaSituacao a ser setado.
	 */
	public void setIdLigacaoAguaSituacao(String idLigacaoAguaSituacao){

		this.idLigacaoAguaSituacao = idLigacaoAguaSituacao;
	}

	/**
	 * @return Retorna o campo idLigacaoEsgotoSituacao.
	 */
	public String getIdLigacaoEsgotoSituacao(){

		return idLigacaoEsgotoSituacao;
	}

	/**
	 * @param idLigacaoEsgotoSituacao
	 *            O idLigacaoEsgotoSituacao a ser setado.
	 */
	public void setIdLigacaoEsgotoSituacao(String idLigacaoEsgotoSituacao){

		this.idLigacaoEsgotoSituacao = idLigacaoEsgotoSituacao;
	}

	/**
	 * @return Retorna o campo idServicoTipo.
	 */
	public String getIdServicoTipo(){

		return idServicoTipo;
	}

	/**
	 * @param idServicoTipo
	 *            O idServicoTipo a ser setado.
	 */
	public void setIdServicoTipo(String idServicoTipo){

		this.idServicoTipo = idServicoTipo;
	}

	/**
	 * @return Retorna o campo indicadorAcrescimoImpontualidade.
	 */
	public String getIndicadorAcrescimoImpontualidade(){

		return indicadorAcrescimoImpontualidade;
	}

	/**
	 * @param indicadorAcrescimoImpontualidade
	 *            O indicadorAcrescimoImpontualidade a ser setado.
	 */
	public void setIndicadorAcrescimoImpontualidade(String indicadorAcrescimoImpontualidade){

		this.indicadorAcrescimoImpontualidade = indicadorAcrescimoImpontualidade;
	}

	/**
	 * @return Retorna o campo indicadorBoletim.
	 */
	public String getIndicadorBoletim(){

		return indicadorBoletim;
	}

	/**
	 * @param indicadorBoletim
	 *            O indicadorBoletim a ser setado.
	 */
	public void setIndicadorBoletim(String indicadorBoletim){

		this.indicadorBoletim = indicadorBoletim;
	}

	/**
	 * @return Retorna o campo indicadorCobrancaDebACobrar.
	 */
	public String getIndicadorCobrancaDebACobrar(){

		return indicadorCobrancaDebACobrar;
	}

	/**
	 * @param indicadorCobrancaDebACobrar
	 *            O indicadorCobrancaDebACobrar a ser setado.
	 */
	public void setIndicadorCobrancaDebACobrar(String indicadorCobrancaDebACobrar){

		this.indicadorCobrancaDebACobrar = indicadorCobrancaDebACobrar;
	}

	/**
	 * @return Retorna o campo indicadorCronograma.
	 */
	public String getIndicadorCronograma(){

		return indicadorCronograma;
	}

	/**
	 * @param indicadorCronograma
	 *            O indicadorCronograma a ser setado.
	 */
	public void setIndicadorCronograma(String indicadorCronograma){

		this.indicadorCronograma = indicadorCronograma;
	}

	/**
	 * @return Retorna o campo indicadorDebito.
	 */
	public String getIndicadorDebito(){

		return indicadorDebito;
	}

	/**
	 * @param indicadorDebito
	 *            O indicadorDebito a ser setado.
	 */
	public void setIndicadorDebito(String indicadorDebito){

		this.indicadorDebito = indicadorDebito;
	}

	/**
	 * @return Retorna o campo indicadorGeracaoTaxa.
	 */
	public String getIndicadorGeracaoTaxa(){

		return indicadorGeracaoTaxa;
	}

	/**
	 * @param indicadorGeracaoTaxa
	 *            O indicadorGeracaoTaxa a ser setado.
	 */
	public void setIndicadorGeracaoTaxa(String indicadorGeracaoTaxa){

		this.indicadorGeracaoTaxa = indicadorGeracaoTaxa;
	}

	/**
	 * @return Retorna o campo indicadorObrigatoriedade.
	 */
	public String getIndicadorObrigatoriedade(){

		return indicadorObrigatoriedade;
	}

	/**
	 * @param indicadorObrigatoriedade
	 *            O indicadorObrigatoriedade a ser setado.
	 */
	public void setIndicadorObrigatoriedade(String indicadorObrigatoriedade){

		this.indicadorObrigatoriedade = indicadorObrigatoriedade;
	}

	/**
	 * @return Retorna o campo indicadorRepeticao.
	 */
	public String getIndicadorRepeticao(){

		return indicadorRepeticao;
	}

	/**
	 * @param indicadorRepeticao
	 *            O indicadorRepeticao a ser setado.
	 */
	public void setIndicadorRepeticao(String indicadorRepeticao){

		this.indicadorRepeticao = indicadorRepeticao;
	}

	/**
	 * @return Retorna o campo indicadorSuspensaoAbastecimento.
	 */
	public String getIndicadorSuspensaoAbastecimento(){

		return indicadorSuspensaoAbastecimento;
	}

	/**
	 * @param indicadorSuspensaoAbastecimento
	 *            O indicadorSuspensaoAbastecimento a ser setado.
	 */
	public void setIndicadorSuspensaoAbastecimento(String indicadorSuspensaoAbastecimento){

		this.indicadorSuspensaoAbastecimento = indicadorSuspensaoAbastecimento;
	}

	/**
	 * @return Retorna o campo indicadorUso.
	 */
	public String getIndicadorUso(){

		return indicadorUso;
	}

	/**
	 * @param indicadorUso
	 *            O indicadorUso a ser setado.
	 */
	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	/**
	 * @return Retorna o campo numeroDiasMinimoAcaoPrecedente.
	 */
	public String getNumeroDiasMinimoAcaoPrecedente(){

		return numeroDiasMinimoAcaoPrecedente;
	}

	/**
	 * @param numeroDiasMinimoAcaoPrecedente
	 *            O numeroDiasMinimoAcaoPrecedente a ser setado.
	 */
	public void setNumeroDiasMinimoAcaoPrecedente(String numeroDiasMinimoAcaoPrecedente){

		this.numeroDiasMinimoAcaoPrecedente = numeroDiasMinimoAcaoPrecedente;
	}

	/**
	 * @return Retorna o campo numeroDiasValidade.
	 */
	public String getNumeroDiasValidade(){

		return numeroDiasValidade;
	}

	/**
	 * @param numeroDiasValidade
	 *            O numeroDiasValidade a ser setado.
	 */
	public void setNumeroDiasValidade(String numeroDiasValidade){

		this.numeroDiasValidade = numeroDiasValidade;
	}

	/**
	 * @return Retorna o campo numeroDiasVencimento.
	 */
	public String getNumeroDiasVencimento(){

		return numeroDiasVencimento;
	}

	/**
	 * @param numeroDiasVencimento
	 *            O numeroDiasVencimento a ser setado.
	 */
	public void setNumeroDiasVencimento(String numeroDiasVencimento){

		this.numeroDiasVencimento = numeroDiasVencimento;
	}

	/**
	 * @return Retorna o campo ordemRealizacao.
	 */
	public String getOrdemRealizacao(){

		return ordemRealizacao;
	}

	/**
	 * @param ordemRealizacao
	 *            O ordemRealizacao a ser setado.
	 */
	public void setOrdemRealizacao(String ordemRealizacao){

		this.ordemRealizacao = ordemRealizacao;
	}

	/**
	 * @return Retorna o campo ultimaAlteracao.
	 */
	public String getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	/**
	 * @param ultimaAlteracao
	 *            O ultimaAlteracao a ser setado.
	 */
	public void setUltimaAlteracao(String ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	/**
	 * @return Retorna o campo usuarioLogado.
	 */
	public Usuario getUsuarioLogado(){

		return usuarioLogado;
	}

	/**
	 * @param usuarioLogado
	 *            O usuarioLogado a ser setado.
	 */
	public void setUsuarioLogado(Usuario usuarioLogado){

		this.usuarioLogado = usuarioLogado;
	}

	public String getIdPrimeiraResolucaoDiretoria(){

		return idPrimeiraResolucaoDiretoria;
	}

	public void setIdPrimeiraResolucaoDiretoria(String idPrimeiraResolucaoDiretoria){

		this.idPrimeiraResolucaoDiretoria = idPrimeiraResolucaoDiretoria;
	}

	public String getIdSegundaResolucaoDiretoria(){

		return idSegundaResolucaoDiretoria;
	}

	public void setIdSegundaResolucaoDiretoria(String idSegundaResolucaoDiretoria){

		this.idSegundaResolucaoDiretoria = idSegundaResolucaoDiretoria;
	}

	public String getIdTerceiraResolucaoDiretoria(){

		return idTerceiraResolucaoDiretoria;
	}

	public void setIdTerceiraResolucaoDiretoria(String idTerceiraResolucaoDiretoria){

		this.idTerceiraResolucaoDiretoria = idTerceiraResolucaoDiretoria;
	}

	public String getIndicadorConsideraCreditoRealizar(){

		return indicadorConsideraCreditoRealizar;
	}

	public void setIndicadorConsideraCreditoRealizar(String indicadorConsideraCreditoRealizar){

		this.indicadorConsideraCreditoRealizar = indicadorConsideraCreditoRealizar;
	}

	public String getIndicadorConsideraGuiaPagamento(){

		return indicadorConsideraGuiaPagamento;
	}

	public void setIndicadorConsideraGuiaPagamento(String indicadorConsideraGuiaPagamento){

		this.indicadorConsideraGuiaPagamento = indicadorConsideraGuiaPagamento;
	}

	public void setNegativador(String negativador){

		this.negativador = negativador;
	}

	public String getNegativador(){

		return negativador;
	}

	public void setIndicadorCPFCNPJ(String indicadorCPFCNPJ){

		this.indicadorCPFCNPJ = indicadorCPFCNPJ;
	}

	public String getIndicadorCPFCNPJ(){

		return indicadorCPFCNPJ;
	}

	public void setIndicadorCEP(String indicadorCEP){

		this.indicadorCEP = indicadorCEP;
	}

	public String getIndicadorCEP(){

		return indicadorCEP;
	}

	public String getQtdDiasRealizacao(){

		return qtdDiasRealizacao;
	}

	public void setQtdDiasRelizacao(String qtdDiasRealizacao){

		this.qtdDiasRealizacao = qtdDiasRealizacao;
	}

	public String getIndicadorEmpresaObrigatoria(){

		return indicadorEmpresaObrigatoria;
	}

	public void setIndicadorEmpresaObrigatoria(String indicadorEmpresaObrigatoria){

		this.indicadorEmpresaObrigatoria = indicadorEmpresaObrigatoria;
	}

}