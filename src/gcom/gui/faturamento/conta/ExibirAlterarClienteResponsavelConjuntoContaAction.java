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

package gcom.gui.faturamento.conta;

import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cobranca.bean.ContaValoresHelper;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.FiltroConta;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.PermissaoEspecial;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirAlterarClienteResponsavelConjuntoContaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirAlterarClienteResponsavelConjuntoConta");

		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		// Inst�ncia do formul�rio que est� sendo utilizado
		AlterarClienteResponsavelConjuntoContaActionForm alterarClienteResposanvelContaActionForm = (AlterarClienteResponsavelConjuntoContaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		boolean temPermissaoAtualizarDebitosExecFiscal = fachada.verificarPermissaoEspecial(
						PermissaoEspecial.ATUALIZAR_DEBITOS_EXECUCAO_FISCAL, this.getUsuarioLogado(httpServletRequest));
		if(!temPermissaoAtualizarDebitosExecFiscal){
			/**
			 * [UC0146] Manter Conta
			 * [SB0040] Verificar Exist�ncia de Conta em Execu��o Fiscal
			 * 
			 * @author Gicevalter Couto
			 * @date 07/08/2014
			 * @param colecaoContas
			 */
			StringBuffer parametroExecucaoFiscal = new StringBuffer();

			// Carregando contas selecionadas
			String contaSelected = httpServletRequest.getParameter("idsContasSelecionadas");
			if(!Util.isVazioOuBranco(contaSelected)){
				// Contas selecionadas pelo usu�rio
				String[] arrayIdentificadores = contaSelected.split(",");

				for(int i = 0; i < arrayIdentificadores.length; i++){

					String dadosConta = arrayIdentificadores[i];
					String[] idContaArray = dadosConta.split("-");
					Integer idConta = new Integer(idContaArray[0]);

					if(idConta != null){
						FiltroConta filtroConta = new FiltroConta();
						filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.ID, idConta));
						filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.DEBITOS_COBRADOS);

						Conta conta = (Conta) Util.retonarObjetoDeColecao(((Collection<Conta>) fachada.pesquisar(filtroConta,
										Conta.class.getName())));
						ContaValoresHelper contaValores = new ContaValoresHelper();
						contaValores.setConta(conta);

						Collection<ContaValoresHelper> colecaoContaValores = new ArrayList<ContaValoresHelper>();
						colecaoContaValores.add(contaValores);

						if(fachada.verificarExecucaoFiscal(colecaoContaValores, null, null)){
							parametroExecucaoFiscal.append(conta.getReferenciaFormatada());
							parametroExecucaoFiscal.append(", ");
						}
					}
				}

				String parametroMensagemExecFiscal = parametroExecucaoFiscal.toString();

				if(!Util.isVazioOuBranco(parametroMensagemExecFiscal)){
					parametroMensagemExecFiscal = parametroMensagemExecFiscal.substring(0, parametroMensagemExecFiscal.length() - 2);

					throw new ActionServletException("atencao.conta.debito.execucao.fiscal", usuario.getNomeUsuario().toString(),
									parametroMensagemExecFiscal);
				}
			}
		}

		String idCliente = alterarClienteResposanvelContaActionForm.getIdCliente();

		if(!Util.isVazioOuBranco(idCliente)){

			FiltroCliente filtroCliente = new FiltroCliente();
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, Integer.valueOf(idCliente)));

			Collection<Cliente> colecaoCliente = fachada.pesquisar(filtroCliente, Cliente.class.getName());

			if(!Util.isVazioOrNulo(colecaoCliente)){

				Cliente cliente = (Cliente) Util.retonarObjetoDeColecao(colecaoCliente);
				alterarClienteResposanvelContaActionForm.setIdCliente(cliente.getId().toString());
				alterarClienteResposanvelContaActionForm.setNomeCliente(cliente.getNome());
			}else{

				httpServletRequest.setAttribute("codigoClienteNaoEncontrado", "true");
				alterarClienteResposanvelContaActionForm.setIdCliente("");
				alterarClienteResposanvelContaActionForm.setNomeCliente("CLIENTE INEXISTENTE");
			}
		}

		// Verifica se o tipoConsulta � diferente de nulo ou vazio esse tipo consulta vem do
		// cliente_pesquisar.jsp. � feita essa verifica��o pois pode ser que ainda n�o tenha feito a
		// pesquisa de cliente.
		if(httpServletRequest.getParameter("tipoConsulta") != null && !httpServletRequest.getParameter("tipoConsulta").equals("")){

			// Verifica se o tipo da consulta de cliente � de cliente
			// se for, os parametros de enviarDadosParametros ser�o mandados para
			// a pagina alterar_cliente_responsavel_conjunto_conta.jsp
			if(httpServletRequest.getParameter("tipoConsulta").equals("cliente")){

				alterarClienteResposanvelContaActionForm.setIdCliente(httpServletRequest.getParameter("idCampoEnviarDados"));
				alterarClienteResposanvelContaActionForm.setNomeCliente(httpServletRequest.getParameter("descricaoCampoEnviarDados"));
			}
		}

		return retorno;
	}

}
