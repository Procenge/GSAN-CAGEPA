
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
 * Inserir ZonaAbastecimento
 * 
 * @author Bruno Ferreira dos Santos
 * @created 02 de Fevereiro de 2011
 */

public class InserirZonaAbastecimentoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		// Obtém a instância da fachada
		Fachada fachada = Fachada.getInstancia();

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		InserirZonaAbastecimentoActionForm inserirZonaAbastecimentoActionForm = (InserirZonaAbastecimentoActionForm) actionForm;

		Usuario usuarioLogado = (Usuario) sessao.getAttribute("usuarioLogado");

		// Atualiza a entidade com os valores do formulário
		ZonaAbastecimento zonaAbastecimento = new ZonaAbastecimento();

		validarZonaAbastecimento(inserirZonaAbastecimentoActionForm);

		inserirZonaAbastecimentoActionForm.setFormValues(zonaAbastecimento);

		Integer idZonaAbastecimento = fachada.inserirZonaAbastecimento(zonaAbastecimento, usuarioLogado);

		montarPaginaSucesso(httpServletRequest, "Zona de Abastecimento de código  " + zonaAbastecimento.getCodigo()
						+ " inserida com sucesso.", "Inserir outra Zona de Abastecimento",
						"exibirInserirZonaAbastecimentoAction.do?menu=sim",
						"exibirAtualizarZonaAbastecimentoAction.do?idRegistroInseridoAtualizar=" + idZonaAbastecimento,
						"Atualizar Zona de Abastecimento Inserida");

		sessao.removeAttribute("colecaoUnidadeOperacional");
		sessao.removeAttribute("colecaoSistemaAbastecimento");

		return retorno;
	}

	/**
	 * @author isilva
	 * @param inserirZonaAbastecimentoActionForm
	 */
	private void validarZonaAbastecimento(InserirZonaAbastecimentoActionForm inserirZonaAbastecimentoActionForm){

		if(Util.isVazioOuBranco(inserirZonaAbastecimentoActionForm.getCodigo())){
			throw new ActionServletException("atencao.required", null, "Código");
		}

		if(Util.isVazioOuBranco(inserirZonaAbastecimentoActionForm.getDescricao())){
			throw new ActionServletException("atencao.required", null, "Descrição");
		}

		if(Util.isVazioOuBranco(inserirZonaAbastecimentoActionForm.getIdSistemaAbastecimento())
						|| inserirZonaAbastecimentoActionForm.getIdSistemaAbastecimento().equalsIgnoreCase(
										ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			throw new ActionServletException("atencao.required", null, "Sistema de Abastecimento");
		}

		if(Util.isVazioOuBranco(inserirZonaAbastecimentoActionForm.getIdDistritoOperacional())
						|| inserirZonaAbastecimentoActionForm.getIdDistritoOperacional().equalsIgnoreCase(
										ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			throw new ActionServletException("atencao.required", null, "Distrito Operacional");
		}

		if(Util.isVazioOuBranco(inserirZonaAbastecimentoActionForm.getIdLocalidade())
						|| inserirZonaAbastecimentoActionForm.getIdLocalidade().equalsIgnoreCase(
										ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			throw new ActionServletException("atencao.required", null, "Localidade");
		}

		if(Util.isVazioOuBranco(inserirZonaAbastecimentoActionForm.getFaixaPressaoInferior())){
			throw new ActionServletException("atencao.required", null, "Faixa Pressão Inferior");
		}

		if(Util.isVazioOuBranco(inserirZonaAbastecimentoActionForm.getFaixaPressaoSuperior())){
			throw new ActionServletException("atencao.required", null, "Faixa Pressão Superior");
		}

	}

}
