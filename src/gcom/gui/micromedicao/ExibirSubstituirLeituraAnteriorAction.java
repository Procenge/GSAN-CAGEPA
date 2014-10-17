
package gcom.gui.micromedicao;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.micromedicao.medicao.MedicaoHistorico;
import gcom.util.ControladorException;
import gcom.util.Util;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3009] - Substituir Leituras Anteriores
 * 
 * @author Hebert Falcão
 * @date 09/06/2011
 */
public class ExibirSubstituirLeituraAnteriorAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("substituirLeituraAnterior");

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		sessao.removeAttribute("colecaoMedicaoHistorico");

		SubstituirLeituraActionForm substituirLeituraActionForm = (SubstituirLeituraActionForm) actionForm;

		String idImovelParamStr = httpServletRequest.getParameter("idImovel");
		Integer idImovelParam = Util.converterStringParaInteger(idImovelParamStr);

		if(!Util.isVazioOuBranco(idImovelParamStr)){
			try{
				String enderecoImovel = fachada.pesquisarEnderecoFormatado(idImovelParam);
				httpServletRequest.setAttribute("enderecoImovel", enderecoImovel);
			}catch(NumberFormatException e){
				e.printStackTrace();
			}catch(ControladorException e){
				e.printStackTrace();
			}
		}

		if(httpServletRequest.getParameter("peloMenu") != null){
			sessao.setAttribute("peloMenu", true);
		}

		String idImovelFormStr = substituirLeituraActionForm.getIdImovelSubstituirLeitura();
		Integer idImovelForm = Util.converterStringParaInteger(idImovelFormStr);

		if(!Util.isVazioOuBranco(idImovelParamStr) || !Util.isVazioOuBranco(idImovelFormStr)){
			Integer idImovel = null;

			if(!Util.isVazioOuBranco(idImovelParamStr)){
				idImovel = idImovelParam;
			}else if(httpServletRequest.getParameter("tipoConsulta") != null){
				idImovel = idImovelForm;
			}

			try{
				String inscricaoFormatada = fachada.pesquisarInscricaoImovel(idImovel, true);

				if(Util.isVazioOuBranco(inscricaoFormatada)){
					substituirLeituraActionForm.setInscricaoImovel("Matrícula inexistente.");
					substituirLeituraActionForm.setIdImovelSubstituirLeitura(null);

					httpServletRequest.setAttribute("corTexto", "#ff0000");
				}else{
					substituirLeituraActionForm.setInscricaoImovel(inscricaoFormatada);

					String endereceoFormatado = fachada.pesquisarEnderecoFormatado(idImovel);
					httpServletRequest.setAttribute("enderecoImovel", endereceoFormatado);
					httpServletRequest.setAttribute("corTexto", "#000000");

					sessao.setAttribute("idImovelSelecionado", idImovel);

					Collection<MedicaoHistorico> colecaoMedicaoHistorico = fachada.pesquisaMedicaoHistoricoSubstituirLeitura(idImovel);

					if(!Util.isVazioOrNulo(colecaoMedicaoHistorico)){
						sessao.setAttribute("colecaoMedicaoHistorico", colecaoMedicaoHistorico);
					}else{
						throw new ActionServletException("atencao.nenhuma_leitura_substituir");
					}
				}
			}catch(ControladorException e){
				e.printStackTrace();
			}

		}

		httpServletRequest.setAttribute("nomeCampo", "idImovelSubstituirLeitura");

		return retorno;
	}

}
