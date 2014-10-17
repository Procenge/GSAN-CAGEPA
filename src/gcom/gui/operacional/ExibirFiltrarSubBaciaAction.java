
package gcom.gui.operacional;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.Bacia;
import gcom.operacional.FiltroBacia;
import gcom.operacional.FiltroSistemaEsgoto;
import gcom.operacional.FiltroSubsistemaEsgoto;
import gcom.operacional.SistemaEsgoto;
import gcom.operacional.SubsistemaEsgoto;
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
 * Filtar SubBacia
 * 
 * @author Bruno Ferreira dos Santos
 * @date 31/01/2011
 */

public class ExibirFiltrarSubBaciaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("filtrarSubBacia");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarSubBaciaActionForm form = (FiltrarSubBaciaActionForm) actionForm;

		// Código para checar ou não o ATUALIZAR

		String primeiraVez = httpServletRequest.getParameter("menu");
		if(primeiraVez != null && !primeiraVez.equals("")){
			sessao.setAttribute("indicadorAtualizar", "1");
			form.setTipoPesquisa(ConstantesSistema.TIPO_PESQUISA_INICIAL.toString());

		}

		// Verificar a existência de dados
		sessao.setAttribute("colecaoBacia", null);
		sessao.setAttribute("colecaoSistema", null);
		sessao.setAttribute("colecaoSubSistema", null);

		FiltroSistemaEsgoto filtroSistemaEsgoto = new FiltroSistemaEsgoto();
		filtroSistemaEsgoto.setCampoOrderBy(FiltroSistemaEsgoto.DESCRICAO);
		filtroSistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSistemaEsgoto.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoSistema = getFachada().pesquisar(filtroSistemaEsgoto, SistemaEsgoto.class.getName());

		if(colecaoSistema == null || colecaoSistema.isEmpty()){
			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "SISTEMA_ESGOTO");
		}else{
			sessao.setAttribute("colecaoSistema", colecaoSistema);
			if((httpServletRequest.getParameter("consultaSistema") != null && !form.getIdSistema().equals("-1"))
							|| (httpServletRequest.getParameter("consultaSubSistema") != null && !form.getIdSubSistema().equals("-1"))){
				FiltroSubsistemaEsgoto filtroSubsistemaEsgoto = new FiltroSubsistemaEsgoto();
				filtroSubsistemaEsgoto.setCampoOrderBy(FiltroSubsistemaEsgoto.DESCRICAO);
				filtroSubsistemaEsgoto.adicionarParametro(new ParametroSimples(FiltroSubsistemaEsgoto.SISTEMAESGOTO_ID, Integer
								.valueOf(form.getIdSistema())));

				Collection colecaoSubSistema = getFachada().pesquisar(filtroSubsistemaEsgoto, SubsistemaEsgoto.class.getName());

				if(colecaoSubSistema == null || colecaoSubSistema.isEmpty()){
					throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "SUBSISTEMA_ESGOTO");
				}else{
					sessao.setAttribute("colecaoSubSistema", colecaoSubSistema);
					if(httpServletRequest.getParameter("consultaSubSistema") != null && !form.getIdSubSistema().equals("-1")){
						FiltroBacia filtroBacia = new FiltroBacia();
						filtroBacia.setCampoOrderBy(FiltroBacia.DESCRICAO);
						filtroBacia
										.adicionarParametro(new ParametroSimples(FiltroBacia.INDICADORUSO,
														ConstantesSistema.INDICADOR_USO_ATIVO));
						filtroBacia.adicionarParametro(new ParametroSimples(FiltroBacia.SUBSISTEMA_ESGOTO_ID, Integer.valueOf(form
										.getIdSubSistema())));

						Collection colecaoBacia = this.getFachada().pesquisar(filtroBacia, Bacia.class.getName());

						if(colecaoBacia == null || colecaoBacia.isEmpty()){
							throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "BACIA");
						}else{
							sessao.setAttribute("colecaoBacia", colecaoBacia);
						}
					}
				}
			}
		}

		// Se voltou da tela de Atualizar Sistema de Esgoto
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
