
package gcom.gui.relatorio.faturamento;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.fachada.Fachada;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

/**
 * Action que faz a exibição da tela para o usuário setar os parâmetros
 * necessário para a geração do relatório
 * [UC3047] Gerar Relatório Faturamento dos Imóveis da Tarifa Social
 * 
 * @author Ricardo Rodrigues
 * @since 23/03/2012
 */
public class ExibirGerarRelatorioFaturamentoTarifaSocialAction
				extends GcomAction {

	/*
	 * Constantes dos parâmetros do formulário, caso precise passar valores para eles.
	 */
	private static final String PARAMETRO_P_LC_IM = "P_LC_IM";

	private static final String PARAMETRO_NOME_LOCALIDADE = "nomeLocalidade";

	/**
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioFaturamentoTarifaSocialAction");

		DynaActionForm dynaForm = (DynaActionForm) actionForm;

		// Valida a parte de quando o usuário pesquisa pelo enter
		this.pesquisarLocalidade(httpServletRequest, dynaForm.getMap());

		return retorno;

	}

	/**
	 * Método de pesquisar a localidade quando é digitado um valor e pressionado ENTER
	 * 
	 * @author Ricardo Rodrigues.
	 * @date 27/03/2012
	 * @param httpServletRequest
	 * @param parametros
	 */
	private void pesquisarLocalidade(HttpServletRequest httpServletRequest, Map<String, Object> parametros){

		// Localidade
		Fachada fachada = Fachada.getInstancia();

		String idLocalidade = (String) parametros.get(PARAMETRO_P_LC_IM);

		if(idLocalidade != null && !idLocalidade.equals("")){
			FiltroLocalidade filtroLocalidade = new FiltroLocalidade();

			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, idLocalidade));

			Collection colecaoLocalidades = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

			if(colecaoLocalidades != null && !colecaoLocalidades.isEmpty()){
				Localidade localidade = (Localidade) Util.retonarObjetoDeColecao(colecaoLocalidades);

				parametros.put(PARAMETRO_P_LC_IM, localidade.getId().toString());
				parametros.put(PARAMETRO_NOME_LOCALIDADE, localidade.getDescricao());
				httpServletRequest.setAttribute("nomeCampo", "P_LC_IM");
			}else{
				parametros.put(PARAMETRO_P_LC_IM, "");
				parametros.put(PARAMETRO_NOME_LOCALIDADE, "Localidade Inexistente");
				httpServletRequest.setAttribute("idLocalidadeNaoEncontrada", "true");
				httpServletRequest.setAttribute("nomeCampo", "P_LC_IM");
			}

		}
	}
}
