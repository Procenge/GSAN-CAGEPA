
package gcom.gui.cobranca;

import gcom.cobranca.FiltroInfracaoImovelSituacao;
import gcom.cobranca.InfracaoImovelSituacao;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ComparacaoTextoCompleto;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FiltrarInfracaoImovelSituacaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterInfracaoImovelSituacao");

		InfracaoImovelSituacaoActionForm form = (InfracaoImovelSituacaoActionForm) actionForm;
		HttpSession sessao = httpServletRequest.getSession(false);
		String codigo = (String) form.getId();
		String descricao = (String) form.getDescricao();
		String indicadorUso = (String) form.getIndicadorUso();
		String atualizar = httpServletRequest.getParameter("indicadorAtualizar");
		String tipoPesquisa = (String) form.getTipoPesquisa();

		Fachada fachada = Fachada.getInstancia();
		boolean peloMenosUmParametroInformado = false;

		FiltroInfracaoImovelSituacao filtro = new FiltroInfracaoImovelSituacao();

		if(codigo != null && !codigo.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtro.adicionarParametro(new ParametroSimples(FiltroInfracaoImovelSituacao.ID, new Integer(codigo)));
		}

		if(descricao != null && !descricao.trim().equalsIgnoreCase("")){

			peloMenosUmParametroInformado = true;

			if(tipoPesquisa != null && tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){
				filtro.adicionarParametro(new ComparacaoTextoCompleto(FiltroInfracaoImovelSituacao.DESCRICAO, descricao));
			}else{
				filtro.adicionarParametro(new ComparacaoTexto(FiltroInfracaoImovelSituacao.DESCRICAO, descricao));
			}
		}
		if(indicadorUso != null && !indicadorUso.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtro.adicionarParametro(new ParametroSimples(FiltroInfracaoImovelSituacao.INDICADOR_USO, indicadorUso));
		}

		// Erro caso o usuário mandou filtrar sem nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		Collection colecaoInfracaoImovelSituacao = (Collection) fachada.pesquisar(filtro, InfracaoImovelSituacao.class.getName());

		if(colecaoInfracaoImovelSituacao != null && !colecaoInfracaoImovelSituacao.isEmpty()){
			if(atualizar != null && colecaoInfracaoImovelSituacao.size() == 1){
				InfracaoImovelSituacao orgao = (InfracaoImovelSituacao) colecaoInfracaoImovelSituacao.iterator().next();
				httpServletRequest.setAttribute("idRegistroAtualizacao", orgao.getId().toString());

				retorno = actionMapping.findForward("exibirAtualizarInfracaoImovelSituacao");

			}else{
				retorno = actionMapping.findForward("retornarFiltroInfracaoImovelSituacao");
			}
		}else{
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		httpServletRequest.setAttribute("filtro", filtro);
		sessao.setAttribute("filtro", filtro);

		return retorno;
	}
}
