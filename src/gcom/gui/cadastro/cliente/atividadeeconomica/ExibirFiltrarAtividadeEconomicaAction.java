
package gcom.gui.cadastro.cliente.atividadeeconomica;

import gcom.cadastro.cliente.AtividadeEconomica;
import gcom.cadastro.cliente.FiltroAtividadeEconomica;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3151] Filtrar Atividade Econômica
 * 
 * @author Anderson Italo
 * @date 29/06/2014
 */
public class ExibirFiltrarAtividadeEconomicaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("filtrarAtividadeEconomica");

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarAtividadeEconomicaActionForm form = (FiltrarAtividadeEconomicaActionForm) actionForm;

		// Código para checar ou não o ATUALIZAR e Passar o valor do Indicador de Uso como "TODOS"
		String primeiraVez = httpServletRequest.getParameter("menu");
		if(primeiraVez != null && !primeiraVez.equals("")){
			sessao.setAttribute("indicadorAtualizar", "1");
			form.setTipoPesquisa(ConstantesSistema.TIPO_PESQUISA_INICIAL.toString());

		}

		FiltroAtividadeEconomica filtroAtividadeEconomica = new FiltroAtividadeEconomica();
		filtroAtividadeEconomica.adicionarParametro(new ParametroSimples(FiltroAtividadeEconomica.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroAtividadeEconomica.setCampoOrderBy(FiltroAtividadeEconomica.CODIGO);
		filtroAtividadeEconomica.setCampoOrderBy(FiltroAtividadeEconomica.DESCRICAO);

		Collection<AtividadeEconomica> colecaoAtividadeEconomica = Fachada.getInstancia().pesquisar(filtroAtividadeEconomica,
						AtividadeEconomica.class.getName());

		if(colecaoAtividadeEconomica == null || colecaoAtividadeEconomica.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Atividade Ecônomica");
		}

		httpServletRequest.setAttribute("colecaoAtividadeEconomica", colecaoAtividadeEconomica);

		if(sessao.getAttribute("voltar") != null && sessao.getAttribute("voltar").equals("filtrar")){
			sessao.setAttribute("indicadorAtualizar", "1");
			if(sessao.getAttribute("tipoPesquisa") != null){
				form.setTipoPesquisa(sessao.getAttribute("tipoPesquisa").toString());
			}
		}

		sessao.removeAttribute("voltar");
		sessao.removeAttribute("tipoPesquisa");

		return retorno;

	}

}
