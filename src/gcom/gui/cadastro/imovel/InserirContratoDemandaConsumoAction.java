
package gcom.gui.cadastro.imovel;

import gcom.arrecadacao.ContratoDemandaConsumo;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.consumotarifa.ConsumoTarifa;
import gcom.gui.ActionServletException;
import gcom.gui.GcomAction;
import gcom.util.ConstantesSistema;
import gcom.util.Util;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Inserir Contrato Demanda Consumo
 * 
 * @author Felipe Rosacruz
 * @date 12/01/2014
 */
public class InserirContratoDemandaConsumoAction
				extends GcomAction {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		// Seta o retorno
		ActionForward retorno = actionMapping.findForward("telaSucesso");
		InserirContratoDemandaConsumoActionForm form = (InserirContratoDemandaConsumoActionForm) actionForm;
		HttpSession sessao = httpServletRequest.getSession(false);
		Fachada fachada = Fachada.getInstancia();

		String consumoFixo = form.getConsumoFixo();
		String emailGestorContrato = form.getEmailgestorContrato();
		String enderecoImovel = form.getEnderecoImovel();
		String idImovel = form.getIdImovel();
		String tarifaConsumo = form.getIdTarifaConsumo();
		String inscricaoImovel = form.getInscricaoImovel();
		String ligacaoAguaSituacao = form.getLigacaoAguaSituacao();
		String ligacaoEsgotoSituacao = form.getLigacaoEsgotoSituacao();
		String mesAnoFaturamentoFinal = form.getMesAnoFaturamentoFinal();
		String mesAnoFaturamentoInicial = form.getMesAnoFaturamentoInicial();
		String numeroContrato = form.getNumeroContrato();
		String tarifaConsumoAtual = form.getTarifaConsumoAtual();
		
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		Integer referenciaFaturamentoCorrente = sistemaParametro.getAnoMesFaturamento();
		if(Util.formatarMesAnoComBarraParaAnoMes(mesAnoFaturamentoInicial) <= referenciaFaturamentoCorrente){
			throw new ActionServletException("atencao.mes_ano_inicial.maior_mes_ano_faturamento_atual");
		}

		ContratoDemandaConsumo contratoDemandaConsumo = new ContratoDemandaConsumo();
		if(!Util.isVazioOuBranco(idImovel)){
			Imovel imovel = new Imovel();
			imovel.setId(Integer.valueOf(idImovel));
			contratoDemandaConsumo.setImovel(imovel);
		}
		Integer numeroContratoInt = null;
		Integer consumoFixoInt = null;

		if(Util.isNaoNuloBrancoZero(numeroContrato)){
			numeroContratoInt = Integer.valueOf(numeroContrato);
		}
		if(Util.isNaoNuloBrancoZero(consumoFixo)){
			consumoFixoInt = Integer.valueOf(consumoFixo);
		}
		contratoDemandaConsumo.setNumeroContrato(numeroContratoInt);
		contratoDemandaConsumo.setAnoMesFaturamentoInicio(Integer.valueOf(Util.formatarMesAnoParaAnoMesSemBarra(mesAnoFaturamentoInicial)));
		contratoDemandaConsumo.setAnoMesFaturamentoFim(Integer.valueOf(Util.formatarMesAnoParaAnoMesSemBarra(mesAnoFaturamentoFinal)));
		if(!Util.isVazioOuBranco(tarifaConsumo) && !tarifaConsumo.equals("-1")){
			ConsumoTarifa consumoTarifa = new ConsumoTarifa();
			consumoTarifa.setId(Integer.valueOf(tarifaConsumo));
			contratoDemandaConsumo.setConsumoTarifa(consumoTarifa);
		}
		contratoDemandaConsumo.setNumeroConsumoFixo(consumoFixoInt);
		contratoDemandaConsumo.setIndicacorEncerramento(ConstantesSistema.NAO.intValue());
		contratoDemandaConsumo.setUltimaAlteracao(new Date());
		contratoDemandaConsumo.setEmail(emailGestorContrato);

		fachada.inserir(contratoDemandaConsumo);


		montarPaginaSucesso(httpServletRequest, "Contrato de demanda de Consumo inserido com sucesso.",
						"Inserir outro Contrato de demanda de Consumo", "exibirInserirContratoDemandaConsumoAction.do?menu=sim");

		return retorno;
	}
}