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

package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * Filtrar Registro Atendimento
 * 
 * @author Leonardo Regis
 * @author eduardo henrique
 * @date 10/03/2009 - Inclusão de filtros de Datas de Previsão Inicial e Final de Atendimento
 */
public class FiltrarRegistroAtendimentoActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String numeroRA;

	private String numeroRAManual;

	private String matriculaImovel;

	private String inscricaoImovel;

	private String situacao;

	private String[] tipoSolicitacao;

	private String[] especificacao;

	private String periodoAtendimentoInicial;

	private String periodoAtendimentoFinal;

	private String periodoEncerramentoInicial;

	private String periodoEncerramentoFinal;

	private String periodoTramiteInicial;

	private String periodoTramiteFinal;

	private String unidadeAtendimentoId;

	private String unidadeAtendimentoDescricao;

	private String unidadeAtualId;

	private String unidadeAtualDescricao;

	private String unidadeSuperiorId;

	private String unidadeSuperiorDescricao;

	private String municipioId;

	private String municipioDescricao;

	private String bairroId;

	private String bairroCodigo;

	private String bairroDescricao;

	private String areaBairroId;

	private String logradouroId;

	private String logradouroDescricao;

	private String loginUsuario;

	private String nomeUsuario;

	private String periodoPrevistoAtendimentoInicial;

	private String periodoPrevistoAtendimentoFinal;

	private String cpf;

	private String cnpj;

	private String solicitante;

	private String codigoCliente;

	private String nomeCliente;

	private String tipoPesquisa;

	private String motivoReativacao;

	private String matriculaAtendente;

	private String nomeAtendente;

	private String indicadorOrdemServicoGerada;

	private String indicadorGeradaPelaUnidadeAtual;

	private String indicadorProcessoAdmJud;

	// Controle
	private String validaImovel = "false";

	private String validaUnidadeAtendimento = "false";

	private String validaUnidadeAtual = "false";

	private String validaUnidadeSuperior = "false";

	private String validaMunicipio = "false";

	private String validaBairro = "false";

	private String validaLogradouro = "false";

	private String selectedTipoSolicitacaoSize = "0";

	private String situacaoId;

	private String indicadorRaVencidas;

	private String indicadorRaPagamento;

	private String indicadorRaDevolucao;

	private RegistroAtendimento ra = new RegistroAtendimento();

	public String getIndicadorRaVencidas(){

		return indicadorRaVencidas;
	}

	public void setIndicadorRaVencidas(String indicadorRaVencidas){

		this.indicadorRaVencidas = indicadorRaVencidas;
	}

	public String getIndicadorRaPagamento(){

		return indicadorRaPagamento;
	}

	public void setIndicadorRaPagamento(String indicadorRaPagamento){

		this.indicadorRaPagamento = indicadorRaPagamento;
	}

	public String getIndicadorRaDevolucao(){

		return indicadorRaDevolucao;
	}

	public void setIndicadorRaDevolucao(String indicadorRaDevolucao){

		this.indicadorRaDevolucao = indicadorRaDevolucao;
	}

	public String getBairroCodigo(){

		return bairroCodigo;
	}

	public void setBairroCodigo(String bairroCodigo){

		this.bairroCodigo = bairroCodigo;
	}

	public String getNumeroRAManual(){

		return numeroRAManual;
	}

	public void setNumeroRAManual(String numeroRAManual){

		this.numeroRAManual = numeroRAManual;
	}

	public RegistroAtendimento getRa(){

		return ra;
	}

	public void setRa(RegistroAtendimento ra){

		this.ra = ra;
	}

	public String getSelectedTipoSolicitacaoSize(){

		return selectedTipoSolicitacaoSize;
	}

	public void setSelectedTipoSolicitacaoSize(String selectedTipoSolicitacaoSize){

		this.selectedTipoSolicitacaoSize = selectedTipoSolicitacaoSize;
	}

	public void resetRA(){

		numeroRA = null;
		matriculaImovel = null;
		inscricaoImovel = null;
		situacao = null;
		tipoSolicitacao = null;
		especificacao = null;
		periodoAtendimentoInicial = null;
		periodoAtendimentoFinal = null;
		periodoEncerramentoInicial = null;
		periodoEncerramentoFinal = null;
		unidadeAtendimentoId = null;
		unidadeAtendimentoDescricao = null;
		unidadeAtualId = null;
		unidadeAtualDescricao = null;
		unidadeSuperiorId = null;
		unidadeSuperiorDescricao = null;
		municipioId = null;
		municipioDescricao = null;
		bairroId = null;
		bairroDescricao = null;
		areaBairroId = null;
		logradouroId = null;
		logradouroDescricao = null;
		loginUsuario = null;
		nomeUsuario = null;
	}

	public String getAreaBairroId(){

		return areaBairroId;
	}

	public void setAreaBairroId(String areaBairroId){

		this.areaBairroId = areaBairroId;
	}

	public String getBairroDescricao(){

		return bairroDescricao;
	}

	public void setBairroDescricao(String bairroDescricao){

		this.bairroDescricao = bairroDescricao;
	}

	public String getBairroId(){

		return bairroId;
	}

	public void setBairroId(String bairroId){

		this.bairroId = bairroId;
	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	public String getLogradouroDescricao(){

		return logradouroDescricao;
	}

	public void setLogradouroDescricao(String logradouroDescricao){

		this.logradouroDescricao = logradouroDescricao;
	}

	public String getLogradouroId(){

		return logradouroId;
	}

	public void setLogradouroId(String logradouroId){

		this.logradouroId = logradouroId;
	}

	public String getMatriculaImovel(){

		return matriculaImovel;
	}

	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}

	public String getMunicipioDescricao(){

		return municipioDescricao;
	}

	public void setMunicipioDescricao(String municipioDescricao){

		this.municipioDescricao = municipioDescricao;
	}

	public String getMunicipioId(){

		return municipioId;
	}

	public void setMunicipioId(String municipioId){

		this.municipioId = municipioId;
	}

	public String getPeriodoAtendimentoFinal(){

		return periodoAtendimentoFinal;
	}

	public void setPeriodoAtendimentoFinal(String periodoAtendimentoFinal){

		this.periodoAtendimentoFinal = periodoAtendimentoFinal;
	}

	public String getPeriodoAtendimentoInicial(){

		return periodoAtendimentoInicial;
	}

	public void setPeriodoAtendimentoInicial(String periodoAtendimentoInicial){

		this.periodoAtendimentoInicial = periodoAtendimentoInicial;
	}

	public String getPeriodoEncerramentoFinal(){

		return periodoEncerramentoFinal;
	}

	public void setPeriodoEncerramentoFinal(String periodoEncerramentoFinal){

		this.periodoEncerramentoFinal = periodoEncerramentoFinal;
	}

	public String getPeriodoEncerramentoInicial(){

		return periodoEncerramentoInicial;
	}

	public void setPeriodoEncerramentoInicial(String periodoEncerramentoInicial){

		this.periodoEncerramentoInicial = periodoEncerramentoInicial;
	}

	public String getSituacao(){

		return situacao;
	}

	public void setSituacao(String situacao){

		this.situacao = situacao;
	}

	public String getUnidadeAtendimentoDescricao(){

		return unidadeAtendimentoDescricao;
	}

	public void setUnidadeAtendimentoDescricao(String unidadeAtendimentoDescricao){

		this.unidadeAtendimentoDescricao = unidadeAtendimentoDescricao;
	}

	public String getUnidadeAtendimentoId(){

		return unidadeAtendimentoId;
	}

	public void setUnidadeAtendimentoId(String unidadeAtendimentoId){

		this.unidadeAtendimentoId = unidadeAtendimentoId;
	}

	public String getUnidadeAtualDescricao(){

		return unidadeAtualDescricao;
	}

	public void setUnidadeAtualDescricao(String unidadeAtualDescricao){

		this.unidadeAtualDescricao = unidadeAtualDescricao;
	}

	public String getUnidadeAtualId(){

		return unidadeAtualId;
	}

	public void setUnidadeAtualId(String unidadeAtualId){

		this.unidadeAtualId = unidadeAtualId;
	}

	public String getUnidadeSuperiorDescricao(){

		return unidadeSuperiorDescricao;
	}

	public void setUnidadeSuperiorDescricao(String unidadeSuperiorDescricao){

		this.unidadeSuperiorDescricao = unidadeSuperiorDescricao;
	}

	public String getUnidadeSuperiorId(){

		return unidadeSuperiorId;
	}

	public void setUnidadeSuperiorId(String unidadeSuperiorId){

		this.unidadeSuperiorId = unidadeSuperiorId;
	}

	public String getNumeroRA(){

		return numeroRA;
	}

	public void setNumeroRA(String numeroRA){

		this.numeroRA = numeroRA;
	}

	public String getValidaBairro(){

		return validaBairro;
	}

	public void setValidaBairro(String validaBairro){

		this.validaBairro = validaBairro;
	}

	public String getValidaImovel(){

		return validaImovel;
	}

	public void setValidaImovel(String validaImovel){

		this.validaImovel = validaImovel;
	}

	public String getValidaLogradouro(){

		return validaLogradouro;
	}

	public void setValidaLogradouro(String validaLogradouro){

		this.validaLogradouro = validaLogradouro;
	}

	public String getValidaMunicipio(){

		return validaMunicipio;
	}

	public void setValidaMunicipio(String validaMunicipio){

		this.validaMunicipio = validaMunicipio;
	}

	public String getValidaUnidadeAtendimento(){

		return validaUnidadeAtendimento;
	}

	public void setValidaUnidadeAtendimento(String validaUnidadeAtendimento){

		this.validaUnidadeAtendimento = validaUnidadeAtendimento;
	}

	public String getValidaUnidadeAtual(){

		return validaUnidadeAtual;
	}

	public void setValidaUnidadeAtual(String validaUnidadeAtual){

		this.validaUnidadeAtual = validaUnidadeAtual;
	}

	public String getValidaUnidadeSuperior(){

		return validaUnidadeSuperior;
	}

	public void setValidaUnidadeSuperior(String validaUnidadeSuperior){

		this.validaUnidadeSuperior = validaUnidadeSuperior;
	}

	public String[] getEspecificacao(){

		return especificacao;
	}

	public void setEspecificacao(String[] especificacao){

		this.especificacao = especificacao;
	}

	public String[] getTipoSolicitacao(){

		return tipoSolicitacao;
	}

	public void setTipoSolicitacao(String[] tipoSolicitacao){

		this.tipoSolicitacao = tipoSolicitacao;
	}

	public String getSituacaoId(){

		return situacaoId;
	}

	public void setSituacaoId(String situacaoId){

		this.situacaoId = situacaoId;
	}

	public String getLoginUsuario(){

		return loginUsuario;
	}

	public void setLoginUsuario(String loginUsuario){

		this.loginUsuario = loginUsuario;
	}

	public String getNomeUsuario(){

		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario){

		this.nomeUsuario = nomeUsuario;
	}

	public String getPeriodoPrevistoAtendimentoInicial(){

		return periodoPrevistoAtendimentoInicial;
	}

	public void setPeriodoPrevistoAtendimentoInicial(String periodoPrevistoAtendimentoInicial){

		this.periodoPrevistoAtendimentoInicial = periodoPrevistoAtendimentoInicial;
	}

	public String getPeriodoPrevistoAtendimentoFinal(){

		return periodoPrevistoAtendimentoFinal;
	}

	public void setPeriodoPrevistoAtendimentoFinal(String periodoPrevistoAtendimentoFinal){

		this.periodoPrevistoAtendimentoFinal = periodoPrevistoAtendimentoFinal;
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

	public String getSolicitante(){

		return solicitante;
	}

	public void setSolicitante(String solicitante){

		this.solicitante = solicitante;
	}

	public String getTipoPesquisa(){

		return tipoPesquisa;
	}

	public void setTipoPesquisa(String tipoPesquisa){

		this.tipoPesquisa = tipoPesquisa;
	}

	public String getMotivoReativacao(){

		return motivoReativacao;
	}

	public void setMotivoReativacao(String motivoReativacao){

		this.motivoReativacao = motivoReativacao;
	}

	public String getMatriculaAtendente(){

		return matriculaAtendente;
	}

	public void setMatriculaAtendente(String matriculaAtendente){

		this.matriculaAtendente = matriculaAtendente;
	}

	public String getNomeAtendente(){

		return nomeAtendente;
	}

	public void setNomeAtendente(String nomeAtendente){

		this.nomeAtendente = nomeAtendente;
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

	public static long getSerialversionuid(){

		return serialVersionUID;
	}

	public String getPeriodoTramiteInicial(){

		return periodoTramiteInicial;
	}

	public void setPeriodoTramiteInicial(String periodoTramiteInicial){

		this.periodoTramiteInicial = periodoTramiteInicial;
	}

	public String getPeriodoTramiteFinal(){

		return periodoTramiteFinal;
	}

	public void setPeriodoTramiteFinal(String periodoTramiteFinal){

		this.periodoTramiteFinal = periodoTramiteFinal;
	}

	public String getCodigoCliente(){

		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente){

		this.codigoCliente = codigoCliente;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
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