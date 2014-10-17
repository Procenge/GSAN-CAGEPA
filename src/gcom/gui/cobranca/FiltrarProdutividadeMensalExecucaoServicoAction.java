
package gcom.gui.cobranca;

import gcom.atendimentopublico.ligacaoagua.CorteTipo;
import gcom.atendimentopublico.ligacaoagua.FiltroCorteTipo;
import gcom.atendimentopublico.ligacaoagua.FiltroSupressaoTipo;
import gcom.atendimentopublico.ligacaoagua.SupressaoTipo;
import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.FiltroServicoTipoGrupo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipoGrupo;
import gcom.cadastro.empresa.Empresa;
import gcom.cadastro.empresa.FiltroEmpresa;
import gcom.cadastro.localidade.FiltroLocalidade;
import gcom.cadastro.localidade.Localidade;
import gcom.cobranca.CobrancaAcao;
import gcom.cobranca.CobrancaGrupo;
import gcom.cobranca.FiltroCobrancaAcao;
import gcom.cobranca.FiltroCobrancaGrupo;
import gcom.fachada.Fachada;
import gcom.gui.ActionServletException;
import gcom.relatorio.ExibidorProcessamentoTarefaRelatorio;
import gcom.relatorio.RelatorioVazioException;
import gcom.relatorio.cobranca.RelatorioProdutividadeMensalExecucaoServico;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesAplicacao;
import gcom.util.ConstantesSistema;
import gcom.util.SistemaException;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import br.com.procenge.util.Mes;

public class FiltrarProdutividadeMensalExecucaoServicoAction
				extends ExibidorProcessamentoTarefaRelatorio {

	public ActionForward exibirFiltrarProdutividadeMensal(ActionMapping mapping, ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception{

		String retorno = "mesmaPagina";

		preecherCombosPadrao(request);
		return mapping.findForward(retorno);
	}

	public ActionForward gerarRelatorioProdutividadeMensal(ActionMapping mapping, ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception{

		ActionForward retorno = null;

		DynaActionForm dynaForm = (DynaActionForm) form;

		String tipoComando = (String) dynaForm.get("tipoComando");

		String idCobrancaAcaoComando = dynaForm.get("idCobrancaAcaoAtividadeComando").toString();
		String idCobrancaAcaoCronograma = dynaForm.get("idCobrancaAcaoAtividadeCronograma").toString();

		String padraoPeriodo = dynaForm.get("padraoPeriodo").toString();
		String periodoInicio = dynaForm.get("periodoInicio").toString();
		String periodoFim = dynaForm.get("periodoFim").toString();
		String periodoMesInicio = null;
		String periodoMesFim = null;
		String periodoAnoInicio = null;
		String periodoAnoFim = null;
		String localidade = dynaForm.get("localidade").toString();

		Integer acaoSelecionada = null;

		if(dynaForm.get("acaoSelecionada") != null && !((Integer) dynaForm.get("acaoSelecionada")).equals(-1)){
			acaoSelecionada = Integer.valueOf(dynaForm.get("acaoSelecionada").toString());
		}

		if(!Util.isVazioOuBranco(dynaForm.get("periodoMesInicio")) && !dynaForm.get("periodoMesInicio").toString().equalsIgnoreCase("-1")){
			periodoMesInicio = dynaForm.get("periodoMesInicio").toString();
		}

		if(!Util.isVazioOuBranco(dynaForm.get("periodoMesFim")) && !dynaForm.get("periodoMesFim").toString().equalsIgnoreCase("-1")){
			periodoMesFim = dynaForm.get("periodoMesFim").toString();
		}

		if(!Util.isVazioOuBranco(dynaForm.get("periodoAnoInicio")) && !dynaForm.get("periodoAnoInicio").toString().equalsIgnoreCase("-1")){
			periodoAnoInicio = dynaForm.get("periodoAnoInicio").toString();
		}

		if(!Util.isVazioOuBranco(dynaForm.get("periodoAnoFim")) && !dynaForm.get("periodoAnoFim").toString().equalsIgnoreCase("-1")){
			periodoAnoFim = dynaForm.get("periodoAnoFim").toString();
		}

		Integer[] empresas = (Integer[]) dynaForm.get("empresaSelecionada");
		if(empresas.length == 1 && empresas[0] == -1){
			empresas = null;
		}

		Integer[] grupos = (Integer[]) dynaForm.get("grupo");
		if(grupos.length == 1 && grupos[0] == -1){
			grupos = null;
		}

		Integer[] setores = (Integer[]) dynaForm.get("setor");
		if(setores.length == 1 && setores[0] == -1){
			setores = null;
		}

		Integer[] bairros = (Integer[]) dynaForm.get("bairro");
		if(bairros.length == 1 && bairros[0] == -1){
			bairros = null;
		}
		Integer[] grupoServicos = (Integer[]) dynaForm.get("grupoServico");
		if(grupoServicos.length == 1 && grupoServicos[0] == -1){
			grupoServicos = null;
		}
		Integer[] subGrupoServicos = (Integer[]) dynaForm.get("subGrupoServico");
		if(subGrupoServicos.length == 1 && subGrupoServicos[0] == -1){
			subGrupoServicos = null;
		}
		Integer[] servicos = (Integer[]) dynaForm.get("servico");
		if(servicos.length == 1 && servicos[0] == -1){
			servicos = null;
		}
		Integer[] tiposCorte = (Integer[]) dynaForm.get("tipoCorte");
		if(tiposCorte.length == 1 && tiposCorte[0] == -1){
			tiposCorte = null;
		}
		Integer[] tiposSupressao = (Integer[]) dynaForm.get("tipoSupressao");
		if(tiposSupressao.length == 1 && tiposSupressao[0] == -1){
			tiposSupressao = null;
		}

		if(localidade == null || localidade.equalsIgnoreCase("-1")){
			localidade = null;
		}

		preecherCombosPadrao(request);

		// Valida os dados obrigatórios para a pesquisa
		this.validarParametros(tipoComando, idCobrancaAcaoComando, idCobrancaAcaoCronograma, empresas, padraoPeriodo, periodoInicio,
						periodoFim, periodoMesInicio, periodoMesFim, periodoAnoInicio, periodoAnoFim, servicos, grupoServicos,
						subGrupoServicos);

		try{

			File template = new File(request.getRealPath("") + "\\xls\\relatorioProdutividadeMensalExecucaoServicoTemplate.xls");

			RelatorioProdutividadeMensalExecucaoServico relatorio = new RelatorioProdutividadeMensalExecucaoServico((Usuario) (request
							.getSession(false)).getAttribute("usuarioLogado"));

			/**
			 * [0] - colecaoProdutividadeMensalExecucaoServicoRelatorioHelper [1] -
			 * evolucaoEnviadaTotal [2] - evolucaoExecutadaTotal [3] -
			 * acumuladaEnviadaTotal [4] - acumuladaExecutadaTotal [5] - sucessoTotal [6] -
			 * sucessoAcumuladaTotal [7] - mediaTotal
			 */

			relatorio.addParametro("template", template);

			relatorio.addParametro("tipoFormatoRelatorio", Integer.valueOf(TarefaRelatorio.TIPO_XLS));

			// ******
			relatorio.addParametro("tipoComando", tipoComando);
			relatorio.addParametro("idCobrancaAcaoComando", idCobrancaAcaoComando);
			relatorio.addParametro("idCobrancaAcaoCronograma", tipoComando);
			relatorio.addParametro("padraoPeriodo", padraoPeriodo);
			relatorio.addParametro("periodoInicio", periodoInicio);
			relatorio.addParametro("periodoFim", periodoFim);
			relatorio.addParametro("periodoMesInicio", periodoMesInicio);
			relatorio.addParametro("periodoMesFim", periodoMesFim);
			relatorio.addParametro("periodoAnoInicio", periodoAnoInicio);
			relatorio.addParametro("periodoAnoFim", periodoAnoFim);
			relatorio.addParametro("localidade", localidade);
			relatorio.addParametro("acaoSelecionada", acaoSelecionada);
			relatorio.addParametro("empresas", empresas);
			relatorio.addParametro("grupos", grupos);
			relatorio.addParametro("setores", setores);
			relatorio.addParametro("bairros", bairros);
			relatorio.addParametro("grupoServicos", grupoServicos);
			relatorio.addParametro("subGrupoServicos", subGrupoServicos);
			relatorio.addParametro("servicos", servicos);
			relatorio.addParametro("tiposCorte", tiposCorte);
			relatorio.addParametro("tiposSupressao", tiposSupressao);

			retorno = processarExibicaoRelatorio(relatorio, TarefaRelatorio.TIPO_XLS, request, response, mapping);
		}catch(SistemaException ex){
			reportarErros(request, "erro.sistema");
			retorno = mapping.findForward("telaErroPopup");
		}catch(RelatorioVazioException ex1){
			reportarErros(request, "atencao.relatorio.vazio");
			retorno = mapping.findForward("telaAtencaoPopup");
		}

		return retorno;
		// return mapping.findForward("mesmaPagina");
	}

	@SuppressWarnings("unchecked")
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

		// Lista de localidades
		FiltroLocalidade filtroLocalidade = new FiltroLocalidade();
		filtroLocalidade.setCampoOrderBy(FiltroLocalidade.DESCRICAO);
		Collection<Localidade> colecaoLocalidade = (Collection<Localidade>) fachada.pesquisar(filtroLocalidade, Localidade.class.getName());

		request.setAttribute("colecaoLocalidade", colecaoLocalidade);

		// Lista de Grupo de Cobrança

		FiltroCobrancaGrupo filtroCobrancaGrupo = new FiltroCobrancaGrupo();
		filtroCobrancaGrupo.setCampoOrderBy(FiltroCobrancaGrupo.DESCRICAO);
		Collection<CobrancaGrupo> colecaoCobrancaGrupo = (Collection<CobrancaGrupo>) fachada.pesquisar(filtroCobrancaGrupo,
						CobrancaGrupo.class.getName());

		request.setAttribute("colecaoCobrancaGrupo", colecaoCobrancaGrupo);

		// Lista de tipo de serviço
		FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
		filtroServicoTipo.setCampoOrderBy(FiltroServicoTipo.DESCRICAO);
		Collection<ServicoTipo> colecaoServicoTipo = (Collection<ServicoTipo>) fachada.pesquisar(filtroServicoTipo, ServicoTipo.class
						.getName());

		request.setAttribute("colecaoServicoTipo", colecaoServicoTipo);

		// Lista de tipo de corte
		FiltroCorteTipo filtroCorteTipo = new FiltroCorteTipo();
		filtroCorteTipo.setCampoOrderBy(FiltroCorteTipo.DESCRICAO);
		Collection<FiltroCorteTipo> colecaoCorteTipo = (Collection<FiltroCorteTipo>) fachada.pesquisar(filtroCorteTipo, CorteTipo.class
						.getName());

		request.setAttribute("colecaoCorteTipo", colecaoCorteTipo);

		// Lista de tipo de supressão
		FiltroSupressaoTipo filtroSupressaoTipo = new FiltroSupressaoTipo();
		filtroSupressaoTipo.setCampoOrderBy(FiltroSupressaoTipo.DESCRICAO);
		Collection<SupressaoTipo> colecaoSupressaoTipo = (Collection<SupressaoTipo>) fachada.pesquisar(filtroCorteTipo, SupressaoTipo.class
						.getName());

		request.setAttribute("colecaoSupressaoTipo", colecaoSupressaoTipo);

		// Lista de Grupo de Servico
		FiltroServicoTipoGrupo filtroServicoTipoGrupo = new FiltroServicoTipoGrupo();
		filtroServicoTipoGrupo.setCampoOrderBy(FiltroServicoTipoGrupo.DESCRICAO);
		Collection<ServicoTipoGrupo> colecaoServicoTipoGrupo = (Collection<ServicoTipoGrupo>) fachada.pesquisar(filtroCorteTipo,
						ServicoTipoGrupo.class.getName());

		request.setAttribute("colecaoServicoTipoGrupo", colecaoServicoTipoGrupo);

		preencheComboAnos(request);
		preencheComboMes(request);
	}

	public void preencheComboAnos(HttpServletRequest request){

		int anoInicial = 1990;
		Collection<Integer> anos = new ArrayList<Integer>();
		for(int i = anoInicial; i <= 2100; i++){
			anos.add(i);
		}
		request.setAttribute("listaAnos", anos);
	}

	public void preencheComboMes(HttpServletRequest request){

		Mes[] meses = Mes.values();
		request.setAttribute("listaMeses", meses);
	}

	/**
	 * @author isilva
	 * @param tipoComando
	 * @param idCobrancaAcaoComando
	 * @param idCobrancaAcaoCronograma
	 * @param empresas
	 * @param periodoInicio
	 * @param periodoFim
	 * @param periodoMesInicio
	 * @param periodoMesFim
	 * @param periodoAnoInicio
	 * @param periodoAnoFim
	 * @param subGrupoServicos
	 * @param grupoServicos
	 * @throws ActionServletException
	 */
	private void validarParametros(String tipoComando, String idCobrancaAcaoComando, String idCobrancaAcaoCronograma, Integer[] empresas,
					String padraoPeriodo, String periodoInicio, String periodoFim, String periodoMesInicio, String periodoMesFim,
					String periodoAnoInicio, String periodoAnoFim, Integer[] servicos, Integer[] grupoServicos, Integer[] subGrupoServicos)
					throws ActionServletException{

		// Tipo de Comando
		if(Util.isVazioOuBranco(tipoComando)){
			throw new ActionServletException("atencao.informe_campo", null, "Tipo de Comando");
		}else if(tipoComando.equalsIgnoreCase("1")){
			// if (Util.isVazioOuBranco(idCobrancaAcaoCronograma)){
			// throw new ActionServletException("atencao.informe_campo", null,
			// "Comando Cronograma");
			// }
		}else if(tipoComando.equalsIgnoreCase("2")){
			// if (Util.isVazioOuBranco(idCobrancaAcaoComando)){
			// throw new ActionServletException("atencao.informe_campo", null, "Comando Eventual");
			// }
		}

		// Empresas
		if(Util.isVazioOuBranco(empresas) || empresas.length == 0){
			throw new ActionServletException("atencao.informe_campo", null, "Empresa");
		}

		String quantidadeMaximaEmpresas = ConstantesAplicacao.get("aplicacao.quantidade.maxima.empresas.relatorio");

		if(!Util.isVazioOuBranco(quantidadeMaximaEmpresas)){
			if(empresas.length > Integer.valueOf(quantidadeMaximaEmpresas).intValue()){
				throw new ActionServletException("aplicacao.quantidade.maxima.empresas.selecionadas.deve.ser.relatorio", null,
								quantidadeMaximaEmpresas);
			}
		}

		// Padrão do Período
		if(Util.isVazioOuBranco(padraoPeriodo)){
			throw new ActionServletException("atencao.informe_campo", null, "Padrão do Periodo");
		}else if(!"1".equalsIgnoreCase(padraoPeriodo) && !"2".equalsIgnoreCase(padraoPeriodo)){
			throw new ActionServletException("atencao.label_inexistente", null, "Padrão do Periodo");
		}

		if("1".equalsIgnoreCase(padraoPeriodo)){
			// Período de 30 dias
			this.validarDatas(periodoInicio, periodoFim, periodoMesInicio, periodoMesFim, periodoAnoInicio, periodoAnoFim);
		}else{
			// Período de 6 meses
			this.validarMesAnoDatas(periodoMesInicio, periodoMesFim, periodoAnoInicio, periodoAnoFim);
		}

		if(Util.isVazioOuBranco(servicos) || servicos.length == 0){
			if((Util.isVazioOrNulo(grupoServicos) || grupoServicos.length == 0)
							&& (Util.isVazioOrNulo(subGrupoServicos) || subGrupoServicos.length == 0)) throw new ActionServletException(
							"atencao.informe_campo", null, "Serviço ou Grupo Serviço");
		}
	}

	/**
	 * @author isilva
	 * @param periodoInicio
	 * @param periodoFim
	 * @param periodoMesInicio
	 * @param periodoMesFim
	 * @param periodoAnoInicio
	 * @param periodoAnoFim
	 * @throws ActionServletException
	 */
	private void validarDatas(String periodoInicio, String periodoFim, String periodoMesInicio, String periodoMesFim,
					String periodoAnoInicio, String periodoAnoFim) throws ActionServletException{

		if(Util.isVazioOuBranco(periodoInicio) && Util.isVazioOuBranco(periodoFim)){
			throw new ActionServletException("atencao.informe_campo", null, "Período");
		}

		Date dataPeriodoInicio = null;
		Date dataPeriodoFim = null;

		// Data Inicial
		if(Util.isVazioOuBranco(periodoInicio)){
			throw new ActionServletException("atencao.required", null, "Período Inicial");
		}else{

			if(!Util.validaDataLinear(periodoInicio)){
				throw new ActionServletException("atencao.campo.invalido", null, "Período Inicial");
			}

			try{
				dataPeriodoInicio = Util.converteStringParaDate(periodoInicio);
			}catch(Exception e){
				throw new ActionServletException("atencao.campo.invalido", null, "Período Inicial");
			}

		}

		// Data Final
		if(Util.isVazioOuBranco(periodoFim)){
			throw new ActionServletException("atencao.required", null, "Período Final");
		}else{

			if(!Util.validaDataLinear(periodoFim)){
				throw new ActionServletException("atencao.campo.invalido", null, "Período Final");
			}

			try{
				dataPeriodoFim = Util.converteStringParaDate(periodoFim);
			}catch(Exception e){
				throw new ActionServletException("atencao.campo.invalido", null, "Período Final");
			}

		}

		// Se data inicio maior que data fim
		if(Util.compararData(dataPeriodoInicio, dataPeriodoFim) == 1){
			throw new ActionServletException("atencao.data_inicial_maior_data_final_sem_informar_descricao");
		}

		// Quantidade de linhas que serão listdas no .xls
		String quantidadeDiasMes = ConstantesAplicacao.get("aplicacao.quantidade.dias.mes.relatorio");

		long qtdDias = Util.diferencaDias(dataPeriodoInicio, dataPeriodoFim);

		if(qtdDias > Long.valueOf(quantidadeDiasMes).longValue()){
			throw new ActionServletException("atencao.data.maior_que.30_dias", null, "" + quantidadeDiasMes);
		}
	}

	private void validarMesAnoDatas(String periodoMesInicio, String periodoMesFim, String periodoAnoInicio, String periodoAnoFim)
					throws ActionServletException{

		if(Util.isVazioOuBranco(periodoMesInicio)){
			throw new ActionServletException("atencao.required", null, "Período Inicio");
		}else if(Integer.valueOf(periodoMesInicio) > 12 || Integer.valueOf(periodoMesInicio) < 1){
			throw new ActionServletException("atencao.campo.invalido", null, "Período Inicio");
		}

		if(Util.isVazioOuBranco(periodoAnoInicio)){
			throw new ActionServletException("atencao.required", null, "Período Inicio");
		}

		if(Util.isVazioOuBranco(periodoMesFim)){
			throw new ActionServletException("atencao.required", null, "Período Fim");
		}else if(Integer.valueOf(periodoMesFim) > 12 || Integer.valueOf(periodoMesFim) < 1){
			throw new ActionServletException("atencao.campo.invalido", null, "Período Fim");
		}

		if(Util.isVazioOuBranco(periodoAnoFim)){
			throw new ActionServletException("atencao.required", null, "Período Fim");
		}

		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		Date dataInicial = null;
		Date dataFim = null;

		try{
			dataInicial = df.parse("01/" + periodoMesInicio + "/" + periodoAnoInicio);
			dataFim = df.parse(Util.obterUltimoDiaMes(Integer.valueOf(periodoMesFim), Integer.valueOf(periodoAnoFim)) + "/" + periodoMesFim
							+ "/" + periodoAnoFim);
		}catch(ParseException e){
			throw new ActionServletException("ERRO_DADOS_INVALIDOS", null, "Período Inicio e/ou Período Fim.");
		}

		if(Util.compararData(dataInicial, dataFim) == 1){
			throw new ActionServletException("atencao.data.intervalo.invalido");
		}

		String quantidadeMes = ConstantesAplicacao.get("aplicacao.quantidade.mes.ano.relatorio");

		if((Util.calcularDiferencaDeMes(dataInicial, dataFim) + 1) > Integer.valueOf(quantidadeMes).intValue()){
			throw new ActionServletException("atencao.data.maior_que.6_meses", null, "" + quantidadeMes);
		}
	}

}
