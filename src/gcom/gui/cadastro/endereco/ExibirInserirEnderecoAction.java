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
 * Este programa é software livre; você pode redistribulogradouroDescricaoí-lo e/ou
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

package gcom.gui.cadastro.endereco;

import gcom.arrecadacao.banco.Agencia;
import gcom.cadastro.cliente.ClienteEndereco;
import gcom.cadastro.cliente.ClienteTipo;
import gcom.cadastro.endereco.*;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.unidadeoperacional.UnidadeOperacional;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.Bacia;
import gcom.operacional.DistritoOperacional;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * NOVO ENDEREÇO
 * Esta classe tem por finalidade exibir para o usuário a tela que receberá os parâmetros para
 * informar
 * um endereço
 * 
 * @author Raphael Rossiter
 * @date 08/05/2006
 */
public class ExibirInserirEnderecoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirInserirEndereco");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		InserirEnderecoActionForm inserirEnderecoActionForm = (InserirEnderecoActionForm) actionForm;

		String limparLogradouro = httpServletRequest.getParameter("limparLogradouro");
		String tipoRetorno = (String) httpServletRequest.getParameter("tipoPesquisaEndereco");
		String exibirMatriculaImovel = (String) httpServletRequest.getParameter("exibirMatriculaImovel");
		String tipoOperacao = (String) httpServletRequest.getParameter("operacao");
		String caminhoRetornoTelaAdicionarSolicitante = (String) httpServletRequest.getParameter("caminhoRetornoTelaAdicionarSolicitante");

		if(exibirMatriculaImovel != null && !exibirMatriculaImovel.equalsIgnoreCase("")){
			sessao.setAttribute("exibirMatriculaImovel", exibirMatriculaImovel);
		}

		if(tipoRetorno != null && !tipoRetorno.trim().equalsIgnoreCase("")){
			sessao.setAttribute("tipoPesquisaRetorno", tipoRetorno);
		}

		if(caminhoRetornoTelaAdicionarSolicitante != null && !caminhoRetornoTelaAdicionarSolicitante.trim().equalsIgnoreCase("")){
			sessao.setAttribute("caminhoRetornoTelaAdicionarSolicitante", caminhoRetornoTelaAdicionarSolicitante);
		}

		if(tipoOperacao != null && !tipoOperacao.trim().equalsIgnoreCase("")){
			sessao.setAttribute("operacao", tipoOperacao);

			try{
				String enderecoReferenciaInicial = String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO);

				if(ParametroCadastro.P_INDICADOR_REFERENCIA_ENDERECO_OBRIGATORIO.executar().equals(ConstantesSistema.SIM.toString())){
					enderecoReferenciaInicial = String.valueOf(EnderecoReferencia.NUMERO);

					sessao.setAttribute("indicadorReferenciaObrigatoria", true);
				}else{
					enderecoReferenciaInicial = String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO);

					sessao.removeAttribute("indicadorReferenciaObrigatoria");
				}

				inserirEnderecoActionForm.setEnderecoReferencia(enderecoReferenciaInicial);
			}catch(ControladorException e){
				throw new ActionServletException(e.getMessage(), e);
			}

			// Limpar o formulário
			inserirEnderecoActionForm.setBairro(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
			inserirEnderecoActionForm.setCep("");
			inserirEnderecoActionForm.setCepUnico("");
			inserirEnderecoActionForm.setComplemento("");
			inserirEnderecoActionForm.setLogradouro("");
			inserirEnderecoActionForm.setLogradouroDescricao("");
			inserirEnderecoActionForm.setNumero("");
			inserirEnderecoActionForm.setTipo(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));
			inserirEnderecoActionForm.setAssociacaoExistente("");
			inserirEnderecoActionForm.setTipoAcao("");
			inserirEnderecoActionForm.setIdMatriculaImovel("");
			inserirEnderecoActionForm.setImovelDescricao("");
			sessao.removeAttribute("colecaoCepSelecionadosUsuario");
			sessao.removeAttribute("objetoCep");

			httpServletRequest.setAttribute("nomeCampo", "logradouro");

		}

		/*
		 * Trecho de código responsável pela exibição de um endereço já cadastrado ou já selecionado
		 * mas ainda
		 * não cadastrado pelo usuário
		 */

		String exibirEndereco = httpServletRequest.getParameter("exibirEndereco");
		if(exibirEndereco != null && !exibirEndereco.equals("")){

			inserirEnderecoActionForm.setTipoAcao("atualizacao");

			Collection enderecos = null;

			if(tipoRetorno.trim().equalsIgnoreCase("cliente")){

				enderecos = (Collection) sessao.getAttribute("colecaoEnderecos");

				String objetoClienteEnderecoSelecionado = httpServletRequest.getParameter("objetoSelecionado");

				String enderecoCorrespondencia = httpServletRequest.getParameter("enderecoCorrespondencia");

				inserirEnderecoActionForm.setObjetoClienteEnderecoSelecionado(objetoClienteEnderecoSelecionado);
				inserirEnderecoActionForm.setEnderecoCorrespondencia(enderecoCorrespondencia);

				Iterator iteratorEnderecos = enderecos.iterator();
				ClienteEndereco clienteEndereco = null;

				while(iteratorEnderecos.hasNext()){
					clienteEndereco = (ClienteEndereco) iteratorEnderecos.next();

					if(obterTimestampIdObjeto(clienteEndereco) == Long.parseLong(inserirEnderecoActionForm
									.getObjetoClienteEnderecoSelecionado())){

						carregarValidarEndereco("OK", "OK", fachada, sessao, httpServletRequest, clienteEndereco.getLogradouroCep()
										.getCep().getCodigo().toString(),
										clienteEndereco.getLogradouroCep().getCep().getCepId().toString(), clienteEndereco
														.getLogradouroCep().getLogradouro().getId().toString(), inserirEnderecoActionForm,
										null, actionMapping, null);

						if(clienteEndereco.getEnderecoTipo() != null){
							inserirEnderecoActionForm.setTipo(clienteEndereco.getEnderecoTipo().getId().toString());
						}

						// Alterado por Leonardo Vieira 11/08/2007
						// Devido a não existência da relação logradourobairro para determinados
						// imóveis
						// Base de dados COMPESA
						if(clienteEndereco.getLogradouroBairro() != null){
							inserirEnderecoActionForm.setBairro(clienteEndereco.getLogradouroBairro().getBairro().getId().toString());
						}

						if(clienteEndereco.getEnderecoReferencia() != null){
							inserirEnderecoActionForm.setEnderecoReferencia(clienteEndereco.getEnderecoReferencia().getId().toString());
						}

						inserirEnderecoActionForm.setNumero(clienteEndereco.getNumero());

						if(clienteEndereco.getComplemento() != null){
							inserirEnderecoActionForm.setComplemento(clienteEndereco.getComplemento());
						}
					}
				}

				if(enderecos.isEmpty() && sessao.getAttribute("tipoPessoa") != null){
					Short tipoPessoa = (Short) sessao.getAttribute("tipoPessoa");

					if(tipoPessoa.equals(ClienteTipo.INDICADOR_PESSOA_JURIDICA)){
						inserirEnderecoActionForm.setTipo(EnderecoTipo.ENDERECO_TIPO_COMERCIAL + "");
					}else if(tipoPessoa.equals(ClienteTipo.INDICADOR_PESSOA_FISICA)){
						inserirEnderecoActionForm.setTipo(EnderecoTipo.ENDERECO_TIPO_RESIDENCIAL + "");
					}
				}

				httpServletRequest.setAttribute("nomeCampo", "tipo");

			}else if(tipoRetorno.trim().equalsIgnoreCase("imovel")){

				enderecos = (Collection) sessao.getAttribute("colecaoEnderecos");

				Imovel imovelEndereco = ((Imovel) enderecos.iterator().next());

				carregarValidarEndereco("OK", "OK", fachada, sessao, httpServletRequest, imovelEndereco.getLogradouroCep().getCep()
								.getCodigo().toString(), null, imovelEndereco.getLogradouroCep().getLogradouro().getId().toString(),
								inserirEnderecoActionForm, null, actionMapping, null);

				if(imovelEndereco.getLogradouroBairro() != null && imovelEndereco.getLogradouroBairro().getBairro() != null) inserirEnderecoActionForm
								.setBairro(imovelEndereco.getLogradouroBairro().getBairro().getId().toString());

				if(imovelEndereco.getEnderecoReferencia() != null){
					inserirEnderecoActionForm.setEnderecoReferencia(imovelEndereco.getEnderecoReferencia().getId().toString());
				}

				inserirEnderecoActionForm.setNumero(imovelEndereco.getNumeroImovel());

				if(imovelEndereco.getComplementoEndereco() != null){
					inserirEnderecoActionForm.setComplemento(imovelEndereco.getComplementoEndereco());
				}

				httpServletRequest.setAttribute("nomeCampo", "cep");

			}else if(tipoRetorno.trim().equalsIgnoreCase("unidadeOperacional")){

				enderecos = (Collection) sessao.getAttribute("colecaoEnderecos");

				UnidadeOperacional unidadeOperacaionalEndereco = ((UnidadeOperacional) enderecos.iterator().next());

				carregarValidarEndereco("OK", "OK", fachada, sessao, httpServletRequest, unidadeOperacaionalEndereco.getCep().getCodigo()
								.toString(), null, unidadeOperacaionalEndereco.getLogradouro().getId().toString(),
								inserirEnderecoActionForm, null, actionMapping, null);

				inserirEnderecoActionForm.setBairro(unidadeOperacaionalEndereco.getBairro().getId().toString());

				if(unidadeOperacaionalEndereco.getEnderecoReferencia() != null){
					inserirEnderecoActionForm.setEnderecoReferencia(unidadeOperacaionalEndereco.getEnderecoReferencia().getId().toString());
				}

				inserirEnderecoActionForm.setNumero(unidadeOperacaionalEndereco.getNumeroImovel().toString());

				if(unidadeOperacaionalEndereco.getComplementoEndereco() != null){
					inserirEnderecoActionForm.setComplemento(unidadeOperacaionalEndereco.getComplementoEndereco());
				}

				httpServletRequest.setAttribute("nomeCampo", "cep");

			}else if(tipoRetorno.trim().equalsIgnoreCase("distritoOperacional")){

				enderecos = (Collection) sessao.getAttribute("colecaoEnderecos");

				DistritoOperacional distritoOperacaionalEndereco = ((DistritoOperacional) enderecos.iterator().next());

				carregarValidarEndereco("OK", "OK", fachada, sessao, httpServletRequest, distritoOperacaionalEndereco.getCep().getCep()
								.getCodigo().toString(), null, distritoOperacaionalEndereco.getBairro().getLogradouro().getId().toString(),
								inserirEnderecoActionForm, null, actionMapping, null);

				inserirEnderecoActionForm.setBairro(distritoOperacaionalEndereco.getBairro().getBairro().getId().toString());

				if(distritoOperacaionalEndereco.getEnderecoReferencia() != null){
					inserirEnderecoActionForm
									.setEnderecoReferencia(distritoOperacaionalEndereco.getEnderecoReferencia().getId().toString());
				}

				inserirEnderecoActionForm.setNumero(distritoOperacaionalEndereco.getNumeroImovel().toString());

				if(distritoOperacaionalEndereco.getComplementoEndereco() != null){
					inserirEnderecoActionForm.setComplemento(distritoOperacaionalEndereco.getComplementoEndereco());
				}

				httpServletRequest.setAttribute("nomeCampo", "cep");

			}else if(tipoRetorno.trim().equalsIgnoreCase("localidade")){

				enderecos = (Collection) sessao.getAttribute("colecaoEnderecos");

				Localidade localidadeEndereco = ((Localidade) enderecos.iterator().next());

				carregarValidarEndereco("OK", "OK", fachada, sessao, httpServletRequest, localidadeEndereco.getLogradouroCep().getCep()
								.getCodigo().toString(), null, localidadeEndereco.getLogradouroCep().getLogradouro().getId().toString(),
								inserirEnderecoActionForm, null, actionMapping, null);

				inserirEnderecoActionForm.setBairro(localidadeEndereco.getLogradouroBairro().getBairro().getId().toString());

				if(localidadeEndereco.getEnderecoReferencia() != null){
					inserirEnderecoActionForm.setEnderecoReferencia(localidadeEndereco.getEnderecoReferencia().getId().toString());
				}

				inserirEnderecoActionForm.setNumero(localidadeEndereco.getNumeroImovel());

				if(localidadeEndereco.getComplementoEndereco() != null){
					inserirEnderecoActionForm.setComplemento(localidadeEndereco.getComplementoEndereco());
				}

				httpServletRequest.setAttribute("nomeCampo", "cep");

			}else if(tipoRetorno.trim().equalsIgnoreCase("registroAtendimento")){

				// Aba Nº 02 - Endereço do local da ocorrência (Objeto Imóvel)
				if(tipoOperacao.trim().equalsIgnoreCase("1")){
					enderecos = (Collection) sessao.getAttribute("colecaoEnderecos");

					Imovel imovelEndereco = ((Imovel) enderecos.iterator().next());

					carregarValidarEndereco("OK", "OK", fachada, sessao, httpServletRequest, imovelEndereco.getLogradouroCep().getCep()
									.getCodigo().toString(), null, imovelEndereco.getLogradouroCep().getLogradouro().getId().toString(),
									inserirEnderecoActionForm, null, actionMapping, null);

					inserirEnderecoActionForm.setBairro(imovelEndereco.getLogradouroBairro().getBairro().getId().toString());

					if(imovelEndereco.getEnderecoReferencia() != null){
						inserirEnderecoActionForm.setEnderecoReferencia(imovelEndereco.getEnderecoReferencia().getId().toString());
					}

					inserirEnderecoActionForm.setNumero(imovelEndereco.getNumeroImovel());

					if(imovelEndereco.getComplementoEndereco() != null){
						inserirEnderecoActionForm.setComplemento(imovelEndereco.getComplementoEndereco());
					}

				}
				// Aba Nº 03 - Endereço do Solicitante (Objeto ClienteEndereco)
				else if(tipoOperacao.trim().equalsIgnoreCase("2")){
					enderecos = (Collection) sessao.getAttribute("colecaoEnderecosAbaSolicitante");

					ClienteEndereco clienteEndereco = ((ClienteEndereco) enderecos.iterator().next());

					carregarValidarEndereco("OK", "OK", fachada, sessao, httpServletRequest, clienteEndereco.getLogradouroCep().getCep()
									.getCodigo().toString(), null, clienteEndereco.getLogradouroCep().getLogradouro().getId().toString(),
									inserirEnderecoActionForm, null, actionMapping, null);

					inserirEnderecoActionForm.setBairro(clienteEndereco.getLogradouroBairro().getBairro().getId().toString());

					if(clienteEndereco.getEnderecoReferencia() != null){
						inserirEnderecoActionForm.setEnderecoReferencia(clienteEndereco.getEnderecoReferencia().getId().toString());
					}

					inserirEnderecoActionForm.setNumero(clienteEndereco.getNumero());

					if(clienteEndereco.getComplemento() != null){
						inserirEnderecoActionForm.setComplemento(clienteEndereco.getComplemento());
					}
				}
				// Aba Nº 02 - Endereço do local da ocorrência (Objeto Imóvel) - Atualizar
				if(tipoOperacao.trim().equalsIgnoreCase("3")){
					enderecos = (Collection) sessao.getAttribute("colecaoEnderecos");

					Imovel imovelEndereco = ((Imovel) enderecos.iterator().next());

					carregarValidarEndereco("OK", "OK", fachada, sessao, httpServletRequest, imovelEndereco.getLogradouroCep().getCep()
									.getCodigo().toString(), null, imovelEndereco.getLogradouroCep().getLogradouro().getId().toString(),
									inserirEnderecoActionForm, null, actionMapping, null);

					inserirEnderecoActionForm.setBairro(imovelEndereco.getLogradouroBairro().getBairro().getId().toString());

					if(imovelEndereco.getEnderecoReferencia() != null){
						inserirEnderecoActionForm.setEnderecoReferencia(imovelEndereco.getEnderecoReferencia().getId().toString());
					}

					inserirEnderecoActionForm.setNumero(imovelEndereco.getNumeroImovel());

					if(imovelEndereco.getComplementoEndereco() != null){
						inserirEnderecoActionForm.setComplemento(imovelEndereco.getComplementoEndereco());
					}

				}
				// Aba Nº 03 - Endereço do Solicitante (Objeto ClienteEndereco) - Atualizar
				else if(tipoOperacao.trim().equalsIgnoreCase("4")){
					enderecos = (Collection) sessao.getAttribute("colecaoEnderecosSolicitante");

					ClienteEndereco clienteEndereco = ((ClienteEndereco) enderecos.iterator().next());

					carregarValidarEndereco("OK", "OK", fachada, sessao, httpServletRequest, clienteEndereco.getLogradouroCep().getCep()
									.getCodigo().toString(), null, clienteEndereco.getLogradouroCep().getLogradouro().getId().toString(),
									inserirEnderecoActionForm, null, actionMapping, null);

					inserirEnderecoActionForm.setBairro(clienteEndereco.getLogradouroBairro().getBairro().getId().toString());

					if(clienteEndereco.getEnderecoReferencia() != null){
						inserirEnderecoActionForm.setEnderecoReferencia(clienteEndereco.getEnderecoReferencia().getId().toString());
					}

					inserirEnderecoActionForm.setNumero(clienteEndereco.getNumero());

					if(clienteEndereco.getComplemento() != null){
						inserirEnderecoActionForm.setComplemento(clienteEndereco.getComplemento());
					}
				}

				httpServletRequest.setAttribute("nomeCampo", "cep");
			}else if(tipoRetorno.trim().equalsIgnoreCase("gerenciaRegional")){

				enderecos = (Collection) sessao.getAttribute("colecaoEnderecos");

				GerenciaRegional gerenciaRegionalEndereco = ((GerenciaRegional) enderecos.iterator().next());

				carregarValidarEndereco("OK", "OK", fachada, sessao, httpServletRequest, gerenciaRegionalEndereco.getLogradouroCep()
								.getCep().getCodigo().toString(), null, gerenciaRegionalEndereco.getLogradouroCep().getLogradouro().getId()
								.toString(), inserirEnderecoActionForm, null, actionMapping, gerenciaRegionalEndereco.getNumeroImovel());

				inserirEnderecoActionForm.setBairro(gerenciaRegionalEndereco.getLogradouroBairro().getBairro().getId().toString());

				if(gerenciaRegionalEndereco.getEnderecoReferencia() != null){
					inserirEnderecoActionForm.setEnderecoReferencia(gerenciaRegionalEndereco.getEnderecoReferencia().getId().toString());
				}

				inserirEnderecoActionForm.setNumero(gerenciaRegionalEndereco.getNumeroImovel());

				if(gerenciaRegionalEndereco.getComplementoEndereco() != null){
					inserirEnderecoActionForm.setComplemento(gerenciaRegionalEndereco.getComplementoEndereco());
				}

				httpServletRequest.setAttribute("nomeCampo", "cep");

			}else if(tipoRetorno.trim().equalsIgnoreCase("agencia")){

				enderecos = (Collection) sessao.getAttribute("colecaoEnderecos");

				Agencia agenciaEndereco = ((Agencia) enderecos.iterator().next());

				carregarValidarEndereco("OK", "OK", fachada, sessao, httpServletRequest, agenciaEndereco.getLogradouroCep().getCep()
								.getCodigo().toString(), null, agenciaEndereco.getLogradouroCep().getLogradouro().getId().toString(),
								inserirEnderecoActionForm, null, actionMapping, null);

				inserirEnderecoActionForm.setBairro(agenciaEndereco.getLogradouroBairro().getBairro().getId().toString());

				if(agenciaEndereco.getEnderecoReferencia() != null){
					inserirEnderecoActionForm.setEnderecoReferencia(agenciaEndereco.getEnderecoReferencia().getId().toString());
				}

				inserirEnderecoActionForm.setNumero(agenciaEndereco.getNumeroImovel());

				if(agenciaEndereco.getComplementoEndereco() != null){
					inserirEnderecoActionForm.setComplemento(agenciaEndereco.getComplementoEndereco());
				}

				httpServletRequest.setAttribute("nomeCampo", "cep");

			}else if(tipoRetorno.trim().equalsIgnoreCase("bacia")){
				enderecos = (Collection<Bacia>) sessao.getAttribute("colecaoEnderecos");

				Bacia bacia = ((Bacia) enderecos.iterator().next());

				LogradouroCep logradouroCep = bacia.getLogradouroCep();
				Cep cep = logradouroCep.getCep();
				Integer codigoCep = cep.getCodigo();
				String codigoCepStr = codigoCep.toString();

				Logradouro logradouro = logradouroCep.getLogradouro();
				Integer idLogradouro = logradouro.getId();
				String idLogradouroStr = idLogradouro.toString();

				carregarValidarEndereco("OK", "OK", fachada, sessao, httpServletRequest, codigoCepStr, null, idLogradouroStr,
								inserirEnderecoActionForm, null, actionMapping, null);

				LogradouroBairro logradouroBairro = bacia.getLogradouroBairro();
				Bairro bairro = logradouroBairro.getBairro();
				Integer idBairro = bairro.getId();
				String idBairroStr = idBairro.toString();
				inserirEnderecoActionForm.setBairro(idBairroStr);

				EnderecoReferencia enderecoReferencia = bacia.getEnderecoReferencia();
				if(enderecoReferencia != null){
					Integer idEnderecoReferencia = enderecoReferencia.getId();
					String idEnderecoReferenciaStr = idEnderecoReferencia.toString();
					inserirEnderecoActionForm.setEnderecoReferencia(idEnderecoReferenciaStr);
				}

				String numeroImovel = bacia.getNumeroImovel();
				inserirEnderecoActionForm.setNumero(numeroImovel);

				String complementoEndereco = bacia.getComplementoEndereco();
				if(complementoEndereco != null){
					inserirEnderecoActionForm.setComplemento(complementoEndereco);
				}

				httpServletRequest.setAttribute("nomeCampo", "cep");
			}

		}else{

			String cepJSP = inserirEnderecoActionForm.getCep();
			String pesquisarCep = httpServletRequest.getParameter("pesquisarCep");
			String pesquisarLogradouro = httpServletRequest.getParameter("pesquisarLogradouro");
			String logradouroJSP = inserirEnderecoActionForm.getLogradouro();
			String numeroJSP = inserirEnderecoActionForm.getNumero();
			String pesquisarImovel = httpServletRequest.getParameter("pesquisarImovel");

			/*
			 * Caso o usuário confirme
			 */
			if(pesquisarImovel != null
							|| (httpServletRequest.getParameter("idCampoEnviarDados") != null && httpServletRequest
											.getParameter("tipoConsulta").toString().equals("imovel"))){

				String idMatricula = "";
				if(pesquisarImovel != null && pesquisarImovel.equals("OK")){
					idMatricula = inserirEnderecoActionForm.getIdMatriculaImovel();
				}else{

					 idMatricula = (String) httpServletRequest.getParameter("idCampoEnviarDados");
				}

				Imovel imovel = fachada.pesquisarImovel(Util.obterInteger(idMatricula));
				inserirEnderecoActionForm.setIdMatriculaImovel(idMatricula);
				inserirEnderecoActionForm.setNumero(imovel.getNumeroImovel());
				inserirEnderecoActionForm.setCep(imovel.getLogradouroCep().getCep().getCodigo().toString());

				inserirEnderecoActionForm.setImovelDescricao(imovel.getInscricaoFormatada());
				pesquisarCep = "OK";
				pesquisarLogradouro = "OK";
				cepJSP = (String) imovel.getLogradouroCep().getCep().getCodigo().toString();
			}

			if(httpServletRequest.getParameter("confirmado") != null){
				sessao.removeAttribute("objetoCep");
				sessao.removeAttribute("colecaoCepSelecionadosUsuario");
				pesquisarLogradouro = "OK";
			}

			Cep chamarConfirmacao = carregarValidarEndereco(pesquisarCep, pesquisarLogradouro, fachada, sessao, httpServletRequest, cepJSP,
							null, logradouroJSP, inserirEnderecoActionForm, limparLogradouro, actionMapping, numeroJSP);


			if(chamarConfirmacao != null){

				// Chamar tela de confirmação
				String[] parametrosMensagem = new String[1];
				parametrosMensagem[0] = chamarConfirmacao.getCepFormatado();

				httpServletRequest.setAttribute("caminhoActionConclusao", "/gsan/exibirInserirEnderecoAction.do");

				return montarPaginaConfirmacao("atencao.endereco_municipio_incompativel", httpServletRequest, actionMapping,
								parametrosMensagem);
			}

			if(tipoRetorno != null && tipoRetorno.trim().equalsIgnoreCase("cliente") && sessao.getAttribute("tipoPessoa") != null){
				Short tipoPessoa = (Short) sessao.getAttribute("tipoPessoa");

				if(tipoPessoa.equals(ClienteTipo.INDICADOR_PESSOA_JURIDICA)){
					inserirEnderecoActionForm.setTipo(EnderecoTipo.ENDERECO_TIPO_COMERCIAL + "");
				}else if(tipoPessoa.equals(ClienteTipo.INDICADOR_PESSOA_FISICA)){
					inserirEnderecoActionForm.setTipo(EnderecoTipo.ENDERECO_TIPO_RESIDENCIAL + "");
				}
			}



		}

		httpServletRequest.setAttribute("flagReload", "false");

		return retorno;
	}

	public Cep carregarValidarEndereco(String pesquisarCep, String pesquisarLogradouro, Fachada fachada, HttpSession sessao,
					HttpServletRequest httpServletRequest, String cepJSP, String cepID, String logradouroJSP,
					InserirEnderecoActionForm inserirEnderecoActionForm, String limparLogradouro, ActionMapping actionMapping,
					String numeroJsp){

		Cep retorno = null;

		if(sessao.getAttribute("colecaoEnderecoReferencia") == null){

			Collection colecaoTipo = null;
			Collection colecaoEnderecoReferencia = null;
			FiltroEnderecoTipo filtroEnderecoTipo = null;
			FiltroEnderecoReferencia filtroEnderecoReferencia = null;

			filtroEnderecoTipo = new FiltroEnderecoTipo(FiltroEnderecoTipo.DESCRICAO);
			filtroEnderecoTipo.adicionarParametro(new ParametroSimples(

			FiltroEnderecoTipo.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroEnderecoReferencia = new FiltroEnderecoReferencia(FiltroEnderecoReferencia.DESCRICAO);

			filtroEnderecoReferencia.adicionarParametro(new ParametroSimples(FiltroEnderecoReferencia.INDICADORUSO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			// Retorna todos os tipos de endereço
			colecaoTipo = fachada.pesquisar(filtroEnderecoTipo, EnderecoTipo.class.getName());

			if(colecaoTipo == null || colecaoTipo.isEmpty()){
				// Nenhum tipo de endereço cadastrado
				throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "EnderecoTipo");
			}else{
				sessao.setAttribute("colecaoTipo", colecaoTipo);
			}

			// Retorna todas as referencias do endereço
			colecaoEnderecoReferencia = fachada.pesquisar(filtroEnderecoReferencia, EnderecoReferencia.class.getName());

			if(colecaoEnderecoReferencia == null || colecaoEnderecoReferencia.isEmpty()){
				// Nenhuma referencia de endereço cadastrada
				throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "EnderecoReferencia");
			}else{
				sessao.setAttribute("colecaoEnderecoReferencia", colecaoEnderecoReferencia);
			}
		}

		String removerCep = httpServletRequest.getParameter("removerCep");

		if(removerCep != null && !removerCep.equals("")){
			sessao.removeAttribute("objetoCep");
			sessao.removeAttribute("colecaoCepSelecionadosUsuario");
		}

		// CEP
		if(((pesquisarCep != null && !pesquisarCep.trim().equalsIgnoreCase("")) && (cepJSP != null && !cepJSP.equals("")))
						|| (httpServletRequest.getParameter("idCampoEnviarDados") != null
										&& httpServletRequest.getParameter("tipoConsulta") != null && httpServletRequest.getParameter(
										"tipoConsulta").equals("cep")) || httpServletRequest.getParameter("idCampoEnviarDados") != null
						&& httpServletRequest.getParameter("tipoConsulta") != null
						&& httpServletRequest.getParameter("tipoConsulta").equals("logradouro") && cepJSP != null && !cepJSP.equals("")){

			/*
			 * Recebendo os parâmetros a partir de uma consulta realizada via popup (Cep)
			 */
			if(httpServletRequest.getParameter("idCampoEnviarDados") != null && httpServletRequest.getParameter("tipoConsulta") =="cep"){
				cepJSP = httpServletRequest.getParameter("idCampoEnviarDados");
			}

			Collection<Cep> colecaoCep = null;
			FiltroCep filtroCep = new FiltroCep();

			filtroCep.adicionarCaminhoParaCarregamentoEntidade("cepTipo");

			if(cepID != null){

				filtroCep.adicionarParametro(new ParametroSimples(FiltroCep.CEPID, cepID));
			}

			filtroCep.adicionarParametro(new ParametroSimples(FiltroCep.CODIGO, cepJSP));
			filtroCep.adicionarParametro(new ParametroSimples(FiltroCep.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			colecaoCep = fachada.pesquisar(filtroCep, Cep.class.getName());

			if(colecaoCep != null && !colecaoCep.isEmpty()){

				inserirEnderecoActionForm.setCepDescricao("");

				if(colecaoCep.size() == 1){

					Cep objetoCep = (Cep) Util.retonarObjetoDeColecao(colecaoCep);

					/*
					 * Inicializando os campos relacionados ao CEP
					 */
					inserirEnderecoActionForm.setCep("");
					inserirEnderecoActionForm.setCepDescricao("");

					/*
					 * ----------------------------------------
					 * REGRAS PARA A NOVA VALIDAÇÃO DE ENDEREÇO
					 * ----------------------------------------
					 */

					/*
					 * Cenário 01
					 * O cep não é único de Município e existe associação com um Logradouro.
					 * Exibe a descrição do CEP e do Logradouro a ele associado
					 * Caso não exista associação com o Logradouro ou existe a associação, mas o CEP
					 * informado é único de Município
					 */
					if(sessao.getAttribute("colecaoCepSelecionadosUsuario") != null
									&& (inserirEnderecoActionForm.getLogradouro() != null && !inserirEnderecoActionForm.getLogradouro()
													.equals(""))){

						Logradouro logradouroJaAssociado = fachada.verificarCepAssociadoOutroLogradouro(objetoCep);

						if(objetoCep.getCepTipo().getId().equals(CepTipo.UNICO)){
							throw new ActionServletException("atencao.logradouro_cep_tipo_unico");
						}else if(objetoCep.getCepTipo().getId().equals(CepTipo.INICIAL)){

							Logradouro logradouroSelecionado = new Logradouro();
							logradouroSelecionado.setId(Integer.valueOf(inserirEnderecoActionForm.getLogradouro()));

							if(fachada.verificarLogradouroAssociadoCepTipoLogradouro(logradouroSelecionado)){
								throw new ActionServletException("atencao.logradouro_cep_tipo_inicial");
							}
						}else if(logradouroJaAssociado != null){

							throw new ActionServletException("atencao.logradouro_cep_ja_associado", String.valueOf(objetoCep.getCodigo()),
											String.valueOf(logradouroJaAssociado.getId()), logradouroJaAssociado.getDescricaoFormatada());
						}

						Collection colecaoCepSelecionadosUsuario = (Collection) sessao.getAttribute("colecaoCepSelecionadosUsuario");

						LogradouroCep logradouroCepADD = new LogradouroCep();
						logradouroCepADD.setCep(objetoCep);

						// Evitar repetição
						Iterator iteratorColecaoCepSelecionadosUsuario = colecaoCepSelecionadosUsuario.iterator();
						boolean repeticao = false;
						while(iteratorColecaoCepSelecionadosUsuario.hasNext()){

							if(((LogradouroCep) iteratorColecaoCepSelecionadosUsuario.next()).getCep().getCepId().equals(
											objetoCep.getCepId())){
								repeticao = true;
								break;
							}
						}

						if(!repeticao){
							colecaoCepSelecionadosUsuario.add(logradouroCepADD);
							inserirEnderecoActionForm.setCepSelecionado(String.valueOf(objetoCep.getCepId()));
						}

						inserirEnderecoActionForm.setAssociacaoExistente("FALSO");

						Logradouro logradouroCarregarBairros = new Logradouro();
						logradouroCarregarBairros.setId(Integer.valueOf(inserirEnderecoActionForm.getLogradouro()));

						Collection<Bairro> colecaoBairros = fachada.obterBairrosPorLogradouro(logradouroCarregarBairros);

						if(colecaoBairros != null && !colecaoBairros.isEmpty()){
							httpServletRequest.setAttribute("logradouroBairros", colecaoBairros);

							/*
							 * Caso o logradouro esteja associado apenas com um bairro, este bairro
							 * será
							 * selecionado
							 * automaticamente pelo sistema.
							 */
							if(colecaoBairros.size() == 1){
								inserirEnderecoActionForm.setBairro(((Bairro) Util.retonarObjetoDeColecao(colecaoBairros)).getId()
												.toString());
							}
						}

						

					}else if(!fachada.verificarCepUnicoMunicipio(objetoCep) && !fachada.verificarCepInicialMunicipio(objetoCep)){

						Logradouro logradouroAssociado = fachada.verificarCepAssociadoOutroLogradouro(objetoCep);

						if(logradouroAssociado != null){

							sessao.setAttribute("objetoCep", objetoCep);
							inserirEnderecoActionForm.setCepSelecionado(String.valueOf(objetoCep.getCepId()));
							// httpServletRequest.setAttribute("logradouroBloqueado", "OK");
							inserirEnderecoActionForm.setLogradouro(String.valueOf(logradouroAssociado.getId()));
							inserirEnderecoActionForm.setLogradouroDescricao(logradouroAssociado.getDescricaoFormatada());

							Collection<Bairro> colecaoBairros = fachada.obterBairrosPorLogradouro(logradouroAssociado);

							if(colecaoBairros != null && !colecaoBairros.isEmpty()){
								httpServletRequest.setAttribute("logradouroBairros", colecaoBairros);

								/*
								 * Caso o logradouro esteja associado apenas com um bairro, este
								 * bairro
								 * será selecionado
								 * automaticamente pelo sistema.
								 */
								if(colecaoBairros.size() == 1){
									inserirEnderecoActionForm.setBairro(((Bairro) Util.retonarObjetoDeColecao(colecaoBairros)).getId()
													.toString());
								}
							}

							inserirEnderecoActionForm.setCepUnico("FALSE");
							inserirEnderecoActionForm.setAssociacaoExistente("OK");
							httpServletRequest.setAttribute("nomeCampo", "bairro");
							sessao.removeAttribute("colecaoCepSelecionadosUsuario");
						}else{

							inserirEnderecoActionForm.setCepUnico("FALSE");
							sessao.setAttribute("objetoCep", objetoCep);
							inserirEnderecoActionForm.setCepSelecionado(String.valueOf(objetoCep.getCepId()));
							inserirEnderecoActionForm.setAssociacaoExistente("FALSO");
							inserirEnderecoActionForm.setLogradouro("");
							inserirEnderecoActionForm.setLogradouroDescricao("");
							httpServletRequest.setAttribute("nomeCampo", "logradouro");
							sessao.removeAttribute("colecaoCepSelecionadosUsuario");

						}
					}else{
						inserirEnderecoActionForm.setCepUnico("TRUE");
						inserirEnderecoActionForm.setCodigoCepUnico(objetoCep.getCepId().toString());
						sessao.setAttribute("objetoCep", objetoCep);
						inserirEnderecoActionForm.setCepSelecionado(String.valueOf(objetoCep.getCepId()));
						inserirEnderecoActionForm.setAssociacaoExistente("FALSO");
						inserirEnderecoActionForm.setLogradouro("");
						inserirEnderecoActionForm.setLogradouroDescricao("");
						httpServletRequest.setAttribute("nomeCampo", "logradouro");
						sessao.removeAttribute("colecaoCepSelecionadosUsuario");
					}

				}else{

					// Ordenar por um único campo
					Collections.sort((List) colecaoCep, new Comparator() {

						public int compare(Object a, Object b){

							String cep1 = ((Cep) a).getDescricaoLogradouroFormatada();
							String cep2 = ((Cep) b).getDescricaoLogradouroFormatada();
							return cep1.compareTo(cep2);
						}
					});

					Collection<LogradouroCep> colecaoCepSelecionadosUsuario = new ArrayList<LogradouroCep>();
					Iterator<Cep> iterator = colecaoCep.iterator();
					while(iterator.hasNext()){
						Cep cep = iterator.next();
						LogradouroCep logradouroCepADD = new LogradouroCep();
						logradouroCepADD.setCep(cep);
						colecaoCepSelecionadosUsuario.add(logradouroCepADD);
					}

					sessao.removeAttribute("objetoCep");
					sessao.setAttribute("colecaoCepSelecionadosUsuario", colecaoCepSelecionadosUsuario);
				}

			}else{

				// Cep nao encontrado
				httpServletRequest.setAttribute("corRetornoCep", "exception");
				inserirEnderecoActionForm.setCep("");
				inserirEnderecoActionForm.setCepDescricao("CEP INEXISTENTE");
				httpServletRequest.setAttribute("nomeCampo", "cep");
			}
		}

		// LOGRADOURO
		if(((pesquisarLogradouro != null && !pesquisarLogradouro.trim().equalsIgnoreCase("")) && (logradouroJSP != null && !logradouroJSP
						.equals("")))
						|| (httpServletRequest.getParameter("idCampoEnviarDados") != null
										&& httpServletRequest.getParameter("tipoConsulta") != null && httpServletRequest.getParameter(
										"tipoConsulta").equals("logradouro"))){

			/*
			 * Recebendo os parâmetros a partir de uma consulta realizada via popup (Logradouro)
			 */
			if(httpServletRequest.getParameter("idCampoEnviarDados") != null){
				logradouroJSP = httpServletRequest.getParameter("idCampoEnviarDados");
			}

			Collection colecaoLogradouro = null;

			FiltroLogradouro filtroLogradouro = new FiltroLogradouro();

			filtroLogradouro.adicionarCaminhoParaCarregamentoEntidade("municipio");
			filtroLogradouro.adicionarCaminhoParaCarregamentoEntidade("logradouroTipo");
			filtroLogradouro.adicionarCaminhoParaCarregamentoEntidade("logradouroTitulo");

			filtroLogradouro.adicionarParametro(new ParametroSimples(FiltroLogradouro.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			filtroLogradouro.adicionarParametro(new ParametroSimples(FiltroLogradouro.ID, logradouroJSP));

			colecaoLogradouro = fachada.pesquisar(filtroLogradouro, Logradouro.class.getName());

			if(colecaoLogradouro == null || colecaoLogradouro.isEmpty()){
				// Logradouro nao encontrado
				httpServletRequest.setAttribute("corRetorno", "exception");
				inserirEnderecoActionForm.setLogradouro("");
				inserirEnderecoActionForm.setLogradouroDescricao("Logradouro inexistente.");
				httpServletRequest.setAttribute("nomeCampo", "logradouro");

			}else{

				httpServletRequest.setAttribute("corRetorno", "valor");
				Logradouro logradouro = (Logradouro) Util.retonarObjetoDeColecao(colecaoLogradouro);

				Collection colecaoCepSelecionadosUsuario = new ArrayList();

				// Caso o usuário não tenha informado o CEP
				if(sessao.getAttribute("objetoCep") == null && sessao.getAttribute("colecaoCepSelecionadosUsuario") == null){

					/*
					 * CENÁRIO 02
					 * Caso o logradouro seja de um município que possui CEP por logradouro, ou seja
					 * existe
					 * associação com 1 ou mais CEPs, que podem ser de logradouro ou inicial de
					 * município
					 */
					if(fachada.verificarMunicipioComCepPorLogradouro(logradouro.getMunicipio())){

						/*
						 * Exibe a descrição do Logradouro e a(s) descriçõe(s) do(s) CEP(s) a ele
						 * associado(s), para a escolha pelo usuário
						 */
						colecaoCepSelecionadosUsuario = fachada.pesquisarAssociacaoLogradouroCepPorLogradouro(logradouro);

						if(colecaoCepSelecionadosUsuario != null && !colecaoCepSelecionadosUsuario.isEmpty()){

							if(numeroJsp != null && numeroJsp != ""){

								Collection cepsAssociados = new ArrayList();

								String saida = numeroJsp.replaceAll("\\D", ""); 
								Integer saidaint = Integer.parseInt(saida);

								if(saidaint != null){

									Iterator cepsIterator = colecaoCepSelecionadosUsuario.iterator();

									while(cepsIterator.hasNext()){

										LogradouroCep logradouroCep = (LogradouroCep) cepsIterator.next();

										if(logradouroCep.getCep().getNumeroFaixaIncial() != null
														&& logradouroCep.getCep().getNumeroFaixaFinal() != null){

											Integer faixaInicial=Integer.parseInt(logradouroCep.getCep().getNumeroFaixaIncial().toString());
											Integer faixaFinal=Integer.parseInt(logradouroCep.getCep().getNumeroFaixaFinal().toString());
											
											if(saidaint>=faixaInicial && saidaint<=faixaFinal){
												
												cepsAssociados.add(logradouroCep);
											}

										}
									}

									colecaoCepSelecionadosUsuario = cepsAssociados;
								}
							}

							sessao.setAttribute("colecaoCepSelecionadosUsuario", colecaoCepSelecionadosUsuario);
						}

						inserirEnderecoActionForm.setAssociacaoExistente("OK");
						httpServletRequest.setAttribute("logradouroMunicipioCepUnico", "FALSE");
					}else{

						/*
						 * CENÁRIO 03
						 * Caso o logradouro seja de um município que NÃO possui CEP por logradouro.
						 * Exibe a descrição do Logradouro e a descrição do CEP do Município (exibe
						 * a descrição do CEP do município na tebela e no campo de CEP, sem permitir
						 * alteração até que mude o logradouro).
						 */

						colecaoCepSelecionadosUsuario = new ArrayList();
						LogradouroCep logradouroCepUnicoMunicipio = new LogradouroCep();

						Cep cepUnicoMunicipio = fachada.obterCepUnicoMunicipio(logradouro.getMunicipio());

						if(cepUnicoMunicipio != null){

							logradouroCepUnicoMunicipio.setCep(cepUnicoMunicipio);
							colecaoCepSelecionadosUsuario.add(logradouroCepUnicoMunicipio);

							sessao.setAttribute("colecaoCepSelecionadosUsuario", colecaoCepSelecionadosUsuario);
							httpServletRequest.setAttribute("logradouroMunicipioCepUnico", "OK");
							inserirEnderecoActionForm.setAssociacaoExistente("OK");
						}else{
							inserirEnderecoActionForm.setAssociacaoExistente("FALSO");
						}
					}

				}else if(sessao.getAttribute("objetoCep") != null){

					Cep cepSelecionado = (Cep) sessao.getAttribute("objetoCep");

					if(cepSelecionado.getCepTipo().getId().equals(CepTipo.UNICO)
									&& fachada.verificarMunicipioComCepPorLogradouro(logradouro.getMunicipio())){
						throw new ActionServletException("atencao.logradouro_cep_tipo_unico");
					}else if(cepSelecionado.getCepTipo().getId().equals(CepTipo.UNICO)
									&& !cepSelecionado.getMunicipio().equalsIgnoreCase(logradouro.getMunicipio().getNome())){

						// Chamar tela de confirmação
						Cep cepUnicoMunicipio = fachada.obterCepUnicoMunicipio(logradouro.getMunicipio());
						return cepUnicoMunicipio;
					}else if(cepSelecionado.getCepTipo().getId().equals(CepTipo.INICIAL)){

						if(fachada.verificarLogradouroAssociadoCepTipoLogradouro(logradouro)){
							throw new ActionServletException("atencao.logradouro_cep_tipo_inicial");
						}
					}

					colecaoCepSelecionadosUsuario = fachada.pesquisarAssociacaoLogradouroCepPorLogradouro(logradouro);

					boolean repeticao = false;
					if(colecaoCepSelecionadosUsuario != null && !colecaoCepSelecionadosUsuario.isEmpty()){

						LogradouroCep logradouroCepADD = new LogradouroCep();
						logradouroCepADD.setCep(cepSelecionado);

						// Evitar repetição
						Iterator iteratorColecaoCepSelecionadosUsuario = colecaoCepSelecionadosUsuario.iterator();

						while(iteratorColecaoCepSelecionadosUsuario.hasNext()){

							if(((LogradouroCep) iteratorColecaoCepSelecionadosUsuario.next()).getCep().getCepId().equals(
											cepSelecionado.getCepId())){
								repeticao = true;

								// -----------------------------------------------
								inserirEnderecoActionForm.setAssociacaoExistente("OK");
								// -----------------------------------------------

								break;
							}
						}

						if(!repeticao){

							colecaoCepSelecionadosUsuario.add(logradouroCepADD);
							inserirEnderecoActionForm.setCepSelecionado(String.valueOf(cepSelecionado.getCepId()));

							sessao.removeAttribute("objetoCep");
							sessao.setAttribute("colecaoCepSelecionadosUsuario", colecaoCepSelecionadosUsuario);
						}
					}

					if((inserirEnderecoActionForm.getTipoAcao() == null || inserirEnderecoActionForm.getTipoAcao().equals(""))
									&& !repeticao){

						inserirEnderecoActionForm.setAssociacaoExistente("FALSO");
					}

				}else if(sessao.getAttribute("objetoCep") == null && sessao.getAttribute("colecaoCepSelecionadosUsuario") != null){

					colecaoCepSelecionadosUsuario = fachada.pesquisarAssociacaoLogradouroCepPorLogradouro(logradouro);

					if(colecaoCepSelecionadosUsuario != null && !colecaoCepSelecionadosUsuario.isEmpty()){
						sessao.setAttribute("colecaoCepSelecionadosUsuario", colecaoCepSelecionadosUsuario);
					}

					inserirEnderecoActionForm.setAssociacaoExistente("OK");
					httpServletRequest.setAttribute("logradouroMunicipioCepUnico", "FALSE");

					Collection<Cep> colecaoCep = null;
					FiltroCep filtroCep = new FiltroCep();

					filtroCep.adicionarCaminhoParaCarregamentoEntidade("cepTipo");

					filtroCep.adicionarParametro(new ParametroSimples(FiltroCep.CEPID, inserirEnderecoActionForm.getCepSelecionado()));
					filtroCep.adicionarParametro(new ParametroSimples(FiltroCep.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

					colecaoCep = fachada.pesquisar(filtroCep, Cep.class.getName());

					Cep cepSelecionado = (Cep) Util.retonarObjetoDeColecao(colecaoCep);

					if(cepSelecionado != null){

						LogradouroCep logradouroCepADD = new LogradouroCep();
						logradouroCepADD.setCep(cepSelecionado);

						colecaoCepSelecionadosUsuario.add(logradouroCepADD);
						inserirEnderecoActionForm.setCepSelecionado(String.valueOf(cepSelecionado.getCepId()));

						sessao.setAttribute("colecaoCepSelecionadosUsuario", colecaoCepSelecionadosUsuario);
					}
				}
				Collection<Bairro> colecaoBairros = fachada.obterBairrosPorLogradouro(logradouro);

				inserirEnderecoActionForm.setLogradouro(String.valueOf(logradouro.getId()));
				inserirEnderecoActionForm.setLogradouroDescricao(logradouro.getDescricaoFormatada());

				if(colecaoBairros != null && !colecaoBairros.isEmpty()){
					httpServletRequest.setAttribute("logradouroBairros", colecaoBairros);

					/*
					 * Caso o logradouro esteja associado apenas com um bairro, este bairro será
					 * selecionado
					 * automaticamente pelo sistema.
					 */
					if(colecaoBairros.size() == 1){
						inserirEnderecoActionForm.setBairro(((Bairro) Util.retonarObjetoDeColecao(colecaoBairros)).getId().toString());
					}
				}

				httpServletRequest.setAttribute("nomeCampo", "bairro");
			}
		}

		// Limpar os dados referentes ao logradouro ===========================
		if(limparLogradouro != null && !limparLogradouro.trim().equalsIgnoreCase("")){
			inserirEnderecoActionForm.setLogradouro("");
			inserirEnderecoActionForm.setLogradouroDescricao("");

			sessao.removeAttribute("colecaoCepSelecionadosUsuario");
		}

		if(httpServletRequest.getParameter("tipoConsulta") != null && !httpServletRequest.getParameter("tipoConsulta").equals("")){

			// Verifica se o tipo da consulta de imovel é de logradouro
			// se for os parametros de enviarDadosParametros serão mandados para
			// a pagina imovel_pesuisar.jsp
			if(httpServletRequest.getParameter("tipoConsulta").equals("logradouro")){

				inserirEnderecoActionForm.setLogradouro(httpServletRequest.getParameter("idCampoEnviarDados"));

				inserirEnderecoActionForm.setLogradouroDescricao(httpServletRequest.getParameter("descricaoCampoEnviarDados"));

			}
		}

		return retorno;
	}
}
