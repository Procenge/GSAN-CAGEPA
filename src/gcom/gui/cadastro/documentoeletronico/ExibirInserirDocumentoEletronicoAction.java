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

package gcom.gui.cadastro.documentoeletronico;

import gcom.cadastro.atendimento.AtendimentoDocumentoTipo;
import gcom.cadastro.atendimento.FiltroAtendimentoDocumentoTipo;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.cliente.FiltroCliente;
import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Descrição da classe
 * 
 * @author Gicevalter Couto
 * @date 30/09/2014
 */
public class ExibirInserirDocumentoEletronicoAction
				extends GcomAction {


	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno para a tela de inserir operação
		ActionForward retorno = actionMapping.findForward("documentoEletronicoInserir");

		// Recupera o form de inserir procedmento de atendimento
		InserirDocumentoEletronicoActionForm form = (InserirDocumentoEletronicoActionForm) actionForm;

		// Cria uma instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Cria uma instância da sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		if(sessao.getAttribute("colecaoAtendimentoDocumentoTipo") == null){
			FiltroAtendimentoDocumentoTipo filtroAtendimentoDocumentoTipo = new FiltroAtendimentoDocumentoTipo();
			filtroAtendimentoDocumentoTipo.setCampoOrderBy(FiltroAtendimentoDocumentoTipo.DESCRICAO);

			Collection colecaoAtendimentoDocumentoTipo = fachada.pesquisar(filtroAtendimentoDocumentoTipo,
							AtendimentoDocumentoTipo.class.getName());
			sessao.setAttribute("colecaoAtendimentoDocumentoTipo", colecaoAtendimentoDocumentoTipo);

		}
		
		if(httpServletRequest.getParameter("carregarImovel") != null && form.getIdImovel() != null
						&& !form.getIdImovel().trim().equalsIgnoreCase("")){

			Imovel imovelAtual = fachada.pesquisarImovel(Integer.valueOf(form.getIdImovel()));

			if(imovelAtual != null){
				form.setIdImovel(String.valueOf(imovelAtual.getId()));
				form.setInscricaoImovel(imovelAtual.getInscricaoFormatada().toString());
				httpServletRequest.setAttribute("imovelEncontrado", "true");

			}else{
				form.setIdImovel("");
				form.setInscricaoImovel("FUNCIONALIDADE INEXISTENTE");
				httpServletRequest.setAttribute("imovelNaoEncontrado", "exception");
			}
		}

		if(httpServletRequest.getParameter("carregarCliente") != null && form.getIdCliente() != null
						&& !form.getIdCliente().trim().equalsIgnoreCase("")){

			FiltroCliente filtroCliente = new FiltroCliente();
			filtroCliente.adicionarParametro(new ParametroSimples(FiltroCliente.ID, Integer.valueOf(form.getIdCliente())));
			filtroCliente.adicionarCaminhoParaCarregamentoEntidade(FiltroCliente.CLIENTE_TIPO);
			Cliente clienteAtual = (Cliente) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroCliente,
							Cliente.class.getName()));


			if(clienteAtual != null){
				form.setIdCliente(String.valueOf(clienteAtual.getId()));
				form.setNomeCliente(clienteAtual.getDescricao().toString());
				httpServletRequest.setAttribute("clienteEncontrado", "true");

			}else{
				form.setIdCliente("");
				form.setNomeCliente("CLIENTE INEXISTENTE");
				httpServletRequest.setAttribute("clienteNaoEncontrado", "exception");
			}
		}

		if(httpServletRequest.getParameter("desfazer") != null){
			form.setIdImovel("");
			form.setInscricaoImovel("");

			form.setIdCliente("");
			form.setNomeCliente("");

			form.setIdTipoDocumento("");
		}


		// Retorna o mapeamento contido na variável retorno
		return retorno;

	}



}