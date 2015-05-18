
package gcom.gui.atendimentopublico.ordemservico;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.parametrizacao.atendimentopublico.ParametroAtendimentoPublico;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirInserirAtividadeAction
				extends GcomAction {

	@Override
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("atividadeInserir");

		AtividadeActionForm atividadeActionForm = (AtividadeActionForm) actionForm;

		atividadeActionForm.setDescricao("");
		atividadeActionForm.setDescricaoAbreviada("");
		atividadeActionForm.setIndicadorAtividadeUnica(String.valueOf(ConstantesSistema.NAO));
		if(Fachada.getInstancia().verificarExistenciaAtividadeUnica(ConstantesSistema.SIM.toString()) != null){
			atividadeActionForm.setVerificadorAtividadeUnica("Existe");
		}
		String permiteCobrarHora = "0";
		try{
			permiteCobrarHora = ParametroAtendimentoPublico.P_PERMITE_COBRAR_HORA_OS.executar();
		}catch(ControladorException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		httpServletRequest.getSession().setAttribute("permiteCobrarHora", permiteCobrarHora);
		return retorno;
	}

}
