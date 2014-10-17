
package gcom.gui.faturamento;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.micromedicao.Rota;
import gcom.util.Util;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3055] Encerrar Faturamento
 * 
 * @author Hebert Falcão
 * @date 01/04/2012
 */
public class ExibirRotasDoGrupoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("exibirRotasDoGrupoAction");

		// Form
		EncerrarFaturamentoActionForm form = (EncerrarFaturamentoActionForm) actionForm;

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		String idFaturamentoGrupo = httpServletRequest.getParameter("idFaturamentoGrupo");
		
		// tipoConsulta: 1 -> Consultar Rotas Faturadas
		//				 2 -> Consultar Rotas Não Faturadas
		String tipoConsulta = httpServletRequest.getParameter("tipoConsulta");

		if(!Util.isVazioOuBranco(idFaturamentoGrupo) && !Util.isVazioOuBranco(tipoConsulta)){

			form.setIdFaturamentoGrupo(idFaturamentoGrupo);

			SistemaParametro sistemaParametro = (SistemaParametro) fachada.pesquisarParametrosDoSistema();
			Collection<Rota> colecaoRota = null;

			colecaoRota = fachada.consultarRotasGrupo(Integer.valueOf(idFaturamentoGrupo), sistemaParametro.getAnoMesFaturamento(),
							Integer.parseInt(tipoConsulta));

			sessao.setAttribute("colecaoRota", colecaoRota);
			sessao.setAttribute("tipoConsulta", tipoConsulta);
		}

		return retorno;
	}

}
