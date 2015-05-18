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
 * Copyright (C) <2013> 
 * * Vicente Vital Zarga Wanderley Lins de Santana
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

package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.bean.ObterDadosAtividadeIdOSHelper;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ConsultarDadosOrdemServicoActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	// Dados Gerais
	private OrdemServico ordemServico;

	private String numeroOS;

	private String numeroOSPesquisada;

	private String numeroOSParametro;

	private String situacaoOS;

	private String situacaoOSId;

	private String numeroRA;

	private String situacaoRA;

	private String numeroDocumentoCobranca;

	private String dataGeracao;

	private String numeroOSReferencia;

	private String tipoServicoId;

	private String tipoServicoDescricao;

	private String tipoServicoReferenciaId;

	private String tipoServicoReferenciaDescricao;

	private String retornoOSReferida;

	private String observacaoOs;

	private String observacaoRa;

	private String valorServicoOriginal;

	private String valorServicoAtual;

	private String prioridadeOriginal;

	private String prioridadeAtual;

	private String unidadeGeracaoId;

	private String unidadeGeracaoDescricao;

	private String usuarioGeracaoId;

	private String usuarioGeracaoNome;

	private String unidadeAtualId;

	private String unidadeAtualDescricao;

	private String dataUltimaEmissao;

	private String idOSServicoReparo;

	// Dados da Programação
	private String dataProgramacao;

	private String equipeProgramacao;

	// Dados Local Ocorrência
	private String enderecoOcorrencia;

	private String matriculaImovel;

	private String inscricaoImovel;

	private String rota;

	private String sequencialRota;

	// Dados de Execução de OS

	private String dataExecucao;

	private String dataEncerramento;

	private String parecerEncerramento;

	private String areaPavimentacao;

	private String comercialAtualizado;

	private String servicoCobrado;

	private String motivoNaoCobranca;

	private String percentualCobranca;

	private String valorCobrado;

	private String motivoEncerramento;

	private String unidadeEncerramentoId;

	private String unidadeEncerramentoDescricao;

	private String usuarioEncerramentoId;

	private String usuarioEncerramentoNome;
	
	private String usuarioEncerramentoLogin;

	// Vala
	private String numeroVala;

	private String idLocalOcorrencia;

	private String idPavimento;

	private String comprimentoVala;

	private String larguraVala;

	private String profundidadeVala;

	private String indicadorValaAterrada;

	private String indicadorEntulho;

	private String descricaoLocalOcorrencia;

	private String descricaoPavimento;

	private String quantidadeEntulho;

	private String descricaoEntulhoMedida;

	private String comprimentoTutulacaoAguaPluvial;

	private String diametroTutulacaoAguaPluvial;

	// Rede/Ramal Água
	private String idCausaAgua;

	private String idRedeRamalAgua;

	private String idDiametroAgua;

	private String idMaterialAgua;

	private String profundidadeAgua;

	private String pressaoAgua;

	private String idAgenteExternoAgua;

	private String descricaoCausaVazamentoAgua;

	private String descricaoDiametroAgua;

	private String descricaoMaterialAgua;

	private String descricaoAgenteExterno;

	private String descricaoAgua;

	// Rede/Ramal Esgoto
	private String idCausaEsgoto;

	private String idRedeRamalEsgoto;

	private String idDiametroEsgoto;

	private String idMaterialEsgoto;

	private String profundidadeEsgoto;

	private String pressaoEsgoto;

	private String idAgenteExternoEsgoto;

	private String descricaoCausaVazamentoEsgoto;

	private String descricaoDiametroEsgoto;

	private String descricaoMaterialEsgoto;

	private String descricaoEsgoto;

	private String exibirDadosRedeRamalAgua;

	private String exibirDadosRedeRamalEsgoto;

	private String permiteTramiteIndependente;

	private String possuiExecucaoServico;

	private String usuarioExecucaoId;

	private String usuarioExecucaoNome;

	private String leituraServico;

	private String tipoCorteId;

	private String tipoCorteDescricao;

	private String emissaoOSHabilitada;

	private String permiteGerarOSReparo;

	private String exibirDadosReparoOSPrincipal;

	private String idOSPrincipal;

	private String valorHorasTrabalhadas;

	private String valorMateriais;

	private Collection<ObterDadosAtividadeIdOSHelper> colecaoOSAtividade = new ArrayList<ObterDadosAtividadeIdOSHelper>();

	Collection<RoteiroOSDadosProgramacaoHelper> collectionRoteiroOSDadosProgramacaoHelpers = new ArrayList<RoteiroOSDadosProgramacaoHelper>();

	Collection<OSDadosInterrupcaoHelper> collectionOsInterrupcaoDeslocamentoHelpers = new ArrayList<OSDadosInterrupcaoHelper>();

	Collection<OSDadosInterrupcaoHelper> collectionOsInterrupcaoExecucaoHelpers = new ArrayList<OSDadosInterrupcaoHelper>();

	private String quantidadeDiasUnidade;

	private String dataPrevisaoCliente;

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest){

		this.ordemServico = null;
		this.situacaoOS = null;
		this.situacaoOSId = null;
		this.numeroRA = null;
		this.situacaoRA = null;
		this.numeroDocumentoCobranca = null;
		this.dataGeracao = null;
		this.numeroOSReferencia = null;
		this.tipoServicoId = null;
		this.tipoServicoDescricao = null;
		this.tipoServicoReferenciaId = null;
		this.tipoServicoReferenciaDescricao = null;
		this.retornoOSReferida = null;
		this.observacaoOs = null;
		this.observacaoRa = null;
		this.valorServicoOriginal = null;
		this.valorServicoAtual = null;
		this.prioridadeOriginal = null;
		this.prioridadeAtual = null;
		this.unidadeGeracaoId = null;
		this.unidadeGeracaoDescricao = null;
		this.usuarioGeracaoId = null;
		this.usuarioGeracaoNome = null;
		this.unidadeAtualId = null;
		this.unidadeAtualDescricao = null;
		this.dataUltimaEmissao = null;

		// Dados de Execução de OS
		this.dataExecucao = null;
		this.dataEncerramento = null;
		this.parecerEncerramento = null;
		this.areaPavimentacao = null;
		this.comercialAtualizado = null;
		this.servicoCobrado = null;
		this.motivoNaoCobranca = null;
		this.percentualCobranca = null;
		this.valorCobrado = null;
		this.motivoEncerramento = null;
		this.unidadeEncerramentoId = null;
		this.unidadeEncerramentoDescricao = null;
		this.usuarioEncerramentoId = null;
		this.usuarioEncerramentoNome = null;
		this.usuarioEncerramentoLogin = null;
		this.exibirDadosRedeRamalAgua = null;
		this.exibirDadosRedeRamalEsgoto = null;

		// Vala
		this.numeroVala = null;
		this.idLocalOcorrencia = null;
		this.idPavimento = null;
		this.comprimentoVala = null;
		this.larguraVala = null;
		this.profundidadeVala = null;
		this.indicadorValaAterrada = null;
		this.indicadorEntulho = null;
		this.descricaoLocalOcorrencia = null;
		this.descricaoPavimento = null;

		// Rede/Ramal Água
		this.idCausaAgua = null;
		this.idRedeRamalAgua = null;
		this.idDiametroAgua = null;
		this.idMaterialAgua = null;
		this.profundidadeAgua = null;
		this.pressaoAgua = null;
		this.idAgenteExternoAgua = null;
		this.descricaoCausaVazamentoAgua = null;
		this.descricaoDiametroAgua = null;
		this.descricaoMaterialAgua = null;
		this.descricaoAgenteExterno = null;
		this.descricaoAgua = null;

		// Rede/Ramal Esgoto
		this.idCausaEsgoto = null;
		this.idRedeRamalEsgoto = null;
		this.idDiametroEsgoto = null;
		this.idMaterialEsgoto = null;
		this.profundidadeEsgoto = null;
		this.pressaoEsgoto = null;
		this.idAgenteExternoEsgoto = null;
		this.descricaoCausaVazamentoEsgoto = null;
		this.descricaoDiametroEsgoto = null;
		this.descricaoMaterialEsgoto = null;
		this.descricaoEsgoto = null;

		this.emissaoOSHabilitada = null;
	}

	/**
	 * @return Retorna o campo motivoEncerramento.
	 */
	public String getMotivoEncerramento(){

		return motivoEncerramento;
	}

	/**
	 * @param motivoEncerramento
	 *            O motivoEncerramento a ser setado.
	 */
	public void setMotivoEncerramento(String motivoEncerramento){

		this.motivoEncerramento = motivoEncerramento;
	}

	public String getNumeroOSParametro(){

		return numeroOSParametro;
	}

	public void setNumeroOSParametro(String numeroOSParametro){

		this.numeroOSParametro = numeroOSParametro;
	}

	public String getNumeroOSPesquisada(){

		return numeroOSPesquisada;
	}

	public void setNumeroOSPesquisada(String numeroOSPesquisada){

		this.numeroOSPesquisada = numeroOSPesquisada;
	}

	public String getAreaPavimentacao(){

		return areaPavimentacao;
	}

	public void setAreaPavimentacao(String areaPavimentacao){

		this.areaPavimentacao = areaPavimentacao;
	}

	public String getComercialAtualizado(){

		return comercialAtualizado;
	}

	public void setComercialAtualizado(String comercialAtualizado){

		this.comercialAtualizado = comercialAtualizado;
	}

	public String getDataExecucao(){

		return dataExecucao;
	}

	public void setDataExecucao(String dataExecucao){

		this.dataExecucao = dataExecucao;
	}

	public String getDataEncerramento(){

		return dataEncerramento;
	}

	public void setDataEncerramento(String dataEncerramento){

		this.dataEncerramento = dataEncerramento;
	}

	public String getDataGeracao(){

		return dataGeracao;
	}

	public void setDataGeracao(String dataGeracao){

		this.dataGeracao = dataGeracao;
	}

	public String getDataUltimaEmissao(){

		return dataUltimaEmissao;
	}

	public void setDataUltimaEmissao(String dataUltimaEmissao){

		this.dataUltimaEmissao = dataUltimaEmissao;
	}

	public String getMotivoNaoCobranca(){

		return motivoNaoCobranca;
	}

	public void setMotivoNaoCobranca(String motivoNaoCobranca){

		this.motivoNaoCobranca = motivoNaoCobranca;
	}

	public String getNumeroDocumentoCobranca(){

		return numeroDocumentoCobranca;
	}

	public void setNumeroDocumentoCobranca(String numeroDocumentoCobranca){

		this.numeroDocumentoCobranca = numeroDocumentoCobranca;
	}

	public String getNumeroOS(){

		return numeroOS;
	}

	public void setNumeroOS(String numeroOS){

		this.numeroOS = numeroOS;
	}

	public String getNumeroOSReferencia(){

		return numeroOSReferencia;
	}

	public void setNumeroOSReferencia(String numeroOSReferencia){

		this.numeroOSReferencia = numeroOSReferencia;
	}

	public String getNumeroRA(){

		return numeroRA;
	}

	public void setNumeroRA(String numeroRA){

		this.numeroRA = numeroRA;
	}

	public String getObservacaoOs(){

		return observacaoOs;
	}

	public final String getObservacaoRa(){

		return observacaoRa;
	}

	public final void setObservacaoRa(String observacaoRa){

		this.observacaoRa = observacaoRa;
	}

	public void setObservacaoOs(String observacao){

		this.observacaoOs = observacao;
	}

	public String getParecerEncerramento(){

		return parecerEncerramento;
	}

	public void setParecerEncerramento(String parecerEncerramento){

		this.parecerEncerramento = parecerEncerramento;
	}

	public String getPercentualCobranca(){

		return percentualCobranca;
	}

	public void setPercentualCobranca(String percentualCobranca){

		this.percentualCobranca = percentualCobranca;
	}

	public String getPrioridadeAtual(){

		return prioridadeAtual;
	}

	public void setPrioridadeAtual(String prioridadeAtual){

		this.prioridadeAtual = prioridadeAtual;
	}

	public String getPrioridadeOriginal(){

		return prioridadeOriginal;
	}

	public void setPrioridadeOriginal(String prioridadeOriginal){

		this.prioridadeOriginal = prioridadeOriginal;
	}

	public String getRetornoOSReferida(){

		return retornoOSReferida;
	}

	public void setRetornoOSReferida(String retornoOSReferida){

		this.retornoOSReferida = retornoOSReferida;
	}

	public String getSituacaoOS(){

		return situacaoOS;
	}

	public void setSituacaoOS(String situacaoOS){

		this.situacaoOS = situacaoOS;
	}

	public String getSituacaoRA(){

		return situacaoRA;
	}

	public void setSituacaoRA(String situacaoRA){

		this.situacaoRA = situacaoRA;
	}

	public String getTipoServicoDescricao(){

		return tipoServicoDescricao;
	}

	public void setTipoServicoDescricao(String tipoServicoDescricao){

		this.tipoServicoDescricao = tipoServicoDescricao;
	}

	public String getTipoServicoId(){

		return tipoServicoId;
	}

	public void setTipoServicoId(String tipoServicoId){

		this.tipoServicoId = tipoServicoId;
	}

	public String getTipoServicoReferenciaDescricao(){

		return tipoServicoReferenciaDescricao;
	}

	public void setTipoServicoReferenciaDescricao(String tipoServicoReferenciaDescricao){

		this.tipoServicoReferenciaDescricao = tipoServicoReferenciaDescricao;
	}

	public String getTipoServicoReferenciaId(){

		return tipoServicoReferenciaId;
	}

	public void setTipoServicoReferenciaId(String tipoServicoReferenciaId){

		this.tipoServicoReferenciaId = tipoServicoReferenciaId;
	}

	public String getUnidadeEncerramentoDescricao(){

		return unidadeEncerramentoDescricao;
	}

	public void setUnidadeEncerramentoDescricao(String unidadeEncerramentoDescricao){

		this.unidadeEncerramentoDescricao = unidadeEncerramentoDescricao;
	}

	public String getUnidadeEncerramentoId(){

		return unidadeEncerramentoId;
	}

	public void setUnidadeEncerramentoId(String unidadeEncerramentoId){

		this.unidadeEncerramentoId = unidadeEncerramentoId;
	}

	public String getUnidadeGeracaoDescricao(){

		return unidadeGeracaoDescricao;
	}

	public void setUnidadeGeracaoDescricao(String unidadeGeracaoDescricao){

		this.unidadeGeracaoDescricao = unidadeGeracaoDescricao;
	}

	public String getUnidadeGeracaoId(){

		return unidadeGeracaoId;
	}

	public void setUnidadeGeracaoId(String unidadeGeracaoId){

		this.unidadeGeracaoId = unidadeGeracaoId;
	}

	public String getUsuarioEncerramentoId(){

		return usuarioEncerramentoId;
	}

	public void setUsuarioEncerramentoId(String usuarioEncerramentoId){

		this.usuarioEncerramentoId = usuarioEncerramentoId;
	}

	public String getUsuarioEncerramentoNome(){

		return usuarioEncerramentoNome;
	}

	public void setUsuarioEncerramentoNome(String usuarioEncerramentoNome){

		this.usuarioEncerramentoNome = usuarioEncerramentoNome;
	}
	
	public String getUsuarioEncerramentoLogin(){

		return usuarioEncerramentoLogin;
	}

	public void setUsuarioEncerramentoLogin(String usuarioEncerramentoLogin){

		this.usuarioEncerramentoLogin = usuarioEncerramentoLogin;

	}

	public String getUsuarioGeracaoId(){

		return usuarioGeracaoId;
	}

	public void setUsuarioGeracaoId(String usuarioGeracaoId){

		this.usuarioGeracaoId = usuarioGeracaoId;
	}

	public String getUsuarioGeracaoNome(){

		return usuarioGeracaoNome;
	}

	public void setUsuarioGeracaoNome(String usuarioGeracaoNome){

		this.usuarioGeracaoNome = usuarioGeracaoNome;
	}

	public String getValorServicoAtual(){

		return valorServicoAtual;
	}

	public void setValorServicoAtual(String valorServicoAtual){

		this.valorServicoAtual = valorServicoAtual;
	}

	public String getValorServicoOriginal(){

		return valorServicoOriginal;
	}

	public void setValorServicoOriginal(String valorServicoOriginal){

		this.valorServicoOriginal = valorServicoOriginal;
	}

	public String getServicoCobrado(){

		return servicoCobrado;
	}

	public void setServicoCobrado(String servicoCobrado){

		this.servicoCobrado = servicoCobrado;
	}

	public String getValorCobrado(){

		return valorCobrado;
	}

	public void setValorCobrado(String valorCobrado){

		this.valorCobrado = valorCobrado;
	}

	public String getSituacaoOSId(){

		return situacaoOSId;
	}

	public void setSituacaoOSId(String situacaoOSId){

		this.situacaoOSId = situacaoOSId;
	}

	public Collection<ObterDadosAtividadeIdOSHelper> getColecaoOSAtividade(){

		return colecaoOSAtividade;
	}

	public void setColecaoOSAtividade(Collection<ObterDadosAtividadeIdOSHelper> colecaoOSAtividade){

		this.colecaoOSAtividade = colecaoOSAtividade;
	}

	/**
	 * @return Retorna o campo dataProgramacao.
	 */
	public String getDataProgramacao(){

		return dataProgramacao;
	}

	/**
	 * @param dataProgramacao
	 *            O dataProgramacao a ser setado.
	 */
	public void setDataProgramacao(String dataProgramacao){

		this.dataProgramacao = dataProgramacao;
	}

	/**
	 * @return Retorna o campo equipeProgramacao.
	 */
	public String getEquipeProgramacao(){

		return equipeProgramacao;
	}

	/**
	 * @param equipeProgramacao
	 *            O equipeProgramacao a ser setado.
	 */
	public void setEquipeProgramacao(String equipeProgramacao){

		this.equipeProgramacao = equipeProgramacao;
	}

	/**
	 * @return Retorna o campo enderecoOcorrencia.
	 */
	public String getEnderecoOcorrencia(){

		return enderecoOcorrencia;
	}

	/**
	 * @param enderecoOcorrencia
	 *            O enderecoOcorrencia a ser setado.
	 */
	public void setEnderecoOcorrencia(String enderecoOcorrencia){

		this.enderecoOcorrencia = enderecoOcorrencia;
	}

	/**
	 * @return Retorna o campo inscricaoImovel.
	 */
	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	/**
	 * @param inscricaoImovel
	 *            O inscricaoImovel a ser setado.
	 */
	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	/**
	 * @return Retorna o campo matriculaImovel.
	 */
	public String getMatriculaImovel(){

		return matriculaImovel;
	}

	/**
	 * @param matriculaImovel
	 *            O matriculaImovel a ser setado.
	 */
	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}

	/**
	 * @return Retorna o campo rota.
	 */
	public String getRota(){

		return rota;
	}

	/**
	 * @param rota
	 *            O rota a ser setado.
	 */
	public void setRota(String rota){

		this.rota = rota;
	}

	/**
	 * @return Retorna o campo sequencialRota.
	 */
	public String getSequencialRota(){

		return sequencialRota;
	}

	/**
	 * @param sequencialRota
	 *            O sequencialRota a ser setado.
	 */
	public void setSequencialRota(String sequencialRota){

		this.sequencialRota = sequencialRota;
	}

	/**
	 * @return the collectionRoteiroOSDadosProgramacaoHelpers
	 */
	public Collection<RoteiroOSDadosProgramacaoHelper> getCollectionRoteiroOSDadosProgramacaoHelpers(){

		return collectionRoteiroOSDadosProgramacaoHelpers;
	}

	/**
	 * @param collectionRoteiroOSDadosProgramacaoHelpers
	 *            the collectionRoteiroOSDadosProgramacaoHelpers to set
	 */
	public void setCollectionRoteiroOSDadosProgramacaoHelpers(
					Collection<RoteiroOSDadosProgramacaoHelper> collectionRoteiroOSDadosProgramacaoHelpers){

		this.collectionRoteiroOSDadosProgramacaoHelpers = collectionRoteiroOSDadosProgramacaoHelpers;
	}

	public OrdemServico getOrdemServico(){

		return ordemServico;
	}

	public void setOrdemServico(OrdemServico ordemServico){

		this.ordemServico = ordemServico;
	}

	public String getNumeroVala(){

		return numeroVala;
	}

	public void setNumeroVala(String numeroVala){

		this.numeroVala = numeroVala;
	}

	public String getIdLocalOcorrencia(){

		return idLocalOcorrencia;
	}

	public void setIdLocalOcorrencia(String idLocalOcorrencia){

		this.idLocalOcorrencia = idLocalOcorrencia;
	}

	public String getIdPavimento(){

		return idPavimento;
	}

	public void setIdPavimento(String idPavimento){

		this.idPavimento = idPavimento;
	}

	public String getComprimentoVala(){

		return comprimentoVala;
	}

	public void setComprimentoVala(String comprimentoVala){

		this.comprimentoVala = comprimentoVala;
	}

	public String getLarguraVala(){

		return larguraVala;
	}

	public void setLarguraVala(String larguraVala){

		this.larguraVala = larguraVala;
	}

	public String getProfundidadeVala(){

		return profundidadeVala;
	}

	public void setProfundidadeVala(String profundidadeVala){

		this.profundidadeVala = profundidadeVala;
	}

	public String getIndicadorValaAterrada(){

		return indicadorValaAterrada;
	}

	public void setIndicadorValaAterrada(String indicadorValaAterrada){

		this.indicadorValaAterrada = indicadorValaAterrada;
	}

	public String getIndicadorEntulho(){

		return indicadorEntulho;
	}

	public void setIndicadorEntulho(String indicadorEntulho){

		this.indicadorEntulho = indicadorEntulho;
	}

	public String getDescricaoLocalOcorrencia(){

		return descricaoLocalOcorrencia;
	}

	public void setDescricaoLocalOcorrencia(String descricaoLocalOcorrencia){

		this.descricaoLocalOcorrencia = descricaoLocalOcorrencia;
	}

	public String getDescricaoPavimento(){

		return descricaoPavimento;
	}

	public void setDescricaoPavimento(String descricaoPavimento){

		this.descricaoPavimento = descricaoPavimento;
	}

	public String getIdCausaAgua(){

		return idCausaAgua;
	}

	public void setIdCausaAgua(String idCausaAgua){

		this.idCausaAgua = idCausaAgua;
	}

	public String getIdRedeRamalAgua(){

		return idRedeRamalAgua;
	}

	public void setIdRedeRamalAgua(String idRedeRamalAgua){

		this.idRedeRamalAgua = idRedeRamalAgua;
	}

	public String getIdDiametroAgua(){

		return idDiametroAgua;
	}

	public void setIdDiametroAgua(String idDiametroAgua){

		this.idDiametroAgua = idDiametroAgua;
	}

	public String getIdMaterialAgua(){

		return idMaterialAgua;
	}

	public void setIdMaterialAgua(String idMaterialAgua){

		this.idMaterialAgua = idMaterialAgua;
	}

	public String getProfundidadeAgua(){

		return profundidadeAgua;
	}

	public void setProfundidadeAgua(String profundidadeAgua){

		this.profundidadeAgua = profundidadeAgua;
	}

	public String getPressaoAgua(){

		return pressaoAgua;
	}

	public void setPressaoAgua(String pressaoAgua){

		this.pressaoAgua = pressaoAgua;
	}

	public String getIdAgenteExternoAgua(){

		return idAgenteExternoAgua;
	}

	public void setIdAgenteExternoAgua(String idAgenteExternoAgua){

		this.idAgenteExternoAgua = idAgenteExternoAgua;
	}

	public String getIdCausaEsgoto(){

		return idCausaEsgoto;
	}

	public void setIdCausaEsgoto(String idCausaEsgoto){

		this.idCausaEsgoto = idCausaEsgoto;
	}

	public String getIdRedeRamalEsgoto(){

		return idRedeRamalEsgoto;
	}

	public void setIdRedeRamalEsgoto(String idRedeRamalEsgoto){

		this.idRedeRamalEsgoto = idRedeRamalEsgoto;
	}

	public String getIdDiametroEsgoto(){

		return idDiametroEsgoto;
	}

	public void setIdDiametroEsgoto(String idDiametroEsgoto){

		this.idDiametroEsgoto = idDiametroEsgoto;
	}

	public String getIdMaterialEsgoto(){

		return idMaterialEsgoto;
	}

	public void setIdMaterialEsgoto(String idMaterialEsgoto){

		this.idMaterialEsgoto = idMaterialEsgoto;
	}

	public String getProfundidadeEsgoto(){

		return profundidadeEsgoto;
	}

	public void setProfundidadeEsgoto(String profundidadeEsgoto){

		this.profundidadeEsgoto = profundidadeEsgoto;
	}

	public String getPressaoEsgoto(){

		return pressaoEsgoto;
	}

	public void setPressaoEsgoto(String pressaoEsgoto){

		this.pressaoEsgoto = pressaoEsgoto;
	}

	public String getIdAgenteExternoEsgoto(){

		return idAgenteExternoEsgoto;
	}

	public void setIdAgenteExternoEsgoto(String idAgenteExternoEsgoto){

		this.idAgenteExternoEsgoto = idAgenteExternoEsgoto;
	}

	public String getDescricaoCausaVazamentoAgua(){

		return descricaoCausaVazamentoAgua;
	}

	public void setDescricaoCausaVazamentoAgua(String descricaoCausaVazamentoAgua){

		this.descricaoCausaVazamentoAgua = descricaoCausaVazamentoAgua;
	}

	public String getDescricaoAgenteExterno(){

		return descricaoAgenteExterno;
	}

	public void setDescricaoAgenteExterno(String descricaoAgenteExterno){

		this.descricaoAgenteExterno = descricaoAgenteExterno;
	}

	public String getDescricaoMaterialAgua(){

		return descricaoMaterialAgua;
	}

	public void setDescricaoMaterialAgua(String descricaoMaterialAgua){

		this.descricaoMaterialAgua = descricaoMaterialAgua;
	}

	public String getDescricaoDiametroAgua(){

		return descricaoDiametroAgua;
	}

	public void setDescricaoDiametroAgua(String descricaoDiametroAgua){

		this.descricaoDiametroAgua = descricaoDiametroAgua;
	}

	public String getDescricaoAgua(){

		return descricaoAgua;
	}

	public void setDescricaoAgua(String descricaoAgua){

		this.descricaoAgua = descricaoAgua;
	}

	public String getDescricaoCausaVazamentoEsgoto(){

		return descricaoCausaVazamentoEsgoto;
	}

	public void setDescricaoCausaVazamentoEsgoto(String descricaoCausaVazamentoEsgoto){

		this.descricaoCausaVazamentoEsgoto = descricaoCausaVazamentoEsgoto;
	}

	public String getDescricaoDiametroEsgoto(){

		return descricaoDiametroEsgoto;
	}

	public void setDescricaoDiametroEsgoto(String descricaoDiametroEsgoto){

		this.descricaoDiametroEsgoto = descricaoDiametroEsgoto;
	}

	public String getDescricaoMaterialEsgoto(){

		return descricaoMaterialEsgoto;
	}

	public void setDescricaoMaterialEsgoto(String descricaoMaterialEsgoto){

		this.descricaoMaterialEsgoto = descricaoMaterialEsgoto;
	}

	public String getDescricaoEsgoto(){

		return descricaoEsgoto;
	}

	public void setDescricaoEsgoto(String descricaoEsgoto){

		this.descricaoEsgoto = descricaoEsgoto;
	}

	public String getExibirDadosRedeRamalAgua(){

		return exibirDadosRedeRamalAgua;
	}

	public void setExibirDadosRedeRamalAgua(String exibirDadosRedeRamalAgua){

		this.exibirDadosRedeRamalAgua = exibirDadosRedeRamalAgua;
	}

	public String getExibirDadosRedeRamalEsgoto(){

		return exibirDadosRedeRamalEsgoto;
	}

	public void setExibirDadosRedeRamalEsgoto(String exibirDadosRedeRamalEsgoto){

		this.exibirDadosRedeRamalEsgoto = exibirDadosRedeRamalEsgoto;
	}

	public String getUnidadeAtualId(){

		return unidadeAtualId;
	}

	public void setUnidadeAtualId(String unidadeAtualId){

		this.unidadeAtualId = unidadeAtualId;
	}

	public String getUnidadeAtualDescricao(){

		return unidadeAtualDescricao;
	}

	public void setUnidadeAtualDescricao(String unidadeAtualDescricao){

		this.unidadeAtualDescricao = unidadeAtualDescricao;
	}

	public String getPermiteTramiteIndependente(){

		return permiteTramiteIndependente;
	}

	public void setPermiteTramiteIndependente(String permiteTramiteIndependente){

		this.permiteTramiteIndependente = permiteTramiteIndependente;
	}

	public String getUsuarioExecucaoId(){

		return usuarioExecucaoId;
	}

	public void setUsuarioExecucaoId(String usuarioExecucaoId){

		this.usuarioExecucaoId = usuarioExecucaoId;
	}

	public String getLeituraServico(){

		return leituraServico;
	}

	public void setLeituraServico(String leituraServico){

		this.leituraServico = leituraServico;
	}

	public String getTipoCorteId(){

		return tipoCorteId;
	}

	public void setTipoCorteId(String tipoCorteId){

		this.tipoCorteId = tipoCorteId;
	}

	public void setPossuiExecucaoServico(String possuiExecucaoServico){

		this.possuiExecucaoServico = possuiExecucaoServico;
	}

	public String getPossuiExecucaoServico(){

		return possuiExecucaoServico;
	}

	public void setTipoCorteDescricao(String tipoCorteDescricao){

		this.tipoCorteDescricao = tipoCorteDescricao;
	}

	public String getTipoCorteDescricao(){

		return tipoCorteDescricao;
	}

	public void setUsuarioExecucaoNome(String usuarioExecucaoNome){

		this.usuarioExecucaoNome = usuarioExecucaoNome;
	}

	public String getUsuarioExecucaoNome(){

		return usuarioExecucaoNome;
	}

	public String getEmissaoOSHabilitada(){

		return emissaoOSHabilitada;
	}

	public void setEmissaoOSHabilitada(String emissaoOSHabilitada){

		this.emissaoOSHabilitada = emissaoOSHabilitada;
	}

	public String getQuantidadeEntulho(){

		return quantidadeEntulho;
	}

	public void setQuantidadeEntulho(String quantidadeEntulho){

		this.quantidadeEntulho = quantidadeEntulho;
	}

	public String getDescricaoEntulhoMedida(){

		return descricaoEntulhoMedida;
	}

	public void setDescricaoEntulhoMedida(String descricaoEntulhoMedida){

		this.descricaoEntulhoMedida = descricaoEntulhoMedida;
	}

	public String getComprimentoTutulacaoAguaPluvial(){

		return comprimentoTutulacaoAguaPluvial;
	}

	public void setComprimentoTutulacaoAguaPluvial(String comprimentoTutulacaoAguaPluvial){

		this.comprimentoTutulacaoAguaPluvial = comprimentoTutulacaoAguaPluvial;
	}

	public String getDiametroTutulacaoAguaPluvial(){

		return diametroTutulacaoAguaPluvial;
	}

	public void setDiametroTutulacaoAguaPluvial(String diametroTutulacaoAguaPluvial){

		this.diametroTutulacaoAguaPluvial = diametroTutulacaoAguaPluvial;
	}

	public String getPermiteGerarOSReparo(){

		return permiteGerarOSReparo;
	}

	public void setPermiteGerarOSReparo(String permiteGerarOSReparo){

		this.permiteGerarOSReparo = permiteGerarOSReparo;
	}

	public String getIdOSServicoReparo(){

		return idOSServicoReparo;
	}

	public void setIdOSServicoReparo(String idOSServicoReparo){

		this.idOSServicoReparo = idOSServicoReparo;
	}

	public String getExibirDadosReparoOSPrincipal(){

		return exibirDadosReparoOSPrincipal;
	}

	public void setExibirDadosReparoOSPrincipal(String exibirDadosReparoOSPrincipal){

		this.exibirDadosReparoOSPrincipal = exibirDadosReparoOSPrincipal;
	}

	public String getIdOSPrincipal(){

		return idOSPrincipal;
	}

	public void setIdOSPrincipal(String idOSPrincipal){

		this.idOSPrincipal = idOSPrincipal;
	}

	public String getValorHorasTrabalhadas(){

		return valorHorasTrabalhadas;
	}

	public void setValorHorasTrabalhadas(String valorHorasTrabalhadas){

		this.valorHorasTrabalhadas = valorHorasTrabalhadas;
	}

	public String getValorMateriais(){

		return valorMateriais;
	}

	public void setValorMateriais(String valorMateriais){

		this.valorMateriais = valorMateriais;
	}

	public Collection<OSDadosInterrupcaoHelper> getCollectionOsInterrupcaoDeslocamentoHelpers(){

		return collectionOsInterrupcaoDeslocamentoHelpers;
	}

	public void setCollectionOsInterrupcaoDeslocamentoHelpers(
					Collection<OSDadosInterrupcaoHelper> collectionOsInterrupcaoDeslocamentoHelpers){

		this.collectionOsInterrupcaoDeslocamentoHelpers = collectionOsInterrupcaoDeslocamentoHelpers;
	}

	public Collection<OSDadosInterrupcaoHelper> getCollectionOsInterrupcaoExecucaoHelpers(){

		return collectionOsInterrupcaoExecucaoHelpers;
	}

	public void setCollectionOsInterrupcaoExecucaoHelpers(Collection<OSDadosInterrupcaoHelper> collectionOsInterrupcaoExecucaoHelpers){

		this.collectionOsInterrupcaoExecucaoHelpers = collectionOsInterrupcaoExecucaoHelpers;
	}

	public String getQuantidadeDiasUnidade(){

		return quantidadeDiasUnidade;
	}

	public void setQuantidadeDiasUnidade(String quantidadeDiasUnidade){

		this.quantidadeDiasUnidade = quantidadeDiasUnidade;
	}

	public String getDataPrevisaoCliente(){

		return dataPrevisaoCliente;
	}

	public void setDataPrevisaoCliente(String dataPrevisaoCliente){

		this.dataPrevisaoCliente = dataPrevisaoCliente;
	}


}