
package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.FiltroAtividade;
import gcom.atendimentopublico.ordemservico.FiltroOsProgramNaoEncerMotivo;
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

public class FiltrarOsProgramNaoEncerMotivoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterOsProgramNaoEncerMotivo");

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarOsProgramNaoEncerMotivoActionForm form = (FiltrarOsProgramNaoEncerMotivoActionForm) actionForm;

		FiltroOsProgramNaoEncerMotivo filtroOsProgramNaoEncerMotivo = new FiltroOsProgramNaoEncerMotivo();

		boolean peloMenosUmParametroInformado = false;

		String descricao = form.getDescricao();
		String descricaoAbreviada = form.getDescricaoAbreviada();
		String indicadorUso = form.getIndicadorUso();
		String atualizar = httpServletRequest.getParameter("atualizar");

		httpServletRequest.setAttribute("atualizar", atualizar);

		if(descricao != null && !descricao.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtroOsProgramNaoEncerMotivo
							.adicionarParametro(new ComparacaoTextoCompleto(FiltroOsProgramNaoEncerMotivo.DESCRICAO, descricao));
		}

		if(descricaoAbreviada != null && !descricaoAbreviada.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtroOsProgramNaoEncerMotivo.adicionarParametro(new ComparacaoTextoCompleto(FiltroOsProgramNaoEncerMotivo.DESCRICAOABREVIADA,
							descricaoAbreviada));
		}

		if(indicadorUso != null && !indicadorUso.trim().equalsIgnoreCase("" + ConstantesSistema.NUMERO_NAO_INFORMADO)){
			if(!indicadorUso.equals(String.valueOf(ConstantesSistema.TODOS))){
				filtroOsProgramNaoEncerMotivo.adicionarParametro(new ParametroSimples(FiltroAtividade.INDICADORUSO, indicadorUso));
				peloMenosUmParametroInformado = true;
			}
		}

		// Erro caso o usuário mandou Pesquisar sem nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		sessao.setAttribute("filtroOsProgramNaoEncerMotivo", filtroOsProgramNaoEncerMotivo);

		return retorno;
	}
}
