
package gcom.gui.seguranca.sistema;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.transacao.EntidadeRegistrarLog;
import gcom.seguranca.transacao.RegistradorLogTransacoes;
import gcom.seguranca.transacao.Tabela;
import gcom.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Action responsável pela exibição do Log.
 * 
 * @author Anderson Italo
 * @created 21/09/2014
 */
public class ExibirLogTransacoesPopupAction
				extends GcomAction {

	@Override
	public ActionForward execute(ActionMapping actionMapping, ActionForm form, HttpServletRequest httpServletRequest,
					HttpServletResponse response){

		ActionForward retorno = actionMapping.findForward("exibirLogTransacoesPopup");

		String idTabela = httpServletRequest.getParameter("idTabela");

		if(Util.isVazioOuBranco(idTabela)){

			throw new ActionServletException("atencao.campo_texto.obrigatorio", null, "idTabela");
		}

		Fachada fachada = Fachada.getInstancia();

		Tabela tabela = (Tabela) fachada.pesquisar(Util.obterInteger(idTabela), Tabela.class);

		if(tabela == null){

			throw new ActionServletException("atencao.entidade.inexistente", null, "Tabela");
		}

		String idRegistro = httpServletRequest.getParameter("idRegistro");

		if(Util.isVazioOuBranco(idRegistro)){

			throw new ActionServletException("atencao.campo_texto.obrigatorio", null, "idRegistro");
		}

		RegistradorLogTransacoes registradorLogTransacoes = EntidadeRegistrarLog.retornarRegistroTabela(tabela,
						Util.obterInteger(idRegistro));

		if(registradorLogTransacoes == null){

			throw new ActionServletException("atencao.campo_texto.obrigatorio", null, "registradorLogTransacoes");
		}

		StringBuilder builderLog = new StringBuilder();

		if(registradorLogTransacoes != null && registradorLogTransacoes.getDescricaoLog() != null){

			builderLog.append(registradorLogTransacoes.getDescricaoLog());
		}

		httpServletRequest.setAttribute("logTransacoes", builderLog.toString());

		return retorno;
	}

}
