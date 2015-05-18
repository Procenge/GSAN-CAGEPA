/**
 * 
 */

package br.com.procenge.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

import br.com.procenge.comum.exception.PCGException;

/**
 * @author gmatos
 */
public class Util {

	/**
	 * Método responsável por converter um string para uma data.
	 * 
	 * @autor gilberto
	 * @param rotulo
	 *            O rorulo do campo
	 * @param strData
	 *            O valor
	 * @param formato
	 *            O formato da data
	 * @return Uma data
	 * @throws PCGException
	 *             Caso ocorra algum erro de convsersão ou formato
	 */
	public static Date converterCampoStringParaData(String rotulo, String strData, String formato) throws PCGException{

		Date data = null;
		SimpleDateFormat formatador = null;

		try{
			formatador = new SimpleDateFormat(formato);
			data = formatador.parse(strData);
			if(!formatador.format(data).equals(strData)){
				throw new PCGException(Constantes.RESOURCE_BUNDLE, Constantes.ERRO_DADOS_INVALIDOS, MensagemUtil.obterMensagem(
								Constantes.RESOURCE_BUNDLE, rotulo));
			}
		}catch(IllegalArgumentException e){
			throw new PCGException(Constantes.RESOURCE_BUNDLE, Constantes.ERRO_FORMATO_INVALIDO, formato);
		}catch(ParseException e){
			throw new PCGException(Constantes.RESOURCE_BUNDLE, Constantes.ERRO_DADOS_INVALIDOS, MensagemUtil.obterMensagem(
							Constantes.RESOURCE_BUNDLE, rotulo));
		}

		return data;
	}

	/**
	 * Método responsável por converter um string para um valor.
	 * 
	 * @autor gilberto
	 * @param rotulo
	 *            O rorulo do campo
	 * @param strValor
	 *            O valor
	 * @return Uma valor
	 * @throws PCGException
	 *             Caso ocorra algum erro de conversão ou formato
	 */
	public static BigDecimal converterCampoStringParaValor(String rotulo, String strValor) throws PCGException{

		BigDecimal valor = null;
		DecimalFormat df = new DecimalFormat("#,###.##", new DecimalFormatSymbols(new Locale("pt", "BR")));
		df.setParseBigDecimal(true);

		try{
			valor = (BigDecimal) df.parse(strValor);
		}catch(ParseException e){
			throw new PCGException(Constantes.RESOURCE_BUNDLE, Constantes.ERRO_DADOS_INVALIDOS, MensagemUtil.obterMensagem(
							Constantes.RESOURCE_BUNDLE, rotulo));
		}

		return valor;
	}

	/**
	 * Método responsável por converter um string para um valor.
	 * 
	 * @autor gilberto
	 * @param rotulo
	 *            O rorulo do campo
	 * @param valor
	 *            O valor
	 * @return Uma valor
	 * @throws PCGException
	 *             Caso ocorra algum erro de conversão ou formato
	 */
	public static String converterCampoValorParaString(String rotulo, BigDecimal valor) throws PCGException{

		DecimalFormat df = new DecimalFormat("#,###.##", new DecimalFormatSymbols(new Locale("pt", "BR")));
		df.setParseBigDecimal(true);
		return df.format(valor);
	}

	public static <T> Comparator<T> getComparator(){

		return new Comparator<T>() {

			public int compare(T o1, T o2){

				if(o1 == null && o2 == null){
					return 0;
				}

				if(o1 == null){
					return -1;
				}

				if(o2 == null){
					return 1;
				}

				return ((Comparable) o1).compareTo(((Comparable) o2));
			}

		};
	}

}
