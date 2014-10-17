
package gcom.relatorio.faturamento;

import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.FiltroFaturamentoGrupo;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.faturamento.consumotarifa.FiltroConsumoTarifa;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Contrato Demanda Consumo
 * 
 * @author Vicente Zarga
 * @since 11/01/2014
 */
public class ExibirGerarRelatorioContratoDemandaConsumoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Obtendo uma instancia da sessao
		HttpSession sessao = httpServletRequest.getSession(false);
		
		ActionForward retorno = actionMapping.findForward("exibirGerarRelatorioContratoDemandaConsumo");

		GerarRelatorioContratoDemandaConsumoActionForm form = (GerarRelatorioContratoDemandaConsumoActionForm) (actionForm);

		// Carregando Coleções
		carregarGrupoFaturamento(httpServletRequest);
		carregarLocalidade(httpServletRequest);

		if(form.getTipoContrato() != null && form.getTipoContrato().equals(ConstantesSistema.SIM.toString())){

			carregarTarifaConsumo(httpServletRequest);
		}else{
			sessao.removeAttribute("colecaoTarifaConsumo");
		}

		return retorno;

	}

	public void carregarGrupoFaturamento(HttpServletRequest httpServletRequest){

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroFaturamentoGrupo filtroFaturamentogrupo = new FiltroFaturamentoGrupo();
		filtroFaturamentogrupo.adicionarParametro(new ParametroSimples(FiltroFaturamentoGrupo.INDICADOR_USO, ConstantesSistema.SIM));
		filtroFaturamentogrupo.setCampoOrderBy(FiltroFaturamentoGrupo.DESCRICAO);

		Collection colecaoGrupoFaturamento = this.getFachada().pesquisar(filtroFaturamentogrupo, FaturamentoGrupo.class.getName());

		if(colecaoGrupoFaturamento != null && !colecaoGrupoFaturamento.isEmpty()){

			sessao.setAttribute("colecaoGrupoFaturamento", colecaoGrupoFaturamento);

		}else{

			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "FATURAMENTO_GRUPO");

		}
	}

	public void carregarLocalidade(HttpServletRequest httpServletRequest){

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.adicionarParametro(new ParametroSimples(FiltroLocalidade.INDICADORUSO, ConstantesSistema.SIM));

		ArrayList<Localidade> colecaoLocalidade = (ArrayList<Localidade>) this.getFachada().pesquisar(filtroLocalidade,
						Localidade.class.getName());

		if(colecaoLocalidade != null && !colecaoLocalidade.isEmpty()){

			Collections.sort(colecaoLocalidade, new Comparator() {

				public int compare(Object o1, Object o2){

					Localidade l1 = (Localidade) o1;
					Localidade l2 = (Localidade) o2;
					return l1.getDescricao().compareToIgnoreCase(l2.getDescricao());
				}
			});

			sessao.setAttribute("colecaoLocalidade", colecaoLocalidade);

		}else{

			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "LOCALIDADE");

		}
	}

	public void carregarTarifaConsumo(HttpServletRequest httpServletRequest){

		HttpSession sessao = httpServletRequest.getSession(false);

		FiltroConsumoTarifa filtroConsumoTarifa = new FiltroConsumoTarifa();
		filtroConsumoTarifa.adicionarParametro(new ParametroSimples(FiltroConsumoTarifa.INDICADOR_USO, ConstantesSistema.SIM));
		filtroConsumoTarifa.setCampoOrderBy(FiltroConsumoTarifa.DESCRICAO);

		Collection colecaoTarifaConsumo = this.getFachada().pesquisar(filtroConsumoTarifa, ConsumoTarifa.class.getName());

		if(colecaoTarifaConsumo != null && !colecaoTarifaConsumo.isEmpty()){

			sessao.setAttribute("colecaoTarifaConsumo", colecaoTarifaConsumo);

		}else{

			throw new ActionServletException("atencao.pesquisa.nenhum_registro_tabela", null, "CONSUMO_TARIFA");

		}
	}
}
