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

import gcom.cadastro.cliente.*;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.StatusWizard;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.filtro.ParametroSimples;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionFormClass;
import org.apache.struts.config.FormBeanConfig;
import org.apache.struts.config.ModuleConfig;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * Action para a pré-exibição da página de Atualizar Cliente
 * 
 * @author rodrigo
 */
public class ExibirAtualizarClienteAction
				extends GcomAction {

	/**
	 * Método que é executado primeiro
	 * 
	 * @param actionMapping
	 *            Mapeamento do Struts
	 * @param actionForm
	 *            Form do Funcionário
	 * @param httpServletRequest
	 *            Request Atual
	 * @param httpServletResponse
	 *            Response Atual
	 * @return Retorno do Mapeamento do Struts
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// ------------Parte que inicializa o processo de
		// atualização----------------------------------

		// localiza o action no objeto actionmapping
		ActionForward retorno = actionMapping.findForward("atualizarClienteNomeTipo");

		// obtém a instância da sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		String codigoCliente = null;

		StatusWizard statusWizard = null;

		if(httpServletRequest.getParameter("desfazer") == null){

			if(httpServletRequest.getParameter("idRegistroAtualizacao") != null){
				codigoCliente = (String) httpServletRequest.getParameter("idRegistroAtualizacao");
			}else if(httpServletRequest.getAttribute("idRegistroAtualizacao") != null){
				codigoCliente = (String) httpServletRequest.getAttribute("idRegistroAtualizacao");
			}

			// Verifica se chegou no atualizar cliente atraves da tela de
			// filtrar devido a um unico registro
			// ou atraves da lista de imoveis no manter cliente
			if(sessao.getAttribute("atualizar") != null || httpServletRequest.getParameter("sucesso") != null){
				statusWizard = new StatusWizard("atualizarClienteWizardAction", "atualizarClienteAction", "cancelarAtualizarClienteAction",
								"exibirFiltrarClienteAction", "exibirAtualizarClienteAction.do", codigoCliente);
				sessao.removeAttribute("atualizar");
			}else{
				statusWizard = new StatusWizard("atualizarClienteWizardAction", "atualizarClienteAction", "cancelarAtualizarClienteAction",
								"exibirManterClienteAction", "exibirAtualizarClienteAction.do", codigoCliente);
			}

			statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(1, "ClienteNomeTipoA.gif", "ClienteNomeTipoD.gif",
							"exibirAtualizarClienteNomeTipoAction", "atualizarClienteNomeTipoAction"));
			statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(2, "ClientePessoaA.gif", "ClientePessoaD.gif",
							"exibirAtualizarClientePessoaAction", "atualizarClientePessoaAction"));
			statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(3, "aba_client_endA.gif", "aba_client_endD.gif",
							"exibirAtualizarClienteEnderecoAction", "atualizarClienteEnderecoAction"));
			statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(4, "ClienteTelefoneA.gif", "ClienteTelefoneD.gif",
							"exibirAtualizarClienteTelefoneAction", "atualizarClienteTelefoneAction"));

			if(this.getParametroCompanhia(httpServletRequest).toString().equals(SistemaParametro.INDICADOR_EMPRESA_DESO.toString())){
				statusWizard.inserirNumeroPaginaCaminho(statusWizard.new StatusWizardItem(5, "ClienteResponsavelA.gif",
								"ClienteResponsavelD.gif", "exibirAtualizarClienteResponsavelAction", "atualizarClienteResponsavelAction"));
			}

			// manda o statusWizard para a sessao
			sessao.setAttribute(ConstantesSistema.NOME_WIZARD_ALTERAR_CLIENTE, statusWizard);

		}else{
			statusWizard = (StatusWizard) sessao.getAttribute(ConstantesSistema.NOME_WIZARD_ALTERAR_CLIENTE);

			codigoCliente = statusWizard.getId();
		}

		// limpa a sessão
		sessao.removeAttribute("colecaoClienteFone");
		sessao.removeAttribute("colecaoEnderecos");
		sessao.removeAttribute("foneTipos");
		sessao.removeAttribute("municipios");
		sessao.removeAttribute("colecaoResponsavelSuperiores");
		sessao.removeAttribute("InserirEnderecoActionForm");

		// Retira o actionForm da sessão para criar um novo mais abaixo na linha 192
		sessao.removeAttribute("ClienteActionForm");

		sessao.removeAttribute("tipoPesquisaRetorno");
		sessao.removeAttribute("clienteAtualizacao");

		// Seta um atributo na sessao para ser utilizado nas abas
		sessao.setAttribute("nomeUnicoWizard", ConstantesSistema.NOME_WIZARD_ALTERAR_CLIENTE);

		// Cria o filtro para cliente
		FiltroCliente filtroCliente = new FiltroCliente();

		filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, codigoCliente));
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.CLIENTE_RESPONSAVEL);
		filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.RACA);

		// pesquisa a coleçao de cliente(s)
		Collection clientes = fachada.pesquisar(filtroCliente, Cliente.class.getName());

		// Caso a coleção esteja vazia, indica erro inesperado
		if(clientes == null || clientes.isEmpty()){
			throw new ActionServletException("erro.sistema");
		}else{
			// O cliente que será atualizado
			Cliente cliente = (Cliente) ((List) clientes).get(0);

			if(cliente.getId() != null && !cliente.getId().equals("")){
				statusWizard.adicionarItemHint("Código:", cliente.getId().toString());
			}
			if(cliente.getNome() != null && !cliente.getNome().equals("")){
				statusWizard.adicionarItemHint("Nome:", cliente.getNome());
			}
			if(cliente.getCnpj() != null && !cliente.getCnpj().equals("")){
				statusWizard.adicionarItemHint("CNPJ:", cliente.getCnpjFormatado());
			}else if(cliente.getCpf() != null && !cliente.getCpf().equals("")){
				statusWizard.adicionarItemHint("CPF:", cliente.getCpfFormatado());
			}

			Collection colecaoClienteCadastradoTarifaSocial = fachada.verificarClienteUsuarioCadastradoTarifaSocial(cliente.getId());

			if(colecaoClienteCadastradoTarifaSocial != null && !colecaoClienteCadastradoTarifaSocial.isEmpty()){

				Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

				if(!fachada.verificarPermissaoAtualizarUsuarioTarifaSocial(usuario)){
					throw new ActionServletException("atencao.usuario.sem.permissao.atualizar.usuario.tarifa_social");
				}
			}

			// Manda o cliente na sessão
			sessao.setAttribute("clienteAtualizacao", cliente);

			// Formato para a conversão de datas
			SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

			/******************************************************************************************/
			/** Código para criar um actionForm totalmente novo - Esta foi a solução encontrada para **/
			/** a passagem do inserir direto para o atualizar, fazendo as substituição dos dados do **/
			/** form de mesmo nome corretamente, é preciso pegar o form pelo httpServletRequest no **/
			/** exibir da primeira aba (neste caso ExibirAtualizarClienteNomeTipoAction) **/
			/******************************************************************************************/

			ModuleConfig module = actionMapping.getModuleConfig();
			FormBeanConfig formBeanConfig = module.findFormBeanConfig("ClienteActionForm");
			DynaActionFormClass dynaClass = DynaActionFormClass.createDynaActionFormClass(formBeanConfig);
			DynaValidatorForm clienteActionForm = null;
			try{
				clienteActionForm = (DynaValidatorForm) dynaClass.newInstance();
			}catch(IllegalAccessException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}catch(InstantiationException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			SistemaParametro param = fachada.pesquisarParametrosDoSistema();
			String parametroEmpresa = param.getParmId().toString();

			sessao.setAttribute("parametroEmpresa", parametroEmpresa);

			/******************************************************************************************/
			clienteActionForm.set("codigoCliente", cliente.getId().toString());
			clienteActionForm.set("nome", formatarResultado(cliente.getNome()));
			clienteActionForm.set("nomeAbreviado", formatarResultado(cliente.getNomeAbreviado()));
			clienteActionForm.set("email", formatarResultado(cliente.getEmail()));
			clienteActionForm.set("indicadorContaBraille", cliente.getIndicadorContaBraille().toString());

			if(cliente.getDataVencimento() != null){
				clienteActionForm.set("diaVencimento", cliente.getDataVencimento().toString());
			}else{
				clienteActionForm.set("diaVencimento", null);
			}

			clienteActionForm.set("tipoPessoa", new Short("" + cliente.getClienteTipo().getId()));
			// Criado para comparação
			clienteActionForm.set("tipoPessoaAntes", new Short("" + cliente.getClienteTipo().getId()));
			if(cliente.getClienteTipoEspecial() != null){
				clienteActionForm.set("clienteTipoEspecialAntes", new Integer(cliente.getClienteTipoEspecial().getId()));
				clienteActionForm.set("clienteTipoEspecial", new Integer(cliente.getClienteTipoEspecial().getId()));
			}else{
				clienteActionForm.set("clienteTipoEspecial", new Integer(-1));
				clienteActionForm.set("clienteTipoEspecialAntes", new Integer(-1));
			}
			clienteActionForm.set("cpf", formatarResultado("" + cliente.getCpf()));
			clienteActionForm.set("rg", formatarResultado("" + cliente.getRg()));
			if(cliente.getDataEmissaoRg() != null){
				clienteActionForm.set("dataEmissao", formatoData.format(cliente.getDataEmissaoRg()));
			}else{
				clienteActionForm.set("dataEmissao", null);

			}
			clienteActionForm.set("idOrgaoExpedidor", formatarResultado(cliente.getOrgaoExpedidorRg()));

			if(cliente.getDataNascimento() != null){
				clienteActionForm.set("dataNascimento", formatoData.format(cliente.getDataNascimento()));
			}else{
				clienteActionForm.set("dataNascimento", null);

			}

			clienteActionForm.set("idProfissao", formatarResultado(cliente.getProfissao()));
			clienteActionForm.set("idPessoaSexo", formatarResultado(cliente.getPessoaSexo()));
			clienteActionForm.set("nomeMae", formatarResultado(cliente.getNomeMae()));
			clienteActionForm.set("idNacionalidade", formatarResultado(cliente.getNacionalidade()));
			if(cliente.getCnpj() != null){
				clienteActionForm.set("cnpj", cliente.getCnpj().toString());
			}
			clienteActionForm.set("idRamoAtividade", formatarResultado(cliente.getRamoAtividade()));
			if(cliente.getCliente() != null){
				clienteActionForm.set("codigoClienteResponsavel", formatarResultado(cliente.getCliente().getId().toString()));
				clienteActionForm.set("nomeClienteResponsavel", formatarResultado(cliente.getCliente().getNome()));
			}

			if(cliente.getDiaVencimento() != null){
				clienteActionForm.set("diaVencimento", cliente.getDiaVencimento());
			}

			if(cliente.getInscricaoEstadual() != null){
				clienteActionForm.set("inscricaoEstadual", cliente.getInscricaoEstadual());
			}

			// Como o Estado é utilizado para os 2 'tipos' de cliente, verifica se fará formatação
			if(cliente.getClienteTipo() != null
							&& new Short(cliente.getClienteTipo().getId().shortValue()).equals(ClienteTipo.INDICADOR_PESSOA_JURIDICA)
							&& cliente.getUnidadeFederacao() != null){
				clienteActionForm.set("idUnidadeFederacao", cliente.getUnidadeFederacao().getId());
			}else{
				clienteActionForm.set("idUnidadeFederacao", Integer.parseInt(ConstantesSistema.VALOR_NAO_INFORMADO));
			}

			if(cliente.getRaca() != null){
				clienteActionForm.set("idRaca", cliente.getRaca().getId());
			}else{
				clienteActionForm.set("idRaca", Integer.parseInt(ConstantesSistema.VALOR_NAO_INFORMADO));
			}
			if(cliente.getEstadoCivil() != null){
				clienteActionForm.set("idEstadoCivil", cliente.getEstadoCivil().getId());
			}else{
				clienteActionForm.set("idEstadoCivil", Integer.parseInt(ConstantesSistema.VALOR_NAO_INFORMADO));
			}

			// Seta a coleção de endereços do usuário
			FiltroClienteEndereco filtroClienteEndereco = new FiltroClienteEndereco();
			filtroClienteEndereco.adicionarParametro(new ParametroSimples(FiltroClienteEndereco.CLIENTE_ID, cliente.getId()));
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTipo");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.logradouro.logradouroTitulo");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("enderecoReferencia");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroBairro.bairro.municipio.unidadeFederacao");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep.cep");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("logradouroCep");
			filtroClienteEndereco.adicionarCaminhoParaCarregamentoEntidade("enderecoTipo");

			Collection enderecosCliente = fachada.pesquisar(filtroClienteEndereco, ClienteEndereco.class.getName());
			Iterator iterator = null;

			if(enderecosCliente != null){
				iterator = enderecosCliente.iterator();

				// Percorrer a coleção dos endereços para setar no form qual o
				// endereço do cliente que
				// foi selecionado como o de correspondência
				while(iterator.hasNext()){
					ClienteEndereco clienteEndereco = (ClienteEndereco) iterator.next();
					if(clienteEndereco.getLogradouroBairro() != null) System.out.println("bairro = "
									+ clienteEndereco.getLogradouroBairro().getBairro().getNome());
					if(clienteEndereco.getIndicadorEnderecoCorrespondencia().equals(ConstantesSistema.INDICADOR_ENDERECO_CORRESPONDENCIA)){
						clienteActionForm.set("enderecoCorrespondenciaSelecao", new Long(obterTimestampIdObjeto(clienteEndereco)));
						break;
					}
				}
				sessao.setAttribute("colecaoEnderecos", enderecosCliente);
			}

			// Seta a coleção de telefones do usuário
			FiltroClienteFone filtroClienteFone = new FiltroClienteFone();
			filtroClienteFone.adicionarParametro(new ParametroSimples(FiltroClienteFone.CLIENTE_ID, cliente.getId()));

			/*
			 * filtroClienteFone
			 * .adicionarCaminhoParaCarregamentoEntidade("foneTipo.descricao");
			 */

			filtroClienteFone.adicionarCaminhoParaCarregamentoEntidade("foneTipo");

			Collection telefonesCliente = fachada.pesquisar(filtroClienteFone, ClienteFone.class.getName());

			if(telefonesCliente != null){

				iterator = telefonesCliente.iterator();

				// Percorrer a coleção dos telefones para setar no form qual o
				// telefone do cliente que
				// foi selecionado como o principal
				while(iterator.hasNext()){
					ClienteFone clienteFone = (ClienteFone) iterator.next();

					if(clienteFone != null){
						if(clienteFone.getIndicadorTelefonePadrao() != null
										&& clienteFone.getIndicadorTelefonePadrao().equals(ConstantesSistema.INDICADOR_TELEFONE_PRINCIPAL)){
							clienteActionForm.set("indicadorTelefonePadrao", new Long(obterTimestampIdObjeto(clienteFone)));
							break;
						}
					}
				}
			}

			// Verifica o indicador de uso
			clienteActionForm.set("indicadorUso", cliente.getIndicadorUso().toString());

			// Seta a coleção de telefones do usuário
			sessao.setAttribute("colecaoClienteFone", telefonesCliente);

			// Seta o form na sessão
			// sessao.setAttribute("ClienteActionForm", clienteActionForm);

			// Manda o form para a primeira página do processo para que depois
			// ela seja colocada na sessão
			httpServletRequest.setAttribute("ClienteActionForm", clienteActionForm);

			try{
				// Seta na sessão o indicador que será utilizado na jsp para decidir se o campo
				// "Dia do Vencimento" será exibido.
				String paramInformarVencimentoCliente = (String) ParametroCadastro.P_INFORMAR_VENCIMENTO_PARA_CLIENTE.executar();
				sessao.setAttribute("paramInformarVencimentoCliente", paramInformarVencimentoCliente);
				
				// indicador exibir campo "Conta Impressa em Braille?"
				String solicitacaoTipoEspecificacaoContaBraille = (String) ParametroCadastro.P_TIPO_SOLICITACAO_ESPECIFICACAO_CONTA_BRAILLE
								.executar();
				if(!solicitacaoTipoEspecificacaoContaBraille.equals(ConstantesSistema.VALOR_NAO_INFORMADO)){
					sessao.setAttribute("indicadorExibirCampoContaBraille", "");
				}else{
					sessao.removeAttribute("indicadorExibirCampoContaBraille");
				}
			}catch(ControladorException e){
				e.printStackTrace();
			}

		}

		// devolve o mapeamento de retorno
		return retorno;
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param parametro
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	private String formatarResultado(String parametro){

		if(parametro != null && !parametro.trim().equals("") && !parametro.trim().equals("null")){
			return parametro.trim();
		}else{
			return "";
		}
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param parametro
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	private Integer formatarResultado(Object parametro){

		if(parametro != null){
			try{
				return (Integer) parametro.getClass().getMethod("getId", (Class[]) null).invoke(parametro, (Object[]) null);
			}catch(SecurityException ex){
				return null;
			}catch(NoSuchMethodException ex){
				return null;
			}catch(InvocationTargetException ex){
				return null;
			}catch(IllegalArgumentException ex){
				return null;
			}catch(IllegalAccessException ex){
				return null;
			}
		}else{
			return new Integer(ConstantesSistema.NUMERO_NAO_INFORMADO);
		}
	}

}