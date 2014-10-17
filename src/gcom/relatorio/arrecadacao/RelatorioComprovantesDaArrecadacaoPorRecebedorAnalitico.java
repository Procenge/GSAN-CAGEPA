
package gcom.relatorio.arrecadacao;

import gcom.arrecadacao.aviso.AvisoBancario;
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
public class RelatorioComprovantesDaArrecadacaoPorRecebedorAnalitico
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioComprovantesDaArrecadacaoPorRecebedorAnalitico(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_COMPROVANTES_DA_ARRECADACAO_POR_RECEBEDOR_ANALITICO);
	}

	@Deprecated
	public RelatorioComprovantesDaArrecadacaoPorRecebedorAnalitico() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		Fachada fachada = Fachada.getInstancia();

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		Integer anoMesReferencia = (Integer) getParametro("anoMesReferencia");

		Collection<Object[]> colecaoRetorno = fachada.pesquisarComprovantesDaArrecadacaoPorRecebedorAnalitico(anoMesReferencia);

		List<RelatorioComprovantesDaArrecadacaoPorRecebedorBean> relatorioBeans = new ArrayList<RelatorioComprovantesDaArrecadacaoPorRecebedorBean>();

		if(Util.isVazioOrNulo(colecaoRetorno)){
			throw new RelatorioVazioException("atencao.relatorio.vazio");
		}else{
			RelatorioComprovantesDaArrecadacaoPorRecebedorBean relatorioBean = null;

			Integer idBanco = null;
			String idBancoStr = "";
			String nomeBanco = "";
			String codigoAgencia = "";
			String nomeAgencia = "";
			String nomeLocalidade = "";
			String codigoContabil = "";
			Date dataLancamento = null;
			String dataLancamentoStr = "";
			Integer idAviso = null;
			String idAvisoStr = "";
			Short indicadorCreditoDebito = null;
			String indicadorCreditoDebitoStr = "";
			BigDecimal valorInformado = null;
			BigDecimal valorCalculado = null;

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

				codigoAgencia = "";

				if(retorno[2] != null){
					codigoAgencia = (String) retorno[2];
				}

				nomeAgencia = "";

				if(retorno[3] != null){
					nomeAgencia = (String) retorno[3];
				}

				nomeLocalidade = "";

				if(retorno[4] != null){
					nomeLocalidade = (String) retorno[4];
				}

				codigoContabil = "";

				if(retorno[5] != null){
					codigoContabil = (String) retorno[5];
				}

				dataLancamentoStr = "";

				if(retorno[6] != null){
					dataLancamento = (Date) retorno[6];
					dataLancamentoStr = Util.formatarData(dataLancamento);
				}

				idAvisoStr = "";

				if(retorno[7] != null){
					idAviso = (Integer) retorno[7];
					idAvisoStr = Integer.toString(idAviso);
				}

				indicadorCreditoDebitoStr = "";
				valorInformado = BigDecimal.ZERO;
				valorCalculado = BigDecimal.ZERO;

				if(retorno[8] != null){
					indicadorCreditoDebito = (Short) retorno[8];

					if(indicadorCreditoDebito.equals(AvisoBancario.INDICADOR_CREDITO)){
						indicadorCreditoDebitoStr = AvisoBancario.CREDITO;

						if(retorno[9] != null){
							valorInformado = (BigDecimal) retorno[9];
						}

						if(retorno[10] != null){
							valorCalculado = (BigDecimal) retorno[10];
						}
					}else{
						indicadorCreditoDebitoStr = AvisoBancario.DEBITO;

						if(retorno[9] != null){
							valorInformado = (BigDecimal) retorno[9];
						}

						if(retorno[10] != null){
							valorCalculado = (BigDecimal) retorno[10];
						}
					}
				}

				relatorioBean.setIdBanco(idBancoStr);
				relatorioBean.setNomeBanco(nomeBanco);
				relatorioBean.setCodigoAgencia(codigoAgencia);
				relatorioBean.setNomeAgencia(nomeAgencia);
				relatorioBean.setNomeLocalidade(nomeLocalidade);
				relatorioBean.setCodigoContabil(codigoContabil);
				relatorioBean.setDataLancamento(dataLancamentoStr);
				relatorioBean.setIdAviso(idAvisoStr);
				relatorioBean.setValorInformado(valorInformado);
				relatorioBean.setValorCalculado(valorCalculado);
				relatorioBean.setIndicadorCreditoDebito(indicadorCreditoDebitoStr);

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

		byte[] retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_COMPROVANTES_DA_ARRECADACAO_POR_RECEBEDOR_ANALITICO,
						parametros, ds, tipoFormatoRelatorio);

		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_COMPROVANTES_DA_ARRECADACAO_POR_RECEBEDOR_ANALITICO,
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

		AgendadorTarefas.agendarTarefa("RelatorioComprovantesDaArrecadacaoPorRecebedorAnalitico", this);
	}

}
