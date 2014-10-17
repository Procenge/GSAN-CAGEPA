/**
 * 
 */

package gcom.gui.cadastro.unidadeoperacional;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.operacional.FiltroSistemaAbastecimento;
import gcom.operacional.SistemaAbastecimento;
import gcom.util.ConstantesSistema;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Péricles Tavares
 */
public class ExibirInserirUnidadeOperacionalAction
				extends GcomAction {

	/**
	 * Este caso de uso permite inserir uma UnidadeOperacional
	 * 
	 * @author Péricles Tavares
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirInserirUnidadeOperacionalAction");

		InserirUnidadeOperacionalActionForm form = (InserirUnidadeOperacionalActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		// atribui os valores q vem pelo request as variaveis
		String idLocalidade = (String) form.getLocalidade();
		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		// cria a colecao para receber a pesquisa
		Collection localidades = new HashSet();
		if(idLocalidade != null && !idLocalidade.toString().trim().equalsIgnoreCase("")){
			filtroLocalidade.limparListaParametros();
			// coloca parametro no filtro
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
			filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, Integer.valueOf(idLocalidade)));
			// pesquisa
			localidades = fachada.pesquisar(filtroLocalidade, Localidade.class.getName());
			if(localidades != null && !localidades.isEmpty()){
				// Localidade foi encontrada
				form.setLocalidade(Util.adicionarZerosEsquedaNumero(3, Integer.valueOf(
								((Localidade) ((List) localidades).get(0)).getId().toString()).toString()));
				form.setDescricaoLocalidade(((Localidade) ((List) localidades).get(0)).getDescricao());
			}else{
				httpServletRequest.setAttribute("codigoLocalidadeNaoEncontrada", "true");
				form.setLocalidade("");
			}
		}

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroSistemaAbastecimento filtroSistemaAbastecimento = new FiltroSistemaAbastecimento();
		Collection<SistemaAbastecimento> sistemasAbastecimento = fachada.pesquisar(filtroSistemaAbastecimento, SistemaAbastecimento.class
						.getName());
		// [FS0001] - Verificar existência de dados
		if(sistemasAbastecimento == null || sistemasAbastecimento.isEmpty()){
			throw new ActionServletException("atencao.entidade_sem_dados_para_selecao", null, "Sistema Abastecimento");
		}
		sessao.setAttribute("colecaoSistemaAbastecimento", sistemasAbastecimento);

		return retorno;
	}

}
