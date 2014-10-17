/*
 * Copyright (C) 2007-2007 the GSAN - Sistema Integrado de Gest�o de Servi�os de Saneamento
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
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN - Sistema Integrado de Gest�o de Servi�os de Saneamento
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
 * Hiroshi Nishimura Goncalves
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

package gcom.gui.relatorio.seguranca;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.seguranca.acesso.FiltrarOperacaoActionForm;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.seguranca.RelatorioManterOperacao;
import gcom.seguranca.acesso.FiltroOperacao;
import gcom.seguranca.acesso.Operacao;
import gcom.seguranca.acesso.OperacaoTipo;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.Util;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0285] Manter Operacao
 * 
 * @author Hiroshi N. Gon�alves
 * @date 09/10/2012
 */
public class GerarRelatorioOperacaoManterAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;
		HttpSession sessao = httpServletRequest.getSession(false);
		FiltrarOperacaoActionForm filtrarOperacaoActionForm = (FiltrarOperacaoActionForm) sessao.getAttribute("filtrarOperacaoActionForm");
		FiltroOperacao filtroOperacao = (FiltroOperacao) sessao.getAttribute("filtroOperacao");
		Collection<OperacaoTipo> colecaoOperacaoTipo = (Collection) sessao.getAttribute("colecaoOperacaoTipo");

		Fachada fachada = Fachada.getInstancia();

		filtroOperacao.adicionarCaminhoParaCarregamentoEntidade("funcionalidade");
		filtroOperacao.adicionarCaminhoParaCarregamentoEntidade("operacaoTipo");
		filtroOperacao.adicionarCaminhoParaCarregamentoEntidade("operacaoPesquisa");
		filtroOperacao.adicionarCaminhoParaCarregamentoEntidade("tabelaColuna");
		filtroOperacao.adicionarCaminhoParaCarregamentoEntidade(FiltroOperacao.ARGUMENTO_PESQUISA);

		Collection colecaoResultado = fachada.pesquisar(filtroOperacao, Operacao.class.getName());

		if(colecaoResultado.isEmpty()){
			// N�o existem dados para a exibi��o do relat�rio.
			throw new ActionServletException("atencao.relatorio.vazio");
		}

		RelatorioManterOperacao relatorioManterOperacao = new RelatorioManterOperacao((Usuario) (httpServletRequest.getSession(false))
						.getAttribute("usuarioLogado"));
		// relatorioManterFeriado.addParametro("parametrosfiltroferiado", form);
		relatorioManterOperacao.addParametro("colecaoResultado", colecaoResultado);

		String tipoRelatorio = httpServletRequest.getParameter("tipoRelatorio");
		if(tipoRelatorio == null){

			tipoRelatorio = TarefaRelatorio.TIPO_PDF + "";
		}

		relatorioManterOperacao.addParametro("tipoFormatoRelatorio", Util.obterInteger(tipoRelatorio));
		relatorioManterOperacao.addParametro("filtrarOperacaoActionForm", filtrarOperacaoActionForm);
		relatorioManterOperacao.addParametro("colecaoOperacaoTipo", colecaoOperacaoTipo);

		retorno = processarExibicaoRelatorio(relatorioManterOperacao, tipoRelatorio, httpServletRequest, httpServletResponse, actionMapping);

		return retorno;
	}
}