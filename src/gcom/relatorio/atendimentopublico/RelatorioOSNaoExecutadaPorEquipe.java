/**
 * 
 */

package gcom.relatorio.atendimentopublico;

import gcom.atendimentopublico.ordemservico.bean.RelatorioOSNaoExecutadaEquipeHelper;
import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Bruno Ferreira dos Santos
 */
public class RelatorioOSNaoExecutadaPorEquipe
				extends TarefaRelatorio {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Deprecated
	public RelatorioOSNaoExecutadaPorEquipe() {

		super(null, "");
	}

	/**
	 * @param usuario
	 * @param nomeRelatorio
	 */
	public RelatorioOSNaoExecutadaPorEquipe(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_OS_NAO_EXECUTADA_EQUIPE);
	}

	/*
	 * (non-Javadoc)
	 * @see gcom.tarefa.TarefaRelatorio#calcularTotalRegistrosRelatorio()
	 */
	@Override
	public int calcularTotalRegistrosRelatorio(){

		Fachada fachada = Fachada.getInstancia();

		int retorno = 0;

		String origemServico = (String) getParametro("origemServico");
		String situacaoOS = (String) getParametro("situacaoOS");
		String[] idsServicosTipos = (String[]) getParametro("idsServicosTipos");
		String idUnidadeAtendimento = (String) getParametro("idUnidadeAtendimento");
		String idUnidadeAtual = (String) getParametro("idUnidadeAtual");
		String idUnidadeEncerramento = (String) getParametro("idUnidadeEncerramento");
		Date periodoAtendimentoInicial = (Date) getParametro("periodoAtendimentoInicial");
		Date periodoAtendimentoFinal = (Date) getParametro("periodoAtendimentoFinal");
		Date periodoEncerramentoInicial = (Date) getParametro("periodoEncerramentoInicial");
		Date periodoEncerramentoFinal = (Date) getParametro("periodoEncerramentoFinal");
		String idEquipeProgramacao = (String) getParametro("idEquipeProgramacao");
		String idEquipeExecucao = (String) getParametro("idEquipeExecucao");
		String idLocalidade = (String) getParametro("idLocalidade");

		retorno = fachada.pesquisarGerarRelatorioResumoOSNaoExecutadasEquipeCount(origemServico, situacaoOS, idsServicosTipos,
						idUnidadeAtendimento, idUnidadeAtual, idUnidadeEncerramento, periodoAtendimentoInicial, periodoAtendimentoFinal,
						periodoEncerramentoInicial, periodoEncerramentoFinal, idEquipeProgramacao, idEquipeExecucao, idLocalidade);

		return retorno;
	}

	/*
	 * (non-Javadoc)
	 * @see gcom.tarefa.Tarefa#agendarTarefaBatch()
	 */
	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("Relatorio OS Não Executadada Equipe", this);
	}

	/*
	 * (non-Javadoc)
	 * @see gcom.tarefa.Tarefa#executar()
	 */
	@Override
	public Object executar() throws TarefaException{

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		String origemServico = (String) getParametro("origemServico");
		String situacaoOS = (String) getParametro("situacaoOS");
		String[] idsServicosTipos = (String[]) getParametro("idsServicosTipos");
		String idUnidadeAtendimento = (String) getParametro("idUnidadeAtendimento");
		String idUnidadeAtual = (String) getParametro("idUnidadeAtual");
		String idUnidadeEncerramento = (String) getParametro("idUnidadeEncerramento");
		Date periodoAtendimentoInicial = (Date) getParametro("periodoAtendimentoInicial");
		Date periodoAtendimentoFinal = (Date) getParametro("periodoAtendimentoFinal");
		Date periodoEncerramentoInicial = (Date) getParametro("periodoEncerramentoInicial");
		Date periodoEncerramentoFinal = (Date) getParametro("periodoEncerramentoFinal");
		String idEquipeProgramacao = (String) getParametro("idEquipeProgramacao");
		String idEquipeExecucao = (String) getParametro("idEquipeExecucao");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		String idLocalidade = (String) getParametro("idLocalidade");

		// valor de retorno
		byte[] retorno = null;

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioOSNaoExecutadaEquipeBean relatorioBean = null;

		Collection colecaoRelatorioOSNaoExecutadaEquipeHelper = fachada.pesquisarGerarRelatorioResumoOSNaoExecutadasEquipe(origemServico,
						situacaoOS, idsServicosTipos, idUnidadeAtendimento, idUnidadeAtual, idUnidadeEncerramento,
						periodoAtendimentoInicial, periodoAtendimentoFinal, periodoEncerramentoInicial, periodoEncerramentoFinal,
						idEquipeProgramacao, idEquipeExecucao, idLocalidade);

		if(colecaoRelatorioOSNaoExecutadaEquipeHelper != null && !colecaoRelatorioOSNaoExecutadaEquipeHelper.isEmpty()){

			Iterator colecaoRelatorioProdutividadeEquipeHelperIterator = colecaoRelatorioOSNaoExecutadaEquipeHelper.iterator();

			RelatorioOSNaoExecutadaEquipeHelper relatorioOSNaoExecutadaEquipeHelper = null;

			while(colecaoRelatorioProdutividadeEquipeHelperIterator.hasNext()){

				relatorioOSNaoExecutadaEquipeHelper = (RelatorioOSNaoExecutadaEquipeHelper) colecaoRelatorioProdutividadeEquipeHelperIterator
								.next();

				// Prepara os campos na formatação correta ou passando-os para
				// String
				// para ser colocado no Bean do relatório

				// Descricao Unidade
				String unidade = "";
				if(relatorioOSNaoExecutadaEquipeHelper.getDescricaoUnidade() != null){
					unidade = relatorioOSNaoExecutadaEquipeHelper.getIdUnidade().toString() + " - "
									+ relatorioOSNaoExecutadaEquipeHelper.getDescricaoUnidade();
				}

				// Descricao Equipe
				String equipe = "";
				if(relatorioOSNaoExecutadaEquipeHelper.getDescricaoEquipe() != null){
					equipe = relatorioOSNaoExecutadaEquipeHelper.getIdEquipe().toString() + " - "
									+ relatorioOSNaoExecutadaEquipeHelper.getDescricaoEquipe();
				}else{
					equipe = "Ordem de serviço não programada";
				}

				// Tipo de Serviço
				String tipoServico = "";
				if(relatorioOSNaoExecutadaEquipeHelper.getIdServicoTipo() != null){
					tipoServico = relatorioOSNaoExecutadaEquipeHelper.getIdServicoTipo().toString() + " - "
									+ relatorioOSNaoExecutadaEquipeHelper.getDescricaoServicoTipo();
				}

				String motivoEncerramento;
				if(relatorioOSNaoExecutadaEquipeHelper.getMotivoEncerramento() != null){
					motivoEncerramento = relatorioOSNaoExecutadaEquipeHelper.getMotivoEncerramento();
				}else{
					motivoEncerramento = "Ordem de serviço não programada";
				}

				relatorioBean = new RelatorioOSNaoExecutadaEquipeBean(

				// Unidade Atual
								unidade,

								// Equipe
								equipe,

								// Tipo de Serviço
								tipoServico,

								// Quantidade
								"" + relatorioOSNaoExecutadaEquipeHelper.getQtd(),

								// Motivo Encerramento
								motivoEncerramento

				);

				// adiciona o bean a coleção
				relatorioBeans.add(relatorioBean);
			}
		}

		// __________________________________________________________________

		// Parâmetros do relatório
		Map parametros = new HashMap();

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		// Período de Encerramento
		String periodoEncerramento = "";

		if(periodoEncerramentoInicial != null){
			String periodoEncerramentoInicialFormatado = Util.formatarData(periodoEncerramentoInicial);
			String periodoEncerramentoFinalFormatado = Util.formatarData(periodoEncerramentoFinal);
			periodoEncerramento = periodoEncerramentoInicialFormatado + " a " + periodoEncerramentoFinalFormatado;
			parametros.put("periodoEncerramento", periodoEncerramento);
		}else{
			parametros.put("periodoEncerramento", periodoEncerramento);
		}

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_OS_NAO_EXECUTADA_EQUIPE, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_OS_NAO_EXECUTADA_EQUIPE, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------

		// retorna o relatório gerado

		return retorno;
	}
}
