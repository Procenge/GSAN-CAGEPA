
package gcom.gui.cobranca;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AtualizarInfracaoPerfilAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		InfracaoPerfilActionForm form = (InfracaoPerfilActionForm) actionForm;

		if(form.getIdCategoria() == null || form.getIdCategoria().equals("-1")){
			throw new ActionServletException("atencao.informe_campo", "a descrição do tipo da infração");
		}

		if(form.getIdSubcategoria() == null || form.getIdSubcategoria().equals("-1")){
			throw new ActionServletException("atencao.informe_campo", "a descrição do tipo da infração");
		}

		if(form.getIdImovelPerfil() == null || form.getIdImovelPerfil().equals("-1")){
			throw new ActionServletException("atencao.informe_campo", "a descrição do tipo da infração");
		}

		if(form.getIdInfracaoTipo() == null || form.getIdInfracaoTipo().equals("-1")){
			throw new ActionServletException("atencao.informe_campo", "a descrição do tipo da infração");
		}

		Fachada fachada = Fachada.getInstancia();
		fachada.atualizarInfracaoPerfil(Integer.valueOf(form.getId()), Integer.valueOf(form.getIdCategoria()), Integer.valueOf(form
						.getIdSubcategoria()), Integer.valueOf(form.getIdImovelPerfil()), Integer.valueOf(form.getIdInfracaoTipo()));

		montarPaginaSucesso(httpServletRequest, "Perfil de Infração atualizado com sucesso.",
						"Realizar outra Manutenção de Perfil de Infração", "exibirFiltrarInfracaoPerfilAction.do?menu=sim");
		return retorno;
	}
}
