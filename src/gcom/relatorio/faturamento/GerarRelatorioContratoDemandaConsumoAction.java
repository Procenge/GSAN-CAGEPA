
package gcom.relatorio.faturamento;

import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.SistemaException;
import gcom.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * Contrato Demanda Consumo
 * 
 * @author Vicente Zarga
 * @since 11/01/2014
 */
public class GerarRelatorioContratoDemandaConsumoAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;

		GerarRelatorioContratoDemandaConsumoActionForm form = (GerarRelatorioContratoDemandaConsumoActionForm) actionForm;

		String faturamentoGrupo = null;
		String[] localidades = null;
		String tipoContrato = null;
		String tarifaConsumo = null;
		String mesAnoFaturamentoInicial = form.getMesAnoFaturamentoInicial();
		String mesAnoFaturamentoFinal = form.getMesAnoFaturamentoFinal();
		String encerrado = form.getEncerrado();

		Usuario usuarioLogado = (Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado");

		String tipoFormatoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		if(form.getFaturamentoGrupo() != null && !form.getFaturamentoGrupo().equals(ConstantesSistema.INVALIDO_ID.toString())){

			faturamentoGrupo = form.getFaturamentoGrupo();
		}

		if(form.getLocalidade() != null){

			localidades = form.getLocalidade();
		}
		if(form.getTipoContrato() != null && !form.getTipoContrato().equals(ConstantesSistema.INVALIDO_ID.toString())){
			tipoContrato = form.getTipoContrato();
		}
		if(form.getTarifaConsumo() != null && !form.getTarifaConsumo().equals("")){
			tarifaConsumo = form.getTarifaConsumo();
		}
		if(form.getEncerrado() != null && !form.getEncerrado().equals("")){
			encerrado = form.getEncerrado();
		}else{

			throw new ActionServletException("atencao.campo_texto.obrigatorio", "Encerrado");

		}

		if((form.getMesAnoFaturamentoInicial() != null && form.getMesAnoFaturamentoFinal() != null)
						&& !form.getMesAnoFaturamentoInicial().equals("") && !form.getMesAnoFaturamentoFinal().equals("")){

			String mesAnoFaturamentoInicialStr = form.getMesAnoFaturamentoInicial();
			String anoMesFaturamentoInicialStr = Util.formatarMesAnoParaAnoMesSemBarra(mesAnoFaturamentoInicialStr);

			String mesAnoFaturamentoFinalStr = form.getMesAnoFaturamentoFinal();
			String anoMesFaturamentoFinalStr = Util.formatarMesAnoParaAnoMesSemBarra(mesAnoFaturamentoFinalStr);

			if(Util.compararAnoMesReferencia(Util.obterInteger(anoMesFaturamentoInicialStr), Util.obterInteger(anoMesFaturamentoFinalStr),
							">")){
				throw new ActionServletException("atencao.mes_ano_faturamento_invalido");
			}
		}

		if(tipoFormatoRelatorio == null){
			tipoFormatoRelatorio = Integer.toString(TarefaRelatorio.TIPO_PDF);
		}

		RelatorioContratoDemandaConsumo relatorioContratoDemandaConsumo = new RelatorioContratoDemandaConsumo(usuarioLogado);

		relatorioContratoDemandaConsumo.addParametro("faturamentoGrupo", faturamentoGrupo);
		relatorioContratoDemandaConsumo.addParametro("localidades", localidades);
		relatorioContratoDemandaConsumo.addParametro("tipoContrato", tipoContrato);
		relatorioContratoDemandaConsumo.addParametro("tarifaConsumo", tarifaConsumo);
		relatorioContratoDemandaConsumo.addParametro("mesAnoFaturamentoInicial", mesAnoFaturamentoInicial);
		relatorioContratoDemandaConsumo.addParametro("mesAnoFaturamentoFinal", mesAnoFaturamentoFinal);
		relatorioContratoDemandaConsumo.addParametro("encerrado", encerrado);
		relatorioContratoDemandaConsumo.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoFormatoRelatorio));

		if(relatorioContratoDemandaConsumo.calcularTotalRegistrosRelatorio() < 1){
			throw new ActionServletException("atencao.relatorio.vazio");
		}

		try{
			retorno = processarExibicaoRelatorio(relatorioContratoDemandaConsumo, tipoFormatoRelatorio, httpServletRequest,
							httpServletResponse, actionMapping);

		}catch(SistemaException ex){
			reportarErros(httpServletRequest, "erro.sistema");

			retorno = actionMapping.findForward("telaErro");

		}catch(RelatorioVazioException ex1){
			reportarErros(httpServletRequest, "atencao.relatorio.vazio");

			retorno = actionMapping.findForward("telaAtencaoPopup");
		}

		if(relatorioContratoDemandaConsumo.calcularTotalRegistrosRelatorio() < 1){
			throw new ActionServletException("atencao.relatorio.vazio");
		}

		return retorno;
	}
}
