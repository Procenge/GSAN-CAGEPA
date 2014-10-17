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

package gcom.gui.cadastro.endereco;

import gcom.arrecadacao.banco.Agencia;
import gcom.cadastro.cliente.ClienteEndereco;
import gcom.cadastro.endereco.Cep;
import gcom.cadastro.endereco.EnderecoReferencia;
import gcom.cadastro.endereco.EnderecoTipo;
import gcom.cadastro.endereco.FiltroCep;
import gcom.cadastro.endereco.FiltroEnderecoReferencia;
import gcom.cadastro.endereco.FiltroEnderecoTipo;
import gcom.cadastro.endereco.FiltroLogradouro;
import gcom.cadastro.endereco.Logradouro;
import gcom.cadastro.endereco.LogradouroBairro;
import gcom.cadastro.endereco.LogradouroCep;
import gcom.cadastro.geografico.Bairro;
import gcom.cadastro.geografico.FiltroBairro;
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
import org.apache.struts.validator.DynaValidatorForm;

/**
 * < <Descrição da Classe>>
 * 
 * @author rodrigo
 */
public class InserirEnderecoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("inserirEndereco");

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		// Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		String tipoRetorno = (String) sessao.getAttribute("tipoPesquisaRetorno");
		String tipoOperacao = (String) sessao.getAttribute("operacao");

		if(tipoRetorno != null && !tipoRetorno.trim().equalsIgnoreCase("")){

			InserirEnderecoActionForm inserirEnderecoActionForm = (InserirEnderecoActionForm) actionForm;

			String enderecoTipoJSP = inserirEnderecoActionForm.getTipo();
			String cepJSP = inserirEnderecoActionForm.getCepSelecionado();
			String logradouroJSP = inserirEnderecoActionForm.getLogradouro();
			String bairroJSP = inserirEnderecoActionForm.getBairro();
			String enderecoReferenciaJSP = inserirEnderecoActionForm.getEnderecoReferencia();
			String numeroJSP = inserirEnderecoActionForm.getNumero();
			String complementoJSP = inserirEnderecoActionForm.getComplemento();
			String tipoAcao = inserirEnderecoActionForm.getTipoAcao();

			Imovel imovel = new Imovel();
			ClienteEndereco clienteEndereco = new ClienteEndereco();
			Localidade localidade = new Localidade();
			GerenciaRegional gerenciaRegional = new GerenciaRegional();
			Bacia bacia = new Bacia();
			Agencia agencia = new Agencia();
			UnidadeOperacional unidadeOperacional = new UnidadeOperacional();
			DistritoOperacional distritoOperacional = new DistritoOperacional();

			LogradouroCep logradouroCep = new LogradouroCep();
			LogradouroBairro logradouroBairro = new LogradouroBairro();

			// == Cep ================================================
			if(cepJSP != null && !cepJSP.trim().equalsIgnoreCase("")){
				Collection colecaoCep = null;
				FiltroCep filtroCep = new FiltroCep();

				filtroCep.adicionarParametro(new ParametroSimples(FiltroCep.CEPID, cepJSP));
				filtroCep.adicionarParametro(new ParametroSimples(FiltroCep.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

				colecaoCep = fachada.pesquisar(filtroCep, Cep.class.getName());

				if(colecaoCep == null || colecaoCep.isEmpty()){

					throw new ActionServletException("atencao.pesquisa.cep_invalido");

				}else{
					Cep cep = (Cep) Util.retonarObjetoDeColecao(colecaoCep);

					// Adiciona o cep ao objeto final
					logradouroCep.setCep(cep);
				}
			}
			// =======================================================

			// == Logradouro =========================================
			if(logradouroJSP != null && !logradouroJSP.trim().equalsIgnoreCase("")){

				Collection colecaoLogradouro = null;
				FiltroLogradouro filtroLogradouro = new FiltroLogradouro();

				// Objetos que serão retornados pelo hibernate.
				filtroLogradouro.adicionarCaminhoParaCarregamentoEntidade("logradouroTipo");
				filtroLogradouro.adicionarCaminhoParaCarregamentoEntidade("logradouroTitulo");

				filtroLogradouro.adicionarParametro(new ParametroSimples(FiltroLogradouro.ID, logradouroJSP));

				filtroLogradouro.adicionarParametro(new ParametroSimples(FiltroLogradouro.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				colecaoLogradouro = fachada.pesquisar(filtroLogradouro, Logradouro.class.getName());

				if(colecaoLogradouro == null || colecaoLogradouro.isEmpty()){
					// Nenhum logradouro foi encontrado
					throw new ActionServletException("atencao.pesquisa.logradouro_inexistente");
				}else{
					Logradouro logradouro = (Logradouro) Util.retonarObjetoDeColecao(colecaoLogradouro);

					// Adiciona o logradouro ao objeto final
					logradouroCep.setLogradouro(logradouro);
				}

				// ======================================================

				// == Bairro ============================================
				if(bairroJSP != null && !bairroJSP.equalsIgnoreCase("")
								&& !bairroJSP.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

					Collection colecaoBairro = null;
					FiltroBairro filtroBairro = new FiltroBairro();

					// Objetos que serão retornados pelo hibernate.
					filtroBairro.adicionarCaminhoParaCarregamentoEntidade("municipio.unidadeFederacao");

					filtroBairro.adicionarParametro(new ParametroSimples(FiltroBairro.ID, bairroJSP));

					filtroBairro
									.adicionarParametro(new ParametroSimples(FiltroBairro.INDICADOR_USO,
													ConstantesSistema.INDICADOR_USO_ATIVO));

					colecaoBairro = fachada.pesquisar(filtroBairro, Bairro.class.getName());

					if(colecaoBairro == null || colecaoBairro.isEmpty()){
						// Nenhum bairro foi encontrado
						throw new ActionServletException("atencao.pesquisa.bairro_inexistente");
					}else{
						Bairro bairro = (Bairro) Util.retonarObjetoDeColecao(colecaoBairro);

						// Adiciona o bairro ao objeto final
						logradouroBairro.setBairro(bairro);
					}
				}
				// ======================================================
			}
			// ========================================================

			// == Endereco Referencia ==========================
			if(enderecoReferenciaJSP != null
							&& !enderecoReferenciaJSP.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

				Collection colecaoEnderecoReferencia = null;

				FiltroEnderecoReferencia filtroEnderecoReferencia = new FiltroEnderecoReferencia();

				filtroEnderecoReferencia.adicionarParametro(new ParametroSimples(FiltroEnderecoReferencia.ID, enderecoReferenciaJSP));

				filtroEnderecoReferencia.adicionarParametro(new ParametroSimples(FiltroEnderecoReferencia.INDICADORUSO,
								ConstantesSistema.INDICADOR_USO_ATIVO));

				colecaoEnderecoReferencia = fachada.pesquisar(filtroEnderecoReferencia, EnderecoReferencia.class.getName());

				if(colecaoEnderecoReferencia == null || colecaoEnderecoReferencia.isEmpty()){
					// Nenhum EnderecoReferencia foi encontrado
					throw new ActionServletException("atencao.pesquisa.endereco_referencia_inexistente");
				}else{
					EnderecoReferencia enderecoReferencia = (EnderecoReferencia) Util.retonarObjetoDeColecao(colecaoEnderecoReferencia);

					// Adiciona o EnderecoReferencia ao objeto final
					clienteEndereco.setEnderecoReferencia(enderecoReferencia);
					imovel.setEnderecoReferencia(enderecoReferencia);
					localidade.setEnderecoReferencia(enderecoReferencia);
					gerenciaRegional.setEnderecoReferencia(enderecoReferencia);
					bacia.setEnderecoReferencia(enderecoReferencia);
					agencia.setEnderecoReferencia(enderecoReferencia);
					unidadeOperacional.setEnderecoReferencia(enderecoReferencia);
					distritoOperacional.setEnderecoReferencia(enderecoReferencia);
				}
			}
			// ========================================================

			// == Endereco Tipo =======================================
			if(tipoRetorno.trim().equalsIgnoreCase("cliente")){
				if(enderecoTipoJSP != null
								&& !enderecoTipoJSP.trim().equalsIgnoreCase(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO))){

					Collection colecaoEnderecoTipo = null;

					FiltroEnderecoTipo filtroEnderecoTipo = new FiltroEnderecoTipo();

					filtroEnderecoTipo.adicionarParametro(new ParametroSimples(FiltroEnderecoTipo.ID, enderecoTipoJSP));

					filtroEnderecoTipo.adicionarParametro(new ParametroSimples(FiltroEnderecoTipo.INDICADORUSO,
									ConstantesSistema.INDICADOR_USO_ATIVO));

					colecaoEnderecoTipo = fachada.pesquisar(filtroEnderecoTipo, EnderecoTipo.class.getName());

					if(colecaoEnderecoTipo == null || colecaoEnderecoTipo.isEmpty()){
						// Nenhum EnderecoTipo foi encontrado
						throw new ActionServletException("atencao.pesquisa.endereco_tipo_inexistente");
					}else{
						EnderecoTipo enderecoTipo = (EnderecoTipo) Util.retonarObjetoDeColecao(colecaoEnderecoTipo);

						// Adiciona o EnderecoTipo ao objeto final
						clienteEndereco.setEnderecoTipo(enderecoTipo);
					}
				}
			}
			// ======================================================

			// == Número ============================================
			if(numeroJSP != null && !numeroJSP.trim().equalsIgnoreCase("")){

				// Adiciona o numero ao objeto final
				clienteEndereco.setNumero(numeroJSP);
				imovel.setNumeroImovel(numeroJSP);
				localidade.setNumeroImovel(numeroJSP);
				gerenciaRegional.setNumeroImovel(numeroJSP);
				bacia.setNumeroImovel(numeroJSP);
				agencia.setNumeroImovel(numeroJSP);
			}
			// ======================================================

			// == Complemento ======================================
			if(complementoJSP != null && !complementoJSP.trim().equalsIgnoreCase("")){

				// Adiciona o complemento ao objeto final
				clienteEndereco.setComplemento(complementoJSP);
				imovel.setComplementoEndereco(complementoJSP);
				localidade.setComplementoEndereco(complementoJSP);
				gerenciaRegional.setComplementoEndereco(complementoJSP);
				bacia.setComplementoEndereco(complementoJSP);
				agencia.setComplementoEndereco(complementoJSP);
			}
			// ======================================================

			// Adiciona o indicador para nao receber correspondência ao objeto
			// final
			clienteEndereco.setIndicadorEnderecoCorrespondencia(ConstantesSistema.INDICADOR_NAO_ENDERECO_CORRESPONDENCIA);

			// Adiciona a data da última alteração ao objeto final
			imovel.setUltimaAlteracao(new Date());
			localidade.setUltimaAlteracao(new Date());
			clienteEndereco.setUltimaAlteracao(new Date());
			gerenciaRegional.setUltimaAlteracao(new Date());
			bacia.setUltimaAlteracao(new Date());
			agencia.setUltimaAlteracao(new Date());
			unidadeOperacional.setUltimaAlteracao(new Date());
			distritoOperacional.setUltimaAlteracao(new Date());

			// Carregando os objetos finais com o LogradouroCep e
			// LogradouroBairro
			logradouroCep.setIndicadorUso(ConstantesSistema.INDICADOR_USO_ATIVO);

			Integer idLogradouroCep = fachada.inserirAssociacaoLogradouroCep(logradouroCep);
			logradouroCep.setId(idLogradouroCep);

			logradouroBairro = fachada.pesquisarAssociacaoLogradouroBairro(logradouroBairro.getBairro().getId(), logradouroCep
							.getLogradouro().getId());

			imovel.setLogradouroCep(logradouroCep);
			imovel.setLogradouroBairro(logradouroBairro);

			localidade.setLogradouroCep(logradouroCep);
			localidade.setLogradouroBairro(logradouroBairro);

			gerenciaRegional.setLogradouroCep(logradouroCep);
			gerenciaRegional.setLogradouroBairro(logradouroBairro);

			bacia.setLogradouroCep(logradouroCep);
			bacia.setLogradouroBairro(logradouroBairro);

			agencia.setLogradouroCep(logradouroCep);
			agencia.setLogradouroBairro(logradouroBairro);

			clienteEndereco.setLogradouroCep(logradouroCep);
			clienteEndereco.setLogradouroBairro(logradouroBairro);

			unidadeOperacional.setBairro(logradouroBairro.getBairro());
			unidadeOperacional.setCep(logradouroCep.getCep());

			distritoOperacional.setBairro(logradouroBairro);
			distritoOperacional.setCep(logradouroCep);
			if(logradouroBairro != null){
				unidadeOperacional.setLogradouro(logradouroBairro.getLogradouro());
			}
			if(logradouroCep != null){
				unidadeOperacional.setLogradouro(logradouroCep.getLogradouro());
			}
			if(numeroJSP != null && !numeroJSP.equals("")){
				unidadeOperacional.setNumeroImovel(numeroJSP);
			}

			if(numeroJSP != null && !numeroJSP.equals("")){
				distritoOperacional.setNumeroImovel(numeroJSP);
			}
			unidadeOperacional.setComplementoEndereco(complementoJSP);
			distritoOperacional.setComplementoEndereco(complementoJSP);
			// ======================================================

			Collection enderecos = null;

			/*
			 * Especialmente para o retorno do caso de uso de RA, foi criado um retorno diferente
			 */
			if(tipoRetorno.trim().equalsIgnoreCase("registroAtendimento")){

				// Aba Nº 02 - Endereço do Imóvel
				if(tipoOperacao.equalsIgnoreCase("1")){

					if(sessao.getAttribute("colecaoEnderecos") == null){
						enderecos = new ArrayList();
						enderecos.add(imovel);
						sessao.setAttribute("colecaoEnderecos", enderecos);
					}else{

						enderecos = (Collection) sessao.getAttribute("colecaoEnderecos");

						if(enderecos.isEmpty() || (tipoAcao != null && !tipoAcao.equals(""))){
							enderecos.clear();
							enderecos.add(imovel);
						}
					}

					httpServletRequest.setAttribute("fecharPopup", "ok");
				}
				// Aba Nº 03 - Endereço do Solicitante
				else if(tipoOperacao.equalsIgnoreCase("2")){

					if(sessao.getAttribute("colecaoEnderecosAbaSolicitante") == null){
						enderecos = new ArrayList();
						enderecos.add(clienteEndereco);
						sessao.setAttribute("colecaoEnderecosAbaSolicitante", enderecos);
					}else{

						enderecos = (Collection) sessao.getAttribute("colecaoEnderecosAbaSolicitante");

						if(enderecos.isEmpty() || (tipoAcao != null && !tipoAcao.equals(""))){
							enderecos.clear();
							enderecos.add(clienteEndereco);
						}
					}

					httpServletRequest.setAttribute("fecharPopup", "ok");
				}
				// Aba Nº 02 - Endereço do Imóvel (Atualizar)
				else if(tipoOperacao.equalsIgnoreCase("3")){

					if(sessao.getAttribute("colecaoEnderecos") == null){
						enderecos = new ArrayList();
						enderecos.add(imovel);
						sessao.setAttribute("colecaoEnderecos", enderecos);
					}else{

						enderecos = (Collection) sessao.getAttribute("colecaoEnderecos");

						if(enderecos.isEmpty() || (tipoAcao != null && !tipoAcao.equals(""))){
							enderecos.clear();
							enderecos.add(imovel);
						}
					}

					httpServletRequest.setAttribute("fecharPopup", "ok");
				}
				// POPUP - Endereço do Solicitante
				else if(tipoOperacao.equalsIgnoreCase("4")){

					if(sessao.getAttribute("colecaoEnderecosSolicitante") == null){
						enderecos = new ArrayList();
						enderecos.add(clienteEndereco);
						sessao.setAttribute("colecaoEnderecosSolicitante", enderecos);
					}else{

						enderecos = (Collection) sessao.getAttribute("colecaoEnderecosSolicitante");

						if(enderecos.isEmpty() || (tipoAcao != null && !tipoAcao.equals(""))){
							enderecos.clear();
							enderecos.add(clienteEndereco);
						}
					}
				}

				httpServletRequest.setAttribute("flagRedirect", "registroAtendimento");

			}

			// Continuação da forma padrão do retorno de um endereço
			else{

				if(sessao.getAttribute("colecaoEnderecos") == null){
					enderecos = new ArrayList();

					if(tipoRetorno.trim().equalsIgnoreCase("cliente")){
						enderecos.add(clienteEndereco);
						httpServletRequest.setAttribute("flagRedirect", "cliente");
					}else if(tipoRetorno.trim().equalsIgnoreCase("imovel")){
						enderecos.add(imovel);
						httpServletRequest.setAttribute("flagRedirect", "imovel");
						httpServletRequest.setAttribute("fecharPopup", "ok");
					}else if(tipoRetorno.trim().equalsIgnoreCase("localidade")){
						enderecos.add(localidade);
						httpServletRequest.setAttribute("flagRedirect", "localidade");
						httpServletRequest.setAttribute("fecharPopup", "ok");
					}else if(tipoRetorno.trim().equalsIgnoreCase("gerenciaRegional")){
						enderecos.add(gerenciaRegional);
						httpServletRequest.setAttribute("flagRedirect", "gerenciaRegional");
						httpServletRequest.setAttribute("fecharPopup", "ok");
					}else if(tipoRetorno.trim().equalsIgnoreCase("agencia")){
						enderecos.add(agencia);
						httpServletRequest.setAttribute("flagRedirect", "agencia");
						httpServletRequest.setAttribute("fecharPopup", "ok");
					}else if(tipoRetorno.trim().equalsIgnoreCase("unidadeOperacional")){
						enderecos.add(unidadeOperacional);
						httpServletRequest.setAttribute("flagRedirect", "unidadeOperacional");
						httpServletRequest.setAttribute("fecharPopup", "ok");
					}else if(tipoRetorno.trim().equalsIgnoreCase("distritoOperacional")){
						enderecos.add(distritoOperacional);
						httpServletRequest.setAttribute("flagRedirect", "distritoOperacional");
						httpServletRequest.setAttribute("fecharPopup", "ok");
					}else if(tipoRetorno.trim().equalsIgnoreCase("bacia")){
						enderecos.add(bacia);
						httpServletRequest.setAttribute("flagRedirect", "bacia");
						httpServletRequest.setAttribute("fecharPopup", "ok");
					}

					sessao.setAttribute("colecaoEnderecos", enderecos);

				}else{
					enderecos = (Collection) sessao.getAttribute("colecaoEnderecos");

					if(tipoRetorno.trim().equalsIgnoreCase("cliente")){

						if(tipoAcao != null && !tipoAcao.equals("")){

							Iterator iteratorEnderecos = enderecos.iterator();
							ClienteEndereco clienteEnderecoNaColecao = null;

							while(iteratorEnderecos.hasNext()){
								clienteEnderecoNaColecao = (ClienteEndereco) iteratorEnderecos.next();

								if(obterTimestampIdObjeto(clienteEnderecoNaColecao) == Long.parseLong(inserirEnderecoActionForm
												.getObjetoClienteEnderecoSelecionado())){

									if(clienteEnderecoNaColecao.getId() != null){
										clienteEndereco.setId(clienteEnderecoNaColecao.getId());
									}

									clienteEndereco.setUltimaAlteracao(clienteEnderecoNaColecao.getUltimaAlteracao());

									iteratorEnderecos.remove();
								}

								if(obterTimestampIdObjeto(clienteEnderecoNaColecao) == Long.parseLong(inserirEnderecoActionForm
												.getEnderecoCorrespondencia())){

									DynaValidatorForm formProcessoCliente = (DynaValidatorForm) sessao.getAttribute("ClienteActionForm");
									formProcessoCliente.set("enderecoCorrespondenciaSelecao", Long.parseLong(inserirEnderecoActionForm
													.getEnderecoCorrespondencia()));

									clienteEndereco
													.setIndicadorEnderecoCorrespondencia(ConstantesSistema.INDICADOR_ENDERECO_CORRESPONDENCIA);

								}
								// Verificando se o endereço já existe na coleção
								// Roberta Costa - 31/07/2006
								if(clienteEnderecoNaColecao.getEnderecoTipo().getId().equals(clienteEndereco.getEnderecoTipo().getId())
												&& clienteEnderecoNaColecao.getLogradouroCep().equals(clienteEndereco.getLogradouroCep())
												&& clienteEnderecoNaColecao.getLogradouroBairro().equals(
																clienteEndereco.getLogradouroBairro())
												&& (clienteEnderecoNaColecao.getEnderecoReferencia() != null && clienteEnderecoNaColecao
																.getEnderecoReferencia().getId().equals(
																				clienteEndereco.getEnderecoReferencia().getId()))
												&& clienteEnderecoNaColecao.getNumero().equals(clienteEndereco.getNumero())
												&& (clienteEnderecoNaColecao.getComplemento() == null
																&& clienteEndereco.getComplemento() == null || (clienteEnderecoNaColecao
																.getComplemento() != null
																&& clienteEndereco.getComplemento() != null && clienteEnderecoNaColecao
																.getComplemento().equals(clienteEndereco.getComplemento())))){

									throw new ActionServletException("atencao.endereco_ja_informado");
								}
							}

							httpServletRequest.setAttribute("fecharPopup", "ok");
						}else{
							Iterator iteratorEnderecos = enderecos.iterator();
							ClienteEndereco clienteEnderecoNaColecao = null;

							while(iteratorEnderecos.hasNext()){
								clienteEnderecoNaColecao = (ClienteEndereco) iteratorEnderecos.next();
								// Verificando se o endereço já existe na coleção
								// Roberta Costa - 31/07/2006
								if(clienteEnderecoNaColecao.getEnderecoTipo().getId().equals(clienteEndereco.getEnderecoTipo().getId())
												&& clienteEnderecoNaColecao.getLogradouroCep().equals(clienteEndereco.getLogradouroCep())
												&& clienteEnderecoNaColecao.getLogradouroBairro().equals(
																clienteEndereco.getLogradouroBairro())
												&& clienteEnderecoNaColecao.getEnderecoReferencia().getId().equals(
																clienteEndereco.getEnderecoReferencia().getId())
												&& clienteEnderecoNaColecao.getNumero().equals(clienteEndereco.getNumero())
												&& (clienteEnderecoNaColecao.getComplemento() == null
																&& clienteEndereco.getComplemento() == null || (clienteEnderecoNaColecao
																.getComplemento() != null
																&& clienteEndereco.getComplemento() != null && clienteEnderecoNaColecao
																.getComplemento().equals(clienteEndereco.getComplemento())))){

									throw new ActionServletException("atencao.endereco_ja_informado");
								}
							}
						}

						enderecos.add(clienteEndereco);

						httpServletRequest.setAttribute("flagRedirect", "cliente");

					}else if(tipoRetorno.trim().equalsIgnoreCase("imovel")){

						if(enderecos.isEmpty() || (tipoAcao != null && !tipoAcao.equals(""))){
							enderecos.clear();
							enderecos.add(imovel);
						}

						httpServletRequest.setAttribute("flagRedirect", "imovel");
						httpServletRequest.setAttribute("fecharPopup", "ok");

					}else if(tipoRetorno.trim().equalsIgnoreCase("localidade")){

						if(enderecos.isEmpty() || (tipoAcao != null && !tipoAcao.equals(""))){
							enderecos.clear();
							enderecos.add(localidade);
						}

						httpServletRequest.setAttribute("flagRedirect", "localidade");
						httpServletRequest.setAttribute("fecharPopup", "ok");

					}else if(tipoRetorno.trim().equalsIgnoreCase("gerenciaRegional")){

						if(enderecos.isEmpty() || (tipoAcao != null && !tipoAcao.equals(""))){
							enderecos.clear();
							enderecos.add(gerenciaRegional);
						}

						httpServletRequest.setAttribute("flagRedirect", "gerenciaRegional");
						httpServletRequest.setAttribute("fecharPopup", "ok");

					}else if(tipoRetorno.trim().equalsIgnoreCase("agencia")){

						if(enderecos.isEmpty() || (tipoAcao != null && !tipoAcao.equals(""))){
							enderecos.clear();
							enderecos.add(agencia);
						}

						httpServletRequest.setAttribute("flagRedirect", "agencia");
						httpServletRequest.setAttribute("fecharPopup", "ok");

					}else if(tipoRetorno.trim().equalsIgnoreCase("unidadeOperacional")){
						if(enderecos.isEmpty() || (tipoAcao != null && !tipoAcao.equals(""))){
							enderecos.clear();
							enderecos.add(unidadeOperacional);
						}

						httpServletRequest.setAttribute("flagRedirect", "unidadeOperacional");
						httpServletRequest.setAttribute("fecharPopup", "ok");
					}else if(tipoRetorno.trim().equalsIgnoreCase("distritoOperacional")){
						if(enderecos.isEmpty() || (tipoAcao != null && !tipoAcao.equals(""))){
							enderecos.clear();
							enderecos.add(distritoOperacional);
						}

						httpServletRequest.setAttribute("flagRedirect", "distritoOperacional");
						httpServletRequest.setAttribute("fecharPopup", "ok");
					}else if(tipoRetorno.trim().equalsIgnoreCase("bacia")){

						if(enderecos.isEmpty() || (tipoAcao != null && !tipoAcao.equals(""))){
							enderecos.clear();
							enderecos.add(bacia);
						}

						httpServletRequest.setAttribute("flagRedirect", "bacia");
						httpServletRequest.setAttribute("fecharPopup", "ok");

					}

				}
			}

			httpServletRequest.setAttribute("flagOperacao", tipoOperacao);
			httpServletRequest.setAttribute("flagReload", "true");

			// Limpar o formulário
			inserirEnderecoActionForm.setBairro("");
			inserirEnderecoActionForm.setCep("");
			inserirEnderecoActionForm.setCepSelecionado("");
			inserirEnderecoActionForm.setCepUnico("");
			inserirEnderecoActionForm.setComplemento("");
			inserirEnderecoActionForm.setEnderecoReferencia(EnderecoReferencia.NUMERO.toString());
			inserirEnderecoActionForm.setLogradouro("");
			inserirEnderecoActionForm.setLogradouroDescricao("");
			inserirEnderecoActionForm.setNumero("");
			inserirEnderecoActionForm.setTipo(String.valueOf(ConstantesSistema.NUMERO_NAO_INFORMADO));

			inserirEnderecoActionForm.setEnderecoCorrespondencia("");
			inserirEnderecoActionForm.setObjetoClienteEnderecoSelecionado("");

			inserirEnderecoActionForm.setTipoAcao("");

			sessao.removeAttribute("objetoCep");
			sessao.removeAttribute("colecaoCepSelecionadosUsuario");

		}
		// devolve o mapeamento de retorno
		return retorno;
	}
}
