package gcom.relatorio.atendimentopublico;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.gui.relatorio.atendimentopublico.ordemservico.RelatorioParecerEncerramentoOSHelper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelatorioParecerEncerramentoOS
				extends TarefaRelatorio {

	public RelatorioParecerEncerramentoOS(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_PARECER_ENCERRAMENTO_OS);
	}

	@Override
	public Object executar() throws TarefaException{

		RelatorioParecerEncerramentoOSHelper relatorioParecerEncerramentoOSHelper = (RelatorioParecerEncerramentoOSHelper) getParametro("relatorioParecerEncerramentoOSHelper");
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// valor de retorno
		byte[] retorno = null;

		// coleção de beans do relatório
		List relatorioParecerEncerramentoOSBeans = new ArrayList();

		Fachada fachada = Fachada.getInstancia();

		RelatorioParecerEncerramentoOSBean relatorioParecerEncerramentoOSBean = null;

		if(relatorioParecerEncerramentoOSHelper != null){

			relatorioParecerEncerramentoOSBean = new RelatorioParecerEncerramentoOSBean(
							// numeroOS
							relatorioParecerEncerramentoOSHelper.getNumeroOS(),

							// situacaoOS
							relatorioParecerEncerramentoOSHelper.getSituacaoOS(),

							// dataGeracao
							relatorioParecerEncerramentoOSHelper.getDataGeracao(),

							// tipoServicoOSId
							relatorioParecerEncerramentoOSHelper.getTipoServicoOSId(),

							// tipoServicoOSDescricao
							relatorioParecerEncerramentoOSHelper.getTipoServicoOSDescricao(),

							// observacao
							relatorioParecerEncerramentoOSHelper.getObservacao(),

							// unidadeGeracaoId
							relatorioParecerEncerramentoOSHelper.getUnidadeGeracaoId(),

							// unidadeGeracaoDescricao
							relatorioParecerEncerramentoOSHelper.getUnidadeGeracaoDescricao(),

							// usuarioGeracaoId
							relatorioParecerEncerramentoOSHelper.getUsuarioGeracaoId(),

							// usuarioGeracaoNome
							relatorioParecerEncerramentoOSHelper.getUsuarioGeracaoNome(),

							// dataUltimaEmissao
							relatorioParecerEncerramentoOSHelper.getDataUltimaEmissao(),

							// dataEncerramento
							relatorioParecerEncerramentoOSHelper.getDataEncerramento(),

							// horaEncerramento
							relatorioParecerEncerramentoOSHelper.getHoraEncerramento(),

							// observacaoEncerramento
							relatorioParecerEncerramentoOSHelper.getObservacaoEncerramento(),

							// matriculaImovel
							relatorioParecerEncerramentoOSHelper.getMatriculaImovel(),

							// enderecoImovel
							relatorioParecerEncerramentoOSHelper.getEnderecoImovel(),

							// matriculaCliente
							relatorioParecerEncerramentoOSHelper.getMatriculaCliente(),

							// nomeCliente
							relatorioParecerEncerramentoOSHelper.getNomeCliente(),

							// rgCliente
							relatorioParecerEncerramentoOSHelper.getRgCliente(),

							// cpfCliente
							relatorioParecerEncerramentoOSHelper.getCpfCliente(),
							// numeroRA
							relatorioParecerEncerramentoOSHelper.getNumeroRA());

			// adiciona o bean a coleção
			relatorioParecerEncerramentoOSBeans.add(relatorioParecerEncerramentoOSBean);

			// Parâmetros do relatório
			Map parametros = new HashMap();

			// adiciona os parâmetros do relatório
			// adiciona o laudo da análise

			SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

			parametros.put("imagem", sistemaParametro.getImagemRelatorio());

			// cria uma instância do dataSource do relatório
			RelatorioDataSource ds = new RelatorioDataSource(relatorioParecerEncerramentoOSBeans);

			retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_PARECER_ENCERRAMENTO_OS, parametros, ds, tipoFormatoRelatorio);

		}

		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void agendarTarefaBatch(){

		// TODO Auto-generated method stub

	}

}
