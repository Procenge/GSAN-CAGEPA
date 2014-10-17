
package gcom.relatorio.operacional.sistemaesgoto;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.operacional.FiltroSistemaEsgoto;
import gcom.operacional.SistemaEsgoto;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class RelatorioManterSistemaEsgotamento
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioManterSistemaEsgotamento(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_SISTEMA_ESGOTAMENTO_MANTER);
	}

	@Deprecated
	public RelatorioManterSistemaEsgotamento() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		FiltroSistemaEsgoto filtroSistemaEsgoto = (FiltroSistemaEsgoto) getParametro("filtroSistemaEsgotoSessao");
		SistemaEsgoto sistemaEsgotoParamentros = (SistemaEsgoto) getParametro("sistemaEsgotoParamentros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// Valor de retorno
		byte[] retorno = null;

		// Coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		RelatorioManterSistemaEsgotamentoBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		Collection<SistemaEsgoto> colecaoSistemaEsgoto = fachada.pesquisar(filtroSistemaEsgoto, SistemaEsgoto.class.getName());

		// Se a coleção de parâmetros da analise não for vazia
		if(colecaoSistemaEsgoto != null && !colecaoSistemaEsgoto.isEmpty()){
			// Organizar a coleção
			Collections.sort((List) colecaoSistemaEsgoto, new Comparator() {

				public int compare(Object a, Object b){

					String descricao1 = ((SistemaEsgoto) a).getDescricao();
					String descricao2 = ((SistemaEsgoto) b).getDescricao();

					return descricao1.compareTo(descricao2);
				}
			});

			// Coloca a coleção de parâmetros da analise no iterator
			Iterator<SistemaEsgoto> sistemaIterator = colecaoSistemaEsgoto.iterator();

			// Laço para criar a coleção de parâmetros da analise
			while(sistemaIterator.hasNext()){
				SistemaEsgoto sistemaEsgoto = (SistemaEsgoto) sistemaIterator.next();

				String codigo = Integer.toString(sistemaEsgoto.getId());
				String descricao = sistemaEsgoto.getDescricao();
				String descricaoAbreviada = sistemaEsgoto.getDescricaoAbreviada();
				String indicadorUso = sistemaEsgoto.getIndicadorUso().toString();

				relatorioBean = new RelatorioManterSistemaEsgotamentoBean(codigo, descricao, descricaoAbreviada, indicadorUso);

				// adiciona o bean a coleção
				relatorioBeans.add(relatorioBean);
			}
		}

		// Parâmetros do relatório
		Map<String, String> parametros = new HashMap<String, String>();

		// Adiciona os parâmetros do relatório
		// Adiciona o laudo da análise
		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("descricao", sistemaEsgotoParamentros.getDescricao());

		String indicadorUso;

		if(ConstantesSistema.INDICADOR_USO_ATIVO.equals(sistemaEsgotoParamentros.getIndicadorUso())){
			indicadorUso = "Ativo";
		}else if(ConstantesSistema.INDICADOR_USO_DESATIVO.equals(sistemaEsgotoParamentros.getIndicadorUso())){
			indicadorUso = "Inativo";
		}else{
			indicadorUso = "Todos";
		}

		parametros.put("indicadorUso", indicadorUso);

		// Cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_SISTEMA_ESGOTAMENTO_MANTER, parametros, ds, tipoFormatoRelatorio);

		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_LOGRADOURO_GERAL, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		// Retorna o relatório gerado
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;

		retorno = Fachada.getInstancia().totalRegistrosPesquisa((FiltroSistemaEsgoto) getParametro("filtroSistemaEsgotoSessao"),
						SistemaEsgoto.class.getName());

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioManterSistemaEsgotamento", this);
	}

}