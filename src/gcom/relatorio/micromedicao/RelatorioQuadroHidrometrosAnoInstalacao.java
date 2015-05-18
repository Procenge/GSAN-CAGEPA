package gcom.relatorio.micromedicao;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.micromedicao.bean.QuadroHidrometrosRelatorioAnoInstalacaoHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.*;

public class RelatorioQuadroHidrometrosAnoInstalacao
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioQuadroHidrometrosAnoInstalacao(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_QUADRO_HIDROMETROS_ANO_INSTALACAO);
	}

	@Deprecated
	public RelatorioQuadroHidrometrosAnoInstalacao() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		// valor de retorno
		byte[] retorno = null;

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioQuadroHidrometrosAnoInstalacaoBean relatorioBean = null;

		Collection colecaoDadosRelatorioQuadroHidrometros = fachada.pesquisarQuadroHidrometrosAnoInstalacao();

		// se a cole��o de par�metros da analise n�o for vazia
		if(colecaoDadosRelatorioQuadroHidrometros != null && !colecaoDadosRelatorioQuadroHidrometros.isEmpty()){

			// coloca a cole��o de par�metros da analise no iterator
			Iterator colecaoDadosRelatorioQuadroHidrometrosIterator = colecaoDadosRelatorioQuadroHidrometros.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while(colecaoDadosRelatorioQuadroHidrometrosIterator.hasNext()){

				QuadroHidrometrosRelatorioAnoInstalacaoHelper quadroHidrometrosRelatorioHelper = (QuadroHidrometrosRelatorioAnoInstalacaoHelper) colecaoDadosRelatorioQuadroHidrometrosIterator
								.next();

				// idMarca
				Integer idMarca = 0;
				if(quadroHidrometrosRelatorioHelper.getIdMarca() != null){
					idMarca = quadroHidrometrosRelatorioHelper.getIdMarca();
				}

				// marca
				String marca = "";
				if(quadroHidrometrosRelatorioHelper.getMarca() != null){
					marca = quadroHidrometrosRelatorioHelper.getMarca();
				}

				// idLocalidade
				Integer idLocalidade = 0;
				if(quadroHidrometrosRelatorioHelper.getIdLocalidade() != null){
					idLocalidade = quadroHidrometrosRelatorioHelper.getIdLocalidade();
				}

				// localidade
				String localidade = "";
				if(quadroHidrometrosRelatorioHelper.getLocalidade() != null){
					localidade = quadroHidrometrosRelatorioHelper.getLocalidade();
				}

				// ano
				Integer ano = 0;
				if(quadroHidrometrosRelatorioHelper.getAno() != null){
					ano = quadroHidrometrosRelatorioHelper.getAno();
				}

				// quantidade
				Integer quantidade = 0;
				if(quadroHidrometrosRelatorioHelper.getQuantidade() != null){
					quantidade = quadroHidrometrosRelatorioHelper.getQuantidade();
				}

				// descricaoLocalidade
				String descricaoLocalidade = "";
				if(idLocalidade != null && localidade != null){
					descricaoLocalidade = idLocalidade + " - " + localidade;
				}


				relatorioBean = new RelatorioQuadroHidrometrosAnoInstalacaoBean(descricaoLocalidade, idLocalidade, ano, quantidade, marca);

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

		parametros.put("P_NM_EMPRESA", sistemaParametro.getNomeEmpresa());

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_QUADRO_HIDROMETROS_ANO_INSTALACAO, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_QUADRO_HIDROMETROS_ANO_INSTALACAO, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// ------------------------------------

		// retorna o relat�rio gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		Integer retorno = (Integer) getParametro("qtdeRegistros");

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioQuadroHidrometrosAnoInstalacao", this);

	}

}