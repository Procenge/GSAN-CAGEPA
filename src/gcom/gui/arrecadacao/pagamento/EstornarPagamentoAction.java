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
 * Thiago Silva Toscano de Brito
 * Vivianne Barbosa Sousa
 * 
 * GSANPCG
 * Andr� Rangel Nishimura
 * Eduardo Henrique Bandeira Carneiro da Silva
 * Lucas Daniel Souza de Medeiros
 * Saulo Vasconcelos de Lima
 * Thiago Toscano
 * Virginia Santos de Melo
 *
 * Este programa � software livre; voc� pode redistribu�-lo e/ou
 * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers�o 2 da Licen�a.
 * Este programa � distribu�do na expectativa de ser �til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
 * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
 * PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
 * junto com este programa; se n�o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.gui.arrecadacao.pagamento;

import gcom.arrecadacao.pagamento.FiltroPagamentoHistorico;
import gcom.arrecadacao.pagamento.PagamentoHistorico;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
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
 * [UC1016] Estornar Pagamentos
 * 
 * @author Saulo Lima
 * @date 19/08/2009
 */
public class EstornarPagamentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		HttpSession sessao = httpServletRequest.getSession(false);
		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		Fachada fachada = Fachada.getInstancia();

		// Pega o formul�rio
		EstornarPagamentoActionForm estornarPagamentoActionForm = (EstornarPagamentoActionForm) actionForm;

		String idPagamentoHistoricoString = null;

		String[] objetosSelecionados = estornarPagamentoActionForm.getId();
		if(objetosSelecionados == null || objetosSelecionados.length == 0){
			throw new ActionServletException("atencao.estorno.nao_selecionado");
		}else{
			idPagamentoHistoricoString = objetosSelecionados[0];
		}

		Integer idPagamentoHistorico = Util.converterStringParaInteger(idPagamentoHistoricoString);
		if(idPagamentoHistorico == null){
			throw new ActionServletException("atencao.nao.numerico", null, "C�digo do pagamento");
		}

		// Recupera o Pagamento Hist�rico selecionado pelo usu�rio
		FiltroPagamentoHistorico filtroPagamentoHistoricoBase = new FiltroPagamentoHistorico();
		filtroPagamentoHistoricoBase.adicionarParametro(new ParametroSimples(FiltroPagamentoHistorico.ID, idPagamentoHistorico));
		Collection<PagamentoHistorico> colecaoPagamentoHistoricoBase = fachada.pesquisar(filtroPagamentoHistoricoBase,
						PagamentoHistorico.class.getName());

		if(colecaoPagamentoHistoricoBase == null || colecaoPagamentoHistoricoBase.isEmpty()){
			throw new ActionServletException("atencao.estorno.pagamento_nao_localizado");
		}

		PagamentoHistorico pagamentoHistorico = (PagamentoHistorico) Util.retonarObjetoDeColecao(colecaoPagamentoHistoricoBase);

		fachada.estornarPagamento(pagamentoHistorico, usuarioLogado);

		montarPaginaSucesso(httpServletRequest, "Estorno efetuado com sucesso", "Estornar outro Pagamento",
						"exibirFiltrarPagamentoAction.do?menu=sim&tela=estorno&primeiraVez=nao");

		return retorno;
	}

}
