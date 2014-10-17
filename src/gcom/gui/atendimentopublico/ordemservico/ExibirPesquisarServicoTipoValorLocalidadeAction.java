package gcom.gui.atendimentopublico.ordemservico;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
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
 * Exibe a pesquisa de Servico Tipo Valor Localidade
 * 
 * @author Ado Rocha
 * @date 29/04/2014
 */
public class ExibirPesquisarServicoTipoValorLocalidadeAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		HttpSession sessao = httpServletRequest.getSession(false);
		PesquisarServicoTipoValorLocalidadeActionForm form = (PesquisarServicoTipoValorLocalidadeActionForm) actionForm;
		ActionForward retorno = actionMapping.findForward("exibirServicoTipoValorLocalidadePopup");

		boolean erro = false;

		// ---Parte que trata o código quando o usuário tecla enter
		String objetoConsulta = httpServletRequest.getParameter("objetoConsulta");

		// Pesquisar Localidade
		if(!Util.isVazioOuBranco(objetoConsulta) && objetoConsulta.trim().equals("1")){
			this.pesquisarLocalidade(form, httpServletRequest);
		}

		if("inserir".equals(form.getMethod())){
			if(!erro){
				form.setMethod(null);
				Collection colecaoServicoTipoValorLocalidade = (Collection) sessao.getAttribute("colecaoServicoTipoValorLocalidade");
				httpServletRequest.setAttribute("close", "true");
			}
		}else{
			form.setMethod(null);
			httpServletRequest.removeAttribute("close");
		}

		if(httpServletRequest.getParameter("limparCampos") != null){
			form.reset();
		}
		return retorno;
	}


	/**
	 * Pesquisar Localidade
	 * 
	 * @author Ado Rocha
	 * @date 29/04/2014
	 */
	private void pesquisarLocalidade(PesquisarServicoTipoValorLocalidadeActionForm form, HttpServletRequest httpServletRequest){

		String codigoLocalidade = form.getIdLocalidadeFiltro();
		if(codigoLocalidade == null || codigoLocalidade.equals("")){
			throw new ActionServletException("atencao.localidade_nao_informada");
		}

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, new Integer(form.getIdLocalidadeFiltro())));

		// Pesquisa de acordo com os parâmetros informados no filtro
		Collection colecaoLocalidade = Fachada.getInstancia().pesquisar(filtroLocalidade, Localidade.class.getName());

		// Verifica se a pesquisa retornou algum objeto para a coleção
		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){

			// Obtém o objeto da coleção pesquisada
			Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidade);

			form.setIdLocalidadeFiltro("" + localidade.getId());
			form.setLocalidadeDescricaoFiltro(localidade.getDescricao());
			httpServletRequest.setAttribute("localidadeEncontrado", "true");

		}else{
			form.setIdLocalidadeFiltro(null);
			httpServletRequest.setAttribute("nomeCampo", "idLocalidadeFiltro");
			httpServletRequest.setAttribute("localidadeEncontrado", "exception");
			form.setLocalidadeDescricaoFiltro("Localidade inexistente");
		}
	}

}
