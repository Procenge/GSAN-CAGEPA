
package gcom.gui.cobranca;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gcom.cobranca.FiltroCobrancaAcaoAtividadeCronograma;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;

public class PesquisarCronogramaAcaoCobrancaAction
				extends GcomAction {

	/**
	 * @author Andre Nishimura
	 * @date 22/06/2010
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("retornarPesquisarCronogramaAcaoCobranca");

		HttpSession sessao = httpServletRequest.getSession(false);
		PesquisarCronogramaAcaoCobrancaActionForm filtrarActionForm = (PesquisarCronogramaAcaoCobrancaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		FiltroCobrancaAcaoAtividadeCronograma filtroCobrancaAcaoAtividadeCronograma = fachada
						.construirFiltroCobrancaAcaoAtividadeCronograma(filtrarActionForm.getPeriodoReferenciaCobrancaInicial(),
										filtrarActionForm.getPeriodoReferenciaCobrancaFinal(), filtrarActionForm.getGrupoCobranca(),
										filtrarActionForm.getAcaoCobranca(), filtrarActionForm.getAtividadeCobranca(), "", "", "", "", "",
										"", "", "", "", "", "", "", "", "");

		sessao.setAttribute("filtroCobrancaAcaoAtividadeCronograma", filtroCobrancaAcaoAtividadeCronograma);

		sessao.setAttribute("filtrarComandosAcaoCobrancaCronogramaActionForm", filtrarActionForm);

		return retorno;
	}
}