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

package gcom.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ligacaoagua.CorteTipo;
import gcom.atendimentopublico.ligacaoagua.SupressaoTipo;
import gcom.atendimentopublico.registroatendimento.AtendimentoMotivoEncerramento;
import gcom.atendimentopublico.registroatendimento.AtendimentoRelacaoTipo;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.cobranca.CobrancaAcaoAtividadeComando;
import gcom.cobranca.CobrancaAcaoAtividadeCronograma;
import gcom.cobranca.CobrancaDocumento;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.Util;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

@ControleAlteracao()
public class OrdemServico
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	public final static Short SITUACAO_PENDENTE = Short.valueOf("1");

	public final static String SITUACAO_DESCRICAO_PENDENTE = "Pendente";

	public final static String SITUACAO_DESC_ABREV_PENDENTE = "Pen";

	public final static String SITUACAO_LETRA_IDENTIFICADORA_PENDENTE = "P";

	public final static Short SITUACAO_ENCERRADO = Short.valueOf("2");

	public final static String SITUACAO_DESCRICAO_ENCERRADO = "Encerrada";

	public final static String SITUACAO_DESCRICAO_ENCERRADO_NAO_EXECUTADA = "encerrada por�m n�o foi executada";

	public final static String SITUACAO_DESC_ABREV_ENCERRADO = "Enc";

	public final static String SITUACAO_LETRA_IDENTIFICADORA_ENCERRADO = "E";

	public final static Short SITUACAO_EXECUCAO_EM_ANDAMENTO = Short.valueOf("3");

	public final static String SITUACAO_DESCRICAO_EXECUCAO_EM_ANDAMENTO = "Execu��o em andamento";

	public final static String SITUACAO_DESC_ABREV_EXECUCAO_EM_ANDAMENTO = "Exec em And";

	public final static String SITUACAO_LETRA_IDENTIFICADORA_EXECUCAO_EM_ANDAMENTO = "A";

	public final static Short SITUACAO_AGUARDANDO_LIBERACAO = Short.valueOf("4");

	public final static String SITUACAO_DESCRICAO_AGUARDANDO_LIBERACAO = "Aguardando libera��o para execu��o";

	public final static String SITUACAO_DESC_ABREV_AGUARDANDO_LIBERACAO = "Aguardo Liber";

	public final static String SITUACAO_LETRA_IDENTIFICADORA_AGUARDANDO_LIBERACAO = "L";

	public final static Short PROGRAMADA = Short.valueOf("1");

	public final static Short NAO_PROGRAMADA = Short.valueOf("2");

	public static final int ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR = 260; // Operacao.OPERACAO_ORDEM_SERVICO_ATUALIZAR

	/** identifier field */
	private Integer id;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private short situacao;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private Date dataGeracao;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private Date dataEmissao;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private Date dataEncerramento;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private String descricaoParecerEncerramento;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private String observacao;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private BigDecimal areaPavimento;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private BigDecimal valorOriginal;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private BigDecimal valorAtual;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private Date ultimaAlteracao;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private short indicadorComercialAtualizado;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private BigDecimal percentualCobranca;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private AtendimentoMotivoEncerramento atendimentoMotivoEncerramento;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private gcom.atendimentopublico.ordemservico.ServicoNaoCobrancaMotivo servicoNaoCobrancaMotivo;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private RegistroAtendimento registroAtendimento;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private CobrancaDocumento cobrancaDocumento;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private gcom.atendimentopublico.ordemservico.FiscalizacaoColetiva fiscalizacaoColetiva;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private gcom.atendimentopublico.ordemservico.OrdemServico osReferencia;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private gcom.atendimentopublico.ordemservico.ServicoTipoPrioridade servicoTipoPrioridadeOriginal;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private gcom.atendimentopublico.ordemservico.ServicoTipoPrioridade servicoTipoPrioridadeAtual;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private gcom.atendimentopublico.ordemservico.OsReferidaRetornoTipo osReferidaRetornoTipo;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private gcom.atendimentopublico.ordemservico.ServicoTipo servicoTipo;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private gcom.atendimentopublico.ordemservico.ServicoTipo servicoTipoReferencia;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private short indicadorServicoDiagnosticado;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private Imovel imovel;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private Date dataFiscalizacaoSituacao;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private FiscalizacaoSituacao fiscalizacaoSituacao;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private Short codigoRetornoVistoria;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private Short codigoTipoRecebimento;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private Short codigoRetornoFiscalizacaoColetiva;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private String parecerFiscalizacaoColetiva;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private short indicadorProgramada;

	private Set<OrdemServicoUnidade> ordemServicoUnidades;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private CorteTipo corteTipo;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private SupressaoTipo supressaoTipo;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private BigDecimal dimensao1;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private BigDecimal dimensao2;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private BigDecimal dimensao3;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private DiametroRedeAgua diametroRedeAgua;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private DiametroRamalAgua diametroRamalAgua;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private DiametroCavaleteAgua diametroCavaleteAgua;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private DiametroRedeEsgoto diametroRedeEsgoto;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private DiametroRamalEsgoto diametroRamalEsgoto;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private MaterialRedeAgua materialRedeAgua;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private MaterialRamalAgua materialRamalAgua;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private MaterialCavaleteAgua materialCavaleteAgua;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private MaterialRedeEsgoto materialRedeEsgoto;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private MaterialRamalEsgoto materialRamalEsgoto;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private CausaVazamento causaVazamento;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private AgenteExterno agenteExterno;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private BigDecimal numeroProfundidade;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private BigDecimal numeroPressao;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private Empresa agente;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private OrdemServico ordemServicoVinculada;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private OrdemServico ordemServicoReparo;

	private OsSeletivaComando osSeletivaComando;

	private String situacaoDocumentoDebito;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private Date dataExecucao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private BigDecimal valorHorasTrabalhadas;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_ORDEM_SERVICO_ATUALIZAR})
	private BigDecimal valorMateriais;

	private Set tramites;

	/** full constructor */
	public OrdemServico(short situacao, Date dataGeracao, Date dataEmissao, Date dataEncerramento, String descricaoParecerEncerramento,
						String observacao, BigDecimal areaPavimento, BigDecimal valorOriginal, BigDecimal valorAtual, Date ultimaAlteracao,
						short indicadorComercialAtualizado, BigDecimal percentualCobranca,
						AtendimentoMotivoEncerramento atendimentoMotivoEncerramento,
						gcom.atendimentopublico.ordemservico.ServicoNaoCobrancaMotivo servicoNaoCobrancaMotivo,
						RegistroAtendimento registroAtendimento, CobrancaDocumento cobrancaDocumento,
						gcom.atendimentopublico.ordemservico.FiscalizacaoColetiva fiscalizacaoColetiva,
						gcom.atendimentopublico.ordemservico.OrdemServico osReferencia,
						gcom.atendimentopublico.ordemservico.ServicoTipoPrioridade servicoTipoPrioridadeOriginal,
						gcom.atendimentopublico.ordemservico.ServicoTipoPrioridade servicoTipoPrioridadeAtual,
						gcom.atendimentopublico.ordemservico.OsReferidaRetornoTipo osReferidaRetornoTipo,
						gcom.atendimentopublico.ordemservico.ServicoTipo servicoTipo,
						gcom.atendimentopublico.ordemservico.ServicoTipo servicoTipoReferencia, Short codigoRetornoFiscalizacaoColetiva,
						String parecerFiscalizacaoColetiva, OsSeletivaComando osSeletivaComando) {

		this.situacao = situacao;
		this.dataGeracao = dataGeracao;
		this.dataEmissao = dataEmissao;
		this.dataEncerramento = dataEncerramento;
		this.descricaoParecerEncerramento = descricaoParecerEncerramento;
		this.observacao = observacao;
		this.areaPavimento = areaPavimento;
		this.valorOriginal = valorOriginal;
		this.valorAtual = valorAtual;
		this.ultimaAlteracao = ultimaAlteracao;
		this.indicadorComercialAtualizado = indicadorComercialAtualizado;
		this.percentualCobranca = percentualCobranca;
		this.atendimentoMotivoEncerramento = atendimentoMotivoEncerramento;
		this.servicoNaoCobrancaMotivo = servicoNaoCobrancaMotivo;
		this.registroAtendimento = registroAtendimento;
		this.cobrancaDocumento = cobrancaDocumento;
		this.fiscalizacaoColetiva = fiscalizacaoColetiva;
		this.osReferencia = osReferencia;
		this.servicoTipoPrioridadeOriginal = servicoTipoPrioridadeOriginal;
		this.servicoTipoPrioridadeAtual = servicoTipoPrioridadeAtual;
		this.osReferidaRetornoTipo = osReferidaRetornoTipo;
		this.servicoTipo = servicoTipo;
		this.servicoTipoReferencia = servicoTipoReferencia;
		this.codigoRetornoFiscalizacaoColetiva = codigoRetornoFiscalizacaoColetiva;
		this.parecerFiscalizacaoColetiva = parecerFiscalizacaoColetiva;
		this.osSeletivaComando = osSeletivaComando;
	}

	/** default constructor */
	public OrdemServico() {

	}

	/** minimal constructor */
	public OrdemServico(short situacao, Date dataGeracao, Date ultimaAlteracao, short indicadorComercialAtualizado,
						AtendimentoMotivoEncerramento atendimentoMotivoEncerramento,
						gcom.atendimentopublico.ordemservico.ServicoNaoCobrancaMotivo servicoNaoCobrancaMotivo,
						RegistroAtendimento registroAtendimento, CobrancaDocumento cobrancaDocumento,
						gcom.atendimentopublico.ordemservico.FiscalizacaoColetiva fiscalizacaoColetiva,
						gcom.atendimentopublico.ordemservico.OrdemServico osReferencia,
						gcom.atendimentopublico.ordemservico.ServicoTipoPrioridade servicoTipoPrioridadeOriginal,
						gcom.atendimentopublico.ordemservico.ServicoTipoPrioridade servicoTipoPrioridadeAtual,
						gcom.atendimentopublico.ordemservico.OsReferidaRetornoTipo osReferidaRetornoTipo,
						gcom.atendimentopublico.ordemservico.ServicoTipo servicoTipo,
						gcom.atendimentopublico.ordemservico.ServicoTipo servicoTipoReferencia) {

		this.situacao = situacao;
		this.dataGeracao = dataGeracao;
		this.ultimaAlteracao = ultimaAlteracao;
		this.indicadorComercialAtualizado = indicadorComercialAtualizado;
		this.atendimentoMotivoEncerramento = atendimentoMotivoEncerramento;
		this.servicoNaoCobrancaMotivo = servicoNaoCobrancaMotivo;
		this.registroAtendimento = registroAtendimento;
		this.cobrancaDocumento = cobrancaDocumento;
		this.fiscalizacaoColetiva = fiscalizacaoColetiva;
		this.osReferencia = osReferencia;
		this.servicoTipoPrioridadeOriginal = servicoTipoPrioridadeOriginal;
		this.servicoTipoPrioridadeAtual = servicoTipoPrioridadeAtual;
		this.osReferidaRetornoTipo = osReferidaRetornoTipo;
		this.servicoTipo = servicoTipo;
		this.servicoTipoReferencia = servicoTipoReferencia;
	}

	public OrdemServico(Date ultimaAlteracao, RegistroAtendimento registroAtendimento,
						gcom.atendimentopublico.ordemservico.ServicoTipo servicoTipo, CobrancaDocumento cobrancaDocumento) {

		this.ultimaAlteracao = ultimaAlteracao;
		this.registroAtendimento = registroAtendimento;
		this.servicoTipo = servicoTipo;
		this.cobrancaDocumento = cobrancaDocumento;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public short getSituacao(){

		return this.situacao;
	}

	public void setSituacao(short situacao){

		this.situacao = situacao;
	}

	public Date getDataGeracao(){

		return this.dataGeracao;
	}

	public void setDataGeracao(Date dataGeracao){

		this.dataGeracao = dataGeracao;
	}

	public Date getDataEmissao(){

		return this.dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao){

		this.dataEmissao = dataEmissao;
	}

	public Date getDataEncerramento(){

		return this.dataEncerramento;
	}

	public void setDataEncerramento(Date dataEncerramento){

		this.dataEncerramento = dataEncerramento;
	}

	public String getDescricaoParecerEncerramento(){

		return this.descricaoParecerEncerramento;
	}

	public void setDescricaoParecerEncerramento(String descricaoParecerEncerramento){

		this.descricaoParecerEncerramento = descricaoParecerEncerramento;
	}

	public String getObservacao(){

		return this.observacao;
	}

	public void setObservacao(String observacao){

		this.observacao = observacao;
	}

	public BigDecimal getAreaPavimento(){

		return this.areaPavimento;
	}

	public void setAreaPavimento(BigDecimal areaPavimento){

		this.areaPavimento = areaPavimento;
	}

	public BigDecimal getValorOriginal(){

		return this.valorOriginal;
	}

	public void setValorOriginal(BigDecimal valorOriginal){

		this.valorOriginal = valorOriginal;
	}

	public BigDecimal getValorAtual(){

		return this.valorAtual;
	}

	public void setValorAtual(BigDecimal valorAtual){

		this.valorAtual = valorAtual;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public short getIndicadorComercialAtualizado(){

		return this.indicadorComercialAtualizado;
	}

	public void setIndicadorComercialAtualizado(short indicadorComercialAtualizado){

		this.indicadorComercialAtualizado = indicadorComercialAtualizado;
	}

	public BigDecimal getPercentualCobranca(){

		return this.percentualCobranca;
	}

	public void setPercentualCobranca(BigDecimal percentualCobranca){

		this.percentualCobranca = percentualCobranca;
	}

	public AtendimentoMotivoEncerramento getAtendimentoMotivoEncerramento(){

		return this.atendimentoMotivoEncerramento;
	}

	public void setAtendimentoMotivoEncerramento(AtendimentoMotivoEncerramento atendimentoMotivoEncerramento){

		this.atendimentoMotivoEncerramento = atendimentoMotivoEncerramento;
	}

	public gcom.atendimentopublico.ordemservico.ServicoNaoCobrancaMotivo getServicoNaoCobrancaMotivo(){

		return this.servicoNaoCobrancaMotivo;
	}

	public void setServicoNaoCobrancaMotivo(gcom.atendimentopublico.ordemservico.ServicoNaoCobrancaMotivo servicoNaoCobrancaMotivo){

		this.servicoNaoCobrancaMotivo = servicoNaoCobrancaMotivo;
	}

	public RegistroAtendimento getRegistroAtendimento(){

		return this.registroAtendimento;
	}

	public void setRegistroAtendimento(RegistroAtendimento registroAtendimento){

		this.registroAtendimento = registroAtendimento;
	}

	public CobrancaDocumento getCobrancaDocumento(){

		return this.cobrancaDocumento;
	}

	public void setCobrancaDocumento(CobrancaDocumento cobrancaDocumento){

		this.cobrancaDocumento = cobrancaDocumento;
	}

	public gcom.atendimentopublico.ordemservico.FiscalizacaoColetiva getFiscalizacaoColetiva(){

		return this.fiscalizacaoColetiva;
	}

	public void setFiscalizacaoColetiva(gcom.atendimentopublico.ordemservico.FiscalizacaoColetiva fiscalizacaoColetiva){

		this.fiscalizacaoColetiva = fiscalizacaoColetiva;
	}

	public gcom.atendimentopublico.ordemservico.OrdemServico getOsReferencia(){

		return this.osReferencia;
	}

	public void setOsReferencia(gcom.atendimentopublico.ordemservico.OrdemServico osReferencia){

		this.osReferencia = osReferencia;
	}

	public gcom.atendimentopublico.ordemservico.ServicoTipoPrioridade getServicoTipoPrioridadeOriginal(){

		return this.servicoTipoPrioridadeOriginal;
	}

	public void setServicoTipoPrioridadeOriginal(gcom.atendimentopublico.ordemservico.ServicoTipoPrioridade servicoTipoPrioridadeOriginal){

		this.servicoTipoPrioridadeOriginal = servicoTipoPrioridadeOriginal;
	}

	public gcom.atendimentopublico.ordemservico.ServicoTipoPrioridade getServicoTipoPrioridadeAtual(){

		return this.servicoTipoPrioridadeAtual;
	}

	public void setServicoTipoPrioridadeAtual(gcom.atendimentopublico.ordemservico.ServicoTipoPrioridade servicoTipoPrioridadeAtual){

		this.servicoTipoPrioridadeAtual = servicoTipoPrioridadeAtual;
	}

	public gcom.atendimentopublico.ordemservico.OsReferidaRetornoTipo getOsReferidaRetornoTipo(){

		return this.osReferidaRetornoTipo;
	}

	public void setOsReferidaRetornoTipo(gcom.atendimentopublico.ordemservico.OsReferidaRetornoTipo osReferidaRetornoTipo){

		this.osReferidaRetornoTipo = osReferidaRetornoTipo;
	}

	public gcom.atendimentopublico.ordemservico.ServicoTipo getServicoTipo(){

		return this.servicoTipo;
	}

	public void setServicoTipo(gcom.atendimentopublico.ordemservico.ServicoTipo servicoTipo){

		this.servicoTipo = servicoTipo;
	}

	public gcom.atendimentopublico.ordemservico.ServicoTipo getServicoTipoReferencia(){

		return this.servicoTipoReferencia;
	}

	public void setServicoTipoReferencia(gcom.atendimentopublico.ordemservico.ServicoTipo servicoTipoReferencia){

		this.servicoTipoReferencia = servicoTipoReferencia;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public String getDescricaoSituacao(){

		String descricao = "";
		short situacao = getSituacao();
		if(SITUACAO_PENDENTE == situacao){
			descricao = SITUACAO_DESCRICAO_PENDENTE;
		}else if(SITUACAO_ENCERRADO == situacao){
			descricao = SITUACAO_DESCRICAO_ENCERRADO;
		}else if(SITUACAO_EXECUCAO_EM_ANDAMENTO == situacao){
			descricao = SITUACAO_DESCRICAO_EXECUCAO_EM_ANDAMENTO;
		}else if(SITUACAO_AGUARDANDO_LIBERACAO == situacao){
			descricao = SITUACAO_DESCRICAO_AGUARDANDO_LIBERACAO;
		}
		return descricao;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Filtro retornaFiltro(){

		FiltroOrdemServico filtroOrdemServico = new FiltroOrdemServico();

		// filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade("dataEncerramento");
		filtroOrdemServico.adicionarCaminhoParaCarregamentoEntidade("registroAtendimento");

		filtroOrdemServico.adicionarParametro(new ParametroSimples(FiltroOrdemServico.ID, this.getId()));
		return filtroOrdemServico;
	}

	/**
	 * @return Retorna o campo dataFiscalizacaoSituacao.
	 */
	public Date getDataFiscalizacaoSituacao(){

		return dataFiscalizacaoSituacao;
	}

	/**
	 * @param dataFiscalizacaoSituacao
	 *            O dataFiscalizacaoSituacao a ser setado.
	 */
	public void setDataFiscalizacaoSituacao(Date dataFiscalizacaoSituacao){

		this.dataFiscalizacaoSituacao = dataFiscalizacaoSituacao;
	}

	/**
	 * @return Retorna o campo fiscalizacaoSituacao.
	 */
	public FiscalizacaoSituacao getFiscalizacaoSituacao(){

		return fiscalizacaoSituacao;
	}

	/**
	 * @param fiscalizacaoSituacao
	 *            O fiscalizacaoSituacao a ser setado.
	 */
	public void setFiscalizacaoSituacao(FiscalizacaoSituacao fiscalizacaoSituacao){

		this.fiscalizacaoSituacao = fiscalizacaoSituacao;
	}

	public Short getCodigoRetornoVistoria(){

		return codigoRetornoVistoria;
	}

	public void setCodigoRetornoVistoria(Short codigoRetornoVistoria){

		this.codigoRetornoVistoria = codigoRetornoVistoria;
	}

	public Short getCodigoTipoRecebimento(){

		return codigoTipoRecebimento;
	}

	public void setCodigoTipoRecebimento(Short codigoTipoRecebimento){

		this.codigoTipoRecebimento = codigoTipoRecebimento;
	}

	/**
	 * @return Retorna o campo codigoRetornoFiscalizacaoColetiva.
	 */
	public Short getCodigoRetornoFiscalizacaoColetiva(){

		return codigoRetornoFiscalizacaoColetiva;
	}

	/**
	 * @param codigoRetornoFiscalizacaoColetiva
	 *            O codigoRetornoFiscalizacaoColetiva a ser setado.
	 */
	public void setCodigoRetornoFiscalizacaoColetiva(Short codigoRetornoFiscalizacaoColetiva){

		this.codigoRetornoFiscalizacaoColetiva = codigoRetornoFiscalizacaoColetiva;
	}

	/**
	 * @return Retorna o campo parecerFiscalizacaoColetiva.
	 */
	public String getParecerFiscalizacaoColetiva(){

		return parecerFiscalizacaoColetiva;
	}

	/**
	 * @param parecerFiscalizacaoColetiva
	 *            O parecerFiscalizacaoColetiva a ser setado.
	 */
	public void setParecerFiscalizacaoColetiva(String parecerFiscalizacaoColetiva){

		this.parecerFiscalizacaoColetiva = parecerFiscalizacaoColetiva;
	}

	public Set<OrdemServicoUnidade> getOrdemServicoUnidades(){

		return ordemServicoUnidades;
	}

	public void setOrdemServicoUnidades(Set<OrdemServicoUnidade> ordemServicoUnidades){

		this.ordemServicoUnidades = ordemServicoUnidades;
	}

	/**
	 * @author isilva
	 * @param ordemServicoUnidade
	 */
	public void addOrdemServicoUnidade(OrdemServicoUnidade ordemServicoUnidade){

		if(this.getOrdemServicoUnidades() == null || this.getOrdemServicoUnidades().isEmpty()){
			this.setOrdemServicoUnidades(new HashSet<OrdemServicoUnidade>());
		}

		this.getOrdemServicoUnidades().add(ordemServicoUnidade);
	}

	/**
	 * @author isilva
	 * @return
	 */
	public UnidadeOrganizacional obterUnidadeOrganizacionalAbertura(){

		UnidadeOrganizacional unidadeOrganizacional = null;

		if(!Util.isVazioOuBranco(this.getOrdemServicoUnidades())){
			for(OrdemServicoUnidade ordemServicoUnidade : this.getOrdemServicoUnidades()){
				if(!Util.isVazioOuBranco(ordemServicoUnidade)
								&& !Util.isVazioOuBranco(ordemServicoUnidade.getAtendimentoRelacaoTipo())
								&& ordemServicoUnidade.getAtendimentoRelacaoTipo().getId().intValue() == AtendimentoRelacaoTipo.ABRIR_REGISTRAR
												.intValue()){
					unidadeOrganizacional = ordemServicoUnidade.getUnidadeOrganizacional();
					break;
				}
			}
		}

		return unidadeOrganizacional;
	}

	public short getIndicadorProgramada(){

		return indicadorProgramada;
	}

	public void setIndicadorProgramada(short indicadorProgramada){

		this.indicadorProgramada = indicadorProgramada;
	}

	public Imovel getImovel(){

		return imovel;
	}

	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	public short getIndicadorServicoDiagnosticado(){

		return indicadorServicoDiagnosticado;
	}

	public void setIndicadorServicoDiagnosticado(short indicadorServicoDiagnosticado){

		this.indicadorServicoDiagnosticado = indicadorServicoDiagnosticado;
	}

	public CorteTipo getCorteTipo(){

		return corteTipo;
	}

	public void setCorteTipo(CorteTipo corteTipo){

		this.corteTipo = corteTipo;
	}

	public SupressaoTipo getSupressaoTipo(){

		return supressaoTipo;
	}

	public void setSupressaoTipo(SupressaoTipo supressaoTipo){

		this.supressaoTipo = supressaoTipo;
	}

	public BigDecimal getDimensao1(){

		return dimensao1;
	}

	public void setDimensao1(BigDecimal dimensao1){

		this.dimensao1 = dimensao1;
	}

	public BigDecimal getDimensao2(){

		return dimensao2;
	}

	public void setDimensao2(BigDecimal dimensao2){

		this.dimensao2 = dimensao2;
	}

	public BigDecimal getDimensao3(){

		return dimensao3;
	}

	public void setDimensao3(BigDecimal dimensao3){

		this.dimensao3 = dimensao3;
	}

	public DiametroRamalAgua getDiametroRamalAgua(){

		return diametroRamalAgua;
	}

	public void setDiametroRamalAgua(DiametroRamalAgua diametroRamalAgua){

		this.diametroRamalAgua = diametroRamalAgua;
	}

	public DiametroRedeEsgoto getDiametroRedeEsgoto(){

		return diametroRedeEsgoto;
	}

	public void setDiametroRedeEsgoto(DiametroRedeEsgoto diametroRedeEsgoto){

		this.diametroRedeEsgoto = diametroRedeEsgoto;
	}

	public DiametroRamalEsgoto getDiametroRamalEsgoto(){

		return diametroRamalEsgoto;
	}

	public void setDiametroRamalEsgoto(DiametroRamalEsgoto diametroRamalEsgoto){

		this.diametroRamalEsgoto = diametroRamalEsgoto;
	}

	public MaterialRedeAgua getMaterialRedeAgua(){

		return materialRedeAgua;
	}

	public void setMaterialRedeAgua(MaterialRedeAgua materialRedeAgua){

		this.materialRedeAgua = materialRedeAgua;
	}

	public MaterialRamalAgua getMaterialRamalAgua(){

		return materialRamalAgua;
	}

	public void setMaterialRamalAgua(MaterialRamalAgua materialRamalAgua){

		this.materialRamalAgua = materialRamalAgua;
	}

	public MaterialRedeEsgoto getMaterialRedeEsgoto(){

		return materialRedeEsgoto;
	}

	public void setMaterialRedeEsgoto(MaterialRedeEsgoto materialRedeEsgoto){

		this.materialRedeEsgoto = materialRedeEsgoto;
	}

	public MaterialRamalEsgoto getMaterialRamalEsgoto(){

		return materialRamalEsgoto;
	}

	public void setMaterialRamalEsgoto(MaterialRamalEsgoto materialRamalEsgoto){

		this.materialRamalEsgoto = materialRamalEsgoto;
	}

	public CausaVazamento getCausaVazamento(){

		return causaVazamento;
	}

	public void setCausaVazamento(CausaVazamento causaVazamento){

		this.causaVazamento = causaVazamento;
	}

	public AgenteExterno getAgenteExterno(){

		return agenteExterno;
	}

	public void setAgenteExterno(AgenteExterno agenteExterno){

		this.agenteExterno = agenteExterno;
	}

	public BigDecimal getNumeroProfundidade(){

		return numeroProfundidade;
	}

	public void setNumeroProfundidade(BigDecimal numeroProfundidade){

		this.numeroProfundidade = numeroProfundidade;
	}

	public BigDecimal getNumeroPressao(){

		return numeroPressao;
	}

	public void setNumeroPressao(BigDecimal numeroPressao){

		this.numeroPressao = numeroPressao;
	}

	@Override
	public int hashCode(){

		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj){

		if(this == obj) return true;
		if(obj == null) return false;
		if(!(obj instanceof OrdemServico)) return false;
		final OrdemServico other = (OrdemServico) obj;
		if(id == null){
			if(other.id != null) return false;
		}else if(!id.equals(other.id)) return false;
		return true;
	}

	public DiametroRedeAgua getDiametroRedeAgua(){

		return diametroRedeAgua;
	}

	public void setDiametroRedeAgua(DiametroRedeAgua diametroRedeAgua){

		this.diametroRedeAgua = diametroRedeAgua;
	}

	public Empresa getAgente(){

		return agente;
	}

	public void setAgente(Empresa agente){

		this.agente = agente;
	}

	public CobrancaAcaoAtividadeComando getCobrancaAcaoAtividadeComando(){

		return cobrancaAcaoAtividadeComando;
	}

	public void setCobrancaAcaoAtividadeComando(CobrancaAcaoAtividadeComando cobrancaAcaoAtividadeComando){

		this.cobrancaAcaoAtividadeComando = cobrancaAcaoAtividadeComando;
	}

	public CobrancaAcaoAtividadeCronograma getCobrancaAcaoAtividadeCronograma(){

		return cobrancaAcaoAtividadeCronograma;
	}

	public void setCobrancaAcaoAtividadeCronograma(CobrancaAcaoAtividadeCronograma cobrancaAcaoAtividadeCronograma){

		this.cobrancaAcaoAtividadeCronograma = cobrancaAcaoAtividadeCronograma;
	}

	public OrdemServico getOrdemServicoVinculada(){

		return ordemServicoVinculada;
	}

	public void setOrdemServicoVinculada(OrdemServico ordemServicoVinculada){

		this.ordemServicoVinculada = ordemServicoVinculada;
	}

	public DiametroCavaleteAgua getDiametroCavaleteAgua(){

		return diametroCavaleteAgua;
	}

	public void setDiametroCavaleteAgua(DiametroCavaleteAgua diametroCavaleteAgua){

		this.diametroCavaleteAgua = diametroCavaleteAgua;
	}

	public MaterialCavaleteAgua getMaterialCavaleteAgua(){

		return materialCavaleteAgua;
	}

	public void setMaterialCavaleteAgua(MaterialCavaleteAgua materialCavaleteAgua){

		this.materialCavaleteAgua = materialCavaleteAgua;
	}

	public Set getTramites(){

		return tramites;
	}

	public void setTramites(Set tramites){

		this.tramites = tramites;
	}

	public OsSeletivaComando getOsSeletivaComando(){

		return osSeletivaComando;
	}

	public void setOsSeletivaComando(OsSeletivaComando osSeletivaComando){

		this.osSeletivaComando = osSeletivaComando;
	}

	public Date getDataExecucao(){

		return dataExecucao;
	}

	public void setDataExecucao(Date dataExecucao){

		this.dataExecucao = dataExecucao;
	}

	public OrdemServico getOrdemServicoReparo(){

		return ordemServicoReparo;
	}

	public void setOrdemServicoReparo(OrdemServico ordemServicoReparo){

		this.ordemServicoReparo = ordemServicoReparo;
	}

	public BigDecimal getValorHorasTrabalhadas(){

		return valorHorasTrabalhadas;
	}

	public void setValorHorasTrabalhadas(BigDecimal valorHorasTrabalhadas){

		this.valorHorasTrabalhadas = valorHorasTrabalhadas;
	}

	public BigDecimal getValorMateriais(){

		return valorMateriais;
	}

	public void setValorMateriais(BigDecimal valorMateriais){

		this.valorMateriais = valorMateriais;
	}

	public String getSituacaoDocumentoDebito(){

		return situacaoDocumentoDebito;
	}

	public void setSituacaoDocumentoDebito(String situacaoDocumentoDebito){

		this.situacaoDocumentoDebito = situacaoDocumentoDebito;
	}

}
