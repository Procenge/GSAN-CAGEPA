
package gcom.gui.cadastro.localidade;

import gcom.cadastro.localidade.Quadra;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirProcessarAtualizarQuadraRotaImoveisAction
				extends GcomAction {

	/**
	 * [UC0025] Manter Quadra
	 * [SB0003] - Verificar Mudança de Rota da Quadra
	 * 
	 * @author Anderson Italo
	 * @date 23/01/2012
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirProcessarAtualizarQuadraRotaImoveis");
		AtualizarQuadraActionForm formulario = (AtualizarQuadraActionForm) actionForm;
		HttpSession sessao = httpServletRequest.getSession(false);
		Fachada fachada = Fachada.getInstancia();

		Integer quantidadeImoveisRotaIgualAnterior = 0;
		Integer quantidadeImoveisRotaDiferenteAnterior = 0;

		Integer quantidadeImoveisDistritoOperacionalIgualAnterior = 0;
		Integer quantidadeImoveisDistritoOperacionalDiferenteAnterior = 0;

		Quadra quadra = (Quadra) sessao.getAttribute("quadraRelatorioImoveis");
		
		// Flag que indica a alteração da rota da quadra
		String rotaAlterada = (String) sessao.getAttribute("rotaAlterada");

		if(!Util.isVazioOuBranco(rotaAlterada)){

			quantidadeImoveisRotaIgualAnterior = fachada.obterTotalImoveisRotaIgualAnteriorQuadra(quadra.getId(),
							Util.obterInteger(formulario.getIdRotaAnterior()));
			quantidadeImoveisRotaDiferenteAnterior = fachada.obterTotalImoveisRotaDiferenteAnteriorQuadra(quadra.getId(),
							Util.obterInteger(formulario.getIdRotaAnterior()));

			formulario.setQuantidadeImoveisRotaIgualAnterior(quantidadeImoveisRotaIgualAnterior.toString());
			formulario.setQuantidadeImoveisRotaDiferenteAnterior(quantidadeImoveisRotaDiferenteAnterior.toString());
		}

		// Flag que indica a alteração do distrito operacional da quadra
		String distritoOperacionalAlterado = (String) sessao.getAttribute("distritoOperacionalAlterado");

		if(!Util.isVazioOuBranco(distritoOperacionalAlterado)){

			quantidadeImoveisDistritoOperacionalIgualAnterior = fachada.obterTotalImoveisDistritoOperacionalIgualAnteriorQuadra(
							quadra.getId(), Util.obterInteger(formulario.getDistritoOperacionalIDAnterior()));
			quantidadeImoveisDistritoOperacionalDiferenteAnterior = fachada.obterTotalImoveisDistritoOperacionalDiferenteAnteriorQuadra(
							quadra.getId(), Util.obterInteger(formulario.getDistritoOperacionalIDAnterior()));

			formulario.setQuantidadeImoveisDistritoOperacionalIgualAnterior(quantidadeImoveisDistritoOperacionalIgualAnterior.toString());
			formulario.setQuantidadeImoveisDistritoOperacionalDiferenteAnterior(quantidadeImoveisDistritoOperacionalDiferenteAnterior
							.toString());

		}

		formulario.setIndicadorSubstituirRota("");
		formulario.setIndicadorSubstituirDistritoOperacional("");

		return retorno;
	}
}
