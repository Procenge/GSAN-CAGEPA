
package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.Atividade;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AtualizarAtividadeAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		Fachada fachada = Fachada.getInstancia();

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		HttpSession sessao = httpServletRequest.getSession(false);

		Atividade atividadeSessao = (Atividade) sessao.getAttribute("atividade");

		AtividadeActionForm atividadeActionForm = (AtividadeActionForm) actionForm;

		Atividade atividade = new Atividade();
		atividade.setId(Integer.valueOf(atividadeActionForm.getId()));
		atividade.setDescricao(atividadeActionForm.getDescricao());
		atividade.setDescricaoAbreviada(atividadeActionForm.getDescricaoAbreviada());
		atividade.setIndicadorAtividadeUnica(Short.valueOf(atividadeActionForm.getIndicadorAtividadeUnica()));
		atividade.setIndicadorUso(Short.valueOf(atividadeActionForm.getIndicadorUso()));
		if(atividadeActionForm.getValorHora() != null && atividadeActionForm.getValorHora().length() == 0){
			atividade.setValorHora(BigDecimal.valueOf(0));
		}else{
			String valorAux1 = atividadeActionForm.getValorHora().replace(".", "");

			valorAux1 = valorAux1.replace(",", ".");
			atividade.setValorHora(BigDecimal.valueOf(Double.valueOf(valorAux1)));
		}
		atividade.setUltimaAlteracao(new Date());

		fachada.atualizar(atividade);

		montarPaginaSucesso(httpServletRequest, "Atividade de código " + atividade.getId() + " atualizada com sucesso.",
						"Realizar outra Manutenção de Atividade", "exibirFiltrarAtividadeAction.do?menu=sim");

		sessao.removeAttribute("atividade");

		return retorno;
	}
}
