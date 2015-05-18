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

import gcom.atendimentopublico.ligacaoagua.FiltroHistoricoManutencaoLigacao;
import gcom.atendimentopublico.ligacaoagua.HistoricoManutencaoLigacao;
import gcom.atendimentopublico.ordemservico.FiltroOrdemServicoUnidade;
import gcom.atendimentopublico.ordemservico.OrdemServico;
import gcom.atendimentopublico.ordemservico.OrdemServicoUnidade;
import gcom.atendimentopublico.ordemservico.bean.ObterDadosAtividadeIdOSHelper;
import gcom.atendimentopublico.ordemservico.bean.ObterDadosAtividadesOSHelper;
import gcom.atendimentopublico.ordemservico.bean.ObterDescricaoSituacaoOSHelper;
import gcom.atendimentopublico.registroatendimento.AtendimentoRelacaoTipo;
import gcom.atendimentopublico.registroatendimento.bean.ObterDescricaoSituacaoRAHelper;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.faturamento.debito.DebitoACobrar;
import gcom.faturamento.debito.DebitoACobrarHistorico;
import gcom.faturamento.debito.FiltroDebitoACobrar;
import gcom.faturamento.debito.FiltroDebitoACobrarHistorico;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.atendimentopublico.ParametroAtendimentoPublico;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action que define o pré-processamento da página de exibir consultar OS Popup
 * 
 * @author Leonardo Regis
 * @created 14/08/2006
 */
public class ExibirConsultarDadosOrdemServicoPopupAction
				extends GcomAction {

	/**
	 * Execute do Consultar OS Popup
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return forward
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("consultarDadosOrdemServicoPopup");
		Fachada fachada = Fachada.getInstancia();
		ConsultarDadosOrdemServicoPopupActionForm consultarDadosOrdemServicoPopupActionForm = (ConsultarDadosOrdemServicoPopupActionForm) actionForm;
		OrdemServico ordemServico = pesquisarOrdemServico(Integer.valueOf(consultarDadosOrdemServicoPopupActionForm.getNumeroOS()));
		consultarDadosOrdemServicoPopupActionForm.resetarConsultarDadosOSPopup();

		if(httpServletRequest.getParameter("voltar") != null && !httpServletRequest.getParameter("voltar").equals("")){
			httpServletRequest.setAttribute("voltar", "s");
		}

		// Dados Gerais da OS
		consultarDadosOrdemServicoPopupActionForm.setNumeroOS(ordemServico.getId() + "");
		consultarDadosOrdemServicoPopupActionForm.setSituacaoOSId(ordemServico.getSituacao() + "");

		// Caso de Uso [UC0454]
		ObterDescricaoSituacaoOSHelper situacaoOS = fachada.obterDescricaoSituacaoOS(ordemServico.getId());
		consultarDadosOrdemServicoPopupActionForm.setSituacaoOS(situacaoOS.getDescricaoSituacao());

		if(ordemServico.getRegistroAtendimento() != null){

			consultarDadosOrdemServicoPopupActionForm.setNumeroRA(ordemServico.getRegistroAtendimento().getId() + "");

			// Caso de Uso [UC0420]
			ObterDescricaoSituacaoRAHelper situacaoRA = fachada.obterDescricaoSituacaoRA(ordemServico.getRegistroAtendimento().getId());
			consultarDadosOrdemServicoPopupActionForm.setSituacaoRA(situacaoRA.getDescricaoSituacao());
		}

		if(ordemServico.getCobrancaDocumento() != null){
			consultarDadosOrdemServicoPopupActionForm.setNumeroDocumentoCobranca(ordemServico.getCobrancaDocumento().getId() + "");
		}

		consultarDadosOrdemServicoPopupActionForm.setDataGeracao(Util.formatarData(ordemServico.getDataGeracao()));
		consultarDadosOrdemServicoPopupActionForm.setTipoServicoId(ordemServico.getServicoTipo().getId() + "");
		consultarDadosOrdemServicoPopupActionForm.setTipoServicoDescricao(ordemServico.getServicoTipo().getDescricao());

		if(ordemServico.getOsReferencia() != null){
			consultarDadosOrdemServicoPopupActionForm.setNumeroOSReferencia(ordemServico.getOsReferencia().getId() + "");
		}

		if(ordemServico.getServicoTipoReferencia() != null){
			consultarDadosOrdemServicoPopupActionForm.setTipoServicoReferenciaId(ordemServico.getServicoTipoReferencia().getId() + "");
			consultarDadosOrdemServicoPopupActionForm.setTipoServicoReferenciaDescricao(ordemServico.getServicoTipoReferencia()
							.getDescricao());
		}

		if(ordemServico.getOsReferidaRetornoTipo() != null){
			consultarDadosOrdemServicoPopupActionForm.setRetornoOSReferida(ordemServico.getOsReferidaRetornoTipo().getDescricao());
		}

		consultarDadosOrdemServicoPopupActionForm.setObservacao(ordemServico.getObservacao());

		String valorServicoOriginal = "";
		if(ordemServico.getValorOriginal() != null){
			valorServicoOriginal = ordemServico.getValorOriginal() + "";
		}

		consultarDadosOrdemServicoPopupActionForm.setValorServicoOriginal(valorServicoOriginal.replace(".", ","));

		String valorServicoAtual = "";
		if(ordemServico.getValorAtual() != null){
			valorServicoAtual = ordemServico.getValorAtual() + "";
		}

		consultarDadosOrdemServicoPopupActionForm.setValorServicoAtual(valorServicoAtual.replace(".", ","));
		consultarDadosOrdemServicoPopupActionForm.setPrioridadeOriginal(ordemServico.getServicoTipoPrioridadeOriginal().getDescricao());
		consultarDadosOrdemServicoPopupActionForm.setPrioridadeAtual(ordemServico.getServicoTipoPrioridadeAtual().getDescricao() + "");

		OrdemServicoUnidade ordemServicoUnidade = consultarOrdemServicoUnidade(ordemServico.getId(), AtendimentoRelacaoTipo.ABRIR_REGISTRAR);

		if(ordemServicoUnidade != null){
			consultarDadosOrdemServicoPopupActionForm.setUnidadeGeracaoId(ordemServicoUnidade.getUnidadeOrganizacional().getId() + "");
			consultarDadosOrdemServicoPopupActionForm.setUnidadeGeracaoDescricao(ordemServicoUnidade.getUnidadeOrganizacional()
							.getDescricao());
			consultarDadosOrdemServicoPopupActionForm.setUsuarioGeracaoId(ordemServicoUnidade.getUsuario().getId() + "");
			consultarDadosOrdemServicoPopupActionForm.setUsuarioGeracaoNome(ordemServicoUnidade.getUsuario().getNomeUsuario());
		}

		if(ordemServico.getDataEmissao() != null){
			consultarDadosOrdemServicoPopupActionForm.setDataUltimaEmissao(Util.formatarData(ordemServico.getDataEmissao()));
		}

		// Unidade Atual
		UnidadeOrganizacional unidadeAtual = fachada.obterUnidadeAtualOrdemServico(ordemServico.getId());
		if(unidadeAtual != null){

			consultarDadosOrdemServicoPopupActionForm.setUnidadeAtualId(unidadeAtual.getId().toString());
			consultarDadosOrdemServicoPopupActionForm.setUnidadeAtualDescricao(unidadeAtual.getDescricao());
		}else{

			consultarDadosOrdemServicoPopupActionForm.setUnidadeAtualId("");
			consultarDadosOrdemServicoPopupActionForm.setUnidadeAtualDescricao("");
		}

		if(ordemServico.getValorHorasTrabalhadas() != null){
			consultarDadosOrdemServicoPopupActionForm.setValorHorasTrabalhadas(ordemServico.getValorHorasTrabalhadas().toString());
		}else{
			consultarDadosOrdemServicoPopupActionForm.setValorHorasTrabalhadas("0");
		}
		if(ordemServico.getValorMateriais() != null){
			consultarDadosOrdemServicoPopupActionForm.setValorMateriais(ordemServico.getValorMateriais().toString());
		}else{
			consultarDadosOrdemServicoPopupActionForm.setValorMateriais("0");
		}

		// Pesquisar dados da programação
		// OrdemServicoProgramacao ordemServicoProgramacao =
		// fachada.pesquisarDataEquipeOSProgramacao(ordemServico.getId());

		// if (ordemServicoProgramacao != null && !ordemServicoProgramacao.equals("")) {
		//
		// httpServletRequest.setAttribute("achouDadosProgramacao", "ok");
		//
		// if (ordemServicoProgramacao.getProgramacaoRoteiro().getDataRoteiro() != null) {
		// consultarDadosOrdemServicoPopupActionForm.setDataProgramacao(Util.formatarData(ordemServicoProgramacao.getProgramacaoRoteiro()
		// .getDataRoteiro()));
		// } else {
		// consultarDadosOrdemServicoPopupActionForm.setDataProgramacao("");
		// }
		// if (ordemServicoProgramacao.getEquipe().getNome() != null) {
		// consultarDadosOrdemServicoPopupActionForm.setEquipeProgramacao(ordemServicoProgramacao.getEquipe().getNome());
		// } else {
		// consultarDadosOrdemServicoPopupActionForm.setEquipeProgramacao("");
		// }
		// } else {
		// consultarDadosOrdemServicoPopupActionForm.setDataProgramacao("");
		// consultarDadosOrdemServicoPopupActionForm.setEquipeProgramacao("");
		// }

		Collection<RoteiroOSDadosProgramacaoHelper> collectionRoteiroOSDadosProgramacaoHelpers = fachada
						.pesquisarProgramacaoOrdemServicoRoteiroEquipe(ordemServico.getId());
		if(collectionRoteiroOSDadosProgramacaoHelpers != null && !collectionRoteiroOSDadosProgramacaoHelpers.isEmpty()){
			httpServletRequest.setAttribute("achouDadosProgramacao", "ok");
			consultarDadosOrdemServicoPopupActionForm
							.setCollectionRoteiroOSDadosProgramacaoHelpers(collectionRoteiroOSDadosProgramacaoHelpers);
		}else{
			consultarDadosOrdemServicoPopupActionForm.setCollectionRoteiroOSDadosProgramacaoHelpers(null);
		}

		// Pesquisar dados do local de ocorrência
		if(ordemServico.getRegistroAtendimento() != null){
			httpServletRequest.setAttribute("achouDadosLocalOcorrencia", "ok");
			String enderecoOcorrencia = fachada.obterEnderecoOcorrenciaRA(ordemServico.getRegistroAtendimento().getId());
			consultarDadosOrdemServicoPopupActionForm.setEnderecoOcorrencia(enderecoOcorrencia);
		}else if(ordemServico.getCobrancaDocumento() != null){
			if(ordemServico.getCobrancaDocumento().getImovel() != null){
				httpServletRequest.setAttribute("achouDadosLocalOcorrencia", "ok");
				String enderecoOcorrencia = fachada.pesquisarEndereco(ordemServico.getCobrancaDocumento().getImovel().getId());
				consultarDadosOrdemServicoPopupActionForm.setEnderecoOcorrencia(enderecoOcorrencia);
			}
		}else{
			if(ordemServico.getImovel() != null){
				httpServletRequest.setAttribute("achouDadosLocalOcorrencia", "ok");
				String enderecoOcorrencia = fachada.pesquisarEndereco(ordemServico.getImovel().getId());
				consultarDadosOrdemServicoPopupActionForm.setEnderecoOcorrencia(enderecoOcorrencia);
			}else{
				consultarDadosOrdemServicoPopupActionForm.setEnderecoOcorrencia("");
			}
		}

		Imovel imovel = ordemServico.getImovel();
		if(imovel != null){
			httpServletRequest.setAttribute("achouDadosLocalOcorrencia", "ok");
			consultarDadosOrdemServicoPopupActionForm.setMatriculaImovel("" + imovel.getId());
			consultarDadosOrdemServicoPopupActionForm.setInscricaoImovel(imovel.getInscricaoFormatada());
			consultarDadosOrdemServicoPopupActionForm.setRota(ordemServico.getImovel().getRota().getCodigo().toString());

			if(ordemServico.getImovel().getNumeroSequencialRota() != null){
				consultarDadosOrdemServicoPopupActionForm.setSequencialRota(ordemServico.getImovel().getNumeroSequencialRota().toString());
			}else{
				consultarDadosOrdemServicoPopupActionForm.setSequencialRota("");
			}
		}else{
			consultarDadosOrdemServicoPopupActionForm.setMatriculaImovel("");
			consultarDadosOrdemServicoPopupActionForm.setInscricaoImovel("");
			consultarDadosOrdemServicoPopupActionForm.setRota("");
			consultarDadosOrdemServicoPopupActionForm.setSequencialRota("");
		}

		// Dados de Execução de OS
		if(Short.valueOf(ordemServico.getSituacao()).intValue() == OrdemServico.SITUACAO_ENCERRADO.intValue()){

			consultarDadosOrdemServicoPopupActionForm.setDataExecucao(Util.formatarDataComHora(ordemServico.getDataExecucao()));
			consultarDadosOrdemServicoPopupActionForm.setDataEncerramento(Util.formatarDataComHora(ordemServico.getDataEncerramento()));
			if(ordemServico.getDescricaoParecerEncerramento() != null && !ordemServico.equals("")){
				consultarDadosOrdemServicoPopupActionForm.setParecerEncerramento(ordemServico.getDescricaoParecerEncerramento());
			}
			// ........................................................................................
			// Alterado por : Yara Souza
			// Data: 13/05/2010
			// Solicitação: Remover da tela de Encerrar OS as informações referente a Pavimento.
			// if (ordemServico.getAreaPavimento() != null) {
			// String areaPavimentacao = ordemServico.getAreaPavimento() + "";
			// consultarDadosOrdemServicoPopupActionForm.setAreaPavimentacao(areaPavimentacao.replace(".",
			// ","));
			// }
			consultarDadosOrdemServicoPopupActionForm.setAreaPavimentacao(null);
			// ........................................................................................

			if(Short.valueOf(ordemServico.getIndicadorComercialAtualizado()).intValue() == 1){
				consultarDadosOrdemServicoPopupActionForm.setComercialAtualizado("SIM");
			}else{
				consultarDadosOrdemServicoPopupActionForm.setComercialAtualizado("NÃO");
			}
			if(ordemServico.getPercentualCobranca() != null){
				String percentualCobrado = ordemServico.getPercentualCobranca() + "";
				consultarDadosOrdemServicoPopupActionForm.setPercentualCobranca(percentualCobrado.replace(".", ","));
			}else{
				consultarDadosOrdemServicoPopupActionForm.setPercentualCobranca("0,00");
			}

			FiltroDebitoACobrar filtroDebitoACobrar = new FiltroDebitoACobrar();
			filtroDebitoACobrar.adicionarParametro(new ParametroSimples(FiltroDebitoACobrar.ORDEM_SERVICO_ID, ordemServico.getId()));
			Collection colecaoDebitoACobrar = this.getFachada().pesquisar(filtroDebitoACobrar, DebitoACobrar.class.getName());

			DebitoACobrar debitoACobrar = (DebitoACobrar) Util.retonarObjetoDeColecao(colecaoDebitoACobrar);

			FiltroDebitoACobrarHistorico filtroDebitoACobrarHistorico = new FiltroDebitoACobrarHistorico();
			filtroDebitoACobrarHistorico.adicionarParametro(new ParametroSimples(FiltroDebitoACobrarHistorico.ORDEM_SERVICO_ID,
							ordemServico.getId()));

			Collection colecaoDebitoACobrarHistorico = this.getFachada().pesquisar(filtroDebitoACobrarHistorico,
							DebitoACobrarHistorico.class.getName());

			DebitoACobrarHistorico debitoACobrarHistorico = (DebitoACobrarHistorico) Util
							.retonarObjetoDeColecao(colecaoDebitoACobrarHistorico);

			if(ordemServico.getServicoNaoCobrancaMotivo() != null){
				consultarDadosOrdemServicoPopupActionForm.setMotivoNaoCobranca(ordemServico.getServicoNaoCobrancaMotivo().getDescricao());
				consultarDadosOrdemServicoPopupActionForm.setServicoCobrado("NÃO");
			}else if(debitoACobrar != null || debitoACobrarHistorico != null){

				consultarDadosOrdemServicoPopupActionForm.setServicoCobrado("SIM");
			}else{
				consultarDadosOrdemServicoPopupActionForm.setMotivoNaoCobranca(null);
				if(ordemServico.getValorAtual() != null && ordemServico.getPercentualCobranca() != null){
					BigDecimal valorAtual = new BigDecimal(Util.converterObjetoParaString(ordemServico.getValorAtual()));
					BigDecimal percentual = new BigDecimal(Util.converterObjetoParaString(ordemServico.getPercentualCobranca()));
					BigDecimal valorCobrado = valorAtual.multiply(percentual).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
					consultarDadosOrdemServicoPopupActionForm.setValorCobrado(Util.formatarMoedaReal(valorCobrado) + "");
				}else{
					consultarDadosOrdemServicoPopupActionForm.setValorCobrado("0,00");
				}
				consultarDadosOrdemServicoPopupActionForm.setServicoCobrado("NÃO");
			}
			OrdemServicoUnidade ordemServicoUnidadeEncerramento = consultarOrdemServicoUnidade(ordemServico.getId(),
							AtendimentoRelacaoTipo.ENCERRAR);
			if(ordemServicoUnidadeEncerramento != null){
				consultarDadosOrdemServicoPopupActionForm.setUnidadeEncerramentoId(ordemServicoUnidadeEncerramento
								.getUnidadeOrganizacional().getId()
								+ "");
				consultarDadosOrdemServicoPopupActionForm.setUnidadeEncerramentoDescricao(ordemServicoUnidadeEncerramento
								.getUnidadeOrganizacional().getDescricao());
				consultarDadosOrdemServicoPopupActionForm.setUsuarioEncerramentoId(ordemServicoUnidadeEncerramento.getUsuario().getId()
								+ "");
				consultarDadosOrdemServicoPopupActionForm.setUsuarioEncerramentoNome(ordemServicoUnidadeEncerramento.getUsuario()
								.getNomeUsuario());
			}
		}

		Collection<ObterDadosAtividadesOSHelper> colecaoObterDadosAtividadesOSHelper = fachada.obterDadosAtividadesOS(ordemServico.getId());
		Collection<ObterDadosAtividadeIdOSHelper> colecaoAtividade = new ArrayList();
		ObterDadosAtividadeIdOSHelper obterAtividadeIdHelper = null;
		if(colecaoObterDadosAtividadesOSHelper != null && !colecaoObterDadosAtividadesOSHelper.isEmpty()){
			for(ObterDadosAtividadesOSHelper dadosAtividade : colecaoObterDadosAtividadesOSHelper){
				obterAtividadeIdHelper = new ObterDadosAtividadeIdOSHelper();
				obterAtividadeIdHelper.setIdOS(ordemServico.getId());
				if(dadosAtividade.isMaterial()){
					if(!atividadePossuiMaterial(colecaoAtividade, dadosAtividade)){
						obterAtividadeIdHelper.setMaterial(true);
						obterAtividadeIdHelper.setAtividade(dadosAtividade.getAtividade());
						colecaoAtividade.add(obterAtividadeIdHelper);
					}
				}else{
					if(!atividadePossuiMaterial(colecaoAtividade, dadosAtividade)){
						obterAtividadeIdHelper.setPeriodo(true);
						obterAtividadeIdHelper.setAtividade(dadosAtividade.getAtividade());
						colecaoAtividade.add(obterAtividadeIdHelper);
					}
				}
			}
			consultarDadosOrdemServicoPopupActionForm.setColecaoOSAtividade(colecaoAtividade);
		}

		/*
		 * [UC0441] 1.5. Dados da Execução do Serviço
		 */
		FiltroHistoricoManutencaoLigacao filtroHistoricoManutencaoLigacao = new FiltroHistoricoManutencaoLigacao();
		filtroHistoricoManutencaoLigacao.adicionarParametro(new ParametroSimples(FiltroHistoricoManutencaoLigacao.ORDEM_SERVICO_ID,
						ordemServico.getId()));
		filtroHistoricoManutencaoLigacao.adicionarCaminhoParaCarregamentoEntidade(FiltroHistoricoManutencaoLigacao.USUARIO_EXECUCAO);
		filtroHistoricoManutencaoLigacao.adicionarCaminhoParaCarregamentoEntidade(FiltroHistoricoManutencaoLigacao.CORTE_TIPO);

		Collection<HistoricoManutencaoLigacao> colecaoHistoricoManutencaoLigacao = fachada.pesquisar(filtroHistoricoManutencaoLigacao,
						HistoricoManutencaoLigacao.class.getName());

		HistoricoManutencaoLigacao historicoManutencaoLigacao = (HistoricoManutencaoLigacao) Util
						.retonarObjetoDeColecao(colecaoHistoricoManutencaoLigacao);

		if(historicoManutencaoLigacao != null){
			consultarDadosOrdemServicoPopupActionForm.setPossuiExecucaoServico("SIM");
			if(historicoManutencaoLigacao.getUsuarioExecucao() != null){
				consultarDadosOrdemServicoPopupActionForm
								.setUsuarioExecucaoId(historicoManutencaoLigacao.getUsuarioExecucao().getId() + "");
				consultarDadosOrdemServicoPopupActionForm.setUsuarioExecucaoNome(historicoManutencaoLigacao.getUsuarioExecucao()
								.getNomeUsuario());
			}else{
				consultarDadosOrdemServicoPopupActionForm.setUsuarioExecucaoId("");
				consultarDadosOrdemServicoPopupActionForm.setUsuarioExecucaoNome("");
			}
			if(historicoManutencaoLigacao.getNumeroLeituraExecucao() != null){
				consultarDadosOrdemServicoPopupActionForm.setLeituraServico(historicoManutencaoLigacao.getNumeroLeituraExecucao() + "");
			}else{
				consultarDadosOrdemServicoPopupActionForm.setLeituraServico("");
			}
			if(historicoManutencaoLigacao.getCorteTipo() != null){
				consultarDadosOrdemServicoPopupActionForm.setTipoCorteId(historicoManutencaoLigacao.getCorteTipo().getId() + "");
			}else{
				consultarDadosOrdemServicoPopupActionForm.setTipoCorteId("");
			}
			if(historicoManutencaoLigacao.getCorteTipo() != null && historicoManutencaoLigacao.getCorteTipo().getDescricao() != null){
				consultarDadosOrdemServicoPopupActionForm.setTipoCorteDescricao(historicoManutencaoLigacao.getCorteTipo().getDescricao());
			}else{
				consultarDadosOrdemServicoPopupActionForm.setTipoCorteDescricao("");
			}
		}else{
			consultarDadosOrdemServicoPopupActionForm.setPossuiExecucaoServico("");
		}

		try{
			if(ParametroAtendimentoPublico.P_PERMITE_COBRAR_MATERIAL_OS.executar().equals(ConstantesSistema.SIM.toString())){
				httpServletRequest.setAttribute("permiteCobrarMaterial", "1");
			}else{
				httpServletRequest.setAttribute("permiteCobrarMaterial", "0");
			}
			if(ParametroAtendimentoPublico.P_PERMITE_COBRAR_HORA_OS.executar().equals(ConstantesSistema.SIM.toString())){
				httpServletRequest.setAttribute("permiteCobrarHora", "1");
			}else{
				httpServletRequest.setAttribute("permiteCobrarHora", "0");
			}
		}catch(ControladorException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// ---------------------------------------------------------------------------------------------------------

		// Definindo a tela de retorno que será chamada no botão Voltar - Raphael Rossiter em
		// 13/02/2007
		String caminhoTelaPesquisaRetorno = httpServletRequest.getParameter("caminhoTelaPesquisaRetorno");
		if(caminhoTelaPesquisaRetorno != null && !caminhoTelaPesquisaRetorno.equals("")){
			httpServletRequest.setAttribute("caminhoTelaPesquisaRetorno", caminhoTelaPesquisaRetorno);
		}

		return retorno;
	}

	private boolean atividadePossuiMaterial(Collection<ObterDadosAtividadeIdOSHelper> colecaoAtividade,
					ObterDadosAtividadesOSHelper dadosAtividade){

		boolean retorno = false;
		for(ObterDadosAtividadeIdOSHelper helper : colecaoAtividade){
			if(helper.getAtividade().getId().intValue() == dadosAtividade.getAtividade().getId().intValue()){
				if(!dadosAtividade.isMaterial()){
					helper.setPeriodo(true);
				}
				retorno = true;
				break;
			}
		}
		return retorno;
	}

	/**
	 * Consulta a ordem de serviço pelo id informado
	 * 
	 * @author Leonardo Regis
	 * @created 14/08/2006
	 */
	private OrdemServico pesquisarOrdemServico(Integer id){

		Fachada fachada = Fachada.getInstancia();
		OrdemServico retorno = fachada.consultarDadosOrdemServico(id);
		if(retorno == null){
			throw new ActionServletException("atencao.naocadastrado", null, "Ordem de Serviço");
		}
		return retorno;
	}

	/**
	 * Consulta a Ordem Serviço Unidade pelo id do OS e Tipo (1=ABRIR/REGISTRAR e 3-ENCERRAR)
	 * 
	 * @author Leonardo Regis
	 * @date 15/08/2006
	 */
	private OrdemServicoUnidade consultarOrdemServicoUnidade(Integer idOS, Integer idAtendimentoTipo){

		OrdemServicoUnidade retorno = null;
		Fachada fachada = Fachada.getInstancia();
		Collection colecaoOrdemServicoUnidade = null;

		FiltroOrdemServicoUnidade filtroOrdemServicoUnidade = new FiltroOrdemServicoUnidade();
		filtroOrdemServicoUnidade.adicionarParametro(new ParametroSimples(FiltroOrdemServicoUnidade.ORDEM_SERVICO_ID, idOS));
		filtroOrdemServicoUnidade.adicionarParametro(new ParametroSimples(FiltroOrdemServicoUnidade.ATENDIMENTO_RELACAO_TIPO_ID,
						idAtendimentoTipo));

		filtroOrdemServicoUnidade.adicionarCaminhoParaCarregamentoEntidade("unidadeOrganizacional");
		filtroOrdemServicoUnidade.adicionarCaminhoParaCarregamentoEntidade("usuario");

		colecaoOrdemServicoUnidade = fachada.pesquisar(filtroOrdemServicoUnidade, OrdemServicoUnidade.class.getName());
		if(colecaoOrdemServicoUnidade != null && !colecaoOrdemServicoUnidade.isEmpty()){
			retorno = (OrdemServicoUnidade) Util.retonarObjetoDeColecao(colecaoOrdemServicoUnidade);
		}

		return retorno;
	}
}