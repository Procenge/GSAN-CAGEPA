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

package gcom.gui.cobranca.parcelamento;

import gcom.cadastro.cliente.*;
import gcom.cadastro.endereco.EnderecoTipo;
import gcom.cadastro.geografico.FiltroUnidadeFederacao;
import gcom.cadastro.geografico.UnidadeFederacao;
import gcom.cobranca.parcelamento.*;
import gcom.fachada.Fachada;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirParcelamentoDadosTermoAction
				extends ExibidorProcessamentoTarefaRelatorio {

	/**
	 * [UC0214] Efetuar Parcelamento de Débitos
	 * [UC0252] Consultar Parcelamentos de Débitos
	 * 
	 * @param actionMapping
	 *            Descrição do parâmetro
	 * @param actionForm
	 *            Descrição do parâmetro
	 * @param httpServletRequest
	 *            Descrição do parâmetro
	 * @param httpServletResponse
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @author Saulo Lima
	 * @date 27/08/2013
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// cria a variável de retorno
		ActionForward retorno = actionMapping.findForward("informarParcelamentoDadosTermo");
		Fachada fachada = Fachada.getInstancia();

		ParcelamentoDadosTermoActionForm form = (ParcelamentoDadosTermoActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);
		String atualizarDadosTermo = httpServletRequest.getParameter("atualizarDadosTermo");
		String idParcelamento = (String) sessao.getAttribute("idParcelamento");
		String idCliente = null;
		String idImovel = (String) (sessao.getAttribute("idImovel"));
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		
		boolean indicadorTermoParcelamentoPreview = false;
		if(sessao.getAttribute("TermoParcelamentoPreview") != null
						&& ((String) sessao.getAttribute("TermoParcelamentoPreview")).equals("True")){
			indicadorTermoParcelamentoPreview = true;		
		}
		
		String pesquisaClienteParametro = httpServletRequest.getParameter("pesquisaCliente");

		FiltroParcelamentoAcordoTipo filtroParcelamentoAcordoTipo = new FiltroParcelamentoAcordoTipo();
		filtroParcelamentoAcordoTipo.adicionarParametro(new ParametroSimples(FiltroParcelamentoAcordoTipo.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroParcelamentoAcordoTipo.adicionarParametro(new ParametroSimples(FiltroParcelamentoAcordoTipo.INDICADOR_PARCELAMETO_NORMAL, 1));
		filtroParcelamentoAcordoTipo.setCampoOrderBy(FiltroParcelamentoAcordoTipo.DESCRICAO);

		httpServletRequest.setAttribute("colecaoAcordoTipo",
						Fachada.getInstancia().pesquisar(filtroParcelamentoAcordoTipo, ParcelamentoAcordoTipo.class.getName()));

		FiltroOrgaoExpedidorRg filtroOrgaoExpedidorRg = new FiltroOrgaoExpedidorRg();
		Collection<OrgaoExpedidorRg> colecaOrgaoExpedidorRg = Fachada.getInstancia().pesquisar(filtroOrgaoExpedidorRg,
						OrgaoExpedidorRg.class.getName());
		sessao.setAttribute("colecaoOrgaoExpedidorRg", colecaOrgaoExpedidorRg);

		FiltroUnidadeFederacao filtroUnidadeFederacao = new FiltroUnidadeFederacao();
		Collection<UnidadeFederacao> colecaoUnidadeFederacao = Fachada.getInstancia().pesquisar(filtroUnidadeFederacao,
						UnidadeFederacao.class.getName());
		sessao.setAttribute("colecaoUnidadeFederacao", colecaoUnidadeFederacao);

		FiltroNacionalidade filtroNacionalidade = new FiltroNacionalidade();
		filtroNacionalidade.setCampoOrderBy(FiltroNacionalidade.DESCRICAO);
		Collection<Nacionalidade> colecaoNacionalidade = fachada.pesquisar(filtroNacionalidade, Nacionalidade.class.getName());
		sessao.setAttribute("colecaoNacionalidade", colecaoNacionalidade);

		String indicadorPessoaFisicaJuridica = (String) sessao.getAttribute("indicadorPessoaFisicaJuridica");

		if(indicadorPessoaFisicaJuridica != null && indicadorPessoaFisicaJuridica.equals(ClienteTipo.INDICADOR_PESSOA_JURIDICA.toString())){
			sessao.setAttribute("informarCliente", "S");

			if(form.getIdCliente() != null && !form.getIdCliente().equals("")){
				idCliente = form.getIdCliente().toString();
			}

		}else{
			idCliente = String.valueOf(sessao.getAttribute("idClienteResponsavel"));
			sessao.removeAttribute("informarCliente");
		}

		Collection<ClienteEndereco> colecaoClienteEndereco = new ArrayList<ClienteEndereco>();
		if(idCliente != null){
			FiltroClienteEndereco filtroClienteEndereco = new FiltroClienteEndereco();
			filtroClienteEndereco.adicionarParametro(new ParametroSimples(FiltroClienteEndereco.CLIENTE_ID, idCliente));
			
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("enderecoTipo");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.cep");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTipo");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTitulo");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("enderecoReferencia");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.logradouro");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.logradouro.logradouroTipo");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.logradouro.logradouroTitulo");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro.municipio");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro.municipio.unidadeFederacao");

			colecaoClienteEndereco = fachada.pesquisar(filtroClienteEndereco, ClienteEndereco.class.getName());
			sessao.setAttribute("colecaoClienteEndereco", colecaoClienteEndereco);
		}else{
			sessao.setAttribute("colecaoClienteEndereco", colecaoClienteEndereco);
		}

		if(!Util.isVazioOuBranco(atualizarDadosTermo) && atualizarDadosTermo.equals("sim")){

			// Veio do botão de atualizar/emitir
			ParcelamentoDadosTermo parcelamentoDadosTermo = new ParcelamentoDadosTermo();

			if(!indicadorTermoParcelamentoPreview){
				Parcelamento parcelamento = new Parcelamento(Integer.valueOf(idParcelamento));
				parcelamentoDadosTermo.setParcelamento(parcelamento);
				
				FiltroParcelamentoDadosTermo filtro = new FiltroParcelamentoDadosTermo();
				filtro.adicionarParametro(new ParametroSimples(FiltroParcelamentoDadosTermo.PARCELAMENTO_ID, idParcelamento));
				Collection<ParcelamentoDadosTermo> colecao = fachada.pesquisar(filtro, ParcelamentoDadosTermo.class.getName());

				if(!Util.isVazioOrNulo(colecao)){
					parcelamentoDadosTermo.setId(((ParcelamentoDadosTermo) colecao.iterator().next()).getId());
					form.setId(String.valueOf(parcelamentoDadosTermo.getId()));
				}				
			}else{
				parcelamentoDadosTermo.setParcelamento(null);
			}
			
			ParcelamentoAcordoTipo parcelamentoAcordoTipo = new ParcelamentoAcordoTipo();
			if(form.getIdParcelamentoAcordoTipo() != null && !form.getIdParcelamentoAcordoTipo().equals("")){
				parcelamentoAcordoTipo.setId(Integer.valueOf(form.getIdParcelamentoAcordoTipo()));
			}
			parcelamentoDadosTermo.setParcelamentoAcordoTipo(parcelamentoAcordoTipo);

			parcelamentoDadosTermo.setIndicadorProcurador(2);

			Cliente cliente = new Cliente();
			if(form.getIdCliente() != null && !form.getIdCliente().equals("")){
				cliente.setId(Integer.valueOf(form.getIdCliente()));
			}
			parcelamentoDadosTermo.setCliente(cliente);

			if(form.getNomeCliente() != null && !form.getNomeCliente().equals("")){
				parcelamentoDadosTermo.setNomeCliente(form.getNomeCliente());
			}

			if(form.getNumeroRgCliente() != null && !form.getNumeroRgCliente().equals("")){
				parcelamentoDadosTermo.setNumeroRgCliente(form.getNumeroRgCliente());
			}

			OrgaoExpedidorRg orgaoExpedidorRg = new OrgaoExpedidorRg();
			if(form.getIdOrgaoExpedidorRgCliente() != null && !form.getIdOrgaoExpedidorRgCliente().equals("")){
				orgaoExpedidorRg.setId(Integer.valueOf(form.getIdOrgaoExpedidorRgCliente()));
			}
			parcelamentoDadosTermo.setOrgaoExpedidorRgCliente(orgaoExpedidorRg);

			UnidadeFederacao unidadeFederacao = new UnidadeFederacao();
			if(form.getIdOrgaoExpedidorRgCliente() != null && !form.getIdOrgaoExpedidorRgCliente().equals("")){
				unidadeFederacao.setId(Integer.valueOf(form.getIdUnidadeFederacaoCliente()));
			}
			parcelamentoDadosTermo.setUnidadeFederacaoCliente(unidadeFederacao);

			Nacionalidade nacionalidade = new Nacionalidade();
			if(form.getIdNacionalidadeCliente() != null && !form.getIdNacionalidadeCliente().equals("")){
				nacionalidade.setId(Integer.valueOf(form.getIdNacionalidadeCliente()));
			}
			parcelamentoDadosTermo.setNacionalidadeCliente(nacionalidade);

			if(form.getNumeroCpfCliente() != null && !form.getNumeroCpfCliente().equals("")){
				parcelamentoDadosTermo.setNumeroCpfCliente(form.getNumeroCpfCliente());
			}

			if(form.getIdEnderecoCliente() != null && !form.getIdEnderecoCliente().equals("")){
				
				for(ClienteEndereco clienteEndereco : colecaoClienteEndereco){
					if(clienteEndereco.getId().equals(Integer.valueOf(form.getIdEnderecoCliente()))){
						
						EnderecoTipo enderecoTipo = new EnderecoTipo();
						enderecoTipo.setId(clienteEndereco.getEnderecoTipo().getId());
						parcelamentoDadosTermo.setEnderecoTipoCliente(enderecoTipo);

						parcelamentoDadosTermo.setIndicadorEnderecoCorrespondenciaCliente(Integer.valueOf(clienteEndereco
										.getIndicadorEnderecoCorrespondencia()));

						parcelamentoDadosTermo.setDescricaoEnderecoCliente(clienteEndereco.getEnderecoFormatadoSemCep());
					}
				} 				
			}

			parcelamentoDadosTermo.setUltimaAlteracao(GregorianCalendar.getInstance().getTime());

			if (!indicadorTermoParcelamentoPreview) {
				if(form.getId() != null && !Util.isVazioOuBranco(form.getId())){
					// – Atualizar Dados Termo Parcelamento
					parcelamentoDadosTermo.setId(Integer.valueOf(form.getId()));
					fachada.atualizar(parcelamentoDadosTermo);
				}else{
					// – Inserir Dados Termo Parcelamento
					form.setId(String.valueOf(fachada.inserir(parcelamentoDadosTermo)));
					parcelamentoDadosTermo.setId(Integer.valueOf(form.getId()));
				}				
			}

			sessao.setAttribute("atualizarEmitirDadosTermo", "true");
			sessao.setAttribute("parcelamentoDadosTermo", parcelamentoDadosTermo);
			retorno = actionMapping.findForward("atualizarParcelamentoDadosTermoAction");

		}else{

			// Primeira vez que entra na tela

			FiltroParcelamentoDadosTermo filtro = new FiltroParcelamentoDadosTermo();
			filtro.adicionarParametro(new ParametroSimples(FiltroParcelamentoDadosTermo.PARCELAMENTO_ID, idParcelamento));
			Collection<ParcelamentoDadosTermo> colecao = fachada.pesquisar(filtro, ParcelamentoDadosTermo.class.getName());

			ParcelamentoDadosTermo parcelamentoDadosTermo = null;
			if(!Util.isVazioOrNulo(colecao)){
				parcelamentoDadosTermo = colecao.iterator().next();
			}else{
				parcelamentoDadosTermo = (ParcelamentoDadosTermo) sessao.getAttribute("parcelamentoDadosTermo");
			}

			// 1. Caso existam dados do parcelamento
			if(parcelamentoDadosTermo != null){

				if(parcelamentoDadosTermo.getId() != null){
					form.setId(parcelamentoDadosTermo.getId().toString());
				}

				if(parcelamentoDadosTermo.getParcelamento() != null && parcelamentoDadosTermo.getParcelamento().getId() != null){
					form.setIdParcelamento(parcelamentoDadosTermo.getParcelamento().getId().toString());
				}

				if(idImovel != null && !idImovel.equals("")){
					form.setIdImovel(idImovel);
				}

				if(parcelamentoDadosTermo.getParcelamentoAcordoTipo() != null
								&& parcelamentoDadosTermo.getParcelamentoAcordoTipo().getId() != null){
					form.setIdParcelamentoAcordoTipo(String.valueOf(parcelamentoDadosTermo.getParcelamentoAcordoTipo().getId()));
				}

				if(parcelamentoDadosTermo.getIndicadorProcurador() != null){
					form.setIndicadorProcurador(String.valueOf(parcelamentoDadosTermo.getIndicadorProcurador()));
				}

				Cliente cliente = null;
				if(parcelamentoDadosTermo.getCliente() != null && parcelamentoDadosTermo.getCliente().getId() != null){
					form.setIdCliente(parcelamentoDadosTermo.getCliente().getId().toString());

					FiltroCliente filtroCliente = new FiltroCliente();

					filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, idCliente));
					filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.ORGAO_EXPEDIDOR_RG);
					filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.UNIDADE_FEDERACAO);
					filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.PROFISSAO);
					filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.NACIONALIDADE);
					filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.ESTADO_CIVIL);

					cliente = (Cliente) Util.retonarObjetoDeColecao(Fachada.getInstancia()
									.pesquisar(filtroCliente, Cliente.class.getName()));
				}

				if(parcelamentoDadosTermo.getNomeCliente() != null){
					form.setNomeCliente(parcelamentoDadosTermo.getNomeCliente());
				}

				if(parcelamentoDadosTermo.getNumeroRgCliente() != null){
					form.setNumeroRgCliente(parcelamentoDadosTermo.getNumeroRgCliente());

					if(cliente != null && cliente.getRg() != null && cliente.getRg().equals(parcelamentoDadosTermo.getNumeroRgCliente())){
						form.setNumeroRgClienteInicial("S");
					}
				}

				if(parcelamentoDadosTermo.getNacionalidadeCliente() != null
								&& parcelamentoDadosTermo.getNacionalidadeCliente().getId() != null){
					form.setIdNacionalidadeCliente(parcelamentoDadosTermo.getNacionalidadeCliente().getId().toString());

					if(cliente != null
									&& cliente.getNacionalidade() != null
									&& cliente.getNacionalidade().getId().toString()
													.equals(parcelamentoDadosTermo.getNacionalidadeCliente().getId().toString())){
						form.setIdNacionalidadeClienteInicial("S");
					}
				}

				if(parcelamentoDadosTermo.getOrgaoExpedidorRgCliente() != null
								&& parcelamentoDadosTermo.getOrgaoExpedidorRgCliente().getId() != null){
					form.setIdOrgaoExpedidorRgCliente(parcelamentoDadosTermo.getOrgaoExpedidorRgCliente().getId().toString());

					if(cliente != null
									&& cliente.getOrgaoExpedidorRg() != null
									&& cliente.getOrgaoExpedidorRg().getId().toString()
													.equals(parcelamentoDadosTermo.getOrgaoExpedidorRgCliente().getId().toString())){
						form.setIdOrgaoExpedidorRgClienteInicial("S");
					}
				}

				if(parcelamentoDadosTermo.getUnidadeFederacaoCliente() != null
								&& parcelamentoDadosTermo.getUnidadeFederacaoCliente().getId() != null){
					form.setIdUnidadeFederacaoCliente(parcelamentoDadosTermo.getUnidadeFederacaoCliente().getId().toString());

					if(cliente != null
									&& cliente.getUnidadeFederacao() != null
									&& cliente.getUnidadeFederacao().getId().toString()
													.equals(parcelamentoDadosTermo.getUnidadeFederacaoCliente().getId().toString())){
						form.setIdUnidadeFederacaoClienteInicial("S");
					}
				}

				if(parcelamentoDadosTermo.getNumeroCpfCliente() != null){
					form.setNumeroCpfCliente(parcelamentoDadosTermo.getNumeroCpfCliente());

					if(cliente != null && cliente.getCpf() != null && cliente.getCpf().equals(parcelamentoDadosTermo.getNumeroCpfCliente())){
						form.setNumeroCpfClienteInicial("S");
					}else{
						String cpfCnpjCliente = (String) httpServletRequest.getSession().getAttribute("cpfClienteParcelamentoDigitado");
						if(cpfCnpjCliente != null && cpfCnpjCliente.equals(parcelamentoDadosTermo.getNumeroCpfCliente())){
							form.setNumeroCpfClienteInicial("S");
						}
					}
				}


				if(cliente != null && parcelamentoDadosTermo.getEnderecoTipoCliente() != null
								&& parcelamentoDadosTermo.getEnderecoTipoCliente().getId() != null
								&& parcelamentoDadosTermo.getDescricaoEnderecoCliente() != null){

					for(ClienteEndereco clienteEndereco : colecaoClienteEndereco){
						if(clienteEndereco.getEnderecoFormatadoSemCep() != null
										&& clienteEndereco.getEnderecoFormatadoSemCep().equals(
														parcelamentoDadosTermo.getDescricaoEnderecoCliente())
										&& clienteEndereco.getEnderecoTipo() != null
										&& clienteEndereco.getEnderecoTipo().getId() != null
										&& clienteEndereco.getEnderecoTipo().getId()
														.equals(parcelamentoDadosTermo.getEnderecoTipoCliente().getId())){
							
							form.setIdEnderecoCliente(clienteEndereco.getId().toString() );
							form.setIdEnderecoTipoCliente(clienteEndereco.getEnderecoTipo().getId().toString());
							form.setIndicadorEnderecoCorrespondenciaCliente(clienteEndereco.getIndicadorEnderecoCorrespondencia().toString());
							form.setDescricaoEnderecoCliente(clienteEndereco.getEnderecoFormatadoSemCep());
						}
					}
				}
				
			}else{

				// Caso contrário, ou seja, não existam dados do parcelamento:
				// 1.1. Tipo de Acordo do Parcelamento (obrigatório)
				if(pesquisaClienteParametro == null || pesquisaClienteParametro.equals("")){
					form.setIndicadorProcurador("2");
					form.setIdParcelamentoAcordoTipo("");
					form.setIdParcelamento("");
					form.setIdImovel("");
					form.setIdCliente("");
					form.setNomeCliente("");
					form.setNumeroRgCliente("");
					form.setNumeroRgClienteInicial("");
					form.setIdOrgaoExpedidorRgCliente("");
					form.setIdOrgaoExpedidorRgClienteInicial("");
					form.setIdUnidadeFederacaoCliente("");
					form.setIdUnidadeFederacaoClienteInicial("");
					form.setNumeroCpfCliente("");
					form.setNumeroCpfClienteInicial("");
					form.setIdEnderecoCliente("");
					form.setIdEnderecoTipoCliente("");
					form.setIndicadorEnderecoCorrespondenciaCliente("");
					form.setDescricaoEnderecoCliente("");
					form.setIdNacionalidadeCliente("");
					form.setIdNacionalidadeClienteInicial("");
				}

				if(idCliente != null){
					if(idParcelamento != null && !idParcelamento.equals("")){
						form.setIdParcelamento(idParcelamento);
					}

					if(idImovel != null && !idImovel.equals("")){
						form.setIdImovel(idImovel);
					}

					FiltroCliente filtroCliente = new FiltroCliente();

					filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, idCliente));
					filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.ORGAO_EXPEDIDOR_RG);
					filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.UNIDADE_FEDERACAO);
					filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.PROFISSAO);
					filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.NACIONALIDADE);
					filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.ESTADO_CIVIL);

					Cliente cliente = (Cliente) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroCliente,
									Cliente.class.getName()));
					
					if(cliente != null){
						form.setIdCliente(idCliente);

						if(cliente.getNome() != null){
							form.setNomeCliente(cliente.getNome());
						}

						if(cliente.getRg() != null){
							form.setNumeroRgCliente(cliente.getRg());
							form.setNumeroRgClienteInicial("S");
						}

						if(cliente.getOrgaoExpedidorRg() != null && cliente.getOrgaoExpedidorRg().getId() != null){
							form.setIdOrgaoExpedidorRgCliente(cliente.getOrgaoExpedidorRg().getId().toString());
							form.setIdOrgaoExpedidorRgClienteInicial("S");
						}

						if(cliente.getUnidadeFederacao() != null && cliente.getUnidadeFederacao().getId() != null){
							form.setIdUnidadeFederacaoCliente(cliente.getUnidadeFederacao().getId().toString());
							form.setIdUnidadeFederacaoClienteInicial("S");
						}

						if(cliente.getCpf() != null){
							form.setNumeroCpfCliente(cliente.getCpf());
							form.setNumeroCpfClienteInicial("S");
						}else{
							String cpfCnpjCliente = (String) httpServletRequest.getSession().getAttribute("cpfClienteParcelamentoDigitado");
							if(cpfCnpjCliente != null && cpfCnpjCliente != ""){
								form.setNumeroCpfCliente(cpfCnpjCliente);
								form.setNumeroCpfClienteInicial("S");
							}
						}

						if(colecaoClienteEndereco != null){
							for(ClienteEndereco clienteEndereco : colecaoClienteEndereco){
								if(clienteEndereco.getIndicadorEnderecoCorrespondencia().intValue() == 1){
									form.setIdEnderecoCliente(String.valueOf(clienteEndereco.getId()));
									form.setIdEnderecoTipoCliente(String.valueOf(clienteEndereco.getEnderecoTipo().getId()));
									form.setIndicadorEnderecoCorrespondenciaCliente(String.valueOf(clienteEndereco
													.getIndicadorEnderecoCorrespondencia()));
									form.setDescricaoEnderecoCliente(clienteEndereco.getEnderecoFormatadoSemCep());
								}
							}
						}

						if(cliente.getNacionalidade() != null && cliente.getNacionalidade().getId() != null){
							form.setIdNacionalidadeCliente(cliente.getNacionalidade().getId().toString());
							form.setIdNacionalidadeClienteInicial("S");
						}
					}
				}
			}
		}
		
		return retorno;
	}
}
