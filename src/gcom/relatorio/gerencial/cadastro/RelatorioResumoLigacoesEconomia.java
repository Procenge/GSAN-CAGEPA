/**
 * 
 */

package gcom.relatorio.gerencial.cadastro;

import gcom.atendimentopublico.ligacaoagua.FiltroLigacaoAguaSituacao;
import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.batch.Relatorio;
import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.Quadra;
import gcom.cadastro.localidade.SetorComercial;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cobranca.CobrancaGrupo;
import gcom.fachada.Fachada;
import gcom.faturamento.FaturamentoGrupo;
import gcom.gerencial.bean.InformarDadosGeracaoRelatorioConsultaHelper;
import gcom.gerencial.cadastro.bean.DetalheLigacaoEconomiaGCSHelper;
import gcom.gerencial.cadastro.bean.SumarioLigacaoPorCategoriaGCSHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.ServiceLocator;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.parametrizacao.ExecutorParametro;
import gcom.util.parametrizacao.Parametrizacao;
import gcom.util.parametrizacao.cadastro.ExecutorParametrosCadastro;
import gcom.util.parametrizacao.cadastro.ParametroCadastro;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * @author mribeiro
 */
public class RelatorioResumoLigacoesEconomia
				extends TarefaRelatorio
				implements Parametrizacao {

	private static final String PARAMETRO_TIPO_FORMATO_RELATORIO = "tipoFormatoRelatorio";

	private static final String PARAMETRO_DADOS_PARAMETROS_CONSULTA = "dadosParametrosConsulta";

	private static final String PARAMETRO_NOME_RELATORIO = "nomeRelatorio";

	private static final Logger LOGGER = Logger.getLogger(RelatorioResumoLigacoesEconomia.class);

	public RelatorioResumoLigacoesEconomia() {

		super(null, ConstantesRelatorios.RELATORIO_RESUMO_LIGACOES_ECONOMIA);
	}

	public RelatorioResumoLigacoesEconomia(Usuario usuario, InformarDadosGeracaoRelatorioConsultaHelper dadosParametrosConsulta) {

		super(usuario, ConstantesRelatorios.RELATORIO_RESUMO_LIGACOES_ECONOMIA);
		addParametro(PARAMETRO_DADOS_PARAMETROS_CONSULTA, dadosParametrosConsulta);
		addParametro(PARAMETRO_NOME_RELATORIO, ConstantesRelatorios.RELATORIO_RESUMO_LIGACOES_ECONOMIA);
		addParametro(PARAMETRO_TIPO_FORMATO_RELATORIO, dadosParametrosConsulta.getTipoRelatorio() == null ? TarefaRelatorio.TIPO_PDF
						: dadosParametrosConsulta.getTipoRelatorio());

	}

	private List<RelatorioResumoLigacoesEconomiaGCSBean> carregarColecaoDados() throws ControladorException{

		LOGGER.debug("Inicio da busca de dados.");
		// DADOS SUMARIOS NAO FORMATADOS PARA RELATORIO
		InformarDadosGeracaoRelatorioConsultaHelper parametroPesquisa = (InformarDadosGeracaoRelatorioConsultaHelper) getParametro(PARAMETRO_DADOS_PARAMETROS_CONSULTA);
		List<SumarioLigacaoPorCategoriaGCSHelper> sumarioLigacoesPorCategoria = Fachada.getInstancia()
						.consultarSumarioLigacoesPorCategoriaGCS(parametroPesquisa);

		// PESQUISA DE LISTA DE LIGACAO AGUA SITUACAO
		FiltroLigacaoAguaSituacao filtroLigacaoAguaSituacao = (FiltroLigacaoAguaSituacao) ParametroCadastro.P_OBTER_FILTRO_LISTA_LIGACAO_AGUA_SITUACAO
						.executar(this, -1);
						
		List<LigacaoAguaSituacao> listaLigacaoAguaSituacao = (List<LigacaoAguaSituacao>) ServiceLocator.getInstancia().getControladorUtil()
						.pesquisar(filtroLigacaoAguaSituacao, LigacaoAguaSituacao.class.getName());

		// FORMATANDO OS DADOS DO RELATORIO
		List<PaginaRelatorioHelper> paginas = new ArrayList<PaginaRelatorioHelper>();
		PaginaRelatorioHelper paginaCorrente = null;

		// SUMARIOS LIGACOES
		LOGGER.debug("CRIANDO OS SUMARIOS : " + sumarioLigacoesPorCategoria.size());
		for(SumarioLigacaoPorCategoriaGCSHelper sumario : sumarioLigacoesPorCategoria){
			if(paginaCorrente == null || !paginaCorrente.getTotalizadorId().equals(sumario.getTotalizadorId())){
				paginaCorrente = new PaginaRelatorioHelper(sumario.getTotalizadorId(), sumario.getTotalizador(), getSubTitulo(
								parametroPesquisa, sumario.getTotalizadorId()), listaLigacaoAguaSituacao);
				// DADOS DETALHES NAO FORMATADOS PARA RELATORIO
				List<DetalheLigacaoEconomiaGCSHelper> detalhesLigacaoEconomia = Fachada.getInstancia()
								.consultarDetalhesLigacoesEconomiasGCS(parametroPesquisa, sumario.getTotalizadorId());
				LOGGER.debug("SETANDO DETALHES: " + "[" + sumario.getTotalizadorId() + "]" + sumario.getTotalizador() + " - "
								+ sumario.getCategoria());
				paginaCorrente.setDetalhes(detalhesLigacaoEconomia);
				paginas.add(paginaCorrente);
			}
			paginaCorrente.addSumario(sumario);
		}

		// MONTAR LISTA DAS PAGINAS PARA EXIBICAO
		List<RelatorioResumoLigacoesEconomiaGCSBean> retorno = new ArrayList<RelatorioResumoLigacoesEconomiaGCSBean>();
		for(PaginaRelatorioHelper pagina : paginas){
			retorno.addAll(pagina.toRelatorioBean());
		}
		return retorno;
	}

	/**
	 * Método getSubTitulo
	 * <p>
	 * Esse método que define o subtitulo do relatorio.
	 * </p>
	 * RASTREIO: [OC897714][UC0269]
	 * 
	 * @param parametroPesquisa
	 * @return
	 * @author Marlos Ribeiro
	 * @param quegraPaginaId
	 * @throws ControladorException
	 * @since 23/11/2012
	 */
	private String getSubTitulo(InformarDadosGeracaoRelatorioConsultaHelper dadosParametrosConsulta, Integer quegraPaginaId)
					throws ControladorException{

		String agrupador = definirAgrupador(dadosParametrosConsulta);
		String quebraPagina = definirQuegraPagina(dadosParametrosConsulta, quegraPaginaId);

		return agrupador + quebraPagina;
	}

	private String definirQuegraPagina(InformarDadosGeracaoRelatorioConsultaHelper dadosParametrosConsulta, Integer quegraPaginaId)
					throws ControladorException{

		String agrupador;
		switch(dadosParametrosConsulta.getOpcaoTotalizacao()){
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_ELO_POLO:
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL_ELO_POLO:
			case ConstantesSistema.CODIGO_ESTADO_ELO_POLO:
				Localidade eloPolo = (Localidade) ServiceLocator.getInstancia().getControladorUtil().pesquisar(quegraPaginaId,
								Localidade.class, false);
				agrupador = "ÉLO PÓLO: " + eloPolo.getDescricaoComId();
				break;
			case ConstantesSistema.CODIGO_ESTADO_GERENCIA_REGIONAL:
				GerenciaRegional gerenciaRegional = (GerenciaRegional) ServiceLocator.getInstancia().getControladorUtil().pesquisar(
								quegraPaginaId, GerenciaRegional.class, false);
				agrupador = "GERÊNCIA REGIONAL: " + gerenciaRegional.getNomeComId();
				break;
			case ConstantesSistema.CODIGO_ESTADO_GRUPO_COBRANCA:
				CobrancaGrupo cobrancaGrupo = (CobrancaGrupo) ServiceLocator.getInstancia().getControladorUtil().pesquisar(quegraPaginaId,
								CobrancaGrupo.class, false);
				agrupador = "GRUPO COBRANÇA: " + cobrancaGrupo.getDescricao();
				break;
			case ConstantesSistema.CODIGO_ESTADO_GRUPO_FATURAMENTO:
				FaturamentoGrupo faturamentoGrupo = (FaturamentoGrupo) ServiceLocator.getInstancia().getControladorUtil().pesquisar(
								quegraPaginaId, FaturamentoGrupo.class, false);
				agrupador = "GRUPO FATURAMENTO: " + faturamentoGrupo.getDescricao();
				break;
			case ConstantesSistema.CODIGO_ESTADO_UNIDADE_NEGOCIO:
				UnidadeNegocio unidadeNegocio = (UnidadeNegocio) ServiceLocator.getInstancia().getControladorUtil().pesquisar(
								quegraPaginaId, UnidadeNegocio.class, false);
				agrupador = "UNIDADE DE NEGÓCIO: " + unidadeNegocio.getNomeComId();
				break;
			case ConstantesSistema.CODIGO_LOCALIDADE_QUADRA:
			case ConstantesSistema.CODIGO_SETOR_COMERCIAL_QUADRA:
				Quadra quadra = (Quadra) ServiceLocator.getInstancia().getControladorUtil().pesquisar(quegraPaginaId, Quadra.class, false);
				agrupador = "QUADRA: " + quadra.getNumeroQuadra();
				break;
			case ConstantesSistema.CODIGO_ELO_POLO_SETOR_COMERCIAL:
			case ConstantesSistema.CODIGO_LOCALIDADE_SETOR_COMERCIAL:
				SetorComercial setorComercial = (SetorComercial) ServiceLocator.getInstancia().getControladorUtil().pesquisar(
								quegraPaginaId, SetorComercial.class, false);
				agrupador = "SETOR COMERCIAL: " + setorComercial.getDescricaoComCodigo();
				break;
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL_LOCALIDADE:
			case ConstantesSistema.CODIGO_ELO_POLO_LOCALIDADE:
			case ConstantesSistema.CODIGO_ESTADO_LOCALIDADE:
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_LOCALIDADE:
				Localidade localidade = (Localidade) ServiceLocator.getInstancia().getControladorUtil().pesquisar(quegraPaginaId,
								Localidade.class, false);
				agrupador = "LOCALIDADE: " + localidade.getDescricaoComId();
				break;
			default:
				agrupador = null;
				break;
		}
		return agrupador == null ? "" : " / " + agrupador;
	}

	private String definirAgrupador(InformarDadosGeracaoRelatorioConsultaHelper dadosParametrosConsulta){

		Integer opcaoTotalizacao = dadosParametrosConsulta.getOpcaoTotalizacao();
		String agrupador;
		switch(opcaoTotalizacao){
			case ConstantesSistema.CODIGO_ESTADO:
			case ConstantesSistema.CODIGO_ESTADO_ELO_POLO:
			case ConstantesSistema.CODIGO_ESTADO_GERENCIA_REGIONAL:
			case ConstantesSistema.CODIGO_ESTADO_GRUPO_COBRANCA:
			case ConstantesSistema.CODIGO_ESTADO_GRUPO_FATURAMENTO:
			case ConstantesSistema.CODIGO_ESTADO_LOCALIDADE:
			case ConstantesSistema.CODIGO_ESTADO_UNIDADE_NEGOCIO:
				agrupador = "TOTAL GERAL";
				break;

			case ConstantesSistema.CODIGO_GRUPO_FATURAMENTO:
				agrupador = "GRUPO DE FATURAMENTO:  " + dadosParametrosConsulta.getFaturamentoGrupo().getId() + " - "
								+ dadosParametrosConsulta.getFaturamentoGrupo().getDescricao();
				break;
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL:
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL_ELO_POLO:
			case ConstantesSistema.CODIGO_GERENCIA_REGIONAL_LOCALIDADE:
				agrupador = "GERÊNCIA REGIONAL:  " + dadosParametrosConsulta.getGerenciaRegional().getNomeComId();
				break;
			case ConstantesSistema.CODIGO_ELO_POLO:
			case ConstantesSistema.CODIGO_ELO_POLO_LOCALIDADE:
			case ConstantesSistema.CODIGO_ELO_POLO_SETOR_COMERCIAL:
				agrupador = "ÉLO PÓLO:  " + dadosParametrosConsulta.getEloPolo().getDescricaoComId();
				break;
			case ConstantesSistema.CODIGO_LOCALIDADE:
			case ConstantesSistema.CODIGO_LOCALIDADE_QUADRA:
			case ConstantesSistema.CODIGO_LOCALIDADE_SETOR_COMERCIAL:
				agrupador = "LOCALIDADE:  " + dadosParametrosConsulta.getLocalidade().getDescricaoComId();
				break;
			case ConstantesSistema.CODIGO_SETOR_COMERCIAL:
			case ConstantesSistema.CODIGO_SETOR_COMERCIAL_QUADRA:
				agrupador = "LOCALIDADE:  " + dadosParametrosConsulta.getLocalidade().getDescricaoComId()//
								+ " SETOR:  " + dadosParametrosConsulta.getSetorComercial().getDescricaoComCodigo();
				break;
			case ConstantesSistema.CODIGO_QUADRA:
				agrupador = "LOCALIDADE:  " + dadosParametrosConsulta.getLocalidade().getDescricaoComId()//
								+ " SETOR:  " + dadosParametrosConsulta.getSetorComercial().getDescricaoComCodigo()//
								+ " QUADRA: " + dadosParametrosConsulta.getQuadra().getNumeroQuadra();
				break;
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO:
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_ELO_POLO:
			case ConstantesSistema.CODIGO_UNIDADE_NEGOCIO_LOCALIDADE:
				agrupador = "UNIDADE DE NEGÓCIO:  " + dadosParametrosConsulta.getUnidadeNegocio().getNomeComId();
				break;
			default:
				agrupador = null;
				break;
		}
		return agrupador;
	}

	/*
	 * (non-Javadoc)
	 * @see gcom.tarefa.TarefaRelatorio#calcularTotalRegistrosRelatorio()
	 */
	@Override
	public int calcularTotalRegistrosRelatorio(){

		InformarDadosGeracaoRelatorioConsultaHelper dadosConsulta = (InformarDadosGeracaoRelatorioConsultaHelper) getParametro(PARAMETRO_DADOS_PARAMETROS_CONSULTA);
		return Fachada.getInstancia().consultarQtdRegistrosResumoLigacoesEconomias(dadosConsulta);
	}

	/*
	 * (non-Javadoc)
	 * @see gcom.tarefa.Tarefa#executar()
	 */
	@Override
	public Object executar() throws TarefaException{

		byte[] relatorio = null;
		try{
			List<RelatorioResumoLigacoesEconomiaGCSBean> colecaoDados = carregarColecaoDados();
			RelatorioDataSource relatorioDataSource = new RelatorioDataSource(colecaoDados);
			relatorio = gerarRelatorio((String) getParametro(PARAMETRO_NOME_RELATORIO), montarMapParametros(), relatorioDataSource,
							(Integer) getParametro(PARAMETRO_TIPO_FORMATO_RELATORIO));
			persistirRelatorioConcluido(relatorio, Relatorio.RESUMO_LIGACOES_ECONOMIA, getIdFuncionalidadeIniciada(), null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		return relatorio;
	}

	private Map montarMapParametros() throws ControladorException{

		Map<String, String> parametros = new HashMap();
		SistemaParametro parametro = Fachada.getInstancia().pesquisarParametrosDoSistema();
		parametros.put("imagem", parametro.getImagemRelatorio());
		InformarDadosGeracaoRelatorioConsultaHelper dadosConsulta = (InformarDadosGeracaoRelatorioConsultaHelper) getParametro(PARAMETRO_DADOS_PARAMETROS_CONSULTA);
		String mesAnoReferencia = dadosConsulta.getAnoMesReferencia().toString();
		parametros.put("anoMesReferencia", mesAnoReferencia.substring(4) + "/" + mesAnoReferencia.substring(0, 4));
		SistemaParametro sistemaParametro;
		sistemaParametro = getControladorUtil().pesquisarParametrosDoSistema();
		if(sistemaParametro != null){
			parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());
		}

		parametros.put("opcaoTotalizacao", dadosConsulta.getDescricaoOpcaoTotalizacao());
		parametros.put("grupoFaturamento", dadosConsulta.getFaturamentoGrupo() == null ? "Não informado" : dadosConsulta
						.getFaturamentoGrupo().getDescricao());
		parametros.put("gerenciaRegional", dadosConsulta.getGerenciaRegional() == null ? "Não informado" : dadosConsulta
						.getGerenciaRegional().getNomeComId());
		parametros.put("unidadeNegocio", dadosConsulta.getUnidadeNegocio() == null ? "Não informado" : dadosConsulta.getUnidadeNegocio()
						.getNomeComId());
		parametros.put("eloPolo", dadosConsulta.getEloPolo() == null ? "Não informado" : dadosConsulta.getEloPolo().getDescricaoComId());
		parametros.put("localidade", dadosConsulta.getLocalidade() == null ? "Não informado" : dadosConsulta.getLocalidade()
						.getDescricaoComId());
		parametros.put("setorComercial", dadosConsulta.getSetorComercial() == null ? "Não informado" : dadosConsulta.getSetorComercial()
						.getDescricaoComCodigo());
		parametros.put("quadra",
						dadosConsulta.getQuadra() == null ? "Não informado" : String.valueOf(dadosConsulta.getQuadra().getNumeroQuadra()));
		parametros.put("perfilImovel", converterColecaoParaString(dadosConsulta.getColecaoImovelPerfil(), "Todos"));
		parametros.put("ligacaoAgua", converterColecaoParaString(dadosConsulta.getColecaoLigacaoAguaSituacao(), "Todas"));
		parametros.put("ligacaoEsgoto", converterColecaoParaString(dadosConsulta.getColecaoLigacaoEsgotoSituacao(), "Todas"));
		parametros.put("categoria", converterColecaoParaString(dadosConsulta.getColecaoCategoria(), "Todas"));
		parametros.put("esferaPoder", converterColecaoParaString(dadosConsulta.getColecaoEsferaPoder(), "Todas"));

		return parametros;
	}

	private String converterColecaoParaString(Collection colecao, String casoVazia){

		StringBuilder builder = new StringBuilder();
		if(Util.isVazioOrNulo(colecao)){
			builder.append(casoVazia);
		}else{
			PropertyDescriptor pd;
			for(Object object : colecao){
				if(builder.length() > 0){
					builder.append(", ");
				}
				try{
					pd = new PropertyDescriptor("descricao", object.getClass());
					builder.append(pd.getReadMethod().invoke(object));
				}catch(IntrospectionException e){
					throw new TarefaException("Erro preenchendo os parametros do relatório.", e);
				}catch(IllegalArgumentException e){
					throw new TarefaException("Erro preenchendo os parametros do relatório.", e);
				}catch(IllegalAccessException e){
					throw new TarefaException("Erro preenchendo os parametros do relatório.", e);
				}catch(InvocationTargetException e){
					throw new TarefaException("Erro preenchendo os parametros do relatório.", e);
				}
			}
		}
		return builder.toString();
	}

	/*
	 * (non-Javadoc)
	 * @see gcom.tarefa.Tarefa#agendarTarefaBatch()
	 */
	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa(this.getClass().getName(), this);
	}

	public ExecutorParametro getExecutorParametro(){

		return ExecutorParametrosCadastro.getInstancia();
	}
}