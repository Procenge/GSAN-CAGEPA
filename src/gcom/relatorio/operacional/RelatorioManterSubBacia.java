/**
 * 
 */

package gcom.relatorio.operacional;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.operacional.FiltroSubBacia;
import gcom.operacional.SubBacia;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
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

/**
 * @author Bruno Ferreira dos Santos
 */
public class RelatorioManterSubBacia
				extends TarefaRelatorio {

	public RelatorioManterSubBacia(Usuario usuario, String nomeRelatorio) {

		super(usuario, nomeRelatorio);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * @see gcom.tarefa.TarefaRelatorio#calcularTotalRegistrosRelatorio()
	 */
	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;

		retorno = Fachada.getInstancia().totalRegistrosPesquisa((FiltroSubBacia) getParametro("filtroSubBaciaSessao"),
						SubBacia.class.getName());

		return retorno;
	}

	/*
	 * (non-Javadoc)
	 * @see gcom.tarefa.Tarefa#agendarTarefaBatch()
	 */
	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioManterSubBacia", this);

	}

	/*
	 * (non-Javadoc)
	 * @see gcom.tarefa.Tarefa#executar()
	 */
	@Override
	public Object executar() throws TarefaException{

		// valor de retorno
		byte[] retorno = null;

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		FiltroSubBacia filtroSubBacia = (FiltroSubBacia) getParametro("filtroSubBaciaSessao");
		SubBacia subBaciaParametros = (SubBacia) getParametro("subBaciaParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioManterSubBaciaBean relatorioBean = null;

		filtroSubBacia.adicionarCaminhoParaCarregamentoEntidade("bacia");
		filtroSubBacia.setConsultaSemLimites(true);

		Collection subBacias = fachada.pesquisar(filtroSubBacia, SubBacia.class.getName());

		// se a coleção de parâmetros da analise não for vazia
		if(subBacias != null && !subBacias.isEmpty()){

			// Organizar a coleção

			Collections.sort((List) subBacias, new Comparator() {

				public int compare(Object a, Object b){

					String bacia1 = ((SubBacia) a).getBacia().getDescricao();
					String bacia2 = ((SubBacia) b).getBacia().getDescricao();

					return bacia1.compareTo(bacia2);
				}
			});

			// coloca a coleção de parâmetros da analise no iterator
			Iterator subBaciaIterator = subBacias.iterator();

			// laço para criar a coleção de parâmetros da analise
			while(subBaciaIterator.hasNext()){

				SubBacia subBacia = (SubBacia) subBaciaIterator.next();

				relatorioBean = new RelatorioManterSubBaciaBean("" + subBacia.getCodigo(), subBacia.getDescricao(), subBacia.getBacia()
								.getDescricao(), subBacia.getBacia().getId().toString(), subBacia.getIndicadorUso() + "");

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

		parametros.put("idBacia", subBaciaParametros.getBacia().getId());

		parametros.put("nomeBacia", subBaciaParametros.getBacia().getDescricao() == null ? "" : subBaciaParametros.getBacia()
						.getDescricao());

		parametros.put("codigoSubBacia", subBaciaParametros.getCodigo() == 0 ? "" : "" + subBaciaParametros.getCodigo());

		parametros.put("descricaoSubBacia", subBaciaParametros.getDescricao());

		String indicadorUso = "";

		if(subBaciaParametros.getIndicadorUso() != null && !subBaciaParametros.getIndicadorUso().equals("")){
			if(subBaciaParametros.getIndicadorUso().equals(new Short("1"))){
				indicadorUso = "Ativo";
			}else if(subBaciaParametros.getIndicadorUso().equals(new Short("2"))){
				indicadorUso = "Inativo";
			}
		}

		parametros.put("indicadorUso", indicadorUso);

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_SUBBACIA_MANTER, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.MANTER_SUBBACIA, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------

		// retorna o relatório gerado
		return retorno;
	}

}
