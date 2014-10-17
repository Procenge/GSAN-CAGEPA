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
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.gui.cadastro.cliente;

import gcom.arrecadacao.banco.Agencia;
import gcom.arrecadacao.banco.FiltroAgencia;
import gcom.cadastro.cliente.*;
import gcom.cadastro.geografico.UnidadeFederacao;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.ParametroNaoInformadoException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import br.com.procenge.parametrosistema.api.ParametroSistema;

/**
 * Description of the Class
 * 
 * @author Rodrigo
 */
public class AtualizarClienteAction
				extends GcomAction {

	/**
	 * Description of the Method
	 * 
	 * @author Eduardo Henrique
	 * @date 04/06/2008
	 *       Alteração para atualizar dados da Inscrição Estadual para Clientes tipo PJ
	 * @author Saulo Lima
	 * @date 17/03/2009
	 *       Permitir que seja informado um CNPJ já cadastrado desde que seja igual ao do cliente
	 *       responsável superior
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return Description of the Return Value
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping.findForward("telaSucesso");
		HttpSession sessao = httpServletRequest.getSession(false);

		// Pega o form do cliente
		DynaValidatorForm clienteActionForm = (DynaValidatorForm) actionForm;

		Short indicadorUso = null;

		if(clienteActionForm.get("indicadorUso") != null){

			indicadorUso = new Short((String) clienteActionForm.get("indicadorUso"));

		}else{

			indicadorUso = new Short("1");

		}

		Boolean validarDadosCliente = this.validarDadosCliente(indicadorUso);

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		Fachada fachada = Fachada.getInstancia();

		boolean usuarioPermissaoEspecial = fachada.verificarPermissaoEspecial(PermissaoEspecial.REMOVER_CPF_CLIENTE, usuario);

		Short tipoPessoa = (Short) clienteActionForm.get("tipoPessoa");

		String indicadorContaBraille = (String) clienteActionForm.get("indicadorContaBraille");

		String tipoPessoaForm = tipoPessoa.toString();

		FiltroClienteTipo filtroClienteTipo = new FiltroClienteTipo();

		filtroClienteTipo.adicionarParametro(new ParametroSimples(FiltroClienteTipo.ID, tipoPessoaForm));
		tipoPessoa = ((ClienteTipo) fachada.pesquisar(filtroClienteTipo, ClienteTipo.class.getName()).iterator().next())
						.getIndicadorPessoaFisicaJuridica();

		Integer clienteTipoEspecial = (Integer) clienteActionForm.get("clienteTipoEspecial");

		ClienteTipoEspecial clienteTE = null;

		if(clienteTipoEspecial != null){

			String clienteTipoEspecialForm = clienteTipoEspecial.toString();

			FiltroClienteTipoEspecial filtroClienteTipoEspecial = new FiltroClienteTipoEspecial();

			filtroClienteTipoEspecial.adicionarParametro(new ParametroSimples(FiltroClienteTipoEspecial.ID, clienteTipoEspecialForm));

			Collection colClienteTE = fachada.pesquisar(filtroClienteTipoEspecial, ClienteTipoEspecial.class.getName());

			Iterator itClienteTE = colClienteTE.iterator();

			if(itClienteTE.hasNext()){
				clienteTE = (ClienteTipoEspecial) itClienteTE.next();
			}
		}

		// Pega o cliente que foi selecionado para atualização
		Cliente clienteAtualizacao = (Cliente) sessao.getAttribute("clienteAtualizacao");

		// Verifica o destino porque se o usuário tentar concluir o processo
		// nesta página, não é necessário verificar a tela de confirmação
		// if (destinoPagina != null && !destinoPagina.trim().equals("")) {
		String cnpj = (String) clienteActionForm.get("cnpj");

		ClienteTipo clienteTipo = new ClienteTipo();
		clienteTipo.setId(new Integer(((Short) clienteActionForm.get("tipoPessoa")).intValue()));

		clienteTipo = (ClienteTipo) Util
						.retonarObjetoDeColecao(fachada.pesquisar(clienteTipo.retornaFiltro(), ClienteTipo.class.getName()));

		if(clienteTipo == null){

			if(validarDadosCliente){

				throw new ActionServletException("atencao.pesquisa_inexistente", null, "Tipo do Cliente");

			}

		}

		if(tipoPessoa != null && tipoPessoa.equals(ClienteTipo.INDICADOR_PESSOA_JURIDICA)){
			// Vai para Pessoa Juridica mas tem dados existentes em pessoa fisica
			String cpf = (String) clienteActionForm.get("cpf");
			String rg = (String) clienteActionForm.get("rg");
			String dataEmissao = (String) clienteActionForm.get("dataEmissao");
			Integer idOrgaoExpedidor = (Integer) clienteActionForm.get("idOrgaoExpedidor");
			String dataNascimento = (String) clienteActionForm.get("dataNascimento");
			Integer idProfissao = (Integer) clienteActionForm.get("idProfissao");
			Integer idPessoaSexo = (Integer) clienteActionForm.get("idPessoaSexo");

			if((idPessoaSexo != null && idPessoaSexo != ConstantesSistema.NUMERO_NAO_INFORMADO)
							|| (cpf != null && !cpf.trim().equalsIgnoreCase("")) || (rg != null && !rg.trim().equalsIgnoreCase(""))
							|| (dataEmissao != null && !dataEmissao.trim().equalsIgnoreCase(""))
							|| (dataNascimento != null && !dataNascimento.trim().equalsIgnoreCase(""))
							|| (idOrgaoExpedidor != null && idOrgaoExpedidor != ConstantesSistema.NUMERO_NAO_INFORMADO)
							|| (idProfissao != null && idProfissao != ConstantesSistema.NUMERO_NAO_INFORMADO)){

				// Limpar todo o conteúdo da página de pessoa física
				clienteActionForm.set("cpf", null);
				clienteActionForm.set("rg", null);
				clienteActionForm.set("dataEmissao", null);
				clienteActionForm.set("idOrgaoExpedidor", new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO));
				clienteActionForm.set("dataNascimento", null);
				clienteActionForm.set("idProfissao", new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO));
				clienteActionForm.set("idPessoaSexo", new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO));
			}

			// Valida se o CNPJ ainda foi informado para o cliente PJ
			// Eduardo Henrique
			if(cnpj == null || cnpj.trim().equalsIgnoreCase("")){ // O CNPJ é obrigatório caso o

				if(validarDadosCliente){

					if(!usuarioPermissaoEspecial){
					// tipo de pessoa seja jurídica
					throw new ActionServletException("atencao.informe_campo",
									"atualizarClienteWizardAction.do?destino=2&action=atualizarClienteNomeTipoAction",
									new ParametroNaoInformadoException("CNPJ"), "CNPJ");
					}
				}

			}else{

				// Se o cpf Informado for diferente do cpf cadastrado
				if(clienteAtualizacao != null && cnpj != null && !cnpj.equals(clienteAtualizacao.getCnpj())){
					// Verifica a existencia de cpf ja cadastrado

					this.existeCnpf(cnpj, fachada, clienteTipo);
				}
			}

		}else if(tipoPessoa != null && tipoPessoa.equals(ClienteTipo.INDICADOR_PESSOA_FISICA)){
			// Vai para Pessoa Fisica mas tem dados existentes em pessoa juridica

			Integer idRamoAtividade = (Integer) clienteActionForm.get("idRamoAtividade");
			String codigoClienteResponsavel = (String) clienteActionForm.get("codigoClienteResponsavel");

			if((cnpj != null && !cnpj.trim().equalsIgnoreCase(""))
							|| (idRamoAtividade != null && idRamoAtividade != ConstantesSistema.NUMERO_NAO_INFORMADO)
							|| (codigoClienteResponsavel != null && !codigoClienteResponsavel.trim().equalsIgnoreCase(""))){
				// Limpa os dados da página de pessoa jurídica
				clienteActionForm.set("cnpj", null);
				clienteActionForm.set("inscricaoEstadual", null);
				clienteActionForm.set("idRamoAtividade", new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO));
				clienteActionForm.set("codigoClienteResponsavel", null);
				clienteActionForm.set("nomeClienteResponsavel", null);
			}

			this.validarObrigatoriedadeCampos(httpServletRequest, clienteActionForm);

		}

		// Pega a coleção de endereços do cliente
		Collection<ClienteEndereco> colecaoEnderecos = (Collection) sessao.getAttribute("colecaoEnderecos");

		// Pega a coleção de telefones do cliente
		Collection<ClienteFone> colecaoFones = (Collection) sessao.getAttribute("colecaoClienteFone");

		// Cria o objeto do cliente para ser inserido
		String nome = (String) clienteActionForm.get("nome");
		String nomeAbreviado = (String) clienteActionForm.get("nomeAbreviado");
		String rg = (String) clienteActionForm.get("rg");
		String cpf = (String) clienteActionForm.get("cpf");
		if(cpf != null && cpf.trim().equals("")){
			cpf = null;
		}
		String dataEmissao = (String) clienteActionForm.get("dataEmissao");
		String dataNascimento = (String) clienteActionForm.get("dataNascimento");

		if(cnpj != null && cnpj.trim().equals("")){
			cnpj = null;
		}
		String inscricaoEstadual = (String) clienteActionForm.get("inscricaoEstadual");
		if(inscricaoEstadual != null && inscricaoEstadual.trim().equals("")){
			inscricaoEstadual = null;
		}

		String email = (String) clienteActionForm.get("email");

		// Verificar se o usuário digitou os 4 campos relacionados com o RG de
		// pessoa física ou se ele não digitou nenhum

		Integer idOrgaoExpedidor = (Integer) clienteActionForm.get("idOrgaoExpedidor");
		Integer idUnidadeFederacao = (Integer) clienteActionForm.get("idUnidadeFederacao");

		if((tipoPessoa != null && tipoPessoa.equals(ClienteTipo.INDICADOR_PESSOA_FISICA))
						&& !(((rg != null && !rg.trim().equalsIgnoreCase("")) && (idOrgaoExpedidor != null && !idOrgaoExpedidor
										.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)))
										&& (idUnidadeFederacao != null && !idUnidadeFederacao
														.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)) || ((rg != null && rg.trim()
										.equalsIgnoreCase(""))
										&& (idOrgaoExpedidor != null && idOrgaoExpedidor.equals(ConstantesSistema.NUMERO_NAO_INFORMADO)) && (idUnidadeFederacao != null && idUnidadeFederacao
										.equals(ConstantesSistema.NUMERO_NAO_INFORMADO))))){
			if(validarDadosCliente){

				throw new ActionServletException("atencao.rg_campos_relacionados.nao_preenchidos");

			}

		}

		if(tipoPessoa != null && tipoPessoa.equals(ClienteTipo.INDICADOR_PESSOA_FISICA) && cpf == null){

			if(validarDadosCliente){
				if(!usuarioPermissaoEspecial){
					throw new ActionServletException("atencao.naoinformado",
									"atualizarClienteWizardAction.do?destino=2&action=atualizarClienteNomeTipoAction",
									new ParametroNaoInformadoException("CPF"), "CPF");
				}
			}

		}else if(tipoPessoa != null && tipoPessoa.equals(ClienteTipo.INDICADOR_PESSOA_FISICA)){

			// Se o cpf Informado for diferente do cpf cadastrado
			if(clienteAtualizacao != null && cpf != null && !cpf.equals(clienteAtualizacao.getCpf())){
				// Verifica a existencia de cpf ja cadastrado
				this.existeCpf(cpf, fachada);
			}

		}

		UnidadeFederacao unidadeFederacao = null;
		if(clienteActionForm.get("idUnidadeFederacao") != null && ((Integer) clienteActionForm.get("idUnidadeFederacao")).intValue() > 0){
			unidadeFederacao = new UnidadeFederacao();
			unidadeFederacao.setId((Integer) clienteActionForm.get("idUnidadeFederacao"));

			unidadeFederacao = (UnidadeFederacao) Util.retonarObjetoDeColecao(fachada.pesquisar(unidadeFederacao.retornaFiltro(),
							UnidadeFederacao.class.getName()));

			if(unidadeFederacao == null){

				if(validarDadosCliente){

					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Unidade Federação");

				}

			}

		}

		if(inscricaoEstadual != null && !inscricaoEstadual.equalsIgnoreCase("") && unidadeFederacao == null){

			if(validarDadosCliente){

				throw new ActionServletException("atencao.informe_campo", null, "Estado");

			}

		}

		OrgaoExpedidorRg orgaoExpedidorRg = null;
		if(clienteActionForm.get("idOrgaoExpedidor") != null && ((Integer) clienteActionForm.get("idOrgaoExpedidor")).intValue() > 0){
			orgaoExpedidorRg = new OrgaoExpedidorRg();
			orgaoExpedidorRg.setId((Integer) clienteActionForm.get("idOrgaoExpedidor"));

			orgaoExpedidorRg = (OrgaoExpedidorRg) Util.retonarObjetoDeColecao(fachada.pesquisar(orgaoExpedidorRg.retornaFiltro(),
							OrgaoExpedidorRg.class.getName()));

			if(orgaoExpedidorRg == null){

				if(validarDadosCliente){

					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Orgão Expedidor");

				}

			}

		}

		PessoaSexo pessoaSexo = null;
		if(clienteActionForm.get("idPessoaSexo") != null && ((Integer) clienteActionForm.get("idPessoaSexo")).intValue() > 0){
			pessoaSexo = new PessoaSexo();
			pessoaSexo.setId((Integer) clienteActionForm.get("idPessoaSexo"));

			pessoaSexo = (PessoaSexo) Util
							.retonarObjetoDeColecao(fachada.pesquisar(pessoaSexo.retornaFiltro(), PessoaSexo.class.getName()));

			if(pessoaSexo == null){

				if(validarDadosCliente){

					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Sexo");

				}

			}

		}

		Profissao profissao = null;
		if(clienteActionForm.get("idProfissao") != null && ((Integer) clienteActionForm.get("idProfissao")).intValue() > 0){
			profissao = new Profissao();
			profissao.setId((Integer) clienteActionForm.get("idProfissao"));

			profissao = (Profissao) Util.retonarObjetoDeColecao(fachada.pesquisar(profissao.retornaFiltro(), Profissao.class.getName()));

			if(profissao == null){

				if(validarDadosCliente){

					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Profissão");

				}

			}

		}

		RamoAtividade ramoAtividade = null;
		if(clienteActionForm.get("idRamoAtividade") != null && ((Integer) clienteActionForm.get("idRamoAtividade")).intValue() > 0){
			ramoAtividade = new RamoAtividade();
			ramoAtividade.setId((Integer) clienteActionForm.get("idRamoAtividade"));

			ramoAtividade = (RamoAtividade) Util.retonarObjetoDeColecao(fachada.pesquisar(ramoAtividade.retornaFiltro(),
							RamoAtividade.class.getName()));

			if(ramoAtividade == null){

				if(validarDadosCliente){

					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Ramo de Atividade");

				}

			}

		}

		Cliente clienteResponsavel = null;
		if(clienteActionForm.get("codigoClienteResponsavel") != null
						&& !((String) clienteActionForm.get("codigoClienteResponsavel")).trim().equalsIgnoreCase("")){
			// Cria o objeto do cliente responsável
			clienteResponsavel = new Cliente();
			clienteResponsavel.setId(new Integer((String) clienteActionForm.get("codigoClienteResponsavel")));

			clienteResponsavel = (Cliente) Util.retonarObjetoDeColecao(fachada.pesquisar(clienteResponsavel.retornaFiltro(), Cliente.class
							.getName()));

			if(clienteResponsavel == null){

				if(validarDadosCliente){

					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Cliente Responsável");

				}

			}

		}

		// Verifica se o usuário adicionou um endereço de correspondência
		Long enderecoCorrespondenciaSelecao = (Long) clienteActionForm.get("enderecoCorrespondenciaSelecao");

		if(enderecoCorrespondenciaSelecao == null || enderecoCorrespondenciaSelecao == 0){

			if(validarDadosCliente){

				throw new ActionServletException("atencao.endereco_correspondencia.nao_selecionado");

			}

		}

		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

		try{
			Cliente cliente = new Cliente(nome, nomeAbreviado, cpf, rg,
							dataEmissao != null && !dataEmissao.trim().equalsIgnoreCase("") ? formatoData.parse(dataEmissao) : null,
							dataNascimento != null && !dataNascimento.trim().equalsIgnoreCase("") ? formatoData.parse(dataNascimento)
											: null, cnpj, email, indicadorUso, clienteAtualizacao.getUltimaAlteracao(), orgaoExpedidorRg,
							clienteResponsavel, pessoaSexo, profissao, unidadeFederacao, clienteTipo, ramoAtividade, null,
							inscricaoEstadual, Short.parseShort(indicadorContaBraille));

			// Seta o id do cliente atualizado para ser identificado no BD na atualização
			cliente.setId(clienteAtualizacao.getId());

			cliente.setIndicadorCobrancaAcrescimos(clienteAtualizacao.getIndicadorCobrancaAcrescimos());

			cliente.setClienteTipoEspecial(clienteTE);

			if(clienteActionForm.get("diaVencimento") != null && !(clienteActionForm.get("diaVencimento").equals(""))){
				String diaVencimento = (String) clienteActionForm.get("diaVencimento");
				cliente.setDataVencimento(new Short(diaVencimento));
			}else{
				cliente.setDataVencimento(null);
			}

			// Nome da Mãe
			if(clienteActionForm.get("nomeMae") != null && (!(clienteActionForm.get("nomeMae").equals("")))){
				cliente.setNomeMae((String) clienteActionForm.get("nomeMae"));
			}

			Short indMulta = httpServletRequest.getParameter("indMulta") != null
							&& new Boolean(httpServletRequest.getParameter("indMulta").toString()).booleanValue() ? ConstantesSistema.SIM
							: ConstantesSistema.NAO;
			Short indJuros = httpServletRequest.getParameter("indJuros") != null
							&& new Boolean(httpServletRequest.getParameter("indJuros").toString()).booleanValue() ? ConstantesSistema.SIM
							: ConstantesSistema.NAO;
			Short indCorrecao = httpServletRequest.getParameter("indCorrecao") != null
							&& new Boolean(httpServletRequest.getParameter("indCorrecao").toString()).booleanValue() ? ConstantesSistema.SIM
							: ConstantesSistema.NAO;
			Short indImpostoFederal = httpServletRequest.getParameter("indImpostoFederal") != null ? Short.valueOf(httpServletRequest
							.getParameter("indImpostoFederal")) : 2;
			Integer agencia = (Integer) clienteActionForm.get("agencia");
			String contaBancaria = (String) clienteActionForm.get("contaBancaria");

			// Atualizar Cliente Responsavel
			ClienteResponsavel responsavelCliente = new ClienteResponsavel();
			responsavelCliente.setCliente(cliente);
			responsavelCliente.setIndMulta(indMulta);
			responsavelCliente.setIndJuros(indJuros);
			responsavelCliente.setIndCorrecao(indCorrecao);
			responsavelCliente.setIndImpostoFederal(indImpostoFederal);
			responsavelCliente.setIndUso(indicadorUso != null ? indicadorUso : ConstantesSistema.INDICADOR_USO_DESATIVO);
			responsavelCliente.setUltimaAlteracao(new Date());

			if(agencia != null){
				FiltroAgencia filtroAgencia = new FiltroAgencia();
				filtroAgencia.adicionarParametro(new ParametroSimples(FiltroAgencia.ID, agencia));

				Collection colecaoAgencia = fachada.pesquisarTabelaAuxiliar(filtroAgencia, Agencia.class.getName());

				if(!colecaoAgencia.isEmpty()){

					responsavelCliente.setAgencia((Agencia) colecaoAgencia.iterator().next());
					responsavelCliente.setContaBancaria(contaBancaria);
				}
			}

			String indDadosAdicionais = (String) httpServletRequest.getParameter("indDadosAdicionais");
			String idRegistroAtualizacao = (String) httpServletRequest.getParameter("idRegistroAtualizacao");


			Integer idRaca = (Integer) clienteActionForm.get("idRaca");

			Raca raca = null;
			if(idRaca != null && idRaca != Integer.parseInt(ConstantesSistema.VALOR_NAO_INFORMADO)){
				FiltroRaca filtroRaca = new FiltroRaca();
				filtroRaca.adicionarParametro(new ParametroSimples(FiltroRaca.INDICADOR_USO, ConstantesSistema.SIM));
				filtroRaca.adicionarParametro(new ParametroSimples(FiltroRaca.ID, idRaca));

				raca = (Raca) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroRaca, Raca.class.getName()));
				if(raca == null){
					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Raça");
				}
			}
			cliente.setRaca(raca);

			// Nacionalidade
			Integer idNacionalidade = (Integer) clienteActionForm.get("idNacionalidade");

			if(idNacionalidade != null && idNacionalidade != Integer.parseInt(ConstantesSistema.VALOR_NAO_INFORMADO)){
				FiltroNacionalidade filtroNacionalidade = new FiltroNacionalidade();
				filtroNacionalidade.adicionarParametro(new ParametroSimples(FiltroNacionalidade.INDICADOR_USO, ConstantesSistema.ATIVO));
				filtroNacionalidade.adicionarParametro(new ParametroSimples(FiltroNacionalidade.ID, clienteActionForm
								.get("idNacionalidade")));

				Collection<Nacionalidade> colecaoNacionalidades = fachada.pesquisar(filtroNacionalidade, Nacionalidade.class.getName());
				if(!Util.isVazioOrNulo(colecaoNacionalidades)){
					Nacionalidade nacionalidade = (Nacionalidade) Util.retonarObjetoDeColecao(colecaoNacionalidades);
					cliente.setNacionalidade(nacionalidade);
				}

			}
			// Estado Civil
			Integer idEstadoCivil = (Integer) clienteActionForm.get("idEstadoCivil");

			EstadoCivil estadoCivil = null;
			if(idEstadoCivil != null && !idEstadoCivil.equals(Integer.parseInt(ConstantesSistema.VALOR_NAO_INFORMADO))){
				FiltroEstadoCivil filtroEstadoCivil = new FiltroEstadoCivil();
				filtroEstadoCivil.adicionarParametro(new ParametroSimples(FiltroEstadoCivil.INDICADOR_USO, ConstantesSistema.SIM));
				filtroEstadoCivil.adicionarParametro(new ParametroSimples(FiltroEstadoCivil.ID, idEstadoCivil));

				estadoCivil = (EstadoCivil) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroEstadoCivil, EstadoCivil.class.getName()));
				if(estadoCivil == null){
					throw new ActionServletException("atencao.pesquisa_inexistente", null, "Estado Civil");
				}
			}
			cliente.setEstadoCivil(estadoCivil);

			// Atualiza o cliente
			fachada.atualizarCliente(cliente, colecaoFones, colecaoEnderecos, responsavelCliente, usuario, indDadosAdicionais,
							idRegistroAtualizacao);

			// limpa a sessão
			sessao.removeAttribute("colecaoClienteFone");
			sessao.removeAttribute("colecaoEnderecos");
			sessao.removeAttribute("foneTipos");
			sessao.removeAttribute("municipios");
			sessao.removeAttribute("colecaoResponsavelSuperiores");
			sessao.removeAttribute("InserirEnderecoActionForm");
			sessao.removeAttribute("ClienteActionForm");
			sessao.removeAttribute("tipoPesquisaRetorno");
			sessao.removeAttribute("clienteAtualizacao");

		}catch(ParseException ex){
			// Erro no hibernate
			reportarErros(httpServletRequest, "erro.sistema", ex);
			// Atribui o mapeamento de retorno para a tela de erro
			retorno = actionMapping.findForward("telaErro");
		}

		// Monta a página de sucesso
		if(retorno.getName().equalsIgnoreCase("telaSucesso")){
			montarPaginaSucesso(httpServletRequest, "Cliente de código " + clienteAtualizacao.getId() + " atualizado com sucesso.",
							"Realizar outra Manutenção de Cliente", "exibirManterClienteAction.do?menu=sim");
		}

		return retorno;
	}

	private void validarObrigatoriedadeCampos(HttpServletRequest httpServletRequest, DynaValidatorForm dynaValidatorForm){

		Boolean permiteInformarCamposSemValor = this.getFachada().verificarPermissaoEspecial(
						PermissaoEspecial.PERMITIR_INFORMAR_CAMPO_OBRIGATORIO_SEM_VALOR, this.getUsuarioLogado(httpServletRequest));

		if(!permiteInformarCamposSemValor){

			Short dataNascClienteObrigatoria = null;
			Short nomeMaeClienteObrigatorio = null;

			try{

				dataNascClienteObrigatoria = Short.valueOf(((ParametroSistema) Fachada.getInstancia().obterParametroSistema(
								"P_DATA_NASC_CLIENTE_OBRIGATORIO")).getValor());

				nomeMaeClienteObrigatorio = Short.valueOf(((ParametroSistema) Fachada.getInstancia().obterParametroSistema(
								"P_NOME_MAE_CLIENTE_OBRIGATORIO")).getValor());

			}catch(Exception e){

				throw new ActionServletException("erro.sistema");

			}

			if(dataNascClienteObrigatoria != null && dataNascClienteObrigatoria.equals(ConstantesSistema.SIM)){

				if(StringUtils.isBlank((String) dynaValidatorForm.get("nomeMae"))){

					throw new ActionServletException("atencao.informe_campo", null, "Nome da Mãe");

				}

			}

			if(nomeMaeClienteObrigatorio != null && nomeMaeClienteObrigatorio.equals(ConstantesSistema.SIM)){

				if(StringUtils.isBlank((String) dynaValidatorForm.get("dataNascimento"))){

					throw new ActionServletException("atencao.informe_campo", null, "Data de Nascimento");

				}

			}

		}

	}

	private void existeCpf(String cpf, Fachada fachada){

		FiltroCliente filtroCpf = new FiltroCliente();
		filtroCpf.adicionarParametro(new ParametroSimples(FiltroCliente.CPF, cpf));

		Cliente clienteCpf = (Cliente) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroCpf,

		Cliente.class.getName()));

		if(clienteCpf != null){
			throw new ActionServletException("atencao.cpf.cliente.ja_cadastrado", null,

			clienteCpf.getId().toString());
		}

	}

	private void existeCnpf(String cnpj, Fachada fachada, ClienteTipo clienteTipo) throws ActionServletException{

		FiltroCliente filtroCnpj = new FiltroCliente();
		filtroCnpj.adicionarParametro(new ParametroSimples(FiltroCliente.CNPJ, cnpj));
		boolean permiteDuplicidade = false;
		Cliente clienteCnpj = (Cliente) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroCnpj, Cliente.class.getName()));

		try{
			if(ParametroCadastro.P_PERMITE_DUPLICIDADE_CNPJ.executar().equals(ConstantesSistema.SIM.toString())
							&& !EsferaPoder.PARTICULAR.toString().equals(clienteTipo.getEsferaPoder().getId().toString())){

				permiteDuplicidade = true;

			}
		}catch(ControladorException e){

			throw new ActionServletException(e.getMessage(), e);
		}

		if(clienteCnpj != null && !permiteDuplicidade){

			throw new ActionServletException("atencao.cnpj.cliente.ja_cadastrado", null, clienteCnpj.getId().toString());

		}

	}

	private Boolean validarDadosCliente(Short indicadorUso){

		Boolean retorno = Boolean.TRUE;

		if(indicadorUso != null && indicadorUso.equals(ConstantesSistema.INDICADOR_USO_DESATIVO)){

			retorno = Boolean.FALSE;

		}

		return retorno;

	}

}
