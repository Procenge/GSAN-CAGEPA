
package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.FiltroOsProgramNaoEncerMotivo;
import gcom.atendimentopublico.ordemservico.OsProgramNaoEncerMotivo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirAtualizarOsProgramNaoEncerMotivoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarOsProgramNaoEncerMotivo");

		AtualizarOsProgramNaoEncerMotivoActionForm form = (AtualizarOsProgramNaoEncerMotivoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		Integer idOsProgramNaoEncerMotivo = null;

		HttpSession sessao = httpServletRequest.getSession(false);

		if(httpServletRequest.getAttribute("filtrar") != null){
			httpServletRequest.setAttribute("filtrar", "sim");
		}

		if(sessao.getAttribute("osProgramNaoEncerMotivo") != null){
			OsProgramNaoEncerMotivo osProgramNaoEncerMotivoAux = (OsProgramNaoEncerMotivo) sessao
							.getAttribute("osProgramNaoEncerMotivo");
			idOsProgramNaoEncerMotivo = osProgramNaoEncerMotivoAux.getId();

		}else if(httpServletRequest.getParameter("idRegistroAtualizar") != null
						&& !"".equalsIgnoreCase((String) httpServletRequest.getParameter("idRegistroAtualizar"))){
			idOsProgramNaoEncerMotivo = new Integer(httpServletRequest.getParameter("idRegistroAtualizar"));

		}else if(httpServletRequest.getParameter("idRegistroAtualizacao") != null
						&& !"".equalsIgnoreCase((String) httpServletRequest.getParameter("idRegistroAtualizacao"))){
			idOsProgramNaoEncerMotivo = new Integer(httpServletRequest.getParameter("idRegistroAtualizacao"));
		}

		FiltroOsProgramNaoEncerMotivo filtroOsProgramNaoEncerMotivo = new FiltroOsProgramNaoEncerMotivo();
		filtroOsProgramNaoEncerMotivo.adicionarParametro(new ParametroSimples(FiltroOsProgramNaoEncerMotivo.ID, idOsProgramNaoEncerMotivo));

		OsProgramNaoEncerMotivo osProgramNaoEncerMotivo = (OsProgramNaoEncerMotivo) Util.retonarObjetoDeColecao(fachada.pesquisar(
						filtroOsProgramNaoEncerMotivo, OsProgramNaoEncerMotivo.class.getName()));

		if(osProgramNaoEncerMotivo != null){
			form.setId(String.valueOf(osProgramNaoEncerMotivo.getId()));
			form.setDescricao(osProgramNaoEncerMotivo.getDescricao());
			form.setDescricaoAbreviada(osProgramNaoEncerMotivo.getDescricaoAbreviada());
			form.setIndicadorUso(String.valueOf(osProgramNaoEncerMotivo.getIndicadorUso()));
			form.setIndicadorVisitaImprodutiva(String.valueOf(osProgramNaoEncerMotivo.getIndicadorVisitaImprodutiva()));
			form.setIndicadorCobraVisitaImprodutiva(String.valueOf(osProgramNaoEncerMotivo.getIndicadorCobraVisitaImprodutiva()));

		}else{
			throw new ActionServletException("atencao.atualizacao.timestamp");
		}

		return retorno;
	}
}
