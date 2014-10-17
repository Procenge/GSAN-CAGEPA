
package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.MeioSolicitacao;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirAtualizarMeioSolicitacaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarMeioSolicitacao");

		MeioSolicitacaoActionForm meioSolicitacaoActionForm = (MeioSolicitacaoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		Integer idMeioSolicitacao = null;

		HttpSession sessao = httpServletRequest.getSession(false);

		if(httpServletRequest.getAttribute("filtrar") != null){
			httpServletRequest.setAttribute("filtrar", "sim");
		}

		if(sessao.getAttribute("meioSolicitacao") != null){
			MeioSolicitacao meioSolicitacaoAux = (MeioSolicitacao) sessao.getAttribute("meioSolicitacao");
			idMeioSolicitacao = meioSolicitacaoAux.getId();

		}else if(httpServletRequest.getParameter("idRegistroAtualizar") != null
						&& !"".equalsIgnoreCase((String) httpServletRequest.getParameter("idRegistroAtualizar"))){
			idMeioSolicitacao = new Integer(httpServletRequest.getParameter("idRegistroAtualizar"));

		}else if(httpServletRequest.getParameter("idRegistroAtualizacao") != null
						&& !"".equalsIgnoreCase((String) httpServletRequest.getParameter("idRegistroAtualizacao"))){
			idMeioSolicitacao = new Integer(httpServletRequest.getParameter("idRegistroAtualizacao"));
		}

		MeioSolicitacao meioSolicitacao = (MeioSolicitacao) fachada.pesquisar(idMeioSolicitacao, MeioSolicitacao.class);

		if(meioSolicitacao != null){
			meioSolicitacaoActionForm.setId(String.valueOf(meioSolicitacao.getId()));
			meioSolicitacaoActionForm.setDescricao(meioSolicitacao.getDescricao());
			meioSolicitacaoActionForm.setDescricaoAbreviada(meioSolicitacao.getDescricaoAbreviada());
			meioSolicitacaoActionForm.setIndicadorUso(String.valueOf(meioSolicitacao.getIndicadorUso()));
			meioSolicitacaoActionForm.setIndicadorLiberacaoDocIdent(String.valueOf(meioSolicitacao.getIndicadorLiberacaoDocIdent()));
		}else{
			throw new ActionServletException("atencao.atualizacao.timestamp");
		}

		return retorno;
	}

}
