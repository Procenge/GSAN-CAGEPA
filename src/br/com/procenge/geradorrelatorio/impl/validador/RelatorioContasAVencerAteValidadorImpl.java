
package br.com.procenge.geradorrelatorio.impl.validador;

import gcom.cadastro.sistemaparametro.SistemaParametro;
import gcom.faturamento.ControladorFaturamentoLocal;
import gcom.faturamento.ControladorFaturamentoLocalHome;
import gcom.gui.ActionServletException;
import gcom.util.ConstantesJNDI;
import gcom.util.ControladorException;
import gcom.util.ControladorUtilLocal;
import gcom.util.ControladorUtilLocalHome;
import gcom.util.ServiceLocator;
import gcom.util.ServiceLocatorException;
import gcom.util.SistemaException;
import gcom.util.Util;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.CreateException;

import br.com.procenge.geradorrelatorio.api.ValidadorParametros;

public class RelatorioContasAVencerAteValidadorImpl
				implements ValidadorParametros {


	private static final String PARAMETRO_VALOR = "p_nValor";

	private static final String PARAMETRO_REF = "p_anoreferencia";




	public Map<String, String> validar(Map<String, Object> parametros){

		Map<String, String> erros = new HashMap<String, String>();
		
		String pReferenciaAnoMes = String.valueOf(parametros.get(PARAMETRO_REF));
		
		Collection<Integer> valorContas = null;

		BigDecimal valorConta = null;

		if(!Util.isVazioOuBranco(pReferenciaAnoMes)){
			try{
				valorContas = this.getControladorFaturamento().obterValorTotalContasSelicionadas(pReferenciaAnoMes);
				if(!Util.isVazioOrNulo(valorContas)){
					valorConta = (BigDecimal) Util.retonarObjetoDeColecao(valorContas);
				}

				if(Util.isVazioOuBranco(valorConta)){
					throw new ActionServletException("atencao.tabela.sem_dados.selecao");
				}

			}catch(NumberFormatException e){
				e.printStackTrace();
			}catch(ControladorException e){
				e.printStackTrace();
			}
		}

		

		String valorMinimo = (String) parametros.get(PARAMETRO_VALOR);
		if(!Util.isVazioOuBranco(valorMinimo)){
			BigDecimal valorMiniomoBigDecimal = Util.formatarMoedaRealparaBigDecimal(valorMinimo);
			if(valorConta.compareTo(valorMiniomoBigDecimal) == -1){
				throw new ActionServletException("atencao.valor.contas.inferir");
			}
		}

		Integer referenciaFaturamento = (Integer) parametros.get(PARAMETRO_REF);
		if(referenciaFaturamento == null || referenciaFaturamento <= 0){
			erros.put(PARAMETRO_REF, "Referência do Faturamento");
		}else{
			SistemaParametro sistemaParametros;
			try{
				sistemaParametros = getControladorUtil().pesquisarParametrosDoSistema();
				if(sistemaParametros == null || sistemaParametros.getAnoMesFaturamento() == null){
					throw new ControladorException("atencao.naocadastrado", null, "Ano/Mês Faturamento Parametros Sistema");
				}else{
					if(referenciaFaturamento > sistemaParametros.getAnoMesFaturamento()){
						String anoMesFormatado = Util.formatarAnoMesSemBarraParaMesAnoComBarra(sistemaParametros.getAnoMesFaturamento());
						throw new ActionServletException("atencao.data_inferior.ano_mes.faturamento", anoMesFormatado);
					}
				}
			}catch(ControladorException e){
				e.printStackTrace();
			}
			

		}


		return erros;
	}

	public Map<String, Object> converter(Map<String, Object> parametros){

		Map<String, Object> parametrosConvertidos = new HashMap<String, Object>();
		this.converterDadosAnoMes(parametros);
		this.converterDadosValor(parametros);
		parametrosConvertidos.putAll(parametros);

		return parametrosConvertidos;
	}


	private void converterDadosAnoMes(Map<String, Object> parametros){

		String pReferenciaAnoMes = String.valueOf(parametros.get(PARAMETRO_REF));

		if(!Util.isVazioOuBranco(pReferenciaAnoMes)){
			pReferenciaAnoMes = Util.formatarMesAnoParaAnoMesSemBarra(pReferenciaAnoMes);

			parametros.put(PARAMETRO_REF, Integer.valueOf(pReferenciaAnoMes));
		}
	}


	private void converterDadosValor(Map<String, Object> parametros){

		String pValorDebito = String.valueOf(parametros.get(PARAMETRO_VALOR));
		BigDecimal pValorDebitoBigDecimal = null;
		if(!Util.isVazioOuBranco(pValorDebito)){
			pValorDebitoBigDecimal = Util.formatarMoedaRealparaBigDecimal(pValorDebito);
		}else{
			pValorDebitoBigDecimal = BigDecimal.ZERO;
		}

		parametros.put(PARAMETRO_VALOR, pValorDebitoBigDecimal.toString());
	}

	/**
	 * Retorna o valor de controladorFaturamento
	 * 
	 * @return O valor de controladorFaturamento
	 */
	protected ControladorFaturamentoLocal getControladorFaturamento(){

		ControladorFaturamentoLocalHome localHome = null;
		ControladorFaturamentoLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorFaturamentoLocalHome) locator.getLocalHomePorEmpresa(ConstantesJNDI.CONTROLADOR_FATURAMENTO_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas à
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}
	}

	/**
	 * Retorna o valor de controladorUtil
	 * 
	 * @return O valor de controladorUtil
	 */
	protected ControladorUtilLocal getControladorUtil(){

		ControladorUtilLocalHome localHome = null;
		ControladorUtilLocal local = null;

		// pega a instância do ServiceLocator.

		ServiceLocator locator = null;

		try{
			locator = ServiceLocator.getInstancia();

			localHome = (ControladorUtilLocalHome) locator.getLocalHome(ConstantesJNDI.CONTROLADOR_UTIL_SEJB);
			// guarda a referencia de um objeto capaz de fazer chamadas à
			// objetos remotamente
			local = localHome.create();

			return local;
		}catch(CreateException e){
			throw new SistemaException(e);
		}catch(ServiceLocatorException e){
			throw new SistemaException(e);
		}

	}

}
