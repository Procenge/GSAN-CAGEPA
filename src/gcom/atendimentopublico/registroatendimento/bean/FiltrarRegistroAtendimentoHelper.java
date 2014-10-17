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

package gcom.atendimentopublico.registroatendimento.bean;

import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimentoUnidade;
import gcom.cadastro.unidade.UnidadeOrganizacional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * Classe que irá auxiliar no formato de entrada da pesquisa
 * de registro de atendimento
 * 
 *@author Leonardo Regis
 *@date 08/09/2006
 * @author eduardo henrique
 *@date 10/03/2009 - Inclusão de filtros de Datas de Previsão Inicial e Final de Atendimento
 */
public class FiltrarRegistroAtendimentoHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private RegistroAtendimento registroAtendimento = null;

	private UnidadeOrganizacional unidadeAtendimento = null;

	private UnidadeOrganizacional unidadeAtual = null;

	private UnidadeOrganizacional unidadeExecutora = null;

	private UnidadeOrganizacional unidadeSuperior = null;

	private Date dataAtendimentoInicial = null;

	private Date dataAtendimentoFinal = null;

	private Date dataEncerramentoInicial = null;

	private Date dataEncerramentoFinal = null;

	private Date dataTramiteInicial = null;

	private Date dataTramiteFinal = null;

	private Collection<Integer> colecaoSituacoes = null;

	private Collection<Integer> colecaoTipoSolicitacaoEspecificacao = null;

	private Collection<Integer> colecaoTipoSolicitacao = null;

	private Set colecaoRAPorUnidades = null;

	private String municipioId = null;

	private String bairroId = null;

	private String bairroCodigo = null;

	private String logradouroId = null;

	private String matriculaAtendenteId = null;

	private Integer numeroPagina = null;

	private Date dataPrevisaoAtendimentoInicial = null;

	private Date dataPrevisaoAtendimentoFinal = null;

	private String codigoCliente;

	private String solicitante;

	private String cpf;

	private String cnpj;

	private boolean contem;

	private boolean iniciado;

	private Collection<Integer> colecaoUnidadeAtendimento = null;

	private Collection<Integer> colecaoUnidadeAtual = null;

	private Collection<Integer> colecaoUnidadesSubordinadas = null;

	private Short codigoSituacao;

	private RegistroAtendimentoUnidade registroAtendimentoUnidade = null;

	private String indicadorOrdemServicoGerada;

	private String indicadorGeradaPelaUnidadeAtual;

	private String indicadorProcessoAdmJud;

	private BigDecimal coordenadaNorteBaixa;

	private BigDecimal coordenadaNorteAlta;

	private BigDecimal coordenadaLesteEsquerda;

	private BigDecimal coordenadaLesteDireita;

	public FiltrarRegistroAtendimentoHelper() {

	}

	public String getBairroId(){

		return bairroId;
	}

	public void setBairroId(String bairroId){

		this.bairroId = bairroId;
	}

	public Collection<Integer> getColecaoTipoSolicitacaoEspecificacao(){

		return colecaoTipoSolicitacaoEspecificacao;
	}

	public void setColecaoTipoSolicitacaoEspecificacao(Collection<Integer> colecaoTipoSolicitacaoEspecificacao){

		this.colecaoTipoSolicitacaoEspecificacao = colecaoTipoSolicitacaoEspecificacao;
	}

	public Date getDataAtendimentoFinal(){

		return dataAtendimentoFinal;
	}

	public void setDataAtendimentoFinal(Date dataAtendimentoFinal){

		this.dataAtendimentoFinal = dataAtendimentoFinal;
	}

	public Date getDataAtendimentoInicial(){

		return dataAtendimentoInicial;
	}

	public void setDataAtendimentoInicial(Date dataAtendimentoInicial){

		this.dataAtendimentoInicial = dataAtendimentoInicial;
	}

	public Date getDataEncerramentoFinal(){

		return dataEncerramentoFinal;
	}

	public void setDataEncerramentoFinal(Date dataEncerramentoFinal){

		this.dataEncerramentoFinal = dataEncerramentoFinal;
	}

	public Date getDataEncerramentoInicial(){

		return dataEncerramentoInicial;
	}

	public void setDataEncerramentoInicial(Date dataEncerramentoInicial){

		this.dataEncerramentoInicial = dataEncerramentoInicial;
	}

	public String getLogradouroId(){

		return logradouroId;
	}

	public void setLogradouroId(String logradouroId){

		this.logradouroId = logradouroId;
	}

	public String getMunicipioId(){

		return municipioId;
	}

	public void setMunicipioId(String municipioId){

		this.municipioId = municipioId;
	}

	public Integer getNumeroPagina(){

		return numeroPagina;
	}

	public void setNumeroPagina(Integer numeroPagina){

		this.numeroPagina = numeroPagina;
	}

	public RegistroAtendimento getRegistroAtendimento(){

		return registroAtendimento;
	}

	public void setRegistroAtendimento(RegistroAtendimento registroAtendimento){

		this.registroAtendimento = registroAtendimento;
	}

	public UnidadeOrganizacional getUnidadeAtendimento(){

		return unidadeAtendimento;
	}

	public void setUnidadeAtendimento(UnidadeOrganizacional unidadeAtendimento){

		this.unidadeAtendimento = unidadeAtendimento;
	}

	public UnidadeOrganizacional getUnidadeAtual(){

		return unidadeAtual;
	}

	public void setUnidadeAtual(UnidadeOrganizacional unidadeAtual){

		this.unidadeAtual = unidadeAtual;
	}

	public UnidadeOrganizacional getUnidadeSuperior(){

		return unidadeSuperior;
	}

	public void setUnidadeSuperior(UnidadeOrganizacional unidadeSuperior){

		this.unidadeSuperior = unidadeSuperior;
	}

	public Set getColecaoRAPorUnidades(){

		return colecaoRAPorUnidades;
	}

	public void setColecaoRAPorUnidades(Set colecaoRAPorUnidades){

		this.colecaoRAPorUnidades = colecaoRAPorUnidades;
	}

	public Collection<Integer> getColecaoTipoSolicitacao(){

		return colecaoTipoSolicitacao;
	}

	public void setColecaoTipoSolicitacao(Collection<Integer> colecaoTipoSolicitacao){

		this.colecaoTipoSolicitacao = colecaoTipoSolicitacao;
	}

	public RegistroAtendimentoUnidade getRegistroAtendimentoUnidade(){

		return registroAtendimentoUnidade;
	}

	public void setRegistroAtendimentoUnidade(RegistroAtendimentoUnidade registroAtendimentoUnidade){

		this.registroAtendimentoUnidade = registroAtendimentoUnidade;
	}

	public String getBairroCodigo(){

		return bairroCodigo;
	}

	public void setBairroCodigo(String bairroCodigo){

		this.bairroCodigo = bairroCodigo;
	}

	public Date getDataPrevisaoAtendimentoInicial(){

		return dataPrevisaoAtendimentoInicial;
	}

	public void setDataPrevisaoAtendimentoInicial(Date dataPrevisaoAtendimentoInicial){

		this.dataPrevisaoAtendimentoInicial = dataPrevisaoAtendimentoInicial;
	}

	public Date getDataPrevisaoAtendimentoFinal(){

		return dataPrevisaoAtendimentoFinal;
	}

	public void setDataPrevisaoAtendimentoFinal(Date dataPrevisaoAtendimentoFinal){

		this.dataPrevisaoAtendimentoFinal = dataPrevisaoAtendimentoFinal;
	}

	public String getNomeSolicitante(){

		return solicitante;
	}

	public void setSolicitante(String nomeSolicitante){

		this.solicitante = nomeSolicitante;
	}

	public String getCpf(){

		return cpf;
	}

	public void setCpf(String cpf){

		this.cpf = cpf;
	}

	public String getCnpj(){

		return cnpj;
	}

	public void setCnpj(String cnpj){

		this.cnpj = cnpj;
	}

	public boolean isContem(){

		return contem;
	}

	public void setContem(boolean contem){

		this.contem = contem;
	}

	public boolean isIniciado(){

		return iniciado;
	}

	public void setIniciado(boolean iniciado){

		this.iniciado = iniciado;
	}

	public Collection<Integer> getColecaoUnidadeAtendimento(){

		return colecaoUnidadeAtendimento;
	}

	public void setColecaoUnidadeAtendimento(Collection<Integer> colecaoUnidadeAtendimento){

		this.colecaoUnidadeAtendimento = colecaoUnidadeAtendimento;
	}

	public Collection<Integer> getColecaoUnidadeAtual(){

		return colecaoUnidadeAtual;
	}

	public void setColecaoUnidadeAtual(Collection<Integer> colecaoUnidadeAtual){

		this.colecaoUnidadeAtual = colecaoUnidadeAtual;
	}

	public Short getCodigoSituacao(){

		return codigoSituacao;
	}

	public void setCodigoSituacao(Short codigoSituacao){

		this.codigoSituacao = codigoSituacao;
	}

	public Collection<Integer> getColecaoUnidadesSubordinadas(){

		return colecaoUnidadesSubordinadas;
	}

	public void setColecaoUnidadesSubordinadas(Collection<Integer> colecaoUnidadesSubordinadas){

		this.colecaoUnidadesSubordinadas = colecaoUnidadesSubordinadas;
	}

	public String getMatriculaAtendenteId(){

		return matriculaAtendenteId;
	}

	public void setMatriculaAtendenteId(String matriculaAtendenteId){

		this.matriculaAtendenteId = matriculaAtendenteId;
	}

	public String getIndicadorOrdemServicoGerada(){

		return indicadorOrdemServicoGerada;
	}

	public void setIndicadorOrdemServicoGerada(String indicadorOrdemServicoGerada){

		this.indicadorOrdemServicoGerada = indicadorOrdemServicoGerada;
	}

	public String getIndicadorGeradaPelaUnidadeAtual(){

		return indicadorGeradaPelaUnidadeAtual;
	}

	public void setIndicadorGeradaPelaUnidadeAtual(String indicadorGeradaPelaUnidadeAtual){

		this.indicadorGeradaPelaUnidadeAtual = indicadorGeradaPelaUnidadeAtual;
	}

	public Date getDataTramiteInicial(){

		return dataTramiteInicial;
	}

	public void setDataTramiteInicial(Date dataTramiteInicial){

		this.dataTramiteInicial = dataTramiteInicial;
	}

	public Date getDataTramiteFinal(){

		return dataTramiteFinal;
	}

	public void setDataTramiteFinal(Date dataTramiteFinal){

		this.dataTramiteFinal = dataTramiteFinal;
	}

	public BigDecimal getCoordenadaNorteBaixa(){

		return coordenadaNorteBaixa;
	}

	public void setCoordenadaNorteBaixa(BigDecimal coordenadaNorteBaixa){

		this.coordenadaNorteBaixa = coordenadaNorteBaixa;
	}

	public BigDecimal getCoordenadaNorteAlta(){

		return coordenadaNorteAlta;
	}

	public void setCoordenadaNorteAlta(BigDecimal coordenadaNorteAlta){

		this.coordenadaNorteAlta = coordenadaNorteAlta;
	}


	public BigDecimal getCoordenadaLesteEsquerda(){

		return coordenadaLesteEsquerda;
	}

	public void setCoordenadaLesteEsquerda(BigDecimal coordenadaLesteEsquerda){

		this.coordenadaLesteEsquerda = coordenadaLesteEsquerda;
	}

	public BigDecimal getCoordenadaLesteDireita(){

		return coordenadaLesteDireita;
	}

	public void setCoordenadaLesteDireita(BigDecimal coordenadaLesteDireita){

		this.coordenadaLesteDireita = coordenadaLesteDireita;
	}

	public String getSolicitante(){

		return solicitante;
	}

	public UnidadeOrganizacional getUnidadeExecutora(){

		return unidadeExecutora;
	}

	public void setUnidadeExecutora(UnidadeOrganizacional unidadeExecutora){

		this.unidadeExecutora = unidadeExecutora;
	}

	public Collection<Integer> getColecaoSituacoes(){

		return colecaoSituacoes;
	}

	public void setColecaoSituacoes(Collection<Integer> colecaoSituacoes){

		this.colecaoSituacoes = colecaoSituacoes;
	}

	public String getCodigoCliente(){

		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente){

		this.codigoCliente = codigoCliente;
	}

	/**
	 * @return the indicadorProcessoAdmJud
	 */
	public String getIndicadorProcessoAdmJud(){

		return indicadorProcessoAdmJud;
	}

	/**
	 * @param indicadorProcessoAdmJud
	 *            the indicadorProcessoAdmJud to set
	 */
	public void setIndicadorProcessoAdmJud(String indicadorProcessoAdmJud){

		this.indicadorProcessoAdmJud = indicadorProcessoAdmJud;
	}

}