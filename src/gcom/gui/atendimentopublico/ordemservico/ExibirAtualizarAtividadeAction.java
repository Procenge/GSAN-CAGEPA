
package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.Atividade;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirAtualizarAtividadeAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarAtividade");

		AtividadeActionForm atividadeActionForm = (AtividadeActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		Integer idAtividade = null;

		HttpSession sessao = httpServletRequest.getSession(false);

		if(httpServletRequest.getAttribute("filtrar") != null){
			httpServletRequest.setAttribute("filtrar", "sim");
		}

		if(sessao.getAttribute("atividade") != null){
			Atividade atividadeAux = (Atividade) sessao.getAttribute("atividade");
			idAtividade = atividadeAux.getId();

		}else if(httpServletRequest.getParameter("idRegistroAtualizar") != null
						&& !"".equalsIgnoreCase((String) httpServletRequest.getParameter("idRegistroAtualizar"))){
			idAtividade = new Integer(httpServletRequest.getParameter("idRegistroAtualizar"));

		}else if(httpServletRequest.getParameter("idRegistroAtualizacao") != null
						&& !"".equalsIgnoreCase((String) httpServletRequest.getParameter("idRegistroAtualizacao"))){
			idAtividade = new Integer(httpServletRequest.getParameter("idRegistroAtualizacao"));
		}

		Atividade atividade = fachada.pesquisarAtividade(idAtividade, null);

		if(atividade != null){
			atividadeActionForm.setId(String.valueOf(atividade.getId()));
			atividadeActionForm.setDescricao(atividade.getDescricao());
			atividadeActionForm.setDescricaoAbreviada(atividade.getDescricaoAbreviada());
			atividadeActionForm.setIndicadorUso(String.valueOf(atividade.getIndicadorUso()));
			atividadeActionForm.setIndicadorAtividadeUnica(String.valueOf(atividade.getIndicadorAtividadeUnica()));
		}else{
			throw new ActionServletException("atencao.atualizacao.timestamp");
		}
		if(Fachada.getInstancia().verificarExistenciaAtividadeUnica(ConstantesSistema.SIM.toString()) != null){
			atividadeActionForm.setVerificadorAtividadeUnica("Existe");
		}

		return retorno;
	}
}
