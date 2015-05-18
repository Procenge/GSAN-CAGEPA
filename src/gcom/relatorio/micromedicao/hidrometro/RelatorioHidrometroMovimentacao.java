
package gcom.relatorio.micromedicao.hidrometro;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.micromedicao.bean.HidrometroMovimentacaoHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.*;

public class RelatorioHidrometroMovimentacao
				extends TarefaRelatorio {

	public RelatorioHidrometroMovimentacao(Usuario usuario, String nomeRelatorio) {

		super(usuario, nomeRelatorio);

	}

	@Deprecated
	public RelatorioHidrometroMovimentacao() {

		super(null, "");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;

		Collection colecaoHidrometroMovimentacaoHelper = (Collection) getParametro("colecaoHidrometroMovimentacaoHelper");

		if(colecaoHidrometroMovimentacaoHelper != null && !colecaoHidrometroMovimentacaoHelper.isEmpty()){
			retorno = colecaoHidrometroMovimentacaoHelper.size();
		}

		return retorno;
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioHidrometroMovimentacao", this);

	}

	@Override
	public Object executar() throws TarefaException{

		// valor de retorno
		byte[] retorno = null;

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		Collection colecaoHidrometroMovimentacaoHelper = (Collection) getParametro("colecaoHidrometroMovimentacaoHelper");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// cole��o de beans do relat�rio
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioHidrometroMovimentacaoBean relatorioBean = null;

		if(colecaoHidrometroMovimentacaoHelper != null && !colecaoHidrometroMovimentacaoHelper.isEmpty()){

			// coloca a cole��o de par�metros da analise no iterator
			Iterator hidrometroMovimentacaoIterator = colecaoHidrometroMovimentacaoHelper.iterator();

			// la�o para criar a cole��o de par�metros da analise
			while(hidrometroMovimentacaoIterator.hasNext()){

				HidrometroMovimentacaoHelper hidrometroMovimentacaoHelper = (HidrometroMovimentacaoHelper) hidrometroMovimentacaoIterator
								.next();

				String numero = "";
				String marca = "";
				String capacidade = "";
				String situacao = "";

				if(hidrometroMovimentacaoHelper.getHidrometro() != null){

					if(hidrometroMovimentacaoHelper.getHidrometro().getNumero() != null){
						numero = hidrometroMovimentacaoHelper.getHidrometro().getNumero();
					}

					if(hidrometroMovimentacaoHelper.getHidrometro().getHidrometroMarca() != null){
						marca = hidrometroMovimentacaoHelper.getHidrometro().getHidrometroMarca().getDescricao();
					}

					if(hidrometroMovimentacaoHelper.getHidrometro().getHidrometroCapacidade() != null){
						capacidade = hidrometroMovimentacaoHelper.getHidrometro().getHidrometroCapacidade().getDescricao();
					}
					if(hidrometroMovimentacaoHelper.getHidrometro().getHidrometroSituacao() != null){
						situacao = hidrometroMovimentacaoHelper.getHidrometro().getHidrometroSituacao().getDescricao();
					}

				}

				String localOrigem = "";
				String localDestino = "";
				String dataTransferencia = "";

				if(hidrometroMovimentacaoHelper.getHidrometroMovimentado() != null
								&& hidrometroMovimentacaoHelper.getHidrometroMovimentado().getHidrometroMovimentacao() != null){

					if(hidrometroMovimentacaoHelper.getHidrometroMovimentado().getHidrometroMovimentacao()
									.getHidrometroLocalArmazenagemOrigem() != null){
						localOrigem = hidrometroMovimentacaoHelper.getHidrometroMovimentado().getHidrometroMovimentacao()
										.getHidrometroLocalArmazenagemOrigem().getDescricao();
					}

					if(hidrometroMovimentacaoHelper.getHidrometroMovimentado().getHidrometroMovimentacao()
									.getHidrometroLocalArmazenagemDestino() != null){
						localDestino = hidrometroMovimentacaoHelper.getHidrometroMovimentado().getHidrometroMovimentacao()
										.getHidrometroLocalArmazenagemDestino().getDescricao();
					}

					if(hidrometroMovimentacaoHelper.getHidrometroMovimentado().getHidrometroMovimentacao().getData() != null){
						dataTransferencia = Util.formatarData(hidrometroMovimentacaoHelper.getHidrometroMovimentado()
										.getHidrometroMovimentacao().getData());
					}

				}

				relatorioBean = new RelatorioHidrometroMovimentacaoBean(numero, marca, capacidade, situacao, localOrigem, localDestino,
								dataTransferencia);

				// adiciona o bean a cole��o
				relatorioBeans.add(relatorioBean);
			}

		}

		// __________________________________________________________________

		// Par�metros do relat�rio
		Map parametros = (Map) getParametro("hidrometroMovimentacaoParametros");

		// adiciona os par�metros do relat�rio
		// adiciona o laudo da an�lise

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());

		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		// cria uma inst�ncia do dataSource do relat�rio
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_HIDROMETRO_MOVIMENTACAO, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relat�rio no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_HIDROMETRO_MOVIMENTACAO, idFuncionalidadeIniciada,
							"Relatorio Hidrometro Movimentacao");
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relat�rio no sistema", e);
		}
		// ------------------------------------

		// retorna o relat�rio gerado
		return retorno;
	}

}
