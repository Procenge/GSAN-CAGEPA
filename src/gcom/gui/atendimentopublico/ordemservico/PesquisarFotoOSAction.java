
package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.OrdemServicoFotoOcorrencia;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

public class PesquisarFotoOSAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession();
		DynaValidatorForm carregarFotoOSActionForm = (DynaValidatorForm) actionForm;

		// RECUPERAR IDS DA SESSÃO OU FORM
		if(httpServletRequest.getParameter("idFoto") != null && !httpServletRequest.getParameter("idFoto").equalsIgnoreCase("")){
			OrdemServicoFotoOcorrencia osFoto = new OrdemServicoFotoOcorrencia();
			osFoto.setId(Integer.valueOf(httpServletRequest.getParameter("idFoto")));

			// PEQUISAR NA BASE DE DADOS AS FOTOS PELOS IDs
			Collection<OrdemServicoFotoOcorrencia> colecaoFotosOcorrencia = fachada.listarOSFoto(osFoto);

			byte[] foto = null;
			String mimeType = "image/jpeg";
			OutputStream out = null;
			try{
				httpServletResponse.setContentType(mimeType);
				out = httpServletResponse.getOutputStream();
				out.write(colecaoFotosOcorrencia.iterator().next().getFoto());
				out.flush();
				out.close();
			}catch(IOException ex){
				// Manda o erro para a página no request atual
				reportarErros(httpServletRequest, "erro.sistema");
			}
		}
		return null;
	}
}
