
package gcom.relatorio.cobranca;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelatorioRemuneracaoCobrancaAdministrativa
				extends TarefaRelatorio {

	private static final String JASPER_PARAMETRO_FILTRO_INFORMADO = "filtroInformado";

	private static final String JASPER_PARAMETRO_IMAGEM = "imagem";

	public static final String FILTRO_INFORMADO = "HELPER";

	public static final String FORMATO_RELATORIO = "tipoFormatoRelatorio";

	public RelatorioRemuneracaoCobrancaAdministrativa(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_REMUNERACAO_COBRANCA_ADMINISTRATIVA);
	}

	public RelatorioRemuneracaoCobrancaAdministrativa() {

		super(null, "");
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		return Fachada.getInstancia().consultarQuantidadeRemuneracaoCobrancaAdministrativa(
						(RelatorioRemuneracaoCobrancaAdministrativaHelper) getParametro(FILTRO_INFORMADO));

	}

	@Override
	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		RelatorioDataSource relatorioDataSource = carregarDadaSource();
		Map<String, Object> parametros = carragarMapaParametrosRelatorio();
		Integer tipoRelatorio = getTipoRelatorio();
		byte[] retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_REMUNERACAO_COBRANCA_ADMINISTRATIVA, parametros,
						relatorioDataSource, tipoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_REMUNERACAO_COBRANCA_ADMINISTRATIVA, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------

		return retorno;

	}

	private Integer getTipoRelatorio(){

		Integer tipoRelatorio = (Integer) getParametro(FORMATO_RELATORIO);
		tipoRelatorio = tipoRelatorio == null ? TarefaRelatorio.TIPO_PDF : tipoRelatorio;
		return tipoRelatorio;
	}

	private Map<String, Object> carragarMapaParametrosRelatorio(){

		Map<String, Object> parametros = new HashMap<String, Object>();
		SistemaParametro sistemaParametro = Fachada.getInstancia().pesquisarParametrosDoSistema();
		parametros.put(JASPER_PARAMETRO_IMAGEM, sistemaParametro.getImagemRelatorio());
		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		// Mapeia os parâmetros

		RelatorioRemuneracaoCobrancaAdministrativaHelper helper = (RelatorioRemuneracaoCobrancaAdministrativaHelper) getParametro(FILTRO_INFORMADO);
		parametros.put("filtroPeriodoPagamentoInicio", Util.formatarData(helper.getPeriodoPagamentoInicial()));
		parametros.put("filtroPeriodoPagamentoFim", Util.formatarData(helper.getPeriodoPagamentoFinal()));

		parametros.put(JASPER_PARAMETRO_FILTRO_INFORMADO,
						montarDescricaoFiltro((RelatorioRemuneracaoCobrancaAdministrativaHelper) getParametro(FILTRO_INFORMADO)));
		return parametros;
	}

	private String montarDescricaoFiltro(RelatorioRemuneracaoCobrancaAdministrativaHelper parametro){

		StringBuffer buffer = new StringBuffer();
		// TIPO SOLICITACAO
		// buffer.append("\n");
		// buffer.append("Tipo de Solicitação: ");
		// if(parametro.getTiposSolicitacao() == null){
		// buffer.append("TODOS");
		// }else{
		// for(SolicitacaoTipo solicitacao : parametro.getTiposSolicitacao()){
		// buffer.append(solicitacao.getDescricaoComId());
		// buffer.append(", ");
		// }
		// buffer.delete(buffer.length() - 2, buffer.length() - 1);
		// }
		//
		// // ESPECIFICACAO
		// buffer.append("\n");
		// buffer.append("Especificação: ");
		// if(parametro.getEspecificacoes() == null){
		// buffer.append("TODOS");
		// }else{
		// for(SolicitacaoTipoEspecificacao especificacao : parametro.getEspecificacoes()){
		// buffer.append(especificacao.getDescricaoComId());
		// buffer.append(", ");
		// }
		// buffer.delete(buffer.length() - 2, buffer.length() - 1);
		// }
		//
		// // UNIDADE ATUAL
		// buffer.append("\n");
		// buffer.append("Unidade Atual: ");
		// if(parametro.getUnidadeAtual() == null){
		// buffer.append("TODOS");
		// }else{
		// buffer.append(parametro.getUnidadeAtual().getDescricaoComId());
		// }
		//
		// // PERIODO ATENDIMENTO
		// buffer.append("\n");
		// buffer.append("Pedíodo de Atendimento: ");
		// buffer.append(Util.formatarData(parametro.getPeriodoAtendimentoInicial()));
		// buffer.append(" à ");
		// buffer.append(Util.formatarData(parametro.getPeriodoAtendimentoFinal()));
		return buffer.toString();
	}

	private RelatorioDataSource carregarDadaSource(){

		List<RelatorioRemuneracaoCobrancaAdministrativaBean> data = Fachada.getInstancia().consultarDadosRemuneracaoCobrancaAdministrativa(
						(RelatorioRemuneracaoCobrancaAdministrativaHelper) getParametro(FILTRO_INFORMADO));
		RelatorioDataSource relatorioDataSource = new RelatorioDataSource(data);
		return relatorioDataSource;
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa(this.getClass().getName(), this);
	}

}
