/*
 * Copyright (C) 2007-2007 the GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
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
 * GSAN - Sistema Integrado de Gestão de Serviços de Saneamento
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

package gcom.gui.faturamento.guiapagamento;

import gcom.fachada.Fachada;
import gcom.faturamento.bean.FiltroGuiaPagamentoHelper;
import gcom.faturamento.bean.GuiaPagamentoHelper;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.relatorio.faturamento.RelatorioManterGuiaPagamentoParametrosHelper;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.parametrizacao.faturamento.ParametroFaturamento;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0188] Manter Guia de Pagamento
 * 
 * @author Anderson Italo
 * @date 31/10/2011
 */
public class ExibirManterGuiaPagamentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterGuiaPagamento");
		HttpSession sessao = httpServletRequest.getSession(false);
		Fachada fachada = Fachada.getInstancia();
		FiltroGuiaPagamentoHelper filtroGuiaPagamentoHelper = null;

		Integer idImovel = null;
		Integer codigoCliente = null;

		// Obtém o filtro da pesquisa
		if(sessao.getAttribute("filtroGuiaPagamentoHelper") != null){

			filtroGuiaPagamentoHelper = (FiltroGuiaPagamentoHelper) sessao.getAttribute("filtroGuiaPagamentoHelper");
		}else if(httpServletRequest.getParameter("idImovel") != null){
			idImovel = new Integer(httpServletRequest.getParameter("idImovel"));
			filtroGuiaPagamentoHelper = new FiltroGuiaPagamentoHelper();
			filtroGuiaPagamentoHelper.setIdImovel(idImovel);
			sessao.setAttribute("filtroGuiaPagamentoHelper", filtroGuiaPagamentoHelper);
		}else if(httpServletRequest.getParameter("codigoCliente") != null){
			codigoCliente = new Integer(httpServletRequest.getParameter("codigoCliente"));
			filtroGuiaPagamentoHelper = new FiltroGuiaPagamentoHelper();
			filtroGuiaPagamentoHelper.setCodigoClienteResponsavel(codigoCliente);
			sessao.setAttribute("filtroGuiaPagamentoHelper", filtroGuiaPagamentoHelper);
		}

		if(sessao.getAttribute("relatorioManterGuiaPagamentoParametrosHelper") == null){
			RelatorioManterGuiaPagamentoParametrosHelper relatorioManterGuiaPagamentoParametrosHelper = new RelatorioManterGuiaPagamentoParametrosHelper();
			if(idImovel != null){
				String inscricaoImovel = fachada.pesquisarImovel(idImovel).getInscricaoFormatada();
				relatorioManterGuiaPagamentoParametrosHelper.setInscricaoImovel(inscricaoImovel);
			}else if(codigoCliente != null){
				relatorioManterGuiaPagamentoParametrosHelper.setCodigoClienteResponsavel(String.valueOf(codigoCliente));
			}
			sessao.setAttribute("relatorioManterGuiaPagamentoParametrosHelper", relatorioManterGuiaPagamentoParametrosHelper);
		}

		if(retorno != null && filtroGuiaPagamentoHelper != null){

			// Controle de Paginação
			Integer totalRegistros = fachada.pesquisarTotalRegistrosManterGuiaPagamento(filtroGuiaPagamentoHelper);
			retorno = controlarPaginacao(httpServletRequest, retorno, totalRegistros);
			Integer pagina = ((Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa"));
			filtroGuiaPagamentoHelper.setNumeroPagina(pagina);
			Collection<GuiaPagamentoHelper> colecaoGuiaPagamento = fachada.pesquisarRegistrosManterGuiaPagamento(filtroGuiaPagamentoHelper);

			// [FS0012] - Verificar existência de guias de pagamento
			if(Util.isVazioOrNulo(colecaoGuiaPagamento)){

				throw new ActionServletException("atencao.pesquisa.nenhumresultado");
			}

			String identificadorAtualizar = (String) sessao.getAttribute("indicadorAtualizar");

			if(!Util.isVazioOuBranco(identificadorAtualizar) && colecaoGuiaPagamento.size() == 1){

				// caso o resultado do filtro só retorne um registro
				// e o check box Atualizar estiver selecionado
				// o sistema não exibe a tela de manter, exibe a de atualizar
				retorno = actionMapping.findForward("exibirAtualizarGuiaPagamento");
				GuiaPagamentoHelper guiaPagamento = (GuiaPagamentoHelper) colecaoGuiaPagamento.iterator().next();
				sessao.setAttribute("idRegistroAtualizacao", guiaPagamento.getNumeroGuia());
				sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarGuiaPagamentoAction.do");
			}else{

				sessao.setAttribute("colecaoGuiaPagamento", colecaoGuiaPagamento);
				sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirManterGuiaPagamentoAction.do");

			}

		}
		
		// Exibir o campo "No. Contrato Parcel. Órgão Público"
		try{
		if(!ParametroFaturamento.P_TIPO_DEBITO_PARCELAMENTO_ORGAO_PUBLICO.executar().equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
				sessao.setAttribute("exibirNuContratoParcOrgaoPublico", ConstantesSistema.SIM);
			}else{
				sessao.removeAttribute("exibirNuContratoParcOrgaoPublico");
			}

		}catch(ControladorException e){
			throw new ActionServletException(e.getMessage(), e);
		}

		return retorno;

	}
}
