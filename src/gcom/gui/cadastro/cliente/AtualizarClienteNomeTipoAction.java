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

package gcom.gui.cadastro.cliente;

import gcom.cadastro.cliente.*;
import gcom.cadastro.endereco.EnderecoTipo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.StatusWizard;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

/**
 * Description of the Class
 * 
 * @author Rodrigo
 */
public class AtualizarClienteNomeTipoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;

		DynaValidatorForm clienteActionForm = (DynaValidatorForm) actionForm;

		// Verifica se o usuário escolheu algum tipo de pessoa para que seja
		// mostrada a página de pessoa física ou de jurídica, se nada estiver
		// especificado a página selecionada será a de pessoa física

		Short tipoPessoa = (Short) clienteActionForm.get("tipoPessoa");
		String tipoPessoaForm = tipoPessoa.toString();
		Short tipoPessoaAntes = (Short) clienteActionForm.get("tipoPessoaAntes");

		// Verifica qual é o próximo passo para a execução do processo
		// String destinoPagina = httpServletRequest.getParameter("destino");
		Fachada fachada = Fachada.getInstancia();

		FiltroClienteTipo filtroClienteTipo = new FiltroClienteTipo();

		filtroClienteTipo.adicionarParametro(new ParametroSimples(FiltroClienteTipo.ID, tipoPessoaForm));
		Collection colecaoClienteTipo = fachada.pesquisar(filtroClienteTipo, ClienteTipo.class.getName());
		ClienteTipo clienteTipo = (ClienteTipo) colecaoClienteTipo.iterator().next();

		Short tipoCliente = clienteTipo.getIndicadorPessoaFisicaJuridica();

		// Pega os dados do formulário
		String cpf = (String) clienteActionForm.get("cpf");
		String codigo = (String) clienteActionForm.get("codigoCliente");
		String rg = (String) clienteActionForm.get("rg");
		String dataEmissao = (String) clienteActionForm.get("dataEmissao");
		Integer idOrgaoExpedidor = (Integer) clienteActionForm.get("idOrgaoExpedidor");
		Integer idUnidadeFederacao = (Integer) clienteActionForm.get("idUnidadeFederacao");

		if(codigo != null && !codigo.equals("") && idUnidadeFederacao.equals(-1)){
			FiltroCliente filtroCliente = new FiltroCliente();

			filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.UNIDADE_FEDERACAO);
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, new Integer(codigo)));

			Collection colecaoCliente = fachada.pesquisar(filtroCliente, Cliente.class.getName());

			if(colecaoCliente != null && !colecaoCliente.isEmpty()){

				Cliente cliente = (Cliente) Util.retonarObjetoDeColecao(colecaoCliente);
				if(cliente.getUnidadeFederacao() != null){
					idUnidadeFederacao = cliente.getUnidadeFederacao().getId();
				}else{
					idUnidadeFederacao = 0;
				}
			}
		}

		if(tipoPessoaForm != null){

			if(tipoPessoaForm.equals(ClienteTipo.APOSENTADO_PENSIONISTA.toString())){

				String numeroBeneficio = (String) clienteActionForm.get("numeroBeneficio");
				if(Util.isVazioOuBranco(numeroBeneficio)){

					throw new ActionServletException("atencao.required", null, "Número do Benefício");
				}

				// Validar Cliente com Número do Benefício Já Existente
				FiltroCliente filtroCliente = new FiltroCliente();
				filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.NUMERO_BENEFICIO, numeroBeneficio));

				Collection<Cliente> colecaoClienteBeneficioExistente = fachada.pesquisar(filtroCliente, Cliente.class.getName());

				if(!Util.isVazioOrNulo(colecaoClienteBeneficioExistente)){

					Cliente clienteNumeroBeneficioExistente = colecaoClienteBeneficioExistente.iterator().next();

					if(!clienteNumeroBeneficioExistente.getId().equals(Util.obterInteger(codigo))){

						throw new ActionServletException("atencao.numero_beneficio_cliente_ja_cadastrado", null,
										new String[] {clienteNumeroBeneficioExistente.getId().toString(), numeroBeneficio});
					}
				}
			}
		}

		clienteActionForm.set("idUnidadeFederacao", idUnidadeFederacao);

		String dataNascimento = (String) clienteActionForm.get("dataNascimento");
		Integer idProfissao = (Integer) clienteActionForm.get("idProfissao");
		Integer idPessoaSexo = (Integer) clienteActionForm.get("idPessoaSexo");
		String cnpj = (String) clienteActionForm.get("cnpj");
		Integer idRamoAtividade = (Integer) clienteActionForm.get("idRamoAtividade");
		String codigoClienteResponsavel = (String) clienteActionForm.get("codigoClienteResponsavel");


		HttpSession sessao = httpServletRequest.getSession();
		StatusWizard statusWizard = (StatusWizard) sessao.getAttribute("statusWizardAlterarCliente");
		sessao.setAttribute("statusWizard", statusWizard);
		// Verifica o destino porque se o usuário tentar concluir o processo
		// nesta página, não é necessário verificar a tela de confirmação
		if(!tipoPessoa.equals(tipoPessoaAntes)){
			if(tipoCliente != null && tipoCliente.equals(ClienteTipo.INDICADOR_PESSOA_JURIDICA)){

				clienteActionForm.set("indicadorPessoaFisicaJuridica", "" + ClienteTipo.INDICADOR_PESSOA_JURIDICA);
				clienteActionForm.set("idPessoaSexo", -1);

				if(sessao.getAttribute("colecaoEnderecos") != null){
					Collection enderecosCliente = (Collection) sessao.getAttribute("colecaoEnderecos");
					Iterator itEndereco = enderecosCliente.iterator();
					ClienteEndereco clienteEndereco = new ClienteEndereco();

					if(itEndereco.hasNext()){
						clienteEndereco = (ClienteEndereco) itEndereco.next();

						clienteEndereco.getEnderecoTipo().setId(new Integer(EnderecoTipo.ENDERECO_TIPO_COMERCIAL));
						clienteEndereco.getEnderecoTipo().setDescricao("COMERCIAL");
					}
				}

				if((idPessoaSexo != null && idPessoaSexo != ConstantesSistema.NUMERO_NAO_INFORMADO)
								|| (cpf != null && !cpf.trim().equalsIgnoreCase("")) || (rg != null && !rg.trim().equalsIgnoreCase(""))
								|| (dataEmissao != null && !dataEmissao.trim().equalsIgnoreCase(""))
								|| (dataNascimento != null && !dataNascimento.trim().equalsIgnoreCase(""))
								|| (idOrgaoExpedidor != null && idOrgaoExpedidor != ConstantesSistema.NUMERO_NAO_INFORMADO)
								|| (idUnidadeFederacao != null && idUnidadeFederacao != ConstantesSistema.NUMERO_NAO_INFORMADO)
								|| (idProfissao != null && idProfissao != ConstantesSistema.NUMERO_NAO_INFORMADO)){
					return montarPaginaConfirmacaoWizard("confirmacao.processo.cliente.dependencia.pessoa_juridica", httpServletRequest,
									actionMapping);
				}
			}else if(tipoCliente != null && tipoCliente.equals(ClienteTipo.INDICADOR_PESSOA_FISICA)){
				// Vai para Pessoa Fisica mas tem dados existentes em pessoa juridica
				clienteActionForm.set("indicadorPessoaFisicaJuridica", "" + ClienteTipo.INDICADOR_PESSOA_FISICA);

				if(sessao.getAttribute("colecaoEnderecos") != null){
					Collection enderecosCliente = (Collection) sessao.getAttribute("colecaoEnderecos");
					Iterator itEndereco = enderecosCliente.iterator();
					ClienteEndereco clienteEndereco = new ClienteEndereco();

					if(itEndereco.hasNext()){
						clienteEndereco = (ClienteEndereco) itEndereco.next();

						clienteEndereco.getEnderecoTipo().setId(new Integer(EnderecoTipo.ENDERECO_TIPO_RESIDENCIAL));
						clienteEndereco.getEnderecoTipo().setDescricao("RESIDENCIAL");
					}
				}

				if((cnpj != null && !cnpj.trim().equalsIgnoreCase(""))
								|| (idRamoAtividade != null && idRamoAtividade != ConstantesSistema.NUMERO_NAO_INFORMADO)
								|| (codigoClienteResponsavel != null && !codigoClienteResponsavel.trim().equalsIgnoreCase(""))){
					return montarPaginaConfirmacaoWizard("confirmacao.processo.cliente.dependencia.pessoa_fisica", httpServletRequest,
									actionMapping);
				}
			}
			clienteActionForm.set("tipoPessoaAntes", tipoPessoa);
		}

		return retorno;
	}
}