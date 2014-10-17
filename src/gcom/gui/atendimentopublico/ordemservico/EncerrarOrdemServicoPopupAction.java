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
//import gcom.atendimentopublico.bean.IntegracaoComercialHelper;
//import gcom.atendimentopublico.ordemservico.EventoGeracaoServico;
//import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
//import gcom.atendimentopublico.ordemservico.FiltroServicoTipoOperacao;
//import gcom.atendimentopublico.ordemservico.OcorrenciaInfracao;
//import gcom.atendimentopublico.ordemservico.OrdemServico;
//import gcom.atendimentopublico.ordemservico.OrigemEncerramentoOrdemServico;
//import gcom.atendimentopublico.ordemservico.ServicoTipo;
//import gcom.atendimentopublico.ordemservico.ServicoTipoOperacao;
//import gcom.atendimentopublico.ordemservico.bean.DadosAtividadesOrdemServicoHelper;
//import gcom.atendimentopublico.ordemservico.bean.ManterDadosAtividadesOrdemServicoHelper;
//import gcom.atendimentopublico.ordemservico.bean.OSEncerramentoHelper;
//import gcom.atendimentopublico.ordemservico.bean.ServicoAssociadoAutorizacaoHelper;
//import gcom.atendimentopublico.registroatendimento.AtendimentoMotivoEncerramento;
//import gcom.atendimentopublico.registroatendimento.AtendimentoRelacaoTipo;
//import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
//import gcom.atendimentopublico.registroatendimento.RegistroAtendimentoUnidade;
//import gcom.fachada.Fachada;
//import gcom.gui.ActionServletException;
//import gcom.gui.GcomAction;
//import gcom.seguranca.acesso.usuario.Usuario;
//import gcom.util.ConstantesSistema;
//import gcom.util.Util;
//import gcom.util.filtro.ParametroSimples;
//
//import java.util.Collection;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
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
// * Action que define o pr�-processamento da p�gina de encerrar OS caso precise entrar na tela
// * 
// * @author S�vio Luiz
// * @created 18/09/2006
// */
//public class EncerrarOrdemServicoPopupAction
//				extends GcomAction {
//
//	private static final String FORWARD_ENCERRAR_ORDEM_SERVICO_POPUP = "encerrarOrdemServicoPopup";
//
//	private static final String ATRIBUTO_POPUP = "popup";
//
//	private static final String ATRIBUTO_AUTORIZAR_SERVICO_ASSOCIADO_HELPER = "autorizarServicoAssociadoHelper";
//
//	private static final String ATRIBUTO_ORIGEM_ENCERRAMENTO_OS = "origemEncerramentoOS";
//
//	private static final String CAMINHO_ENCERRAR_ORDEM_SERVICO = "encerrarOrdemServicoPopupAction";
//
//	private static final String CAMINHO_RETORNO_CONSULTAR_OS = ""; // TODO
//
//	/**
//	 * Execute do Consultar OS Popup
//	 * 
//	 * @param actionMapping
//	 * @param actionForm
//	 * @param httpServletRequest
//	 * @param httpServletResponse
//	 * @return forward
//	 */
//	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
//					HttpServletResponse httpServletResponse){
//
//		// Seta o mapeamento de retorno
//		ActionForward retorno = actionMapping.findForward(FORWARD_ENCERRAR_ORDEM_SERVICO_POPUP);
//
//		Fachada fachada = Fachada.getInstancia();
//		HttpSession sessao = httpServletRequest.getSession(false);
//		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
//		EncerrarOrdemServicoActionForm encerrarOrdemServicoActionForm = (EncerrarOrdemServicoActionForm) actionForm;
//
//		OcorrenciaInfracao ocorrenciaInfracao = null;
//		Integer[] colecaoInfracaoTipo = null;
//
//		sessao.setAttribute(ATRIBUTO_POPUP, true);
//
//		String valorConfirmacao = httpServletRequest.getParameter("confirmado");
//
//		// parte da integra��o com o sistema comercial
//		IntegracaoComercialHelper integracaoComercialHelper = (IntegracaoComercialHelper) sessao.getAttribute("integracaoComercialHelper");
//
//		if(valorConfirmacao == null || valorConfirmacao.equals("")){
//
//			AutorizarServicoAssociadoHelper autorizarServicoAssociadoHelper = null;
//
//			Map<Integer, ServicoAssociadoAutorizacaoHelper> mapServicosAutorizados = (Map<Integer, ServicoAssociadoAutorizacaoHelper>) httpServletRequest
//							.getAttribute("mapServicosAutorizados");
//
//			if(mapServicosAutorizados == null || mapServicosAutorizados.isEmpty()){
//
//				OSEncerramentoHelper osEncerramentoHelper = new OSEncerramentoHelper();
//				String indicadorFormaEncerramento = "";
//
//				if(sessao.getAttribute("ocorrenciaInfracao") != null && sessao.getAttribute("colecaoInfracaoTipo") != null){
//					ocorrenciaInfracao = (OcorrenciaInfracao) sessao.getAttribute("ocorrenciaInfracao");
//					colecaoInfracaoTipo = (Integer[]) sessao.getAttribute("colecaoInfracaoTipo");
//
//					osEncerramentoHelper.setOcorrenciaInfracao(ocorrenciaInfracao);
//					osEncerramentoHelper.setColecaoInfracaoTipo(colecaoInfracaoTipo);
//				}
//
//				// valida enter
//				String idServicoTipo = encerrarOrdemServicoActionForm.getIdServicoTipo();
//				String descricaoServicoTipo = encerrarOrdemServicoActionForm.getDescricaoServicoTipo();
//
//				// Collection<ManterDadosAtividadesOrdemServicoHelper>
//				// colecaoManterDadosAtividadesOrdemServicoHelper =
//				// (Collection<ManterDadosAtividadesOrdemServicoHelper>)
//				// sessao.getAttribute("colecaoManutencao");
//				DadosAtividadesOrdemServicoHelper dadosHelper = (DadosAtividadesOrdemServicoHelper) sessao
//								.getAttribute("colecaoManutencao");
//
//				Collection<ManterDadosAtividadesOrdemServicoHelper> colecaoManterDadosAtividadesOrdemServicoHelper = null;
//
//				if(dadosHelper != null){
//					colecaoManterDadosAtividadesOrdemServicoHelper = dadosHelper.getColecaoManterDadosAtividadesOrdemServicoHelper();
//				}
//
//				if((idServicoTipo != null && !idServicoTipo.equals(""))
//								&& (descricaoServicoTipo == null || descricaoServicoTipo.equals(""))){
//
//					FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
//
//					filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, idServicoTipo));
//					filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.INDICADOR_USO,
//									ConstantesSistema.INDICADOR_USO_ATIVO));
//
//					Collection<ServicoTipo> servicoTipoEncontrado = fachada.pesquisar(filtroServicoTipo, ServicoTipo.class.getName());
//
//					if(servicoTipoEncontrado == null || servicoTipoEncontrado.isEmpty()){
//						throw new ActionServletException("atencao.label_inexistente", null, "Servi�o Tipo");
//					}else{
//						ServicoTipo servicoTipo = (ServicoTipo) Util.retonarObjetoDeColecao(servicoTipoEncontrado);
//
//						// O servi�o tipo foi encontrado
//						encerrarOrdemServicoActionForm.setIdServicoTipo(String.valueOf(servicoTipo.getId()));
//						encerrarOrdemServicoActionForm.setDescricaoServicoTipo(servicoTipo.getDescricao());
//					}
//
//				}
//				if(encerrarOrdemServicoActionForm.getIndicadorPavimento() != null
//								&& !("").equals(encerrarOrdemServicoActionForm.getIndicadorPavimento())
//								&& Short.valueOf(encerrarOrdemServicoActionForm.getIndicadorPavimento()) == ServicoTipo.INDICADOR_PAVIMENTO_SIM
//								&& ("2").equals(encerrarOrdemServicoActionForm.getIdMotivoEncerramento())){
//					if(encerrarOrdemServicoActionForm.getDimensao1() == null || ("").equals(encerrarOrdemServicoActionForm.getDimensao1())){
//						throw new ActionServletException("atencao.campo_texto.obrigatorio", null, "Pavimento Dimens�o 1");
//					}else{
//						if(encerrarOrdemServicoActionForm.getDimensao2() == null
//										|| ("").equals(encerrarOrdemServicoActionForm.getDimensao2())){
//							throw new ActionServletException("atencao.campo_texto.obrigatorio", null, "Pavimento Dimens�o 2");
//						}else{
//							encerrarOrdemServicoActionForm.setPavimento(String.valueOf(Double.valueOf(encerrarOrdemServicoActionForm
//											.getDimensao1().replaceAll(",", "."))
//											* Double.valueOf(encerrarOrdemServicoActionForm.getDimensao2().replaceAll(",", "."))));
//						}
//					}
//				}
//
//				// caso seja a primeira vez
//				if(integracaoComercialHelper == null || integracaoComercialHelper.equals("")){
//
//					// [FS0002] - Validar Tipo Servi�o
//					// [FS0004] - Verificar preenchimento dos campos
//					// [FS0007] - Validar Data de Encerramento
//					// [FS0008] - Validar Data do roteiro
//					fachada.validarCamposEncerrarOS(encerrarOrdemServicoActionForm.getIndicadorExecucao(), encerrarOrdemServicoActionForm
//									.getNumeroOS(), encerrarOrdemServicoActionForm.getIdMotivoEncerramento(),
//									encerrarOrdemServicoActionForm.getDataEncerramento(), colecaoManterDadosAtividadesOrdemServicoHelper,
//									encerrarOrdemServicoActionForm.getTipoServicoReferenciaId(), encerrarOrdemServicoActionForm
//													.getIndicadorPavimento(), encerrarOrdemServicoActionForm.getPavimento(),
//									encerrarOrdemServicoActionForm.getIdTipoRetornoReferida(), encerrarOrdemServicoActionForm
//													.getIndicadorDeferimento(), encerrarOrdemServicoActionForm.getIndicadorTrocaServico(),
//									idServicoTipo, encerrarOrdemServicoActionForm.getDataRoteiro(), encerrarOrdemServicoActionForm
//													.getNumeroRA(), encerrarOrdemServicoActionForm.getIndicadorVistoriaServicoTipo(),
//									encerrarOrdemServicoActionForm.getCodigoRetornoVistoriaOs(), encerrarOrdemServicoActionForm
//													.getHoraEncerramento(), usuarioLogado);
//				}
//
//				// indicador execu��o seja diferente de nulo
//				if(encerrarOrdemServicoActionForm.getIndicadorExecucao() != null
//								&& !encerrarOrdemServicoActionForm.getIndicadorExecucao().equals("")){
//
//					short indicadorExecucao = Short.parseShort(encerrarOrdemServicoActionForm.getIndicadorExecucao());
//					Integer numeroOS = Util.converterStringParaInteger(encerrarOrdemServicoActionForm.getNumeroOS());
//
//					Date dataEncerramento = null;
//
//					if(encerrarOrdemServicoActionForm.getHoraEncerramento() == null
//									|| encerrarOrdemServicoActionForm.getHoraEncerramento().equals("")){
//						dataEncerramento = Util.converteStringParaDate(encerrarOrdemServicoActionForm.getDataEncerramento());
//					}else{
//						dataEncerramento = Util.converteStringParaDateHora(encerrarOrdemServicoActionForm.getDataEncerramento() + " "
//										+ encerrarOrdemServicoActionForm.getHoraEncerramento() + ":00");
//					}
//
//					Date dataUltimaAlteracao = encerrarOrdemServicoActionForm.getUltimaAlteracao();
//
//					// indicador execu��o seja igual a n�o(2)
//					if(indicadorExecucao == AtendimentoMotivoEncerramento.INDICADOR_EXECUCAO_NAO){
//						// OrdemServico osFiscalizacao = (OrdemServico)
//						// sessao.getAttribute("ordemServico");
//						OrdemServico osFiscalizacao = (OrdemServico) sessao.getAttribute("osFiscalizacao");
//
//						osEncerramentoHelper.setNumeroOS(numeroOS);
//						osEncerramentoHelper.setDataEncerramento(dataEncerramento);
//						osEncerramentoHelper.setUsuarioLogado(usuarioLogado);
//						osEncerramentoHelper.setIdMotivoEncerramento(encerrarOrdemServicoActionForm.getIdMotivoEncerramento());
//						osEncerramentoHelper.setUltimaAlteracao(dataUltimaAlteracao);
//						osEncerramentoHelper.setParecerEncerramento(encerrarOrdemServicoActionForm.getObservacaoEncerramento());
//						osEncerramentoHelper.setOsFiscalizacao(osFiscalizacao);
//						osEncerramentoHelper.setIndicadorVistoriaServicoTipo(encerrarOrdemServicoActionForm
//										.getIndicadorVistoriaServicoTipo());
//						osEncerramentoHelper.setCodigoRetornoVistoriaOs(encerrarOrdemServicoActionForm.getCodigoRetornoVistoriaOs());
//
//						// [SB0001] - Encerrar sem execu��o
//						fachada
//										.encerrarOSSemExecucao(osEncerramentoHelper, null,
//														OrigemEncerramentoOrdemServico.ENCERRAMENTO_ORDEM_SERVICO);
//
//					}else{
//						// indicador execu��o seja igual a sim(1)
//						if(indicadorExecucao == AtendimentoMotivoEncerramento.INDICADOR_EXECUCAO_SIM){
//
//							if(encerrarOrdemServicoActionForm.getIndicadorAtualizaComercial() != null
//											&& !encerrarOrdemServicoActionForm.getIndicadorAtualizaComercial().equals("")){
//
//								Short indicadorComercialAtualiza = Short.valueOf(encerrarOrdemServicoActionForm
//												.getIndicadorAtualizaComercial());
//
//								if(indicadorComercialAtualiza.equals(ServicoTipo.INDICADOR_ATUALIZA_COMERCIAL_SIM)){
//
//									// caso n�o exista o objeto helper na sess�o ent�o � a primeira
//									// vez
//									if(integracaoComercialHelper == null || integracaoComercialHelper.equals("")){
//
//										// caso exista tipo de servi�o
//										if(encerrarOrdemServicoActionForm.getTipoServicoOSId() != null
//														&& !encerrarOrdemServicoActionForm.getTipoServicoOSId().equals("")){
//
//											FiltroServicoTipoOperacao filtroServicoTipoOperacao = new FiltroServicoTipoOperacao();
//											filtroServicoTipoOperacao.adicionarCaminhoParaCarregamentoEntidade("operacao");
//											filtroServicoTipoOperacao.adicionarParametro(new ParametroSimples(
//															FiltroServicoTipoOperacao.ID_SERVICO_TIPO, encerrarOrdemServicoActionForm
//																			.getTipoServicoOSId()));
//											Collection colecaoServicoTipoOperacao = fachada.pesquisar(filtroServicoTipoOperacao,
//															ServicoTipoOperacao.class.getName());
//
//											// caso exista uma funcionalidade associada ao servi�o
//											// tipo
//											if(colecaoServicoTipoOperacao != null && !colecaoServicoTipoOperacao.isEmpty()){
//												ServicoTipoOperacao servicoTipoOperacao = (ServicoTipoOperacao) Util
//																.retonarObjetoDeColecao(colecaoServicoTipoOperacao);
//												String caminhoOperacao = servicoTipoOperacao.getOperacao().getCaminhoUrl();
//
//												// caso exista o caminho da opera��o
//												if(caminhoOperacao != null && !caminhoOperacao.equals("")){
//													int tamanhoOperacao = caminhoOperacao.length();
//
//													// seta o caminho no mapeamento para ser chamado
//													String caminhoRetorno = caminhoOperacao.substring(0, tamanhoOperacao - 3);
//													httpServletRequest.setAttribute("veioEncerrarOS", encerrarOrdemServicoActionForm
//																	.getNumeroOS());
//													httpServletRequest.setAttribute("dataEncerramento", encerrarOrdemServicoActionForm
//																	.getDataEncerramento());
//													httpServletRequest.setAttribute("caminhoRetornoIntegracaoComercial",
//																	"exibirEncerrarOrdemServicoPopupAction.do?retornoConsulta=1");
//													retorno = actionMapping.findForward(caminhoRetorno);
//													if(retorno == null){
//														throw new ActionServletException("atencao.caminho_url_indisponivel");
//													}else{
//
//														// caso seja chamado a integra��o ent�o seta
//														// a OS na sess�o com outro
//														// nome e remove a OS da sess�o visto que a
//														// integra��o usa o mesmo
//														// nome da OS que est� na sess�o
//														sessao.setAttribute("osFiscalizacao", sessao.getAttribute("ordemServico"));
//														sessao.removeAttribute("ordemServico");
//													}
//												}
//											}
//										}
//									}
//								}
//							}
//							if(retorno.getName().equalsIgnoreCase(FORWARD_ENCERRAR_ORDEM_SERVICO_POPUP)){
//
//								OrdemServico osFiscalizacao = null;
//
//								// if (integracaoComercialHelper == null ||
//								// integracaoComercialHelper.equals("")) {
//								// osFiscalizacao = (OrdemServico)
//								// sessao.getAttribute("ordemServico");
//								// }
//
//								// caso a os fiscaliza��o n�o esteja na sess�o ent�o
//								// n�o foi para integra��o e ent�o pode pegar o OS mesmo
//								if(sessao.getAttribute("osFiscalizacao") != null && !sessao.getAttribute("osFiscalizacao").equals("")){
//									osFiscalizacao = (OrdemServico) sessao.getAttribute("osFiscalizacao");
//								}
//
//								OrdemServico ordemServico = fachada.consultarDadosOrdemServico(numeroOS);
//								ServicoTipo servicoTipo = ordemServico.getServicoTipo();
//
//								List<ServicoAssociadoAutorizacaoHelper> servicoAssociadoAutorizacaoHelperList = null;
//
//								if(isOrigemRoteiro((OrigemEncerramentoOrdemServico) sessao.getAttribute(ATRIBUTO_ORIGEM_ENCERRAMENTO_OS))){
//									servicoAssociadoAutorizacaoHelperList = fachada.verificarServicosAssociadosParaAutorizacao(servicoTipo,
//													EventoGeracaoServico.ENCERRAMENTO_ORDEM_SERVICO,
//													OrigemEncerramentoOrdemServico.ACOMPANHAMENTO_ROTEIRO, numeroOS);
//								}else{
//									servicoAssociadoAutorizacaoHelperList = fachada.verificarServicosAssociadosParaAutorizacao(servicoTipo,
//													EventoGeracaoServico.ENCERRAMENTO_ORDEM_SERVICO,
//													OrigemEncerramentoOrdemServico.ENCERRAMENTO_ORDEM_SERVICO, numeroOS);
//								}
//
//								// se o servi�o tipo referencia seja igual a nulo
//								if(encerrarOrdemServicoActionForm.getTipoServicoReferenciaId() == null
//												|| encerrarOrdemServicoActionForm.getTipoServicoReferenciaId().equals("")){
//
//									osEncerramentoHelper.setNumeroOS(numeroOS);
//									osEncerramentoHelper.setDataEncerramento(dataEncerramento);
//									osEncerramentoHelper.setUsuarioLogado(usuarioLogado);
//									osEncerramentoHelper.setIdMotivoEncerramento(encerrarOrdemServicoActionForm.getIdMotivoEncerramento());
//									osEncerramentoHelper.setUltimaAlteracao(dataUltimaAlteracao);
//									osEncerramentoHelper.setParecerEncerramento(encerrarOrdemServicoActionForm.getObservacaoEncerramento());
//									osEncerramentoHelper.setIndicadorPavimento(encerrarOrdemServicoActionForm.getIndicadorPavimento());
//									osEncerramentoHelper.setPavimento(encerrarOrdemServicoActionForm.getPavimento());
//									osEncerramentoHelper
//													.setColecaoManterDadosAtividadesOrdemServicoHelper(colecaoManterDadosAtividadesOrdemServicoHelper);
//									osEncerramentoHelper.setIntegracaoComercialHelper(integracaoComercialHelper);
//									osEncerramentoHelper.setTipoServicoOSId(encerrarOrdemServicoActionForm.getTipoServicoOSId());
//									osEncerramentoHelper.setOsFiscalizacao(osFiscalizacao);
//									osEncerramentoHelper.setIndicadorVistoriaServicoTipo(encerrarOrdemServicoActionForm
//													.getIndicadorVistoriaServicoTipo());
//									osEncerramentoHelper.setCodigoRetornoVistoriaOs(encerrarOrdemServicoActionForm
//													.getCodigoRetornoVistoriaOs());
//									osEncerramentoHelper.setDimensao1(encerrarOrdemServicoActionForm.getDimensao1());
//									osEncerramentoHelper.setDimensao2(encerrarOrdemServicoActionForm.getDimensao2());
//									osEncerramentoHelper.setDimensao3(encerrarOrdemServicoActionForm.getDimensao3());
//
//									indicadorFormaEncerramento = "encerrarOSComExecucaoSemReferencia";
//
//								}else{
//
//									osEncerramentoHelper.setNumeroOS(numeroOS);
//									osEncerramentoHelper.setDataEncerramento(dataEncerramento);
//									osEncerramentoHelper.setUsuarioLogado(usuarioLogado);
//									osEncerramentoHelper.setIdMotivoEncerramento(encerrarOrdemServicoActionForm.getIdMotivoEncerramento());
//									osEncerramentoHelper.setUltimaAlteracao(dataUltimaAlteracao);
//									osEncerramentoHelper.setParecerEncerramento(encerrarOrdemServicoActionForm.getObservacaoEncerramento());
//									osEncerramentoHelper.setIndicadorPavimento(encerrarOrdemServicoActionForm.getIndicadorPavimento());
//									osEncerramentoHelper.setPavimento(encerrarOrdemServicoActionForm.getPavimento());
//									osEncerramentoHelper.setIdTipoRetornoOSReferida(encerrarOrdemServicoActionForm
//													.getIdTipoRetornoReferida());
//									osEncerramentoHelper.setIndicadorDeferimento(encerrarOrdemServicoActionForm.getIndicadorDeferimento());
//									osEncerramentoHelper.setIndicadorTrocaServicoTipo(encerrarOrdemServicoActionForm
//													.getIndicadorTrocaServico());
//									osEncerramentoHelper.setIdServicoTipo(encerrarOrdemServicoActionForm.getIdServicoTipo());
//									osEncerramentoHelper.setIdOSReferencia(encerrarOrdemServicoActionForm.getNumeroOSReferencia());
//									osEncerramentoHelper.setIdServicoTipoReferenciaOS(encerrarOrdemServicoActionForm
//													.getServicoTipoReferenciaOS());
//									osEncerramentoHelper
//													.setColecaoManterDadosAtividadesOrdemServicoHelper(colecaoManterDadosAtividadesOrdemServicoHelper);
//									osEncerramentoHelper.setIntegracaoComercialHelper(integracaoComercialHelper);
//									osEncerramentoHelper.setTipoServicoOSId(encerrarOrdemServicoActionForm.getTipoServicoOSId());
//									osEncerramentoHelper.setOsFiscalizacao(osFiscalizacao);
//									osEncerramentoHelper.setIndicadorVistoriaServicoTipo(encerrarOrdemServicoActionForm
//													.getIndicadorVistoriaServicoTipo());
//									osEncerramentoHelper.setCodigoRetornoVistoriaOs(encerrarOrdemServicoActionForm
//													.getCodigoRetornoVistoriaOs());
//									osEncerramentoHelper.setDimensao1(encerrarOrdemServicoActionForm.getDimensao1());
//									osEncerramentoHelper.setDimensao2(encerrarOrdemServicoActionForm.getDimensao2());
//									osEncerramentoHelper.setDimensao3(encerrarOrdemServicoActionForm.getDimensao3());
//
//									indicadorFormaEncerramento = "encerrarOSComExecucaoComReferencia";
//
//								}
//
//								if(servicoAssociadoAutorizacaoHelperList != null && !servicoAssociadoAutorizacaoHelperList.isEmpty()){
//
//									// Parametros obrigatorios para a tela de autoriza��o
//									autorizarServicoAssociadoHelper = new AutorizarServicoAssociadoHelper();
//
//									Map<String, Object> parametrosCaminhoAutorizacao = new HashMap<String, Object>();
//									parametrosCaminhoAutorizacao.put("indicadorFormaEncerramento", indicadorFormaEncerramento);
//									parametrosCaminhoAutorizacao.put("osEncerramentoHelper", osEncerramentoHelper);
//									autorizarServicoAssociadoHelper.setParametrosCaminhoAutorizacao(parametrosCaminhoAutorizacao);
//									autorizarServicoAssociadoHelper.setCaminhoAutorizacao(CAMINHO_ENCERRAR_ORDEM_SERVICO);
//
//									Map<String, Object> parametrosCaminhoRetorno = new HashMap<String, Object>();
//									parametrosCaminhoRetorno.put("numeroOS", numeroOS);
//									autorizarServicoAssociadoHelper.setParametrosCaminhoRetorno(parametrosCaminhoRetorno);
//									autorizarServicoAssociadoHelper.setCaminhoRetorno(CAMINHO_RETORNO_CONSULTAR_OS);
//
//									autorizarServicoAssociadoHelper.setServicosParaAutorizacao(servicoAssociadoAutorizacaoHelperList);
//
//									sessao.setAttribute(ATRIBUTO_AUTORIZAR_SERVICO_ASSOCIADO_HELPER, autorizarServicoAssociadoHelper);
//
//									retorno = actionMapping.findForward("mostrarAutorizarServicoAssociado");
//
//									return retorno;
//
//								}else{
//
//									if(indicadorFormaEncerramento.equals("encerrarOSComExecucaoSemReferencia")){
//
//										// [SB0002] - Encerrar com execu��o e sem refer�ncia
//										if(this.isOrigemRoteiro((OrigemEncerramentoOrdemServico) sessao
//														.getAttribute(ATRIBUTO_ORIGEM_ENCERRAMENTO_OS))){
//											fachada.encerrarOSComExecucaoSemReferencia(osEncerramentoHelper, null,
//															OrigemEncerramentoOrdemServico.ACOMPANHAMENTO_ROTEIRO);
//										}else{
//											fachada.encerrarOSComExecucaoSemReferencia(osEncerramentoHelper, null,
//															OrigemEncerramentoOrdemServico.ENCERRAMENTO_ORDEM_SERVICO);
//										}
//
//									}else if(indicadorFormaEncerramento.equals("encerrarOSComExecucaoComReferencia")){
//
//										// [SB0003] - Encerrar com execu��o e com refer�ncia
//										if(this.isOrigemRoteiro((OrigemEncerramentoOrdemServico) sessao
//														.getAttribute(ATRIBUTO_ORIGEM_ENCERRAMENTO_OS))){
//											fachada.encerrarOSComExecucaoComReferencia(osEncerramentoHelper, null,
//															OrigemEncerramentoOrdemServico.ACOMPANHAMENTO_ROTEIRO);
//										}else{
//											fachada.encerrarOSComExecucaoComReferencia(osEncerramentoHelper, null,
//															OrigemEncerramentoOrdemServico.ENCERRAMENTO_ORDEM_SERVICO);
//										}
//
//									}else{
//										throw new ActionServletException("atencao.campo.invalido", null,
//														"Indicador de Forma de Encerramento");
//									}
//
//								}
//
//							}
//						}
//					}
//				}
//
//				// if (retorno.getName().equalsIgnoreCase(FORWARD_ENCERRAR_ORDEM_SERVICO_POPUP)) {
//				// boolean telaConfirmacao = fachada
//				// .tramitarOuEncerrarRADaOSEncerrada(Util.converterStringParaInteger(encerrarOrdemServicoActionForm.getNumeroOS()),
//				// usuarioLogado, encerrarOrdemServicoActionForm.getIdMotivoEncerramento(),
//				// encerrarOrdemServicoActionForm
//				// .getNumeroRA(), encerrarOrdemServicoActionForm.getDataRoteiro());
//				//
//				// // se for para ir para a tela de confirma��o
//				// if (telaConfirmacao) {
//				// httpServletRequest.setAttribute("caminhoActionConclusao",
//				// "/gsan/encerrarOrdemServicoPopupAction.do");
//				// httpServletRequest.setAttribute("cancelamento", "TRUE");
//				// httpServletRequest.setAttribute("nomeBotao1", "Sim");
//				// httpServletRequest.setAttribute("nomeBotao2", "N�o");
//				//
//				// return montarPaginaConfirmacao("atencao.encerrar_RA_da_OS", httpServletRequest,
//				// actionMapping);
//				// }
//				// }
//			}else{
//
//				autorizarServicoAssociadoHelper = (AutorizarServicoAssociadoHelper) sessao
//								.getAttribute(ATRIBUTO_AUTORIZAR_SERVICO_ASSOCIADO_HELPER);
//				Map<String, Object> parametrosCaminhoAutorizacao = autorizarServicoAssociadoHelper.getParametrosCaminhoAutorizacao();
//				String indicadorFormaEncerramento = (String) parametrosCaminhoAutorizacao.get("indicadorFormaEncerramento");
//				OSEncerramentoHelper osEncerramentoHelper = (OSEncerramentoHelper) parametrosCaminhoAutorizacao.get("osEncerramentoHelper");
//
//				if(sessao.getAttribute("ocorrenciaInfracao") != null && sessao.getAttribute("colecaoInfracaoTipo") != null){
//					ocorrenciaInfracao = (OcorrenciaInfracao) sessao.getAttribute("ocorrenciaInfracao");
//					colecaoInfracaoTipo = (Integer[]) sessao.getAttribute("colecaoInfracaoTipo");
//
//					osEncerramentoHelper.setOcorrenciaInfracao(ocorrenciaInfracao);
//					osEncerramentoHelper.setColecaoInfracaoTipo(colecaoInfracaoTipo);
//				}
//
//				if(indicadorFormaEncerramento.equals("encerrarOSComExecucaoSemReferencia")){
//					// [SB0002] - Encerrar com execu��o e sem refer�ncia
//					if(isOrigemRoteiro((OrigemEncerramentoOrdemServico) sessao.getAttribute(ATRIBUTO_ORIGEM_ENCERRAMENTO_OS))){
//						fachada.encerrarOSComExecucaoSemReferencia(osEncerramentoHelper, mapServicosAutorizados,
//										OrigemEncerramentoOrdemServico.ACOMPANHAMENTO_ROTEIRO);
//					}else{
//						fachada.encerrarOSComExecucaoSemReferencia(osEncerramentoHelper, mapServicosAutorizados,
//										OrigemEncerramentoOrdemServico.ENCERRAMENTO_ORDEM_SERVICO);
//					}
//
//				}else if(indicadorFormaEncerramento.equals("encerrarOSComExecucaoComReferencia")){
//					// [SB0003] - Encerrar com execu��o e com refer�ncia
//					if(isOrigemRoteiro((OrigemEncerramentoOrdemServico) sessao.getAttribute(ATRIBUTO_ORIGEM_ENCERRAMENTO_OS))){
//						fachada.encerrarOSComExecucaoComReferencia(osEncerramentoHelper, mapServicosAutorizados,
//										OrigemEncerramentoOrdemServico.ACOMPANHAMENTO_ROTEIRO);
//					}else{
//						fachada.encerrarOSComExecucaoComReferencia(osEncerramentoHelper, mapServicosAutorizados,
//										OrigemEncerramentoOrdemServico.ENCERRAMENTO_ORDEM_SERVICO);
//					}
//				}else{
//					throw new ActionServletException("atencao.campo.invalido", null, "Indicador de Forma de Encerramento");
//				}
//
//			}
//
//			// ----------------------------------------------------------------------------------------------------------------
//			// Foi colocado nesse trecho, pois deve ser exibida a pegunta de encerrar RA nos 2
//			// casos, com ou sem servi�os associados.
//			if(retorno.getName().equalsIgnoreCase(FORWARD_ENCERRAR_ORDEM_SERVICO_POPUP)){
//				boolean telaConfirmacao = fachada.tramitarOuEncerrarRADaOSEncerrada(Util
//								.converterStringParaInteger(encerrarOrdemServicoActionForm.getNumeroOS()), usuarioLogado,
//								encerrarOrdemServicoActionForm.getIdMotivoEncerramento(), encerrarOrdemServicoActionForm.getNumeroRA(),
//								encerrarOrdemServicoActionForm.getDataRoteiro());
//
//				// se for para ir para a tela de confirma��o
//				if(telaConfirmacao){
//					httpServletRequest.setAttribute("caminhoActionConclusao", "/gsan/encerrarOrdemServicoPopupAction.do");
//					httpServletRequest.setAttribute("cancelamento", "TRUE");
//					httpServletRequest.setAttribute("nomeBotao1", "Sim");
//					httpServletRequest.setAttribute("nomeBotao2", "N�o");
//
//					return montarPaginaConfirmacao("atencao.encerrar_RA_da_OS", httpServletRequest, actionMapping);
//				}
//			}
//
//			// ----------------------------------------------------------------------------------------------------------------
//
//		}else{
//
//			// se o usu�rio confirmar o encerramento da RA da OS que est� sendo encerrada
//			if(valorConfirmacao.equals("ok")){
//				RegistroAtendimento registroAtendimento = new RegistroAtendimento();
//				registroAtendimento.setId(Util.converterStringParaInteger(encerrarOrdemServicoActionForm.getNumeroRA()));
//				AtendimentoMotivoEncerramento atendimentoMotivoEncerramento = new AtendimentoMotivoEncerramento();
//				atendimentoMotivoEncerramento.setId(Util.converterStringParaInteger(encerrarOrdemServicoActionForm
//								.getIdMotivoEncerramento()));
//				registroAtendimento.setAtendimentoMotivoEncerramento(atendimentoMotivoEncerramento);
//				registroAtendimento.setCodigoSituacao(RegistroAtendimento.SITUACAO_ENCERRADO);
//				registroAtendimento.setDataEncerramento(Util.converteStringParaDate(encerrarOrdemServicoActionForm.getDataEncerramento()));
//				if(encerrarOrdemServicoActionForm.getObservacaoEncerramento() == null
//								|| encerrarOrdemServicoActionForm.getObservacaoEncerramento().equals("")){
//					registroAtendimento.setObservacao("ENCERRADO ATRAV�S DA FUNCIONALIDADE ENCERRAMENTO DA ORDEM DE SERVI�O");
//				}else{
//					registroAtendimento.setObservacao(encerrarOrdemServicoActionForm.getObservacaoEncerramento());
//				}
//				registroAtendimento.setUltimaAlteracao(new Date());
//
//				// cria o objeto para a inser��o do registro de atendimento unidade
//				RegistroAtendimentoUnidade registroAtendimentoUnidade = new RegistroAtendimentoUnidade();
//				registroAtendimentoUnidade.setRegistroAtendimento(registroAtendimento);
//
//				if(usuarioLogado.getUnidadeOrganizacional() != null && !usuarioLogado.getUnidadeOrganizacional().equals("")){
//					registroAtendimentoUnidade.setUnidadeOrganizacional(usuarioLogado.getUnidadeOrganizacional());
//				}
//				registroAtendimentoUnidade.setUsuario(usuarioLogado);
//
//				// atendimento rela��o tipo
//				AtendimentoRelacaoTipo atendimentoRelacaoTipo = new AtendimentoRelacaoTipo();
//				atendimentoRelacaoTipo.setId(AtendimentoRelacaoTipo.ENCERRAR);
//				registroAtendimentoUnidade.setAtendimentoRelacaoTipo(atendimentoRelacaoTipo);
//				registroAtendimentoUnidade.setUltimaAlteracao(new Date());
//				fachada.encerrarRegistroAtendimento(registroAtendimento, registroAtendimentoUnidade, usuarioLogado);
//			}
//		}
//
//		sessao.getAttribute("retornoTela");
//		sessao.removeAttribute("ocorrenciaInfracao");
//		sessao.removeAttribute("colecaoInfracaoTipo");
//		sessao.removeAttribute("closePage");
//		sessao.removeAttribute("isPopup");
//		sessao.removeAttribute("colecaoVala");
//
//		// volta para tela e limpa o popup
//		httpServletRequest.setAttribute("fecharPopup", "SIM");
//
//		return retorno;
//	}
//
//	/**
//	 * Verifica se a origem do encerramento � 'Acompanhamento de Roteiro'.
//	 * 
//	 * @param origem
//	 *            - OrigemEncerramentoOrdemServico
//	 * @return resultado
//	 */
//	private Boolean isOrigemRoteiro(OrigemEncerramentoOrdemServico origem){
//
//		Boolean retorno = false;
//
//		if(origem != null && OrigemEncerramentoOrdemServico.ACOMPANHAMENTO_ROTEIRO.compareTo(origem) == 0){
//			retorno = true;
//		}
//
//		return retorno;
//	}
// }