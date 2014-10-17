
package gcom.gui.atendimentopublico.ordemservico;

import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.seguranca.acesso.usuario.UsuarioAcao;
import gcom.seguranca.acesso.usuario.UsuarioAcaoUsuarioHelper;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

public class RemoverOrdemServicoFotoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession();
		ActionForward retorno = actionMapping.findForward("telaSucessoPopup");
		DynaValidatorForm form = (DynaValidatorForm) actionForm;

		Usuario usuario = (Usuario) sessao.getAttribute("usuarioLogado");
		// ------------ REGISTRAR TRANSAÇÃO ----------------
		UsuarioAcaoUsuarioHelper usuarioAcaoUsuarioHelper = new UsuarioAcaoUsuarioHelper(usuario, UsuarioAcao.USUARIO_ACAO_EFETUOU_OPERACAO);
		Collection<UsuarioAcaoUsuarioHelper> colecaoUsuarios = new ArrayList();
		colecaoUsuarios.add(usuarioAcaoUsuarioHelper);
		// ------------ REGISTRAR TRANSAÇÃO ----------------

		if(form.get("idFotosRemovidas") != null){
			String[] idsSplitado = new String[2];
			String[] ids = new String[2];
			ids = (String[]) form.get("idFotosRemovidas");
			for(int i = 0; i < ids.length; i++){
				idsSplitado = ids[i].split(",");
			}

			fachada.remover(idsSplitado, "OrdemServicoFotoOcorrencia", null, colecaoUsuarios);
		}

		// Monta a página de sucesso
		if(retorno.getName().equalsIgnoreCase("telaSucessoPopup")){
			montarPaginaSucesso(httpServletRequest, "Fotos removidas com sucesso.", "", "");
		}

		return retorno;
	}
}
