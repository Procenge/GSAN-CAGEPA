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

package gcom.cobranca;

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
@ControleAlteracao()
public class CobrancaAcao
				extends ObjetoTransacao {

	public static final int ATRIBUTOS_COBRANCA_ACAO_INSERIR = 1010;

	public static final int ATRIBUTOS_COBRANCA_ACAO_REMOVER = 1040;

	public static final int ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR = 1043;

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private String descricaoCobrancaAcao;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private Short indicadorObrigatoriedade;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private Short indicadorRepeticao;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private Short indicadorSuspensaoAbastecimento;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private Short numeroDiasValidade;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private Short numeroDiasMinimoAcaoPrecedente;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private Short indicadorUso;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private Short indicadorCPFCNPJ;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private Short indicadorCEP;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private Short indicadorCobrancaDebACobrar;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private Short indicadorGeracaoTaxa;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private Short ordemRealizacao;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private Short indicadorAcrescimoImpontualidade;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private gcom.cobranca.CobrancaAcao cobrancaAcaoPredecessora;

	/** persistent field */
	@ControleAlteracao(value = FiltroCobrancaAcao.LIGACAO_ESGOTO_SITUACAO, funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private LigacaoEsgotoSituacao ligacaoEsgotoSituacao;

	/** persistent field */
	@ControleAlteracao(value = FiltroCobrancaAcao.DOCUMENTO_TIPO, funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private gcom.cobranca.DocumentoTipo documentoTipo;

	/** persistent field */
	@ControleAlteracao(value = FiltroCobrancaAcao.NEGATIVADOR, funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private Negativador negativador;

	/** persistent field */
	@ControleAlteracao(value = FiltroCobrancaAcao.LIGACAO_AGUA_SITUACAO, funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private LigacaoAguaSituacao ligacaoAguaSituacao;

	/** persistent field */
	@ControleAlteracao(value = FiltroCobrancaAcao.SERVICO_TIPO, funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private ServicoTipo servicoTipo;

	/** persistent field */
	@ControleAlteracao(value = FiltroCobrancaAcao.COBRANCA_CRITERIO, funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private CobrancaCriterio cobrancaCriterio;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private Short indicadorCronograma;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private Short indicadorBoletim;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private Short indicadorDebito;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private Integer numeroDiasVencimento;

	/** persisten field */
	private CobrancaEstagio cobrancaEstagio;

	/** persisten field */
	@ControleAlteracao(value = FiltroCobrancaAcao.ACAO_COBRANCA_EFEITO, funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private AcaoCobrancaEfeito acaoCobrancaEfeito;

	/** persisten field */
	@ControleAlteracao(value = FiltroCobrancaAcao.PRIMEIRA_RESOLUCAO_DIRETORIA, funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private ResolucaoDiretoria primeiraResolucaoDiretoria;

	/** persisten field */
	@ControleAlteracao(value = FiltroCobrancaAcao.SEGUNDA_RESOLUCAO_DIRETORIA, funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private ResolucaoDiretoria segundaResolucaoDiretoria;

	/** persisten field */
	@ControleAlteracao(value = FiltroCobrancaAcao.TERCEIRA_RESOLUCAO_DIRETORIA, funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private ResolucaoDiretoria terceiraResolucaoDiretoria;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private Short indicadorConsideraCreditoRealizar;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private Short indicadorConsideraGuiaPagamento;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private Short indicadorEntregaDocumento;

	/** persistent field */
	@ControleAlteracao(value = FiltroCobrancaAcao.QTD_DIAS_REALIZACAO, funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private Short qtdDiasRealizacao;

	/** persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_COBRANCA_ACAO_INSERIR, ATRIBUTOS_COBRANCA_ACAO_REMOVER, ATRIBUTOS_COBRANCA_ACAO_ATUALIZAR})
	private CobrancaSituacao cobrancaSituacao;

	private String codigoConstante;

	private Short indicadorEmpresaObrigatoria;

	public AcaoCobrancaEfeito getAcaoCobrancaEfeito(){

		return acaoCobrancaEfeito;
	}

	public void setAcaoCobrancaEfeito(AcaoCobrancaEfeito acaoCobrancaEfeito){

		this.acaoCobrancaEfeito = acaoCobrancaEfeito;
	}

	public final static Integer AVISO_CORTE = 1;

	public final static Integer CORTE_ADMINISTRATIVO = 2;

	public final static Integer CORTE_FISICO = 3;

	public final static Integer SUPRESSAO_PARCIAL = 4;

	public final static Integer SUPRESSAO_TOTAL = 5;

	public final static Integer AVISO_TAMPONAMENTO = 6;

	public final static Integer TAMPONAMENTO_ESGOTO = 7;

	public final static Integer FISCALIZACAO_SUPRIMIDO = 8;

	public final static Integer FISCALIZACAO_CORTADO = 9;

	public final static Integer CARTA_COBRANCA_SUPRIMIDO = 10;

	public final static Integer CARTA_COBRANCA_CORTADO = 11;

	public final static Integer CARTA_TARIFA_SOCIAL_LIGADO = 12;

	public final static Integer CARTA_TARIFA_SOCIAL_CORTADO = 13;

	public final static Integer CARTA_COBRANCA_LIGADO = 14;

	public final static Integer CARTA_COBRANCA_PARCELAMENTO = 15;

	public final static Integer FISCALIZACAO_FACTIVEL = 16;

	public final static Integer FISCALIZACAO_POTENCIAL = 17;

	public final static Integer FISCALIZACAO_LIGADO = 18;

	public final static Integer FISCALIZACAO_LIGADO_A_REVELIA = 20;

	public final static Integer AVISO_CORTE_A_REVELIA = 21;

	public final static Integer CORTE_ADMINISTRATIVO_LIGADO_A_REVELIA = 22;

	public final static Integer CORTE_FISICO_LIGADO_A_REVELIA = 23;

	public final static Integer CARTA_TARIFA_SOCIAL_LIGADO_A_REVELIA = 24;

	public final static Integer CARTA_COBRANCA_LIGADO_A_REVELIA = 25;

	public final static Integer FISCALIZACAO_TOTAL = 26;

	public final static Integer CORTE_FISICO_PUBLICO = 27;

	public final static Integer RECORTE = 29;

	public final static Integer AVISO_DEBITO = 30;

	public final static Integer TELECOBRANCA = 31;

	public final static Integer COBRANCA_JURIDICA = 32;

	public final static Integer NEGATIVACAO_SPC_SP = 33;

	public final static Integer NEGATIVACAO_SERASA = 34;

	public final static Integer NEGATIVACAO_SPC_BRASIL = 35;

	public final static Integer CARTA_COM_OPCOES_DE_PAGAMENTO = 61;

	// ---- Indicadores.

	public final static Short INDICADOR_CRONOGRAMA_ATIVO = 1;

	public final static Short INDICADOR_BOLETIM_SIM = 1;

	public final static Short INDICADOR_BOLETIM_NAO = 2;

	public final static Short INDICADOR_DEBITO_SIM = 1;

	public final static Short INDICADOR_DEBITO_NAO = 2;

	public final static Short INDICADOR_SIM = 1;

	public final static Short INDICADOR_NAO = 2;

	public final static Integer COBRANCA_BANCARIA = 36;

	public final static Integer COBRANCA_ADMINISTRATIVA = 37;

	/** full constructor */
	public CobrancaAcao(String descricaoCobrancaAcao, Short indicadorObrigatoriedade, Short indicadorRepeticao,
						Short indicadorSuspensaoAbastecimento, Short numeroDiasValidade, Short qtdDiasRealizacao,
						Short numeroDiasMinimoAcaoPrecedente, Short indicadorUso, Date ultimaAlteracao, Short indicadorCobrancaDebACobrar,
						Short indicadorGeracaoTaxa, gcom.cobranca.CobrancaAcao cobrancaAcaoPredecessora,
						LigacaoEsgotoSituacao ligacaoEsgotoSituacao, gcom.cobranca.DocumentoTipo documentoTipo,
						LigacaoAguaSituacao ligacaoAguaSituacao, ServicoTipo servicoTipo) {

		this.descricaoCobrancaAcao = descricaoCobrancaAcao;
		this.indicadorObrigatoriedade = indicadorObrigatoriedade;
		this.indicadorRepeticao = indicadorRepeticao;
		this.indicadorSuspensaoAbastecimento = indicadorSuspensaoAbastecimento;
		this.numeroDiasValidade = numeroDiasValidade;
		this.qtdDiasRealizacao = qtdDiasRealizacao;
		this.numeroDiasMinimoAcaoPrecedente = numeroDiasMinimoAcaoPrecedente;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.indicadorCobrancaDebACobrar = indicadorCobrancaDebACobrar;
		this.indicadorGeracaoTaxa = indicadorGeracaoTaxa;
		this.cobrancaAcaoPredecessora = cobrancaAcaoPredecessora;
		this.ligacaoEsgotoSituacao = ligacaoEsgotoSituacao;
		this.documentoTipo = documentoTipo;
		this.ligacaoAguaSituacao = ligacaoAguaSituacao;
		this.servicoTipo = servicoTipo;
	}

	/**
	 * full constructor
	 * 
	 * @param cobrancaEstagio
	 */
	public CobrancaAcao(String descricaoCobrancaAcao, Short indicadorObrigatoriedade, Short indicadorRepeticao,
						Short indicadorSuspensaoAbastecimento, Short numeroDiasValidade, Short qtdDiasRealizacao,
						Short numeroDiasMinimoAcaoPrecedente, Short indicadorUso, Date ultimaAlteracao, Short indicadorCobrancaDebACobrar,
						Short indicadorGeracaoTaxa, gcom.cobranca.CobrancaAcao cobrancaAcao, LigacaoEsgotoSituacao ligacaoEsgotoSituacao,
						gcom.cobranca.DocumentoTipo documentoTipo, LigacaoAguaSituacao ligacaoAguaSituacao, ServicoTipo servicoTipo,
						CobrancaCriterio cobrancaCriterio, CobrancaEstagio cobrancaEstagio, CobrancaSituacao cobrancaSituacao) {

		this.descricaoCobrancaAcao = descricaoCobrancaAcao;
		this.indicadorObrigatoriedade = indicadorObrigatoriedade;
		this.indicadorRepeticao = indicadorRepeticao;
		this.indicadorSuspensaoAbastecimento = indicadorSuspensaoAbastecimento;
		this.numeroDiasValidade = numeroDiasValidade;
		this.qtdDiasRealizacao = qtdDiasRealizacao;
		this.numeroDiasMinimoAcaoPrecedente = numeroDiasMinimoAcaoPrecedente;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.indicadorCobrancaDebACobrar = indicadorCobrancaDebACobrar;
		this.indicadorGeracaoTaxa = indicadorGeracaoTaxa;
		this.cobrancaAcaoPredecessora = cobrancaAcao;
		this.ligacaoEsgotoSituacao = ligacaoEsgotoSituacao;
		this.documentoTipo = documentoTipo;
		this.ligacaoAguaSituacao = ligacaoAguaSituacao;
		this.servicoTipo = servicoTipo;
		this.cobrancaCriterio = cobrancaCriterio;
		this.cobrancaEstagio = cobrancaEstagio;
		this.cobrancaSituacao = cobrancaSituacao;
	}

	/**
	 * @return Retorna o campo cobrancaCriterio.
	 */
	public CobrancaCriterio getCobrancaCriterio(){

		return cobrancaCriterio;
	}

	/**
	 * @param cobrancaCriterio
	 *            O cobrancaCriterio a ser setado.
	 */
	public void setCobrancaCriterio(CobrancaCriterio cobrancaCriterio){

		this.cobrancaCriterio = cobrancaCriterio;
	}

	/**
	 * default constructor
	 * 
	 * @param id
	 */
	public CobrancaAcao(Integer id) {

		this.id = id;
	}

	/** minimal constructor */
	public CobrancaAcao(gcom.cobranca.CobrancaAcao cobrancaAcaoPredecessora, LigacaoEsgotoSituacao ligacaoEsgotoSituacao,
						gcom.cobranca.DocumentoTipo documentoTipo, LigacaoAguaSituacao ligacaoAguaSituacao, ServicoTipo servicoTipo) {

		this.cobrancaAcaoPredecessora = cobrancaAcaoPredecessora;
		this.ligacaoEsgotoSituacao = ligacaoEsgotoSituacao;
		this.documentoTipo = documentoTipo;
		this.ligacaoAguaSituacao = ligacaoAguaSituacao;
		this.servicoTipo = servicoTipo;
	}

	public CobrancaAcao() {

	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getDescricaoCobrancaAcao(){

		return this.descricaoCobrancaAcao;
	}

	public void setDescricaoCobrancaAcao(String descricaoCobrancaAcao){

		this.descricaoCobrancaAcao = descricaoCobrancaAcao;
	}

	public Short getIndicadorObrigatoriedade(){

		return this.indicadorObrigatoriedade;
	}

	public void setIndicadorObrigatoriedade(Short indicadorObrigatoriedade){

		this.indicadorObrigatoriedade = indicadorObrigatoriedade;
	}

	public Short getIndicadorRepeticao(){

		return this.indicadorRepeticao;
	}

	public void setIndicadorRepeticao(Short indicadorRepeticao){

		this.indicadorRepeticao = indicadorRepeticao;
	}

	public Short getIndicadorSuspensaoAbastecimento(){

		return this.indicadorSuspensaoAbastecimento;
	}

	public void setIndicadorSuspensaoAbastecimento(Short indicadorSuspensaoAbastecimento){

		this.indicadorSuspensaoAbastecimento = indicadorSuspensaoAbastecimento;
	}

	public Short getNumeroDiasValidade(){

		return this.numeroDiasValidade;
	}

	public void setNumeroDiasValidade(Short numeroDiasValidade){

		this.numeroDiasValidade = numeroDiasValidade;
	}

	public Short getNumeroDiasMinimoAcaoPrecedente(){

		return this.numeroDiasMinimoAcaoPrecedente;
	}

	public void setNumeroDiasMinimoAcaoPrecedente(Short numeroDiasMinimoAcaoPrecedente){

		this.numeroDiasMinimoAcaoPrecedente = numeroDiasMinimoAcaoPrecedente;
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

	public Short getIndicadorCobrancaDebACobrar(){

		return this.indicadorCobrancaDebACobrar;
	}

	public void setIndicadorCobrancaDebACobrar(Short indicadorCobrancaDebACobrar){

		this.indicadorCobrancaDebACobrar = indicadorCobrancaDebACobrar;
	}

	public Short getIndicadorGeracaoTaxa(){

		return this.indicadorGeracaoTaxa;
	}

	public void setIndicadorGeracaoTaxa(Short indicadorGeracaoTaxa){

		this.indicadorGeracaoTaxa = indicadorGeracaoTaxa;
	}

	public gcom.cobranca.CobrancaAcao getCobrancaAcaoPredecessora(){

		return this.cobrancaAcaoPredecessora;
	}

	public void setCobrancaAcaoPredecessora(gcom.cobranca.CobrancaAcao cobrancaAcaoPredecessora){

		this.cobrancaAcaoPredecessora = cobrancaAcaoPredecessora;
	}

	public LigacaoEsgotoSituacao getLigacaoEsgotoSituacao(){

		return this.ligacaoEsgotoSituacao;
	}

	public void setLigacaoEsgotoSituacao(LigacaoEsgotoSituacao ligacaoEsgotoSituacao){

		this.ligacaoEsgotoSituacao = ligacaoEsgotoSituacao;
	}

	public gcom.cobranca.DocumentoTipo getDocumentoTipo(){

		return this.documentoTipo;
	}

	public void setDocumentoTipo(gcom.cobranca.DocumentoTipo documentoTipo){

		this.documentoTipo = documentoTipo;
	}

	public LigacaoAguaSituacao getLigacaoAguaSituacao(){

		return this.ligacaoAguaSituacao;
	}

	public void setLigacaoAguaSituacao(LigacaoAguaSituacao ligacaoAguaSituacao){

		this.ligacaoAguaSituacao = ligacaoAguaSituacao;
	}

	public ServicoTipo getServicoTipo(){

		return this.servicoTipo;
	}

	public void setServicoTipo(ServicoTipo servicoTipo){

		this.servicoTipo = servicoTipo;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public Short getOrdemRealizacao(){

		return ordemRealizacao;
	}

	public void setOrdemRealizacao(Short ordemRealizacao){

		this.ordemRealizacao = ordemRealizacao;
	}

	public Short getIndicadorAcrescimoImpontualidade(){

		return indicadorAcrescimoImpontualidade;
	}

	public void setIndicadorAcrescimoImpontualidade(Short indicadorAcrescimoImpontualidade){

		this.indicadorAcrescimoImpontualidade = indicadorAcrescimoImpontualidade;
	}

	public Short getIndicadorCronograma(){

		return indicadorCronograma;
	}

	public void setIndicadorCronograma(Short indicadorCronograma){

		this.indicadorCronograma = indicadorCronograma;
	}

	public Short getIndicadorBoletim(){

		return indicadorBoletim;
	}

	public void setIndicadorBoletim(Short indicadorBoletim){

		this.indicadorBoletim = indicadorBoletim;
	}

	public Short getIndicadorDebito(){

		return indicadorDebito;
	}

	public void setIndicadorDebito(Short indicadorDebito){

		this.indicadorDebito = indicadorDebito;
	}

	public Integer getNumeroDiasVencimento(){

		return numeroDiasVencimento;
	}

	public void setNumeroDiasVencimento(Integer numeroDiasVencimento){

		this.numeroDiasVencimento = numeroDiasVencimento;
	}

	/**
	 * @return the cobrancaEstagio
	 */
	public CobrancaEstagio getCobrancaEstagio(){

		return cobrancaEstagio;
	}

	/**
	 * @param cobrancaEstagio
	 *            the cobrancaEstagio to set
	 */
	public void setCobrancaEstagio(CobrancaEstagio cobrancaEstagio){

		this.cobrancaEstagio = cobrancaEstagio;
	}

	public ResolucaoDiretoria getPrimeiraResolucaoDiretoria(){

		return primeiraResolucaoDiretoria;
	}

	public void setPrimeiraResolucaoDiretoria(ResolucaoDiretoria primeiraResolucaoDiretoria){

		this.primeiraResolucaoDiretoria = primeiraResolucaoDiretoria;
	}

	public ResolucaoDiretoria getSegundaResolucaoDiretoria(){

		return segundaResolucaoDiretoria;
	}

	public void setSegundaResolucaoDiretoria(ResolucaoDiretoria segundaResolucaoDiretoria){

		this.segundaResolucaoDiretoria = segundaResolucaoDiretoria;
	}

	public ResolucaoDiretoria getTerceiraResolucaoDiretoria(){

		return terceiraResolucaoDiretoria;
	}

	public void setTerceiraResolucaoDiretoria(ResolucaoDiretoria terceiraResolucaoDiretoria){

		this.terceiraResolucaoDiretoria = terceiraResolucaoDiretoria;
	}

	public CobrancaSituacao getCobrancaSituacao(){

		return cobrancaSituacao;
	}

	public void setCobrancaSituacao(CobrancaSituacao cobrancaSituacao){

		this.cobrancaSituacao = cobrancaSituacao;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Filtro retornaFiltro(){

		FiltroCobrancaAcao filtroCobrancaAcao = new FiltroCobrancaAcao();

		filtroCobrancaAcao.adicionarParametro(new ParametroSimples(FiltroCobrancaAcao.ID, this.getId()));

		filtroCobrancaAcao.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaAcao.COBRANCA_CRITERIO);
		filtroCobrancaAcao.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaAcao.COBRANCA_ACAO_PRECEDENTE);
		filtroCobrancaAcao.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaAcao.ACAO_COBRANCA_EFEITO);
		filtroCobrancaAcao.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaAcao.LIGACAO_AGUA_SITUACAO);
		filtroCobrancaAcao.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaAcao.LIGACAO_ESGOTO_SITUACAO);
		filtroCobrancaAcao.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaAcao.DOCUMENTO_TIPO);
		filtroCobrancaAcao.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaAcao.SERVICO_TIPO);
		filtroCobrancaAcao.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaAcao.PRIMEIRA_RESOLUCAO_DIRETORIA);
		filtroCobrancaAcao.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaAcao.SEGUNDA_RESOLUCAO_DIRETORIA);
		filtroCobrancaAcao.adicionarCaminhoParaCarregamentoEntidade(FiltroCobrancaAcao.TERCEIRA_RESOLUCAO_DIRETORIA);

		return filtroCobrancaAcao;
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"descricaoCobrancaAcao"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Descricao"};
		return labels;
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		String retorno = null;

		if(this.getId() != null && this.getDescricaoCobrancaAcao() != null){
			retorno = this.getId() + " - " + this.getDescricaoCobrancaAcao();
		}else{
			retorno = null;
		}

		return retorno;
	}

	public Short getIndicadorConsideraCreditoRealizar(){

		return indicadorConsideraCreditoRealizar;
	}

	public void setIndicadorConsideraCreditoRealizar(Short indicadorConsideraCreditoRealizar){

		this.indicadorConsideraCreditoRealizar = indicadorConsideraCreditoRealizar;
	}

	public Short getIndicadorConsideraGuiaPagamento(){

		return indicadorConsideraGuiaPagamento;
	}

	public void setIndicadorConsideraGuiaPagamento(Short indicadorConsideraGuiaPagamento){

		this.indicadorConsideraGuiaPagamento = indicadorConsideraGuiaPagamento;
	}

	public Short getIndicadorEntregaDocumento(){

		return indicadorEntregaDocumento;
	}

	public void setIndicadorEntregaDocumento(Short indicadorEntregaDocumento){

		this.indicadorEntregaDocumento = indicadorEntregaDocumento;
	}

	public void setNegativador(Negativador negativador){

		this.negativador = negativador;
	}

	public Negativador getNegativador(){

		return negativador;
	}

	public void setIndicadorCPFCNPJ(Short indicadorCPFCNPJ){

		this.indicadorCPFCNPJ = indicadorCPFCNPJ;
	}

	public Short getIndicadorCPFCNPJ(){

		return indicadorCPFCNPJ;
	}

	public void setIndicadorCEP(Short indicadorCEP){

		this.indicadorCEP = indicadorCEP;
	}

	public Short getIndicadorCEP(){

		return indicadorCEP;
	}

	public Short getQtdDiasRealizacao(){

		return qtdDiasRealizacao;
	}

	public void setQtdDiasRealizacao(Short qtdDiasRealizacao){

		this.qtdDiasRealizacao = qtdDiasRealizacao;
	}

	public String getCodigoConstante(){

		return codigoConstante;
	}

	public void setCodigoConstante(String codigoConstante){

		this.codigoConstante = codigoConstante;
	}

	public Short getIndicadorEmpresaObrigatoria(){

		return indicadorEmpresaObrigatoria;
	}

	public void setIndicadorEmpresaObrigatoria(Short indicadorEmpresaObrigatoria){

		this.indicadorEmpresaObrigatoria = indicadorEmpresaObrigatoria;
	}

}
