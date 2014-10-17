
package gcom.relatorio.atendimentopublico;

import gcom.atendimentopublico.registroatendimento.SolicitacaoTipo;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
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

public class RelatorioEstatisticoRegistroAtendimento
				extends TarefaRelatorio {

	private static final String STRING_VIRGULA_ESPACO = ", ";

	private static final String STRING_TODOS = "TODOS";

	private static final String JASPER_PARAMETRO_IMAGEM = "imagem";

	public static final String FILTRO_INFORMADO = "HELPER";

	public static final String FORMATO_RELATORIO = "tipoFormatoRelatorio";

	public RelatorioEstatisticoRegistroAtendimento(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_ESTATISTICO_REGISTRO_ATENDIMENTO);
	}

	public RelatorioEstatisticoRegistroAtendimento() {

		super(null, "");
	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		return Fachada.getInstancia().constularQuantidadeRA((RelatorioEstatisticoRegistroAtendimentoHelper) getParametro(FILTRO_INFORMADO));
	}

	@Override
	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();
		RelatorioDataSource relatorioDataSource = carregarDadaSource();
		Map<String, Object> parametros = carragarMapaParametrosRelatorio();
		Integer tipoRelatorio = getTipoRelatorio();
		byte[] retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_ESTATISTICO_REGISTRO_ATENDIMENTO, parametros, relatorioDataSource,
						tipoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_ESTATISTICO_REGISTRO_ATENDIMENTO, idFuncionalidadeIniciada, null);
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

		montarDescricaoFiltro((RelatorioEstatisticoRegistroAtendimentoHelper) getParametro(FILTRO_INFORMADO), parametros);
		return parametros;
	}

	private void montarDescricaoFiltro(RelatorioEstatisticoRegistroAtendimentoHelper parametro, Map<String, Object> parametros){

		// UNIDADE SUPERIOR
		parametros.put("filtroUnidadeSuperior", parametro.getUnidadeSuperior() == null ? STRING_TODOS : parametro.getUnidadeSuperior()
						.getDescricaoComId());

		// UNIDADE ATENDIMENTO
		parametros.put("filtroUnidadeAtendimento", parametro.getUnidadeAtendimento() == null ? STRING_TODOS : parametro
						.getUnidadeAtendimento().getDescricaoComId());

		// USUARIO
		parametros.put("filtroLoginUsuario", parametro.getUsuario() == null ? STRING_TODOS : parametro.getUsuario().getNomeUsuario());

		String situacao = null;

		if(parametro.getSituacao() != null
						&& parametro.getSituacao().equals(RelatorioEstatisticoRegistroAtendimentoHelper.SITUACAO_PENDENTES)){
			situacao = "PENDENTES";
		}else if(parametro.getSituacao() != null
						&& parametro.getSituacao().equals(RelatorioEstatisticoRegistroAtendimentoHelper.SITUACAO_ENCERRADOS)){
			situacao = "ENCERRADOS";
		}else if(parametro.getSituacao() != null
						&& parametro.getSituacao().equals(RelatorioEstatisticoRegistroAtendimentoHelper.SITUACAO_REITERADOS)){
			situacao = "REITERADOS";
		}else{
			situacao = "TODOS";
		}

		parametros.put("FiltroSituacao", situacao);

		// TIPO SOLICITACAO
		StringBuffer buffer = new StringBuffer();
		if(parametro.getTiposSolicitacao() == null){
			buffer.append(STRING_TODOS);
		}else{
			for(SolicitacaoTipo solicitacao : parametro.getTiposSolicitacao()){
				buffer.append(solicitacao.getDescricaoComId());
				buffer.append(STRING_VIRGULA_ESPACO);
			}
			buffer.delete(buffer.length() - 2, buffer.length() - 1);
		}
		parametros.put("filtroSolicitacao", buffer.toString());
		buffer.delete(0, buffer.length());

		// ESPECIFICACAO
		if(parametro.getEspecificacoes() == null){
			buffer.append(STRING_TODOS);
		}else{
			for(SolicitacaoTipoEspecificacao especificacao : parametro.getEspecificacoes()){
				buffer.append(especificacao.getDescricaoComId());
				buffer.append(STRING_VIRGULA_ESPACO);
			}
			buffer.delete(buffer.length() - 2, buffer.length() - 1);
		}
		parametros.put("filtroEspecificacao", buffer.toString());
		buffer.delete(0, buffer.length());

		// UNIDADE ATUAL
		parametros.put("filtroUnidadeAtual", parametro.getUnidadeAtual() == null ? STRING_TODOS : parametro.getUnidadeAtual()
						.getDescricaoComId());

		// PERIODO ATENDIMENTO
		parametros.put("filtroPeriodoAtendimentoInicio", Util.formatarData(parametro.getPeriodoAtendimentoInicial()));
		parametros.put("filtroPeriodoAtendimentoFim", Util.formatarData(parametro.getPeriodoAtendimentoFinal()));
	}

	private RelatorioDataSource carregarDadaSource(){

		List<RelatorioEstatisticoRegistroAtendimentoBean> data = Fachada.getInstancia().consultarDadosEstatisticosRegistroAtendimento(
						(RelatorioEstatisticoRegistroAtendimentoHelper) getParametro(FILTRO_INFORMADO));
		RelatorioDataSource relatorioDataSource = new RelatorioDataSource(data);
		return relatorioDataSource;
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa(this.getClass().getName(), this);
	}

}
