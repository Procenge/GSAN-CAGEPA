
package gcom.gui.seguranca.acesso;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.transacao.FiltroTabela;
import gcom.seguranca.transacao.Tabela;
import gcom.util.filtro.ComparacaoTexto;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class PesquisarTabelaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirResultadoPesquisaTabelaAction");

		PesquisarTabelaActionForm form = (PesquisarTabelaActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroTabela filtroTabela = new FiltroTabela();

		Fachada fachada = Fachada.getInstancia();

		boolean peloMenosUmParametroInformado = false;

		String idTabela = form.getIdTabela();

		String nomeTabela = form.getNomeTabela();

		String descricaoTabela = form.getDescricaoTabela();

		if(idTabela != null && !idTabela.trim().equals("")){
			filtroTabela.adicionarParametro(new ParametroSimples(FiltroTabela.ID, idTabela));
			peloMenosUmParametroInformado = true;
		}

		if(nomeTabela != null && !nomeTabela.trim().equals("")){
			filtroTabela.adicionarParametro(new ComparacaoTexto(FiltroTabela.NOME, nomeTabela));
			peloMenosUmParametroInformado = true;
		}

		if(descricaoTabela != null && !descricaoTabela.trim().equals("")){
			filtroTabela.adicionarParametro(new ComparacaoTexto(FiltroTabela.DESCRICAO, descricaoTabela));
			peloMenosUmParametroInformado = true;
		}

		if(!peloMenosUmParametroInformado){
			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		Collection colecaoTabela = fachada.pesquisar(filtroTabela, Tabela.class.getName());

		if(colecaoTabela != null && !colecaoTabela.isEmpty()){

			sessao.setAttribute("colecaoTabela", colecaoTabela);
		}else{
			throw new ActionServletException("atencao.colecao_vazia");
		}
		return retorno;

	}

}
