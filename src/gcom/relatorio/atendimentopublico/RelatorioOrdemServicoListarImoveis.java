
package gcom.relatorio.atendimentopublico;

import gcom.batch.Relatorio;
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
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.*;

public class RelatorioOrdemServicoListarImoveis
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioOrdemServicoListarImoveis(Usuario usuario, String nomeRelatorio) {

		super(usuario, ConstantesRelatorios.RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA);
	}

	@Deprecated
	public RelatorioOrdemServicoListarImoveis() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		Map<Integer, Integer> mapOdensServico = (Map<Integer, Integer>) getParametro("mapOdensServico");
		OrdemServicoSeletivaHelper helper = (OrdemServicoSeletivaHelper) getParametro("helper");

		byte[] retornoRelatorioListagemImoveis = null;
		Fachada fachada = Fachada.getInstancia();

		Integer idOrdemServico = null;
		Integer idImovel = null;
		Imovel imovel = null;

		// coleção de beans do relatório
		List relatorioBeans = new ArrayList();

		Set<Integer> chaves = mapOdensServico.keySet();

		for(Integer chave : chaves){
			idImovel = chave;
			idOrdemServico = mapOdensServico.get(chave);

			imovel = fachada.pesquisarImovel(idImovel);
			String enderecoAbreviado = "";
			enderecoAbreviado = fachada.pesquisarEndereco(idImovel);

			RelatorioEmitirOrdemServicoSeletivaBean relatorioEmitirOrdemServicoSeletivaBean = new RelatorioEmitirOrdemServicoSeletivaBean();
			relatorioEmitirOrdemServicoSeletivaBean.setTipoServico(helper.getServicoTipo());
			relatorioEmitirOrdemServicoSeletivaBean.setDescricaoTipoServico(helper.getServicoTipoDescricao());
			relatorioEmitirOrdemServicoSeletivaBean.setCodigoElo(helper.getElo());
			relatorioEmitirOrdemServicoSeletivaBean.setNomeElo(helper.getNomeElo());

			relatorioEmitirOrdemServicoSeletivaBean.setIdFirma(helper.getFirma());
			relatorioEmitirOrdemServicoSeletivaBean.setNomeFirma(helper.getNomeFirma());
			relatorioEmitirOrdemServicoSeletivaBean.setInscricao(imovel.getInscricaoFormatada());
			relatorioEmitirOrdemServicoSeletivaBean.setMatriculaImovel("" + imovel.getId());
			relatorioEmitirOrdemServicoSeletivaBean.setEndereco(enderecoAbreviado);
			relatorioEmitirOrdemServicoSeletivaBean.setNumeroOrdem("" + idOrdemServico);

			relatorioBeans.add(relatorioEmitirOrdemServicoSeletivaBean);
		}

		SistemaParametro sistemaParametros = fachada.pesquisarParametrosDoSistema();

		// Parâmetros do relatório
		Map parametros = new HashMap();

		parametros.put("imagem", sistemaParametros.getImagemRelatorio());

		RelatorioDataSource ds = new RelatorioDataSource((List) relatorioBeans);

		retornoRelatorioListagemImoveis = gerarRelatorio(ConstantesRelatorios.RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA, parametros, ds,
						helper.getTipoFormatoRelatorio());

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retornoRelatorioListagemImoveis, Relatorio.RELATORIO_EMITIR_ORDEM_SERVICO_SELETIVA, this
							.getIdFuncionalidadeIniciada(), null);
		}catch(ControladorException e){
			throw new SistemaException(e, "Erro Batch Relatório");
		}
		// ------------------------------------
		// retorna o relatório gerado

		return retornoRelatorioListagemImoveis;
	}

	public int calcularTotalRegistrosRelatorio(){

		// TODO Auto-generated method stub
		return 0;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioEmitirOrdemServicoSeletiva", this);
	}
}
