
package gcom.gui.cobranca;

import gcom.cobranca.BoletoBancarioMotivoCancelamento;
import gcom.cobranca.FiltroBoletoBancarioMotivoCancelamento;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3023] Manter Boleto Bancário
 * 
 * @author Hebert Falcão
 * @date 12/10/2011
 */
public class ExibirCancelarBoletoBancarioAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("exibirCancelarBoletoBancarioAction");

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		// Boletos Selecionados
		CancelarBoletoBancarioActionForm cancelarBoletoBancarioActionForm = (CancelarBoletoBancarioActionForm) actionForm;

		String[] idRegistrosCancelamento = cancelarBoletoBancarioActionForm.getIdRegistrosCancelamento();
		sessao.setAttribute("idRegistrosCancelamento", idRegistrosCancelamento);

		// Motivo do Cancelamento
		FiltroBoletoBancarioMotivoCancelamento filtroMotivoCancelamento = new FiltroBoletoBancarioMotivoCancelamento(
						FiltroBoletoBancarioMotivoCancelamento.DESCRICAO);
		filtroMotivoCancelamento.adicionarParametro(new ParametroSimples(FiltroBoletoBancarioMotivoCancelamento.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection<BoletoBancarioMotivoCancelamento> colecaoBoletoBancarioMotivoCancelamento = fachada.pesquisar(filtroMotivoCancelamento,
						BoletoBancarioMotivoCancelamento.class.getName());

		// FS0003 - Verifica existência de dados
		if(Util.isVazioOrNulo(colecaoBoletoBancarioMotivoCancelamento)){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Boleto Bancário Motivo Cancelamento");
		}

		sessao.setAttribute("colecaoBoletoBancarioMotivoCancelamento", colecaoBoletoBancarioMotivoCancelamento);

		return retorno;
	}

}
