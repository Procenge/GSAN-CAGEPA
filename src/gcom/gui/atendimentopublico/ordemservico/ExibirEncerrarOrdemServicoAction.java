/*
 * Copyright (C) 2007-2007 the GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
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
 * Foundation, Inc., 59 Temple Place � Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa � software livre; voc� pode redistribu�-lo e/ou
 * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers�o 2 da
 * Licen�a.
 * Este programa � distribu�do na expectativa de ser �til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
 * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
 * PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais
 * detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
 * junto com este programa; se n�o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.*;
import gcom.atendimentopublico.ordemservico.bean.ManterDadosAtividadesOrdemServicoHelper;
import gcom.atendimentopublico.ordemservico.bean.OSAtividadePeriodoExecucaoHelper;
import gcom.atendimentopublico.ordemservico.bean.OSExecucaoEquipeHelper;
import gcom.atendimentopublico.ordemservico.bean.ObterDescricaoSituacaoOSHelper;
import gcom.atendimentopublico.registroatendimento.AtendimentoMotivoEncerramento;
import gcom.atendimentopublico.registroatendimento.AtendimentoRelacaoTipo;
import gcom.atendimentopublico.registroatendimento.FiltroAtendimentoMotivoEncerramento;
import gcom.atendimentopublico.registroatendimento.LocalOcorrencia;
import gcom.atendimentopublico.registroatendimento.bean.ObterDescricaoSituacaoRAHelper;
import gcom.cadastro.imovel.PavimentoCalcada;
import gcom.cadastro.imovel.PavimentoRua;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action que define o pr�-processamento da p�gina de encerra OS caso precise
 * entrar na tela
 * 
 * @author S�vio Luiz
 * @created 18/09/2006
 */
public class ExibirEncerrarOrdemServicoAction
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
		ActionForward retorno = actionMapping.findForward("encerrarOrdemServico");
		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);
		EncerrarOrdemServicoActionForm encerrarOrdemServicoActionForm = (EncerrarOrdemServicoActionForm) actionForm;

		encerrarOrdemServicoActionForm.setTmEncerramento(Util.formatarData(new Date()));

		/*
		 * Redireciona para exibi��o da tela limpando os dados passados por queryString no
		 * httpServletRequest,e evitando assim que o usu�rio ao teclar F5 fa�a a requisi��o
		 * novamente realizando uma opera��o(inclus�o, remo��o, etc) repetida
		 */
		if(httpServletRequest.getParameter("exibirApenas") != null){

			/*
			 * Informar qual o campo receber� o foco no carregamento, caso nenhum tenha sido
			 * informado
			 */
			if(httpServletRequest.getAttribute("nomeCampo") == null){

				httpServletRequest.setAttribute("nomeCampo", "dataEncerramento");
			}

			/*
			 * Seta atributos utilizados para levar a tela de NILA no httpServletRequest caso seja
			 * necess�rio
			 */
			setarAtributosUtilizadosRequestNILA(httpServletRequest, sessao, encerrarOrdemServicoActionForm);

			return retorno;
		}

		if(httpServletRequest.getParameter("submitAutomatico1") != null
						&& httpServletRequest.getParameter("submitAutomatico1").equals("ok")){
			httpServletRequest.setAttribute("submitAutomatico1", "ok");
		}

		// Verifica parametros passados que identifica que veio da tela de Acompanhamento do Roteiro
		if(!Util.isVazioOuBranco(httpServletRequest.getParameter("dataRoteiro"))
						&& !Util.isVazioOuBranco(httpServletRequest.getParameter("numeroOS"))
						&& !Util.isVazioOuBranco(httpServletRequest.getParameter("retornoTela"))){

			encerrarOrdemServicoActionForm.resetarConsultarDadosOSPopup();
		}

		if(!Util.isVazioOuBranco(httpServletRequest.getParameter("dataRoteiro"))){

			encerrarOrdemServicoActionForm.setDataRoteiro(httpServletRequest.getParameter("dataRoteiro"));
		}else if(sessao.getAttribute("dataRoteiro") != null){

			encerrarOrdemServicoActionForm.setDataRoteiro(sessao.getAttribute("dataRoteiro").toString());
		}

		Integer numeroOS = null;

		if(!Util.isVazioOuBranco(httpServletRequest.getParameter("numeroOS"))){

			numeroOS = Util.converterStringParaInteger(httpServletRequest.getParameter("numeroOS"));
			encerrarOrdemServicoActionForm.setNumeroOS(numeroOS.toString());
		}

		if(numeroOS == null){

			if(!Util.isVazioOuBranco(encerrarOrdemServicoActionForm.getNumeroOS())){

				numeroOS = Integer.valueOf(encerrarOrdemServicoActionForm.getNumeroOS());
			}
		}

		String retornoTela = httpServletRequest.getParameter("retornoTela");

		if(!Util.isVazioOuBranco(retornoTela)){

			if(retornoTela.equals("exibirAcompanharRoteiroProgramacaoOrdemServicoAction.do")){

				sessao.setAttribute("retornoTela", retornoTela + "?dataRoteiro=" + encerrarOrdemServicoActionForm.getDataRoteiro());
			}else{

				sessao.setAttribute("retornoTela", retornoTela + "&dataRoteiro=" + encerrarOrdemServicoActionForm.getDataRoteiro());
			}
		}

		encerrarOrdemServicoActionForm.setQtdFotos(fachada.pesquisarQuantidadeFotosOrdemServico(numeroOS));

		// Caso o Tipo de Servi�o da OS seja de fiscaliza��o ou vistoria(NILA)
		if(isOSFiscalizacaoOuVistoria(numeroOS)){

			httpServletRequest.setAttribute("abrePopupDados", "S");
		}else{

			httpServletRequest.setAttribute("abrePopupDados", "N");
		}

		String idMotivoEncerramento = httpServletRequest.getParameter("idMotivoEncerramento");
		String dataEncerramento = httpServletRequest.getParameter("dataEncerramento");

		String carregarCampos = httpServletRequest.getParameter("carregarCampos");
		String retornoConsulta = httpServletRequest.getParameter("retornoConsulta");

		String carregarCamposComReferencia = httpServletRequest.getParameter("carregarCamposComReferencia");

		String pesquisaServicoTipo = httpServletRequest.getParameter("pesquisaServicoTipo");

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		String idServicoTipoString = httpServletRequest.getParameter("idServicoTipo");
		if(idServicoTipoString != null && !idServicoTipoString.equals("")){

			httpServletRequest.setAttribute("idServicoTipo", idServicoTipoString);
		}

		String processoAutorizacaoServicosAssociados = httpServletRequest.getParameter("processoAutorizacaoServicosAssociados");
		if(processoAutorizacaoServicosAssociados != null && !processoAutorizacaoServicosAssociados.equals("")){

			httpServletRequest.setAttribute("processoAutorizacaoServicosAssociados", processoAutorizacaoServicosAssociados);
		}

		if(httpServletRequest.getParameter("redirecionarPagina") != null
						&& !httpServletRequest.getParameter("redirecionarPagina").equals("")){

			inserirColecaoAtendimentoMotivoEncerradoSessao(fachada, sessao, encerrarOrdemServicoActionForm);

			String redirecionarPagina = httpServletRequest.getParameter("redirecionarPagina");

			if(redirecionarPagina.equals("processoAutorizacaoServicosAssociados")){

				httpServletRequest.setAttribute("processoAutorizacaoServicosAssociados", "processoAutorizacaoServicosAssociados");
				// encerrarOrdemServicoActionForm.resetarConsultarDadosOSPopup();
			}

			retorno = actionMapping.findForward(redirecionarPagina);
			if(Util.isVazioOuBranco(encerrarOrdemServicoActionForm.getNumeroOS())){

				httpServletRequest.setAttribute("numeroOSInfracao", "''");
			}
			return retorno;
		}

		/*
		 * Caso seja uma requisi��o de pesquisar um tipo de servi�o a partir da tecla enter(esse
		 * caso s� acontece caso n�o haja uma cole��o de tipos de servi�o na sess�o, apenas foi
		 * exibido uma campo para o usu�rio pesquisar o tipod e servi�o)
		 */
		if(!Util.isVazioOuBranco(pesquisaServicoTipo)){

			String idServicoTipo = encerrarOrdemServicoActionForm.getIdServicoTipo();
			String descricaoServicoTipo = encerrarOrdemServicoActionForm.getDescricaoServicoTipo();
			if((idServicoTipo != null && !idServicoTipo.equals("")) && (descricaoServicoTipo == null || descricaoServicoTipo.equals(""))){

				FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();

				filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, idServicoTipo));
				filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				Collection<ServicoTipo> servicoTipoEncontrado = fachada.pesquisar(filtroServicoTipo, ServicoTipo.class.getName());

				if(servicoTipoEncontrado != null && !servicoTipoEncontrado.isEmpty()){

					ServicoTipo servicoTipo = (ServicoTipo) Util.retonarObjetoDeColecao(servicoTipoEncontrado);
					// O servi�o tipo foi encontrado
					encerrarOrdemServicoActionForm.setIdServicoTipo("" + servicoTipo.getId());
					encerrarOrdemServicoActionForm.setDescricaoServicoTipo(servicoTipo.getDescricao());
					httpServletRequest.setAttribute("idServicoTipoNaoEncontrado", "true");
					httpServletRequest.setAttribute("nomeCampo", "ButtonAtividade");

				}else{

					encerrarOrdemServicoActionForm.setIdServicoTipo("");
					httpServletRequest.setAttribute("nomeCampo", "idServicoTipo");
					httpServletRequest.setAttribute("idServicoTipoNaoEncontrado", "exception");
					encerrarOrdemServicoActionForm.setDescricaoServicoTipo("Tipo Servi�o Inexistente");

				}
			}

		}else{

			if(retornoConsulta == null || retornoConsulta.equals("")){

				if(carregarCamposComReferencia == null || carregarCamposComReferencia.equals("")){

					// caso o motivo de encerramento n�o tenha sido mudado
					if(carregarCampos == null || carregarCampos.equals("")){

						// [FS0001] - Verificar unidade do usu�rio
						fachada.verificarUnidadeUsuario(numeroOS, usuarioLogado);

						// Limpa campos do form
						// encerrarOrdemServicoActionForm.resetarConsultarDadosOSPopup();
						OrdemServico ordemServico = pesquisarOrdemServico(numeroOS);

						if(Util.isVazioOuBranco(encerrarOrdemServicoActionForm.getDataRoteiro())){

							// [FS0006] - Verificar Origem do Encerramento da Ordem de Servi�o
							fachada.verificarOrigemEncerramentoOS(numeroOS, Util.converteStringParaDate(dataEncerramento));
						}else{

							sessao.setAttribute("realizandoAcompanhamentoProgramacao", "true");
							sessao.setAttribute("dataRoteiro", encerrarOrdemServicoActionForm.getDataRoteiro());
							httpServletRequest.setAttribute("idServicoTipo", ordemServico.getServicoTipo().getId().toString());
						}

						// seta o id do motivo encerramento
						if(!Util.isVazioOuBranco(idMotivoEncerramento)){

							encerrarOrdemServicoActionForm.setIdMotivoEncerramento(idMotivoEncerramento);
						}

						/*
						 * Alterado por Raphael Rossiter em 01/08/2007 (Analista: Rosana Carvalho)
						 * OBJETIVO: N�o colocar a data e a hora atual como sugest�o para a data e
						 * a hora de encerramento da Ordem de Servi�o
						 */
						if(!Util.isVazioOuBranco(dataEncerramento)){

							encerrarOrdemServicoActionForm.setDataEncerramento(dataEncerramento);
						}

						// Dados Gerais da OS
						encerrarOrdemServicoActionForm.setNumeroOS(ordemServico.getId().toString());
						encerrarOrdemServicoActionForm.setSituacaoOSId(String.valueOf(ordemServico.getSituacao()));

						// Caso de Uso [UC0454]
						ObterDescricaoSituacaoOSHelper situacaoOS = fachada.obterDescricaoSituacaoOS(ordemServico.getId());
						encerrarOrdemServicoActionForm.setSituacaoOS(situacaoOS.getDescricaoSituacao());
						if(ordemServico.getRegistroAtendimento() != null){

							encerrarOrdemServicoActionForm.setNumeroRA(ordemServico.getRegistroAtendimento().getId() + "");

							// Caso de Uso [UC0420]
							ObterDescricaoSituacaoRAHelper situacaoRA = fachada.obterDescricaoSituacaoRA(ordemServico
											.getRegistroAtendimento().getId());
							encerrarOrdemServicoActionForm.setSituacaoRA(situacaoRA.getDescricaoSituacao());
						}

						if(ordemServico.getCobrancaDocumento() != null){

							encerrarOrdemServicoActionForm.setNumeroDocumentoCobranca(ordemServico.getCobrancaDocumento().getId() + "");
						}

						encerrarOrdemServicoActionForm.setDataGeracao(Util.formatarData(ordemServico.getDataGeracao()));

						if(ordemServico.getServicoTipo() != null){

							encerrarOrdemServicoActionForm.setTipoServicoOSId(ordemServico.getServicoTipo().getId() + "");
							encerrarOrdemServicoActionForm.setTipoServicoOSDescricao(ordemServico.getServicoTipo().getDescricao());

							// Alterado por: Yara Souza
							// Data : 12/05/2010
							// Solicita��o: N�o exibir a op��o de informa��o dos dados do pavimento.
							// ------------------------------------------------------------------------------------------------------------------
							// encerrarOrdemServicoActionForm.setIndicadorPavimento("" +
							// ordemServico.getServicoTipo().getIndicadorPavimento());
							encerrarOrdemServicoActionForm.setIndicadorPavimento("" + ServicoTipo.INDICADOR_PAVIMENTO_NAO);
							// ------------------------------------------------------------------------------------------------------------------

							encerrarOrdemServicoActionForm.setIndicadorAtualizaComercial(""
											+ ordemServico.getServicoTipo().getIndicadorAtualizaComercial());
							encerrarOrdemServicoActionForm.setIndicadorVistoriaServicoTipo(""
											+ ordemServico.getServicoTipo().getIndicadorVistoria());

							if(ordemServico.getServicoTipo().getServicoTipoReferencia() != null){

								encerrarOrdemServicoActionForm.setTipoServicoReferenciaId(""
												+ ordemServico.getServicoTipo().getServicoTipoReferencia().getId());
								encerrarOrdemServicoActionForm.setServicoTipoReferenciaDescricao(ordemServico.getServicoTipo()
												.getServicoTipoReferencia().getDescricao());
							}
						}

						// dados da referencia do servi�o tipo da OS
						if(ordemServico.getServicoTipoReferencia() != null && !ordemServico.getServicoTipoReferencia().equals("")){

							encerrarOrdemServicoActionForm.setServicoTipoReferenciaOS("" + ordemServico.getServicoTipoReferencia().getId());
							encerrarOrdemServicoActionForm.setServicoTipoReferenciaOSDescricao(""
											+ ordemServico.getServicoTipoReferencia().getDescricao());
						}

						if(ordemServico.getOsReferencia() != null){

							sessao.setAttribute("osReferencia", ordemServico.getOsReferencia());
							if(ordemServico.getOsReferencia().getServicoTipo() != null
											&& !ordemServico.getOsReferencia().getServicoTipo().equals("")){

								encerrarOrdemServicoActionForm.setTipoServicoReferenciaDescricao(ordemServico.getOsReferencia()
												.getServicoTipo().getDescricao());
							}
						}

						encerrarOrdemServicoActionForm.setObservacao(ordemServico.getObservacao());
						encerrarOrdemServicoActionForm.setValorServicoOriginal(ordemServico.getValorOriginal() + "");
						encerrarOrdemServicoActionForm
										.setPrioridadeOriginal(ordemServico.getServicoTipoPrioridadeOriginal().getDescricao());
						encerrarOrdemServicoActionForm.setPrioridadeAtual(ordemServico.getServicoTipoPrioridadeAtual().getDescricao() + "");
						OrdemServicoUnidade ordemServicoUnidade = consultarOrdemServicoUnidade(ordemServico.getId(),
										AtendimentoRelacaoTipo.ABRIR_REGISTRAR);

						if(ordemServicoUnidade != null){

							encerrarOrdemServicoActionForm.setUnidadeGeracaoId(ordemServicoUnidade.getUnidadeOrganizacional().getId() + "");
							encerrarOrdemServicoActionForm.setUnidadeGeracaoDescricao(ordemServicoUnidade.getUnidadeOrganizacional()
											.getDescricao());
							encerrarOrdemServicoActionForm.setUsuarioGeracaoId(ordemServicoUnidade.getUsuario().getId() + "");
							encerrarOrdemServicoActionForm.setUsuarioGeracaoNome(ordemServicoUnidade.getUsuario().getNomeUsuario());
						}

						if(ordemServico.getDataEmissao() != null){

							encerrarOrdemServicoActionForm.setDataUltimaEmissao(Util.formatarData(ordemServico.getDataEmissao()));
						}

						FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
						filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, ordemServico.getServicoTipo()
										.getId()));

						Collection<ServicoTipo> colecaoServicoTipo = fachada.pesquisar(filtroServicoTipo, ServicoTipo.class.getName());

						if(!Util.isVazioOrNulo(colecaoServicoTipo)){

							ServicoTipo servicoTipo = (ServicoTipo) Util.retonarObjetoDeColecao(colecaoServicoTipo);
							ordemServico.setServicoTipo(servicoTipo);
						}

						sessao.setAttribute("ordemServicoEncerrar", ordemServico);

						encerrarOrdemServicoActionForm.setUltimaAlteracao(ordemServico.getUltimaAlteracao());
						inserirColecaoAtendimentoMotivoEncerradoSessao(fachada, sessao, encerrarOrdemServicoActionForm);

					}else{

						// Caso tenha selecionado um motivo de encerramento
						if(!Util.isVazioOuBranco(encerrarOrdemServicoActionForm.getIdMotivoEncerramento())){

							Collection<AtendimentoMotivoEncerramento> colecaoAtendimentoMotivoEncerrado = (Collection<AtendimentoMotivoEncerramento>) sessao
											.getAttribute("colecaoAtendimentoMotivoEncerrado");
							Iterator<AtendimentoMotivoEncerramento> iteAtendimentoMotivoEncerramento = colecaoAtendimentoMotivoEncerrado
											.iterator();
							while(iteAtendimentoMotivoEncerramento.hasNext()){

								AtendimentoMotivoEncerramento atendimentoMotivoEncerramento = iteAtendimentoMotivoEncerramento.next();

								if(atendimentoMotivoEncerramento.getId() != null
												&& atendimentoMotivoEncerramento.getId().equals(
																Util.converterStringParaInteger(encerrarOrdemServicoActionForm
																				.getIdMotivoEncerramento()))){

									encerrarOrdemServicoActionForm.setIndicadorExecucao(""
													+ atendimentoMotivoEncerramento.getIndicadorExecucao());

									// 4.6 caso o indicador de execu��o seja igual a sim(1)
									if(atendimentoMotivoEncerramento.getIndicadorExecucao() == AtendimentoMotivoEncerramento.INDICADOR_EXECUCAO_SIM){

										// Caso primeira requisi��o veio da tela de Acompanhamento
										// de Roteiro da OS
										if(!Util.isVazioOuBranco(encerrarOrdemServicoActionForm.getDataRoteiro())){

											// Limpa a sess�o
											sessao.removeAttribute("colecaoManterDadosAtividadesOrdemServicoHelper");
											sessao.removeAttribute("colecaoInterrupcaoDeslocamento");
											sessao.removeAttribute("colecaoVala");

											// Obter a OS para posteriores valida��es
											OrdemServico ordemServico = pesquisarOrdemServico(numeroOS);

											FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
											filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, ordemServico
															.getServicoTipo().getId()));

											Collection<ServicoTipo> colecaoServicoTipo = fachada.pesquisar(filtroServicoTipo,
															ServicoTipo.class.getName());

											if(!Util.isVazioOrNulo(colecaoServicoTipo)){

												ServicoTipo servicoTipo = (ServicoTipo) Util.retonarObjetoDeColecao(colecaoServicoTipo);
												ordemServico.setServicoTipo(servicoTipo);
											}

											Collection<Atividade> colecaoAtividadeOS = fachada.obterAtividadesOrdemServico(numeroOS);
											this.montarListaHelperAtividades(sessao, colecaoAtividadeOS, numeroOS, fachada);

											// Recuperar Equipe Atual
											Date dataRoteiro = Util.converteStringParaDate(encerrarOrdemServicoActionForm.getDataRoteiro());

											Collection<Equipe> colecaoEquipeProgramacao = fachada
															.recuperaEquipeDaOSProgramacaoPorDataRoteiro(numeroOS, dataRoteiro);

											if(!Util.isVazioOrNulo(colecaoEquipeProgramacao) && colecaoEquipeProgramacao.size() > 1){

												throw new ActionServletException("atencao.ordem_servico_programada_varias_equipes");
											}

											Equipe equipeAtual = (Equipe) Util.retonarObjetoDeColecao(colecaoEquipeProgramacao);

											// Recuperar dados Deslocamento
											if(equipeAtual != null){

												this.limparDadosDeslocamento(encerrarOrdemServicoActionForm);

												OrdemServicoProgramacao osProgramacao = this.obterOrdemServicoProgramacao(fachada, sessao,
																dataRoteiro, numeroOS, equipeAtual.getId());
												sessao.setAttribute("osProgramacao", osProgramacao);

												this.recuperarDadosDeslocamentoCadastrado(sessao, httpServletRequest,
																encerrarOrdemServicoActionForm, fachada, equipeAtual.getId(), dataRoteiro,
																numeroOS);
											}

											/*
											 * Verificando se existe alguma OrdemServicoValaPvto
											 * cadastrada
											 */
											Collection<OrdemServicoValaPavimento> colecaoOsValaPavimento = fachada
															.pesquisarOrdemServicoValaPavimento(numeroOS);

											if(!Util.isVazioOrNulo(colecaoOsValaPavimento)){

												sessao.setAttribute("colecaoVala", colecaoOsValaPavimento);
											}

											/*
											 * Setando cole��es necess�rias para exibi��o dos dados
											 * das atividades e dos dados operacionais da OS
											 */
											if(ordemServico != null && ordemServico.getServicoTipo() != null){

												// Motivo de Interrup��o do Deslocamento
												if(ordemServico.getServicoTipo().getIndicadorDeslocamento() != ConstantesSistema.INDICADOR_USO_DESATIVO
																.intValue()){

													Collection<MotivoInterrupcao> colecaoMotivoInterrupcao = fachada
																	.pesquisarMotivoInterrupcao();
													sessao.setAttribute("colecaoMotivoInterrupcao", colecaoMotivoInterrupcao);

												}

												// Local de Ocorr�ncia
												if(ordemServico.getServicoTipo().getIndicadorVala() != ConstantesSistema.INDICADOR_USO_DESATIVO
																.intValue()){

													Collection<LocalOcorrencia> colecaoLocalOcorrencia = fachada.pesquisarLocalOcorrencia();
													sessao.setAttribute("colecaoLocalOcorrencia", colecaoLocalOcorrencia);
												}

												// Causa
												if(ordemServico.getServicoTipo().getIndicadorRedeRamalAgua() != ConstantesSistema.INDICADOR_USO_DESATIVO
																.intValue()
																|| ordemServico.getServicoTipo().getIndicadorRedeRamalEsgoto() != ConstantesSistema.INDICADOR_USO_DESATIVO
																				.intValue()){

													if(ordemServico.getServicoTipo().getIndicadorCausaOcorrencia() != ConstantesSistema.INDICADOR_USO_DESATIVO
																	.intValue()){

														Collection<CausaVazamento> colecaoCausa = fachada.pesquisarCausaVazamento();
														sessao.setAttribute("colecaoCausa", colecaoCausa);
													}

													// Agente Externo
													Collection<AgenteExterno> colecaoAgenteExterno = fachada.pesquisarAgenteExterno();
													sessao.setAttribute("colecaoAgenteExterno", colecaoAgenteExterno);
												}
											}
										}

										// 4.6.2 caso o servi�o tipo da ordem de servi�o tenha
										// refer�ncia
										if(encerrarOrdemServicoActionForm.getTipoServicoReferenciaId() != null
														&& !encerrarOrdemServicoActionForm.getTipoServicoReferenciaId().equals("")) encerrarComExecucaoComReferencia(
														fachada, sessao, httpServletRequest, encerrarOrdemServicoActionForm, numeroOS);
									}
									break;
								}
							}
						}else{
							encerrarOrdemServicoActionForm.setIndicadorExecucao("");
							encerrarOrdemServicoActionForm.setPavimento("");
							encerrarOrdemServicoActionForm.setObservacao("");
							encerrarOrdemServicoActionForm.setServicoTipoObrigatorio("");
							encerrarOrdemServicoActionForm.setIndicadorDeferimento("");
							encerrarOrdemServicoActionForm.setIdServicoTipo("");
							encerrarOrdemServicoActionForm.setDescricaoServicoTipo("");
							encerrarOrdemServicoActionForm.setIdTipoRetornoReferida("");
							sessao.removeAttribute("colecaoServicoTipo");
						}

					}
				}else{

					// caso seja mudado o Tipo de Retorno Referida
					encerrarComExecucaoComReferencia(fachada, sessao, httpServletRequest, encerrarOrdemServicoActionForm, numeroOS);
				}
			}else{

				if(retornoConsulta.equals("informarOS")){

					httpServletRequest.setAttribute("nomeCampo", "ButtonOSFiscalizacao");
				}
			}
		}

		/*
		 * Informar qual o campo receber� o foco no carregamento, caso nenhum tenha sido informado
		 */
		if(httpServletRequest.getAttribute("nomeCampo") == null){

			httpServletRequest.setAttribute("nomeCampo", "dataEncerramento");
		}

		/*
		 * Seta atributos utilizados para levar a tela de NILA no httpServletRequest caso seja
		 * necess�rio
		 */
		setarAtributosUtilizadosRequestNILA(httpServletRequest, sessao, encerrarOrdemServicoActionForm);

		OrdemServicoFotoOcorrencia osFoto = new OrdemServicoFotoOcorrencia();
		osFoto.setIdOrdemServico(Integer
						.valueOf(!Util.isVazioOuBranco(encerrarOrdemServicoActionForm.getNumeroOS()) ? encerrarOrdemServicoActionForm
										.getNumeroOS() : "0"));
		Collection<OrdemServicoFotoOcorrencia> colecaoFotosOcorrencia = fachada.listarOSFoto(osFoto);
		encerrarOrdemServicoActionForm.setQtdFotos(colecaoFotosOcorrencia.size());
		encerrarOrdemServicoActionForm.setColecaoOSFoto(colecaoFotosOcorrencia);

		// Remover Atividade
		String removerAtividade = httpServletRequest.getParameter("removerAtividade");

		if(!Util.isVazioOuBranco(removerAtividade)){

			this.removerAtividade(sessao, removerAtividade);
			return actionMapping.findForward("exibirApenasEncerrarOrdemServico");
		}

		// Adicionar Atividade
		String adicionarAtividade = httpServletRequest.getParameter("adicionarAtividade");

		if(!Util.isVazioOuBranco(adicionarAtividade)){

			this.adicionarAtividade(httpServletRequest, fachada, sessao, encerrarOrdemServicoActionForm);
			return actionMapping.findForward("exibirApenasEncerrarOrdemServico");
		}else{

			// Pesquisar Atividade
			if(!Util.isVazioOuBranco(encerrarOrdemServicoActionForm.getIdAtividade())){

				if(Util.validarNumeroMaiorQueZERO(encerrarOrdemServicoActionForm.getIdAtividade())){

					this.pesquisarAtividade(encerrarOrdemServicoActionForm, fachada, httpServletRequest);
				}
			}
		}

		boolean manterAtividade = false;
		if(!Util.isVazioOuBranco(encerrarOrdemServicoActionForm.getMostrarHorasExecucao())
						&& encerrarOrdemServicoActionForm.getMostrarHorasExecucao().equals(ConstantesSistema.SIM.toString())){

			manterAtividade = true;
		}else if(!Util.isVazioOuBranco(encerrarOrdemServicoActionForm.getMostrarMateriais())
						&& encerrarOrdemServicoActionForm.getMostrarMateriais().equals(ConstantesSistema.SIM.toString())){

			manterAtividade = true;
		}

		// Caso o usu�rio tenha clicado no rel�gio(manter horas execu��o)
		if(manterAtividade){

			boolean retornarTela = false;

			try{

				retornarTela = manterDadosAtividades(httpServletRequest, fachada, sessao, encerrarOrdemServicoActionForm, numeroOS,
								dataEncerramento);
			}catch(ActionServletException e){

				String[] parametros = new String[e.getParametroMensagem().size()];
				e.getParametroMensagem().toArray(parametros);
				ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
				ex.setUrlBotaoVoltar("/gsan/exibirEncerrarOrdemServicoAction.do?carregarCampos="
								+ "&processoAutorizacaoServicosAssociados=&idServicoTipo=");
				throw ex;

			}

			/*
			 * Redireciona para exibi��o da tela limpando os dados passados por queryString no
			 * httpServletRequest,e evitando assim que o usu�rio tecle F5 fa�a a requisi��o
			 * novamente
			 * realizando uma inclus�o repetida
			 */
			if(retornarTela){

				return actionMapping.findForward("exibirApenasEncerrarOrdemServico");
			}
		}

		// Dados Operacionais
		// Remover Interrup��o do Deslocamento
		String removerInterrupcaoDeslocamento = httpServletRequest.getParameter("removerInterrupcaoDeslocamento");

		if(!Util.isVazioOuBranco(removerInterrupcaoDeslocamento)){

			this.removerInterrupcaoDeslocamento(sessao, removerInterrupcaoDeslocamento);
			return actionMapping.findForward("exibirApenasEncerrarOrdemServico");
		}

		// Adicionar Interrup��o do Deslocamento
		String adicionarInterrupcaoDeslocamento = httpServletRequest.getParameter("adicionarInterrupcaoDeslocamento");

		if(!Util.isVazioOuBranco(adicionarInterrupcaoDeslocamento)){

			try{

				this.adicionarInterrupcaoDeslocamento(encerrarOrdemServicoActionForm, sessao);
			}catch(ActionServletException e){

				String[] parametros = new String[e.getParametroMensagem().size()];
				e.getParametroMensagem().toArray(parametros);
				ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
				ex.setUrlBotaoVoltar("/gsan/exibirEncerrarOrdemServicoAction.do?carregarCampos="
								+ "&processoAutorizacaoServicosAssociados=&idServicoTipo=");
				throw ex;
			}

			return actionMapping.findForward("exibirApenasEncerrarOrdemServico");
		}

		// Adicionar Vala
		String adicionarVala = httpServletRequest.getParameter("adicionarVala");

		if(!Util.isVazioOuBranco(adicionarVala)){

			try{

				this.adicionarVala(encerrarOrdemServicoActionForm, sessao, httpServletRequest, fachada, (OrdemServico) (sessao
								.getAttribute("ordemServicoEncerrar")));
			}catch(ActionServletException e){

				String[] parametros = new String[e.getParametroMensagem().size()];
				e.getParametroMensagem().toArray(parametros);
				ActionServletException ex = new ActionServletException(e.getMessage(), e, parametros);
				ex.setUrlBotaoVoltar("/gsan/exibirEncerrarOrdemServicoAction.do?carregarCampos="
								+ "&processoAutorizacaoServicosAssociados=&idServicoTipo=");
				throw ex;
			}

			return actionMapping.findForward("exibirApenasEncerrarOrdemServico");
		}

		// Remover Vala
		String removerVala = httpServletRequest.getParameter("removerVala");

		if(!Util.isVazioOuBranco(removerVala)){

			this.removerVala(sessao, removerVala);
			return actionMapping.findForward("exibirApenasEncerrarOrdemServico");
		}

		return retorno;
	}

	/**
	 * Seta atributos utilizados para levar a tela de NILA no httpServletRequest caso seja
	 * necess�rio
	 * 
	 * @date 01/12/2011
	 */
	private void setarAtributosUtilizadosRequestNILA(HttpServletRequest httpServletRequest, HttpSession sessao,
					EncerrarOrdemServicoActionForm encerrarOrdemServicoActionForm){

		if(!Util.isVazioOuBranco(encerrarOrdemServicoActionForm.getNumeroOS())){

			httpServletRequest.setAttribute("numeroOSInfracao", "''");

			if(sessao.getAttribute("ocorrenciaInfracao") != null && sessao.getAttribute("colecaoInfracaoTipo") != null){

				httpServletRequest.setAttribute("abrirPopupOcorrenciaInfracao", "abrirPopupOcorrenciaInfracao");
				httpServletRequest.setAttribute("numeroOSInfracao", encerrarOrdemServicoActionForm.getNumeroOS());
			}
		}else{

			httpServletRequest.setAttribute("numeroOSInfracao", "''");
		}
	}

	private void inserirColecaoAtendimentoMotivoEncerradoSessao(Fachada fachada, HttpSession sessao,
					EncerrarOrdemServicoActionForm encerrarOrdemServicoActionForm){

		FiltroAtendimentoMotivoEncerramento filtroAtendimentoMotivoEncerramento = new FiltroAtendimentoMotivoEncerramento();
		filtroAtendimentoMotivoEncerramento.adicionarParametro(new ParametroSimples(FiltroAtendimentoMotivoEncerramento.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroAtendimentoMotivoEncerramento.adicionarParametro(new ParametroSimples(
						FiltroAtendimentoMotivoEncerramento.INDICADOR_DUPLICIDADE,
						AtendimentoMotivoEncerramento.INDICADOR_DUPLICIDADE_INATIVO));
		Collection<AtendimentoMotivoEncerramento> colecaoAtendimentoMotivoEncerrado = fachada.pesquisar(
						filtroAtendimentoMotivoEncerramento, AtendimentoMotivoEncerramento.class.getName());
		sessao.setAttribute("colecaoAtendimentoMotivoEncerrado", colecaoAtendimentoMotivoEncerrado);

		if(encerrarOrdemServicoActionForm.getIdMotivoEncerramento() != null
						&& !encerrarOrdemServicoActionForm.getIdMotivoEncerramento().equals("")){
			Iterator<AtendimentoMotivoEncerramento> iteAtendimentoMotivoEncerramento = colecaoAtendimentoMotivoEncerrado.iterator();
			while(iteAtendimentoMotivoEncerramento.hasNext()){
				AtendimentoMotivoEncerramento atendimentoMotivoEncerramento = iteAtendimentoMotivoEncerramento.next();
				if(atendimentoMotivoEncerramento.getId() != null
								&& atendimentoMotivoEncerramento.getId().equals(
												Util.converterStringParaInteger(encerrarOrdemServicoActionForm.getIdMotivoEncerramento()))){
					encerrarOrdemServicoActionForm.setIndicadorExecucao("" + atendimentoMotivoEncerramento.getIndicadorExecucao());

					break;
				}
			}

		}else{
			encerrarOrdemServicoActionForm.setIndicadorExecucao("");
		}
	}

	/**
	 * Consulta a ordem de servi�o pelo id informado
	 * 
	 * @author S�vio Luiz
	 * @created 18/09/2006
	 */
	private OrdemServico pesquisarOrdemServico(Integer id){

		Fachada fachada = Fachada.getInstancia();
		OrdemServico retorno = fachada.consultarDadosOrdemServico(id);
		if(retorno == null){
			throw new ActionServletException("atencao.naocadastrado", null, "Ordem de Servi�o");
		}
		// validarOrdemServicoFiscalizacao(retorno);
		return retorno;
	}

	/**
	 * Consulta a Ordem Servi�o Unidade pelo id do OS e Tipo (1=ABRIR/REGISTRAR e 3-ENCERRAR)
	 * 
	 * @author S�vio luiz
	 * @date 18/09/2006
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

	private void encerrarComExecucaoComReferencia(Fachada fachada, HttpSession sessao, HttpServletRequest httpServletRequest,
					EncerrarOrdemServicoActionForm encerrarOrdemServicoActionForm, Integer numeroOS){

		// verifica a existencia da cole��o na sess�o
		Collection<OsReferidaRetornoTipo> colecaoOSReferidaRetornoTipo = (Collection<OsReferidaRetornoTipo>) sessao
						.getAttribute("colecaoOSReferidaRetornoTipo");

		// caso n�o exista ent�o pesquisa na base
		if(colecaoOSReferidaRetornoTipo == null || colecaoOSReferidaRetornoTipo.isEmpty()){
			FiltroOSReferidaRetornoTipo filtroOSReferidaRetornoTipo = new FiltroOSReferidaRetornoTipo(FiltroOSReferidaRetornoTipo.DESCRICAO);
			filtroOSReferidaRetornoTipo.adicionarParametro(new ParametroSimples(FiltroOSReferidaRetornoTipo.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroOSReferidaRetornoTipo.adicionarParametro(new ParametroSimples(FiltroOSReferidaRetornoTipo.ID_SERVICO_TIPO_REFERENCIA,
							encerrarOrdemServicoActionForm.getTipoServicoReferenciaId()));

			colecaoOSReferidaRetornoTipo = fachada.pesquisar(filtroOSReferidaRetornoTipo, OsReferidaRetornoTipo.class.getName());
			sessao.setAttribute("colecaoOSReferidaRetornoTipo", colecaoOSReferidaRetornoTipo);

		}else{

			// verifica se foi escolhida um tipo de retorno referida
			if(encerrarOrdemServicoActionForm.getIdTipoRetornoReferida() != null
							&& !encerrarOrdemServicoActionForm.getIdTipoRetornoReferida().equals("")){
				Integer idTipoRetornoReferida = Util.converterStringParaInteger(encerrarOrdemServicoActionForm.getIdTipoRetornoReferida());
				Iterator<OsReferidaRetornoTipo> iteratorOSReferidaRetorno = colecaoOSReferidaRetornoTipo.iterator();

				while(iteratorOSReferidaRetorno.hasNext()){

					OsReferidaRetornoTipo osReferidaRetornoTipo = iteratorOSReferidaRetorno.next();

					// procura pelo id o objeto que foi escolhido na cole��o que est� na sess�o
					if(osReferidaRetornoTipo.getId() != null && osReferidaRetornoTipo.getId().equals(idTipoRetornoReferida)){

						encerrarOrdemServicoActionForm.setIndicadorDeferimento("" + osReferidaRetornoTipo.getIndicadorDeferimento());

						// 9.1 indicador de deferimento igual a sim(1)
						if(osReferidaRetornoTipo.getIndicadorDeferimento() == OsReferidaRetornoTipo.INDICADOR_DEFERIMENTO_SIM){

							encerrarOrdemServicoActionForm.setServicoTipoObrigatorio("SIM");

							encerrarOrdemServicoActionForm.setIndicadorTrocaServico("" + osReferidaRetornoTipo.getIndicadorTrocaServico());

							// 9.1.2 indicador de servi�o de troca igual a sim(1)
							if(osReferidaRetornoTipo.getIndicadorTrocaServico() == OsReferidaRetornoTipo.INDICADOR_TROCA_SERVICO_SIM){

								// 9.1.2.1 caso a ordem de servi�o tenha refer�ncia
								if(encerrarOrdemServicoActionForm.getNumeroOSReferencia() != null
												&& !encerrarOrdemServicoActionForm.getNumeroOSReferencia().equals("")){

									Collection colecaoServicoTipo = fachada.pesquisarColecaoServicoTipo(numeroOS);
									if(colecaoServicoTipo != null && !colecaoServicoTipo.isEmpty()){
										sessao.setAttribute("colecaoServicoTipo", colecaoServicoTipo);
									}
								}
								// 9.1.3 servi�o tipo n�o � obrigat�rio ent�o n�o mostra o label
							}else{
								encerrarOrdemServicoActionForm.setServicoTipoObrigatorio("NAO");
								sessao.removeAttribute("colecaoServicoTipo");
							}

							// 9.2 indicador de deferimento igual a n�o(2)
						}else{

							// [FS0003] - Alerta de Indeferimento
							httpServletRequest.setAttribute("atencaoIndeferimento", "O Tipo de Retorno selecionado � de indeferimento.");
							encerrarOrdemServicoActionForm.setServicoTipoObrigatorio("NAO");
							sessao.removeAttribute("colecaoServicoTipo");
						}
						break;
					}

				}
			}else{
				encerrarOrdemServicoActionForm.setServicoTipoObrigatorio("");
				encerrarOrdemServicoActionForm.setIndicadorDeferimento("");
				encerrarOrdemServicoActionForm.setIdServicoTipo("");
				encerrarOrdemServicoActionForm.setDescricaoServicoTipo("");
				encerrarOrdemServicoActionForm.setPavimento("");
				encerrarOrdemServicoActionForm.setIdTipoRetornoReferida("");
				sessao.removeAttribute("colecaoServicoTipo");
			}
		}
	}

	/**
	 * Consulta a ordem de servi�o pelo id informado.
	 * Retorna true caso a OS seja � do tipo fiscaliza�ao ou vistoria.
	 * 
	 * @author Ailton Junior
	 * @created 19/05/2011
	 */
	private boolean isOSFiscalizacaoOuVistoria(Integer id){

		Fachada fachada = Fachada.getInstancia();
		if(Util.isVazioOuBranco(id)){
			return false;
		}
		OrdemServico ordemServico = fachada.consultarDadosOrdemServico(id);
		if(ordemServico == null){
			throw new ActionServletException("atencao.naocadastrado", null, "Ordem de Servi�o");
		}

		if(ordemServico.getServicoTipo() != null
						&& (ordemServico.getServicoTipo().getIndicadorFiscalizacaoInfracao() == ConstantesSistema.INDICADOR_USO_ATIVO
										.shortValue()
										|| ConstantesSistema.INDICADOR_USO_ATIVO.equals(ordemServico.getServicoTipo()
														.getIndicadorFiscalizacaoSituacao()) || ordemServico.getServicoTipo()
										.getIndicadorVistoria() == ConstantesSistema.INDICADOR_USO_ATIVO.shortValue())){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * Pesquisar uma atividade ap�s teclar ENTER pelo id informado
	 * 
	 * @date 25/11/2011
	 */
	private Atividade pesquisarAtividade(EncerrarOrdemServicoActionForm form, Fachada fachada, HttpServletRequest httpServletRequest){

		Atividade retorno = null;

		FiltroAtividade filtroAtividade = new FiltroAtividade();
		filtroAtividade.adicionarParametro(new ParametroSimples(FiltroAtividade.ID, form.getIdAtividade()));
		filtroAtividade.adicionarParametro(new ParametroSimples(FiltroAtividade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoAtividade = fachada.pesquisar(filtroAtividade, Atividade.class.getName());

		if(Util.isVazioOrNulo(colecaoAtividade)){

			form.setIdAtividade("");
			form.setDescricaoAtividade("Atividade inexistente");
			httpServletRequest.setAttribute("corAtividade", "exception");
			httpServletRequest.setAttribute("nomeCampo", "idAtividade");

		}else{

			Atividade atividade = (Atividade) Util.retonarObjetoDeColecao(colecaoAtividade);

			form.setIdAtividade(atividade.getId().toString());
			form.setDescricaoAtividade(atividade.getDescricao());
			httpServletRequest.setAttribute("nomeCampo", "idAtividade");
			retorno = atividade;
		}

		return retorno;
	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Servi�o
	 * O sistema apresenta uma lista de horas executadas das equipes associadas � ordem de servi�o
	 * atividade para a data informada.
	 * 
	 * @date 25/11/2011
	 */
	private void montarListaHelperAtividades(HttpSession sessao, Collection<Atividade> colecaoAtividades, Integer numeroOS, Fachada fachada){

		Collection<ManterDadosAtividadesOrdemServicoHelper> colecaoManterDadosAtividadesOrdemServicoHelper = null;

		if(sessao.getAttribute("colecaoManterDadosAtividadesOrdemServicoHelper") != null){

			colecaoManterDadosAtividadesOrdemServicoHelper = (Collection) sessao
							.getAttribute("colecaoManterDadosAtividadesOrdemServicoHelper");
		}else{

			colecaoManterDadosAtividadesOrdemServicoHelper = new ArrayList<ManterDadosAtividadesOrdemServicoHelper>();
		}

		for(Atividade atividade : colecaoAtividades){

			FiltroOrdemServicoAtividade filtroOrdemServicoAtividade = new FiltroOrdemServicoAtividade();
			filtroOrdemServicoAtividade
							.adicionarParametro(new ParametroSimples(FiltroOrdemServicoAtividade.ID_ATIVIDADE, atividade.getId()));
			filtroOrdemServicoAtividade.adicionarParametro(new ParametroSimples(FiltroOrdemServicoAtividade.ID_ORDEM_SERVICO, numeroOS));
			filtroOrdemServicoAtividade.adicionarCaminhoParaCarregamentoEntidade(FiltroOrdemServicoAtividade.ATIVIDADE);
			Collection<OrdemServicoAtividade> colecaoOSAtividade = fachada.pesquisar(filtroOrdemServicoAtividade,
							OrdemServicoAtividade.class.getName());

			OrdemServicoAtividade ordemServicoAtividade = (OrdemServicoAtividade) Util.retonarObjetoDeColecao(colecaoOSAtividade);

			// Obt�m as equipes programadas
			Collection<Equipe> colecaoEquipes = fachada.obterEquipesProgramadas(Util.converterStringParaInteger(numeroOS.toString()));
			ManterDadosAtividadesOrdemServicoHelper manterDadosAtividadesOrdemServicoHelper = null;

			if(ordemServicoAtividade != null){

				manterDadosAtividadesOrdemServicoHelper = new ManterDadosAtividadesOrdemServicoHelper();
				manterDadosAtividadesOrdemServicoHelper.setOrdemServicoAtividade(ordemServicoAtividade);

				Collection<OSAtividadePeriodoExecucaoHelper> colecaoOSAtividadePeriodoExecucaoHelper = new ArrayList<OSAtividadePeriodoExecucaoHelper>();

				FiltroOsAtividadePeriodoExecucao filtroOsAtividadePeriodoExecucao = new FiltroOsAtividadePeriodoExecucao();
				filtroOsAtividadePeriodoExecucao.adicionarParametro(new ParametroSimples(
								FiltroOsAtividadePeriodoExecucao.ID_ORDEM_SERVICO_ATIVIDADE, ordemServicoAtividade.getId()));

				Collection<OsAtividadePeriodoExecucao> colecaoOsAtividadePeriodoExecucao = fachada.pesquisar(
								filtroOsAtividadePeriodoExecucao, OsAtividadePeriodoExecucao.class.getName());

				// Caso j� exista alguma OsAtividadePeriodoExecucao associado a atividade da OS
				if(!Util.isVazioOrNulo(colecaoOsAtividadePeriodoExecucao)){

					for(OsAtividadePeriodoExecucao osAtividadePeriodoExecucao : colecaoOsAtividadePeriodoExecucao){

						OSAtividadePeriodoExecucaoHelper helper = new OSAtividadePeriodoExecucaoHelper();
						helper.setOsAtividadePeriodoExecucao(osAtividadePeriodoExecucao);

						if(!Util.isVazioOrNulo(colecaoEquipes)){

							for(Equipe equipe : colecaoEquipes){

								OSExecucaoEquipeHelper osExecucaoEquipeHelper = new OSExecucaoEquipeHelper();
								OsExecucaoEquipe osExecucaoEquipe = new OsExecucaoEquipe();
								osExecucaoEquipe.setEquipe(equipe);
								osExecucaoEquipeHelper.setOsExecucaoEquipe(osExecucaoEquipe);
								helper.setOrdemServicoExecucaoEquipeHelper(osExecucaoEquipeHelper);
								colecaoOSAtividadePeriodoExecucaoHelper.add(helper);
							}
						}
					}

				}

				if(!Util.isVazioOrNulo(colecaoOSAtividadePeriodoExecucaoHelper)){

					manterDadosAtividadesOrdemServicoHelper
									.setColecaoOSAtividadePeriodoExecucaoHelper(colecaoOSAtividadePeriodoExecucaoHelper);
				}

				colecaoManterDadosAtividadesOrdemServicoHelper.add(manterDadosAtividadesOrdemServicoHelper);

			}else{

				ordemServicoAtividade = new OrdemServicoAtividade();
				ordemServicoAtividade.setAtividade(atividade);

				OrdemServico ordemServico = new OrdemServico();
				ordemServico.setId(numeroOS);
				ordemServicoAtividade.setOrdemServico(ordemServico);

				ordemServicoAtividade.setUltimaAlteracao(new Date());

				manterDadosAtividadesOrdemServicoHelper = new ManterDadosAtividadesOrdemServicoHelper();
				manterDadosAtividadesOrdemServicoHelper.setOrdemServicoAtividade(ordemServicoAtividade);
				colecaoManterDadosAtividadesOrdemServicoHelper.add(manterDadosAtividadesOrdemServicoHelper);
			}
		}

		sessao.setAttribute("colecaoManterDadosAtividadesOrdemServicoHelper", colecaoManterDadosAtividadesOrdemServicoHelper);
	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Servi�o
	 * Adiciona um ManterOrdemServicoAtividadeHelper na cole��o de hepers relativo a atividade
	 * informada
	 * 
	 * @date 26/11/2011
	 */
	private void adicionarAtividade(HttpServletRequest httpServletRequest, Fachada fachada, HttpSession sessao,
					EncerrarOrdemServicoActionForm encerrarOrdemServicoActionForm){

		Atividade atividadeAdicionar = this.pesquisarAtividade(encerrarOrdemServicoActionForm, fachada, httpServletRequest);

		if(atividadeAdicionar != null){

			Collection<ManterDadosAtividadesOrdemServicoHelper> colecaoHelperAtividade = null;

			if(sessao.getAttribute("colecaoManterDadosAtividadesOrdemServicoHelper") != null){

				colecaoHelperAtividade = (Collection) sessao.getAttribute("colecaoManterDadosAtividadesOrdemServicoHelper");
			}else{

				colecaoHelperAtividade = new ArrayList<ManterDadosAtividadesOrdemServicoHelper>();
			}

			// [FS0003 - Verificar exist�ncia da atividade]
			if(!Util.isVazioOrNulo(colecaoHelperAtividade)){

				for(ManterDadosAtividadesOrdemServicoHelper atividadeHelper : colecaoHelperAtividade){

					if(atividadeHelper.getOrdemServicoAtividade().getAtividade().getId().intValue() == atividadeAdicionar.getId()
									.intValue()){

						throw new ActionServletException("atencao.atividade_ja_existente");
					}
				}
			}

			Collection<Atividade> atividadesAdicionar = new ArrayList<Atividade>();
			atividadesAdicionar.add(atividadeAdicionar);

			this.montarListaHelperAtividades(sessao, atividadesAdicionar, Integer.valueOf(encerrarOrdemServicoActionForm.getNumeroOS()),
							fachada);

			encerrarOrdemServicoActionForm.setIdAtividade("");
			encerrarOrdemServicoActionForm.setDescricaoAtividade("");
			httpServletRequest.setAttribute("nomeCampo", "idAtividade");
		}else{

			throw new ActionServletException("atencao.atividade.ordem.servico.inexistente");
		}
	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Servi�o
	 * Remove um ManterOrdemServicoAtividadeHelper da cole��o de hepers relativo a atividade
	 * informada
	 * 
	 * @date 26/11/2011
	 */
	private void removerAtividade(HttpSession sessao, String idAtividadeRemocao){

		Atividade remover = new Atividade();
		remover.setId(Integer.valueOf(idAtividadeRemocao));

		if(sessao.getAttribute("colecaoManterDadosAtividadesOrdemServicoHelper") != null){

			Collection colecaoManterDadosAtividadesOrdemServicoHelper = (Collection) sessao
							.getAttribute("colecaoManterDadosAtividadesOrdemServicoHelper");

			Iterator iteratorColecaoManterDadosAtividadesOrdemServicoHelper = colecaoManterDadosAtividadesOrdemServicoHelper.iterator();

			ManterDadosAtividadesOrdemServicoHelper helper = null;

			while(iteratorColecaoManterDadosAtividadesOrdemServicoHelper.hasNext()){

				helper = (ManterDadosAtividadesOrdemServicoHelper) iteratorColecaoManterDadosAtividadesOrdemServicoHelper.next();

				if(helper.getOrdemServicoAtividade().getAtividade().getId().equals(remover.getId())){

					colecaoManterDadosAtividadesOrdemServicoHelper.remove(helper);
					sessao.setAttribute("colecaoManterDadosAtividadesOrdemServicoHelper", colecaoManterDadosAtividadesOrdemServicoHelper);
					break;
				}
			}
		}
	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Servi�o
	 * Remove um ManterOrdemServicoAtividadeHelper da cole��o de hepers relativo ao periodo de
	 * execu��o informado
	 * 
	 * @date 26/11/2011
	 */
	private void removerPeriodoExecucaoEquipe(String identificacaoEquipe, Integer idAtividade,
					Collection colecaoManterOrdemServicoAtividadeHelper, HttpServletRequest httpServletRequest,
					EncerrarOrdemServicoActionForm formulario){

		Iterator iteratorColecaoManterOrdemServicoAtividadeHelper = colecaoManterOrdemServicoAtividadeHelper.iterator();
		ManterDadosAtividadesOrdemServicoHelper manterDadosAtividadesOrdemServicoHelper = null;
		OSAtividadePeriodoExecucaoHelper osAtividadePeriodoExecucaoHelper = null;
		OSExecucaoEquipeHelper osExecucaoEquipeHelper = null;

		// Atividade
		while(iteratorColecaoManterOrdemServicoAtividadeHelper.hasNext()){

			manterDadosAtividadesOrdemServicoHelper = (ManterDadosAtividadesOrdemServicoHelper) iteratorColecaoManterOrdemServicoAtividadeHelper
							.next();

			if(manterDadosAtividadesOrdemServicoHelper.getOrdemServicoAtividade() != null
							&& manterDadosAtividadesOrdemServicoHelper.getOrdemServicoAtividade().getAtividade().getId()
											.equals(idAtividade)){

				// Per�odo
				Collection colecaoOSAtividadePeriodoExecucaoHelper = manterDadosAtividadesOrdemServicoHelper
								.getColecaoOSAtividadePeriodoExecucaoHelper();

				Collection colecaoOSAtividadePeriodoExecucaoRemovida = new ArrayList<OSAtividadePeriodoExecucaoHelper>();

				Iterator iteratorColecaoOSAtividadePeriodoExecucaoHelper = colecaoOSAtividadePeriodoExecucaoHelper.iterator();

				while(iteratorColecaoOSAtividadePeriodoExecucaoHelper.hasNext()){

					osAtividadePeriodoExecucaoHelper = (OSAtividadePeriodoExecucaoHelper) iteratorColecaoOSAtividadePeriodoExecucaoHelper
									.next();

					// Equipe
					osExecucaoEquipeHelper = (OSExecucaoEquipeHelper) osAtividadePeriodoExecucaoHelper
									.getOrdemServicoExecucaoEquipeHelper();

					if(Util.formatarDataComHora(osExecucaoEquipeHelper.getOsExecucaoEquipe().getEquipe().getUltimaAlteracao()).equals(
									identificacaoEquipe)){

						colecaoOSAtividadePeriodoExecucaoRemovida.add(osAtividadePeriodoExecucaoHelper);
					}
				}

				manterDadosAtividadesOrdemServicoHelper.getColecaoOSAtividadePeriodoExecucaoHelper().removeAll(
								colecaoOSAtividadePeriodoExecucaoRemovida);

			}
		}

		// Preparando o formul�rio para uma nova requisi��o do usu�rio
		formulario.setHoraInicioExecucao("");
		formulario.setHoraFimExecucao("");
		formulario.setIdEquipeProgramada("");
		formulario.setIdEquipeNaoProgramada("");
		formulario.setDescricaoEquipeNaoProgramada("");
		httpServletRequest.setAttribute("nomeCampo", "horaInicioExecucao");
	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Servi�o
	 * Atualiza a cole��o de ManterOrdemServicoAtividadeHelper com o periodo de execu��o informado
	 * 
	 * @date 25/11/2011
	 */
	private void atualizarManterOrdemServicoAtividadeHelper(
					Collection<ManterDadosAtividadesOrdemServicoHelper> colecaoOrdemServicoAtividadeHelper, Integer numeroOS,
					Integer idAtividade, String dataExecucao, String horaInicio, String horaFim, Integer idEquipe, HttpSession sessao,
					Fachada fachada, String indicadorEquipeProgramada){

		if(!Util.isVazioOrNulo(colecaoOrdemServicoAtividadeHelper)){

			for(ManterDadosAtividadesOrdemServicoHelper manterDadosAtividadesOrdemServicoHelper : colecaoOrdemServicoAtividadeHelper){

				// Caso a atividade seja igual � atividade selecionada
				if(manterDadosAtividadesOrdemServicoHelper.getOrdemServicoAtividade().getAtividade().getId().equals(idAtividade)){

					// Atualiza a cole��o de hepers com o per�odo execu��o informado
					Collection colecaoOsAtividadePeriodoExecucaoHelper = this.informarOsAtividadePeriodoExecucao(
									manterDadosAtividadesOrdemServicoHelper.getColecaoOSAtividadePeriodoExecucaoHelper(), dataExecucao,
									horaInicio, horaFim, idEquipe, fachada, manterDadosAtividadesOrdemServicoHelper
													.getOrdemServicoAtividade(), indicadorEquipeProgramada, sessao);

					manterDadosAtividadesOrdemServicoHelper
									.setColecaoOSAtividadePeriodoExecucaoHelper(colecaoOsAtividadePeriodoExecucaoHelper);
					sessao.setAttribute("colecaoManterDadosAtividadesOrdemServicoHelper", colecaoOrdemServicoAtividadeHelper);
					break;
				}
			}
		}

	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Servi�o
	 * Atualiza a cole��o de OsAtividadePeriodoExecucaoHelper com o periodo de execu��o informado
	 * 
	 * @date 26/11/2011
	 */
	private Collection<OSAtividadePeriodoExecucaoHelper> informarOsAtividadePeriodoExecucao(
					Collection<OSAtividadePeriodoExecucaoHelper> colecaoOsAtividadePeriodoExecucaoHelper, String dataExecucao,
					String horaInicio, String horaFim, Integer idEquipe, Fachada fachada, OrdemServicoAtividade ordemServicoAtividade,
					String indicadorEquipeProgramada, HttpSession sessao){

		Collection colecaoRetorno = null;
		OSAtividadePeriodoExecucaoHelper osAtividadePeriodoExecucaoHelper = null;

		if(!Util.isVazioOrNulo(colecaoOsAtividadePeriodoExecucaoHelper)){

			// Gerar o helper do per�odo de execu��o informado e atualiza a cole��o
			osAtividadePeriodoExecucaoHelper = this.gerarOsAtividadePeriodoExecucao(colecaoOsAtividadePeriodoExecucaoHelper, dataExecucao,
							horaInicio, horaFim, idEquipe, fachada, ordemServicoAtividade, indicadorEquipeProgramada, sessao);

			colecaoOsAtividadePeriodoExecucaoHelper.add(osAtividadePeriodoExecucaoHelper);

			return colecaoOsAtividadePeriodoExecucaoHelper;
		}else{

			// Gerar o helper do per�odo de execu��o informado, cria a cole��o e adiciona-o nela
			osAtividadePeriodoExecucaoHelper = this.gerarOsAtividadePeriodoExecucao(colecaoOsAtividadePeriodoExecucaoHelper, dataExecucao,
							horaInicio, horaFim, idEquipe, fachada, ordemServicoAtividade, indicadorEquipeProgramada, sessao);
			colecaoRetorno = new ArrayList();
			colecaoRetorno.add(osAtividadePeriodoExecucaoHelper);

			return colecaoRetorno;
		}
	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Servi�o
	 * Este m�todo tem por finalidade gerar um helper do per�odo de execu��o informado para a
	 * atividade de um OS
	 * 
	 * @date 26/11/2011
	 */
	private OSAtividadePeriodoExecucaoHelper gerarOsAtividadePeriodoExecucao(
					Collection<OSAtividadePeriodoExecucaoHelper> colecaoOsAtividadePeriodoExecucaoHelper, String dataExecucao,
					String horaInicio, String horaFim, Integer idEquipe, Fachada fachada, OrdemServicoAtividade ordemServicoAtividade,
					String indicadorEquipeProgramada, HttpSession sessao){

		OSAtividadePeriodoExecucaoHelper osAtividadePeriodoExecucaoHelper = new OSAtividadePeriodoExecucaoHelper();

		OsAtividadePeriodoExecucao osAtividadePeriodoExecucao = new OsAtividadePeriodoExecucao();

		// Gerando uma String no formato dd/MM/yyyy HH:mm:ss
		String dataHoraExecucaoInicio = dataExecucao + " " + horaInicio + ":00";
		String dataHoraExecucaoFim = dataExecucao + " " + horaFim + ":00";

		osAtividadePeriodoExecucao.setDataInicio(Util.converteStringParaDateHora(dataHoraExecucaoInicio));
		osAtividadePeriodoExecucao.setDataFim(Util.converteStringParaDateHora(dataHoraExecucaoFim));
		osAtividadePeriodoExecucao.setOrdemServicoAtividade(ordemServicoAtividade);
		osAtividadePeriodoExecucao.setUltimaAlteracao(new Date());

		osAtividadePeriodoExecucaoHelper.setOsAtividadePeriodoExecucao(osAtividadePeriodoExecucao);

		// Informando OsExecucaoEquipe
		OSExecucaoEquipeHelper ordermServicoExecucaoEquipeHelper = this.informarOsExecucaoEquipe(colecaoOsAtividadePeriodoExecucaoHelper,
						idEquipe, fachada, osAtividadePeriodoExecucaoHelper, indicadorEquipeProgramada, sessao);

		osAtividadePeriodoExecucaoHelper.setOrdemServicoExecucaoEquipeHelper(ordermServicoExecucaoEquipeHelper);

		return osAtividadePeriodoExecucaoHelper;

	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Servi�o
	 * Este m�todo tem por finalidade atualizar o helper do per�odo de execu��o com adi��o da equipe
	 * informada
	 * 
	 * @date 26/11/2011
	 */
	private OSExecucaoEquipeHelper informarOsExecucaoEquipe(Collection<OSAtividadePeriodoExecucaoHelper> colecaoPeriodoHelper,
					Integer idEquipe, Fachada fachada, OSAtividadePeriodoExecucaoHelper osAtividadePeriodoExecucaoHelper,
					String indicadorEquipeProgramada, HttpSession sessao){

		OSExecucaoEquipeHelper retorno = null;
		boolean dataInicialDentroPeriodo = false;
		boolean dataFinalDentroPeriodo = false;

		if(!Util.isVazioOrNulo(colecaoPeriodoHelper)){

			Iterator iteratorColecaoPeriodoEquipeHelper = colecaoPeriodoHelper.iterator();

			while(iteratorColecaoPeriodoEquipeHelper.hasNext()){

				OSAtividadePeriodoExecucaoHelper periodoHelper = (OSAtividadePeriodoExecucaoHelper) iteratorColecaoPeriodoEquipeHelper
								.next();

				if(osAtividadePeriodoExecucaoHelper.getOsAtividadePeriodoExecucao().getDataInicio().compareTo(
								periodoHelper.getOsAtividadePeriodoExecucao().getDataInicio()) > 0
								|| osAtividadePeriodoExecucaoHelper.getOsAtividadePeriodoExecucao().getDataInicio().compareTo(
												periodoHelper.getOsAtividadePeriodoExecucao().getDataInicio()) == 0){
					dataInicialDentroPeriodo = true;
				}

				if(osAtividadePeriodoExecucaoHelper.getOsAtividadePeriodoExecucao().getDataFim().compareTo(
								periodoHelper.getOsAtividadePeriodoExecucao().getDataFim()) < 0
								|| osAtividadePeriodoExecucaoHelper.getOsAtividadePeriodoExecucao().getDataFim().compareTo(
												periodoHelper.getOsAtividadePeriodoExecucao().getDataFim()) == 0){
					dataFinalDentroPeriodo = true;
				}

				/*
				 * Verifica se j� existe na cole��o uma OSExecucaoEquipeHelper com a mesma equipe
				 * [FS0004] - Verificar horas apropriada j� existente
				 */
				if(periodoHelper.getOrdemServicoExecucaoEquipeHelper().getOsExecucaoEquipe().getEquipe().getId().equals(idEquipe)
								&& (dataInicialDentroPeriodo && dataFinalDentroPeriodo)){

					throw new ActionServletException("atencao.data_periodo_equipe_ja_informado");
				}
			}

			/*
			 * Caso j� exista OSExecucaoEquipeHelper informada, por�m ainda n�o foi cadastrada
			 * nenhuma
			 * com a mesma equipe
			 */
			retorno = this.gerarOsExecucaoEquipe(idEquipe, fachada, sessao);

			if(indicadorEquipeProgramada.equals(ConstantesSistema.SIM.toString())){

				retorno.setIndicadorEquipeProgramada(ConstantesSistema.SIM.toString());
			}else{

				retorno.setIndicadorEquipeProgramada(ConstantesSistema.NAO.toString());
			}

			return retorno;

		}else{

			// Primeira OsExecucaoEquipe informada
			retorno = this.gerarOsExecucaoEquipe(idEquipe, fachada, sessao);

			if(indicadorEquipeProgramada.equals(ConstantesSistema.SIM.toString())){

				retorno.setIndicadorEquipeProgramada(ConstantesSistema.SIM.toString());
			}else{

				retorno.setIndicadorEquipeProgramada(ConstantesSistema.NAO.toString());
			}

			return retorno;
		}
	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Servi�o
	 * Este m�todo tem por finalidade gerar um helper da Equipe de Execu��o do Per�odo Informado
	 * para a OS
	 * 
	 * @date 26/11/2011
	 */
	private OSExecucaoEquipeHelper gerarOsExecucaoEquipe(Integer idEquipe, Fachada fachada, HttpSession sessao){

		OSExecucaoEquipeHelper osExecucaoEquipeHelper = new OSExecucaoEquipeHelper();

		FiltroEquipe filtroEquipe = new FiltroEquipe();
		filtroEquipe.adicionarParametro(new ParametroSimples(FiltroEquipe.ID, idEquipe));
		Collection colecaoEquipe = fachada.pesquisar(filtroEquipe, Equipe.class.getName());

		Equipe equipe = (Equipe) Util.retonarObjetoDeColecao(colecaoEquipe);

		// Alterado para facilitar a identifica��o de uma equipe na cole��o
		equipe.setUltimaAlteracao(new Date());

		OsExecucaoEquipe osExecucaoEquipe = new OsExecucaoEquipe();
		osExecucaoEquipe.setEquipe(equipe);

		if(sessao.getAttribute("osProgramacao") != null){

			osExecucaoEquipe.setOrdemServicoProgramacao((OrdemServicoProgramacao) sessao.getAttribute("osProgramacao"));
		}

		osExecucaoEquipeHelper.setOsExecucaoEquipe(osExecucaoEquipe);

		return osExecucaoEquipeHelper;

	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Servi�o
	 * Este m�todo tem por finalidade a realiza��o a inclus�o de um per�odo de execu��o para uma
	 * determinada equipe
	 * 
	 * @date 26/11/2011
	 */
	private void adicionarPeriodoExecucao(HttpServletRequest httpServletRequest, Fachada fachada, HttpSession sessao,
					EncerrarOrdemServicoActionForm encerrarOrdemServicoActionForm){

		// Equipe
		Integer idEquipe = null;
		String indicadorEquipeProgramada = "1";
		if(!Util.isVazioOuBranco(encerrarOrdemServicoActionForm.getIdEquipeProgramada())){

			idEquipe = Util.converterStringParaInteger(encerrarOrdemServicoActionForm.getIdEquipeProgramada());
		}else{

			idEquipe = Util.converterStringParaInteger(encerrarOrdemServicoActionForm.getIdEquipeNaoProgramada());
			indicadorEquipeProgramada = "2";
		}

		fachada.verificaDadosAdicionarPeriodoEquipe(encerrarOrdemServicoActionForm.getDataExecucao(), encerrarOrdemServicoActionForm
						.getHoraInicioExecucao(), encerrarOrdemServicoActionForm.getHoraFimExecucao(), idEquipe,
						encerrarOrdemServicoActionForm.getDataEncerramento(), Util
										.converterStringParaInteger(encerrarOrdemServicoActionForm.getNumeroOS()));

		Collection colecaoManterDadosAtividadesOrdemServicoHelper = (Collection) sessao
						.getAttribute("colecaoManterDadosAtividadesOrdemServicoHelper");

		this.atualizarManterOrdemServicoAtividadeHelper(colecaoManterDadosAtividadesOrdemServicoHelper, Util
						.converterStringParaInteger(encerrarOrdemServicoActionForm.getNumeroOS()), Util
						.converterStringParaInteger(encerrarOrdemServicoActionForm.getIdAtividadeSelecionada()),
						encerrarOrdemServicoActionForm.getDataExecucao(), encerrarOrdemServicoActionForm.getHoraInicioExecucao(),
						encerrarOrdemServicoActionForm.getHoraFimExecucao(), idEquipe, sessao, fachada, indicadorEquipeProgramada);

		// Inicializando dados do formul�rio para uma nova inclus�o
		encerrarOrdemServicoActionForm.setHoraInicioExecucao("");
		encerrarOrdemServicoActionForm.setHoraFimExecucao("");
		encerrarOrdemServicoActionForm.setIdEquipeProgramada("");
		encerrarOrdemServicoActionForm.setIdEquipeNaoProgramada("");
		encerrarOrdemServicoActionForm.setDescricaoEquipeNaoProgramada("");

		httpServletRequest.setAttribute("nomeCampo", "horaInicioExecucao");
	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Servi�o
	 * Este m�todo tem por finalidade a realiza��o da manuten��o das horas de execu��o/interrupe��o
	 * e materias das atividades de uma Ordem de Servi�o
	 * 
	 * @date 24/11/2011
	 */
	private boolean manterDadosAtividades(HttpServletRequest httpServletRequest, Fachada fachada, HttpSession sessao,
					EncerrarOrdemServicoActionForm encerrarOrdemServicoActionForm, Integer numeroOS, String dataEncerramento){

		boolean retorno = false;

		/*
		 * Apresentar na data de execu��o a data do roteiro caso tenha sido informada, caso
		 * contr�rio informar a data do encerramento
		 */
		String dataRoteiro = httpServletRequest.getParameter("dataRoteiro");

		if(!Util.isVazioOuBranco(dataRoteiro)){

			encerrarOrdemServicoActionForm.setDataExecucao(dataRoteiro);
			httpServletRequest.setAttribute("nomeCampo", "horaInicioExecucao");
			sessao.setAttribute("desabilitarDataExecucao", "OK");
		}else{

			if(!Util.isVazioOuBranco(dataEncerramento) && Util.isVazioOuBranco(encerrarOrdemServicoActionForm.getDataExecucao())){

				encerrarOrdemServicoActionForm.setDataExecucao(dataEncerramento);
			}else if(Util.isVazioOuBranco(encerrarOrdemServicoActionForm.getDataExecucao())){

				encerrarOrdemServicoActionForm.setDataExecucao(encerrarOrdemServicoActionForm.getDataEncerramento());
			}

			httpServletRequest.setAttribute("nomeCampo", "dataExecucao");
			sessao.removeAttribute("desabilitarDataExecucao");
		}

		// Equipes Programadas
		Collection colecaoEquipes = fachada.obterEquipesProgramadas(numeroOS);
		if(!Util.isVazioOrNulo(colecaoEquipes)){

			sessao.setAttribute("colecaoEquipe", colecaoEquipes);
		}

		// Caso a OS esteja associada a um documento de cobran�a
		sessao.removeAttribute("documentoCobranca");
		sessao.removeAttribute("registroAtendimento");
		sessao.removeAttribute("colecaoEquipesPorUnidade");

		if(fachada.verificarOSAssociadaDocumentoCobranca(numeroOS)){

			sessao.setAttribute("documentoCobranca", "OK");
		}else if(fachada.verificarOSAssociadaRA(numeroOS)){

			/*
			 * Caso a OS esteja associada a um RA. Pesquisa o �ltimo tramite do RA para obter a
			 * unidade de destino
			 */
			UnidadeOrganizacional unidadeDestino = fachada.obterUnidadeDestinoUltimoTramite(numeroOS);
			if(unidadeDestino != null){

				// Obt�m todas as equipes pertencentens a unidade de destino
				Collection colecaoEquipesPorUnidade = fachada.obterEquipesPorUnidade(unidadeDestino.getId());

				if(!Util.isVazioOrNulo(colecaoEquipesPorUnidade)){

					sessao.setAttribute("registroAtendimento", "OK");
					sessao.setAttribute("colecaoEquipesPorUnidade", colecaoEquipesPorUnidade);
				}
			}
		}

		// Pesquisar Equipe n�o Programada pela tecla ENTER
		if(!Util.isVazioOuBranco(encerrarOrdemServicoActionForm.getIdEquipeNaoProgramada())
						&& Util.isVazioOuBranco(encerrarOrdemServicoActionForm.getDescricaoEquipeNaoProgramada())){

			pesquisarEquipe(httpServletRequest, fachada, encerrarOrdemServicoActionForm);
		}

		// Adicionar Per�odo Execu��o
		String adicionarPeriodoEquipe = httpServletRequest.getParameter("adicionarPeriodoEquipe");

		if(adicionarPeriodoEquipe != null && !adicionarPeriodoEquipe.equalsIgnoreCase("")){

			if(!Util.isVazioOuBranco(encerrarOrdemServicoActionForm.getIdAtividadeSelecionada())){

				adicionarPeriodoExecucao(httpServletRequest, fachada, sessao, encerrarOrdemServicoActionForm);
				retorno = true;
			}
		}

		// Remover Per�odo Execu��o
		String removerPeriodoEquipe = httpServletRequest.getParameter("removerPeriodoEquipe");

		if(removerPeriodoEquipe != null && !removerPeriodoEquipe.equalsIgnoreCase("")){

			Collection colecaoManterDadosAtividadesOrdemServicoHelperSessao = (Collection) sessao
							.getAttribute("colecaoManterDadosAtividadesOrdemServicoHelper");

			this.removerPeriodoExecucaoEquipe(removerPeriodoEquipe, Util.converterStringParaInteger(encerrarOrdemServicoActionForm
							.getIdAtividadeSelecionada()), colecaoManterDadosAtividadesOrdemServicoHelperSessao, httpServletRequest,
							encerrarOrdemServicoActionForm);
			retorno = true;
		}

		// Adicionar Interrup��o Execu��o
		String adicionarInterrupcao = httpServletRequest.getParameter("adicionarInterrupcao");

		if(!Util.isVazioOuBranco(adicionarInterrupcao)){

			adicionarInterrupcao((Collection<ManterDadosAtividadesOrdemServicoHelper>) sessao
							.getAttribute("colecaoManterDadosAtividadesOrdemServicoHelper"), httpServletRequest, fachada, sessao,
							encerrarOrdemServicoActionForm);

			// Preparando o formul�rio para uma nova requisi��o do usu�rio
			encerrarOrdemServicoActionForm.setHoraInicioExecucao("");
			encerrarOrdemServicoActionForm.setHoraFimExecucao("");
			encerrarOrdemServicoActionForm.setIdEquipeProgramada("");
			encerrarOrdemServicoActionForm.setIdEquipeNaoProgramada("");
			encerrarOrdemServicoActionForm.setDescricaoEquipeNaoProgramada("");
			retorno = true;
		}

		// Remover Interrupcao Execu��o
		String removerInterrupcao = httpServletRequest.getParameter("removerInterrupcao");

		if(!Util.isVazioOuBranco(removerInterrupcao)){

			removerInterrupcao((Collection<ManterDadosAtividadesOrdemServicoHelper>) sessao
							.getAttribute("colecaoManterDadosAtividadesOrdemServicoHelper"), encerrarOrdemServicoActionForm,
							removerInterrupcao, httpServletRequest);
			retorno = true;
		}

		// Pesquisar Motivo Interrup��o Execu��o/Deslocamento
		Collection<MotivoInterrupcao> colecaoMotivoInterrupcao = fachada.pesquisarMotivoInterrupcao();
		sessao.setAttribute("colecaoMotivoInterrupcao", colecaoMotivoInterrupcao);

		// Material Programado
		Collection colecaoMaterialProgramado = fachada.obterMateriaisProgramados(numeroOS);
		if(colecaoMaterialProgramado != null && !colecaoMaterialProgramado.isEmpty()){

			sessao.setAttribute("colecaoMaterialProgramado", colecaoMaterialProgramado);
		}

		// Pesquisar Material n�o Programado pela tecla ENTER
		if(!Util.isVazioOuBranco(encerrarOrdemServicoActionForm.getIdMaterialNaoProgramado())){

			FiltroMaterial filtroMaterial = new FiltroMaterial();
			filtroMaterial.adicionarParametro(new ParametroSimples(FiltroMaterial.ID, encerrarOrdemServicoActionForm
							.getIdMaterialNaoProgramado()));
			filtroMaterial.adicionarParametro(new ParametroSimples(FiltroMaterial.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection colecaoMaterialEncontrado = fachada.pesquisar(filtroMaterial, Material.class.getName());

			if(Util.isVazioOrNulo(colecaoMaterialEncontrado)){

				encerrarOrdemServicoActionForm.setIdMaterialNaoProgramado("");
				encerrarOrdemServicoActionForm.setDescricaoMaterialNaoProgramado("MATERIAL INEXISTENTE");
				httpServletRequest.setAttribute("corMaterial", "exception");
				httpServletRequest.setAttribute("nomeCampo", "idMaterialNaoProgramado");

			}else{

				Material material = (Material) Util.retonarObjetoDeColecao(colecaoMaterialEncontrado);
				encerrarOrdemServicoActionForm.setIdMaterialNaoProgramado(material.getId().toString());
				encerrarOrdemServicoActionForm.setDescricaoMaterialNaoProgramado(material.getDescricao());
				httpServletRequest.setAttribute("nomeCampo", "botaoAdicionar");

			}
		}

		// Adicionar Material
		String adicionarMaterial = httpServletRequest.getParameter("adicionarMaterial");

		if(!Util.isVazioOuBranco(adicionarMaterial)){

			Collection<ManterDadosAtividadesOrdemServicoHelper> colecaoHelper = (Collection<ManterDadosAtividadesOrdemServicoHelper>) sessao
							.getAttribute("colecaoManterDadosAtividadesOrdemServicoHelper");

			if(!Util.isVazioOuBranco(encerrarOrdemServicoActionForm.getIdMaterialProgramado())){

				for(ManterDadosAtividadesOrdemServicoHelper helper : colecaoHelper){

					if(helper.getOrdemServicoAtividade().getAtividade().getId().toString().equals(
									encerrarOrdemServicoActionForm.getIdAtividadeSelecionada())){

						this.informarOsAtividadeMaterialExecucao(helper, numeroOS, Util.obterInteger(encerrarOrdemServicoActionForm
										.getIdMaterialProgramado()), fachada, encerrarOrdemServicoActionForm);
						break;
					}
				}

				sessao.setAttribute("colecaoManterDadosAtividadesOrdemServicoHelper", colecaoHelper);

			}else{

				if(!Util.isVazioOuBranco(encerrarOrdemServicoActionForm.getIdMaterialNaoProgramado())){

					for(ManterDadosAtividadesOrdemServicoHelper helper : colecaoHelper){

						if(helper.getOrdemServicoAtividade().getAtividade().getId().toString().equals(
										encerrarOrdemServicoActionForm.getIdAtividadeSelecionada())){

							this.informarOsAtividadeMaterialExecucao(helper, numeroOS, Util.obterInteger(encerrarOrdemServicoActionForm
											.getIdMaterialNaoProgramado()), fachada, encerrarOrdemServicoActionForm);
							break;
						}
					}

				}
			}

			retorno = true;
		}

		// Remover Material
		String removerMaterial = httpServletRequest.getParameter("removerMaterial");

		if(!Util.isVazioOuBranco(removerMaterial)){

			removerMaterial((Collection<ManterDadosAtividadesOrdemServicoHelper>) sessao
							.getAttribute("colecaoManterDadosAtividadesOrdemServicoHelper"), encerrarOrdemServicoActionForm,
							removerMaterial, httpServletRequest);
			retorno = true;
		}

		return retorno;
	}

	/**
	 * Este m�todo tem por finalidade pesquisar uma equipe
	 * 
	 * @date 24/11/2011
	 */
	private void pesquisarEquipe(HttpServletRequest httpServletRequest, Fachada fachada,
					EncerrarOrdemServicoActionForm encerrarOrdemServicoActionForm){

		FiltroEquipe filtroEquipe = new FiltroEquipe();

		filtroEquipe.adicionarParametro(new ParametroSimples(FiltroEquipe.ID, encerrarOrdemServicoActionForm.getIdEquipeNaoProgramada()));

		filtroEquipe.adicionarParametro(new ParametroSimples(FiltroEquipe.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoEquipe = fachada.pesquisar(filtroEquipe, Equipe.class.getName());

		if(colecaoEquipe == null || colecaoEquipe.isEmpty()){

			encerrarOrdemServicoActionForm.setIdEquipeNaoProgramada("");
			encerrarOrdemServicoActionForm.setDescricaoEquipeNaoProgramada("EQUIPE INEXISTENTE");

			httpServletRequest.setAttribute("corEquipe", "exception");
			httpServletRequest.setAttribute("nomeCampo", "idEquipeNaoProgramada");

		}else{

			Equipe equipe = (Equipe) Util.retonarObjetoDeColecao(colecaoEquipe);

			encerrarOrdemServicoActionForm.setIdEquipeNaoProgramada(equipe.getId().toString());
			encerrarOrdemServicoActionForm.setDescricaoEquipeNaoProgramada(equipe.getNome());

			httpServletRequest.setAttribute("nomeCampo", "botaoAdicionar");
		}
	}

	/**
	 * M�todo respons�vel por realizar a exclus�o de uma interrup��o da cole��o de
	 * ManterDadosAtividadesOrdemServicoHelper.
	 * 
	 * @date 28/11/2011
	 */
	private void removerInterrupcao(Collection colecaoManterOrdemServicoAtividadeHelper, EncerrarOrdemServicoActionForm formulario,
					String identificadorRemocaoInterrupcao, HttpServletRequest httpServletRequest){

		Iterator iteratorColecaoManterOrdemServicoAtividadeHelper = colecaoManterOrdemServicoAtividadeHelper.iterator();
		ManterDadosAtividadesOrdemServicoHelper manterDadosAtividadesOrdemServicoHelper = null;
		OrdemServicoInterrupcaoExecucao ordemServicoInterrupcaoExecucao = null;

		while(iteratorColecaoManterOrdemServicoAtividadeHelper.hasNext()){

			manterDadosAtividadesOrdemServicoHelper = (ManterDadosAtividadesOrdemServicoHelper) iteratorColecaoManterOrdemServicoAtividadeHelper
							.next();

			Collection colecaoInterrupcao = manterDadosAtividadesOrdemServicoHelper.getColecaoInterrupcao();
			Collection colecaoInterrupcaoRemovida = new ArrayList<OrdemServicoInterrupcaoExecucao>();

			Iterator iteratorColecaoInterrupcao = colecaoInterrupcao.iterator();

			while(iteratorColecaoInterrupcao.hasNext()){

				ordemServicoInterrupcaoExecucao = (OrdemServicoInterrupcaoExecucao) iteratorColecaoInterrupcao.next();

				if(Util.formatarDataComHora(ordemServicoInterrupcaoExecucao.getUltimaAlteracao()).equals(identificadorRemocaoInterrupcao)){

					colecaoInterrupcaoRemovida.add(ordemServicoInterrupcaoExecucao);
				}
			}

			manterDadosAtividadesOrdemServicoHelper.getColecaoInterrupcao().removeAll(colecaoInterrupcaoRemovida);
		}

		// Preparando o formul�rio para uma nova requisi��o do usu�rio
		formulario.setHoraInicioInterrupcaoExecucao("");
		formulario.setHoraFimInterrupcaoExecucao("");
		formulario.setIdMotivoInterrupcaoExecucao("");
		httpServletRequest.setAttribute("nomeCampo", "horaInicioInterrupcao");
	}

	/**
	 * [UC0461] Manter Dados das Atividades da Ordem de Servi�o
	 * Este m�todo atualiza a cole��o de ManterDadosAtividadesOrdemServicoHelper com um registro de
	 * uma interru��o
	 * 
	 * @date 28/11/2011
	 */
	private void informarInterrupcao(Fachada fachada, HttpSession sessao, HttpServletRequest request, String numeroOS, String idAtividade,
					String dataExecucao, String horaInicioInterrupcao, String horaFimInterrupcao, String idMotivoInterrupcao,
					Collection<ManterDadosAtividadesOrdemServicoHelper> colecaoManterDadosAtividadesOrdemServicoHelper){

		// Gerando uma String no formato dd/MM/yyyy HH:mm:ss
		String strDataHoraInterrupcaoInicio = dataExecucao + " " + horaInicioInterrupcao + ":00";
		String strDataHoraInterrupcaoFim = dataExecucao + " " + horaFimInterrupcao + ":00";
		Date dataHoraInterrupcaoInicio = Util.converteStringParaDateHora(strDataHoraInterrupcaoInicio);
		Date dataHoraInterrupcaoFim = Util.converteStringParaDateHora(strDataHoraInterrupcaoFim);
		boolean intervaloOk = false;

		// Verificar se hora informada est� dentro dos per�odos de execu��o j� informados.
		for(ManterDadosAtividadesOrdemServicoHelper helper : colecaoManterDadosAtividadesOrdemServicoHelper){

			if(idAtividade.equals(helper.getOrdemServicoAtividade().getAtividade().getId().toString())){

				if(!Util.isVazioOrNulo(helper.getColecaoOSAtividadePeriodoExecucaoHelper())){

					for(OSAtividadePeriodoExecucaoHelper periodoExecucao : helper.getColecaoOSAtividadePeriodoExecucaoHelper()){

						String horaInicioExecucao = periodoExecucao.getOsAtividadePeriodoExecucao().getHoraInicioFormatada();

						String horaFimExecucao = periodoExecucao.getOsAtividadePeriodoExecucao().getHoraFimFormatada();

						if(Util.compararHoraMinuto(horaInicioInterrupcao, horaInicioExecucao, ">")
										&& Util.compararHoraMinuto(horaFimInterrupcao, horaFimExecucao, "<")){

							intervaloOk = true;
							break;
						}
					}
				}else{

					intervaloOk = true;
				}

				if(intervaloOk){

					OrdemServicoInterrupcaoExecucao osInterrupcaoExecucao = new OrdemServicoInterrupcaoExecucao();
					osInterrupcaoExecucao.setInterrupcaoInicio(dataHoraInterrupcaoInicio);
					osInterrupcaoExecucao.setInterrupcaoFim(dataHoraInterrupcaoFim);
					osInterrupcaoExecucao.setUltimaAlteracao(new Date());

					Collection<MotivoInterrupcao> colecaoMotivoInterrupcao = fachada.pesquisarMotivoInterrupcao();

					for(MotivoInterrupcao motivoInterrupcao : colecaoMotivoInterrupcao){

						if(motivoInterrupcao.getId().toString().equals(idMotivoInterrupcao.toString())){

							osInterrupcaoExecucao.setMotivoInterrupcao(motivoInterrupcao);
							break;
						}
					}

					if(!Util.isVazioOrNulo(helper.getColecaoInterrupcao())){

						helper.getColecaoInterrupcao().add(osInterrupcaoExecucao);
					}else{

						helper.setColecaoInterrupcao(new ArrayList<OrdemServicoInterrupcaoExecucao>());
						helper.getColecaoInterrupcao().add(osInterrupcaoExecucao);
					}

					break;
				}
			}
		}

		if(intervaloOk == false){

			throw new ActionServletException("atencao.periodo_intervalo_execucao_invalido");
		}

		sessao.setAttribute("colecaoManterDadosAtividadesOrdemServicoHelper", colecaoManterDadosAtividadesOrdemServicoHelper);
	}

	/**
	 * M�todo respons�vel por realizar a inclus�o de uma interrup��o na cole��o de
	 * ManterDadosAtividadesOrdemServicoHelper.
	 * 
	 * @date 28/11/2011
	 */
	private void adicionarInterrupcao(Collection<ManterDadosAtividadesOrdemServicoHelper> colecaoManterDadosAtividadesOrdemServicoHelper,
					HttpServletRequest httpServletRequest, Fachada fachada, HttpSession sessao,
					EncerrarOrdemServicoActionForm encerrarOrdemServicoActionForm){

		String horaInicioInterrupcao = encerrarOrdemServicoActionForm.getHoraInicioInterrupcaoExecucao();
		String horaFimInterrupcao = encerrarOrdemServicoActionForm.getHoraFimInterrupcaoExecucao();
		String idMotivoInterrupcao = encerrarOrdemServicoActionForm.getIdMotivoInterrupcaoExecucao();
		String dataExecucao = encerrarOrdemServicoActionForm.getDataExecucao();

		// Validar hora in�cio e fim de interrup��o.
		if(Util.compararHoraMinuto(horaInicioInterrupcao, horaFimInterrupcao, ">")){

			throw new ActionServletException("atencao.hora_final_anterior_hora_inicio");
		}

		// Verificando motivo informado
		if(Util.isVazioOuBranco(idMotivoInterrupcao) || Integer.valueOf(idMotivoInterrupcao) == ConstantesSistema.NUMERO_NAO_INFORMADO){

			throw new ActionServletException("atencao.informar_motivo_interrupcao");
		}

		this.informarInterrupcao(fachada, sessao, httpServletRequest, encerrarOrdemServicoActionForm.getNumeroOS(),
						encerrarOrdemServicoActionForm.getIdAtividadeSelecionada(), dataExecucao, horaInicioInterrupcao,
						horaFimInterrupcao, idMotivoInterrupcao, colecaoManterDadosAtividadesOrdemServicoHelper);

		encerrarOrdemServicoActionForm.setHoraInicioInterrupcaoExecucao("");
		encerrarOrdemServicoActionForm.setHoraFimInterrupcaoExecucao("");
		encerrarOrdemServicoActionForm.setIdMotivoInterrupcaoExecucao("");
	}

	/**
	 * M�todo respons�vel por realizar a inclus�o de um material na cole��o de
	 * ManterDadosAtividadesOrdemServicoHelper.
	 * 
	 * @date 28/11/2011
	 */
	private void informarOsAtividadeMaterialExecucao(ManterDadosAtividadesOrdemServicoHelper helper, Integer numeroOS, Integer idMaterial,
					Fachada fachada, EncerrarOrdemServicoActionForm formulario){

		String quantidadeStr = formulario.getQuantidade();
		BigDecimal quantidade = Util.formatarStringParaBigDecimal(quantidadeStr, 2, true);

		OsAtividadeMaterialExecucao osAtividadeMaterialExecucao = null;

		if(!Util.isVazioOrNulo(helper.getColecaoOsAtividadeMaterialExecucao())){

			Iterator iteratorColecaoOsAtividadeMaterialExecucao = helper.getColecaoOsAtividadeMaterialExecucao().iterator();

			while(iteratorColecaoOsAtividadeMaterialExecucao.hasNext()){

				osAtividadeMaterialExecucao = (OsAtividadeMaterialExecucao) iteratorColecaoOsAtividadeMaterialExecucao.next();

				/*
				 * Verifica se j� existe na cole��o uma OsAtividadeMaterialExecucao com o mesmo
				 * material
				 * [FS0012] - Verificar Material j� existente
				 */
				if(osAtividadeMaterialExecucao.getMaterial().getId().toString().equals(idMaterial.toString())){

					throw new ActionServletException("atencao.material_ja_informado");
				}
			}

			osAtividadeMaterialExecucao = this.gerarOsAtividadeMaterialExecucao(numeroOS, idMaterial, fachada);
			osAtividadeMaterialExecucao.setQuantidadeMaterial(quantidade);
			osAtividadeMaterialExecucao.setOrdemServicoAtividade(helper.getOrdemServicoAtividade());
			osAtividadeMaterialExecucao.setUltimaAlteracao(new Date());
			helper.getColecaoOsAtividadeMaterialExecucao().add(osAtividadeMaterialExecucao);

		}else{

			osAtividadeMaterialExecucao = this.gerarOsAtividadeMaterialExecucao(numeroOS, idMaterial, fachada);
			osAtividadeMaterialExecucao.setQuantidadeMaterial(quantidade);
			osAtividadeMaterialExecucao.setUltimaAlteracao(new Date());
			osAtividadeMaterialExecucao.setOrdemServicoAtividade(helper.getOrdemServicoAtividade());
			helper.setColecaoOsAtividadeMaterialExecucao(new ArrayList());
			helper.getColecaoOsAtividadeMaterialExecucao().add(osAtividadeMaterialExecucao);
		}

		formulario.setIdMaterialProgramado("");
		formulario.setIdMaterialNaoProgramado("");
		formulario.setDescricaoMaterialNaoProgramado("");
		formulario.setQuantidade("");
	}

	/**
	 * M�todo respons�vel por gerar um objeto OsAtividadeMaterialExecucao para ser colocado na
	 * cole��o de
	 * ManterDadosAtividadesOrdemServicoHelper
	 * 
	 * @date 28/11/2011
	 */
	private OsAtividadeMaterialExecucao gerarOsAtividadeMaterialExecucao(Integer numeroOS, Integer idMaterial, Fachada fachada){

		OsAtividadeMaterialExecucao osAtividadeMaterialExecucao = new OsAtividadeMaterialExecucao();

		FiltroMaterial filtroMaterial = new FiltroMaterial();
		filtroMaterial.adicionarParametro(new ParametroSimples(FiltroMaterial.ID, idMaterial));

		Collection colecaoMaterial = fachada.pesquisar(filtroMaterial, Material.class.getName());

		// [FS0011] - Verificar Exist�ncia do Material
		if(colecaoMaterial == null || colecaoMaterial.isEmpty()){

			throw new ActionServletException("atencao.pesquisa_inexistente", null, "Material");
		}

		Material material = (Material) Util.retonarObjetoDeColecao(colecaoMaterial);

		osAtividadeMaterialExecucao.setMaterial(material);

		// Obter quantidade padr�o de acordo com o material e a OS
		BigDecimal qtdMaterial = fachada.obterQuantidadePadraoMaterial(numeroOS, idMaterial);

		if(qtdMaterial != null){

			osAtividadeMaterialExecucao.setQuantidadeMaterial(qtdMaterial);
		}

		return osAtividadeMaterialExecucao;
	}


	/**
	 * Limpa os dados do deslocamento no formul�rio
	 * 
	 * @date 29/11/2011
	 */
	private void limparDadosDeslocamento(EncerrarOrdemServicoActionForm form){

		form.setKmInicial("");
		form.setKmFinal("");
		form.setDataInicioInterrupcaoDeslocamento("");
		form.setHoraInicioInterrupcaoDeslocamento("");
		form.setDataFimInterrupcaoDeslocamento("");
		form.setHoraFimInterrupcaoDeslocamento("");
		form.setKmInterrupcaoDeslocamento("");
		form.setIdMotivoInterrupcaoDeslocamento("");
		form.setDescricaoMotivoInterrupcaoDeslocamento("");
		form.setDataInicioDeslocamento("");
		form.setDataFimDeslocamento("");
		form.setHoraInicioDeslocamento("");
		form.setHoraFimDeslocamento("");
	}

	/**
	 * Recuperar dados do deslocamento e das interrup��es do deslocamento.
	 * 
	 * @date 29/11/2011
	 */
	private void recuperarDadosDeslocamentoCadastrado(HttpSession sessao, HttpServletRequest httpServletRequest,
					EncerrarOrdemServicoActionForm form, Fachada fachada, Integer idEquipeAtual, Date dataRoteiro, Integer numeroOs){

		OrdemServicoProgramacao osProgramacao = null;
		OrdemServicoDeslocamento osDeslocamento = null;
		Collection<OrdemServicoInterrupcaoDeslocamento> colecaoInterrupcao = null;

		// Recuperando colecao da sessao
		Collection<OrdemServicoInterrupcaoDeslocamento> colecaoInterrupcaoSessao = (Collection) sessao
						.getAttribute("colecaoInterrupcaoDeslocamento");

		// Verificando se existe alguma informacao de deslocamento cadastrada para exibir
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		Integer idUnidadeLotacao = usuario.getUnidadeOrganizacional().getId();

		if(!Util.isVazioOuBranco(form.getDataRoteiro())){

			// Consulta programacaoRoteiro
			ProgramacaoRoteiro programacaoRoteiro = this.consultarProgramacaoRoteiro(dataRoteiro, idUnidadeLotacao);

			// Buscar a OrdemServicoProgramacao
			osProgramacao = fachada.pesquisarOrdemServicoProgramacao(programacaoRoteiro.getId(), numeroOs, idEquipeAtual);

			// Pesquisar OS Deslocamento e Interrup��es
			if(osProgramacao != null){

				osDeslocamento = fachada.pesquisarOrdemServicoDeslocamento(osProgramacao.getId());
				colecaoInterrupcao = fachada.pesquisarOSInterrupcaoDeslocamento(osProgramacao.getId());
			}

			if(osDeslocamento != null){

				form.setKmInicial(osDeslocamento.getNumeroKmInicio().toString());
				form.setKmFinal(osDeslocamento.getNumeroKmFim().toString());
				form.setDataInicioDeslocamento(Util.formatarData(osDeslocamento.getDeslocamentoInicio()));
				form.setHoraInicioDeslocamento(Util.formatarHoraSemSegundos(osDeslocamento.getDeslocamentoInicio()));
				form.setDataFimDeslocamento(Util.formatarData(osDeslocamento.getDeslocamentoFim()));
				form.setHoraFimDeslocamento(Util.formatarHoraSemSegundos(osDeslocamento.getDeslocamentoFim()));
			}

			// Pesquisar Interrup��es
			if(!Util.isVazioOrNulo(colecaoInterrupcao)){

				if(!Util.isVazioOrNulo(colecaoInterrupcaoSessao)){

					colecaoInterrupcaoSessao.addAll(colecaoInterrupcao);
				}else{

					sessao.setAttribute("colecaoInterrupcaoDeslocamento", colecaoInterrupcao);
				}
			}

		}else{

			throw new ActionServletException("atencao.erro_ao_recuperar_programacao_roteiro");
		}

	}

	/**
	 * Pesquisar a Programacao Roteiro
	 * 
	 * @date 29/11/2011
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

	/**
	 * Este metodo remove da cole��o de interrup��es do deslocamento um item
	 * 
	 * @date 26/11/2011
	 */
	private void removerInterrupcaoDeslocamento(HttpSession sessao, String indexInterrupcaoRemocao){

		if(sessao.getAttribute("colecaoInterrupcaoDeslocamento") != null){

			List colecaoRemocao = (List) sessao.getAttribute("colecaoInterrupcaoDeslocamento");
			colecaoRemocao.remove(Util.obterInteger(indexInterrupcaoRemocao).intValue());
			sessao.setAttribute("colecaoInterrupcaoDeslocamento", colecaoRemocao);
		}
	}

	/**
	 * Adiciona uma linha na cole��o de interrup��es.
	 * 
	 * @date 29/11/2011
	 */
	private void adicionarInterrupcaoDeslocamento(EncerrarOrdemServicoActionForm form, HttpSession sessao){

		Collection<OrdemServicoInterrupcaoDeslocamento> colecaoInterrupcao = (Collection) sessao
						.getAttribute("colecaoInterrupcaoDeslocamento");

		// Recuperando algumas informa��es do deslocamento
		String dataInicioDeslocamento = form.getDataInicioDeslocamento();
		String horaInicioDeslocamento = form.getHoraInicioDeslocamento();
		String dataFimDeslocamento = form.getDataFimDeslocamento();
		String horaFimDeslocamento = form.getHoraFimDeslocamento();

		// Recuperando informa��es de interrup��o
		String dataInicioInterrupcao = form.getDataInicioInterrupcaoDeslocamento();
		String horaInicioInterrupcao = form.getHoraInicioInterrupcaoDeslocamento();
		String dataFimInterrupcao = form.getDataFimInterrupcaoDeslocamento();
		String horaFimInterrupcao = form.getHoraFimInterrupcaoDeslocamento();
		String kmInterrupcao = form.getKmInterrupcaoDeslocamento();
		String idMotivoInterrupcao = form.getIdMotivoInterrupcaoDeslocamento();

		OrdemServicoInterrupcaoDeslocamento interrupcao = new OrdemServicoInterrupcaoDeslocamento();

		// Valida��o datas
		if(Util.validarDiaMesAno(dataInicioInterrupcao) || Util.validarDiaMesAno(dataFimInterrupcao)){

			throw new ActionServletException("atencao.verificar_datas_interrupcao");
		}

		// Validando se Km interrup��o est� dentro do intervalo informado.
		BigDecimal kmInicialDeslocamento = Util.formatarMoedaRealparaBigDecimal(form.getKmInicial(), 1);
		BigDecimal kmFinalDeslocamento = Util.formatarMoedaRealparaBigDecimal(form.getKmFinal(), 1);
		BigDecimal kmInterrupcaoDeslocamento = Util.formatarMoedaRealparaBigDecimal(kmInterrupcao, 1);

		if(kmInterrupcaoDeslocamento.compareTo(kmInicialDeslocamento) == -1
						|| kmInterrupcaoDeslocamento.compareTo(kmFinalDeslocamento) == 1){

			throw new ActionServletException("atencao.km_interrupcao_fora_do_intervalo");
		}

		// Datas do deslocamento
		String strDataHoraInicioDeslocamento = dataInicioDeslocamento + " " + horaInicioDeslocamento;
		String strDataHoraFimDeslocamento = dataFimDeslocamento + " " + horaFimDeslocamento;

		// Datas da interrup��o
		String strDataHoraInicioInterrupcao = dataInicioInterrupcao + " " + horaInicioInterrupcao;
		String strDataHoraFimInterrupcao = dataFimInterrupcao + " " + horaFimInterrupcao;

		// Preparando datas para valida��es
		Date dataHoraInicioDeslocamento = Util.converteStringParaDate(strDataHoraInicioDeslocamento, "dd/MM/yyyy HH:mm");
		Date dataHoraFimDeslocamento = Util.converteStringParaDate(strDataHoraFimDeslocamento, "dd/MM/yyyy HH:mm");
		Date dataHoraInicioInterrupcao = Util.converteStringParaDate(strDataHoraInicioInterrupcao, "dd/MM/yyyy HH:mm");
		Date dataHoraFimInterrupcao = Util.converteStringParaDate(strDataHoraFimInterrupcao, "dd/MM/yyyy HH:mm");

		if(dataHoraInicioDeslocamento == null || dataHoraFimDeslocamento == null || dataHoraInicioInterrupcao == null
						|| dataHoraFimInterrupcao == null){

			throw new ActionServletException("atencao.verificar_datas_deslocamento");

		}

		// Verificando se data inicial � anterior a data final.
		if(dataHoraInicioInterrupcao.compareTo(dataHoraFimInterrupcao) > 0){

			throw new ActionServletException("atencao.verificar_data_inicial_final_interrupcao");
		}

		// Validando se o intervalo de interrup��o est� dentro do intervalo do deslocamento.
		if(dataHoraInicioInterrupcao.compareTo(dataHoraInicioDeslocamento) < 0
						|| dataHoraFimInterrupcao.compareTo(dataHoraFimDeslocamento) > 0){

			throw new ActionServletException("atencao.data_interrupcao_fora_intervalo");
		}

		interrupcao.setInterrupcaoInicio(Util.converteStringParaDate(strDataHoraInicioInterrupcao, "dd/MM/yyyy HH:mm"));
		interrupcao.setInterrupcaoFim(Util.converteStringParaDate(strDataHoraFimInterrupcao, "dd/MM/yyyy HH:mm"));
		interrupcao.setNumeroKm(kmInterrupcaoDeslocamento);
		interrupcao.setUltimaAlteracao(new Date());

		// Recuperar o motivoInterrupcao da cole��o que est� na sess�o.
		Collection<MotivoInterrupcao> colecaoMotivoInterrupcao = (Collection) sessao.getAttribute("colecaoMotivoInterrupcao");
		for(MotivoInterrupcao motivoInterrupcao : colecaoMotivoInterrupcao){

			if(motivoInterrupcao.getId().equals(Integer.valueOf(idMotivoInterrupcao))){

				interrupcao.setMotivoInterrupcao(motivoInterrupcao);
			}
		}

		if(!Util.isVazioOrNulo(colecaoInterrupcao)){

			colecaoInterrupcao.add(interrupcao);
			sessao.setAttribute("colecaoInterrupcaoDeslocamento", colecaoInterrupcao);
		}else{

			colecaoInterrupcao = new ArrayList<OrdemServicoInterrupcaoDeslocamento>();
			colecaoInterrupcao.add(interrupcao);
			sessao.setAttribute("colecaoInterrupcaoDeslocamento", colecaoInterrupcao);
		}

		this.limparDadosInterrupcao(form);

	}

	/**
	 * Limpar dados interrup��o deslocamento.
	 * 
	 * @date 29/11/2011
	 */
	private void limparDadosInterrupcao(EncerrarOrdemServicoActionForm form){

		form.setDataInicioInterrupcaoDeslocamento("");
		form.setHoraInicioInterrupcaoDeslocamento("");
		form.setDataFimInterrupcaoDeslocamento("");
		form.setHoraFimInterrupcaoDeslocamento("");
		form.setKmInterrupcaoDeslocamento("");
		form.setIdMotivoInterrupcaoDeslocamento("");
	}

	/**
	 * Este metodo remove da cole��o de valas um item
	 * 
	 * @date 29/11/2011
	 */
	private void removerVala(HttpSession sessao, String indexVala){

		if(sessao.getAttribute("colecaoVala") != null){

			List colecaoRemocao = (List) sessao.getAttribute("colecaoVala");
			colecaoRemocao.remove(Util.obterInteger(indexVala).intValue());
			sessao.setAttribute("colecaoVala", colecaoRemocao);
		}
	}

	/**
	 * Adiciona um registro na cole��o de vala.
	 * 
	 * @date 29/11/2011
	 */
	private void adicionarVala(EncerrarOrdemServicoActionForm form, HttpSession sessao, HttpServletRequest httpServletRequest,
					Fachada fachada, OrdemServico ordemServico){

		Collection<OrdemServicoValaPavimento> colecaoVala = null;
		if(sessao.getAttribute("colecaoVala") != null){

			colecaoVala = (Collection) sessao.getAttribute("colecaoVala");
		}else{

			colecaoVala = new ArrayList<OrdemServicoValaPavimento>();
		}

		// Recuperando informa��es da vala
		String numeroVala = form.getNumeroVala();
		String idLocalOcorrencia = form.getIdLocalOcorrencia();
		String idPavimento = form.getIdPavimento();
		String comprimentoVala = form.getComprimentoVala();
		String larguraVala = form.getLarguraVala();
		String profundidadeVala = form.getProfundidadeVala();
		String indicadorValaAterrada = form.getIndicadorValaAterrada();
		String indicadorEntulho = form.getIndicadorEntulho();

		OrdemServicoValaPavimento vala = new OrdemServicoValaPavimento();

		// Verifica se numero da vala � maior que zero
		if(Util.validarNumeroMaiorQueZERO(numeroVala)){

			vala.setNumeroVala(Integer.valueOf(numeroVala));
		}else{

			throw new ActionServletException("atencao.campo.invalido", null, "N�mero Vala");
		}

		// Varrer a cole��o para verificar se o n�mero informado j� existe.
		for(OrdemServicoValaPavimento ordemServicoValaPavimento : colecaoVala){

			if(ordemServicoValaPavimento.getNumeroVala().equals(Integer.valueOf(numeroVala))){

				throw new ActionServletException("atencao.numero_vala_ja_existe");
			}
		}

		// Buscar o obj LocalOcorrencia para verificar qual o tipo do pavimento
		LocalOcorrencia localOcorrencia = fachada.pesquisarLocalOcorrencia(Integer.valueOf(idLocalOcorrencia));

		if(localOcorrencia != null){

			vala.setLocalOcorrencia(localOcorrencia);

			if(localOcorrencia.getIndicadorCalcada() == ConstantesSistema.INDICADOR_USO_ATIVO.intValue()){

				// Pavimento Calcada
				PavimentoCalcada pavimentoCalcada = fachada.pesquisarPavimentoCalcada(Integer.valueOf(idPavimento));
				vala.setPavimentoCalcada(pavimentoCalcada);

			}else if(localOcorrencia.getIndicadorRua() == ConstantesSistema.INDICADOR_USO_ATIVO.intValue()){

				// Pavimento Rua
				PavimentoRua pavimentoRua = fachada.pesquisarPavimentoRua(Integer.valueOf(idPavimento));
				vala.setPavimentoRua(pavimentoRua);

			}
		}

		vala.setNumeroComprimento(Util.formatarMoedaRealparaBigDecimal(comprimentoVala, 2));
		vala.setNumeroLargura(Util.formatarMoedaRealparaBigDecimal(larguraVala, 2));
		vala.setNumeroProfundidade(Util.formatarMoedaRealparaBigDecimal(profundidadeVala, 2));

		if(!Util.isVazioOuBranco(indicadorValaAterrada)){

			vala.setIndicadorAterro(Integer.parseInt(indicadorValaAterrada));
		}else{

			throw new ActionServletException("atencao.informe_indicador_aterro");
		}

		if(!Util.isVazioOuBranco(indicadorEntulho)){

			vala.setIndicadorEntulho(Integer.parseInt(indicadorEntulho));
		}else{

			throw new ActionServletException("atencao.informe_indicador_entulho");
		}

		vala.setUltimaAlteracao(new Date());
		vala.setOrdemServico(ordemServico);

		colecaoVala.add(vala);
		sessao.setAttribute("colecaoVala", colecaoVala);

		this.limparDadosVala(form);

	}

	/**
	 * Limpar dados da vala.
	 * 
	 * @date 29/11/2011
	 */
	private void limparDadosVala(EncerrarOrdemServicoActionForm form){

		form.setNumeroVala("");
		form.setIdLocalOcorrencia("");
		form.setIdPavimento("");
		form.setComprimentoVala("");
		form.setLarguraVala("");
		form.setProfundidadeVala("");
		form.setIndicadorValaAterrada("");
		form.setIndicadorEntulho("");
	}

	/**
	 * M�todo respons�vel por realizar a exclus�o de uma interrup��o da cole��o de
	 * ManterDadosAtividadesOrdemServicoHelper.
	 * 
	 * @date 28/11/2011
	 */
	private void removerMaterial(Collection colecaoManterOrdemServicoAtividadeHelper, EncerrarOrdemServicoActionForm formulario,
					String identificadorRemocaoMaterial, HttpServletRequest httpServletRequest){

		Iterator iteratorColecaoManterOrdemServicoAtividadeHelper = colecaoManterOrdemServicoAtividadeHelper.iterator();
		ManterDadosAtividadesOrdemServicoHelper manterDadosAtividadesOrdemServicoHelper = null;
		OsAtividadeMaterialExecucao osAtividadeMaterialExecucao = null;

		while(iteratorColecaoManterOrdemServicoAtividadeHelper.hasNext()){

			manterDadosAtividadesOrdemServicoHelper = (ManterDadosAtividadesOrdemServicoHelper) iteratorColecaoManterOrdemServicoAtividadeHelper
							.next();

			Collection colecaoMaterial = manterDadosAtividadesOrdemServicoHelper.getColecaoOsAtividadeMaterialExecucao();
			Collection colecaoMaterialRemovida = new ArrayList<OsAtividadeMaterialExecucao>();

			if(!Util.isVazioOrNulo(colecaoMaterial)){

				Iterator iteratorColecaoMaterial = colecaoMaterial.iterator();

				while(iteratorColecaoMaterial.hasNext()){

					osAtividadeMaterialExecucao = (OsAtividadeMaterialExecucao) iteratorColecaoMaterial.next();

					if(Util.formatarDataComHora(osAtividadeMaterialExecucao.getUltimaAlteracao()).equals(identificadorRemocaoMaterial)){

						colecaoMaterialRemovida.add(osAtividadeMaterialExecucao);
					}
				}

				if(!Util.isVazioOrNulo(colecaoMaterialRemovida)){

					manterDadosAtividadesOrdemServicoHelper.getColecaoOsAtividadeMaterialExecucao().removeAll(colecaoMaterialRemovida);
					break;
				}
			}
		}

		// Preparando o formul�rio para uma nova requisi��o do usu�rio
		formulario.setIdMaterialProgramado("");
		formulario.setIdMaterialNaoProgramado("");
		formulario.setDescricaoMaterialNaoProgramado("");
		formulario.setQuantidade("");
		httpServletRequest.setAttribute("nomeCampo", "horaInicioMaterial");
	}

	/**
	 * M�todo respons�vel por obter a Ordem de Servi�o Programa��o relativa a OS passada como
	 * parametro
	 * 
	 * @date 02/12/2011
	 */
	public OrdemServicoProgramacao obterOrdemServicoProgramacao(Fachada fachada, HttpSession sessao, Date dataRoteiro, Integer numeroOS,
					Integer idEquipeAtual){

		OrdemServicoProgramacao osProgramacao = null;

		// Verificando se existe alguma informacao de deslocamento cadastrada para exibir
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		Integer idUnidadeLotacao = usuario.getUnidadeOrganizacional().getId();

		if(dataRoteiro != null){

			// Buscar a OrdemServicoProgramacao
			osProgramacao = fachada.pesquisarOrdemServicoProgramacao(idUnidadeLotacao, numeroOS, idEquipeAtual, dataRoteiro);
		}

		return osProgramacao;
	}
}