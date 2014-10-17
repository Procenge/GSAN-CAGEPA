
package gcom.gui.faturamento.conta.contamotivo;

import gcom.cobranca.FiltroMotivoNaoEntregaDocumento;
import gcom.fachada.Fachada;
import gcom.faturamento.conta.MotivoNaoEntregaDocumento;
import gcom.gui.GcomAction;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirAtualizarMotivoNaoEntregaDocumentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		final ActionForward retorno = actionMapping.findForward("exibirAtualizarMotivoNaoEntregaDocumento");

		final HttpSession sessao = httpServletRequest.getSession(false);

		final AtualizarMotivoNaoEntregaDocumentoActionForm atualizarMotivoNaoEntregaDocumentoActionForm = (AtualizarMotivoNaoEntregaDocumentoActionForm) actionForm;

		final Fachada fachada = Fachada.getInstancia();

		String idRegistroAtualizacao = (String) httpServletRequest.getParameter("idRegistroAtualizacao");

		FiltroMotivoNaoEntregaDocumento filtroMotivoNaoEntregaDocumento = new FiltroMotivoNaoEntregaDocumento();

		filtroMotivoNaoEntregaDocumento.adicionarParametro(new ParametroSimples(FiltroMotivoNaoEntregaDocumento.ID, idRegistroAtualizacao));

		Collection colecao = fachada.pesquisar(filtroMotivoNaoEntregaDocumento, MotivoNaoEntregaDocumento.class.getName());

		MotivoNaoEntregaDocumento motivoNaoEntregaDocumento = (MotivoNaoEntregaDocumento) colecao.iterator().next();

		atualizarMotivoNaoEntregaDocumentoActionForm.setId(String.valueOf(motivoNaoEntregaDocumento.getId()));
		atualizarMotivoNaoEntregaDocumentoActionForm.setDescricaoMotivo(motivoNaoEntregaDocumento.getDescricao());
		atualizarMotivoNaoEntregaDocumentoActionForm.setAbreviado(motivoNaoEntregaDocumento.getDescricaoAbreviada());
		sessao.setAttribute("atualizarMotivoNaoEntregaDocumentoActionForm", atualizarMotivoNaoEntregaDocumentoActionForm);
		return retorno;
	}
}
