
package gcom.gui.faturamento.conta;

import gcom.fachada.Fachada;
import gcom.faturamento.bean.FiltroContaSimularCalculoHelper;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.consumotarifa.ConsumoTarifaVigencia;
import gcom.faturamento.consumotarifa.FiltroConsumoTarifa;
import gcom.faturamento.consumotarifa.FiltroConsumoTarifaVigencia;
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
 * [UC3156] Simular Cálculo da Conta Dados Reais
 * 
 * @author Anderson Italo
 * @date 21/09/2014
 */
public class ExibirSimularCalculoContaDadosReaisDadosAdicionaisAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o mapeamento de retorno
		ActionForward retorno = actionMapping.findForward("exibirSimularCalculoContaDadosReaisDadosAdicionais");

		SimularCalculoContaDadosReaisDadosAdicionaisActionForm form = (SimularCalculoContaDadosReaisDadosAdicionaisActionForm) actionForm;

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroContaSimularCalculoHelper filtroContaSimularCalculoHelper = (FiltroContaSimularCalculoHelper) sessao
						.getAttribute("filtroContaSimularCalculoHelper");

		Fachada fachada = Fachada.getInstancia();

		Integer quantidadeContas = fachada.pesquisarTotalRegistrosContasSimularCalculoDadosReais(filtroContaSimularCalculoHelper);

		if((quantidadeContas == null || quantidadeContas.intValue() == 0) && (Util.isVazioOuBranco(form.getQuantidadeContas()))){

			// Nenhuma registro encontrado
			ActionServletException ex = new ActionServletException("atencao.pesquisa.nenhumresultado", null, "Conta");
			ex.setUrlBotaoVoltar("/gsan/exibirSimularCalculoContaDadosReaisFiltrarAction.do");
			throw ex;
		}

		if(quantidadeContas != null){

			form.setQuantidadeContas(quantidadeContas.toString());
		}else{

			form.setQuantidadeContas("0");
		}

		// Faz a pesquisa das Tarifa de Consumo para Recálculo
		this.pesquisarConsumoTarifa(httpServletRequest);
		
		// Faz a pesquisa das Vigências da Tarifa
		this.pesquisarConsumoTarifaVigencia(httpServletRequest, form);

		return retorno;

	}

	/**
	 * Pesquisar Tarifa de Consumo
	 * 
	 * @author Anderson Italo
	 * @date 21/09/2014
	 */
	private void pesquisarConsumoTarifa(HttpServletRequest httpServletRequest){

		// Carregamento inicial do formulário.
		FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();

		filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);

		filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.INDICADOR_USO,
						ConstantesSistema.INDICADOR_USO_ATIVO));

		// Retorna Tarifa de Consumo
		Collection colecaoConsumoTarifa = this.getFachada().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

		// [FS0001] - Verificar existência de dados
		if(colecaoConsumoTarifa == null || colecaoConsumoTarifa.isEmpty()){

			// Nenhum registro na tabela consumo_tarifa foi encontrada
			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "Consumo Tarifa");
		}else{

			httpServletRequest.setAttribute("colecaoConsumoTarifa", colecaoConsumoTarifa);
		}
	}

	/**
	 * Pesquisar ConsumoTarifaVigencia
	 * 
	 * @author Anderson Italo
	 * @date 21/09/2014
	 */
	private void pesquisarConsumoTarifaVigencia(HttpServletRequest httpServletRequest,
					SimularCalculoContaDadosReaisDadosAdicionaisActionForm form){

		if(Util.verificarIdNaoVazio(form.getIdConsumoTarifa())){

			// Carregamento inicial do formulário.
			FiltroConsumoTarifaVigencia filtroConsumoTarifaVigencia = new FiltroConsumoTarifaVigencia();
			filtroConsumoTarifaVigencia.adicionarParametro(new ParametroSimples(FiltroConsumoTarifaVigencia.CONSUMO_TARIFA_ID, Integer
							.valueOf(form.getIdConsumoTarifa())));
			filtroConsumoTarifaVigencia.setCampoOrderByDesc(FiltroConsumoTarifaVigencia.DATA_VIGENCIA);

			// Retorna sub_categoria
			Collection colecaoConsumoTarifaVigencia = this.getFachada().pesquisar(filtroConsumoTarifaVigencia,
							ConsumoTarifaVigencia.class.getName());
			httpServletRequest.setAttribute("colecaoConsumoTarifaVigencia", colecaoConsumoTarifaVigencia);

		}else{

			httpServletRequest.removeAttribute("colecaoConsumoTarifaVigencia");
			form.setIdConsumoTarifaVigencia(null);
		}
	}
}
