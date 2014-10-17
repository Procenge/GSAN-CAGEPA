
package gcom.gui.cobranca.contrato;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gcom.cobranca.contrato.CobrancaContratoRemuneracaoPorProdutividade;
import gcom.gui.GcomAction;

public class RemoverRemuneracaoProdutividadeAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward(httpServletRequest.getParameter("mapeamento"));
		HttpSession sessao = httpServletRequest.getSession(false);

		String posicaoLinha = httpServletRequest.getParameter("posicaoLinha");
		Collection<CobrancaContratoRemuneracaoPorProdutividade> colecaoRemuracaoProdutividade = (Collection<CobrancaContratoRemuneracaoPorProdutividade>) sessao
						.getAttribute("colecaoRemuneracaoProdutividade");

		if(colecaoRemuracaoProdutividade != null && !colecaoRemuracaoProdutividade.isEmpty()){
			int posicao = Integer.valueOf(posicaoLinha).intValue();
			int count = 0;

			Collection<CobrancaContratoRemuneracaoPorProdutividade> newColecaoCobrancaContratoServicoValor = new ArrayList<CobrancaContratoRemuneracaoPorProdutividade>();
			for(CobrancaContratoRemuneracaoPorProdutividade ccsv : colecaoRemuracaoProdutividade){
				if(posicao != count){
					newColecaoCobrancaContratoServicoValor.add(ccsv);
				}
				count++;
			}

			sessao.setAttribute("colecaoRemuneracaoProdutividade", newColecaoCobrancaContratoServicoValor);
		}

		return retorno;
	}
}
