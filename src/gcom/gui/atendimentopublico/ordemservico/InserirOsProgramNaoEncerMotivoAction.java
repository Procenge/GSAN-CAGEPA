
package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.OsProgramNaoEncerMotivo;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InserirOsProgramNaoEncerMotivoAction
				extends GcomAction {

	@Override
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		InserirOsProgramNaoEncerMotivoActionForm form = (InserirOsProgramNaoEncerMotivoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		String descricao = form.getDescricao();
		String descricaoAbreviada = form.getDescricaoAbreviada();
		Short indicadorVisitaImprodutiva = Short.valueOf(form.getIndicadorVisitaImprodutiva());
		Short indicadorCobraVisitaImprodutiva = Short.valueOf(form.getIndicadorCobraVisitaImprodutiva());
		
		OsProgramNaoEncerMotivo osProgramNaoEncerMotivo = new OsProgramNaoEncerMotivo();
		osProgramNaoEncerMotivo.setDescricao(descricao);
		osProgramNaoEncerMotivo.setDescricaoAbreviada(descricaoAbreviada);
		osProgramNaoEncerMotivo.setIndicadorUso(ConstantesSistema.SIM);
		osProgramNaoEncerMotivo.setIndicadorVisitaImprodutiva(indicadorVisitaImprodutiva);
		osProgramNaoEncerMotivo.setIndicadorCobraVisitaImprodutiva(indicadorCobraVisitaImprodutiva);
		osProgramNaoEncerMotivo.setUltimaAlteracao(new Date());

		Integer idOsProgramNaoEncerMotivo;
		idOsProgramNaoEncerMotivo = (Integer) fachada.inserir(osProgramNaoEncerMotivo);

		// monta página de sucesso
		montarPaginaSucesso(httpServletRequest, "Motivo do não encerramento da OS " + form.getDescricao() + " inserida com sucesso.",
						"Inserir outro Motivo do não encerramento da OS", "exibirInserirOsProgramNaoEncerMotivoAction.do?menu=sim",
						"exibirAtualizarOsProgramNaoEncerMotivoAction.do?idRegistroAtualizar=" + idOsProgramNaoEncerMotivo
										+ "&retornoFiltrar=1", "Atualizar Motivo do não encerramento da OS inserido");

		return retorno;
	}
}
