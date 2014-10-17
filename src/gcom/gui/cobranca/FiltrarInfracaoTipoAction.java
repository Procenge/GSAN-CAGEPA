
package gcom.gui.cobranca;

import java.util.Collection;

import gcom.cobranca.FiltroInfracaoTipo;
import gcom.cobranca.InfracaoTipo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ComparacaoTextoCompleto;
import gcom.util.filtro.ParametroSimples;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class FiltrarInfracaoTipoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterInfracaoTipo");

		InfracaoTipoActionForm form = (InfracaoTipoActionForm) actionForm;
		HttpSession sessao = httpServletRequest.getSession(false);
		String codigo = (String) form.getId();
		String descricao = (String) form.getDescricao();
		String indicadorUso = (String) form.getIndicadorUso();
		String atualizar = httpServletRequest.getParameter("indicadorAtualizar");
		String tipoPesquisa = (String) form.getTipoPesquisa();

		Fachada fachada = Fachada.getInstancia();
		boolean peloMenosUmParametroInformado = false;

		FiltroInfracaoTipo filtro = new FiltroInfracaoTipo();

		if(codigo != null && !codigo.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtro.adicionarParametro(new ParametroSimples(FiltroInfracaoTipo.ID, new Integer(codigo)));
		}

		if(descricao != null && !descricao.trim().equalsIgnoreCase("")){

			peloMenosUmParametroInformado = true;

			if(tipoPesquisa != null && tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){
				filtro.adicionarParametro(new ComparacaoTextoCompleto(FiltroInfracaoTipo.DESCRICAO, descricao));
			}else{
				filtro.adicionarParametro(new ComparacaoTexto(FiltroInfracaoTipo.DESCRICAO, descricao));
			}
		}
		if(indicadorUso != null && !indicadorUso.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtro.adicionarParametro(new ParametroSimples(FiltroInfracaoTipo.INDICADOR_USO, indicadorUso));
		}

		// Erro caso o usuário mandou filtrar sem nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		Collection colecaoInfracaoTipo = (Collection) fachada.pesquisar(filtro, InfracaoTipo.class.getName());

		if(colecaoInfracaoTipo != null && !colecaoInfracaoTipo.isEmpty()){
			if(atualizar != null && colecaoInfracaoTipo.size() == 1){
				InfracaoTipo orgao = (InfracaoTipo) colecaoInfracaoTipo.iterator().next();
				httpServletRequest.setAttribute("idRegistroAtualizacao", orgao.getId().toString());

				retorno = actionMapping.findForward("exibirAtualizarInfracaoTipo");

			}else{
				retorno = actionMapping.findForward("retornarFiltroInfracaoTipo");
			}
		}else{
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		httpServletRequest.setAttribute("filtro", filtro);
		sessao.setAttribute("filtro", filtro);

		return retorno;
	}
}
