/**
 * 
 */
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

package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.ordemservico.EspecificacaoServicoTipo;
import gcom.atendimentopublico.registroatendimento.*;
import gcom.cadastro.cliente.ClienteRelacaoTipo;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descrição da classe
 * 
 * @author Rômulo Aurélio
 * @date 13/11/2006
 */
public class AtualizarAdicionarSolicitacaoEspecificacaoAction
				extends GcomAction {

	/**
	 * Description of the Method
	 * 
	 * @param actionMapping
	 *            Description of the Parameter
	 * @param actionForm
	 *            Description of the Parameter
	 * @param httpServletRequest
	 *            Description of the Parameter
	 * @param httpServletResponse
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Mudar isso quando tiver esquema de segurança
		HttpSession sessao = httpServletRequest.getSession(false);

		// Set no mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("atualizarAdicionarSolicitacaoEspecificacao");

		Collection colecaoSolicitacaoTipoEspecificacao = null;
		if(sessao.getAttribute("colecaoSolicitacaoTipoEspecificacao") == null){
			colecaoSolicitacaoTipoEspecificacao = new ArrayList();
		}else{
			colecaoSolicitacaoTipoEspecificacao = (Collection) sessao.getAttribute("colecaoSolicitacaoTipoEspecificacao");
		}

		// String idSolicitacaoTipo = sessao.getAttribute("idTipoSolicitacao",
		// idSolicitacaoTipo);

		AtualizarAdicionarSolicitacaoEspecificacaoActionForm atualizarAdicionarSolicitacaoEspecificacaoActionForm = (AtualizarAdicionarSolicitacaoEspecificacaoActionForm) actionForm;

		// Obtém a instância da Fachada
		Fachada fachada = Fachada.getInstancia();

		if(sessao.getAttribute("atualizar") != null){

			// Obs: Se remover tudo da coleção, qdo a coleçao tiver mais de um elemento vai dar pau.
			// Entao, terei que Instaciar um objeto pra
			// receber os dados da colecao que esta na sessao, para depois comparar com a que esta
			// sendo alterada, pra depois fazer
			// alteração.

			Integer posicaoComponente = (Integer) sessao.getAttribute("posicaoComponente");

			int index = 0;

			Iterator colecaoSolicitacaoTipoEspecificacaoIterator = colecaoSolicitacaoTipoEspecificacao.iterator();

			while(colecaoSolicitacaoTipoEspecificacaoIterator.hasNext()){

				index++;

				SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = (SolicitacaoTipoEspecificacao) colecaoSolicitacaoTipoEspecificacaoIterator
								.next();

				if(index == posicaoComponente){

					solicitacaoTipoEspecificacao.setIndicadorSolicitante(new Short("1"));

					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getPrazoPrevisaoAtendimento() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getPrazoPrevisaoAtendimento().equals("")){
						// Prazo de previsão de atendimento
						solicitacaoTipoEspecificacao.setHorasPrazo(new Integer(atualizarAdicionarSolicitacaoEspecificacaoActionForm
										.getPrazoPrevisaoAtendimento()));
					}
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCalculoDataPrevistaAtendimento() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCalculoDataPrevistaAtendimento()
													.equals("")){
						// Prazo de previsão de atendimento
						solicitacaoTipoEspecificacao.setIndicadorCalculoDataPrevistaAtendimento(new Short(
										atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCalculoDataPrevistaAtendimento()));
					}
					// Descrição da especificação
					solicitacaoTipoEspecificacao.setDescricao(atualizarAdicionarSolicitacaoEspecificacaoActionForm
									.getDescricaoSolicitacao());

					// Pavimento de calçada obrigatório
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorPavimentoCalcada() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorPavimentoCalcada().equals("")){
						solicitacaoTipoEspecificacao.setIndicadorPavimentoCalcada(new Short(
										atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorPavimentoCalcada()));
					}
					// Pavimento de rua obrigatório
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorPavimentoRua() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorPavimentoRua().equals("")){
						solicitacaoTipoEspecificacao.setIndicadorPavimentoRua(new Short(
										atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorPavimentoRua()));
					}
					// Indicador de coletor obrigatório
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorColetor() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorColetor().equals("")){
						solicitacaoTipoEspecificacao.setIndicadorColetor(Short.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm
										.getIndicadorColetor()));
					}
					// Indicador de coletor obrigatório
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorColetor() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorColetor().equals("")){
						solicitacaoTipoEspecificacao.setIndicadorColetor(Short.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm
										.getIndicadorColetor()));
					}
					// Indicador de obrigatoriedade de preenchimento do RG na RA
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorObrigatoriedadeRgRA() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorObrigatoriedadeRgRA().equals("")){
						solicitacaoTipoEspecificacao.setIndicadorObrigatoriedadeRgRA(Short
										.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorObrigatoriedadeRgRA()));
					}

					// Indicador de obrigatoriedade de preenchimento do CPF/CNPJ na RA
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorObrigatoriedadeCpfCnpjRA() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorObrigatoriedadeCpfCnpjRA().equals(
													"")){
						solicitacaoTipoEspecificacao.setIndicadorObrigatoriedadeCpfCnpjRA(Short
										.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm
														.getIndicadorObrigatoriedadeCpfCnpjRA()));
					}

					// Indicador de obrigatoriedade de preenchimento do RG na RA
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorObrigatoriedadeRgRA() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorObrigatoriedadeRgRA().equals("")){
						solicitacaoTipoEspecificacao.setIndicadorObrigatoriedadeRgRA(Short
										.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorObrigatoriedadeRgRA()));
					}

					// Indicador de obrigatoriedade de preenchimento do CPF/CNPJ na RA
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorObrigatoriedadeCpfCnpjRA() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorObrigatoriedadeCpfCnpjRA().equals(
													"")){
						solicitacaoTipoEspecificacao.setIndicadorObrigatoriedadeCpfCnpjRA(Short
										.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm
														.getIndicadorObrigatoriedadeCpfCnpjRA()));
					}

					// refere-se a ligação de agua
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorLigacaoAgua() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorLigacaoAgua().equals("")){
						solicitacaoTipoEspecificacao.setIndicadorLigacaoAgua(new Short(atualizarAdicionarSolicitacaoEspecificacaoActionForm
										.getIndicadorLigacaoAgua()));
					}
					// Cobrança de material
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCobrancaMaterial() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCobrancaMaterial().equals("")){
						solicitacaoTipoEspecificacao.setIndicadorCobrancaMaterial(new Integer(
										atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCobrancaMaterial()));
					}
					// Matricula do imóvel obrigatório
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorMatriculaImovel() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorMatriculaImovel().equals("")){
						solicitacaoTipoEspecificacao.setIndicadorMatricula(new Integer(atualizarAdicionarSolicitacaoEspecificacaoActionForm
										.getIndicadorMatriculaImovel()));
					}
					// Parecer de encerramento obrigatório
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorParecerEncerramento() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorParecerEncerramento().equals("")){
						solicitacaoTipoEspecificacao.setIndicadorParecerEncerramento(new Integer(
										atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorParecerEncerramento()));
					}
					// Gera débito
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarDebito() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarDebito().equals("")){
						solicitacaoTipoEspecificacao.setIndicadorGeracaoDebito(new Integer(
										atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarDebito()));
					}
					// Gera Crédito
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarCredito() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarCredito().equals("")){
						solicitacaoTipoEspecificacao.setIndicadorGeracaoCredito(new Integer(
										atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarCredito()));
					}
					// Gera Ordem de Serviço
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarOrdemServico() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarOrdemServico().equals("")){
						solicitacaoTipoEspecificacao.setIndicadorGeracaoOrdemServico(new Short(
										atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarOrdemServico()));
					}

					// hora e data correntes
					solicitacaoTipoEspecificacao.setUltimaAlteracao(new Date());

					// Unidade inicial tramitação
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIdUnidadeTramitacao() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIdUnidadeTramitacao().equals("")){
						// Verifica se o código foi modificado
						if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getDescricaoUnidadeTramitacao() == null
										|| atualizarAdicionarSolicitacaoEspecificacaoActionForm.getDescricaoUnidadeTramitacao().trim()
														.equals("")){

							FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();

							filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID,
											atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIdUnidadeTramitacao()));

							filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.INDICADOR_USO,
											ConstantesSistema.INDICADOR_USO_ATIVO));

							Collection unidadeOrganizacionalEncontrado = fachada.pesquisar(filtroUnidadeOrganizacional,
											UnidadeOrganizacional.class.getName());

							if(unidadeOrganizacionalEncontrado != null && !unidadeOrganizacionalEncontrado.isEmpty()){
								UnidadeOrganizacional uinidadeOrganizacional = (UnidadeOrganizacional) ((List) unidadeOrganizacionalEncontrado)
												.get(0);
								solicitacaoTipoEspecificacao.setUnidadeOrganizacional(uinidadeOrganizacional);

							}else{
								throw new ActionServletException("atencao.pesquisa_inexistente", null, "Unidade Organizacional");
							}

						}else{
							UnidadeOrganizacional unidadeOrganizacional = new UnidadeOrganizacional();
							unidadeOrganizacional.setId(new Integer(atualizarAdicionarSolicitacaoEspecificacaoActionForm
											.getIdUnidadeTramitacao()));
							solicitacaoTipoEspecificacao.setUnidadeOrganizacional(unidadeOrganizacional);
						}
					}

					// id do tipo da solicitação gerada
					solicitacaoTipoEspecificacao.setServicoTipo(null);

					if(!Util.isVazioOuBranco(atualizarAdicionarSolicitacaoEspecificacaoActionForm
									.getIdSolicitacaoTipoEspecificacaoMensagem())){
						FiltroSolicitacaoTipoEspecificacaoMensagem filtroSolicitacaoTipoEspecificacaoMensagem = new FiltroSolicitacaoTipoEspecificacaoMensagem();
						filtroSolicitacaoTipoEspecificacaoMensagem.adicionarParametro(new ParametroSimples(
										FiltroSolicitacaoTipoEspecificacaoMensagem.ID, Integer
														.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm
																		.getIdSolicitacaoTipoEspecificacaoMensagem())));
						filtroSolicitacaoTipoEspecificacaoMensagem.adicionarParametro(new ParametroSimples(
										FiltroSolicitacaoTipoEspecificacaoMensagem.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

						SolicitacaoTipoEspecificacaoMensagem objetoSolicitacaoTipoEspecificacaoMensagem = (SolicitacaoTipoEspecificacaoMensagem) Util
										.retonarObjetoDeColecao(fachada.pesquisar(filtroSolicitacaoTipoEspecificacaoMensagem,
														SolicitacaoTipoEspecificacaoMensagem.class.getName()));

						if(objetoSolicitacaoTipoEspecificacaoMensagem == null){
							throw new ActionServletException("atencao.pesquisa_inexistente", null, "Mensagem Automática Padrão");
						}

					}

					if(!Util.isVazioOuBranco(atualizarAdicionarSolicitacaoEspecificacaoActionForm
									.getIdSolicitacaoTipoEspecificacaoMensagem())){
						FiltroSolicitacaoTipoEspecificacaoMensagem filtroSolicitacaoTipoEspecificacaoMensagem = new FiltroSolicitacaoTipoEspecificacaoMensagem();
						filtroSolicitacaoTipoEspecificacaoMensagem.adicionarParametro(new ParametroSimples(
										FiltroSolicitacaoTipoEspecificacaoMensagem.ID, Integer
														.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm
																		.getIdSolicitacaoTipoEspecificacaoMensagem())));
						filtroSolicitacaoTipoEspecificacaoMensagem.adicionarParametro(new ParametroSimples(
										FiltroSolicitacaoTipoEspecificacaoMensagem.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

						SolicitacaoTipoEspecificacaoMensagem objetoSolicitacaoTipoEspecificacaoMensagem = (SolicitacaoTipoEspecificacaoMensagem) Util
										.retonarObjetoDeColecao(fachada.pesquisar(filtroSolicitacaoTipoEspecificacaoMensagem,
														SolicitacaoTipoEspecificacaoMensagem.class.getName()));

						if(objetoSolicitacaoTipoEspecificacaoMensagem == null){
							throw new ActionServletException("atencao.pesquisa_inexistente", null, "Mensagem Automática Padrão");
						}

						solicitacaoTipoEspecificacao.setSolicitacaoTipoEspecificacaoMensagem(objetoSolicitacaoTipoEspecificacaoMensagem);
					}else{
						solicitacaoTipoEspecificacao.setSolicitacaoTipoEspecificacaoMensagem(null);
					}

					// Gera ordem de serviço
					// solicitacaoTipoEspecificacao.setIndicadorGeracaoOrdemServico(ConstantesSistema.SIM);

					// Cliente Obrigatório
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCliente() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCliente().equals("")){

						solicitacaoTipoEspecificacao.setIndicadorCliente(new Short(atualizarAdicionarSolicitacaoEspecificacaoActionForm
										.getIndicadorCliente()));
					}

					// Indicador verfificcação de débito
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorVerificarDebito() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorVerificarDebito().equals("")){

						solicitacaoTipoEspecificacao.setIndicadorVerificarDebito(Short
										.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorVerificarDebito()));

						if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorTipoVerificarDebito() != null
										&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorTipoVerificarDebito().equals(
														"")){

							solicitacaoTipoEspecificacao
											.setIndicadorTipoVerificarDebito(Short
															.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm
																			.getIndicadorTipoVerificarDebito()));

							if(solicitacaoTipoEspecificacao.getIndicadorTipoVerificarDebito().toString()
											.equals(SolicitacaoTipoEspecificacao.INDICADOR_DEBITOS_IMOVEIS.toString())
											|| solicitacaoTipoEspecificacao.getIndicadorTipoVerificarDebito().toString()
															.equals(SolicitacaoTipoEspecificacao.INDICADOR_DEBITOS_AMBOS.toString())){

								if(!Util.isVazioOuBranco(atualizarAdicionarSolicitacaoEspecificacaoActionForm
												.getIndicadorConsiderarApenasDebitoTitularAtual())){

									solicitacaoTipoEspecificacao.setIndicadorConsiderarApenasDebitoTitularAtual(Short
													.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm
																	.getIndicadorConsiderarApenasDebitoTitularAtual()));
								}else{

									solicitacaoTipoEspecificacao.setIndicadorConsiderarApenasDebitoTitularAtual(ConstantesSistema.NAO);
								}

								if(solicitacaoTipoEspecificacao.getIndicadorConsiderarApenasDebitoTitularAtual().equals(
												ConstantesSistema.NAO)
												|| Util.isVazioOuBranco(atualizarAdicionarSolicitacaoEspecificacaoActionForm
																.getIdClienteRelacaoTipo())
												|| atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIdClienteRelacaoTipo().equals(
																ConstantesSistema.NUMERO_NAO_INFORMADO_STRING)){

									solicitacaoTipoEspecificacao.setClienteRelacaoTipo(null);
								}else{

									ClienteRelacaoTipo clienteRelacaoTipo = new ClienteRelacaoTipo();
									clienteRelacaoTipo.setId(Util.obterInteger(atualizarAdicionarSolicitacaoEspecificacaoActionForm
													.getIdClienteRelacaoTipo()));
									solicitacaoTipoEspecificacao.setClienteRelacaoTipo(clienteRelacaoTipo);
								}
							}else{

								solicitacaoTipoEspecificacao.setIndicadorConsiderarApenasDebitoTitularAtual(ConstantesSistema.NAO);
								solicitacaoTipoEspecificacao.setClienteRelacaoTipo(null);
							}
						}else{

							solicitacaoTipoEspecificacao.setIndicadorConsiderarApenasDebitoTitularAtual(ConstantesSistema.NAO);
							solicitacaoTipoEspecificacao.setClienteRelacaoTipo(null);
						}
					}else{

						solicitacaoTipoEspecificacao.setIndicadorConsiderarApenasDebitoTitularAtual(ConstantesSistema.NAO);
						solicitacaoTipoEspecificacao.setClienteRelacaoTipo(null);
					}

					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCalculoDataPrevistaAtendimento() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCalculoDataPrevistaAtendimento()
													.equals("")){

						solicitacaoTipoEspecificacao.setIndicadorCalculoDataPrevistaAtendimento(Short
										.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm
														.getIndicadorCalculoDataPrevistaAtendimento()));
					}

					// Situação imovel
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIdSituacaoImovel() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIdSituacaoImovel().equals("")){
						EspecificacaoImovelSituacao especificacaoImovelSituacao = new EspecificacaoImovelSituacao();
						especificacaoImovelSituacao.setId(new Integer(atualizarAdicionarSolicitacaoEspecificacaoActionForm
										.getIdSituacaoImovel()));
						solicitacaoTipoEspecificacao.setEspecificacaoImovelSituacao(especificacaoImovelSituacao);
					}else{
						solicitacaoTipoEspecificacao.setEspecificacaoImovelSituacao(null);
					}

					Collection<EspecificacaoServicoTipo> colecaoEspecificacaoServicoTipo = (Collection) sessao
									.getAttribute("colecaoEspecificacaoServicoTipo");

					// recupera a coleção de especificacao servico tipo
					if(colecaoEspecificacaoServicoTipo != null && !colecaoEspecificacaoServicoTipo.isEmpty()){

						Collection colecao = new ArrayList();
						colecao.addAll(colecaoEspecificacaoServicoTipo);
						// [SF0004] - Validar Valor Ordem de Serviço 2ª parte
						fachada.verificarOrdemExecucaoForaOrdem(colecao);

						// Ordem de Serviço Automática
						for(EspecificacaoServicoTipo especificacaoServicoTipo : colecaoEspecificacaoServicoTipo){
							Integer idServicoTipo = especificacaoServicoTipo.getComp_id().getIdServicoTipo();

							String parametroIndicadorGeracaoAutomatica = "indicadorGeracaoAutomatica" + idServicoTipo;
							String indicadorGeracaoAutomaticaStr = httpServletRequest.getParameter(parametroIndicadorGeracaoAutomatica);

							if(!Util.isVazioOuBranco(indicadorGeracaoAutomaticaStr)){
								Short indicadorGeracaoAutomatica = Short.valueOf(indicadorGeracaoAutomaticaStr);
								especificacaoServicoTipo.setIndicadorGeracaoAutomatica(indicadorGeracaoAutomatica);
							}
						}

						solicitacaoTipoEspecificacao.setEspecificacaoServicoTipos(new HashSet(colecaoEspecificacaoServicoTipo));
						sessao.removeAttribute("colecaoEspecificacaoServicoTipo");
					}

					if(getParametroCompanhia(httpServletRequest) == SistemaParametro.INDICADOR_EMPRESA_DESO.shortValue()){
						// Indicador Conta em Revisão.
						if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorContaEmRevisao() != null
										&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorContaEmRevisao().equals("")){
							solicitacaoTipoEspecificacao.setIndicadorContaEmRevisao(Short
											.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorContaEmRevisao()));
							if(Short.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorContaEmRevisao()) == ConstantesSistema.SIM
											.shortValue()){
								if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorMensagemAlertaRevisao() != null
												&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm
																.getIndicadorMensagemAlertaRevisao().equals("")){
									solicitacaoTipoEspecificacao.setIndicadorMensagemAlertaRevisao(Short
													.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm
																	.getIndicadorMensagemAlertaRevisao()));
								}
							}else{
								solicitacaoTipoEspecificacao.setIndicadorMensagemAlertaRevisao(null);
							}
						}
					}

					// indicador de uso ativo
					solicitacaoTipoEspecificacao.setIndicadorUso(new Short(ConstantesSistema.INDICADOR_USO_ATIVO));
					SolicitacaoTipo solicitacaoTipo = new SolicitacaoTipo();
					solicitacaoTipo = (SolicitacaoTipo) sessao.getAttribute("solicitacaoTipoAtualizar");
					solicitacaoTipoEspecificacao.setSolicitacaoTipo(solicitacaoTipo);
				}
			}
		}else{

			SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = new SolicitacaoTipoEspecificacao();
			solicitacaoTipoEspecificacao.setIndicadorSolicitante(new Short("1"));

			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getPrazoPrevisaoAtendimento() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getPrazoPrevisaoAtendimento().equals("")){
				// Prazo de previsão de atendimento
				solicitacaoTipoEspecificacao.setHorasPrazo(new Integer(atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.getPrazoPrevisaoAtendimento()));
			}
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCalculoDataPrevistaAtendimento() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCalculoDataPrevistaAtendimento()
											.equals("")){
				solicitacaoTipoEspecificacao.setIndicadorCalculoDataPrevistaAtendimento(new Short(
								atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCalculoDataPrevistaAtendimento()));
			}
			// Descrição da especificação
			solicitacaoTipoEspecificacao.setDescricao(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getDescricaoSolicitacao());

			// Pavimento de calçada obrigatório
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorPavimentoCalcada() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorPavimentoCalcada().equals("")){
				solicitacaoTipoEspecificacao.setIndicadorPavimentoCalcada(new Short(atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.getIndicadorPavimentoCalcada()));
			}
			// Pavimento de rua obrigatório
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorPavimentoRua() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorPavimentoRua().equals("")){
				solicitacaoTipoEspecificacao.setIndicadorPavimentoRua(new Short(atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.getIndicadorPavimentoRua()));
			}

			// refere-se a ligação de agua
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorLigacaoAgua() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorLigacaoAgua().equals("")){
				solicitacaoTipoEspecificacao.setIndicadorLigacaoAgua(new Short(atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.getIndicadorLigacaoAgua()));
			}
			// Cobrança de material
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCobrancaMaterial() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCobrancaMaterial().equals("")){
				solicitacaoTipoEspecificacao.setIndicadorCobrancaMaterial(new Integer(atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.getIndicadorCobrancaMaterial()));
			}
			// Matricula do imóvel obrigatório
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorMatriculaImovel() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorMatriculaImovel().equals("")){
				solicitacaoTipoEspecificacao.setIndicadorMatricula(new Integer(atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.getIndicadorMatriculaImovel()));
			}
			// Indicador de coletor obrigatório
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorColetor() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorColetor().equals("")){
				solicitacaoTipoEspecificacao.setIndicadorColetor(Short.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.getIndicadorColetor()));
			}
			// Indicador de coletor obrigatório
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorColetor() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorColetor().equals("")){
				solicitacaoTipoEspecificacao.setIndicadorColetor(Short.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.getIndicadorColetor()));
			}
			// Indicador de obrigatoriedade de preenchimento do RG na RA
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorObrigatoriedadeRgRA() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorObrigatoriedadeRgRA().equals("")){
				solicitacaoTipoEspecificacao.setIndicadorObrigatoriedadeRgRA(Short
								.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorObrigatoriedadeRgRA()));
			}
			// Indicador de obrigatoriedade de preenchimento do CPF/CNPJ na RA
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorObrigatoriedadeCpfCnpjRA() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorObrigatoriedadeCpfCnpjRA().equals("")){
				solicitacaoTipoEspecificacao.setIndicadorObrigatoriedadeCpfCnpjRA(Short
								.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorObrigatoriedadeCpfCnpjRA()));
			}
			// Parecer de encerramento obrigatório
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorParecerEncerramento() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorParecerEncerramento().equals("")){
				solicitacaoTipoEspecificacao.setIndicadorParecerEncerramento(new Integer(
								atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorParecerEncerramento()));
			}
			// Gera débito
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarDebito() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarDebito().equals("")){
				solicitacaoTipoEspecificacao.setIndicadorGeracaoDebito(new Integer(atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.getIndicadorGerarDebito()));
			}
			// Gera Crédito
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarCredito() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarCredito().equals("")){
				solicitacaoTipoEspecificacao.setIndicadorGeracaoCredito(new Integer(atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.getIndicadorGerarCredito()));
			}
			// hora e data correntes
			solicitacaoTipoEspecificacao.setUltimaAlteracao(new Date());

			// Unidade inicial tramitação
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIdUnidadeTramitacao() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIdUnidadeTramitacao().equals("")){
				// Verifica se o código foi modificado
				if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getDescricaoUnidadeTramitacao() == null
								|| atualizarAdicionarSolicitacaoEspecificacaoActionForm.getDescricaoUnidadeTramitacao().trim().equals("")){

					FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();

					filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID,
									atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIdUnidadeTramitacao()));

					filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.INDICADOR_USO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					Collection unidadeOrganizacionalEncontrado = fachada.pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class
									.getName());

					if(unidadeOrganizacionalEncontrado != null && !unidadeOrganizacionalEncontrado.isEmpty()){
						UnidadeOrganizacional uinidadeOrganizacional = (UnidadeOrganizacional) ((List) unidadeOrganizacionalEncontrado)
										.get(0);
						solicitacaoTipoEspecificacao.setUnidadeOrganizacional(uinidadeOrganizacional);

					}else{
						throw new ActionServletException("atencao.pesquisa_inexistente", null, "Unidade Organizacional");
					}

				}else{
					UnidadeOrganizacional unidadeOrganizacional = new UnidadeOrganizacional();
					unidadeOrganizacional.setId(new Integer(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIdUnidadeTramitacao()));
					solicitacaoTipoEspecificacao.setUnidadeOrganizacional(unidadeOrganizacional);
				}
			}

			// id do tipo da solicitação gerada
			solicitacaoTipoEspecificacao.setServicoTipo(null);

			if(!Util.isVazioOuBranco(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIdSolicitacaoTipoEspecificacaoMensagem())){
				FiltroSolicitacaoTipoEspecificacaoMensagem filtroSolicitacaoTipoEspecificacaoMensagem = new FiltroSolicitacaoTipoEspecificacaoMensagem();
				filtroSolicitacaoTipoEspecificacaoMensagem.adicionarParametro(new ParametroSimples(
								FiltroSolicitacaoTipoEspecificacaoMensagem.ID, Integer
												.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm
																.getIdSolicitacaoTipoEspecificacaoMensagem())));
				filtroSolicitacaoTipoEspecificacaoMensagem.adicionarParametro(new ParametroSimples(
								FiltroSolicitacaoTipoEspecificacaoMensagem.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));

				SolicitacaoTipoEspecificacaoMensagem objetoSolicitacaoTipoEspecificacaoMensagem = (SolicitacaoTipoEspecificacaoMensagem) Util
								.retonarObjetoDeColecao(fachada.pesquisar(filtroSolicitacaoTipoEspecificacaoMensagem,
												SolicitacaoTipoEspecificacaoMensagem.class.getName()));

				if(objetoSolicitacaoTipoEspecificacaoMensagem == null){
					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Mensagem Automática Padrão");
				}

				solicitacaoTipoEspecificacao.setSolicitacaoTipoEspecificacaoMensagem(objetoSolicitacaoTipoEspecificacaoMensagem);
			}else{
				solicitacaoTipoEspecificacao.setSolicitacaoTipoEspecificacaoMensagem(null);
			}

			// Gera ordem de serviço
			solicitacaoTipoEspecificacao.setIndicadorGeracaoOrdemServico(ConstantesSistema.SIM);

			// Cliente Obrigatório
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCliente() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCliente().equals("")){

				solicitacaoTipoEspecificacao.setIndicadorCliente(new Short(atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.getIndicadorCliente()));
			}

			// Indicador verfificcação de débito
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorVerificarDebito() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorVerificarDebito().equals("")){

				solicitacaoTipoEspecificacao.setIndicadorVerificarDebito(new Short(atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.getIndicadorVerificarDebito()));

				if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorTipoVerificarDebito() != null
								&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorTipoVerificarDebito().equals("")){
					solicitacaoTipoEspecificacao.setIndicadorTipoVerificarDebito(Short
									.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorTipoVerificarDebito()));

					if(solicitacaoTipoEspecificacao.getIndicadorTipoVerificarDebito().toString()
									.equals(SolicitacaoTipoEspecificacao.INDICADOR_DEBITOS_IMOVEIS.toString())
									|| solicitacaoTipoEspecificacao.getIndicadorTipoVerificarDebito().toString()
													.equals(SolicitacaoTipoEspecificacao.INDICADOR_DEBITOS_AMBOS.toString())){

						if(!Util.isVazioOuBranco(atualizarAdicionarSolicitacaoEspecificacaoActionForm
										.getIndicadorConsiderarApenasDebitoTitularAtual())){

							solicitacaoTipoEspecificacao.setIndicadorConsiderarApenasDebitoTitularAtual(Short
											.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm
															.getIndicadorConsiderarApenasDebitoTitularAtual()));
						}

						if(solicitacaoTipoEspecificacao.getIndicadorConsiderarApenasDebitoTitularAtual().equals(ConstantesSistema.NAO)
										|| Util.isVazioOuBranco(atualizarAdicionarSolicitacaoEspecificacaoActionForm
														.getIdClienteRelacaoTipo())
										|| atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIdClienteRelacaoTipo().equals(
														ConstantesSistema.NUMERO_NAO_INFORMADO_STRING)){

							solicitacaoTipoEspecificacao.setClienteRelacaoTipo(null);
						}else{

							ClienteRelacaoTipo clienteRelacaoTipo = new ClienteRelacaoTipo();
							clienteRelacaoTipo.setId(Util.obterInteger(atualizarAdicionarSolicitacaoEspecificacaoActionForm
											.getIdClienteRelacaoTipo()));
							solicitacaoTipoEspecificacao.setClienteRelacaoTipo(clienteRelacaoTipo);
						}
					}else{

						solicitacaoTipoEspecificacao.setIndicadorConsiderarApenasDebitoTitularAtual(ConstantesSistema.NAO);
						solicitacaoTipoEspecificacao.setClienteRelacaoTipo(null);
					}
				}else{

					solicitacaoTipoEspecificacao.setIndicadorConsiderarApenasDebitoTitularAtual(ConstantesSistema.NAO);
					solicitacaoTipoEspecificacao.setClienteRelacaoTipo(null);
				}
			}else{

				solicitacaoTipoEspecificacao.setIndicadorConsiderarApenasDebitoTitularAtual(ConstantesSistema.NAO);
				solicitacaoTipoEspecificacao.setClienteRelacaoTipo(null);
			}

			// Situação imovel
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIdSituacaoImovel() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIdSituacaoImovel().equals("")){
				EspecificacaoImovelSituacao especificacaoImovelSituacao = new EspecificacaoImovelSituacao();
				especificacaoImovelSituacao.setId(new Integer(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIdSituacaoImovel()));
				solicitacaoTipoEspecificacao.setEspecificacaoImovelSituacao(especificacaoImovelSituacao);
			}else{
				solicitacaoTipoEspecificacao.setEspecificacaoImovelSituacao(null);
			}

			Collection<EspecificacaoServicoTipo> colecaoEspecificacaoServicoTipo = (Collection) sessao
							.getAttribute("colecaoEspecificacaoServicoTipo");

			// recupera a coleção de especificacao servico tipo
			if(colecaoEspecificacaoServicoTipo != null && !colecaoEspecificacaoServicoTipo.isEmpty()){

				Collection colecao = new ArrayList();
				colecao.addAll(colecaoEspecificacaoServicoTipo);

				// [SF0004] - Validar Valor Ordem de Serviço 2ª parte
				fachada.verificarOrdemExecucaoForaOrdem(colecao);

				// Ordem de Serviço Automática
				for(EspecificacaoServicoTipo especificacaoServicoTipo : colecaoEspecificacaoServicoTipo){
					Date ultimaAlteracao = especificacaoServicoTipo.getUltimaAlteracao();
					long timeUltimaAlteracao = ultimaAlteracao.getTime();

					String parametroIndicadorGeracaoAutomatica = "indicadorGeracaoAutomatica" + timeUltimaAlteracao;
					String indicadorGeracaoAutomaticaStr = httpServletRequest.getParameter(parametroIndicadorGeracaoAutomatica);

					if(!Util.isVazioOuBranco(indicadorGeracaoAutomaticaStr)){
						Short indicadorGeracaoAutomatica = Short.valueOf(indicadorGeracaoAutomaticaStr);
						especificacaoServicoTipo.setIndicadorGeracaoAutomatica(indicadorGeracaoAutomatica);
					}
				}

				solicitacaoTipoEspecificacao.setEspecificacaoServicoTipos(new HashSet(colecaoEspecificacaoServicoTipo));
				sessao.removeAttribute("colecaoEspecificacaoServicoTipo");
			}

			if(getParametroCompanhia(httpServletRequest) == SistemaParametro.INDICADOR_EMPRESA_DESO.shortValue()){
				// Indicador Conta em Revisão.
				if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorContaEmRevisao() != null
								&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorContaEmRevisao().equals("")){
					solicitacaoTipoEspecificacao.setIndicadorContaEmRevisao(Short
									.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorContaEmRevisao()));
					if(Short.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorContaEmRevisao()) == ConstantesSistema.SIM
									.shortValue()){
						if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorMensagemAlertaRevisao() != null
										&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorMensagemAlertaRevisao()
														.equals("")){
							solicitacaoTipoEspecificacao.setIndicadorMensagemAlertaRevisao(Short
											.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm
															.getIndicadorMensagemAlertaRevisao()));
						}
					}else{
						solicitacaoTipoEspecificacao.setIndicadorMensagemAlertaRevisao(null);
					}
				}
			}

			if(getParametroCompanhia(httpServletRequest) == SistemaParametro.INDICADOR_EMPRESA_DESO.shortValue()){
				// Indicador Conta em Revisão.
				if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorContaEmRevisao() != null
								&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorContaEmRevisao().equals("")){
					solicitacaoTipoEspecificacao.setIndicadorContaEmRevisao(Short
									.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorContaEmRevisao()));
					if(Short.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorContaEmRevisao()) == ConstantesSistema.SIM
									.shortValue()){
						if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorMensagemAlertaRevisao() != null
										&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorMensagemAlertaRevisao()
														.equals("")){
							solicitacaoTipoEspecificacao.setIndicadorMensagemAlertaRevisao(Short
											.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm
															.getIndicadorMensagemAlertaRevisao()));
						}
					}else{
						solicitacaoTipoEspecificacao.setIndicadorMensagemAlertaRevisao(null);
					}
				}
			}
			// indicador de uso ativo
			solicitacaoTipoEspecificacao.setIndicadorUso(new Short(ConstantesSistema.INDICADOR_USO_ATIVO));

			SolicitacaoTipo solicitacaoTipo = new SolicitacaoTipo();

			solicitacaoTipo = (SolicitacaoTipo) sessao.getAttribute("solicitacaoTipoAtualizar");

			solicitacaoTipoEspecificacao.setSolicitacaoTipo(solicitacaoTipo);
			if(!colecaoSolicitacaoTipoEspecificacao.contains(solicitacaoTipoEspecificacao)){
				colecaoSolicitacaoTipoEspecificacao.add(solicitacaoTipoEspecificacao);
			}

		}

		sessao.setAttribute("colecaoSolicitacaoTipoEspecificacao", colecaoSolicitacaoTipoEspecificacao);

		// manda um parametro para fechar o popup
		httpServletRequest.setAttribute("fecharPopup", 1);

		// no vo tetetete

		return retorno;
	}
}
