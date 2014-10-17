
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RelatorioEmitirOrdemServicoSeletivaModelo4
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioEmitirOrdemServicoSeletivaModelo4(Usuario usuario, String nomeRelatorio) {

		super(usuario, ConstantesRelatorios.RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA_MODELO_4);
	}

	@Deprecated
	public RelatorioEmitirOrdemServicoSeletivaModelo4() {

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

			RelatorioEmitirOrdemServicoSeletivaModelo4Bean modelo4Bean = new RelatorioEmitirOrdemServicoSeletivaModelo4Bean();

			modelo4Bean.setDescricaoTipoServico(helper.getServicoTipoDescricao());
			modelo4Bean.setIdLocalidade(imovel.getLocalidade().getId().toString());
			modelo4Bean.setNomeLocalidade(imovel.getLocalidade().getDescricao().toString());
			modelo4Bean.setIdOrdemServico(idOrdemServico.toString());
			modelo4Bean.setMatriculaImovel(Util.retornaMatriculaImovelFormatada(imovel.getId()));
			modelo4Bean.setInscricao(imovel.getInscricaoFormatada());

			modelo4Bean.setNomeFirma(helper.getNomeFirma());
			modelo4Bean.setCodigoRelatorio(helper.getServicoTipo());

			String nomeCliente = fachada.pesquisarNomeClientePorImovel(idImovel);
			modelo4Bean.setNomeCliente(nomeCliente);

			try{
				String endereco = fachada.pesquisarEnderecoFormatado(idImovel);
				modelo4Bean.setEndereco(endereco);

				Object[] qtdeEValorContas = fachada.listarSomatorioEValorFaturasDebito(idImovel);
				modelo4Bean.setQuantidadeContas((Integer) qtdeEValorContas[0]);
				modelo4Bean.setValorContas((BigDecimal) qtdeEValorContas[1]);

			}catch(ControladorException e){
				throw new SistemaException(e, "Erro Batch Relatório");
			}

			// Pesquisa a Categoria
			categoria = fachada.obterPrincipalCategoriaImovel(imovel.getId());
			modelo4Bean.setCategoria(categoria.getDescricaoAbreviada());


			modelo4Bean.setSituacaoAgua(imovel.getLigacaoAguaSituacao().getId().toString());
			modelo4Bean.setSituacaoEsgoto(imovel.getLigacaoEsgotoSituacao().getId().toString());

			relatorioBeans.add(modelo4Bean);
		}

		SistemaParametro sistemaParametros = fachada.pesquisarParametrosDoSistema();

		// Parâmetros do relatório
		Map parametros = new HashMap();

		parametros.put("imagem", sistemaParametros.getImagemRelatorio());

		RelatorioDataSource ds = new RelatorioDataSource((List) relatorioBeans);

		if(helper.getTipoFormatoRelatorio().equals(TarefaRelatorio.TIPO_ZIP)){
			helper.setTipoFormatoRelatorio(TarefaRelatorio.TIPO_PDF);
		}
		retornoRelatorio = gerarRelatorio(ConstantesRelatorios.RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA_MODELO_4, parametros, ds,
						helper.getTipoFormatoRelatorio());

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

		AgendadorTarefas.agendarTarefa("RelatorioEmitirOrdemServicoSeletivaModelo4", this);
	}
}
