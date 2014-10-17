/**
 * 
 */

package gcom.gui.micromedicao.leitura;

import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.FiltroLeiturista;
import gcom.micromedicao.Leiturista;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Exibi a tela para efetuar a pesquisa das rotas de um determinado leiturista.
 * 
 * @author Péricles Tavares
 * @date 09/08/2011
 */
public class ExibirPesquisarRotaLeituristaAction
				extends GcomAction {

	/**
	 * Exibi a tela para efetuar a pesquisa das rotas de um determinado leiturista.
	 * 
	 * @author Péricles Tavares
	 * @date 09/08/2011
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirPesquisarRotaLeituristaAction");

		PesquisarRotaLeituristaActionForm form = (PesquisarRotaLeituristaActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		if(!Util.isVazioOuBranco(httpServletRequest.getParameter("chamarPopup"))){
			selecionarEmpresa(httpServletRequest, form);
			selecionarLeiturista(httpServletRequest, form);
		}

		carregarEmpresa(sessao);
		if(!Util.isVazioOuBranco(form.getIdEmpresa())){
			carregarLeiturista(form, sessao);
		}

		return retorno;
	}

	private void carregarLeiturista(PesquisarRotaLeituristaActionForm form, HttpSession sessao){

		FiltroLeiturista filtroLeiturista = new FiltroLeiturista();
		filtroLeiturista.adicionarCaminhoParaCarregamentoEntidade(FiltroLeiturista.FUNCIONARIO);
		filtroLeiturista.adicionarCaminhoParaCarregamentoEntidade(FiltroLeiturista.CLIENTE);
		filtroLeiturista.adicionarParametro(new ParametroSimples(FiltroLeiturista.EMPRESA_ID, Integer.valueOf(form.getIdEmpresa())));
		filtroLeiturista.adicionarParametro(new ParametroSimples(FiltroLeiturista.INDICADOR_USO, ConstantesSistema.SIM));

		Collection<Leiturista> leituristas = getFachada().pesquisar(filtroLeiturista, Leiturista.class.getName());

		if(Util.isVazioOrNulo(leituristas)){
			throw new ActionServletException("atencao.entidade.inexistente", null, "LEITURISTA");
		}

		sessao.setAttribute("colecaoLeiturista", leituristas);
	}

	private void carregarEmpresa(HttpSession sessao){

		FiltroEmpresa filtroEmpresa = new FiltroEmpresa();
		filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.INDICADORUSO, ConstantesSistema.SIM));

		Collection empresas = getFachada().pesquisar(filtroEmpresa, Empresa.class.getName());
		if(Util.isVazioOrNulo(empresas)){
			throw new ActionServletException("atencao.entidade.inexistente", null, "EMPRESA");
		}
		sessao.setAttribute("colecaoEmpresa", empresas);
	}

	private void selecionarLeiturista(HttpServletRequest httpServletRequest, PesquisarRotaLeituristaActionForm form){

		if(!Util.isVazioOuBranco(httpServletRequest.getParameter(PesquisarRotaLeituristaActionForm.LEITURISTA))
						&& !Util.obterInteger(httpServletRequest.getParameter(PesquisarRotaLeituristaActionForm.LEITURISTA)).equals(
										ConstantesSistema.NUMERO_NAO_INFORMADO)){
			form.setEditarLeiturista(Boolean.FALSE);
			form.setIdLeiturista(httpServletRequest.getParameter(PesquisarRotaLeituristaActionForm.LEITURISTA));
		}else{
			form.setEditarLeiturista(Boolean.TRUE);
			form.setIdLeiturista(ConstantesSistema.NUMERO_NAO_INFORMADO + "");
		}
	}

	private void selecionarEmpresa(HttpServletRequest httpServletRequest, PesquisarRotaLeituristaActionForm form){

		if(!Util.isVazioOuBranco(httpServletRequest.getParameter(PesquisarRotaLeituristaActionForm.EMPRESA))
						&& !Util.obterInteger(httpServletRequest.getParameter(PesquisarRotaLeituristaActionForm.EMPRESA)).equals(
										ConstantesSistema.NUMERO_NAO_INFORMADO)){
			form.setEditarEmpresa(Boolean.FALSE);
			form.setIdEmpresa(httpServletRequest.getParameter(PesquisarRotaLeituristaActionForm.EMPRESA));
		}else{
			form.setEditarEmpresa(Boolean.TRUE);
			form.setIdEmpresa(ConstantesSistema.NUMERO_NAO_INFORMADO + "");
		}
	}
}
