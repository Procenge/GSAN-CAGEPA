
package gcom.gui.cobranca;

import gcom.cobranca.FiltroOrgaoExterno;
import gcom.cobranca.OrgaoExterno;
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
import org.apache.struts.action.DynaActionForm;

public class FiltrarOrgaoExternoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterOrgaoExternoAction");
		DynaActionForm pesquisarActionForm = (DynaActionForm) actionForm;
		HttpSession sessao = httpServletRequest.getSession(false);
		String codigo = (String) pesquisarActionForm.get("codigo");
		String descricao = (String) pesquisarActionForm.get("descricao");
		String indicadorUso = (String) pesquisarActionForm.get("indicadorUso");
		String atualizar = httpServletRequest.getParameter("atualizar");
		String tipoPesquisa = (String) pesquisarActionForm.get("tipoPesquisa");

		Fachada fachada = Fachada.getInstancia();
		boolean peloMenosUmParametroInformado = false;

		FiltroOrgaoExterno filtro = new FiltroOrgaoExterno();

		if(codigo != null && !codigo.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtro.adicionarParametro(new ParametroSimples(FiltroOrgaoExterno.ID, new Integer(codigo)));
		}

		if(descricao != null && !descricao.trim().equalsIgnoreCase("")){

			peloMenosUmParametroInformado = true;

			if(tipoPesquisa != null && tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){
				filtro.adicionarParametro(new ComparacaoTextoCompleto(FiltroOrgaoExterno.DESCRICAO, descricao));
			}else{
				filtro.adicionarParametro(new ComparacaoTexto(FiltroOrgaoExterno.DESCRICAO, descricao));
			}
		}
		if(indicadorUso != null && !indicadorUso.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtro.adicionarParametro(new ParametroSimples(FiltroOrgaoExterno.INDICADOR_USO, indicadorUso));
		}

		// Erro caso o usuário mandou filtrar sem nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		Collection colecaoBairro = (Collection) fachada.pesquisar(filtro, OrgaoExterno.class.getName());

		if(colecaoBairro != null && !colecaoBairro.isEmpty()){
			if(atualizar != null && colecaoBairro.size() == 1){
				OrgaoExterno orgao = (OrgaoExterno) colecaoBairro.iterator().next();
				httpServletRequest.setAttribute("idRegistroAtualizacao", orgao.getId().toString());

				retorno = actionMapping.findForward("exibirAtualizarOrgaoExterno");

			}else{
				retorno = actionMapping.findForward("retornarFiltroOrgaoExterno");
			}
		}else{
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		// Manda o filtro pelo request para o ExibirManterClienteAction
		httpServletRequest.setAttribute("filtro", filtro);
		sessao.setAttribute("filtro", filtro);

		return retorno;

	}

}
