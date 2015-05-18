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

import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;

import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class SolicitacaoTipoEspecificacao
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	private String descricao;

	// Atributo eliminado para se trabalhar com prazo em Horas
	// /** nullable persistent field */
	// private Integer diasPrazo;

	/** nullable persistent field */
	private Integer horasPrazo;

	/** persistent field */
	private short indicadorPavimentoCalcada;

	/** persistent field */
	private short indicadorPavimentoRua;

	/** nullable persistent field */
	private Integer indicadorCobrancaMaterial;

	/** nullable persistent field */
	private Integer indicadorMatricula;

	/** nullable persistent field */
	private Integer indicadorParecerEncerramento;

	/** nullable persistent field */
	private Integer indicadorGeracaoDebito;

	/** nullable persistent field */
	private Integer indicadorGeracaoCredito;

	/** persistent field */
	private Date ultimaAlteracao;

	/** persistent field */
	private short indicadorGeracaoOrdemServico;

	/** nullable persistent field */
	private Short indicadorCliente;

	/** nullable persistent field */
	private Short indicadorLigacaoAgua;

	private Short indicadorSolicitante;

	private Short indicadorVerificarDebito;

	private Short indicadorTipoVerificarDebito;

	private Short indicadorCalculoDataPrevistaAtendimento;

	/** persistent field */
	private gcom.atendimentopublico.registroatendimento.SolicitacaoTipo solicitacaoTipo;

	/** persistent field */
	private UnidadeOrganizacional unidadeOrganizacional;

	/** persistent field */
	private gcom.atendimentopublico.ordemservico.ServicoTipo servicoTipo;

	/** persistent field */
	private EspecificacaoImovelSituacao especificacaoImovelSituacao;

	private short indicadorUso;

	private Set especificacaoServicoTipos;

	private Short indicadorColetor;

	private SolicitacaoTipoEspecificacaoMensagem solicitacaoTipoEspecificacaoMensagem;

	private Short indicadorContaEmRevisao;

	private Short indicadorMensagemAlertaRevisao;

	private Short indicadorObrigatoriedadeRgRA;

	private Short indicadorObrigatoriedadeCpfCnpjRA;

	private Short indicadorConsiderarApenasDebitoTitularAtual;

	private ClienteRelacaoTipo clienteRelacaoTipo;



	/**
	 * Description of the Field
	 */
	public final static Integer INDICADOR_MATRICULA_OBRIGATORIO = 1;

	/**
	 * Description of the Field
	 */
	public final static Integer INDICADOR_MATRICULA_NAO_OBRIGATORIO = 2;

	/**
	 * Description of the Field
	 */
	public final static Integer INDICADOR_GERACAO_CREDITO_SIM = 1;

	/**
	 * Description of the Field
	 */
	public final static Integer INDICADOR_GERACAO_CREDITO_NAO = 2;

	/**
	 * Description of the Field
	 */
	public final static Integer INDICADOR_GERACAO_DEBITO_SIM = 1;

	/**
	 * Description of the Field
	 */
	public final static Integer INDICADOR_CALCULO_DATA_PREVISTA_ATENDIMENTO_NAO = 2;

	/**
	 * Description of the Field
	 */
	public final static Integer INDICADOR_CALCULO_DATA_PREVISTA_ATENDIMENTO_SIM = 1;

	/**
	 * Description of the Field
	 */
	public final static Integer INDICADOR_GERACAO_DEBITO_NAO = 2;

	public final static Integer INDICADOR_DEBITOS_CLIENTES = 1;

	public final static Integer INDICADOR_DEBITOS_IMOVEIS = 2;

	public final static Integer INDICADOR_DEBITOS_AMBOS = 3;


	/** full constructor */
	public SolicitacaoTipoEspecificacao(String descricao, Integer diasPrazo, Integer horasPrazo, short indicadorPavimentoCalcada,
										short indicadorPavimentoRua, Integer indicadorCobrancaMaterial, Integer indicadorMatricula,
										Integer indicadorParecerEncerramento, Integer indicadorGeracaoDebito,
										Integer indicadorGeracaoCredito, Date ultimaAlteracao, short indicadorGeracaoOrdemServico,
										Short indicadorCliente,
										gcom.atendimentopublico.registroatendimento.SolicitacaoTipo solicitacaoTipo,
										UnidadeOrganizacional unidadeOrganizacional,
										gcom.atendimentopublico.ordemservico.ServicoTipo servicoTipo,
										EspecificacaoImovelSituacao especificacaoImovelSituacao) {

		this.descricao = descricao;
		// this.diasPrazo = diasPrazo;
		this.horasPrazo = horasPrazo;
		this.indicadorPavimentoCalcada = indicadorPavimentoCalcada;
		this.indicadorPavimentoRua = indicadorPavimentoRua;
		this.indicadorCobrancaMaterial = indicadorCobrancaMaterial;
		this.indicadorMatricula = indicadorMatricula;
		this.indicadorParecerEncerramento = indicadorParecerEncerramento;
		this.indicadorGeracaoDebito = indicadorGeracaoDebito;
		this.indicadorGeracaoCredito = indicadorGeracaoCredito;
		this.ultimaAlteracao = ultimaAlteracao;
		this.indicadorGeracaoOrdemServico = indicadorGeracaoOrdemServico;
		this.indicadorCliente = indicadorCliente;
		this.solicitacaoTipo = solicitacaoTipo;
		this.unidadeOrganizacional = unidadeOrganizacional;
		this.servicoTipo = servicoTipo;
		this.especificacaoImovelSituacao = especificacaoImovelSituacao;
	}

	public EspecificacaoImovelSituacao getEspecificacaoImovelSituacao(){

		return especificacaoImovelSituacao;
	}

	public void setEspecificacaoImovelSituacao(EspecificacaoImovelSituacao especificacaoImovelSituacao){

		this.especificacaoImovelSituacao = especificacaoImovelSituacao;
	}

	/** default constructor */
	public SolicitacaoTipoEspecificacao() {

	}

	/** minimal constructor */
	public SolicitacaoTipoEspecificacao(short indicadorPavimentoCalcada, short indicadorPavimentoRua, Date ultimaAlteracao,
										short indicadorGeracaoOrdemServico,
										gcom.atendimentopublico.registroatendimento.SolicitacaoTipo solicitacaoTipo,
										UnidadeOrganizacional unidadeOrganizacional,
										gcom.atendimentopublico.ordemservico.ServicoTipo servicoTipo) {

		this.indicadorPavimentoCalcada = indicadorPavimentoCalcada;
		this.indicadorPavimentoRua = indicadorPavimentoRua;
		this.ultimaAlteracao = ultimaAlteracao;
		this.indicadorGeracaoOrdemServico = indicadorGeracaoOrdemServico;
		this.solicitacaoTipo = solicitacaoTipo;
		this.unidadeOrganizacional = unidadeOrganizacional;
		this.servicoTipo = servicoTipo;
	}

	public Short getIndicadorColetor(){

		return indicadorColetor;
	}

	public void setIndicadorColetor(Short indicadorColetor){

		this.indicadorColetor = indicadorColetor;
	}

	public short getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(short indicadorUso){

		this.indicadorUso = indicadorUso;
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

	// Atributo eliminado para se trabalhar com prazo em Horas
	// public Integer getDiasPrazo() {
	// return this.diasPrazo;
	// }
	//
	// public void setDiasPrazo(Integer diasPrazo) {
	// this.diasPrazo = diasPrazo;
	// }

	public short getIndicadorPavimentoCalcada(){

		return this.indicadorPavimentoCalcada;
	}

	public void setIndicadorPavimentoCalcada(short indicadorPavimentoCalcada){

		this.indicadorPavimentoCalcada = indicadorPavimentoCalcada;
	}

	public short getIndicadorPavimentoRua(){

		return this.indicadorPavimentoRua;
	}

	public void setIndicadorPavimentoRua(short indicadorPavimentoRua){

		this.indicadorPavimentoRua = indicadorPavimentoRua;
	}

	public Integer getIndicadorCobrancaMaterial(){

		return this.indicadorCobrancaMaterial;
	}

	public void setIndicadorCobrancaMaterial(Integer indicadorCobrancaMaterial){

		this.indicadorCobrancaMaterial = indicadorCobrancaMaterial;
	}

	public Integer getIndicadorMatricula(){

		return this.indicadorMatricula;
	}

	public void setIndicadorMatricula(Integer indicadorMatricula){

		this.indicadorMatricula = indicadorMatricula;
	}

	public Integer getIndicadorParecerEncerramento(){

		return this.indicadorParecerEncerramento;
	}

	public void setIndicadorParecerEncerramento(Integer indicadorParecerEncerramento){

		this.indicadorParecerEncerramento = indicadorParecerEncerramento;
	}

	public Integer getIndicadorGeracaoDebito(){

		return this.indicadorGeracaoDebito;
	}

	public void setIndicadorGeracaoDebito(Integer indicadorGeracaoDebito){

		this.indicadorGeracaoDebito = indicadorGeracaoDebito;
	}

	public Integer getIndicadorGeracaoCredito(){

		return this.indicadorGeracaoCredito;
	}

	public void setIndicadorGeracaoCredito(Integer indicadorGeracaoCredito){

		this.indicadorGeracaoCredito = indicadorGeracaoCredito;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public short getIndicadorGeracaoOrdemServico(){

		return this.indicadorGeracaoOrdemServico;
	}

	public void setIndicadorGeracaoOrdemServico(short indicadorGeracaoOrdemServico){

		this.indicadorGeracaoOrdemServico = indicadorGeracaoOrdemServico;
	}

	public Short getIndicadorCliente(){

		return this.indicadorCliente;
	}

	public void setIndicadorCliente(Short indicadorCliente){

		this.indicadorCliente = indicadorCliente;
	}

	public gcom.atendimentopublico.registroatendimento.SolicitacaoTipo getSolicitacaoTipo(){

		return this.solicitacaoTipo;
	}

	public void setSolicitacaoTipo(gcom.atendimentopublico.registroatendimento.SolicitacaoTipo solicitacaoTipo){

		this.solicitacaoTipo = solicitacaoTipo;
	}

	public UnidadeOrganizacional getUnidadeOrganizacional(){

		return this.unidadeOrganizacional;
	}

	public void setUnidadeOrganizacional(UnidadeOrganizacional unidadeOrganizacional){

		this.unidadeOrganizacional = unidadeOrganizacional;
	}

	public gcom.atendimentopublico.ordemservico.ServicoTipo getServicoTipo(){

		return this.servicoTipo;
	}

	public void setServicoTipo(gcom.atendimentopublico.ordemservico.ServicoTipo servicoTipo){

		this.servicoTipo = servicoTipo;
	}

	public Set getEspecificacaoServicoTipos(){

		return especificacaoServicoTipos;
	}

	public void setEspecificacaoServicoTipos(Set especificacaoServicoTipos){

		this.especificacaoServicoTipos = especificacaoServicoTipos;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public Short getIndicadorLigacaoAgua(){

		return indicadorLigacaoAgua;
	}

	public void setIndicadorLigacaoAgua(Short indicadorLigacaoAgua){

		this.indicadorLigacaoAgua = indicadorLigacaoAgua;
	}

	public boolean equals(Object other){

		if((this == other)){
			return true;
		}
		if(!(other instanceof SolicitacaoTipoEspecificacao)){
			return false;
		}
		SolicitacaoTipoEspecificacao castOther = (SolicitacaoTipoEspecificacao) other;

		return (this.getDescricao().equalsIgnoreCase(castOther.getDescricao()));
	}

	public Short getIndicadorSolicitante(){

		return indicadorSolicitante;
	}

	public void setIndicadorSolicitante(Short indicadorSolicitante){

		this.indicadorSolicitante = indicadorSolicitante;
	}

	public Short getIndicadorVerificarDebito(){

		return indicadorVerificarDebito;
	}

	public void setIndicadorVerificarDebito(Short indicadorVerificarDebito){

		this.indicadorVerificarDebito = indicadorVerificarDebito;
	}

	public Integer getHorasPrazo(){

		return horasPrazo;
	}

	public void setHorasPrazo(Integer horasPrazo){

		this.horasPrazo = horasPrazo;
	}

	/**
	 * @return the solicitacaoTipoEspecificacaoMensagem
	 */
	public SolicitacaoTipoEspecificacaoMensagem getSolicitacaoTipoEspecificacaoMensagem(){

		return solicitacaoTipoEspecificacaoMensagem;
	}

	/**
	 * @param solicitacaoTipoEspecificacaoMensagem
	 *            the solicitacaoTipoEspecificacaoMensagem to set
	 */
	public void setSolicitacaoTipoEspecificacaoMensagem(SolicitacaoTipoEspecificacaoMensagem solicitacaoTipoEspecificacaoMensagem){

		this.solicitacaoTipoEspecificacaoMensagem = solicitacaoTipoEspecificacaoMensagem;
	}

	public Short getIndicadorContaEmRevisao(){

		return indicadorContaEmRevisao;
	}

	public void setIndicadorContaEmRevisao(Short indicadorContaEmRevisao){

		this.indicadorContaEmRevisao = indicadorContaEmRevisao;
	}

	public Short getIndicadorMensagemAlertaRevisao(){

		return indicadorMensagemAlertaRevisao;
	}

	public void setIndicadorMensagemAlertaRevisao(Short indicadorMensagemAlertaRevisao){

		this.indicadorMensagemAlertaRevisao = indicadorMensagemAlertaRevisao;
	}

	public Filtro retornaFiltro(){

		return null;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"id"};
		return retorno;

	}

	public Short getIndicadorObrigatoriedadeRgRA(){

		return indicadorObrigatoriedadeRgRA;
	}

	public void setIndicadorObrigatoriedadeRgRA(Short indicadorObrigatoriedadeRgRA){

		this.indicadorObrigatoriedadeRgRA = indicadorObrigatoriedadeRgRA;
	}

	public Short getIndicadorObrigatoriedadeCpfCnpjRA(){

		return indicadorObrigatoriedadeCpfCnpjRA;
	}

	public void setIndicadorObrigatoriedadeCpfCnpjRA(Short indicadorObrigatoriedadeCpfCnpjRA){

		this.indicadorObrigatoriedadeCpfCnpjRA = indicadorObrigatoriedadeCpfCnpjRA;
	}

	public String getDescricaoComId(){

		String descricaoComId = null;

		Integer id = this.getId();
		String descricao = this.getDescricao();

		if(id.compareTo(10) == -1){
			descricaoComId = "0" + id + " - " + descricao;
		}else{
			descricaoComId = id + " - " + descricao;
		}

		return descricaoComId;
	}

	public void setIndicadorTipoVerificarDebito(Short indicadorTipoVerificarDebito){

		this.indicadorTipoVerificarDebito = indicadorTipoVerificarDebito;
	}

	public Short getIndicadorTipoVerificarDebito(){

		return indicadorTipoVerificarDebito;
	}

	public void setIndicadorCalculoDataPrevistaAtendimento(Short indicadorCalculoDataPrevistaAtendimento){

		this.indicadorCalculoDataPrevistaAtendimento = indicadorCalculoDataPrevistaAtendimento;
	}

	public Short getIndicadorCalculoDataPrevistaAtendimento(){

		return indicadorCalculoDataPrevistaAtendimento;
	}

	public Short getIndicadorConsiderarApenasDebitoTitularAtual(){

		return indicadorConsiderarApenasDebitoTitularAtual;
	}

	public void setIndicadorConsiderarApenasDebitoTitularAtual(Short indicadorConsiderarApenasDebitoTitularAtual){

		this.indicadorConsiderarApenasDebitoTitularAtual = indicadorConsiderarApenasDebitoTitularAtual;
	}

	public ClienteRelacaoTipo getClienteRelacaoTipo(){

		return clienteRelacaoTipo;
	}

	public void setClienteRelacaoTipo(ClienteRelacaoTipo clienteRelacaoTipo){

		this.clienteRelacaoTipo = clienteRelacaoTipo;
	}

}
