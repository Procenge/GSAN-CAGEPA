
package gcom.gui.cadastro.localidade;

import gcom.cadastro.localidade.Quadra;
import gcom.fachada.Fachada;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class ProcessarAtualizarQuadraRotaImoveisAction
				extends ExibidorProcessamentoTarefaRelatorio {

	/**
	 * [UC0025] Manter Quadra
	 * [SB0003] - Verificar Mudan�a de Rota da Quadra
	 * 
	 * @author Anderson Italo
	 * @date 23/01/2012
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");

		AtualizarQuadraActionForm formulario = (AtualizarQuadraActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		Fachada fachada = Fachada.getInstancia();

		Quadra quadra = null;
		if(sessao.getAttribute("quadraRelatorioImoveis") != null){

			quadra = (Quadra) sessao.getAttribute("quadraRelatorioImoveis");
		}

		// Atualiza a quadra
		Usuario usuarioLogado = (Usuario) sessao.getAttribute(Usuario.USUARIO_LOGADO);

		fachada.atualizarQuadra(quadra, usuarioLogado);

		if(!Util.isVazioOuBranco(formulario.getIndicadorSubstituirRota())){
			
			/*
			 * Caso o usu�rio tenha selecionado a op��o
			 * "Com a substitui��o das rotas de todos os im�veis da quadra"
			 */
			if(formulario.getIndicadorSubstituirRota().equals(ConstantesSistema.SIM.toString())){

				fachada.atualizarRotaImoveisPorQuadra(quadra.getId(), quadra.getRota().getId(), null);

			}else{

				/*
				 * Caso o usu�rio tenha selecionado a op��o
				 * "Com a substitui��o das rotas dos im�veis com rota igual � rota anterior", o
				 * sistema
				 * atualiza as rotas dos im�veis com rota igual � rota anterior
				 */
				fachada.atualizarRotaImoveisPorQuadra(quadra.getId(), quadra.getRota().getId(),
								Integer.valueOf(formulario.getIdRotaAnterior()));
			}
		}

		if(!Util.isVazioOuBranco(formulario.getIndicadorSubstituirDistritoOperacional())){

			/*
			 * Caso o usu�rio tenha selecionado a op��o
			 * "Com a substitui��o dos distritos operacionais de todos os im�veis da quadra"
			 */
			if(formulario.getIndicadorSubstituirDistritoOperacional().equals(ConstantesSistema.SIM.toString())){

				fachada.atualizarDistritoOperacionalImoveisPorQuadra(quadra.getId(), quadra.getDistritoOperacional().getId(), null);
			}else{

				/*
				 * Caso o usu�rio tenha selecionado a op��o
				 * "Com a substitui��o dos distritos operacionais dos im�veis com distrito operacional igual ao anterior"
				 * , o sistema
				 * atualiza os distritos operacionais dos im�veis com distrito igual ao anterior
				 */
				fachada.atualizarDistritoOperacionalImoveisPorQuadra(quadra.getId(), quadra.getDistritoOperacional().getId(),
								Integer.valueOf(formulario.getDistritoOperacionalIDAnterior()));
			}
		}

		// Limpa os dados da usados pela tela de atualiza��o da sess�o
		sessao.removeAttribute("quadraManter");
		sessao.removeAttribute("colecaoPerfilQuadra");
		sessao.removeAttribute("colecaoSistemaEsgoto");
		sessao.removeAttribute("colecaoZeis");
		sessao.removeAttribute("colecaoBacia");
		sessao.removeAttribute("rotaAlterada");
		sessao.removeAttribute("distritoOperacionalAlterado");

		// Monta p�gina de sucesso
		montarPaginaSucesso(httpServletRequest, "Quadra de n�mero  " + quadra.getNumeroQuadra() + " do setor comercial "
						+ quadra.getSetorComercial().getCodigo() + "-" + quadra.getSetorComercial().getDescricao() + " da localidade "
						+ quadra.getSetorComercial().getLocalidade().getId() + "-"
						+ quadra.getSetorComercial().getLocalidade().getDescricao() + " atualizada com sucesso.",
						"Realizar outra Manuten��o de Quadra", "exibirFiltrarQuadraAction.do?desfazer=S",
						"gerarRelatorioImoveisPorQuadraAction.do", "Emitir Relat�rio dos Im�veis da Quadra");
		return retorno;
	}
}
