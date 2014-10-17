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
 * GSANPCG
 * Eduardo Henrique
 * Virgínia Melo
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

package gcom.gui.cobranca;

import gcom.cobranca.CobrancaAcao;
import gcom.cobranca.bean.CobrancaAcaoHelper;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Processamento para inserir o critério da cobrança e as linhas do criterio da
 * cobrança
 * 
 * @author Sávio Luiz
 * @date 18/09/2007
 * @author eduardo henrique
 * @date 26/08/2008
 *       Alterações no [UC0645] para a v0.04
 * @author Virgínia Melo
 * @date 31/10/2008
 *       Desfazer alterações para a v0.06
 */
public class AtualizarAcaoCobrancaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		AcaoCobrancaAtualizarActionForm acaoCobrancaAtualizarActionForm = (AcaoCobrancaAtualizarActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		Fachada fachada = Fachada.getInstancia();

		CobrancaAcao cobrancaAcao = (CobrancaAcao) sessao.getAttribute("cobrancaAcao");

		Util.validarNumeroMaiorQueOutro("Quantidade de Dias para Realização da Ação", "Quantidade de Dias de Validade da Ação",
						acaoCobrancaAtualizarActionForm.getQtdDiasRealizacao(), acaoCobrancaAtualizarActionForm.getNumeroDiasValidade());

		CobrancaAcaoHelper cobrancaAcaoHelper = new CobrancaAcaoHelper(acaoCobrancaAtualizarActionForm.getDescricaoAcao(),
						acaoCobrancaAtualizarActionForm.getIcAcaoObrigatoria(), acaoCobrancaAtualizarActionForm.getIcRepetidaCiclo(),
						acaoCobrancaAtualizarActionForm.getIcSuspensaoAbastecimento(), acaoCobrancaAtualizarActionForm
										.getNumeroDiasValidade(), acaoCobrancaAtualizarActionForm.getQtdDiasRealizacao(),
						acaoCobrancaAtualizarActionForm.getNumeroDiasEntreAcoes(), acaoCobrancaAtualizarActionForm.getIcUso(),
						acaoCobrancaAtualizarActionForm.getIcDebitosACobrar(), acaoCobrancaAtualizarActionForm.getIcGeraTaxa(),
						acaoCobrancaAtualizarActionForm.getOrdemCronograma(), acaoCobrancaAtualizarActionForm
										.getIcAcrescimosImpontualidade(), acaoCobrancaAtualizarActionForm.getIdAcaoPredecessora(),
						acaoCobrancaAtualizarActionForm.getIdSituacaoLigacaoEsgoto(), acaoCobrancaAtualizarActionForm
										.getIdTipoDocumentoGerado(), acaoCobrancaAtualizarActionForm.getIdSituacaoLigacaoAgua(),
						acaoCobrancaAtualizarActionForm.getIdServicoTipo(), acaoCobrancaAtualizarActionForm.getIdCobrancaCriterio(),
						acaoCobrancaAtualizarActionForm.getIcCompoeCronograma(), acaoCobrancaAtualizarActionForm
										.getIcEmitirBoletimCadastro(), acaoCobrancaAtualizarActionForm.getIcImoveisSemDebitos(),
						acaoCobrancaAtualizarActionForm.getNumeroDiasVencimento(), acaoCobrancaAtualizarActionForm
										.getDescricaoCobrancaCriterio(), acaoCobrancaAtualizarActionForm.getDescricaoServicoTipo(),
						acaoCobrancaAtualizarActionForm.getAcaoCobrancaEfeito(), usuarioLogado, acaoCobrancaAtualizarActionForm
										.getIdPrimeiraResolucaoDiretoria(), acaoCobrancaAtualizarActionForm
										.getIdSegundaResolucaoDiretoria(), acaoCobrancaAtualizarActionForm
										.getIdTerceiraResolucaoDiretoria(), acaoCobrancaAtualizarActionForm
										.getIndicadorConsideraCreditoRealizar(), acaoCobrancaAtualizarActionForm
										.getIndicadorConsideraGuiaPagamento(), acaoCobrancaAtualizarActionForm.getNegativador(),
						acaoCobrancaAtualizarActionForm.getIcClienteUsuarioSemCPFCNPJ(), acaoCobrancaAtualizarActionForm
										.getIcEnderecoSemCEP(), acaoCobrancaAtualizarActionForm.getIcEmpresaObrigatoria(), 
										acaoCobrancaAtualizarActionForm.getIdSituacaoCobranca());

		fachada.atualizarAcaoCobranca(cobrancaAcao, cobrancaAcaoHelper);

		montarPaginaSucesso(httpServletRequest, "Ação de Cobrança " + acaoCobrancaAtualizarActionForm.getDescricaoAcao()
						+ " atualizada com sucesso.", "Realizar outra Manutenção de Ação de Cobrança",
						"exibirFiltrarAcaoCobrancaAction.do?menu=sim");

		sessao.removeAttribute("voltar");
		sessao.removeAttribute("cobrancaAcao");
		sessao.removeAttribute("colecaoLigacaoEsgotoSituacao");
		sessao.removeAttribute("colecaoLigacaoAguaSituacao");
		sessao.removeAttribute("colecaoDocumentoTipo");
		sessao.removeAttribute("colecaoAcaoPredecessora");

		return retorno;
	}
}
