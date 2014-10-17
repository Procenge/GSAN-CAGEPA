
package gcom.gui.cobranca;

import gcom.cobranca.FiltroInfracaoTipo;
import gcom.cobranca.InfracaoTipo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ComparacaoTextoCompleto;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PesquisarInfracaoTipoAction
				extends GcomAction {

	/**
	 * 
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("listaInfracaoTipoResultado");

		InfracaoTipoActionForm form = (InfracaoTipoActionForm) actionForm;
		HttpSession sessao = httpServletRequest.getSession(false);
		String codigo = (String) form.getId();
		String descricao = (String) form.getDescricao();
		String indicadorUso = (String) form.getIndicadorUso();
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

		Map resultado = controlarPaginacao(httpServletRequest, retorno, filtro, InfracaoTipo.class.getName());

		colecaoInfracaoTipo = (Collection) resultado.get("colecaoRetorno");

		retorno = (ActionForward) resultado.get("destinoActionForward");

		if(colecaoInfracaoTipo == null || colecaoInfracaoTipo.isEmpty()){
			throw new ActionServletException("atencao.pesquisa.nenhumresultado", null, "infracao tipo");
		}else if(colecaoInfracaoTipo.size() > ConstantesSistema.NUMERO_MAXIMO_REGISTROS_PESQUISA){
			throw new ActionServletException("atencao.pesquisa.muitosregistros");
		}else{
			sessao.setAttribute("colecaoInfracaoTipo", colecaoInfracaoTipo);
		}

		return retorno;
	}
}