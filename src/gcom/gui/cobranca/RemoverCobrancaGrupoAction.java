
package gcom.gui.cobranca;

import gcom.cobranca.CobrancaGrupo;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.gui.ManutencaoRegistroActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class RemoverCobrancaGrupoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		Fachada fachada = Fachada.getInstancia();

		ManutencaoRegistroActionForm manutencaoRegistroActionForm = (ManutencaoRegistroActionForm) actionForm;

		String[] ids = manutencaoRegistroActionForm.getIdRegistrosRemocao();

		fachada.remover(ids, CobrancaGrupo.class.getName(), null, null);

		montarPaginaSucesso(httpServletRequest, ids.length + " Grupo(s) de Cobrança removido(s) com sucesso.",
						"Realizar outra Manutenção de Grupo de Cobrança", "exibirFiltrarCobrancaGrupoAction.do?menu=sim");

		return retorno;

	}
}