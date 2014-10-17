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

package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.OrdemServicoFotoOcorrencia;

import java.util.Collection;
import java.util.Date;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

public class EncerrarOrdemServicoActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	// Dados Gerais
	private String numeroOS;

	private String numeroOSOriginal;

	private String situacaoOS;

	private String situacaoOSId;

	private String numeroRA;

	private String situacaoRA;

	private String numeroDocumentoCobranca;

	private String dataGeracao;

	private String numeroOSReferencia;

	private String tipoServicoOSId;

	private String tipoServicoOSDescricao;

	private String tipoServicoReferenciaId;

	private String tipoServicoReferenciaDescricao;

	private String retornoOSReferida;

	private String observacao;

	private String valorServicoOriginal;

	private String valorServicoAtual;

	private String prioridadeOriginal;

	private String prioridadeAtual;

	private String unidadeGeracaoId;

	private String unidadeGeracaoDescricao;

	private String usuarioGeracaoId;

	private String usuarioGeracaoNome;

	private String dataUltimaEmissao;

	private String idMotivoEncerramento;

	// Este campo na tela corresponde a Data de Execução (orse_tmexecucao)
	private String dataEncerramento;

	private Date ultimaAlteracao;

	private String indicadorExecucao;

	private String observacaoEncerramento;

	private String situacaoOSReferencia;

	private String pavimento;

	private String indicadorPavimento;

	private String servicoTipoReferenciaDescricao;

	private String indicadorDeferimento;

	private String idTipoRetornoReferida;

	private String indicadorTrocaServico;

	private String idServicoTipo;

	private String descricaoServicoTipo;

	private String servicoTipoObrigatorio;

	private String servicoTipoReferenciaOS;

	private String servicoTipoReferenciaOSDescricao;

	private String dataRoteiro;

	private String indicadorAtualizaComercial;

	private String indicadorVistoriaServicoTipo;

	private String codigoRetornoVistoriaOs;

	private String horaEncerramento;

	private String dimensao1;

	private String dimensao2;

	private String dimensao3;

	private FormFile[] fotos;

	private FormFile fotos1;

	private FormFile fotos2;

	private FormFile fotos3;

	private Integer qtdFotos;

	private Collection<OrdemServicoFotoOcorrencia> colecaoOSFoto;

	// Dados das Atividades
	private String idAtividade;

	private String idAtividadeSelecionada;

	private String descricaoAtividade;

	private String mostrarHorasExecucao;

	private String mostrarMateriais;

	private String dataExecucao;

	private String horaInicioExecucao;

	private String horaFimExecucao;

	private String idEquipeProgramada;

	private String idEquipeNaoProgramada;

	private String descricaoEquipeNaoProgramada;

	private String horaInicioInterrupcaoExecucao;

	private String horaFimInterrupcaoExecucao;

	private String idMotivoInterrupcaoExecucao;

	private String idMaterialProgramado;

	private String idMaterialNaoProgramado;

	private String descricaoMaterialNaoProgramado;

	private String quantidade;

	// Dados operacionais
	// Deslocamentos
	private String kmInicial;

	private String kmFinal;

	private String placaVeiculo;

	private String dataInicioInterrupcaoDeslocamento;

	private String horaInicioInterrupcaoDeslocamento;

	private String dataFimInterrupcaoDeslocamento;

	private String horaFimInterrupcaoDeslocamento;

	private String kmInterrupcaoDeslocamento;

	private String idMotivoInterrupcaoDeslocamento;

	private String descricaoMotivoInterrupcaoDeslocamento;

	private String dataInicioDeslocamento;

	private String dataFimDeslocamento;

	private String horaInicioDeslocamento;

	private String horaFimDeslocamento;

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

	// Rede/Ramal Água
	private String idCausaAgua;

	private String idRedeRamalAgua;

	private String idDiametroAgua;

	private String idMaterialAgua;

	private String profundidadeAgua;

	private String pressaoAgua;

	private String idAgenteExternoAgua;

	// Rede/Ramal Esgoto
	private String idCausaEsgoto;

	private String idRedeRamalEsgoto;

	private String idDiametroEsgoto;

	private String idMaterialEsgoto;

	private String profundidadeEsgoto;

	private String pressaoEsgoto;

	private String idAgenteExternoEsgoto;

	private String txParecerEncerramento;

	private String tmEncerramento;

	private String submitAutomatico1;

	private String submitAutomatico3;

	public Collection<OrdemServicoFotoOcorrencia> getColecaoOSFoto(){

		return colecaoOSFoto;
	}

	public void setColecaoOSFoto(Collection<OrdemServicoFotoOcorrencia> colecaoOSFoto){

		this.colecaoOSFoto = colecaoOSFoto;
	}

	public String getDimensao1(){

		return dimensao1;
	}

	public void setDimensao1(String dimensao1){

		this.dimensao1 = dimensao1;
	}

	public String getDimensao2(){

		return dimensao2;
	}

	public void setDimensao2(String dimensao2){

		this.dimensao2 = dimensao2;
	}

	public String getDimensao3(){

		return dimensao3;
	}

	public void setDimensao3(String dimensao3){

		this.dimensao3 = dimensao3;
	}

	/**
	 * @return Retorna o campo horaEncerramento.
	 */
	public String getHoraEncerramento(){

		return horaEncerramento;
	}

	/**
	 * @param horaEncerramento
	 *            O horaEncerramento a ser setado.
	 */
	public void setHoraEncerramento(String horaEncerramento){

		this.horaEncerramento = horaEncerramento;
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

	public String getObservacao(){

		return observacao;
	}

	public void setObservacao(String observacao){

		this.observacao = observacao;
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

	public String getSituacaoOSId(){

		return situacaoOSId;
	}

	public void setSituacaoOSId(String situacaoOSId){

		this.situacaoOSId = situacaoOSId;
	}

	public String getDataEncerramento(){

		return dataEncerramento;
	}

	public void setDataEncerramento(String dataEncerramento){

		this.dataEncerramento = dataEncerramento;
	}

	public String getIdMotivoEncerramento(){

		return idMotivoEncerramento;
	}

	public void setIdMotivoEncerramento(String idMotivoEncerramento){

		this.idMotivoEncerramento = idMotivoEncerramento;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String getIndicadorExecucao(){

		return indicadorExecucao;
	}

	public void setIndicadorExecucao(String indicadorExecucao){

		this.indicadorExecucao = indicadorExecucao;
	}

	public String getObservacaoEncerramento(){

		return observacaoEncerramento;
	}

	public void setObservacaoEncerramento(String observacaoEncerramento){

		this.observacaoEncerramento = observacaoEncerramento;
	}

	public String getSituacaoOSReferencia(){

		return situacaoOSReferencia;
	}

	public void setSituacaoOSReferencia(String situacaoOSReferencia){

		this.situacaoOSReferencia = situacaoOSReferencia;
	}

	public String getPavimento(){

		return pavimento;
	}

	public void setPavimento(String pavimento){

		this.pavimento = pavimento;
	}

	public String getIndicadorPavimento(){

		return indicadorPavimento;
	}

	public void setIndicadorPavimento(String indicadorPavimento){

		this.indicadorPavimento = indicadorPavimento;
	}

	public String getServicoTipoReferenciaDescricao(){

		return servicoTipoReferenciaDescricao;
	}

	public void setServicoTipoReferenciaDescricao(String servicoTipoReferenciaDescricao){

		this.servicoTipoReferenciaDescricao = servicoTipoReferenciaDescricao;
	}

	public String getIndicadorDeferimento(){

		return indicadorDeferimento;
	}

	public void setIndicadorDeferimento(String indicadorDeferimento){

		this.indicadorDeferimento = indicadorDeferimento;
	}

	public String getIdTipoRetornoReferida(){

		return idTipoRetornoReferida;
	}

	public void setIdTipoRetornoReferida(String idTipoRetornoReferida){

		this.idTipoRetornoReferida = idTipoRetornoReferida;
	}

	public String getIndicadorTrocaServico(){

		return indicadorTrocaServico;
	}

	public void setIndicadorTrocaServico(String indicadorTrocaServico){

		this.indicadorTrocaServico = indicadorTrocaServico;
	}

	public String getDescricaoServicoTipo(){

		return descricaoServicoTipo;
	}

	public void setDescricaoServicoTipo(String descricaoServicoTipo){

		this.descricaoServicoTipo = descricaoServicoTipo;
	}

	public String getIdServicoTipo(){

		return idServicoTipo;
	}

	public void setIdServicoTipo(String idServicoTipo){

		this.idServicoTipo = idServicoTipo;
	}

	public String getServicoTipoObrigatorio(){

		return servicoTipoObrigatorio;
	}

	public void setServicoTipoObrigatorio(String servicoTipoObrigatorio){

		this.servicoTipoObrigatorio = servicoTipoObrigatorio;
	}

	public String getServicoTipoReferenciaOS(){

		return servicoTipoReferenciaOS;
	}

	public void setServicoTipoReferenciaOS(String servicoTipoReferenciaOS){

		this.servicoTipoReferenciaOS = servicoTipoReferenciaOS;
	}

	public String getDataRoteiro(){

		return dataRoteiro;
	}

	public void setDataRoteiro(String dataRoteiro){

		this.dataRoteiro = dataRoteiro;
	}

	public String getTipoServicoOSDescricao(){

		return tipoServicoOSDescricao;
	}

	public void setTipoServicoOSDescricao(String tipoServicoOSDescricao){

		this.tipoServicoOSDescricao = tipoServicoOSDescricao;
	}

	public String getTipoServicoOSId(){

		return tipoServicoOSId;
	}

	public void setTipoServicoOSId(String tipoServicoOSId){

		this.tipoServicoOSId = tipoServicoOSId;
	}

	public String getIndicadorAtualizaComercial(){

		return indicadorAtualizaComercial;
	}

	public void setIndicadorAtualizaComercial(String indicadorAtualizaComercial){

		this.indicadorAtualizaComercial = indicadorAtualizaComercial;
	}

	public String getServicoTipoReferenciaOSDescricao(){

		return servicoTipoReferenciaOSDescricao;
	}

	public void setServicoTipoReferenciaOSDescricao(String servicoTipoReferenciaOSDescricao){

		this.servicoTipoReferenciaOSDescricao = servicoTipoReferenciaOSDescricao;
	}

	public void resetarConsultarDadosOSPopup(){

		this.numeroOS = null;
		this.situacaoOS = null;
		this.situacaoOSId = null;
		this.numeroRA = null;
		this.situacaoRA = null;
		this.numeroDocumentoCobranca = null;
		this.dataGeracao = null;
		this.numeroOSReferencia = null;
		this.tipoServicoOSId = null;
		this.tipoServicoOSDescricao = null;
		this.tipoServicoReferenciaId = null;
		this.tipoServicoReferenciaDescricao = null;
		this.retornoOSReferida = null;
		this.observacao = null;
		this.valorServicoOriginal = null;
		this.valorServicoAtual = null;
		this.prioridadeOriginal = null;
		this.prioridadeAtual = null;
		this.unidadeGeracaoId = null;
		this.unidadeGeracaoDescricao = null;
		this.usuarioGeracaoId = null;
		this.usuarioGeracaoNome = null;
		this.dataUltimaEmissao = null;
		this.dataEncerramento = null;
		this.idMotivoEncerramento = null;
		this.dataEncerramento = null;
		this.ultimaAlteracao = null;
		this.indicadorExecucao = null;
		this.observacaoEncerramento = null;
		this.situacaoOSReferencia = null;
		this.pavimento = null;
		this.indicadorPavimento = null;
		this.servicoTipoReferenciaDescricao = null;
		this.indicadorDeferimento = null;
		this.idTipoRetornoReferida = null;
		this.indicadorTrocaServico = null;
		this.idServicoTipo = null;
		this.descricaoServicoTipo = null;
		this.servicoTipoObrigatorio = null;
		this.servicoTipoReferenciaOS = null;
		this.dataRoteiro = null;
		this.indicadorAtualizaComercial = null;
		this.servicoTipoReferenciaOSDescricao = null;
		this.codigoRetornoVistoriaOs = null;
		this.indicadorVistoriaServicoTipo = null;
		this.horaEncerramento = null;
		this.dimensao1 = null;
		this.dimensao2 = null;
		this.dimensao3 = null;
		this.kmInicial = null;
		this.kmFinal = null;
		this.dataInicioDeslocamento = null;
		this.dataFimDeslocamento = null;
		this.placaVeiculo = null;
		this.kmInterrupcaoDeslocamento = null;
		this.horaInicioInterrupcaoDeslocamento = null;
		this.horaFimInterrupcaoDeslocamento = null;
		this.dataInicioInterrupcaoDeslocamento = null;
		this.dataFimInterrupcaoDeslocamento = null;
		this.idMotivoInterrupcaoDeslocamento = null;
		this.idRedeRamalAgua = null;
		this.idRedeRamalEsgoto = null;
		this.profundidadeAgua = null;
		this.profundidadeEsgoto = null;
		this.pressaoAgua = null;
		this.pressaoEsgoto = null;
		this.idAgenteExternoAgua = null;
		this.idAgenteExternoEsgoto = null;
		this.idMaterialAgua = null;
		this.idMaterialEsgoto = null;
		this.idCausaAgua = null;
		this.idCausaEsgoto = null;
		this.idEquipeProgramada = null;
		this.idEquipeNaoProgramada = null;
		this.horaInicioExecucao = null;
		this.horaFimExecucao = null;
		this.numeroVala = null;
		this.idLocalOcorrencia = null;
		this.idPavimento = null;
		this.comprimentoVala = null;
		this.larguraVala = null;
		this.indicadorEntulho = null;
		this.indicadorValaAterrada = null;

	}

	public String getCodigoRetornoVistoriaOs(){

		return codigoRetornoVistoriaOs;
	}

	public void setCodigoRetornoVistoriaOs(String codigoRetornoVistoriaOs){

		this.codigoRetornoVistoriaOs = codigoRetornoVistoriaOs;
	}

	public String getIndicadorVistoriaServicoTipo(){

		return indicadorVistoriaServicoTipo;
	}

	public void setIndicadorVistoriaServicoTipo(String indicadorVistoriaServicoTipo){

		this.indicadorVistoriaServicoTipo = indicadorVistoriaServicoTipo;
	}

	public String getNumeroOSOriginal(){

		return numeroOSOriginal;
	}

	public void setNumeroOSOriginal(String numeroOSOriginal){

		this.numeroOSOriginal = numeroOSOriginal;
	}

	public FormFile[] getFotos(){

		return fotos;
	}

	public void setFotos(FormFile[] fotos){

		this.fotos = fotos;
	}

	public FormFile getFotos1(){

		return fotos1;
	}

	public void setFotos1(FormFile fotos1){

		this.fotos1 = fotos1;
	}

	public FormFile getFotos2(){

		return fotos2;
	}

	public void setFotos2(FormFile fotos2){

		this.fotos2 = fotos2;
	}

	public FormFile getFotos3(){

		return fotos3;
	}

	public void setFotos3(FormFile fotos3){

		this.fotos3 = fotos3;
	}

	public Integer getQtdFotos(){

		return qtdFotos;
	}

	public void setQtdFotos(Integer qtdFotos){

		this.qtdFotos = qtdFotos;
	}

	public String getIdAtividade(){

		return idAtividade;
	}

	public void setIdAtividade(String idAtividade){

		this.idAtividade = idAtividade;
	}

	public String getDescricaoAtividade(){

		return descricaoAtividade;
	}

	public void setDescricaoAtividade(String descricaoAtividade){

		this.descricaoAtividade = descricaoAtividade;
	}

	public String getMostrarHorasExecucao(){

		return mostrarHorasExecucao;
	}

	public void setMostrarHorasExecucao(String mostrarHorasExecucao){

		this.mostrarHorasExecucao = mostrarHorasExecucao;
	}

	public String getDataExecucao(){

		return dataExecucao;
	}

	public void setDataExecucao(String dataExecucao){

		this.dataExecucao = dataExecucao;
	}

	public String getHoraInicioExecucao(){

		return horaInicioExecucao;
	}

	public void setHoraInicioExecucao(String horaInicioExecucao){

		this.horaInicioExecucao = horaInicioExecucao;
	}

	public String getHoraFimExecucao(){

		return horaFimExecucao;
	}

	public void setHoraFimExecucao(String horaFimExecucao){

		this.horaFimExecucao = horaFimExecucao;
	}

	public String getIdEquipeProgramada(){

		return idEquipeProgramada;
	}

	public void setIdEquipeProgramada(String idEquipeProgramada){

		this.idEquipeProgramada = idEquipeProgramada;
	}

	public String getIdEquipeNaoProgramada(){

		return idEquipeNaoProgramada;
	}

	public void setIdEquipeNaoProgramada(String idEquipeNaoProgramada){

		this.idEquipeNaoProgramada = idEquipeNaoProgramada;
	}

	public String getDescricaoEquipeNaoProgramada(){

		return descricaoEquipeNaoProgramada;
	}

	public void setDescricaoEquipeNaoProgramada(String descricaoEquipeNaoProgramada){

		this.descricaoEquipeNaoProgramada = descricaoEquipeNaoProgramada;
	}

	public String getIdAtividadeSelecionada(){

		return idAtividadeSelecionada;
	}

	public void setIdAtividadeSelecionada(String idAtividadeSelecionada){

		this.idAtividadeSelecionada = idAtividadeSelecionada;
	}

	public String getIdMaterialProgramado(){

		return idMaterialProgramado;
	}

	public void setIdMaterialProgramado(String idMaterialProgramado){

		this.idMaterialProgramado = idMaterialProgramado;
	}

	public String getIdMaterialNaoProgramado(){

		return idMaterialNaoProgramado;
	}

	public void setIdMaterialNaoProgramado(String idMaterialNaoProgramado){

		this.idMaterialNaoProgramado = idMaterialNaoProgramado;
	}

	public String getDescricaoMaterialNaoProgramado(){

		return descricaoMaterialNaoProgramado;
	}

	public void setDescricaoMaterialNaoProgramado(String descricaoMaterialNaoProgramado){

		this.descricaoMaterialNaoProgramado = descricaoMaterialNaoProgramado;
	}

	public String getIdMotivoInterrupcaoExecucao(){

		return idMotivoInterrupcaoExecucao;
	}

	public void setIdMotivoInterrupcaoExecucao(String idMotivoInterrupcaoExecucao){

		this.idMotivoInterrupcaoExecucao = idMotivoInterrupcaoExecucao;
	}

	public String getHoraInicioInterrupcaoExecucao(){

		return horaInicioInterrupcaoExecucao;
	}

	public void setHoraInicioInterrupcaoExecucao(String horaInicioInterrupcaoExecucao){

		this.horaInicioInterrupcaoExecucao = horaInicioInterrupcaoExecucao;
	}

	public String getHoraFimInterrupcaoExecucao(){

		return horaFimInterrupcaoExecucao;
	}

	public void setHoraFimInterrupcaoExecucao(String horaFimInterrupcaoExecucao){

		this.horaFimInterrupcaoExecucao = horaFimInterrupcaoExecucao;
	}

	public String getKmInicial(){

		return kmInicial;
	}

	public void setKmInicial(String kmInicial){

		this.kmInicial = kmInicial;
	}

	public String getKmFinal(){

		return kmFinal;
	}

	public void setKmFinal(String kmFinal){

		this.kmFinal = kmFinal;
	}

	public String getDataInicioInterrupcaoDeslocamento(){

		return dataInicioInterrupcaoDeslocamento;
	}

	public void setDataInicioInterrupcaoDeslocamento(String dataInicioInterrupcaoDeslocamento){

		this.dataInicioInterrupcaoDeslocamento = dataInicioInterrupcaoDeslocamento;
	}

	public String getHoraInicioInterrupcaoDeslocamento(){

		return horaInicioInterrupcaoDeslocamento;
	}

	public void setHoraInicioInterrupcaoDeslocamento(String horaInicioInterrupcaoDeslocamento){

		this.horaInicioInterrupcaoDeslocamento = horaInicioInterrupcaoDeslocamento;
	}

	public String getDataFimInterrupcaoDeslocamento(){

		return dataFimInterrupcaoDeslocamento;
	}

	public void setDataFimInterrupcaoDeslocamento(String dataFimInterrupcaoDeslocamento){

		this.dataFimInterrupcaoDeslocamento = dataFimInterrupcaoDeslocamento;
	}

	public String getHoraFimInterrupcaoDeslocamento(){

		return horaFimInterrupcaoDeslocamento;
	}

	public void setHoraFimInterrupcaoDeslocamento(String horaFimInterrupcaoDeslocamento){

		this.horaFimInterrupcaoDeslocamento = horaFimInterrupcaoDeslocamento;
	}

	public String getKmInterrupcaoDeslocamento(){

		return kmInterrupcaoDeslocamento;
	}

	public void setKmInterrupcaoDeslocamento(String kmInterrupcaoDeslocamento){

		this.kmInterrupcaoDeslocamento = kmInterrupcaoDeslocamento;
	}

	public String getIdMotivoInterrupcaoDeslocamento(){

		return idMotivoInterrupcaoDeslocamento;
	}

	public void setIdMotivoInterrupcaoDeslocamento(String idMotivoInterrupcaoDeslocamento){

		this.idMotivoInterrupcaoDeslocamento = idMotivoInterrupcaoDeslocamento;
	}

	public String getDescricaoMotivoInterrupcaoDeslocamento(){

		return descricaoMotivoInterrupcaoDeslocamento;
	}

	public void setDescricaoMotivoInterrupcaoDeslocamento(String descricaoMotivoInterrupcaoDeslocamento){

		this.descricaoMotivoInterrupcaoDeslocamento = descricaoMotivoInterrupcaoDeslocamento;
	}

	public String getDataInicioDeslocamento(){

		return dataInicioDeslocamento;
	}

	public void setDataInicioDeslocamento(String dataInicioDeslocamento){

		this.dataInicioDeslocamento = dataInicioDeslocamento;
	}

	public String getDataFimDeslocamento(){

		return dataFimDeslocamento;
	}

	public void setDataFimDeslocamento(String dataFimDeslocamento){

		this.dataFimDeslocamento = dataFimDeslocamento;
	}

	public String getHoraInicioDeslocamento(){

		return horaInicioDeslocamento;
	}

	public void setHoraInicioDeslocamento(String horaInicioDeslocamento){

		this.horaInicioDeslocamento = horaInicioDeslocamento;
	}

	public String getHoraFimDeslocamento(){

		return horaFimDeslocamento;
	}

	public void setHoraFimDeslocamento(String horaFimDeslocamento){

		this.horaFimDeslocamento = horaFimDeslocamento;
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

	public void setQuantidade(String quantidade){

		this.quantidade = quantidade;
	}

	public String getQuantidade(){

		return quantidade;
	}

	public String getMostrarMateriais(){

		return mostrarMateriais;
	}

	public void setMostrarMateriais(String mostrarMateriais){

		this.mostrarMateriais = mostrarMateriais;
	}

	public String getPlacaVeiculo(){

		return placaVeiculo;
	}

	public void setPlacaVeiculo(String placaVeiculo){

		this.placaVeiculo = placaVeiculo;
	}

	public String getTxParecerEncerramento(){

		return txParecerEncerramento;
	}

	public void setTxParecerEncerramento(String txParecerEncerramento){

		this.txParecerEncerramento = txParecerEncerramento;
	}

	public String getTmEncerramento(){

		return tmEncerramento;
	}

	public void setTmEncerramento(String tmEncerramento){

		this.tmEncerramento = tmEncerramento;
	}

	public String getSubmitAutomatico1(){

		return submitAutomatico1;
	}

	public void setSubmitAutomatico1(String submitAutomatico1){

		this.submitAutomatico1 = submitAutomatico1;
	}

	public String getSubmitAutomatico3(){

		return submitAutomatico3;
	}

	public void setSubmitAutomatico3(String submitAutomatico3){

		this.submitAutomatico3 = submitAutomatico3;
	}

}