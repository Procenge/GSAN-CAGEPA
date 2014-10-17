
package gcom.gui.atendimentopublico.registroatendimento;

import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirPesquisarMensagemTipoSolicitacaoEspecificacaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		HttpSession sessao = httpServletRequest.getSession(false);

		ActionForward retorno = actionMapping.findForward("pesquisarMensagemTipoSolicitacaoEspecificacao");

		PesquisarMensagemTipoSolicitacaoEspecificacaoActionForm form = (PesquisarMensagemTipoSolicitacaoEspecificacaoActionForm) actionForm;
		form.setDescricao("");
		form.setTipoPesquisa(ConstantesSistema.TIPO_PESQUISA_INICIAL.toString());

		if(httpServletRequest.getParameter("caminhoRetornoTelaPesquisaMensagemTipoSolicitacaoEspecificacao") != null){
			sessao.setAttribute("caminhoRetornoTelaPesquisaMensagemTipoSolicitacaoEspecificacao", httpServletRequest
							.getParameter("caminhoRetornoTelaPesquisaMensagemTipoSolicitacaoEspecificacao"));
		}

		return retorno;
	}

}
