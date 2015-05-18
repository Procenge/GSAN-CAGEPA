
package gcom.relatorio.faturamento;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RelatorioTotalContasEmitidasLocalidade
				extends TarefaRelatorio {

	public RelatorioTotalContasEmitidasLocalidade(Usuario usuario, String nomeRelatorio) {

		super(usuario, nomeRelatorio);

	}

	public RelatorioTotalContasEmitidasLocalidade() {

		super(null, ConstantesRelatorios.RELATORIO_TOTAL_CONTAS_EMITIDAS_LOCALIDADE);

	}

	@Override
	public int calcularTotalRegistrosRelatorio(){

		Integer referencia = (Integer) getParametro("referencia");
		Long quantidade = Fachada.getInstancia().gerarQuantidadeRelatorioTotalContasEmitidasLocalidade(referencia);

		return quantidade.intValue();
	}

	@Override
	public Object executar() throws TarefaException{

		Integer referencia = (Integer) getParametro("referencia");

		List<RelatorioTotalContasEmitidasLocalidadeBean> lista = new ArrayList<RelatorioTotalContasEmitidasLocalidadeBean>();

		List<Object[]> listaObjetos = Fachada.getInstancia().gerarRelatorioTotalContasEmitidasLocalidade(referencia);

		BigDecimal valorTotalContas = BigDecimal.ZERO;
		BigDecimal valorTotalParticular = BigDecimal.ZERO;
		BigDecimal valorTotalPublico = BigDecimal.ZERO;

		BigDecimal valorTotalGeral = BigDecimal.ZERO;

		for(Object obj : listaObjetos){

			Object[] objArray = (Object[]) obj;

			RelatorioTotalContasEmitidasLocalidadeBean relatorio = new RelatorioTotalContasEmitidasLocalidadeBean();

			relatorio.setLocal(objArray[0].toString());

			relatorio.setSetor(objArray[1].toString());

			relatorio.setQuantidadeContas(objArray[2].toString());

			NumberFormat number = DecimalFormat.getNumberInstance();
			number.setMaximumFractionDigits(2);

			String valorTotal = number.format(Double.valueOf(objArray[3].toString()));

			valorTotalContas = valorTotalContas.add(new BigDecimal(objArray[3].toString()));

			valorTotalParticular = valorTotalParticular.add(new BigDecimal(objArray[4].toString()));

			valorTotalPublico = valorTotalPublico.add(new BigDecimal(objArray[5].toString()));

			relatorio.setValorContas(valorTotal);

			String valorParticular = number.format(Double.valueOf(objArray[4].toString()));

			String valorPublico = number.format(Double.valueOf(objArray[5].toString()));

			relatorio.setValorParticular(valorParticular);

			relatorio.setValorPublico(valorPublico);

			BigDecimal geral = new BigDecimal(objArray[3].toString());
			geral = geral.add(new BigDecimal(objArray[4].toString()));

			geral = geral.add(new BigDecimal(objArray[5].toString()));

			String valorGeral = number.format(geral.doubleValue());

			relatorio.setValorGeral(valorGeral);

			lista.add(relatorio);

		}

		RelatorioDataSource ds = new RelatorioDataSource(lista);

		SistemaParametro sistemaParametro = Fachada.getInstancia().pesquisarParametrosDoSistema();

		// Parâmetros do relatório
		Map parametros = new HashMap();

		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("referencia", getParametro("referenciaRelatorio").toString());

		NumberFormat number = DecimalFormat.getNumberInstance();
		number.setMaximumFractionDigits(2);
		String valorTotalCobradoContas = number.format(valorTotalContas.doubleValue());

		parametros.put("valorTotalContas", valorTotalCobradoContas);

		String valorTotaisPublico = number.format(valorTotalPublico.doubleValue());
		String valorTotaisParticula = number.format(valorTotalParticular.doubleValue());

		valorTotalGeral = valorTotalGeral.add(valorTotalContas);

		valorTotalGeral = valorTotalGeral.add(valorTotalPublico);

		valorTotalGeral = valorTotalGeral.add(valorTotalParticular);

		parametros.put("valorTotalPublico", valorTotaisPublico);
		parametros.put("valorTotalParticular", valorTotaisParticula);

		String valorTotaisGerais = number.format(valorTotalGeral.doubleValue());

		parametros.put("valorTotalGeral", valorTotaisGerais);

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");

		// exporta o relatório em pdf e retorna o array de bytes
		byte[] retorno = gerarRelatorio(ConstantesRelatorios.RELATORIO_TOTAL_CONTAS_EMITIDAS_LOCALIDADE, parametros, ds,
						tipoFormatoRelatorio);

		// ------------------------------------
		// Grava o relatório no sistema
		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_TOTAL_CONTAS_EMITIDAS_LOCALIDADE, getIdFuncionalidadeIniciada(), null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}
		// ------------------------------------

		// retorna o relatório gerado*/
		return retorno;
	}

	@Override
	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioTotalContasEmitidasLocalidade", this);
	}

}
