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

package gcom.atendimentopublico.registroatendimento;

import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cadastro.endereco.LogradouroCep;
import gcom.cadastro.geografico.BairroArea;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.operacional.DivisaoEsgoto;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

@ControleAlteracao()
public class RegistroAtendimento
				extends ObjetoTransacao
				implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static Integer CONSTANTE = Integer.valueOf(1);

	public final static int SITUACAO_TODOS = 0;

	public final static Short SITUACAO_PENDENTE = Short.valueOf("1");

	public final static String SITUACAO_DESCRICAO_PENDENTE = "Pendente";

	public final static String SITUACAO_DESC_ABREV_PENDENTE = "Pen";

	public final static Short SITUACAO_ENCERRADO = Short.valueOf("2");

	public final static String SITUACAO_DESCRICAO_ENCERRADO = "Encerrado";

	public final static String SITUACAO_DESC_ABREV_ENCERRADO = "Enc";

	public final static Short SITUACAO_BLOQUEADO = Short.valueOf("3");

	public final static String SITUACAO_DESCRICAO_BLOQUEADO = "Bloqueado";

	public final static String SITUACAO_DESC_ABREV_BLOQUEADO = "Blq";

	public final static short CODIGO_ASSOCIADO_SEM_RA = 0;

	public final static short CODIGO_ASSOCIADO_RA_REFERENCIA = 1;

	public final static short CODIGO_ASSOCIADO_RA_ATUAL = 2;

	public final static short CODIGO_ASSOCIADO_RA_ANTERIOR = 3;

	public final static String NUMERO_RA_REFERENCIA = "Número do RA de Referência:";

	public final static String NUMERO_RA_ATUAL = "Número do RA Atual:";

	public final static String NUMERO_RA_ANTERIOR = "Número do RA Anterior:";

	public final static String SITUACAO_RA_REFERENCIA = "Situação do RA de Referência:";

	public final static String SITUACAO_RA_ATUAL = "Situação do RA Atual:";

	public final static String SITUACAO_RA_ANTERIOR = "Situação do RA Anterior:";

	public final static Short INDICADOR_MANUTENCAO_RA_NAO = Short.valueOf("2");

	public final static Short INDICADOR_ATENDIMENTO_ON_LINE = Short.valueOf("1");

	public final static Short INDICADOR_ATENDIMENTO_MANUAL = Short.valueOf("2");

	public final static Integer INDICADOR_EXECUCAO_LIBERADA = Integer.valueOf(2);

	public final static Short MEIO_SOLICITACAO_BALCAO = Short.valueOf("1");

	// public final static Integer PAGAMENTO_PRIMEIRA_PARCELA_EFETUADO_NO_VENCIMENTO =
	// Integer.valueOf(39);

	// public final static Integer PAGAMENTO_PRIMEIRA_PARCELA_EFETUADO_APOS_VENCIMENTO =
	// Integer.valueOf(40);

	public static final int ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR = 543; // Operacao.OPERACAO_REGISTRO_ATENDIMENTO_ATUALIZAR

	// public static final int ATRIBUTOS_REGISTRO_ATENDIMENTO_INSERIR = 267; //
	// Operacao.OPERACAO_REGISTRO_ATENDIMENTO_INSERIR

	/** identifier field */
	private Integer id;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private short codigoSituacao;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private Date registroAtendimento;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private Date dataPrevistaOriginal;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private Date dataPrevistaAtual;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private Date ultimaEmissao;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private Date dataEncerramento;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private Short quantidadeReiteracao;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private Date ultimaReiteracao;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private String observacao;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private String descricaoLocalOcorrencia;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private Date ultimaAlteracao;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private String parecerEncerramento;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private String pontoReferencia;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private String complementoEndereco;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private short indicadorAtendimentoOnline;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private Date dataInicioEspera;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private Date dataFimEspera;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private String numeroImovel;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private Integer reiteracao;

	// Não Mapeado
	private Integer indicadorExecucaoLiberada;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private LogradouroBairro logradouroBairro;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private SetorComercial setorComercial;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private gcom.atendimentopublico.registroatendimento.AtendimentoMotivoEncerramento atendimentoMotivoEncerramento;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private gcom.cadastro.imovel.PavimentoCalcada pavimentoCalcada;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private gcom.atendimentopublico.registroatendimento.LocalOcorrencia localOcorrencia;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private LogradouroCep logradouroCep;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private gcom.atendimentopublico.registroatendimento.MeioSolicitacao meioSolicitacao;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private Integer senhaAtendimento;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private gcom.atendimentopublico.registroatendimento.RaMotivoReativacao raMotivoReativacao;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private gcom.atendimentopublico.registroatendimento.RegistroAtendimento registroAtendimentoReativacao;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private gcom.atendimentopublico.registroatendimento.RegistroAtendimento registroAtendimentoDuplicidade;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private gcom.atendimentopublico.registroatendimento.RegistroAtendimentoUnidade registroAtendimentoUnidade;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private Imovel imovel;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private BairroArea bairroArea;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private Quadra quadra;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private Localidade localidade;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private gcom.cadastro.imovel.PavimentoRua pavimentoRua;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private DivisaoEsgoto divisaoEsgoto;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private Integer manual;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private short indicadorProcessoAdmJud = 2;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private String numeroProcessoAgencia;

	private Set tramites;

	private Set raDadosAgenciaReguladoras;

	private Set registroAtendimentoUnidades;

	private Set registroAtendimentoSolicitantes;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private BigDecimal coordenadaNorte;

	/**
	 * persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_REGISTRO_ATENDIMENTO_ATUALIZAR})
	private BigDecimal coordenadaLeste;

	private transient Integer quantidadeGuiasPendentes;

	/** full constructor */
	public RegistroAtendimento(short codigoSituacao, Date registroAtendimento, Date dataPrevistaOriginal, Date dataPrevistaAtual,
								Date ultimaEmissao, Date dataEncerramento, Short quantidadeReiteracao, Date ultimaReiteracao,
								String observacao, String descricaoLocalOcorrencia, Date ultimaAlteracao, String parecerEncerramento,
								String pontoReferencia, String complementoEndereco, short indicadorAtendimentoOnline,
								Date dataInicioEspera, Date dataFimEspera, LogradouroBairro logradouroBairro,
								SetorComercial setorComercial,
								gcom.atendimentopublico.registroatendimento.AtendimentoMotivoEncerramento atendimentoMotivoEncerramento,
								gcom.cadastro.imovel.PavimentoCalcada pavimentoCalcada,
								gcom.atendimentopublico.registroatendimento.LocalOcorrencia localOcorrencia, LogradouroCep logradouroCep,
								gcom.atendimentopublico.registroatendimento.MeioSolicitacao meioSolicitacao, Integer senhaAtendimento,
								gcom.atendimentopublico.registroatendimento.RaMotivoReativacao raMotivoReativacao,
								gcom.atendimentopublico.registroatendimento.RegistroAtendimento registroAtendimentoReativacao,
								gcom.atendimentopublico.registroatendimento.RegistroAtendimento registroAtendimentoDuplicidade,
								Imovel imovel, BairroArea bairroArea,
								gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao,
								Quadra quadra, Localidade localidade, gcom.cadastro.imovel.PavimentoRua pavimentoRua,
								DivisaoEsgoto divisaoEsgoto) {

		this.codigoSituacao = codigoSituacao;
		this.registroAtendimento = registroAtendimento;
		this.dataPrevistaOriginal = dataPrevistaOriginal;
		this.dataPrevistaAtual = dataPrevistaAtual;
		this.ultimaEmissao = ultimaEmissao;
		this.dataEncerramento = dataEncerramento;
		this.quantidadeReiteracao = quantidadeReiteracao;
		this.ultimaReiteracao = ultimaReiteracao;
		this.observacao = observacao;
		this.descricaoLocalOcorrencia = descricaoLocalOcorrencia;
		this.ultimaAlteracao = ultimaAlteracao;
		this.parecerEncerramento = parecerEncerramento;
		this.pontoReferencia = pontoReferencia;
		this.complementoEndereco = complementoEndereco;
		this.indicadorAtendimentoOnline = indicadorAtendimentoOnline;
		this.dataInicioEspera = dataInicioEspera;
		this.dataFimEspera = dataFimEspera;
		this.logradouroBairro = logradouroBairro;
		this.setorComercial = setorComercial;
		this.atendimentoMotivoEncerramento = atendimentoMotivoEncerramento;
		this.pavimentoCalcada = pavimentoCalcada;
		this.localOcorrencia = localOcorrencia;
		this.logradouroCep = logradouroCep;
		this.meioSolicitacao = meioSolicitacao;
		this.senhaAtendimento = senhaAtendimento;
		this.raMotivoReativacao = raMotivoReativacao;
		this.registroAtendimentoReativacao = registroAtendimentoReativacao;
		this.registroAtendimentoDuplicidade = registroAtendimentoDuplicidade;
		this.imovel = imovel;
		this.bairroArea = bairroArea;
		this.solicitacaoTipoEspecificacao = solicitacaoTipoEspecificacao;
		this.quadra = quadra;
		this.localidade = localidade;
		this.pavimentoRua = pavimentoRua;
		this.divisaoEsgoto = divisaoEsgoto;
	}

	/** default constructor */
	public RegistroAtendimento() {

	}

	/** minimal constructor */
	public RegistroAtendimento(short codigoSituacao, Date registroAtendimento, Date dataPrevistaOriginal, Date dataPrevistaAtual,
								Date ultimaAlteracao, short indicadorAtendimentoOnline, LogradouroBairro logradouroBairro,
								SetorComercial setorComercial,
								gcom.atendimentopublico.registroatendimento.AtendimentoMotivoEncerramento atendimentoMotivoEncerramento,
								gcom.cadastro.imovel.PavimentoCalcada pavimentoCalcada,
								gcom.atendimentopublico.registroatendimento.LocalOcorrencia localOcorrencia, LogradouroCep logradouroCep,
								gcom.atendimentopublico.registroatendimento.MeioSolicitacao meioSolicitacao,
								gcom.atendimentopublico.registroatendimento.RaMotivoReativacao raMotivoReativacao,
								gcom.atendimentopublico.registroatendimento.RegistroAtendimento registroAtendimentoReativacao,
								gcom.atendimentopublico.registroatendimento.RegistroAtendimento registroAtendimentoDuplicidade,
								Imovel imovel, BairroArea bairroArea,
								gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao,
								Quadra quadra, Localidade localidade, gcom.cadastro.imovel.PavimentoRua pavimentoRua,
								DivisaoEsgoto divisaoEsgoto) {

		this.codigoSituacao = codigoSituacao;
		this.registroAtendimento = registroAtendimento;
		this.dataPrevistaOriginal = dataPrevistaOriginal;
		this.dataPrevistaAtual = dataPrevistaAtual;
		this.ultimaAlteracao = ultimaAlteracao;
		this.indicadorAtendimentoOnline = indicadorAtendimentoOnline;
		this.logradouroBairro = logradouroBairro;
		this.setorComercial = setorComercial;
		this.atendimentoMotivoEncerramento = atendimentoMotivoEncerramento;
		this.pavimentoCalcada = pavimentoCalcada;
		this.localOcorrencia = localOcorrencia;
		this.logradouroCep = logradouroCep;
		this.meioSolicitacao = meioSolicitacao;
		this.raMotivoReativacao = raMotivoReativacao;
		this.registroAtendimentoReativacao = registroAtendimentoReativacao;
		this.registroAtendimentoDuplicidade = registroAtendimentoDuplicidade;
		this.imovel = imovel;
		this.bairroArea = bairroArea;
		this.solicitacaoTipoEspecificacao = solicitacaoTipoEspecificacao;
		this.quadra = quadra;
		this.localidade = localidade;
		this.pavimentoRua = pavimentoRua;
		this.divisaoEsgoto = divisaoEsgoto;
	}

	public Integer getQuantidadeGuiasPendentes(){

		return quantidadeGuiasPendentes;
	}

	public void setQuantidadeGuiasPendentes(Integer quantidadeGuiasPendentes){

		this.quantidadeGuiasPendentes = quantidadeGuiasPendentes;
	}

	public Integer getManual(){

		return manual;
	}

	public void setManual(Integer manual){

		this.manual = manual;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public short getCodigoSituacao(){

		return this.codigoSituacao;
	}

	public void setCodigoSituacao(short codigoSituacao){

		this.codigoSituacao = codigoSituacao;
	}

	public Date getRegistroAtendimento(){

		return this.registroAtendimento;
	}

	public void setRegistroAtendimento(Date registroAtendimento){

		this.registroAtendimento = registroAtendimento;
	}

	public Date getDataPrevistaOriginal(){

		return this.dataPrevistaOriginal;
	}

	public void setDataPrevistaOriginal(Date dataPrevistaOriginal){

		this.dataPrevistaOriginal = dataPrevistaOriginal;
	}

	public Date getDataPrevistaAtual(){

		return this.dataPrevistaAtual;
	}

	public void setDataPrevistaAtual(Date dataPrevistaAtual){

		this.dataPrevistaAtual = dataPrevistaAtual;
	}

	public Date getUltimaEmissao(){

		return this.ultimaEmissao;
	}

	public void setUltimaEmissao(Date ultimaEmissao){

		this.ultimaEmissao = ultimaEmissao;
	}

	public Date getDataEncerramento(){

		return this.dataEncerramento;
	}

	public void setDataEncerramento(Date dataEncerramento){

		this.dataEncerramento = dataEncerramento;
	}

	public Short getQuantidadeReiteracao(){

		return this.quantidadeReiteracao;
	}

	public void setQuantidadeReiteracao(Short quantidadeReiteracao){

		this.quantidadeReiteracao = quantidadeReiteracao;
	}

	public Date getUltimaReiteracao(){

		return this.ultimaReiteracao;
	}

	public void setUltimaReiteracao(Date ultimaReiteracao){

		this.ultimaReiteracao = ultimaReiteracao;
	}

	public String getObservacao(){

		return this.observacao;
	}

	public void setObservacao(String observacao){

		this.observacao = observacao;
	}

	public String getDescricaoLocalOcorrencia(){

		return this.descricaoLocalOcorrencia;
	}

	public void setDescricaoLocalOcorrencia(String descricaoLocalOcorrencia){

		this.descricaoLocalOcorrencia = descricaoLocalOcorrencia;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String getParecerEncerramento(){

		return this.parecerEncerramento;
	}

	public void setParecerEncerramento(String parecerEncerramento){

		this.parecerEncerramento = parecerEncerramento;
	}

	public String getPontoReferencia(){

		return this.pontoReferencia;
	}

	public void setPontoReferencia(String pontoReferencia){

		this.pontoReferencia = pontoReferencia;
	}

	public String getComplementoEndereco(){

		return this.complementoEndereco;
	}

	public void setComplementoEndereco(String complementoEndereco){

		this.complementoEndereco = complementoEndereco;
	}

	public short getIndicadorAtendimentoOnline(){

		return this.indicadorAtendimentoOnline;
	}

	public void setIndicadorAtendimentoOnline(short indicadorAtendimentoOnline){

		this.indicadorAtendimentoOnline = indicadorAtendimentoOnline;
	}

	public Date getDataInicioEspera(){

		return this.dataInicioEspera;
	}

	public void setDataInicioEspera(Date dataInicioEspera){

		this.dataInicioEspera = dataInicioEspera;
	}

	public Date getDataFimEspera(){

		return this.dataFimEspera;
	}

	public void setDataFimEspera(Date dataFimEspera){

		this.dataFimEspera = dataFimEspera;
	}

	public LogradouroBairro getLogradouroBairro(){

		return this.logradouroBairro;
	}

	public void setLogradouroBairro(LogradouroBairro logradouroBairro){

		this.logradouroBairro = logradouroBairro;
	}

	public SetorComercial getSetorComercial(){

		return this.setorComercial;
	}

	public void setSetorComercial(SetorComercial setorComercial){

		this.setorComercial = setorComercial;
	}

	public gcom.atendimentopublico.registroatendimento.AtendimentoMotivoEncerramento getAtendimentoMotivoEncerramento(){

		return this.atendimentoMotivoEncerramento;
	}

	public void setAtendimentoMotivoEncerramento(
					gcom.atendimentopublico.registroatendimento.AtendimentoMotivoEncerramento atendimentoMotivoEncerramento){

		this.atendimentoMotivoEncerramento = atendimentoMotivoEncerramento;
	}

	public gcom.cadastro.imovel.PavimentoCalcada getPavimentoCalcada(){

		return this.pavimentoCalcada;
	}

	public void setPavimentoCalcada(gcom.cadastro.imovel.PavimentoCalcada pavimentoCalcada){

		this.pavimentoCalcada = pavimentoCalcada;
	}

	public gcom.atendimentopublico.registroatendimento.LocalOcorrencia getLocalOcorrencia(){

		return this.localOcorrencia;
	}

	public void setLocalOcorrencia(gcom.atendimentopublico.registroatendimento.LocalOcorrencia localOcorrencia){

		this.localOcorrencia = localOcorrencia;
	}

	public LogradouroCep getLogradouroCep(){

		return this.logradouroCep;
	}

	public void setLogradouroCep(LogradouroCep logradouroCep){

		this.logradouroCep = logradouroCep;
	}

	public gcom.atendimentopublico.registroatendimento.MeioSolicitacao getMeioSolicitacao(){

		return this.meioSolicitacao;
	}

	public void setMeioSolicitacao(gcom.atendimentopublico.registroatendimento.MeioSolicitacao meioSolicitacao){

		this.meioSolicitacao = meioSolicitacao;
	}

	public gcom.atendimentopublico.registroatendimento.RaMotivoReativacao getRaMotivoReativacao(){

		return this.raMotivoReativacao;
	}

	public void setRaMotivoReativacao(gcom.atendimentopublico.registroatendimento.RaMotivoReativacao raMotivoReativacao){

		this.raMotivoReativacao = raMotivoReativacao;
	}

	public gcom.atendimentopublico.registroatendimento.RegistroAtendimento getRegistroAtendimentoReativacao(){

		return this.registroAtendimentoReativacao;
	}

	public void setRegistroAtendimentoReativacao(
					gcom.atendimentopublico.registroatendimento.RegistroAtendimento registroAtendimentoReativacao){

		this.registroAtendimentoReativacao = registroAtendimentoReativacao;
	}

	public gcom.atendimentopublico.registroatendimento.RegistroAtendimento getRegistroAtendimentoDuplicidade(){

		return this.registroAtendimentoDuplicidade;
	}

	public void setRegistroAtendimentoDuplicidade(
					gcom.atendimentopublico.registroatendimento.RegistroAtendimento registroAtendimentoDuplicidade){

		this.registroAtendimentoDuplicidade = registroAtendimentoDuplicidade;
	}

	public Imovel getImovel(){

		return this.imovel;
	}

	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	public BairroArea getBairroArea(){

		return this.bairroArea;
	}

	public void setBairroArea(BairroArea bairroArea){

		this.bairroArea = bairroArea;
	}

	public gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao getSolicitacaoTipoEspecificacao(){

		return this.solicitacaoTipoEspecificacao;
	}

	public void setSolicitacaoTipoEspecificacao(
					gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao){

		this.solicitacaoTipoEspecificacao = solicitacaoTipoEspecificacao;
	}

	public Quadra getQuadra(){

		return this.quadra;
	}

	public void setQuadra(Quadra quadra){

		this.quadra = quadra;
	}

	public Localidade getLocalidade(){

		return this.localidade;
	}

	public void setLocalidade(Localidade localidade){

		this.localidade = localidade;
	}

	public gcom.cadastro.imovel.PavimentoRua getPavimentoRua(){

		return this.pavimentoRua;
	}

	public void setPavimentoRua(gcom.cadastro.imovel.PavimentoRua pavimentoRua){

		this.pavimentoRua = pavimentoRua;
	}

	public DivisaoEsgoto getDivisaoEsgoto(){

		return this.divisaoEsgoto;
	}

	public void setDivisaoEsgoto(DivisaoEsgoto divisaoEsgoto){

		this.divisaoEsgoto = divisaoEsgoto;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public Filtro retornaFiltro(){

		FiltroRegistroAtendimento filtroRegistroAtendimento = new FiltroRegistroAtendimento();

		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro");
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade("setorComercial");
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade("atendimentoMotivoEncerramento");
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade("pavimentoCalcada");
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade("localOcorrencia");
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade("logradouroCep");
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade("meioSolicitacao");
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade("raMotivoReativacao");
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade("registroAtendimentoReativacao");
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade("registroAtendimentoDuplicidade");
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade("imovel");
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade("bairroArea");
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade("solicitacaoTipoEspecificacao");
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade("quadra");
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade("localidade");
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade("pavimentoRua");
		filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade("divisaoEsgoto");
		filtroRegistroAtendimento.adicionarParametro(new ParametroSimples(FiltroRegistroAtendimento.ID, this.getId()));
		return filtroRegistroAtendimento;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"id"};
		return retorno;
	}

	/**
	 * Gets the endereco attribute of the Imovel object
	 * 
	 * @return The endereco value
	 */
	public String getEnderecoFormatadoAbreviado(){

		String endereco = null;

		// verifica se o logradouro do registro atendimento é diferente de null
		if(this.getLogradouroCep() != null && this.getLogradouroCep().getLogradouro() != null
						&& !this.getLogradouroCep().getLogradouro().getId().equals(new Integer("0"))){

			// verifica se o logradouro tipo do registro atendimento é diferente
			// de null
			if(this.getLogradouroCep().getLogradouro().getLogradouroTipo() != null
							&& !this.getLogradouroCep().getLogradouro().getLogradouroTipo().equals("")){
				if(this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricaoAbreviada() != null
								&& !this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricaoAbreviada().equals("")){
					// concatena o logradouro tipo do registro atendimento
					endereco = this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricaoAbreviada().trim();
				}
			}
			// verifica se o logradouro titulo do registro atendimento é
			// diferente de null
			if(this.getLogradouroCep().getLogradouro().getLogradouroTitulo() != null
							&& !this.getLogradouroCep().getLogradouro().getLogradouroTitulo().equals("")){
				if(this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricaoAbreviada() != null
								&& !this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricaoAbreviada().equals("")){
					// concatena o logradouro titulo do registro atendimento
					endereco = endereco + " "
									+ this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricaoAbreviada().trim();
				}
			}

			// concatena o logradouro do registro atendimento
			endereco = endereco + " " + this.getLogradouroCep().getLogradouro().getNome().trim();

			if(this.getComplementoEndereco() != null && !this.getComplementoEndereco().equalsIgnoreCase("")){
				endereco = endereco + " - " + this.getComplementoEndereco().trim();
			}

			// concate o numero do imovel
			if(this.getNumeroImovel() != null && !this.getNumeroImovel().equals("")){
				endereco = endereco + " - " + this.getNumeroImovel().trim();
			}

			if(this.getLogradouroBairro() != null && this.getLogradouroBairro().getBairro() != null
							&& this.getLogradouroBairro().getBairro().getId().intValue() != 0){
				endereco = endereco + " - " + this.getLogradouroBairro().getBairro().getNome().trim();

				if(this.getLogradouroBairro().getBairro().getMunicipio() != null
								&& this.getLogradouroBairro().getBairro().getMunicipio().getId().intValue() != 0){
					endereco = endereco + " " + this.getLogradouroBairro().getBairro().getMunicipio().getNome().trim();
				}

				if(this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao() != null
								&& this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao().getId().intValue() != 0){
					endereco = endereco + " "
									+ this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao().getSigla().trim();
				}
			}

			if(this.getLogradouroCep() != null && this.getLogradouroCep().getCep() != null){
				// concatena o cep formatado do registro atendimento
				endereco = endereco + " " + this.getLogradouroCep().getCep().getCepFormatado().trim();
			}

		}

		return endereco;
	}

	/**
	 * Gets the endereco attribute of the Imovel object
	 * 
	 * @return The endereco value
	 */
	public String getEnderecoFormatado(){

		String endereco = "";

		// verifica se o logradouro do registro atendimento é diferente de null
		if(this.getLogradouroCep() != null && this.getLogradouroCep().getLogradouro() != null
						&& !this.getLogradouroCep().getLogradouro().getId().equals(new Integer("0"))){

			// verifica se o logradouro tipo do registro atendimento é diferente
			// de null
			if(this.getLogradouroCep().getLogradouro().getLogradouroTipo() != null
							&& !this.getLogradouroCep().getLogradouro().getLogradouroTipo().equals("")){
				if(this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricao() != null
								&& !this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricao().equals("")){
					// concatena o logradouro tipo do registro atendimento
					endereco = this.getLogradouroCep().getLogradouro().getLogradouroTipo().getDescricao().trim();
				}
			}
			// verifica se o logradouro titulo do registro atendimento é
			// diferente de null
			if(this.getLogradouroCep().getLogradouro().getLogradouroTitulo() != null
							&& !this.getLogradouroCep().getLogradouro().getLogradouroTitulo().equals("")){
				if(this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricao() != null
								&& !this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricao().equals("")){
					// concatena o logradouro titulo do registro atendimento
					endereco = endereco + " " + this.getLogradouroCep().getLogradouro().getLogradouroTitulo().getDescricao().trim();
				}
			}

			// concatena o logradouro do registro atendimento
			endereco = endereco + " " + this.getLogradouroCep().getLogradouro().getNome().trim();

		}

		// concate o numero do imovel
		if(this.getNumeroImovel() != null && !this.getNumeroImovel().equals("")){
			endereco = endereco + " - " + this.getNumeroImovel().trim();
		}

		// concate o complemento do endereço
		if(this.getComplementoEndereco() != null && !this.getComplementoEndereco().trim().equalsIgnoreCase("")){
			endereco = endereco + " - " + this.getComplementoEndereco().trim();
		}

		if(this.getLogradouroBairro() != null && this.getLogradouroBairro().getBairro() != null
						&& this.getLogradouroBairro().getBairro().getId().intValue() != 0){
			endereco = endereco + " - " + this.getLogradouroBairro().getBairro().getNome().trim();

			if(this.getLogradouroBairro().getBairro().getMunicipio() != null
							&& this.getLogradouroBairro().getBairro().getMunicipio().getId().intValue() != 0){
				endereco = endereco + " - " + this.getLogradouroBairro().getBairro().getMunicipio().getNome().trim();
			}

			if(this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao() != null
							&& this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao().getId().intValue() != 0){
				endereco = endereco + " - " + this.getLogradouroBairro().getBairro().getMunicipio().getUnidadeFederacao().getSigla().trim();
			}
		}

		if(this.getLogradouroCep() != null && this.getLogradouroCep().getCep() != null){
			// concatena o cep formatado do registro atendimento
			endereco = endereco + " " + this.getLogradouroCep().getCep().getCepFormatado().trim();
		}

		return endereco;
	}

	public final String getDescricaoSituacao(){

		String descricao = "";
		if(SITUACAO_PENDENTE.equals(getCodigoSituacao())){
			descricao = SITUACAO_DESCRICAO_PENDENTE;
		}else if(SITUACAO_ENCERRADO.equals(getCodigoSituacao())){
			descricao = SITUACAO_DESCRICAO_ENCERRADO;
		}else if(SITUACAO_BLOQUEADO.equals(getCodigoSituacao())){
			descricao = SITUACAO_DESCRICAO_BLOQUEADO;
		}
		return descricao;
	}

	/**
	 * @return Retorna o campo numeroImovel.
	 */
	public String getNumeroImovel(){

		return numeroImovel;
	}

	/**
	 * @param numeroImovel
	 *            O numeroImovel a ser setado.
	 */
	public void setNumeroImovel(String numeroImovel){

		this.numeroImovel = numeroImovel;
	}

	public Set getTramites(){

		return tramites;
	}

	public void setTramites(Set tramites){

		this.tramites = tramites;
	}

	public Set getRaDadosAgenciaReguladoras(){

		return raDadosAgenciaReguladoras;
	}

	public void setRaDadosAgenciaReguladoras(Set raDadosAgenciaReguladoras){

		this.raDadosAgenciaReguladoras = raDadosAgenciaReguladoras;
	}

	public Set getRegistroAtendimentoUnidades(){

		return registroAtendimentoUnidades;
	}

	public void setRegistroAtendimentoUnidades(Set registroAtendimentoUnidades){

		this.registroAtendimentoUnidades = registroAtendimentoUnidades;
	}

	public RegistroAtendimentoUnidade getRegistroAtendimentoUnidade(){

		return registroAtendimentoUnidade;
	}

	public void setRegistroAtendimentoUnidade(RegistroAtendimentoUnidade registroAtendimentoUnidade){

		this.registroAtendimentoUnidade = registroAtendimentoUnidade;
	}

	public Integer getSenhaAtendimento(){

		return senhaAtendimento;
	}

	public void setSenhaAtendimento(Integer senhaAtendimento){

		this.senhaAtendimento = senhaAtendimento;
	}

	public BigDecimal getCoordenadaNorte(){

		return coordenadaNorte;
	}

	public void setCoordenadaNorte(BigDecimal coordenadaNorte){

		this.coordenadaNorte = coordenadaNorte;
	}

	public BigDecimal getCoordenadaLeste(){

		return coordenadaLeste;
	}

	public void setCoordenadaLeste(BigDecimal coordenadaLeste){

		this.coordenadaLeste = coordenadaLeste;
	}

	public Integer getReiteracao(){

		return reiteracao;
	}

	public void setReiteracao(Integer reiteracao){

		this.reiteracao = reiteracao;
	}

	public Integer getIndicadorExecucaoLiberada(){

		return indicadorExecucaoLiberada;
	}

	public void setIndicadorExecucaoLiberada(Integer indicadorExecucaoLiberada){

		this.indicadorExecucaoLiberada = indicadorExecucaoLiberada;
	}

	public String getDescricao(){

		return this.getClass().getSimpleName() + "[" + id + "]";

	}

	/**
	 * @return the numeroProcessoAgencia
	 */
	public String getNumeroProcessoAgencia(){

		return numeroProcessoAgencia;
	}

	/**
	 * @param numeroProcessoAgencia
	 *            the numeroProcessoAgencia to set
	 */
	public void setNumeroProcessoAgencia(String numeroProcessoAgencia){

		this.numeroProcessoAgencia = numeroProcessoAgencia;
	}

	/**
	 * @return the indicadorProcessoAdmJud
	 */
	public short getIndicadorProcessoAdmJud(){

		return indicadorProcessoAdmJud;
	}

	/**
	 * @param indicadorProcessoAdmJud
	 *            the indicadorProcessoAdmJud to set
	 */
	public void setIndicadorProcessoAdmJud(short indicadorProcessoAdmJud){

		this.indicadorProcessoAdmJud = indicadorProcessoAdmJud;
	}

	public Set getRegistroAtendimentoSolicitantes(){

		return registroAtendimentoSolicitantes;
	}

	public void setRegistroAtendimentoSolicitantes(Set registroAtendimentoSolicitantes){

		this.registroAtendimentoSolicitantes = registroAtendimentoSolicitantes;
	}

}
