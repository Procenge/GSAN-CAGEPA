
package gcom.gui.operacional;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.DistritoOperacional;
import gcom.operacional.FiltroDistritoOperacional;
import gcom.operacional.FiltroSistemaAbastecimento;
import gcom.operacional.FiltroZonaAbastecimento;
import gcom.operacional.SistemaAbastecimento;
import gcom.operacional.ZonaAbastecimento;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Atualizar ZonaAbastecimento
 * 
 * @author Bruno Ferreira dos Santos
 * @date 02/02/2011
 */

public class ExibirAtualizarZonaAbastecimentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atualizarZonaAbastecimento");

		AtualizarZonaAbastecimentoActionForm atualizarZonaAbastecimentoActionForm = (AtualizarZonaAbastecimentoActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		// **************** Carregar Combos ********************
		pesquisarSistemaAbastecimento(sessao);
		pesquisarDistritoOperacional(sessao);
		pesquisarLocalidade(sessao);
		// *****************************************************

		ZonaAbastecimento zonaAbastecimento = null;

		String idZonaAbastecimento = null;

		if(httpServletRequest.getParameter("idZonaAbastecimento") != null){
			// tela do manter
			idZonaAbastecimento = (String) httpServletRequest.getParameter("idZonaAbastecimento");
			sessao.setAttribute("idZonaAbastecimento", idZonaAbastecimento);
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarZonaAbastecimentoAction.do");
		}else if(sessao.getAttribute("idZonaAbastecimento") != null){
			// tela do filtrar
			idZonaAbastecimento = (String) sessao.getAttribute("idZonaAbastecimento");
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarZonaAbastecimentoAction.do");
		}else if(httpServletRequest.getParameter("idRegistroInseridoAtualizar") != null){
			// link na tela de sucesso do inserir
			idZonaAbastecimento = (String) httpServletRequest.getParameter("idRegistroInseridoAtualizar");
			sessao.setAttribute("caminhoRetornoVoltar", "/gsan/exibirFiltrarZonaAbastecimentoAction.do?menu=sim");
		}

		if(idZonaAbastecimento == null){

			if(sessao.getAttribute("idRegistroAtualizar") != null){
				zonaAbastecimento = (ZonaAbastecimento) sessao.getAttribute("idRegistroAtualizar");
			}else{
				idZonaAbastecimento = (String) httpServletRequest.getParameter("idZonaAbastecimento").toString();
			}
		}

		// Inicio da parte que verifica se vem da página de subbacia_manter.jsp

		if(zonaAbastecimento == null){

			if(idZonaAbastecimento != null && !idZonaAbastecimento.equals("")){

				FiltroZonaAbastecimento filtroZonaAbastecimento = new FiltroZonaAbastecimento();
				filtroZonaAbastecimento.adicionarCaminhoParaCarregamentoEntidade(FiltroZonaAbastecimento.SISTEMA_ABASTECIMENTO);
				filtroZonaAbastecimento.adicionarCaminhoParaCarregamentoEntidade(FiltroZonaAbastecimento.DISTRITO_OPERACIONAL);
				filtroZonaAbastecimento.adicionarCaminhoParaCarregamentoEntidade(FiltroZonaAbastecimento.LOCALIDADE);
				filtroZonaAbastecimento.adicionarParametro(new ParametroSimples(FiltroZonaAbastecimento.ID, idZonaAbastecimento));

				zonaAbastecimento = (ZonaAbastecimento) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroZonaAbastecimento,
								ZonaAbastecimento.class.getName()));

				if(zonaAbastecimento == null){
					new ActionServletException("erro.sistema.sistema.parametro");
				}
			}
		}

		// A zona de abastecimento foi encontrada

		atualizarZonaAbastecimentoActionForm.setCodigo((zonaAbastecimento.getCodigo() != null ? String.valueOf(zonaAbastecimento
						.getCodigo()) : ""));
		atualizarZonaAbastecimentoActionForm.setDescricao(zonaAbastecimento.getDescricao());
		atualizarZonaAbastecimentoActionForm.setDescricaoAbreviada(zonaAbastecimento.getDescricaoAbreviada());
		atualizarZonaAbastecimentoActionForm.setIdLocalidade(zonaAbastecimento.getLocalidade() != null ? String.valueOf(zonaAbastecimento
						.getLocalidade().getId()) : "-1");
		atualizarZonaAbastecimentoActionForm.setIdDistritoOperacional(zonaAbastecimento.getDistritoOperacional() != null ? String
						.valueOf(zonaAbastecimento.getDistritoOperacional().getId()) : "-1");

		// Faixa Pressao Inferior
		String faixaPressaoInferiorStr = "";
		BigDecimal faixaPressaoInferior = zonaAbastecimento.getFaixaPressaoInferior();

		if(faixaPressaoInferior != null){
			faixaPressaoInferiorStr = Util.formataBigDecimal(faixaPressaoInferior, 4, false);
		}

		atualizarZonaAbastecimentoActionForm.setFaixaPressaoInferior(faixaPressaoInferiorStr);

		// Faixa Pressao Superior
		String faixaPressaoSuperiorStr = "";
		BigDecimal faixaPressaoSuperior = zonaAbastecimento.getFaixaPressaoSuperior();

		if(faixaPressaoSuperior != null){
			faixaPressaoSuperiorStr = Util.formataBigDecimal(faixaPressaoSuperior, 4, false);
		}

		atualizarZonaAbastecimentoActionForm.setFaixaPressaoSuperior(faixaPressaoSuperiorStr);

		atualizarZonaAbastecimentoActionForm.setSistemaAbastecimento(String.valueOf(zonaAbastecimento.getSistemaAbastecimento().getId()));
		atualizarZonaAbastecimentoActionForm.setIndicadorUso(String.valueOf(zonaAbastecimento.getIndicadorUso()));
		atualizarZonaAbastecimentoActionForm.setUltimaAlteracao(Util.formatarDataComHora(zonaAbastecimento.getUltimaAlteracao()));

		sessao.setAttribute("zonaAbastecimento", zonaAbastecimento);

		httpServletRequest.setAttribute("idZonaAbastecimento", idZonaAbastecimento);

		// Fim da parte que verifica se vem da página de subbacia_manter.jsp

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
