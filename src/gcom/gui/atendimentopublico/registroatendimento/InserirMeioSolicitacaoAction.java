
package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.MeioSolicitacao;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class InserirMeioSolicitacaoAction
				extends GcomAction {

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
					throws Exception{

		ActionForward retorno = mapping.findForward("telaSucesso");

		MeioSolicitacaoActionForm meioSolicitacaoActionForm = (MeioSolicitacaoActionForm) form;

		Fachada fachada = Fachada.getInstancia();

		String descricao = meioSolicitacaoActionForm.getDescricao();
		String descricaoAbreviada = meioSolicitacaoActionForm.getDescricaoAbreviada();
		Short indicadorLiberacaoDocIdent = Short.valueOf(meioSolicitacaoActionForm.getIndicadorLiberacaoDocIdent());

		MeioSolicitacao meioSolicitacao = new MeioSolicitacao();
		meioSolicitacao.setDescricao(descricao);
		meioSolicitacao.setDescricaoAbreviada(descricaoAbreviada);
		meioSolicitacao.setIndicadorUso(ConstantesSistema.SIM);
		meioSolicitacao.setIndicadorLiberacaoDocIdent(indicadorLiberacaoDocIdent);
		meioSolicitacao.setUltimaAlteracao(new Date());

		Integer idMeioSolicitacao;
		idMeioSolicitacao = (Integer) fachada.inserir(meioSolicitacao);

		// monta página de sucesso
		montarPaginaSucesso(request, "Meio Solicitação " + meioSolicitacaoActionForm.getDescricao() + " inserido com sucesso.",
						"Inserir outro Meio Solicitação", "exibirInserirMeioSolicitacaoAction.do?menu=sim",
						"exibirAtualizarMeioSolicitacaoAction.do?idRegistroAtualizar=" + idMeioSolicitacao + "&retornoFiltrar=1",
						"Atualizar Meio Solicitação inserido");

		return retorno;
	}

}
