
package gcom.gui.cobranca;

import gcom.cobranca.CobrancaGrupo;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class AtualizarCobrancaGrupoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		CobrancaGrupoActionForm cobrancaGrupoActionForm = (CobrancaGrupoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		CobrancaGrupo cobrancaGrupo = (CobrancaGrupo) sessao.getAttribute("cobrancaGrupo");

		// Atualiza a entidade com os valores do formul�rio

		cobrancaGrupo.setDescricao(cobrancaGrupoActionForm.getDescricao().trim());
		cobrancaGrupo.setDescricaoAbreviada(cobrancaGrupoActionForm.getDescAbreviada().trim());
		cobrancaGrupo.setIndicadorUso(Short.parseShort(cobrancaGrupoActionForm.getIndicadorUso()));

		cobrancaGrupo.setAnoMesReferencia(null);
		String mesAno = cobrancaGrupoActionForm.getAnoMesReferencia();
		if(mesAno != null && !mesAno.trim().equals("")){
			String mes = mesAno.substring(0, 2);
			String ano = mesAno.substring(3, 7);
			String anoMes = ano + "" + mes;
			cobrancaGrupo.setAnoMesReferencia(new Integer(anoMes));

		}

		fachada.atualizar(cobrancaGrupo);

		// Monta a p�gina de sucesso
		montarPaginaSucesso(httpServletRequest, "Grupo de Cobran�a de c�digo " + cobrancaGrupo.getId() + " atualizado com sucesso.",
						"Realizar outra Manuten��o de Grupo de Cobran�a", "exibirFiltrarCobrancaGrupoAction.do?menu=sim");

		return retorno;
	}
}
