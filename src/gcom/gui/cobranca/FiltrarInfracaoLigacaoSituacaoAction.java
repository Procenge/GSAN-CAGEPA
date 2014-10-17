
package gcom.gui.cobranca;

import gcom.cobranca.FiltroInfracaoLigacaoSituacao;
import gcom.cobranca.InfracaoLigacaoSituacao;
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

public class FiltrarInfracaoLigacaoSituacaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterInfracaoLigacaoSituacao");

		InfracaoLigacaoSituacaoActionForm form = (InfracaoLigacaoSituacaoActionForm) actionForm;
		HttpSession sessao = httpServletRequest.getSession(false);
		String codigo = (String) form.getId();
		String descricao = (String) form.getDescricao();
		String indicadorUso = (String) form.getIndicadorUso();
		String atualizar = httpServletRequest.getParameter("indicadorAtualizar");
		String tipoPesquisa = (String) form.getTipoPesquisa();

		Fachada fachada = Fachada.getInstancia();
		boolean peloMenosUmParametroInformado = false;

		FiltroInfracaoLigacaoSituacao filtro = new FiltroInfracaoLigacaoSituacao();

		if(codigo != null && !codigo.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtro.adicionarParametro(new ParametroSimples(FiltroInfracaoLigacaoSituacao.ID, new Integer(codigo)));
		}

		if(descricao != null && !descricao.trim().equalsIgnoreCase("")){

			peloMenosUmParametroInformado = true;

			if(tipoPesquisa != null && tipoPesquisa.equals(ConstantesSistema.TIPO_PESQUISA_COMPLETA.toString())){
				filtro.adicionarParametro(new ComparacaoTextoCompleto(FiltroInfracaoLigacaoSituacao.DESCRICAO, descricao));
			}else{
				filtro.adicionarParametro(new ComparacaoTexto(FiltroInfracaoLigacaoSituacao.DESCRICAO, descricao));
			}
		}
		if(indicadorUso != null && !indicadorUso.trim().equalsIgnoreCase("")){
			peloMenosUmParametroInformado = true;
			filtro.adicionarParametro(new ParametroSimples(FiltroInfracaoLigacaoSituacao.INDICADOR_USO, indicadorUso));
		}

		// Erro caso o usuário mandou filtrar sem nenhum parâmetro
		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		Collection colecaoInfracaoLigacaoSituacao = (Collection) fachada.pesquisar(filtro, InfracaoLigacaoSituacao.class.getName());

		if(colecaoInfracaoLigacaoSituacao != null && !colecaoInfracaoLigacaoSituacao.isEmpty()){
			if(atualizar != null && colecaoInfracaoLigacaoSituacao.size() == 1){
				InfracaoLigacaoSituacao orgao = (InfracaoLigacaoSituacao) colecaoInfracaoLigacaoSituacao.iterator().next();
				httpServletRequest.setAttribute("idRegistroAtualizacao", orgao.getId().toString());

				retorno = actionMapping.findForward("exibirAtualizarInfracaoLigacaoSituacao");

			}else{
				retorno = actionMapping.findForward("retornarFiltroInfracaoLigacaoSituacao");
			}
		}else{
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		httpServletRequest.setAttribute("filtro", filtro);
		sessao.setAttribute("filtro", filtro);

		return retorno;
	}
}
