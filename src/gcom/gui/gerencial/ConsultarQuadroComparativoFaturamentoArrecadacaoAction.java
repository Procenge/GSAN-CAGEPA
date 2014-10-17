/**
 * 
 */

package gcom.gui.gerencial;

import gcom.cadastro.localidade.FiltroGerenciaRegional;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.fachada.Fachada;
import gcom.gerencial.bean.FiltroQuadroComparativoFaturamentoArrecadacaoHelper;
import gcom.gerencial.bean.QuadroComparativoFaturamentoArrecadacaoHelper;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Bruno Ferreira dos Santos
 */
public class ConsultarQuadroComparativoFaturamentoArrecadacaoAction
				extends GcomAction {

	/**
	 * @author Bruno Ferreira dos Santos
	 * @date 27/01/2011
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("consultarQuadroComparativoFaturamentoArrecadacao");

		ConsultarQuadroComparativoFaturamentoArrecadacaoActionForm form = (ConsultarQuadroComparativoFaturamentoArrecadacaoActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		String mesAnoReferenciaStr = form.getMesAnoReferencia();
		String opcaoTotalizacao = form.getOpcaoTotalizacao();

		String gerenciaRegional = null;
		String localidade = null;

		FiltroQuadroComparativoFaturamentoArrecadacaoHelper filtro = new FiltroQuadroComparativoFaturamentoArrecadacaoHelper();

		if(!Util.isVazioOuBranco(mesAnoReferenciaStr)){
			filtro.setAnoMesReferencia(Util.formatarMesAnoComBarraParaAnoMes(mesAnoReferenciaStr));
		}else{
			throw new ActionServletException("Informe o Mês/Ano de Referência");
		}

		if(!Util.isVazioOuBranco(opcaoTotalizacao)){

			filtro.setOpcaoTotalizacao(Integer.parseInt(opcaoTotalizacao));

			if(filtro.getOpcaoTotalizacao() == FiltroQuadroComparativoFaturamentoArrecadacaoHelper.OPCAO_TOTALIZACAO_GERENCIA_REGIONAL){

				gerenciaRegional = form.getGerenciaRegional();

				if(!Util.isVazioOuBranco(gerenciaRegional)){
					filtro.setIdGerenciaRegional(Integer.valueOf(gerenciaRegional));

					FiltroGerenciaRegional filtroGerenciaRegional = new FiltroGerenciaRegional();
					filtroGerenciaRegional.adicionarParametro(new ParametroSimples(FiltroGerenciaRegional.ID, filtro
									.getIdGerenciaRegional()));

					GerenciaRegional gerencia = (GerenciaRegional) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroGerenciaRegional,
									GerenciaRegional.class.getName()));

					if(gerencia != null){
						filtro.setNomeGerenciaRegional(gerencia.getNome());
					}

				}else{
					throw new ActionServletException("Informe a Gerência Regional");
				}
				
			}else if(filtro.getOpcaoTotalizacao() == FiltroQuadroComparativoFaturamentoArrecadacaoHelper.OPCAO_TOTALIZACAO_LOCALIDADE){
				
				localidade = form.getLocalidade();

				if(!Util.isVazioOuBranco(localidade)){
					filtro.setIdLocalidade(Integer.valueOf(localidade));

					FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
					filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.ID, filtro.getIdLocalidade()));

					Localidade local = (Localidade) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroLocalidade,
									Localidade.class.getName()));

					if(local != null){
						filtro.setDescricaoLocalidade(local.getDescricaoComId());
					}
				}else{
					throw new ActionServletException("Informe a Localidade");
				}
			}

		}else{
			throw new ActionServletException("Informe a Opção de Totalização");
		}

		// Pesquisar dados
		Collection<QuadroComparativoFaturamentoArrecadacaoHelper> colecaoQuadroComparativoFaturamentoArrecadacao = fachada
						.consultarQuadroComparativoFaturamentoArrecadacao(filtro);

		if(!Util.isVazioOrNulo(colecaoQuadroComparativoFaturamentoArrecadacao)){

			httpServletRequest.setAttribute("colecaoQuadroComparativoFaturamentoArrecadacao",
							colecaoQuadroComparativoFaturamentoArrecadacao);
		}else{

			throw new ActionServletException("atencao.nao_ha_dados");
		}

		return retorno;
	}

}
