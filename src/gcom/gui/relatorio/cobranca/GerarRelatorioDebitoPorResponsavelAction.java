
package gcom.gui.relatorio.cobranca;

import gcom.cobranca.bean.FiltroRelatorioDebitoPorResponsavelHelper;
import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.cobranca.RelatorioDebitoPorResponsavelAnalitico;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GerarRelatorioDebitoPorResponsavelAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;
		GerarRelatorioDebitoPorResponsavelActionForm form = (GerarRelatorioDebitoPorResponsavelActionForm) actionForm;
		FiltroRelatorioDebitoPorResponsavelHelper filtro = new FiltroRelatorioDebitoPorResponsavelHelper();
		boolean informouParametro = false;

		// Responsável
		if(!Util.isVazioOuBranco(form.getIdClienteInicial())){

			filtro.setIdClienteInicial(Util.obterInteger(form.getIdClienteInicial()));
			informouParametro = true;

			if(!Util.isVazioOuBranco(form.getIdClienteFinal())){

				filtro.setIdClienteFinal(Util.obterInteger(form.getIdClienteFinal()));
			}else{

				filtro.setIdClienteFinal(Util.obterInteger(form.getIdClienteInicial()));
			}
		}

		// Tipo do Responsável
		if(Util.verificarIdNaoVazio(form.getIdTipoClienteInicial()) && !form.getIdTipoClienteInicial().equals("t")){

			filtro.setIdTipoClienteInicial(Util.obterInteger(form.getIdTipoClienteInicial()));
			informouParametro = true;

			if(!Util.isVazioOuBranco(form.getIdTipoClienteFinal()) && !form.getIdTipoClienteFinal().equals("t")){

				filtro.setIdTipoClienteFinal(Util.obterInteger(form.getIdTipoClienteFinal()));
			}else{

				filtro.setIdTipoClienteFinal(Util.obterInteger(form.getIdTipoClienteInicial()));
			}
		}

		// Ano/Mês Referência
		if(!Util.isVazioOuBranco(form.getReferenciaDebitoInicial())){

			filtro.setReferenciaDebitoInicial(Util.formatarMesAnoComBarraParaAnoMes(form.getReferenciaDebitoInicial()));
			informouParametro = true;

			if(!Util.isVazioOuBranco(form.getReferenciaDebitoFinal())){

				filtro.setReferenciaDebitoFinal(Util.formatarMesAnoComBarraParaAnoMes(form.getReferenciaDebitoFinal()));
			}else{

				filtro.setReferenciaDebitoFinal(Util.formatarMesAnoComBarraParaAnoMes(form.getReferenciaDebitoInicial()));
			}
		}

		// Contas em revisão
		if(!Util.isVazioOuBranco(form.getIndicadorContasEmRevisao())){

			filtro.setIndicadorContasEmRevisao(Util.obterShort(form.getIndicadorContasEmRevisao()));

			informouParametro = true;

			// Motivo da Revisão
			if(Util.verificarIdNaoVazio(form.getIdMotivoRevisao())){


				filtro.setIdMotivoRevisao(Util.obterInteger(form.getIdMotivoRevisao()));
			}
		}

		// Responsabilidade
		if(!Util.isVazioOuBranco(form.getIndicadorResponsabilidade())){

			filtro.setIndicadorResponsabilidade(form.getIndicadorResponsabilidade());
		}

		// Valor Corrigido
		if(!Util.isVazioOuBranco(form.getIndicadorValorCorrigido())){

			filtro.setIndicadorValorCorrigido(Util.obterShort(form.getIndicadorValorCorrigido()));
		}

		// Tipo Relatorio
		if(!Util.isVazioOuBranco(form.getIndicadorTipoRelatorio())){

			filtro.setIndicadorTipoRelatorio(form.getIndicadorTipoRelatorio());
		}

		// Esfera Poder
		if(!Util.isVazioOuBranco(form.getEsferaPoder())){

			filtro.setIdEsferaPoder(Util.obterInteger(form.getEsferaPoder()));

		}

		if(!informouParametro){

			throw new ActionServletException("atencao.filtro.nenhum_parametro_informado");
		}

		RelatorioDebitoPorResponsavelAnalitico relatorio = new RelatorioDebitoPorResponsavelAnalitico(getUsuarioLogado(httpServletRequest));
		relatorio.addParametro("filtroRelatorioDebitoPorResponsavelHelper", filtro);

		relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_PDF);
		retorno = processarExibicaoRelatorio(relatorio, String.valueOf(TarefaRelatorio.TIPO_PDF), httpServletRequest, httpServletResponse,
						actionMapping);

		return retorno;
	}
}
