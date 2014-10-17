
package gcom.gui.operacional.abastecimento;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.FiltroSistemaAbastecimento;
import gcom.operacional.SistemaAbastecimento;
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
 * [UCXXXX] Manter Sistema Abastecimento
 * 
 * @author Anderson Italo
 * @date 31/01/2011
 */
public class ExibirFiltrarSistemaAbastecimentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("filtrarSistemaAbastecimento");

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarSistemaAbastecimentoActionForm form = (FiltrarSistemaAbastecimentoActionForm) actionForm;

		// Código para checar ou não o ATUALIZAR e Passar o valor do Indicador de Uso como "TODOS"
		String primeiraVez = httpServletRequest.getParameter("menu");
		if(primeiraVez != null && !primeiraVez.equals("")){
			sessao.setAttribute("indicadorAtualizar", "1");
			form.setTipoPesquisa(ConstantesSistema.TIPO_PESQUISA_INICIAL.toString());

		}

		FiltroSistemaAbastecimento filtroSistemaAbastecimento = new FiltroSistemaAbastecimento();
		filtroSistemaAbastecimento.adicionarParametro(new ParametroSimples(FiltroSistemaAbastecimento.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroSistemaAbastecimento.setCampoOrderBy(FiltroSistemaAbastecimento.CODIGO);
		filtroSistemaAbastecimento.setCampoOrderBy(FiltroSistemaAbastecimento.DESCRICAO);

		Collection<SistemaAbastecimento> colecaoSistemaAbastecimento = Fachada.getInstancia().pesquisar(filtroSistemaAbastecimento,
						SistemaAbastecimento.class.getName());

		if(colecaoSistemaAbastecimento == null || colecaoSistemaAbastecimento.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Sistema de Abastecimento");
		}

		httpServletRequest.setAttribute("colecaoSistemaAbastecimento", colecaoSistemaAbastecimento);

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
