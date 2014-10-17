
package gcom.gui.atendimentopublico.ordemservico;

import gcom.gui.GcomAction;
import gcom.micromedicao.hidrometro.FiltroHidrometroCapacidade;
import gcom.micromedicao.hidrometro.FiltroHidrometroDiametro;
import gcom.micromedicao.hidrometro.HidrometroCapacidade;
import gcom.micromedicao.hidrometro.HidrometroDiametro;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * [UC0711] Filtro para Emissão de Ordens Seletivas - Dados do Hidrômetro
 * 
 * @author Hebert Falcão
 * @date 13/05/2011
 */
public class ExibirInserirDadosDoHidrometroPopupAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirInserirDadosDoHidrometroPopup");

		// Obtém a instância da sessão
		HttpSession sessao = httpServletRequest.getSession(false);

		// Coleção Hidrômetro Diametro
		FiltroHidrometroDiametro filtroHidrometroDiametro = new FiltroHidrometroDiametro();
		filtroHidrometroDiametro.setCampoOrderBy(FiltroHidrometroDiametro.DESCRICAO);
		filtroHidrometroDiametro.adicionarParametro(new ParametroSimples(FiltroHidrometroDiametro.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoHidrometroDiametro = this.getFachada().pesquisar(filtroHidrometroDiametro, HidrometroDiametro.class.getName());

		sessao.setAttribute("colecaoHidrometroDiametro", colecaoHidrometroDiametro);

		// Coleção Hidrômetro Capacidade
		FiltroHidrometroCapacidade filtroHidrometroCapacidade = new FiltroHidrometroCapacidade();
		filtroHidrometroCapacidade.setCampoOrderBy(FiltroHidrometroCapacidade.DESCRICAO);
		filtroHidrometroCapacidade.adicionarParametro(new ParametroSimples(FiltroHidrometroCapacidade.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		Collection colecaoHidrometroCapacidade = this.getFachada().pesquisar(filtroHidrometroCapacidade,
						HidrometroCapacidade.class.getName());

		sessao.setAttribute("colecaoHidrometroCapacidade", colecaoHidrometroCapacidade);

		DadosDoHidrometroPopupActionForm dadosDoHidrometroPopupActionForm = (DadosDoHidrometroPopupActionForm) actionForm;
		dadosDoHidrometroPopupActionForm.setIdHidrometroDiametro("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
		dadosDoHidrometroPopupActionForm.setDescricaoHidrometroDiametro("");
		dadosDoHidrometroPopupActionForm.setIdHidrometroCapacidade("" + ConstantesSistema.NUMERO_NAO_INFORMADO);
		dadosDoHidrometroPopupActionForm.setDescricaoHidrometroCapacidade("");
		dadosDoHidrometroPopupActionForm.setIntervaloInstalacaoInicial("");
		dadosDoHidrometroPopupActionForm.setIntervaloInstalacaoFinal("");
		dadosDoHidrometroPopupActionForm.setNumeroLeituraAcumulada("");

		sessao.removeAttribute("closePage");

		return retorno;
	}
}
