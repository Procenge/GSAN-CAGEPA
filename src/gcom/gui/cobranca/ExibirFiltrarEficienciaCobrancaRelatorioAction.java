
package gcom.gui.cobranca;

import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cobranca.CobrancaAcao;
import gcom.cobranca.CobrancaGrupo;
import gcom.cobranca.FiltroCobrancaAcao;
import gcom.cobranca.FiltroCobrancaGrupo;
import gcom.fachada.Fachada;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author isilva
 */
public class ExibirFiltrarEficienciaCobrancaRelatorioAction
				extends ExibidorProcessamentoTarefaRelatorio {

	@SuppressWarnings("unchecked")
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirFiltrarEficienciaCobrancaRelatorioAction");

		FiltroEficienciaCobrancaRelatorioActionForm form = (FiltroEficienciaCobrancaRelatorioActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// Limpar os dados da tela
		if(!Util.isVazioOuBranco(httpServletRequest.getParameter("limpar"))
						&& httpServletRequest.getParameter("limpar").equalsIgnoreCase("true")){
			form.reset(actionMapping, httpServletRequest);
		}

		// Colecao Empresa
		Collection colecaoEmpresa = null;

		FiltroEmpresa filtroEmpresa = new FiltroEmpresa();
		filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroEmpresa.setCampoOrderBy(FiltroEmpresa.DESCRICAO);
		colecaoEmpresa = fachada.pesquisar(filtroEmpresa, Empresa.class.getName());

		httpServletRequest.setAttribute("colecaoEmpresa", colecaoEmpresa);

		// ColecaoAcao
		Collection colecaoAcao = null;

		FiltroCobrancaAcao filtroCobrancaAcao = new FiltroCobrancaAcao();
		filtroCobrancaAcao
						.adicionarParametro(new ParametroSimples(FiltroCobrancaAcao.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroCobrancaAcao.setCampoOrderBy(FiltroCobrancaAcao.DESCRICAO);
		colecaoAcao = fachada.pesquisar(filtroCobrancaAcao, CobrancaAcao.class.getName());

		httpServletRequest.setAttribute("colecaoCobrancaAcao", colecaoAcao);

		// ******************* CobrancaGrupo **********************
		FiltroCobrancaGrupo filtroCobrancaGrupo = new FiltroCobrancaGrupo();
		filtroCobrancaGrupo.adicionarParametro(new ParametroSimples(FiltroCobrancaGrupo.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO.toString()));

		Collection<CobrancaGrupo> colecaoCobrancaGrupo = Fachada.getInstancia().pesquisar(filtroCobrancaGrupo,
						CobrancaGrupo.class.getName());

		if(colecaoCobrancaGrupo == null || colecaoCobrancaGrupo.isEmpty()){
			colecaoCobrancaGrupo = new ArrayList<CobrancaGrupo>();
		}

		httpServletRequest.setAttribute("colecaoCobrancaGrupo", colecaoCobrancaGrupo);

		// ********************************************************

		Collection<SetorComercial> colecaoSetorComercial = null;

		if(!Util.isVazioOuBranco(form.getIdGrupo()) && !Util.isVazioOuBranco(form.getIdGrupo()[0]) && form.getIdGrupo().length == 1){
			colecaoSetorComercial = Fachada.getInstancia().pesquisarSetorComercialPorGrupoEmRota(Integer.parseInt(form.getIdGrupo()[0]));
		}

		if(colecaoSetorComercial == null || colecaoSetorComercial.isEmpty()){
			colecaoSetorComercial = new ArrayList<SetorComercial>();
		}

		httpServletRequest.setAttribute("colecaoSetorComercial", colecaoSetorComercial);

		return retorno;
	}
}