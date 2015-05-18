
package gcom.gui.operacional;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.DistritoOperacional;
import gcom.operacional.FiltroDistritoOperacional;
import gcom.operacional.FiltroSistemaAbastecimento;
import gcom.operacional.SistemaAbastecimento;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Filtar ZonaAbastecimento
 * 
 * @author Bruno Ferreira dos Santos
 * @date 02/02/2011
 */

public class ExibirFiltrarZonaAbastecimentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("filtrarZonaAbastecimento");

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarZonaAbastecimentoActionForm form = (FiltrarZonaAbastecimentoActionForm) actionForm;
		String objetoConsulta = (String) httpServletRequest.getParameter("objetoConsulta");
		String idSistemaAbastecimento = (String) form.getIdSistemaAbastecimento();
		// Código para checar ou não o ATUALIZAR

		String primeiraVez = httpServletRequest.getParameter("menu");
		if(primeiraVez != null && !primeiraVez.equals("")){
			sessao.setAttribute("indicadorAtualizar", "1");
			form.setTipoPesquisa(ConstantesSistema.TIPO_PESQUISA_INICIAL.toString());

		}

		// Verificar a existência de dados
		sessao.setAttribute("colecaoUnidadeOperacional", null);
		sessao.setAttribute("colecaoSistemaAbastecimento", null);

		// Preenche o combo Sistema Abastecimento
		pesquisarSistemaAbastecimento(sessao);

		if(objetoConsulta != null && !objetoConsulta.trim().equalsIgnoreCase("")){
			pesquisarDistritoOperacional(sessao, objetoConsulta, idSistemaAbastecimento);
		}

		// Preenche o combo Localidade
		pesquisarLocalidade(sessao);

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

	/**
	 * @author isilva
	 * @param sessao
	 */
	@SuppressWarnings("unchecked")
	private void pesquisarSistemaAbastecimento(HttpSession sessao){

		Collection<SistemaAbastecimento> colecaoSistemaAbastecimento = (ArrayList<SistemaAbastecimento>) sessao
						.getAttribute("colecaoSistemaAbastecimento");

		if(colecaoSistemaAbastecimento == null || colecaoSistemaAbastecimento.isEmpty()){

			FiltroSistemaAbastecimento filtroSistemaAbastecimento = new FiltroSistemaAbastecimento();
			filtroSistemaAbastecimento.setCampoOrderBy(FiltroSistemaAbastecimento.DESCRICAO);
			filtroSistemaAbastecimento.adicionarParametro(new ParametroSimples(FiltroSistemaAbastecimento.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			colecaoSistemaAbastecimento = (ArrayList<SistemaAbastecimento>) getFachada().pesquisar(filtroSistemaAbastecimento,
							SistemaAbastecimento.class.getName());

			if(colecaoSistemaAbastecimento == null || colecaoSistemaAbastecimento.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "SISTEMA_ABASTECIMENTO");
			}

			sessao.setAttribute("colecaoSistemaAbastecimento", colecaoSistemaAbastecimento);
		}
	}

	/**
	 * @author isilva
	 * @param sessao
	 */
	@SuppressWarnings("unchecked")
	private void pesquisarDistritoOperacional(HttpSession sessao){

		Collection<DistritoOperacional> colecaoDistritoOperacional = (ArrayList<DistritoOperacional>) sessao
						.getAttribute("colecaoDistritoOperacional");

		if(colecaoDistritoOperacional == null || colecaoDistritoOperacional.isEmpty()){
			FiltroDistritoOperacional filtroDistritoOperacional = new FiltroDistritoOperacional();
			filtroDistritoOperacional.setCampoOrderBy(FiltroDistritoOperacional.DESCRICAO);
			filtroDistritoOperacional.adicionarParametro(new ParametroSimples(FiltroDistritoOperacional.INDICADORUSO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			colecaoDistritoOperacional = (ArrayList<DistritoOperacional>) getFachada().pesquisar(filtroDistritoOperacional,
							DistritoOperacional.class.getName());

			if(colecaoDistritoOperacional == null || colecaoDistritoOperacional.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "DISTRITO_OPERACIONAL");
			}

			sessao.setAttribute("colecaoDistritoOperacional", colecaoDistritoOperacional);
		}
	}

	/**
	 * @author isilva
	 * @param sessao
	 */
	@SuppressWarnings("unchecked")
	private void pesquisarDistritoOperacional(HttpSession sessao, String objetoConsulta, String idSistemaAbastecimento){

		Collection<DistritoOperacional> colecaoDistritoOperacional = (ArrayList<DistritoOperacional>) sessao
						.getAttribute("colecaoDistritoOperacional");

		if(colecaoDistritoOperacional == null || colecaoDistritoOperacional.isEmpty()){
			FiltroDistritoOperacional filtroDistritoOperacional = new FiltroDistritoOperacional();
			filtroDistritoOperacional.setCampoOrderBy(FiltroDistritoOperacional.DESCRICAO);
			filtroDistritoOperacional.adicionarParametro(new ParametroSimples(FiltroDistritoOperacional.INDICADORUSO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroDistritoOperacional.adicionarParametro(new ParametroSimples(FiltroDistritoOperacional.SISTEMA_ABASTECIMENTO_ID, idSistemaAbastecimento));

			colecaoDistritoOperacional = (ArrayList<DistritoOperacional>) getFachada().pesquisar(filtroDistritoOperacional,
							DistritoOperacional.class.getName());

			if(colecaoDistritoOperacional == null || colecaoDistritoOperacional.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "DISTRITO_OPERACIONAL");
			}

			sessao.setAttribute("colecaoDistritoOperacional", colecaoDistritoOperacional);
		}
	}

	/**
	 * @author isilva
	 * @param sessao
	 */
	@SuppressWarnings("unchecked")
	private void pesquisarLocalidade(HttpSession sessao){

		Collection<Localidade> colecaoLocalidade = (ArrayList<Localidade>) sessao.getAttribute("colecaoLocalidade");

		if(colecaoLocalidade == null || colecaoLocalidade.isEmpty()){
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.setCampoOrderBy(FiltroLocalidade.DESCRICAO);
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));

			colecaoLocalidade = (ArrayList<Localidade>) getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoLocalidade == null || colecaoLocalidade.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "LOCALIDADE");
			}

			sessao.setAttribute("colecaoLocalidade", colecaoLocalidade);
		}
	}

}
