
package gcom.relatorio.operacional;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.operacional.FiltroLocalidadeDadoOperacional;
import gcom.operacional.LocalidadeDadoOperacional;
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
 * @date 02/02/2011
 */
public class RelatorioLocalidadeDadoOperacional
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioLocalidadeDadoOperacional(Usuario usuario) {

		// Passar a ConstantesRelatorios.RELATORIO_LOCALIDADE_DADOS_OPERACIONAIS_MANTER quando
		// implementar a funcionalidade
		// super(usuario, ConstantesRelatorios.RELATORIO_LOCALIDADE_DADOS_OPERACIONAIS_MANTER);
		super(usuario, null);
	}

	@Deprecated
	public RelatorioLocalidadeDadoOperacional() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		FiltroLocalidadeDadoOperacional filtroLocalidadeDadoOperacional = (FiltroLocalidadeDadoOperacional) getParametro("filtroLocalidadeDadoOperacional");
		String idLocalidadeParamentro = (String) getParametro("idLocalidadeParamentro");
		String descricaoLocalidadeParamentro = (String) getParametro("descricaoLocalidadeParamentro");
		String mesAnoReferenciaInicial = (String) getParametro("mesAnoReferenciaInicial");
		String mesAnoReferenciaFinal = (String) getParametro("mesAnoReferenciaFinal");
		String indicadorUso = (String) getParametro("indicadorUso");

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// Valor de retorno
		byte[] retorno = null;

		// Coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		RelatorioManterLocalidadeDadoOperacionalBean relatorioBean = null;

		Fachada fachada = Fachada.getInstancia();

		Collection<LocalidadeDadoOperacional> colecaoLocalidadeDadoOperacional = fachada.pesquisar(filtroLocalidadeDadoOperacional,
						LocalidadeDadoOperacional.class.getName());

		// Se a coleção de parâmetros da analise não for vazia
		if(colecaoLocalidadeDadoOperacional != null && !colecaoLocalidadeDadoOperacional.isEmpty()){
			// Organizar a coleção
			// Collections.sort((List) colecaoLocalidadeDadoOperacional, new Comparator() {
			// public int compare(Object a, Object b) {
			// Integer id1 = ((LocalidadeDadoOperacional) a).getLocalidade().getId();
			// Integer id2 = ((LocalidadeDadoOperacional) b).getLocalidade().getId();
			//
			// return id1.compareTo(id2);
			// }
			// });

			// Coloca a coleção de parâmetros da analise no iterator
			Iterator<LocalidadeDadoOperacional> localidadeDadoOperacionalIterator = colecaoLocalidadeDadoOperacional.iterator();

			// Laço para criar a coleção de parâmetros da analise
			while(localidadeDadoOperacionalIterator.hasNext()){
				LocalidadeDadoOperacional localidadeDadoOperacional = (LocalidadeDadoOperacional) localidadeDadoOperacionalIterator.next();

				String codigoLocalidade = String.valueOf(localidadeDadoOperacional.getLocalidade().getId());
				String descricaoLocalidade = localidadeDadoOperacional.getLocalidade().getDescricao();
				String mesAnoReferencia = Util.formatarAnoMesSemBarraParaMesAnoComBarra(localidadeDadoOperacional.getAnoMesReferencia());
				// String indicadorUso = localidadeDadoOperacional.getIndicadorUso().toString();

				relatorioBean = new RelatorioManterLocalidadeDadoOperacionalBean(codigoLocalidade, descricaoLocalidade, mesAnoReferencia);

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

		StringBuffer idDescricaoLocalidade = new StringBuffer("");

		if(!Util.isVazioOuBranco(idLocalidadeParamentro)){
			idDescricaoLocalidade.append(idLocalidadeParamentro);
		}

		if(!Util.isVazioOuBranco(descricaoLocalidadeParamentro)){

			if(!"".equalsIgnoreCase(idDescricaoLocalidade.toString())){
				idDescricaoLocalidade.append(" - ");
			}
			idDescricaoLocalidade.append(descricaoLocalidadeParamentro);
		}

		parametros.put("idDescricaoLocalidadeParamentro", idDescricaoLocalidade.toString());
		parametros.put("mesAnoReferenciaInicial", mesAnoReferenciaInicial);
		parametros.put("mesAnoReferenciaFinal", mesAnoReferenciaFinal);

		if(!Util.isVazioOuBranco(indicadorUso)){
			if(String.valueOf(ConstantesSistema.INDICADOR_USO_ATIVO).equalsIgnoreCase(indicadorUso)){
				parametros.put("indicadorUso", "Ativo");
			}else if(String.valueOf(ConstantesSistema.INDICADOR_USO_DESATIVO).equalsIgnoreCase(indicadorUso)){
				parametros.put("indicadorUso", "Inativo");
			}
		}else{
			parametros.put("indicadorUso", "Todos");
		}

		// Cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		// Passar a ConstantesRelatorios.RELATORIO_LOCALIDADE_DADOS_OPERACIONAIS_MANTER quando
		// implementar a funcionalidade
		// retorno =
		// this.gerarRelatorio(ConstantesRelatorios.RELATORIO_LOCALIDADE_DADOS_OPERACIONAIS_MANTER,
		// parametros, ds, tipoFormatoRelatorio);
		retorno = this.gerarRelatorio(null, parametros, ds, tipoFormatoRelatorio);

		try{
			// Passar o Relatorio.MANTER_LOCALIDADE_DADOS_OPERACIONAIS quando implementar a
			// funcionalidade
			// persistirRelatorioConcluido(retorno, Relatorio.MANTER_LOCALIDADE_DADOS_OPERACIONAIS,
			// idFuncionalidadeIniciada, null);
			persistirRelatorioConcluido(retorno, 0, idFuncionalidadeIniciada, null);
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

		retorno = Fachada.getInstancia().totalRegistrosPesquisa(
						(FiltroLocalidadeDadoOperacional) getParametro("filtroLocalidadeDadoOperacional"),
						LocalidadeDadoOperacional.class.getName());

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioLocalidadeDadoOperacional", this);
	}

}