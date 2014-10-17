
package gcom.gui.relatorio.faturamento;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.com.procenge.parametrosistema.api.ParametroSistema;

public class ExibirGerarRelatorioProvisaoReceitaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		String anoMes = "";

		try{

			anoMes = ((ParametroSistema) Fachada.getInstancia().obterParametroSistema("P_ANO_MES_REFERENCIA_PROVISAO_RECEITA")).getValor();

		}catch(Exception e){

			throw new ActionServletException("erro.sistema");

		}

		httpServletRequest.setAttribute("mesAno", Util.formatarAnoMesParaMesAno(anoMes));

		return actionMapping.findForward("exibirGerarRelatorioProvisaoReceita");

	}

}
