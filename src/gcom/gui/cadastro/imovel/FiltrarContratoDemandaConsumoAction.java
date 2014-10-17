package gcom.gui.cadastro.imovel;

import gcom.arrecadacao.ContratoDemandaConsumo;
import gcom.fachada.Fachada;
import gcom.faturamento.FiltroContratoDemandaConsumo;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.Util;
import gcom.util.filtro.MaiorQue;
import gcom.util.filtro.MenorQue;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Manter Contrato Demanda Consumo
 * 
 * @author Felipe Rosacruz
 * @date 12/01/2014
 */
public class FiltrarContratoDemandaConsumoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("exibirManterContratoDemandaConsumo");
		ManterContratoDemandaConsumoActionForm form = (ManterContratoDemandaConsumoActionForm) actionForm;
		Fachada fachada = Fachada.getInstancia();
		HttpSession sessao = httpServletRequest.getSession(false);

		Integer idImovel = null;
		String numeroContrato = null;
		Integer anoMesFaturamento = null;
		Integer idTarifaCOnsumo = null;
		String consumoFixo = null;

		FiltroContratoDemandaConsumo filtroContratoDemandaConsumo = new FiltroContratoDemandaConsumo();
		filtroContratoDemandaConsumo.adicionarCaminhoParaCarregamentoEntidade("imovel");

		if(Util.isNaoNuloBrancoZero(form.getIdImovel())){
			idImovel = Integer.valueOf(form.getIdImovel());
			filtroContratoDemandaConsumo.adicionarParametro(new ParametroSimples(FiltroContratoDemandaConsumo.IMOVEL_ID, idImovel));
		}
		if(Util.isNaoNuloBrancoZero(form.getNumeroContrato())){
			numeroContrato = form.getNumeroContrato();
			filtroContratoDemandaConsumo.adicionarParametro(new ParametroSimples(FiltroContratoDemandaConsumo.MUMEROCONTRATO,
							numeroContrato));
		}
		if(Util.isNaoNuloBrancoZero(form.getMesAnoFaturamento())){
			anoMesFaturamento = Util.formatarMesAnoComBarraParaAnoMes(form.getMesAnoFaturamento());
			filtroContratoDemandaConsumo.adicionarParametro(new MaiorQue(FiltroContratoDemandaConsumo.ANOMESFATURAMENTOFIM,
							anoMesFaturamento));
			filtroContratoDemandaConsumo.adicionarParametro(new MenorQue("anoMesFaturamentoInicio",
							anoMesFaturamento));
		}
		if(Util.isNaoNuloBrancoZero(form.getIdTarifaConsumo()) && !form.getIdTarifaConsumo().equals("-1")){
			idTarifaCOnsumo = Integer.valueOf(form.getIdTarifaConsumo());
			filtroContratoDemandaConsumo.adicionarParametro(new ParametroSimples("consumoTarifa.id", idTarifaCOnsumo));
		}
		if(Util.isNaoNuloBrancoZero(form.getConsumoFixo())){
			consumoFixo = form.getConsumoFixo();
			filtroContratoDemandaConsumo.adicionarParametro(new ParametroSimples("numeroConsumoFixo", consumoFixo));
		}
		Collection colecaoContratoDemandaConsumo = (Collection) fachada.pesquisar(filtroContratoDemandaConsumo,
						ContratoDemandaConsumo.class.getName());

		if(Util.isVazioOrNulo(colecaoContratoDemandaConsumo)){
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		sessao.setAttribute("filtroContratoDemandaConsumo", filtroContratoDemandaConsumo);

		return retorno;
	}

}
