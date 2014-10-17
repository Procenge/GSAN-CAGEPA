
package gcom.gui.cobranca;

import gcom.cobranca.InfracaoLigacaoSituacao;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author anishimura
 */
public class AtualizarInfracaoLigacaoSituacaoAction
				extends GcomAction {

	/**
	 * @date fevereiro/2011
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		InfracaoLigacaoSituacaoActionForm form = (InfracaoLigacaoSituacaoActionForm) actionForm;

		if(form.getDescricao() == null || form.getDescricao().equals("")){
			throw new ActionServletException("atencao.informe_campo", "a descrição da situacao da infração da ligação ");
		}

		if(form.getDescricaoAbreviada() == null || form.getDescricaoAbreviada().equals("")){
			throw new ActionServletException("atencao.informe_campo", "a descrição abreviada da situação da infração da ligação");
		}

		if(form.getIndicadorUso() == null || form.getIndicadorUso().equals("")){
			throw new ActionServletException("atencao.informe_campo", "Indicador de Uso");
		}
		InfracaoLigacaoSituacao bean = new InfracaoLigacaoSituacao();

		bean.setId(Integer.valueOf(form.getId()));
		bean.setDescricao(form.getDescricao());
		bean.setDescricaoAbreviada(form.getDescricaoAbreviada());
		bean.setIndicadorUso(Short.valueOf((String) form.getIndicadorUso()));

		Fachada fachada = Fachada.getInstancia();
		fachada.atualizarInfracaoLigacaoSituacao(bean);

		montarPaginaSucesso(httpServletRequest, "Infraçao Ligacao Situação atualizada com sucesso.",
						"Realizar outra Manutenção de Infraçao Ligacao Situação", "exibirFiltrarInfracaoLigacaoSituacaoAction.do?menu=sim");
		return retorno;
	}
}
