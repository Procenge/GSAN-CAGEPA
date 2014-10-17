///*
// * Copyright (C) 2007-2007 the GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
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
// * Foundation, Inc., 59 Temple Place � Suite 330, Boston, MA 02111-1307, USA
// */
//
///*
// * GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
// * Copyright (C) <2007> 
// * Adriano Britto Siqueira
// * Alexandre Santos Cabral
// * Ana Carolina Alves Breda
// * Ana Maria Andrade Cavalcante
// * Aryed Lins de Ara�jo
// * Bruno Leonardo Rodrigues Barros
// * Carlos Elmano Rodrigues Ferreira
// * Cl�udio de Andrade Lira
// * Denys Guimar�es Guenes Tavares
// * Eduardo Breckenfeld da Rosa Borges
// * Fab�ola Gomes de Ara�jo
// * Fl�vio Leonardo Cavalcanti Cordeiro
// * Francisco do Nascimento J�nior
// * Homero Sampaio Cavalcanti
// * Ivan S�rgio da Silva J�nior
// * Jos� Edmar de Siqueira
// * Jos� Thiago Ten�rio Lopes
// * K�ssia Regina Silvestre de Albuquerque
// * Leonardo Luiz Vieira da Silva
// * M�rcio Roberto Batista da Silva
// * Maria de F�tima Sampaio Leite
// * Micaela Maria Coelho de Ara�jo
// * Nelson Mendon�a de Carvalho
// * Newton Morais e Silva
// * Pedro Alexandre Santos da Silva Filho
// * Rafael Corr�a Lima e Silva
// * Rafael Francisco Pinto
// * Rafael Koury Monteiro
// * Rafael Palermo de Ara�jo
// * Raphael Veras Rossiter
// * Roberto Sobreira Barbalho
// * Rodrigo Avellar Silveira
// * Rosana Carvalho Barbosa
// * S�vio Luiz de Andrade Cavalcante
// * Tai Mu Shih
// * Thiago Augusto Souza do Nascimento
// * Tiago Moreno Rodrigues
// * Vivianne Barbosa Sousa
// *
// * Este programa � software livre; voc� pode redistribu�-lo e/ou
// * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
// * publicada pela Free Software Foundation; vers�o 2 da
// * Licen�a.
// * Este programa � distribu�do na expectativa de ser �til, mas SEM
// * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
// * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
// * PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais
// * detalhes.
// * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
// * junto com este programa; se n�o, escreva para Free Software
// * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
// * 02111-1307, USA.
// */
//
//package gcom.gui.atendimentopublico.ordemservico;
//
//import gcom.atendimentopublico.ordemservico.AgenteExterno;
//import gcom.atendimentopublico.ordemservico.Atividade;
//import gcom.atendimentopublico.ordemservico.CausaVazamento;
//import gcom.atendimentopublico.ordemservico.DiametroCavaleteAgua;
//import gcom.atendimentopublico.ordemservico.DiametroRamalAgua;
//import gcom.atendimentopublico.ordemservico.DiametroRamalEsgoto;
//import gcom.atendimentopublico.ordemservico.DiametroRedeAgua;
//import gcom.atendimentopublico.ordemservico.DiametroRedeEsgoto;
//import gcom.atendimentopublico.ordemservico.Equipe;
//import gcom.atendimentopublico.ordemservico.FiltroAtividade;
//import gcom.atendimentopublico.ordemservico.FiltroOrdemServico;
//import gcom.atendimentopublico.ordemservico.FiltroOrdemServicoAtividade;
//import gcom.atendimentopublico.ordemservico.FiltroOsAtividadePeriodoExecucao;
//import gcom.atendimentopublico.ordemservico.FiltroProgramacaoRoteiro;
//import gcom.atendimentopublico.ordemservico.MaterialCavaleteAgua;
//import gcom.atendimentopublico.ordemservico.MaterialRamalAgua;
//import gcom.atendimentopublico.ordemservico.MaterialRamalEsgoto;
//import gcom.atendimentopublico.ordemservico.MaterialRedeAgua;
//import gcom.atendimentopublico.ordemservico.MaterialRedeEsgoto;
//import gcom.atendimentopublico.ordemservico.MotivoInterrupcao;
//import gcom.atendimentopublico.ordemservico.OrdemServico;
//import gcom.atendimentopublico.ordemservico.OrdemServicoAtividade;
//import gcom.atendimentopublico.ordemservico.OrdemServicoDeslocamento;
//import gcom.atendimentopublico.ordemservico.OrdemServicoInterrupcaoDeslocamento;
//import gcom.atendimentopublico.ordemservico.OrdemServicoInterrupcaoExecucao;
//import gcom.atendimentopublico.ordemservico.OrdemServicoProgramacao;
//import gcom.atendimentopublico.ordemservico.OrdemServicoValaPavimento;
//import gcom.atendimentopublico.ordemservico.OsAtividadePeriodoExecucao;
//import gcom.atendimentopublico.ordemservico.OsExecucaoEquipe;
//import gcom.atendimentopublico.ordemservico.ProgramacaoRoteiro;
//import gcom.atendimentopublico.ordemservico.bean.DadosAtividadesOrdemServicoHelper;
//import gcom.atendimentopublico.ordemservico.bean.ManterDadosAtividadesOrdemServicoHelper;
//import gcom.atendimentopublico.ordemservico.bean.OSAtividadePeriodoExecucaoHelper;
//import gcom.atendimentopublico.ordemservico.bean.OSExecucaoEquipeHelper;
//import gcom.atendimentopublico.registroatendimento.LocalOcorrencia;
//import gcom.cadastro.imovel.PavimentoCalcada;
//import gcom.cadastro.imovel.PavimentoRua;
//import gcom.fachada.Fachada;
//import gcom.gui.ActionServletException;
//import gcom.gui.GcomAction;
//import gcom.seguranca.acesso.usuario.Usuario;
//import gcom.util.ConstantesSistema;
//import gcom.util.Util;
//import gcom.util.filtro.ParametroSimples;
//
//import java.math.BigDecimal;
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
//import org.apache.commons.lang.StringUtils;
//import org.apache.struts.action.ActionForm;
//import org.apache.struts.action.ActionForward;
//import org.apache.struts.action.ActionMapping;
//
///**
// * Esta classe tem por finalidade exibir para o usu�rio a tela que receber� os par�metros para
// * realiza��o da atualiza��o do dados das atividades de
// * uma OS
// * 
// * @author Raphael Rossiter
// * @date 15/09/2006
// */
//public class ExibirManterDadosAtividadesOrdemServicoAction
//				extends GcomAction {
//
//	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest request,
//					HttpServletResponse httpServletResponse){
//
//		ActionForward retorno = actionMapping.findForward("manterDadosAtividadesOrdemServico");
//		ManterDadosAtividadesOrdemServicoActionForm manterDadosAtividadesOrdemServicoActionForm = (ManterDadosAtividadesOrdemServicoActionForm) actionForm;
//		HttpSession sessao = request.getSession(false);
//		Fachada fachada = Fachada.getInstancia();
//		OrdemServico ordemServico = null;
//		Equipe equipeAtual = null;
//
//		/*
//		 * O caso de uso recebe o n�mero sa OS, data de roteiro e a data de encerramento (dever� vir
//		 * informado um ou outro e ambos)
//		 */
//		String numeroOSParametro = request.getParameter("numeroOS");
//
//		if(numeroOSParametro != null && !numeroOSParametro.equals("")){
//
//			String dataRoteiroParametro = request.getParameter("dataRoteiro");
//			if(dataRoteiroParametro == null || "".equals(dataRoteiroParametro)){
//				if(sessao.getAttribute("dataRoteiro") != null){
//					dataRoteiroParametro = (String) sessao.getAttribute("dataRoteiro");
//				}
//			}
//
//			String dataEncerramentoParametro = request.getParameter("dataEncerramento");
//
//			String caminhoRetorno = "";
//			if(request.getParameter("caminhoRetorno") != null){
//				caminhoRetorno = request.getParameter("caminhoRetorno");
//			}else if(request.getAttribute("caminhoRetornoPopUp") != null){
//				caminhoRetorno = (String) request.getAttribute("caminhoRetornoPopUp");
//			}
//			manterDadosAtividadesOrdemServicoActionForm.setCaminhoRetorno(caminhoRetorno);
//
//			// Conversa��o dos objetos
//			Integer numeroOS = Util.converterStringParaInteger(numeroOSParametro);
//			manterDadosAtividadesOrdemServicoActionForm.setNumeroOSForm(numeroOSParametro);
//
//			// Date dataRoteiro = null;
//			if(dataRoteiroParametro != null && !dataRoteiroParametro.equals("")){
//
//				// dataRoteiro = Util.converteStringParaDate(dataRoteiroParametro);
//				manterDadosAtividadesOrdemServicoActionForm.setDataRoteiroForm(dataRoteiroParametro);
//			}
//
//			// Date dataEncerramento = null;
//			if(dataEncerramentoParametro != null && !dataEncerramentoParametro.equals("")){
//
//				// dataEncerramento = Util.converteStringParaDate(dataEncerramentoParametro);
//				manterDadosAtividadesOrdemServicoActionForm.setDataEncerramentoForm(dataEncerramentoParametro);
//			}
//
//			// O sistema apresenta uma lista de atividades associadas ao servi�o tipo que pertencem
//			// � ordem de servi�o
//			if(sessao.getAttribute("colecaoManterDadosAtividadesOrdemServicoHelper") == null){
//				Collection<Atividade> colecaoAtividadeOS = fachada.obterAtividadesOrdemServico(numeroOS);
//				sessao.setAttribute("colecaoAtividade", colecaoAtividadeOS);
//				this.adicionarOrdemServicoAtividadesNaSessao(sessao, colecaoAtividadeOS, numeroOS, fachada);
//			}
//
//			// Obter o servicoTipo da OS
//			FiltroOrdemServico filtroOS = new FiltroOrdemServico();
//			filtroOS.adicionarParametro(new ParametroSimples("id", manterDadosAtividadesOrdemServicoActionForm.getNumeroOSForm()));
//			filtroOS.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.SERVICO_TIPO);
//			filtroOS.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.SERVICO_TIPO_REFERENCIA);
//
//			Collection colecaoOrdemServico = fachada.pesquisar(filtroOS, OrdemServico.class.getName());
//
//			if(colecaoOrdemServico != null && !colecaoOrdemServico.isEmpty()){
//				ordemServico = new OrdemServico();
//				ordemServico = (OrdemServico) colecaoOrdemServico.iterator().next();
//				sessao.setAttribute("ordemServico", ordemServico);
//			}
//
//			// Recuperar Equipe Atual
//			Date dataRoteiro = Util.converteStringParaDate(manterDadosAtividadesOrdemServicoActionForm.getDataRoteiroForm());
//
//			Collection<Equipe> colecao = fachada.recuperaEquipeDaOSProgramacaoPorDataRoteiro(Integer
//							.valueOf(manterDadosAtividadesOrdemServicoActionForm.getNumeroOSForm()), dataRoteiro);
//
//			if(colecao != null && colecao.size() > 1){
//				throw new ActionServletException("atencao.ordem_servico_programada_varias_equipes");
//			}
//
//			equipeAtual = (Equipe) Util.retonarObjetoDeColecao(colecao);
//			sessao.setAttribute("equipeAtual", equipeAtual);
//
//			// Recuperar dados Deslocamento
//			if(equipeAtual != null){
//
//				this.limparDadosDeslocamento(manterDadosAtividadesOrdemServicoActionForm);
//
//				manterDadosAtividadesOrdemServicoActionForm.setIdEquipe(String.valueOf(equipeAtual.getId()));
//				manterDadosAtividadesOrdemServicoActionForm.setPlacaVeiculo(equipeAtual.getPlacaVeiculo());
//
//				OrdemServicoProgramacao osProgramacao = this.obterOrdemServicoProgramacao(fachada, sessao, dataRoteiro, numeroOS,
//								equipeAtual.getId());
//				sessao.setAttribute("osProgramacao", osProgramacao);
//				sessao.setAttribute("colecaoInterrupcao", null);
//				this.recuperarDadosDeslocamentoCadastrado(sessao, request, manterDadosAtividadesOrdemServicoActionForm, fachada,
//								equipeAtual.getId(), dataRoteiro);
//			}
//
//			// limpar campos dados
//			this.limparDadosRede(manterDadosAtividadesOrdemServicoActionForm);
//
//			// Recuperar dados �gua
//			this.recuperarDadosAgua(manterDadosAtividadesOrdemServicoActionForm, ordemServico);
//
//			// Recuperar dados Esgoto
//			this.recuperarDadosEsgoto(manterDadosAtividadesOrdemServicoActionForm, ordemServico);
//
//		}
//
//		// Obter o servicoTipo da OS
//		FiltroOrdemServico filtroOS = new FiltroOrdemServico();
//		filtroOS.adicionarParametro(new ParametroSimples("id", manterDadosAtividadesOrdemServicoActionForm.getNumeroOSForm()));
//		filtroOS.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.SERVICO_TIPO);
//		filtroOS.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServico.SERVICO_TIPO_REFERENCIA);
//
//		Collection colecaoOrdemServico = fachada.pesquisar(filtroOS, OrdemServico.class.getName());
//
//		if(colecaoOrdemServico != null && !colecaoOrdemServico.isEmpty()){
//			ordemServico = new OrdemServico();
//			ordemServico = (OrdemServico) colecaoOrdemServico.iterator().next();
//			sessao.setAttribute("ordemServico", ordemServico);
//		}
//
//		// ------------------------------------------------------------------------------
//		// VALA PAVIMENTO
//		// -----------------------------------------------------------------------------
//
//		// Verificando se existe alguma OrdemServicoValaPvto cadastrada
//		if(manterDadosAtividadesOrdemServicoActionForm.getNumeroOSForm() != null
//						&& !manterDadosAtividadesOrdemServicoActionForm.getNumeroOSForm().equals("")
//						&& request.getParameter("dataRoteiro") != null && !request.getParameter("dataRoteiro").equals("")){
//			String numeroOsForm = manterDadosAtividadesOrdemServicoActionForm.getNumeroOSForm();
//			Collection<OrdemServicoValaPavimento> colecaoOsValaPavimento = fachada.pesquisarOrdemServicoValaPavimento(Integer
//							.valueOf(numeroOsForm));
//
//			// remover se cole��o existente.
//			sessao.removeAttribute("colecaoVala");
//
//			if(colecaoOsValaPavimento != null && !colecaoOsValaPavimento.isEmpty()){
//				sessao.setAttribute("colecaoVala", colecaoOsValaPavimento);
//			}
//		}
//
//		Collection<OrdemServicoValaPavimento> colecaoVala = (Collection) sessao.getAttribute("colecaoVala");
//
//		if(colecaoVala != null && !colecaoVala.isEmpty()){
//			request.setAttribute("colecaoVala", colecaoVala);
//		}
//
//		// Adicionar Vala
//		String adicionarVala = request.getParameter("adicionarVala");
//		if(isTokenValid(request)){
//			if(adicionarVala != null && !adicionarVala.equals("")){
//				this.adicionarVala(manterDadosAtividadesOrdemServicoActionForm, sessao, request, fachada, ordemServico);
//			}
//		}
//
//		// Remover Vala
//		String posicaoRemoverVala = request.getParameter("posicaoVala");
//
//		if(isTokenValid(request)){
//			if(posicaoRemoverVala != null && !posicaoRemoverVala.equals("")){
//				removerLinhaColecao(posicaoRemoverVala, sessao, request, "colecaoVala");
//			}
//		}
//
//		// -----------------------------------------------------------------------------
//
//		// Verificando se existe alguma cole��o na sess�o que precise ser exibida/ passar para o
//		// request;
//		Collection<OrdemServicoInterrupcaoDeslocamento> colecaoInterrupcao = (Collection) sessao.getAttribute("colecaoInterrupcao");
//
//		if(colecaoInterrupcao != null && !colecaoInterrupcao.isEmpty()){
//			request.setAttribute("colecaoInterrupcao", colecaoInterrupcao);
//		}
//
//		// Setando cole��es necess�rias para exibi��o da tela
//		if(ordemServico != null && ordemServico.getServicoTipo() != null){
//
//			if(ordemServico.getServicoTipo().getIndicadorDeslocamento() != ConstantesSistema.INDICADOR_USO_DESATIVO.intValue()){
//				Collection<MotivoInterrupcao> colecaoMotivoInterrupcao = fachada.pesquisarMotivoInterrupcao();
//				sessao.setAttribute("colecaoMotivoInterrupcao", colecaoMotivoInterrupcao);
//
//			}
//
//			if(ordemServico.getServicoTipo().getIndicadorVala() != ConstantesSistema.INDICADOR_USO_DESATIVO.intValue()){
//				Collection<LocalOcorrencia> colecaoLocalOcorrencia = fachada.pesquisarLocalOcorrencia();
//				sessao.setAttribute("colecaoLocalOcorrencia", colecaoLocalOcorrencia);
//			}
//
//			if(ordemServico.getServicoTipo().getIndicadorRedeRamalAgua() != ConstantesSistema.INDICADOR_USO_DESATIVO.intValue()
//							|| ordemServico.getServicoTipo().getIndicadorRedeRamalEsgoto() != ConstantesSistema.INDICADOR_USO_DESATIVO
//											.intValue()){
//
//				if(ordemServico.getServicoTipo().getIndicadorCausaOcorrencia() != ConstantesSistema.INDICADOR_USO_DESATIVO.intValue()){
//					Collection<CausaVazamento> colecaoCausa = fachada.pesquisarCausaVazamento();
//					sessao.setAttribute("colecaoCausa", colecaoCausa);
//				}
//
//				Collection<AgenteExterno> colecaoAgenteExterno = fachada.pesquisarAgenteExterno();
//				sessao.setAttribute("colecaoAgenteExterno", colecaoAgenteExterno);
//
//			}
//
//		}
//
//		// Pesquisar Atividade
//		if(manterDadosAtividadesOrdemServicoActionForm.getIdAtividade() != null
//						&& !manterDadosAtividadesOrdemServicoActionForm.getIdAtividade().equals("")){
//			if(Util.validarNumeroMaiorQueZERO(manterDadosAtividadesOrdemServicoActionForm.getIdAtividade())){
//				this.pesquisarAtividade(manterDadosAtividadesOrdemServicoActionForm, fachada, request);
//			}
//		}
//
//		// Remover Atividade
//		String removerAtividade = request.getParameter("removerAtividade");
//
//		if(removerAtividade != null && !removerAtividade.equals("")){
//
//			Atividade remover = new Atividade();
//			remover.setId(Integer.valueOf(removerAtividade));
//
//			Collection<Atividade> colecaoAtividade = (Collection) sessao.getAttribute("colecaoAtividade");
//			colecaoAtividade.remove(remover);
//
//			if(sessao.getAttribute("colecaoManterDadosAtividadesOrdemServicoHelper") != null){
//
//				Collection colecaoManterDadosAtividadesOrdemServicoHelper = (Collection) sessao
//								.getAttribute("colecaoManterDadosAtividadesOrdemServicoHelper");
//
//				Iterator iteratorColecaoManterDadosAtividadesOrdemServicoHelper = colecaoManterDadosAtividadesOrdemServicoHelper.iterator();
//
//				ManterDadosAtividadesOrdemServicoHelper helper = null;
//
//				while(iteratorColecaoManterDadosAtividadesOrdemServicoHelper.hasNext()){
//
//					helper = (ManterDadosAtividadesOrdemServicoHelper) iteratorColecaoManterDadosAtividadesOrdemServicoHelper.next();
//
//					if(helper.getOrdemServicoAtividade().getAtividade().getId().equals(remover.getId())){
//
//						colecaoManterDadosAtividadesOrdemServicoHelper.remove(helper);
//						break;
//					}
//				}
//			}
//		}
//
//		// Adicionar Atividade
//		String adicionarAtividade = request.getParameter("adicionarAtividade");
//		if(adicionarAtividade != null && !adicionarAtividade.equals("")){
//
//			Atividade adicionar = this.pesquisarAtividade(manterDadosAtividadesOrdemServicoActionForm, fachada, request);
//			if(adicionar != null){
//
//				Collection<Atividade> colecaoAtividade = (Collection) sessao.getAttribute("colecaoAtividade");
//
//				// [FS0003 - Verificar exist�ncia da atividade]
//				if(colecaoAtividade.contains(adicionar)){
//					throw new ActionServletException("atencao.atividade_ja_existente");
//				}
//
//				colecaoAtividade.add(adicionar);
//
//				if(manterDadosAtividadesOrdemServicoActionForm.getNumeroOSForm() != null
//								&& !manterDadosAtividadesOrdemServicoActionForm.getNumeroOSForm().equals("")){
//
//					Collection<Atividade> atividadesAdicionar = new ArrayList<Atividade>();
//					atividadesAdicionar.add(adicionar);
//
//					this.adicionarOrdemServicoAtividadesNaSessao(sessao, atividadesAdicionar, Integer
//									.valueOf(manterDadosAtividadesOrdemServicoActionForm.getNumeroOSForm()), fachada);
//				}
//				manterDadosAtividadesOrdemServicoActionForm.setIdAtividade("");
//				manterDadosAtividadesOrdemServicoActionForm.setDescricaoAtividade("");
//				request.setAttribute("nomeCampo", "idAtividade");
//			}
//		}
//
//		// Pesquisar Atividade POPUP
//		String pesquisarAtividade = request.getParameter("pesquisarAtividade");
//
//		if(pesquisarAtividade != null && !pesquisarAtividade.equalsIgnoreCase("")){
//			retorno = actionMapping.findForward("pesquisarAtividade");
//		}
//
//		// Recebendo dados Atividade POPUP
//		if(request.getParameter("idCampoEnviarDados") != null){
//			manterDadosAtividadesOrdemServicoActionForm.setIdAtividade(request.getParameter("idCampoEnviarDados"));
//			manterDadosAtividadesOrdemServicoActionForm.setDescricaoAtividade(request.getParameter("descricaoCampoEnviarDados"));
//
//			request.setAttribute("nomeCampo", "botaoAdicionar");
//		}
//
//		// Adicionar Interrupcao
//		String adicionarInterrupcao = request.getParameter("adicionarInterrupcao");
//		if(isTokenValid(request)){
//			if(adicionarInterrupcao != null && !adicionarInterrupcao.equals("")){
//				this.adicionarInterrupcao(manterDadosAtividadesOrdemServicoActionForm, sessao, request);
//			}
//		}
//
//		// Remover Interrupcao
//		String posicaoRemoverInterrupcao = request.getParameter("posicaoInterrupcao");
//		if(isTokenValid(request)){
//			if(posicaoRemoverInterrupcao != null && !posicaoRemoverInterrupcao.equals("")){
//				removerLinhaColecao(posicaoRemoverInterrupcao, sessao, request, "colecaoInterrupcao");
//			}
//		}
//
//		// // Adicionar Vala
//		// String adicionarVala = request.getParameter("adicionarVala");
//		// if (isTokenValid(request)) {
//		// if (adicionarVala != null && !adicionarVala.equals("")) {
//		// this.adicionarVala(manterDadosAtividadesOrdemServicoActionForm, sessao, request, fachada,
//		// ordemServico);
//		// }
//		// }
//		//		
//		// // Remover Vala
//		// String posicaoRemoverVala = request.getParameter("posicaoVala");
//		//		
//		// if (isTokenValid(request)) {
//		// if (posicaoRemoverVala != null && !posicaoRemoverVala.equals("")) {
//		// removerLinhaColecao(posicaoRemoverVala, sessao, request, "colecaoVala");
//		// }
//		// }
//
//		// Concluir Manuten��o
//		String concluirManutencao = request.getParameter("inserir");
//		DadosAtividadesOrdemServicoHelper dadosHelper = new DadosAtividadesOrdemServicoHelper();
//		dadosHelper.setOrdemServico(ordemServico);
//		if(concluirManutencao != null && !concluirManutencao.equalsIgnoreCase("")){
//			if(ordemServico != null && ordemServico.getServicoTipo() != null){
//				// Preencher helper
//				this.montarHelperDadosAtividades(manterDadosAtividadesOrdemServicoActionForm, dadosHelper, ordemServico, sessao, fachada);
//			}
//			if(manterDadosAtividadesOrdemServicoActionForm.getCaminhoRetorno() != null
//							&& !manterDadosAtividadesOrdemServicoActionForm.getCaminhoRetorno().equalsIgnoreCase("")){
//				fachada.inserirDadosAtividadesOrdemServico(dadosHelper);
//				sessao.removeAttribute("dadosHelper");
//				sessao.removeAttribute("colecaoVala");
//				request.setAttribute("caminhoRetorno", manterDadosAtividadesOrdemServicoActionForm.getCaminhoRetorno());
//			}else{
//				fachada.inserirDadosAtividadesOrdemServico(dadosHelper);
//				sessao.setAttribute("colecaoManutencao", dadosHelper);
//			}
//
//			sessao.removeAttribute("ordemServico");
//			request.setAttribute("fecharPopup", "OK");
//		}
//		if(request.getParameter("caminhoRetornoDadosAtividadesOS") != null
//						&& !request.getParameter("caminhoRetornoDadosAtividadesOS").equals("")){
//			sessao.setAttribute("caminhoRetornoDadosAtividadesOS", request.getParameter("caminhoRetornoDadosAtividadesOS"));
//		}
//		saveToken(request);
//		return retorno;
//	}
//
//	private void recuperarDadosEsgoto(ManterDadosAtividadesOrdemServicoActionForm form, OrdemServico ordemServico){
//
//		form.setProfundidadeEsgoto("");
//		form.setPressaoEsgoto("");
//
//		if(ordemServico != null && ordemServico.getServicoTipo() != null
//						&& ordemServico.getServicoTipo().getIndicadorRedeRamalEsgoto() != 2){
//
//			if(ordemServico.getNumeroProfundidade() != null && !ordemServico.getNumeroProfundidade().equals("")){
//				form.setProfundidadeEsgoto(String.valueOf(ordemServico.getNumeroProfundidade()));
//			}
//
//			if(ordemServico.getNumeroPressao() != null && !ordemServico.getNumeroPressao().equals("")){
//				form.setPressaoEsgoto(String.valueOf(ordemServico.getNumeroPressao()));
//			}
//
//			if(ordemServico.getCausaVazamento() != null){
//				form.setIdCausaEsgoto(String.valueOf(ordemServico.getCausaVazamento().getId()));
//			}
//
//			// Recuperar id rede ou ramal e Di�metro
//			if(ordemServico.getDiametroRamalEsgoto() != null){
//				form.setIdRedeRamalEsgoto(ConstantesSistema.INDICADOR_RAMAL);
//				form.setIdDiametroEsgoto(String.valueOf(ordemServico.getDiametroRamalEsgoto().getId()));
//			}else if(ordemServico.getDiametroRedeEsgoto() != null){
//				form.setIdDiametroEsgoto(String.valueOf(ordemServico.getDiametroRedeEsgoto().getId()));
//				form.setIdRedeRamalEsgoto(ConstantesSistema.INDICADOR_REDE);
//			}
//
//			// Recuperar dados material
//			if(ordemServico.getMaterialRamalEsgoto() != null){
//				form.setIdMaterialEsgoto(String.valueOf(ordemServico.getMaterialRamalEsgoto().getId()));
//			}else if(ordemServico.getMaterialRedeEsgoto() != null){
//				form.setIdMaterialEsgoto(String.valueOf(ordemServico.getMaterialRedeEsgoto().getId()));
//			}
//
//			// Recuperar Agente
//			if(ordemServico.getAgenteExterno() != null){
//				form.setIdAgenteExternoEsgoto(String.valueOf(ordemServico.getAgenteExterno().getId()));
//			}
//
//		}
//
//	}
//
//	private void recuperarDadosAgua(ManterDadosAtividadesOrdemServicoActionForm form, OrdemServico ordemServico){
//
//		form.setProfundidadeAgua("");
//		form.setPressaoAgua("");
//
//		if(ordemServico != null && ordemServico.getServicoTipo() != null && ordemServico.getServicoTipo().getIndicadorRedeRamalAgua() != 2){
//
//			if(ordemServico.getNumeroProfundidade() != null && !ordemServico.getNumeroProfundidade().equals("")){
//				form.setProfundidadeAgua(String.valueOf(ordemServico.getNumeroProfundidade()));
//			}
//
//			if(ordemServico.getNumeroPressao() != null && !ordemServico.getNumeroPressao().equals("")){
//				form.setPressaoAgua(String.valueOf(ordemServico.getNumeroPressao()));
//			}
//
//			if(ordemServico.getCausaVazamento() != null){
//				form.setIdCausaAgua(String.valueOf(ordemServico.getCausaVazamento().getId()));
//			}
//
//			// Recuperar id rede ou ramal e Di�metro
//			if(ordemServico.getDiametroRamalAgua() != null){
//				form.setIdRedeRamalAgua(ConstantesSistema.INDICADOR_RAMAL);
//				form.setIdDiametroAgua(String.valueOf(ordemServico.getDiametroRamalAgua().getId()));
//			}else if(ordemServico.getDiametroRedeAgua() != null){
//				form.setIdDiametroAgua(String.valueOf(ordemServico.getDiametroRedeAgua().getId()));
//				form.setIdRedeRamalAgua(ConstantesSistema.INDICADOR_REDE);
//			}else if(ordemServico.getDiametroCavaleteAgua() != null){
//				form.setIdDiametroAgua(String.valueOf(ordemServico.getDiametroCavaleteAgua().getId()));
//				form.setIdRedeRamalAgua(ConstantesSistema.INDICADOR_CAVALETE);
//			}
//
//			// Recuperar dados material
//			if(ordemServico.getMaterialRamalAgua() != null){
//				form.setIdMaterialAgua(String.valueOf(ordemServico.getMaterialRamalAgua().getId()));
//			}else if(ordemServico.getMaterialRedeAgua() != null){
//				form.setIdMaterialAgua(String.valueOf(ordemServico.getMaterialRedeAgua().getId()));
//			}else if(ordemServico.getMaterialCavaleteAgua() != null){
//				form.setIdMaterialAgua(String.valueOf(ordemServico.getMaterialCavaleteAgua().getId()));
//			}
//
//			// Recuperar Agente
//			if(ordemServico.getAgenteExterno() != null){
//				form.setIdAgenteExternoAgua(String.valueOf(ordemServico.getAgenteExterno().getId()));
//			}
//
//		}
//	}
//
//	/**
//	 * Recuperar dados do deslocamento e das interrup��es do deslocamento.
//	 * 
//	 * @param sessao
//	 * @param form
//	 * @param fachada
//	 * @param idEquipeAtual
//	 * @param dataRoteiro
//	 * @param idOS
//	 */
//	private void recuperarDadosDeslocamentoCadastrado(HttpSession sessao, HttpServletRequest httpServletRequest,
//					ManterDadosAtividadesOrdemServicoActionForm form, Fachada fachada, Integer idEquipeAtual, Date dataRoteiro){
//
//		OrdemServicoProgramacao osProgramacao = null;
//		OrdemServicoDeslocamento osDeslocamento = null;
//		Collection<OrdemServicoInterrupcaoDeslocamento> colecaoInterrupcao = null;
//
//		// Recuperando colecao da sessao
//		Collection<OrdemServicoInterrupcaoDeslocamento> colecaoInterrupcaoSessao = (Collection) sessao.getAttribute("colecaoInterrupcao");
//
//		// Verificando se existe alguma informacao de deslocamento cadastrada para exibir
//		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
//		Integer idUnidadeLotacao = usuario.getUnidadeOrganizacional().getId();
//
//		if(form.getDataRoteiroForm() != null && !form.getDataRoteiroForm().equals("")){
//
//			// Consulta programacaoRoteiro
//			ProgramacaoRoteiro programacaoRoteiro = this.consultarProgramacaoRoteiro(dataRoteiro, idUnidadeLotacao);
//
//			// Buscar a OrdemServicoProgramacao
//			osProgramacao = fachada.pesquisarOrdemServicoProgramacao(programacaoRoteiro.getId(), Integer.valueOf(form.getNumeroOSForm()),
//							idEquipeAtual);
//
//			// Pesquisar OS Deslocamento e Interrup��es
//			if(osProgramacao != null){
//				osDeslocamento = fachada.pesquisarOrdemServicoDeslocamento(osProgramacao.getId());
//				colecaoInterrupcao = fachada.pesquisarOSInterrupcaoDeslocamento(osProgramacao.getId());
//			}
//
//			if(osDeslocamento != null){
//				form.setKmInicial(osDeslocamento.getNumeroKmInicio().toString());
//				form.setKmFinal(osDeslocamento.getNumeroKmFim().toString());
//				form.setDataInicioDeslocamento(Util.formatarData(osDeslocamento.getDeslocamentoInicio()));
//				form.setHoraInicioDeslocamento(Util.formatarHoraSemSegundos(osDeslocamento.getDeslocamentoInicio()));
//				form.setDataFimDeslocamento(Util.formatarData(osDeslocamento.getDeslocamentoFim()));
//				form.setHoraFimDeslocamento(Util.formatarHoraSemSegundos(osDeslocamento.getDeslocamentoFim()));
//			}
//
//			// Pesquisar Interrup��es
//			if(colecaoInterrupcao != null && !colecaoInterrupcao.isEmpty()){
//				if(colecaoInterrupcaoSessao != null && !colecaoInterrupcaoSessao.isEmpty()){
//					colecaoInterrupcaoSessao.addAll(colecaoInterrupcao);
//				}else{
//					sessao.setAttribute("colecaoInterrupcao", colecaoInterrupcao);
//				}
//			}
//			httpServletRequest.setAttribute("colecaoInterrupcao", colecaoInterrupcao);
//
//		}else{
//			throw new ActionServletException("atencao.erro_ao_recuperar_programacao_roteiro");
//		}
//
//	}
//
//	public OrdemServicoProgramacao obterOrdemServicoProgramacao(Fachada fachada, HttpSession sessao, Date dataRoteiro, Integer numeroOS,
//					Integer idEquipeAtual){
//
//		OrdemServicoProgramacao osProgramacao = null;
//
//		// Verificando se existe alguma informacao de deslocamento cadastrada para exibir
//		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
//		Integer idUnidadeLotacao = usuario.getUnidadeOrganizacional().getId();
//
//		if(dataRoteiro != null){
//			// Buscar a OrdemServicoProgramacao
//			osProgramacao = fachada.pesquisarOrdemServicoProgramacao(idUnidadeLotacao, numeroOS, idEquipeAtual, dataRoteiro);
//		}
//
//		return osProgramacao;
//	}
//
//	/**
//	 * M�todo respons�vel por recuperar os dados do form e montar o helper.
//	 * 
//	 * @param form
//	 * @param dadosHelper
//	 * @param ordemServico
//	 */
//	private void montarHelperDadosAtividades(ManterDadosAtividadesOrdemServicoActionForm form,
//					DadosAtividadesOrdemServicoHelper dadosHelper, OrdemServico ordemServico, HttpSession sessao, Fachada fachada){
//
//		if(ordemServico.getServicoTipo().getIndicadorDeslocamento() != ConstantesSistema.INDICADOR_USO_DESATIVO.intValue()){
//
//			// Campos
//			String placaVeiculo = "";
//
//			String dataInicioDeslocamento = form.getDataInicioDeslocamento();
//			String dataFimDeslocamento = form.getDataFimDeslocamento();
//			String horaInicioDeslocamento = form.getHoraInicioDeslocamento();
//			String horaFimDeslocamento = form.getHoraFimDeslocamento();
//
//			String kmInicial = form.getKmInicial();
//			String kmFinal = form.getKmFinal();
//
//			Date ultimaAlteracao = new Date();
//			OrdemServicoProgramacao osProgramacao = new OrdemServicoProgramacao();
//			boolean inserirDeslocamento = true;
//			if((!dataInicioDeslocamento.equals("") && !dataFimDeslocamento.equals(""))
//							|| (ordemServico.getServicoTipo().getIndicadorDeslocamento() == ConstantesSistema.INDICADOR_USO_ATIVO
//											.intValue())){
//				if(Util.validarDiaMesAno(dataInicioDeslocamento) || Util.validarDiaMesAno(dataFimDeslocamento)){
//					throw new ActionServletException("atencao.verificar_datas_deslocamento");
//				}
//				Util.validarIntervaloDatas(dataInicioDeslocamento, dataFimDeslocamento);
//			}else if(ordemServico.getServicoTipo().getIndicadorDeslocamento() == 3){ // campos
//				// opcionais
//				// se informar 1, precisa informar tudo
//				if((!dataInicioDeslocamento.equals("") || !dataFimDeslocamento.equals("") || !horaInicioDeslocamento.equals("")
//								|| !horaFimDeslocamento.equals("") || !kmInicial.equals("") || !kmFinal.equals(""))
//								&& (dataInicioDeslocamento.equals("") || dataFimDeslocamento.equals("")
//												|| horaInicioDeslocamento.equals("") || horaFimDeslocamento.equals("")
//												|| kmInicial.equals("") || kmFinal.equals(""))){
//					throw new ActionServletException("atencao.demais_campos_necessarios_deslocamento");
//				}else{
//					if(dataInicioDeslocamento.equals("") || dataFimDeslocamento.equals("") || horaInicioDeslocamento.equals("")
//									|| horaFimDeslocamento.equals("") || kmInicial.equals("") || kmFinal.equals("")){
//						inserirDeslocamento = false;
//					}
//				}
//			}
//
//			String dataHoraInicioDeslocamento = dataInicioDeslocamento + " " + horaInicioDeslocamento;
//			String dataHoraFimDeslocamento = dataFimDeslocamento + " " + horaFimDeslocamento;
//
//			// Precisa pegar a unidade do usuario do login que esta na sessao
//			Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
//			Integer idUnidadeLotacao = usuario.getUnidadeOrganizacional().getId();
//
//			if(form.getDataRoteiroForm() != null && !form.getDataRoteiroForm().equals("")){
//
//				// Consulta programacaoRoteiro
//				Date dataRoteiro = Util.converteStringParaDate(form.getDataRoteiroForm(), "dd/MM/yyyy");
//				ProgramacaoRoteiro programacaoRoteiro = this.consultarProgramacaoRoteiro(dataRoteiro, idUnidadeLotacao);
//
//				// Buscar a OrdemServicoProgramacao
//				if(form.getIdEquipe() != null && !form.getIdEquipe().equals("") && programacaoRoteiro != null){
//					placaVeiculo = form.getPlacaVeiculo();
//					if(placaVeiculo == null || placaVeiculo.equals("")){
//						throw new ActionServletException("atencao.inserir_equipe_placa_veiculo_invalida");
//					}
//					osProgramacao = fachada.pesquisarOrdemServicoProgramacao(programacaoRoteiro.getId(), ordemServico.getId(), Integer
//									.valueOf(form.getIdEquipe()));
//				}
//
//			}else{
//				throw new ActionServletException("atencao.erro_ao_recuperar_programacao_roteiro");
//			}
//
//			// Recuperando dados do deslocamento
//			OrdemServicoDeslocamento osDeslocamento = new OrdemServicoDeslocamento();
//
//			if(ordemServico.getServicoTipo().getIndicadorDeslocamento() == ConstantesSistema.INDICADOR_USO_ATIVO.intValue()){
//				if(StringUtils.isEmpty(kmInicial) || StringUtils.isEmpty(kmFinal) || StringUtils.isEmpty(dataHoraInicioDeslocamento)
//								|| StringUtils.isEmpty(dataHoraFimDeslocamento)){
//					throw new ActionServletException("atencao.campos_obrigatorios_nao_informados_deslocamento");
//				}
//			}
//
//			osDeslocamento.setOrdemServicoProgramacao(osProgramacao);
//			osDeslocamento.setPlacaVeiculo(placaVeiculo);
//			osDeslocamento.setDeslocamentoInicio(!dataHoraInicioDeslocamento.equals("") ? Util.converteStringParaDate(
//							dataHoraInicioDeslocamento, "dd/MM/yyyy HH:mm") : null);
//			osDeslocamento.setDeslocamentoFim(!dataHoraFimDeslocamento.equals("") ? Util.converteStringParaDate(dataHoraFimDeslocamento,
//							"dd/MM/yyyy HH:mm") : null);
//			osDeslocamento.setUltimaAlteracao(ultimaAlteracao);
//			osDeslocamento.setNumeroKmInicio(!kmInicial.equals("") ? Util.formatarMoedaRealparaBigDecimal(kmInicial, 1) : null);
//			osDeslocamento.setNumeroKmFim(!kmFinal.equals("") ? Util.formatarMoedaRealparaBigDecimal(kmFinal, 1) : null);
//
//			// Setar no novo Helper
//			if(ordemServico.getServicoTipo().getIndicadorDeslocamento() == ConstantesSistema.INDICADOR_USO_ATIVO.intValue()
//							|| inserirDeslocamento) dadosHelper.setOrdemServicoDeslocamento(osDeslocamento);
//
//			// Verifica se existem interrupcoes informadas
//			Collection<OrdemServicoInterrupcaoDeslocamento> osInterrupcaoDeslocamento = (Collection) sessao
//							.getAttribute("colecaoInterrupcao");
//
//			if(osInterrupcaoDeslocamento != null && !osInterrupcaoDeslocamento.isEmpty()){
//				dadosHelper.setColecaoOrdemServicoInterrupcaoDeslocamento(osInterrupcaoDeslocamento);
//			}
//
//		}
//
//		if(ordemServico.getServicoTipo().getIndicadorRedeRamalAgua() != ConstantesSistema.INDICADOR_USO_DESATIVO.intValue()){
//
//			String idRedeRamal = form.getIdRedeRamalAgua();
//
//			String idDiametroAgua = form.getIdDiametroAgua();
//			String idMaterialAgua = form.getIdMaterialAgua();
//			String idAgenteExternoAgua = form.getIdAgenteExternoAgua();
//			String profundidade = form.getProfundidadeAgua();
//			String pressao = form.getPressaoAgua();
//
//			if(ordemServico.getServicoTipo().getIndicadorRedeRamalAgua() == ConstantesSistema.INDICADOR_USO_ATIVO.intValue()){
//				if(idRedeRamal.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))
//								|| idDiametroAgua.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))
//								|| idMaterialAgua.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))
//								|| idAgenteExternoAgua.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
//
//					throw new ActionServletException("atencao.dados_obrigatorios_nao_informados_redeRamalAgua");
//				}
//			}
//			dadosHelper.setIdRedeRamalAgua(idRedeRamal);
//
//			if(idRedeRamal.equals(ConstantesSistema.INDICADOR_REDE)){
//
//				if(idDiametroAgua != null && !idDiametroAgua.equals("")){
//					DiametroRedeAgua diametroRedeAgua = new DiametroRedeAgua();
//					diametroRedeAgua.setId(Integer.valueOf(idDiametroAgua));
//					dadosHelper.setDiametroRedeAgua(diametroRedeAgua);
//				}
//
//				if(idMaterialAgua != null && !idMaterialAgua.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
//					MaterialRedeAgua materialRedeAgua = new MaterialRedeAgua();
//					materialRedeAgua.setId(Integer.valueOf(idMaterialAgua));
//					dadosHelper.setMaterialRedeAgua(materialRedeAgua);
//				}
//
//			}else if(idRedeRamal.equals(ConstantesSistema.INDICADOR_RAMAL)){
//
//				if(idDiametroAgua != null && !idDiametroAgua.equals("")){
//					DiametroRamalAgua diametroRamalAgua = new DiametroRamalAgua();
//					diametroRamalAgua.setId(Integer.valueOf(idDiametroAgua));
//					dadosHelper.setDiametroRamalAgua(diametroRamalAgua);
//				}
//
//				if(idMaterialAgua != null && !idMaterialAgua.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
//					MaterialRamalAgua materialRamalAgua = new MaterialRamalAgua();
//					materialRamalAgua.setId(Integer.valueOf(idMaterialAgua));
//					dadosHelper.setMaterialRamalAgua(materialRamalAgua);
//				}
//			}else if(idRedeRamal.equals(ConstantesSistema.INDICADOR_CAVALETE)){
//
//				if(idDiametroAgua != null && !idDiametroAgua.equals("")){
//					DiametroCavaleteAgua diametroCavaleteAgua = new DiametroCavaleteAgua();
//					diametroCavaleteAgua.setId(Integer.valueOf(idDiametroAgua));
//					dadosHelper.setDiametroCavaleteAgua(diametroCavaleteAgua);
//				}
//
//				if(idMaterialAgua != null && !idMaterialAgua.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
//					MaterialCavaleteAgua materialCavaleteAgua = new MaterialCavaleteAgua();
//					materialCavaleteAgua.setId(Integer.valueOf(idMaterialAgua));
//					dadosHelper.setMaterialCavaleteAgua(materialCavaleteAgua);
//				}
//			}
//
//			// Para esses campos nao importa se � rede ou ramal
//			if(idAgenteExternoAgua != null && !idAgenteExternoAgua.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
//				AgenteExterno agenteExterno = new AgenteExterno();
//				agenteExterno.setId(Integer.valueOf(idAgenteExternoAgua));
//				dadosHelper.setAgenteExternoAgua(agenteExterno);
//			}
//
//			if(profundidade != null && !profundidade.equals("")){
//				dadosHelper.setProfundidadeAgua(Util.formatarMoedaRealparaBigDecimal(profundidade, 2));
//			}
//
//			if(pressao != null && !pressao.equals("")){
//				dadosHelper.setPressaoAgua(Util.formatarMoedaRealparaBigDecimal(pressao, 4));
//			}
//
//			if(ordemServico.getServicoTipo().getIndicadorCausaOcorrencia() != ConstantesSistema.INDICADOR_USO_DESATIVO.intValue()){
//
//				String idCausa = form.getIdCausaAgua();
//				if(ordemServico.getServicoTipo().getIndicadorCausaOcorrencia() == ConstantesSistema.INDICADOR_USO_ATIVO.intValue()){
//					if(idCausa == null || idCausa.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
//						throw new ActionServletException("atencao.dados_obrigatorios_nao_informados_redeRamalAgua");
//					}else{
//						CausaVazamento causaVazamento = new CausaVazamento();
//						causaVazamento.setId(Integer.valueOf(idCausa));
//						dadosHelper.setCausaVazamentoAgua(causaVazamento);
//					}
//				}else{
//					if(idCausa != null && !idCausa.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
//						CausaVazamento causaVazamento = new CausaVazamento();
//						causaVazamento.setId(Integer.valueOf(idCausa));
//						dadosHelper.setCausaVazamentoAgua(causaVazamento);
//					}
//				}
//			}
//
//		}else if(ordemServico.getServicoTipo().getIndicadorRedeRamalEsgoto() != ConstantesSistema.INDICADOR_USO_DESATIVO.intValue()){
//
//			String idRedeRamal = form.getIdRedeRamalEsgoto();
//
//			String idDiametroEsgoto = form.getIdDiametroEsgoto();
//			String idMaterialEsgoto = form.getIdMaterialEsgoto();
//			String idAgenteExternoEsgoto = form.getIdAgenteExternoEsgoto();
//			String profundidade = form.getProfundidadeEsgoto();
//			String pressao = form.getPressaoEsgoto();
//
//			if(ordemServico.getServicoTipo().getIndicadorRedeRamalEsgoto() == ConstantesSistema.INDICADOR_USO_ATIVO.intValue()){
//				if(idRedeRamal.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))
//								|| idDiametroEsgoto.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))
//								|| idMaterialEsgoto.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))
//								|| idAgenteExternoEsgoto.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
//
//					throw new ActionServletException("atencao.dados_obrigatorios_nao_informados_redeRamalEsgoto");
//				}
//			}
//			dadosHelper.setIdRedeRamalEsgoto(idRedeRamal);
//
//			if(idRedeRamal.equals(ConstantesSistema.INDICADOR_REDE)){
//
//				if(idDiametroEsgoto != null && !idDiametroEsgoto.equals("")){
//					DiametroRedeEsgoto diametroRedeEsgoto = new DiametroRedeEsgoto();
//					diametroRedeEsgoto.setId(Integer.valueOf(idDiametroEsgoto));
//					dadosHelper.setDiametroRedeEsgoto(diametroRedeEsgoto);
//				}
//
//				if(idMaterialEsgoto != null && !idMaterialEsgoto.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
//					MaterialRedeEsgoto materialRedeEsgoto = new MaterialRedeEsgoto();
//					materialRedeEsgoto.setId(Integer.valueOf(idMaterialEsgoto));
//					dadosHelper.setMaterialRedeEsgoto(materialRedeEsgoto);
//				}
//
//			}else{
//
//				if(idDiametroEsgoto != null && !idDiametroEsgoto.equals("")){
//					DiametroRamalEsgoto diametroRamalEsgoto = new DiametroRamalEsgoto();
//					diametroRamalEsgoto.setId(Integer.valueOf(idDiametroEsgoto));
//					dadosHelper.setDiametroRamalEsgoto(diametroRamalEsgoto);
//				}
//
//				if(idMaterialEsgoto != null && !idMaterialEsgoto.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
//					MaterialRamalEsgoto materialRamalEsgoto = new MaterialRamalEsgoto();
//					materialRamalEsgoto.setId(Integer.valueOf(idMaterialEsgoto));
//					dadosHelper.setMaterialRamalEsgoto(materialRamalEsgoto);
//				}
//
//			}
//
//			// Para esses campos n�o importa se � rede ou ramal
//			if(!idAgenteExternoEsgoto.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
//				AgenteExterno agenteExterno = new AgenteExterno();
//				agenteExterno.setId(Integer.valueOf(idAgenteExternoEsgoto));
//				dadosHelper.setAgenteExternoEsgoto(agenteExterno);
//			}
//
//			if(profundidade != null && !profundidade.equals("")){
//				dadosHelper.setProfundidadeEsgoto(Util.formatarMoedaRealparaBigDecimal(profundidade));
//			}
//
//			if(pressao != null && !pressao.equals("")){
//				dadosHelper.setPressaoEsgoto(new BigDecimal(pressao));
//			}
//
//			if(ordemServico.getServicoTipo().getIndicadorCausaOcorrencia() != ConstantesSistema.INDICADOR_USO_DESATIVO.intValue()){
//
//				String idCausa = form.getIdCausaEsgoto();
//				if((ordemServico.getServicoTipo().getIndicadorCausaOcorrencia() == ConstantesSistema.INDICADOR_USO_ATIVO.intValue())){
//					if(idCausa.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
//						throw new ActionServletException("atencao.dados_obrigatorios_nao_informados_redeRamalEsgoto");
//					}else{
//						CausaVazamento causaVazamento = new CausaVazamento();
//						causaVazamento.setId(Integer.valueOf(idCausa));
//						dadosHelper.setCausaVazamentoEsgoto(causaVazamento);
//					}
//				}else{
//					if(!idCausa.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){
//						CausaVazamento causaVazamento = new CausaVazamento();
//						causaVazamento.setId(Integer.valueOf(idCausa));
//						dadosHelper.setCausaVazamentoEsgoto(causaVazamento);
//					}
//				}
//			}
//		}
//
//		// Setando cole��o de vala
//		if(ordemServico.getServicoTipo().getIndicadorVala() != ConstantesSistema.INDICADOR_USO_DESATIVO.intValue()){
//
//			Collection<OrdemServicoValaPavimento> colecaoVala = (Collection) sessao.getAttribute("colecaoVala");
//			if(colecaoVala != null && !colecaoVala.isEmpty()){
//				dadosHelper.setColecaoOrdemServicoValaPavimento(colecaoVala);
//			}
//		}
//
//		// Verificando se exite alguma interrupcao de execucao na sess�o - Essas interrup��es s�o
//		// informadas
//		// no popup de hor�rios por�m elas n�o s�o especificas para a atividade clicada.
//		Collection<OrdemServicoInterrupcaoExecucao> colecaoInterrupcaoExecucao = (Collection) sessao
//						.getAttribute("colecaoInterrupcaoExecucao");
//
//		if(colecaoInterrupcaoExecucao != null && !colecaoInterrupcaoExecucao.isEmpty()){
//			dadosHelper.setColecaoOrdemServicoInterrupcaoExecucao(colecaoInterrupcaoExecucao);
//		}
//
//		// Pegando o helper que ja existia e setando no novo.
//		Collection colecaoManterDadosAtividadesOrdemServicoHelper = (Collection) sessao
//						.getAttribute("colecaoManterDadosAtividadesOrdemServicoHelper");
//
//		dadosHelper.setColecaoManterDadosAtividadesOrdemServicoHelper(colecaoManterDadosAtividadesOrdemServicoHelper);
//
//		OrdemServicoProgramacao osProgramacaoSessao = (OrdemServicoProgramacao) sessao.getAttribute("osProgramacao");
//		dadosHelper.setOrdemServicoProgramacao(osProgramacaoSessao);
//	}
//
//	private Atividade pesquisarAtividade(ManterDadosAtividadesOrdemServicoActionForm form, Fachada fachada,
//					HttpServletRequest httpServletRequest){
//
//		Atividade retorno = null;
//
//		FiltroAtividade filtroAtividade = new FiltroAtividade();
//		filtroAtividade.adicionarParametro(new ParametroSimples(FiltroAtividade.ID, form.getIdAtividade()));
//		filtroAtividade.adicionarParametro(new ParametroSimples(FiltroAtividade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
//
//		Collection colecaoAtividade = fachada.pesquisar(filtroAtividade, Atividade.class.getName());
//
//		if(colecaoAtividade == null || colecaoAtividade.isEmpty()){
//
//			form.setIdAtividade("");
//			form.setDescricaoAtividade("Atividade inexistente");
//
//			httpServletRequest.setAttribute("corAtividade", "exception");
//			httpServletRequest.setAttribute("nomeCampo", "idAtividade");
//
//		}else{
//
//			Atividade atividade = (Atividade) Util.retonarObjetoDeColecao(colecaoAtividade);
//
//			form.setIdAtividade(atividade.getId().toString());
//			form.setDescricaoAtividade(atividade.getDescricao());
//
//			httpServletRequest.setAttribute("nomeCampo", "idAtividade");
//			retorno = atividade;
//		}
//
//		return retorno;
//	}
//
//	/**
//	 * Adiciona uma linha na cole��o de interrup��es.
//	 * 
//	 * @param form
//	 * @param sessao
//	 * @param httpServletRequest
//	 */
//	private void adicionarInterrupcao(ManterDadosAtividadesOrdemServicoActionForm form, HttpSession sessao,
//					HttpServletRequest httpServletRequest){
//
//		Collection<OrdemServicoInterrupcaoDeslocamento> colecaoInterrupcao = (Collection) sessao.getAttribute("colecaoInterrupcao");
//
//		// Recuperando algumas informa��es do deslocamento
//		String dataInicioDeslocamento = form.getDataInicioDeslocamento();
//		String horaInicioDeslocamento = form.getHoraInicioDeslocamento();
//		String dataFimDeslocamento = form.getDataFimDeslocamento();
//		String horaFimDeslocamento = form.getHoraFimDeslocamento();
//
//		// Recuperando informa��es de interrup��o
//		String dataInicioInterrupcao = form.getDataInicioInterrupcao();
//		String horaInicioInterrupcao = form.getHoraInicioInterrupcao();
//		String dataFimInterrupcao = form.getDataFimInterrupcao();
//		String horaFimInterrupcao = form.getHoraFimInterrupcao();
//		String kmInterrupcao = form.getKmInterrupcao();
//		String idMotivoInterrupcao = form.getIdMotivoInterrupcao();
//
//		OrdemServicoInterrupcaoDeslocamento interrupcao = new OrdemServicoInterrupcaoDeslocamento();
//
//		// Valida��o datas
//		if(Util.validarDiaMesAno(dataInicioInterrupcao) || Util.validarDiaMesAno(dataFimInterrupcao)){
//			throw new ActionServletException("atencao.verificar_datas_interrupcao");
//		}
//
//		// Validando se Km interrup��o est� dentro do intervalo informado.
//		BigDecimal kmInicialDeslocamento = Util.formatarMoedaRealparaBigDecimal(form.getKmInicial(), 1);
//		BigDecimal kmFinalDeslocamento = Util.formatarMoedaRealparaBigDecimal(form.getKmFinal(), 1);
//		BigDecimal kmInterrupcaoDeslocamento = Util.formatarMoedaRealparaBigDecimal(kmInterrupcao, 1);
//
//		if(kmInterrupcaoDeslocamento.compareTo(kmInicialDeslocamento) == -1
//						|| kmInterrupcaoDeslocamento.compareTo(kmFinalDeslocamento) == 1){
//			throw new ActionServletException("atencao.km_interrupcao_fora_do_intervalo");
//		}
//
//		// Datas do deslocamento
//		String strDataHoraInicioDeslocamento = dataInicioDeslocamento + " " + horaInicioDeslocamento;
//		String strDataHoraFimDeslocamento = dataFimDeslocamento + " " + horaFimDeslocamento;
//
//		// Datas da interrup��o
//		String strDataHoraInicioInterrupcao = dataInicioInterrupcao + " " + horaInicioInterrupcao;
//		String strDataHoraFimInterrupcao = dataFimInterrupcao + " " + horaFimInterrupcao;
//
//		// Preparando datas para valida��es
//		Date dataHoraInicioDeslocamento = Util.converteStringParaDate(strDataHoraInicioDeslocamento, "dd/MM/yyyy HH:mm");
//		Date dataHoraFimDeslocamento = Util.converteStringParaDate(strDataHoraFimDeslocamento, "dd/MM/yyyy HH:mm");
//		Date dataHoraInicioInterrupcao = Util.converteStringParaDate(strDataHoraInicioInterrupcao, "dd/MM/yyyy HH:mm");
//		Date dataHoraFimInterrupcao = Util.converteStringParaDate(strDataHoraFimInterrupcao, "dd/MM/yyyy HH:mm");
//
//		if(dataHoraInicioDeslocamento == null || dataHoraFimDeslocamento == null || dataHoraInicioInterrupcao == null
//						|| dataHoraFimInterrupcao == null){
//
//			throw new ActionServletException("atencao.verificar_datas_deslocamento");
//
//		}
//
//		// Verificando se data inicial � anterior a data final.
//		if(dataHoraInicioInterrupcao.compareTo(dataHoraFimInterrupcao) > 0){
//			throw new ActionServletException("atencao.verificar_data_inicial_final_interrupcao");
//		}
//
//		// Validando se o intervalo de interrup��o est� dentro do intervalo do deslocamento.
//		if(dataHoraInicioInterrupcao.compareTo(dataHoraInicioDeslocamento) < 0
//						|| dataHoraFimInterrupcao.compareTo(dataHoraFimDeslocamento) > 0){
//			throw new ActionServletException("atencao.data_interrupcao_fora_intervalo");
//		}
//
//		interrupcao.setInterrupcaoInicio(Util.converteStringParaDate(strDataHoraInicioInterrupcao, "dd/MM/yyyy HH:mm"));
//		interrupcao.setInterrupcaoFim(Util.converteStringParaDate(strDataHoraFimInterrupcao, "dd/MM/yyyy HH:mm"));
//		interrupcao.setNumeroKm(kmInterrupcaoDeslocamento);
//		interrupcao.setUltimaAlteracao(new Date());
//
//		// Recuperar o motivoInterrupcao da cole��o que est� na sess�o.
//		Collection<MotivoInterrupcao> colecaoMotivoInterrupcao = (Collection) sessao.getAttribute("colecaoMotivoInterrupcao");
//		for(MotivoInterrupcao motivoInterrupcao : colecaoMotivoInterrupcao){
//			if(motivoInterrupcao.getId().equals(Integer.valueOf(idMotivoInterrupcao))){
//				interrupcao.setMotivoInterrupcao(motivoInterrupcao);
//			}
//		}
//
//		if(colecaoInterrupcao != null && !colecaoInterrupcao.isEmpty()){
//			colecaoInterrupcao.add(interrupcao);
//		}else{
//			// primeiro elemento
//			colecaoInterrupcao = new ArrayList<OrdemServicoInterrupcaoDeslocamento>();
//			colecaoInterrupcao.add(interrupcao);
//			sessao.setAttribute("colecaoInterrupcao", colecaoInterrupcao);
//		}
//		httpServletRequest.setAttribute("colecaoInterrupcao", colecaoInterrupcao);
//		this.limparDadosInterrupcao(form);
//
//	}
//
//	/**
//	 * Adiciona uma linha na cole��o de vala.
//	 * 
//	 * @param form
//	 * @param sessao
//	 * @param httpServletRequest
//	 */
//	private void adicionarVala(ManterDadosAtividadesOrdemServicoActionForm form, HttpSession sessao, HttpServletRequest httpServletRequest,
//					Fachada fachada, OrdemServico ordemServico){
//
//		// Collection<OrdemServicoValaPavimento> colecaoVala = (Collection)
//		// sessao.getAttribute("colecaoVala");
//		Collection<OrdemServicoValaPavimento> colecaoVala = (Collection) httpServletRequest.getAttribute("colecaoVala");
//
//		// Recuperando informa��es da vala
//		String numeroVala = form.getNumeroVala();
//		String idLocalOcorrencia = form.getIdLocalOcorrencia();
//		String idPavimento = form.getIdPavimento();
//		String comprimentoVala = form.getComprimentoVala();
//		String larguraVala = form.getLarguraVala();
//		String profundidadeVala = form.getProfundidadeVala();
//		String indicadorValaAterrada = form.getIndicadorValaAterrada();
//		String indicadorEntulho = form.getIndicadorEntulho();
//
//		OrdemServicoValaPavimento vala = new OrdemServicoValaPavimento();
//
//		// Verifica se numero da vala � maior que zero
//		if(Util.validarNumeroMaiorQueZERO(numeroVala)){
//			vala.setNumeroVala(Integer.valueOf(numeroVala));
//		}else{
//			throw new ActionServletException("atencao.campo.invalido", null, "N�mero Vala");
//		}
//
//		// Varrer a cole��o para verificar se o n�mero informado j� existe.
//		if(colecaoVala != null && !colecaoVala.isEmpty()){
//			for(OrdemServicoValaPavimento ordemServicoValaPavimento : colecaoVala){
//				if(ordemServicoValaPavimento.getNumeroVala().equals(Integer.valueOf(numeroVala))){
//					throw new ActionServletException("atencao.numero_vala_ja_existe");
//				}
//			}
//		}
//
//		// Buscar o obj LocalOcorrencia para verificar qual o tipo do pavimento
//		LocalOcorrencia localOcorrencia = fachada.pesquisarLocalOcorrencia(Integer.valueOf(idLocalOcorrencia));
//
//		if(localOcorrencia != null){
//
//			vala.setLocalOcorrencia(localOcorrencia);
//
//			if(localOcorrencia.getIndicadorCalcada() == ConstantesSistema.INDICADOR_USO_ATIVO.intValue()){
//
//				// Pavimento Calcada
//				PavimentoCalcada pavimentoCalcada = fachada.pesquisarPavimentoCalcada(Integer.valueOf(idPavimento));
//				vala.setPavimentoCalcada(pavimentoCalcada);
//
//			}else if(localOcorrencia.getIndicadorRua() == ConstantesSistema.INDICADOR_USO_ATIVO.intValue()){
//
//				// Pavimento Rua
//				PavimentoRua pavimentoRua = fachada.pesquisarPavimentoRua(Integer.valueOf(idPavimento));
//				vala.setPavimentoRua(pavimentoRua);
//
//			}
//		}
//
//		vala.setNumeroComprimento(Util.formatarMoedaRealparaBigDecimal(comprimentoVala, 2));
//		vala.setNumeroLargura(Util.formatarMoedaRealparaBigDecimal(larguraVala, 2));
//		vala.setNumeroProfundidade(Util.formatarMoedaRealparaBigDecimal(profundidadeVala, 2));
//
//		if(indicadorValaAterrada != null && !indicadorValaAterrada.equals("")){
//			vala.setIndicadorAterro(Integer.parseInt(indicadorValaAterrada));
//		}else{
//			throw new ActionServletException("atencao.informe_indicador_aterro");
//		}
//
//		if(indicadorEntulho != null && !indicadorEntulho.equals("")){
//			vala.setIndicadorEntulho(Integer.parseInt(indicadorEntulho));
//		}else{
//			throw new ActionServletException("atencao.informe_indicador_entulho");
//		}
//
//		vala.setUltimaAlteracao(new Date());
//		vala.setOrdemServico(ordemServico);
//
//		if(colecaoVala != null && !colecaoVala.isEmpty()){
//			colecaoVala.add(vala);
//		}else{
//			// primeiro elemento
//			colecaoVala = new ArrayList<OrdemServicoValaPavimento>();
//			colecaoVala.add(vala);
//			sessao.setAttribute("colecaoVala", colecaoVala);
//		}
//		httpServletRequest.setAttribute("colecaoVala", colecaoVala);
//		this.limparDadosVala(form);
//
//	}
//
//	/**
//	 * M�todo respons�vel por realizar a exclus�o de uma linha da cole��o.
//	 * 
//	 * @author Virg�nia Melo
//	 * @date 11/06/2009
//	 * @param posicaoLinha
//	 *            Posi��o da linha que ser� exclu�da.
//	 * @param sessao
//	 *            Sess�o
//	 */
//	private void removerLinhaColecao(String posicaoLinha, HttpSession sessao, HttpServletRequest request, String nomeColecao){
//
//		if((posicaoLinha != null && !posicaoLinha.equalsIgnoreCase(""))
//						&& (sessao.getAttribute(nomeColecao) != null && !sessao.getAttribute(nomeColecao).equals(""))){
//
//			List colecao = (ArrayList) sessao.getAttribute(nomeColecao);
//			if(colecao.size() > Integer.valueOf(posicaoLinha)){
//				colecao.remove(Integer.parseInt(posicaoLinha));
//				request.setAttribute(nomeColecao, colecao);
//
//			}
//		}
//	}
//
//	/**
//	 * Adicionar objetos OrdemServicoAtividade na sess�o
//	 * 
//	 * @author Saulo Lima
//	 * @date 18/06/2009
//	 * @param sessao
//	 * @param colecaoAtividades
//	 * @param numeroOS
//	 */
//	private void adicionarOrdemServicoAtividadesNaSessao(HttpSession sessao, Collection<Atividade> colecaoAtividades, Integer numeroOS,
//					Fachada fachada){
//
//		Collection<ManterDadosAtividadesOrdemServicoHelper> colecaoManterDadosAtividadesOrdemServicoHelper = null;
//
//		if(sessao.getAttribute("colecaoManterDadosAtividadesOrdemServicoHelper") != null){
//			colecaoManterDadosAtividadesOrdemServicoHelper = (Collection) sessao
//							.getAttribute("colecaoManterDadosAtividadesOrdemServicoHelper");
//		}else{
//			colecaoManterDadosAtividadesOrdemServicoHelper = new ArrayList<ManterDadosAtividadesOrdemServicoHelper>();
//		}
//
//		for(Atividade atividade : colecaoAtividades){
//			FiltroOrdemServicoAtividade filtroOrdemServicoAtividade = new FiltroOrdemServicoAtividade();
//			filtroOrdemServicoAtividade
//							.adicionarParametro(new ParametroSimples(FiltroOrdemServicoAtividade.ID_ATIVIDADE, atividade.getId()));
//			filtroOrdemServicoAtividade.adicionarParametro(new ParametroSimples(FiltroOrdemServicoAtividade.ID_ORDEM_SERVICO, numeroOS));
//			Collection<OrdemServicoAtividade> colecaoOSAtividade = fachada.pesquisar(filtroOrdemServicoAtividade,
//							OrdemServicoAtividade.class.getName());
//
//			OrdemServicoAtividade ordemServicoAtividade = (OrdemServicoAtividade) Util.retonarObjetoDeColecao(colecaoOSAtividade);
//
//			Collection<Equipe> colecaoEquipes = fachada.obterEquipesProgramadas(Util.converterStringParaInteger(numeroOS.toString()));
//
//			if(ordemServicoAtividade != null){
//				ManterDadosAtividadesOrdemServicoHelper manterDadosAtividadesOrdemServicoHelper = new ManterDadosAtividadesOrdemServicoHelper();
//				manterDadosAtividadesOrdemServicoHelper.setOrdemServicoAtividade(ordemServicoAtividade);
//
//				Collection<OSAtividadePeriodoExecucaoHelper> colecaoOSAtividadePeriodoExecucaoHelper = new ArrayList<OSAtividadePeriodoExecucaoHelper>();
//
//				FiltroOsAtividadePeriodoExecucao filtroOsAtividadePeriodoExecucao = new FiltroOsAtividadePeriodoExecucao();
//				filtroOsAtividadePeriodoExecucao.adicionarParametro(new ParametroSimples(
//								FiltroOsAtividadePeriodoExecucao.ID_ORDEM_SERVICO_ATIVIDADE, ordemServicoAtividade.getId()));
//
//				Collection<OsAtividadePeriodoExecucao> colecaoOsAtividadePeriodoExecucao = fachada.pesquisar(
//								filtroOsAtividadePeriodoExecucao, OsAtividadePeriodoExecucao.class.getName());
//
//				if(colecaoOsAtividadePeriodoExecucao != null && !colecaoOsAtividadePeriodoExecucao.isEmpty()){
//					for(OsAtividadePeriodoExecucao osAtividadePeriodoExecucao : colecaoOsAtividadePeriodoExecucao){
//
//						OSAtividadePeriodoExecucaoHelper helper = new OSAtividadePeriodoExecucaoHelper();
//						helper.setDataExecucaoParaQuebra(Util.formatarDataSemHora(osAtividadePeriodoExecucao.getDataInicio()));
//						helper.setHoraMinutoInicioParaQuebra(Util.formatarHoraSemSegundos(osAtividadePeriodoExecucao.getDataInicio()));
//						helper.setHoraMinutoFimParaQuebra(Util.formatarHoraSemSegundos(osAtividadePeriodoExecucao.getDataFim()));
//						helper.setOsAtividadePeriodoExecucao(osAtividadePeriodoExecucao);
//
//						Collection<OSExecucaoEquipeHelper> colecaoOSExecucaoEquipeHelper = new ArrayList<OSExecucaoEquipeHelper>();
//						if(colecaoEquipes != null && !colecaoEquipes.isEmpty()){
//							for(Equipe equipe : colecaoEquipes){
//								OSExecucaoEquipeHelper osExecucaoEquipeHelper = new OSExecucaoEquipeHelper();
//								OsExecucaoEquipe osExecucaoEquipe = new OsExecucaoEquipe();
//								osExecucaoEquipe.setEquipe(equipe);
//								osExecucaoEquipeHelper.setOsExecucaoEquipe(osExecucaoEquipe);
//								colecaoOSExecucaoEquipeHelper.add(osExecucaoEquipeHelper);
//							}
//						}
//						if(colecaoOSExecucaoEquipeHelper != null && !colecaoOSExecucaoEquipeHelper.isEmpty()){
//							helper.setColecaoOSExecucaoEquipeHelper(colecaoOSExecucaoEquipeHelper);
//						}
//						colecaoOSAtividadePeriodoExecucaoHelper.add(helper);
//					}
//
//				}
//				if(colecaoOSAtividadePeriodoExecucaoHelper != null && !colecaoOSAtividadePeriodoExecucaoHelper.isEmpty()){
//					manterDadosAtividadesOrdemServicoHelper
//									.setColecaoOSAtividadePeriodoExecucaoHelper(colecaoOSAtividadePeriodoExecucaoHelper);
//				}
//
//				colecaoManterDadosAtividadesOrdemServicoHelper.add(manterDadosAtividadesOrdemServicoHelper);
//			}
//
//		}
//
//		sessao.setAttribute("colecaoManterDadosAtividadesOrdemServicoHelper", colecaoManterDadosAtividadesOrdemServicoHelper);
//	}
//
//	/**
//	 * Limpar dados interrup��o deslocamento.
//	 * 
//	 * @param form
//	 *            Form.
//	 */
//	private void limparDadosInterrupcao(ManterDadosAtividadesOrdemServicoActionForm form){
//
//		form.setDataInicioInterrupcao("");
//		form.setHoraInicioInterrupcao("");
//		form.setDataFimInterrupcao("");
//		form.setHoraFimInterrupcao("");
//		form.setKmInterrupcao("");
//		form.setIdMotivoInterrupcao("");
//	}
//
//	/**
//	 * Limpar dados da vala.
//	 * 
//	 * @param form
//	 *            Form.
//	 */
//	private void limparDadosVala(ManterDadosAtividadesOrdemServicoActionForm form){
//
//		form.setNumeroVala("");
//		form.setIdLocalOcorrencia("");
//		form.setIdPavimento("");
//		form.setComprimentoVala("");
//		form.setLarguraVala("");
//		form.setProfundidadeVala("");
//		form.setIndicadorValaAterrada("");
//		form.setIndicadorEntulho("");
//	}
//
//	/**
//	 * Pesquisar a Programacao Roteiro
//	 * 
//	 * @param data
//	 *            do roteiro,idUnidade
//	 */
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
//	private void limparDadosDeslocamento(ManterDadosAtividadesOrdemServicoActionForm form){
//
//		// Deslocamento
//		form.setKmInicial("");
//		form.setKmFinal("");
//		form.setDataInicioInterrupcao("");
//		form.setHoraInicioInterrupcao("");
//		form.setDataFimInterrupcao("");
//		form.setHoraFimInterrupcao("");
//		form.setKmInterrupcao("");
//		form.setIdMotivoInterrupcao("");
//		form.setDescricaoMotivoInterrupcao("");
//		form.setDataInicioDeslocamento("");
//		form.setDataFimDeslocamento("");
//		form.setHoraInicioDeslocamento("");
//		form.setHoraFimDeslocamento("");
//
//	}
//
//	private void limparDadosRede(ManterDadosAtividadesOrdemServicoActionForm form){
//
//		// Rede/Ramal �gua
//		form.setIdCausaAgua("");
//		form.setIdRedeRamalAgua("");
//		form.setIdDiametroAgua("");
//		form.setIdMaterialAgua("");
//		form.setProfundidadeAgua("");
//		form.setPressaoAgua("");
//		form.setIdAgenteExternoAgua("");
//
//		// Rede/Ramal Esgoto
//		form.setIdCausaEsgoto("");
//		form.setIdRedeRamalEsgoto("");
//		form.setIdDiametroEsgoto("");
//		form.setIdMaterialEsgoto("");
//		form.setProfundidadeEsgoto("");
//		form.setPressaoEsgoto("");
//		form.setIdAgenteExternoEsgoto("");
//	}
//
// }
