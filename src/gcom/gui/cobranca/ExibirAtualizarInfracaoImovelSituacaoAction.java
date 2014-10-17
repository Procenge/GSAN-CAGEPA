
package gcom.gui.cobranca;

import gcom.cobranca.FiltroInfracaoImovelSituacao;
import gcom.cobranca.InfracaoImovelSituacao;
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
 * @author anishimura
 */
public class ExibirAtualizarInfracaoImovelSituacaoAction
				extends GcomAction {

	/**
	 * @date fevereiro/2011
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirAtualizarInfracaoImovelSituacao");

		String idRegistroAtualizacao = (String) httpServletRequest.getAttribute("idRegistroAtualizacao");
		if(idRegistroAtualizacao == null){
			idRegistroAtualizacao = (String) httpServletRequest.getParameter("idRegistroAtualizacao");
		}

		InfracaoImovelSituacaoActionForm form = (InfracaoImovelSituacaoActionForm) actionForm;

		final Fachada fachada = Fachada.getInstancia();

		FiltroInfracaoImovelSituacao filtro = new FiltroInfracaoImovelSituacao();
		filtro.adicionarParametro(new ParametroSimples(FiltroInfracaoImovelSituacao.ID, Integer.valueOf(idRegistroAtualizacao)));
		Collection colecao = fachada.pesquisar(filtro, InfracaoImovelSituacao.class.getName());
		InfracaoImovelSituacao bean = (InfracaoImovelSituacao) colecao.iterator().next();
		form.setId(bean.getId().toString());
		form.setDescricao(bean.getDescricao());
		form.setDescricaoAbreviada(bean.getDescricaoAbreviada());
		form.setIndicadorUso(bean.getIndicadorUso().toString());

		return retorno;

	}
}
