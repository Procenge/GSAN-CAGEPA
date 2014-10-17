/**
 * 
 */

package gcom.relatorio.operacional;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.cadastro.unidadeoperacional.FiltroUnidadeOperacional;
import gcom.cadastro.unidadeoperacional.UnidadeOperacional;
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
public class RelatorioManterUnidadeOperacional
				extends TarefaRelatorio {

	public RelatorioManterUnidadeOperacional(Usuario usuario, String nomeRelatorio) {

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

		retorno = Fachada.getInstancia().totalRegistrosPesquisa((FiltroUnidadeOperacional) getParametro("filtroUnidadeOperacionalSessao"),
						UnidadeOperacional.class.getName());

		return retorno;
	}

	/*
	 * (non-Javadoc)
	 * @see gcom.tarefa.Tarefa#agendarTarefaBatch()
	 */
	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioManterUnidadeOperacional", this);

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

		FiltroUnidadeOperacional filtroUnidadeOperacional = (FiltroUnidadeOperacional) getParametro("filtroUnidadeOperacionalSessao");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioManterUnidadeOperacionalBean relatorioBean = null;

		filtroUnidadeOperacional.setConsultaSemLimites(true);

		Collection unidadesOperacionais = fachada.pesquisar(filtroUnidadeOperacional, UnidadeOperacional.class.getName());

		// se a cole��o de par�metros da analise n�o for vazia
		if(unidadesOperacionais != null && !unidadesOperacionais.isEmpty()){

			// Organizar a cole��o

			Collections.sort((List) unidadesOperacionais, new Comparator() {

				public int compare(Object a, Object b){

					String unidadeOperacional1 = ((UnidadeOperacional) a).getDescricao();
					String unidadeOperacional2 = ((UnidadeOperacional) b).getDescricao();

					return unidadeOperacional1.compareTo(unidadeOperacional2);
				}
			});

			// coloca a cole��o de par�metros da analise no iterator
			Iterator unidadeOperacionalIterator = unidadesOperacionais.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while(unidadeOperacionalIterator.hasNext()){

				UnidadeOperacional unidadeOperacional = (UnidadeOperacional) unidadeOperacionalIterator.next();

				relatorioBean = new RelatorioManterUnidadeOperacionalBean("" + unidadeOperacional.getCodigoUnidadeOperacional(),
								unidadeOperacional.getDescricao(), unidadeOperacional.getIndicadorAtivo() + "");

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

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_UNIDADE_OPERACIONAL_MANTER, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.MANTER_UNIDADE_OPERACIONAL, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// ------------------------------------

		// retorna o relat�rio gerado
		return retorno;
	}

}
