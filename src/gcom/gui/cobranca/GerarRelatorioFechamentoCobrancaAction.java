
package gcom.gui.cobranca;

import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cobranca.CobrancaAcao;
import gcom.cobranca.CobrancaAcaoAtividadeComando;
import gcom.cobranca.CobrancaAcaoAtividadeCronograma;
import gcom.cobranca.FiltroCobrancaAcao;
import gcom.cobranca.FiltroCobrancaAcaoAtividadeComando;
import gcom.cobranca.FiltroCobrancaAcaoAtividadeCronograma;
import gcom.cobranca.bean.RelatorioFechamentoCobrancaHelper;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.cobranca.RelatorioFechamentoCobrancaAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.SistemaException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.io.File;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class GerarRelatorioFechamentoCobrancaAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) throws Exception{

		ActionForward retorno = null;
		FiltrarRelatorioFechamentoCobrancaActionForm form = (FiltrarRelatorioFechamentoCobrancaActionForm) actionForm;

		Fachada fachada = Fachada.getInstancia();

		String stringComandoTipo = form.getComando();
		String stringComando = form.getIdCobrancaAcaoAtividadeComando();
		String stringCronograma = form.getIdCobrancaAcaoAtividadeCronograma();
		String stringEmpresa = form.getEmpresa();
		String stringAcao = form.getAcao();
		String stringPeriodoInicio = form.getPeriodoInicio();
		String stringPeriodoFim = form.getPeriodoFim();

		if(stringComandoTipo == null || stringComandoTipo.equals("")){
			throw new ActionServletException("atencao.campo.invalido", null, "Tipo do Comando");
		}

		if((stringPeriodoInicio != null && !stringPeriodoInicio.equals("")) && ((stringPeriodoFim == null || stringPeriodoFim.equals("")))){
			throw new ActionServletException("atencao.informe_periodo_fim_relatorio_remuneracao");
		}

		if((stringPeriodoFim != null && !stringPeriodoFim.equals("")) && (stringPeriodoInicio == null || stringPeriodoInicio.equals(""))){
			throw new ActionServletException("atencao.informe_periodo_inicio_relatorio_remuneracao");
		}

		File template = new File(httpServletRequest.getRealPath("") + "\\xls\\relatorioFechamentoCobranca.xls");

		boolean informouPeriodo = false;
		Date dataInicial = null;
		Date dataFinal = null;

		if((stringPeriodoFim != null && !stringPeriodoFim.equals("")) && (stringPeriodoInicio != null && !stringPeriodoInicio.equals(""))){

			informouPeriodo = true;

			// Data Inicial
			if(Util.isVazioOuBranco(stringPeriodoInicio)){
				throw new ActionServletException("atencao.required", null, "Período Inicial");
			}

			try{
				dataInicial = Util.converteStringParaDate(stringPeriodoInicio);
			}catch(Exception e){
				throw new ActionServletException("atencao.campo.invalido", e, "Período Inicial");
			}

			// Data Final
			if(Util.isVazioOuBranco(stringPeriodoFim)){
				throw new ActionServletException("atencao.required", null, "Período Final");
			}

			try{
				dataFinal = Util.converteStringParaDate(stringPeriodoFim);
			}catch(Exception e){
				throw new ActionServletException("atencao.campo.invalido", e, "Período Final");
			}

		}

		/* recuperaObjetos */
		CobrancaAcaoAtividadeCronograma cronograma = null;
		CobrancaAcaoAtividadeComando comando = null;
		Empresa empresa = null;
		CobrancaAcao acao = null;

		if(stringComandoTipo != null && !stringComandoTipo.equals("")){

			if(stringComandoTipo.equals("1")){

				// Cronograma
				if(stringCronograma != null && !stringCronograma.equals("") && !stringCronograma.equals("-1")){
					FiltroCobrancaAcaoAtividadeCronograma filtro1 = new FiltroCobrancaAcaoAtividadeCronograma();
					filtro1.adicionarParametro(new ParametroSimples(FiltroCobrancaAcaoAtividadeCronograma.ID, stringCronograma));
					Collection<CobrancaAcaoAtividadeCronograma> colecaoCronograma = fachada.pesquisar(filtro1,
									CobrancaAcaoAtividadeCronograma.class.getName());
					cronograma = (colecaoCronograma != null && !colecaoCronograma.isEmpty() ? (CobrancaAcaoAtividadeCronograma) colecaoCronograma
									.iterator().next()
									: null);

					if(cronograma == null){
						throw new ActionServletException("atencao.campo.invalido", null, "Comando Cronograma");
					}
				}else{

					// Ação, Empresa ou Periodo - Obrigatorio informar pelo menos 1
					this.verificarCamposObrigatorios(stringEmpresa, stringAcao, informouPeriodo);
				}

			}else if(stringComandoTipo.equals("2")){

				// Comando
				if(stringComando != null && !stringComando.equals("") && !stringComando.equals("-1")){
					FiltroCobrancaAcaoAtividadeComando filtro2 = new FiltroCobrancaAcaoAtividadeComando();
					filtro2.adicionarParametro(new ParametroSimples(FiltroCobrancaAcaoAtividadeComando.ID, stringComando));
					Collection<CobrancaAcaoAtividadeComando> colecaoComando = fachada.pesquisar(filtro2, CobrancaAcaoAtividadeComando.class
									.getName());
					comando = (colecaoComando != null && !colecaoComando.isEmpty() ? colecaoComando.iterator().next() : null);

					if(comando == null){
						throw new ActionServletException("atencao.campo.invalido", null, "Comando Eventual");
					}
				}else{

					// Ação, Empresa ou Periodo - Obrigatorio informar pelo menos 1
					this.verificarCamposObrigatorios(stringEmpresa, stringAcao, informouPeriodo);
				}
			}else{
				// Ação, Empresa ou Periodo - Obrigatorio informar pelo menos 1
				this.verificarCamposObrigatorios(stringEmpresa, stringAcao, informouPeriodo);
			}
		}

		if(stringEmpresa != null && !stringEmpresa.equals("") && !stringEmpresa.equals("-1")){
			FiltroEmpresa filtro3 = new FiltroEmpresa();
			filtro3.adicionarParametro(new ParametroSimples(FiltroEmpresa.ID, stringEmpresa));
			Collection<Empresa> colecaoEmpresa = fachada.pesquisar(filtro3, Empresa.class.getName());
			empresa = (colecaoEmpresa != null && !colecaoEmpresa.isEmpty() ? (Empresa) colecaoEmpresa.iterator().next() : null);
		}

		if(stringAcao != null && !stringAcao.equals("") && !stringAcao.equals("-1")){
			FiltroCobrancaAcao filtro4 = new FiltroCobrancaAcao();
			filtro4.adicionarParametro(new ParametroSimples(FiltroCobrancaAcao.ID, stringAcao));
			Collection<CobrancaAcao> colecaoAcao = fachada.pesquisar(filtro4, CobrancaAcao.class.getName());
			acao = (colecaoAcao != null && !colecaoAcao.isEmpty() ? colecaoAcao.iterator().next() : null);
		}

		Collection<RelatorioFechamentoCobrancaHelper> colecaoRetorno = fachada.filtrarRelatorioFechamentoCobranca(dataInicial, dataFinal,
						acao, empresa, comando, cronograma, stringComandoTipo);

		if(colecaoRetorno != null && !colecaoRetorno.isEmpty()){
			RelatorioFechamentoCobrancaAction relatorio = new RelatorioFechamentoCobrancaAction((Usuario) (httpServletRequest
							.getSession(false)).getAttribute("usuarioLogado"));
			relatorio.addParametro("comandoTipo", stringComandoTipo);
			relatorio.addParametro("comando", comando);
			relatorio.addParametro("cronograma", cronograma);
			relatorio.addParametro("empresa", empresa);
			relatorio.addParametro("acao", acao);
			relatorio.addParametro("periodoInicio", dataInicial);
			relatorio.addParametro("periodoFim", dataFinal);
			relatorio.addParametro("colecaoRetorno", colecaoRetorno);
			relatorio.addParametro("tipoFormatoRelatorio", Integer.valueOf(TarefaRelatorio.TIPO_XLS));
			relatorio.addParametro("template", template);
			try{
				retorno = processarExibicaoRelatorio(relatorio, TarefaRelatorio.TIPO_XLS, httpServletRequest, httpServletResponse,
								actionMapping);
			}catch(SistemaException ex){
				reportarErros(httpServletRequest, "erro.sistema");
				retorno = actionMapping.findForward("telaErroPopup");
			}catch(RelatorioVazioException ex1){
				reportarErros(httpServletRequest, "atencao.relatorio.vazio");
				retorno = actionMapping.findForward("telaAtencaoPopup");
			}catch(TarefaException e){
				throw new ActionServletException("atencao.planilha_template_nao_encontrado");
			}
		}else{
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}

		return retorno;
	}

	public void verificarCamposObrigatorios(String stringEmpresa, String stringAcao, boolean informouPeriodo){

		if((stringEmpresa == null || stringEmpresa.equals("") || stringEmpresa.equals("-1"))
						&& (stringAcao == null || stringAcao.equals("") || stringAcao.equals("-1")) && !informouPeriodo){
			throw new ActionServletException("atencao.informe_pelo_menos_um_acao_empresa_ou_periodo");
		}

	}
}