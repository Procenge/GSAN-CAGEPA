
package gcom.relatorio.faturamento.conta;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.ControladorFaturamento;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.*;

import org.apache.log4j.Logger;

/**
 * [UC0088] Registrar Faturamento Imediato
 * [SB0015] - Gera Relat�rio de Contas Bloqueadas para an�lise
 * 
 * @author Anderson Italo
 * @date 20/09/2013
 */
public class RelatorioContasBloqueadasAnalise
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(ControladorFaturamento.class);

	public RelatorioContasBloqueadasAnalise(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_CONTAS_BLOQUEADAS_ANALISE);
	}

	@Deprecated
	public RelatorioContasBloqueadasAnalise() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		log.info("*************In�cio Gera��o Relat�rio de Contas Bloqueadas para An�lise***************");
		byte[] retorno = null;
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		// Obt�m os par�metros passados
		String anoMesReferenciaFaturamento = Util.retornaDescricaoAnoMes4Digitos(getParametro("anoMesReferenciaFaturamento").toString());
		String idFaturamentoGrupo = getParametro("idFaturamentoGrupo").toString();
		String dataVencimentoGrupo = getParametro("dataVencimentoGrupo").toString();

		Collection<RelatorioContasBloqueadasAnaliseHelper> colecaoContasBloqueadasAnaliseHelper = (Collection<RelatorioContasBloqueadasAnaliseHelper>) getParametro("colecaoContasBloqueadasAnaliseHelper");

		// Monta a cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();
		Fachada fachada = Fachada.getInstancia();

		for(Iterator iterator = colecaoContasBloqueadasAnaliseHelper.iterator(); iterator.hasNext();){

			RelatorioContasBloqueadasAnaliseHelper contaBloqueada = (RelatorioContasBloqueadasAnaliseHelper) iterator.next();
			RelatorioContasBloqueadasAnaliseBean bean = new RelatorioContasBloqueadasAnaliseBean();

			bean.setDataLeitura(contaBloqueada.getDataLeitura());
			bean.setIdLocalidade(contaBloqueada.getIdLocalidade().toString());
			bean.setDescricaoLocalidade(contaBloqueada.getDescricaoLocalidade());
			bean.setCodigoSetorComercial(contaBloqueada.getCodigoSetorComercial().toString());
			bean.setNumeroQuadra(contaBloqueada.getNumeroQuadra());
			bean.setNumeroLote(contaBloqueada.getNumeroLote());
			bean.setMatriculaImovel(Util.retornaMatriculaImovelParametrizada(contaBloqueada.getMatriculaImovel()));
			bean.setConsumoMesUm(contaBloqueada.getConsumoMesUm());
			bean.setConsumoMesDois(contaBloqueada.getConsumoMesDois());
			bean.setConsumoMesTres(contaBloqueada.getConsumoMesTres());
			bean.setConsumoMesQuatro(contaBloqueada.getConsumoMesQuatro());
			bean.setConsumoMesCinco(contaBloqueada.getConsumoMesCinco());
			bean.setConsumoMesSeis(contaBloqueada.getConsumoMesSeis());
			bean.setLeituraMesUm(contaBloqueada.getLeituraMesUm());
			bean.setLeituraMesDois(contaBloqueada.getLeituraMesDois());
			bean.setLeituraMesTres(contaBloqueada.getLeituraMesTres());
			bean.setLeituraMesQuatro(contaBloqueada.getLeituraMesQuatro());
			bean.setLeituraMesCinco(contaBloqueada.getLeituraMesCinco());
			bean.setLeituraMesSeis(contaBloqueada.getLeituraMesSeis());
			bean.setDescricaoCategoria(contaBloqueada.getDescricaoCategoria());
			bean.setQuantidadeEconomias(contaBloqueada.getQuantidadeEconomias());
			bean.setIdSituacaoLigacaoAgua(contaBloqueada.getIdSituacaoLigacaoAgua());
			bean.setIdSituacaoLigacaoEsgoto(contaBloqueada.getIdSituacaoLigacaoEsgoto());
			bean.setConsumoFaturadoAgua(contaBloqueada.getConsumoFaturadoAgua());
			bean.setCobrouMedia(contaBloqueada.getCobrouMedia());
			bean.setConsumoMedio(contaBloqueada.getConsumoMedio());
			bean.setDiasConsumo(contaBloqueada.getDiasConsumo());
			bean.setVariacao(contaBloqueada.getVariacao());
			bean.setConsumoMinimo(contaBloqueada.getConsumoMinimo());
			bean.setPercentualEsgoto(contaBloqueada.getPercentualEsgoto());
			bean.setLeituraAnterior(contaBloqueada.getLeituraAnterior());
			bean.setLeituraAtual(contaBloqueada.getLeituraAtual());
			bean.setIdLeituraAnormalidade(contaBloqueada.getIdLeituraAnormalidade());

			relatorioBeans.add(bean);
		}

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("tipoFormatoRelatorio", "PDF");
		parametros.put("anoMesReferenciaFaturamento", anoMesReferenciaFaturamento);
		parametros.put("idFaturamentoGrupo", idFaturamentoGrupo);
		parametros.put("dataVencimentoGrupo", dataVencimentoGrupo);

		// Cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		// Processa a gera��o do arquivo
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_CONTAS_BLOQUEADAS_ANALISE, parametros, ds, TIPO_PDF);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{

			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_CONTAS_BLOQUEADAS_ANALISE, idFuncionalidadeIniciada,
							"Cont_Bloq_Analise_G" + idFaturamentoGrupo + "_Ref" + anoMesReferenciaFaturamento.replace("/", ""));
		}catch(ControladorException e){

			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// ------------------------------------

		log.info("*************Fim Gera��o Relat�rio de Contas Bloqueadas para An�lise***************");

		// retorna o relat�rio gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){


		return 0;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioContasBloqueadasAnalise", this);
	}

}
