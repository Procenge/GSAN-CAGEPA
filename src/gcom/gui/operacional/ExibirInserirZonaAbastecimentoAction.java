
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
 * Inserir ZonaAbastecimento
 * 
 * @author Bruno Ferreira dos Santos
 * @created 02 de Fevereiro de 2011
 */

public class ExibirInserirZonaAbastecimentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("exibirInserirZonaAbastecimento");

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		InserirZonaAbastecimentoActionForm inserirZonaAbastecimentoActionForm = (InserirZonaAbastecimentoActionForm) actionForm;

		String limparCampos = (String) httpServletRequest.getParameter("limparCampos");

		// Verificar a existência de dados
		sessao.setAttribute("colecaoLocalidade", null);
		sessao.setAttribute("colecaoDistritoOperacional", null);
		sessao.setAttribute("colecaoSistemaAbastecimento", null);

		// Preenche o combo Sistema Abastecimento
		pesquisarSistemaAbastecimento(sessao);

		// Preenche o combo Distrito Operacional
		pesquisarDistritoOperacional(sessao, inserirZonaAbastecimentoActionForm);

		// Preenche o combo Localidade
		pesquisarLocalidade(sessao, inserirZonaAbastecimentoActionForm);

		// Botão Desfazer
		String desfazer = httpServletRequest.getParameter("desfazer");

		if((desfazer != null && desfazer.equalsIgnoreCase("S"))){
			// Limpando o formulario
			inserirZonaAbastecimentoActionForm.setCodigo("");
			inserirZonaAbastecimentoActionForm.setDescricao("");
			inserirZonaAbastecimentoActionForm.setDescricaoAbreviada("");

			// Campos do tipo lista no formulário
			inserirZonaAbastecimentoActionForm.setIdDistritoOperacional("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			inserirZonaAbastecimentoActionForm.setIdLocalidade("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			inserirZonaAbastecimentoActionForm.setIdSistemaAbastecimento("" + ConstantesSistema.NUMERO_NAO_INFORMADO);

			inserirZonaAbastecimentoActionForm.setFaixaPressaoInferior("");
			inserirZonaAbastecimentoActionForm.setFaixaPressaoSuperior("");
		}

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
	private void pesquisarDistritoOperacional(HttpSession sessao, InserirZonaAbastecimentoActionForm form){

		Collection<DistritoOperacional> colecaoDistritoOperacional = (ArrayList<DistritoOperacional>) sessao
						.getAttribute("colecaoDistritoOperacional");

		if(colecaoDistritoOperacional == null || colecaoDistritoOperacional.isEmpty()){
			FiltroDistritoOperacional filtroDistritoOperacional = new FiltroDistritoOperacional();
			filtroDistritoOperacional.setCampoOrderBy(FiltroDistritoOperacional.DESCRICAO);
			filtroDistritoOperacional.adicionarParametro(new ParametroSimples(FiltroDistritoOperacional.INDICADORUSO,
							ConstantesSistema.INDICADOR_USO_ATIVO));
			if(form.getIdSistemaAbastecimento() != null && !form.getIdSistemaAbastecimento().equals("-1")){
				filtroDistritoOperacional.adicionarParametro(new ParametroSimples(FiltroDistritoOperacional.SISTEMA_ABASTECIMENTO_ID, form
							.getIdSistemaAbastecimento()));
			}
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
	private void pesquisarLocalidade(HttpSession sessao, InserirZonaAbastecimentoActionForm form){

		Collection<Localidade> colecaoLocalidade = (ArrayList<Localidade>) sessao.getAttribute("colecaoLocalidade");

		if(colecaoLocalidade == null || colecaoLocalidade.isEmpty()){
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
			filtroLocalidade.setCampoOrderBy(FiltroLocalidade.DESCRICAO);
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
			if(form.getIdDistritoOperacional() != null && !form.getIdDistritoOperacional().equals("-1")){
				DistritoOperacional distritoOp = (DistritoOperacional) getFachada().pesquisar(
								Integer.parseInt(form.getIdDistritoOperacional()), DistritoOperacional.class);
				filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, distritoOp.getLocalidade().getId()));
			}
			colecaoLocalidade = (ArrayList<Localidade>) getFachada().pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoLocalidade == null || colecaoLocalidade.isEmpty()){
				throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "LOCALIDADE");
			}

			sessao.setAttribute("colecaoLocalidade", colecaoLocalidade);
		}
	}

}
