/*
 * Copyright (C) 2007-2007 the GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
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
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
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

import gcom.atendimentopublico.bean.IntegracaoComercialHelper;
import gcom.atendimentopublico.ordemservico.*;
import gcom.atendimentopublico.ordemservico.bean.DadosAtividadesOrdemServicoHelper;
import gcom.atendimentopublico.ordemservico.bean.ManterDadosAtividadesOrdemServicoHelper;
import gcom.atendimentopublico.ordemservico.bean.OSEncerramentoHelper;
import gcom.atendimentopublico.ordemservico.bean.ServicoAssociadoAutorizacaoHelper;
import gcom.atendimentopublico.registroatendimento.*;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.relatorio.atendimentopublico.ordemservico.RelatorioParecerEncerramentoOSHelper;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.FachadaException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action que define o pré-processamento da página de encerra OS caso precise
 * entrar na tela
 * 
 * @author Sávio Luiz
 * @created 18/09/2006
 */
public class EncerrarOrdemServicoAction
				extends GcomAction {

	private static final String ATRIBUTO_AUTORIZAR_SERVICO_ASSOCIADO_HELPER = "autorizarServicoAssociadoHelper";

	private static final String CAMINHO_ENCERRAR_ORDEM_SERVICO = "encerrarOrdemServicoAction";

	private static final String CAMINHO_RETORNO_CONSULTAR_OS = "filtrarOrdemServicoAction";

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
		ActionForward retorno = actionMapping.findForward("telaSucesso");
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
		EncerrarOrdemServicoActionForm encerrarOrdemServicoActionForm = (EncerrarOrdemServicoActionForm) actionForm;
		OSEncerramentoHelper osEncerramentoHelper = new OSEncerramentoHelper();
		DadosAtividadesOrdemServicoHelper dadosAtividadesHelper = null;
		OrdemServico ordemServicoEncerrar = null;

		if(sessao.getAttribute("ordemServicoEncerrar") != null){

			ordemServicoEncerrar = (OrdemServico) sessao.getAttribute("ordemServicoEncerrar");
		}

		if(httpServletRequest.getParameter("submitAutomatico3") != null
						&& httpServletRequest.getParameter("submitAutomatico3").equals("ok")){
			httpServletRequest.setAttribute("submitAutomatico3", "ok");
		}

		/*
		 * Verifica se será necessário processar o [UC0461] Manter Dados das Atividades da Ordem
		 * de Serviço
		 */
		if(ordemServicoEncerrar != null){

			AtendimentoMotivoEncerramento atendimentoMotivoEncerramento = null;
			if(!Util.isVazioOuBranco(encerrarOrdemServicoActionForm.getIdMotivoEncerramento())){

				FiltroAtendimentoMotivoEncerramento filtroAtendimentoMotivoEncerramento = new FiltroAtendimentoMotivoEncerramento();
				filtroAtendimentoMotivoEncerramento.adicionarParametro(new ParametroSimples(FiltroAtendimentoMotivoEncerramento.ID, Util
								.obterInteger(encerrarOrdemServicoActionForm.getIdMotivoEncerramento())));

				Collection<AtendimentoMotivoEncerramento> colecaoAtendimentoMotivoEncerramento = fachada.pesquisar(
								filtroAtendimentoMotivoEncerramento, AtendimentoMotivoEncerramento.class.getName());

				if(!Util.isVazioOrNulo(colecaoAtendimentoMotivoEncerramento)){

					atendimentoMotivoEncerramento = (AtendimentoMotivoEncerramento) Util
									.retonarObjetoDeColecao(colecaoAtendimentoMotivoEncerramento);
				}
			}

			if(atendimentoMotivoEncerramento.getId() != null
							&& atendimentoMotivoEncerramento.getId().equals(
											Util.converterStringParaInteger(encerrarOrdemServicoActionForm.getIdMotivoEncerramento()))){

				// Caso o indicador de execução seja igual a sim(1)
				if(atendimentoMotivoEncerramento.getIndicadorExecucao() == AtendimentoMotivoEncerramento.INDICADOR_EXECUCAO_SIM){

					if((sessao.getAttribute("colecaoManterDadosAtividadesOrdemServicoHelper") != null
									|| sessao.getAttribute("colecaoInterrupcaoDeslocamento") != null || sessao.getAttribute("colecaoVala") != null)
									&& sessao.getAttribute("realizandoAcompanhamentoProgramacao") != null
									&& !Util.isVazioOuBranco(encerrarOrdemServicoActionForm.getDataRoteiro())){

						try{

							// [UC0461] Manter Dados das Atividades da Ordem de Serviço
							dadosAtividadesHelper = this.montarHelperDadosAtividades(encerrarOrdemServicoActionForm,
											new DadosAtividadesOrdemServicoHelper(), ordemServicoEncerrar, sessao, fachada);
						}catch(ActionServletException e){

							String[] parametros = new String[e.getParametroMensagem().size()];
							e.getParametroMensagem().toArray(parametros);
							ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
							ex.setUrlBotaoVoltar("/gsan/exibirEncerrarOrdemServicoAction.do?carregarCampos="
											+ "&processoAutorizacaoServicosAssociados=&idServicoTipo=");
							throw ex;

						}
					}else{

						FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
						filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, ordemServicoEncerrar
										.getServicoTipo().getId()));

						Collection<ServicoTipo> colecaoServicoTipo = fachada.pesquisar(filtroServicoTipo, ServicoTipo.class.getName());

						if(!Util.isVazioOrNulo(colecaoServicoTipo)){

							ServicoTipo servicoTipo = (ServicoTipo) Util.retonarObjetoDeColecao(colecaoServicoTipo);

							String acaoObrigatoriaProgramacao = "";
							if(servicoTipo.getIndicadorDeslocamento() == ConstantesSistema.SIM.intValue()){

								acaoObrigatoriaProgramacao = "Deslocamento";
							}else if(servicoTipo.getIndicadorCausaOcorrencia() == ConstantesSistema.SIM.intValue()){

								acaoObrigatoriaProgramacao = "Causa Ocorrência";
							}else if(servicoTipo.getIndicadorHorariosExecucao() == ConstantesSistema.SIM.intValue()){

								acaoObrigatoriaProgramacao = "Horários Execução";
							}else if(servicoTipo.getIndicadorMaterial() == ConstantesSistema.SIM.intValue()){

								acaoObrigatoriaProgramacao = "Material";
							}else if(servicoTipo.getIndicadorPavimento() == ConstantesSistema.SIM.intValue()){

								acaoObrigatoriaProgramacao = "Pavimento";
							}else if(servicoTipo.getIndicadorRedeRamalAgua() == ConstantesSistema.SIM.intValue()){

								acaoObrigatoriaProgramacao = "Rede/Ramal de Água";
							}else if(servicoTipo.getIndicadorRedeRamalEsgoto() == ConstantesSistema.SIM.intValue()){

								acaoObrigatoriaProgramacao = "Rede/Ramal de Esgoto";
							}

							if(!Util.isVazioOuBranco(acaoObrigatoriaProgramacao)){

								ActionServletException ex = new ActionServletException("atencao.ordem.servico.programacao",
												new String[] {ordemServicoEncerrar.getId().toString(), acaoObrigatoriaProgramacao});
								ex.setUrlBotaoVoltar("/gsan/exibirEncerrarOrdemServicoAction.do?carregarCampos="
												+ "&processoAutorizacaoServicosAssociados=&idServicoTipo=");

								throw ex;
							}
						}
					}
				}
			}

		}

		OcorrenciaInfracao ocorrenciaInfracao = null;
		Integer[] colecaoInfracaoTipo = null;

		if(sessao.getAttribute("ocorrenciaInfracao") != null && sessao.getAttribute("colecaoInfracaoTipo") != null){
			ocorrenciaInfracao = (OcorrenciaInfracao) sessao.getAttribute("ocorrenciaInfracao");
			colecaoInfracaoTipo = (Integer[]) sessao.getAttribute("colecaoInfracaoTipo");

			osEncerramentoHelper.setOcorrenciaInfracao(ocorrenciaInfracao);
			osEncerramentoHelper.setColecaoInfracaoTipo(colecaoInfracaoTipo);

			sessao.removeAttribute("closePage");
			sessao.removeAttribute("isPopup");

		}

		String valorConfirmacao = httpServletRequest.getParameter("confirmado");
		String valorEncerramentoRA = httpServletRequest.getParameter("valorEncerramentoRA");
		Integer idOSDiagnostico = null;

		AutorizarServicoAssociadoHelper autorizarServicoAssociadoHelper = null;

		Map<Integer, ServicoAssociadoAutorizacaoHelper> mapServicosAutorizados = (Map<Integer, ServicoAssociadoAutorizacaoHelper>) httpServletRequest
						.getAttribute("mapServicosAutorizados");

		if(mapServicosAutorizados == null || mapServicosAutorizados.isEmpty()){

			String indicadorFormaEncerramento = "";

			// parte da integração com o sistema comercial
			IntegracaoComercialHelper integracaoComercialHelper = (IntegracaoComercialHelper) sessao
							.getAttribute("integracaoComercialHelper");

			if(encerrarOrdemServicoActionForm.getIndicadorPavimento() != null
							&& !("").equals(encerrarOrdemServicoActionForm.getIndicadorPavimento())
							&& Short.valueOf(encerrarOrdemServicoActionForm.getIndicadorPavimento()) == ServicoTipo.INDICADOR_PAVIMENTO_SIM
							&& ("2").equals(encerrarOrdemServicoActionForm.getIdMotivoEncerramento())){
				if(encerrarOrdemServicoActionForm.getDimensao1() == null || ("").equals(encerrarOrdemServicoActionForm.getDimensao1())){
					throw new ActionServletException("atencao.campo_texto.obrigatorio", null, "Pavimento Dimensão 1");
				}else{
					if(encerrarOrdemServicoActionForm.getDimensao2() == null || ("").equals(encerrarOrdemServicoActionForm.getDimensao2())){
						throw new ActionServletException("atencao.campo_texto.obrigatorio", null, "Pavimento Dimensão 2");
					}else{
						encerrarOrdemServicoActionForm.setPavimento(String.valueOf(Double.valueOf(encerrarOrdemServicoActionForm
										.getDimensao1().replaceAll(",", "."))
										* Double.valueOf(encerrarOrdemServicoActionForm.getDimensao2().replaceAll(",", "."))));
					}
				}
			}

			/*
			 * Colocado por Raphael Rossiter em 09/05/2007
			 * Caso o id do motivo de não cobrança venha com o valor -1 não será gerado o objeto
			 * ServicoNaoCobrancaMotivo dentro da OS
			 */
			if(integracaoComercialHelper != null
							&& integracaoComercialHelper.getOrdemServico() != null
							&& integracaoComercialHelper.getOrdemServico().getServicoNaoCobrancaMotivo() != null
							&& integracaoComercialHelper.getOrdemServico().getServicoNaoCobrancaMotivo().getId().equals(
											ConstantesSistema.NUMERO_NAO_INFORMADO)){

				integracaoComercialHelper.getOrdemServico().setServicoNaoCobrancaMotivo(null);
			}

			if(valorConfirmacao == null || valorConfirmacao.equals("")){

				// valida enter
				String idServicoTipo = encerrarOrdemServicoActionForm.getIdServicoTipo();

				if((idServicoTipo != null && !idServicoTipo.equals("")) /*
																		 * && (descricaoServicoTipo
																		 * == null ||
																		 * descricaoServicoTipo
																		 * .equals(""))
																		 */){

					FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();

					filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, idServicoTipo));

					Collection<ServicoTipo> servicoTipoEncontrado = fachada.pesquisar(filtroServicoTipo, ServicoTipo.class.getName());

					if(servicoTipoEncontrado == null || servicoTipoEncontrado.isEmpty()){
						throw new ActionServletException("atencao.label_inexistente", null, "Serviço Tipo");
					}

				}
				// caso seja a primeira vez
				if(integracaoComercialHelper == null || integracaoComercialHelper.equals("")){

					// [FS0002] - Validar Tipo Serviço
					// [FS0004] - Verificar preenchimento dos campos
					// [FS0007] - Validar Data de Encerramento
					// [FS0008] - Validar Data do roteiro
					try{
						fachada.validarCamposEncerrarOS(encerrarOrdemServicoActionForm.getIndicadorExecucao(),
										encerrarOrdemServicoActionForm.getNumeroOS(), encerrarOrdemServicoActionForm
														.getIdMotivoEncerramento(), encerrarOrdemServicoActionForm.getDataEncerramento(),
										encerrarOrdemServicoActionForm.getTipoServicoReferenciaId(), encerrarOrdemServicoActionForm
														.getIndicadorPavimento(), encerrarOrdemServicoActionForm.getPavimento(),
										encerrarOrdemServicoActionForm.getIdTipoRetornoReferida(), encerrarOrdemServicoActionForm
														.getIndicadorDeferimento(), encerrarOrdemServicoActionForm
														.getIndicadorTrocaServico(), idServicoTipo, encerrarOrdemServicoActionForm
														.getDataRoteiro(), encerrarOrdemServicoActionForm.getNumeroRA(),
										encerrarOrdemServicoActionForm.getIndicadorVistoriaServicoTipo(), encerrarOrdemServicoActionForm
														.getCodigoRetornoVistoriaOs(),
										encerrarOrdemServicoActionForm.getHoraEncerramento(), usuarioLogado,
										encerrarOrdemServicoActionForm.getIndicadorAfericaoServicoTipo(),
										encerrarOrdemServicoActionForm.getIdHidrometroCondicao(),
										encerrarOrdemServicoActionForm.getIndicadorResultado(),
										encerrarOrdemServicoActionForm.getIdFuncionario(),
										encerrarOrdemServicoActionForm.getIndicadorClienteAcompanhou());
					}catch(FachadaException e){
						String[] parametros = new String[e.getParametroMensagem().size()];
						e.getParametroMensagem().toArray(parametros);
						ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
						ex.setUrlBotaoVoltar("/gsan/exibirEncerrarOrdemServicoAction.do?carregarCampos="
										+ "&processoAutorizacaoServicosAssociados=&idServicoTipo=");
						throw ex;

					}
				}

				encerrarOrdemServicoActionForm.setNumeroOSOriginal(encerrarOrdemServicoActionForm.getNumeroOS());
				// indicador execução seja diferente de nulo
				if(encerrarOrdemServicoActionForm.getIndicadorExecucao() != null
								&& !encerrarOrdemServicoActionForm.getIndicadorExecucao().equals("")){
					Integer indicadorExecucao = Integer.parseInt(encerrarOrdemServicoActionForm.getIndicadorExecucao());
					Integer numeroOS = Util.converterStringParaInteger(encerrarOrdemServicoActionForm.getNumeroOS());
					Date dataEncerramento = null;
					if(encerrarOrdemServicoActionForm.getHoraEncerramento() == null
									|| encerrarOrdemServicoActionForm.getHoraEncerramento().equals("")){
						dataEncerramento = new Date();
						dataEncerramento = Util.converteStringParaDate(encerrarOrdemServicoActionForm.getDataEncerramento());
					}else{
						dataEncerramento = new Date();
						dataEncerramento = Util.converteStringParaDateHora(encerrarOrdemServicoActionForm.getDataEncerramento() + " "
										+ encerrarOrdemServicoActionForm.getHoraEncerramento() + ":00");
					}
					Date dataUltimaAlteracao = encerrarOrdemServicoActionForm.getUltimaAlteracao();

					// indicador execução seja igual a não(2)
					if(indicadorExecucao == AtendimentoMotivoEncerramento.INDICADOR_EXECUCAO_NAO){

						// OrdemServico osFiscalizacao = (OrdemServico)
						// sessao.getAttribute("ordemServico");
						OrdemServico osFiscalizacao = (OrdemServico) sessao.getAttribute("osFiscalizacao");

						// Verificação necessário, pois a ordem de serviço de fiscalização é
						// colocada na sessão quando o popup é aberto. Nesse momento o usuário pode
						// não querer gerar e fechar o popup. Isso faz com que a ordem de servviço
						// fique sem tipo de serviço
						if(osFiscalizacao != null){
							ServicoTipo servicoTipo = osFiscalizacao.getServicoTipo();

							if(servicoTipo == null){
								osFiscalizacao = null;
							}
						}

						osEncerramentoHelper.setNumeroOS(numeroOS);
						osEncerramentoHelper.setDataExecucao(dataEncerramento);
						osEncerramentoHelper.setUsuarioLogado(usuarioLogado);
						osEncerramentoHelper.setIdMotivoEncerramento(encerrarOrdemServicoActionForm.getIdMotivoEncerramento());
						osEncerramentoHelper.setUltimaAlteracao(dataUltimaAlteracao);
						osEncerramentoHelper.setParecerEncerramento(encerrarOrdemServicoActionForm.getObservacaoEncerramento());
						osEncerramentoHelper.setOsFiscalizacao(osFiscalizacao);
						osEncerramentoHelper.setIndicadorVistoriaServicoTipo(encerrarOrdemServicoActionForm
										.getIndicadorVistoriaServicoTipo());
						osEncerramentoHelper.setCodigoRetornoVistoriaOs(encerrarOrdemServicoActionForm.getCodigoRetornoVistoriaOs());

						// [SB0001] - Encerrar sem execução
						try{
							fachada.encerrarOSSemExecucao(osEncerramentoHelper, null,
											OrigemEncerramentoOrdemServico.ENCERRAMENTO_ORDEM_SERVICO, dadosAtividadesHelper);

						}catch(FachadaException e){
							String[] parametros = new String[e.getParametroMensagem().size()];
							e.getParametroMensagem().toArray(parametros);
							ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
							ex.setUrlBotaoVoltar("/gsan/exibirEncerrarOrdemServicoAction.do?carregarCampos="
											+ "&processoAutorizacaoServicosAssociados=&idServicoTipo=");
							throw ex;

						}

					}else{
						// indicador execução seja igual a sim(1)
						if(indicadorExecucao == AtendimentoMotivoEncerramento.INDICADOR_EXECUCAO_SIM){
							if(encerrarOrdemServicoActionForm.getIndicadorAtualizaComercial() != null
											&& !encerrarOrdemServicoActionForm.getIndicadorAtualizaComercial().equals("")){
								Short indicadorComercialAtualiza = Short.valueOf(encerrarOrdemServicoActionForm
												.getIndicadorAtualizaComercial());

								if(indicadorComercialAtualiza.equals(ServicoTipo.INDICADOR_ATUALIZA_COMERCIAL_SIM)){

									// caso não exista o objeto helper na sessão então é a primeira
									// vez
									if(integracaoComercialHelper == null || integracaoComercialHelper.equals("")){

										// caso exista tipo de serviço
										if(encerrarOrdemServicoActionForm.getTipoServicoOSId() != null
														&& !encerrarOrdemServicoActionForm.getTipoServicoOSId().equals("")){

											FiltroServicoTipoOperacao filtroServicoTipoOperacao = new FiltroServicoTipoOperacao();
											filtroServicoTipoOperacao.adicionarCaminhoParaCarregamentoEntidade("operacao");
											filtroServicoTipoOperacao.adicionarParametro(new ParametroSimples(
															FiltroServicoTipoOperacao.ID_SERVICO_TIPO, encerrarOrdemServicoActionForm
																			.getTipoServicoOSId()));
											Collection<ServicoTipoOperacao> colecaoServicoTipoOperacao = fachada.pesquisar(
															filtroServicoTipoOperacao, ServicoTipoOperacao.class.getName());

											// caso exista uma funcionalidade associada ao serviço
											// tipo
											if(colecaoServicoTipoOperacao != null && !colecaoServicoTipoOperacao.isEmpty()){
												ServicoTipoOperacao servicoTipoOperacao = (ServicoTipoOperacao) Util
																.retonarObjetoDeColecao(colecaoServicoTipoOperacao);
												String caminhoOperacao = servicoTipoOperacao.getOperacao().getCaminhoUrl();

												// caso exista o caminho da operação
												if(caminhoOperacao != null && !caminhoOperacao.equals("")){
													int tamanhoOperacao = caminhoOperacao.length();

													// seta o caminho no mapeamento para ser chamado
													String caminhoRetorno = caminhoOperacao.substring(0, tamanhoOperacao - 3);
													httpServletRequest.setAttribute("veioEncerrarOS", encerrarOrdemServicoActionForm
																	.getNumeroOS());
													httpServletRequest.setAttribute("dataEncerramento", encerrarOrdemServicoActionForm
																	.getDataEncerramento());
													httpServletRequest.setAttribute("caminhoRetornoIntegracaoComercial",
																	"exibirEncerrarOrdemServicoAction.do?retornoConsulta=1");
													retorno = actionMapping.findForward(caminhoRetorno);
													if(retorno == null){
														throw new ActionServletException("atencao.caminho_url_indisponivel");
													}else{

														// caso seja chamado a integração então seta
														// a OS na sessão com outro
														// nome e remove a OS da sessão visto que a
														// integração usa o mesmo
														// nome da OS que está na sessão
														sessao.setAttribute("osFiscalizacao", sessao.getAttribute("ordemServico"));
														sessao.removeAttribute("ordemServico");
													}
												}
											}
										}
									}
								}
							}
							if(retorno.getName().equalsIgnoreCase("telaSucesso")){

								OrdemServico osFiscalizacao = null;

								// if (integracaoComercialHelper == null ||
								// integracaoComercialHelper.equals("")) {
								// osFiscalizacao = (OrdemServico)
								// sessao.getAttribute("ordemServico");
								// }

								// caso a os fiscalização não esteja na sessão então não foi para
								// integração e então pode pegar o OS mesmo
								if(sessao.getAttribute("osFiscalizacao") != null && !sessao.getAttribute("osFiscalizacao").equals("")){
									osFiscalizacao = (OrdemServico) sessao.getAttribute("osFiscalizacao");
								}

								OrdemServico ordemServico = fachada.consultarDadosOrdemServico(numeroOS);
								ServicoTipo servicoTipo = ordemServico.getServicoTipo();
								List<ServicoAssociadoAutorizacaoHelper> servicoAssociadoAutorizacaoHelperList;
								try{
									OrigemEncerramentoOrdemServico origemEncerramento = null;

									if(sessao.getAttribute("osProgramacao") != null){
										origemEncerramento = OrigemEncerramentoOrdemServico.ACOMPANHAMENTO_ROTEIRO;
									}else{
										origemEncerramento = OrigemEncerramentoOrdemServico.ENCERRAMENTO_ORDEM_SERVICO;
									}

									servicoAssociadoAutorizacaoHelperList = fachada.verificarServicosAssociadosParaAutorizacao(servicoTipo,
													EventoGeracaoServico.ENCERRAMENTO_ORDEM_SERVICO, origemEncerramento, numeroOS);

								}catch(FachadaException e){
									String[] parametros = new String[e.getParametroMensagem().size()];
									e.getParametroMensagem().toArray(parametros);
									ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
									ex.setUrlBotaoVoltar("/gsan/exibirEncerrarOrdemServicoAction.do?carregarCampos="
													+ "&processoAutorizacaoServicosAssociados=&idServicoTipo=");
									throw ex;

								}

								// se o serviço tipo referencia seja igual a nulo
								if(encerrarOrdemServicoActionForm.getTipoServicoReferenciaId() == null
												|| encerrarOrdemServicoActionForm.getTipoServicoReferenciaId().equals("")){

									osEncerramentoHelper.setNumeroOS(numeroOS);
									osEncerramentoHelper.setDataExecucao(dataEncerramento);
									osEncerramentoHelper.setUsuarioLogado(usuarioLogado);
									osEncerramentoHelper.setIdMotivoEncerramento(encerrarOrdemServicoActionForm.getIdMotivoEncerramento());
									osEncerramentoHelper.setUltimaAlteracao(dataUltimaAlteracao);
									osEncerramentoHelper.setParecerEncerramento(encerrarOrdemServicoActionForm.getObservacaoEncerramento());
									osEncerramentoHelper.setIndicadorPavimento(encerrarOrdemServicoActionForm.getIndicadorPavimento());
									osEncerramentoHelper.setPavimento(encerrarOrdemServicoActionForm.getPavimento());
									osEncerramentoHelper.setIntegracaoComercialHelper(integracaoComercialHelper);
									osEncerramentoHelper.setTipoServicoOSId(encerrarOrdemServicoActionForm.getTipoServicoOSId());
									osEncerramentoHelper.setOsFiscalizacao(osFiscalizacao);
									osEncerramentoHelper.setIndicadorVistoriaServicoTipo(encerrarOrdemServicoActionForm
													.getIndicadorVistoriaServicoTipo());
									osEncerramentoHelper.setCodigoRetornoVistoriaOs(encerrarOrdemServicoActionForm
													.getCodigoRetornoVistoriaOs());
									osEncerramentoHelper.setDimensao1(encerrarOrdemServicoActionForm.getDimensao1());
									osEncerramentoHelper.setDimensao2(encerrarOrdemServicoActionForm.getDimensao2());
									osEncerramentoHelper.setDimensao3(encerrarOrdemServicoActionForm.getDimensao3());
									if(encerrarOrdemServicoActionForm.getIndicadorCobraHorasMateriais() != null){
										osEncerramentoHelper.setIndicadorCobrarHorasMateriais(encerrarOrdemServicoActionForm
														.getIndicadorCobraHorasMateriais());
									}else{
										osEncerramentoHelper.setIndicadorCobrarHorasMateriais("2");
									}

									indicadorFormaEncerramento = "encerrarOSComExecucaoSemReferencia";

								}else{

									osEncerramentoHelper.setNumeroOS(numeroOS);
									osEncerramentoHelper.setDataExecucao(dataEncerramento);
									osEncerramentoHelper.setUsuarioLogado(usuarioLogado);
									osEncerramentoHelper.setIdMotivoEncerramento(encerrarOrdemServicoActionForm.getIdMotivoEncerramento());
									osEncerramentoHelper.setUltimaAlteracao(dataUltimaAlteracao);
									osEncerramentoHelper.setParecerEncerramento(encerrarOrdemServicoActionForm.getObservacaoEncerramento());
									osEncerramentoHelper.setIndicadorPavimento(encerrarOrdemServicoActionForm.getIndicadorPavimento());
									osEncerramentoHelper.setPavimento(encerrarOrdemServicoActionForm.getPavimento());
									osEncerramentoHelper.setIdTipoRetornoOSReferida(encerrarOrdemServicoActionForm
													.getIdTipoRetornoReferida());
									osEncerramentoHelper.setIndicadorDeferimento(encerrarOrdemServicoActionForm.getIndicadorDeferimento());
									osEncerramentoHelper.setIndicadorTrocaServicoTipo(encerrarOrdemServicoActionForm
													.getIndicadorTrocaServico());
									osEncerramentoHelper.setIdServicoTipo(encerrarOrdemServicoActionForm.getIdServicoTipo());
									osEncerramentoHelper.setIdOSReferencia(encerrarOrdemServicoActionForm.getNumeroOSReferencia());
									osEncerramentoHelper.setIdServicoTipoReferenciaOS(encerrarOrdemServicoActionForm
													.getServicoTipoReferenciaOS());
									osEncerramentoHelper.setIntegracaoComercialHelper(integracaoComercialHelper);
									osEncerramentoHelper.setTipoServicoOSId(encerrarOrdemServicoActionForm.getTipoServicoOSId());
									osEncerramentoHelper.setOsFiscalizacao(osFiscalizacao);
									osEncerramentoHelper.setIndicadorVistoriaServicoTipo(encerrarOrdemServicoActionForm
													.getIndicadorVistoriaServicoTipo());
									osEncerramentoHelper.setCodigoRetornoVistoriaOs(encerrarOrdemServicoActionForm
													.getCodigoRetornoVistoriaOs());
									osEncerramentoHelper.setDimensao1(encerrarOrdemServicoActionForm.getDimensao1());
									osEncerramentoHelper.setDimensao2(encerrarOrdemServicoActionForm.getDimensao2());
									osEncerramentoHelper.setDimensao3(encerrarOrdemServicoActionForm.getDimensao3());
									if(encerrarOrdemServicoActionForm.getIndicadorCobraHorasMateriais() != null){
										osEncerramentoHelper.setIndicadorCobrarHorasMateriais(encerrarOrdemServicoActionForm
														.getIndicadorCobraHorasMateriais());
									}else{
										osEncerramentoHelper.setIndicadorCobrarHorasMateriais("2");
									}

									indicadorFormaEncerramento = "encerrarOSComExecucaoComReferencia";

								}

								// Caso esteja indicado aferição de hidrômetro
								if(!Util.isVazioOuBranco(encerrarOrdemServicoActionForm.getIndicadorAfericaoServicoTipo())
												&& encerrarOrdemServicoActionForm.getIndicadorAfericaoServicoTipo().equals(
																ConstantesSistema.SIM.toString())){

									osEncerramentoHelper.setIndicadorAfericaoServicoTipo(ConstantesSistema.SIM);
									osEncerramentoHelper.setIdHidrometroCondicao(Util.obterInteger(encerrarOrdemServicoActionForm
													.getIdHidrometroCondicao()));
									osEncerramentoHelper.setIndicadorResultado(Util.obterShort(encerrarOrdemServicoActionForm
													.getIndicadorResultado()));
									osEncerramentoHelper.setIdFuncionario(Util.obterInteger(encerrarOrdemServicoActionForm
													.getIdFuncionario()));
									osEncerramentoHelper.setIndicadorClienteAcompanhou(Util.obterShort(encerrarOrdemServicoActionForm
													.getIndicadorClienteAcompanhou()));
								}

								if(servicoAssociadoAutorizacaoHelperList != null && !servicoAssociadoAutorizacaoHelperList.isEmpty()){

									// Parametros obrigatorios para a tela de autorização
									autorizarServicoAssociadoHelper = new AutorizarServicoAssociadoHelper();

									Map<String, Object> parametrosCaminhoAutorizacao = new HashMap<String, Object>();
									parametrosCaminhoAutorizacao.put("indicadorFormaEncerramento", indicadorFormaEncerramento);
									parametrosCaminhoAutorizacao.put("osEncerramentoHelper", osEncerramentoHelper);
									autorizarServicoAssociadoHelper.setParametrosCaminhoAutorizacao(parametrosCaminhoAutorizacao);
									autorizarServicoAssociadoHelper.setCaminhoAutorizacao(CAMINHO_ENCERRAR_ORDEM_SERVICO);

									Map<String, Object> parametrosCaminhoRetorno = new HashMap<String, Object>();
									parametrosCaminhoRetorno.put("numeroOS", numeroOS);
									autorizarServicoAssociadoHelper.setParametrosCaminhoRetorno(parametrosCaminhoRetorno);
									autorizarServicoAssociadoHelper.setCaminhoRetorno(CAMINHO_RETORNO_CONSULTAR_OS);

									autorizarServicoAssociadoHelper.setServicosParaAutorizacao(servicoAssociadoAutorizacaoHelperList);

									sessao.setAttribute(ATRIBUTO_AUTORIZAR_SERVICO_ASSOCIADO_HELPER, autorizarServicoAssociadoHelper);

									retorno = actionMapping.findForward("mostrarAutorizarServicoAssociado");

									return retorno;

								}else{

									if(indicadorFormaEncerramento.equals("encerrarOSComExecucaoSemReferencia")){
										// [SB0002] - Encerrar com execução e sem referência
										try{
											fachada.encerrarOSComExecucaoSemReferencia(osEncerramentoHelper, null,
															OrigemEncerramentoOrdemServico.ENCERRAMENTO_ORDEM_SERVICO,
															dadosAtividadesHelper);
										}catch(FachadaException e){
											String[] parametros = new String[e.getParametroMensagem().size()];
											e.getParametroMensagem().toArray(parametros);
											ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
											ex.setUrlBotaoVoltar("/gsan/exibirEncerrarOrdemServicoAction.do?carregarCampos="
															+ "&processoAutorizacaoServicosAssociados=&idServicoTipo=");
											throw ex;

										}
									}else if(indicadorFormaEncerramento.equals("encerrarOSComExecucaoComReferencia")){
										// [SB0003] - Encerrar com execução e com referência
										try{
											idOSDiagnostico = fachada.encerrarOSComExecucaoComReferencia(osEncerramentoHelper, null,
															OrigemEncerramentoOrdemServico.ENCERRAMENTO_ORDEM_SERVICO,
															dadosAtividadesHelper);
										}catch(FachadaException e){
											String[] parametros = new String[e.getParametroMensagem().size()];
											e.getParametroMensagem().toArray(parametros);
											ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
											ex.setUrlBotaoVoltar("/gsan/exibirEncerrarOrdemServicoAction.do?carregarCampos="
															+ "&processoAutorizacaoServicosAssociados=&idServicoTipo=");
											throw ex;

										}
									}

								}
							}
						}
					}
				}

				if(retorno.getName().equalsIgnoreCase("telaSucesso")){
					boolean telaConfirmacao;
					try{
						telaConfirmacao = fachada.tramitarOuEncerrarRADaOSEncerrada(Util
										.converterStringParaInteger(encerrarOrdemServicoActionForm.getNumeroOS()), usuarioLogado,
										encerrarOrdemServicoActionForm.getIdMotivoEncerramento(), encerrarOrdemServicoActionForm
														.getNumeroRA(), encerrarOrdemServicoActionForm.getDataRoteiro());
					}catch(FachadaException e){
						String[] parametros = new String[e.getParametroMensagem().size()];
						e.getParametroMensagem().toArray(parametros);
						ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
						ex.setUrlBotaoVoltar("/gsan/exibirEncerrarOrdemServicoAction.do?carregarCampos="
										+ "&processoAutorizacaoServicosAssociados=&idServicoTipo=");
						throw ex;

					}

					// se for para ir para a tela de confirmação
					if(telaConfirmacao){
						httpServletRequest.setAttribute("caminhoActionConclusao", "/gsan/encerrarOrdemServicoAction.do");
						httpServletRequest.setAttribute("cancelamento", "TRUE");
						httpServletRequest.setAttribute("nomeBotao1", "Sim");
						httpServletRequest.setAttribute("nomeBotao2", "Não");
						httpServletRequest.setAttribute("urlBotaoVoltar", "/gsan/exibirEncerrarOrdemServicoAction.do");

						return montarPaginaConfirmacao("atencao.encerrar_RA_da_OS", httpServletRequest, actionMapping);
					}
				}
			}else{

				// se o usuário confirmar o encerramento da RA da OS que está sendo encerrada
				if(valorConfirmacao.equals("ok")){
					if(valorEncerramentoRA == null){
						return actionMapping.findForward("exibirParecerEncerrametnoRA");
					}

					RegistroAtendimento registroAtendimento = new RegistroAtendimento();
					registroAtendimento.setId(Util.converterStringParaInteger(encerrarOrdemServicoActionForm.getNumeroRA()));
					AtendimentoMotivoEncerramento atendimentoMotivoEncerramento = new AtendimentoMotivoEncerramento();
					atendimentoMotivoEncerramento.setId(Util.converterStringParaInteger(encerrarOrdemServicoActionForm
									.getIdMotivoEncerramento()));
					registroAtendimento.setAtendimentoMotivoEncerramento(atendimentoMotivoEncerramento);
					registroAtendimento.setCodigoSituacao(RegistroAtendimento.SITUACAO_ENCERRADO);
					registroAtendimento.setDataEncerramento(Util.converteStringParaDateHora(encerrarOrdemServicoActionForm
									.getDataEncerramento()
									+ " " + encerrarOrdemServicoActionForm.getHoraEncerramento() + ":00"));
					if(encerrarOrdemServicoActionForm.getObservacaoEncerramento() == null
									|| encerrarOrdemServicoActionForm.getObservacaoEncerramento().equals("")){
						registroAtendimento.setObservacao("ENCERRADO ATRAVÉS DA FUNCIONALIDADE ENCERRAMENTO DA ORDEM DE SERVIÇO");
					}else{
						registroAtendimento.setObservacao(encerrarOrdemServicoActionForm.getObservacaoEncerramento());
					}
					if(encerrarOrdemServicoActionForm.getTxParecerEncerramento() != null
									&& !encerrarOrdemServicoActionForm.getTxParecerEncerramento().equals("")){
						registroAtendimento.setParecerEncerramento(encerrarOrdemServicoActionForm.getTxParecerEncerramento());
					}

					registroAtendimento.setUltimaAlteracao(new Date());

					// cria o objeto para a inserção do registro de atendimento unidade
					RegistroAtendimentoUnidade registroAtendimentoUnidade = new RegistroAtendimentoUnidade();
					registroAtendimentoUnidade.setRegistroAtendimento(registroAtendimento);

					if(usuarioLogado.getUnidadeOrganizacional() != null && !usuarioLogado.getUnidadeOrganizacional().equals("")){
						registroAtendimentoUnidade.setUnidadeOrganizacional(usuarioLogado.getUnidadeOrganizacional());
					}
					registroAtendimentoUnidade.setUsuario(usuarioLogado);

					// atendimento relação tipo
					AtendimentoRelacaoTipo atendimentoRelacaoTipo = new AtendimentoRelacaoTipo();
					atendimentoRelacaoTipo.setId(AtendimentoRelacaoTipo.ENCERRAR);
					registroAtendimentoUnidade.setAtendimentoRelacaoTipo(atendimentoRelacaoTipo);
					registroAtendimentoUnidade.setUltimaAlteracao(new Date());
					try{
						fachada.encerrarRegistroAtendimento(registroAtendimento, registroAtendimentoUnidade, usuarioLogado);
					}catch(FachadaException e){
						String[] parametros = new String[e.getParametroMensagem().size()];
						e.getParametroMensagem().toArray(parametros);
						ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
						ex.setUrlBotaoVoltar("/gsan/exibirEncerrarOrdemServicoAction.do?carregarCampos="
										+ "&processoAutorizacaoServicosAssociados=&idServicoTipo=");
						throw ex;

					}
				}
			}

		}else{

			autorizarServicoAssociadoHelper = (AutorizarServicoAssociadoHelper) sessao
							.getAttribute(ATRIBUTO_AUTORIZAR_SERVICO_ASSOCIADO_HELPER);
			Map<String, Object> parametrosCaminhoAutorizacao = autorizarServicoAssociadoHelper.getParametrosCaminhoAutorizacao();
			String indicadorFormaEncerramento = (String) parametrosCaminhoAutorizacao.get("indicadorFormaEncerramento");
			OSEncerramentoHelper osEncerramentoHelper2 = (OSEncerramentoHelper) parametrosCaminhoAutorizacao.get("osEncerramentoHelper");

			if(ocorrenciaInfracao != null){
				osEncerramentoHelper2.setOcorrenciaInfracao(ocorrenciaInfracao);
				osEncerramentoHelper2.setColecaoInfracaoTipo(colecaoInfracaoTipo);
			}

			if(indicadorFormaEncerramento.equals("encerrarOSComExecucaoSemReferencia")){

				for(ServicoAssociadoAutorizacaoHelper servicoAssociadoAutorizacaoHelper : autorizarServicoAssociadoHelper
								.getServicosParaAutorizacao()){

					if(servicoAssociadoAutorizacaoHelper.getFormaEncerramentoHelper().isAutomatica()){

						OSEncerramentoHelper osEncerramentoHelperAutomatica = new OSEncerramentoHelper();
						osEncerramentoHelperAutomatica.setIdMotivoEncerramento(AtendimentoMotivoEncerramento.ENCERRAMENTO_AUTOMATICO
										.toString());
						osEncerramentoHelperAutomatica.setIdServicoTipo(servicoAssociadoAutorizacaoHelper.getIdServicoAssociado()
										.toString());
						osEncerramentoHelperAutomatica.setDataExecucao(osEncerramentoHelper2.getDataExecucao());

						mapServicosAutorizados.get(servicoAssociadoAutorizacaoHelper.getIdServicoAssociado()).setOSEncerramentoHelper(
										osEncerramentoHelperAutomatica);
					}
				}

				// [SB0002] - Encerrar com execução e sem referência
				try{
					fachada.encerrarOSComExecucaoSemReferencia(osEncerramentoHelper2, mapServicosAutorizados,
									OrigemEncerramentoOrdemServico.ENCERRAMENTO_ORDEM_SERVICO, dadosAtividadesHelper);
				}catch(FachadaException e){
					String[] parametros = new String[e.getParametroMensagem().size()];
					e.getParametroMensagem().toArray(parametros);
					ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
					ex.setUrlBotaoVoltar("/gsan/exibirEncerrarOrdemServicoAction.do?carregarCampos="
									+ "&processoAutorizacaoServicosAssociados=&idServicoTipo=");
					throw ex;

				}
			}else if(indicadorFormaEncerramento.equals("encerrarOSComExecucaoComReferencia")){

				// [SB0003] - Encerrar com execução e com referência
				try{
					idOSDiagnostico = fachada.encerrarOSComExecucaoComReferencia(osEncerramentoHelper2, mapServicosAutorizados,
									OrigemEncerramentoOrdemServico.ENCERRAMENTO_ORDEM_SERVICO, dadosAtividadesHelper);
				}catch(FachadaException e){
					String[] parametros = new String[e.getParametroMensagem().size()];
					e.getParametroMensagem().toArray(parametros);
					ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
					ex.setUrlBotaoVoltar("/gsan/exibirEncerrarOrdemServicoAction.do?carregarCampos="
									+ "&processoAutorizacaoServicosAssociados=&idServicoTipo=");
					throw ex;

				}
			}else{
				throw new ActionServletException("atencao.campo.invalido", null, "Indicador de Forma de Encerramento");
			}

		}

		RelatorioParecerEncerramentoOSHelper relatorioParecerEncerramentoOSHelper = new RelatorioParecerEncerramentoOSHelper();

		relatorioParecerEncerramentoOSHelper.setNumeroOS(encerrarOrdemServicoActionForm.getNumeroOS());
		relatorioParecerEncerramentoOSHelper.setSituacaoOS(encerrarOrdemServicoActionForm.getSituacaoOS());
		relatorioParecerEncerramentoOSHelper.setDataGeracao(encerrarOrdemServicoActionForm.getDataGeracao());
		relatorioParecerEncerramentoOSHelper.setTipoServicoOSId(encerrarOrdemServicoActionForm.getTipoServicoOSId());
		relatorioParecerEncerramentoOSHelper.setTipoServicoOSDescricao(encerrarOrdemServicoActionForm.getTipoServicoOSDescricao());
		relatorioParecerEncerramentoOSHelper.setObservacao(encerrarOrdemServicoActionForm.getObservacao());
		relatorioParecerEncerramentoOSHelper.setUnidadeGeracaoId(encerrarOrdemServicoActionForm.getUnidadeGeracaoId());
		relatorioParecerEncerramentoOSHelper.setUnidadeGeracaoDescricao(encerrarOrdemServicoActionForm.getUnidadeGeracaoDescricao());
		relatorioParecerEncerramentoOSHelper.setUsuarioGeracaoId(encerrarOrdemServicoActionForm.getUsuarioGeracaoId());
		relatorioParecerEncerramentoOSHelper.setUsuarioGeracaoNome(encerrarOrdemServicoActionForm.getUsuarioGeracaoNome());
		relatorioParecerEncerramentoOSHelper.setDataUltimaEmissao(encerrarOrdemServicoActionForm.getDataUltimaEmissao());
		relatorioParecerEncerramentoOSHelper.setDataEncerramento(encerrarOrdemServicoActionForm.getDataEncerramento());
		relatorioParecerEncerramentoOSHelper.setHoraEncerramento(encerrarOrdemServicoActionForm.getHoraEncerramento());
		relatorioParecerEncerramentoOSHelper.setObservacaoEncerramento(encerrarOrdemServicoActionForm.getObservacaoEncerramento());

		sessao.setAttribute("relatorioParecerEncerramentoOSHelper", relatorioParecerEncerramentoOSHelper);

		sessao.setAttribute("encerrarOrdemServicoActionForm", encerrarOrdemServicoActionForm);

		if(retorno.getName().equalsIgnoreCase("telaSucesso")){

			// montando página de sucesso
			if(idOSDiagnostico == null){

				if(sessao.getAttribute("realizandoAcompanhamentoProgramacao") != null){

					String retornoTela = "";
					if(!Util.isVazioOuBranco(sessao.getAttribute("retornoTela"))){
						retornoTela = sessao.getAttribute("retornoTela").toString();
					}

					montarPaginaSucesso(httpServletRequest, "Ordem de Serviço de código "
									+ encerrarOrdemServicoActionForm.getNumeroOSOriginal() + " encerrada com sucesso.", "Consultar OS",
									"/exibirConsultarDadosOrdemServicoAction.do?menu=sim&numeroOS="
													+ encerrarOrdemServicoActionForm.getNumeroOSOriginal(), "", "",
									"Acompanhamento de Programação de Ordens de Serviço", retornoTela,
									"Imprimir Parecer", "/gerarRelatorioParecerEncerramentoOSAction.do");

					// Limpando a sessão
					sessao.removeAttribute("dataRoteiro");
					sessao.removeAttribute("realizandoAcompanhamentoProgramacao");
					sessao.removeAttribute("colecaoManterDadosAtividadesOrdemServicoHelper");
					sessao.removeAttribute("colecaoInterrupcaoDeslocamento");
					sessao.removeAttribute("colecaoVala");
				}else{

					montarPaginaSucesso(httpServletRequest, "Ordem de Serviço de código "
									+ encerrarOrdemServicoActionForm.getNumeroOSOriginal() + " encerrada com sucesso.", "Consultar OS",
									"/exibirConsultarDadosOrdemServicoAction.do?menu=sim&numeroOS="
													+ encerrarOrdemServicoActionForm.getNumeroOSOriginal(),
									"/gerarRelatorioParecerEncerramentoOSAction.do", "Imprimir Parecer");
				}
			}else{

				if(sessao.getAttribute("realizandoAcompanhamentoProgramacao") != null){

					montarPaginaSucesso(httpServletRequest, "Ordem de Serviço de código "
									+ encerrarOrdemServicoActionForm.getNumeroOSOriginal() + " encerrada com sucesso.", "Consultar OS",
									"/exibirConsultarDadosOrdemServicoAction.do?menu=sim&numeroOS="
													+ encerrarOrdemServicoActionForm.getNumeroOSOriginal(), "", "",
									"Acompanhamento de Programação de Ordens de Serviço", sessao.getAttribute("retornoTela").toString(),
									"Imprimir Parecer", "/gerarRelatorioParecerEncerramentoOSAction.do");
					// Limpando a sessão
					sessao.removeAttribute("dataRoteiro");
					sessao.removeAttribute("realizandoAcompanhamentoProgramacao");
					sessao.removeAttribute("colecaoManterDadosAtividadesOrdemServicoHelper");
					sessao.removeAttribute("colecaoInterrupcaoDeslocamento");
					sessao.removeAttribute("colecaoVala");
				}else{

					String msg = "Ordem de Serviço de código " + encerrarOrdemServicoActionForm.getNumeroOSOriginal()
									+ " encerrada com sucesso. Ordem de Serviço de código " + idOSDiagnostico + " gerada com sucesso";

					montarPaginaSucesso(httpServletRequest, msg, "Consultar OS",
									"/exibirConsultarDadosOrdemServicoAction.do?menu=sim&numeroOS="
													+ encerrarOrdemServicoActionForm.getNumeroOSOriginal(),
									"/gerarRelatorioOrdemServicoAction.do?idsOS=" + encerrarOrdemServicoActionForm.getNumeroOSOriginal(),
									"Imprimir Ordem de Serviço Encerrada", "Imprimir Ordem de Serviço Gerada",
									"/gerarRelatorioOrdemServicoAction.do?idsOS=" + idOSDiagnostico, "Imprimir Parecer",
									"/gerarRelatorioParecerEncerramentoOSAction.do");
				}

			}
			sessao.removeAttribute("canceladoOSFiscalizacao");
			sessao.removeAttribute("integracaoComercialHelper");
			sessao.removeAttribute("ordemServico");
			sessao.removeAttribute("ocorrenciaInfracao");
			sessao.removeAttribute("colecaoInfracaoTipo");

		}

		return retorno;
	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Serviço
	 * Método responsável por recuperar os dados do form e montar o helper.
	 * 
	 * @date 28/11/2011
	 */
	private DadosAtividadesOrdemServicoHelper montarHelperDadosAtividades(EncerrarOrdemServicoActionForm form,
					DadosAtividadesOrdemServicoHelper dadosHelper, OrdemServico ordemServico, HttpSession sessao, Fachada fachada){

		dadosHelper.setOrdemServico(ordemServico);

		// Setando os dados operacionais no DadosAtividadesOrdemServicoHelper
		if(ordemServico.getServicoTipo().getIndicadorDeslocamento() != ConstantesSistema.INDICADOR_USO_DESATIVO.intValue()){

			String placaVeiculo = form.getPlacaVeiculo();
			String dataInicioDeslocamento = form.getDataInicioDeslocamento();
			String dataFimDeslocamento = form.getDataFimDeslocamento();
			String horaInicioDeslocamento = form.getHoraInicioDeslocamento();
			String horaFimDeslocamento = form.getHoraFimDeslocamento();

			String kmInicial = form.getKmInicial();
			String kmFinal = form.getKmFinal();

			Date ultimaAlteracao = new Date();
			OrdemServicoProgramacao osProgramacao = new OrdemServicoProgramacao();
			boolean inserirDeslocamento = true;

			if((!dataInicioDeslocamento.equals("") && !dataFimDeslocamento.equals(""))
							|| (ordemServico.getServicoTipo().getIndicadorDeslocamento() == ConstantesSistema.INDICADOR_USO_ATIVO
											.intValue())){

				if(Util.validarDiaMesAno(dataInicioDeslocamento) || Util.validarDiaMesAno(dataFimDeslocamento)){

					throw new ActionServletException("atencao.verificar_datas_deslocamento");
				}

				Util.validarIntervaloDatas(dataInicioDeslocamento, dataFimDeslocamento);
			}else if(ordemServico.getServicoTipo().getIndicadorDeslocamento() == 3){ // campos

				// opcionais
				// se informar 1, precisa informar tudo
				if((!dataInicioDeslocamento.equals("") || !dataFimDeslocamento.equals("") || !horaInicioDeslocamento.equals("")
								|| !horaFimDeslocamento.equals("") || !kmInicial.equals("") || !kmFinal.equals(""))
								&& (dataInicioDeslocamento.equals("") || dataFimDeslocamento.equals("")
												|| horaInicioDeslocamento.equals("") || horaFimDeslocamento.equals("")
												|| kmInicial.equals("") || kmFinal.equals(""))){

					throw new ActionServletException("atencao.demais_campos_necessarios_deslocamento");
				}else{

					if(dataInicioDeslocamento.equals("") || dataFimDeslocamento.equals("") || horaInicioDeslocamento.equals("")
									|| horaFimDeslocamento.equals("") || kmInicial.equals("") || kmFinal.equals("")){

						inserirDeslocamento = false;
					}
				}
			}

			String dataHoraInicioDeslocamento = dataInicioDeslocamento + " " + horaInicioDeslocamento;
			String dataHoraFimDeslocamento = dataFimDeslocamento + " " + horaFimDeslocamento;

			// Precisa pegar a unidade do usuario do login que esta na sessao
			Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
			Integer idUnidadeLotacao = usuario.getUnidadeOrganizacional().getId();

			if(!Util.isVazioOuBranco(form.getDataRoteiro())){

				// Consulta programacaoRoteiro
				Date dataRoteiro = Util.converteStringParaDate(form.getDataRoteiro(), "dd/MM/yyyy");
				ProgramacaoRoteiro programacaoRoteiro = this.consultarProgramacaoRoteiro(dataRoteiro, idUnidadeLotacao);

				Collection<Equipe> colecaoEquipeProgramacao = fachada.recuperaEquipeDaOSProgramacaoPorDataRoteiro(ordemServico.getId(),
								dataRoteiro);

				if(!Util.isVazioOrNulo(colecaoEquipeProgramacao) && colecaoEquipeProgramacao.size() > 1){

					throw new ActionServletException("atencao.ordem_servico_programada_varias_equipes");
				}

				Equipe equipeAtual = (Equipe) Util.retonarObjetoDeColecao(colecaoEquipeProgramacao);

				// Buscar a OrdemServicoProgramacao
				if(equipeAtual != null && programacaoRoteiro != null){

					osProgramacao = fachada.pesquisarOrdemServicoProgramacao(programacaoRoteiro.getId(), ordemServico.getId(), equipeAtual
									.getId());
				}

			}else{

				throw new ActionServletException("atencao.erro_ao_recuperar_programacao_roteiro");
			}

			// Recuperando dados do deslocamento
			OrdemServicoDeslocamento osDeslocamento = new OrdemServicoDeslocamento();

			if(ordemServico.getServicoTipo().getIndicadorDeslocamento() == ConstantesSistema.INDICADOR_USO_ATIVO.intValue()){

				if(StringUtils.isEmpty(kmInicial) || StringUtils.isEmpty(kmFinal) || StringUtils.isEmpty(dataHoraInicioDeslocamento)
								|| StringUtils.isEmpty(dataHoraFimDeslocamento)){

					throw new ActionServletException("atencao.campos_obrigatorios_nao_informados_deslocamento");
				}
			}

			osDeslocamento.setOrdemServicoProgramacao(osProgramacao);
			osDeslocamento.setPlacaVeiculo(placaVeiculo);
			osDeslocamento.setDeslocamentoInicio(!dataHoraInicioDeslocamento.equals("") ? Util.converteStringParaDate(
							dataHoraInicioDeslocamento, "dd/MM/yyyy HH:mm") : null);
			osDeslocamento.setDeslocamentoFim(!dataHoraFimDeslocamento.equals("") ? Util.converteStringParaDate(dataHoraFimDeslocamento,
							"dd/MM/yyyy HH:mm") : null);
			osDeslocamento.setUltimaAlteracao(ultimaAlteracao);
			osDeslocamento.setNumeroKmInicio(!kmInicial.equals("") ? Util.formatarMoedaRealparaBigDecimal(kmInicial, 1) : null);
			osDeslocamento.setNumeroKmFim(!kmFinal.equals("") ? Util.formatarMoedaRealparaBigDecimal(kmFinal, 1) : null);

			// Setar no novo Helper
			if(ordemServico.getServicoTipo().getIndicadorDeslocamento() == ConstantesSistema.INDICADOR_USO_ATIVO.intValue()
							|| inserirDeslocamento) dadosHelper.setOrdemServicoDeslocamento(osDeslocamento);

			// Verifica se existem interrupcoes informadas
			Collection<OrdemServicoInterrupcaoDeslocamento> colecaoOsInterrupcaoDeslocamento = (Collection) sessao
							.getAttribute("colecaoInterrupcaoDeslocamento");

			if(!Util.isVazioOrNulo(colecaoOsInterrupcaoDeslocamento)){

				dadosHelper.setColecaoOrdemServicoInterrupcaoDeslocamento(colecaoOsInterrupcaoDeslocamento);
			}

		}

		if(ordemServico.getServicoTipo().getIndicadorRedeRamalAgua() != ConstantesSistema.INDICADOR_USO_DESATIVO.intValue()){

			String idRedeRamal = form.getIdRedeRamalAgua();

			String idDiametroAgua = form.getIdDiametroAgua();
			String idMaterialAgua = form.getIdMaterialAgua();
			String idAgenteExternoAgua = form.getIdAgenteExternoAgua();
			String profundidade = form.getProfundidadeAgua();
			String pressao = form.getPressaoAgua();

			if(ordemServico.getServicoTipo().getIndicadorRedeRamalAgua() == ConstantesSistema.INDICADOR_USO_ATIVO.intValue()){
				if(idRedeRamal.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))
								|| idDiametroAgua.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))
								|| idMaterialAgua.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))
								|| idAgenteExternoAgua.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

					throw new ActionServletException("atencao.dados_obrigatorios_nao_informados_redeRamalAgua");
				}
			}

			dadosHelper.setIdRedeRamalAgua(idRedeRamal);

			if(idRedeRamal.equals(ConstantesSistema.INDICADOR_REDE)){

				if(idDiametroAgua != null && !idDiametroAgua.equals("")){

					DiametroRedeAgua diametroRedeAgua = new DiametroRedeAgua();
					diametroRedeAgua.setId(Integer.valueOf(idDiametroAgua));
					dadosHelper.setDiametroRedeAgua(diametroRedeAgua);
				}

				if(idMaterialAgua != null && !idMaterialAgua.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

					MaterialRedeAgua materialRedeAgua = new MaterialRedeAgua();
					materialRedeAgua.setId(Integer.valueOf(idMaterialAgua));
					dadosHelper.setMaterialRedeAgua(materialRedeAgua);
				}

			}else if(idRedeRamal.equals(ConstantesSistema.INDICADOR_RAMAL)){

				if(idDiametroAgua != null && !idDiametroAgua.equals("")){

					DiametroRamalAgua diametroRamalAgua = new DiametroRamalAgua();
					diametroRamalAgua.setId(Integer.valueOf(idDiametroAgua));
					dadosHelper.setDiametroRamalAgua(diametroRamalAgua);
				}

				if(idMaterialAgua != null && !idMaterialAgua.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

					MaterialRamalAgua materialRamalAgua = new MaterialRamalAgua();
					materialRamalAgua.setId(Integer.valueOf(idMaterialAgua));
					dadosHelper.setMaterialRamalAgua(materialRamalAgua);
				}
			}else if(idRedeRamal.equals(ConstantesSistema.INDICADOR_CAVALETE)){

				if(idDiametroAgua != null && !idDiametroAgua.equals("")){

					DiametroCavaleteAgua diametroCavaleteAgua = new DiametroCavaleteAgua();
					diametroCavaleteAgua.setId(Integer.valueOf(idDiametroAgua));
					dadosHelper.setDiametroCavaleteAgua(diametroCavaleteAgua);
				}

				if(idMaterialAgua != null && !idMaterialAgua.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

					MaterialCavaleteAgua materialCavaleteAgua = new MaterialCavaleteAgua();
					materialCavaleteAgua.setId(Integer.valueOf(idMaterialAgua));
					dadosHelper.setMaterialCavaleteAgua(materialCavaleteAgua);
				}
			}

			// Para esses campos nao importa se é rede ou ramal
			if(idAgenteExternoAgua != null && !idAgenteExternoAgua.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

				AgenteExterno agenteExterno = new AgenteExterno();
				agenteExterno.setId(Integer.valueOf(idAgenteExternoAgua));
				dadosHelper.setAgenteExternoAgua(agenteExterno);
			}

			if(profundidade != null && !profundidade.equals("")){

				dadosHelper.setProfundidadeAgua(Util.formatarMoedaRealparaBigDecimal(profundidade, 2));
			}

			if(pressao != null && !pressao.equals("")){

				dadosHelper.setPressaoAgua(Util.formatarMoedaRealparaBigDecimal(pressao, 4));
			}

			if(ordemServico.getServicoTipo().getIndicadorCausaOcorrencia() != ConstantesSistema.INDICADOR_USO_DESATIVO.intValue()){

				String idCausa = form.getIdCausaAgua();
				if(ordemServico.getServicoTipo().getIndicadorCausaOcorrencia() == ConstantesSistema.INDICADOR_USO_ATIVO.intValue()){

					if(idCausa == null || idCausa.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

						throw new ActionServletException("atencao.dados_obrigatorios_nao_informados_redeRamalAgua");
					}else{

						CausaVazamento causaVazamento = new CausaVazamento();
						causaVazamento.setId(Integer.valueOf(idCausa));
						dadosHelper.setCausaVazamentoAgua(causaVazamento);
					}
				}else{
					if(idCausa != null && !idCausa.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

						CausaVazamento causaVazamento = new CausaVazamento();
						causaVazamento.setId(Integer.valueOf(idCausa));
						dadosHelper.setCausaVazamentoAgua(causaVazamento);
					}
				}
			}

		}else if(ordemServico.getServicoTipo().getIndicadorRedeRamalEsgoto() != ConstantesSistema.INDICADOR_USO_DESATIVO.intValue()){

			String idRedeRamal = form.getIdRedeRamalEsgoto();

			String idDiametroEsgoto = form.getIdDiametroEsgoto();
			String idMaterialEsgoto = form.getIdMaterialEsgoto();
			String idAgenteExternoEsgoto = form.getIdAgenteExternoEsgoto();
			String profundidade = form.getProfundidadeEsgoto();
			String pressao = form.getPressaoEsgoto();

			if(ordemServico.getServicoTipo().getIndicadorRedeRamalEsgoto() == ConstantesSistema.INDICADOR_USO_ATIVO.intValue()){
				if(idRedeRamal.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))
								|| idDiametroEsgoto.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))
								|| idMaterialEsgoto.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))
								|| idAgenteExternoEsgoto.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

					throw new ActionServletException("atencao.dados_obrigatorios_nao_informados_redeRamalEsgoto");
				}
			}
			dadosHelper.setIdRedeRamalEsgoto(idRedeRamal);

			if(idRedeRamal.equals(ConstantesSistema.INDICADOR_REDE)){

				if(idDiametroEsgoto != null && !idDiametroEsgoto.equals("")){

					DiametroRedeEsgoto diametroRedeEsgoto = new DiametroRedeEsgoto();
					diametroRedeEsgoto.setId(Integer.valueOf(idDiametroEsgoto));
					dadosHelper.setDiametroRedeEsgoto(diametroRedeEsgoto);
				}

				if(idMaterialEsgoto != null && !idMaterialEsgoto.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

					MaterialRedeEsgoto materialRedeEsgoto = new MaterialRedeEsgoto();
					materialRedeEsgoto.setId(Integer.valueOf(idMaterialEsgoto));
					dadosHelper.setMaterialRedeEsgoto(materialRedeEsgoto);
				}

			}else{

				if(idDiametroEsgoto != null && !idDiametroEsgoto.equals("")){

					DiametroRamalEsgoto diametroRamalEsgoto = new DiametroRamalEsgoto();
					diametroRamalEsgoto.setId(Integer.valueOf(idDiametroEsgoto));
					dadosHelper.setDiametroRamalEsgoto(diametroRamalEsgoto);
				}

				if(idMaterialEsgoto != null && !idMaterialEsgoto.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

					MaterialRamalEsgoto materialRamalEsgoto = new MaterialRamalEsgoto();
					materialRamalEsgoto.setId(Integer.valueOf(idMaterialEsgoto));
					dadosHelper.setMaterialRamalEsgoto(materialRamalEsgoto);
				}

			}

			// Para esses campos não importa se é rede ou ramal
			if(!idAgenteExternoEsgoto.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

				AgenteExterno agenteExterno = new AgenteExterno();
				agenteExterno.setId(Integer.valueOf(idAgenteExternoEsgoto));
				dadosHelper.setAgenteExternoEsgoto(agenteExterno);
			}

			if(profundidade != null && !profundidade.equals("")){

				dadosHelper.setProfundidadeEsgoto(Util.formatarMoedaRealparaBigDecimal(profundidade));
			}

			if(pressao != null && !pressao.equals("")){

				dadosHelper.setPressaoEsgoto(new BigDecimal(pressao));
			}

			if(ordemServico.getServicoTipo().getIndicadorCausaOcorrencia() != ConstantesSistema.INDICADOR_USO_DESATIVO.intValue()){

				String idCausa = form.getIdCausaEsgoto();
				if((ordemServico.getServicoTipo().getIndicadorCausaOcorrencia() == ConstantesSistema.INDICADOR_USO_ATIVO.intValue())){

					if(idCausa.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

						throw new ActionServletException("atencao.dados_obrigatorios_nao_informados_redeRamalEsgoto");
					}else{

						CausaVazamento causaVazamento = new CausaVazamento();
						causaVazamento.setId(Integer.valueOf(idCausa));
						dadosHelper.setCausaVazamentoEsgoto(causaVazamento);
					}
				}else{
					if(!idCausa.equals(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

						CausaVazamento causaVazamento = new CausaVazamento();
						causaVazamento.setId(Integer.valueOf(idCausa));
						dadosHelper.setCausaVazamentoEsgoto(causaVazamento);
					}
				}
			}
		}

		// Setando coleção de vala
		if(ordemServico.getServicoTipo().getIndicadorVala() != ConstantesSistema.INDICADOR_USO_DESATIVO.intValue()){

			Collection<OrdemServicoValaPavimento> colecaoVala = (Collection) sessao.getAttribute("colecaoVala");

			if(ordemServico.getServicoTipo().getIndicadorVala() == ConstantesSistema.SIM.intValue() && Util.isVazioOrNulo(colecaoVala)){

				throw new ActionServletException("atencao.informe_vala");
			}else if(!Util.isVazioOrNulo(colecaoVala)){

				dadosHelper.setColecaoOrdemServicoValaPavimento(colecaoVala);
			}
		}

		// Setando os dados das atividades no DadosAtividadesOrdemServicoHelper
		if(sessao.getAttribute("colecaoManterDadosAtividadesOrdemServicoHelper") != null){

			Collection colecaoManterDadosAtividadesOrdemServicoHelper = (Collection) sessao
							.getAttribute("colecaoManterDadosAtividadesOrdemServicoHelper");

			if(!Util.isVazioOrNulo(colecaoManterDadosAtividadesOrdemServicoHelper)){

				dadosHelper.setColecaoManterDadosAtividadesOrdemServicoHelper(colecaoManterDadosAtividadesOrdemServicoHelper);
			}
		}

		boolean primeiraInterrupcao = true;
		if(!Util.isVazioOrNulo(dadosHelper.getColecaoManterDadosAtividadesOrdemServicoHelper())){

			for(ManterDadosAtividadesOrdemServicoHelper helperAtividade : dadosHelper.getColecaoManterDadosAtividadesOrdemServicoHelper()){

				if(!Util.isVazioOrNulo(helperAtividade.getColecaoInterrupcao())){

					if(primeiraInterrupcao){

						dadosHelper.setColecaoOrdemServicoInterrupcaoExecucao(new ArrayList<OrdemServicoInterrupcaoExecucao>());
					}

					dadosHelper.getColecaoOrdemServicoInterrupcaoExecucao().addAll(helperAtividade.getColecaoInterrupcao());
				}
			}
		}

		OrdemServicoProgramacao osProgramacaoSessao = (OrdemServicoProgramacao) sessao.getAttribute("osProgramacao");
		dadosHelper.setOrdemServicoProgramacao(osProgramacaoSessao);

		return dadosHelper;
	}

	/**
	 * Pesquisar a Programacao Roteiro
	 */
	private ProgramacaoRoteiro consultarProgramacaoRoteiro(Date dataRoterio, Integer unidade){

		FiltroProgramacaoRoteiro filtroProgramacaoRoteiro = new FiltroProgramacaoRoteiro();
		filtroProgramacaoRoteiro.adicionarParametro(new ParametroSimples(FiltroProgramacaoRoteiro.DATA_ROTEIRO, dataRoterio));
		filtroProgramacaoRoteiro.adicionarParametro(new ParametroSimples(FiltroProgramacaoRoteiro.UNIDADE_ORGANIZACIONAL_ID, unidade));

		Collection colecaoProgramacaoRoteiro = Fachada.getInstancia().pesquisar(filtroProgramacaoRoteiro,
						ProgramacaoRoteiro.class.getName());
		ProgramacaoRoteiro programacaoRoteiro = (ProgramacaoRoteiro) Util.retonarObjetoDeColecao(colecaoProgramacaoRoteiro);

		return programacaoRoteiro;

	}

}