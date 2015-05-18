
package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.OsProgramNaoEncerMotivo;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AtualizarOsProgramNaoEncerMotivoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		Fachada fachada = Fachada.getInstancia();

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		HttpSession sessao = httpServletRequest.getSession(false);

		AtualizarOsProgramNaoEncerMotivoActionForm form = (AtualizarOsProgramNaoEncerMotivoActionForm) actionForm;

		OsProgramNaoEncerMotivo osProgramNaoEncerMotivo = new OsProgramNaoEncerMotivo();
		osProgramNaoEncerMotivo.setId(Integer.valueOf(form.getId()));
		osProgramNaoEncerMotivo.setDescricao(form.getDescricao());
		osProgramNaoEncerMotivo.setDescricaoAbreviada(form.getDescricaoAbreviada());
		osProgramNaoEncerMotivo.setIndicadorVisitaImprodutiva(Short.valueOf(form.getIndicadorVisitaImprodutiva()));
		osProgramNaoEncerMotivo.setIndicadorCobraVisitaImprodutiva(Short.valueOf(form.getIndicadorCobraVisitaImprodutiva()));
		osProgramNaoEncerMotivo.setIndicadorUso(Short.valueOf(form.getIndicadorUso()));
		osProgramNaoEncerMotivo.setUltimaAlteracao(new Date());

		fachada.atualizar(osProgramNaoEncerMotivo);

		montarPaginaSucesso(httpServletRequest, "Motivo do não encerramento da OS de código " + osProgramNaoEncerMotivo.getId()
						+ " atualizada com sucesso.", "Realizar outra Manutenção de Motivo do não encerramento da OS",
						"exibirFiltrarOsProgramNaoEncerMotivoAction.do?menuPrincipal=sim&menu=sim");

		sessao.removeAttribute("atividade");

		return retorno;
	}
}
