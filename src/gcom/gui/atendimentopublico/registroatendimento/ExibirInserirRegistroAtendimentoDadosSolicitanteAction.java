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

import gcom.atendimentopublico.registroatendimento.FiltroSolicitacaoTipoEspecificacao;
import gcom.atendimentopublico.registroatendimento.MeioSolicitacao;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
import gcom.cadastro.cliente.*;
import gcom.cadastro.funcionario.FiltroFuncionario;
import gcom.cadastro.funcionario.Funcionario;
import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.FiltroUnidadeFederacao;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.geografico.UnidadeFederacao;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cadastro.unidade.FiltroUnidadeOrganizacional;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Esta classe tem por finalidade exibir para o usu�rio a tela que receber� os par�metros para
 * realiza��o
 * da inser��o de um R.A (Aba n� 03 - Dados do solicitante)
 * 
 * @author Raphael Rossiter
 * @date 25/07/2006
 */
public class ExibirInserirRegistroAtendimentoDadosSolicitanteAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("inserirRegistroAtendimentoDadosSolicitante");

		InserirRegistroAtendimentoActionForm form = (InserirRegistroAtendimentoActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		httpServletRequest.setAttribute("nomeCampo", "idCliente");

		/*
		 * Pesquisas realizadas a partir do ENTER
		 * 
		 * ==========================================================================================
		 * =================
		 */

		String pesquisarCliente = httpServletRequest.getParameter("pesquisarCliente");

		if(pesquisarCliente != null && !pesquisarCliente.equalsIgnoreCase("")){

			// [FS0027] � Verificar informa��o do im�vel
			Cliente cliente = fachada.verificarInformacaoImovel(Util.converterStringParaInteger(form.getIdCliente()), Util
							.converterStringParaInteger(form.getIdImovel()), false);

			if(cliente == null){

				form.setIdCliente("");
				form.setNomeCliente("Cliente inexistente");

				httpServletRequest.setAttribute("corCliente", "exception");
				httpServletRequest.setAttribute("nomeCampo", "idCliente");

			}else{

				form.setIdCliente(cliente.getId().toString());
				form.setNomeCliente(cliente.getNome());

				Collection colecaoEnderecos = fachada.pesquisarEnderecosClienteAbreviado(cliente.getId());

				if(colecaoEnderecos != null && !colecaoEnderecos.isEmpty()){

					Iterator endCorrespondencia = colecaoEnderecos.iterator();
					ClienteEndereco endereco = null;

					while(endCorrespondencia.hasNext()){

						endereco = (ClienteEndereco) endCorrespondencia.next();

						if(endereco.getIndicadorEnderecoCorrespondencia() != null
										&& endereco.getIndicadorEnderecoCorrespondencia().equals(
														ConstantesSistema.INDICADOR_ENDERECO_CORRESPONDENCIA)){

							form.setClienteEnderecoSelected(endereco.getId().toString());
							break;
						}
					}

					sessao.setAttribute("colecaoEnderecosAbaSolicitante", colecaoEnderecos);
					sessao.setAttribute("enderecoPertenceCliente", "OK");
				}

				Collection colecaoFones = fachada.pesquisarClienteFone(cliente.getId());

				if(colecaoFones != null && !colecaoFones.isEmpty()){

					Iterator fonePrincipal = colecaoFones.iterator();
					ClienteFone fone = null;

					while(fonePrincipal.hasNext()){

						fone = (ClienteFone) fonePrincipal.next();

						if(fone.getIndicadorTelefonePadrao() != null
										&& fone.getIndicadorTelefonePadrao().equals(ConstantesSistema.INDICADOR_TELEFONE_PRINCIPAL)){

							form.setClienteFoneSelected(fone.getId().toString());
							form.setFoneClienteFoneSelected(fone.getDddTelefone().toString());
							form.setClienteFoneSelected(fone.getId().toString());
							break;
						}
					}

					sessao.setAttribute("colecaoFonesAbaSolicitante", colecaoFones);
				}
				carregarDadosCliente(form, cliente);
				this.limparUnidadeSolicitante(sessao);
				this.limparNomeSolicitante(sessao);

				sessao.setAttribute("desabilitarDadosSolicitanteUnidade", "OK");
				sessao.setAttribute("desabilitarDadosSolicitanteFuncionario", "OK");
				sessao.setAttribute("desabilitarDadosSolicitanteNome", "OK");
				sessao.setAttribute("habilitarAlteracaoEnderecoSolicitante", "NAO");

			}
		}

		String pesquisarUnidade = httpServletRequest.getParameter("pesquisarUnidade");

		if(pesquisarUnidade != null && !pesquisarUnidade.equalsIgnoreCase("")){

			FiltroUnidadeOrganizacional filtroUnidadeOrganizacional = new FiltroUnidadeOrganizacional();

			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.ID, form
							.getIdUnidadeSolicitante()));

			filtroUnidadeOrganizacional.adicionarParametro(new ParametroSimples(FiltroUnidadeOrganizacional.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection colecaoUnidade = fachada.pesquisar(filtroUnidadeOrganizacional, UnidadeOrganizacional.class.getName());

			if(colecaoUnidade == null || colecaoUnidade.isEmpty()){

				form.setIdUnidadeSolicitante("");
				form.setDescricaoUnidadeSolicitante("Unidade Solicitante inexistente");

				httpServletRequest.setAttribute("corUnidadeSolicitante", "exception");
				httpServletRequest.setAttribute("nomeCampo", "idUnidadeSolicitante");

			}else{
				UnidadeOrganizacional unidade = (UnidadeOrganizacional) Util.retonarObjetoDeColecao(colecaoUnidade);

				sessao.setAttribute("enderecoPertenceCliente", "OK");

				form.setIdUnidadeSolicitante(unidade.getId().toString());
				form.setDescricaoUnidadeSolicitante(unidade.getDescricao());

				this.limparCliente(sessao);
				this.limparNomeSolicitante(sessao);

				httpServletRequest.setAttribute("nomeCampo", "idFuncionarioResponsavel");

				sessao.setAttribute("desabilitarDadosSolicitanteCliente", "OK");
				sessao.setAttribute("desabilitarDadosSolicitanteNome", "OK");
				sessao.setAttribute("habilitarAlteracaoEnderecoSolicitante", "NAO");
				sessao.setAttribute("habilitarAlteracaoEnderecoAbaSolicitante", "NAO");

			}
		}

		String pesquisarFuncionario = httpServletRequest.getParameter("pesquisarFuncionario");

		if(pesquisarFuncionario != null && !pesquisarFuncionario.equalsIgnoreCase("")){

			FiltroFuncionario filtroFuncionario = new FiltroFuncionario();

			filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.ID, form.getIdFuncionarioResponsavel()));

			filtroFuncionario.adicionarParametro(new ParametroSimples(FiltroFuncionario.UNIDADE_ORGANIZACIONAL_ID, form
							.getIdUnidadeSolicitante()));

			Collection colecaoFuncionario = fachada.pesquisar(filtroFuncionario, Funcionario.class.getName());

			if(colecaoFuncionario == null || colecaoFuncionario.isEmpty()){

				form.setIdFuncionarioResponsavel("");
				form.setNomeFuncionarioResponsavel("Funcion�rio inexistente");

				httpServletRequest.setAttribute("corFuncionario", "exception");
				httpServletRequest.setAttribute("nomeCampo", "idUnidadeSolicitanteInformar");

			}else{
				Funcionario funcionario = (Funcionario) Util.retonarObjetoDeColecao(colecaoFuncionario);

				form.setIdFuncionarioResponsavel(funcionario.getId().toString());
				form.setNomeFuncionarioResponsavel(funcionario.getNome());

				this.limparCliente(sessao);
				this.limparNomeSolicitante(sessao);

				sessao.setAttribute("desabilitarDadosSolicitanteCliente", "OK");
				sessao.setAttribute("desabilitarDadosSolicitanteNome", "OK");
				sessao.setAttribute("habilitarAlteracaoEnderecoSolicitante", "SIM");
			}
		}

		/*
		 * Fim das pesquisas realizadas pelo ENTER
		 * 
		 * ==========================================================================================
		 * =================
		 * 
		 * ==========================================================================================
		 * =================
		 */

		String informadoNomeSolicitante = httpServletRequest.getParameter("informadoNomeSolicitante");

		if(informadoNomeSolicitante != null && !informadoNomeSolicitante.equalsIgnoreCase("")){

			this.limparCliente(sessao);
			this.limparUnidadeSolicitante(sessao);

			sessao.setAttribute("desabilitarDadosSolicitanteCliente", "OK");
			sessao.setAttribute("desabilitarDadosSolicitanteUnidade", "OK");
			sessao.setAttribute("desabilitarDadosSolicitanteFuncionario", "OK");
			sessao.setAttribute("habilitarAlteracaoEnderecoSolicitante", "SIM");

			httpServletRequest.setAttribute("nomeCampo", "nomeSolicitante");
		}

		String limparCliente = httpServletRequest.getParameter("limparClienteSolicitante");

		if(limparCliente != null && !limparCliente.equalsIgnoreCase("")){

			this.limparCliente(sessao);

			httpServletRequest.setAttribute("nomeCampo", "idCliente");
		}

		String limparUnidadeSolicitante = httpServletRequest.getParameter("limparUnidadeSolicitante");

		if(limparUnidadeSolicitante != null && !limparUnidadeSolicitante.equalsIgnoreCase("")){

			this.limparUnidadeSolicitante(sessao);

			httpServletRequest.setAttribute("nomeCampo", "idCliente");
		}

		String limparNomeSolicitante = httpServletRequest.getParameter("limparNomeSolicitante");

		if(limparNomeSolicitante != null && !limparNomeSolicitante.equalsIgnoreCase("")){

			this.limparNomeSolicitante(sessao);

			httpServletRequest.setAttribute("nomeCampo", "idCliente");
		}

		// Remover Telefone
		String removerFone = httpServletRequest.getParameter("removerFone");

		if(removerFone != null && !removerFone.equalsIgnoreCase("")){

			long objetoRemocao = (Long.valueOf(httpServletRequest.getParameter("removerFone"))).longValue();
			Collection colecaoFones = (Collection) sessao.getAttribute("colecaoFonesAbaSolicitante");
			Iterator iteratorColecaoFones = colecaoFones.iterator();
			ClienteFone clienteFone = null;

			while(iteratorColecaoFones.hasNext()){
				clienteFone = (ClienteFone) iteratorColecaoFones.next();

				if(obterTimestampIdObjeto(clienteFone) == objetoRemocao){
					colecaoFones.remove(clienteFone);
					break;
				}
			}

		}

		/*
		 * Adicionar Fone
		 */
		String adicionarFone = httpServletRequest.getParameter("telaRetorno");

		if(adicionarFone != null && !adicionarFone.trim().equalsIgnoreCase("")){

			retorno = actionMapping.findForward("informarFone");
		}

		/*
		 * Removendo endere�o
		 */
		String removerEndereco = httpServletRequest.getParameter("removerEndereco");

		if(removerEndereco != null && !removerEndereco.trim().equalsIgnoreCase("")){

			if(sessao.getAttribute("colecaoEnderecosAbaSolicitante") != null){

				Collection enderecos = (Collection) sessao.getAttribute("colecaoEnderecosAbaSolicitante");

				if(!enderecos.isEmpty()){
					enderecos.remove(enderecos.iterator().next());
				}
			}
		}

		/*
		 * Adicionar endere�o
		 */
		String adicionarEndereco = httpServletRequest.getParameter("tipoPesquisaEndereco");

		if(adicionarEndereco != null && !adicionarEndereco.trim().equalsIgnoreCase("")){

			retorno = actionMapping.findForward("informarEndereco");
		}

		// Selecionar Fone com indicador padr�o
		String foneIndicadorPadrao = httpServletRequest.getParameter("clienteFoneSelected");

		this.selecionarFoneComIndicadorPadrao(foneIndicadorPadrao, sessao);

		// Disponibilizando os dados do cliente usu�rio do im�vel
		String retornoEndereco = httpServletRequest.getParameter("retornoEndereco");
		String retornoFone = httpServletRequest.getParameter("retornoFone");

		if(form.getIdImovel() != null && !form.getIdImovel().equalsIgnoreCase("")
						&& (pesquisarCliente == null || pesquisarCliente.equalsIgnoreCase(""))
						&& (pesquisarUnidade == null || pesquisarUnidade.equalsIgnoreCase(""))
						&& (pesquisarFuncionario == null || pesquisarFuncionario.equalsIgnoreCase(""))
						&& (informadoNomeSolicitante == null || informadoNomeSolicitante.equalsIgnoreCase(""))
						&& (limparCliente == null || limparCliente.equalsIgnoreCase(""))
						&& (limparUnidadeSolicitante == null || limparUnidadeSolicitante.equalsIgnoreCase(""))
						&& (limparNomeSolicitante == null || limparNomeSolicitante.equalsIgnoreCase(""))
						&& (removerFone == null || removerFone.equalsIgnoreCase(""))
						&& (removerEndereco == null || removerEndereco.equalsIgnoreCase(""))
						&& (retornoEndereco == null || retornoEndereco.equalsIgnoreCase(""))
						&& (adicionarEndereco == null || adicionarEndereco.equalsIgnoreCase(""))
						&& (adicionarFone == null || adicionarFone.equalsIgnoreCase(""))
						&& (retornoFone == null || retornoFone.equalsIgnoreCase(""))){

			Cliente clienteUsuarioImovel = fachada.pesquisarClienteUsuarioImovel(Util.converterStringParaInteger(form.getIdImovel()));

			if(clienteUsuarioImovel != null
							&& ((form.getIdCliente() == null || form.getIdCliente().equalsIgnoreCase(""))
											&& (form.getIdUnidadeSolicitante() == null || form.getIdUnidadeSolicitante().equalsIgnoreCase(
															""))
											&& (form.getIdFuncionarioResponsavel() == null || form.getIdFuncionarioResponsavel()
															.equalsIgnoreCase("")) && (form.getNomeSolicitante() == null || form
											.getNomeSolicitante().equalsIgnoreCase("")))
							|| form.getIdImovel().equalsIgnoreCase(form.getIdImovelAssociacaoCliente())){

				form.setIdCliente(clienteUsuarioImovel.getId().toString());
				form.setNomeCliente(clienteUsuarioImovel.getNome());

				form.setIdImovelAssociacaoCliente(form.getIdImovel());

				Collection colecaoEnderecos = fachada.pesquisarEnderecosClienteAbreviado(clienteUsuarioImovel.getId());

				if(colecaoEnderecos != null && !colecaoEnderecos.isEmpty()){

					Iterator endCorrespondencia = colecaoEnderecos.iterator();
					ClienteEndereco endereco = null;

					while(endCorrespondencia.hasNext()){

						endereco = (ClienteEndereco) endCorrespondencia.next();

						if(endereco.getIndicadorEnderecoCorrespondencia() != null
										&& endereco.getIndicadorEnderecoCorrespondencia().equals(
														ConstantesSistema.INDICADOR_ENDERECO_CORRESPONDENCIA)){

							form.setClienteEnderecoSelected(endereco.getId().toString());
							break;
						}
					}

					sessao.setAttribute("colecaoEnderecosAbaSolicitante", colecaoEnderecos);
					sessao.setAttribute("enderecoPertenceCliente", "OK");
				}

				Collection colecaoFones = fachada.pesquisarClienteFone(clienteUsuarioImovel.getId());

				if(colecaoFones != null && !colecaoFones.isEmpty()){

					Iterator fonePrincipal = colecaoFones.iterator();
					ClienteFone fone = null;

					while(fonePrincipal.hasNext()){

						fone = (ClienteFone) fonePrincipal.next();

						if(fone.getIndicadorTelefonePadrao() != null
										&& fone.getIndicadorTelefonePadrao().equals(ConstantesSistema.INDICADOR_TELEFONE_PRINCIPAL)){

							form.setClienteFoneSelected(fone.getId().toString());
							break;
						}
					}

					sessao.setAttribute("colecaoFonesAbaSolicitante", colecaoFones);
				}

				this.limparUnidadeSolicitante(sessao);
				this.limparNomeSolicitante(sessao);

				carregarDadosCliente(form, clienteUsuarioImovel);

				sessao.setAttribute("desabilitarDadosSolicitanteUnidade", "OK");
				sessao.setAttribute("desabilitarDadosSolicitanteFuncionario", "OK");
				sessao.setAttribute("desabilitarDadosSolicitanteNome", "OK");
				sessao.setAttribute("habilitarAlteracaoEnderecoSolicitante", "NAO");

			}
		}

		if(form.getIdImovel() == null
						|| form.getIdImovel().equalsIgnoreCase("")
						&& (pesquisarCliente == null || pesquisarCliente.equalsIgnoreCase(""))
						&& (pesquisarUnidade == null || pesquisarUnidade.equalsIgnoreCase(""))
						&& (pesquisarFuncionario == null || pesquisarFuncionario.equalsIgnoreCase(""))
						&& (informadoNomeSolicitante == null || informadoNomeSolicitante.equalsIgnoreCase(""))
						&& (limparCliente == null || limparCliente.equalsIgnoreCase(""))
						&& (limparUnidadeSolicitante == null || limparUnidadeSolicitante.equalsIgnoreCase(""))
						&& (limparNomeSolicitante == null || limparNomeSolicitante.equalsIgnoreCase(""))
						&& (removerFone == null || removerFone.equalsIgnoreCase(""))
						&& (removerEndereco == null || removerEndereco.equalsIgnoreCase(""))
						&& (retornoEndereco == null || retornoEndereco.equalsIgnoreCase(""))
						&& (retornoFone == null || retornoFone.equalsIgnoreCase(""))
						&& (adicionarEndereco == null || adicionarEndereco.equalsIgnoreCase(""))
						&& (adicionarFone == null || adicionarFone.equalsIgnoreCase(""))
						&& sessao.getAttribute("colecaoEnderecos") != null
						&& (sessao.getAttribute("colecaoEnderecosAbaSolicitante") == null || (sessao
										.getAttribute("colecaoEnderecosAbaSolicitante") != null && ((Collection) sessao
										.getAttribute("colecaoEnderecosAbaSolicitante")).isEmpty()))){

			Collection colecaoEnderecos = (Collection) sessao.getAttribute("colecaoEnderecos");

			Imovel imovel = (Imovel) Util.retonarObjetoDeColecao(colecaoEnderecos);

			ClienteEndereco clienteEndereco = new ClienteEndereco();

			clienteEndereco.setComplemento(imovel.getComplementoEndereco());
			clienteEndereco.setEnderecoReferencia(imovel.getEnderecoReferencia());
			clienteEndereco.setLogradouroBairro(imovel.getLogradouroBairro());
			clienteEndereco.setLogradouroCep(imovel.getLogradouroCep());
			clienteEndereco.setNumero(imovel.getNumeroImovel());
			clienteEndereco.setUltimaAlteracao(new Date());

			Collection colecaoEnderecosAbaSolicitante = new ArrayList();
			colecaoEnderecosAbaSolicitante.add(clienteEndereco);

			sessao.setAttribute("colecaoEnderecosAbaSolicitante", colecaoEnderecosAbaSolicitante);

		}

		SistemaParametro param = Fachada.getInstancia().pesquisarParametrosDoSistema();
		String parametroEmpresa = param.getParmId().toString();

		sessao.setAttribute("parametroEmpresa", parametroEmpresa);

		// Prepara a p�gina para Pessoa F�sica
		FiltroOrgaoExpedidorRg filtroOrgaoExpedidor = new FiltroOrgaoExpedidorRg(FiltroOrgaoExpedidorRg.DESCRICAO_ABREVIADA);
		filtroOrgaoExpedidor.adicionarParametro(new ParametroSimples(FiltroOrgaoExpedidorRg.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		// Faz a pesquisa das cole��es
		Collection orgaosExpedidores = fachada.pesquisar(filtroOrgaoExpedidor, OrgaoExpedidorRg.class.getName());

		// Seta no request as cole��es
		httpServletRequest.setAttribute("orgaosExpedidores", orgaosExpedidores);
		FiltroUnidadeFederacao filtroUnidadeFederacao = new FiltroUnidadeFederacao(FiltroUnidadeFederacao.SIGLA);
		Collection unidadesFederacao = fachada.pesquisar(filtroUnidadeFederacao, UnidadeFederacao.class.getName());

		httpServletRequest.setAttribute("unidadesFederacao", unidadesFederacao);

		// se veio das outras abas
		validarLocalidadeMuniciopio(form, fachada, httpServletRequest);

		if(!Util.isVazioOuBranco(pesquisarCliente)){

			Integer idEspecificacao = null;
			Integer idImovel = null;
			Integer idCliente = null;

			if(!Util.isVazioOuBranco(form.getEspecificacao())){
				idEspecificacao = Integer.valueOf(form.getEspecificacao());
			}

			if(!Util.isVazioOuBranco(form.getIdImovel())){
				idImovel = Integer.valueOf(form.getIdImovel());
			}

			if(!Util.isVazioOuBranco(form.getIdCliente())){
				idCliente = Integer.valueOf(form.getIdCliente());
			}

			Fachada.getInstancia().verificarDebitosImovelCliente(idEspecificacao, idImovel, idCliente);
		}

		carregarDadosTipoEspecificacao(form);

		return retorno;
	}

	private void carregarDadosCliente(InserirRegistroAtendimentoActionForm form, Cliente cliente){

		FiltroCliente filtroCliente = new FiltroCliente();
		filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, cliente.getId()));
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.ORGAO_EXPEDIDOR_RG);
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.TIPOCLIENTE);
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.UNIDADE_FEDERACAO);
		Cliente clienteRecuperado = (Cliente) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroCliente,
						Cliente.class.getName()));

		form.setClienteTipo(clienteRecuperado.getClienteTipo().getIndicadorPessoaFisicaJuridica().toString());

		if(Integer.valueOf(form.getClienteTipo()) == ConstantesSistema.INDICADOR_PESSOA_JURIDICA.intValue()){
			form.setNumeroCnpj(clienteRecuperado.getCnpj());
		}else{
			form.setNumeroCpf(clienteRecuperado.getCpf());
			form.setNumeroRG(clienteRecuperado.getRg());
			if(clienteRecuperado.getOrgaoExpedidorRg() != null){
				form.setOrgaoExpedidorRg(clienteRecuperado.getOrgaoExpedidorRg().getId().toString());
			}
			if(clienteRecuperado.getUnidadeFederacao() != null){
				form.setUnidadeFederacaoRG(clienteRecuperado.getUnidadeFederacao().getId().toString());
			}
		}
	}

	private void carregarDadosTipoEspecificacao(InserirRegistroAtendimentoActionForm form){

		SolicitacaoTipoEspecificacao solicitacaoTipoEspecificacao = null;
		if(form.getTipoSolicitacao() != null){
			FiltroSolicitacaoTipoEspecificacao filtroSolicitacaoTipoEspecificacao = new FiltroSolicitacaoTipoEspecificacao();
			filtroSolicitacaoTipoEspecificacao.adicionarParametro(new ParametroSimples(filtroSolicitacaoTipoEspecificacao.ID, form
							.getEspecificacao()));
			solicitacaoTipoEspecificacao = (SolicitacaoTipoEspecificacao) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(
							filtroSolicitacaoTipoEspecificacao, SolicitacaoTipoEspecificacao.class.getName()));

			if(solicitacaoTipoEspecificacao != null){
				if(solicitacaoTipoEspecificacao.getIndicadorObrigatoriedadeRgRA() != null){
					form.setIndicadorRg(solicitacaoTipoEspecificacao.getIndicadorObrigatoriedadeRgRA().toString());
				}
				if(solicitacaoTipoEspecificacao.getIndicadorObrigatoriedadeCpfCnpjRA() != null){
					form.setIndicadorCpfCnpj(solicitacaoTipoEspecificacao.getIndicadorObrigatoriedadeCpfCnpjRA().toString());
				}
			}
		}

		this.carregarDadosMeioSolicitacao(form);
	}

	private void carregarDadosMeioSolicitacao(InserirRegistroAtendimentoActionForm form){

		MeioSolicitacao meioSolicitacao = null;
		if(form.getMeioSolicitacao() != null){
			// Permite o n�o preenchimento do campo de doc de identifica��o da aba solicitante caso
			// o indicador de libera��o para preenchimento do meio de solicita��o esteja ativado,
			// independente dos indicadores de permiss�o do tipo de especifica��o
			if(Fachada.getInstancia().isMeioSolicitacaoLiberaDocumentoIdentificacaoRA(Integer.valueOf(form.getMeioSolicitacao()))){

				if(form.getIndicadorRg() != null){
					form.setIndicadorRg(ConstantesSistema.NAO.toString());
				}

				if(form.getIndicadorCpfCnpj() != null){
					form.setIndicadorCpfCnpj(ConstantesSistema.NAO.toString());
				}

			}
		}
	}

	/**
	 * @author isilva
	 * @param inserirRegistroAtendimentoActionForm
	 * @param fachada
	 * @param httpServletRequest
	 */
	@SuppressWarnings("unchecked")
	private void validarLocalidadeMuniciopio(InserirRegistroAtendimentoActionForm inserirRegistroAtendimentoActionForm, Fachada fachada,
					HttpServletRequest httpServletRequest){

		if(this.getParametroCompanhia(httpServletRequest).toString().equals(SistemaParametro.INDICADOR_EMPRESA_DESO.toString())){
			if(!Util.isVazioOuBranco(inserirRegistroAtendimentoActionForm.getIdLocalidade())
							&& !Util.isVazioOuBranco(inserirRegistroAtendimentoActionForm.getIdMunicipio())){

				Integer idMunicipio = Integer.valueOf(inserirRegistroAtendimentoActionForm.getIdMunicipio());
				Integer idLocalidade = Integer.valueOf(inserirRegistroAtendimentoActionForm.getIdLocalidade());

				FiltroMunicipio filtroMunicipio = new FiltroMunicipio();
				filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.ID, idMunicipio));
				filtroMunicipio.adicionarParametro(new ParametroSimples(FiltroMunicipio.INDICADOR_USO,
								ConstantesSistema.INDICADOR_USO_ATIVO));
				Collection<Municipio> colecaoMunicipio = (ArrayList<Municipio>) fachada.pesquisar(filtroMunicipio, Municipio.class
								.getName());

				if(colecaoMunicipio == null || colecaoMunicipio.isEmpty()){
					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Munic�pio");
				}

				FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				Collection<Localidade> colecaoLocalidade = (ArrayList<Localidade>) fachada.pesquisar(filtroLocalidade, Localidade.class
								.getName());

				if(colecaoLocalidade == null || colecaoLocalidade.isEmpty()){
					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Localidade");
				}

				if(!fachada.existeVinculoLocalidadeMunicipio(Integer.valueOf(idLocalidade), Integer.valueOf(idMunicipio))){
					throw new ActionServletException("atencao.localidade.nao.esta.municipio.informado");
				}
			}
		}
	}

	private void limparCliente(HttpSession sessao){

		if(sessao.getAttribute("enderecoPertenceCliente") != null){
			sessao.removeAttribute("colecaoEnderecosAbaSolicitante");
			sessao.removeAttribute("colecaoFonesAbaSolicitante");
		}

		sessao.removeAttribute("enderecoPertenceCliente");
		sessao.removeAttribute("desabilitarDadosSolicitanteUnidade");
		sessao.removeAttribute("desabilitarDadosSolicitanteFuncionario");
		sessao.removeAttribute("desabilitarDadosSolicitanteNome");
		sessao.removeAttribute("habilitarAlteracaoEnderecoSolicitante");

	}

	private void limparUnidadeSolicitante(HttpSession sessao){

		sessao.removeAttribute("desabilitarDadosSolicitanteCliente");
		sessao.removeAttribute("desabilitarDadosSolicitanteNome");
		sessao.removeAttribute("habilitarAlteracaoEnderecoSolicitante");
		sessao.removeAttribute("habilitarAlteracaoEnderecoAbaSolicitante");

	}

	private void limparNomeSolicitante(HttpSession sessao){

		sessao.removeAttribute("desabilitarDadosSolicitanteCliente");
		sessao.removeAttribute("desabilitarDadosSolicitanteUnidade");
		sessao.removeAttribute("desabilitarDadosSolicitanteFuncionario");
		sessao.removeAttribute("habilitarAlteracaoEnderecoSolicitante");

	}

	private void selecionarFoneComIndicadorPadrao(String objetoFoneIndicadorPadrao, HttpSession sessao){

		if(objetoFoneIndicadorPadrao != null && !objetoFoneIndicadorPadrao.equalsIgnoreCase("")){

			long objetoPadrao = (Long.valueOf(objetoFoneIndicadorPadrao)).longValue();

			if(sessao.getAttribute("colecaoFonesAbaSolicitante") != null){

				Collection colecaoFones = (Collection) sessao.getAttribute("colecaoFonesAbaSolicitante");
				Iterator iteratorColecaoFones = colecaoFones.iterator();
				ClienteFone clienteFone = null;

				while(iteratorColecaoFones.hasNext()){
					clienteFone = (ClienteFone) iteratorColecaoFones.next();

					if(obterTimestampIdObjeto(clienteFone) == objetoPadrao){
						clienteFone.setIndicadorTelefonePadrao(new Short("1"));
					}else{
						clienteFone.setIndicadorTelefonePadrao(null);
					}
				}
			}
		}
	}

}
