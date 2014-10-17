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

package gcom.gui.faturamento.conta;

import gcom.atendimentopublico.registroatendimento.FiltroRegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
import gcom.cadastro.cliente.*;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaMotivoCancelamento;
import gcom.faturamento.conta.FiltroConta;
import gcom.faturamento.conta.FiltroContaMotivoCancelamento;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;
import gcom.util.filtro.ParametroNulo;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class CancelarConjuntoContaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirCancelarConjuntoConta");
		httpServletRequest.getAttribute("idsContasSelecionadas");
		HttpSession sessao = httpServletRequest.getSession(false);

		// Inst�ncia do formul�rio que est� sendo utilizado
		CancelarContaActionForm cancelarContaActionForm = (CancelarContaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		String numeroRA = null;

		// MotivoCancelamentoConta selecinado pelo usu�rio
		ContaMotivoCancelamento contaMotivoCancelamento = new ContaMotivoCancelamento();
		contaMotivoCancelamento.setId(new Integer(cancelarContaActionForm.getMotivoCancelamentoContaID()));

		if(sessao.getAttribute("colecaoImovel") != null){

			Collection<Conta> colecaoImovel = (Collection) sessao.getAttribute("colecaoImovel");

			Integer anoMes = null;
			if(sessao.getAttribute("anoMes") != null){
				anoMes = (Integer) sessao.getAttribute("anoMes");
			}

			Integer anoMesFim = null;
			if(sessao.getAttribute("anoMesFim") != null){
				anoMesFim = (Integer) sessao.getAttribute("anoMesFim");
			}

			Date dataVencimentoContaInicio = null;
			Date dataVencimentoContaFim = null;
			Integer idGrupoFaturamento = null;

			if(sessao.getAttribute("dataVencimentoContaInicial") != null){

				dataVencimentoContaInicio = (Date) sessao.getAttribute("dataVencimentoContaInicial");
			}

			if(sessao.getAttribute("dataVencimentoContaFinal") != null){

				dataVencimentoContaFim = (Date) sessao.getAttribute("dataVencimentoContaFinal");
			}

			if(sessao.getAttribute("idGrupoFaturamento") != null){

				idGrupoFaturamento = (Integer) sessao.getAttribute("idGrupoFaturamento");
			}

			Integer codigoCliente = null;
			if(sessao.getAttribute("codigoCliente") != null){
				codigoCliente = new Integer((String) sessao.getAttribute("codigoCliente"));
			}

			// Usuario logado no sistema
			Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

			// Cancelando uma ou v�rias contas
			Collection<Conta> colecaoContasSelecionadas = new ArrayList<Conta>();

			String contaSelected = cancelarContaActionForm.getContaSelected();
			Collection idsConta = new ArrayList();

			if(!Util.isVazioOuBranco(contaSelected)){
				// Contas selecionadas pelo usu�rio
				String[] arrayIdentificadores = contaSelected.split(",");

			for(int i = 0; i < arrayIdentificadores.length; i++){

				String dadosConta = arrayIdentificadores[i];
				String[] idContaArray = dadosConta.split("-");
				Integer idConta = new Integer(idContaArray[0]);
					idsConta.add(idConta);

				// [FS0021] Verificar situa��o da conta
				FiltroConta filtroConta = new FiltroConta();
				filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.IMOVEL);
				filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.LOCALIDADE);
					filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.QUADRA);
					filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.SETOR_COMERCIAL);
				filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.DEBITO_CREDITO_SITUACAO_ATUAL);

				filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.ID, idConta));
				Collection colecaoContas = fachada.pesquisar(filtroConta, Conta.class.getName());
				Conta contaSelecao = (Conta) colecaoContas.iterator().next();

				if(contaSelecao != null){
					if(!fachada.verificarSituacaoContaPermitida(contaSelecao)){
						throw new ActionServletException("atencao.conta_em_situacao_nao_permitida", contaSelecao
										.getDebitoCreditoSituacaoAtual().getDescricaoDebitoCreditoSituacao(), "a��o");
					}
				}

				colecaoContasSelecionadas.add(contaSelecao);
			}

			}


			// **************************************
			// [FS0033 � Validar registro de atendimento]
			// **************************************
			if(!Util.isVazioOuBranco(cancelarContaActionForm.getNumeroRA())){
				numeroRA = cancelarContaActionForm.getNumeroRA();

				if(!Util.isVazioOuBranco(numeroRA) && !Util.isVazioOrNulo(colecaoContasSelecionadas)){

					for(Conta conta : colecaoContasSelecionadas){
						// Consulta motivo do cancelamento da conta
						FiltroContaMotivoCancelamento filtroContaMotivoCancelamento = new FiltroContaMotivoCancelamento();
						filtroContaMotivoCancelamento.adicionarParametro(new ParametroSimples(FiltroContaMotivoCancelamento.CODIGO,
										contaMotivoCancelamento.getId()));

						filtroContaMotivoCancelamento
										.adicionarCaminhoParaCarregamentoEntidade(FiltroContaMotivoCancelamento.SOLICITACAO_TIPO_ESPECIFICACAO);

						Collection colecaoContaMotivoCancelamento = fachada.pesquisar(filtroContaMotivoCancelamento,
										ContaMotivoCancelamento.class.getName());

						ContaMotivoCancelamento contaMotivoCancelamentoAux = (ContaMotivoCancelamento) Util
										.retonarObjetoDeColecao(colecaoContaMotivoCancelamento);

						if(!Util.isVazioOuBranco(contaMotivoCancelamentoAux)){

							SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = new SolicitacaoTipoEspecificacao();

							solicitacaoTipoEspecificacao = contaMotivoCancelamentoAux.getSolicitacaoTipoEspecificacao();

							// [FS0033 � Validar registro de atendimento]
							this.validarRegistroAtendimento(conta, numeroRA, solicitacaoTipoEspecificacao);
						}
					}
				}
			}

			// Realizar um reload na tela de manter conta
			if(sessao.getAttribute("retornoArquivo") != null){
				httpServletRequest.setAttribute("reloadPage", "grupo");
			}else{
				httpServletRequest.setAttribute("reloadPage", "OK");
			}

			String enderecoURL = httpServletRequest.getServletPath();

			fachada.verificarBloqueioFuncionalidadeMotivoRetificacaoRevisao(idsConta, usuarioLogado, false, false, enderecoURL);

			// Cancelando uma ou v�rias contas
			fachada.cancelarConjuntoConta(colecaoContasSelecionadas, contaMotivoCancelamento, usuarioLogado, numeroRA);
		}

		sessao.setAttribute("cancelar", "1");
		sessao.removeAttribute("anoMes");

		return retorno;
	}

	/**
	 * Pesquisa Conta Motivo Revis�o
	 * 
	 * @author Carlos Chrystian
	 * @date 10/05/2013
	 */
	private void validarRegistroAtendimento(Conta conta, String numeroRA, SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao){

		Collection colecaoRegistroAtendimento = null;

		if(!Util.isVazioOuBranco(numeroRA)){

			FiltroRegistroAtendimento filtroRegistroAtendimento = new FiltroRegistroAtendimento();
			filtroRegistroAtendimento.adicionarParametro(new ParametroSimples(FiltroRegistroAtendimento.ID, numeroRA));

			filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimento.SOLICITACAO_TIPO_ESPECIFICACAO);
			filtroRegistroAtendimento.adicionarCaminhoParaCarregamentoEntidade(FiltroRegistroAtendimento.ATENDIMENTO_MOTIVO_ENCERRAMENTO);

			colecaoRegistroAtendimento = Fachada.getInstancia().pesquisar(filtroRegistroAtendimento, RegistroAtendimento.class.getName());

			// Caso o registro de atendimento informado exista
			if(!Util.isVazioOrNulo(colecaoRegistroAtendimento)){
				RegistroAtendimento registroAtendimento = (RegistroAtendimento) Util.retonarObjetoDeColecao(colecaoRegistroAtendimento);

				if(!Util.isVazioOuBranco(registroAtendimento)){
					// Caso o registro de atendimento N�O esteja encerrado
					if(Util.isVazioOuBranco(registroAtendimento.getAtendimentoMotivoEncerramento())){
						// Verifica compatibilidade da especifica��o com o motivo de cancelamento
						if(!Util.isVazioOuBranco(registroAtendimento.getSolicitacaoTipoEspecificacao())
										&& !Util.isVazioOuBranco(solicitacaoTipoEspecificacao)){
							// Caso a especifica��o do registro de atendimento n�o corresponda
							// ao motivo de cancelamento, exibir a mensagem
							if(registroAtendimento.getSolicitacaoTipoEspecificacao().getId().intValue() == solicitacaoTipoEspecificacao
											.getId().intValue()){

								// Consulta o cliente do im�vel
								FiltroClienteImovel filtroClienteImovel = new FiltroClienteImovel();

								filtroClienteImovel.adicionarParametro(new ParametroSimples(FiltroClienteImovel.IMOVEL_ID,
												registroAtendimento.getImovel().getId()));
								filtroClienteImovel.adicionarParametro(new ParametroNulo(FiltroClienteImovel.DATA_FIM_RELACAO));
								filtroClienteImovel.adicionarParametro(new ParametroSimples(
												FiltroClienteImovel.CLIENTE_RELACAO_TIPO_DESCRICAO,
												ClienteRelacaoTipo.DESCRICAO_RESPONSAVEL));
								filtroClienteImovel.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteImovel.CLIENTE);

								Collection colecaoClienteImovel = Fachada.getInstancia().pesquisar(filtroClienteImovel,
												ClienteImovel.class.getName());

								ClienteImovel clienteImovel = null;

								if(!Util.isVazioOrNulo(colecaoClienteImovel)){
									clienteImovel = (ClienteImovel) Util.retonarObjetoDeColecao(colecaoClienteImovel);
								}

								// Consulta o cliente da conta
								FiltroClienteConta filtroClienteConta = new FiltroClienteConta();

								filtroClienteConta.adicionarParametro(new ParametroSimples(FiltroClienteConta.CONTA_ID, conta.getId()));
								filtroClienteConta
												.adicionarParametro(new ParametroSimples(FiltroClienteConta.CLIENTE_RELACAO_TIPO_DESCRICAO,
																ClienteRelacaoTipo.DESCRICAO_RESPONSAVEL));
								filtroClienteConta.adicionarCaminhoParaCarregamentoEntidade(FiltroClienteConta.CLIENTE);

								Collection<ClienteConta> colecaoClienteConta = Fachada.getInstancia().pesquisar(filtroClienteConta,
												ClienteConta.class.getName());

								ClienteConta clienteConta = null;

								if(!Util.isVazioOrNulo(colecaoClienteConta)){
									clienteConta = (ClienteConta) Util.retonarObjetoDeColecao(colecaoClienteConta);
								}

								// Caso o cliente respons�vel pelo im�vel do registro de atendimento
								// n�o corresponda ao cliente respons�vel pelas contas selecionadas
								// para cancelamento, exibir a mensagem
								if(!Util.isVazioOuBranco(clienteImovel)
												&& !Util.isVazioOuBranco(clienteConta)
												&& !Util.isVazioOuBranco(clienteImovel.getCliente())
												&& !Util.isVazioOuBranco(clienteConta.getCliente())
												&& clienteImovel.getCliente().getId().intValue() != clienteConta.getCliente().getId()
																.intValue()){
									throw new ActionServletException("atencao.registro_atendimento_cliente_nao_corresponde", clienteImovel
													.getCliente().getNome(), clienteConta.getCliente().getNome());
								}
							}else{
								// Caso a especifica��o do registro de atendimento n�o corresponda
								// ao motivo de cancelamento, exibir a mensagem �A especifica��o
								// do Registro de Atendimento, n�o corresponde � especifica��o
								// vinculada ao motivo de cancelamento.�
								// e retornar para o passo correspondente no fluxo.
								throw new ActionServletException("atencao.registro_atendimento_especificacao_nao_corresponde",
												registroAtendimento.getSolicitacaoTipoEspecificacao().getDescricao(),
												solicitacaoTipoEspecificacao.getDescricao());
							}
						}else{
							// Caso a especifica��o do registro de atendimento n�o corresponda
							// ao motivo de cancelamento, exibir a mensagem �A especifica��o
							// do Registro de Atendimento, n�o corresponde � especifica��o
							// vinculada ao motivo de cancelamento.�
							// e retornar para o passo correspondente no fluxo.
							throw new ActionServletException("atencao.registro_atendimento_especificacao_nao_corresponde",
											registroAtendimento.getSolicitacaoTipoEspecificacao().getDescricao(),
											solicitacaoTipoEspecificacao.getDescricao());
						}
					}else{
						// Caso o registro de atendimento esteja encerrado
						// exibir a mensagem �Registro de Atendimento Encerrado.�
						// e retornar para o passo correspondente no fluxo.
						throw new ActionServletException("atencao.ra.encerrado");
					}
				}
			}else{
				// Caso o registro de atendimento esteja encerrado,
				// exibir a mensagem �Registro de Atendimento encerrado.
				// N�o � poss�vel efetuar o cancelamento das contas.�
				// e retornar para o passo correspondente no fluxo.
				throw new ActionServletException("atencao.ra.inexistente");
			}
		}else{
			// Caso o registro de atendimento informado n�o exista,
			// exibir a mensagem �Registro de Atendimento inexistente.�
			// e retornar para o passo correspondente no fluxo.
			throw new ActionServletException("atencao.ra.inexistente");
		}
	}

}
