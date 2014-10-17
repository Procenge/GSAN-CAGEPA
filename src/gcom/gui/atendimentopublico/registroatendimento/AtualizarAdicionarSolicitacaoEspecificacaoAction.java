/**
 * 
 */
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
 * Descri��o da classe
 * 
 * @author R�mulo Aur�lio
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

		// Mudar isso quando tiver esquema de seguran�a
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

		// Obt�m a inst�ncia da Fachada
		Fachada fachada = Fachada.getInstancia();

		if(sessao.getAttribute("atualizar") != null){

			// Obs: Se remover tudo da cole��o, qdo a cole�ao tiver mais de um elemento vai dar pau.
			// Entao, terei que Instaciar um objeto pra
			// receber os dados da colecao que esta na sessao, para depois comparar com a que esta
			// sendo alterada, pra depois fazer
			// altera��o.

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
						// Prazo de previs�o de atendimento
						solicitacaoTipoEspecificacao.setHorasPrazo(new Integer(atualizarAdicionarSolicitacaoEspecificacaoActionForm
										.getPrazoPrevisaoAtendimento()));
					}
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCalculoDataPrevistaAtendimento() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCalculoDataPrevistaAtendimento()
													.equals("")){
						// Prazo de previs�o de atendimento
						solicitacaoTipoEspecificacao.setIndicadorCalculoDataPrevistaAtendimento(new Short(
										atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCalculoDataPrevistaAtendimento()));
					}
					// Descri��o da especifica��o
					solicitacaoTipoEspecificacao.setDescricao(atualizarAdicionarSolicitacaoEspecificacaoActionForm
									.getDescricaoSolicitacao());

					// Pavimento de cal�ada obrigat�rio
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorPavimentoCalcada() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorPavimentoCalcada().equals("")){
						solicitacaoTipoEspecificacao.setIndicadorPavimentoCalcada(new Short(
										atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorPavimentoCalcada()));
					}
					// Pavimento de rua obrigat�rio
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorPavimentoRua() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorPavimentoRua().equals("")){
						solicitacaoTipoEspecificacao.setIndicadorPavimentoRua(new Short(
										atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorPavimentoRua()));
					}
					// Indicador de coletor obrigat�rio
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorColetor() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorColetor().equals("")){
						solicitacaoTipoEspecificacao.setIndicadorColetor(Short.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm
										.getIndicadorColetor()));
					}
					// Indicador de coletor obrigat�rio
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

					// refere-se a liga��o de agua
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorLigacaoAgua() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorLigacaoAgua().equals("")){
						solicitacaoTipoEspecificacao.setIndicadorLigacaoAgua(new Short(atualizarAdicionarSolicitacaoEspecificacaoActionForm
										.getIndicadorLigacaoAgua()));
					}
					// Cobran�a de material
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCobrancaMaterial() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCobrancaMaterial().equals("")){
						solicitacaoTipoEspecificacao.setIndicadorCobrancaMaterial(new Integer(
										atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCobrancaMaterial()));
					}
					// Matricula do im�vel obrigat�rio
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorMatriculaImovel() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorMatriculaImovel().equals("")){
						solicitacaoTipoEspecificacao.setIndicadorMatricula(new Integer(atualizarAdicionarSolicitacaoEspecificacaoActionForm
										.getIndicadorMatriculaImovel()));
					}
					// Parecer de encerramento obrigat�rio
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorParecerEncerramento() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorParecerEncerramento().equals("")){
						solicitacaoTipoEspecificacao.setIndicadorParecerEncerramento(new Integer(
										atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorParecerEncerramento()));
					}
					// Gera d�bito
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarDebito() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarDebito().equals("")){
						solicitacaoTipoEspecificacao.setIndicadorGeracaoDebito(new Integer(
										atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarDebito()));
					}
					// Gera Cr�dito
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarCredito() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarCredito().equals("")){
						solicitacaoTipoEspecificacao.setIndicadorGeracaoCredito(new Integer(
										atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarCredito()));
					}
					// Gera Ordem de Servi�o
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarOrdemServico() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarOrdemServico().equals("")){
						solicitacaoTipoEspecificacao.setIndicadorGeracaoOrdemServico(new Short(
										atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarOrdemServico()));
					}

					// hora e data correntes
					solicitacaoTipoEspecificacao.setUltimaAlteracao(new Date());

					// Unidade inicial tramita��o
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIdUnidadeTramitacao() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIdUnidadeTramitacao().equals("")){
						// Verifica se o c�digo foi modificado
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

					// id do tipo da solicita��o gerada
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
							throw new ActionServletException("atencao.pesquisa_inexistente", null, "Mensagem Autom�tica Padr�o");
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
							throw new ActionServletException("atencao.pesquisa_inexistente", null, "Mensagem Autom�tica Padr�o");
						}

						solicitacaoTipoEspecificacao.setSolicitacaoTipoEspecificacaoMensagem(objetoSolicitacaoTipoEspecificacaoMensagem);
					}else{
						solicitacaoTipoEspecificacao.setSolicitacaoTipoEspecificacaoMensagem(null);
					}

					// Gera ordem de servi�o
					// solicitacaoTipoEspecificacao.setIndicadorGeracaoOrdemServico(ConstantesSistema.SIM);

					// Cliente Obrigat�rio
					if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCliente() != null
									&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCliente().equals("")){

						solicitacaoTipoEspecificacao.setIndicadorCliente(new Short(atualizarAdicionarSolicitacaoEspecificacaoActionForm
										.getIndicadorCliente()));
					}

					// Indicador verfificca��o de d�bito
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

					// Situa��o imovel
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

					// recupera a cole��o de especificacao servico tipo
					if(colecaoEspecificacaoServicoTipo != null && !colecaoEspecificacaoServicoTipo.isEmpty()){

						Collection colecao = new ArrayList();
						colecao.addAll(colecaoEspecificacaoServicoTipo);
						// [SF0004] - Validar Valor Ordem de Servi�o 2� parte
						fachada.verificarOrdemExecucaoForaOrdem(colecao);

						// Ordem de Servi�o Autom�tica
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
						// Indicador Conta em Revis�o.
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
				// Prazo de previs�o de atendimento
				solicitacaoTipoEspecificacao.setHorasPrazo(new Integer(atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.getPrazoPrevisaoAtendimento()));
			}
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCalculoDataPrevistaAtendimento() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCalculoDataPrevistaAtendimento()
											.equals("")){
				solicitacaoTipoEspecificacao.setIndicadorCalculoDataPrevistaAtendimento(new Short(
								atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCalculoDataPrevistaAtendimento()));
			}
			// Descri��o da especifica��o
			solicitacaoTipoEspecificacao.setDescricao(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getDescricaoSolicitacao());

			// Pavimento de cal�ada obrigat�rio
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorPavimentoCalcada() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorPavimentoCalcada().equals("")){
				solicitacaoTipoEspecificacao.setIndicadorPavimentoCalcada(new Short(atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.getIndicadorPavimentoCalcada()));
			}
			// Pavimento de rua obrigat�rio
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorPavimentoRua() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorPavimentoRua().equals("")){
				solicitacaoTipoEspecificacao.setIndicadorPavimentoRua(new Short(atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.getIndicadorPavimentoRua()));
			}

			// refere-se a liga��o de agua
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorLigacaoAgua() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorLigacaoAgua().equals("")){
				solicitacaoTipoEspecificacao.setIndicadorLigacaoAgua(new Short(atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.getIndicadorLigacaoAgua()));
			}
			// Cobran�a de material
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCobrancaMaterial() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCobrancaMaterial().equals("")){
				solicitacaoTipoEspecificacao.setIndicadorCobrancaMaterial(new Integer(atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.getIndicadorCobrancaMaterial()));
			}
			// Matricula do im�vel obrigat�rio
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorMatriculaImovel() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorMatriculaImovel().equals("")){
				solicitacaoTipoEspecificacao.setIndicadorMatricula(new Integer(atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.getIndicadorMatriculaImovel()));
			}
			// Indicador de coletor obrigat�rio
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorColetor() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorColetor().equals("")){
				solicitacaoTipoEspecificacao.setIndicadorColetor(Short.valueOf(atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.getIndicadorColetor()));
			}
			// Indicador de coletor obrigat�rio
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
			// Parecer de encerramento obrigat�rio
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorParecerEncerramento() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorParecerEncerramento().equals("")){
				solicitacaoTipoEspecificacao.setIndicadorParecerEncerramento(new Integer(
								atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorParecerEncerramento()));
			}
			// Gera d�bito
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarDebito() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarDebito().equals("")){
				solicitacaoTipoEspecificacao.setIndicadorGeracaoDebito(new Integer(atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.getIndicadorGerarDebito()));
			}
			// Gera Cr�dito
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarCredito() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorGerarCredito().equals("")){
				solicitacaoTipoEspecificacao.setIndicadorGeracaoCredito(new Integer(atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.getIndicadorGerarCredito()));
			}
			// hora e data correntes
			solicitacaoTipoEspecificacao.setUltimaAlteracao(new Date());

			// Unidade inicial tramita��o
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIdUnidadeTramitacao() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIdUnidadeTramitacao().equals("")){
				// Verifica se o c�digo foi modificado
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

			// id do tipo da solicita��o gerada
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
					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Mensagem Autom�tica Padr�o");
				}

				solicitacaoTipoEspecificacao.setSolicitacaoTipoEspecificacaoMensagem(objetoSolicitacaoTipoEspecificacaoMensagem);
			}else{
				solicitacaoTipoEspecificacao.setSolicitacaoTipoEspecificacaoMensagem(null);
			}

			// Gera ordem de servi�o
			solicitacaoTipoEspecificacao.setIndicadorGeracaoOrdemServico(ConstantesSistema.SIM);

			// Cliente Obrigat�rio
			if(atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCliente() != null
							&& !atualizarAdicionarSolicitacaoEspecificacaoActionForm.getIndicadorCliente().equals("")){

				solicitacaoTipoEspecificacao.setIndicadorCliente(new Short(atualizarAdicionarSolicitacaoEspecificacaoActionForm
								.getIndicadorCliente()));
			}

			// Indicador verfificca��o de d�bito
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

			// Situa��o imovel
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

			// recupera a cole��o de especificacao servico tipo
			if(colecaoEspecificacaoServicoTipo != null && !colecaoEspecificacaoServicoTipo.isEmpty()){

				Collection colecao = new ArrayList();
				colecao.addAll(colecaoEspecificacaoServicoTipo);

				// [SF0004] - Validar Valor Ordem de Servi�o 2� parte
				fachada.verificarOrdemExecucaoForaOrdem(colecao);

				// Ordem de Servi�o Autom�tica
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
				// Indicador Conta em Revis�o.
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
				// Indicador Conta em Revis�o.
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
