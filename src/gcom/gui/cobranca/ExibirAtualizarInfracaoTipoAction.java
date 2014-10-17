
package gcom.gui.cobranca;

import gcom.cobranca.FiltroInfracaoTipo;
import gcom.cobranca.InfracaoTipo;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @date fevereiro/2011
 * @author anishimura
 */
public class ExibirAtualizarInfracaoTipoAction
				extends GcomAction {

	/**
	 * @date fevereiro/2011
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirAtualizarInfracaoTipo");

		String idRegistroAtualizacao = (String) httpServletRequest.getAttribute("idRegistroAtualizacao");
		if(idRegistroAtualizacao == null){
			idRegistroAtualizacao = (String) httpServletRequest.getParameter("idRegistroAtualizacao");
		}

		InfracaoTipoActionForm form = (InfracaoTipoActionForm) actionForm;

		final Fachada fachada = Fachada.getInstancia();

		FiltroInfracaoTipo filtro = new FiltroInfracaoTipo();
		filtro.adicionarParametro(new ParametroSimples(FiltroInfracaoTipo.ID, Integer.valueOf(idRegistroAtualizacao)));
		Collection colecao = fachada.pesquisar(filtro, InfracaoTipo.class.getName());
		InfracaoTipo bean = (InfracaoTipo) colecao.iterator().next();
		form.setId(bean.getId().toString());
		form.setDescricao(bean.getDescricao());
		form.setDescricaoAbreviada(bean.getDescricaoAbreviada());
		form.setIndicadorUso(bean.getIndicadorUso().toString());

		return retorno;

	}
}