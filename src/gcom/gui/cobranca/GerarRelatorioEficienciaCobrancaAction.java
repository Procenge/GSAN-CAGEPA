
package gcom.gui.cobranca;

import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cobranca.CobrancaAcao;
import gcom.cobranca.CobrancaAcaoAtividadeComando;
import gcom.cobranca.CobrancaAcaoAtividadeCronograma;
import gcom.cobranca.FiltroCobrancaAcao;
import gcom.cobranca.FiltroCobrancaAcaoAtividadeComando;
import gcom.cobranca.FiltroCobrancaAcaoAtividadeCronograma;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.cobranca.RelatorioEficienciaCobrancaAction;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.SistemaException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.validator.GenericValidator;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author isilva
 */
public class GerarRelatorioEficienciaCobrancaAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward execute(ActionMapping actionMapping, ActionForm actionForm, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse){

		ActionForward retorno = null;
		FiltroEficienciaCobrancaRelatorioActionForm form = (FiltroEficienciaCobrancaRelatorioActionForm) actionForm;

		this.validaroFormulario(form);

		Fachada fachada = Fachada.getInstancia();

		String stringComandoTipo = form.getComando();
		String stringComando = form.getIdCobrancaAcaoAtividadeComando();
		String stringCronograma = form.getIdCobrancaAcaoAtividadeCronograma();
		String stringEmpresa = null;
		if(!Util.isVazioOuBranco(form.getEmpresa())){
			stringEmpresa = form.getEmpresa();
		}else{
			throw new ActionServletException("atencao.informe_campo", "a Empresa.");
		}
		String stringAcao = form.getAcao();
		Date dataInicial = null;
		Date dataFinal = null;

		if(!GenericValidator.isBlankOrNull(form.getPeriodoInicio())){
			dataInicial = Util.converteStringParaDate(form.getPeriodoInicio());
			dataFinal = Util.converteStringParaDate(form.getPeriodoFim());
			dataFinal = Util.formatarDataFinal(dataFinal);
		}

		String[] setores = form.getIdSetor();
		String[] grupos = form.getIdGrupo();

		/* recuperaObjetos */
		CobrancaAcaoAtividadeCronograma cronograma = null;
		CobrancaAcaoAtividadeComando comando = null;
		Empresa empresa = null;
		CobrancaAcao acao = null;

		if(!Util.isVazioOuBranco(stringComandoTipo)){
			if(stringComandoTipo.equalsIgnoreCase("1")){
				// Cronograma
				if(Util.isVazioOuBranco(stringCronograma)){
					throw new ActionServletException("atencao.required", null, "Tipo de Comando  Cronograma");
				}
				FiltroCobrancaAcaoAtividadeCronograma filtro1 = new FiltroCobrancaAcaoAtividadeCronograma();
				filtro1.adicionarParametro(new ParametroSimples(FiltroCobrancaAcaoAtividadeCronograma.ID, stringCronograma));
				Collection colecaoCronograma = fachada.pesquisar(filtro1, CobrancaAcaoAtividadeCronograma.class.getName());
				if(colecaoCronograma == null || colecaoCronograma.isEmpty()){
					cronograma = null;
				}else{
					cronograma = (CobrancaAcaoAtividadeCronograma) colecaoCronograma.iterator().next();
				}

				// Comando
			}else if(stringComandoTipo.equalsIgnoreCase("2")){

				if(Util.isVazioOuBranco(stringComando)){
					throw new ActionServletException("atencao.required", null, "Tipo de Comando Eventual");
				}
				FiltroCobrancaAcaoAtividadeComando filtro2 = new FiltroCobrancaAcaoAtividadeComando();
				filtro2.adicionarParametro(new ParametroSimples(FiltroCobrancaAcaoAtividadeComando.ID, stringComando));
				Collection colecaoComando = fachada.pesquisar(filtro2, CobrancaAcaoAtividadeComando.class.getName());
				if(colecaoComando == null || colecaoComando.isEmpty()){
					comando = null;
				}else{
					comando = (CobrancaAcaoAtividadeComando) colecaoComando.iterator().next();
				}

			}
		}

		if(stringEmpresa != null && !stringEmpresa.equals("")){
			FiltroEmpresa filtro3 = new FiltroEmpresa();
			filtro3.adicionarParametro(new ParametroSimples(FiltroEmpresa.ID, stringEmpresa));
			Collection colecaoEmpresa = fachada.pesquisar(filtro3, Empresa.class.getName());
			if(colecaoEmpresa == null || colecaoEmpresa.isEmpty()){
				empresa = null;
			}else{
				empresa = (Empresa) colecaoEmpresa.iterator().next();
			}
		}

		if(stringAcao != null && !stringAcao.equals("")){
			FiltroCobrancaAcao filtro4 = new FiltroCobrancaAcao();
			filtro4.adicionarParametro(new ParametroSimples(FiltroCobrancaAcao.ID, stringAcao));
			Collection colecaoAcao = fachada.pesquisar(filtro4, CobrancaAcao.class.getName());
			if(colecaoAcao == null || colecaoAcao.isEmpty()){
				acao = null;
			}else{
				acao = (CobrancaAcao) colecaoAcao.iterator().next();
			}
		}

		FileInputStream template;
		try{
			template = new FileInputStream(httpServletRequest.getRealPath("") + "\\xls\\relatorioEficienciaCobrancaTemplate.xls");
		}catch(FileNotFoundException e1){
			throw new ActionServletException(e1.getMessage());
		}

		// Collection colecaoRetorno = fachada.filtrarCobrancaRemuneracaoPorAcao(dataInicial,
		// dataFinal, acao, empresa, comando, cronograma);
		// Collection colecaoRetornoEficienciaCobranca =
		// fachada.filtrarRelatorioEficienciaCobranca(dataInicial, dataFinal, acao, empresa,
		// comando, cronograma, setores, grupos);

		Collection colecaoRetorno = fachada.filtrarRelatorioEficienciaCobranca(dataInicial, dataFinal, acao, empresa, comando, cronograma,
						setores, grupos);

		if(colecaoRetorno == null || colecaoRetorno.isEmpty()){
			throw new ActionServletException("atencao.pesquisa.nenhumresultado");
		}
		RelatorioEficienciaCobrancaAction relatorio = new RelatorioEficienciaCobrancaAction(
						(Usuario) (httpServletRequest.getSession(false)).getAttribute("usuarioLogado"));

		if(Util.isVazioOuBranco(form.getPeriodoInicio()) || Util.isVazioOuBranco(form.getPeriodoFim())){
			relatorio.addParametro("periodoComando", "");
		}else{
			relatorio.addParametro("periodoComando", form.getPeriodoInicio() + " à " + form.getPeriodoFim());
		}

		relatorio.addParametro("nomeEmpresa", empresa.getDescricao());

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

		return retorno;
	}

	/**
	 * @author isilva
	 * @param form
	 */
	private void validaroFormulario(FiltroEficienciaCobrancaRelatorioActionForm form){

		if(form.getComando() == null || form.getComando().equalsIgnoreCase("") || form.getComando().length() < 1){
			throw new ActionServletException("atencao.informe_campo", null, "Tipo de Comando");
		}

		// Ambos
		if(form.getComando().equalsIgnoreCase("3")){
			this.validarDatas(form);
		}

	}

	/**
	 * @author isilva
	 * @param form
	 */
	private void validarDatas(FiltroEficienciaCobrancaRelatorioActionForm form){

		if(Util.isVazioOuBranco(form.getPeriodoInicio()) && Util.isVazioOuBranco(form.getPeriodoInicio())){
			throw new ActionServletException("atencao.informe_campo", null, "Período");
		}

		Date periodoInicio = null;
		Date periodoFim = null;

		// Data Inicial
		if(Util.isVazioOuBranco(form.getPeriodoInicio())){
			throw new ActionServletException("atencao.required", null, "Período Inicial");
		}else{

			try{
				periodoInicio = Util.converteStringParaDate(form.getPeriodoInicio());
			}catch(Exception e){
				throw new ActionServletException("atencao.campo.invalido", null, "Período Inicial");
			}

		}

		// Data Final
		if(Util.isVazioOuBranco(form.getPeriodoFim())){
			throw new ActionServletException("atencao.required", null, "Período Final");
		}else{

			try{
				periodoFim = Util.converteStringParaDate(form.getPeriodoFim());
			}catch(Exception e){
				throw new ActionServletException("atencao.campo.invalido", null, "Período Final");
			}

		}

		// Se data inicio maior que data fim
		if(Util.compararData(periodoInicio, periodoFim) == 1){
			throw new ActionServletException("atencao.data_inicial_maior_data_final", form.getPeriodoInicio(), form.getPeriodoFim());
		}

	}
}