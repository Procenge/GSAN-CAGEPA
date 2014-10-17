
package gcom.gui.faturamento;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.SetorComercial;
import gcom.fachada.Fachada;
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
 * @author Flávio
 * @date 26/09/2007
 */

public class ExibirFiltrarQualidadeAguaAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("filtrarQualidadeAgua");

		Fachada fachada = Fachada.getInstancia();

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltrarQualidadeAguaActionForm form = (FiltrarQualidadeAguaActionForm) actionForm;

		// Código para checar ou não o ATUALIZAR

		String primeiraVez = httpServletRequest.getParameter("menu");
		if(primeiraVez != null && !primeiraVez.equals("")){
			sessao.setAttribute("indicadorAtualizar", "1");
			form.setTipoPesquisa(ConstantesSistema.TIPO_PESQUISA_INICIAL.toString());

		}

		String idLocalidade = form.getIdLocalidade();
		String codigoSetor = form.getCodigoSetor();

		// Verificar se o número do Localidade não está cadastrado
		if(idLocalidade != null && !idLocalidade.trim().equals("")){

			// Filtro para descobrir id do Localidade
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));

			Collection colecaoLocalidade = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoLocalidade == null || colecaoLocalidade.isEmpty()){
				form.setIdLocalidade("");
				form.setNomeLocalidade("LOCALIDADE INEXISTENTE");
				httpServletRequest.setAttribute("idLocalidadeNaoEncontrado", "exception");
				httpServletRequest.setAttribute("nomeCampo", "idLocalidade");
			}else{
				Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);
				form.setIdLocalidade(localidade.getId().toString());
				form.setNomeLocalidade(localidade.getDescricao());
				httpServletRequest.setAttribute("nomeCampo", "codigoSetor");
			}
		}

		// Verifica se o id do imovel não está cadastrado
		if(codigoSetor != null && !codigoSetor.trim().equals("")){

			// Filtro para descobrir id do Imovel
			FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();

			filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, codigoSetor));

			Collection colecaoSetor = fachada.pesquisar(filtroSetorComercial, SetorComercial.class.getName());

			if(colecaoSetor == null || colecaoSetor.isEmpty()){
				form.setCodigoSetor("");
				form.setNomeSetor("SETOR COMERCIAL INEXISTENTE");
				httpServletRequest.setAttribute("codigoSetorNaoEncontrado", "exception");
				httpServletRequest.setAttribute("nomeCampo", "codigoSetor");
			}else{
				SetorComercial setorComercial = (SetorComercial) Util.retonarObjetoDeColecao(colecaoSetor);
				form.setCodigoSetor(setorComercial.getCodigo() + "");
				form.setNomeSetor(setorComercial.getDescricao());
				httpServletRequest.setAttribute("nomeCampo", "fonteCaptacao");
			}
		}

		return retorno;
	}
}
