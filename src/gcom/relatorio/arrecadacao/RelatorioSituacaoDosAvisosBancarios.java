
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
import gcom.util.ConstantesSistema;
import gcom.util.ControladorException;
import gcom.util.Util;
import gcom.util.agendadortarefas.AgendadorTarefas;

import java.math.BigDecimal;
import java.util.*;

/**
 * Situação dos avisos bancários
 * 
 * @author Hebert Falcão
 * @since 04/10/2013
 */
public class RelatorioSituacaoDosAvisosBancarios
				extends TarefaRelatorio {

	private static final long serialVersionUID = 1L;

	public RelatorioSituacaoDosAvisosBancarios(Usuario usuario) {

		super(usuario, ConstantesRelatorios.RELATORIO_SITUACAO_DOS_AVISOS_BANCARIOS);
	}

	@Deprecated
	public RelatorioSituacaoDosAvisosBancarios() {

		super(null, "");
	}

	public Object executar() throws TarefaException{

		Integer idFuncionalidadeIniciada = this.getIdFuncionalidadeIniciada();

		Fachada fachada = Fachada.getInstancia();

		int tipoFormatoRelatorio = (Integer) getParametro("tipoFormatoRelatorio");
		Integer anoMesReferencia = (Integer) getParametro("anoMesReferencia");

		Collection<Object[]> colecaoRetorno = fachada.pesquisarSituacaoDosAvisosBancarios(anoMesReferencia);

		List<RelatorioSituacaoDosAvisosBancariosBean> relatorioBeans = new ArrayList<RelatorioSituacaoDosAvisosBancariosBean>();

		if(Util.isVazioOrNulo(colecaoRetorno)){
			throw new RelatorioVazioException("atencao.relatorio.vazio");
		}else{
			RelatorioSituacaoDosAvisosBancariosBean relatorioBean = null;

			Integer idBanco = null;
			String idBancoStr = "";
			String nomeBanco = "";
			String codigoAgencia = "";
			String nomeAgencia = "";
			Date dataLancamento = null;
			String dataLancamentoStr = "";
			Integer idAviso = null;
			String idAvisoStr = "";
			BigDecimal valorAviso = null;
			BigDecimal valorInformadoCredito = null;
			BigDecimal valorCalculadoCredito = null;
			BigDecimal valorInformadoDebito = null;
			BigDecimal valorCalculadoDebito = null;
			String situacao = "";
			Short indicadorCreditoDebito = null;
			BigDecimal valorDiferenca = null;

			Integer idBancoAnterior = null;
			String codigoAgenciaAnterior = null;

			Integer qtdAvisosClassificados = 0;
			Integer qtdAvisosNaoClassificados = 0;

			Integer qtdAvisosClassificadosBanco = 0;
			Integer qtdAvisosNaoClassificadosBanco = 0;

			Integer qtdAvisosClassificadosAgencia = 0;
			Integer qtdAvisosNaoClassificadosAgencia = 0;

			for(Object[] retorno : colecaoRetorno){
				relatorioBean = new RelatorioSituacaoDosAvisosBancariosBean();

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

				dataLancamentoStr = "";

				if(retorno[4] != null){
					dataLancamento = (Date) retorno[4];
					dataLancamentoStr = Util.formatarData(dataLancamento);
				}

				idAvisoStr = "";

				if(retorno[5] != null){
					idAviso = (Integer) retorno[5];
					idAvisoStr = Integer.toString(idAviso);
				}

				valorInformadoCredito = BigDecimal.ZERO;

				if(retorno[6] != null){
					valorInformadoCredito = (BigDecimal) retorno[6];
				}

				valorCalculadoCredito = BigDecimal.ZERO;

				if(retorno[7] != null){
					valorCalculadoCredito = (BigDecimal) retorno[7];
				}

				valorInformadoDebito = BigDecimal.ZERO;

				if(retorno[8] != null){
					valorInformadoDebito = (BigDecimal) retorno[8];
				}

				valorCalculadoDebito = BigDecimal.ZERO;

				if(retorno[9] != null){
					valorCalculadoDebito = (BigDecimal) retorno[9];
				}

				if(retorno[10] != null){
					indicadorCreditoDebito = (Short) retorno[10];

					if(indicadorCreditoDebito.equals(AvisoBancario.INDICADOR_CREDITO)){
						valorDiferenca = valorInformadoCredito.subtract(valorCalculadoCredito);
					}else{
						valorDiferenca = valorInformadoDebito.subtract(valorCalculadoDebito);
					}

					if(idBancoAnterior == null || (idBancoAnterior != null && idBancoAnterior.intValue() != idBanco.intValue())){
						qtdAvisosClassificadosBanco = 0;
						qtdAvisosNaoClassificadosBanco = 0;
						qtdAvisosClassificadosAgencia = 0;
						qtdAvisosNaoClassificadosAgencia = 0;

						codigoAgenciaAnterior = null;
					}

					if(valorDiferenca.compareTo(BigDecimal.ZERO) != 0){
						situacao = ConstantesSistema.ABERTO;

						if(codigoAgenciaAnterior == null || (codigoAgenciaAnterior != null && !codigoAgenciaAnterior.equals(codigoAgencia))){
							qtdAvisosNaoClassificadosAgencia = 0;
						}

						qtdAvisosNaoClassificados = qtdAvisosNaoClassificados + 1;
						qtdAvisosNaoClassificadosBanco = qtdAvisosNaoClassificadosBanco + 1;
						qtdAvisosNaoClassificadosAgencia = qtdAvisosNaoClassificadosAgencia + 1;
					}else{
						situacao = ConstantesSistema.FECHADO;

						if(codigoAgenciaAnterior == null || (codigoAgenciaAnterior != null && !codigoAgenciaAnterior.equals(codigoAgencia))){
							qtdAvisosClassificadosAgencia = 0;
						}

						qtdAvisosClassificados = qtdAvisosClassificados + 1;
						qtdAvisosClassificadosBanco = qtdAvisosClassificadosBanco + 1;
						qtdAvisosClassificadosAgencia = qtdAvisosClassificadosAgencia + 1;
					}

					idBancoAnterior = idBanco;
					codigoAgenciaAnterior = codigoAgencia;
				}

				valorAviso = BigDecimal.ZERO;

				if(retorno[11] != null){
					valorAviso = (BigDecimal) retorno[11];
				}

				relatorioBean.setIdBanco(idBancoStr);
				relatorioBean.setNomeBanco(nomeBanco);
				relatorioBean.setCodigoAgencia(codigoAgencia);
				relatorioBean.setNomeAgencia(nomeAgencia);
				relatorioBean.setDataLancamento(dataLancamentoStr);
				relatorioBean.setIdAviso(idAvisoStr);
				relatorioBean.setValorAviso(valorAviso);
				relatorioBean.setValorInformadoCredito(valorInformadoCredito);
				relatorioBean.setValorCalculadoCredito(valorCalculadoCredito);
				relatorioBean.setValorInformadoDebito(valorInformadoDebito);
				relatorioBean.setValorCalculadoDebito(valorCalculadoDebito);
				relatorioBean.setSituacao(situacao);
				relatorioBean.setQtdAvisosClassificados(qtdAvisosClassificados);
				relatorioBean.setQtdAvisosNaoClassificados(qtdAvisosNaoClassificados);
				relatorioBean.setQtdAvisosClassificadosBanco(qtdAvisosClassificadosBanco);
				relatorioBean.setQtdAvisosNaoClassificadosBanco(qtdAvisosNaoClassificadosBanco);
				relatorioBean.setQtdAvisosClassificadosAgencia(qtdAvisosClassificadosAgencia);
				relatorioBean.setQtdAvisosNaoClassificadosAgencia(qtdAvisosNaoClassificadosAgencia);

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

		byte[] retorno = this.gerarRelatorio(ConstantesRelatorios.RELATORIO_SITUACAO_DOS_AVISOS_BANCARIOS, parametros, ds,
						tipoFormatoRelatorio);

		try{
			persistirRelatorioConcluido(retorno, Relatorio.RELATORIO_SITUACAO_DOS_AVISOS_BANCARIOS, idFuncionalidadeIniciada, null);
		}catch(ControladorException e){
			e.printStackTrace();
			throw new TarefaException("Erro ao gravar relatório no sistema", e);
		}

		return retorno;
	}

	public int calcularTotalRegistrosRelatorio(){

		Integer anoMesReferencia = (Integer) getParametro("anoMesReferencia");

		Fachada fachada = Fachada.getInstancia();

		return fachada.pesquisarSituacaoDosAvisosBancariosCount(anoMesReferencia);
	}

	public void agendarTarefaBatch(){

		AgendadorTarefas.agendarTarefa("RelatorioSituacaoDosAvisosBancarios", this);
	}

}
