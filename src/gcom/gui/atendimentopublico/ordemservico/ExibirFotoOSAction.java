
package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.OrdemServicoFotoOcorrencia;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ExibirFotoOSAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		HttpSession sessao = httpServletRequest.getSession();
		ActionForward retorno = actionMapping.findForward("exibirFotoOSAction");
		Fachada fachada = Fachada.getInstancia();
		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");

		ConsultarDadosOrdemServicoActionForm formOS = (ConsultarDadosOrdemServicoActionForm) sessao
						.getAttribute("ConsultarDadosOrdemServicoActionForm");

		ConsultarDadosOrdemServicoPopupActionForm formPopup = null;
		EncerrarOrdemServicoActionForm encerrarOSForm = null;

		Integer idOrdemServicoProgramacao = null;
		Integer idOrdemServico = null;
		// varias actions de forms diferentes chamam this action, então o tratamento abaixo é apenas
		// para pegar os id´s de OS e OSprogramada
		if(formOS == null){
			encerrarOSForm = (EncerrarOrdemServicoActionForm) sessao.getAttribute("EncerrarOrdemServicoActionForm");
			if(encerrarOSForm == null){
				formPopup = (ConsultarDadosOrdemServicoPopupActionForm) sessao.getAttribute("ConsultarDadosOrdemServicoPopupActionForm");
				if(formPopup == null){
					AcompanharRoteiroProgramacaoOrdemServicoActionForm formOSProgramada = new AcompanharRoteiroProgramacaoOrdemServicoActionForm();
					formOSProgramada = (AcompanharRoteiroProgramacaoOrdemServicoActionForm) sessao
									.getAttribute("AcompanharRoteiroProgramacaoOrdemServicoActionForm");
					idOrdemServico = Integer.valueOf(formOSProgramada.getIdOrdemServico());
					idOrdemServicoProgramacao = Integer.valueOf(formOSProgramada.getIdOrdemServicoProgramacao());
				}else{
					idOrdemServico = Integer.valueOf(formPopup.getNumeroOS());
				}
			}else{
				idOrdemServico = Integer.valueOf(encerrarOSForm.getNumeroOS());
			}
		}else{
			idOrdemServico = Integer.valueOf(formOS.getNumeroOS());
		}
		// CONSULTAR IDS DAS IMAGENS
		OrdemServicoFotoOcorrencia osFoto = new OrdemServicoFotoOcorrencia();
		if(idOrdemServico != null){
			osFoto.setIdOrdemServico(idOrdemServico);
			osFoto.setIdOrdemServicoProgramacao(idOrdemServicoProgramacao);
			Collection<OrdemServicoFotoOcorrencia> colecaoOSFotos = fachada.listarOSFoto(osFoto);

			if(colecaoOSFotos == null || colecaoOSFotos.isEmpty()){
				throw new ActionServletException("atencao.ordem.servico.sem.imagem");
			}

			String[] idFotos = new String[colecaoOSFotos.size()];
			int i = 0;
			for(OrdemServicoFotoOcorrencia osFotoBase : colecaoOSFotos){
				idFotos[i] = osFotoBase.getId().toString();
				i++;
			}
			httpServletRequest.setAttribute("idFotos", idFotos);
		}
		String remover = (String) httpServletRequest.getParameter("remover");
		if(remover != null && remover.equalsIgnoreCase("true")){
			httpServletRequest.setAttribute("remover", remover);
		}
		return retorno;
	}
}
