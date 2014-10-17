package gcom.relatorio.faturamento.conta;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.faturamento.ControladorFaturamento;
import gcom.faturamento.FaturamentoGrupo;
import gcom.faturamento.bean.EmitirContaTipo2Helper;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;
import gcom.util.parametrizacao.ParametroGeral;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import br.com.procenge.comum.exception.NegocioException;

public class RelatorioContaModelo3
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(ControladorFaturamento.class);

	public RelatorioContaModelo3(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_CONTA_MODELO_3);
	}

	@Deprecated
	public RelatorioContaModelo3() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		// valor de retorno
		byte[] retorno = null;

		Collection<EmitirContaTipo2Helper> colecaoContaHelper = (Collection) getParametro("colecaoContaHelper");
		FaturamentoGrupo faturamentoGrupo = (FaturamentoGrupo) getParametro("faturamentoGrupo");
		Integer anoMesReferencia = (Integer) getParametro("anoMesReferencia");
		String descricaoArquivo = (String) getParametro("descricaoArquivo");

		log.info("............Início Relatório Emitir Contas: " + descricaoArquivo);
		
		List<RelatorioContaModelo3Bean> relatorioBeans = Fachada.getInstancia().obterDadosRelatorioEmitirContasModelo3(faturamentoGrupo,
						anoMesReferencia, colecaoContaHelper);

		Map parametros = new HashMap();
		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);
		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		SistemaParametro sistemaParametro = Fachada.getInstancia().pesquisarParametrosDoSistema();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("imagemConta", sistemaParametro.getImagemConta());


		parametros.put("P_NM_EMPRESA", sistemaParametro.getNomeEmpresa());
		try{
			parametros.put("P_ENDERECO", Fachada.getInstancia().pesquisarEnderecoFormatadoEmpresa());
		}catch(ControladorException e1){
			e1.printStackTrace();
			throw new TarefaException("Erro ao gerar dados para o relatorio");
		}
		parametros.put("P_CNPJ", gcom.util.Util.formatarCnpj(sistemaParametro.getCnpjEmpresa()));
		parametros.put("P_FONE", Util.formatarFone(sistemaParametro.getNumeroTelefone()));
		try{
			parametros.put("P_INSC_EST",
							(String) Fachada.getInstancia().obterValorDoParametroPorCodigo(
											ParametroGeral.P_INSCRICAO_ESTADUAL_EMPRESA.getCodigo()));
			parametros.put("P_JUCOR",
							(String) Fachada.getInstancia().obterValorDoParametroPorCodigo(
											ParametroGeral.P_JUNTA_COMERCIAL_EMPRESA.getCodigo()));
		}catch(NegocioException e1){
			e1.printStackTrace();
			throw new TarefaException("Erro ao gerar dados para o relatorio");
		}

		parametros.put("email", sistemaParametro.getDescricaoEmail());
		
		retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_CONTA_MODELO_3, parametros, ds, tipoFormatoRelatorio);

		try{

			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_CONTA_MODELO_3, idFuncionalidadeIniciada, descricaoArquivo);
		}catch(ControladorException e){

			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		log.info("............Fim Relatório Emitir Contas: " + descricaoArquivo);
		return retorno;
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		int retorno = 0;

		return retorno;
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioContaModelo3", this);
	}

}
