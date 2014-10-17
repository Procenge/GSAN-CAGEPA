
package gcom.gui.cobranca;

import gcom.cobranca.FiltroInfracaoLigacaoSituacao;
import gcom.cobranca.InfracaoLigacaoSituacao;
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
public class ExibirAtualizarInfracaoLigacaoSituacaoAction
				extends GcomAction {

	/**
	 * @date fevereiro/2011
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirAtualizarInfracaoLigacaoSituacao");

		String idRegistroAtualizacao = (String) httpServletRequest.getAttribute("idRegistroAtualizacao");
		if(idRegistroAtualizacao == null){
			idRegistroAtualizacao = (String) httpServletRequest.getParameter("idRegistroAtualizacao");
		}

		InfracaoLigacaoSituacaoActionForm form = (InfracaoLigacaoSituacaoActionForm) actionForm;

		final Fachada fachada = Fachada.getInstancia();

		FiltroInfracaoLigacaoSituacao filtro = new FiltroInfracaoLigacaoSituacao();
		filtro.adicionarParametro(new ParametroSimples(FiltroInfracaoLigacaoSituacao.ID, Integer.valueOf(idRegistroAtualizacao)));
		Collection colecao = fachada.pesquisar(filtro, InfracaoLigacaoSituacao.class.getName());
		InfracaoLigacaoSituacao bean = (InfracaoLigacaoSituacao) colecao.iterator().next();
		form.setId(bean.getId().toString());
		form.setDescricao(bean.getDescricao());
		form.setDescricaoAbreviada(bean.getDescricaoAbreviada());
		form.setIndicadorUso(bean.getIndicadorUso().toString());

		return retorno;

	}
}
