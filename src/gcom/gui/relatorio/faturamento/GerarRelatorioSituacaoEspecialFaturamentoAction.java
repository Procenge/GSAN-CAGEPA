
package gcom.gui.relatorio.faturamento;

import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.faturamento.RelatorioSituacaoEspecialFaturamento;
import gcom.relatorio.faturamento.RelatorioSituacaoEspecialFaturamentoHelper;
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
 * [XYZ] Gerar Relatório Situação Especial de Faturamento
 * 
 * @author Hebert Falcão
 * @date 16/03/2014
 */
public class GerarRelatorioSituacaoEspecialFaturamentoAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;

		GerarRelatorioSituacaoEspecialFaturamentoActionForm relatorioForm = (GerarRelatorioSituacaoEspecialFaturamentoActionForm) actionForm;

		RelatorioSituacaoEspecialFaturamentoHelper situacaoEspecialFaturamentoHelper = new RelatorioSituacaoEspecialFaturamentoHelper();

		// Referência do Faturamento
		String mesAnoFaturamentoStr = relatorioForm.getMesAnoFaturamento();
		Integer anoMesFaturamento = Util.formatarMesAnoComBarraParaAnoMes(mesAnoFaturamentoStr);

		situacaoEspecialFaturamentoHelper.setAnoMesFaturamento(anoMesFaturamento);

		// Localidade
		String idLocalidadeStr = relatorioForm.getIdLocalidade();

		if(!Util.isVazioOuBranco(idLocalidadeStr)){
			Integer idLocalidade = Integer.valueOf(idLocalidadeStr);
			situacaoEspecialFaturamentoHelper.setIdLocalidade(idLocalidade);
		}

		// Setor Comercial
		String codigoSetorComercialStr = relatorioForm.getCodigoSetorComercial();

		if(!Util.isVazioOuBranco(codigoSetorComercialStr)){
			Integer codigoSetorComercial = Integer.valueOf(codigoSetorComercialStr);
			situacaoEspecialFaturamentoHelper.setCodigoSetorComercial(codigoSetorComercial);
		}

		// Quadra
		String numeroQuadraStr = relatorioForm.getNumeroQuadra();

		if(!Util.isVazioOuBranco(numeroQuadraStr)){
			Integer numeroQuadra = Integer.valueOf(numeroQuadraStr);
			situacaoEspecialFaturamentoHelper.setNumeroQuadra(numeroQuadra);
		}

		// Rota
		String codigoRotaStr = relatorioForm.getCodigoRota();

		if(!Util.isVazioOuBranco(codigoRotaStr)){
			Short codigoRota = Short.valueOf(codigoRotaStr);
			situacaoEspecialFaturamentoHelper.setCodigoRota(codigoRota);
		}

		// Tipo da Situação Especial de Faturamento
		String idFaturamentoSituacaoTipoStr = relatorioForm.getIdFaturamentoSituacaoTipo();

		if(!Util.isVazioOuBranco(idFaturamentoSituacaoTipoStr)
						&& !idFaturamentoSituacaoTipoStr.equals(ConstantesSistema.NUMERO_NAO_INFORMADO_STRING)){
			Integer idFaturamentoSituacaoTipo = Integer.valueOf(idFaturamentoSituacaoTipoStr);
			situacaoEspecialFaturamentoHelper.setIdFaturamentoSituacaoTipo(idFaturamentoSituacaoTipo);
		}

		// Motivo da Situação Especial de Faturamento
		String idFaturamentoSituacaoMotivoStr = relatorioForm.getIdFaturamentoSituacaoMotivo();

		if(!Util.isVazioOuBranco(idFaturamentoSituacaoMotivoStr)
						&& !idFaturamentoSituacaoMotivoStr.equals(ConstantesSistema.NUMERO_NAO_INFORMADO_STRING)){
			Integer idFaturamentoSituacaoMotivo = Integer.valueOf(idFaturamentoSituacaoMotivoStr);
			situacaoEspecialFaturamentoHelper.setIdFaturamentoSituacaoMotivo(idFaturamentoSituacaoMotivo);
		}

		Usuario usuarioLogado = (Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado");

		String tipoFormatoRelatorio = httpServletRequest.getParameter("tipoRelatorio");

		if(tipoFormatoRelatorio == null){
			tipoFormatoRelatorio = Integer.toString(TarefaRelatorio.TIPO_PDF);
		}

		RelatorioSituacaoEspecialFaturamento relatorioSituacaoEspecialFaturamento = new RelatorioSituacaoEspecialFaturamento(usuarioLogado);
		relatorioSituacaoEspecialFaturamento.addParametro("situacaoEspecialFaturamentoHelper", situacaoEspecialFaturamentoHelper);
		relatorioSituacaoEspecialFaturamento.addParametro("tipoFormatoRelatorio", Integer.parseInt(tipoFormatoRelatorio));

		try{
			retorno = this.processarExibicaoRelatorio(relatorioSituacaoEspecialFaturamento, tipoFormatoRelatorio, httpServletRequest,
							httpServletResponse, actionMapping);
		}catch(SistemaException ex){
			reportarErros(httpServletRequest, "erro.sistema");

			retorno = actionMapping.findForward("telaErroPopup");
		}catch(RelatorioVazioException ex1){
			throw new ActionServletException("atencao.relatorio.vazio");
		}

		return retorno;
	}

}
