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

package gcom.gui.faturamento.conta;

import gcom.cadastro.cliente.Cliente;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.FiltroConta;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
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

/**
 * [UC0146] Manter Conta
 * [SB0015] - Alterar Cliente Responsável de um Conjunto de Contas
 * 
 * @author Anderson Italo
 * @date 20/01/2014
 */
public class AlterarClienteResponsavelConjuntoContaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirAlterarClienteResponsavelConjuntoConta");
		httpServletRequest.getAttribute("idsContasSelecionadas");
		HttpSession sessao = httpServletRequest.getSession(false);

		AlterarClienteResponsavelConjuntoContaActionForm alterarClienteResponsavelConjuntoContaActionForm = (AlterarClienteResponsavelConjuntoContaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		if(Util.isNaoNuloBrancoZero(alterarClienteResponsavelConjuntoContaActionForm.getIdCliente())){

			// Usuario logado no sistema
			Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");
			Collection<Conta> colecaoContasSelecionadas = new ArrayList<Conta>();
			String contaSelected = alterarClienteResponsavelConjuntoContaActionForm.getContaSelected();
			Collection idsConta = new ArrayList();

			if(!Util.isVazioOuBranco(contaSelected)){

				// Contas selecionadas pelo usuário
				String[] arrayIdentificadores = contaSelected.split(",");

				for(int i = 0; i < arrayIdentificadores.length; i++){

					String dadosConta = arrayIdentificadores[i];
					String[] idContaArray = dadosConta.split("-");
					Integer idConta = new Integer(idContaArray[0]);
					idsConta.add(idConta);

					FiltroConta filtroConta = new FiltroConta();
					filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.IMOVEL);
					filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.LOCALIDADE);
					filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.QUADRA);
					filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.SETOR_COMERCIAL);
					filtroConta.adicionarCaminhoParaCarregamentoEntidade(FiltroConta.DEBITO_CREDITO_SITUACAO_ATUAL);

					filtroConta.adicionarParametro(new ParametroSimples(FiltroConta.ID, idConta));
					Collection colecaoContas = fachada.pesquisar(filtroConta, Conta.class.getName());
					Conta contaSelecao = (Conta) colecaoContas.iterator().next();
					colecaoContasSelecionadas.add(contaSelecao);
				}
			}

			// Realizar um reload na tela de manter conjunto conta
			if(sessao.getAttribute("retornoArquivo") != null){

				httpServletRequest.setAttribute("reloadPage", "grupo");
			}else{

				httpServletRequest.setAttribute("reloadPage", "OK");
			}

			// Cliente
			Cliente cliente = null;

			cliente = (Cliente) fachada.pesquisar(Integer.valueOf(alterarClienteResponsavelConjuntoContaActionForm.getIdCliente()),
							Cliente.class);

			if(cliente == null){

				throw new ActionServletException("atencao.cliente.inexistente");
			}

			// [SB0015 - Alterar Cliente Responsável de um Conjunto de Contas]
			fachada.alterarClienteResponsavelConjuntoContas(colecaoContasSelecionadas, cliente, usuarioLogado);
		}

		return retorno;
	}
}
