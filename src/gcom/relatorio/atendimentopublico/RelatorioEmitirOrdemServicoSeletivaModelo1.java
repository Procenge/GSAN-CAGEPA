
package gcom.relatorio.atendimentopublico;

import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.batch.Relatorio;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.atendimentopublico.ordemservico.OrdemServicoSeletivaHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.SistemaException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.filtro.ParametroSimples;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RelatorioEmitirOrdemServicoSeletivaModelo1
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioEmitirOrdemServicoSeletivaModelo1(Usuario usuario, String nomeRelatorio) {

		super(usuario, ConstantesRelatorios.RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA_MODELO_1);
	}

	@Deprecated
	public RelatorioEmitirOrdemServicoSeletivaModelo1() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		Map<Integer, Integer> mapOdensServico = (Map<Integer, Integer>) getParametro("mapOdensServico");
		OrdemServicoSeletivaHelper helper = (OrdemServicoSeletivaHelper) getParametro("helper");

		byte[] retornoRelatorio = null;
		Fachada fachada = Fachada.getInstancia();

		Integer idOrdemServico = null;
		Integer idImovel = null;
		Categoria categoria = null;
		Imovel imovel = null;

		int quantidadeEconomias = 0;

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		Set<Integer> chaves = mapOdensServico.keySet();

		// Caso o helper não possua a informação da descrição do tipo de serviço
		if(Util.isVazioOuBranco(helper.getServicoTipoDescricao())){
			FiltroServicoTipo filtroServicoTipo = new FiltroServicoTipo();
			filtroServicoTipo.adicionarParametro(new ParametroSimples(FiltroServicoTipo.ID, Integer.valueOf(helper.getServicoTipo())));

			Collection<ServicoTipo> colecaoServicoTipo = fachada.pesquisar(filtroServicoTipo, ServicoTipo.class.getName());

			ServicoTipo servicoTipo = (ServicoTipo) Util.retonarObjetoDeColecao(colecaoServicoTipo);

			helper.setServicoTipoDescricao(servicoTipo.getDescricao());
		}

		for(Integer chave : chaves){
			idImovel = chave;
			idOrdemServico = mapOdensServico.get(chave);

			imovel = fachada.pesquisarImovel(idImovel);
			// String enderecoAbreviado = "";
			// enderecoAbreviado = fachada.pesquisarEndereco(idImovel);

			RelatorioEmitirOrdemServicoSeletivaModelo1Bean relatorioEmitirOrdemServicoSeletivaModelo1Bean = new RelatorioEmitirOrdemServicoSeletivaModelo1Bean();

			relatorioEmitirOrdemServicoSeletivaModelo1Bean.setDescricaoTipoServico(helper.getServicoTipoDescricao());
			relatorioEmitirOrdemServicoSeletivaModelo1Bean.setIdLocalidade(imovel.getLocalidade().getId().toString());
			relatorioEmitirOrdemServicoSeletivaModelo1Bean.setNomeLocalidade(imovel.getLocalidade().getDescricao().toString());
			relatorioEmitirOrdemServicoSeletivaModelo1Bean.setIdOrdemServico(idOrdemServico.toString());
			relatorioEmitirOrdemServicoSeletivaModelo1Bean.setMatriculaImovel(Util.retornaMatriculaImovelFormatada(imovel.getId()));
			relatorioEmitirOrdemServicoSeletivaModelo1Bean.setInscricao(imovel.getInscricaoFormatada());

			// Pesquisa a Categoria
			categoria = fachada.obterPrincipalCategoriaImovel(imovel.getId());
			relatorioEmitirOrdemServicoSeletivaModelo1Bean.setCategoria(categoria.getDescricaoAbreviada());

			// Pesquisa a quantidade de economias
			quantidadeEconomias = fachada.obterQuantidadeEconomias(imovel);
			relatorioEmitirOrdemServicoSeletivaModelo1Bean.setQuantidadeEconomias(Integer.toString(quantidadeEconomias));

			relatorioEmitirOrdemServicoSeletivaModelo1Bean.setSituacaoEsgoto(imovel.getLigacaoEsgotoSituacao().getId().toString());

			relatorioBeans.add(relatorioEmitirOrdemServicoSeletivaModelo1Bean);
		}

		SistemaParametro sistemaParametros = fachada.pesquisarParametrosDoSistema();

		// Parâmetros do relatório
		Map parametros = new HashMap();

		parametros.put("imagem", sistemaParametros.getImagemRelatorio());

		RelatorioDataSource ds = new RelatorioDataSource((List) relatorioBeans);

		retornoRelatorio = gerarRelatorio(ConstantesRelatorios.RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA_MODELO_1, parametros, ds,
						TarefaRelatorio.TIPO_PDF);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retornoRelatorio, Relatorio.RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA, this
							.getIdFuncionalidadeIniciada(), null);
		}catch(ControladorException e){
			throw new SistemaException(e, "Erro Batch Relatório");
		}
		// ------------------------------------
		// retorna o relatório gerado

		return retornoRelatorio;
	}

	public int calcularTotalRegistrosRelatorio(){

		// TODO Auto-generated method stub
		return 0;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioEmitirOrdemServicoSeletivaModelo1", this);
	}
}
