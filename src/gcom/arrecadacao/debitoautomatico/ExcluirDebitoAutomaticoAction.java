
package gcom.arrecadacao.debitoautomatico;

import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExcluirDebitoAutomaticoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		DebitoAutomatico debitoAutomatico = (DebitoAutomatico) sessao.getAttribute("debitoAutomaticoSessao");

		if(debitoAutomatico != null){

			String resultado = getFachada().removerDebitoAutomatico(debitoAutomatico.getImovel().getId().toString(),
							debitoAutomatico.getAgencia().getBanco().getId().toString(), debitoAutomatico.getAgencia().getCodigoAgencia(),
							debitoAutomatico.getIdentificacaoClienteBanco().toString(), debitoAutomatico.getDataOpcaoDebitoContaCorrente(),
							usuarioLogado);

			if(resultado.equals("OK")){
				montarPaginaSucesso(httpServletRequest, "Débito Automático removido com sucesso", "Excluir outro Débito Automático?",
								"exibirExcluirDebitoAutomaticoAction.do?menu=sim", "", "");
				sessao.removeAttribute("debitoAutomaticoSessao");
			}else{
				montarPaginaSucesso(httpServletRequest, resultado, "Voltar", "exibirExcluirDebitoAutomaticoAction.do", "", "");
			}

		}else{
			throw new ActionServletException("atencao.imovel_nao_selecionado");
		}

		return retorno;
	}

}
