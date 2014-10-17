
package gcom.gui.operacional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.ManutencaoRegistroActionForm;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.FachadaException;

/**
 * Remover ZonaAbastecimento
 * 
 * @author Bruno Ferreira dos Santos
 * @date 02/02/2011
 */

public class RemoverZonaAbastecimentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ManutencaoRegistroActionForm manutencaoRegistroActionForm = (ManutencaoRegistroActionForm) actionForm;

		String[] ids = manutencaoRegistroActionForm.getIdRegistrosRemocao();

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		// mensagem de erro quando o usuário tenta excluir sem ter selecionado
		// nenhum registro
		if(ids == null || ids.length == 0){
			throw new ActionServletException("atencao.registros.nao_selecionados");
		}

		// Captura a Exceção para que, no caso de ser por dependência na exclusão, lança mensagem
		// explicativa.
		try{
			fachada.removerZonaAbastecimento(ids, usuarioLogado);
		}catch(FachadaException ex){
			if(ex.getMessage() != null && ex.getMessage().toString().equals("atencao.dependencias.existentes")){
				throw new ActionServletException("atencao.entidade.possui.dependentes", "Zona de Abastecimento", "Unidade Operacional",
								"Unidade Operacional");
			}else{
				throw new FachadaException(ex.getMessage(), ex, ex.getParametroMensagem());
			}
		}

		// Monta a página de sucesso
		if(retorno.getName().equalsIgnoreCase("telaSucesso")){
			montarPaginaSucesso(httpServletRequest, ids.length + " Zona(s) de Abastecimento removido(s) com sucesso.",
							"Realizar outra Manutenção de Zona de Abastecimento", "exibirFiltrarZonaAbastecimentoAction.do?menu=sim");
		}

		return retorno;

	}

}
