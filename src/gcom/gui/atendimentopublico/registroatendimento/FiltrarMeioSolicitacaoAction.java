
package gcom.gui.atendimentopublico.registroatendimento;

import gcom.atendimentopublico.registroatendimento.FiltroMeioSolicitacao;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ComparacaoTextoCompleto;
import gcom.util.filtro.ParametroSimples;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FiltrarMeioSolicitacaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterMeioSolicitacao");

		HttpSession sessao = httpServletRequest.getSession(false);

		MeioSolicitacaoActionForm meioSolicitacaoActionForm = (MeioSolicitacaoActionForm) actionForm;

		FiltroMeioSolicitacao filtroMeioSolicitacao = new FiltroMeioSolicitacao();

		boolean peloMenosUmParametroInformado = false;

		String descricao = meioSolicitacaoActionForm.getDescricao();
		String descricaoAbreviada = meioSolicitacaoActionForm.getDescricaoAbreviada();
		String indicadorUso = meioSolicitacaoActionForm.getIndicadorUso();
		String indicadorLiberacaoDocIdent = meioSolicitacaoActionForm.getIndicadorLiberacaoDocIdent();
		String atualizar = httpServletRequest.getParameter("atualizar");

		httpServletRequest.setAttribute("atualizar", atualizar);

		if(descricao != null && !descricao.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtroMeioSolicitacao.adicionarParametro(new ComparacaoTextoCompleto(FiltroMeioSolicitacao.DESCRICAO, descricao));
		}

		if(descricaoAbreviada != null && !descricaoAbreviada.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtroMeioSolicitacao.adicionarParametro(new ComparacaoTextoCompleto(FiltroMeioSolicitacao.DESCRICAO_ABREVIADA,
							descricaoAbreviada));
		}

		if(indicadorUso != null && !indicadorUso.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			if(indicadorUso.equals(String.valueOf(ConstantesSistema.TODOS))){
				peloMenosUmParametroInformado = true;
			}else{
				filtroMeioSolicitacao.adicionarParametro(new ParametroSimples(FiltroMeioSolicitacao.INDICADOR_USO, indicadorUso));
			}
		}

		if(indicadorLiberacaoDocIdent != null
						&& !indicadorLiberacaoDocIdent.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			filtroMeioSolicitacao.adicionarParametro(new ParametroSimples(FiltroMeioSolicitacao.INDICADOR_LIBERACAO_DOC_IDENT,
							indicadorLiberacaoDocIdent));
		}

		// Erro caso o usuário mandou Pesquisar sem nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		sessao.setAttribute("filtroMeioSolicitacao", filtroMeioSolicitacao);

		return retorno;
	}

}
