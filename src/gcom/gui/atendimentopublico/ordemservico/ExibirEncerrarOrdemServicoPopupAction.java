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
import gcom.atendimentopublico.ordemservico.bean.ObterDescricaoSituacaoOSHelper;
import gcom.atendimentopublico.registroatendimento.AtendimentoMotivoEncerramento;
import gcom.atendimentopublico.registroatendimento.AtendimentoRelacaoTipo;
import gcom.atendimentopublico.registroatendimento.FiltroAtendimentoMotivoEncerramento;
import gcom.atendimentopublico.registroatendimento.bean.ObterDescricaoSituacaoRAHelper;
import gcom.cadastro.funcionario.FiltroFuncionario;
import gcom.cadastro.funcionario.Funcionario;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.hidrometro.FiltroHidrometroCondicao;
import gcom.micromedicao.hidrometro.HidrometroCondicao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Iterator;

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
public class ExibirEncerrarOrdemServicoPopupAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("encerrarOrdemServicoPopup");

		HttpSession sessao = httpServletRequest.getSession(false);

		EncerrarOrdemServicoActionForm encerrarOrdemServicoActionForm = (EncerrarOrdemServicoActionForm) actionForm;

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

			String redirecionarPagina = httpServletRequest.getParameter("redirecionarPagina");

			if(redirecionarPagina.equals("exibirGerarOSInserirRA")){
				httpServletRequest.setAttribute("numeroRA", encerrarOrdemServicoActionForm.getNumeroRA());
				httpServletRequest.setAttribute("numeroOS", encerrarOrdemServicoActionForm.getNumeroOS());
				httpServletRequest.setAttribute("veioEncerrarOS", "SIM");
			}

			if(redirecionarPagina.equals("processoAutorizacaoServicosAssociados")){
				httpServletRequest.setAttribute("processoAutorizacaoServicosAssociados", "processoAutorizacaoServicosAssociados");
				encerrarOrdemServicoActionForm.resetarConsultarDadosOSPopup();
			}

			retorno = actionMapping.findForward(redirecionarPagina);
			return retorno;
		}

		Fachada fachada = Fachada.getInstancia();

		Integer numeroOS = Util.converterStringParaInteger(httpServletRequest.getParameter("numeroOS"));
		String idMotivoEncerramento = httpServletRequest.getParameter("idMotivoEncerramento");
		String dataEncerramento = httpServletRequest.getParameter("dataEncerramento");

		if(numeroOS != null && isOSFiscalizacaoOuVistoria(numeroOS)){
			httpServletRequest.setAttribute("abrePopupDados", "S");
		}else{
			httpServletRequest.setAttribute("abrePopupDados", "N");
		}

		if(Util.isVazioOuBranco(numeroOS) && !Util.isVazioOuBranco(encerrarOrdemServicoActionForm.getNumeroOS())){
			numeroOS = Util.converterStringParaInteger(encerrarOrdemServicoActionForm.getNumeroOS());
		}

		String dataRoteiro = httpServletRequest.getParameter("dataRoteiro");
		if(dataRoteiro != null && !"".equals(dataRoteiro)){
			encerrarOrdemServicoActionForm.setDataRoteiro(dataRoteiro);
			sessao.setAttribute("dataRoteiro", dataRoteiro);
		}

		String carregarCampos = httpServletRequest.getParameter("carregarCampos");
		String retornoConsulta = httpServletRequest.getParameter("retornoConsulta");
		String retornoTela = httpServletRequest.getParameter("retornoTela");

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		if(retornoTela != null && !retornoTela.equals("")){
			sessao.setAttribute("retornoTela", retornoTela);
		}

		String carregarCamposComReferencia = httpServletRequest.getParameter("carregarCamposComReferencia");

		String pesquisaServicoTipo = httpServletRequest.getParameter("pesquisaServicoTipo");

		// caso tenha escolhido um tipo de servi�o
		if(pesquisaServicoTipo != null && !pesquisaServicoTipo.equals("")){

			// verifica se veio do resultado da pesquisa de servi�o.
			if(httpServletRequest.getParameter("tipoConsulta") != null && !httpServletRequest.getParameter("tipoConsulta").equals("")){

				encerrarOrdemServicoActionForm.setIdServicoTipo(httpServletRequest.getParameter("idCampoEnviarDados"));
				encerrarOrdemServicoActionForm.setDescricaoServicoTipo(httpServletRequest.getParameter("descricaoCampoEnviarDados"));

				httpServletRequest.setAttribute("nomeCampo", "ButtonAtividade");

			}else{

				// valida enter
				String idServicoTipo = encerrarOrdemServicoActionForm.getIdServicoTipo();
				String descricaoServicoTipo = encerrarOrdemServicoActionForm.getDescricaoServicoTipo();

				if((idServicoTipo != null && !idServicoTipo.equals(""))
								&& (descricaoServicoTipo == null || descricaoServicoTipo.equals(""))){

					FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();

					filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, idServicoTipo));
					filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					Collection servicoTipoEncontrado = fachada.pesquisar(filtroServicoTipo, ServicoTipo.class.getName());

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
			}

		}else{

			if(retornoConsulta == null || retornoConsulta.equals("")){

				if(carregarCamposComReferencia == null || carregarCamposComReferencia.equals("")){

					// caso o mitivo de encerramento n�o tenha sido mudado
					if(carregarCampos == null || carregarCampos.equals("")){

						// [FS0001] - Verificar unidade do usu�rio
						fachada.verificarUnidadeUsuario(numeroOS, usuarioLogado);
						OrdemServico ordemServico = pesquisarOrdemServico(numeroOS);

						// limpa do campos do form
						encerrarOrdemServicoActionForm.resetarConsultarDadosOSPopup();

						// seta o id do motivo encerramento
						if(idMotivoEncerramento != null && !idMotivoEncerramento.equals("")){
							encerrarOrdemServicoActionForm.setIdMotivoEncerramento(idMotivoEncerramento);
						}

						/*
						 * Alterado por Raphael Rossiter em 01/08/2007 (Analista: Rosana Carvalho)
						 * OBJETIVO: N�o colocar a data e a hora atual como sugest�o para a data e
						 * a hora de encerramento da Ordem de Servi�o
						 */
						if(dataEncerramento != null && dataEncerramento.equals("")){
							encerrarOrdemServicoActionForm.setDataEncerramento(dataEncerramento);
						}

						// Dados Gerais da OS
						encerrarOrdemServicoActionForm.setNumeroOS(ordemServico.getId() + "");
						encerrarOrdemServicoActionForm.setSituacaoOSId(ordemServico.getSituacao() + "");

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
						encerrarOrdemServicoActionForm.setTipoServicoOSId(ordemServico.getServicoTipo().getId() + "");

						if(ordemServico.getServicoTipo() != null && !ordemServico.getServicoTipo().equals("")){

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

							encerrarOrdemServicoActionForm.setIndicadorAfericaoServicoTipo(ordemServico.getServicoTipo()
											.getIndicadorAfericaoHidrometro().toString());

							FiltroHidrometroCondicao filtroHidrometroCondicao = new FiltroHidrometroCondicao();
							filtroHidrometroCondicao.adicionarParametro(new ParametroSimples(FiltroHidrometroCondicao.INDICADOR_USO,
											ConstantesSistema.INDICADOR_USO_ATIVO));
							filtroHidrometroCondicao.setCampoOrderBy(FiltroHidrometroCondicao.DESCRICAO);

							Collection<HidrometroCondicao> colecaoHidrometroCondicao = fachada.pesquisar(filtroHidrometroCondicao,
											HidrometroCondicao.class.getName());

							if(!Util.isVazioOrNulo(colecaoHidrometroCondicao)){

								sessao.setAttribute("colecaoHidrometroCondicao", colecaoHidrometroCondicao);
							}

							if(ordemServico.getServicoTipo().getServicoTipoReferencia() != null
											&& !ordemServico.getServicoTipo().getServicoTipoReferencia().equals("")){

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

						// encerrarOrdemServicoActionForm.setDataRoteiro(dataRoteiro);

						encerrarOrdemServicoActionForm.setUltimaAlteracao(ordemServico.getUltimaAlteracao());
						FiltroAtendimentoMotivoEncerramento filtroAtendimentoMotivoEncerramento = new FiltroAtendimentoMotivoEncerramento();
						filtroAtendimentoMotivoEncerramento.adicionarParametro(new ParametroSimples(
										FiltroAtendimentoMotivoEncerramento.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
						filtroAtendimentoMotivoEncerramento.adicionarParametro(new ParametroSimples(
										FiltroAtendimentoMotivoEncerramento.INDICADOR_DUPLICIDADE,
										AtendimentoMotivoEncerramento.INDICADOR_DUPLICIDADE_INATIVO));
						Collection colecaoAtendimentoMotivoEncerrado = fachada.pesquisar(filtroAtendimentoMotivoEncerramento,
										AtendimentoMotivoEncerramento.class.getName());
						sessao.setAttribute("colecaoAtendimentoMotivoEncerrado", colecaoAtendimentoMotivoEncerrado);

						if(encerrarOrdemServicoActionForm.getIdMotivoEncerramento() != null
										&& !encerrarOrdemServicoActionForm.getIdMotivoEncerramento().equals("")){

							Iterator iteAtendimentoMotivoEncerramento = colecaoAtendimentoMotivoEncerrado.iterator();

							while(iteAtendimentoMotivoEncerramento.hasNext()){

								AtendimentoMotivoEncerramento atendimentoMotivoEncerramento = (AtendimentoMotivoEncerramento) iteAtendimentoMotivoEncerramento
												.next();
								if(atendimentoMotivoEncerramento.getId() != null
												&& atendimentoMotivoEncerramento.getId().equals(
																Util.converterStringParaInteger(encerrarOrdemServicoActionForm
																				.getIdMotivoEncerramento()))){
									encerrarOrdemServicoActionForm.setIndicadorExecucao(""
													+ atendimentoMotivoEncerramento.getIndicadorExecucao());

									break;
								}
							}

						}else{
							encerrarOrdemServicoActionForm.setIndicadorExecucao("");
						}

					}else{

						if(encerrarOrdemServicoActionForm.getIdMotivoEncerramento() != null
										&& !encerrarOrdemServicoActionForm.getIdMotivoEncerramento().equals("")){

							Collection colecaoAtendimentoMotivoEncerrado = (Collection) sessao
											.getAttribute("colecaoAtendimentoMotivoEncerrado");
							Iterator iteAtendimentoMotivoEncerramento = colecaoAtendimentoMotivoEncerrado.iterator();

							while(iteAtendimentoMotivoEncerramento.hasNext()){
								AtendimentoMotivoEncerramento atendimentoMotivoEncerramento = (AtendimentoMotivoEncerramento) iteAtendimentoMotivoEncerramento
												.next();

								if(atendimentoMotivoEncerramento.getId() != null
												&& atendimentoMotivoEncerramento.getId().equals(
																Util.converterStringParaInteger(encerrarOrdemServicoActionForm
																				.getIdMotivoEncerramento()))){

									encerrarOrdemServicoActionForm.setIndicadorExecucao(""
													+ atendimentoMotivoEncerramento.getIndicadorExecucao());

									// 4.6 caso o indicador de execu��o seja igual a sim(1)
									if(atendimentoMotivoEncerramento.getIndicadorExecucao() == AtendimentoMotivoEncerramento.INDICADOR_EXECUCAO_SIM){

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
				sessao.removeAttribute("caminhoRetornoDadosAtividadesOS");

				if(retornoConsulta.equals("informarOS")){
					httpServletRequest.setAttribute("nomeCampo", "ButtonOSFiscalizacao");
				}
			}
		}

		// Pesquisar Funcion�rio
		if(!Util.isVazioOuBranco(encerrarOrdemServicoActionForm.getIdFuncionario())){

			if(Util.validarNumeroMaiorQueZERO(encerrarOrdemServicoActionForm.getIdFuncionario())){

				this.pesquisarFuncionario(encerrarOrdemServicoActionForm, fachada, httpServletRequest);
			}
		}

		/*
		 * Colocado por Raphael Rossiter em 01/08/2007
		 * OBJETIVO: Informar qual o campo receber� o foco no carregamento, caso nenhum j� tenh sido
		 * informado
		 */
		if(httpServletRequest.getAttribute("nomeCampo") == null){
			httpServletRequest.setAttribute("nomeCampo", "dataEncerramento");
		}

		return retorno;
	}

	// /**
	// * Consulta a ordem de servi�o pelo id informado
	// *
	// * @author S�vio Luiz
	// * @created 22/03/2007
	// */
	// private void validarOrdemServicoFiscalizacao(OrdemServico ordemServico) {
	//
	// if (ordemServico.getServicoTipo() != null
	// && ordemServico.getServicoTipo().getIndicadorFiscalizacaoInfracao() ==
	// ConstantesSistema.INDICADOR_USO_ATIVO) {
	//
	// if (ordemServico.getFiscalizacaoSituacao() == null ||
	// ordemServico.getFiscalizacaoSituacao().getId() == null
	// || ordemServico.getFiscalizacaoSituacao().getId().equals("")) {
	// throw new ActionServletException("atencao.ordem_servico_fiscalizacao_imovel");
	// }
	// }
	// }

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
	 * Consulta a Ordem Servi�o Unidade pelo id do OS e Tipo (1=ABRIR/REGISTRAR
	 * e 3-ENCERRAR)
	 * 
	 * @author S�vio luiz
	 * @date 18/09/2006
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

	private void encerrarComExecucaoComReferencia(Fachada fachada, HttpSession sessao, HttpServletRequest httpServletRequest,
					EncerrarOrdemServicoActionForm encerrarOrdemServicoActionForm, Integer numeroOS){

		// verifica a existencia da cole��o na sess�o
		Collection colecaoOSReferidaRetornoTipo = (Collection) sessao.getAttribute("colecaoOSReferidaRetornoTipo");

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
				Iterator iteratorOSReferidaRetorno = colecaoOSReferidaRetornoTipo.iterator();

				while(iteratorOSReferidaRetorno.hasNext()){
					OsReferidaRetornoTipo osReferidaRetornoTipo = (OsReferidaRetornoTipo) iteratorOSReferidaRetorno.next();

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
	 * Pesquisar uma funcion�rio ap�s teclar ENTER pelo id informado
	 * 
	 * @author Anderson Italo
	 * @date 08/09/2014
	 */
	private void pesquisarFuncionario(EncerrarOrdemServicoActionForm form, Fachada fachada, HttpServletRequest httpServletRequest){

		FiltroFuncionario filtroFuncionario = new FiltroFuncionario();
		filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, form.getIdFuncionario()));

		Collection colecaoFuncionario = fachada.pesquisar(filtroFuncionario, Funcionario.class.getName());

		if(Util.isVazioOrNulo(colecaoFuncionario)){

			form.setIdFuncionario("");
			form.setNomeFuncionario("Funcion�rio Inexistente");
			httpServletRequest.setAttribute("nomeCampo", "idFuncionario");

		}else{

			Funcionario funcionario = (Funcionario) Util.retonarObjetoDeColecao(colecaoFuncionario);

			form.setIdFuncionario(funcionario.getId().toString());
			form.setNomeFuncionario(funcionario.getNome());
			httpServletRequest.setAttribute("idFuncionarioEncontrado", "true");
			httpServletRequest.setAttribute("nomeCampo", "idFuncionario");
		}
	}
}