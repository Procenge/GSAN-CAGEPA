/**
 * 
 */

package gcom.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author isilva
 * @date 24/07/2010
 */
public class DateUtil {

	public static SimpleDateFormat FORMATO_DIA_MES_ANO_HORA_MIN_SEG = new SimpleDateFormat("dd/MM/yyyy k:mm:ss");

	public static SimpleDateFormat FORMATO_DIA_MES_ANO = new SimpleDateFormat("dd/MM/yyyy");

	public static SimpleDateFormat FORMATO_HORA_MIN_SEG = new SimpleDateFormat("k:mm:ss");

	public static long calcularDiferencaDeAno(Date dataInicial, Date dataFinal){

		Calendar dataInicialCalendar = Calendar.getInstance();
		dataInicialCalendar.setTime(dataInicial);

		Calendar dataFinalCalendar = Calendar.getInstance();
		dataFinalCalendar.setTime(dataFinal);

		return dataFinalCalendar.get(Calendar.YEAR) - dataInicialCalendar.get(Calendar.YEAR);
	}

	public static long calcularDiferencaDeMes(Date dataInicial, Date dataFinal){

		Calendar dataInicialCalendar = Calendar.getInstance();
		dataInicialCalendar.setTime(dataInicial);

		Calendar dataFinalCalendar = Calendar.getInstance();
		dataFinalCalendar.setTime(dataFinal);

		return dataFinalCalendar.get(Calendar.MONTH) - dataInicialCalendar.get(Calendar.MONTH)
						+ (calcularDiferencaDeAno(dataInicial, dataFinal) * 12);
	}

	public static long calcularDiferencaDeDia(Date dataInicial, Date dataFinal){

		Calendar dataInicialCalendar = Calendar.getInstance();
		dataInicialCalendar.setTime(dataInicial);

		Calendar dataFinalCalendar = Calendar.getInstance();
		dataFinalCalendar.setTime(dataFinal);

		return (dataFinalCalendar.get(Calendar.MILLISECOND) - dataInicialCalendar.get(Calendar.MILLISECOND)) / (1000 * 60 * 60 * 24);
	}

	public static long calcularDiferencaDeHora(Date dataInicial, Date dataFinal){

		Calendar dataInicialCalendar = Calendar.getInstance();
		dataInicialCalendar.setTime(dataInicial);

		Calendar dataFinalCalendar = Calendar.getInstance();
		dataFinalCalendar.setTime(dataFinal);

		return (dataFinalCalendar.get(Calendar.MILLISECOND) - dataInicialCalendar.get(Calendar.MILLISECOND)) / (1000 * 60 * 60);
	}

	public static long calcularDiferencaDeMinuto(Date dataInicial, Date dataFinal){

		Calendar dataInicialCalendar = Calendar.getInstance();
		dataInicialCalendar.setTime(dataInicial);

		Calendar dataFinalCalendar = Calendar.getInstance();
		dataFinalCalendar.setTime(dataFinal);

		return (dataFinalCalendar.get(Calendar.MILLISECOND) - dataInicialCalendar.get(Calendar.MILLISECOND)) / (1000 * 60);
	}

	public static long calcularDiferencaDeSegundo(Date dataInicial, Date dataFinal){

		Calendar dataInicialCalendar = Calendar.getInstance();
		dataInicialCalendar.setTime(dataInicial);

		Calendar dataFinalCalendar = Calendar.getInstance();
		dataFinalCalendar.setTime(dataFinal);

		return (dataFinalCalendar.get(Calendar.MILLISECOND) - dataInicialCalendar.get(Calendar.MILLISECOND)) / (1000);
	}

	public static long calcularDiferencaDeMilisegundo(Date dataInicial, Date dataFinal){

		Calendar dataInicialCalendar = Calendar.getInstance();
		dataInicialCalendar.setTime(dataInicial);

		Calendar dataFinalCalendar = Calendar.getInstance();
		dataFinalCalendar.setTime(dataFinal);

		return dataFinalCalendar.get(Calendar.MILLISECOND) - dataInicialCalendar.get(Calendar.MILLISECOND);
	}

	public static void main(String[] args) throws ParseException{

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		// format.setLenient(false);

		Date data1 = format.parse("29/02/2009");
		Date data2 = format.parse("01/02/2008");

		// System.out.println(calcularDiferencaDeAno(data1, data2));
		System.out.println(calcularDiferencaDeMes(data1, data2));

	}

	public static Object parse(String valorParametro) throws ParseException{

		return FORMATO_DIA_MES_ANO.parse(valorParametro);
	}
}
