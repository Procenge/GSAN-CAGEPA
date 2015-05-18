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
import gcom.atendimentopublico.ordemservico.*;
import gcom.atendimentopublico.ordemservico.bean.ObterDadosAtividadeIdOSHelper;
import gcom.atendimentopublico.ordemservico.bean.ObterDadosAtividadesOSHelper;
import gcom.atendimentopublico.ordemservico.bean.ObterDescricaoSituacaoOSHelper;
import gcom.atendimentopublico.registroatendimento.AtendimentoRelacaoTipo;
import gcom.atendimentopublico.registroatendimento.FiltroRegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
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
import gcom.gui.relatorio.atendimentopublico.ordemservico.RelatorioParecerEncerramentoOSHelper;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.atendimentopublico.ParametroAtendimentoPublico;
import gcom.util.parametrizacao.cobranca.ExecutorParametrosCobranca;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action que define o pré-processamento da página de exibir consultar OS.
 * 
 * @author lms
 * @created 04/09/2006
 */
public class ExibirConsultarDadosOrdemServicoAction
				extends GcomAction {

	/**
	 * Execute do Consultar OS.
	 * 
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return forward
	 * @throws ControladorException
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) throws ControladorException{

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("consultarDadosOrdemServico");

		// Sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		ConsultarDadosOrdemServicoActionForm consultarDadosOrdemServicoActionForm = (ConsultarDadosOrdemServicoActionForm) actionForm;

		OrdemServico ordemServico = null;
		if(httpServletRequest.getAttribute("numeroOS") != null && httpServletRequest.getParameter("numeroOSParametro") == null){
			ordemServico = pesquisarOrdemServico((Integer) httpServletRequest.getAttribute("numeroOS"));
		}else if(httpServletRequest.getParameter("numeroOSParametro") != null){
			ordemServico = pesquisarOrdemServico(Integer.valueOf(httpServletRequest.getParameter("numeroOSParametro")));
		}else{
			ordemServico = pesquisarOrdemServico(Integer.valueOf(httpServletRequest.getParameter("numeroOS")));
		}

		consultarDadosOrdemServicoActionForm.reset(actionMapping, httpServletRequest);

		Short permiteTramiteIndependente = Short.parseShort( (String) ParametroAtendimentoPublico.P_OS_TRAMITE_INDEPENDENTE.executar(ExecutorParametrosCobranca.getInstancia()));

		Collection<OrdemServicoValaPavimento> colecaoOsValaPavimento = fachada.pesquisarOrdemServicoValaPavimento(ordemServico.getId());
		sessao.setAttribute("colecaoVala", colecaoOsValaPavimento);

		Integer quantidadeDiasUnidade = fachada.recuperaQuantidadeDiasUnidade(ordemServico.getId(), permiteTramiteIndependente);

		if(quantidadeDiasUnidade > 0){
			consultarDadosOrdemServicoActionForm.setQuantidadeDiasUnidade(quantidadeDiasUnidade.toString());
		}

		OrdemServicoProgramacao programacao = fachada.pesquisarOrdemServicoProgramacaoPorOS(ordemServico.getId());

		if(programacao != null){
			Collection<OSDadosInterrupcaoHelper> collectionOsInterrupcaoDeslocamentoHelpers = new ArrayList<OSDadosInterrupcaoHelper>();
			Collection<OSDadosInterrupcaoHelper> collectionOsInterrupcaoExecucaoHelpers = new ArrayList<OSDadosInterrupcaoHelper>();
			OSDadosInterrupcaoHelper helper = null;
			Collection<OrdemServicoInterrupcaoDeslocamento> interrupcaoDeslocamento = fachada
							.pesquisarOSInterrupcaoDeslocamento(programacao.getId());
			Collection<OrdemServicoInterrupcaoExecucao> interrupcaoExecucao = fachada.pesquisarOSInterrupcaoExecucao(programacao.getId());
			if(interrupcaoDeslocamento != null && interrupcaoDeslocamento.size() > 0){
				for(OrdemServicoInterrupcaoDeslocamento deslocamento : interrupcaoDeslocamento){
					helper = new OSDadosInterrupcaoHelper();
					helper.setFimInterrupcao(Util.formatarData(deslocamento.getInterrupcaoFim()));
					helper.setInicioInterrupcao(Util.formatarData(deslocamento.getInterrupcaoInicio()));
					helper.setKm(deslocamento.getNumeroKm().toString());
					helper.setMotivoInterrupcao(deslocamento.getMotivoInterrupcao().getDescricao());
					collectionOsInterrupcaoDeslocamentoHelpers.add(helper);
				}
				httpServletRequest.setAttribute("achouDadosInterrupcaoDeslocamento", "ok");
				consultarDadosOrdemServicoActionForm
								.setCollectionOsInterrupcaoDeslocamentoHelpers(collectionOsInterrupcaoDeslocamentoHelpers);
				sessao.setAttribute("collectionOsInterrupcaoDeslocamentoHelpers", collectionOsInterrupcaoDeslocamentoHelpers);
			}
			if(interrupcaoExecucao != null && interrupcaoExecucao.size() > 0){
				for(OrdemServicoInterrupcaoExecucao execucao : interrupcaoExecucao){
					helper = new OSDadosInterrupcaoHelper();
					helper.setFimInterrupcao(Util.formatarData(execucao.getInterrupcaoFim()));
					helper.setInicioInterrupcao(Util.formatarData(execucao.getInterrupcaoInicio()));
					helper.setMotivoInterrupcao(execucao.getMotivoInterrupcao().getDescricao());
					collectionOsInterrupcaoExecucaoHelpers.add(helper);
				}
				consultarDadosOrdemServicoActionForm.setCollectionOsInterrupcaoExecucaoHelpers(collectionOsInterrupcaoExecucaoHelpers);
				httpServletRequest.setAttribute("achouDadosInterrupcaoExecucao", "ok");
				sessao.setAttribute("collectionOsInterrupcaoExecucaoHelpers", collectionOsInterrupcaoExecucaoHelpers);
			}
		}

		OrdemServico ordemServicoPai = fachada.pesquisarOrdemServicoPrincipal(ordemServico.getId());
		if(ordemServicoPai != null){
			consultarDadosOrdemServicoActionForm.setIdOSPrincipal(ordemServicoPai.getId().toString());
			Collection<OrdemServicoValaPavimento> colecaoOsValaPavimentoOSPrincipal = fachada
							.pesquisarOrdemServicoValaPavimento(ordemServicoPai.getId());
			consultarDadosOrdemServicoActionForm.setExibirDadosReparoOSPrincipal("true");
			sessao.setAttribute("colecaoValaOSPrincipal", colecaoOsValaPavimentoOSPrincipal);
		}else{
			consultarDadosOrdemServicoActionForm.setExibirDadosReparoOSPrincipal(null);
			sessao.setAttribute("colecaoValaOSPrincipal", null);
		}

		Integer permiteGerarOSReparo = Integer.valueOf(ConstantesSistema.NAO);
		if(ordemServico.getOrdemServicoReparo() == null
						&& ordemServico.getServicoTipo().getIndicadorVala() == Integer.valueOf(ConstantesSistema.SIM)
						&& ordemServico.getRegistroAtendimento() != null
						&& ordemServico.getRegistroAtendimento().getCodigoSituacao() == RegistroAtendimento.SITUACAO_PENDENTE
						&& colecaoOsValaPavimento != null && colecaoOsValaPavimento.size() > 0){
			permiteGerarOSReparo = Integer.valueOf(ConstantesSistema.SIM);
		}

		if(ordemServico.getRegistroAtendimento() != null && ordemServico.getRegistroAtendimento().getDataPrevistaAtual() != null) consultarDadosOrdemServicoActionForm
						.setDataPrevisaoCliente(Util.formatarData(ordemServico.getRegistroAtendimento().getDataPrevistaAtual()));

		consultarDadosOrdemServicoActionForm.setPermiteGerarOSReparo(permiteGerarOSReparo.toString());

		consultarDadosOrdemServicoActionForm.setPermiteTramiteIndependente(permiteTramiteIndependente.toString());

		// Dados Gerais da OS
		consultarDadosOrdemServicoActionForm.setOrdemServico(ordemServico);

		consultarDadosOrdemServicoActionForm.setNumeroOS(ordemServico.getId() + "");
		consultarDadosOrdemServicoActionForm.setNumeroOSPesquisada(ordemServico.getId() + "");
		consultarDadosOrdemServicoActionForm.setSituacaoOSId(ordemServico.getSituacao() + "");


		// Pesquisar dados da programação
		// OrdemServicoProgramacao ordemServicoProgramacao =
		// fachada.pesquisarDataEquipeOSProgramacao(ordemServico.getId());
		// if (ordemServicoProgramacao != null && !ordemServicoProgramacao.equals("")) {
		// if (ordemServicoProgramacao.getProgramacaoRoteiro().getDataRoteiro() != null) {
		// consultarDadosOrdemServicoActionForm.setDataProgramacao(Util.formatarData(ordemServicoProgramacao.getProgramacaoRoteiro()
		// .getDataRoteiro()));
		// } else {
		// consultarDadosOrdemServicoActionForm.setDataProgramacao("");
		// }
		// if (ordemServicoProgramacao.getEquipe().getNome() != null) {
		// consultarDadosOrdemServicoActionForm.setEquipeProgramacao(ordemServicoProgramacao.getEquipe().getNome());
		// } else {
		// consultarDadosOrdemServicoActionForm.setEquipeProgramacao("");
		// }
		// } else {
		// consultarDadosOrdemServicoActionForm.setDataProgramacao("");
		// consultarDadosOrdemServicoActionForm.setEquipeProgramacao("");
		// }

		Collection<RoteiroOSDadosProgramacaoHelper> collectionRoteiroOSDadosProgramacaoHelpers = fachada
						.pesquisarProgramacaoOrdemServicoRoteiroEquipe(ordemServico.getId());
		if(collectionRoteiroOSDadosProgramacaoHelpers != null && !collectionRoteiroOSDadosProgramacaoHelpers.isEmpty()){
			httpServletRequest.setAttribute("achouDadosProgramacao", "ok");
			consultarDadosOrdemServicoActionForm.setCollectionRoteiroOSDadosProgramacaoHelpers(collectionRoteiroOSDadosProgramacaoHelpers);
		}else{
			consultarDadosOrdemServicoActionForm.setCollectionRoteiroOSDadosProgramacaoHelpers(null);
		}

		// Pesquisar dados local de ocorrência
		if(ordemServico.getRegistroAtendimento() != null){
			String enderecoOcorrencia = fachada.obterEnderecoOcorrenciaRA(ordemServico.getRegistroAtendimento().getId());
			consultarDadosOrdemServicoActionForm.setEnderecoOcorrencia(enderecoOcorrencia);
		}else if(ordemServico.getCobrancaDocumento() != null){
			if(ordemServico.getCobrancaDocumento().getImovel() != null){
				String enderecoOcorrencia = fachada.pesquisarEndereco(ordemServico.getCobrancaDocumento().getImovel().getId());
				consultarDadosOrdemServicoActionForm.setEnderecoOcorrencia(enderecoOcorrencia);
			}
		}else{
			if(ordemServico.getImovel() != null){
				String enderecoOcorrencia = fachada.pesquisarEndereco(ordemServico.getImovel().getId());
				consultarDadosOrdemServicoActionForm.setEnderecoOcorrencia(enderecoOcorrencia);
			}else{
				consultarDadosOrdemServicoActionForm.setEnderecoOcorrencia("");
			}
		}
		Imovel imovel = ordemServico.getImovel();
		if(imovel != null){

			consultarDadosOrdemServicoActionForm.setMatriculaImovel("" + imovel.getId());
			consultarDadosOrdemServicoActionForm.setInscricaoImovel(imovel.getInscricaoFormatada());

			if(imovel.getRota() != null && imovel.getRota().getCodigo() != null){
				consultarDadosOrdemServicoActionForm.setRota(imovel.getRota().getCodigo().toString());
			}else{
				consultarDadosOrdemServicoActionForm.setRota("");
			}

			if(ordemServico.getImovel().getNumeroSequencialRota() != null){
				consultarDadosOrdemServicoActionForm.setSequencialRota(ordemServico.getImovel().getNumeroSequencialRota().toString());
			}else{
				consultarDadosOrdemServicoActionForm.setSequencialRota("");
			}
		}else{
			consultarDadosOrdemServicoActionForm.setMatriculaImovel("");
			consultarDadosOrdemServicoActionForm.setInscricaoImovel("");
			consultarDadosOrdemServicoActionForm.setRota("");
			consultarDadosOrdemServicoActionForm.setSequencialRota("");
		}

		// Caso de Uso [UC0454]
		ObterDescricaoSituacaoOSHelper situacaoOS = fachada.obterDescricaoSituacaoOS(ordemServico.getId());
		consultarDadosOrdemServicoActionForm.setSituacaoOS(situacaoOS.getDescricaoSituacao());

		if(ordemServico.getRegistroAtendimento() != null){
			consultarDadosOrdemServicoActionForm.setNumeroRA(ordemServico.getRegistroAtendimento().getId() + "");

			FiltroRegistroAtendimento filtroRegistroAtendimento = new FiltroRegistroAtendimento();
			filtroRegistroAtendimento.adicionarParametro(new ParametroSimples(FiltroRegistroAtendimento.ID, ordemServico
							.getRegistroAtendimento().getId()));
			RegistroAtendimento registroAtendimento = (RegistroAtendimento) Util.retonarObjetoDeColecao(fachada.pesquisar(
							filtroRegistroAtendimento, RegistroAtendimento.class.getName()));
			consultarDadosOrdemServicoActionForm.setObservacaoRa(registroAtendimento.getObservacao());

			// Caso de Uso [UC0420]
			ObterDescricaoSituacaoRAHelper situacaoRA = fachada.obterDescricaoSituacaoRA(ordemServico.getRegistroAtendimento().getId());
			consultarDadosOrdemServicoActionForm.setSituacaoRA(situacaoRA.getDescricaoSituacao());
		}

		if(ordemServico.getCobrancaDocumento() != null){
			consultarDadosOrdemServicoActionForm.setNumeroDocumentoCobranca(ordemServico.getCobrancaDocumento().getId() + "");
		}

		consultarDadosOrdemServicoActionForm.setDataGeracao(Util.formatarData(ordemServico.getDataGeracao()));
		consultarDadosOrdemServicoActionForm.setTipoServicoId(ordemServico.getServicoTipo().getId() + "");
		consultarDadosOrdemServicoActionForm.setTipoServicoDescricao(ordemServico.getServicoTipo().getDescricao());

		if(ordemServico.getOsReferencia() != null){
			consultarDadosOrdemServicoActionForm.setNumeroOSReferencia(ordemServico.getOsReferencia().getId() + "");
		}else{
			consultarDadosOrdemServicoActionForm.setNumeroOSReferencia(null);
		}

		if(ordemServico.getServicoTipoReferencia() != null){
			consultarDadosOrdemServicoActionForm.setTipoServicoReferenciaId(ordemServico.getServicoTipoReferencia().getId() + "");
			consultarDadosOrdemServicoActionForm.setTipoServicoReferenciaDescricao(ordemServico.getServicoTipoReferencia().getDescricao());
		}else{
			consultarDadosOrdemServicoActionForm.setTipoServicoReferenciaId(null);
		}

		if(ordemServico.getOsReferidaRetornoTipo() != null){
			consultarDadosOrdemServicoActionForm.setRetornoOSReferida(ordemServico.getOsReferidaRetornoTipo().getDescricao());
		}else{
			consultarDadosOrdemServicoActionForm.setRetornoOSReferida(null);
		}

		consultarDadosOrdemServicoActionForm.setObservacaoOs(ordemServico.getObservacao());

		String valorServicoOriginal = "";
		if(ordemServico.getValorOriginal() != null){
			valorServicoOriginal = ordemServico.getValorOriginal() + "";
			consultarDadosOrdemServicoActionForm.setValorServicoOriginal(valorServicoOriginal.replace(".", ","));
		}else{
			consultarDadosOrdemServicoActionForm.setValorServicoOriginal("");
		}

		String valorServicoAtual = "";
		if(ordemServico.getValorAtual() != null){
			valorServicoAtual = ordemServico.getValorAtual() + "";
			consultarDadosOrdemServicoActionForm.setValorServicoAtual(valorServicoAtual.replace(".", ","));
		}else{
			consultarDadosOrdemServicoActionForm.setValorServicoAtual("");
		}

		consultarDadosOrdemServicoActionForm.setPrioridadeOriginal(ordemServico.getServicoTipoPrioridadeOriginal().getDescricao());
		consultarDadosOrdemServicoActionForm.setPrioridadeAtual(ordemServico.getServicoTipoPrioridadeAtual().getDescricao());

		OrdemServicoUnidade ordemServicoUnidade = consultarOrdemServicoUnidade(ordemServico.getId(), AtendimentoRelacaoTipo.ABRIR_REGISTRAR);

		if(ordemServicoUnidade != null){
			consultarDadosOrdemServicoActionForm.setUnidadeGeracaoId(ordemServicoUnidade.getUnidadeOrganizacional().getId() + "");
			consultarDadosOrdemServicoActionForm.setUnidadeGeracaoDescricao(ordemServicoUnidade.getUnidadeOrganizacional().getDescricao());
			consultarDadosOrdemServicoActionForm.setUsuarioGeracaoId(ordemServicoUnidade.getUsuario().getId() + "");
			try{

				consultarDadosOrdemServicoActionForm.setUsuarioGeracaoNome(ordemServicoUnidade.getUsuario().getNomeUsuario());
			}catch(Exception e){

				consultarDadosOrdemServicoActionForm.setUsuarioGeracaoNome("");
			}

		}else{
			consultarDadosOrdemServicoActionForm.setUnidadeGeracaoId("");
			consultarDadosOrdemServicoActionForm.setUnidadeGeracaoDescricao("");
			consultarDadosOrdemServicoActionForm.setUsuarioGeracaoId("");
			consultarDadosOrdemServicoActionForm.setUsuarioGeracaoNome("");
		}

		// Unidade Atual
		UnidadeOrganizacional unidadeAtual = fachada.obterUnidadeAtualOrdemServico(ordemServico.getId());
		if(unidadeAtual != null){

			consultarDadosOrdemServicoActionForm.setUnidadeAtualId(unidadeAtual.getId().toString());
			consultarDadosOrdemServicoActionForm.setUnidadeAtualDescricao(unidadeAtual.getDescricao());
		}else{

			consultarDadosOrdemServicoActionForm.setUnidadeAtualId("");
			consultarDadosOrdemServicoActionForm.setUnidadeAtualDescricao("");
		}

		if(ordemServico.getDataEmissao() != null){
			consultarDadosOrdemServicoActionForm.setDataUltimaEmissao(Util.formatarData(ordemServico.getDataEmissao()));
		}else{
			consultarDadosOrdemServicoActionForm.setDataUltimaEmissao("");
		}

		if(ordemServico.getOrdemServicoReparo() != null){
			consultarDadosOrdemServicoActionForm.setIdOSServicoReparo(ordemServico.getOrdemServicoReparo().getId().toString());
		}else{
			consultarDadosOrdemServicoActionForm.setIdOSServicoReparo(null);
		}

		// Dados de Execução de OS
		if(Short.valueOf(ordemServico.getSituacao()).intValue() == OrdemServico.SITUACAO_ENCERRADO.intValue()){

			consultarDadosOrdemServicoActionForm.setDataExecucao(Util.formatarDataComHora(ordemServico.getDataExecucao()));
			consultarDadosOrdemServicoActionForm.setDataEncerramento(Util.formatarDataComHora(ordemServico.getDataEncerramento()));
			if(ordemServico.getDescricaoParecerEncerramento() != null && !ordemServico.equals("")){
				consultarDadosOrdemServicoActionForm.setParecerEncerramento(ordemServico.getDescricaoParecerEncerramento());
			}
			// ......................................................................................
			// Alterado por : Yara Souza
			// Data: 13/05/2010
			// Solicitação: Remover da tela de Encerrar OS as informações referentes a Pavimento.
			// if (ordemServico.getAreaPavimento() != null) {
			// String areaPavimentacao = ordemServico.getAreaPavimento() + "";
			// consultarDadosOrdemServicoActionForm.setAreaPavimentacao(areaPavimentacao.replace(".",
			// ","));
			// } else {
			// consultarDadosOrdemServicoActionForm.setAreaPavimentacao("");
			// }
			consultarDadosOrdemServicoActionForm.setAreaPavimentacao(null);
			// ......................................................................................

			if(Short.valueOf(ordemServico.getIndicadorComercialAtualizado()).intValue() == 1){
				consultarDadosOrdemServicoActionForm.setComercialAtualizado("SIM");
			}else{
				consultarDadosOrdemServicoActionForm.setComercialAtualizado("NÃO");
			}
			if(ordemServico.getPercentualCobranca() != null){
				String percentualCobrado = ordemServico.getPercentualCobranca() + "";
				consultarDadosOrdemServicoActionForm.setPercentualCobranca(percentualCobrado.replace(".", ","));
			}else{
				consultarDadosOrdemServicoActionForm.setPercentualCobranca("0,00");
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
				consultarDadosOrdemServicoActionForm.setMotivoNaoCobranca(ordemServico.getServicoNaoCobrancaMotivo().getDescricao());
				consultarDadosOrdemServicoActionForm.setServicoCobrado("Não");

			}else if(debitoACobrar != null || debitoACobrarHistorico != null){
				BigDecimal valorCobrado = BigDecimal.ZERO;

				if(debitoACobrar != null){
					valorCobrado = debitoACobrar.getValorDebito();
				}else{
					valorCobrado = debitoACobrarHistorico.getValorDebito();
				}

				String valorCobradoStr = Util.formatarMoedaReal(valorCobrado);
				consultarDadosOrdemServicoActionForm.setValorCobrado(valorCobradoStr);

				consultarDadosOrdemServicoActionForm.setServicoCobrado("Sim");
			}else{
				consultarDadosOrdemServicoActionForm.setMotivoNaoCobranca(null);
				if(ordemServico.getValorAtual() != null && ordemServico.getPercentualCobranca() != null){
					BigDecimal valorAtual = new BigDecimal(Util.converterObjetoParaString(ordemServico.getValorAtual()));
					BigDecimal percentual = new BigDecimal(Util.converterObjetoParaString(ordemServico.getPercentualCobranca()));
					BigDecimal valorCobrado = valorAtual.multiply(percentual).divide(new BigDecimal("100"), 2, BigDecimal.ROUND_HALF_UP);
					consultarDadosOrdemServicoActionForm.setValorCobrado(Util.formatarMoedaReal(valorCobrado) + "");
				}else{
					consultarDadosOrdemServicoActionForm.setValorCobrado("0,00");
				}
				consultarDadosOrdemServicoActionForm.setServicoCobrado("Não");
			}

			if(ordemServico.getAtendimentoMotivoEncerramento() != null){
				consultarDadosOrdemServicoActionForm.setMotivoEncerramento(ordemServico.getAtendimentoMotivoEncerramento().getDescricao());
			}else{
				consultarDadosOrdemServicoActionForm.setMotivoEncerramento(null);
			}

			OrdemServicoUnidade ordemServicoUnidadeEncerramento = consultarOrdemServicoUnidade(ordemServico.getId(),
							AtendimentoRelacaoTipo.ENCERRAR);
			if(ordemServicoUnidadeEncerramento != null){
				consultarDadosOrdemServicoActionForm.setUnidadeEncerramentoId(ordemServicoUnidadeEncerramento.getUnidadeOrganizacional()
								.getId()
								+ "");
				consultarDadosOrdemServicoActionForm.setUnidadeEncerramentoDescricao(ordemServicoUnidadeEncerramento
								.getUnidadeOrganizacional().getDescricao());
				consultarDadosOrdemServicoActionForm.setUsuarioEncerramentoId(ordemServicoUnidadeEncerramento.getUsuario().getId() + "");
				consultarDadosOrdemServicoActionForm.setUsuarioEncerramentoNome(ordemServicoUnidadeEncerramento.getUsuario()
								.getNomeUsuario());
				consultarDadosOrdemServicoActionForm.setUsuarioEncerramentoLogin(ordemServicoUnidadeEncerramento.getUsuario().getLogin());
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
			consultarDadosOrdemServicoActionForm.setColecaoOSAtividade(colecaoAtividade);
		}else{
			consultarDadosOrdemServicoActionForm.setColecaoOSAtividade(null);
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
			consultarDadosOrdemServicoActionForm.setPossuiExecucaoServico("SIM");
			if(historicoManutencaoLigacao.getUsuarioExecucao() != null){
				consultarDadosOrdemServicoActionForm.setUsuarioExecucaoId(historicoManutencaoLigacao.getUsuarioExecucao().getId() + "");
				consultarDadosOrdemServicoActionForm.setUsuarioExecucaoNome(historicoManutencaoLigacao.getUsuarioExecucao()
								.getNomeUsuario());
			}else{
				consultarDadosOrdemServicoActionForm.setUsuarioExecucaoId("");
				consultarDadosOrdemServicoActionForm.setUsuarioExecucaoNome("");
			}
			if(historicoManutencaoLigacao.getNumeroLeituraExecucao() != null){
				consultarDadosOrdemServicoActionForm.setLeituraServico(historicoManutencaoLigacao.getNumeroLeituraExecucao() + "");
			}else{
				consultarDadosOrdemServicoActionForm.setLeituraServico("");
			}
			if(historicoManutencaoLigacao.getCorteTipo() != null){
				consultarDadosOrdemServicoActionForm.setTipoCorteId(historicoManutencaoLigacao.getCorteTipo().getId() + "");
			}else{
				consultarDadosOrdemServicoActionForm.setTipoCorteId("");
			}
			if(historicoManutencaoLigacao.getCorteTipo() != null && historicoManutencaoLigacao.getCorteTipo().getDescricao() != null){
				consultarDadosOrdemServicoActionForm.setTipoCorteDescricao(historicoManutencaoLigacao.getCorteTipo().getDescricao());
			}else{
				consultarDadosOrdemServicoActionForm.setTipoCorteDescricao("");
			}
		}else{
			consultarDadosOrdemServicoActionForm.setPossuiExecucaoServico("");
		}
		if(ordemServico.getValorHorasTrabalhadas() != null){
			consultarDadosOrdemServicoActionForm.setValorHorasTrabalhadas(ordemServico.getValorHorasTrabalhadas().toString());
		} else {
			consultarDadosOrdemServicoActionForm.setValorHorasTrabalhadas("0");
		}
		if(ordemServico.getValorMateriais() != null){
			consultarDadosOrdemServicoActionForm.setValorMateriais(ordemServico.getValorMateriais().toString());
		} else {
			consultarDadosOrdemServicoActionForm.setValorMateriais("0");
		}
		RelatorioParecerEncerramentoOSHelper relatorioParecerEncerramentoOSHelper = new RelatorioParecerEncerramentoOSHelper();

		relatorioParecerEncerramentoOSHelper.setNumeroOS(consultarDadosOrdemServicoActionForm.getNumeroOS());
		relatorioParecerEncerramentoOSHelper.setSituacaoOS(consultarDadosOrdemServicoActionForm.getSituacaoOS());
		relatorioParecerEncerramentoOSHelper.setDataGeracao(consultarDadosOrdemServicoActionForm.getDataGeracao());
		relatorioParecerEncerramentoOSHelper.setTipoServicoOSId(consultarDadosOrdemServicoActionForm.getTipoServicoId());
		relatorioParecerEncerramentoOSHelper.setTipoServicoOSDescricao(consultarDadosOrdemServicoActionForm.getTipoServicoDescricao());
		if(consultarDadosOrdemServicoActionForm.getObservacaoOs() != null){
			relatorioParecerEncerramentoOSHelper.setObservacao(consultarDadosOrdemServicoActionForm.getObservacaoOs());
		}else{
			relatorioParecerEncerramentoOSHelper.setObservacao("");
		}
		relatorioParecerEncerramentoOSHelper.setUnidadeGeracaoId(consultarDadosOrdemServicoActionForm.getUnidadeGeracaoId());
		relatorioParecerEncerramentoOSHelper.setUnidadeGeracaoDescricao(consultarDadosOrdemServicoActionForm.getUnidadeGeracaoDescricao());
		relatorioParecerEncerramentoOSHelper.setUsuarioGeracaoId(consultarDadosOrdemServicoActionForm.getUsuarioGeracaoId());
		relatorioParecerEncerramentoOSHelper.setUsuarioGeracaoNome(consultarDadosOrdemServicoActionForm.getUsuarioGeracaoNome());
		relatorioParecerEncerramentoOSHelper.setDataUltimaEmissao(consultarDadosOrdemServicoActionForm.getDataUltimaEmissao());

		if(!Util.isVazioOuBranco(consultarDadosOrdemServicoActionForm.getDataEncerramento())){
			Date dataEncerramento = Util.converterStringParaData(consultarDadosOrdemServicoActionForm.getDataEncerramento());
			String data = Util.formatarData(dataEncerramento);
			relatorioParecerEncerramentoOSHelper.setDataEncerramento(data);

			Date horaEncerramento = Util.converteStringParaDateHora(consultarDadosOrdemServicoActionForm.getDataEncerramento());
			String hora = Util.formatarHoraSemSegundos(horaEncerramento);
			relatorioParecerEncerramentoOSHelper.setHoraEncerramento(hora);
		}

		if(consultarDadosOrdemServicoActionForm.getParecerEncerramento() != null){
			relatorioParecerEncerramentoOSHelper.setObservacaoEncerramento(consultarDadosOrdemServicoActionForm.getParecerEncerramento());
		}else{
			relatorioParecerEncerramentoOSHelper.setObservacaoEncerramento("");
		}


		sessao.setAttribute("relatorioParecerEncerramentoOSHelper", relatorioParecerEncerramentoOSHelper);

		// ---------------------------------------------------------------------------------------------------------
		// ---------------------------------------------------------------------------------------------------------
		// 27012011
		// Obter o servicoTipo da OS
		FiltroOrdemServico filtroOS = new FiltroOrdemServico();
		filtroOS.adicionarParametro(new ParametroSimples("id", ordemServico.getId()));
		filtroOS.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.SERVICO_TIPO);
		filtroOS.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.SERVICO_TIPO_REFERENCIA);
		filtroOS.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.DIAMETRO_REDE_AGUA);
		filtroOS.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.DIAMETRO_RAMAL_AGUA);
		filtroOS.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.DIAMETRO_CAVALETE_AGUA);
		filtroOS.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.DIAMETRO_REDE_ESGOTO);
		filtroOS.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.DIAMETRO_RAMAL_ESGOTO);
		filtroOS.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.MATERIAL_REDE_AGUA);
		filtroOS.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.MATERIAL_RAMAL_AGUA);
		filtroOS.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.MATERIAL_CAVALETE_AGUA);
		filtroOS.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.MATERIAL_REDE_ESGOTO);
		filtroOS.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.MATERIAL_RAMAL_ESGOTO);
		filtroOS.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.CAUSA_VAZAMENTO);
		filtroOS.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.AGENTE_EXTERNO);

		Collection colecaoOrdemServico = fachada.pesquisar(filtroOS, OrdemServico.class.getName());

		if(colecaoOrdemServico != null && !colecaoOrdemServico.isEmpty()){
			ordemServico = (OrdemServico) colecaoOrdemServico.iterator().next();
		}

		this.recuperarDadosAgua(consultarDadosOrdemServicoActionForm, ordemServico);
		this.recuperarDadosEsgoto(consultarDadosOrdemServicoActionForm, ordemServico);
		this.recuperarDadosVala(consultarDadosOrdemServicoActionForm, fachada, ordemServico);


		// ---------------------------------------------------------------------------------------------------------
		// ---------------------------------------------------------------------------------------------------------

		// [SB0002] – Habilitar Emissão da OS
		if(fachada.verificarHabilitacaoEmissaoOS(ordemServico)){
			consultarDadosOrdemServicoActionForm.setEmissaoOSHabilitada(ConstantesSistema.SIM.toString());
		}else{
			consultarDadosOrdemServicoActionForm.setEmissaoOSHabilitada(ConstantesSistema.NAO.toString());
		}


		// Colocado por Raphael Rossiter em 26/10/2006
		consultarDadosOrdemServicoActionForm.setNumeroOSParametro("");
		httpServletRequest.setAttribute("nomeCampo", "numeroOSParametro");

		if(sessao.getAttribute("manterOs") == null){
			// Colocado por Sávio Luiz em 24/04/2007
			// Caso venha da consulta de documentos cobranças então não mostra
			// os butões de encerra nem atualizar ordem serviço
			if(sessao.getAttribute("caminhoRetornoOS") == null && httpServletRequest.getParameter("caminhoRetornoOS") != null){
				sessao.setAttribute("caminhoRetornoOS", httpServletRequest.getParameter("caminhoRetornoOS"));
			}else{
				sessao.setAttribute("caminhoRetornoOS", "exibirFiltrarOrdemServicoAction.do");
			}

		}else{
			sessao.removeAttribute("caminhoRetornoOS");
		}
		if(sessao.getAttribute("ocorrenciaInfracao") != null && sessao.getAttribute("colecaoInfracaoTipo") != null){
			sessao.removeAttribute("ocorrenciaInfracao");
			sessao.removeAttribute("colecaoInfracaoTipo");
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

		Collection<OrdemServicoUnidade> colecaoOrdemServicoUnidade = null;

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

	// 27012011
	private void recuperarDadosAgua(ConsultarDadosOrdemServicoActionForm form, OrdemServico ordemServico){

		form.setProfundidadeAgua("");
		form.setPressaoAgua("");

		if(ordemServico != null && ordemServico.getServicoTipo() != null && ordemServico.getServicoTipo().getIndicadorRedeRamalAgua() != 2){

			if(ordemServico.getNumeroProfundidade() != null && !ordemServico.getNumeroProfundidade().equals("")){
				form.setProfundidadeAgua(String.valueOf(ordemServico.getNumeroProfundidade()));
			}

			if(ordemServico.getNumeroPressao() != null && !ordemServico.getNumeroPressao().equals("")){
				form.setPressaoAgua(String.valueOf(ordemServico.getNumeroPressao()));
			}

			if(ordemServico.getCausaVazamento() != null){
				form.setIdCausaAgua(String.valueOf(ordemServico.getCausaVazamento().getId()));
				form.setDescricaoCausaVazamentoAgua(ordemServico.getCausaVazamento().getDescricao());
			}

			// Recuperar id rede ou ramal e Diâmetro
			if(ordemServico.getDiametroRamalAgua() != null){
				form.setIdRedeRamalAgua(ConstantesSistema.INDICADOR_RAMAL);
				form.setIdDiametroAgua(String.valueOf(ordemServico.getDiametroRamalAgua().getId()));
				form.setDescricaoDiametroAgua(ordemServico.getDiametroRamalAgua().getDescricao());
				form.setDescricaoAgua("Ramal");

			}else if(ordemServico.getDiametroRedeAgua() != null){
				form.setIdDiametroAgua(String.valueOf(ordemServico.getDiametroRedeAgua().getId()));
				form.setIdRedeRamalAgua(ConstantesSistema.INDICADOR_REDE);
				form.setDescricaoDiametroAgua(ordemServico.getDiametroRedeAgua().getDescricao());
				form.setDescricaoAgua("Rede");

			}else if(ordemServico.getDiametroCavaleteAgua() != null){
				form.setIdDiametroAgua(String.valueOf(ordemServico.getDiametroCavaleteAgua().getId()));
				form.setIdRedeRamalAgua(ConstantesSistema.INDICADOR_CAVALETE);
				form.setDescricaoDiametroAgua(ordemServico.getDiametroCavaleteAgua().getDescricao());
				form.setDescricaoAgua("Cavalete");
			}

			// Recuperar dados material
			if(ordemServico.getMaterialRamalAgua() != null){
				form.setIdMaterialAgua(String.valueOf(ordemServico.getMaterialRamalAgua().getId()));
				form.setDescricaoMaterialAgua(ordemServico.getMaterialRamalAgua().getDescricao());

			}else if(ordemServico.getMaterialRedeAgua() != null){
				form.setIdMaterialAgua(String.valueOf(ordemServico.getMaterialRedeAgua().getId()));
				form.setDescricaoMaterialAgua(ordemServico.getMaterialRedeAgua().getDescricao());

			}else if(ordemServico.getMaterialCavaleteAgua() != null){
				form.setIdMaterialAgua(String.valueOf(ordemServico.getMaterialCavaleteAgua().getId()));
				form.setDescricaoMaterialAgua(ordemServico.getMaterialCavaleteAgua().getDescricao());
			}

			// Recuperar Agente
			if(ordemServico.getAgenteExterno() != null){
				form.setIdAgenteExternoAgua(String.valueOf(ordemServico.getAgenteExterno().getId()));
				form.setDescricaoAgenteExterno(ordemServico.getAgenteExterno().getDescricao());
			}

		}

		// Verifica se descricaoAgua e causaVazamentoAgua estão preenchidos e se estão, indica que
		// deve exibir os dados de Rede/Ramal Água
		if(!GenericValidator.isBlankOrNull(form.getDescricaoAgua())
						&& !GenericValidator.isBlankOrNull(form.getDescricaoCausaVazamentoAgua())){
			form.setExibirDadosRedeRamalAgua("true");
		}else{
			form.setExibirDadosRedeRamalAgua(null);
		}
	}

	// 27012011
	private void recuperarDadosEsgoto(ConsultarDadosOrdemServicoActionForm form, OrdemServico ordemServico){

		form.setProfundidadeEsgoto("");
		form.setPressaoEsgoto("");

		if(ordemServico != null && ordemServico.getServicoTipo() != null
						&& ordemServico.getServicoTipo().getIndicadorRedeRamalEsgoto() != 2){

			if(ordemServico.getNumeroProfundidade() != null && !ordemServico.getNumeroProfundidade().equals("")){
				form.setProfundidadeEsgoto(String.valueOf(ordemServico.getNumeroProfundidade()));
			}

			if(ordemServico.getNumeroPressao() != null && !ordemServico.getNumeroPressao().equals("")){
				form.setPressaoEsgoto(String.valueOf(ordemServico.getNumeroPressao()));
			}

			if(ordemServico.getCausaVazamento() != null){
				form.setIdCausaEsgoto(String.valueOf(ordemServico.getCausaVazamento().getId()));
				form.setDescricaoCausaVazamentoEsgoto(ordemServico.getCausaVazamento().getDescricao());

			}

			// Recuperar id rede ou ramal e Diâmetro
			if(ordemServico.getDiametroRamalEsgoto() != null){
				form.setIdRedeRamalEsgoto(ConstantesSistema.INDICADOR_RAMAL);
				form.setIdDiametroEsgoto(String.valueOf(ordemServico.getDiametroRamalEsgoto().getId()));
				form.setDescricaoDiametroEsgoto(ordemServico.getDiametroRamalEsgoto().getDescricao());
				form.setDescricaoEsgoto("Ramal");

			}else if(ordemServico.getDiametroRedeEsgoto() != null){
				form.setIdDiametroEsgoto(String.valueOf(ordemServico.getDiametroRedeEsgoto().getId()));
				form.setIdRedeRamalEsgoto(ConstantesSistema.INDICADOR_REDE);
				form.setDescricaoDiametroEsgoto(ordemServico.getDiametroRedeEsgoto().getDescricao());
				form.setDescricaoEsgoto("Rede");
			}

			// Recuperar dados material
			if(ordemServico.getMaterialRamalEsgoto() != null){
				form.setIdMaterialEsgoto(String.valueOf(ordemServico.getMaterialRamalEsgoto().getId()));
				form.setDescricaoMaterialEsgoto(ordemServico.getMaterialRamalEsgoto().getDescricao());

			}else if(ordemServico.getMaterialRedeEsgoto() != null){
				form.setIdMaterialEsgoto(String.valueOf(ordemServico.getMaterialRedeEsgoto().getId()));
				form.setDescricaoMaterialEsgoto(ordemServico.getMaterialRedeEsgoto().getDescricao());
			}

			// Recuperar Agente
			if(ordemServico.getAgenteExterno() != null){
				form.setIdAgenteExternoEsgoto(String.valueOf(ordemServico.getAgenteExterno().getId()));
				form.setDescricaoAgenteExterno(ordemServico.getAgenteExterno().getDescricao());
			}

		}

		// Verifica se descricaoEsgoto e causaVazamentoEsgoto estão preenchidos e se estão, indica
		// que deve exibir os dados de Rede/Ramal Esgoto
		if(!GenericValidator.isBlankOrNull(form.getDescricaoEsgoto())
						&& !GenericValidator.isBlankOrNull(form.getDescricaoCausaVazamentoEsgoto())){
			form.setExibirDadosRedeRamalEsgoto("true");
		}else{
			form.setExibirDadosRedeRamalEsgoto(null);
		}
	}

	// 27012011
	private Collection recuperarDadosVala(ConsultarDadosOrdemServicoActionForm form, Fachada fachada, OrdemServico ordemServico){

		// pesquisar o objeto OrdemServicoValaPavimento que está associado à ordemServico que vem
		// como parametro
		Collection valas = fachada.pesquisarOrdemServicoValaPavimento(ordemServico.getId());
		OrdemServicoValaPavimento vala = null;
		if(valas != null && !valas.isEmpty()){
			vala = (OrdemServicoValaPavimento) valas.iterator().next();
		}

		if(vala != null){
			/*
			 * // Vala
			 * private String numeroVala;
			 * private String idLocalOcorrencia;
			 * private String idPavimento;
			 * private String comprimentoVala;
			 * private String larguraVala;
			 * private String profundidadeVala;
			 * private String indicadorValaAterrada;
			 * private String indicadorEntulho;
			 * private String descricaoLocalOcorrencia;
			 * private String descricaoPavimento;
			 */
			form.setNumeroVala(vala.getNumeroVala().toString());
			form.setComprimentoVala(vala.getNumeroComprimento().toString());
			form.setLarguraVala(vala.getNumeroLargura().toString());
			form.setProfundidadeVala(vala.getNumeroProfundidade().toString());
			form.setDescricaoLocalOcorrencia(vala.getLocalOcorrencia().getDescricao());
			if(vala.getPavimentoCalcada() != null){
				form.setDescricaoPavimento(vala.getPavimentoCalcada().getDescricao());

			}else if(vala.getPavimentoRua() != null){
				form.setDescricaoPavimento(vala.getPavimentoRua().getDescricao());

			}
			if(vala.getQuantidadeEntulho() != null){
				form.setQuantidadeEntulho(vala.getQuantidadeEntulho().toString());
			}
			if(vala.getEntulhoMedida() != null){
				form.setDescricaoEntulhoMedida(vala.getEntulhoMedida().getDescricao());
			}
			if(vala.getNumeroComprimentoTutulacaoAguaPluvial() != null){
				form.setComprimentoTutulacaoAguaPluvial(vala.getNumeroComprimentoTutulacaoAguaPluvial().toString());
			}
			if(vala.getNumeroDiametroTutulacaoAguaPluvial() != null){
				form.setDiametroTutulacaoAguaPluvial(vala.getNumeroDiametroTutulacaoAguaPluvial().toString());
			}

		}
		return valas;
	}
}