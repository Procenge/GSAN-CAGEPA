
package gcom.gui.faturamento.conta.contamotivo;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gcom.fachada.Fachada;
import gcom.faturamento.conta.ContaMotivoRevisao;
import gcom.faturamento.conta.FiltroContaMotivoRevisao;
import gcom.gui.GcomAction;
import gcom.util.filtro.ParametroSimples;

public class ExibirAtualizarContaMotivoRevisaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		final ActionForward retorno = actionMapping.findForward("exibirAtualizarContaMotivoRevisao");

		final HttpSession sessao = httpServletRequest.getSession(false);

		final AtualizarContaMotivoRevisaoActionForm atualizarContaMotivoRevisaoActionForm = (AtualizarContaMotivoRevisaoActionForm) actionForm;

		final Fachada fachada = Fachada.getInstancia();

		String idRegistroAtualizacao = (String) httpServletRequest.getParameter("idRegistroAtualizacao");

		FiltroContaMotivoRevisao filtroContaMotivoRevisao = new FiltroContaMotivoRevisao();

		filtroContaMotivoRevisao.adicionarParametro(new ParametroSimples(FiltroContaMotivoRevisao.ID, idRegistroAtualizacao));

		Collection colecao = fachada.pesquisar(filtroContaMotivoRevisao, ContaMotivoRevisao.class.getName());

		ContaMotivoRevisao contaMotivoRevisao = (ContaMotivoRevisao) colecao.iterator().next();

		atualizarContaMotivoRevisaoActionForm.setId(String.valueOf(contaMotivoRevisao.getId()));
		atualizarContaMotivoRevisaoActionForm.setDescricaoMotivoRevisaoConta(contaMotivoRevisao.getDescricaoMotivoRevisaoConta());
		sessao.setAttribute("atualizarContaMotivoRevisaoActionForm", atualizarContaMotivoRevisaoActionForm);
		return retorno;
	}
}
