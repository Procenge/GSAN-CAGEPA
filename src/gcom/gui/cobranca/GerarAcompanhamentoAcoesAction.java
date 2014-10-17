
package gcom.gui.cobranca;

import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cobranca.CobrancaAcao;
import gcom.cobranca.FiltroCobrancaAcao;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.cobranca.RelatorioAcompanhamentoAcao;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
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
import org.apache.struts.action.DynaActionForm;

public class GerarAcompanhamentoAcoesAction
				extends ExibidorProcessamentoTarefaRelatorio {

	/**
	 * jns
	 * 04/08/2010
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward exibirGerarAcompanhamentoAcoes(ActionMapping mapping, ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception{

		preecherCombosPadrao(request);
		return mapping.findForward("mesmaPagina");
	}

	/**
	 * jns
	 * 04/08/2010
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward gerarRelatorioAcompanhamentoAcoes(ActionMapping mapping, ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception{

		ActionForward retorno = null;

		DynaActionForm dynaForm = (DynaActionForm) form;

		String tipoComandoStr = dynaForm.get("tipoComando").toString();
		String acaoSelecionadaStr = dynaForm.get("acaoSelecionada").toString();
		String empresaSelecionadaStr = dynaForm.get("empresaSelecionada").toString();
		String periodoInicioStr = dynaForm.get("periodoInicio").toString();
		String periodoFimStr = dynaForm.get("periodoFim").toString();
		String idCobrancaAcaoAtividadeComandoStr = dynaForm.get("idCobrancaAcaoAtividadeComando").toString();
		String idCobrancaAcaoAtividadeCronogramaStr = dynaForm.get("idCobrancaAcaoAtividadeCronograma").toString();

		preecherCombosPadrao(request);

		this.validarFormulario(tipoComandoStr, acaoSelecionadaStr, empresaSelecionadaStr, periodoInicioStr, periodoFimStr,
						idCobrancaAcaoAtividadeComandoStr, idCobrancaAcaoAtividadeCronogramaStr);

		if(Util.isVazioOuBranco(idCobrancaAcaoAtividadeCronogramaStr)){
			idCobrancaAcaoAtividadeCronogramaStr = null;
		}

		if(Util.isVazioOuBranco(idCobrancaAcaoAtividadeComandoStr)){
			idCobrancaAcaoAtividadeComandoStr = null;
		}

		if("-1".equalsIgnoreCase(acaoSelecionadaStr)){
			acaoSelecionadaStr = null;
		}

		if("-1".equalsIgnoreCase(empresaSelecionadaStr)){
			empresaSelecionadaStr = null;
		}

		try{

			File template = new File(request.getRealPath("") + "\\xls\\relatorioAcompanhamentoAcaoTemplate.xls");

			RelatorioAcompanhamentoAcao relatorio = new RelatorioAcompanhamentoAcao((Usuario) (request.getSession(false))
							.getAttribute("usuarioLogado"));

			relatorio.addParametro("template", template);

			relatorio.addParametro("tipoFormatoRelatorio", Integer.valueOf(TarefaRelatorio.TIPO_XLS));

			// ******
			relatorio.addParametro("tipoComando", tipoComandoStr);
			relatorio.addParametro("acaoSelecionada", acaoSelecionadaStr);
			relatorio.addParametro("empresa", empresaSelecionadaStr);
			relatorio.addParametro("periodoInicio", periodoInicioStr);
			relatorio.addParametro("periodoFim", periodoFimStr);
			relatorio.addParametro("idCobrancaAcaoAtividadeComando", idCobrancaAcaoAtividadeComandoStr);
			relatorio.addParametro("idCobrancaAcaoAtividadeCronograma", idCobrancaAcaoAtividadeCronogramaStr);

			retorno = processarExibicaoRelatorio(relatorio, TarefaRelatorio.TIPO_XLS, request, response, mapping);
		}catch(SistemaException ex){
			reportarErros(request, "erro.sistema");
			retorno = mapping.findForward("telaErroPopup");
		}catch(RelatorioVazioException ex1){
			reportarErros(request, "atencao.relatorio.vazio");
			retorno = mapping.findForward("telaAtencaoPopup");
		}

		return retorno;
	}

	/**
	 * jns
	 * 04/08/2010
	 * 
	 * @param request
	 */
	public void preecherCombosPadrao(HttpServletRequest request){

		Fachada fachada = Fachada.getInstancia();

		// Colecao Empresa
		FiltroEmpresa filtroEmpresa = new FiltroEmpresa();
		filtroEmpresa.adicionarParametro(new ParametroSimples(FiltroEmpresa.INDICADORUSO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroEmpresa.setCampoOrderBy(FiltroEmpresa.DESCRICAO);
		Collection<Empresa> colecaoEmpresa = (Collection<Empresa>) fachada.pesquisar(filtroEmpresa, Empresa.class.getName());

		request.setAttribute("colecaoEmpresa", colecaoEmpresa);

		// ColecaoAcao
		FiltroCobrancaAcao filtroCobrancaAcao = new FiltroCobrancaAcao();
		filtroCobrancaAcao
						.adicionarParametro(new ParametroSimples(FiltroCobrancaAcao.INDICADOR_USO, ConstantesSistema.INDICADOR_USO_ATIVO));
		filtroCobrancaAcao.setCampoOrderBy(FiltroCobrancaAcao.DESCRICAO);
		Collection<CobrancaAcao> colecaoAcao = (Collection<CobrancaAcao>) fachada.pesquisar(filtroCobrancaAcao, CobrancaAcao.class
						.getName());

		request.setAttribute("colecaoCobrancaAcao", colecaoAcao);
	}

	/**
	 * @author isilva
	 * @date 05/08/2010
	 * @param tipoComandoStr
	 * @param acaoSelecionadaStr
	 * @param empresaSelecionadaStr
	 * @param periodoInicioStr
	 * @param periodoFimStr
	 * @param idCobrancaAcaoAtividadeComandoStr
	 * @param idCobrancaAcaoAtividadeCronogramaStr
	 */
	private void validarFormulario(String tipoComandoStr, String acaoSelecionadaStr, String empresaSelecionadaStr, String periodoInicioStr,
					String periodoFimStr, String idCobrancaAcaoAtividadeComandoStr, String idCobrancaAcaoAtividadeCronogramaStr){

		// Tipo de Comando
		if(Util.isVazioOuBranco(tipoComandoStr)){
			throw new ActionServletException("atencao.informe_campo", null, "Tipo de Comando");
		}else if(tipoComandoStr.equalsIgnoreCase("1")){
			if(Util.isVazioOuBranco(idCobrancaAcaoAtividadeCronogramaStr)){
				throw new ActionServletException("atencao.informe_campo", null, "Comando Cronograma");
			}
		}else if(tipoComandoStr.equalsIgnoreCase("2")){
			if(Util.isVazioOuBranco(idCobrancaAcaoAtividadeComandoStr)){
				throw new ActionServletException("atencao.informe_campo", null, "Comando Eventual");
			}
		}

		// Validar Empresa
		if(Util.isVazioOuBranco(empresaSelecionadaStr) || "-1".equals(empresaSelecionadaStr)){
			throw new ActionServletException("atencao.usuario.empresa.nula", null, "Empresa");
		}

		// Validar Datas
		this.validarDatas(periodoInicioStr, periodoFimStr);
	}

	/**
	 * @author isilva
	 * @date 05/08/2010
	 * @param periodoInicio
	 * @param periodoFim
	 * @param periodoMesInicio
	 * @param periodoMesFim
	 * @param periodoAnoInicio
	 * @param periodoAnoFim
	 * @throws ActionServletException
	 */
	private void validarDatas(String periodoInicio, String periodoFim) throws ActionServletException{

		if(Util.isVazioOuBranco(periodoInicio) && Util.isVazioOuBranco(periodoFim)){
			throw new ActionServletException("atencao.informe_campo", null, "Período");
		}

		Date dataPeriodoInicio = null;
		Date dataPeriodoFim = null;

		// Data Inicial
		if(Util.isVazioOuBranco(periodoInicio)){
			throw new ActionServletException("atencao.required", null, "Período Inicio");
		}else{

			if(!Util.validaDataLinear(periodoInicio)){
				throw new ActionServletException("atencao.campo.invalido", null, "Período Inicio");
			}

			try{
				dataPeriodoInicio = Util.converteStringParaDate(periodoInicio);
			}catch(Exception e){
				throw new ActionServletException("atencao.campo.invalido", null, "Período Inicio");
			}

		}

		// Data Final
		if(Util.isVazioOuBranco(periodoFim)){
			throw new ActionServletException("atencao.required", null, "Período Fim");
		}else{

			if(!Util.validaDataLinear(periodoFim)){
				throw new ActionServletException("atencao.campo.invalido", null, "Período Fim");
			}

			try{
				dataPeriodoFim = Util.converteStringParaDate(periodoFim);
			}catch(Exception e){
				throw new ActionServletException("atencao.campo.invalido", null, "Período Fim");
			}

		}

		// Se data inicio maior que data fim
		if(Util.compararData(dataPeriodoInicio, dataPeriodoFim) == 1){
			throw new ActionServletException("atencao.data_inicial_maior_data_final_sem_informar_descricao");
		}
	}
}
