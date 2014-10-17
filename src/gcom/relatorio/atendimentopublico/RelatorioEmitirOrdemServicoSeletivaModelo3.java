
package gcom.relatorio.atendimentopublico;

import gcom.atendimentopublico.ordemservico.FiltroServicoTipo;
import gcom.atendimentopublico.ordemservico.ServicoTipo;
import gcom.batch.Relatorio;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.imovel.bean.ImovelDadosConsumoHistoricoHelper;
import gcom.cadastro.imovel.bean.ImovelDadosMedicaoHistoricoHelper;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.bean.EmitirContaHelper;
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

public class RelatorioEmitirOrdemServicoSeletivaModelo3
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioEmitirOrdemServicoSeletivaModelo3(Usuario usuario, String nomeRelatorio) {

		super(usuario, ConstantesRelatorios.RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA_MODELO_3);
	}

	@Deprecated
	public RelatorioEmitirOrdemServicoSeletivaModelo3() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		Map<Integer, Integer> mapOdensServico = (Map<Integer, Integer>) getParametro("mapOdensServico");
		OrdemServicoSeletivaHelper helper = (OrdemServicoSeletivaHelper) getParametro("helper");

		byte[] retornoRelatorio = null;
		Fachada fachada = Fachada.getInstancia();

		Integer idOrdemServico = null;
		Integer idImovel = null;
		Integer[] tiposLigacaoMedicao = null;
		Integer idTipoLigacao = null;
		Integer idTipoMedicao = null;
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

			RelatorioEmitirOrdemServicoSeletivaModelo3Bean relatorioEmitirOrdemServicoSeletivaModelo3Bean = new RelatorioEmitirOrdemServicoSeletivaModelo3Bean();

			relatorioEmitirOrdemServicoSeletivaModelo3Bean.setDescricaoTipoServico(helper.getServicoTipoDescricao());
			relatorioEmitirOrdemServicoSeletivaModelo3Bean.setIdLocalidade(imovel.getLocalidade().getId().toString());
			relatorioEmitirOrdemServicoSeletivaModelo3Bean.setNomeLocalidade(imovel.getLocalidade().getDescricao().toString());
			relatorioEmitirOrdemServicoSeletivaModelo3Bean.setIdOrdemServico(idOrdemServico.toString());
			relatorioEmitirOrdemServicoSeletivaModelo3Bean.setMatriculaImovel(Util.retornaMatriculaImovelFormatada(imovel.getId()));
			relatorioEmitirOrdemServicoSeletivaModelo3Bean.setInscricao(imovel.getInscricaoFormatada());

			// Pesquisa a Categoria
			categoria = fachada.obterPrincipalCategoriaImovel(imovel.getId());
			relatorioEmitirOrdemServicoSeletivaModelo3Bean.setCategoria(categoria.getDescricaoAbreviada());

			// Pesquisa a quantidade de economias
			quantidadeEconomias = fachada.obterQuantidadeEconomias(imovel);
			relatorioEmitirOrdemServicoSeletivaModelo3Bean.setQuantidadeEconomias(Integer.toString(quantidadeEconomias));

			relatorioEmitirOrdemServicoSeletivaModelo3Bean.setSituacaoAgua(imovel.getLigacaoAguaSituacao().getId().toString());
			relatorioEmitirOrdemServicoSeletivaModelo3Bean.setSituacaoEsgoto(imovel.getLigacaoEsgotoSituacao().getId().toString());

			// Rotina utilizada para obter os tipos de ligação e de medição
			EmitirContaHelper emitirContaHelper = new EmitirContaHelper();
			emitirContaHelper.setIdLigacaoAguaSituacao(imovel.getLigacaoAguaSituacao().getId());
			emitirContaHelper.setIdLigacaoEsgotoSituacao(imovel.getLigacaoEsgotoSituacao().getId());
			tiposLigacaoMedicao = fachada.determinarTipoLigacaoMedicao(emitirContaHelper);

			// Dados obtidos a partir do tipo da ligação

			idTipoLigacao = tiposLigacaoMedicao[0];
			ImovelDadosConsumoHistoricoHelper imovelDadosConsumoHistoricoHelper = null;

			if(!Util.isVazioOuBranco(idTipoLigacao)){
				imovelDadosConsumoHistoricoHelper = fachada.obterDadosConsumoMaiorReferenciaFaturamento(imovel, idTipoLigacao);
			}

			if(!Util.isVazioOuBranco(imovelDadosConsumoHistoricoHelper)){
				if(!Util.isVazioOuBranco(imovelDadosConsumoHistoricoHelper.getConsumoMes())){
					relatorioEmitirOrdemServicoSeletivaModelo3Bean.setConsumo(imovelDadosConsumoHistoricoHelper.getConsumoMes().toString());
				}

				if(!Util.isVazioOuBranco(imovelDadosConsumoHistoricoHelper.getConsumoMedioImovel())){
					relatorioEmitirOrdemServicoSeletivaModelo3Bean.setConsumoMedioImovel(imovelDadosConsumoHistoricoHelper
									.getConsumoMedioImovel().toString());
				}

				if(!Util.isVazioOuBranco(imovelDadosConsumoHistoricoHelper.getConsumoMedioImovel())
								&& !Util.isVazioOuBranco(imovelDadosConsumoHistoricoHelper.getConsumoMes())){

					Integer variacao = null;
					if(imovelDadosConsumoHistoricoHelper.getConsumoMedioImovel() > 0){
						variacao = ((imovelDadosConsumoHistoricoHelper.getConsumoMes() - imovelDadosConsumoHistoricoHelper
										.getConsumoMedioImovel()) * 100)
										/ imovelDadosConsumoHistoricoHelper.getConsumoMedioImovel();
					}

					if(!Util.isVazioOuBranco(variacao)){
						relatorioEmitirOrdemServicoSeletivaModelo3Bean.setVariacao(variacao.toString());
					}
				}

			}

			ImovelDadosMedicaoHistoricoHelper imovelDadosMedicaoHistoricoHelper = null;

			if(!Util.isVazioOuBranco(idTipoLigacao)){
				imovelDadosMedicaoHistoricoHelper = fachada.obterDadosMedicaoMaiorReferenciaLeitura(imovel, idTipoLigacao);
			}

			if(!Util.isVazioOuBranco(imovelDadosMedicaoHistoricoHelper)){
				if(!Util.isVazioOuBranco(imovelDadosMedicaoHistoricoHelper.getLeituraAtual())){
					relatorioEmitirOrdemServicoSeletivaModelo3Bean.setLeitura(imovelDadosMedicaoHistoricoHelper.getLeituraAtual()
									.toString());
				}
			}

			relatorioBeans.add(relatorioEmitirOrdemServicoSeletivaModelo3Bean);
		}

		SistemaParametro sistemaParametros = fachada.pesquisarParametrosDoSistema();

		// Parâmetros do relatório
		Map parametros = new HashMap();

		parametros.put("imagem", sistemaParametros.getImagemRelatorio());

		RelatorioDataSource ds = new RelatorioDataSource((List) relatorioBeans);

		if(helper.getTipoFormatoRelatorio().equals(TarefaRelatorio.TIPO_ZIP)){
			helper.setTipoFormatoRelatorio(TarefaRelatorio.TIPO_PDF);
		}
		retornoRelatorio = gerarRelatorio(ConstantesRelatorios.RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA_MODELO_3, parametros, ds, helper
						.getTipoFormatoRelatorio());

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

		AgendadorTarefas.agendarTarefa("RelatorioEmitirOrdemServicoSeletivaModelo3", this);
	}
}
