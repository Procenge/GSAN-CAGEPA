
package gcom.relatorio.operacional.bacia;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.operacional.Bacia;
import gcom.operacional.FiltroBacia;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author isilva
 * @date 03/02/2011
 */
public class RelatorioBacia
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioBacia(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_BACIA_MANTER);
	}

	@Deprecated
	public RelatorioBacia() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		FiltroBacia filtroBacia = (FiltroBacia) getParametro("filtroBacia");
		String codigoParamentro = (String) getParametro("codigoParamentro");
		String descricaoParamentro = (String) getParametro("descricaoParamentro");
		String descricaoAbreviadaParamentro = (String) getParametro("descricaoAbreviadaParamentro");
		String sistemaEsgotoDescricaoComIdParamentro = (String) getParametro("sistemaEsgotoDescricaoComIdParamentro");
		String subsistemaEsgotoDescricaoComCodigoParamentro = (String) getParametro("subsistemaEsgotoDescricaoComCodigoParamentro");
		String indicadorUso = (String) getParametro("indicadorUso");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// Valor de retorno
		byte[] retorno = null;

		// Coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		RelatorioManterBaciaBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		if(filtroBacia.getColecaoCaminhosParaCarregamentoEntidades() == null
						|| filtroBacia.getColecaoCaminhosParaCarregamentoEntidades().isEmpty()){
			// Objetos que serã retornados pelo hibernate
			filtroBacia.adicionarCaminhoParaCarregamentoEntidade(FiltroBacia.SUBSISTEMA_ESGOTO_SISTEMA_ESGOTO);
		}

		Collection<Bacia> colecaoBacia = fachada.pesquisar(filtroBacia, Bacia.class.getName());

		// Se a coleção de parâmetros da analise não for vazia
		if(colecaoBacia != null && !colecaoBacia.isEmpty()){
			// Organizar a coleção
			// Collections.sort((List) colecaoBacia, new Comparator() {
			// public int compare(Object a, Object b) {
			// Integer id1 = ((Bacia) a).getSubsistemaEsgoto().getSistemaEsgoto().getId();
			// Integer id2 = ((Bacia) b).getSubsistemaEsgoto().getSistemaEsgoto().getId();
			//
			// return id1.compareTo(id2);
			// }
			// });

			// Coloca a coleção de parâmetros da analise no iterator
			Iterator<Bacia> localidadeDadoOperacionalIterator = colecaoBacia.iterator();

			// Laço para criar a coleção de parâmetros da analise
			while(localidadeDadoOperacionalIterator.hasNext()){
				Bacia bacia = (Bacia) localidadeDadoOperacionalIterator.next();

				String codigoBacia = String.valueOf(bacia.getCodigo());
				String descricaoBacia = bacia.getDescricao();
				String descricaoAbreviadaBacia = bacia.getDescricaoAbreviada();
				String descricaoCodigoSistemaEsgoto = "";
				String descricaoCodigoSubsistemaEsgoto = "";

				if(bacia.getSubsistemaEsgoto() != null && bacia.getSubsistemaEsgoto().getSistemaEsgoto() != null){
					descricaoCodigoSistemaEsgoto = bacia.getSubsistemaEsgoto().getSistemaEsgoto().getDescricaoComId();
				}

				if(bacia.getSubsistemaEsgoto() != null){
					descricaoCodigoSubsistemaEsgoto = bacia.getSubsistemaEsgoto().getDescricaoComCodigo();
				}

				relatorioBean = new RelatorioManterBaciaBean(codigoBacia, descricaoBacia, descricaoAbreviadaBacia,
								descricaoCodigoSistemaEsgoto, descricaoCodigoSubsistemaEsgoto);

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

		parametros.put("codigoParamentro", codigoParamentro);
		parametros.put("descricaoParamentro", descricaoParamentro);
		parametros.put("descricaoAbreviadaParamentro", descricaoAbreviadaParamentro);
		parametros.put("sistemaEsgotoDescricaoComIdParamentro", sistemaEsgotoDescricaoComIdParamentro);
		parametros.put("subsistemaEsgotoDescricaoComCodigoParamentro", subsistemaEsgotoDescricaoComCodigoParamentro);

		if(!Util.isVazioOuBranco(indicadorUso)){
			if(String.valueOf(ConstantesSistema.INDICADOR_USO_ATIVO).equalsIgnoreCase(indicadorUso)){
				parametros.put("indicadorUso", "Ativo");
			}else if(String.valueOf(ConstantesSistema.INDICADOR_USO_DESATIVO).equalsIgnoreCase(indicadorUso)){
				parametros.put("indicadorUso", "Inativo");
			}else{
				parametros.put("indicadorUso", "Todos");
			}
		}else{
			parametros.put("indicadorUso", "Todos");
		}

		// Cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_BACIA_MANTER, parametros, ds, tipoFormatoRelatorio);

		try{
			persistirRelatorioConcluido(retorno, Relatorio.MANTER_BACIA, idFuncionalidadeIniciada, null);
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

		retorno = Fachada.getInstancia().totalRegistrosPesquisa((FiltroBacia) getParametro("filtroBacia"), Bacia.class.getName());

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioBacia", this);
	}

}