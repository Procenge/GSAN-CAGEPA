/**
 * 
 */

package gcom.gui.micromedicao;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.micromedicao.FiltroLeiturista;
import gcom.micromedicao.Leiturista;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.filtro.ParametroSimples;

import java.util.*;

/**
 * @author P�ricles Tavares
 */
public class RelatorioAnormalidadesLeituras
				extends TarefaRelatorio {

	public RelatorioAnormalidadesLeituras(Usuario usuario, String nomeRelatorio) {

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

		return ((Integer) this.getParametro("totalRegistrosRelatorio")).intValue();
	}

	/*
	 * (non-Javadoc)
	 * @see gcom.tarefa.Tarefa#agendarTarefaBatch()
	 */
	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioAnormalidadesLeituras", this);

	}

	/*
	 * (non-Javadoc)
	 * @see gcom.tarefa.Tarefa#executar()
	 */
	@Override
	public Object executar() throws TarefaException{

		Fachada fachada = Fachada.getInstancia();

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		// valor de retorno
		byte[] retorno = null;

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		Collection<Object[]> movimentosRoteiroEmpresa = (Collection<Object[]>) getParametro("movimentosRoteiroEmpresa");
		List<RelatorioAnormalidadesLeiturasBean> relatorioBeans = new ArrayList<RelatorioAnormalidadesLeiturasBean>();

		// se a cole��o de par�metros da analise n�o for vazia
		if(movimentosRoteiroEmpresa != null && !movimentosRoteiroEmpresa.isEmpty()){

			// coloca a cole��o de par�metros da analise no iterator
			Iterator movimentosRoteiroEmpresaIterator = movimentosRoteiroEmpresa.iterator();
			String nomeEmpresa = null;
			Integer idLeiturista = null;
			Integer idLeituristaAnterior = null;
			Leiturista leiturista = null;
			String nomeLeiturista = null;
			Integer rota = null;
			Integer rotaAnterior = null;
			String leituraAnormalidade = null;
			Integer totalLeituraAnormalidade = null;
			FiltroLeiturista filtroLeiturista = null;

			Boolean imprimeAgenteComercial = Boolean.FALSE;
			Boolean imprimeRota = Boolean.FALSE;

			RelatorioAnormalidadesLeiturasBean relatorioBean;
			// la�o para criar a cole��o de par�metros da analise
			while(movimentosRoteiroEmpresaIterator.hasNext()){

				Object[] array = (Object[]) movimentosRoteiroEmpresaIterator.next();
				nomeEmpresa = (String) array[0];
				idLeiturista = (Integer) array[1];
				filtroLeiturista = new FiltroLeiturista();
				filtroLeiturista.adicionarParametro(new ParametroSimples(FiltroLeiturista.ID, idLeiturista));
				filtroLeiturista.adicionarCaminhoParaCarregamentoEntidade(FiltroLeiturista.CLIENTE);
				filtroLeiturista.adicionarCaminhoParaCarregamentoEntidade(FiltroLeiturista.FUNCIONARIO);
				leiturista = (Leiturista) Util.retonarObjetoDeColecao(fachada.pesquisar(filtroLeiturista, Leiturista.class.getName()));
				if(leiturista.getFuncionario() != null){
					nomeLeiturista = leiturista.getFuncionario().getNome();
				}else{
					nomeLeiturista = leiturista.getCliente().getNome();
				}
				rota = (Integer) array[2];
				leituraAnormalidade = (String) array[3];
				totalLeituraAnormalidade = ((Number) array[4]).intValue();

				if(idLeituristaAnterior == null || !idLeituristaAnterior.equals(idLeiturista)){
					imprimeAgenteComercial = Boolean.TRUE;
				}else{
					imprimeAgenteComercial = Boolean.FALSE;
				}

				if(rotaAnterior == null || !rotaAnterior.equals(rota) || imprimeAgenteComercial){
					imprimeRota = Boolean.TRUE;
				}else{
					imprimeRota = Boolean.FALSE;
				}

				relatorioBean = new RelatorioAnormalidadesLeiturasBean(nomeEmpresa, nomeLeiturista, rota.toString(), leituraAnormalidade,
								totalLeituraAnormalidade, imprimeAgenteComercial, imprimeRota);

				idLeituristaAnterior = idLeiturista;
				rotaAnterior = rota;

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);
			}
		}

		// Par�metros do relat�rio
		Map parametros = new HashMap();

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_ANORMALIDADES_LEITURAS, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.ANORMALIDADES_LEITURAS, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// ------------------------------------

		// retorna o relat�rio gerado
		return retorno;
	}
}
