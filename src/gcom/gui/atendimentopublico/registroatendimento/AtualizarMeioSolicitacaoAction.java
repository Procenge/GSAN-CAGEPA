
package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.MeioSolicitacao;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AtualizarMeioSolicitacaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		Fachada fachada = Fachada.getInstancia();

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		HttpSession sessao = httpServletRequest.getSession(false);

		MeioSolicitacaoActionForm meioSolicitacaoActionForm = (MeioSolicitacaoActionForm) actionForm;

		MeioSolicitacao meioSolicitacao = new MeioSolicitacao();
		meioSolicitacao.setId(Integer.valueOf(meioSolicitacaoActionForm.getId()));
		meioSolicitacao.setDescricao(meioSolicitacaoActionForm.getDescricao());
		meioSolicitacao.setDescricaoAbreviada(meioSolicitacaoActionForm.getDescricaoAbreviada());
		meioSolicitacao.setIndicadorLiberacaoDocIdent(Short.valueOf(meioSolicitacaoActionForm.getIndicadorLiberacaoDocIdent()));
		meioSolicitacao.setIndicadorUso(Short.valueOf(meioSolicitacaoActionForm.getIndicadorUso()));
		meioSolicitacao.setUltimaAlteracao(new Date());

		fachada.atualizar(meioSolicitacao);

		montarPaginaSucesso(httpServletRequest, "Meio Solicitação de código " + meioSolicitacao.getId() + " atualizado com sucesso.",
						"Realizar outra Manutenção de Meio Solicitação", "exibirFiltrarMeioSolicitacaoAction.do?menu=sim");

		sessao.removeAttribute("meioSolicitacao");

		return retorno;
	}

}
