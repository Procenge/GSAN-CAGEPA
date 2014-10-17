
package gcom.gui.faturamento;

import gcom.cadastro.imovel.Imovel;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC3014] Emitir Declaração Anual Quitação Débitos
 * 
 * @author Hebert Falcão
 * @created 27/04/2013
 */
public class ExibirEmitirDeclaracaoAnualQuitacaoDebitosAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("exibirEmitirDeclaracaoAnualQuitacaoDebitos");

		// Obtém a sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		EmitirDeclaracaoAnualQuitacaoDebitosActionForm emitirDeclaracaoAnualQuitacaoDebitosActionForm = (EmitirDeclaracaoAnualQuitacaoDebitosActionForm) actionForm;

		// Botão Desfazer
		String desfazer = httpServletRequest.getParameter("desfazer");

		if(desfazer != null && desfazer.equalsIgnoreCase("S")){

			emitirDeclaracaoAnualQuitacaoDebitosActionForm.setIdImovel(null);
			emitirDeclaracaoAnualQuitacaoDebitosActionForm.setInscricaoImovel(null);
			emitirDeclaracaoAnualQuitacaoDebitosActionForm.setAnoBaseDeclaracaoInformado(null);
			emitirDeclaracaoAnualQuitacaoDebitosActionForm.setIdFaturamentoGrupo("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
			sessao.removeAttribute("colecaoFaturamentoGrupo");
		}

		this.pesquisarImovel(emitirDeclaracaoAnualQuitacaoDebitosActionForm, Fachada.getInstancia(), httpServletRequest);

		if(sessao.getAttribute("colecaoFaturamentoGrupo") == null){

			FiltroFaturamentoGrupo filtroFaturamentoGrupo = new FiltroFaturamentoGrupo();
			filtroFaturamentoGrupo.setCampoOrderBy(FiltroFaturamentoGrupo.DESCRICAO);
			filtroFaturamentoGrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.INDICADOR_USO,
							ConstantesSistema.INDICADOR_USO_ATIVO));

			Collection<FaturamentoGrupo> colecaoFaturamentoGrupo = this.getFachada().pesquisar(filtroFaturamentoGrupo,
							FaturamentoGrupo.class.getName());

			if(Util.isVazioOrNulo(colecaoFaturamentoGrupo)){
				throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "FATURAMENTO_GRUPO");
			}else{
				sessao.setAttribute("colecaoFaturamentoGrupo", colecaoFaturamentoGrupo);
			}
		}

		return retorno;
	}

	private void pesquisarImovel(EmitirDeclaracaoAnualQuitacaoDebitosActionForm form, Fachada fachada, HttpServletRequest httpServletRequest){

		if(!Util.isVazioOuBranco(form.getIdImovel()) && Util.isInteger(form.getIdImovel())){

			Imovel imovel = fachada.pesquisarImovel(Util.obterInteger(form.getIdImovel()));

			if(imovel != null){

				form.setInscricaoImovel(imovel.getInscricaoFormatada());
				httpServletRequest.setAttribute("imovelExistente", "true");
				httpServletRequest.removeAttribute("imovelInexistente");
			}else{

				httpServletRequest.removeAttribute("imovelExistente");
				httpServletRequest.setAttribute("imovelInexistente", "true");
				form.setInscricaoImovel("IMÓVEL INEXISTENTE");
			}
		}else{

			form.setIdImovel(null);
			form.setInscricaoImovel(null);
			httpServletRequest.removeAttribute("imovelInexistente");
			httpServletRequest.setAttribute("imovelExistente", "true");
		}
	}

}
