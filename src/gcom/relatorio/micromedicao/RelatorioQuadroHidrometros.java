package gcom.relatorio.micromedicao;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.micromedicao.bean.QuadroHidrometrosRelatorioHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.*;

public class RelatorioQuadroHidrometros
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioQuadroHidrometros(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_QUADRO_HIDROMETROS);
	}

	@Deprecated
	public RelatorioQuadroHidrometros() {

		super(null, "");
	}


	public Object executar() throws TarefaException{

		// valor de retorno
		byte[] retorno = null;

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		String dataReferencia = (String) getParametro("dataReferencia");

		String idLocalidadeStr = (String) getParametro("idLocalidadde");
		String idGerenciaRegionalStr = getParametro("idGerenciaRegional").toString();
		String idUnidadeNegocioStr = (String) getParametro("idUnidadeNegocio");

		Integer idLocalidade = null;
		if(idLocalidadeStr != null){
			idLocalidade = Util.converterStringParaInteger(idLocalidadeStr);
		}

		Integer idGerenciaRegional = null;
		if(idGerenciaRegionalStr != null){
			idGerenciaRegional = Util.converterStringParaInteger(idGerenciaRegionalStr);
		}

		Integer idUnidadeNegocio = null;
		if(idUnidadeNegocioStr != null){
			idUnidadeNegocio = Util.converterStringParaInteger(idUnidadeNegocioStr);
		}

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		Date dateReferencia = Util.converterStringParaData(dataReferencia);

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();
		
		RelatorioQuadroHidrometrosBean relatorioBean = null;		

		Collection colecaoDadosRelatorioQuadroHidrometros = fachada.pesquisarQuadroHidrometros(dateReferencia, idLocalidade,
						idGerenciaRegional, idUnidadeNegocio);

		// se a coleção de parâmetros da analise não for vazia
		if(colecaoDadosRelatorioQuadroHidrometros != null && !colecaoDadosRelatorioQuadroHidrometros.isEmpty()){

			// coloca a coleção de parâmetros da analise no iterator
			Iterator colecaoDadosRelatorioQuadroHidrometrosIterator = colecaoDadosRelatorioQuadroHidrometros.iterator();

			// laço para criar a coleção de parâmetros da analise
			while(colecaoDadosRelatorioQuadroHidrometrosIterator.hasNext()){

				QuadroHidrometrosRelatorioHelper quadroHidrometrosRelatorioHelper = (QuadroHidrometrosRelatorioHelper) colecaoDadosRelatorioQuadroHidrometrosIterator
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

				idLocalidade = 0;
				if(quadroHidrometrosRelatorioHelper.getIdlocalidade() != null){
					idLocalidade = quadroHidrometrosRelatorioHelper.getIdlocalidade();
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
				
				String gerenciaRegional = "";
				if(idGerencia != null && descricaoGerencia != null){
					gerenciaRegional = idGerencia + " - " + descricaoGerencia;
				}

				String localidade = "";
				if(idLocalidade != null && descricaoLocalidade != null){
					localidade = idLocalidade + " - " + descricaoLocalidade;
				}

				String descricao = "";
				if(capacidade != null && diametro != null){
					descricao = capacidade + " " + diametro;
				}

				relatorioBean = new RelatorioQuadroHidrometrosBean(gerenciaRegional, idGerencia, localidade, idLocalidade, descricao,
								marca, quantidade);

				// adiciona o bean a coleção
				relatorioBeans.add(relatorioBean);
			}

		}

		// __________________________________________________________________

		// Parâmetros do relatório
		Map parametros = new HashMap();

		// adiciona os parâmetros do relatório
		// adiciona o laudo da análise

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		parametros.put("P_NM_EMPRESA", sistemaParametro.getNomeEmpresa());

		parametros.put("dataReferencia", dataReferencia);

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_QUADRO_HIDROMETROS, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_QUADRO_HIDROMETROS, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------

		// retorna o relatório gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		Integer retorno = (Integer) getParametro("qtdeRegistros");

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioQuadroHidrometros", this);

	}

}