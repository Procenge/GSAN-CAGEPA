
package gcom.relatorio.arrecadacao;

import gcom.batch.Relatorio;
import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.fachada.Fachada;
import gcom.relatorio.ConstantesRelatorios;
import gcom.relatorio.RelatorioDataSource;
import gcom.relatorio.RelatorioVazioException;
import gcom.seguranca.acesso.usuario.Usuario;
import gcom.tarefa.TarefaException;
import gcom.tarefa.TarefaRelatorio;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.math.BigDecimal;
import java.util.*;

/**
 * Comprovantes da Arrecadação por Recebedor
 * 
 * @author Hebert Falcão
 * @since 28/09/2013
 */
public class RelatorioComprovantesDaArrecadacaoPorRecebedorSintetico
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioComprovantesDaArrecadacaoPorRecebedorSintetico(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_COMPROVANTES_DA_ARRECADACAO_POR_RECEBEDOR_SINTETICO);
	}

	@Deprecated
	public RelatorioComprovantesDaArrecadacaoPorRecebedorSintetico() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		Fachada fachada = Fachada.getInstancia();

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		Integer anoMesReferencia = (Integer) getParametro("anoMesReferencia");

		Collection<Object[]> colecaoRetorno = fachada.pesquisarComprovantesDaArrecadacaoPorRecebedorSintetico(anoMesReferencia);

		List<RelatorioComprovantesDaArrecadacaoPorRecebedorBean> relatorioBeans = new ArrayList<RelatorioComprovantesDaArrecadacaoPorRecebedorBean>();

		if(Util.isVazioOrNulo(colecaoRetorno)){
			throw new RelatorioVazioException("atencao.relatorio.vazio");
		}else{
			RelatorioComprovantesDaArrecadacaoPorRecebedorBean relatorioBean = null;

			Integer idBanco = null;
			String idBancoStr = "";
			String nomeBanco = "";
			BigDecimal valorInformadoCredito = null;
			BigDecimal valorCalculadoCredito = null;
			BigDecimal valorInformadoDebito = null;
			BigDecimal valorCalculadoDebito = null;

			for(Object[] retorno : colecaoRetorno){
				relatorioBean = new RelatorioComprovantesDaArrecadacaoPorRecebedorBean();

				idBancoStr = "";

				if(retorno[0] != null){
					idBanco = (Integer) retorno[0];
					idBancoStr = Integer.toString(idBanco);
				}

				nomeBanco = "";

				if(retorno[1] != null){
					nomeBanco = (String) retorno[1];
				}

				valorInformadoCredito = BigDecimal.ZERO;

				if(retorno[2] != null){
					valorInformadoCredito = (BigDecimal) retorno[2];
				}

				valorCalculadoCredito = BigDecimal.ZERO;

				if(retorno[3] != null){
					valorCalculadoCredito = (BigDecimal) retorno[3];
				}

				valorInformadoDebito = BigDecimal.ZERO;

				if(retorno[4] != null){
					valorInformadoDebito = (BigDecimal) retorno[4];
				}

				valorCalculadoDebito = BigDecimal.ZERO;

				if(retorno[5] != null){
					valorCalculadoDebito = (BigDecimal) retorno[5];
				}

				relatorioBean.setIdBanco(idBancoStr);
				relatorioBean.setNomeBanco(nomeBanco);
				relatorioBean.setValorInformadoCredito(valorInformadoCredito);
				relatorioBean.setValorCalculadoCredito(valorCalculadoCredito);
				relatorioBean.setValorInformadoDebito(valorInformadoDebito);
				relatorioBean.setValorCalculadoDebito(valorCalculadoDebito);

				relatorioBeans.add(relatorioBean);
			}
		}

		SistemaParametro sistemaParametro = fachada.pesquisarParametrosDoSistema();

		String mesAnoReferencia = Util.formatarMesAnoReferencia(anoMesReferencia);

		Map<String, String> parametros = new HashMap<String, String>();
		parametros.put("imagem", sistemaParametro.getImagemRelatorio());
		parametros.put("mesAnoReferencia", mesAnoReferencia);
		parametros.put("P_NM_ESTADO", sistemaParametro.getNomeEstado());

		RelatorioDataSource ds = new RelatorioDataSource(relatorioBeans);

		byte[] retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_COMPROVANTES_DA_ARRECADACAO_POR_RECEBEDOR_SINTETICO,
						parametros, ds, tipoFormatoRelatorio);

		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_COMPROVANTES_DA_ARRECADACAO_POR_RECEBEDOR_SINTETICO,
							idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		return retorno;
	}

	public int calcularTotalRegistrosRelatorio(){

		Integer anoMesReferencia = (Integer) getParametro("anoMesReferencia");

		Fachada fachada = Fachada.getInstancia();

		return fachada.pesquisarComprovantesDaArrecadacaoPorRecebedorCount(anoMesReferencia);
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioComprovantesDaArrecadacaoPorRecebedorSintetico", this);
	}

}
