
package gcom.gui.cobranca;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.cobranca.RelatorioImoveisPorAcaoCobranca;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.Util;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GerarRelatorioImovelPorAcaoCobrancaAction
				extends ExibidorProcessamentoTarefaRelatorio {

	/**
	 * @author Andre Nishimura
	 * @date 23/07/2010
	 * @param actionMapping
	 * @param actionForm
	 * @param httpServletRequest
	 * @param httpServletResponse
	 * @return
	 */
	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = actionMapping.findForward("");

		FiltroRelacaoImovelPorAcaoCobrancaActionForm form = (FiltroRelacaoImovelPorAcaoCobrancaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		if(form.getComando() == null
						|| (!form.getComando().equals("1") && !form.getComando().equals("2") && !form.getComando().equals("3"))){
			throw new ActionServletException("atencao.informe_campo", null, "Tipo de Comando");
		}

		File template = new File(httpServletRequest.getRealPath("") + "\\xls\\relatorioImovelPorAcaoCobrancaTemplate.xls");

		RelatorioImoveisPorAcaoCobranca relatorio = new RelatorioImoveisPorAcaoCobranca(((Usuario) (httpServletRequest.getSession(false))
						.getAttribute("usuarioLogado")), ConstantesRelatorios.RELATORIO_IMOVEL_POR_ACAO_COBRANCA);

		Date dataInicial = null;
		Date dataFinal = null;
		if(form.getIdCobrancaAcaoAtividadeComando() == null && form.getIdCobrancaAcaoAtividadeCronograma() == null){
			validarDatas(form.getPeriodoInicial(), form.getPeriodoFim(), dataInicial, dataFinal);
		}
		if(form.getPeriodoInicial() != null && !form.getPeriodoInicial().equals("") && form.getPeriodoFim() != null
						&& !form.getPeriodoFim().equals("")){
			dataInicial = Util.converteStringParaDate(form.getPeriodoInicial());
			dataFinal = Util.converteStringParaDateHora(form.getPeriodoFim() + " 23:59:59");
		}
		relatorio.addParametro("template", template);
		relatorio.addParametro("comando", form.getComando());
		relatorio.addParametro("idComando", form.getIdCobrancaAcaoAtividadeComando());
		relatorio.addParametro("idCronograma", form.getIdCobrancaAcaoAtividadeCronograma());
		relatorio.addParametro("acao", form.getAcao());
		relatorio.addParametro("dataInicial", dataInicial);
		relatorio.addParametro("dataFinal", dataFinal);
		relatorio.addParametro("grupo", form.getIdGrupo());
		relatorio.addParametro("bairro", form.getBairro());
		relatorio.addParametro("setorComercial", form.getSetorComercial());
		relatorio.addParametro("categoria", form.getCategoria());
		relatorio.addParametro("nomeEmpresa", sistemaParametro.getNomeEmpresa());
		relatorio.addParametro("localidade", form.getLocalidade());
		relatorio.addParametro("tipoFormatoRelatorio", TarefaRelatorio.TIPO_XLS);

		try{
			retorno = processarExibicaoRelatorio(relatorio, TarefaRelatorio.TIPO_XLS, httpServletRequest, httpServletResponse,
							actionMapping);
		}catch(TarefaException e){
			throw new ActionServletException(e.getMessage());
		}
		return retorno;
	}

	/**
	 * @author Isaac Silva
	 * @date 23/07/2010
	 * @param form
	 * @param periodoFim
	 * @param periodoInicio
	 */
	private void validarDatas(String periodoInicioString, String periodoFimString, Date periodoInicio, Date periodoFim){

		if(Util.isVazioOuBranco(periodoInicioString) && Util.isVazioOuBranco(periodoFimString)){
			throw new ActionServletException("atencao.informe_campo", null, "Período");
		}

		// Data Inicial
		if(Util.isVazioOuBranco(periodoInicioString)){
			throw new ActionServletException("atencao.required", null, "Período Inicial");
		}

		try{
			periodoInicio = Util.converteStringParaDate(periodoInicioString);
		}catch(Exception e){
			throw new ActionServletException("atencao.campo.invalido", null, "Período Inicial");
		}

		// Data Final
		if(Util.isVazioOuBranco(periodoFimString)){
			throw new ActionServletException("atencao.required", null, "Período Final");
		}

		try{
			periodoFim = Util.converteStringParaDate(periodoFimString);
		}catch(Exception e){
			throw new ActionServletException("atencao.campo.invalido", null, "Período Final");
		}

		// Se data inicio maior que data fim
		if(Util.compararData(periodoInicio, periodoFim) == 1){
			throw new ActionServletException("atencao.data_inicial_maior_data_final", periodoInicioString, periodoFimString);
		}
	}
}