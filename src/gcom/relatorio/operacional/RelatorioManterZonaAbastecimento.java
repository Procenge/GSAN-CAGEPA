
package gcom.relatorio.operacional;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.operacional.FiltroZonaAbastecimento;
import gcom.operacional.ZonaAbastecimento;
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

public class RelatorioManterZonaAbastecimento
				extends TarefaRelatorio {

	public RelatorioManterZonaAbastecimento(Usuario usuario, String nomeRelatorio) {

		super(usuario, nomeRelatorio);
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;

		retorno = Fachada.getInstancia().totalRegistrosPesquisa((FiltroZonaAbastecimento) getParametro("filtroZonaAbastecimentoSessao"),
						ZonaAbastecimento.class.getName());

		return retorno;
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioManterZonaAbastecimento", this);

	}

	@Override
	public Object executar() throws TarefaException{

		// valor de retorno
		byte[] retorno = null;

		// ------------------------------------
		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		// ------------------------------------

		FiltroZonaAbastecimento filtroZonaAbastecimento = (FiltroZonaAbastecimento) getParametro("filtroZonaAbastecimentoSessao");
		ZonaAbastecimento zonaAbastecimentoParametros = (ZonaAbastecimento) getParametro("zonaAbastecimentoParametros");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioManterZonaAbastecimentoBean relatorioBean = null;

		filtroZonaAbastecimento.adicionarCaminhoParaCarregamentoEntidade(FiltroZonaAbastecimento.SISTEMA_ABASTECIMENTO);
		filtroZonaAbastecimento.adicionarCaminhoParaCarregamentoEntidade(FiltroZonaAbastecimento.DISTRITO_OPERACIONAL);
		filtroZonaAbastecimento.adicionarCaminhoParaCarregamentoEntidade(FiltroZonaAbastecimento.LOCALIDADE);
		filtroZonaAbastecimento.setConsultaSemLimites(true);

		Collection zonaAbastecimentos = fachada.pesquisar(filtroZonaAbastecimento, ZonaAbastecimento.class.getName());

		// se a coleção de parâmetros da analise não for vazia
		if(zonaAbastecimentos != null && !zonaAbastecimentos.isEmpty()){

			// Organizar a coleção
			Collections.sort((List) zonaAbastecimentos, new Comparator() {

				public int compare(Object a, Object b){

					if(((ZonaAbastecimento) a).getDistritoOperacional() != null && ((ZonaAbastecimento) b).getDistritoOperacional() != null){
						String zonaAbastecimento1 = ((ZonaAbastecimento) a).getDistritoOperacional().getDescricao();
						String zonaAbastecimento2 = ((ZonaAbastecimento) b).getDistritoOperacional().getDescricao();
						return zonaAbastecimento1.compareTo(zonaAbastecimento2);
					}
					return 0;
				}
			});

			// coloca a coleção de parâmetros da analise no iterator
			Iterator zonaAbastecimentoIterator = zonaAbastecimentos.iterator();

			// laço para criar a coleção de parâmetros da analise
			while(zonaAbastecimentoIterator.hasNext()){

				ZonaAbastecimento zonaAbastecimento = (ZonaAbastecimento) zonaAbastecimentoIterator.next();

				String idDistritoOperacional = "0";
				if(zonaAbastecimento.getDistritoOperacional() != null){
					idDistritoOperacional = String.valueOf(zonaAbastecimento.getDistritoOperacional().getId());
				}

				String descricaoComIdLocalidade = "";
				if(zonaAbastecimento.getLocalidade() != null){
					descricaoComIdLocalidade = zonaAbastecimento.getLocalidade().getDescricaoComId();
				}

				String codigoZonaAbastecimento = "";
				if(zonaAbastecimento.getCodigo() != null){
					codigoZonaAbastecimento = String.valueOf(zonaAbastecimento.getCodigo());
				}

				relatorioBean = new RelatorioManterZonaAbastecimentoBean(codigoZonaAbastecimento, zonaAbastecimento.getDescricao(),
								zonaAbastecimento.getDistritoOperacional().getDescricao(), idDistritoOperacional, descricaoComIdLocalidade,
								String.valueOf(zonaAbastecimento.getIndicadorUso()));

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

		parametros.put("idDistritoOperacional", zonaAbastecimentoParametros.getDistritoOperacional() != null ? zonaAbastecimentoParametros
						.getDistritoOperacional().getId() : "0");

		parametros.put("nomeDistritoOperacional",
						zonaAbastecimentoParametros.getDistritoOperacional() != null ? zonaAbastecimentoParametros.getDistritoOperacional()
										.getDescricao() == null ? "" : zonaAbastecimentoParametros.getDistritoOperacional().getDescricao()
										: "");

		parametros.put("codigoZonaAbastecimento", (zonaAbastecimentoParametros.getCodigo() == 0 ? "" : ""
						+ zonaAbastecimentoParametros.getCodigo()));

		parametros.put("descricaoZonaAbastecimento", zonaAbastecimentoParametros.getDescricao());

		String descricaoComId = "";

		if(zonaAbastecimentoParametros != null && zonaAbastecimentoParametros.getLocalidade() != null
						&& zonaAbastecimentoParametros.getLocalidade().getId() != null){
			descricaoComId = zonaAbastecimentoParametros.getLocalidade().getDescricaoComId();
		}

		parametros.put("descricaoComId", descricaoComId);

		String indicadorUso = "Todos";

		if(zonaAbastecimentoParametros.getIndicadorUso() != null
						&& !String.valueOf(zonaAbastecimentoParametros.getIndicadorUso()).equalsIgnoreCase("")){
			if(zonaAbastecimentoParametros.getIndicadorUso().equals(new Short("1"))){
				indicadorUso = "Ativo";
			}else if(zonaAbastecimentoParametros.getIndicadorUso().equals(new Short("2"))){
				indicadorUso = "Inativo";
			}
		}

		parametros.put("indicadorUso", indicadorUso);

		// cria uma instância do dataSource do relatório
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_ZONA_ABASTECIMENTO_MANTER, parametros, ds, tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.MANTER_ZONA_ABASTECIMENTO, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------

		// retorna o relatório gerado
		return retorno;
	}
}
