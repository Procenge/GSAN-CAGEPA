
package gcom.gui.operacional;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.ZonaAbastecimento;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Atualizar ZonaAbastecimento
 * 
 * @author Bruno Ferreira dos Santos
 * @date 02/02/2011
 */

public class AtualizarZonaAbastecimentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("telaSucesso");

		AtualizarZonaAbastecimentoActionForm atualizarZonaAbastecimentoActionForm = (AtualizarZonaAbastecimentoActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		ZonaAbastecimento zonaAbastecimento = (ZonaAbastecimento) sessao.getAttribute("zonaAbastecimento");

		validarZonaAbastecimento(atualizarZonaAbastecimentoActionForm);

		// Atualiza a entidade com os valores do formulário
		atualizarZonaAbastecimentoActionForm.setFormValues(zonaAbastecimento);

		// atualiza na base de dados de Sistema Esgoto
		fachada.atualizarZonaAbastecimento(zonaAbastecimento, usuarioLogado);

		// Monta a página de sucesso
		montarPaginaSucesso(httpServletRequest, "Zona de Abastecimento de código " + zonaAbastecimento.getCodigo()
						+ " alterado com sucesso", "Realizar outra Manutenção de Zona de Abastecimento",
						"exibirFiltrarZonaAbastecimentoAction.do?menu=sim");

		return retorno;
	}

	/**
	 * @author isilva
	 * @param inserirZonaAbastecimentoActionForm
	 */
	private void validarZonaAbastecimento(AtualizarZonaAbastecimentoActionForm atualizarZonaAbastecimentoActionForm){

		if(Util.isVazioOuBranco(atualizarZonaAbastecimentoActionForm.getCodigo())){
			throw new ActionServletException("atencao.required", null, "Código");
		}

		if(Util.isVazioOuBranco(atualizarZonaAbastecimentoActionForm.getDescricao())){
			throw new ActionServletException("atencao.required", null, "Descrição");
		}

		if(Util.isVazioOuBranco(atualizarZonaAbastecimentoActionForm.getSistemaAbastecimento())){
			throw new ActionServletException("atencao.required", null, "Sistema de Abastecimento");
		}

		if(Util.isVazioOuBranco(atualizarZonaAbastecimentoActionForm.getIdDistritoOperacional())
						|| atualizarZonaAbastecimentoActionForm.getIdDistritoOperacional().equalsIgnoreCase(
										ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			throw new ActionServletException("atencao.required", null, "Distrito Operacional");
		}

		if(Util.isVazioOuBranco(atualizarZonaAbastecimentoActionForm.getIdLocalidade())
						|| atualizarZonaAbastecimentoActionForm.getIdLocalidade().equalsIgnoreCase(
										ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			throw new ActionServletException("atencao.required", null, "Localidade");
		}

		if(Util.isVazioOuBranco(atualizarZonaAbastecimentoActionForm.getFaixaPressaoInferior())){
			throw new ActionServletException("atencao.required", null, "Faixa Pressão Inferior");
		}

		if(Util.isVazioOuBranco(atualizarZonaAbastecimentoActionForm.getFaixaPressaoSuperior())){
			throw new ActionServletException("atencao.required", null, "Faixa Pressão Superior");
		}

		if(Integer.parseInt(atualizarZonaAbastecimentoActionForm.getFaixaPressaoInferior()) > Integer
						.parseInt(atualizarZonaAbastecimentoActionForm.getFaixaPressaoSuperior())){
			throw new ActionServletException("atencao.valor.maior.que.outro", "Faixa de Pressão Inferior", "Faixa de Pressão Superior");
		}

	}
}
