
package gcom.gui.cobranca;

import gcom.cobranca.FiltroOrgaoExterno;
import gcom.cobranca.OrgaoExterno;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class ExibirAtualizarOrgaoExternoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarOrgaoExterno");

		String idRegistroAtualizacao = (String) httpServletRequest.getAttribute("idRegistroAtualizacao");
		if(idRegistroAtualizacao == null){
			idRegistroAtualizacao = (String) httpServletRequest.getParameter("idRegistroAtualizacao");
		}

		DynaActionForm form = (DynaActionForm) actionForm;

		final Fachada fachada = Fachada.getInstancia();

		FiltroOrgaoExterno filtro = new FiltroOrgaoExterno();
		filtro.adicionarParametro(new ParametroSimples(FiltroOrgaoExterno.ID, Integer.valueOf(idRegistroAtualizacao)));
		Collection colecao = fachada.pesquisar(filtro, OrgaoExterno.class.getName());
		OrgaoExterno bean = (OrgaoExterno) colecao.iterator().next();
		form.set("codigo", bean.getId().toString());
		form.set("descricao", bean.getDescricao());
		form.set("indicadorUso", bean.getIndicadorUso().toString());

		return retorno;

	}

}
