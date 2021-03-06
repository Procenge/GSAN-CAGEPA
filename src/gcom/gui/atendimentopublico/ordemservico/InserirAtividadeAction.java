
package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.Atividade;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;

import java.math.BigDecimal;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InserirAtividadeAction
				extends GcomAction {

	@Override
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		AtividadeActionForm atividadeActionForm = (AtividadeActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		String descricao = atividadeActionForm.getDescricao();
		String descricaoAbreviada = atividadeActionForm.getDescricaoAbreviada();
		Short indicadorAtividadeUnica = Short.valueOf(atividadeActionForm.getIndicadorAtividadeUnica());
		BigDecimal valorHora = BigDecimal.valueOf(0);
		
		if(atividadeActionForm.getValorHora() != null && atividadeActionForm.getValorHora().length() != 0){
			String valorAux1 = atividadeActionForm.getValorHora().replace(".", "");

			valorAux1 = valorAux1.replace(",", ".");
			valorHora = BigDecimal.valueOf(Double.valueOf(valorAux1));
		}

		Atividade atividade = new Atividade();
		atividade.setDescricao(descricao);
		atividade.setDescricaoAbreviada(descricaoAbreviada);
		atividade.setIndicadorUso(ConstantesSistema.SIM);
		atividade.setIndicadorAtividadeUnica(indicadorAtividadeUnica);
		atividade.setUltimaAlteracao(new Date());
		atividade.setValorHora(valorHora);

		Integer idAtividade;
		idAtividade = (Integer) fachada.inserir(atividade);

		// monta p�gina de sucesso
		montarPaginaSucesso(httpServletRequest, "Atividade " + atividadeActionForm.getDescricao() + " inserida com sucesso.",
						"Inserir outra Atividade", "exibirInserirAtividadeAction.do?menu=sim",
						"exibirAtualizarAtividadeAction.do?idRegistroAtualizar=" + idAtividade + "&retornoFiltrar=1",
						"Atualizar Atividade inserida");

		return retorno;
	}
}
