
package gcom.gui.cobranca;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3023] Manter Boleto Banc�rio
 * 
 * @author Hebert Falc�o
 * @date 12/10/2011
 */
public class ExibirTotalizadorBoletoBancarioAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("exibirTotalizadorBoletoBancarioAction");

		// Obt�m a sess�o
		HttpSession sessao = httpServletRequest.getSession(false);

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		BoletoBancarioHelper boletoBancarioHelper = (BoletoBancarioHelper) sessao.getAttribute("boletoBancarioHelper");
		boolean verificarNumeroBoletoCartaCobranca = (Boolean) sessao.getAttribute("verificarNumeroBoletoCartaCobranca");
		boolean desconsiderarParametros = (Boolean) sessao.getAttribute("desconsiderarParametros");

		// Pagina��o
		Collection<BoletoBancarioTotalizadorHelper> colecaoBoletoBancarioTotalizadorHelperAux = fachada
						.pesquisarBoletoBancarioTotalizadorPorImovel(boletoBancarioHelper, verificarNumeroBoletoCartaCobranca,
										desconsiderarParametros, false, ConstantesSistema.NUMERO_NAO_INFORMADO);

		if(Util.isVazioOrNulo(colecaoBoletoBancarioTotalizadorHelperAux)){
			// Nenhuma registro encontrado
			throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "Boleto Banc�rio");
		}

		Integer totalRegistros = colecaoBoletoBancarioTotalizadorHelperAux.size();

		retorno = this.controlarPaginacao(httpServletRequest, retorno, totalRegistros);

		Collection<BoletoBancarioTotalizadorHelper> colecaoBoletoBancarioTotalizadorHelper = fachada
						.pesquisarBoletoBancarioTotalizadorPorImovel(boletoBancarioHelper, verificarNumeroBoletoCartaCobranca,
										desconsiderarParametros, false, (Integer) httpServletRequest.getAttribute("numeroPaginasPesquisa"));

		if(Util.isVazioOrNulo(colecaoBoletoBancarioTotalizadorHelper)){
			throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "Boleto Banc�rio");
		}

		sessao.setAttribute("colecaoBoletoBancarioTotalizadorHelper", colecaoBoletoBancarioTotalizadorHelper);

		return retorno;
	}

}
