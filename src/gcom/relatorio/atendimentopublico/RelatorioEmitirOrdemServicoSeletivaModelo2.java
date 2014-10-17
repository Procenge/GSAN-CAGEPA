
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
import gcom.micromedicao.bean.HidrometroRelatorioOSHelper;
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

public class RelatorioEmitirOrdemServicoSeletivaModelo2
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioEmitirOrdemServicoSeletivaModelo2(Usuario usuario, String nomeRelatorio) {

		super(usuario, ConstantesRelatorios.RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA_MODELO_2);
	}

	@Deprecated
	public RelatorioEmitirOrdemServicoSeletivaModelo2() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		Map<Integer, Integer> mapOdensServico = (Map<Integer, Integer>) getParametro("mapOdensServico");
		OrdemServicoSeletivaHelper helper = (OrdemServicoSeletivaHelper) getParametro("helper");

		Integer tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");


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

			RelatorioEmitirOrdemServicoSeletivaModelo2Bean relatorioEmitirOrdemServicoSeletivaModelo2Bean = new RelatorioEmitirOrdemServicoSeletivaModelo2Bean();

			relatorioEmitirOrdemServicoSeletivaModelo2Bean.setDescricaoTipoServico(helper.getServicoTipoDescricao());
			relatorioEmitirOrdemServicoSeletivaModelo2Bean.setIdLocalidade(imovel.getLocalidade().getId().toString());
			relatorioEmitirOrdemServicoSeletivaModelo2Bean.setNomeLocalidade(imovel.getLocalidade().getDescricao().toString());
			relatorioEmitirOrdemServicoSeletivaModelo2Bean.setIdOrdemServico(idOrdemServico.toString());
			relatorioEmitirOrdemServicoSeletivaModelo2Bean.setMatriculaImovel(Util.retornaMatriculaImovelFormatada(imovel.getId()));
			relatorioEmitirOrdemServicoSeletivaModelo2Bean.setInscricao(imovel.getInscricaoFormatada());

			// Pesquisa a Categoria
			categoria = fachada.obterPrincipalCategoriaImovel(imovel.getId());
			relatorioEmitirOrdemServicoSeletivaModelo2Bean.setCategoria(categoria.getDescricaoAbreviada());

			// Pesquisa a quantidade de economias
			quantidadeEconomias = fachada.obterQuantidadeEconomias(imovel);
			relatorioEmitirOrdemServicoSeletivaModelo2Bean.setQuantidadeEconomias(Integer.toString(quantidadeEconomias));

			relatorioEmitirOrdemServicoSeletivaModelo2Bean.setSituacaoAgua(imovel.getLigacaoAguaSituacao().getId().toString());
			relatorioEmitirOrdemServicoSeletivaModelo2Bean.setSituacaoEsgoto(imovel.getLigacaoEsgotoSituacao().getId().toString());

			// Rotina utilizada para obter os tipos de ligação e de medição
			EmitirContaHelper emitirContaHelper = new EmitirContaHelper();
			emitirContaHelper.setIdLigacaoAguaSituacao(imovel.getLigacaoAguaSituacao().getId());
			emitirContaHelper.setIdLigacaoEsgotoSituacao(imovel.getLigacaoEsgotoSituacao().getId());
			tiposLigacaoMedicao = fachada.determinarTipoLigacaoMedicao(emitirContaHelper);

			// Dados obtidos a partir do tipo da medição
			idTipoMedicao = tiposLigacaoMedicao[1];

			HidrometroRelatorioOSHelper hidrometroRelatorioOSHelper = null;

			if(!Util.isVazioOuBranco(idTipoMedicao)){
				hidrometroRelatorioOSHelper = fachada.obterDadosHidrometroPorTipoMedicao(idImovel, idTipoMedicao);
			}

			if(!Util.isVazioOuBranco(hidrometroRelatorioOSHelper)){
				relatorioEmitirOrdemServicoSeletivaModelo2Bean.setNumeroHidrometro(hidrometroRelatorioOSHelper.getHidrometroNumero());
				relatorioEmitirOrdemServicoSeletivaModelo2Bean.setCapacidade(hidrometroRelatorioOSHelper.getHidrometroCapacidade());
				relatorioEmitirOrdemServicoSeletivaModelo2Bean.setDataInstalacao(hidrometroRelatorioOSHelper.getDataInstalacaoHidrometo());
			}

			// Dados obtidos a partir do tipo da ligação
			idTipoLigacao = tiposLigacaoMedicao[0];
			ImovelDadosConsumoHistoricoHelper imovelDadosConsumoHistoricoHelper = null;

			if(!Util.isVazioOuBranco(idTipoLigacao)){
				imovelDadosConsumoHistoricoHelper = fachada.obterDadosConsumoMaiorReferenciaFaturamento(imovel, idTipoLigacao);
			}

			if(!Util.isVazioOuBranco(imovelDadosConsumoHistoricoHelper)){
				if(!Util.isVazioOuBranco(imovelDadosConsumoHistoricoHelper.getConsumoMedioImovel())){
					relatorioEmitirOrdemServicoSeletivaModelo2Bean.setConsumoMedioImovel(imovelDadosConsumoHistoricoHelper
									.getConsumoMedioImovel().toString());
				}
			}

			ImovelDadosMedicaoHistoricoHelper imovelDadosMedicaoHistoricoHelper = null;

			if(!Util.isVazioOuBranco(idTipoLigacao)){
				imovelDadosMedicaoHistoricoHelper = fachada.obterDadosMedicaoMaiorReferenciaLeitura(imovel, idTipoLigacao);
			}

			if(!Util.isVazioOuBranco(imovelDadosMedicaoHistoricoHelper)){
				if(!Util.isVazioOuBranco(imovelDadosMedicaoHistoricoHelper.getIdAnormalidadeLeitura())){
					relatorioEmitirOrdemServicoSeletivaModelo2Bean.setAnormalidadeLeitura(imovelDadosMedicaoHistoricoHelper
									.getIdAnormalidadeLeitura().toString());
				}
			}

			relatorioBeans.add(relatorioEmitirOrdemServicoSeletivaModelo2Bean);
		}

		SistemaParametro sistemaParametros = fachada.pesquisarParametrosDoSistema();

		// Parâmetros do relatório
		Map parametros = new HashMap();

		parametros.put("imagem", sistemaParametros.getImagemRelatorio());


		RelatorioDataSource ds = new RelatorioDataSource((List) relatorioBeans);

		if(tipoFormatoRelatorio.equals(TarefaRelatorio.TIPO_ZIP)){
			tipoFormatoRelatorio = TarefaRelatorio.TIPO_PDF;
		}
		retornoRelatorio = gerarRelatorio(ConstantesRelatorios.RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA_MODELO_2, parametros, ds,
						tipoFormatoRelatorio);

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

		AgendadorTarefas.agendarTarefa("RelatorioEmitirOrdemServicoSeletivaModelo2", this);
	}
}
