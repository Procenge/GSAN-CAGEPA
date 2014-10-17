
package gcom.gui.cobranca;

import gcom.cobranca.InfracaoImovelSituacao;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AtualizarInfracaoImovelSituacaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");
		InfracaoImovelSituacaoActionForm form = (InfracaoImovelSituacaoActionForm) actionForm;
		if(form.getDescricao() == null || form.getDescricao().equals("")){
			throw new ActionServletException("atencao.informe_campo", "a descri��o da situacao da infra�ao do imovel");
		}

		if(form.getDescricaoAbreviada() == null || form.getDescricaoAbreviada().equals("")){
			throw new ActionServletException("atencao.informe_campo", "a descri��o abreviada da situa��o da infra��o do imovel");
		}

		if(form.getIndicadorUso() == null || form.getIndicadorUso().equals("")){
			throw new ActionServletException("atencao.informe_campo", "Indicador de Uso");
		}

		InfracaoImovelSituacao bean = new InfracaoImovelSituacao();

		bean.setId(Integer.valueOf(form.getId()));
		bean.setDescricao(form.getDescricao());
		bean.setDescricaoAbreviada(form.getDescricaoAbreviada());
		bean.setIndicadorUso(Short.valueOf((String) form.getIndicadorUso()));

		Fachada fachada = Fachada.getInstancia();
		fachada.atualizarInfracaoImovelSituacao(bean);

		montarPaginaSucesso(httpServletRequest, "Infracao Imovel Situa��o atualizado com sucesso.",
						"Realizar outra Manuten��o de Infracao Imovel Situacao", "exibirFiltrarInfracaoImovelSituacaoAction.do?menu=sim");
		return retorno;
	}
}
