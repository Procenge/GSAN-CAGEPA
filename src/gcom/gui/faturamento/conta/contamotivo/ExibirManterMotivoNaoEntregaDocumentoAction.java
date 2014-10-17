
package gcom.gui.faturamento.conta.contamotivo;

import gcom.cobranca.FiltroMotivoNaoEntregaDocumento;
import gcom.faturamento.conta.MotivoNaoEntregaDocumento;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirManterMotivoNaoEntregaDocumentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterMotivoNaoEntregaDocumento");
		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroMotivoNaoEntregaDocumento filtroMotivoNaoEntregaDocumento = new FiltroMotivoNaoEntregaDocumento();
		if(sessao.getAttribute("filtroMotivoNaoEntregaDocumento") != null){
			filtroMotivoNaoEntregaDocumento = (FiltroMotivoNaoEntregaDocumento) sessao.getAttribute("filtroMotivoNaoEntregaDocumento");
		}

		// Componente de Paginação
		Map resultado = controlarPaginacao(httpServletRequest, retorno, filtroMotivoNaoEntregaDocumento, MotivoNaoEntregaDocumento.class
						.getName());

		Collection colecao = (Collection) resultado.get("colecaoRetorno");

		sessao.setAttribute("colecao", colecao);

		retorno = (ActionForward) resultado.get("destinoActionForward");

		// [FS0001] Verificar existência de dados
		if(colecao == null || colecao.isEmpty()){
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		return retorno;
	}
}
