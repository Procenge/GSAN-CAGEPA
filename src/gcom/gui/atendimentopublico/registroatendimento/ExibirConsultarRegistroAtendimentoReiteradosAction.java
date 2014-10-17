
package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.bean.ObterDescricaoSituacaoRAHelper;
import gcom.cadastro.imovel.bean.ConsultarImovelRegistroAtendimentoHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ControladorException;
import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirConsultarRegistroAtendimentoReiteradosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirConsultarRegistroAtendimentoReiterados");
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession();
		ConsultarRegistroAtendimentoActionForm form = (ConsultarRegistroAtendimentoActionForm) actionForm;

		String botaoTipo = httpServletRequest.getParameter("botaoTipo");
		if(botaoTipo != null){
			sessao.setAttribute("botaoTipo", botaoTipo);
		}

		Collection<RegistroAtendimento> colecaoRA = null;
		try{
			RegistroAtendimento registroAtendimentoTMP = new RegistroAtendimento();
			registroAtendimentoTMP.setId(Integer.valueOf(form.getNumeroRA()));
			colecaoRA = fachada.listarRAsReiteradas(registroAtendimentoTMP);
		}catch(ControladorException e){
			throw new ActionServletException("", e);
		}

		Collection colecaoRegistroAtendimentoHelper = null;
		if(colecaoRA != null && !colecaoRA.isEmpty()){

			Iterator iteratorColecaoRegistroAtendimento = colecaoRA.iterator();
			colecaoRegistroAtendimentoHelper = new ArrayList();

			while(iteratorColecaoRegistroAtendimento.hasNext()){
				RegistroAtendimento registroAtendimento = (RegistroAtendimento) iteratorColecaoRegistroAtendimento.next();
				ConsultarImovelRegistroAtendimentoHelper consultarImovelRegistroAtendimentoHelper = new ConsultarImovelRegistroAtendimentoHelper();

				// id registro atendimento
				if(registroAtendimento != null && registroAtendimento.getId() != null){
					consultarImovelRegistroAtendimentoHelper.setIdRegistroAtendimento(registroAtendimento.getId().toString());
				}

				// tipo de solicitação
				if(registroAtendimento != null && registroAtendimento.getSolicitacaoTipoEspecificacao() != null
								&& registroAtendimento.getSolicitacaoTipoEspecificacao().getSolicitacaoTipo() != null){
					consultarImovelRegistroAtendimentoHelper.setTipoSolicitacao(registroAtendimento.getSolicitacaoTipoEspecificacao()
									.getSolicitacaoTipo().getDescricao());
				}

				// especificação
				if(registroAtendimento != null && registroAtendimento.getSolicitacaoTipoEspecificacao() != null){
					consultarImovelRegistroAtendimentoHelper.setEspecificacao(registroAtendimento.getSolicitacaoTipoEspecificacao()
									.getDescricao());
				}

				// data de atendimento
				if(registroAtendimento != null && registroAtendimento.getRegistroAtendimento() != null){
					consultarImovelRegistroAtendimentoHelper.setDataAtendimento(Util.formatarData(registroAtendimento
									.getRegistroAtendimento()));
				}

				// situacao
				if(registroAtendimento != null && registroAtendimento.getId() != null){
					ObterDescricaoSituacaoRAHelper obterDescricaoSituacaoRAHelper = fachada.obterDescricaoSituacaoRA(registroAtendimento
									.getId());
					consultarImovelRegistroAtendimentoHelper.setSituacao(obterDescricaoSituacaoRAHelper.getDescricaoSituacao());
				}

				colecaoRegistroAtendimentoHelper.add(consultarImovelRegistroAtendimentoHelper);
			}
		}

		sessao.setAttribute("colecaoRegistroAtendimentoHelper", colecaoRegistroAtendimentoHelper);
		return retorno;
	}
}
