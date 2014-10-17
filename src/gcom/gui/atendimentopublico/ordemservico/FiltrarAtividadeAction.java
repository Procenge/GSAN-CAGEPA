
package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.FiltroAtividade;
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

public class FiltrarAtividadeAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterAtividade");

		HttpSession sessao = httpServletRequest.getSession(false);

		AtividadeActionForm atividadeActionForm = (AtividadeActionForm) actionForm;

		FiltroAtividade filtroAtividade = new FiltroAtividade();

		boolean peloMenosUmParametroInformado = false;

		String descricao = atividadeActionForm.getDescricao();
		String descricaoAbreviada = atividadeActionForm.getDescricaoAbreviada();
		String indicadorUso = atividadeActionForm.getIndicadorUso();
		String indicadorAtividadeUnica = atividadeActionForm.getIndicadorAtividadeUnica();
		String atualizar = httpServletRequest.getParameter("atualizar");

		httpServletRequest.setAttribute("atualizar", atualizar);

		if(descricao != null && !descricao.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtroAtividade.adicionarParametro(new ComparacaoTextoCompleto(FiltroAtividade.DESCRICAO, descricao));
		}

		if(descricaoAbreviada != null && !descricaoAbreviada.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtroAtividade.adicionarParametro(new ComparacaoTextoCompleto(FiltroAtividade.DESCRICAOABREVIADA, descricaoAbreviada));
		}

		if(indicadorUso != null && !indicadorUso.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			if(indicadorUso.equals(String.valueOf(ConstantesSistema.TODOS))){
				peloMenosUmParametroInformado = true;
			}else{
				filtroAtividade.adicionarParametro(new ParametroSimples(FiltroAtividade.INDICADORUSO, indicadorUso));
			}
		}

		if(indicadorAtividadeUnica != null && !indicadorAtividadeUnica.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			filtroAtividade.adicionarParametro(new ParametroSimples(FiltroAtividade.INDICADORATIVIDADEUNICA, indicadorAtividadeUnica));
		}

		// Erro caso o usuário mandou Pesquisar sem nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		sessao.setAttribute("filtroAtividade", filtroAtividade);

		return retorno;
	}
}
