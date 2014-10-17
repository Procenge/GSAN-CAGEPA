package gcom.relatorio.micromedicao;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.micromedicao.bean.QuadroHidrometrosSituacaoRelatorioHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.*;

public class RelatorioQuadroHidrometrosSituacao
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioQuadroHidrometrosSituacao(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_QUADRO_HIDROMETROS_SITUACAO);
	}

	@Deprecated
	public RelatorioQuadroHidrometrosSituacao() {

		super(null, "");
	}


	public Object executar() throws TarefaException{

		// valor de retorno
		byte[] retorno = null;

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		String dataReferencia = (String) getParametro("dataReferencia");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		Date dateInicioMes = Util.converterMesAnoParaDataInicial(dataReferencia);
		Date dateFimMes = Util.converterMesAnoParaDataFinalMes(dataReferencia);

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();
		
		RelatorioQuadroHidrometrosSituacaoBean relatorioBean = null;

		Collection colecaoDadosRelatorioQuadroHidrometros = fachada.pesquisarQuadroHidrometrosSituacao(dateInicioMes, dateFimMes);

		// se a cole��o de par�metros da analise n�o for vazia
		if(colecaoDadosRelatorioQuadroHidrometros != null && !colecaoDadosRelatorioQuadroHidrometros.isEmpty()){

			// coloca a cole��o de par�metros da analise no iterator
			Iterator colecaoDadosRelatorioQuadroHidrometrosIterator = colecaoDadosRelatorioQuadroHidrometros.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while(colecaoDadosRelatorioQuadroHidrometrosIterator.hasNext()){

				QuadroHidrometrosSituacaoRelatorioHelper quadroHidrometrosRelatorioHelper = (QuadroHidrometrosSituacaoRelatorioHelper) colecaoDadosRelatorioQuadroHidrometrosIterator
								.next();

				String capacidade = "";
				if(quadroHidrometrosRelatorioHelper.getCapacidade() != null){
					capacidade = quadroHidrometrosRelatorioHelper.getCapacidade();
				}

				String diametro = "";
				if(quadroHidrometrosRelatorioHelper.getDiametro() != null){
					diametro = quadroHidrometrosRelatorioHelper.getDiametro();
				}

				String marca = "";
				if(quadroHidrometrosRelatorioHelper.getMarca() != null){
					marca = quadroHidrometrosRelatorioHelper.getMarca();
				}

				Integer idLocalidade = 0;
				if(quadroHidrometrosRelatorioHelper.getIdLocalidade() != null){
					idLocalidade = quadroHidrometrosRelatorioHelper.getIdLocalidade();
				}
				String descricaoLocalidade = "";
				if(quadroHidrometrosRelatorioHelper.getDescricaoLocalidade() != null){
					descricaoLocalidade = quadroHidrometrosRelatorioHelper.getDescricaoLocalidade();
				}

				Integer idGerencia = 0;
				if(quadroHidrometrosRelatorioHelper.getIdGerencia() != null){
					idGerencia = quadroHidrometrosRelatorioHelper.getIdGerencia();
				}

				String descricaoGerencia = "";
				if(quadroHidrometrosRelatorioHelper.getDescricaoGerencia() != null){
					descricaoGerencia = quadroHidrometrosRelatorioHelper.getDescricaoGerencia();
				}

				Integer quantidade = 0;
				if(quadroHidrometrosRelatorioHelper.getQuantidade() != null){
					quantidade = quadroHidrometrosRelatorioHelper.getQuantidade();
				}
				
				Integer idLeituraAnormalidade = 0;
				if(quadroHidrometrosRelatorioHelper.getIdLeituraAnormalidade() != null){
					idLeituraAnormalidade = quadroHidrometrosRelatorioHelper.getIdLeituraAnormalidade();
				}

				String descricaoLeituraAnormalidade = "";
				if(quadroHidrometrosRelatorioHelper.getDescricaoLeituraAnormalidade() != null){
					descricaoLeituraAnormalidade = quadroHidrometrosRelatorioHelper.getDescricaoLeituraAnormalidade();
				}else{
					descricaoLeituraAnormalidade = "NORMAL";
				}

				String descricaoGerenciaBean = "";
				if(idGerencia != null && descricaoGerencia != null){
					descricaoGerenciaBean = idGerencia + " - " + descricaoGerencia;
				}

				String descricaoLocalidadeBean = "";
				if(idLocalidade != null && descricaoLocalidade != null){
					descricaoLocalidadeBean = idLocalidade + " - " + descricaoLocalidade;
				}


				relatorioBean = new RelatorioQuadroHidrometrosSituacaoBean(capacidade, diametro, marca, idLocalidade,
								descricaoLocalidadeBean, idGerencia, descricaoGerenciaBean, quantidade, idLeituraAnormalidade,
								descricaoLeituraAnormalidade);

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);
			}

		}

		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		parametros.put("dataReferencia", dataReferencia);

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_QUADRO_HIDROMETROS_SITUACAO, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_QUADRO_HIDROMETROS_SITUACAO, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// // ------------------------------------

		// retorna o relat�rio gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		Integer retorno = (Integer) getParametro("qtdeRegistros");

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioQuadroHidrometrosSituacao", this);

	}

}