
package gcom.gui.cobranca;

import gcom.cobranca.InfracaoTipo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AtualizarInfracaoTipoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");
		InfracaoTipoActionForm form = (InfracaoTipoActionForm) actionForm;
		if(form.getDescricao() == null || form.getDescricao().equals("")){
			throw new ActionServletException("atencao.informe_campo", "a descri��o do tipo da infra��o");
		}

		if(form.getDescricaoAbreviada() == null || form.getDescricaoAbreviada().equals("")){
			throw new ActionServletException("atencao.informe_campo", "a descri��o abreviada do tipo da infra��o");
		}

		if(form.getIndicadorUso() == null || form.getIndicadorUso().equals("")){
			throw new ActionServletException("atencao.informe_campo", "Indicador de Uso");
		}

		InfracaoTipo bean = new InfracaoTipo();

		bean.setId(Integer.valueOf(form.getId()));
		bean.setDescricao(form.getDescricao());
		bean.setDescricaoAbreviada(form.getDescricaoAbreviada());
		bean.setIndicadorUso(Short.valueOf(form.getIndicadorUso()));

		Fachada fachada = Fachada.getInstancia();
		fachada.atualizarInfracaoTipo(bean);

		montarPaginaSucesso(httpServletRequest, "Tipo de Infra��o atualizado com sucesso.",
						"Realizar outra Manuten��o de Tipo de Infra��o", "exibirFiltrarInfracaoTipoAction.do?menu=sim");
		return retorno;
	}
}
