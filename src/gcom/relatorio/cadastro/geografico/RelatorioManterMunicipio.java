/**
 * 
 */

package gcom.relatorio.cadastro.geografico;

import gcom.batch.Relatorio;
import gcom.cadastro.geografico.FiltroMunicipio;
import gcom.cadastro.geografico.Municipio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author Péricles Tavares
 */
public class RelatorioManterMunicipio
				extends TarefaRelatorio {

	public RelatorioManterMunicipio(Usuario usuario, String nomeRelatorio) {

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

		retorno = Fachada.getInstancia().totalRegistrosPesquisa((FiltroMunicipio) getParametro("filtroMunicipioSessao"),
						Municipio.class.getName());

		return retorno;
	}

	/*
	 * (non-Javadoc)
	 * @see gcom.tarefa.Tarefa#agendarTarefaBatch()
	 */
	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioManterMunicipio", this);

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

		FiltroMunicipio filtroMunicipio = (FiltroMunicipio) getParametro("filtroMunicipioSessao");
		filtroMunicipio.adicionarCaminhoParaCarregamentoEntidade("regiaoDesenvolvimento");
		filtroMunicipio.adicionarCaminhoParaCarregamentoEntidade("microrregiao.regiao");
		filtroMunicipio.adicionarCaminhoParaCarregamentoEntidade("unidadeFederacao");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioManterMunicipiolBean relatorioBean = null;

		filtroMunicipio.setConsultaSemLimites(true);

		Collection municipios = fachada.pesquisar(filtroMunicipio, Municipio.class.getName());

		// se a coleção de parâmetros da analise não for vazia
		if(municipios != null && !municipios.isEmpty()){

			// Organizar a coleção

			Collections.sort((List) municipios, new Comparator() {

				public int compare(Object a, Object b){

					String municipio1 = ((Municipio) a).getNome();
					String municipio2 = ((Municipio) b).getNome();

					return municipio1.compareTo(municipio2);
				}
			});

			// coloca a coleção de parâmetros da analise no iterator
			Iterator municipioIterator = municipios.iterator();

			// laço para criar a coleção de parâmetros da analise
			while(municipioIterator.hasNext()){

				Municipio municipio = (Municipio) municipioIterator.next();

				relatorioBean = new RelatorioManterMunicipiolBean("" + municipio.getId(), municipio.getNome(), municipio.getIndicadorUso()
								+ "", municipio.getUnidadeFederacao().getSigla(), municipio.getCepInicio(), municipio.getCepFim());

				// adiciona o bean a coleção
				relatorioBeans.add(relatorioBean);
			}
		}
		// __________________________________________________________________

		// Parâmetros do relatório
		Map parametros = (Map) getParametro("municipioParametros");

		// adiciona os parâmetros do relatório
		// adiciona o laudo da análise

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_MUNICIPIO_MANTER, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.MANTER_MUNICIPIO, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------

		// retorna o relatório gerado
		return retorno;
	}

}
