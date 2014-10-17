/**
 * 
 */

package gcom.gui.cadastro.aguaparatodos;

import gcom.cadastro.aguaparatodos.FaturamentoHistoricoAguaParaTodos;
import gcom.cadastro.aguaparatodos.FiltroFaturamentoHistoricoAguaParaTodos;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.Intervalo;
import gcom.util.filtro.ParametroSimples;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Bruno Ferreira dos Santos
 */
public class FaturamentoHistoricoAguaParaTodosAction
				extends GcomAction {

	/**
	 * @author Bruno Ferreira dos Santos
	 * @date 28/01/2011
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("consultarFaturamentoHistoricoAguaParaTodos");

		FaturamentoHistoricoAguaParaTodosActionForm form = (FaturamentoHistoricoAguaParaTodosActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		String idImovel = httpServletRequest.getParameter("idImovel");
		String dataHabilitacao = httpServletRequest.getParameter("dataHabilitacao");

		Date data1 = Util.formatarDataInicial(new Date(Long.parseLong(dataHabilitacao)));
		Date data2 = Util.formatarDataFinal(new Date(Long.parseLong(dataHabilitacao)));

		FiltroFaturamentoHistoricoAguaParaTodos filtroFaturamentoHistoricoAguaParaTodos = new FiltroFaturamentoHistoricoAguaParaTodos();
		filtroFaturamentoHistoricoAguaParaTodos.adicionarParametro(new ParametroSimples(FiltroFaturamentoHistoricoAguaParaTodos.IMOVEL_ID,
						Integer.valueOf(idImovel)));
		filtroFaturamentoHistoricoAguaParaTodos.adicionarParametro(new Intervalo(FiltroFaturamentoHistoricoAguaParaTodos.DATA_HABILITACAO,
						data1, data2));
		filtroFaturamentoHistoricoAguaParaTodos.setCampoOrderBy(FiltroFaturamentoHistoricoAguaParaTodos.ANO_MES_REFERENCIA);

		Collection colFaturamento = fachada.pesquisar(filtroFaturamentoHistoricoAguaParaTodos, FaturamentoHistoricoAguaParaTodos.class
						.getName());

		form.setColFaturamentoHistorico(colFaturamento);

		if(colFaturamento.isEmpty()){
			throw new ActionServletException("atencao.nenhum_historico_faturamento");
		}

		return retorno;
	}
}
