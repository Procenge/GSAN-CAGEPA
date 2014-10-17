
package gcom.gui.cobranca;

import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.cobranca.RelatorioLiquidosRecebiveis;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.Util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import br.com.procenge.util.Constantes;

public class GerarRelatorioLiquidosRecebiveisAction
				extends ExibidorProcessamentoTarefaRelatorio {

	/**
	 * 
	 */
	public final ActionForward execute(final ActionMapping actionMapping, final ActionForm actionForm,
					final HttpServletRequest httpServletRequest, final HttpServletResponse httpServletResponse){

		final FiltroRelatorioLiquidosRecebiveisActionForm form = (FiltroRelatorioLiquidosRecebiveisActionForm) actionForm;

		this.validarForm(form, httpServletRequest);
		final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Constantes.LOCALE_PT_BR);

		try{
			int qtde = Fachada.getInstancia().consultarQuantidadeRegistrosDeLiquidosRecebiveis(dateFormat.parse(form.getPeriodoInicial()),
							dateFormat.parse(form.getPeriodoFim()));
			if(qtde == 0){
				throw new ActionServletException("atencao.sem_dados_para_selecao");
			}

			final RelatorioLiquidosRecebiveis relatorio = this.criarRelatorioLiquidosRecebiveis(form, httpServletRequest);

			return this.processarExibicaoRelatorio(relatorio, TarefaRelatorio.TIPO_PDF, httpServletRequest, httpServletResponse,
							actionMapping);
		}catch(TarefaException e){
			throw new ActionServletException(e.getMessage(), e);
		}catch(ParseException e){
			throw new ActionServletException("atencao.campo.invalido", e);
		}
	}

	/**
	 * 
	 */
	private void validarForm(final FiltroRelatorioLiquidosRecebiveisActionForm form, final HttpServletRequest httpServletRequest){

		if(Util.isVazioOuBranco(form.getComando())){
			throw new ActionServletException("atencao.informe_campo", null, "Opção de geração");
		}

		if(Util.isVazioOuBranco(form.getPeriodoInicial()) && Util.isVazioOuBranco(form.getPeriodoFim())){
			throw new ActionServletException("atencao.informe_campo", null, "Período");
		}

		if(Util.isVazioOuBranco(form.getPeriodoInicial()) || Util.isVazioOuBranco(form.getPeriodoFim())){
			this.validarDatas(form);
		}
	}

	/**
	 * @param form
	 */
	private void validarDatas(final FiltroRelatorioLiquidosRecebiveisActionForm form){

		Date periodoInicio = null;
		Date periodoFim = null;

		if(Util.isVazioOuBranco(form.getPeriodoInicial()) && Util.isVazioOuBranco(form.getPeriodoFim())){
			throw new ActionServletException("atencao.informe_campo", null, "Período Data do Pagamento");
		}

		// Data Inicial
		if(Util.isVazioOuBranco(form.getPeriodoInicial())){
			throw new ActionServletException("atencao.required", null, "Data do Pagamento Inicial");
		}

		try{
			periodoInicio = Util.converteStringParaDate(form.getPeriodoInicial());
		}catch(Exception e){
			throw new ActionServletException("atencao.campo.invalido", e, "Data do Pagamento Inicial");
		}

		// Data Final
		if(Util.isVazioOuBranco(form.getPeriodoFim())){
			throw new ActionServletException("atencao.required", null, "Data do Pagamento Final");
		}

		try{
			periodoFim = Util.converteStringParaDate(form.getPeriodoFim());
		}catch(Exception e){
			throw new ActionServletException("atencao.campo.invalido", e, "Data do Pagamento Final");
		}

		// Se data inicio maior que data fim
		if(Util.compararData(periodoInicio, periodoFim) == 1){
			throw new ActionServletException("atencao.data_inicial_maior_data_final", form.getPeriodoInicial(), form.getPeriodoFim());
		}

		// Se data inicio maior que data fim
		if(Util.compararData(periodoInicio, periodoFim) == 1){
			throw new ActionServletException("atencao.data_inicial_maior_data_final", form.getPeriodoInicial(), form.getPeriodoFim());
		}

		Date dataCorrente = new Date();
		if(Util.compararData(periodoInicio, dataCorrente) == 1){
			throw new ActionServletException("atencao.data_param_maior_data_corrente", "Data do Pagamento", Util.formatarData(dataCorrente));
		}
	}

	/**
	 */
	private RelatorioLiquidosRecebiveis criarRelatorioLiquidosRecebiveis(final FiltroRelatorioLiquidosRecebiveisActionForm form,
					final HttpServletRequest httpServletRequest){

		String nomeRelatorio = null;
		String comando = form.getComando();
		if("1".equals(comando)){
			nomeRelatorio = ConstantesRelatorios.RELATORIO_LIQUIDOS_RECEBIVEIS_ANALITICO;
		}else if("2".equals(comando)){
			nomeRelatorio = ConstantesRelatorios.RELATORIO_LIQUIDOS_RECEBIVEIS_SINTETICO;
		}

		final RelatorioLiquidosRecebiveis relatorio = new RelatorioLiquidosRecebiveis(this.getUsuarioLogado(httpServletRequest),
						nomeRelatorio);

		relatorio.addParametro("comando", comando);
		relatorio.addParametro("tipoFormatoRelatorio", Integer.valueOf(TarefaRelatorio.TIPO_PDF));

		if((form.getPeriodoInicial() != null && !"".equals(form.getPeriodoInicial()))
						&& (form.getPeriodoFim() != null && !"".equals(form.getPeriodoFim()))){
			final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Constantes.LOCALE_PT_BR);

			try{
				relatorio.addParametro("dataInicio", dateFormat.parse(form.getPeriodoInicial()));
				relatorio.addParametro("dataFim", dateFormat.parse(form.getPeriodoFim()));
			}catch(ParseException e){
				throw new ActionServletException("atencao.campo.invalido", e);
			}
		}

		return relatorio;
	}
}