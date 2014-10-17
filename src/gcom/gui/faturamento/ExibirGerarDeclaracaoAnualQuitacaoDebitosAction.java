
package gcom.gui.faturamento;

import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
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
 * [UC3013] Gerar Declaração Anual Quitação Débitos
 * 
 * @author Hebert Falcão
 * @created 28/04/2013
 */
public class ExibirGerarDeclaracaoAnualQuitacaoDebitosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("exibirGerarDeclaracaoAnualQuitacaoDebitos");

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		GerarDeclaracaoAnualQuitacaoDebitosActionForm gerarDeclaracaoAnualQuitacaoDebitosActionForm = (GerarDeclaracaoAnualQuitacaoDebitosActionForm) actionForm;

		// Botão Desfazer
		String desfazer = httpServletRequest.getParameter("desfazer");

		if(desfazer != null && desfazer.equalsIgnoreCase("S")){
			// Campos do tipo lista no formulário
			gerarDeclaracaoAnualQuitacaoDebitosActionForm.setIdFaturamentoGrupo("" + ConstantesSistema.NUMERO_NAO_INFORMADO);

			sessao.removeAttribute("colecaoFaturamentoGrupo");
		}

		if(sessao.getAttribute("colecaoFaturamentoGrupo") == null){
			FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();
			filtroFaturamentoGrupo.setCampoOrderBy(FiltroFaturamentoGrupo.DESCRICAO);
			filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection<FaturamentoGrupo> colecaoFaturamentoGrupo = this.getFachada().pesquisar(filtroFaturamentoGrupo,
							FaturamentoGrupo.class.getName());

			if(Util.isVazioOrNulo(colecaoFaturamentoGrupo)){
				throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "FATURAMENTO_GRUPO");
			}else{
				sessao.setAttribute("colecaoFaturamentoGrupo", colecaoFaturamentoGrupo);
			}
		}

		return retorno;
	}

}
