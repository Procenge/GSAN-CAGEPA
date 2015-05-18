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
import gcom.gui.ActionServletException;
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

public class ExibirParcelamentoDadosTermoExecFiscalAction
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
		ActionForward retorno = actionMapping.findForward("informarParcelamentoDadosTermoExecFiscal");
		Fachada fachada = Fachada.getInstancia();
		ParcelamentoDadosTermoActionForm form = (ParcelamentoDadosTermoActionForm) actionForm;		

		HttpSession sessao = httpServletRequest.getSession(false);
		String atualizarDadosTermo = httpServletRequest.getParameter("atualizarDadosTermo");
		String idParcelamento = (String) sessao.getAttribute("idParcelamento");
		String idCliente = null;
		String idImovel = (String) (sessao.getAttribute("idImovel"));
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		String modificarAcordoTipoProcurador = httpServletRequest.getParameter("modificarAcordoTipoProcurador");

		String indicadorPessoaFisicaJuridica = (String) sessao.getAttribute("indicadorPessoaFisicaJuridica");

		if(indicadorPessoaFisicaJuridica.equals(ClienteTipo.INDICADOR_PESSOA_JURIDICA.toString())){
			sessao.setAttribute("informarCliente", "S");

			if(form.getIdCliente() != null && !form.getIdCliente().equals("")){
				idCliente = form.getIdCliente().toString();
			}else{
				form.setNomeCliente("");
			}

		}else{
			idCliente = String.valueOf(sessao.getAttribute("idClienteResponsavel"));
			sessao.removeAttribute("informarCliente");
		}

		boolean indicadorTermoParcelamentoPreview = false;
		if(sessao.getAttribute("TermoParcelamentoPreview") != null
						&& ((String) sessao.getAttribute("TermoParcelamentoPreview")).equals("True")){
			indicadorTermoParcelamentoPreview = true;		
		}
		
		FiltroParcelamentoAcordoTipo filtroParcelamentoAcordoTipo = new FiltroParcelamentoAcordoTipo();
		filtroParcelamentoAcordoTipo.adicionarParametro(new ParametroSimples(FiltroParcelamentoAcordoTipo.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroParcelamentoAcordoTipo.adicionarParametro(new ParametroSimples(FiltroParcelamentoAcordoTipo.INDICADOR_PARCELAMETO_NORMAL, 2));
		filtroParcelamentoAcordoTipo.setCampoOrderBy(FiltroParcelamentoAcordoTipo.DESCRICAO);

		httpServletRequest.setAttribute("colecaoAcordoTipo",
						Fachada.getInstancia().pesquisar(filtroParcelamentoAcordoTipo, ParcelamentoAcordoTipo.class.getName()));

		FiltroOrgaoExpedidorRg filtroOrgaoExpedidorRg = new FiltroOrgaoExpedidorRg();
		Collection<OrgaoExpedidorRg> colecaOrgaoExpedidorRg = fachada.pesquisar(filtroOrgaoExpedidorRg,
						OrgaoExpedidorRg.class.getName());
		sessao.setAttribute("colecaoOrgaoExpedidorRg", colecaOrgaoExpedidorRg);

		FiltroUnidadeFederacao filtroUnidadeFederacao = new FiltroUnidadeFederacao();
		Collection<UnidadeFederacao> colecaoUnidadeFederacao = fachada.pesquisar(filtroUnidadeFederacao,
						UnidadeFederacao.class.getName());
		sessao.setAttribute("colecaoUnidadeFederacao", colecaoUnidadeFederacao);

		FiltroEstadoCivil filtroEstadoCivil = new FiltroEstadoCivil();
		filtroEstadoCivil.setCampoOrderBy(FiltroEstadoCivil.DESCRICAO);
		Collection<EstadoCivil> colecaoEstadoCivil = fachada.pesquisar(filtroEstadoCivil, EstadoCivil.class.getName());
		sessao.setAttribute("colecaoEstadoCivil", colecaoEstadoCivil);

		FiltroProfissao filtroProfissao = new FiltroProfissao();
		filtroProfissao.setCampoOrderBy(FiltroProfissao.DESCRICAO);
		Collection<Profissao> colecaoProfissao = fachada.pesquisar(filtroProfissao, Profissao.class.getName());
		sessao.setAttribute("colecaoProfissao", colecaoProfissao);

		FiltroNacionalidade filtroNacionalidade = new FiltroNacionalidade();
		filtroNacionalidade.setCampoOrderBy(FiltroNacionalidade.DESCRICAO);
		Collection<Nacionalidade> colecaoNacionalidade = fachada.pesquisar(filtroNacionalidade, Nacionalidade.class.getName());
		sessao.setAttribute("colecaoNacionalidade", colecaoNacionalidade);
		
		FiltroRamoAtividade filtroRamoAtividade = new FiltroRamoAtividade();
		filtroRamoAtividade.setCampoOrderBy(FiltroRamoAtividade.DESCRICAO);
		Collection<RamoAtividade> colecaoRamoAtividade = fachada.pesquisar(filtroRamoAtividade, RamoAtividade.class.getName());
		sessao.setAttribute("colecaoRamoAtividade", colecaoRamoAtividade);

		Collection<ClienteEndereco> colecaoClienteEnderecoEmpresa = new ArrayList<ClienteEndereco>();
		if(form != null && form.getIdClienteEmpresa() != null && !form.getIdClienteEmpresa().equals("")){
			String idClienteDigitado = form.getIdClienteEmpresa();
	
			FiltroCliente filtroCliente = new FiltroCliente();
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, idClienteDigitado));
			filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.CLIENTE_TIPO);
			Collection<Cliente> clientesEmpresa = fachada.pesquisar(filtroCliente, Cliente.class.getName());
	
			Cliente clienteEmpresa = null;
			if(clientesEmpresa != null && !clientesEmpresa.isEmpty()){
				clienteEmpresa = clientesEmpresa.iterator().next();
			}
	
			if(clienteEmpresa != null){
				httpServletRequest.removeAttribute("clienteInexistente");
				
				form.setIdClienteEmpresaExistente(clienteEmpresa.getId().toString());
				form.setNomeClienteEmpresa(clienteEmpresa.getNome());
				
				if(clienteEmpresa.getRamoAtividade() != null && clienteEmpresa.getRamoAtividade().getId() != null){
					form.setIdRamoAtividadeClienteEmpresa(clienteEmpresa.getRamoAtividade().getId().toString());
					form.setIdRamoAtividadeClienteEmpresaInicial("S");
				}

				if(clienteEmpresa.getCnpj() != null){
					form.setNumeroCnpjClienteEmpresa(clienteEmpresa.getCnpj());
					form.setNumeroCnpjClienteEmpresaInicial("S");
				}

				if(!(clienteEmpresa.getClienteTipo().getIndicadorPessoaFisicaJuridica().intValue() == ClienteTipo.INDICADOR_PESSOA_JURIDICA
								.intValue())){
					form.setIdClienteEmpresaExistente("");
					form.setNomeClienteEmpresa("");
					form.setIdRamoAtividadeClienteEmpresa("");
					form.setIdRamoAtividadeClienteEmpresaInicial("N");
					form.setNumeroCnpjClienteEmpresa("");
					form.setNumeroCnpjClienteEmpresaInicial("N");

					throw new ActionServletException("atencao.cliente_tipo.parcelamento.juridica", clienteEmpresa.getId().toString(),
									clienteEmpresa
									.getClienteTipo().getDescricao());
				}

				if(idCliente != null){
					FiltroClienteEndereco filtroClienteEndereco = new FiltroClienteEndereco();
					filtroClienteEndereco.adicionarParametro(new ParametroSimples(FiltroClienteEndereco.CLIENTE_ID, idClienteDigitado));

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

					colecaoClienteEnderecoEmpresa = fachada.pesquisar(filtroClienteEndereco, ClienteEndereco.class.getName());

					if(colecaoClienteEnderecoEmpresa != null){
						for(ClienteEndereco clienteEnderecoEmpresa : colecaoClienteEnderecoEmpresa){
							if(clienteEnderecoEmpresa.getIndicadorEnderecoCorrespondencia().intValue() == 1){
								form.setIdEnderecoClienteEmpresa(String.valueOf(clienteEnderecoEmpresa.getId()));
								form.setIdEnderecoTipoClienteEmpresa(String.valueOf(clienteEnderecoEmpresa.getEnderecoTipo().getId()));
								form.setIndicadorEnderecoCorrespondenciaClienteEmpresa(String.valueOf(clienteEnderecoEmpresa
												.getIndicadorEnderecoCorrespondencia()));
								form.setDescricaoEnderecoClienteEmpresa(clienteEnderecoEmpresa.getEnderecoFormatadoSemCep());
							}
						}
					}
				}

			}else{
				form.setIdClienteEmpresaExistente("");
				form.setNomeClienteEmpresa("CLIENTE INEXISTENTE");
				form.setIdRamoAtividadeClienteEmpresa("");
				form.setIdRamoAtividadeClienteEmpresaInicial("N");
				form.setNumeroCnpjClienteEmpresa("");
				form.setNumeroCnpjClienteEmpresaInicial("N");

				httpServletRequest.setAttribute("clienteInexistente", "true");
			}
		}		
		sessao.setAttribute("colecaoClienteEnderecoEmpresa", colecaoClienteEnderecoEmpresa);

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
		}

		sessao.setAttribute("colecaoClienteEndereco", colecaoClienteEndereco);

		// Fim de Carga dos Colecoes do Form

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
			
			Integer idParcelamentoAcordoTipo = null;			
			ParcelamentoAcordoTipo parcelamentoAcordoTipo = new ParcelamentoAcordoTipo();
			if(form.getIdParcelamentoAcordoTipo() != null && !form.getIdParcelamentoAcordoTipo().equals("")){
				parcelamentoAcordoTipo.setId(Integer.valueOf(form.getIdParcelamentoAcordoTipo()));
				idParcelamentoAcordoTipo = Integer.valueOf(form.getIdParcelamentoAcordoTipo());
			}
			parcelamentoDadosTermo.setParcelamentoAcordoTipo(parcelamentoAcordoTipo);

			Integer indicadorProcurador = null; 
			if(form.getIndicadorProcurador() != null && !form.getIndicadorProcurador().equals("")){
				parcelamentoDadosTermo.setIndicadorProcurador(Integer.valueOf(form.getIndicadorProcurador()));
				indicadorProcurador = Integer.valueOf(form.getIndicadorProcurador());
			}

			Cliente cliente = new Cliente();
			if(form.getIdCliente() != null && !form.getIdCliente().equals("")){
				cliente.setId(Integer.valueOf(form.getIdCliente()));
			}
			parcelamentoDadosTermo.setCliente(cliente);

			if(form.getNomeCliente() != null && !form.getNomeCliente().equals("")){
				parcelamentoDadosTermo.setNomeCliente(form.getNomeCliente());
			}

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
			if(form.getIdUnidadeFederacaoCliente() != null && !form.getIdUnidadeFederacaoCliente().equals("")){
				unidadeFederacao.setId(Integer.valueOf(form.getIdUnidadeFederacaoCliente()));
			}
			parcelamentoDadosTermo.setUnidadeFederacaoCliente(unidadeFederacao);

			if(form.getNumeroCpfCliente() != null && !form.getNumeroCpfCliente().equals("")){
				parcelamentoDadosTermo.setNumeroCpfCliente(form.getNumeroCpfCliente());
			}

			if(form.getIdEnderecoCliente() != null && !form.getIdEnderecoCliente().equals("")){
				
				for(ClienteEndereco clienteEndereco : colecaoClienteEndereco){
					if(clienteEndereco.getId().toString().equals(form.getIdEnderecoCliente())){
						
						EnderecoTipo enderecoTipo = new EnderecoTipo();
						enderecoTipo.setId(clienteEndereco.getEnderecoTipo().getId());
						parcelamentoDadosTermo.setEnderecoTipoCliente(enderecoTipo);

						parcelamentoDadosTermo.setIndicadorEnderecoCorrespondenciaCliente(Integer.valueOf(clienteEndereco
										.getIndicadorEnderecoCorrespondencia()));

						parcelamentoDadosTermo.setDescricaoEnderecoCliente(clienteEndereco.getEnderecoFormatadoSemCep());
					}
				} 				
			}

			EstadoCivil estadoCivil = new EstadoCivil();
			if(form.getIdEstadoCivilCliente() != null && !form.getIdEstadoCivilCliente().equals("")){
				estadoCivil.setId(Integer.valueOf(form.getIdEstadoCivilCliente()));
			}
			parcelamentoDadosTermo.setEstadoCivilCliente(estadoCivil);

			Profissao profissao = new Profissao();
			if(form.getIdProfissaoCliente() != null && !form.getIdProfissaoCliente().equals("")){
				profissao.setId(Integer.valueOf(form.getIdProfissaoCliente()));
			}
			parcelamentoDadosTermo.setProfissaoCliente(profissao);
			
			Nacionalidade nacionalidade = new Nacionalidade();
			if(form.getIdNacionalidadeCliente() != null && !form.getIdNacionalidadeCliente().equals("")){
				nacionalidade.setId(Integer.valueOf(form.getIdNacionalidadeCliente()));
			}
			parcelamentoDadosTermo.setNacionalidadeCliente(nacionalidade);

			switch(idParcelamentoAcordoTipo){
				case 5:
					if(form.getNumeroProcesso() != null && !form.getNumeroProcesso().equals("")){
						parcelamentoDadosTermo.setNumeroProcesso(form.getNumeroProcesso());
					}

					if(form.getNumeroVara() != null && !form.getNumeroVara().equals("")){
						parcelamentoDadosTermo.setNumeroVara(Integer.valueOf(form.getNumeroVara()));
					}

					break;

				default:
					parcelamentoDadosTermo.setNumeroProcesso(null);
					parcelamentoDadosTermo.setNumeroVara(null);

					break;
			}

			switch(idParcelamentoAcordoTipo){
				case 5:
				case 6:
				case 10:
				case 11:
					if(form.getNomeExecutado() != null && !form.getNomeExecutado().equals("")){
						parcelamentoDadosTermo.setNomeExecutado(form.getNomeExecutado());
					}

					break;

				default:
					parcelamentoDadosTermo.setNomeExecutado(null);

					break;
			}

			switch(idParcelamentoAcordoTipo){
				case 7:

					if(form.getTituloPosse() != null && !form.getTituloPosse().equals("")){
						parcelamentoDadosTermo.setTituloPosse(form.getTituloPosse());
					}

					break;

				default:
					parcelamentoDadosTermo.setTituloPosse(null);

					break;
			}

			// Dados da Empresa
			Cliente clienteEmpresa = new Cliente();
			RamoAtividade ramoAtividadeEmpresa = new RamoAtividade();
			switch(idParcelamentoAcordoTipo){
				case 8:

					if(form.getIdClienteEmpresaExistente() != null && !form.getIdClienteEmpresaExistente().equals("")){
						clienteEmpresa.setId(Integer.valueOf(form.getIdClienteEmpresaExistente()));
					}

					if(form.getNomeClienteEmpresa() != null && !form.getNomeClienteEmpresa().equals("")){
						parcelamentoDadosTermo.setNomeClienteEmpresa(form.getNomeClienteEmpresa());
					}

					if(form.getIdRamoAtividadeClienteEmpresa() != null && !form.getIdRamoAtividadeClienteEmpresa().equals("")){
						ramoAtividadeEmpresa.setId(Integer.valueOf(form.getIdRamoAtividadeClienteEmpresa()));
					}

					if(form.getNumeroCnpjClienteEmpresa() != null && !form.getNumeroCnpjClienteEmpresa().equals("")){
						parcelamentoDadosTermo.setNumeroCnpjClienteEmpresa(form.getNumeroCnpjClienteEmpresa());
					}

					if(form.getIdEnderecoClienteEmpresa() != null && !form.getIdEnderecoClienteEmpresa().equals("")){

						for(ClienteEndereco clienteEnderecoEmpresa : colecaoClienteEnderecoEmpresa){
							if(clienteEnderecoEmpresa.getId().toString().equals(form.getIdEnderecoClienteEmpresa())){

								EnderecoTipo enderecoTipo = new EnderecoTipo();
								enderecoTipo.setId(clienteEnderecoEmpresa.getEnderecoTipo().getId());
								parcelamentoDadosTermo.setEnderecoTipoClienteEmpresa(enderecoTipo);

								parcelamentoDadosTermo.setIndicadorEnderecoCorrespondenciaClienteEmpresa(Integer
												.valueOf(clienteEnderecoEmpresa
												.getIndicadorEnderecoCorrespondencia()));

								parcelamentoDadosTermo.setDescricaoEnderecoClienteEmpresa(clienteEnderecoEmpresa
												.getEnderecoFormatadoSemCep());
							}
						}
					}					

					break;

				default:
					parcelamentoDadosTermo.setNomeClienteEmpresa(null);
					parcelamentoDadosTermo.setNumeroCnpjClienteEmpresa(null);
					parcelamentoDadosTermo.setEnderecoTipoClienteEmpresa(null);
					parcelamentoDadosTermo.setIndicadorEnderecoCorrespondenciaClienteEmpresa(null);
					parcelamentoDadosTermo.setDescricaoEnderecoClienteEmpresa(null);

					break;
			}
			parcelamentoDadosTermo.setClienteEmpresa(clienteEmpresa);
			parcelamentoDadosTermo.setRamoAtividadeClienteEmpresa(ramoAtividadeEmpresa);

			// Dados Procurador
			switch(indicadorProcurador){
				case 1:
					if(form.getNomeProcurador() != null && !form.getNomeProcurador().equals("")){
						parcelamentoDadosTermo.setNomeProcurador(form.getNomeProcurador());
					}
					
					if(form.getNumeroRgProcurador() != null && !form.getNumeroRgProcurador().equals("")){
						parcelamentoDadosTermo.setNumeroRgProcurador(form.getNumeroRgProcurador());
					}

					OrgaoExpedidorRg orgaoExpedidorRgProcurador = new OrgaoExpedidorRg();
					if(form.getIdOrgaoExpedidorRgProcurador() != null && !form.getIdOrgaoExpedidorRgProcurador().equals("")){
						orgaoExpedidorRgProcurador.setId(Integer.valueOf(form.getIdOrgaoExpedidorRgProcurador()));
					}
					parcelamentoDadosTermo.setOrgaoExpedidorRgProcurador(orgaoExpedidorRgProcurador);

					UnidadeFederacao unidadeFederacaoProcurador = new UnidadeFederacao();
					if(form.getIdUnidadeFederacaoProcurador() != null && !form.getIdUnidadeFederacaoProcurador().equals("")){
						unidadeFederacaoProcurador.setId(Integer.valueOf(form.getIdUnidadeFederacaoProcurador()));
					}
					parcelamentoDadosTermo.setUnidadeFederacaoProcurador(unidadeFederacaoProcurador);

					if(form.getNumeroCpfProcurador() != null && !form.getNumeroCpfProcurador().equals("")){
						parcelamentoDadosTermo.setNumeroCpfProcurador(form.getNumeroCpfProcurador());
					}

					if(form.getDescricaoEnderecoProcurador() != null && !form.getDescricaoEnderecoProcurador().equals("")){
						parcelamentoDadosTermo.setDescricaoEnderecoProcurador(form.getDescricaoEnderecoProcurador());
					}

					EstadoCivil estadoCivilProcurador = new EstadoCivil();
					if(form.getIdEstadoCivilProcurador() != null && !form.getIdEstadoCivilProcurador().equals("")){
						estadoCivilProcurador.setId(Integer.valueOf(form.getIdEstadoCivilProcurador()));
					}
					parcelamentoDadosTermo.setEstadoCivilProcurador(estadoCivilProcurador);

					Profissao profissaoProcurador = new Profissao();
					if(form.getIdProfissaoProcurador() != null && !form.getIdProfissaoProcurador().equals("")){
						profissaoProcurador.setId(Integer.valueOf(form.getIdProfissaoProcurador()));
					}
					parcelamentoDadosTermo.setProfissaoProcurador(profissaoProcurador);

					Nacionalidade nacionalidadeProcurador = new Nacionalidade();
					if(form.getIdNacionalidadeProcurador() != null && !form.getIdNacionalidadeProcurador().equals("")){
						nacionalidadeProcurador.setId(Integer.valueOf(form.getIdNacionalidadeProcurador()));
					}
					parcelamentoDadosTermo.setNacionalidadeProcurador(nacionalidadeProcurador);

					break;

				default:
					parcelamentoDadosTermo.setNomeProcurador(null);
					parcelamentoDadosTermo.setNumeroRgProcurador(null);
					parcelamentoDadosTermo.setOrgaoExpedidorRgProcurador(null);
					parcelamentoDadosTermo.setUnidadeFederacaoProcurador(null);
					parcelamentoDadosTermo.setNumeroCpfProcurador(null);
					parcelamentoDadosTermo.setDescricaoEnderecoProcurador(null);
					parcelamentoDadosTermo.setEstadoCivilProcurador(null);
					parcelamentoDadosTermo.setProfissaoProcurador(null);
					parcelamentoDadosTermo.setNacionalidadeProcurador(null);

					break;
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
			retorno = actionMapping.findForward("atualizarParcelamentoDadosTermoExecFiscalAction");

		}else{

			if(!Util.isVazioOuBranco(modificarAcordoTipoProcurador) && modificarAcordoTipoProcurador.equals("sim")){
				Integer idParcelamentoAcordoTipo = null;
				if(form.getIdParcelamentoAcordoTipo() != null && !form.getIdParcelamentoAcordoTipo().equals("")){
					idParcelamentoAcordoTipo = Integer.valueOf(form.getIdParcelamentoAcordoTipo());
				}

				if(form.getIndicadorProcurador() != null && form.getIndicadorProcurador().equals("2")){
					form.setNomeProcurador("");
					form.setNumeroRgProcurador("");
					form.setIdOrgaoExpedidorRgProcurador("");
					form.setIdUnidadeFederacaoProcurador("");
					form.setNumeroCpfProcurador("");
					form.setDescricaoEnderecoProcurador("");
					form.setIdEstadoCivilProcurador("");
					form.setIdProfissaoProcurador("");
					form.setIdNacionalidadeProcurador("");
				}

				if(idParcelamentoAcordoTipo != null){
					switch(idParcelamentoAcordoTipo){
						case 1:
							form.setVisivelNacionalidadeCliente("S");
							form.setVisivelNumeroProcesso("N");
							form.setVisivelNumeroVara("N");
							form.setVisivelNomeExecutado("N");
							form.setVisivelTituloPosse("N");
							form.setVisivelDadosEmpresa("N");							
							
							break;
						case 5:
							form.setVisivelNacionalidadeCliente("S");
							form.setVisivelNumeroProcesso("S");
							form.setVisivelNumeroVara("S");
							form.setVisivelNomeExecutado("S");
							form.setVisivelTituloPosse("N");
							form.setVisivelDadosEmpresa("N");

							break;

						case 6:
						case 10:
						case 11:
							form.setVisivelNacionalidadeCliente("S");
							form.setVisivelNumeroProcesso("N");
							form.setVisivelNumeroVara("N");
							form.setVisivelNomeExecutado("S");
							form.setVisivelTituloPosse("N");
							form.setVisivelDadosEmpresa("N");

							break;

						case 7:
							form.setVisivelNacionalidadeCliente("S");
							form.setVisivelNumeroProcesso("N");
							form.setVisivelNumeroVara("N");
							form.setVisivelNomeExecutado("N");
							form.setVisivelTituloPosse("S");
							form.setVisivelDadosEmpresa("N");

							break;

						case 8:
							form.setVisivelNacionalidadeCliente("S");
							form.setVisivelNumeroProcesso("N");
							form.setVisivelNumeroVara("N");
							form.setVisivelNomeExecutado("N");
							form.setVisivelTituloPosse("N");
							form.setVisivelDadosEmpresa("S");

							break;

						default:
							form.setVisivelNacionalidadeCliente("S");
							form.setVisivelNumeroProcesso("N");
							form.setVisivelNumeroVara("N");
							form.setVisivelNomeExecutado("N");
							form.setVisivelTituloPosse("N");
							form.setVisivelDadosEmpresa("N");
							
							break;
					}

				}
			}else{
				// Primeira vez que entra na tela
				form.setVisivelNacionalidadeCliente("S");

				FiltroParcelamentoDadosTermo filtro = new FiltroParcelamentoDadosTermo();
				filtro.adicionarParametro(new ParametroSimples(FiltroParcelamentoDadosTermo.PARCELAMENTO_ID, idParcelamento));
				Collection<ParcelamentoDadosTermo> colecao = fachada.pesquisar(filtro, ParcelamentoDadosTermo.class.getName());

				ParcelamentoDadosTermo parcelamentoDadosTermo = null;
				if(!Util.isVazioOrNulo(colecao)){
					parcelamentoDadosTermo = colecao.iterator().next();
				}else{
					parcelamentoDadosTermo = (ParcelamentoDadosTermo) sessao.getAttribute("parcelamentoDadosTermo");
				}

				String pesquisaClienteParametro = httpServletRequest.getParameter("pesquisaCliente");

				// 1. Caso existam dados do parcelamento
				if(parcelamentoDadosTermo != null){

					form.setId("");
					if(parcelamentoDadosTermo.getId() != null){
						form.setId(parcelamentoDadosTermo.getId().toString());
					} 
					
					form.setIdParcelamento("");
					if(parcelamentoDadosTermo.getParcelamento() != null && parcelamentoDadosTermo.getParcelamento().getId() != null){
						form.setIdParcelamento(parcelamentoDadosTermo.getParcelamento().getId().toString());
					} 

					form.setIdImovel("");
					if(idImovel != null && !idImovel.equals("")){
						form.setIdImovel(idImovel);
					}

					form.setIdParcelamentoAcordoTipo("");
					if(parcelamentoDadosTermo.getParcelamentoAcordoTipo() != null
									&& parcelamentoDadosTermo.getParcelamentoAcordoTipo().getId() != null){
						form.setIdParcelamentoAcordoTipo(String.valueOf(parcelamentoDadosTermo.getParcelamentoAcordoTipo().getId()));
					} 

					form.setIndicadorProcurador("2");
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

						cliente = (Cliente) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroCliente,
										Cliente.class.getName()));
					}

					form.setNomeCliente("");
					if(parcelamentoDadosTermo.getNomeCliente() != null){
						form.setNomeCliente(parcelamentoDadosTermo.getNomeCliente());
					} 

					form.setNumeroRgCliente("");
					form.setNumeroRgClienteInicial("");					
					if(parcelamentoDadosTermo.getNumeroRgCliente() != null){
						form.setNumeroRgCliente(parcelamentoDadosTermo.getNumeroRgCliente());

						if(cliente != null && cliente.getRg() != null
										&& cliente.getRg().equals(parcelamentoDadosTermo.getNumeroRgCliente())){
							form.setNumeroRgClienteInicial("S");
						}
					} 

					form.setIdOrgaoExpedidorRgCliente("");
					form.setIdOrgaoExpedidorRgClienteInicial("");					
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

					form.setIdUnidadeFederacaoCliente("");
					form.setIdUnidadeFederacaoClienteInicial("");					
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

					form.setNumeroCpfCliente("");
					form.setNumeroCpfClienteInicial("");					
					if(parcelamentoDadosTermo.getNumeroCpfCliente() != null){
						form.setNumeroCpfCliente(parcelamentoDadosTermo.getNumeroCpfCliente());

						if(cliente != null && cliente.getCpf() != null
										&& cliente.getCpf().equals(parcelamentoDadosTermo.getNumeroCpfCliente())){
							form.setNumeroCpfClienteInicial("S");
						}else{
							String cpfCnpjCliente = (String) httpServletRequest.getSession().getAttribute("cpfClienteParcelamentoDigitado");
							if(cpfCnpjCliente != null && cpfCnpjCliente.equals(parcelamentoDadosTermo.getNumeroCpfCliente())){
								form.setNumeroCpfClienteInicial("S");
							}
						}
					} 

					form.setIdEnderecoCliente("");
					form.setIdEnderecoTipoCliente("");
					form.setIndicadorEnderecoCorrespondenciaCliente("");
					form.setDescricaoEnderecoCliente("");					
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

								form.setIdEnderecoCliente(clienteEndereco.getId().toString());
								form.setIdEnderecoTipoCliente(clienteEndereco.getEnderecoTipo().getId().toString());
								form.setIndicadorEnderecoCorrespondenciaCliente(clienteEndereco.getIndicadorEnderecoCorrespondencia()
												.toString());
								form.setDescricaoEnderecoCliente(clienteEndereco.getEnderecoFormatadoSemCep());
							}
						}
					}

					form.setIdEstadoCivilCliente("");
					form.setIdEstadoCivilClienteInicial("");					
					if(parcelamentoDadosTermo.getEstadoCivilCliente() != null
									&& parcelamentoDadosTermo.getEstadoCivilCliente().getId() != null){
						form.setIdEstadoCivilCliente(parcelamentoDadosTermo.getEstadoCivilCliente().getId().toString());

						if(cliente != null
										&& cliente.getEstadoCivil() != null
										&& cliente.getEstadoCivil().getId().toString()
														.equals(parcelamentoDadosTermo.getEstadoCivilCliente().getId().toString())){
							form.setIdEstadoCivilClienteInicial("S");
						}
					} 

					form.setIdProfissaoCliente("");
					form.setIdProfissaoClienteInicial("");					
					if(parcelamentoDadosTermo.getProfissaoCliente() != null && parcelamentoDadosTermo.getProfissaoCliente().getId() != null){
						form.setIdProfissaoCliente(parcelamentoDadosTermo.getProfissaoCliente().getId().toString());

						if(cliente != null
										&& cliente.getProfissao() != null
										&& cliente.getProfissao().getId().toString()
														.equals(parcelamentoDadosTermo.getProfissaoCliente().getId().toString())){
							form.setIdProfissaoClienteInicial("S");
						}
					}

					form.setIdNacionalidadeCliente("");
					form.setIdNacionalidadeClienteInicial("");					
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

					form.setNumeroProcesso("");
					if(parcelamentoDadosTermo.getNumeroProcesso() != null){
						form.setNumeroProcesso(parcelamentoDadosTermo.getNumeroProcesso());
					} 

					form.setNumeroVara("");
					if(parcelamentoDadosTermo.getNumeroVara() != null){
						form.setNumeroVara(parcelamentoDadosTermo.getNumeroVara().toString());
					} 

					form.setNomeExecutado("");
					if(parcelamentoDadosTermo.getNomeExecutado() != null){
						form.setNomeExecutado(parcelamentoDadosTermo.getNomeExecutado());
					} 

					form.setTituloPosse("");
					if(parcelamentoDadosTermo.getTituloPosse() != null){
						form.setTituloPosse(parcelamentoDadosTermo.getTituloPosse());
					} 
					
					Cliente clienteEmpresa = null;
					if(parcelamentoDadosTermo.getClienteEmpresa() != null && parcelamentoDadosTermo.getClienteEmpresa().getId() != null){
						form.setIdClienteEmpresa(parcelamentoDadosTermo.getClienteEmpresa().getId().toString());
						form.setIdClienteEmpresaExistente(parcelamentoDadosTermo.getClienteEmpresa().getId().toString());

						FiltroCliente filtroCliente = new FiltroCliente();

						filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, parcelamentoDadosTermo.getClienteEmpresa()
										.getId()));
						filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.RAMO_ATIVIDADE );

						clienteEmpresa = (Cliente) Util.retonarObjetoDeColecao(Fachada.getInstancia().pesquisar(filtroCliente,
										Cliente.class.getName()));
					}
					
					form.setNomeClienteEmpresa("");
					if(parcelamentoDadosTermo.getNomeClienteEmpresa() != null){
						form.setNomeClienteEmpresa(parcelamentoDadosTermo.getNomeClienteEmpresa());
					} 

					form.setIdRamoAtividadeClienteEmpresa("");
					form.setIdRamoAtividadeClienteEmpresaInicial("");				
					if(parcelamentoDadosTermo.getRamoAtividadeClienteEmpresa() != null
									&& parcelamentoDadosTermo.getRamoAtividadeClienteEmpresa().getId() != null){
						form.setIdRamoAtividadeClienteEmpresa(parcelamentoDadosTermo.getRamoAtividadeClienteEmpresa().getId().toString());
						
						if(clienteEmpresa != null
										&& clienteEmpresa.getRamoAtividade() != null
										&& clienteEmpresa.getRamoAtividade().getId() != null
										&& clienteEmpresa.getRamoAtividade().getId().toString()
														.equals(parcelamentoDadosTermo.getRamoAtividadeClienteEmpresa().getId())){
							form.setIdRamoAtividadeClienteEmpresaInicial("S");
						}

					} 


					form.setNumeroCnpjClienteEmpresa("");
					form.setNumeroCnpjClienteEmpresaInicial("");
					if(parcelamentoDadosTermo.getNumeroCnpjClienteEmpresa() != null){
						form.setNumeroCnpjClienteEmpresa(parcelamentoDadosTermo.getNumeroCnpjClienteEmpresa());

						if(clienteEmpresa != null && clienteEmpresa.getCnpj() != null
										&& clienteEmpresa.getCnpj().equals(parcelamentoDadosTermo.getNumeroCnpjClienteEmpresa())){
							form.setNumeroCnpjClienteEmpresaInicial("S");
						}
					} 

					form.setIdEnderecoClienteEmpresa("");
					form.setIdEnderecoTipoClienteEmpresa("");
					form.setIndicadorEnderecoCorrespondenciaClienteEmpresa("");
					form.setDescricaoEnderecoClienteEmpresa("");					
					if(clienteEmpresa != null && parcelamentoDadosTermo.getEnderecoTipoClienteEmpresa() != null
									&& parcelamentoDadosTermo.getEnderecoTipoClienteEmpresa().getId() != null
									&& parcelamentoDadosTermo.getDescricaoEnderecoClienteEmpresa() != null){

						for(ClienteEndereco clienteEnderecoEmpresa : colecaoClienteEnderecoEmpresa){
							if(clienteEnderecoEmpresa.getEnderecoFormatadoSemCep() != null
											&& clienteEnderecoEmpresa.getEnderecoFormatadoSemCep().equals(
															parcelamentoDadosTermo.getDescricaoEnderecoClienteEmpresa())
											&& clienteEnderecoEmpresa.getEnderecoTipo() != null
											&& clienteEnderecoEmpresa.getEnderecoTipo().getId() != null
											&& clienteEnderecoEmpresa.getEnderecoTipo().getId()
															.equals(parcelamentoDadosTermo.getEnderecoTipoClienteEmpresa().getId())){

								form.setIdEnderecoClienteEmpresa(clienteEnderecoEmpresa.getId().toString());
								form.setIdEnderecoTipoClienteEmpresa(clienteEnderecoEmpresa.getEnderecoTipo().getId().toString());
								form.setIndicadorEnderecoCorrespondenciaClienteEmpresa(clienteEnderecoEmpresa
												.getIndicadorEnderecoCorrespondencia()
												.toString());
								form.setDescricaoEnderecoClienteEmpresa(clienteEnderecoEmpresa.getEnderecoFormatadoSemCep());
							}
						}
					}

					form.setNomeProcurador("");
					if(parcelamentoDadosTermo.getNomeProcurador() != null){
						form.setNomeProcurador(parcelamentoDadosTermo.getNomeProcurador());
					}
					
					form.setNumeroRgProcurador("");
					if(parcelamentoDadosTermo.getNumeroRgProcurador() != null){
						form.setNumeroRgProcurador(parcelamentoDadosTermo.getNumeroRgProcurador());
					}
					
					form.setIdOrgaoExpedidorRgProcurador("");
					if(parcelamentoDadosTermo.getOrgaoExpedidorRgProcurador() != null
									&& parcelamentoDadosTermo.getOrgaoExpedidorRgProcurador().getId() != null){
						form.setIdOrgaoExpedidorRgProcurador(parcelamentoDadosTermo.getOrgaoExpedidorRgProcurador().getId().toString());
					}

					form.setIdUnidadeFederacaoProcurador("");
					if(parcelamentoDadosTermo.getUnidadeFederacaoProcurador() != null
									&& parcelamentoDadosTermo.getUnidadeFederacaoProcurador().getId() != null){
						form.setIdUnidadeFederacaoProcurador(parcelamentoDadosTermo.getUnidadeFederacaoProcurador().getId().toString());
					}

					form.setNumeroCpfProcurador("");
					if(parcelamentoDadosTermo.getNumeroCpfProcurador() != null){
						form.setNumeroCpfProcurador(parcelamentoDadosTermo.getNumeroCpfProcurador());
					}

					form.setDescricaoEnderecoProcurador("");
					if(parcelamentoDadosTermo.getDescricaoEnderecoProcurador() != null){
						form.setDescricaoEnderecoProcurador(parcelamentoDadosTermo.getDescricaoEnderecoProcurador());
					}

					form.setIdEstadoCivilProcurador("");
					if(parcelamentoDadosTermo.getEstadoCivilProcurador() != null
									&& parcelamentoDadosTermo.getEstadoCivilProcurador().getId() != null){
						form.setIdEstadoCivilProcurador(parcelamentoDadosTermo.getEstadoCivilProcurador().getId().toString());
					}

					form.setIdProfissaoProcurador("");
					if(parcelamentoDadosTermo.getProfissaoProcurador() != null
									&& parcelamentoDadosTermo.getProfissaoProcurador().getId() != null){
						form.setIdProfissaoProcurador(parcelamentoDadosTermo.getProfissaoProcurador().getId().toString());
					}

					form.setIdNacionalidadeProcurador("");
					if(parcelamentoDadosTermo.getNacionalidadeProcurador() != null
									&& parcelamentoDadosTermo.getNacionalidadeProcurador().getId() != null){
						form.setIdNacionalidadeProcurador(parcelamentoDadosTermo.getNacionalidadeProcurador().getId().toString());
					}


				}else{

					// Caso contrário, ou seja, não existam dados do parcelamento:
					// 1.1. Tipo de Acordo do Parcelamento (obrigatório)
					if(pesquisaClienteParametro == null || pesquisaClienteParametro.equals("")){
						form.setIndicadorProcurador("2");
						form.setIdParcelamentoAcordoTipo("");
						form.setNumeroCpfCliente("");
						form.setNumeroCpfClienteInicial("");
						form.setIdEnderecoCliente("");
						form.setIdEnderecoTipoCliente("");
						form.setIndicadorEnderecoCorrespondenciaCliente("");
						form.setDescricaoEnderecoCliente("");
						form.setIdEstadoCivilCliente("");
						form.setIdEstadoCivilClienteInicial("");
						form.setIdProfissaoCliente("");
						form.setIdProfissaoClienteInicial("");
						form.setIdNacionalidadeCliente("");
						form.setIdNacionalidadeClienteInicial("");
						form.setNumeroProcesso("");
						form.setNumeroVara("");
						form.setNomeExecutado("");
						form.setTituloPosse("");
						form.setIdClienteEmpresa("");
						form.setIdClienteEmpresaExistente("");
						form.setNomeClienteEmpresa("");
						form.setIdRamoAtividadeClienteEmpresa("");
						form.setIdRamoAtividadeClienteEmpresaInicial("");
						form.setNumeroCnpjClienteEmpresa("");
						form.setNumeroCnpjClienteEmpresaInicial("");
						form.setIdEnderecoClienteEmpresa("");
						form.setIdEnderecoTipoClienteEmpresa("");
						form.setIndicadorEnderecoCorrespondenciaClienteEmpresa("");
						form.setDescricaoEnderecoClienteEmpresa("");
						form.setNomeProcurador("");
						form.setNumeroRgProcurador("");
						form.setIdOrgaoExpedidorRgProcurador("");
						form.setIdUnidadeFederacaoProcurador("");
						form.setNumeroCpfProcurador("");
						form.setDescricaoEnderecoProcurador("");
						form.setIdEstadoCivilCliente("");
						form.setIdProfissaoProcurador("");
						form.setIdNacionalidadeProcurador("");
						form.setIdImovel("");
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
								String cpfCnpjCliente = (String) httpServletRequest.getSession().getAttribute(
												"cpfClienteParcelamentoDigitado");
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

							if(cliente.getEstadoCivil() != null && cliente.getEstadoCivil().getId() != null){
								form.setIdEstadoCivilCliente(cliente.getEstadoCivil().getId().toString());
								form.setIdEstadoCivilClienteInicial("S");
							}

							if(cliente.getProfissao() != null && cliente.getProfissao().getId() != null){
								form.setIdProfissaoCliente(cliente.getProfissao().getId().toString());
								form.setIdProfissaoClienteInicial("S");
							}

							if(cliente.getNacionalidade() != null && cliente.getNacionalidade().getId() != null){
								form.setIdNacionalidadeCliente(cliente.getNacionalidade().getId().toString());
								form.setIdNacionalidadeClienteInicial("S");
							}

						}
					}
				}
			}
		}
		
		return retorno;
	}


}
