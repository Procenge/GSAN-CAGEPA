
package gcom.gui.cobranca;

import java.util.Date;

import gcom.cobranca.OrgaoExterno;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class AtualizarOrgaoExternoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		DynaActionForm form = (DynaActionForm) actionForm;

		if(form.get("descricao") == null || ((String) form.get("descricao")).equals("")){
			throw new ActionServletException("atencao.informe_campo", "a descrição do orgão externo");
		}

		OrgaoExterno orgao = new OrgaoExterno();

		orgao.setId(Integer.valueOf((String) form.get("codigo")));
		orgao.setDescricao((String) form.get("descricao"));
		orgao.setIndicadorUso(Integer.valueOf((String) form.get("indicadorUso")));
		orgao.setUltimaAlteracao(new Date());

		Fachada fachada = Fachada.getInstancia();
		fachada.atualizar(orgao);

		montarPaginaSucesso(httpServletRequest, "Orgão Externo atualizado com sucesso.", "Realizar outra Manutenção de Orgão Externo",
						"exibirFiltrarOrgaoExternoAction.do?menu=sim");
		return retorno;

	}

}
