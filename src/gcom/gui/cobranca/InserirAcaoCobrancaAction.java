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
 * GSANPCG
 * Eduardo Henrique
 * Virg�nia Melo
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

package gcom.gui.cobranca;

import gcom.cobranca.bean.CobrancaAcaoHelper;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Processamento para inserir o crit�rio da cobran�a e as linhas do criterio da
 * cobran�a
 * 
 * @author S�vio Luiz
 * @date 18/09/2007
 * @author eduardo henrique
 * @date 26/08/2008
 *       Altera��es no [UC0643] para a v0.04
 * @author Virg�nia Melo
 * @date 29/10/2008
 *       Desfazer customiza��es
 */
public class InserirAcaoCobrancaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		AcaoCobrancaActionForm acaoCobrancaActionForm = (AcaoCobrancaActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		Fachada fachada = Fachada.getInstancia();
		Integer idCobrancaAcao = null;

		/*
		 * CobrancaAcaoHelper cobrancaAcaoHelper = new
		 * CobrancaAcaoHelper(acaoCobrancaActionForm.getDescricaoAcao(),
		 * acaoCobrancaActionForm.getIcSuspensaoAbastecimento(),
		 * ""+ConstantesSistema.INDICADOR_USO_ATIVO,
		 * acaoCobrancaActionForm.getIdTipoDocumentoGerado(),
		 * acaoCobrancaActionForm.getIdServicoTipo(),
		 * acaoCobrancaActionForm.getDescricaoServicoTipo(),
		 * acaoCobrancaActionForm.getIdCobrancaEstagio(),
		 * usuarioLogado);
		 * idCobrancaAcao = fachada.inserirAcaoCobranca(cobrancaAcaoHelper);
		 * montarPaginaSucesso(httpServletRequest, "A��o de Cobran�a "
		 * + acaoCobrancaActionForm.getDescricaoAcao() + " inserida com sucesso.",
		 * "Inserir outra A��o de Cobran�a",
		 * "exibirInserirAcaoCobrancaAction.do?menu=sim",
		 * "exibirAtualizarAcaoCobrancaAction.do?idRegistroAtualizar="
		 * + idCobrancaAcao + "&retornoFiltrar=1",
		 * "Atualizar A��o de Cobran�a inserida");
		 */

		Util.validarNumeroMaiorQueOutro("Quantidade de Dias para Realiza��o da A��o", "Quantidade de Dias de Validade da A��o",
						acaoCobrancaActionForm.getQtdDiasRealizacao(), acaoCobrancaActionForm.getNumeroDiasValidade());

		CobrancaAcaoHelper cobrancaAcaoHelper = new CobrancaAcaoHelper(acaoCobrancaActionForm.getDescricaoAcao(), acaoCobrancaActionForm
						.getIcAcaoObrigatoria(), acaoCobrancaActionForm.getIcRepetidaCiclo(), acaoCobrancaActionForm
						.getIcSuspensaoAbastecimento(), acaoCobrancaActionForm.getNumeroDiasValidade(), acaoCobrancaActionForm
						.getQtdDiasRealizacao(), acaoCobrancaActionForm.getNumeroDiasEntreAcoes(), ""
						+ ConstantesSistema.INDICADOR_USO_ATIVO, acaoCobrancaActionForm.getIcDebitosACobrar(), acaoCobrancaActionForm
						.getIcGeraTaxa(), acaoCobrancaActionForm.getOrdemCronograma(), acaoCobrancaActionForm
						.getIcAcrescimosImpontualidade(), acaoCobrancaActionForm.getIdAcaoPredecessora(), acaoCobrancaActionForm
						.getIdSituacaoLigacaoEsgoto(), acaoCobrancaActionForm.getIdTipoDocumentoGerado(), acaoCobrancaActionForm
						.getIdSituacaoLigacaoAgua(), acaoCobrancaActionForm.getIdServicoTipo(), acaoCobrancaActionForm
						.getIdCobrancaCriterio(), acaoCobrancaActionForm.getIcCompoeCronograma(), acaoCobrancaActionForm
						.getIcEmitirBoletimCadastro(), acaoCobrancaActionForm.getIcImoveisSemDebitos(), acaoCobrancaActionForm
						.getNumeroDiasVencimento(), acaoCobrancaActionForm.getDescricaoCobrancaCriterio(), acaoCobrancaActionForm
						.getDescricaoServicoTipo(), acaoCobrancaActionForm.getAcaoCobrancaEfeito(), usuarioLogado, acaoCobrancaActionForm
						.getIdPrimeiraResolucaoDiretoria(), acaoCobrancaActionForm.getIdSegundaResolucaoDiretoria(), acaoCobrancaActionForm
						.getIdTerceiraResolucaoDiretoria(), acaoCobrancaActionForm.getIndicadorConsideraCreditoRealizar(),
						acaoCobrancaActionForm.getIndicadorConsideraGuiaPagamento(), acaoCobrancaActionForm.getNegativador(),
						acaoCobrancaActionForm.getIcClienteUsuarioSemCPFCNPJ(), acaoCobrancaActionForm.getIcEnderecoSemCEP(),
						acaoCobrancaActionForm.getIcEmpresaObrigatoria(), acaoCobrancaActionForm.getIdSituacaoCobranca());

		idCobrancaAcao = fachada.inserirAcaoCobranca(cobrancaAcaoHelper);

		montarPaginaSucesso(httpServletRequest, "A��o de Cobran�a " + acaoCobrancaActionForm.getDescricaoAcao() + " inserida com sucesso.",
						"Inserir outra A��o de Cobran�a", "exibirInserirAcaoCobrancaAction.do?menu=sim",
						"exibirAtualizarAcaoCobrancaAction.do?idRegistroAtualizar=" + idCobrancaAcao + "&retornoFiltrar=1",
						"Atualizar A��o de Cobran�a inserida");
		return retorno;
	}
}