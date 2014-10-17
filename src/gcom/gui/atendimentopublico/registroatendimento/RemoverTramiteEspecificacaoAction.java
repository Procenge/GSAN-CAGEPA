
package gcom.gui.atendimentopublico.registroatendimento;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.gui.ManutencaoRegistroActionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Remover Tr�mite Especifica��o
 * 
 * @author Hebert Falc�o
 * @date 25/03/2011
 */
public class RemoverTramiteEspecificacaoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		ManutencaoRegistroActionForm form = (ManutencaoRegistroActionForm) actionForm;

		// Obt�m a inst�ncia da fachada
		Fachada fachada = Fachada.getInstancia();

		String[] ids = form.getIdRegistrosRemocao();

		// Mensagem de erro quando o usu�rio tenta excluir sem ter selecionado nenhum registro
		if(ids == null || ids.length == 0){
			throw new ActionServletException("atencao.registros.nao_selecionados");
		}

		fachada.removerTramiteEspecificacao(ids);

		// Monta a p�gina de sucesso
		if(retorno.getName().equalsIgnoreCase("telaSucesso")){
			montarPaginaSucesso(httpServletRequest, ids.length + " tr�mite(s) por especifica��o removido(s) com sucesso",
							"Realizar outra Manuten��o de Tr�mite por Especifica��o", "exibirFiltrarTramiteEspecificacaoAction.do?menu=sim");
		}

		return retorno;
	}

}