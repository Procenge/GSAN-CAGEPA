///*
// * Copyright (C) 2007-2007 the GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
// *
// * This file is part of GSAN, an integrated service management system for Sanitation
// *
// * GSAN is free software; you can redistribute it and/or modify
// * it under the terms of the GNU General Public License as published by
// * the Free Software Foundation; either version 2 of the License.
// *
// * GSAN is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// * GNU General Public License for more details.
// *
// * You should have received a copy of the GNU General Public License
// * along with this program; if not, write to the Free Software
// * Foundation, Inc., 59 Temple Place – Suite 330, Boston, MA 02111-1307, USA
// */
//
///*
// * GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
// * Copyright (C) <2007> 
// * Adriano Britto Siqueira
// * Alexandre Santos Cabral
// * Ana Carolina Alves Breda
// * Ana Maria Andrade Cavalcante
// * Aryed Lins de Araújo
// * Bruno Leonardo Rodrigues Barros
// * Carlos Elmano Rodrigues Ferreira
// * Cláudio de Andrade Lira
// * Denys Guimarães Guenes Tavares
// * Eduardo Breckenfeld da Rosa Borges
// * Fabíola Gomes de Araújo
// * Flávio Leonardo Cavalcanti Cordeiro
// * Francisco do Nascimento Júnior
// * Homero Sampaio Cavalcanti
// * Ivan Sérgio da Silva Júnior
// * José Edmar de Siqueira
// * José Thiago Tenório Lopes
// * Kássia Regina Silvestre de Albuquerque
// * Leonardo Luiz Vieira da Silva
// * Márcio Roberto Batista da Silva
// * Maria de Fátima Sampaio Leite
// * Micaela Maria Coelho de Araújo
// * Nelson Mendonça de Carvalho
// * Newton Morais e Silva
// * Pedro Alexandre Santos da Silva Filho
// * Rafael Corrêa Lima e Silva
// * Rafael Francisco Pinto
// * Rafael Koury Monteiro
// * Rafael Palermo de Araújo
// * Raphael Veras Rossiter
// * Roberto Sobreira Barbalho
// * Rodrigo Avellar Silveira
// * Rosana Carvalho Barbosa
// * Sávio Luiz de Andrade Cavalcante
// * Tai Mu Shih
// * Thiago Augusto Souza do Nascimento
// * Tiago Moreno Rodrigues
// * Vivianne Barbosa Sousa
// *
// * Este programa é software livre; você pode redistribuí-lo e/ou
// * modificá-lo sob os termos de Licença Pública Geral GNU, conforme
// * publicada pela Free Software Foundation; versão 2 da
// * Licença.
// * Este programa é distribuído na expectativa de ser útil, mas SEM
// * QUALQUER GARANTIA; sem mesmo a garantia implícita de
// * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
// * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
// * detalhes.
// * Você deve ter recebido uma cópia da Licença Pública Geral GNU
// * junto com este programa; se não, escreva para Free Software
// * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
// * 02111-1307, USA.
// */
//
//package gcom.gui.atendimentopublico.ordemservico;
//
//import gcom.atendimentopublico.ordemservico.Atividade;
//import gcom.atendimentopublico.ordemservico.Equipe;
//import gcom.atendimentopublico.ordemservico.FiltroEquipe;
//import gcom.atendimentopublico.ordemservico.FiltroProgramacaoRoteiro;
//import gcom.atendimentopublico.ordemservico.MotivoInterrupcao;
//import gcom.atendimentopublico.ordemservico.OrdemServico;
//import gcom.atendimentopublico.ordemservico.OrdemServicoAtividade;
//import gcom.atendimentopublico.ordemservico.OrdemServicoInterrupcaoExecucao;
//import gcom.atendimentopublico.ordemservico.OrdemServicoProgramacao;
//import gcom.atendimentopublico.ordemservico.OsAtividadePeriodoExecucao;
//import gcom.atendimentopublico.ordemservico.OsExecucaoEquipe;
//import gcom.atendimentopublico.ordemservico.ProgramacaoRoteiro;
//import gcom.atendimentopublico.ordemservico.bean.ManterDadosAtividadesOrdemServicoHelper;
//import gcom.atendimentopublico.ordemservico.bean.OSAtividadePeriodoExecucaoHelper;
//import gcom.atendimentopublico.ordemservico.bean.OSExecucaoEquipeHelper;
//import gcom.cadastro.unidade.UnidadeOrganizacional;
//import gcom.fachada.Fachada;
//import gcom.gui.ActionServletException;
//import gcom.gui.GcomAction;
//import gcom.seguranca.acesso.usuario.Usuario;
//import gcom.util.ConstantesSistema;
//import gcom.util.Util;
//import gcom.util.filtro.ParametroSimples;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.List;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.apache.struts.action.ActionForm;
//import org.apache.struts.action.ActionForward;
//import org.apache.struts.action.ActionMapping;
//
///**
// * Esta classe tem por finalidade exibir para o usuário a tela que receberá os
// * parâmetros para realização da atualização dos dados das atividades de uma OS
// * (Horas)
// * 
// * @author Raphael Rossiter
// * @date 18/09/2006
// */
//public class ExibirManterHorasExecucaoEquipeOSAction
//				extends GcomAction {
//
//	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
//					HttpServletResponse httpServletResponse){
//
//		ActionForward retorno = actionMapping.findForward("manterHorasExecucaoEquipeOS");
//
//		ManterHorasExecucaoEquipeOSActionForm form = (ManterHorasExecucaoEquipeOSActionForm) actionForm;
//
//		HttpSession sessao = httpServletRequest.getSession(false);
//
//		Fachada fachada = Fachada.getInstancia();
//
//		// Carregamento inicial
//		String numeroOS = httpServletRequest.getParameter("ordemServico");
//
//		if(numeroOS != null && !numeroOS.equalsIgnoreCase("")){
//
//			// Identifica se a chamada foi feita por uma tela principal ou por um popup
//			String caminhoRetorno = httpServletRequest.getParameter("caminhoRetorno");
//
//			if(caminhoRetorno != null && !caminhoRetorno.equalsIgnoreCase("")){
//				sessao.setAttribute("caminhoRetornoManterHoras", caminhoRetorno);
//			}
//
//			String idAtividade = httpServletRequest.getParameter("idAtividade");
//			String descricaoAtividade = httpServletRequest.getParameter("descricaoAtividade");
//
//			form.setNumeroOS(numeroOS);
//			form.setIdAtividade(idAtividade);
//			form.setDescricaoAtividade(descricaoAtividade);
//
//			/*
//			 * Apresentar na data de execução a data do roteiro caso tenha sido informada, caso
//			 * contrário informar
//			 * a data do encerramento
//			 */
//			String dataRoteiro = httpServletRequest.getParameter("dataRoteiro");
//			String dataEncerramento = httpServletRequest.getParameter("dataEncerramento");
//
//			if(dataRoteiro != null && !dataRoteiro.equalsIgnoreCase("")){
//				form.setDataExecucao(dataRoteiro);
//				form.setDataEncerramentoOS("");
//				httpServletRequest.setAttribute("nomeCampo", "horaInicioExecucao");
//				sessao.setAttribute("desabilitarDataExecucao", "OK");
//			}else{
//				form.setDataExecucao(dataEncerramento);
//				form.setDataEncerramentoOS(dataEncerramento);
//				httpServletRequest.setAttribute("nomeCampo", "dataExecucao");
//				sessao.removeAttribute("desabilitarDataExecucao");
//			}
//
//			// Equipes Programadas
//			Collection colecaoEquipes = fachada.obterEquipesProgramadas(Util.converterStringParaInteger(numeroOS));
//			if(colecaoEquipes != null && !colecaoEquipes.isEmpty()){
//				sessao.setAttribute("colecaoEquipe", colecaoEquipes);
//			}
//
//			// Caso a OS esteja associada a um documento de cobrança
//			sessao.removeAttribute("documentoCobranca");
//			sessao.removeAttribute("registroAtendimento");
//			sessao.removeAttribute("colecaoEquipesPorUnidade");
//
//			if(fachada.verificarOSAssociadaDocumentoCobranca(Util.converterStringParaInteger(numeroOS))){
//				sessao.setAttribute("documentoCobranca", "OK");
//			}
//			// Caso a OS esteja associada a um RA
//			else if(fachada.verificarOSAssociadaRA(Util.converterStringParaInteger(numeroOS))){
//
//				// Pesquisa o último tramite do RA para obter a unidade de destino
//				UnidadeOrganizacional unidadeDestino = fachada.obterUnidadeDestinoUltimoTramite(Util.converterStringParaInteger(numeroOS));
//				if(unidadeDestino != null){
//
//					// Obtém todas as equipes pertencentens a unidade de destino
//					Collection colecaoEquipesPorUnidade = fachada.obterEquipesPorUnidade(unidadeDestino.getId());
//
//					if(colecaoEquipesPorUnidade != null && !colecaoEquipesPorUnidade.isEmpty()){
//						sessao.setAttribute("registroAtendimento", "OK");
//						sessao.setAttribute("colecaoEquipesPorUnidade", colecaoEquipesPorUnidade);
//					}
//				}
//			}
//
//			// Inicializando o formulário
//			form.setHoraInicioExecucao("");
//			form.setHoraFimExecucao("");
//			form.setIdEquipeProgramada("");
//			form.setIdEquipeNaoProgramada("");
//			form.setDescricaoEquipeNaoProgramada("");
//
//			// Pesquisar Motivo Interrupção
//			Collection<MotivoInterrupcao> colecaoMotivoInterrupcao = fachada.pesquisarMotivoInterrupcao();
//			sessao.setAttribute("colecaoMotivoInterrupcao", colecaoMotivoInterrupcao);
//
//			// Recuperar dados de Interrupção
//			Equipe equipeAtual = (Equipe) sessao.getAttribute("equipeAtual");
//			Date dtDataRoteiro = Util.converteStringParaDate(dataRoteiro);
//			if(equipeAtual != null){
//				this.recuperarDadosInterrupcaoExecucao(sessao, httpServletRequest, form, fachada, equipeAtual.getId(), dtDataRoteiro);
//			}
//
//		}
//
//		// Recuperando colecao da sessao
//		Collection<OrdemServicoInterrupcaoExecucao> colecaoInterrupcaoExecucaoSessao = (Collection) sessao
//						.getAttribute("colecaoInterrupcaoExecucao");
//
//		if(colecaoInterrupcaoExecucaoSessao != null && !colecaoInterrupcaoExecucaoSessao.isEmpty()){
//			httpServletRequest.setAttribute("colecaoInterrupcaoExecucao", colecaoInterrupcaoExecucaoSessao);
//		}
//
//		// Pesquisar Equipe ENTER
//		if((form.getIdEquipeNaoProgramada() != null && !form.getIdEquipeNaoProgramada().equals(""))
//						&& (form.getDescricaoEquipeNaoProgramada() == null || form.getDescricaoEquipeNaoProgramada().equals(""))){
//
//			FiltroEquipe filtroEquipe = new FiltroEquipe();
//
//			filtroEquipe.adicionarParametro(new ParametroSimples(FiltroEquipe.ID, form.getIdEquipeNaoProgramada()));
//
//			filtroEquipe.adicionarParametro(new ParametroSimples(FiltroEquipe.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
//
//			Collection colecaoEquipe = fachada.pesquisar(filtroEquipe, Equipe.class.getName());
//
//			if(colecaoEquipe == null || colecaoEquipe.isEmpty()){
//
//				form.setIdEquipeNaoProgramada("");
//				form.setDescricaoEquipeNaoProgramada("EQUIPE INEXISTENTE");
//
//				httpServletRequest.setAttribute("corEquipe", "exception");
//				httpServletRequest.setAttribute("nomeCampo", "idEquipeNaoProgramada");
//
//			}else{
//
//				Equipe equipe = (Equipe) Util.retonarObjetoDeColecao(colecaoEquipe);
//
//				form.setIdEquipeNaoProgramada(equipe.getId().toString());
//				form.setDescricaoEquipeNaoProgramada(equipe.getNome());
//
//				httpServletRequest.setAttribute("nomeCampo", "botaoAdicionar");
//
//			}
//		}
//
//		// Pesquisar Equipe POPUP
//		String pesquisarEquipe = httpServletRequest.getParameter("pesquisarEquipe");
//
//		if(pesquisarEquipe != null && !pesquisarEquipe.equalsIgnoreCase("")){
//			retorno = actionMapping.findForward("pesquisarEquipe");
//		}
//
//		// Recebendo dados Equipe POPUP
//		if(httpServletRequest.getParameter("idCampoEnviarDados") != null){
//			form.setIdEquipeNaoProgramada(httpServletRequest.getParameter("idCampoEnviarDados"));
//			form.setDescricaoEquipeNaoProgramada(httpServletRequest.getParameter("descricaoCampoEnviarDados"));
//
//			httpServletRequest.setAttribute("nomeCampo", "horaInicioExecucao");
//		}
//
//		// Adicionar
//		String adicionarPeriodoEquipe = httpServletRequest.getParameter("adicionarPeriodoEquipe");
//
//		if(adicionarPeriodoEquipe != null && !adicionarPeriodoEquipe.equalsIgnoreCase("")){
//
//			// Verificando as informações colhidas
//			// ===================================================================================
//			Integer idEquipe = null;
//			if(form.getIdEquipeProgramada() != null && !form.getIdEquipeProgramada().equalsIgnoreCase("")){
//				idEquipe = Util.converterStringParaInteger(form.getIdEquipeProgramada());
//			}else{
//				idEquipe = Util.converterStringParaInteger(form.getIdEquipeNaoProgramada());
//			}
//
//			fachada.verificaDadosAdicionarPeriodoEquipe(form.getDataExecucao(), form.getHoraInicioExecucao(), form.getHoraFimExecucao(),
//							idEquipe, form.getDataEncerramentoOS(), Util.converterStringParaInteger(form.getNumeroOS()));
//			// ===================================================================================
//
//			// Informando OrdemServicoAtividade
//			if(sessao.getAttribute("colecaoManterDadosAtividadesOrdemServicoHelper") != null){
//
//				Collection colecaoManterDadosAtividadesOrdemServicoHelper = (Collection) sessao
//								.getAttribute("colecaoManterDadosAtividadesOrdemServicoHelper");
//
//				this.informarOrdemServicoAtividade(colecaoManterDadosAtividadesOrdemServicoHelper, Util.converterStringParaInteger(form
//								.getNumeroOS()), Util.converterStringParaInteger(form.getIdAtividade()), form.getDataExecucao(), form
//								.getHoraInicioExecucao(), form.getHoraFimExecucao(), idEquipe, sessao, fachada);
//			}else{
//
//				this.informarOrdemServicoAtividade(null, Util.converterStringParaInteger(form.getNumeroOS()), Util
//								.converterStringParaInteger(form.getIdAtividade()), form.getDataExecucao(), form.getHoraInicioExecucao(),
//								form.getHoraFimExecucao(), idEquipe, sessao, fachada);
//
//			}
//
//			// Inicializando o formulário
//			form.setHoraInicioExecucao("");
//			form.setHoraFimExecucao("");
//			form.setIdEquipeProgramada("");
//			form.setIdEquipeNaoProgramada("");
//			form.setDescricaoEquipeNaoProgramada("");
//
//			httpServletRequest.setAttribute("nomeCampo", "horaInicioExecucao");
//		}
//
//		// Remover
//		String removerPeriodoEquipe = httpServletRequest.getParameter("removerPeriodoEquipe");
//
//		if(removerPeriodoEquipe != null && !removerPeriodoEquipe.equalsIgnoreCase("")){
//
//			long identificadorEquipe = Long.valueOf(removerPeriodoEquipe);
//			Collection colecaoSessao = (Collection) sessao.getAttribute("colecaoManterDadosAtividadesOrdemServicoHelper");
//
//			this.removerPeriodoExecucaoEquipe(identificadorEquipe, Util.converterStringParaInteger(form.getIdAtividade()), colecaoSessao);
//
//			// Inicializando o formulário
//			form.setHoraInicioExecucao("");
//			form.setHoraFimExecucao("");
//			form.setIdEquipeProgramada("");
//			form.setIdEquipeNaoProgramada("");
//			form.setDescricaoEquipeNaoProgramada("");
//
//			httpServletRequest.setAttribute("nomeCampo", "horaInicioExecucao");
//		}
//
//		// Adicionar Interrupção
//		String adicionarInterrupcao = httpServletRequest.getParameter("adicionarInterrupcao");
//
//		if(isTokenValid(httpServletRequest)){
//			if(adicionarInterrupcao != null && !adicionarInterrupcao.equals("")){
//
//				String horaInicioInterrupcao = form.getHoraInicioInterrupcao();
//				String horaFimInterrupcao = form.getHoraFimInterrupcao();
//				String idMotivoInterrupcao = form.getIdMotivoInterrupcao();
//				String dataExecucao = form.getDataExecucao();
//
//				// Validar hora início e fim de interrupção.
//				if(Util.compararHoraMinuto(horaInicioInterrupcao, horaFimInterrupcao, ">")){
//					throw new ActionServletException("atencao.hora_final_anterior_hora_inicio");
//				}
//
//				// Verificando motivo informado
//				if(idMotivoInterrupcao == null || idMotivoInterrupcao == ""
//								|| Integer.valueOf(idMotivoInterrupcao) == ConstantesSistema.NUMERO_NAO_INFORMADO){
//
//					throw new ActionServletException("atencao.informar_motivo_interrupcao");
//				}
//
//				this.informarInterrupcao(fachada, sessao, httpServletRequest, form.getNumeroOS(), form.getIdAtividade(), dataExecucao,
//								horaInicioInterrupcao, horaFimInterrupcao, idMotivoInterrupcao);
//			}
//		}
//
//		// Remover Interrupcao
//		String posicaoRemoverInterrupcao = httpServletRequest.getParameter("posicaoInterrupcao");
//
//		if(isTokenValid(httpServletRequest)){
//			if(posicaoRemoverInterrupcao != null && !posicaoRemoverInterrupcao.equals("")){
//				removerInterrupcaoExecucao(posicaoRemoverInterrupcao, sessao, httpServletRequest);
//			}
//		}
//
//		// Objetos utilizados apenas para facilitar a quebra na exibição
//		httpServletRequest.setAttribute("numeroOS", form.getNumeroOS());
//		httpServletRequest.setAttribute("idAtividade", form.getIdAtividade());
//
//		saveToken(httpServletRequest);
//		return retorno;
//	}
//
//	/**
//	 * Método responsável por realizar a exclusão de uma interrupção.
//	 * 
//	 * @author Virgínia Melo
//	 * @date 01/07/2009
//	 * @param posicaoLinha
//	 *            Posição da linha que será excluída.
//	 * @param sessao
//	 *            Sessão
//	 */
//	private void removerInterrupcaoExecucao(String posicaoLinha, HttpSession sessao, HttpServletRequest request){
//
//		if((posicaoLinha != null && !posicaoLinha.equalsIgnoreCase(""))
//						&& (sessao.getAttribute("colecaoInterrupcaoExecucao") != null && !sessao.getAttribute("colecaoInterrupcaoExecucao")
//										.equals(""))){
//
//			List colecao = (ArrayList) sessao.getAttribute("colecaoInterrupcaoExecucao");
//			if(colecao.size() > Integer.valueOf(posicaoLinha)){
//				colecao.remove(Integer.parseInt(posicaoLinha));
//				request.setAttribute("colecaoInterrupcaoExecucao", colecao);
//
//			}
//		}
//	}
//
//	private void recuperarDadosInterrupcaoExecucao(HttpSession sessao, HttpServletRequest httpServletRequest,
//					ManterHorasExecucaoEquipeOSActionForm form, Fachada fachada, Integer idEquipeAtual, Date dataRoteiro){
//
//		OrdemServicoProgramacao osProgramacao = null;
//		Collection<OrdemServicoInterrupcaoExecucao> colecaoInterrupcaoExecucao = null;
//
//		// Recuperando colecao da sessao
//		Collection<OrdemServicoInterrupcaoExecucao> colecaoInterrupcaoExecucaoSessao = (Collection) sessao
//						.getAttribute("colecaoInterrupcaoExecucao");
//
//		// Verificando se existe alguma informacao de execucao cadastrada para exibir
//		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
//		Integer idUnidadeLotacao = usuario.getUnidadeOrganizacional().getId();
//
//		if(dataRoteiro != null && !dataRoteiro.equals("")){
//
//			if(colecaoInterrupcaoExecucaoSessao == null || colecaoInterrupcaoExecucaoSessao.isEmpty()){
//
//				// Consulta programacaoRoteiro
//				ProgramacaoRoteiro programacaoRoteiro = this.consultarProgramacaoRoteiro(dataRoteiro, idUnidadeLotacao);
//
//				// Buscar a OrdemServicoProgramacao
//				osProgramacao = fachada.pesquisarOrdemServicoProgramacao(programacaoRoteiro.getId(), Integer.valueOf(form.getNumeroOS()),
//								idEquipeAtual);
//
//				// Pesquisar Interrupções
//				if(osProgramacao != null){
//					colecaoInterrupcaoExecucao = fachada.pesquisarOSInterrupcaoExecucao(osProgramacao.getId());
//				}
//
//				// Pesquisar Interrupções
//				if(colecaoInterrupcaoExecucao != null && !colecaoInterrupcaoExecucao.isEmpty()){
//					if(colecaoInterrupcaoExecucaoSessao != null && !colecaoInterrupcaoExecucaoSessao.isEmpty()){
//						colecaoInterrupcaoExecucaoSessao.addAll(colecaoInterrupcaoExecucao);
//					}else{
//						sessao.setAttribute("colecaoInterrupcaoExecucao", colecaoInterrupcaoExecucao);
//					}
//				}
//			}
//
//		}else{
//			throw new ActionServletException("atencao.erro_ao_recuperar_programacao_roteiro");
//		}
//
//	}
//
//	private ProgramacaoRoteiro consultarProgramacaoRoteiro(Date dataRoterio, Integer unidade){
//
//		FiltroProgramacaoRoteiro filtroProgramacaoRoteiro = new FiltroProgramacaoRoteiro();
//		filtroProgramacaoRoteiro.adicionarParametro(new ParametroSimples(FiltroProgramacaoRoteiro.DATA_ROTEIRO, dataRoterio));
//		filtroProgramacaoRoteiro.adicionarParametro(new ParametroSimples(FiltroProgramacaoRoteiro.UNIDADE_ORGANIZACIONAL_ID, unidade));
//
//		Collection colecaoProgramacaoRoteiro = Fachada.getInstancia().pesquisar(filtroProgramacaoRoteiro,
//						ProgramacaoRoteiro.class.getName());
//		ProgramacaoRoteiro programacaoRoteiro = (ProgramacaoRoteiro) Util.retonarObjetoDeColecao(colecaoProgramacaoRoteiro);
//
//		return programacaoRoteiro;
//
//	}
//
//	private void informarInterrupcao(Fachada fachada, HttpSession sessao, HttpServletRequest request, String numeroOS, String idAtividade,
//					String dataExecucao, String horaInicioInterrupcao, String horaFimInterrupcao, String idMotivoInterrupcao){
//
//		// Recuperando a coleção de interrupção.
//		Collection<OrdemServicoInterrupcaoExecucao> colecaoInterrupcaoExecucao = (Collection) sessao
//						.getAttribute("colecaoInterrupcaoExecucao");
//
//		Collection<OSAtividadePeriodoExecucaoHelper> colecaoPeriodoExecucao = null;
//
//		// Colecao das atividades
//		Collection<ManterDadosAtividadesOrdemServicoHelper> colecaoManterDadosAtividadesOrdemServicoHelper = (Collection<ManterDadosAtividadesOrdemServicoHelper>) sessao
//						.getAttribute("colecaoManterDadosAtividadesOrdemServicoHelper");
//
//		if(colecaoManterDadosAtividadesOrdemServicoHelper != null && !colecaoManterDadosAtividadesOrdemServicoHelper.isEmpty()){
//
//			for(ManterDadosAtividadesOrdemServicoHelper helper : colecaoManterDadosAtividadesOrdemServicoHelper){
//
//				// Verifica se já existe na coleção uma OSAtividade com o mesmo numOS e mesma
//				// atividade.
//				if(helper.getOrdemServicoAtividade() != null
//								&& helper.getOrdemServicoAtividade().getOrdemServico().getId().equals(Integer.valueOf(numeroOS))
//								&& helper.getOrdemServicoAtividade().getAtividade().getId().equals(Integer.valueOf(idAtividade))){
//
//					if(helper.getColecaoOSAtividadePeriodoExecucaoHelper() != null
//									&& !helper.getColecaoOSAtividadePeriodoExecucaoHelper().isEmpty()){
//
//						// recuperar a colecao dos periodos de execução para validação.
//						colecaoPeriodoExecucao = helper.getColecaoOSAtividadePeriodoExecucaoHelper();
//					}
//				}
//			}
//		}
//
//		// Gerando uma String no formato dd/MM/yyyy HH:mm:ss
//		String strDataHoraExecucaoInicio = dataExecucao + " " + horaInicioInterrupcao + ":00";
//		String strDataHoraExecucaoFim = dataExecucao + " " + horaFimInterrupcao + ":00";
//
//		Date dataHoraExecucaoInicio = Util.converteStringParaDateHora(strDataHoraExecucaoInicio);
//		Date dataHoraExecucaoFim = Util.converteStringParaDateHora(strDataHoraExecucaoFim);
//
//		// Verificar se hora informada está dentro dos períodos de execução já informados.
//		if(colecaoPeriodoExecucao != null && !colecaoPeriodoExecucao.isEmpty()){
//
//			boolean intervaloOk = false;
//
//			for(OSAtividadePeriodoExecucaoHelper periodoExecucao : colecaoPeriodoExecucao){
//
//				if(Util.compararHoraMinuto(horaInicioInterrupcao, periodoExecucao.getHoraMinutoInicioParaQuebra(), ">")
//								&& Util.compararHoraMinuto(horaFimInterrupcao, periodoExecucao.getHoraMinutoFimParaQuebra(), "<")){
//					intervaloOk = true;
//					break;
//				}
//			}
//
//			if(!intervaloOk){
//				throw new ActionServletException("atencao.periodo_intervalo_execucao_invalido");
//			}
//
//		}else{
//
//			// Se for vazio, solicitar que o período de execução seja informado.
//			throw new ActionServletException("atencao.informe_periodo_execucao");
//		}
//
//		// Montar Objeto Interrupção Execução
//		OrdemServicoInterrupcaoExecucao osInterrupcaoExecucao = new OrdemServicoInterrupcaoExecucao();
//		osInterrupcaoExecucao.setInterrupcaoInicio(dataHoraExecucaoInicio);
//		osInterrupcaoExecucao.setInterrupcaoFim(dataHoraExecucaoFim);
//		osInterrupcaoExecucao.setUltimaAlteracao(new Date());
//
//		// Recuperar o motivoInterrupcao da coleção que está na sessão.
//		Collection<MotivoInterrupcao> colecaoMotivoInterrupcao = (Collection) sessao.getAttribute("colecaoMotivoInterrupcao");
//		for(MotivoInterrupcao motivoInterrupcao : colecaoMotivoInterrupcao){
//			if(motivoInterrupcao.getId().equals(Integer.valueOf(idMotivoInterrupcao))){
//				osInterrupcaoExecucao.setMotivoInterrupcao(motivoInterrupcao);
//			}
//		}
//
//		if(colecaoInterrupcaoExecucao != null && !colecaoInterrupcaoExecucao.isEmpty()){
//			colecaoInterrupcaoExecucao.add(osInterrupcaoExecucao);
//			sessao.setAttribute("colecaoInterrupcaoExecucao", colecaoInterrupcaoExecucao);
//			request.setAttribute("colecaoInterrupcaoExecucao", colecaoInterrupcaoExecucao);
//		}else{
//			Collection<OrdemServicoInterrupcaoExecucao> colecaoOsInterrupcao = new ArrayList<OrdemServicoInterrupcaoExecucao>();
//			colecaoOsInterrupcao.add(osInterrupcaoExecucao);
//			sessao.setAttribute("colecaoInterrupcaoExecucao", colecaoOsInterrupcao);
//			request.setAttribute("colecaoInterrupcaoExecucao", colecaoOsInterrupcao);
//		}
//
//	}
//
//	private void informarOrdemServicoAtividade(Collection colecaoSessao, Integer numeroOS, Integer idAtividade, String dataExecucao,
//					String horaInicio, String horaFim, Integer idEquipe, HttpSession sessao, Fachada fachada){
//
//		ManterDadosAtividadesOrdemServicoHelper manterDadosAtividadesOrdemServicoHelper = null;
//
//		if(colecaoSessao != null && !colecaoSessao.isEmpty()){
//
//			boolean ordemServicoAtividadeInformada = false;
//			Iterator iteratorColecaoSessao = colecaoSessao.iterator();
//
//			while(iteratorColecaoSessao.hasNext()){
//
//				manterDadosAtividadesOrdemServicoHelper = (ManterDadosAtividadesOrdemServicoHelper) iteratorColecaoSessao.next();
//
//				/*
//				 * Verifica se já existe na coleção uma OrdemServicoAtividade com o mesmo número de
//				 * OS e
//				 * mesma atividade informada
//				 */
//				if(manterDadosAtividadesOrdemServicoHelper.getOrdemServicoAtividade() != null
//								&& manterDadosAtividadesOrdemServicoHelper.getOrdemServicoAtividade().getOrdemServico().getId().equals(
//												numeroOS)
//								&& manterDadosAtividadesOrdemServicoHelper.getOrdemServicoAtividade().getAtividade().getId().equals(
//												idAtividade)){
//
//					// Informando OsAtividadePeriodoExecucao
//					Collection colecaoOsAtividadePeriodoExecucaoHelper = this.informarOsAtividadePeriodoExecucao(
//									manterDadosAtividadesOrdemServicoHelper.getColecaoOSAtividadePeriodoExecucaoHelper(), dataExecucao,
//									horaInicio, horaFim, idEquipe, fachada);
//
//					manterDadosAtividadesOrdemServicoHelper
//									.setColecaoOSAtividadePeriodoExecucaoHelper(colecaoOsAtividadePeriodoExecucaoHelper);
//
//					ordemServicoAtividadeInformada = true;
//					break;
//				}
//			}
//
//			/*
//			 * Caso já exista OrdemServicoAtividade informada, porém ainda não foi cadastrada
//			 * nenhuma com o mesmo
//			 * número de OS e atividade informados
//			 */
//			if(!ordemServicoAtividadeInformada){
//
//				// 1º Passo - Gerando o objeto
//				manterDadosAtividadesOrdemServicoHelper = this.gerarOrdemServicoAtividade(numeroOS, idAtividade, dataExecucao, horaInicio,
//								horaFim, idEquipe, fachada);
//
//				// 2º Passo - Adicionando o objeto na coleção que foi recebida (Já foi criada e
//				// colocada na sessão)
//				colecaoSessao.add(manterDadosAtividadesOrdemServicoHelper);
//			}
//		}else{
//
//			// Primeira OrdemServicoAtividade informada
//
//			// 1º Passo - Gerando o objeto
//			manterDadosAtividadesOrdemServicoHelper = this.gerarOrdemServicoAtividade(numeroOS, idAtividade, dataExecucao, horaInicio,
//							horaFim, idEquipe, fachada);
//
//			// 2º Passo - Adicionando o objeto em uma coleção vazia
//			Collection colecaoManterDadosAtividadesOrdemServicoHelper = new ArrayList();
//			colecaoManterDadosAtividadesOrdemServicoHelper.add(manterDadosAtividadesOrdemServicoHelper);
//
//			// 3º Passo - Colocando a coleção gerada na sessão
//			sessao.setAttribute("colecaoManterDadosAtividadesOrdemServicoHelper", colecaoManterDadosAtividadesOrdemServicoHelper);
//
//		}
//	}
//
//	private ManterDadosAtividadesOrdemServicoHelper gerarOrdemServicoAtividade(Integer numeroOS, Integer idAtividade, String dataExecucao,
//					String horaInicio, String horaFim, Integer idEquipe, Fachada fachada){
//
//		ManterDadosAtividadesOrdemServicoHelper manterDadosAtividadesOrdemServicoHelper = new ManterDadosAtividadesOrdemServicoHelper();
//
//		OrdemServico ordemServico = new OrdemServico();
//		ordemServico.setId(numeroOS);
//
//		Atividade atividade = new Atividade();
//		atividade.setId(idAtividade);
//
//		OrdemServicoAtividade ordemServicoAtividade = new OrdemServicoAtividade();
//		ordemServicoAtividade.setAtividade(atividade);
//		ordemServicoAtividade.setOrdemServico(ordemServico);
//
//		manterDadosAtividadesOrdemServicoHelper.setOrdemServicoAtividade(ordemServicoAtividade);
//
//		// Informando OsAtividadePeriodoExecucao
//		Collection colecaoOsAtividadePeriodoExecucaoHelper = this.informarOsAtividadePeriodoExecucao(null, dataExecucao, horaInicio,
//						horaFim, idEquipe, fachada);
//
//		manterDadosAtividadesOrdemServicoHelper.setColecaoOSAtividadePeriodoExecucaoHelper(colecaoOsAtividadePeriodoExecucaoHelper);
//
//		return manterDadosAtividadesOrdemServicoHelper;
//
//	}
//
//	private Collection informarOsAtividadePeriodoExecucao(Collection colecaoOsAtividadePeriodoExecucaoHelper, String dataExecucao,
//					String horaInicio, String horaFim, Integer idEquipe, Fachada fachada){
//
//		Collection colecaoRetorno = null;
//		OSAtividadePeriodoExecucaoHelper osAtividadePeriodoExecucaoHelper = null;
//
//		if(colecaoOsAtividadePeriodoExecucaoHelper != null && !colecaoOsAtividadePeriodoExecucaoHelper.isEmpty()){
//
//			boolean osAtividadePeriodoExecucaoInformada = false;
//			Iterator iteratorcolecaoOsAtividadePeriodoExecucaoHelper = colecaoOsAtividadePeriodoExecucaoHelper.iterator();
//
//			while(iteratorcolecaoOsAtividadePeriodoExecucaoHelper.hasNext()){
//
//				osAtividadePeriodoExecucaoHelper = (OSAtividadePeriodoExecucaoHelper) iteratorcolecaoOsAtividadePeriodoExecucaoHelper
//								.next();
//
//				/*
//				 * Verifica se já existe na coleção uma OSAtividadePeriodoExecucaoHelper com a mesma
//				 * data de execução
//				 * e mesmo período de execução
//				 */
//				if(osAtividadePeriodoExecucaoHelper.getDataExecucaoParaQuebra().equals(Util.converteStringParaDate(dataExecucao))
//								&& osAtividadePeriodoExecucaoHelper.getHoraMinutoInicioParaQuebra().equals(horaInicio)
//								&& osAtividadePeriodoExecucaoHelper.getHoraMinutoFimParaQuebra().equals(horaFim)){
//
//					// Informando OsAtividadePeriodoExecucao
//					Collection colecaoOsExecucaoEquipeHelper = this.informarOsExecucaoEquipe(osAtividadePeriodoExecucaoHelper
//									.getColecaoOSExecucaoEquipeHelper(), idEquipe, fachada);
//
//					osAtividadePeriodoExecucaoHelper.setColecaoOSExecucaoEquipeHelper(colecaoOsExecucaoEquipeHelper);
//
//					osAtividadePeriodoExecucaoInformada = true;
//					break;
//				}
//			}
//
//			/*
//			 * Caso já exista OSAtividadePeriodoExecucaoHelper informada, porém ainda não foi
//			 * cadastrada nenhuma
//			 * com a mesma data de execução e mesmo período de execução
//			 */
//			if(!osAtividadePeriodoExecucaoInformada){
//
//				// 1º Passo - Gerando o objeto
//				osAtividadePeriodoExecucaoHelper = this.gerarOsAtividadePeriodoExecucao(dataExecucao, horaInicio, horaFim, idEquipe,
//								fachada);
//
//				// 2º Passo - Adicionando o objeto na coleção que foi recebida (Já foi criada e
//				// colocada na sessão)
//				colecaoOsAtividadePeriodoExecucaoHelper.add(osAtividadePeriodoExecucaoHelper);
//			}
//
//			return colecaoOsAtividadePeriodoExecucaoHelper;
//		}else{
//
//			// Primeira OsAtividadePeriodoExecucao informada
//
//			// 1º Passo - Gerando o objeto
//			osAtividadePeriodoExecucaoHelper = this.gerarOsAtividadePeriodoExecucao(dataExecucao, horaInicio, horaFim, idEquipe, fachada);
//
//			// 2º Passo - Adicionando o objeto em uma coleção vazia
//			colecaoRetorno = new ArrayList();
//			colecaoRetorno.add(osAtividadePeriodoExecucaoHelper);
//
//			return colecaoRetorno;
//		}
//	}
//
//	private OSAtividadePeriodoExecucaoHelper gerarOsAtividadePeriodoExecucao(String dataExecucao, String horaInicio, String horaFim,
//					Integer idEquipe, Fachada fachada){
//
//		OSAtividadePeriodoExecucaoHelper osAtividadePeriodoExecucaoHelper = new OSAtividadePeriodoExecucaoHelper();
//
//		OsAtividadePeriodoExecucao osAtividadePeriodoExecucao = new OsAtividadePeriodoExecucao();
//
//		// Gerando uma String no formato dd/MM/yyyy HH:mm:ss
//		String dataHoraExecucaoInicio = dataExecucao + " " + horaInicio + ":00";
//		String dataHoraExecucaoFim = dataExecucao + " " + horaFim + ":00";
//
//		osAtividadePeriodoExecucao.setDataInicio(Util.converteStringParaDateHora(dataHoraExecucaoInicio));
//		osAtividadePeriodoExecucao.setDataFim(Util.converteStringParaDateHora(dataHoraExecucaoFim));
//
//		// Objetos utilizados apenas para facilitar a quebra na exibição
//		osAtividadePeriodoExecucaoHelper.setDataExecucaoParaQuebra(Util.converteStringParaDate(dataExecucao));
//		osAtividadePeriodoExecucaoHelper.setHoraMinutoInicioParaQuebra(horaInicio);
//		osAtividadePeriodoExecucaoHelper.setHoraMinutoFimParaQuebra(horaFim);
//
//		osAtividadePeriodoExecucaoHelper.setOsAtividadePeriodoExecucao(osAtividadePeriodoExecucao);
//
//		// Informando OsExecucaoEquipe
//		Collection colecaoOSExecucaoEquipeHelper = this.informarOsExecucaoEquipe(null, idEquipe, fachada);
//
//		osAtividadePeriodoExecucaoHelper.setColecaoOSExecucaoEquipeHelper(colecaoOSExecucaoEquipeHelper);
//
//		return osAtividadePeriodoExecucaoHelper;
//
//	}
//
//	private Collection informarOsExecucaoEquipe(Collection colecaoOSExecucaoEquipeHelper, Integer idEquipe, Fachada fachada){
//
//		Collection colecaoRetorno = null;
//		OSExecucaoEquipeHelper osExecucaoEquipeHelper = null;
//
//		if(colecaoOSExecucaoEquipeHelper != null && !colecaoOSExecucaoEquipeHelper.isEmpty()){
//
//			Iterator iteratorColecaoOSExecucaoEquipeHelper = colecaoOSExecucaoEquipeHelper.iterator();
//
//			while(iteratorColecaoOSExecucaoEquipeHelper.hasNext()){
//
//				osExecucaoEquipeHelper = (OSExecucaoEquipeHelper) iteratorColecaoOSExecucaoEquipeHelper.next();
//
//				/*
//				 * Verifica se já existe na coleção uma OSExecucaoEquipeHelper com a mesma equipe
//				 * [FS002] - Verificar Existência de Dados
//				 */
//				if(osExecucaoEquipeHelper.getOsExecucaoEquipe().getEquipe().getId().equals(idEquipe)){
//
//					throw new ActionServletException("atencao.data_periodo_equipe_ja_informado");
//				}
//			}
//
//			/*
//			 * Caso já exista OSExecucaoEquipeHelper informada, porém ainda não foi cadastrada
//			 * nenhuma
//			 * com a mesma equipe
//			 */
//
//			// 1º Passo - Gerando o objeto
//			osExecucaoEquipeHelper = this.gerarOsExecucaoEquipe(idEquipe, fachada);
//
//			// 2º Passo - Adicionando o objeto na coleção que foi recebida (Já foi criada e colocada
//			// na sessão)
//			colecaoOSExecucaoEquipeHelper.add(osExecucaoEquipeHelper);
//
//			return colecaoOSExecucaoEquipeHelper;
//		}else{
//
//			// Primeira OsExecucaoEquipe informada
//
//			// 1º Passo - Gerando o objeto
//			osExecucaoEquipeHelper = this.gerarOsExecucaoEquipe(idEquipe, fachada);
//
//			// 2º Passo - Adicionando o objeto em uma coleção vazia
//			colecaoRetorno = new ArrayList();
//			colecaoRetorno.add(osExecucaoEquipeHelper);
//
//			return colecaoRetorno;
//		}
//	}
//
//	private OSExecucaoEquipeHelper gerarOsExecucaoEquipe(Integer idEquipe, Fachada fachada){
//
//		OSExecucaoEquipeHelper osExecucaoEquipeHelper = new OSExecucaoEquipeHelper();
//
//		FiltroEquipe filtroEquipe = new FiltroEquipe();
//
//		filtroEquipe.adicionarParametro(new ParametroSimples(FiltroEquipe.ID, idEquipe));
//
//		Collection colecaoEquipe = fachada.pesquisar(filtroEquipe, Equipe.class.getName());
//
//		Equipe equipe = (Equipe) Util.retonarObjetoDeColecao(colecaoEquipe);
//
//		// Alterado para facilitar a identificação de uma equipe na coleção
//		equipe.setUltimaAlteracao(new Date());
//
//		OsExecucaoEquipe osExecucaoEquipe = new OsExecucaoEquipe();
//		osExecucaoEquipe.setEquipe(equipe);
//
//		osExecucaoEquipeHelper.setOsExecucaoEquipe(osExecucaoEquipe);
//
//		return osExecucaoEquipeHelper;
//
//	}
//
//	private void removerPeriodoExecucaoEquipe(long identificacaoEquipe, Integer idAtividade, Collection colecaoSessao){
//
//		Iterator iteratorColecaoSessao = colecaoSessao.iterator();
//		ManterDadosAtividadesOrdemServicoHelper manterDadosAtividadesOrdemServicoHelper = null;
//		OSAtividadePeriodoExecucaoHelper osAtividadePeriodoExecucaoHelper = null;
//		OSExecucaoEquipeHelper osExecucaoEquipeHelper = null;
//
//		boolean removerSuperior = false;
//
//		// Atividade
//		while(iteratorColecaoSessao.hasNext()){
//
//			manterDadosAtividadesOrdemServicoHelper = (ManterDadosAtividadesOrdemServicoHelper) iteratorColecaoSessao.next();
//
//			if(manterDadosAtividadesOrdemServicoHelper.getOrdemServicoAtividade() != null
//							&& manterDadosAtividadesOrdemServicoHelper.getOrdemServicoAtividade().getAtividade().getId()
//											.equals(idAtividade)){
//
//				// Período
//				Collection colecaoOSAtividadePeriodoExecucaoHelper = manterDadosAtividadesOrdemServicoHelper
//								.getColecaoOSAtividadePeriodoExecucaoHelper();
//
//				Iterator iteratorColecaoOSAtividadePeriodoExecucaoHelper = colecaoOSAtividadePeriodoExecucaoHelper.iterator();
//
//				while(iteratorColecaoOSAtividadePeriodoExecucaoHelper.hasNext()){
//
//					osAtividadePeriodoExecucaoHelper = (OSAtividadePeriodoExecucaoHelper) iteratorColecaoOSAtividadePeriodoExecucaoHelper
//									.next();
//
//					// Equipe
//					Collection colecaoOSExecucaoEquipeHelper = osAtividadePeriodoExecucaoHelper.getColecaoOSExecucaoEquipeHelper();
//
//					Iterator iteratorColecaoOSExecucaoEquipeHelper = colecaoOSExecucaoEquipeHelper.iterator();
//
//					while(iteratorColecaoOSExecucaoEquipeHelper.hasNext()){
//
//						osExecucaoEquipeHelper = (OSExecucaoEquipeHelper) iteratorColecaoOSExecucaoEquipeHelper.next();
//
//						if((GcomAction.obterTimestampIdObjeto(osExecucaoEquipeHelper.getOsExecucaoEquipe().getEquipe())) == identificacaoEquipe){
//
//							colecaoOSExecucaoEquipeHelper.remove(osExecucaoEquipeHelper);
//
//							if(colecaoOSExecucaoEquipeHelper.size() < 1){
//								removerSuperior = true;
//							}
//
//							break;
//						}
//					}
//
//					if(removerSuperior){
//						colecaoOSAtividadePeriodoExecucaoHelper.remove(osAtividadePeriodoExecucaoHelper);
//
//						if(colecaoOSAtividadePeriodoExecucaoHelper.size() < 1){
//							removerSuperior = true;
//						}else{
//							removerSuperior = false;
//						}
//
//						break;
//					}
//				}
//
//				if(removerSuperior){
//					break;
//				}
//			}
//		}
//
//		if(removerSuperior){
//			colecaoSessao.remove(manterDadosAtividadesOrdemServicoHelper);
//		}
//	}
// }
