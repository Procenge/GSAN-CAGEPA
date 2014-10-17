
package gcom.gui.faturamento.conta.contamotivo;

import gcom.fachada.Fachada;
import gcom.faturamento.conta.ContaMotivoRetificacao;
import gcom.faturamento.conta.FiltroContaMotivoRetificacao;
import gcom.gui.GcomAction;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirAtualizarContaMotivoRetificacaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		final ActionForward retorno = actionMapping.findForward("exibirAtualizarContaMotivoRetificacao");

		final HttpSession sessao = httpServletRequest.getSession(false);

		final AtualizarContaMotivoRetificacaoActionForm atualizarContaMotivoRetificacaoActionForm = (AtualizarContaMotivoRetificacaoActionForm) actionForm;

		final Fachada fachada = Fachada.getInstancia();

		String idRegistroAtualizacao = (String) httpServletRequest.getParameter("idRegistroAtualizacao");

		if(idRegistroAtualizacao == null){
			idRegistroAtualizacao = (String) httpServletRequest.getAttribute("idRegistroAtualizacao");
		}

		FiltroContaMotivoRetificacao filtroContaMotivoRetificacao = new FiltroContaMotivoRetificacao();

		filtroContaMotivoRetificacao.adicionarParametro(new ParametroSimples(FiltroContaMotivoRetificacao.CODIGO, idRegistroAtualizacao));

		Collection colecao = fachada.pesquisar(filtroContaMotivoRetificacao, ContaMotivoRetificacao.class.getName());

		ContaMotivoRetificacao contaMotivoRetificacao = (ContaMotivoRetificacao) colecao.iterator().next();

		atualizarContaMotivoRetificacaoActionForm.setId(String.valueOf(contaMotivoRetificacao.getId()));
		atualizarContaMotivoRetificacaoActionForm.setDescricaoMotivoRetificacaoConta(contaMotivoRetificacao
						.getDescricaoMotivoRetificacaoConta());
		sessao.setAttribute("atualizarContaMotivoRetificacaoActionForm", atualizarContaMotivoRetificacaoActionForm);
		return retorno;
	}
}
