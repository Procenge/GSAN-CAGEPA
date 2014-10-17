/*
 * Copyright (C) 2007-2007 the GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 *
 * This file is part of GSAN, an integrated service management system for Sanitation
 *
 * GSAN is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License.
 *
 * GSAN is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place – Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN – Sistema Integrado de Gestão de Serviços de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araújo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cláudio de Andrade Lira
 * Denys Guimarães Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabíola Gomes de Araújo
 * Flávio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Júnior
 * Homero Sampaio Cavalcanti
 * Ivan Sérgio da Silva Júnior
 * José Edmar de Siqueira
 * José Thiago Tenório Lopes
 * Kássia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Márcio Roberto Batista da Silva
 * Maria de Fátima Sampaio Leite
 * Micaela Maria Coelho de Araújo
 * Nelson Mendonça de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrêa Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araújo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sávio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos de Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; versão 2 da
 * Licença.
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. Consulte a Licença Pública Geral GNU para obter mais
 * detalhes.
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.util;

import gcom.cadastro.EnvioEmail;
import gcom.cadastro.imovel.Categoria;
import gcom.cadastro.imovel.Subcategoria;
import gcom.cobranca.MunicipioFeriado;
import gcom.cobranca.NacionalFeriado;
import gcom.gui.ActionServletException;
import gcom.util.email.ServicosEmail;
import gcom.util.parametrizacao.arrecadacao.ParametroArrecadacao;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.channels.FileChannel;
import java.sql.Timestamp;
import java.text.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.GenericValidator;
import org.hibernate.Query;

import br.com.procenge.comum.exception.NegocioException;

public class Util {

	private static Map<Character, String> mapValorBinario;

	static{

		mapValorBinario = new HashMap<Character, String>();
		mapValorBinario.put('0', "00110");
		mapValorBinario.put('1', "10001");
		mapValorBinario.put('2', "01001");
		mapValorBinario.put('3', "11000");
		mapValorBinario.put('4', "00101");
		mapValorBinario.put('5', "10100");
		mapValorBinario.put('6', "01100");
		mapValorBinario.put('7', "00011");
		mapValorBinario.put('8', "10010");
		mapValorBinario.put('9', "01010");

	}

	public static int getMes(Date date){

		Calendar dataCalendar = GregorianCalendar.getInstance();
		dataCalendar.setTime(date);

		return (dataCalendar.get(Calendar.MONTH) + 1);
	}

	public static int getAno(Date date){

		Calendar dataCalendar = GregorianCalendar.getInstance();
		dataCalendar.setTime(date);

		return dataCalendar.get(Calendar.YEAR);
	}

	public static int getDiaMes(Date date){

		Calendar dataCalendar = GregorianCalendar.getInstance();
		dataCalendar.setTime(date);

		return dataCalendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Método que retorn o ano mes como Integer
	 * 
	 * @author thiago toscano
	 * @date 05/04/2006
	 * @param date
	 * @return
	 */

	public static int getAnoMesComoInt(Date date){

		return getAnoMesComoInteger(date).intValue();
	}

	/**
	 * Método que retorn o ano mes como Integer
	 * 
	 * @author thiago toscano
	 * @date 05/04/2006
	 * @param date
	 * @return
	 */

	public static Integer getAnoMesComoInteger(Date date){

		int mes = getMes(date);
		String sMes = mes + "";
		if(sMes.length() == 1){
			sMes = "0" + sMes;
		}
		int ano = getAno(date);

		return Integer.valueOf(ano + "" + sMes);
	}

	/*
	 * Formatação para numeração de RA manual Autor: Raphael Rossiter Data:
	 * 07/11/2006
	 */
	public static String formatarNumeracaoRAManual(Integer numeracao){

		String retorno = null;

		if(numeracao != null){

			String sequencialString = String.valueOf(numeracao);
			int digitoModulo11 = Util.obterDigitoVerificadorModulo11(Long.parseLong(sequencialString));

			if(sequencialString.length() < 9){

				int complementoZeros = 9 - sequencialString.length();
				String sequencialStringFinal = sequencialString;

				for(int y = 0; y < complementoZeros; y++){
					sequencialStringFinal = "0" + sequencialStringFinal;
				}

				retorno = sequencialStringFinal.trim() + "-" + digitoModulo11;
			}else{

				retorno = sequencialString.trim() + "-" + digitoModulo11;
			}
		}

		return retorno;
	}

	/*
	 * Obter apenas o valor da numeração Autor: Raphael Rossiter Data:
	 * 07/11/2006
	 */
	public static Integer obterNumeracaoRAManual(String numeracao){

		Integer retorno = null;

		if(numeracao != null){

			String[] arrayNumercao = numeracao.split("-");
			retorno = Integer.valueOf(arrayNumercao[0]);
		}

		return retorno;
	}

	/**
	 * Método que retorn o ano mes como string
	 * 
	 * @author thiago toscano
	 * @date 05/04/2006
	 * @param date
	 * @return
	 */
	public static String getAnoMesComoString(Date date){

		return getAnoMesComoInteger(date).toString();
	}

	/**
	 * <<Descrição do método>>
	 * Saulo Lima - Melhorando uso memória - 01/10/2010
	 * 
	 * @param colecao
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public static Object retonarObjetoDeColecao(Collection colecao){

		Object retorno = null;

		if(colecao != null && !colecao.isEmpty()){
			retorno = colecao.iterator().next();
		}
		return retorno;
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param colecao
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public static Object[] retonarObjetoDeColecaoArray(Collection colecao){

		Object[] retorno = null;

		if(colecao != null && !colecao.isEmpty()){

			// TODO Saulo Lima - Melhorando uso memória - 01/10/2010
			// Iterator iterator = colecao.iterator();
			//
			// while (iterator.hasNext()) {
			// retorno = (Object[]) iterator.next();
			// }
			retorno = (Object[]) colecao.iterator().next();

		}
		return retorno;
	}

	/**
	 * Description of the Method
	 * 
	 * @param value1
	 *            Description of the Parameter
	 * @param value2
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public static Integer somaInteiros(Integer value1, Integer value2){

		int v1 = 0;
		int v2 = 0;

		if(value1 != null){
			v1 = value1.intValue();
		}
		if(value2 != null){
			v2 = value2.intValue();
		}
		return Integer.valueOf(v1 + v2);
	}

	/**
	 * Método Valida se um número é maior que o outro.
	 * 
	 * @author: José Cláudio
	 * @date: 31/05/2012
	 * @param String
	 *            NOME do primero número
	 * @param String
	 *            NOME do segundo número
	 * @param String
	 *            VARIAVEL primeiro número
	 * @param String
	 *            VARIAVEL do segundo número
	 */
	public static void validarNumeroMaiorQueOutro(String nomeCampo1, String nomeCampo2, String numero1, String numero2){

		if(numero1 != null && numero1 != "" && numero2 != null && numero2 != ""){
			if(Integer.parseInt(numero1) > Integer.parseInt(numero2)){
				throw new ActionServletException("atencao.numero1.maior.numero2", nomeCampo1, nomeCampo2);
			}
		}
	}

	/**
	 * Description of the Method
	 * 
	 * @param value1
	 *            Description of the Parameter
	 * @param value2
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public static BigDecimal somaBigDecimal(BigDecimal value1, BigDecimal value2){

		BigDecimal v1 = BigDecimal.ZERO;
		BigDecimal v2 = BigDecimal.ZERO;

		if(value1 != null){
			v1 = value1;
		}
		if(value2 != null){
			v2 = value2;
		}
		return v1.add(v2);
	}

	/**
	 * Description of the Method
	 * 
	 * @param value1
	 *            Description of the Parameter
	 * @param value2
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public static BigDecimal subtrairBigDecimal(BigDecimal value1, BigDecimal value2){

		BigDecimal v1 = BigDecimal.ZERO;
		BigDecimal v2 = BigDecimal.ZERO;

		if(value1 != null){
			v1 = value1;
		}

		if(value2 != null){
			v2 = value2;
		}

		return v1.subtract(v2);
	}

	/**
	 * Subtrai a data no formato AAAAMM Exemplo 200508 retorna 200507
	 */
	public static int subtrairData(int data){

		String dataFormatacao = "" + data;

		int ano = Integer.valueOf(dataFormatacao.substring(0, 4)).intValue();
		int mes = Integer.valueOf(dataFormatacao.substring(4, 6)).intValue();

		int mesTemp = (mes - 1);

		if(mesTemp == 0){
			mesTemp = 12;
			ano = ano - 1;
		}

		String anoMes = null;
		String tamanhoMes = "" + mesTemp;

		if(tamanhoMes.length() == 1){
			anoMes = ano + "0" + mesTemp;
		}else{
			anoMes = ano + "" + mesTemp;
		}
		return Integer.valueOf(anoMes).intValue();
	}

	/**
	 * Subtrai a data no formato AAAAMM Exemplo 200508 retorna 200507
	 * Author: Sávio Luiz Data: 20/01/2006
	 * 
	 * @param data
	 *            com a barra
	 * @return Uma data no formato yyyyMM (sem a barra)
	 */
	public static int subtrairMesDoAnoMes(int anoMes, int qtdMeses){

		String dataFormatacao = "" + anoMes;

		int ano = Integer.valueOf(dataFormatacao.substring(0, 4)).intValue();
		int mes = Integer.valueOf(dataFormatacao.substring(4, 6)).intValue();

		int mesTemp = (mes - qtdMeses);

		if(mesTemp <= 0){
			mesTemp = (12 + mes) - qtdMeses;
			ano = ano - 1;
		}

		String anoMesSubtraido = null;
		String tamanhoMes = "" + mesTemp;

		if(tamanhoMes.length() == 1){
			anoMesSubtraido = ano + "0" + mesTemp;
		}else{
			anoMesSubtraido = ano + "" + mesTemp;
		}
		return Integer.valueOf(anoMesSubtraido).intValue();
	}

	public static String formatarMesAnoParaAnoMes(String data){

		// remove pontuações
		data = data.replaceAll("\\p{Punct}", "");

		String mes = data.substring(0, 2);
		String ano = data.substring(2, 6);

		return ano + mes;
	}

	public static String formatarMesAnoParaAnoMesComBarra(String data){

		// remove pontuações
		data = data.replaceAll("\\p{Punct}", "");

		String mes = data.substring(0, 2);
		String ano = data.substring(2, 6);

		return ano + "/" + mes;
	}

	public static int formatarMesAnoParaAnoMes(int mesAno){

		String mesAnoString = "" + mesAno;

		if(mesAnoString.length() > 4){

			// modificado por sávio,pois se a data for 012006 ele tira o 0
			// quando é
			// passado para int e ai dá estouro.
			if(mesAnoString.length() == 5){
				mesAnoString = "0" + mesAnoString;
			}
			String mes = mesAnoString.substring(0, 2);
			String ano = mesAnoString.substring(2, 6);
			return Integer.parseInt(ano + mes);
		}else{
			return 0;
		}

	}

	public static Integer formatarMesAnoComBarraParaAnoMes(String mesAno){

		String mes = mesAno.substring(0, 2);
		String ano = mesAno.substring(3, 7);

		return Integer.parseInt(ano + mes);
	}

	/**
	 * Author: Raphael Rossiter Data: 20/01/2006
	 * 
	 * @param data
	 *            com a barra
	 * @return Uma data no formato yyyyMM (sem a barra)
	 */
	public static String formatarMesAnoParaAnoMesSemBarra(String data){

		String mes = data.substring(0, 2);
		String ano = data.substring(3, 7);

		return ano + mes;
	}

	public static String formatarAnoMesParaMesAno(int anoMes){

		String anoMesFormatado = "";
		String anoMesRecebido = "" + anoMes;
		if(anoMesRecebido.length() < 6){
			anoMesFormatado = anoMesRecebido;
		}else{
			String mes = anoMesRecebido.substring(4, 6);
			String ano = anoMesRecebido.substring(0, 4);
			anoMesFormatado = mes + "/" + ano;
		}
		return anoMesFormatado;
	}

	public static String formatarAnoMesParaMesAnoSemBarra(int anoMes){

		String anoMesFormatado = "";
		String anoMesRecebido = "" + anoMes;
		if(anoMesRecebido.length() < 6){
			anoMesFormatado = anoMesRecebido;
		}else{
			String mes = anoMesRecebido.substring(4, 6);
			String ano = anoMesRecebido.substring(0, 4);
			anoMesFormatado = mes + ano;
		}
		return anoMesFormatado;
	}

	/**
	 * Formata o anomes para o mesano sem barra e o no só com os 2 ultimos
	 * digitos EX.: entrada: 200702 saída:0207 Autor:Sávio Luiz
	 */
	public static String formatarAnoMesParaMesAnoCom2Digitos(int anoMes){

		String anoMesFormatado = "";
		String anoMesRecebido = "" + anoMes;
		if(anoMesRecebido.length() < 6){
			anoMesFormatado = anoMesRecebido;
		}else{
			String mes = anoMesRecebido.substring(4, 6);
			String ano = anoMesRecebido.substring(2, 4);
			anoMesFormatado = mes + ano;
		}
		return anoMesFormatado;
	}

	public static String formatarAnoMesParaMesAno(String anoMes){

		String anoMesFormatado = "";
		String anoMesRecebido = "" + anoMes;
		if(anoMesRecebido.length() < 6){
			anoMesFormatado = anoMesRecebido;
		}else{
			String mes = anoMesRecebido.substring(4, 6);
			String ano = anoMesRecebido.substring(0, 4);
			anoMesFormatado = mes + "/" + ano;
		}
		return anoMesFormatado;
	}

	public static String coverterAnoMesParaMesAno(String anoMes){

		String anoMesFormatado = "";
		String anoMesRecebido = "" + anoMes;
		if(anoMesRecebido.length() < 6){
			anoMesFormatado = anoMesRecebido;
		}else{
			String mes = anoMesRecebido.substring(0, 2);
			String ano = anoMesRecebido.substring(3, 7);
			anoMesFormatado = mes + "/" + ano;
		}
		return anoMesFormatado;
	}


	public static int somarData(int data){

		String dataFormatacao = "" + data;

		int ano = Integer.valueOf(dataFormatacao.substring(0, 4)).intValue();
		int mes = Integer.valueOf(dataFormatacao.substring(4, 6)).intValue();

		int mesTemp = (mes + 1);

		if(mesTemp == 13){
			mesTemp = 1;
			ano = ano + 1;
		}

		String anoMes = null;
		String tamanhoMes = "" + mesTemp;

		if(tamanhoMes.length() == 1){
			anoMes = ano + "0" + mesTemp;
		}else{
			anoMes = ano + "" + mesTemp;
		}
		return Integer.valueOf(anoMes).intValue();
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param parametro
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public static String formatarResultado(String parametro){

		if(parametro != null && !parametro.trim().equals("")){
			if(parametro.equals("null")){
				return "";
			}else{
				return parametro.trim();
			}
		}else{
			return "";
		}
	}

	/**
	 * Adiciona zeros a esqueda do número informado tamamho máximo campo 6
	 * Número 16 retorna 000016
	 * 
	 * @param tamanhoMaximoCampo
	 *            Descrição do parâmetro
	 * @param numero
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public static String adicionarZerosEsquedaNumeroVelho(int tamanhoMaximoCampo, String numero){

		String zeros = "";

		String retorno = null;

		if(numero != null && !numero.equals("")){
			for(int a = 0; a < (tamanhoMaximoCampo - numero.length()); a++){
				zeros = zeros.concat("0");
			}
			// concatena os zeros ao numero
			// caso o numero seja diferente de nulo
			retorno = zeros.concat(numero);
		}else{
			for(int a = 0; a < tamanhoMaximoCampo; a++){
				zeros = zeros.concat("0");
			}
			// retorna os zeros
			// caso o numero seja nulo
			retorno = zeros;
		}

		return retorno;
	}

	/**
	 * Adiciona zeros a esqueda do número informado tamamho máximo campo 6
	 * Número 16 retorna 000016
	 * 
	 * @param tamanhoMaximoCampo
	 *            Descrição do parâmetro
	 * @param numero
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public static String adicionarZerosEsquedaNumero(int tamanhoMaximoCampo, String numero){

		StringBuilder sb = new StringBuilder(tamanhoMaximoCampo);
		for(int i = 0; (numero == null && i < tamanhoMaximoCampo) || (numero != null && i < tamanhoMaximoCampo - numero.length()); i++){
			sb.append('0');
		}
		if(numero != null){
			sb.append(numero);
		}
		return Util.truncarString(sb.toString(), tamanhoMaximoCampo);
	}

	/**
	 * Método que converte uma hora passa em Date onde contém apenas a hora
	 * informada EX: 11:30
	 * 
	 * @param horaMinuto
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @author thiago toscano
	 */
	public static Date converterStringParaHoraMinuto(String horaMinuto){

		Date retorno = null;

		// Obtém a hora
		String hora = horaMinuto.substring(0, 2);
		// Obtém os minutos
		String minuto = horaMinuto.substring(3, 5);

		// obtém a data mínima do mês selecionado
		Calendar data = Calendar.getInstance();

		// Seta como data atual
		data.setTime(new Date());

		// Seta a hora
		data.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hora).intValue());
		data.set(Calendar.MINUTE, Integer.valueOf(minuto).intValue());
		data.set(Calendar.SECOND, 0);
		data.set(Calendar.MILLISECOND, 0);

		retorno = data.getTime();

		return retorno;
	}

	/**
	 * Método que converte uma hora passa em Date onde contém apenas a hora
	 * informada EX: 11:30:52
	 * 
	 * @param horaMinutoSegundo
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 * @author fernanda paiva
	 */
	public static Date converterStringParaHoraMinutoSegundo(String horaMinutoSegundo){

		Date retorno = null;

		SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

		String dataCompleta = "01/01/2006 " + horaMinutoSegundo;

		try{
			retorno = formatoData.parse(dataCompleta);
		}catch(ParseException e){
			e.printStackTrace();
		}

		return retorno;
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param n
	 *            Descrição do parâmetro
	 * @param d
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public static int dividirArredondarResultado(int n, int d){

		int retorno = 0;

		BigDecimal numerador = new BigDecimal(n);
		BigDecimal denominador = new BigDecimal(d);

		if(denominador.intValue() != 0){
			BigDecimal resultado = numerador.divide(denominador, BigDecimal.ROUND_HALF_UP);

			retorno = resultado.intValue();
		}

		return retorno;
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param n
	 *            Descrição do parâmetro
	 * @param d
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public static int dividirTruncarResultado(int n, int d){

		int retorno = 0;

		BigDecimal numerador = new BigDecimal(n);
		BigDecimal denominador = new BigDecimal(d);

		if(denominador.intValue() != 0){
			BigDecimal resultado = numerador.divide(denominador, BigDecimal.ROUND_FLOOR);

			retorno = resultado.intValue();
		}

		return retorno;
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param n
	 *            Descrição do parâmetro
	 * @param d
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public static int arredondarResultadoDivisaoParaMaior(int n, int d){

		int retorno = 0;

		BigDecimal numerador = new BigDecimal(n);
		BigDecimal denominador = new BigDecimal(d);

		if(denominador.intValue() != 0){
			BigDecimal resultado = numerador.divide(denominador, BigDecimal.ROUND_UP);

			retorno = resultado.intValue();
		}

		return retorno;
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param numero
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public static int arredondar(BigDecimal numero){

		numero = numero.setScale(0, BigDecimal.ROUND_HALF_UP);

		return numero.intValue();
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param anoMes
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public static int obterMes(int anoMes){

		String dataFormatacao = "" + anoMes;

		int mes = Integer.valueOf(dataFormatacao.substring(4, 6)).intValue();

		return mes;
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param anoMes
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public static int obterAno(int anoMes){

		String dataFormatacao = "" + anoMes;

		int ano = Integer.valueOf(dataFormatacao.substring(0, 4)).intValue();

		return ano;
	}

	public static int divideDepoisMultiplica(int numerador, int denominador, int numeroMultiplicado){

		BigDecimal n = new BigDecimal(numerador);

		BigDecimal d = new BigDecimal(denominador);

		BigDecimal resultado = n.divide(d, 4, BigDecimal.ROUND_HALF_UP);

		resultado = resultado.multiply(new BigDecimal(numeroMultiplicado));

		return Util.arredondar(resultado);

	}

	/**
	 * Converte a data passada em string dd/MM/yyyy
	 * 
	 * @author: Thiago Toscano
	 * @date: 20/03/2006
	 * @param data
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public static String formatarData(Date data){

		String retorno = "";
		if(data != null){ // 1
			Calendar dataCalendar = new GregorianCalendar();
			StringBuffer dataBD = new StringBuffer();

			dataCalendar.setTime(data);

			if(dataCalendar.get(Calendar.DAY_OF_MONTH) > 9){
				dataBD.append(dataCalendar.get(Calendar.DAY_OF_MONTH) + "/");
			}else{
				dataBD.append("0" + dataCalendar.get(Calendar.DAY_OF_MONTH) + "/");
			}

			// Obs.: Janeiro no Calendar é mês zero
			if((dataCalendar.get(Calendar.MONTH) + 1) > 9){
				dataBD.append(dataCalendar.get(Calendar.MONTH) + 1 + "/");
			}else{
				dataBD.append("0" + (dataCalendar.get(Calendar.MONTH) + 1) + "/");
			}

			dataBD.append(dataCalendar.get(Calendar.YEAR));
			retorno = dataBD.toString();
		}
		return retorno;
	}

	/**
	 * Converte a data passada em string retorna AAAAMMDD
	 * 
	 * @author: Sávio Luiz
	 * @date: 09/04/2007
	 * @param data
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public static String formatarDataSemBarra(Date data){

		String retorno = "";
		if(data != null){ // 1
			Calendar dataCalendar = new GregorianCalendar();
			StringBuffer dataBD = new StringBuffer();

			dataCalendar.setTime(data);

			dataBD.append(dataCalendar.get(Calendar.YEAR));

			// Obs.: Janeiro no Calendar é mês zero
			if((dataCalendar.get(Calendar.MONTH) + 1) > 9){
				dataBD.append(dataCalendar.get(Calendar.MONTH) + 1);
			}else{
				dataBD.append("0" + (dataCalendar.get(Calendar.MONTH) + 1));
			}

			if(dataCalendar.get(Calendar.DAY_OF_MONTH) > 9){
				dataBD.append(dataCalendar.get(Calendar.DAY_OF_MONTH));
			}else{
				dataBD.append("0" + dataCalendar.get(Calendar.DAY_OF_MONTH));
			}

			retorno = dataBD.toString();
		}
		return retorno;
	}

	/**
	 * Converte a data passada em string retorna DDMMAAAA
	 * 
	 * @author: Sávio Luiz
	 * @date: 09/04/2007
	 * @param data
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public static String formatarDataSemBarraDDMMAAAA(Date data){

		String retorno = "";
		if(data != null){ // 1
			Calendar dataCalendar = new GregorianCalendar();
			StringBuffer dataBD = new StringBuffer();

			dataCalendar.setTime(data);

			if(dataCalendar.get(Calendar.DAY_OF_MONTH) > 9){
				dataBD.append(dataCalendar.get(Calendar.DAY_OF_MONTH));
			}else{
				dataBD.append("0" + dataCalendar.get(Calendar.DAY_OF_MONTH));
			}

			// Obs.: Janeiro no Calendar é mês zero
			if((dataCalendar.get(Calendar.MONTH) + 1) > 9){
				dataBD.append(dataCalendar.get(Calendar.MONTH) + 1);
			}else{
				dataBD.append("0" + (dataCalendar.get(Calendar.MONTH) + 1));
			}

			dataBD.append(dataCalendar.get(Calendar.YEAR));

			retorno = dataBD.toString();
		}
		return retorno;
	}

	/**
	 * Converte a data passada em string retorna AAAAMMDD
	 * 
	 * @author: Sávio Luiz
	 * @date: 09/04/2007
	 * @param data
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public static String formatarDataComTracoAAAAMMDD(Date data){

		String retorno = "";
		if(data != null){ // 1
			Calendar dataCalendar = new GregorianCalendar();
			StringBuffer dataBD = new StringBuffer();

			dataCalendar.setTime(data);

			dataBD.append(dataCalendar.get(Calendar.YEAR));
			dataBD.append("-");

			// Obs.: Janeiro no Calendar é mês zero
			if((dataCalendar.get(Calendar.MONTH) + 1) > 9){
				dataBD.append(dataCalendar.get(Calendar.MONTH) + 1);
			}else{
				dataBD.append("0" + (dataCalendar.get(Calendar.MONTH) + 1));
			}
			dataBD.append("-");
			if(dataCalendar.get(Calendar.DAY_OF_MONTH) > 9){
				dataBD.append(dataCalendar.get(Calendar.DAY_OF_MONTH));
			}else{
				dataBD.append("0" + dataCalendar.get(Calendar.DAY_OF_MONTH));
			}

			retorno = dataBD.toString();
		}
		return retorno;
	}

	/**
	 * Monta um data inicial com hora,minuto e segundo zerados para pesquisa no
	 * banco
	 * 
	 * @author: Rafael Pinto
	 * @date: 19/10/2006
	 * @param Date
	 * @return dataInicial
	 */
	public static Date formatarDataInicial(Date dataInicial){

		Calendar calendario = Calendar.getInstance();

		calendario.setTime(dataInicial);

		calendario.set(Calendar.HOUR_OF_DAY, 0);
		calendario.set(Calendar.MINUTE, 0);
		calendario.set(Calendar.SECOND, 0);
		calendario.set(Calendar.MILLISECOND, 0);

		return calendario.getTime();
	}

	/**
	 * Monta um data inicial com hora,minuto e segundo zerados para pesquisa no
	 * banco
	 * 
	 * @author: Rafael Pinto
	 * @date: 19/10/2006
	 * @param Date
	 * @return dataInicial
	 */
	public static Date obterDataPrimeiroDiaMes(Date dataInicial){

		Calendar calendario = Calendar.getInstance();

		calendario.setTime(dataInicial);

		calendario.set(Calendar.DATE, 1);
		calendario.set(Calendar.HOUR_OF_DAY, 0);
		calendario.set(Calendar.MINUTE, 0);
		calendario.set(Calendar.SECOND, 0);
		calendario.set(Calendar.MILLISECOND, 0);

		return calendario.getTime();
	}

	/**
	 * Monta um data inicial com hora,minuto e segundo zerados para pesquisa no
	 * banco
	 * 
	 * @author: Rafael Pinto
	 * @date: 19/10/2006
	 * @param Date
	 * @return dataInicial
	 */
	public static Date formatarDataFinal(Date dataFinal){

		Calendar calendario = Calendar.getInstance();

		calendario.setTime(dataFinal);

		calendario.set(Calendar.HOUR_OF_DAY, 23);
		calendario.set(Calendar.MINUTE, 59);
		calendario.set(Calendar.SECOND, 59);
		calendario.set(Calendar.MILLISECOND, 999);

		return calendario.getTime();
	}

	/**
	 * Converte a data passada para o formato "DD/MM/YYYY"
	 * 
	 * @author: Raphael Rossiter
	 * @date: 22/03/2006
	 * @param String
	 *            data no formato "YYYYMMDD"
	 * @return String data no formato "DD/MM/YYYY"
	 */
	public static String formatarData(String data){

		String retorno = "";

		if(data != null && !data.equals("") && data.trim().length() == 8){

			retorno = data.substring(6, 8) + "/" + data.substring(4, 6) + "/" + data.substring(0, 4);

		}

		return retorno;
	}

	/**
	 * Método retornar uma String no formato dd/MM/aaaa HH:MM:SS a partir da data recebida.
	 * 
	 * @param data
	 *            Objeto do tipo Date
	 * @return String
	 *         No formato dd/MM/aaaa HH:MM:SS
	 */
	public static String formatarDataComHora(Date data){

		StringBuffer dataBD = new StringBuffer();

		if(data != null){
			Calendar dataCalendar = new GregorianCalendar();

			dataCalendar.setTime(data);

			if(dataCalendar.get(Calendar.DAY_OF_MONTH) > 9){
				dataBD.append(dataCalendar.get(Calendar.DAY_OF_MONTH) + "/");
			}else{
				dataBD.append("0" + dataCalendar.get(Calendar.DAY_OF_MONTH) + "/");
			}

			// Obs.: Janeiro no Calendar é mês zero
			if((dataCalendar.get(Calendar.MONTH) + 1) > 9){
				dataBD.append(dataCalendar.get(Calendar.MONTH) + 1 + "/");
			}else{
				dataBD.append("0" + (dataCalendar.get(Calendar.MONTH) + 1) + "/");
			}

			dataBD.append(dataCalendar.get(Calendar.YEAR));

			dataBD.append(" ");

			if(dataCalendar.get(Calendar.HOUR_OF_DAY) > 9){
				dataBD.append(dataCalendar.get(Calendar.HOUR_OF_DAY));
			}else{
				dataBD.append("0" + dataCalendar.get(Calendar.HOUR_OF_DAY));
			}

			dataBD.append(":");

			if(dataCalendar.get(Calendar.MINUTE) > 9){
				dataBD.append(dataCalendar.get(Calendar.MINUTE));
			}else{
				dataBD.append("0" + dataCalendar.get(Calendar.MINUTE));
			}

			dataBD.append(":");

			if(dataCalendar.get(Calendar.SECOND) > 9){
				dataBD.append(dataCalendar.get(Calendar.SECOND));
			}else{
				dataBD.append("0" + dataCalendar.get(Calendar.SECOND));
			}
		}

		return dataBD.toString();
	}

	/**
	 * Método retornar uma String no formato AAAA-MM-DD.HH.MM.SS a partir da data recebida.
	 * 
	 * @param data
	 *            Objeto do tipo Date
	 * @return String
	 *         No formato AAAA-MM-DD.HH.MM.SS
	 */
	public static String formatarDataComTracoAAAAMMDDHHMMSS(Date data){

		StringBuffer dataBD = new StringBuffer();

		if(data != null){
			Calendar dataCalendar = new GregorianCalendar();

			dataCalendar.setTime(data);

			dataBD.append(dataCalendar.get(Calendar.YEAR));
			dataBD.append("-");

			// Obs.: Janeiro no Calendar é mês zero
			if((dataCalendar.get(Calendar.MONTH) + 1) > 9){
				dataBD.append(dataCalendar.get(Calendar.MONTH) + 1 + "-");
			}else{
				dataBD.append("0" + (dataCalendar.get(Calendar.MONTH) + 1) + "-");
			}

			if(dataCalendar.get(Calendar.DAY_OF_MONTH) > 9){
				dataBD.append(dataCalendar.get(Calendar.DAY_OF_MONTH) + ".");
			}else{
				dataBD.append("0" + dataCalendar.get(Calendar.DAY_OF_MONTH) + ".");
			}

			if(dataCalendar.get(Calendar.HOUR_OF_DAY) > 9){
				dataBD.append(dataCalendar.get(Calendar.HOUR_OF_DAY));
			}else{
				dataBD.append("0" + dataCalendar.get(Calendar.HOUR_OF_DAY));
			}

			dataBD.append(".");

			if(dataCalendar.get(Calendar.MINUTE) > 9){
				dataBD.append(dataCalendar.get(Calendar.MINUTE));
			}else{
				dataBD.append("0" + dataCalendar.get(Calendar.MINUTE));
			}

			dataBD.append(".");

			if(dataCalendar.get(Calendar.SECOND) > 9){
				dataBD.append(dataCalendar.get(Calendar.SECOND));
			}else{
				dataBD.append("0" + dataCalendar.get(Calendar.SECOND));
			}
		}

		return dataBD.toString();
	}

	/**
	 * Compara dois objetos no formato anoMesReferencia de acordo com o sinal
	 * logico passado.
	 * 
	 * @param anoMesReferencia1
	 * @param anoMesReferencia2
	 * @param sinal
	 * @return um boleano
	 */
	public static boolean compararAnoMesReferencia(Integer anoMesReferencia1, Integer anoMesReferencia2, String sinal){

		boolean retorno = true;

		// Separando os valores de mês e ano para realizar a comparação
		String mesReferencia1 = String.valueOf(anoMesReferencia1.intValue()).substring(4, 6);
		String anoReferencia1 = String.valueOf(anoMesReferencia1.intValue()).substring(0, 4);

		String mesReferencia2 = String.valueOf(anoMesReferencia2.intValue()).substring(4, 6);
		String anoReferencia2 = String.valueOf(anoMesReferencia2.intValue()).substring(0, 4);

		if(sinal.equalsIgnoreCase("=")){
			if(!Integer.valueOf(anoReferencia1).equals(Integer.valueOf(anoReferencia2))){
				retorno = false;
			}else if(!Integer.valueOf(mesReferencia1).equals(Integer.valueOf(mesReferencia2))){
				retorno = false;
			}
		}else if(sinal.equalsIgnoreCase(">")){
			if(Integer.valueOf(anoReferencia1).intValue() < Integer.valueOf(anoReferencia2).intValue()){
				retorno = false;
			}else if(Integer.valueOf(anoReferencia1).equals(Integer.valueOf(anoReferencia2))
							&& Integer.valueOf(mesReferencia1).intValue() <= Integer.valueOf(mesReferencia2).intValue()){
				retorno = false;
			}
		}else{
			if(Integer.valueOf(anoReferencia2).intValue() < Integer.valueOf(anoReferencia1).intValue()){
				retorno = false;
			}else if(Integer.valueOf(anoReferencia2).equals(Integer.valueOf(anoReferencia1))
							&& Integer.valueOf(mesReferencia2).intValue() <= Integer.valueOf(mesReferencia1).intValue()){
				retorno = false;
			}
		}

		return retorno;
	}

	/**
	 * Compara dois objetos no formato anoMesReferencia de acordo com o sinal
	 * logico passado.
	 * 
	 * @param anoMesReferencia1
	 * @param anoMesReferencia2
	 * @param sinal
	 * @return um boleano
	 */
	public static boolean compararAnoMesReferencia(String anoMesReferencia1, String anoMesReferencia2, String sinal){

		boolean retorno = true;

		// Separando os valores de mês e ano para realizar a comparação
		String mesReferencia1 = String.valueOf(anoMesReferencia1).substring(4, 6);
		String anoReferencia1 = String.valueOf(anoMesReferencia1).substring(0, 4);

		String mesReferencia2 = String.valueOf(anoMesReferencia2).substring(4, 6);
		String anoReferencia2 = String.valueOf(anoMesReferencia2).substring(0, 4);

		if(sinal.equalsIgnoreCase("=")){
			if(!Integer.valueOf(anoReferencia1).equals(Integer.valueOf(anoReferencia2))){
				retorno = false;
			}else if(!Integer.valueOf(mesReferencia1).equals(Integer.valueOf(mesReferencia2))){
				retorno = false;
			}
		}else if(sinal.equalsIgnoreCase(">")){
			if(Integer.valueOf(anoReferencia1).intValue() < Integer.valueOf(anoReferencia2).intValue()){
				retorno = false;
			}else if(Integer.valueOf(anoReferencia1).equals(Integer.valueOf(anoReferencia2))
							&& Integer.valueOf(mesReferencia1).intValue() <= Integer.valueOf(mesReferencia2).intValue()){
				retorno = false;
			}
		}else{
			if(Integer.valueOf(anoReferencia2).intValue() < Integer.valueOf(anoReferencia1).intValue()){
				retorno = false;
			}else if(Integer.valueOf(anoReferencia2).equals(Integer.valueOf(anoReferencia1))
							&& Integer.valueOf(mesReferencia2).intValue() <= Integer.valueOf(mesReferencia1).intValue()){
				retorno = false;
			}
		}

		return retorno;
	}

	/**
	 * Compara dois objetos no formato HH:MM de acordo com o sinal logico
	 * passado.
	 * 
	 * @param horaMinuto1
	 * @param horaMinuto2
	 * @param sinal
	 * @return um boleano
	 */
	public static boolean compararHoraMinuto(String horaMinuto1, String horaMinuto2, String sinal){

		boolean retorno = true;

		// Separando os valores de hora e minuto para realizar a comparação
		String hora1 = horaMinuto1.substring(0, 2);
		String minuto1 = horaMinuto1.substring(3, 5);

		String hora2 = horaMinuto2.substring(0, 2);
		String minuto2 = horaMinuto2.substring(3, 5);

		if(sinal.equalsIgnoreCase("=")){
			if(!Integer.valueOf(hora1).equals(Integer.valueOf(hora2))){
				retorno = false;
			}else if(!Integer.valueOf(minuto1).equals(Integer.valueOf(minuto2))){
				retorno = false;
			}
		}else if(sinal.equalsIgnoreCase(">")){
			if(Integer.valueOf(hora1).intValue() < Integer.valueOf(hora2).intValue()){
				retorno = false;
			}else if(Integer.valueOf(hora1).equals(Integer.valueOf(hora2))
							&& Integer.valueOf(minuto1).intValue() <= Integer.valueOf(minuto2).intValue()){
				retorno = false;
			}
		}else{
			if(Integer.valueOf(hora2).intValue() < Integer.valueOf(hora1).intValue()){
				retorno = false;
			}else if(Integer.valueOf(hora2).equals(Integer.valueOf(hora1))
							&& Integer.valueOf(minuto2).intValue() <= Integer.valueOf(minuto1).intValue()){
				retorno = false;
			}
		}

		return retorno;
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param data
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public static String formatarHoraSemData(Date data){

		StringBuffer dataBD = new StringBuffer("");

		if(data != null){

			Calendar dataCalendar = new GregorianCalendar();
			dataCalendar.setTime(data);

			if(dataCalendar.get(Calendar.HOUR_OF_DAY) > 9){
				dataBD.append(dataCalendar.get(Calendar.HOUR_OF_DAY));
			}else{
				dataBD.append("0" + dataCalendar.get(Calendar.HOUR_OF_DAY));
			}

			dataBD.append(":");

			if(dataCalendar.get(Calendar.MINUTE) > 9){
				dataBD.append(dataCalendar.get(Calendar.MINUTE));
			}else{
				dataBD.append("0" + dataCalendar.get(Calendar.MINUTE));
			}

			dataBD.append(":");

			if(dataCalendar.get(Calendar.SECOND) > 9){
				dataBD.append(dataCalendar.get(Calendar.SECOND));
			}else{
				dataBD.append("0" + dataCalendar.get(Calendar.SECOND));
			}

		}

		return dataBD.toString();
	}

	/**
	 * Obter Dígito Verificador Módulo CAERN Author : Rafael Francisco Pinto
	 * Data : 13/04/2007
	 * Calcula o dígito verificador do código de barras no módulo caern
	 * 
	 * @param numero
	 *            Número do código de barra para calcular o dígito veficador
	 * @return digito verificador do módulo caern
	 */
	public static Integer obterDigitoVerificadorModuloCAERN(String numero){

		String entradaString = adicionarZerosEsquedaNumero(6, numero);

		Integer digitoCalculo = (Integer.valueOf(entradaString.substring(0, 1)) * 35)
						+ (Integer.valueOf(entradaString.substring(1, 2)) * 31) + (Integer.valueOf(entradaString.substring(2, 3)) * 29)
						+ (Integer.valueOf(entradaString.substring(3, 4)) * 23) + (Integer.valueOf(entradaString.substring(4, 5)) * 19)
						+ (Integer.valueOf(entradaString.substring(5, 6)) * 17);

		Integer resultado = digitoCalculo / 11;
		Integer restoDigito = digitoCalculo - (resultado * 11);

		/*
		 * Colocado por Raphael Rossiter em 03/04/2007 (Analista: Eduardo
		 * Borges) Caso o dígito seja igual a 10 o retorno será zero
		 */
		if(restoDigito > 9){
			restoDigito = 0;
		}

		return restoDigito;
	}

	/**
	 * [UC0261] - Obter Dígito Verificador Módulo 11 Author : Pedro Alexandre
	 * Data : 15/02/2006
	 * Calcula o dígito verificador do código de barras no módulo 11(onze)
	 * 
	 * @param numero
	 *            Número do código de barra no formato long para calcular o
	 *            dígito veficador
	 * @return digito verificador do módulo 11(onze)
	 */
	public static Integer obterDigitoVerificadorModulo11(Long numero){

		// converte o número recebido para uma string
		String entradaString = String.valueOf(numero);

		// inicia o sequêncial de multiplicação para 2(dois)
		int sequencia = 2;

		// cria as variáveis que serão utilizadas no calculo
		int digito, contAuxiliar;

		// variável que vai armazenar a soma da múltiplicação de cada dígito
		int somaDigitosProduto = 0;

		// contador auxiliar
		contAuxiliar = 1;

		// laço para calcular a soma da múltiplicação de cada dígito
		for(int i = 0; i < entradaString.length(); i++){

			// recupera o dígito da string
			digito = Integer.valueOf(entradaString.substring(entradaString.length() - contAuxiliar, entradaString.length() - i)).intValue();

			// multiplica o digito pelo sequência e acumula o resultado
			somaDigitosProduto = somaDigitosProduto + (digito * sequencia);

			// se osequência for igual a 9(nove)
			if(sequencia == 9){
				// a sequência volta para 2(dois)
				sequencia = 2;
			}else{
				// incrementa a sequência mais 1
				++sequencia;
			}

			// incrementa o contador auxiliar
			contAuxiliar++;
		}

		// calcula o resto da divisão
		int resto = (somaDigitosProduto % 11);

		// variável que vai armazenar o dígito verificador
		int dac;

		// se o resto for 0(zero) ou 1(1)
		if(resto == 0 || resto == 1){
			// o dígito verificador vai ser 0(zero)
			dac = 0;
		}else if(resto == 10){
			// o dígito verificador vai ser 1(um)
			dac = 1;
		}else{
			// o dígito verificador vai ser a diferença
			dac = 11 - resto;
		}
		// retorna o dígito verificador calculado
		return Integer.valueOf(dac);
	}

	/**
	 * [UC0261] - Obter Dígito Verificador Módulo 11 Author : Pedro Alexandre
	 * Data : 15/02/2006
	 * Calcula o dígito verificador do código de barras no módulo 11(onze)
	 * 
	 * @param numero
	 *            Número do código de barra no formato string para calcular o
	 *            dígito veficador
	 * @return digito verificador do módulo 11(onze)
	 */
	public static Integer obterDigitoVerificadorModulo11(String numero){

		String wnumero = numero;
		int param = 2;
		int soma = 0;

		for(int ind = (wnumero.length() - 1); ind > 0; ind--){
			if(param > 9){
				param = 2;
			}
			soma = soma + (Integer.parseInt(wnumero.substring(ind - 1, ind)) * param);
			param = param + 1;
		}

		int resto = soma % 11;
		int dv;

		if((resto == 0) || (resto == 1)){
			dv = 0;
		}else{
			dv = 11 - resto;
		}
		return dv;
		/*
		 * // converte o número recebido para uma string String entradaString =
		 * numero; // inicia o sequêncial de multiplicação para 2(dois) int
		 * sequencia = 2; // cria as variáveis que serão utilizadas no calculo
		 * int digito, contAuxiliar; // variável que vai armazenar a soma da
		 * múltiplicação de cada dígito int somaDigitosProduto = 0; // contador
		 * auxiliar contAuxiliar = 1; // laço para calcular a soma da
		 * múltiplicação de cada dígito for (int i = 0; i <
		 * entradaString.length(); i++) { // recupera o dígito da string digito =
		 * new Integer(entradaString.substring(entradaString.length() -
		 * contAuxiliar, entradaString.length() - i)).intValue(); // multiplica
		 * o digito pelo sequência e acumula o resultado somaDigitosProduto =
		 * somaDigitosProduto + (digito * sequencia); // se osequência for igual
		 * a 9(nove) if (sequencia == 9) { // a sequência volta para 2(dois)
		 * sequencia = 2; } else { // incrementa a sequência mais 1 ++sequencia; } //
		 * incrementa o contador auxiliar contAuxiliar++; } // calcula o resto
		 * da divisão int resto = (somaDigitosProduto % 11); // variável que vai
		 * armazenar o dígito verificador int dac; // se o resto for 0(zero) ou
		 * 1(1) if (resto == 0 || resto == 1) { // o dígito verificador vai ser
		 * 0(zero) dac = 0; } else if (resto == 10) { // o dígito verificador
		 * vai ser 1(um) dac = 1; } else { // o dígito verificador vai ser a
		 * diferença dac = 11 - resto; } // retorna o dígito verificador
		 * calculado return new Integer(dac);
		 */
	}

	/**
	 * [UC0261] - Obter Dígito Verificador Módulo 11 Author : Pedro Alexandre
	 * Data : 18/09/2013
	 * Hiroshi
	 * A versão antiga está com erro.
	 */
	public static Integer obterDigitoVerificadorModulo11Correto(String numero){

		String wnumero = numero;
		int param = 2;
		int soma = 0;

		for(int ind = (wnumero.length() - 1); ind >= 0; ind--){
			if(param > 9){
				param = 2;
			}
			soma = soma + (Integer.parseInt(wnumero.substring(ind, ind + 1)) * param);
			param = param + 1;
		}

		int resto = soma % 11;
		int dv;

		if((resto == 0) || (resto == 1)){
			dv = 0;
		}else{
			dv = 11 - resto;
		}
		return dv;
		/*
		 * // converte o número recebido para uma string String entradaString =
		 * numero; // inicia o sequêncial de multiplicação para 2(dois) int
		 * sequencia = 2; // cria as variáveis que serão utilizadas no calculo
		 * int digito, contAuxiliar; // variável que vai armazenar a soma da
		 * múltiplicação de cada dígito int somaDigitosProduto = 0; // contador
		 * auxiliar contAuxiliar = 1; // laço para calcular a soma da
		 * múltiplicação de cada dígito for (int i = 0; i <
		 * entradaString.length(); i++) { // recupera o dígito da string digito =
		 * new Integer(entradaString.substring(entradaString.length() -
		 * contAuxiliar, entradaString.length() - i)).intValue(); // multiplica
		 * o digito pelo sequência e acumula o resultado somaDigitosProduto =
		 * somaDigitosProduto + (digito * sequencia); // se osequência for igual
		 * a 9(nove) if (sequencia == 9) { // a sequência volta para 2(dois)
		 * sequencia = 2; } else { // incrementa a sequência mais 1 ++sequencia; } //
		 * incrementa o contador auxiliar contAuxiliar++; } // calcula o resto
		 * da divisão int resto = (somaDigitosProduto % 11); // variável que vai
		 * armazenar o dígito verificador int dac; // se o resto for 0(zero) ou
		 * 1(1) if (resto == 0 || resto == 1) { // o dígito verificador vai ser
		 * 0(zero) dac = 0; } else if (resto == 10) { // o dígito verificador
		 * vai ser 1(um) dac = 1; } else { // o dígito verificador vai ser a
		 * diferença dac = 11 - resto; } // retorna o dígito verificador
		 * calculado return new Integer(dac);
		 */
	}

	/**
	 * Método que recebe uma string ex."123456" e converte para o objeto
	 * BigDecimal ex. "123456".
	 * 354654564,12 = 354654564.12 354.654.564,12 = 354654564.12 35465456412 =
	 * 35465456412.00 354654564.12 = 354654564.12 354654564,12 = 354654564.12
	 * 
	 * @param data
	 * @autor Sávio Luiz, Thiago Toscano
	 * @date 15/02/2006, 18/03/2006
	 * @return
	 */

	public static BigDecimal formatarMoedaRealparaBigDecimal(String valor){

		BigDecimal bigDecimalFormatado = null;

		if(valor != null && !valor.trim().equals("null")){
			valor = valor.trim();

			boolean negativo = false;
			if(valor.startsWith("-")){
				negativo = true;
			}

			boolean temCasaDecimal = false;
			int qtdCasasdecimais = 0;
			if(valor.length() > 2
							&& (valor.substring(valor.length() - 3, valor.length() - 2).equals(".") || valor.substring(valor.length() - 3,
											valor.length() - 2).equals(","))){
				temCasaDecimal = true;
				qtdCasasdecimais = 2;
			}else if(valor.length() > 2
							&& (valor.substring(valor.length() - 2, valor.length() - 1).equals(".") || valor.substring(valor.length() - 2,
											valor.length() - 1).equals(","))){
				temCasaDecimal = true;
				qtdCasasdecimais = 1;

			}

			String valorSemPontuacao = "";
			// metodo que tira todos os pontos no meio da string
			for(int i = 0; i < valor.length(); i++){
				try{
					Integer.parseInt(valor.substring(i, i + 1));
					valorSemPontuacao = valorSemPontuacao + valor.substring(i, i + 1);
				}catch(Exception e){
				}
			}
			if(temCasaDecimal){
				int tamanho = valorSemPontuacao.length();
				if(qtdCasasdecimais == 2)
				valorSemPontuacao = valorSemPontuacao.substring(0, tamanho - 2) + "." + valorSemPontuacao.substring(tamanho - 2, tamanho);
				else if(qtdCasasdecimais == 1) valorSemPontuacao = valorSemPontuacao.substring(0, tamanho - 1) + "."
								+ valorSemPontuacao.substring(tamanho - 1, tamanho);

			}
			if(negativo){
				valorSemPontuacao = "-" + valorSemPontuacao;
			}
			bigDecimalFormatado = new BigDecimal(valorSemPontuacao);

		}else{
			bigDecimalFormatado = BigDecimal.ZERO;
		}

		return bigDecimalFormatado;
	}

	/**
	 * Método que recebe uma string (ex."123456") e converte para o objeto BigDecimal
	 * Ex. "1234.56" colocado as ultimas duas strins como casas decimais.
	 * 
	 * @autor Thiago Toscano
	 * @date 15/02/2006, 18/03/2006
	 * @author Saulo Lima
	 * @date 28/02/2009
	 *       Correção para strings com menos de 3 caracteres
	 * @param valor
	 * @return BigDecimal
	 */
	public static BigDecimal formatarMoedaRealparaBigDecimalComUltimos2CamposDecimais(String valor){

		BigDecimal bigDecimalFormatado = null;

		int tamanho = valor.length();

		if(tamanho >= 3){
			valor = valor.substring(0, tamanho - 2) + "." + valor.substring(tamanho - 2, tamanho);
		}else if(tamanho == 2){
			valor = "0." + valor;
		}else if(tamanho == 1){
			valor = "0.0" + valor;
		}

		bigDecimalFormatado = new BigDecimal(valor);
		return bigDecimalFormatado;
	}

	/**
	 * Método que recebe uma string ex."123456" e converte para o objeto
	 * BigDecimal ex. "12.3456" colocando o valor de Decimais passadas
	 * OBS : Valor só considerará decimais, se for encontrado ',' ou '.' antes das últimas
	 * posições da String, baseado na qtd de decimais.
	 * 
	 * @param String
	 *            valor, int numeroDecimais
	 * @autor Eduardo Henrique
	 * @date 07/07/2008
	 * @return
	 */
	public static BigDecimal formatarMoedaRealparaBigDecimal(String valor, int decimais){

		BigDecimal bigDecimalFormatado = null;

		if(valor != null){
			boolean temCasaDecimal = false;
			if(valor.length() > decimais
							&& (valor.substring(valor.length() - (decimais + 1), valor.length() - decimais).equals(".") || valor.substring(
											valor.length() - (decimais + 1), valor.length() - decimais).equals(","))){
				temCasaDecimal = true;
			}
			// retira pontos e vírgulas
			if(temCasaDecimal){
				if(valor.indexOf(".") >= 0 || valor.indexOf(",") >= 0){
					valor = valor.replaceAll("\\p{Punct}", "");

				}
			}

			bigDecimalFormatado = new BigDecimal(valor);
			if(temCasaDecimal){
				bigDecimalFormatado = bigDecimalFormatado.movePointLeft(decimais);
			}
		}else{
			bigDecimalFormatado = BigDecimal.ZERO;
		}

		return bigDecimalFormatado;
	}

	/**
	 * Método que verifica se a string passada já tem casa decimal
	 * 
	 * @param data
	 * @autor Sávio Luiz
	 * @date 15/02/2006
	 * @return
	 */
	public static boolean verificaSeBigDecimal(String valor){

		boolean temCasaDecimal = false;
		if(valor.length() > 2
						&& (valor.substring(valor.length() - 3, valor.length() - 2).equals(".") || valor.substring(valor.length() - 3,
										valor.length() - 2).equals(","))){
			temCasaDecimal = true;
		}
		return temCasaDecimal;
	}

	/**
	 * Método que recebe uma e verifica se a string só tem numeros.
	 * 
	 * @param data
	 * @autor Sávio Luiz
	 * @date 20/05/2005
	 * @return
	 */
	public static Integer recuperaAnoMesDaData(Date data){

		int ano = Util.getAno(data);
		int mes = Util.getMes(data);
		String mesFormatado = null;
		if(mes > 0 && mes < 10){
			mesFormatado = "0" + mes;
		}else{
			mesFormatado = "" + mes;
		}

		return Integer.valueOf(ano + "" + mesFormatado);

	}

	/**
	 * Método que recebe uma data com string no formato dd/MM/yyyy e converte
	 * para o objeto Date.
	 * 
	 * @deprecated Este metodo não valida datas do tipo "31/02/2012". Usar o método:
	 *             gcom.util.Util.converteStringParaDate(String, boolean) Passando false no
	 *             argumento booleano.
	 * @param data
	 * @autor Thiago Toscano
	 * @date 20/05/2005
	 * @return
	 */
	public static Date converteStringParaDate(String data){

		return converteStringParaDatePorFormato(data, true, "dd/MM/yyyy");
	}

	public static Date converteStringParaDate(String data, boolean lenient){

		return converteStringParaDatePorFormato(data, lenient, "dd/MM/yyyy");
	}

	public static Date converteStringParaDatePorFormato(String data, boolean lenient, String formato){

		Date retorno = null;
		try{
			if(data != null && !"".equals(data)){
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formato, new Locale("pt", "BR"));
				simpleDateFormat.setLenient(lenient);
				retorno = simpleDateFormat.parse(data);
			}

		}catch(Exception e){
			throw new IllegalArgumentException(data + " não tem o formato dd/MM/yyyy.");
		}
		return retorno;
	}

	/**
	 * Método que recebe um String com uma data e o seu formato converte
	 * para o objeto Date.
	 * 
	 * @param data
	 * @autor André Nishimura
	 * @date 13/03/2009
	 * @return
	 */
	public static Date converteStringParaDate(String data, String formato){

		Date retorno = null;
		try{
			if(data != null && !"".equals(data)){
				retorno = new SimpleDateFormat(formato, new Locale("pt", "BR")).parse(data);
			}

		}catch(Exception e){
			new IllegalArgumentException(data + " não tem o formato " + formato + ".");
		}
		return retorno;
	}

	/**
	 * Método que recebe uma data com hora com string no formato dd/MM/yyyy
	 * HH:mm:ss e converte para o objeto Date.
	 * 
	 * @param data
	 * @autor Rafael Santos
	 * @date 06/04/2006
	 * @return
	 */
	public static Date converteStringParaDateHora(String data){

		Date retorno = null;
		try{
			if(data != null && !"".equals(data)){
				retorno = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", new Locale("pt", "BR")).parse(data);
			}
		}catch(Exception e){
			new IllegalArgumentException(data + " não tem o formato dd/MM/yyyy HH:mm:ss.");
		}
		return retorno;
	}

	/**
	 * Método que recebe um int e formata para a data de referência no
	 * formato(mm/aaaa).
	 * 
	 * @param numero
	 *            inteiro
	 * @autor Pedro Alexandre
	 * @date 06/01/2006
	 * @return uma string contendo a referência formatada
	 */
	public static String formatarMesAnoReferencia(int anoMes){

		// converte o valor do tipo int(primitivo em um objeto String)
		String referenciaEmString = (Integer.valueOf(anoMes)).toString();

		// devolve a data de referência formatada com o mês na frente seguido
		// pelo ano
		// separados por uma "/"
		if(referenciaEmString.length() == 6){
			return referenciaEmString.substring(4, 6) + "/" + referenciaEmString.substring(0, 4);
		}else{
			return "";
		}

	}

	/**
	 * Método que recebe um HQL tira espacos em branco do final de uma String de
	 * acordo com o valor informado
	 * 
	 * @param numero
	 *            inteiro
	 * @autor Rhawi Dantas
	 * @date 07/01/2006
	 * @return uma string contendo o HQL formatado
	 */
	public static String formatarHQL(String hql, int valor){

		return hql.substring(0, hql.length() - valor);
	}

	/**
	 * Comparar duas datas e retornar a diferença de meses entre elas Author:
	 * Rafael Santos Data: 07/01/20069
	 * 
	 * @param dataInicial
	 *            Data Inicial
	 * @param dataFinal
	 *            Data Final
	 * @return int
	 */
	public static int dataDiff(Date dataInicial, Date dataFinal){

		int quantidadeMeses = 0;

		GregorianCalendar tempoInicial = new GregorianCalendar();
		GregorianCalendar tempoFinal = new GregorianCalendar();

		GregorianCalendar tempoCorrente = new GregorianCalendar();
		GregorianCalendar tempoBase = new GregorianCalendar();

		tempoInicial.setTime(dataInicial);
		tempoFinal.setTime(dataFinal);

		// Verifica a ordem de inicio das datas
		if(dataInicial.compareTo(dataFinal) < 0){
			tempoBase.setTime(dataFinal);
			tempoCorrente.setTime(dataInicial);

		}else{
			tempoBase.setTime(dataInicial);
			tempoCorrente.setTime(dataFinal);

		}

		// Acumular o Mes
		while(tempoCorrente.get(GregorianCalendar.YEAR) < tempoBase.get(GregorianCalendar.YEAR)
						|| tempoCorrente.get(GregorianCalendar.MONTH) < tempoBase.get(GregorianCalendar.MONTH)){

			quantidadeMeses = quantidadeMeses + 1;
			tempoCorrente.add(GregorianCalendar.MONTH, 1);

		}

		return quantidadeMeses;
	}

	/**
	 * Author: Sávio Luiz Data: 16/01/2006 Valida o ano mes de referencia
	 * retornando true se a data for inválida e false se a data for válida
	 */
	public static boolean validarAnoMes(String anoMesReferencia){

		boolean anoMesInvalido = false;

		if(anoMesReferencia.length() == 7){

			String mesAnoReferencia = anoMesReferencia.substring(4, 6) + "/" + anoMesReferencia.substring(0, 4);

			SimpleDateFormat dataTxt = new SimpleDateFormat("MM/yyyy");

			try{
				dataTxt.parse(mesAnoReferencia);
			}catch(ParseException e){
				anoMesInvalido = true;
			}

		}else{
			anoMesInvalido = true;
		}

		return anoMesInvalido;
	}

	/**
	 * Valida o ano mes de referencia retornando se a data for Inválida: TRUE e se a data for
	 * Válida: FALSE
	 * 
	 * @author Sávio Luiz
	 * @date 16/01/2006
	 * @author Saulo Lima
	 * @date 30/12/2008
	 *       Verificar se a String é nula.
	 * @param String
	 *            anoMesReferencia
	 * @return boolean
	 */
	public static boolean validarAnoMesSemBarra(String anoMesReferencia){

		boolean anoMesInvalido = false;

		if(anoMesReferencia != null){
			if(anoMesReferencia.length() == 6){

				String mes = anoMesReferencia.substring(4, 6);
				// String ano = anoMesReferencia.substring(0, 4);

				try{
					int mesInt = Integer.parseInt(mes);
					// int anoInt = Integer.parseInt(ano);

					if(mesInt > 12){
						anoMesInvalido = true;
					}
				}catch(NumberFormatException e){
					anoMesInvalido = true;
				}
			}else{
				anoMesInvalido = true;
			}
		}else{
			anoMesInvalido = true;
		}

		return anoMesInvalido;
	}

	/**
	 * Author: Rodrigo Silveira Data: 21/01/2006 Valida o mês/ano de referência
	 * retornando true se a data for inválida e false se a data for válida
	 * Alterado por Sávio Luiz. Data: 15/03/2006
	 * Ex.: 11/2005
	 */
	public static boolean validarMesAno(String mesAnoReferencia){

		boolean mesAnoValido = true;

		if(mesAnoReferencia.length() == 7){
			String mes = mesAnoReferencia.substring(0, 2);
			// String ano = mesAnoReferencia.substring(3, 7);

			try{
				int mesInt = Integer.parseInt(mes);
				// int anoInt = Integer.parseInt(ano);

				if(mesInt > 12){
					mesAnoValido = false;
				}
			}catch(NumberFormatException e){
				mesAnoValido = false;
			}

		}else{
			mesAnoValido = false;
		}

		return mesAnoValido;
	}

	/**
	 * Author: Rafael Santos Data: 09/01/2006 Dividir dois BigDecimal
	 * arredondando Arredondanda o resultado apatir da 8 casa decimal para cima
	 * 
	 * @param dividendo
	 *            Valor do Dividendo
	 * @param divisor
	 *            Valor do Dividor
	 * @return O Valor divido, caso necessário arredondado
	 */
	public static BigDecimal dividirArredondando(BigDecimal dividendo, BigDecimal divisor){

		BigDecimal resultado = null;

		if(dividendo != null && divisor != null){

			resultado = dividendo.divide(divisor, 7, BigDecimal.ROUND_HALF_UP);

		}

		return resultado;
	}

	public static String formatarRGApresentacao(String rg, String orgao, String uf){

		return rg + " " + orgao + "/" + uf;
	}

	/**
	 * [UC0000] - Author: Raphael Rossiter Data: 13/01/2006
	 * Calcula a representação númerica do código de barras no módulo 10
	 * 
	 * @param numero
	 * @return digito verificador
	 */
	public static Integer calculoRepresentacaoNumericaCodigoBarrasModulo10(Integer numero){

		int entrada = numero.intValue();

		String entradaString = String.valueOf(entrada);

		int sequencia = 2;
		int contEntrada, digito, contAuxiliar, produto, contProduto;
		String produtoString;
		int somaDigitosProduto = 0;

		contAuxiliar = 1;
		for(contEntrada = 0; contEntrada < entradaString.length(); contEntrada++){

			digito = Integer.valueOf(entradaString.substring(entradaString.length() - contAuxiliar, entradaString.length() - contEntrada))
							.intValue();

			produto = digito * sequencia;
			produtoString = String.valueOf(produto);

			for(contProduto = 0; contProduto < produtoString.length(); contProduto++){
				somaDigitosProduto = somaDigitosProduto + Integer.valueOf(produtoString.substring(contProduto, contProduto + 1)).intValue();
			}

			if(sequencia == 2){
				sequencia = 1;
			}else{
				sequencia = 2;
			}

			contAuxiliar++;
		}

		int resto = (somaDigitosProduto % 10);
		int dac;

		if(resto == 0){
			dac = 0;
		}else{
			dac = 10 - resto;
		}

		return Integer.valueOf(dac);
	}

	/**
	 * <Breve descrição sobre o caso de uso>
	 * [UC0260] Obter Dígito Verificador Módulo 10
	 * 
	 * @author Rafael Rossiter
	 * @date 17/03/2006
	 * @param numero
	 * @return
	 */
	public static Integer obterDigitoVerificadorModulo10(Long numero){

		long entrada = numero.longValue();

		String entradaString = String.valueOf(entrada);

		int sequencia = 2;
		int contEntrada, digito, contAuxiliar, produto, contProduto;
		String produtoString;
		int somaDigitosProduto = 0;

		contAuxiliar = 1;
		for(contEntrada = 0; contEntrada < entradaString.length(); contEntrada++){

			digito = Integer.valueOf(entradaString.substring(entradaString.length() - contAuxiliar, entradaString.length() - contEntrada))
							.intValue();

			produto = digito * sequencia;
			produtoString = String.valueOf(produto);

			for(contProduto = 0; contProduto < produtoString.length(); contProduto++){
				somaDigitosProduto = somaDigitosProduto + Integer.valueOf(produtoString.substring(contProduto, contProduto + 1)).intValue();
			}

			if(sequencia == 2){
				sequencia = 1;
			}else{
				sequencia = 2;
			}

			contAuxiliar++;
		}

		int resto = (somaDigitosProduto % 10);

		int dac;
		if(resto == 0){
			dac = 0;
		}else{
			dac = 10 - resto;
		}

		return Integer.valueOf(dac);
	}

	/**
	 * <Breve descrição sobre o caso de uso>
	 * [UC0260] Obter Dígito Verificador Módulo 10
	 * 
	 * @author Rafael Rossiter
	 * @date 17/03/2006
	 * @param numero
	 * @return
	 */
	public static Integer obterDigitoVerificadorModulo10(String numero){

		String entradaString = numero;

		int sequencia = 2;
		int contEntrada, digito, contAuxiliar, produto, contProduto;
		String produtoString;
		int somaDigitosProduto = 0;

		contAuxiliar = 1;
		for(contEntrada = 0; contEntrada < entradaString.length(); contEntrada++){

			digito = Integer.valueOf(entradaString.substring(entradaString.length() - contAuxiliar, entradaString.length() - contEntrada))
							.intValue();

			produto = digito * sequencia;
			produtoString = String.valueOf(produto);

			for(contProduto = 0; contProduto < produtoString.length(); contProduto++){
				somaDigitosProduto = somaDigitosProduto + Integer.valueOf(produtoString.substring(contProduto, contProduto + 1)).intValue();
			}

			if(sequencia == 2){
				sequencia = 1;
			}else{
				sequencia = 2;
			}

			contAuxiliar++;
		}

		int resto = (somaDigitosProduto % 10);

		int dac;
		if(resto == 0){
			dac = 0;
		}else{
			dac = 10 - resto;
		}

		return Integer.valueOf(dac);
	}

	public static String[] formatarAnoMes(String anoMesReferencia){

		String[] dataCompleta = new String[2];

		String mes = anoMesReferencia.substring(4, 6);
		String ano = anoMesReferencia.substring(0, 4);

		dataCompleta[0] = mes;
		dataCompleta[1] = ano;

		return dataCompleta;

	}

	/**
	 * Verifica se duas datas são iguais
	 * 
	 * @author Sávio Luiz
	 * @date 20/03/2006
	 * @param primeiraData
	 *            <Descrição>
	 * @param segundaData
	 *            <Descrição>
	 * @return retorno
	 */
	public static boolean datasIguais(Date primeiraData, Date segundaData){

		boolean retorno = false;

		Calendar d1 = Calendar.getInstance();
		Calendar d2 = Calendar.getInstance();

		d1.setTime(primeiraData);
		d2.setTime(segundaData);

		d1.set(Calendar.HOUR_OF_DAY, 0);
		d1.set(Calendar.MINUTE, 0);
		d1.set(Calendar.SECOND, 0);
		d1.set(Calendar.MILLISECOND, 0);

		d2.set(Calendar.HOUR_OF_DAY, 0);
		d2.set(Calendar.MINUTE, 0);
		d2.set(Calendar.SECOND, 0);
		d2.set(Calendar.MILLISECOND, 0);

		if(d1.getTime().equals(d2.getTime())){
			retorno = true;
		}

		return retorno;

	}

	/**
	 * Método que recebe uma data e adapta os seus valores para comparação de
	 * uma data final de inetervalo para o uso do between.
	 * 
	 * @param data
	 * @autor Raphael Rossiter
	 * @date 29/09/2005
	 * @return
	 */
	public static Date adaptarDataFinalComparacaoBetween(Date data){

		Date retorno = null;

		Calendar calendario = Calendar.getInstance();

		calendario.setTime(data);

		calendario.set(Calendar.HOUR, 23);
		calendario.set(Calendar.MINUTE, 59);
		calendario.set(Calendar.SECOND, 59);
		calendario.set(Calendar.MILLISECOND, 999);

		retorno = calendario.getTime();

		return retorno;
	}

	/**
	 * extrai a hora o minuto e o segundo da data ex.: Thu May 11 10:12:50
	 * GMT-03:00 2006 o resultado será Thu May 11 00:00:00 GMT-03:00 2006
	 * 
	 * @author Sávio Luiz
	 * @date 11/05/2006
	 * @param numero
	 * @return
	 */
	public static Date formatarDataSemHora(Date data){

		Calendar d1 = Calendar.getInstance();
		d1.setTime(data);
		d1.set(Calendar.HOUR_OF_DAY, 0);
		d1.set(Calendar.MINUTE, 0);
		d1.set(Calendar.SECOND, 0);
		d1.set(Calendar.MILLISECOND, 0);
		return d1.getTime();
	}

	public static String formatarMoedaReal(BigDecimal valor){

		/**
		 * Símbolos especificos do Real Brasileiro
		 */
		DecimalFormatSymbols REAL = new DecimalFormatSymbols(new Locale("pt", "BR"));
		/**
		 * Mascara de dinheiro para Real Brasileiro
		 */
		// DecimalFormat DINHEIRO_REAL = new DecimalFormat("###,###,##0.00");
		//
		// return DINHEIRO_REAL.format(valor);
		if(valor != null && !"".equals(valor)){
			DecimalFormat DINHEIRO_REAL = new DecimalFormat("###,###,##0.00", REAL);
			return DINHEIRO_REAL.format(valor);
		}else{
			return "";
		}
	}

	/**
	 * Formata o valor com o número de casas decimais especificados (mínimo 1 e máximo 6)
	 * 
	 * @author Saulo Lima
	 * @date 14/09/2009
	 * @param valor
	 * @return valorFormatado
	 */
	public static String formatarPercentual(double valor, int casasDecimais){

		String mascaraDecimais = "";
		switch(casasDecimais){
			case 1:
				mascaraDecimais = "0.0";
				break;
			case 2:
				mascaraDecimais = "0.00";
				break;
			case 3:
				mascaraDecimais = "0.000";
				break;
			case 4:
				mascaraDecimais = "0.0000";
				break;
			case 5:
				mascaraDecimais = "0.00000";
				break;
			case 6:
				mascaraDecimais = "0.000000";
				break;
			default:
				mascaraDecimais = "0.00";
				break;
		}

		DecimalFormatSymbols real = new DecimalFormatSymbols(new Locale("pt", "BR"));
		DecimalFormat formatoDecimais = new DecimalFormat("#####" + mascaraDecimais, real);

		return formatoDecimais.format(valor);
	}

	/**
	 * overload do método para informação de quantidade de decimais
	 * 
	 * @author eduardo henrique
	 * @date 07/07/2008
	 * @param valor
	 * @param decimais
	 * @return
	 */
	public static String formatarMoedaReal(BigDecimal valor, int decimais){

		/**
		 * Símbolos especificos do Real Brasileiro
		 */
		DecimalFormatSymbols REAL = new DecimalFormatSymbols(new Locale("pt", "BR"));

		String mascaraDecimais = "";
		switch(decimais){
			case 2:
				mascaraDecimais = "0.00";
				break;
			case 3:
				mascaraDecimais = "0.000";
				break;
			case 4:
				mascaraDecimais = "0.0000";
				break;
			default:
				mascaraDecimais = "0.00";
				break;
		}

		//
		// return DINHEIRO_REAL.format(valor);
		if(valor != null && !"".equals(valor)){
			DecimalFormat DINHEIRO_REAL = new DecimalFormat("###,###,##" + mascaraDecimais, REAL);
			return DINHEIRO_REAL.format(valor);
		}else{
			return "";
		}

	}

	public static boolean validarDiaMesAno(String diaAnoMesReferencia){

		boolean anoMesInvalido = false;

		if(diaAnoMesReferencia.length() == 10){

			// String mesAnoReferencia = anoMesReferencia.substring(4, 6) + "/"
			// + anoMesReferencia.substring(0, 4);

			SimpleDateFormat dataTxt = new SimpleDateFormat("DD/MM/yyyy");

			try{
				dataTxt.parse(diaAnoMesReferencia);
			}catch(ParseException e){
				anoMesInvalido = true;
			}
		}else{
			anoMesInvalido = true;
		}

		return anoMesInvalido;
	}

	public static boolean validarAnoMesDiaSemBarra(String diaMesAnoReferencia){

		boolean diaMesAnoInvalido = false;

		if(diaMesAnoReferencia.length() == 8){

			// String ano = diaMesAnoReferencia.substring(0, 4);
			String mes = diaMesAnoReferencia.substring(4, 6);
			String dia = diaMesAnoReferencia.substring(6, 8);

			try{
				int mesInt = Integer.parseInt(mes);
				// int anoInt = Integer.parseInt(ano);
				int diaInt = Integer.parseInt(dia);

				if(mesInt > 12){
					diaMesAnoInvalido = true;
				}
				if(diaInt > 31){
					diaMesAnoInvalido = true;
				}
			}catch(NumberFormatException e){
				diaMesAnoInvalido = true;
			}

		}else{
			diaMesAnoInvalido = true;
		}

		return diaMesAnoInvalido;
	}

	/**
	 * Método que recebe uma data com string no formato AAAAMMDD e converte para
	 * o objeto Date.
	 * 
	 * @param data
	 * @autor Sávio Luiz
	 * @date 20/05/2005
	 * @return
	 */
	public static Date converteStringInvertidaSemBarraParaDate(String data){

		Date retorno = null;
		String dataInvertida = data.substring(6, 8) + "/" + data.substring(4, 6) + "/" + data.substring(0, 4);
		SimpleDateFormat dataTxt = new SimpleDateFormat("dd/MM/yyyy");
		try{
			retorno = dataTxt.parse(dataInvertida);
		}catch(ParseException e){
			throw new IllegalArgumentException(data + " não tem o formato dd/MM/yyyy.");
		}
		return retorno;
	}

	/**
	 * Método que recebe uma string e verifica se a string só tem numeros com
	 * casas decimais
	 * 
	 * @param data
	 * @autor thiago
	 * @date 18/03/2006
	 * @return
	 */
	public static boolean validarValorNaoNumericoComoBigDecimal(String valor){

		boolean numeroNaoNumerico = false;
		try{

			new BigDecimal(valor);
		}catch(NumberFormatException e){
			numeroNaoNumerico = true;
		}
		return numeroNaoNumerico;
	}

	/**
	 * Método que recebe uma string e verifica se a string só tem numeros.
	 * 
	 * @author Sávio Luiz
	 * @date 20/05/2005
	 * @param valor
	 * @return boolean
	 *         TRUE = valor não numérico;
	 *         FALSE = valor numérico.
	 */
	public static boolean validarValorNaoNumerico(String valor){

		boolean numeroNaoNumerico = false;
		try{

			Integer.parseInt(valor);
		}catch(NumberFormatException e){
			numeroNaoNumerico = true;
		}
		return numeroNaoNumerico;
	}

	public static Integer formataAnoMes(Date data){

		int ano = Util.getAno(data);
		int mes = Util.getMes(data);

		String mesFormatado = null;

		if(mes >= 0 && mes < 10){
			mesFormatado = "0" + mes;
		}else{
			mesFormatado = "" + mes;
		}

		String anoMesFormatado = "" + ano + mesFormatado;

		return Integer.valueOf(anoMesFormatado);

	}

	/**
	 * Verifica se eh dia util
	 * (Verifica por feriado nacional,municipal e se final de semana)
	 * Auhtor: Rafael Pinto
	 * Data: 23/08/2007
	 * 
	 * @param Date
	 *            data a ser verificada
	 * @param Colecao
	 *            <NacionalFeriado)
	 * @param Colecao
	 *            <MunicipioFeriado)
	 * @return boolean (true - eh dia util, false - nao dia util)
	 */
	public static boolean ehDiaUtil(Date dataAnalisada, Collection<NacionalFeriado> colecaoNacionalFeriado,
					Collection<MunicipioFeriado> colecaoMunicipioFeriado){

		boolean ehDiaUtil = true;

		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(dataAnalisada);

		// Dia referente a semana
		int diaDaSemana = calendar.get(Calendar.DAY_OF_WEEK);

		// Verifica se eh Sabado ou Domingo
		if(diaDaSemana == Calendar.SATURDAY || diaDaSemana == Calendar.SUNDAY){

			ehDiaUtil = false;

			// Verifica se eh Feriado
		}else{

			if(colecaoNacionalFeriado != null && !colecaoNacionalFeriado.isEmpty()){

				Iterator itera = colecaoNacionalFeriado.iterator();

				while(itera.hasNext()){
					NacionalFeriado nacionalFeriado = (NacionalFeriado) itera.next();

					if(nacionalFeriado.getData().compareTo(formatarDataSemHora(dataAnalisada)) == 0){
						ehDiaUtil = false;
						break;
					}
				}
			}

			if(ehDiaUtil){

				if(colecaoMunicipioFeriado != null && !colecaoMunicipioFeriado.isEmpty()){

					Iterator itera = colecaoMunicipioFeriado.iterator();

					while(itera.hasNext()){
						MunicipioFeriado municipioFeriado = (MunicipioFeriado) itera.next();

						if(municipioFeriado.getDataFeriado().compareTo(formatarDataSemHora(dataAnalisada)) == 0){
							ehDiaUtil = false;
							break;
						}
					}
				}
			}

		}// fim do if diaSemana

		return ehDiaUtil;
	}

	/**
	 * Verifica se é dia util
	 * (Verifica por feriado nacional, municipal e por dias excludentes)
	 * 
	 * @author isilva
	 *         Data: 21/07/2010
	 * @param dataAnalisada
	 * @param colecaoNacionalFeriado
	 * @param colecaoMunicipioFeriado
	 * @param diasExcludentes
	 *            Dias que não serão contados como dias uteis
	 * @return boolean (true - eh dia util, false - nao dia util)
	 */
	public static boolean ehDiaUtil(Date dataAnalisada, List<Calendar> colecaoNacionalFeriado, List<Calendar> colecaoMunicipioFeriado,
					Collection<Integer> diasExcludentes){

		boolean ehDiaUtil = true;

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dataAnalisada);

		int diaDaSemana = calendar.get(Calendar.DAY_OF_WEEK);

		if(diasExcludentes != null && !diasExcludentes.isEmpty()){
			for(Integer dia : diasExcludentes){
				if(diaDaSemana == dia){
					return false;
				}
			}
		}

		if(colecaoNacionalFeriado != null && !colecaoNacionalFeriado.isEmpty()){
			if(colecaoNacionalFeriado.contains(calendar)){
				return false;
			}
		}

		if(colecaoMunicipioFeriado != null && !colecaoMunicipioFeriado.isEmpty()){
			if(colecaoMunicipioFeriado.contains(calendar)){
				return false;
			}
		}

		return ehDiaUtil;
	}

	/**
	 * Retorna o Ultimo Dia Util do Mês informado
	 * Auhtor: Rafael Santos
	 * Data: 20/02/2006
	 * Indices de Mês 1 - Janiero
	 * 2 - Fevereiro
	 * 3 - Março
	 * 4 - Abril
	 * 5 - Maio
	 * 6 - Junho
	 * 7 - Julho
	 * 8 - Agosto
	 * 9 - Setembro
	 * 10 - Outubro
	 * 11 - Novembro
	 * 12 - Dezembro
	 * 
	 * @param mes
	 *            Indice do Mês
	 * @param ano
	 *            Ano
	 * @param colecaoDatasFeriados
	 *            Coleção de Datas dos Feriados
	 * @return Ultimo Dia do Mes util
	 */
	public static int obterUltimoDiaUtilMes(int mes, int ano, Collection colecaoDatasFeriados){

		int ultimoDiaUtil = 0;

		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.YEAR, ano);// ano

		switch(mes){
			case 1:// JANEIRO
				calendar.set(Calendar.MONTH, 0);
				break;
			case 2:// FEVEREIRO
				calendar.set(Calendar.MONTH, 1);
				break;
			case 3:// MARÇO
				calendar.set(Calendar.MONTH, 2);
				break;
			case 4:// ABRIL
				calendar.set(Calendar.MONTH, 3);
				break;
			case 5:// MAIO
				calendar.set(Calendar.MONTH, 4);
				break;
			case 6:// JUNHO
				calendar.set(Calendar.MONTH, 5);
				break;
			case 7:// JULHO
				calendar.set(Calendar.MONTH, 6);
				break;
			case 8:// AGOSTO
				calendar.set(Calendar.MONTH, 7);
				break;
			case 9:// SETEMBRO
				calendar.set(Calendar.MONTH, 8);
				break;
			case 10:// OUTUBRO
				calendar.set(Calendar.MONTH, 9);
				break;
			case 11:// NOVEMBRO
				calendar.set(Calendar.MONTH, 10);
				break;
			case 12:// DEZEMBRO
				calendar.set(Calendar.MONTH, 11);
				break;
			default:
				break;
		}

		// ultima dia do mes
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

		boolean feriado = true;

		while(feriado
						| ((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) | (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY))){

			// se o dia for domingo voltar um dia
			if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
				calendar.add(Calendar.DAY_OF_MONTH, -1);
			}

			// se o dia for sabado voltar um dia
			if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
				calendar.add(Calendar.DAY_OF_MONTH, -1);
			}

			// verfica se o dia é feriado
			if(colecaoDatasFeriados != null && !colecaoDatasFeriados.isEmpty()){

				// Organizar a coleção
				Collections.sort((List) colecaoDatasFeriados, new Comparator() {

					public int compare(Object a, Object b){

						Date data1 = ((Date) a);
						Date data2 = ((Date) b);

						return ((data1.compareTo(data2)) * -1);
					}
				});

				Iterator iteratorColecaoDatasFeriados = colecaoDatasFeriados.iterator();

				while(iteratorColecaoDatasFeriados.hasNext()){
					Date dataFeriado = (Date) iteratorColecaoDatasFeriados.next();
					Calendar calendarDataFeriado = new GregorianCalendar();
					calendarDataFeriado.setTime(dataFeriado);

					// verifica se o dia, mes e ano
					if((calendar.get(Calendar.DAY_OF_MONTH) == calendarDataFeriado.get(Calendar.DAY_OF_MONTH))
									&& (calendar.get(Calendar.MONTH) == calendarDataFeriado.get(Calendar.MONTH))
									&& (calendar.get(Calendar.YEAR) == calendarDataFeriado.get(Calendar.YEAR))){
						calendar.add(Calendar.DAY_OF_MONTH, -1);
					}

				}
				feriado = false;
			}else{
				feriado = false;
			}
		}

		ultimoDiaUtil = calendar.get(Calendar.DAY_OF_MONTH);

		return ultimoDiaUtil;
	}

	/**
	 * Retorna o Ultimo Dia do Mês informado Auhtor: Rafael Corrêa Data:
	 * 02/04/2007 Indices de Mês 1 - Janiero 2 - Fevereiro 3 - Março 4 - Abril 5 -
	 * Maio 6 - Junho 7 - Julho 8 - Agosto 9 - Setembro 10 - Outubro 11 -
	 * Novembro 12 - Dezembro
	 * 
	 * @param mes
	 *            Indice do Mês
	 * @param ano
	 *            Ano
	 * @return Ultimo Dia do Mes
	 */
	public static String obterUltimoDiaMes(int mes, int ano){

		String ultimoDia = "";

		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.YEAR, ano);

		switch(mes){
			case 1:// JANEIRO
				calendar.set(Calendar.MONTH, 0);
				break;
			case 2:// FEVEREIRO
				calendar.set(Calendar.MONTH, 1);
				break;
			case 3:// MARÇO
				calendar.set(Calendar.MONTH, 2);
				break;
			case 4:// ABRIL
				calendar.set(Calendar.MONTH, 3);
				break;
			case 5:// MAIO
				calendar.set(Calendar.MONTH, 4);
				break;
			case 6:// JUNHO
				calendar.set(Calendar.MONTH, 5);
				break;
			case 7:// JULHO
				calendar.set(Calendar.MONTH, 6);
				break;
			case 8:// AGOSTO
				calendar.set(Calendar.MONTH, 7);
				break;
			case 9:// SETEMBRO
				calendar.set(Calendar.MONTH, 8);
				break;
			case 10:// OUTUBRO
				calendar.set(Calendar.MONTH, 9);
				break;
			case 11:// NOVEMBRO
				calendar.set(Calendar.MONTH, 10);
				break;
			case 12:// DEZEMBRO
				calendar.set(Calendar.MONTH, 11);
				break;
			default:
				break;
		}

		// ultima dia do mês
		ultimoDia = "" + calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

		return ultimoDia;
	}

	/**
	 * Método para comparar duas data e retornar o numero de dias da diferença
	 * entre elas
	 * Author: Rafael Santos Data: 07/03/2006
	 * 
	 * @param dataInicial
	 *            Data Inicial
	 * @param dataFinal
	 *            Data Final
	 * @return int Quantidade de Dias
	 */
	public static int obterQuantidadeDiasEntreDuasDatas(Date dataInicial, Date dataFinal){

		GregorianCalendar startTime = new GregorianCalendar();
		GregorianCalendar endTime = new GregorianCalendar();

		GregorianCalendar curTime = new GregorianCalendar();
		GregorianCalendar baseTime = new GregorianCalendar();

		startTime.setTime(dataInicial);
		endTime.setTime(dataFinal);

		int multiplicadorDiferenca = 1;

		// Verifica a ordem de inicio das datas
		if(dataInicial.compareTo(dataFinal) < 0){
			baseTime.setTime(dataFinal);
			curTime.setTime(dataInicial);
			multiplicadorDiferenca = 1;
		}else{
			baseTime.setTime(dataInicial);
			curTime.setTime(dataFinal);
			multiplicadorDiferenca = -1;
		}

		int resultadoAno = 0;
		int resultadoMeses = 0;
		int resultadoDias = 0;

		// Para cada mes e ano, vai de mes em mes pegar o ultimo dia para ir
		// acumulando
		// no total de dias. Ja leva em consideracao ano bissesto
		while(curTime.get(GregorianCalendar.YEAR) < baseTime.get(GregorianCalendar.YEAR)
						|| curTime.get(GregorianCalendar.MONTH) < baseTime.get(GregorianCalendar.MONTH)){

			int max_day = curTime.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
			resultadoMeses += max_day;
			curTime.add(GregorianCalendar.MONTH, 1);

		}

		// Marca que é um saldo negativo ou positivo
		resultadoMeses = resultadoMeses * multiplicadorDiferenca;

		// Retirna a diferenca de dias do total dos meses
		resultadoDias += (endTime.get(GregorianCalendar.DAY_OF_MONTH) - startTime.get(GregorianCalendar.DAY_OF_MONTH));

		return resultadoAno + resultadoMeses + resultadoDias;
	}

	/**
	 * Author: Pedro Alexandre Data: 08/01/2006
	 * Subtrai nº de dias de uma data
	 * 
	 * @param numeroDias
	 * @param data
	 * @return data menos o nº de dias informado
	 */
	public static Date subtrairNumeroDiasDeUmaData(Date data, int numeroDias){

		// cria uma instância de GregorianCalendar para manipular a data
		Calendar c = GregorianCalendar.getInstance();

		// seta a data
		c.setTime(data);

		// subtrai o nº de dias INFORMADO da data
		c.add(Calendar.DAY_OF_MONTH, (-1 * numeroDias));

		// recupera a data subtraida dos nº de dias
		data = c.getTime();

		// retorna a nova data
		return data;
	}

	/**
	 * Author: Raphael Rossiter Data: 03/08/2007
	 * Subtrai nº de anos de uma data
	 * 
	 * @param numeroAnos
	 * @param data
	 * @return data menos o nº de anos informado
	 */
	public static Date subtrairNumeroAnosDeUmaData(Date data, int numeroAnos){

		Calendar calendar = Calendar.getInstance();

		calendar.setTime(data);
		calendar.add(Calendar.YEAR, (-1 * numeroAnos));

		return calendar.getTime();

	}

	/**
	 * Author: Pedro Alexandre Data: 08/01/2006
	 * Adiciona nº de dias para uma data
	 * 
	 * @param numeroDias
	 * @param data
	 * @return data mais o nº de dias informado
	 */
	public static Date adicionarNumeroDiasDeUmaData(Date data, int numeroDias){

		// cria uma instância de GregorianCalendar para manipular a data
		Calendar c = GregorianCalendar.getInstance();

		// seta a data
		c.setTime(data);

		// adiciona o nº de dias INFORMADO à data
		c.add(Calendar.DAY_OF_MONTH, (numeroDias));

		// recupera a data somada aos nº de dias
		data = c.getTime();

		// retorna a nova data
		return data;
	}

	/**
	 * @author isilva
	 * @date 26/07/2010
	 *       Adiciona nº de meses para uma data
	 * @param data
	 * @param numeroMeses
	 * @return
	 */
	public static Date adicionarNumeroMesDeUmaData(Date data, int numeroMeses){

		// cria uma instância de GregorianCalendar para manipular a data
		Calendar c = GregorianCalendar.getInstance();

		// seta a data
		c.setTime(data);

		c.add(Calendar.MONTH, (numeroMeses));

		// recupera a data somada aos nº de meses
		data = c.getTime();

		// retorna a nova data
		return data;
	}

	/**
	 * Author: Saulo Lima Data: 12/08/2008
	 * Adiciona nº de horas para uma data
	 * 
	 * @param numeroHoras
	 * @param data
	 * @return data mais o nº de horas informado
	 */
	public static Date adicionarNumeroHorasAUmaData(Date data, int numeroHoras){

		// cria uma instância de GregorianCalendar para manipular a data
		Calendar c = GregorianCalendar.getInstance();

		// seta a data
		c.setTime(data);

		// soma o nº de horas INFORMADO na data
		c.add(Calendar.HOUR, (numeroHoras));

		// recupera a data somada ao nº de horas
		data = c.getTime();

		// retorna a nova data
		return data;
	}

	/**
	 * Author: Sávio Luiz Data: 21/02/2006
	 * Recebe uma data e retorna AAAAMMDD
	 * 
	 * @param numeroDias
	 * @param data
	 * @return data menos o nº de dias informado
	 */
	public static String recuperaDataInvertida(Date data){

		int dia = getDiaMes(data);
		int mes = getMes(data);
		int ano = getAno(data);

		String diaFormatado = null;
		String mesFormatado = null;

		if(mes > 0 && mes < 10){
			mesFormatado = "0" + mes;
		}else{
			mesFormatado = "" + mes;
		}

		if(dia > 0 && dia < 10){
			diaFormatado = "0" + dia;
		}else{
			diaFormatado = "" + dia;
		}

		// retorna a nova data no formato AAAAMMDD
		String dataFormatada = "" + ano + mesFormatado + diaFormatado;
		return dataFormatada;
	}

	// Complementa o tamanho da string com espaços em branco.
	// Autor:Sávio Luiz
	public static String completaString(String str, int tm){

		char[] temp = new char[tm];
		Arrays.fill(temp, ' ');
		int tamanho = 0;
		if(str != null){
			tamanho = str.length();
		}else{
			tamanho = 0;
			str = "";
		}

		StringBuilder stringBuilder = null;
		// caso o tamanho da string seja maior do que o tamanho especificado
		if(tamanho > tm){
			// trunca a String
			String strTruncado = str.substring(0, tm);
			stringBuilder = new StringBuilder(strTruncado);
			// coloca o tamanho para o tamanho truncado
			tamanho = strTruncado.length();
		}else{
			stringBuilder = new StringBuilder(str);
		}

		stringBuilder.append(temp, tamanho, tm - tamanho);
		return stringBuilder.toString();
	}

	/**
	 * O metódo completa uma string com espaços em branco (ex: passa a string
	 * "12.36" e o tamanho máximo 10 e retorna " 12.36" )
	 * [UC0321] Emitir Fatura de Cliente Responsável
	 * 
	 * @author Pedro Alexandre
	 * @date 02/05/2006
	 * @param str
	 *            String que vai ser complementada com espaços em branco a
	 *            esquerda
	 * @param tm
	 *            Tamanho máximo da string
	 * @return
	 */
	public static String completaStringComEspacoAEsquerda(String str, int tamanhoMaximo){

		// Tamanho da string informada
		int tamanhoString = 0;
		if(str != null){
			tamanhoString = str.length();
		}else{
			tamanhoString = 0;
		}

		// Calcula a quantidade de espaços embranco necessários
		int quantidadeEspacos = tamanhoMaximo - tamanhoString;

		if(quantidadeEspacos < 0){
			quantidadeEspacos = tamanhoMaximo;
		}

		// Cria um array de caracteres de espaços em branco
		char[] tempCharEspacos = new char[quantidadeEspacos];
		Arrays.fill(tempCharEspacos, ' ');

		// Cria uma string temporaria com os espaços em branco
		String temp = new String(tempCharEspacos);

		// Cria uma strinBuilder para armazenar a string
		StringBuilder stringBuilder = null;

		// Caso o tamanhoda string informada seja maior que o tamanho máximo da
		// string
		// trunca a string informada
		if(tamanhoString > tamanhoMaximo){
			String strTruncado = str.substring(0, tamanhoMaximo);
			stringBuilder = new StringBuilder(strTruncado);
		}else{
			stringBuilder = new StringBuilder(temp);
			stringBuilder.append(str);
		}

		// Retorna a string informada com espaços em branco a esquerda
		// totalizando o tamanho máximo informado
		return stringBuilder.toString();
	}

	/**
	 * O metódo completa uma string com espaços em branco
	 * (ex: passa a string "abcde" e o tamanho máximo 8 e retorna "   abcde" )
	 * (ex: passa a string "abcde" e o tamanho máximo 4 e retorna "bcde" )
	 * 
	 * @author Saulo Lima
	 * @date 07/11/2012
	 * @param str
	 *            String que vai ser complementada com espaços em branco a esquerda
	 * @param tamanhoMaximo
	 *            Tamanho máximo da string
	 * @return stringFormatada
	 */
	public static String completaStringComEspacoAEsquerdaTruncandoPelaEsquerda(String str, int tamanhoMaximo){

		// Tamanho da string informada
		int tamanhoString = 0;
		if(str != null){
			tamanhoString = str.length();
		}

		// Cria uma strinBuilder para armazenar a string
		StringBuilder stringBuilder = null;

		// Caso o tamanho da string informada seja maior que o tamanho máximo da
		// permitido, trunca a string informada pela ESQUERDA
		if(tamanhoString > tamanhoMaximo){
			String strTruncado = str.substring(tamanhoString - tamanhoMaximo, tamanhoString);
			stringBuilder = new StringBuilder(strTruncado);

		}else{

			// Calcula a quantidade de espaços em branco necessários
			int quantidadeEspacos = tamanhoMaximo - tamanhoString;

			// Cria um array de caracteres de espaços em branco
			char[] tempCharEspacos = new char[quantidadeEspacos];
			Arrays.fill(tempCharEspacos, ' ');

			// Cria uma string temporaria com os espaços em branco
			String temp = new String(tempCharEspacos);

			stringBuilder = new StringBuilder(temp);
			stringBuilder.append(str);
		}

		// Retorna a string informada com espaços em branco a esquerda
		// totalizando o tamanho máximo informado
		return stringBuilder.toString();
	}

	/**
	 * O metódo completa uma string com espaços em branco (ex: passa a string
	 * "12.36" e o tamanho máximo 10 e retorna " 12.36" ) apenas se a string não exceder o tamanho
	 * máximo
	 * 
	 * @author Rodrigo Silveira
	 * @date 04/01/2008
	 * @param str
	 *            String que vai ser complementada com espaços em branco a
	 *            esquerda
	 * @param tm
	 *            Tamanho máximo da string
	 * @return
	 */
	public static String completaStringComEspacoAEsquerdaCondicaoTamanhoMaximo(String str, int tamanhoMaximo){

		// Tamanho da string informada
		int tamanhoString = 0;
		if(str != null){
			tamanhoString = str.length();
		}else{
			tamanhoString = 0;
		}

		// Calcula a quantidade de espaços embranco necessários
		int quantidadeEspacos = tamanhoMaximo - tamanhoString;

		if(quantidadeEspacos < 0){
			return str;

		}

		// Cria um array de caracteres de espaços em branco
		char[] tempCharEspacos = new char[quantidadeEspacos];
		Arrays.fill(tempCharEspacos, ' ');

		// Cria uma string temporaria com os espaços em branco
		String temp = new String(tempCharEspacos);

		// Cria uma strinBuilder para armazenar a string
		StringBuilder stringBuilder = new StringBuilder(temp);

		stringBuilder.append(str);

		// Retorna a string informada com espaços em branco a esquerda
		// totalizando o tamanho máximo informado
		return stringBuilder.toString();
	}

	/**
	 * Formatar para MM/AAAA Entrada: MMAAAA Saída: MM/AAAA
	 * 
	 * @author Raphael Rossiter
	 * @date 03/04/2006
	 * @author eduardo henrique
	 * @date 16/12/2008
	 *       Alteração no método para validar se parâmetro passado é nulo ou vazio
	 * @param mesAno
	 * @return Uma string no formato MM/AAAA
	 */
	public static String formatarMesAnoSemBarraParaMesAnoComBarra(String mesAno){

		if(mesAno != null && !mesAno.trim().equalsIgnoreCase("")){
			String mes = mesAno.substring(0, 2);
			String ano = mesAno.substring(2, 6);

			return mes + "/" + ano;
		}else{
			return "";
		}
	}

	/**
	 * Formatar um Integer no formato AAAAMM para uma string no formato MM/AAAA.
	 * 
	 * @author Virgínia Melo
	 * @date 21/08/2008
	 * @param anoMes
	 *            - Ano Mês ex: 200808
	 * @return Uma String no formato MM/AAAA
	 */
	public static String formatarAnoMesSemBarraParaMesAnoComBarra(Integer anoMes){

		String mesAnoFormatado = "";

		if(!isVazioOuBranco(anoMes)){
			String anoMesRecebido = "" + anoMes;
			if(anoMesRecebido.length() < 6){
				mesAnoFormatado = anoMesRecebido;
			}else{
				String ano = anoMesRecebido.substring(0, 4);
				String mes = anoMesRecebido.substring(4, 6);
				mesAnoFormatado = mes + "/" + ano;
			}
		}
		return mesAnoFormatado;

	}

	/**
	 * Obtém a representação númerica do código de barras de um pagamento de
	 * acordo com os parâmetros informados
	 * [UC0229] Obter Representação Numérica do Código de Barras
	 * Obtém o dígito verificador geral do código de barra com 43 posições
	 * [SB0002] Obter Dígito Verificador Geral
	 * 
	 * @author Pedro Alexandre
	 * @date 20/04/2006
	 * @param codigoBarraCom43Posicoes
	 * @return
	 */
	public static Integer obterDigitoVerificadorGeral(String codigoBarraCom43Posicoes){

		// Recupera o dígito verificador do módulo 11 para o código de barra com
		// 43 posições
		// Passando uma string como parâmetro

		// MUDOU PARA O MODULO 10
		// Integer digitoVerificadorGeral =
		// obterDigitoVerificadorModulo11(codigoBarraCom43Posicoes);

		Integer digitoVerificadorGeral = obterDigitoVerificadorModulo10(codigoBarraCom43Posicoes);

		// Retorna o dígito verificador calculado
		return digitoVerificadorGeral;
	}

	/**
	 * Recupera o AnoMesDia da Data Entrada: Uma data(da base) Saída: AAAAMMDD
	 * 
	 * @author Sávio Luiz
	 * @date 22/04/2006
	 * @param data
	 * @return Uma string no formato AAAAMMDD
	 */
	public static String recuperaAnoMesDiaDaData(Date data){

		String ano = "" + getAno(data);
		String mes = "" + getMes(data);
		if(mes.length() == 1){
			mes = "0" + mes;
		}
		String dia = "" + getDiaMes(data);
		if(dia.length() == 1){
			dia = "0" + dia;
		}

		return ano + mes + dia;
	}

	/**
	 * Recupera o AnoMesDia da Data Entrada: Uma data(da base) Saída: DDMMAA
	 * 
	 * @author Sávio Luiz
	 * @date 22/04/2006
	 * @param data
	 * @return Uma string no formato DDMMAA
	 */
	public static String recuperaDiaMesAnoCom2DigitosDaData(Date data){

		String ano = "" + getAno(data);
		if(ano != null && ano.length() > 2){
			ano = ano.substring(ano.length() - 2, ano.length());
		}
		String mes = "" + getMes(data);
		if(mes.length() == 1){
			mes = "0" + mes;
		}
		String dia = "" + getDiaMes(data);
		if(dia.length() == 1){
			dia = "0" + dia;
		}

		return dia + mes + ano;
	}

	/**
	 * Permite executar as atividades do faturamento previamente comandadas
	 * [UC0111] Executar Atividade do Faturamento
	 * Adiciona mais um ao mês do anoMesReferencia
	 * somaUmMesAnoMesReferencia
	 * 
	 * @author Roberta Costa
	 * @date 04/05/2006
	 * @param anoMesReferencia
	 * @return
	 */
	public static Integer somaUmMesAnoMesReferencia(Integer anoMesReferencia){

		int mes = obterMes(anoMesReferencia.intValue());
		int ano = obterAno(anoMesReferencia.intValue());
		String anoMes = "";

		if(mes >= 12){
			mes = 1;
			ano = ano + 1;
		}else{
			mes = mes + 1;
		}

		if(mes < 10){
			anoMes = "" + ano + "0" + mes;
		}else{
			anoMes = "" + ano + mes;
		}
		return Integer.parseInt(anoMes);
	}

	/**
	 * Adiciona mais um ao mês do anoMesReferencia
	 * somaUmMesAnoMesReferencia
	 * 
	 * @author Roberta Costa
	 * @date 04/05/2006
	 * @param anoMesReferencia
	 * @return
	 */
	public static Integer somaMesAnoMesReferencia(Integer anoMesReferencia, int qtdMeses){

		int mes = obterMes(anoMesReferencia.intValue());
		int ano = obterAno(anoMesReferencia.intValue());
		String anoMes = "";
		mes = mes + qtdMeses;
		if(mes > 12){
			while(mes > 12){
				mes = mes - 12;
				ano = ano + 1;
			}
		}
		if(mes < 10){
			anoMes = "" + ano + "0" + mes;
		}else{
			anoMes = "" + ano + mes;
		}
		return Integer.parseInt(anoMes);
	}

	/**
	 * Subtrair ano ao anoMesReferencia
	 * subitrairAnoAnoMesReferencia
	 * 
	 * @param anoMesReferencia
	 * @return Integer
	 */
	public static Integer subtrairAnoAnoMesReferencia(Integer anoMesReferencia, int qtdAnos){

		int mes = obterMes(anoMesReferencia.intValue());
		int ano = obterAno(anoMesReferencia.intValue());
		String anoMes = "";
		ano = ano - qtdAnos;
		if(mes < 10){
			anoMes = "" + ano + "0" + mes;
		}else{
			anoMes = "" + ano + mes;
		}
		return Integer.parseInt(anoMes);
	}

	/**
	 * Adiciona mais um ao mês ao mes/ano
	 * somaUmMesMesAnoComBarra
	 * 
	 * @author Flávio Cordeiro
	 * @date 14/02/2007
	 * @param mesAno
	 * @return
	 */
	public static String somaMesMesAnoComBarra(String mesAno, int qtdMeses){

		int mes = Integer.valueOf(mesAno.substring(0, 2));
		int ano = Integer.valueOf(mesAno.substring(3, 7));

		String mesAnoComBarra = "";
		mes = mes + qtdMeses;
		if(mes > 12){
			while(mes > 12){
				mes = mes - 12;
				ano = ano + 1;
			}
		}
		if(mes < 10){
			mesAnoComBarra = "0" + mes + "/" + ano;
		}else{
			mesAnoComBarra = mes + "/" + ano;
		}
		return mesAnoComBarra;
	}

	/**
	 * [UC0336] Gerar Relatório de Acompanhamento do Faturamento
	 * Diminui até seis meses do anoMesReferencia
	 * somaUmMesAnoMesReferencia
	 * 
	 * @author Fernanda Paiva
	 * @date 12/05/2006
	 * @param anoMesReferencia
	 * @return
	 */
	public static Integer subtraiAteSeisMesesAnoMesReferencia(Integer anoMesReferencia, Integer meses){

		int mes = obterMes(anoMesReferencia.intValue());
		int ano = obterAno(anoMesReferencia.intValue());

		String anoMes = "";
		String mesDiferenca = "";

		Integer diferencaMes = mes - meses;

		if(diferencaMes <= 0){
			mes = 12 + diferencaMes;
			ano = ano - 1;
		}else{
			mes = diferencaMes;
		}

		if(mes <= 9){
			mesDiferenca = "0" + mes;
		}else{
			mesDiferenca = "" + mes;
		}
		anoMes = "" + ano + mesDiferenca;
		return Integer.parseInt(anoMes);
	}

	/**
	 * Método que gera uma senha aleatória
	 * 
	 * @author Rodrigo Silveira
	 * @date 19/05/2006
	 * @param tamanhoSenha
	 *            Tamanho da senha aleatória a ser gerada
	 * @return Senha aleatória
	 */
	public static String geradorSenha(int tamanhoSenha){

		char[] pw = new char[tamanhoSenha];
		int c = 'A';
		int r1 = 0;
		for(int i = 0; i < tamanhoSenha; i++){
			r1 = (int) (Math.random() * 3);
			switch(r1){
				case 0:
					c = '0' + (int) (Math.random() * 10);
					break;
				case 1:
					c = 'a' + (int) (Math.random() * 26);
					break;
				case 2:
					c = 'A' + (int) (Math.random() * 26);
					break;
			}
			pw[i] = (char) c;
		}
		return new String(pw);
	}

	// --------------- Calcular Percentual
	// a funcao recebe dois valores, pega o primeiro valor(valor1) multiplica
	// por 100
	// e divide pelo segundo valor(valor2)
	public static String calcularPercentual(String valor1, String valor2){

		return calcularPercentual(valor1, valor2, 2);
	}

	// --------------- Calcular Percentual
	// a funcao recebe dois valores, pega o primeiro valor(valor1) multiplica
	// por 100
	// e divide pelo segundo valor(valor2)
	public static String calcularPercentual(String valor1, String valor2, int qtdDecimal){

		BigDecimal bigValor1 = new BigDecimal(valor1);
		BigDecimal bigValor2 = new BigDecimal(valor2 != null ? valor2 : "1");

		BigDecimal numeroCem = new BigDecimal("100");

		BigDecimal primeiroNumero = bigValor1.multiply(numeroCem);

		BigDecimal resultado = primeiroNumero.divide(bigValor2, qtdDecimal, BigDecimal.ROUND_HALF_UP);

		return (resultado + "");
	}

	// --------------- Calcular Percentual
	// a funcao recebe dois valores, pega o primeiro valor(valor1) multiplica
	// por 100
	// e divide pelo segundo valor(valor2)
	public static BigDecimal calcularPercentualBigDecimal(String valor1, String valor2){

		BigDecimal bigValor1 = new BigDecimal(valor1);
		BigDecimal bigValor2 = new BigDecimal(valor2 != null ? valor2 : "1");

		BigDecimal numeroCem = new BigDecimal("100");

		BigDecimal primeiroNumero = bigValor1.multiply(numeroCem);

		BigDecimal resultado = primeiroNumero.divide(bigValor2, 2, BigDecimal.ROUND_HALF_UP);

		return resultado;
	}

	// --------------- Calcular Percentual
	// a funcao recebe dois valores, pega o primeiro valor(valor1) multiplica
	// por 100
	// e divide pelo segundo valor(valor2)
	public static BigDecimal calcularPercentualBigDecimal(BigDecimal bigValor1, BigDecimal bigValor2){

		BigDecimal numeroCem = new BigDecimal("100");

		BigDecimal primeiroNumero = bigValor1.multiply(numeroCem);

		BigDecimal resultado = primeiroNumero.divide(bigValor2, 2, BigDecimal.ROUND_HALF_UP);

		return resultado;
	}

	/**
	 * Método que valida se uma String é composta apenas de dígitos.
	 * 
	 * @author lms
	 * @date 18/07/2006
	 * @param string
	 *            String numérica
	 * @return boolean true, caso a String possa ser convertida em um Integer.
	 *         false, caso contrário.
	 */
	public static boolean validarStringNumerica(String string){

		boolean ehValida = true;
		try{
			Integer.valueOf(string);
		}catch(NumberFormatException e){
			ehValida = false;
		}
		return ehValida;
	}

	/**
	 * Método que converte um objeto qualquer em uma String através do método
	 * toString(). Caso o objeto seja null, retorna uma String vazia "".
	 * 
	 * @author lms
	 * @date 20/07/2006
	 * @param objeto
	 *            Um objeto qualquer
	 * @return String objeto.toString(), caso o objeto != null. "", caso
	 *         contrário.
	 */
	public static String converterObjetoParaString(Object objeto){

		String string = "";
		if(objeto != null){
			string = objeto.toString();
		}
		return string;
	}

	/**
	 * Método que valida se um determinado Integer é diferente de null e maior
	 * que ZERO.
	 * 
	 * @author lms
	 * @date 21/07/2006
	 * @param numero
	 *            Um numero qualquer
	 * @return boolean true, caso numero != null && numero.compareTo(ZERO) > 0
	 *         false, caso contrário.
	 */
	public static boolean validarNumeroMaiorQueZERO(Integer numero){

		boolean retorno = false;
		if(numero != null && numero.compareTo(0) > 0){
			retorno = true;
		}
		return retorno;
	}

	/**
	 * Método que converte uma String em um Integer. Caso a String seja null ou
	 * não seja um número válido, retorna null.
	 * 
	 * @author lms
	 * @date 20/07/2006
	 * @param string
	 *            Uma string numérica
	 * @return Integer Integer.parseInt(string), caso a string seja um número.
	 *         null, caso contrário.
	 */
	public static Integer converterStringParaInteger(String string){

		Integer integer = null;
		try{
			integer = Integer.parseInt(string);
		}catch(NumberFormatException e){

		}
		return integer;
	}

	/**
	 * Método que converte uma String no formato "int,int,int,..." (Exemplo: "3,10,20,45") em uma
	 * coleção de objetos Integer. Caso a String seja null ou
	 * não respeite o formato citado acima, retorna null.
	 * 
	 * @author Luciano Galvao
	 * @date 07/01/2013
	 */
	public static Collection<Integer> converterStringParaColecaoInteger(String string){

		String[] valores = null;
		Collection<Integer> colecaoValores = null;

		if(!isVazioOuBranco(string)){

			// Recupera os valores da string
			valores = string.split(",");

			if(!isVazioOrNulo(valores)){

				colecaoValores = new ArrayList<Integer>();

				// carrega valores
				Integer valorInteiro = null;
				for(String valor : valores){

					// converte valor
					try{
						valorInteiro = Integer.valueOf(valor);
					}catch(NumberFormatException e){
						valorInteiro = null;
					}

					if(valorInteiro != null){
						colecaoValores.add(valorInteiro);
					}
				}

				// Se nenhum valor foi identificado e adicionado à coleção, retornar null
				if(colecaoValores.isEmpty()){
					colecaoValores = null;
				}
			}
		}

		return colecaoValores;
	}

	/**
	 * Valida a string de acordo com a máscara passada.
	 * 
	 * @author Leonardo Regis
	 * @date 29/07/2006
	 * @param value
	 *            A string a ser validada.
	 * @param mask
	 *            A expressão regular
	 * @return boolean True se é válida e False se não
	 */
	public static boolean validateMask(String value, String mask){

		if(mask == null){
			return true; // sem máscara, deixa passar
		}
		if(GenericValidator.isBlankOrNull(value)){
			return true; // é como o struts lida com a situação
		}
		return GenericValidator.matchRegexp(value, mask);
	}

	/**
	 * Método que valida se uma determinada String é um número maior que ZERO.
	 * 
	 * @author lms
	 * @date 21/07/2006
	 * @param numero
	 *            Um numero qualquer
	 * @return boolean true, caso numero != null && numero.compareTo(ZERO) > 0
	 *         false, caso contrário.
	 */
	public static boolean validarNumeroMaiorQueZERO(String string){

		return validarNumeroMaiorQueZERO(converterStringParaInteger(string));
	}

	/**
	 * Compara duas datas levando em consideração apenas o dia, mês e ano.
	 * 
	 * @author lms
	 * @date 10/08/2006
	 * @param date1
	 *            ,
	 *            date2 Duas datas
	 * @return int -1, se a data1 > data2 0, se data1 == data2 1, se data1 <
	 *         data2
	 */
	public static int compararDiaMesAno(Date date1, Date date2){

		int retorno = 0;
		int dia1, dia2, mes1, mes2, ano1, ano2;
		Calendar c = GregorianCalendar.getInstance();

		c.setTime(date1);
		dia1 = c.get(Calendar.DAY_OF_MONTH);
		mes1 = c.get(Calendar.MONTH);
		ano1 = c.get(Calendar.YEAR);

		c.setTime(date2);
		dia2 = c.get(Calendar.DAY_OF_MONTH);
		mes2 = c.get(Calendar.MONTH);
		ano2 = c.get(Calendar.YEAR);

		if(ano1 > ano2){
			retorno = 1;
		}else if(ano1 < ano2){
			retorno = -1;
		}else{
			// ano1 == ano2
			if(mes1 > mes2){
				retorno = 1;
			}else if(mes1 < mes2){
				retorno = -1;
			}else{
				// mes1 == mes2
				if(dia1 > dia2){
					retorno = 1;
				}else if(dia1 < dia2){
					retorno = -1;
				}
			}
		}

		return retorno;
	}

	/**
	 * Retorna uma hora no formato HH:MM a partir de um objeto Date
	 * 
	 * @author Raphael Rossiter
	 * @date 25/07/2006
	 */
	public static String formatarHoraSemSegundos(Date data){

		StringBuffer dataBD = new StringBuffer();

		if(data != null){
			Calendar dataCalendar = new GregorianCalendar();

			dataCalendar.setTime(data);

			if(dataCalendar.get(Calendar.HOUR_OF_DAY) > 9){
				dataBD.append(dataCalendar.get(Calendar.HOUR_OF_DAY));
			}else{
				dataBD.append("0" + dataCalendar.get(Calendar.HOUR_OF_DAY));
			}

			dataBD.append(":");

			if(dataCalendar.get(Calendar.MINUTE) > 9){
				dataBD.append(dataCalendar.get(Calendar.MINUTE));
			}else{
				dataBD.append("0" + dataCalendar.get(Calendar.MINUTE));
			}

		}

		return dataBD.toString();
	}

	/**
	 * Recupera quantidade de horas entre duas datas
	 * 
	 * @author Leonardo Regis
	 * @date 15/09/2006
	 */
	public static int obterQtdeHorasEntreDatas(Date dataInicial, Date dataFinal){

		Calendar start = Calendar.getInstance();
		start.setTime(dataInicial);
		// Date startTime = start.getTime();
		if(!dataInicial.before(dataFinal)) return 0;
		for(int i = 1;; ++i){
			start.add(Calendar.HOUR, 1);
			if(start.getTime().after(dataFinal)){
				start.add(Calendar.HOUR, -1);
				return (i - 1);
			}
		}
	}

	/**
	 * Transforma um BigDecimal em uma string substituindo o ponto e com o
	 * número de casas decimais determinado.
	 * 
	 * @param numero
	 *            O número a ser transformado
	 * @param casas
	 *            A quantidade de casas decimais
	 * @param agruparMilhares
	 *            Indicador informando se deve agrupar milhares
	 * @author Rafael Francisco Pinto
	 * @return A string no formato especificado
	 */
	public static String formataBigDecimal(BigDecimal numero, int casas, boolean agruparMilhares){

		if(numero == null){
			numero = BigDecimal.ZERO;
		}

		NumberFormat formato = NumberFormat.getInstance(new Locale("pt", "BR"));
		formato.setMaximumFractionDigits(casas);
		formato.setMinimumFractionDigits(casas);
		formato.setGroupingUsed(agruparMilhares);

		return formato.format(numero);
	}

	/**
	 * Converte a String em collection
	 * 
	 * @param separador
	 *            O separador usado ex:(, : ;)
	 * @param campos
	 *            A String que será usada
	 * @author Rafael Francisco Pinto
	 * @return Collection com os valores separado da String
	 */
	public static Collection separarCamposString(String separador, String campos){

		Collection retorno = new LinkedList();

		StringTokenizer st = new StringTokenizer(campos, separador);

		while(st.hasMoreTokens()){

			retorno.add(st.nextToken());

		}

		return retorno;

	}

	public static BigDecimal calcularValorDebitoComPorcentagem(BigDecimal valorDebito, String percentual){

		BigDecimal retorno = null;

		if(percentual.trim().equalsIgnoreCase("70")){
			retorno = valorDebito.multiply(new BigDecimal("0.7"));
		}else if(percentual.trim().equalsIgnoreCase("50")){
			retorno = valorDebito.multiply(new BigDecimal("0.5"));
		}else if(percentual.trim().equalsIgnoreCase("25")){
			retorno = valorDebito.multiply(new BigDecimal("0.25"));
		}else{
			retorno = valorDebito;
		}

		return retorno;
	}

	/*
	 * Retorna o valor monetário por extenso, a partir de um BigDecimal
	 */
	public static String valorExtenso(BigDecimal numero){

		double num = numero.doubleValue();

		String s = "";

		String nome[] = {"UM BILHÃO", " BILHÕES", "UM MILHÃO", " MILHÕES"};
		long n = (long) num;
		long mil_milhoes;
		long milhoes;
		long milhares;
		long unidades;
		long centavos;
		double frac = num - n;

		if(num == 0){
			return "ZERO";
		}
		mil_milhoes = (n - n % 1000000000) / 1000000000;
		n -= mil_milhoes * 1000000000;
		milhoes = (n - n % 1000000) / 1000000;
		n -= milhoes * 1000000;
		milhares = (n - n % 1000) / 1000;
		n -= milhares * 1000;
		unidades = n;
		centavos = (long) (frac * 100);
		if((long) (frac * 1000 % 10) > 5){
			centavos++;
		}

		if(mil_milhoes > 0){
			if(mil_milhoes == 1){
				s += nome[0];
			}else{
				s += numero(mil_milhoes);
				s += nome[1];
			}
			if((unidades == 0) && ((milhares == 0) && (milhoes > 0))){
				s += " E ";
			}else if((unidades != 0) || ((milhares != 0) || (milhoes != 0))){
				s += ", ";
			}
		}
		if(milhoes > 0){
			if(milhoes == 1){
				s += nome[2];
			}else{
				s += numero(milhoes);
				s += nome[3];
			}
			if((unidades == 0) && (milhares > 0)){
				s += " E ";
			}else if((unidades != 0) || (milhares != 0)){
				s += ", ";
			}
		}
		if(milhares > 0){
			if(milhares != 1){
				s += numero(milhares);
			}
			s += " MIL";
			if(unidades > 0){
				if((milhares > 100) && (unidades > 100)){
					s += ", ";
				}else if(((unidades % 100) != 0) || ((unidades % 100 == 0) && (milhares < 10))){
					s += " E ";
				}else{
					s += " ";
				}
			}
		}
		s += numero(unidades);
		if(num > 0){
			s += ((long) num == 1L) ? " REAL" : " REAIS";
		}

		if(centavos != 0){
			s += " E ";
			s += numero(centavos);
			s += (centavos == 1) ? " CENTAVO" : " CENTAVOS";
		}

		return s.toString();
	}

	public static String numero(long n){


		String u[] = {"", "UM", "DOIS", "TRES", "QUATRO", "CINCO", "SEIS", "SETE", "OITO", "NOVE", "DEZ", "ONZE", "DOZE", "TREZE", "CATORZE", "QUINZE", "DEZESSEIS", "DEZESSETE", "DEZOITO", "DEZENOVE"};
		String d[] = {"", "", "VINTE", "TRINTA", "QUARENTA", "CINQUENTA", "SESSENTA", "SETENTA", "OITENTA", "NOVENTA"};
		String c[] = {"", "CENTO", "DUZENTOS", "TREZENTOS", "QUATROCENTOS", "QUINHENTOS", "SEISCENTOS", "SETECENTOS", "OITOCENTOS", "NOVECENTOS"};

		String extenso_do_numero = new String();

		if((n < 1000) && (n != 0)){
			if(n == 100){
				extenso_do_numero = "CEM";
			}else{
				if(n > 99){
					extenso_do_numero += c[(int) (n / 100)];
					if(n % 100 > 0){
						extenso_do_numero += " E ";
					}
				}
				if(n % 100 < 20){
					extenso_do_numero += u[(int) n % 100];
				}else{
					extenso_do_numero += d[((int) n % 100) / 10];
					if((n % 10 > 0) && (n > 10)){
						extenso_do_numero += " E ";
						extenso_do_numero += u[(int) n % 10];
					}
				}
			}
		}else if(n > 999){
			extenso_do_numero = "<<ERRO: NUMERO > 999>>";
		}
		return extenso_do_numero;
	}

	/**
	 * Compara duas datas sem verificar a hora.
	 * 
	 * @param data1
	 *            A primeira data
	 * @param data2
	 *            A segunda data
	 * @author Rafael Francisco Pinto
	 * @return -1 se a data1 for menor que a data2,
	 *         0 se as datas forem iguais,
	 *         1 se a data1 for maior que a data2.
	 */
	public static int compararData(Date data1, Date data2){

		Calendar calendar1;
		Calendar calendar2;

		int ano1;
		int ano2;
		int mes1;
		int mes2;
		int dia1;
		int dia2;

		int resultado;

		calendar1 = Calendar.getInstance();
		calendar1.setTime(data1);

		ano1 = calendar1.get(Calendar.YEAR);
		mes1 = calendar1.get(Calendar.MONTH);
		dia1 = calendar1.get(Calendar.DAY_OF_MONTH);

		calendar2 = Calendar.getInstance();
		calendar2.setTime(data2);

		ano2 = calendar2.get(Calendar.YEAR);
		mes2 = calendar2.get(Calendar.MONTH);
		dia2 = calendar2.get(Calendar.DAY_OF_MONTH);

		if(ano1 == ano2){

			if(mes1 == mes2){

				if(dia1 == dia2){
					resultado = 0;
				}else if(dia1 < dia2){
					resultado = -1;
				}else{
					resultado = 1;
				}
			}else if(mes1 < mes2){
				resultado = -1;
			}else{
				resultado = 1;
			}
		}else if(ano1 < ano2){
			resultado = -1;
		}else{
			resultado = 1;
		}
		return resultado;
	}

	/**
	 * Compara duas datas verificando hora, minuto, segundo e milisegundo.
	 * 
	 * @param data1
	 *            A primeira data
	 * @param data2
	 *            A segunda data
	 * @author Rafael Francisco Pinto
	 * @return -1 se a data1 for menor que a data2, 0 se as datas forem iguais,
	 *         1 se a data1 for maior que a data2.
	 */
	public static int compararDataTime(Date data1, Date data2){

		long dataTime1 = data1.getTime();
		long dataTime2 = data2.getTime();
		int result;
		if(dataTime1 == dataTime2){
			result = 0;
		}else if(dataTime1 < dataTime2){
			result = -1;
		}else{
			result = 1;
		}
		return result;
	}

	/**
	 * Verifica se a hora está no intervalo
	 * 
	 * @param data1
	 *            A primeira hora do intervalo
	 * @param data2
	 *            A segunda hora do intervalo
	 * @param data3
	 *            A terceira hora será usado para verficar se esta no intervelo
	 * @author Rafael Francisco Pinto
	 * @return true está no intervalo false não está no intervalo.
	 */
	public static boolean verifcarIntervaloHora(Date intervaloInicio, Date intervaloFim, Date dataAnalisada){

		boolean ehIgual = false;

		if(dataAnalisada.compareTo(intervaloInicio) != -1 && dataAnalisada.compareTo(intervaloFim) != 1){

			ehIgual = true;
		}

		return ehIgual;
	}

	/**
	 * Verifica se a data está no intervalo
	 * 
	 * @param data1
	 *            A primeira data do intervalo
	 * @param data2
	 *            A segunda data do intervalo
	 * @param data3
	 *            A terceira data será usado para verficar se esta no intervelo
	 * @author Rafael Francisco Pinto
	 * @return true está no intervalo false não está no intervalo.
	 */
	public static boolean verifcarIntervaloData(Date intervaloInicio, Date intervaloFim, Date dataAnalisada){

		boolean ehIgual = false;

		if(dataAnalisada.compareTo(intervaloInicio) != -1 && dataAnalisada.compareTo(intervaloFim) != 1){

			ehIgual = true;
		}

		return ehIgual;
	}

	/**
	 * Cria uma data
	 * 
	 * @param dia
	 *            O dia
	 * @param mes
	 *            O mês
	 * @param ano
	 *            O ano
	 * @return Uma instância da classe Date
	 */
	public static Date criarData(int dia, int mes, int ano){

		Calendar calendario;

		calendario = Calendar.getInstance();
		calendario.set(ano, mes - 1, dia, 0, 0, 0);

		return calendario.getTime();
	}

	/**
	 * retorna sequencial formatado(Ex.: 000.001)
	 */
	public static String retornaSequencialFormatado(int sequencial){

		// sequencial impressão
		String retorno = Util.adicionarZerosEsquedaNumero(6, "" + sequencial);

		retorno = retorno.substring(0, 3) + "." + retorno.substring(3, 6);

		return retorno;
	}

	/**
	 * Adciona ou subtrai o mes na data ou mesAno.
	 * Caso queira subtrair manda o valor dos meses negativo ex.(-5) vai subtrair 5 meses da data ou
	 * do mesAno
	 * 
	 * @param data
	 * @param meses
	 * @param anoMes
	 * @author Rafael Francisco Pinto
	 * @return a descrição do mês
	 */
	public static Date adcionarOuSubtrairMesesAData(Date data, int meses, int anoMes){

		Calendar calendar = Calendar.getInstance();

		if(data == null){
			String anoMesString = "" + anoMes;

			String ano = anoMesString.substring(0, 4);
			String mes = anoMesString.substring(4, 6);

			data = Util.criarData(1, Integer.parseInt(mes), Integer.parseInt(ano));

		}

		calendar.setTime(data);
		calendar.add(Calendar.MONTH, meses);

		return calendar.getTime();
	}

	/**
	 * Retorna a descrição do mes
	 * 
	 * @param mes
	 * @author Rafael Francisco Pinto
	 * @return a descrição do mês
	 */
	public static String retornaDescricaoMes(int mes){

		String meses[] = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};

		String mesPorExtenso = meses[mes - 1];// mes-1 pq o indice do array
		// começa no zero

		return mesPorExtenso;
	}

	/**
	 * Retorna a descrição abreviado do mes
	 * 
	 * @param mes
	 * @author Rafael Francisco Pinto
	 * @return a descrição do mês
	 */
	public static String retornaDescricaoAbreviadoMes(int mes){

		String meses[] = {"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"};

		String mesPorExtenso = meses[mes - 1];// mes-1 pq o indice do array
		// começa no zero

		return mesPorExtenso;
	}

	/**
	 * Retorna a descrição abreviado do mes em inglês
	 * 
	 * @param mes
	 * @author Saulo Lima
	 * @return a abreviação da descrição do mês em inglês
	 */
	public static String retornaDescricaoAbreviadoMesIngles(int mes){

		String meses[] = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
		String mesPorExtenso = meses[mes - 1];// mes-1 pq o indice do array começa no zero
		return mesPorExtenso;
	}

	/**
	 * Retorna a descrição abreviada do ano Mes
	 * Ex.: Recebe 200810 e retorna Out/08
	 * 
	 * @param anoMes
	 * @author Rafael Francisco Pinto
	 */
	public static String retornaDescricaoAnoMes(String anoMes){

		int mes = Integer.valueOf(anoMes.substring(4, 6));
		String ano = anoMes.substring(2, 4);

		String descricao = retornaDescricaoAbreviadoMes(mes) + "/" + ano;

		return descricao;
	}

	/**
	 * Retorna a descrição abreviada do ano Mes
	 * Ex.: Recebe 200811 e retorna Nov/2008
	 * 
	 * @param anoMes
	 * @author Saulo Lima
	 */
	public static String retornaDescricaoAnoMes4Digitos(String anoMes){

		String descricao = "";
		if(anoMes.length() >= 6){
			int mes = Integer.valueOf(anoMes.substring(4, 6));
			String ano = anoMes.substring(0, 4);

			descricao = retornaDescricaoAbreviadoMes(mes) + "/" + ano;
		}
		return descricao;
	}

	/**
	 * Retorna a matrícula do imóvel formatada.ex.:1234567 retorna 123456.7
	 * 
	 * @author Sávio Luiz
	 */
	public static String retornaMatriculaImovelFormatada(Integer matriculaImovel){

		String matriculaImovelFormatada = "";
		if(matriculaImovel != null && !matriculaImovel.equals("")){
			matriculaImovelFormatada = "" + matriculaImovel;
			int quantidadeCaracteresImovel = matriculaImovel.toString().length();
			matriculaImovelFormatada = matriculaImovelFormatada.substring(0, quantidadeCaracteresImovel - 1) + "."
							+ matriculaImovelFormatada.substring(quantidadeCaracteresImovel - 1, quantidadeCaracteresImovel);
		}

		return matriculaImovelFormatada;
	}

	/**
	 * Retorna a matrícula do imóvel formatada.ex.:1234567 retorna 123456.7
	 * conforme quantidade de dígitos do parâmetro
	 * 
	 * @author Carlos Chrystian
	 */
	public static String retornaMatriculaImovelFormatadaParametrizada(Integer matriculaImovel){

		String matriculaImovelFormatada = "";

		try{
			// Recupera o parâmetro número de dígitos para matricula do imóvel na tabela de
			// parâmetros do sistema
			int numeroDigitosMatriculaImovel = Integer.valueOf(ParametroArrecadacao.P_NUMERO_DIGITOS_MATRICULA_IMOVEL.executar())
							.intValue();
			// Caso a matrícula tenha a quantidade de dígitos menor do que a cadastrada no parâmetro
			// P_NUMERO_DIGITOS_MATRICULA_IMOVEL adiciona zeros a esquerda do número
			matriculaImovelFormatada = Util.completarStringZeroEsquerda("" + matriculaImovel, numeroDigitosMatriculaImovel);

			matriculaImovelFormatada = matriculaImovelFormatada.substring(0, numeroDigitosMatriculaImovel - 1) + "."
							+ matriculaImovelFormatada.substring(numeroDigitosMatriculaImovel - 1, numeroDigitosMatriculaImovel);
		}catch(ControladorException e){
			throw new RuntimeException("erro.parametro.sistema.inscricao.imovel", e);
		}

		return matriculaImovelFormatada;
	}

	/**
	 * Retorna a matrícula do imóvel formatada.ex.:1234567 retorna 123456.7
	 * conforme quantidade de dígitos do parâmetro
	 * 
	 * @author Carlos Chrystian
	 */
	public static String retornaMatriculaImovelParametrizada(Integer matriculaImovel){

		String matriculaImovelParatrizada = "";

		try{
			// Recupera o parâmetro número de dígitos para matricula do imóvel na tabela de
			// parâmetros do sistema
			int numeroDigitosMatriculaImovel = Integer.valueOf(ParametroArrecadacao.P_NUMERO_DIGITOS_MATRICULA_IMOVEL.executar())
							.intValue();
			// Caso a matrícula tenha a quantidade de dígitos menor do que a cadastrada no parâmetro
			// P_NUMERO_DIGITOS_MATRICULA_IMOVEL adiciona zeros a esquerda do número
			matriculaImovelParatrizada = Util.completarStringZeroEsquerda("" + matriculaImovel, numeroDigitosMatriculaImovel);

		}catch(ControladorException e){
			throw new RuntimeException("erro.parametro.sistema.inscricao.imovel", e);
		}

		return matriculaImovelParatrizada;
	}
	/**
	 * Retorna uma hora no formato HH:MM a partir de um objeto Date
	 * 
	 * @author Raphael Rossiter
	 * @date 25/07/2006
	 */
	public static String formatarHoraSemSegundos(String horaMinuto){

		String retorno = null;

		if(horaMinuto != null && !horaMinuto.equalsIgnoreCase("")){

			String[] vetorHora = horaMinuto.split(":");

			if(vetorHora[0].trim().length() < 2){
				retorno = "0" + vetorHora[0] + ":";
			}else{
				retorno = vetorHora[0] + ":";
			}

			if(vetorHora[1].trim().length() < 2){
				retorno = retorno + "0" + vetorHora[1];
			}else{
				retorno = retorno + vetorHora[1];
			}
		}

		return retorno.trim();
	}

	/**
	 * Gera uma data a partir do ano/mês de referência setando o último dia do
	 * mês.
	 * Utilizado no UC0302
	 * 
	 * @author Pedro Alexandre
	 * @date 19/03/2007
	 * @param anoMesRefencia
	 * @return
	 */
	public static Date gerarDataApartirAnoMesRefencia(Integer anoMesReferencia){

		Date retorno = null;

		String dataFormatacao = "" + anoMesReferencia;

		Integer ano = Integer.valueOf(dataFormatacao.substring(0, 4));

		Integer mes = Integer.valueOf(dataFormatacao.substring(4, 6));

		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.HOUR_OF_DAY, 23);

		calendar.set(Calendar.MONTH, (mes - 1));
		calendar.set(Calendar.YEAR, ano);
		Integer dia = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DATE, dia);

		retorno = calendar.getTime();

		return retorno;
	}

	/**
	 * Gera uma data a partir do ano/mês de referência setando o primeiro dia do
	 * mês.
	 * 
	 * @author Sávio Luiz
	 * @date 19/03/2007
	 * @param anoMesRefencia
	 * @return
	 */
	public static Date gerarDataInicialApartirAnoMesRefencia(Integer anoMesReferencia){

		Date retorno = null;

		String dataFormatacao = "" + anoMesReferencia;

		Integer ano = Integer.valueOf(dataFormatacao.substring(0, 4));

		Integer mes = Integer.valueOf(dataFormatacao.substring(4, 6));

		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.SECOND, 00);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.HOUR_OF_DAY, 00);

		calendar.set(Calendar.MONTH, (mes - 1));
		calendar.set(Calendar.YEAR, ano);
		calendar.set(Calendar.DATE, 1);

		retorno = calendar.getTime();

		return retorno;
	}

	/**
	 * Formata um bigDecimal para String tirando os pontos.
	 * 
	 * @author Sávio Luiz
	 * @date 13/04/2007
	 * @param valor
	 * @return
	 */
	public static String formatarBigDecimalParaString(BigDecimal valor, int casas){

		String valorItemAnteriorString = "";
		if(valor != null){
			valor = valor.setScale(casas);
			valorItemAnteriorString = "" + valor;
			valorItemAnteriorString = valorItemAnteriorString.replace(".", "");
		}

		return valorItemAnteriorString;
	}

	/**
	 * Author: Raphael Rossiter
	 * Data: 12/04/2007
	 */
	public static Collection<Categoria> montarColecaoCategoria(Collection colecaoSubcategorias){

		Collection<Categoria> colecaoRetorno = null;

		if(colecaoSubcategorias != null && !colecaoSubcategorias.isEmpty()){

			colecaoRetorno = new ArrayList();

			Iterator colecaoSubcategoriaIt = colecaoSubcategorias.iterator();
			Categoria categoriaAnterior = null;
			Subcategoria subcategoria;
			int totalEconomiasCategoria = 0;

			while(colecaoSubcategoriaIt.hasNext()){

				subcategoria = (Subcategoria) colecaoSubcategoriaIt.next();

				if(categoriaAnterior == null){
					totalEconomiasCategoria = subcategoria.getQuantidadeEconomias();
				}else if(subcategoria.getCategoria().equals(categoriaAnterior)){
					totalEconomiasCategoria = totalEconomiasCategoria + subcategoria.getQuantidadeEconomias();
				}else{
					categoriaAnterior.setQuantidadeEconomiasCategoria(totalEconomiasCategoria);
					colecaoRetorno.add(categoriaAnterior);

					totalEconomiasCategoria = subcategoria.getQuantidadeEconomias();
				}

				categoriaAnterior = subcategoria.getCategoria();
			}

			categoriaAnterior.setQuantidadeEconomiasCategoria(totalEconomiasCategoria);
			colecaoRetorno.add(categoriaAnterior);
		}

		return colecaoRetorno;
	}

	// Formata o codigo de barra colocando os - e os espacos.
	// Flávio Cordeiro
	public static String formatarCodigoBarra(String codigoBarra){

		String retorno = "";

		retorno = codigoBarra.substring(0, 11) + "-" + codigoBarra.substring(11, 12) + " " + codigoBarra.substring(12, 23) + "-"
						+ codigoBarra.substring(23, 24) + " " + codigoBarra.substring(24, 35) + "-" + codigoBarra.substring(35, 36) + " "
						+ codigoBarra.substring(36, 47) + "-" + codigoBarra.substring(47, 48);

		return retorno;
	}

	/**
	 * Author: Rafael Pinto
	 * Formata o numero com (.) ponto
	 * Ex: Numero = 1000 Resultado = 1.000
	 * Data: 22/11/2007
	 */
	public static String agruparNumeroEmMilhares(Integer numero){

		String retorno = "0";
		if(numero != null){
			NumberFormat formato = NumberFormat.getInstance(new Locale("pt", "BR"));
			retorno = formato.format(numero);
		}
		return retorno;
	}

	/**
	 * Author: Raphael Rossiter
	 * Data: 23/08/2007
	 */
	public static java.sql.Date getSQLDate(Date data){

		java.sql.Date dt = new java.sql.Date(data.getTime());

		return dt;
	}

	/**
	 * Author: Raphael Rossiter
	 * Data: 23/08/2007
	 */
	public static Timestamp getSQLTimesTemp(Date data){

		Timestamp dt = new Timestamp(data.getTime());

		return dt;
	}

	public static String separarStringPorLetraMaiuscula(String target){

		StringBuilder builder = new StringBuilder(target);

		char[] cs = target.toCharArray();
		ArrayList<Integer> indicesMaiusculos = new ArrayList<Integer>();

		for(int i = 0; i < cs.length; i++){
			if(Character.isUpperCase(cs[i])){
				indicesMaiusculos.add(i);

			}
		}

		int i = 0;
		for(Iterator iter = indicesMaiusculos.iterator(); iter.hasNext();){
			int indice = (Integer) iter.next();
			if(indice > 0){
				builder.insert(indice + i, " ");
				i++;

			}
		}

		return builder.toString();
	}

	public static boolean validarDiaMesAnoSemBarra(String diaMesAnoReferencia){

		boolean diaMesAnoInvalido = false;

		if(diaMesAnoReferencia.length() == 8){

			String dia = diaMesAnoReferencia.substring(0, 2);
			String mes = diaMesAnoReferencia.substring(2, 4);

			try{
				int mesInt = Integer.parseInt(mes);
				// int anoInt = Integer.parseInt(ano);
				int diaInt = Integer.parseInt(dia);

				if(mesInt > 12){
					diaMesAnoInvalido = true;
				}
				if(diaInt > 31){
					diaMesAnoInvalido = true;
				}
			}catch(NumberFormatException e){
				diaMesAnoInvalido = true;
			}

		}else{
			diaMesAnoInvalido = true;
		}

		return diaMesAnoInvalido;
	}

	/**
	 * Método que recebe uma data com string no formato DDMMAAAA e converte para
	 * o objeto Date.
	 */
	public static Date converteStringSemBarraParaDate(String data){

		Date retorno = null;
		String dataInvertida = data.substring(0, 2) + "/" + data.substring(2, 4) + "/" + data.substring(4, 8);
		SimpleDateFormat dataTxt = new SimpleDateFormat("dd/MM/yyyy");
		dataTxt.setLenient(false);
		try{
			retorno = dataTxt.parse(dataInvertida);
		}catch(ParseException e){
			throw new IllegalArgumentException(data + " não tem o formato dd/MM/yyyy.");
		}
		return retorno;
	}

	/**
	 * Retorna o valor de cnpjFormatado
	 * 
	 * @return O valor de cnpjFormatado
	 */
	public static String formatarCnpj(String cnpj){

		String cnpjFormatado = cnpj;
		String zeros = "";

		if(cnpjFormatado != null){

			for(int a = 0; a < (14 - cnpjFormatado.length()); a++){
				zeros = zeros.concat("0");
			}
			// concatena os zeros ao numero
			// caso o numero seja diferente de nulo
			cnpjFormatado = zeros.concat(cnpjFormatado);

			cnpjFormatado = cnpjFormatado.substring(0, 2) + "." + cnpjFormatado.substring(2, 5) + "." + cnpjFormatado.substring(5, 8) + "/"
							+ cnpjFormatado.substring(8, 12) + "-" + cnpjFormatado.substring(12, 14);
		}

		return cnpjFormatado;
	}

	/**
	 * Retorna o valor de uma Prestacao a partir de um determinado valor. Caso a divisão não seja
	 * exata,
	 * inclui o resíduo na última parcela.
	 * 
	 * @param valorTotal
	 * @param totalPrestacao
	 * @param numeroPrestacao
	 * @return
	 */
	public static BigDecimal calcularValorPrestacao(BigDecimal valorTotal, int totalPrestacao, int numeroPrestacao){

		BigDecimal prestacao = null;
		BigDecimal saldoPrestacoes = BigDecimal.ZERO;

		if(valorTotal != null){

			prestacao = valorTotal.divide(new BigDecimal(totalPrestacao), 2, BigDecimal.ROUND_DOWN);
			// verifica se existirá residuo , caso seja última parcela, adicionar o mesmo
			if(numeroPrestacao == totalPrestacao){

				if(prestacao.compareTo(BigDecimal.ZERO) > 0){
					saldoPrestacoes = valorTotal.remainder((prestacao.multiply(new BigDecimal(totalPrestacao))), new MathContext(2,
									RoundingMode.HALF_DOWN));
				}else{
					saldoPrestacoes = valorTotal;
				}

				saldoPrestacoes = saldoPrestacoes.round(new MathContext(2, RoundingMode.HALF_DOWN));
				if(saldoPrestacoes.doubleValue() > 0){
					prestacao = prestacao.add(saldoPrestacoes);
				}
			}
		}else{
			prestacao = BigDecimal.ZERO;
		}

		return prestacao;
	}

	/**
	 * verifica a data do evento, caso ocorra entre 0:00 e 5:59, considera-se d-1 . Utilizado em
	 * métodos da Contabilização por Evento
	 * 
	 * @return Date - data do Evento para contabilização
	 */
	public static Date obterDataEventoContabilizacao(){

		Calendar dataAtual = GregorianCalendar.getInstance();
		dataAtual.setLenient(false);

		int horaDataEvento = dataAtual.get(Calendar.HOUR_OF_DAY);
		int minutoDataEvento = dataAtual.get(Calendar.MINUTE);
		String horaMinutoDataEvento = (horaDataEvento < 10 ? ("0" + horaDataEvento) : ("" + horaDataEvento)) + ":"
						+ (minutoDataEvento < 10 ? ("0" + minutoDataEvento) : ("" + minutoDataEvento));

		if(Util.compararHoraMinuto(horaMinutoDataEvento, "00:00", ">") && Util.compararHoraMinuto(horaMinutoDataEvento, "06:00", "<")){
			dataAtual.add(Calendar.DAY_OF_MONTH, -1);
		}

		// soma mais 1 ao mês, pois método Criar Data diminui o valor do mês
		return criarData(dataAtual.get(Calendar.DAY_OF_MONTH), (dataAtual.get(Calendar.MONTH) + 1), dataAtual.get(Calendar.YEAR));
	}

	/**
	 * Obter Dígito Verificador Módulo ADA Author : Vitor Hora
	 * Data : 05/08/2008
	 * Calcula o dígito verificador do código de barras no módulo ada
	 * 
	 * @param numero
	 *            Número do código de barra para calcular o dígito veficador de acordo com empresa
	 * @return digito verificador do módulo ada
	 */
	public static Integer obterDigitoVerificadorModulo(String numero, Short empresa){

		// =====================================================
		// Luciano Galvão - 28/06/2012
		// Código comentado pois está idêntico ao trecho abaixo
		// Por isto, o If não é necessário
		// =====================================================
		// ADA
		// if(Short.parseShort(ConstantesAplicacao.get("empresa.ada_codigo_empresa")) == empresa){
		// String entradaString = adicionarZerosEsquedaNumero(6, numero);
		//
		// Integer digitoCalculo = (Integer.valueOf(entradaString.substring(0, 1)) * 35)
		// + (Integer.valueOf(entradaString.substring(1, 2)) * 31) +
		// (Integer.valueOf(entradaString.substring(2, 3)) * 29)
		// + (Integer.valueOf(entradaString.substring(3, 4)) * 23) +
		// (Integer.valueOf(entradaString.substring(4, 5)) * 19)
		// + (Integer.valueOf(entradaString.substring(5, 6)) * 17);
		//
		// Integer resultado = digitoCalculo / 11;
		// Integer restoDigito = digitoCalculo - (resultado * 11);
		//
		// if(restoDigito > 9){
		// restoDigito = 0;
		// }
		//
		// return restoDigito;
		//
		// // Qualquer outra empresa
		// }else{

		String entradaString = adicionarZerosEsquedaNumero(6, numero);

		Integer digitoCalculo = (Integer.valueOf(entradaString.substring(0, 1)) * 35)
						+ (Integer.valueOf(entradaString.substring(1, 2)) * 31) + (Integer.valueOf(entradaString.substring(2, 3)) * 29)
						+ (Integer.valueOf(entradaString.substring(3, 4)) * 23) + (Integer.valueOf(entradaString.substring(4, 5)) * 19)
						+ (Integer.valueOf(entradaString.substring(5, 6)) * 17);

		Integer resultado = digitoCalculo / 11;
		Integer restoDigito = digitoCalculo - (resultado * 11);

		if(restoDigito > 9){
			restoDigito = 0;
		}

		return restoDigito;

		// }

	}

	public static String formatarNumeroInteiro(int valor){

		String numeroFormatado = "";
		if(valor != 0){
			numeroFormatado = NumberFormat.getNumberInstance(new Locale("pt-BR")).format(valor);
		}
		return numeroFormatado;

	}

	public static void validarIntervaloDatas(String dataInicial, String dataFinal){

		if((dataInicial.trim().length() == 10) && (dataFinal.trim().length() == 10)){
			Date dtCriacaoInicial = Util.converteStringParaDate(dataInicial);
			Date dtCriacaoFinal = Util.converteStringParaDate(dataFinal);

			if(dtCriacaoFinal.compareTo(dtCriacaoInicial) < 0){
				throw new ActionServletException("atencao.data.intervalo.invalido");
			}
		}
	}

	/**
	 * Método retorna true se a coleção for nula ou vazia
	 * 
	 * @author Marlon Patrick
	 * @date 11/08/2009
	 */
	public static boolean isVazioOrNulo(Collection<? extends Object> colecao){

		return (colecao == null || colecao.isEmpty());
	}

	/**
	 * Método retorna true se o array for nulo ou vazio
	 * 
	 * @author Marlon Patrick
	 * @date 11/08/2009
	 */
	public static boolean isVazioOrNulo(Object[] array){

		return (array == null || array.length == 0);
	}

	/**
	 * Método retorna true se a String for numérica (Integer)
	 * 
	 * @author Saulo Lima
	 * @date 02/07/2010
	 */
	public static boolean isInteger(String string){

		boolean retorno = false;

		Integer inteiro = converterStringParaInteger(string);

		if(inteiro != null){
			retorno = true;
		}

		return retorno;
	}

	/**
	 * Inserir caractere separador do dígito.
	 * Ex.: Recebe a String "424632" e retorna "42463-2"
	 * 
	 * @date 27/20/2008
	 * @author Saulo Lima
	 * @param String
	 *            Numeração sem caractere separador
	 * @return String
	 *         Numeração cem caractere separador
	 */
	public static String inserirCaractereSeparador(String numero){

		int tamanho = numero.length();
		String parte1 = numero.substring(0, tamanho - 1);
		String parte2 = numero.substring(tamanho - 1, tamanho);

		return parte1 + "-" + parte2;

	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param n
	 *            Descrição do parâmetro
	 * @param d
	 *            Descrição do parâmetro
	 * @param formaArredondamento
	 *            baseado nas opcoes da classe BigDecimal
	 * @return Descrição do retorno
	 */
	public static int dividirArredondarResultado(int n, int d, int formaArrendondamento){

		int retorno = 0;

		BigDecimal numerador = new BigDecimal(n);
		BigDecimal denominador = new BigDecimal(d);

		if(denominador.intValue() != 0){
			BigDecimal resultado = numerador.divide(denominador, formaArrendondamento);

			retorno = resultado.intValue();
		}

		return retorno;
	}

	/**
	 * Retorna o valor de cnpjFormatado
	 * 
	 * @return O valor de cnpjFormatado
	 */
	public static String formatarCpf(String cpf){

		String cpfFormatado = cpf;
		String zeros = "";

		if(cpfFormatado != null){

			for(int a = 0; a < (11 - cpfFormatado.length()); a++){
				zeros = zeros.concat("0");
			}
			// concatena os zeros ao numero
			// caso o numero seja diferente de nulo
			cpfFormatado = zeros.concat(cpfFormatado);

			cpfFormatado = cpfFormatado.substring(0, 3) + "." + cpfFormatado.substring(3, 6) + "." + cpfFormatado.substring(6, 9) + "-"
							+ cpfFormatado.substring(9, 11);
		}

		return cpfFormatado;
	}

	/**
	 * Converte a data passada em string retorna AAAAMMDD
	 * 
	 * @author: Thiago Toscano
	 * @date: 13/02/2008
	 * @param data
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public static String formatarDataAAAAMMDD(Date data){

		String retorno = "";
		if(data != null){ // 1
			Calendar dataCalendar = new GregorianCalendar();
			StringBuffer dataBD = new StringBuffer();

			dataCalendar.setTime(data);

			dataBD.append(dataCalendar.get(Calendar.YEAR));
			// dataBD.append("-");

			// Obs.: Janeiro no Calendar é mês zero
			if((dataCalendar.get(Calendar.MONTH) + 1) > 9){
				dataBD.append(dataCalendar.get(Calendar.MONTH) + 1);
			}else{
				dataBD.append("0" + (dataCalendar.get(Calendar.MONTH) + 1));
			}
			// dataBD.append("-");
			if(dataCalendar.get(Calendar.DAY_OF_MONTH) > 9){
				dataBD.append(dataCalendar.get(Calendar.DAY_OF_MONTH));
			}else{
				dataBD.append("0" + dataCalendar.get(Calendar.DAY_OF_MONTH));
			}

			retorno = dataBD.toString();
		}

		return retorno;
	}

	/**
	 * Método que recebe uma String e tira os ultimos X caracteres da mesma.
	 * Onde X é o valor informado como parametro.
	 * 
	 * @autor Rhawi Dantas
	 * @date 07/01/2006
	 */
	public static String removerUltimosCaracteres(String hql, int valor){

		return hql.substring(0, hql.length() - valor);
	}

	/**
	 * Gera datas a partir da quantidade de dias informado.
	 * 
	 * @author Yara Souza
	 * @date 16/09/2010
	 */
	public static Collection gerarDatasApartirNumeroDias(Date data, Integer numeroDias){

		Collection colecao = new ArrayList();
		for(int i = numeroDias - 1; i >= 0; i--){
			Date dataRetorno = adicionarNumeroDiasDeUmaData(data, i);
			colecao.add(formatarData(dataRetorno));
		}

		return colecao;
	}

	/**
	 * Gera datas a partir da quantidade de dias informado.
	 * 
	 * @author Yara Souza
	 * @date 16/09/2010
	 */
	public static Collection gerarDatasApartirNumeroDiasMes(Date data, Integer numeroDias, Integer mes){

		Collection colecao = new ArrayList();
		for(int i = numeroDias - 1; i >= 0; i--){
			Date dataRetorno = adicionarNumeroDiasDeUmaData(data, i);
			if(getMes(dataRetorno) == mes){
				colecao.add(dataRetorno);
			}

		}

		return colecao;
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param data
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public static String formatarDataHHMM(Date data){

		StringBuffer dataBD = new StringBuffer();

		if(data != null){
			Calendar dataCalendar = new GregorianCalendar();

			dataCalendar.setTime(data);

			if(dataCalendar.get(Calendar.HOUR_OF_DAY) > 9){
				dataBD.append(dataCalendar.get(Calendar.HOUR_OF_DAY));
			}else{
				dataBD.append("0" + dataCalendar.get(Calendar.HOUR_OF_DAY));
			}

			if(dataCalendar.get(Calendar.MINUTE) > 9){
				dataBD.append(dataCalendar.get(Calendar.MINUTE));
			}else{
				dataBD.append("0" + dataCalendar.get(Calendar.MINUTE));
			}
		}

		return dataBD.toString();
	}

	/**
	 * Método que acrescenta quantidade de meses a uma data.
	 * 
	 * @param data
	 * @param qtdMeses
	 * @return
	 */
	public static Date somaMesData(Date data, int qtdMeses){

		return somaMesData(data, qtdMeses, false);
	}

	/**
	 * Método que acrescenta quantidade de meses a uma data.
	 * 
	 * @param data
	 * @param qtdMeses
	 * @param verificarUltimoDiaMes
	 *            <true> Neste caso, se o dia da data informada for maior que o último dia do mês
	 *            após soma da qtd de meses,
	 *            será considerado o último dia do mês após soma da qtd de meses
	 *            <false> Caso não seja necessário verificar.
	 * @return
	 */
	public static Date somaMesData(Date data, int qtdMeses, boolean verificarUltimoDiaMes){

		Calendar c = GregorianCalendar.getInstance();

		c.setTime(data);
		int dia = c.get(Calendar.DAY_OF_MONTH);
		int mes = c.get(Calendar.MONTH) + 1;
		int ano = c.get(Calendar.YEAR);

		mes = mes + qtdMeses;
		if(mes > 12){
			while(mes > 12){
				mes = mes - 12;
				ano = ano + 1;
			}
		}

		// Se for para verificar o último dia do mês e o dia da data em questão for maior que 28.
		// Caso o dia seja menor que 28, esta verificação não se faz necessária, pois não há um mês
		// que tenha menos que 28 dias.
		if(verificarUltimoDiaMes && dia > 28){
			String ultimoDiaMesStr = obterUltimoDiaMes(mes, ano);
			int ultimoDiaMes = 0;

			if(!Util.isVazioOuBranco(ultimoDiaMesStr)){
				ultimoDiaMes = Integer.parseInt(ultimoDiaMesStr);

				if(ultimoDiaMes > 0 && ultimoDiaMes < dia){
					dia = ultimoDiaMes;
				}
			}
		}

		c.set(Calendar.DAY_OF_MONTH, dia);
		c.set(Calendar.MONTH, mes - 1);
		c.set(Calendar.YEAR, ano);

		return c.getTime();
	}

	/**
	 * Método utilizado para calcular a diferença de tempo (em minutos, segundos e milésimos) entre
	 * o parâmetro passado o tempo neste instante.
	 * 
	 * @author Saulo Lima
	 * @date 20/05/2010
	 * @param tempoInicio
	 * @return String (mm:ss:mmm)
	 */
	public static String calcularDiferencaTempo(Calendar tempoInicio){

		String retorno = "";

		if(tempoInicio != null){

			Calendar tempoFim = Calendar.getInstance();

			// Calcula a diferença entre os tempos
			long diferencaSegundos = (tempoFim.getTimeInMillis() - tempoInicio.getTimeInMillis()) / 1000;
			long diferencaMili = (tempoFim.getTimeInMillis() - tempoInicio.getTimeInMillis()) % 1000;

			// Formata o tempo de retorno
			retorno = String.format("%02d:%02d:%03d", diferencaSegundos / 60, diferencaSegundos % 60, diferencaMili);
		}

		return retorno;
	}

	/**
	 * Método responsável por quebrar valor de linha do arquivo.
	 * 
	 * @autor Yara Souza
	 * @param posicaoInicial
	 * @param tamanho
	 * @return String
	 */
	public static String getConteudo(int posicaoInicial, int tamanho, char[] registro){

		String retorno = "";
		while(retorno.length() != tamanho){
			retorno = retorno + registro[(retorno.length() + posicaoInicial) - 1];
		}
		return retorno;
	}

	/**
	 * Valida se um conteúdo passado só contém algarismo de 0 à 9.
	 * 
	 * @author isilva
	 * @param numero
	 *            Conteúdo que será validado.
	 * @param comQuantidadeMaxima
	 *            Informa se a validação será com valor máximo ou sem limite de algarismos <br>
	 *            true - Com valor mínimo. Deve-se informar o valor no parametro quantidadeMinima. <br>
	 *            false - Sem valor mínimo. Nesse caso o número não tem limite de algarismos. <br>
	 * @param quantidadeMaxima
	 *            Quantidade Máxima de algarismos que o número deverá ter. <br>
	 *            0 - Por default quando: comQuantidadeMinima == false.
	 * @return
	 */
	public static boolean isNumero(String numero, boolean comQuantidadeMaxima, int quantidadeMaxima){

		String regex = "[0-9]{1,";

		if(comQuantidadeMaxima){
			if(numero.length() > quantidadeMaxima){
				return false;
			}
			regex += "" + quantidadeMaxima;
		}

		regex += "}";

		Matcher m;
		m = Pattern.compile(regex).matcher(numero);
		return m.matches();
	}

	/**
	 * Methodo que valida se um objeto String ou ArrayList são vazios
	 * 
	 * @author isilva
	 * @param form
	 * @param nome
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isVazioOuBranco(Object object){

		boolean retorno = false;

		if(object == null) return true;

		if(object instanceof String){
			String conteudo = ((String) object).trim();
			if(conteudo.length() == 0 || "".equalsIgnoreCase(conteudo)){
				return true;
			}
		}else if(object instanceof ArrayList){
			ArrayList conteudo = (ArrayList) object;
			if(conteudo.isEmpty()){
				return true;
			}
		}

		return retorno;
	}

	public static long calcularDiferencaDeAno(Date dataInicial, Date dataFinal){

		Calendar dataInicialCalendar = Calendar.getInstance();
		dataInicialCalendar.setTime(dataInicial);

		Calendar dataFinalCalendar = Calendar.getInstance();
		dataFinalCalendar.setTime(dataFinal);

		return dataFinalCalendar.get(Calendar.YEAR) - dataInicialCalendar.get(Calendar.YEAR);
	}

	/**
	 * Adiciona zeros a esqueda do parametro "stringOriginal" caso o número de caracteres de
	 * "stringOriginal" seja menor que "tamanhoMinimoString"
	 * 
	 * @param tamanhoMinimoString
	 *            Descrição do parâmetro
	 * @param numero
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public static String adicionarZerosEsqueda(int tamanhoMinimoString, String stringOriginal){

		StringBuffer stringNova = new StringBuffer();

		if(stringOriginal != null && !"".equals(stringOriginal) && tamanhoMinimoString > 0){
			if(stringOriginal.length() < tamanhoMinimoString){
				for(int x = 0; x < (tamanhoMinimoString - stringOriginal.length()); x++){
					stringNova.append("0");
				}
				stringNova.append(stringOriginal);
			}else{
				return stringOriginal;
			}
			return stringNova.toString();
		}else{
			return stringOriginal;
		}
	}

	/**
	 * Remove zeros a esquerda do parametro "stringOriginal" caso venham zeros antes de outros
	 * caracteres.
	 * 
	 * @author Saulo Lima
	 * @date 17/07/2012
	 * @author Thiago Santos
	 * @date 19/09/2012
	 * @param stringOriginal
	 * @return String original sem os zeros existentes a esquerda
	 */
	public static String removerZerosEsquerda(String stringOriginal){

		if(stringOriginal == null){
			return stringOriginal;
		}
		String normalizada = stringOriginal.trim();
		int index = 0;
		// varre até encontrar algo diferente de zero
		while(index < normalizada.length() && normalizada.charAt(index++) == '0')
			;
		// se não houver zeros a esqueda a string deve ser a partir de zero e não index-1
		return normalizada.substring(Math.max(0, index - 1));
	}

	public static long calcularDiferencaDeMes(Date dataInicial, Date dataFinal){

		Calendar dataInicialCalendar = Calendar.getInstance();
		dataInicialCalendar.setTime(dataInicial);

		Calendar dataFinalCalendar = Calendar.getInstance();
		dataFinalCalendar.setTime(dataFinal);

		return dataFinalCalendar.get(Calendar.MONTH) - dataInicialCalendar.get(Calendar.MONTH)
						+ (calcularDiferencaDeAno(dataInicial, dataFinal) * 12);
	}

	/**
	 * Método que recebe uma data com string no formato dd/MM/yyyy e verifica se é uma data valida.
	 * 
	 * @author isilva
	 * @param data
	 * @autor Thiago Toscano
	 * @date 28/07/2010
	 * @return
	 */
	public static boolean validaDataLinear(String data){

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		format.setLenient(false);

		try{
			format.parse(data);
			return true;
		}catch(ParseException e){
			return false;
		}
	}

	/**
	 * Formata uma data em MM/yyyy
	 * Ex: 01/02/2010, será retornado: 02/2010
	 * 
	 * @author isilva
	 * @param data
	 * @return
	 */
	public static String formataMesAno(Date data){

		Calendar dataCalendar = Calendar.getInstance();
		dataCalendar.setTime(data);

		String dataFormatada = formatarData(dataCalendar.getTime());
		return dataFormatada.substring(3);
	}

	/**
	 * @author isilva
	 * @param valor
	 * @return
	 */
	public static String converteBigDecimal(double valor){

		DecimalFormat numberFormat = new DecimalFormat("#,##0.00");
		numberFormat.setParseBigDecimal(true);
		numberFormat.setDecimalSeparatorAlwaysShown(true);
		numberFormat.setMinimumFractionDigits(2);
		numberFormat.setMaximumFractionDigits(2);
		return numberFormat.format(valor);
	}

	public static long diferencaDias(Date dataInical, Date dataFinal){

		Calendar dataInicialCalendar = Calendar.getInstance();
		dataInicialCalendar.setTime(dataInical);

		Calendar dataFinalCalendar = Calendar.getInstance();
		dataFinalCalendar.setTime(dataFinal);

		long r = dataFinalCalendar.getTime().getTime() - dataInicialCalendar.getTime().getTime();
		long qtdDias = r / (1000 * 60 * 60 * 24);

		return qtdDias;
	}

	/**
	 * Adiciona os parametros na query. Os tipos de dados podem ser: <br>
	 * <br>
	 * String[], String, Integer[], Integer, Date[], Date, Double[], Double, BigDecimal[],
	 * BigDecimal, Short[], Short, Set, Collection, Boolean[],
	 * Boolean, Float[], Float, Long[], Long, BigInteger[], BigInteger, Calendar[], Calendar,
	 * Byte[], Byte, Character[], Character <br>
	 * <br>
	 * 
	 * @author isilva
	 * @param query
	 *            HQL que será pesquisado
	 * @param parameters
	 *            Parametros da query
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Query configuraParamentrosEmQuery(Query query, Map parameters){

		Query novaQuery = query;

		if(parameters != null && !parameters.isEmpty() && parameters.size() > 0){

			Set set = parameters.keySet();
			Iterator iterMap = set.iterator();

			while(iterMap.hasNext()){
				String key = (String) iterMap.next();

				if(parameters.get(key) instanceof String[]){
					String[] collection = (String[]) parameters.get(key);
					novaQuery.setParameterList(key, collection);
				}else if(parameters.get(key) instanceof String){
					String parametroString = (String) parameters.get(key);
					novaQuery.setParameter(key, parametroString);
				}else if(parameters.get(key) instanceof Integer[]){
					Integer[] collection = (Integer[]) parameters.get(key);
					novaQuery.setParameterList(key, collection);
				}else if(parameters.get(key) instanceof Integer){
					Integer parametroInteger = (Integer) parameters.get(key);
					novaQuery.setParameter(key, parametroInteger);
				}else if(parameters.get(key) instanceof Date[]){
					Date[] parametroDate = (Date[]) parameters.get(key);
					novaQuery.setParameterList(key, parametroDate);
				}else if(parameters.get(key) instanceof Date){
					Date parametroDate = (Date) parameters.get(key);
					novaQuery.setParameter(key, parametroDate);
				}else if(parameters.get(key) instanceof Double[]){
					Double[] parametroDouble = (Double[]) parameters.get(key);
					novaQuery.setParameterList(key, parametroDouble);
				}else if(parameters.get(key) instanceof Double){
					Double parametroDouble = (Double) parameters.get(key);
					novaQuery.setParameter(key, parametroDouble);
				}else if(parameters.get(key) instanceof BigDecimal[]){
					BigDecimal[] bigDecimalParameter = (BigDecimal[]) parameters.get(key);
					novaQuery.setParameterList(key, bigDecimalParameter);
				}else if(parameters.get(key) instanceof BigDecimal){
					BigDecimal bigDecimalParameter = (BigDecimal) parameters.get(key);
					novaQuery.setParameter(key, bigDecimalParameter);
				}else if(parameters.get(key) instanceof Short[]){
					Short[] parametroShort = (Short[]) parameters.get(key);
					novaQuery.setParameterList(key, parametroShort);
				}else if(parameters.get(key) instanceof Short){
					Short parametroShort = (Short) parameters.get(key);
					novaQuery.setParameter(key, parametroShort);
				}else if(parameters.get(key) instanceof Set){
					Set setList = (HashSet) parameters.get(key);
					novaQuery.setParameterList(key, setList);
				}else if(parameters.get(key) instanceof Collection){
					Collection collection = (ArrayList) parameters.get(key);
					novaQuery.setParameterList(key, collection);
				}else if(parameters.get(key) instanceof Boolean[]){
					Boolean[] booleanParameter = (Boolean[]) parameters.get(key);
					novaQuery.setParameterList(key, booleanParameter);
				}else if(parameters.get(key) instanceof Boolean){
					Boolean booleanParameter = (Boolean) parameters.get(key);
					novaQuery.setParameter(key, booleanParameter);
				}else if(parameters.get(key) instanceof Float[]){
					Float[] parametroFloat = (Float[]) parameters.get(key);
					novaQuery.setParameterList(key, parametroFloat);
				}else if(parameters.get(key) instanceof Float){
					Float parametroFloat = (Float) parameters.get(key);
					novaQuery.setParameter(key, parametroFloat);
				}else if(parameters.get(key) instanceof Long[]){
					Long[] parametroLong = (Long[]) parameters.get(key);
					novaQuery.setParameterList(key, parametroLong);
				}else if(parameters.get(key) instanceof Long){
					Long parametroLong = (Long) parameters.get(key);
					novaQuery.setParameter(key, parametroLong);
				}else if(parameters.get(key) instanceof BigInteger[]){
					BigInteger[] parametroBigInteger = (BigInteger[]) parameters.get(key);
					novaQuery.setParameterList(key, parametroBigInteger);
				}else if(parameters.get(key) instanceof BigInteger){
					BigInteger parametroBigInteger = (BigInteger) parameters.get(key);
					novaQuery.setParameter(key, parametroBigInteger);
				}else if(parameters.get(key) instanceof Calendar[]){
					Calendar[] parametroCalendar = (Calendar[]) parameters.get(key);
					novaQuery.setParameterList(key, parametroCalendar);
				}else if(parameters.get(key) instanceof Calendar){
					Calendar parametroCalendar = (Calendar) parameters.get(key);
					novaQuery.setParameter(key, parametroCalendar);
				}else if(parameters.get(key) instanceof Byte[]){
					Byte[] byteParameter = (Byte[]) parameters.get(key);
					novaQuery.setParameterList(key, byteParameter);
				}else if(parameters.get(key) instanceof Byte){
					Byte byteParameter = (Byte) parameters.get(key);
					novaQuery.setParameter(key, byteParameter);
				}else if(parameters.get(key) instanceof Character[]){
					Character[] characterParameter = (Character[]) parameters.get(key);
					novaQuery.setParameterList(key, characterParameter);
				}else if(parameters.get(key) instanceof Character){
					Character characterParameter = (Character) parameters.get(key);
					novaQuery.setParameter(key, characterParameter);
				}else{
					novaQuery.setParameter(key, parameters.get(key));
				}
			}
		}

		return novaQuery;
	}

	/**
	 * @author isilva
	 * @param colecao
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Collection dividirColecaoOrdenando(Collection colecao){

		List retorno = new ArrayList();
		List colecaoTmp = (List) colecao;
		int quantidadeItens = 0;
		int tamanhoColecao = colecaoTmp.size();
		int metadeColecao = 0;
		if(tamanhoColecao % 2 == 0){
			metadeColecao = tamanhoColecao / 2;
		}else{
			metadeColecao = (tamanhoColecao / 2) + 1;
		}
		while(quantidadeItens < metadeColecao){
			retorno.add(colecaoTmp.get(quantidadeItens));
			if(metadeColecao + quantidadeItens < tamanhoColecao){
				retorno.add(colecaoTmp.get(metadeColecao + quantidadeItens));
			}
			quantidadeItens++;
		}
		tamanhoColecao = 0;

		return retorno;
	}

	/**
	 * @author isilva
	 * @param lista
	 * @param pag
	 * @param qtd
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List obterSubListParaPaginacao(List lista, int pag, int qtd){

		int deIndice = pag * qtd;
		int paraIndice = deIndice + qtd;

		if(paraIndice > lista.size()){
			paraIndice = lista.size();
		}

		// subList(inclusive, exclusive)
		return lista.subList(deIndice, paraIndice);
	}

	public static boolean verificarIdNaoVazio(String id){

		if(id == null || id.equals("null") || id.trim().equals("") || id.trim().equals(ConstantesSistema.NUMERO_NAO_INFORMADO + "")){
			return false;
		}
		return true;
	}

	public static boolean verificarNaoVazio(String valor){

		if(valor == null || valor.trim().equals("")){
			return false;
		}
		return true;
	}

	/**
	 * Author: Vinicius Medeiros Data: 11/02/2009 Formatar CEP
	 */
	public static String formatarCEP(String codigo){

		String retornoCEP = null;

		String parte1 = codigo.substring(0, 2);
		String parte2 = codigo.substring(2, 5);
		String parte3 = codigo.substring(5, 8);

		retornoCEP = parte1 + "." + parte2 + "-" + parte3;

		return retornoCEP;
	}

	/**
	 * Author: Carlos Chrystian Data: 03/01/20012
	 * Formatar CEP sem ponto e com Traço
	 */
	public static String formatarCEPSemPonto(String codigo){

		String retornoCEP = null;

		String parte1 = codigo.substring(0, 5);
		String parte2 = codigo.substring(5, 8);

		retornoCEP = parte1 + "-" + parte2;

		return retornoCEP;
	}

	/**
	 * Author: Vinicius Medeiros Data: 11/02/2009 Retirar formatacao CEP
	 */
	public static String retirarFormatacaoCEP(String codigo){

		String retornoCEP = null;

		String parte1 = codigo.substring(0, 2);
		String parte2 = codigo.substring(3, 6);
		String parte3 = codigo.substring(7, 10);

		retornoCEP = parte1 + parte2 + parte3;

		return retornoCEP;
	}

	/**
	 * Método que recebe um arquivo e retorna a extensão do mesmo.
	 * 
	 * @author Raphael Rossiter
	 * @date 29/07/2009
	 * @param FileItem
	 * @return String
	 */
	public static String obterExtensaoDoArquivo(FileItem fileItem){

		String extensao = null;

		String nomeArquivo = fileItem.getName().toUpperCase();
		String[] nomeArquivoPartido = nomeArquivo.split("\\.");

		if(nomeArquivoPartido[1] != null){
			extensao = nomeArquivoPartido[1];
		}

		return extensao;
	}

	/**
	 * @author isilva
	 * @param valor
	 * @param quantidadeCasaDecimais
	 * @return
	 */
	public static BigDecimal converterEmPercentagem(String valor, int quantidadeCasaDecimais){

		BigDecimal cem = new BigDecimal("100");
		BigDecimal valorBigDecimal = new BigDecimal(valor);

		BigDecimal resultado = null;

		if(valorBigDecimal != null && cem != null){
			resultado = valorBigDecimal.divide(cem, quantidadeCasaDecimais, BigDecimal.ROUND_HALF_UP);
		}

		return resultado;
	}

	/**
	 * Método que recebe uma string ex."123456" e converte para o objeto
	 * BigDecimal ex. "12.3456" colocando o valor de Decimais passadas
	 * OBS1 : Valor só considerará decimais, se for encontrado ',' ou '.' antes das últimas
	 * posições da String, baseado na qtd de decimais.
	 * OBS2 : Pode retornar null
	 * 
	 * @author isilva
	 * @date 03/12/2010
	 * @return
	 */
	public static BigDecimal formatarStringMoedaRealparaBigDecimal(String valor, int decimais){

		BigDecimal bigDecimalFormatado = null;

		if(valor != null && !valor.equalsIgnoreCase("")){
			boolean temCasaDecimal = false;
			if(valor.length() > decimais
							&& (valor.substring(valor.length() - (decimais + 1), valor.length() - decimais).equals(".") || valor.substring(
											valor.length() - (decimais + 1), valor.length() - decimais).equals(","))){
				temCasaDecimal = true;
			}
			// retira pontos e vírgulas
			if(temCasaDecimal){
				if(valor.indexOf(".") >= 0 || valor.indexOf(",") >= 0){
					valor = valor.replaceAll("\\p{Punct}", "");

				}
			}

			bigDecimalFormatado = new BigDecimal(valor);
			if(temCasaDecimal){
				bigDecimalFormatado = bigDecimalFormatado.movePointLeft(decimais);
			}
		}else{
			bigDecimalFormatado = null;
		}

		return bigDecimalFormatado;
	}

	/**
	 * Método criado para padrão código de barras Int2of5
	 * The Interleave 2 of 5
	 * Esse padrão de código de barras, só aceita número par.
	 * 
	 * @param numero
	 * @return
	 */

	public static String formatarParaCodigoBarrasInt2Of5(String numero){

		String retorno = "";
		if(numero.length() % 2 != 0){
			retorno = "0" + numero;
		}else{
			retorno = numero;
		}

		return retorno;
	}

	public static Date zerarHoraMinutoSegundo(Date data){

		return ajustarHoraMinutoSegundo(data, 0, 0, 0, 0);
	}

	public static Date ajustarHoraMinutoSegundo(Date data, int hora, int minuto, int segundo, int miliseg){

		Calendar dataCalendar = GregorianCalendar.getInstance();

		dataCalendar.setTime(data);
		dataCalendar.set(Calendar.HOUR_OF_DAY, hora);
		dataCalendar.set(Calendar.MINUTE, minuto);
		dataCalendar.set(Calendar.SECOND, segundo);
		dataCalendar.set(Calendar.MILLISECOND, miliseg);

		return dataCalendar.getTime();
	}

	/**
	 * Retorna 0 se null ou vázio
	 * 
	 * @author Péricles Tavares
	 * @param valor
	 * @return
	 */
	public static Short obterShort(String valor){

		Short retorno = null;
		try{
			retorno = (valor == null || valor.length() == 0) ? 0 : Short.valueOf(valor);
		}catch(NumberFormatException e){
			e.printStackTrace();
		}

		return retorno;
	}

	/**
	 * Retorna 0 se null ou vazio
	 * 
	 * @author isilva
	 * @param valor
	 * @return
	 */
	public static Integer obterInteger(String valor){

		Integer retorno = null;
		try{
			retorno = (valor == null || valor.length() == 0) ? 0 : Integer.valueOf(valor);
		}catch(NumberFormatException e){
			e.printStackTrace();
		}

		return retorno;
	}

	public static Integer[] toArrayInteger(String[] arrayString){

		Integer[] arrayInteger = new Integer[arrayString.length];
		for(int i = 0; i < arrayString.length; i++){
			arrayInteger[i] = Integer.valueOf(arrayString[i]);
		}
		return arrayInteger;
	}

	public static List<Integer> toListInteger(String[] array){

		List<Integer> lista = new ArrayList<Integer>();

		if(array != null){

			for(String valor : array){

				if(StringUtils.isNumeric(valor.trim())){

					lista.add(Integer.valueOf(valor.trim()));

				}

			}

		}

		return lista;

	}

	/**
	 * Método que recebe uma string e converte para o objeto BigDecimal
	 * OBS: Se null ou branco, retorna 0
	 * 
	 * @author isilva
	 * @date 26/01/2011
	 * @param valor
	 * @return
	 */
	public static BigDecimal formatarStringParaBigDecimal(String valor){

		BigDecimal bigDecimalFormatado = BigDecimal.ZERO;

		if(valor != null && !valor.equalsIgnoreCase("")){
			bigDecimalFormatado = new BigDecimal(valor);
		}

		return bigDecimalFormatado;
	}

	/**
	 * Método que recebe uma string e completa com 0 a esquerda
	 * 
	 * @author Anderson Italo
	 * @date 15/02/2011
	 * @param valor
	 *            , quantidade de caracteres da String de retorno
	 * @return
	 */
	public static String completarStringZeroEsquerda(String valor, int quantidade){

		String retorno = valor;

		if(valor != null){

			if(quantidade == valor.length()){
				return retorno;
			}else if(valor.length() > quantidade){
				return valor.substring(0, quantidade);
			}else{
				for(int i = 0; i < quantidade; i++){

					if(retorno.length() == quantidade){
						break;
					}

					retorno = "0" + retorno;
				}
			}
		}else{
			retorno = "";
		}

		return retorno;
	}

	/**
	 * Repetir texto
	 * 
	 * @author Hebert Falcão
	 * @date 16/02/2011
	 * @param texto
	 *            texto que deve ser repetido
	 * @param vezes
	 *            quantidade de repetições
	 * @return texto com as repetições
	 */
	public static String repetirTexto(String texto, int vezes){

		StringBuilder result = new StringBuilder();

		for(int i = 0; i < vezes; i++){
			result.append(texto);
		}

		return result.toString();
	}

	/**
	 * Verifica se uma determinada data está em um intervalo.
	 * 
	 * @author isilva
	 * @date 07/07/2010
	 * @param dataEntre
	 *            Data que será verificada
	 * @param dataMinima
	 *            Data mínima de verificação
	 * @param dataMaxima
	 *            Data máxima de verificação
	 * @param fechadoEsquerda
	 * <br>
	 *            <code>true</code> Considera a data mínima na comparação <br>
	 *            <code>false</code> Não considera a data mínima na comparação <br>
	 * @param fechadoDireita
	 * <br>
	 *            <code>true</code> Considera a data máxima na comparação <br>
	 *            <code>false</code> Não considera a data máxima na comparação <br>
	 *            Virifica se considera a data máxima ou não na comparação
	 * @return
	 */
	public static boolean estaNoIntervalo(Date dataEntre, Date dataMinima, Date dataMaxima, boolean fechadoEsquerda, boolean fechadoDireita){

		if(fechadoEsquerda && fechadoDireita){
			return dataEntre.compareTo(dataMinima) >= 0 && dataEntre.compareTo(dataMaxima) <= 0;
		}else if(fechadoEsquerda){
			return dataEntre.compareTo(dataMinima) >= 0 && dataEntre.compareTo(dataMaxima) < 0;
		}else if(fechadoDireita){
			return dataEntre.compareTo(dataMinima) > 0 && dataEntre.compareTo(dataMaxima) <= 0;
		}

		return dataEntre.compareTo(dataMinima) > 0 && dataEntre.compareTo(dataMaxima) < 0;
	}

	/**
	 * Trunca String
	 * 
	 * @author isilva
	 * @param tamanhoMaximoCampo
	 *            Descrição do parâmetro
	 * @param str
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public static String truncarString(String str, int tamanhoMaximoCampo){

		String retorno = str;

		if(retorno != null){
			if(tamanhoMaximoCampo < str.length()){
				// trunca a String
				String strTruncado = retorno.substring(0, tamanhoMaximoCampo);
				retorno = strTruncado;
			}
		}

		return retorno;
	}

	/**
	 * Retorna a String formatada HH:MM para uma certa quantidade de minutos
	 * 
	 * @author Péricles Tavares
	 * @param qtidadeMinutos
	 * @return
	 */
	public static String obterHoraMinutos(Integer qtidadeMinutos){

		StringBuilder sb = new StringBuilder();
		if(qtidadeMinutos == null){
			sb.append("00:00");
		}else{
			sb.append(Util.adicionarZerosEsqueda(2, "" + qtidadeMinutos / 60));
			sb.append(":");
			sb.append(Util.adicionarZerosEsqueda(2, "" + qtidadeMinutos % 60));
		}

		return sb.toString();
	}

	/**
	 * Método que recebe uma string ex."123456" e converte para o objeto
	 * BigDecimal ex. "12.3456" colocando o valor de Decimais passadas
	 * Observação: se decimais for zero, retornará null
	 * 
	 * @param valor
	 *            - número que será formatado
	 * @param decimais
	 *            - quantidade de casas decimais (deverá ser maior que zero)
	 * @param zeroSeNull
	 *            - <br>
	 *            <ul>
	 *            <li>se true: retorna zero se valor for null ou vázio</li>
	 *            <li>se false: retorna null se valor for null ou vázio</li>
	 *            </ul>
	 * @author isilva
	 * @date 12/04/2011
	 * @return
	 */
	public static BigDecimal formatarStringParaBigDecimal(String valor, int decimais, boolean zeroSeNull){

		if(isVazioOuBranco(valor)){
			if(zeroSeNull){
				return BigDecimal.ZERO.setScale(decimais);
			}else{
				return null;
			}
		}

		valor = valor.replace(".", "");
		valor = valor.replace(",", ".");

		if(decimais > 0){
			return new BigDecimal(valor).setScale(decimais);
		}else{
			return null;
		}
	}

	/**
	 * Formata um bigDecimal para String.
	 * 
	 * @author isilva
	 * @date 21/03/2011
	 * @param valor
	 * @return
	 */
	public static String bigDecimalParaString(BigDecimal valor, int casasDecimais){

		if(valor == null){
			String retorno = "" + valor;

			for(int i = 0; i < casasDecimais; i++){
				retorno = "0" + retorno;

				if(casasDecimais == retorno.length()){
					break;
				}
			}

			return "0," + retorno;
		}

		String valorItemAnterior = "" + valor;
		valorItemAnterior = valorItemAnterior.replace(".", ",");
		return valorItemAnterior;
	}

	/**
	 * Retorna a diferença entre duas datas, o tipo de retorno será igual ao definido pelo
	 * paramentro: tipoComparacao. <br>
	 * <br>
	 * Não Utilizar para comparar a diferença entre Ano e Mês. Usar as CconstantesSistema: <br>
	 * <ul>
	 * <li>DIFERENCA_DIAS</li>
	 * <li>DIFERENCA_HORAS</li>
	 * <li>DIFERENCA_MINUTOS</li>
	 * <li>DIFERENCA_SEGUNDOS</li>
	 * <li>DIFERENCA_MILISEGUNDOS</li> </lu> <br>
	 * 
	 * @author isilva
	 * @param dataInicial
	 * @param dataFinal
	 * @param tipoComparacao
	 * @return
	 */
	public static BigDecimal calcularDiferencaEntreDatas(Date dataInicial, Date dataFinal, int tipoComparacao){

		BigDecimal diferenca = BigDecimal.ZERO;

		BigDecimal VINTE_E_QUATRO = new BigDecimal("24");
		BigDecimal SESSENTA = new BigDecimal("60");
		BigDecimal MIL = new BigDecimal("1000");

		Calendar dataInicialCalendar = Calendar.getInstance();
		dataInicialCalendar.setTime(dataInicial);
		dataInicialCalendar.setLenient(true);

		Calendar dataFinalCalendar = Calendar.getInstance();
		dataFinalCalendar.setTime(dataFinal);
		dataFinalCalendar.setLenient(true);

		BigDecimal diferencaMilisegundos = new BigDecimal((dataFinalCalendar.getTimeInMillis() - dataInicialCalendar.getTimeInMillis()));

		if(ConstantesSistema.DIFERENCA_DIAS == tipoComparacao){
			diferenca = diferencaMilisegundos.divide(MIL.multiply(SESSENTA.multiply(SESSENTA.multiply(VINTE_E_QUATRO))), 2,
							BigDecimal.ROUND_HALF_UP);
		}else if(ConstantesSistema.DIFERENCA_HORAS == tipoComparacao){
			diferenca = diferencaMilisegundos.divide(MIL.multiply(SESSENTA.multiply(SESSENTA)), 2, BigDecimal.ROUND_HALF_UP);
		}else if(ConstantesSistema.DIFERENCA_MINUTOS == tipoComparacao){
			diferenca = diferencaMilisegundos.divide(MIL.multiply(SESSENTA), 2, BigDecimal.ROUND_HALF_UP);
		}else if(ConstantesSistema.DIFERENCA_SEGUNDOS == tipoComparacao){
			diferenca = diferencaMilisegundos.divide(MIL, 2, BigDecimal.ROUND_HALF_UP);
		}else if(ConstantesSistema.DIFERENCA_MILISEGUNDOS == tipoComparacao){
			diferenca = diferencaMilisegundos;
		}

		return diferenca;
	}

	/**
	 * Método que converte uma String em um Long. Caso a String seja null ou
	 * não seja um número válido, retorna null.
	 * 
	 * @author Péricles Tavares
	 * @date 15/04/2011
	 * @param string
	 *            Uma string numérica
	 * @return Long Long.parseLong(string), caso a string seja um número.
	 *         null, caso contrário.
	 */
	public static Long converterStringParaLong(String string){

		Long longNumber = null;
		try{
			longNumber = Long.parseLong(string);
		}catch(NumberFormatException e){

		}
		return longNumber;
	}

	/**
	 * Método que converte um Ano/Mes em uma Data Inicial
	 * 
	 * @author Hebert Falcão
	 * @date 18/05/2011
	 * @param anoMes
	 * @return Date
	 */
	public static Date converterAnoMesParaDataInicial(String anoMes){

		// remove pontuações
		anoMes = anoMes.replaceAll("\\p{Punct}", "");

		String ano = anoMes.substring(0, 4);
		String mes = anoMes.substring(4, 6);

		Calendar calendario = new GregorianCalendar();
		calendario.set(Calendar.YEAR, Util.converterStringParaInteger(ano));
		calendario.set(Calendar.MONTH, Util.converterStringParaInteger(mes) - 1);
		calendario.set(Calendar.DATE, calendario.getActualMinimum(Calendar.DATE));

		calendario.set(Calendar.HOUR_OF_DAY, 0);
		calendario.set(Calendar.MINUTE, 0);
		calendario.set(Calendar.SECOND, 0);
		calendario.set(Calendar.MILLISECOND, 0);

		return calendario.getTime();
	}

	/**
	 * Método que converte um Ano/Mes em uma Data Final
	 * 
	 * @author Hebert Falcão
	 * @date 18/05/2011
	 * @param anoMes
	 * @return Date
	 */
	public static Date converterAnoMesParaDataFinal(String anoMes){

		// remove pontuações
		anoMes = anoMes.replaceAll("\\p{Punct}", "");

		String ano = anoMes.substring(0, 4);
		String mes = anoMes.substring(4, 6);

		Calendar calendario = new GregorianCalendar();
		calendario.set(Calendar.YEAR, Util.converterStringParaInteger(ano));
		calendario.set(Calendar.MONTH, Util.converterStringParaInteger(mes) - 1);
		calendario.set(Calendar.DATE, calendario.getActualMaximum(Calendar.DATE));

		calendario.set(Calendar.HOUR_OF_DAY, 0);
		calendario.set(Calendar.MINUTE, 0);
		calendario.set(Calendar.SECOND, 0);
		calendario.set(Calendar.MILLISECOND, 0);

		return calendario.getTime();
	}

	/**
	 * Método que converte um Ano/Mes em uma Data Final
	 * 
	 * @author Hebert Falcão
	 * @date 18/05/2011
	 * @param anoMes
	 * @return Date
	 */
	public static Date converterAnoMesParaDataFinal(String anoMes, int diasAMenos){

		// remove pontuações
		anoMes = anoMes.replaceAll("\\p{Punct}", "");

		String ano = anoMes.substring(0, 4);
		String mes = anoMes.substring(4, 6);

		Calendar calendario = new GregorianCalendar();
		calendario.set(Calendar.YEAR, Util.converterStringParaInteger(ano));
		calendario.set(Calendar.MONTH, Util.converterStringParaInteger(mes) - 1);
		calendario.set(Calendar.DATE, (calendario.getActualMaximum(Calendar.DATE) - diasAMenos));

		calendario.set(Calendar.HOUR_OF_DAY, 0);
		calendario.set(Calendar.MINUTE, 0);
		calendario.set(Calendar.SECOND, 0);
		calendario.set(Calendar.MILLISECOND, 0);

		return calendario.getTime();
	}

	public static Date converterAnoMesParaDataMenosDoisMesesFinal(String anoMes){

		// remove pontuações
		anoMes = anoMes.replaceAll("\\p{Punct}", "");

		String ano = anoMes.substring(0, 4);
		String mes = anoMes.substring(4, 6);

		Calendar calendario = new GregorianCalendar();
		calendario.set(Calendar.YEAR, Util.converterStringParaInteger(ano));
		calendario.set(Calendar.MONTH, Util.converterStringParaInteger(mes) - 2);
		calendario.set(Calendar.DATE, calendario.getActualMaximum(Calendar.DATE));

		calendario.set(Calendar.HOUR_OF_DAY, 0);
		calendario.set(Calendar.MINUTE, 0);
		calendario.set(Calendar.SECOND, 0);
		calendario.set(Calendar.MILLISECOND, 0);

		return calendario.getTime();
	}

	/**
	 * Método que passa o dia, ano e mes de uma data para a data zero (01/01/1970)
	 * 
	 * @author Hebert Falcão
	 * @date 14/06/2011
	 */
	public static Date converterDateParaDataZero(Date data){

		Calendar calendario = Calendar.getInstance();
		calendario.setTime(data);

		calendario.set(Calendar.YEAR, 1970);
		calendario.set(Calendar.MONTH, Calendar.JANUARY);
		calendario.set(Calendar.DATE, 1);

		return calendario.getTime();
	}

	/**
	 * Método que recebe um data e retorna a hora da mesma
	 * 
	 * @author Anderson Italo
	 * @date 22/07/2011
	 */
	public static int obterHoraDeDate(Date data){

		Calendar dataCalendar = GregorianCalendar.getInstance();

		dataCalendar.setTime(data);

		return dataCalendar.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * Método que recebe um data e retorna os minutos da mesma
	 * 
	 * @author Anderson Italo
	 * @date 22/07/2011
	 */
	public static int obterMinutoDeDate(Date data){

		Calendar dataCalendar = GregorianCalendar.getInstance();

		dataCalendar.setTime(data);

		return dataCalendar.get(Calendar.MINUTE);
	}

	/**
	 * Método responsável por remover alguns caracteres especiais como . - /
	 * 
	 * @deprecated Usar o metodo: substituirCaracteresEspeciais
	 * @param texto
	 *            O texto que será formatado
	 * @return O texto sem os caracteres especiais
	 */
	public static String removerCaracteresEspeciais(String texto){

		String retorno = texto;
		retorno = retorno.replace(".", "");
		retorno = retorno.replace("/", "");
		retorno = retorno.replace("-", "");
		return retorno;
	}

	/**
	 * Método que converte uma String em um Short.
	 * 
	 * @author Ailton Sousa
	 * @date 29/08/2011
	 * @param string
	 * @return
	 */
	public static Short converterStringParaShort(String string){

		Short retorno = null;
		try{
			retorno = Short.parseShort(string);
		}catch(NumberFormatException e){

		}
		return retorno;
	}

	public static byte[] getBytesFromFile(File file) throws IOException{

		InputStream is = new FileInputStream(file);

		// Get the size of the file
		long length = file.length();

		if(length > Integer.MAX_VALUE){
			// File is too large
		}

		// Create the byte array to hold the data
		byte[] bytes = new byte[(int) length];

		// Read in the bytes
		int offset = 0;
		int numRead = 0;
		while(offset < bytes.length && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0){
			offset += numRead;
		}

		// Ensure all the bytes have been read in
		if(offset < bytes.length){
			throw new IOException("Could not completely read file " + file.getName());
		}

		// Close the input stream and return bytes
		is.close();
		return bytes;
	}

	public byte[] getBytesFromFileZip(StringBuffer sb, String nomeArquivo, EnvioEmail envioEmail) throws IOException, Exception{

		byte[] retorno = null;
		ZipOutputStream zos = null;
		BufferedWriter out = null;
		File leituraTipo = new File(nomeArquivo);
		File compactado = new File(nomeArquivo + ".zip");

		zos = new ZipOutputStream(new FileOutputStream(compactado));
		out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(leituraTipo.getAbsolutePath())));

		out.write(sb.toString());
		out.flush();
		out.close();

		ZipUtil.adicionarArquivo(zos, leituraTipo);

		zos.close();
		leituraTipo.delete();
		retorno = this.getBytesFromFile(compactado);

		ServicosEmail.enviarMensagemArquivoAnexado(envioEmail.getEmailReceptor(), envioEmail.getEmailRemetente(), envioEmail
						.getTituloMensagem(), envioEmail.getCorpoMensagem(), compactado);

		compactado.delete();

		return retorno;
	}

	/**
	 * Método que recebe uma string e completa com os valor a esquerda até o tamanho informado
	 * 
	 * @author Anderson Italo
	 * @date 21/09/2011
	 * @param valorInformado
	 *            , valorEsquerda, tamanhoStringRetorno
	 * @return
	 */
	public static String completarStringComValorEsquerda(String valorInformado, String valorEsquerda, int tamanhoStringRetorno){

		String retorno = valorInformado;

		if(valorInformado != null && valorEsquerda != null){

			if(tamanhoStringRetorno == valorInformado.length()){
				return retorno;
			}else if(valorInformado.length() > tamanhoStringRetorno){
				return valorInformado.substring(0, tamanhoStringRetorno);
			}else{
				for(int i = 0; i < tamanhoStringRetorno; i++){

					if(retorno.length() == tamanhoStringRetorno){
						break;
					}

					retorno = valorEsquerda + retorno;
				}
			}
		}else{
			retorno = "";
		}

		return retorno;
	}

	/**
	 * Método que recebe uma string e retira uma quantidade de valores não significativos à
	 * esquerda.
	 * Ex: recebe "000.000.04.05", new String[] {"0", "."} e 4 e retorna "4.05"
	 * 
	 * @author Anderson Italo
	 * @date 27/09/2011
	 * @param valor
	 * @return
	 */
	public static String retiraValoresNaoSignificativosAEsquerda(String valor, String[] valoresParaRetirar, int tamanhoRetornoStringFinal){

		String retorno = valor;
		boolean retornar = false;

		for(int i = 0; i < valor.length(); i++){

			if(i < tamanhoRetornoStringFinal){

				for(int j = 0; j < valoresParaRetirar.length; j++){

					if(!(valor.charAt(i) == valoresParaRetirar[j].charAt(0))){

						retorno = valor.substring(i);
						retornar = true;
						break;
					}
				}

				if(retornar){

					break;
				}
			}else{

				retorno = valor.substring(i);
				break;
			}
		}

		return retorno;
	}

	public static String formatarHoraSemDataSemPonto(Date data){

		StringBuffer dataBD = new StringBuffer("");

		if(data != null){

			Calendar dataCalendar = new GregorianCalendar();
			dataCalendar.setTime(data);

			if(dataCalendar.get(Calendar.HOUR_OF_DAY) > 9){
				dataBD.append(dataCalendar.get(Calendar.HOUR_OF_DAY));
			}else{
				dataBD.append("0" + dataCalendar.get(Calendar.HOUR_OF_DAY));
			}

			if(dataCalendar.get(Calendar.MINUTE) > 9){
				dataBD.append(dataCalendar.get(Calendar.MINUTE));
			}else{
				dataBD.append("0" + dataCalendar.get(Calendar.MINUTE));
			}

			if(dataCalendar.get(Calendar.SECOND) > 9){
				dataBD.append(dataCalendar.get(Calendar.SECOND));
			}else{
				dataBD.append("0" + dataCalendar.get(Calendar.SECOND));
			}

		}

		return dataBD.toString();
	}

	/**
	 * Converte a data passada para o formato "DD/MM/YYYY"
	 * 
	 * @author: Pericles Tavares
	 * @date: 21/10/2011
	 * @param String
	 *            data no formato "YYYYMMDD"
	 * @return String data no formato "DD/MM/YYYY"
	 */
	public static String formatarDataDDMMYYYY(String data){

		String retorno = "";

		if(data != null && !data.equals("") && data.trim().length() == 8){

			retorno = data.substring(0, 2) + "/" + data.substring(2, 4) + "/" + data.substring(4);

		}

		return retorno;
	}

	/**
	 * @param data
	 * @return
	 */

	public static String recuperaDiaMesAnoDaData(Date data){

		String ano = "" + getAno(data);
		String mes = "" + getMes(data);
		if(mes.length() == 1){
			mes = "0" + mes;
		}
		String dia = "" + getDiaMes(data);
		if(dia.length() == 1){
			dia = "0" + dia;
		}

		return dia + mes + ano;
	}

	/**
	 * Calcular a diferença em segundos entre datas
	 * 
	 * @param dataInical
	 * @param dataFinal
	 * @return BigDecimal
	 */
	public static BigDecimal diferencaSegundos(Date dataInical, Date dataFinal){

		Calendar dataInicialCalendar = Calendar.getInstance();
		dataInicialCalendar.setTime(dataInical);

		Calendar dataFinalCalendar = Calendar.getInstance();
		dataFinalCalendar.setTime(dataFinal);

		long r = dataFinalCalendar.getTime().getTime() - dataInicialCalendar.getTime().getTime();

		BigDecimal diffSeg = new BigDecimal(r).divide(new BigDecimal(1000)).setScale(4, RoundingMode.HALF_UP);

		return diffSeg;
	}

	public static boolean isVazioOuBrancoOuZero(Object object){

		boolean retorno = false;

		if(object == null) return true;

		if(object instanceof String){
			String conteudo = ((String) object).trim();
			if(conteudo.length() == 0 || "".equalsIgnoreCase(conteudo) || "0".equalsIgnoreCase(conteudo)){
				return true;
			}
		}else if(object instanceof ArrayList){
			ArrayList conteudo = (ArrayList) object;
			if(conteudo.isEmpty()){
				return true;
			}
		}else if(object instanceof Integer){
			Integer conteudo = (Integer) object;
			if(conteudo.equals(0)){
				return true;
			}
		}

		return retorno;
	}

	/**
	 * Método que faz valida se o objeto não é Nulo Branco ou Zero (String e Integer)
	 * 
	 * @param object
	 * @return <true> Se o objeto tiver preenchido e <false> se o objeto for NULO ou BRANCO ou ZERO
	 */
	public static boolean isNaoNuloBrancoZero(Object object){

		return !isVazioOuBrancoOuZero(object);
	}

	public static String formatarFone(String fone){

		String foneRetorno = "";

		if(fone != null && !fone.trim().equals("")){
			fone = adicionarZerosEsqueda(8, fone);
			String foneParte1 = fone.substring(0, 4);
			String foneParte2 = fone.substring(4, fone.length());
			// caso o numero seja 0800
			if(foneParte2.length() > 4){
				String foneParte3 = foneParte2.substring(0, 4);
				String foneParte4 = foneParte2.substring(4, foneParte2.length());
				foneParte2 = foneParte3 + "." + foneParte4;
			}

			foneRetorno = foneParte1 + "." + foneParte2;

		}
		return foneRetorno;
	}

	/*
	 * public static void main(String[] a){
	 * String fone = formatarFone("111144444");
	 * System.out.println(fone);
	 * }
	 */

	public static Date diferencaDias(Date data, Long dias){

		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -dias.intValue());

		return c.getTime();
	}

	// Formata o codigo de barra colocando os espacos.
	// Josenildo Neves.
	public static String formatarCodigoBarraComEspacos(String codigoBarra){

		String retorno = "";

		retorno = codigoBarra.substring(0, 12) + " " + codigoBarra.substring(12, 24) + " " + codigoBarra.substring(24, 36) + " "
						+ codigoBarra.substring(36, 48);

		return retorno;
	}

	/**
	 * @author: Yara Souza
	 * @date: 05/04/2012
	 *        Retorna BigDecimal truncado.
	 * @param numero
	 * @param casasDecimais
	 * @return
	 */

	public static String completarStringZeroDireita(String valor, int quantidade){

		String retorno = valor;

		if(valor != null){

			if(quantidade == valor.length()){
				return retorno;
			}else if(valor.length() > quantidade){
				return valor.substring(0, quantidade);
			}else{
				for(int i = 0; i < quantidade; i++){

					if(retorno.length() == quantidade){
						break;
					}

					retorno = retorno + "0";
				}
			}
		}else{
			retorno = "";
		}

		return retorno;
	}

	public static String completarStringCaractereDireita(String valor, int quantidade, char caracter){

		String retorno = valor;

		if(valor != null){

			if(quantidade == valor.length()){
				return retorno;
			}else if(valor.length() > quantidade){
				return valor.substring(0, quantidade);
			}else{
				for(int i = 0; i < quantidade; i++){

					if(retorno.length() == quantidade){
						break;
					}

					retorno = retorno + caracter;
				}
			}
		}else{
			retorno = "";
		}

		return retorno;
	}

	/**
	 * @author: Yara Souza
	 * @date: 05/04/2012
	 *        Retorna BigDecimal truncado.
	 * @param numero
	 * @param casasDecimais
	 * @return
	 */
	public static Integer[] obterReferenciasAPartirRefencia(Integer anoMesReferencia, int meses){

		Integer[] referencias = new Integer[meses];

		referencias[0] = anoMesReferencia;

		for(int i = 1; i < meses; i++){

			referencias[i] = somaMesAnoMesReferencia(referencias[i - 1], 1);

		}

		return referencias;

	}

	/**
	 * @author: Yara Souza
	 * @date: 05/04/2012
	 *        Retorna BigDecimal truncado.
	 * @param numero
	 * @param casasDecimais
	 * @return
	 */

	public static BigDecimal truncar(BigDecimal numero, int casasDecimais){

		numero = numero.setScale(casasDecimais, BigDecimal.ROUND_DOWN);

		return numero;
	}

	public static BigDecimal dividirArredondando(BigDecimal dividendo, BigDecimal divisor, int casasDecimais){

		BigDecimal resultado = null;

		if(dividendo != null && divisor != null){

			resultado = dividendo.divide(divisor, casasDecimais, BigDecimal.ROUND_DOWN);

		}

		return resultado;
	}

	/**
	 * Método que compara valores iniciais e finais;
	 * 
	 * @author Ricardo Rodrigues
	 * @date 17/04/2012
	 * @param valorInicial
	 * @param valorFinal
	 * @param sinalComparacao
	 * @return
	 */
	public static boolean compararInicialEFinal(String valorInicial, String valorFinal, String sinalComparacao){

		boolean retorno = false;
		if(!isVazioOuBranco(valorInicial) && !isVazioOuBranco(valorFinal)){
			Integer valorInicialInteger = Integer.valueOf(valorInicial);
			Integer valorFinalInteger = Integer.valueOf(valorFinal);

			if(sinalComparacao.equals(">")){
				if(valorInicialInteger > valorFinalInteger){
					retorno = true;
				}
			}else if(sinalComparacao.equals("<")){
				if(valorInicialInteger < valorFinalInteger){
					retorno = true;
				}
			}else if(sinalComparacao.equals("==")){
				if(valorInicialInteger.equals(valorFinalInteger)){
					retorno = true;
				}
			}

		}

		return retorno;
	}

	/**
	 * Método que compara valores iniciais e finais como Integer.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 17/04/2012
	 * @param valorInicial
	 * @param valorFinal
	 * @param sinalComparacao
	 * @return
	 */
	public static boolean compararInicialEFinalInteger(Integer valorInicial, Integer valorFinal, String sinalComparacao){

		boolean retorno = false;
		if(!isVazioOuBranco(valorInicial) && !isVazioOuBranco(valorFinal)){
			if(sinalComparacao.equals(">")){
				if(valorInicial > valorFinal){
					retorno = true;
				}
			}else if(sinalComparacao.equals("<")){
				if(valorInicial < valorFinal){
					retorno = true;
				}
			}else if(sinalComparacao.equals("==")){
				if(valorInicial.equals(valorFinal)){
					retorno = true;
				}
			}

		}

		return retorno;
	}

	/**
	 * Método que atribui valores iniciais e finais ou ao contrário para os validadores dos
	 * relatórios.
	 * 
	 * @author Ricardo Rodrigues
	 * @date 17/04/2012
	 * @param valorInicial
	 * @param valorFinal
	 * @param valorParametroInicial
	 * @param valorParametroFinal
	 * @param parametros
	 */
	public static void atribuirValoresInicialFinalOuFinalInicial(String valorInicial, String valorFinal, String valorParametroInicial,
					String valorParametroFinal, Map<String, Object> parametros){

		if(!isVazioOuBranco(valorInicial) && isVazioOuBranco(valorFinal)){
			valorFinal = valorInicial;
		}else if(Util.isVazioOuBranco(valorInicial) && !Util.isVazioOuBranco(valorFinal)){
			valorInicial = valorFinal;
		}

		parametros.put(valorParametroFinal, valorFinal);
		parametros.put(valorParametroInicial, valorInicial);

	}

	/**
	 * Retorna True se um dado número é Par e False, caso contrário
	 * 
	 * @author Luciano Galvão
	 * @date 17/04/2012
	 * @param numero
	 * @return
	 */
	public static boolean isNumeroPar(int numero){

		return ((numero % 2) == 0);
	}

	public static BigDecimal trunc(int decimais, BigDecimal value){

		double p = Math.pow(10, decimais);
		double doubleValue = (value.doubleValue() * p) / p;
		try{
			new BigDecimal(doubleValue, MathContext.DECIMAL64).setScale(decimais, RoundingMode.HALF_EVEN);
		}catch(Exception ex){
			ex.printStackTrace();
			return BigDecimal.ZERO;
		}
		return new BigDecimal(doubleValue, MathContext.DECIMAL64).setScale(decimais, RoundingMode.HALF_EVEN);
	}

	/**
	 * Método que recebe um integer e completa com os valores passados como parâmetros à esquerda
	 * até a quantidade.
	 * 
	 * @author Josenildo Neves
	 * @date 28/05/2012
	 * @param valor
	 *            , valorEsquerda, quantidade de caracteres da String de retorno
	 * @return
	 */
	public static String completarNumeroComValorEsquerdaVelho(Integer valor, String valorEsquerda, int quantidade){

		String retorno = "";

		if(valor != null && valorEsquerda != null){

			retorno = valor.toString();

			if(quantidade == retorno.length()){
				return retorno;
			}else if(retorno.length() > quantidade){
				retorno = retorno.substring(retorno.length() - 2);
			}else{
				for(int i = 0; i < quantidade; i++){

					if(retorno.length() == quantidade){
						break;
					}

					retorno = valorEsquerda + retorno;
				}
			}
		}

		return retorno;
	}

	/**
	 * Método que recebe um integer e completa com os valores passados como parâmetros à esquerda
	 * até a quantidade.
	 * 
	 * @author Thiago Santos
	 * @date 01/11/2012
	 * @param valor
	 *            Número a ser preenchido.
	 * @param valorEsquerda
	 *            texto para reenchimento,
	 * @param quantidade
	 *            de caracteres da String de retorno
	 * @return
	 */
	public static String completarNumeroComValorEsquerda(Integer valor, String valorEsquerda, int quantidade){

		StringBuilder retorno = new StringBuilder(quantidade);
		if(valor != null && valorEsquerda != null){
			String tmp = valor.toString();
			for(int i = 0; i < quantidade - tmp.length(); i += valorEsquerda.length()){
				retorno.append(valorEsquerda);
			}
			retorno.append(tmp);
		}
		return retorno.toString();
	}

	/**
	 * Método que recebe uma string que repsenta um código de barra de uma conta e insere a
	 * matrícula do imóvel nas posições livres (36 a 43)
	 * 
	 * @author Hugo Lima
	 * @date 13/06/2012
	 * @param valor
	 *            , quantidade de caracteres da String de retorno
	 * @return
	 * @deprecated
	 */
	// public static String inserirMatriculaImovelCodigoBarra(Integer idImovel, String codigoBarra){
	//
	// String retorno = "";
	//
	// String matriculaImovelFormatada = Util.completarStringZeroEsquerda(idImovel.toString(), 8);
	//
	// retorno = codigoBarra.substring(0, 36) + matriculaImovelFormatada +
	// codigoBarra.substring(44);
	//
	// return retorno;
	// }

	/**
	 * Adciona ao Ano Mes Referencia a quantidade de meses informada.
	 * 
	 * @param qtdMeses
	 *            Representa a quantidade de meses a ser adcionada ao ano mes.
	 * @return Ano mes referencia resultante.
	 */
	@SuppressWarnings("static-access")
	public static Integer calcularAnoMesReferencial(Integer anoMesReferencia, int qtdMeses){

		if(anoMesReferencia != null){

			String dataFormatacao = anoMesReferencia.toString();
			int mesDezembro = 12;

			int ano = Integer.valueOf(dataFormatacao.substring(0, 4)).intValue();
			int mes = Integer.valueOf(dataFormatacao.substring(4, 6)).intValue();

			GregorianCalendar gregorianCalendar = new GregorianCalendar(ano, mes, 01);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(gregorianCalendar.getTime());
			calendar.add(calendar.MONTH, qtdMeses);

			ano = calendar.get(calendar.YEAR);
			mes = calendar.get(calendar.MONTH);

			if(mes == 0){
				mes = mesDezembro;
				dataFormatacao = String.valueOf(ano) + String.valueOf(mes);

			}else{
				if(mes < 10){

					dataFormatacao = String.valueOf(ano) + "0" + String.valueOf(mes);

				}else{

					dataFormatacao = String.valueOf(ano) + String.valueOf(mes);

				}

			}

			return Integer.valueOf(dataFormatacao);

		}

		return null;
	}

	/**
	 * [UC0340] Gerar Código de Barras.
	 * Este caso de uso permite a geração do código de barras para impressão no formato “2 de 5
	 * intercalado”, conforme padrão da FEBRABAN.
	 * 
	 * @param codigoBarras
	 * @return
	 */
	public static String converterCodigoBarrasParaFormato2de5Intercalado(String codigoBarras){

		StringBuilder novoCodigoBarras = new StringBuilder();

		String primeiroNumero;
		String segundoNumero;

		if(!isVazioOuBranco(codigoBarras)){
			// 2. Caso a quantidade de dígitos do número recebido não seja par, adicionar um zero a
			// esquerda do número, tornando par a quantidade de dígitos do número
			if((codigoBarras.length() % 2) != 0){

				codigoBarras = "0" + codigoBarras;

			}

			// 6. Adicionar o bit de start (<) no início
			novoCodigoBarras.append("<");

			for(int i = 0; i < codigoBarras.length(); i += 2){

				primeiroNumero = mapValorBinario.get(codigoBarras.charAt(i));
				segundoNumero = mapValorBinario.get(codigoBarras.charAt(i + 1));

				// 4.2. Converte o número de 10 dígitos formatado em uma string de 5 letras
				for(int n = 0; n <= 4; n++){

					// 5. Concatenar as string’s geradas formando o código de barras.
					if((String.valueOf(primeiroNumero.charAt(n)) + String.valueOf(segundoNumero.charAt(n))).equals("00")){

						novoCodigoBarras.append("n");

					}else{

						if((String.valueOf(primeiroNumero.charAt(n)) + String.valueOf(segundoNumero.charAt(n))).equals("01")){

							novoCodigoBarras.append("N");

						}else{

							if((String.valueOf(primeiroNumero.charAt(n)) + String.valueOf(segundoNumero.charAt(n))).equals("10")){

								novoCodigoBarras.append("w");

							}else{

								novoCodigoBarras.append("W");

							}

						}

					}

				}

			}

			// 6. Adicionar o bit de stop (>) e final da string.
			novoCodigoBarras.append(">");

		}else{

			novoCodigoBarras.append("");
		}

		return novoCodigoBarras.toString();
	}

	/*
	 * public static void main(String[] args){
	 * // 11:41:01,850 INFO [STDOUT]
	 * // ***********************************************************************taxa juros=0.0142
	 * // 11:41:01,850 INFO [STDOUT]
	 * //
	 * 
	 * ***********************************************************************fatorPrestacao=0.986056
	 * // 11:41:01,850 INFO [STDOUT]
	 * //
	 * 
	 * ***********************************************************************valorPrestacao=4112.819150
	 * BigDecimal taxaJuros = new BigDecimal("0.0142");
	 * BigDecimal valorUm = BigDecimal.ONE;
	 * Short quantidadePrestacao = 1;
	 * BigDecimal fatorPrestacao1 = BigDecimal.ZERO;
	 * BigDecimal fatorPrestacao2 = BigDecimal.ZERO;
	 * BigDecimal fatorPrestacao3 = BigDecimal.ZERO;
	 * BigDecimal fatorPrestacao4 = BigDecimal.ZERO;
	 * BigDecimal fatorPrestacao = BigDecimal.ONE;
	 * // ------------------------------------------------------------------
	 * MathContext decimal = MathContext.DECIMAL64;
	 * fatorPrestacao = valorUm.add(taxaJuros, decimal);
	 * fatorPrestacao = fatorPrestacao.pow(quantidadePrestacao);
	 * fatorPrestacao = valorUm.divide(fatorPrestacao, decimal);
	 * System.out.println("****fatorPrestacao  -> " + fatorPrestacao);
	 * fatorPrestacao = valorUm.subtract(fatorPrestacao, decimal);
	 * System.out.println("***fatorPrestacao  -> " + fatorPrestacao);
	 * fatorPrestacao = fatorPrestacao.divide(taxaJuros, decimal);
	 * System.out.println("*fatorPrestacao  -> " + fatorPrestacao.setScale(6,
	 * BigDecimal.ROUND_DOWN));
	 * // ------------------------------------------------------------------
	 * }
	 */

	public static int obterQuintoDiaUtilMes(int mes, int ano, Collection<NacionalFeriado> colecaoFeriados,
					Collection colecaoMunicipioFeriado){

		// TODO Quinto dia util
		int quintoDiaUtil = 0;

		Calendar calendar = new GregorianCalendar();

		calendar.set(Calendar.YEAR, ano);// ano

		switch(mes){
			case 1:// JANEIRO
				calendar.set(Calendar.MONTH, 0);
				break;
			case 2:// FEVEREIRO
				calendar.set(Calendar.MONTH, 1);
				break;
			case 3:// MARÇO
				calendar.set(Calendar.MONTH, 2);
				break;
			case 4:// ABRIL
				calendar.set(Calendar.MONTH, 3);
				break;
			case 5:// MAIO
				calendar.set(Calendar.MONTH, 4);
				break;
			case 6:// JUNHO
				calendar.set(Calendar.MONTH, 5);
				break;
			case 7:// JULHO
				calendar.set(Calendar.MONTH, 6);
				break;
			case 8:// AGOSTO
				calendar.set(Calendar.MONTH, 7);
				break;
			case 9:// SETEMBRO
				calendar.set(Calendar.MONTH, 8);
				break;
			case 10:// OUTUBRO
				calendar.set(Calendar.MONTH, 9);
				break;
			case 11:// NOVEMBRO
				calendar.set(Calendar.MONTH, 10);
				break;
			case 12:// DEZEMBRO
				calendar.set(Calendar.MONTH, 11);
				break;
			default:
				break;
		}

		// Primeiro dia do mes
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));

		int i = 1;
		while(i < 5){

			if(ehDiaUtil(calendar.getTime(), colecaoFeriados, colecaoMunicipioFeriado)){
				i++;
			}
			calendar.add(Calendar.DAY_OF_MONTH, +1);

		}

		if(!ehDiaUtil(calendar.getTime(), colecaoFeriados, colecaoMunicipioFeriado)){

			while(!ehDiaUtil(calendar.getTime(), colecaoFeriados, colecaoMunicipioFeriado)){
				calendar.add(Calendar.DAY_OF_MONTH, +1);
			}

		}

		return calendar.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * Author: Josenildo Neves Data: 27/06/2012
	 * Adciona a data a quantidade de dias informada.
	 * 
	 * @param qtdDias
	 *            Representa a quantidade de dias a ser adcionada a data.
	 * @return data resultante.
	 */
	public static String somaDiasAData(Date data, int qtdDias){

		String novaData = "";

		if(data != null){

			// cria uma instância de GregorianCalendar para manipular a data
			Calendar calendar = GregorianCalendar.getInstance();

			// seta a data
			calendar.setTime(data);

			// subtrai o nº de dias INFORMADO da data
			calendar.add(Calendar.DAY_OF_MONTH, qtdDias);

			// recupera a data subtraida dos nº de dias
			data = calendar.getTime();

			// retorna a nova data
			novaData = formatarData(data);

		}

		return novaData;
	}

	/**
	 * Converte a data passada em string retorna AAAAMM
	 * 
	 * @author: Josenildo Neves
	 * @date: 27/06/2012
	 * @param data
	 *            Descrição do parâmetro
	 * @return anoMes sem barra
	 */
	public static String formatarDataSemBarraAnoMes(Date data){

		String retorno = "";
		if(data != null){ // 1
			Calendar dataCalendar = new GregorianCalendar();
			StringBuffer dataBD = new StringBuffer();

			dataCalendar.setTime(data);

			dataBD.append(dataCalendar.get(Calendar.YEAR));

			// Obs.: Janeiro no Calendar é mês zero
			if((dataCalendar.get(Calendar.MONTH) + 1) > 9){
				dataBD.append(dataCalendar.get(Calendar.MONTH) + 1);
			}else{
				dataBD.append("0" + (dataCalendar.get(Calendar.MONTH) + 1));
			}

			retorno = dataBD.toString();
		}
		return retorno;
	}

	/**
	 * Converte a data passada em string dd/MM
	 * 
	 * @author: Luciano Galvao
	 * @date: 26/06/2012
	 * @param data
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public static String formatarDataDiaMesComBarra(Date data){

		String retorno = "";
		String dataFormatada = formatarData(data);
		if(!isVazioOuBranco(dataFormatada) && (dataFormatada.length() == 10)){
			retorno = dataFormatada.substring(0, 5);
		}

		return retorno;
	}

	/**
	 * Remove os caracteres especiais de uma string
	 * 
	 * @author: Jose Claudio
	 * @date: 08/08/2012
	 * @param texto
	 * @return string de texto sem caracteres especiais
	 */
	public static String substituirCaracteresEspeciais(String str){

		return str.replaceAll("[ãâàáä]", "a")//
						.replaceAll("[êèéë]", "e")//
						.replaceAll("[îìíï]", "i")//
						.replaceAll("[õôòóö]", "o")//
						.replaceAll("[ûúùü]", "u")//
						.replaceAll("[ÃÂÀÁÄ]", "A")//
						.replaceAll("[ÊÈÉË]", "E")//
						.replaceAll("[ÎÌÍÏ]", "I")//
						.replaceAll("[ÕÔÒÓÖ]", "O")//
						.replaceAll("[ÛÙÚÜ]", "U")//
						.replace('ç', 'c')//
						.replace('Ç', 'C')//
						.replace('ñ', 'n')//
						.replace('Ñ', 'N')//
						.replaceAll("!", "")//
						.replaceAll("\\[\\´\\`\\?!\\@\\#\\$\\%\\¨\\*", " ")//
						.replaceAll("\\(\\)\\=\\{\\}\\[\\]\\~\\^\\]", " ")//
						.replaceAll("[\\.\\;\\-\\_\\+\\'\\ª\\º\\:\\;\\/]", " ");

	}

	public static String obterStackTraceCompleta(Throwable e, StringWriter writer){

		PrintWriter printer = null;
		String msgNegocio = e.getClass().getName();
		try{
			printer = new PrintWriter(writer);

			while(e != null){
				if(e.getClass().equals(NegocioException.class)){
					msgNegocio = e.getMessage();
				}
				printer.println(e);
				StackTraceElement[] trace = e.getStackTrace();
				for(int i = 0; i < trace.length; i++)
					printer.println("\tem " + trace[i]);

				e = e.getCause();
				if(e != null) printer.println("Causado Por:\r\n");
			}
		}finally{
			if(printer != null) printer.close();
		}
		return msgNegocio;
	}

	public static String recuperarIpCliente(ServletRequest request){

		// Get client's IP address
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String ipAddress = httpRequest.getHeader("x-forwarded-for");
		if(ipAddress == null){
			ipAddress = httpRequest.getHeader("X_FORWARDED_FOR");
			if(ipAddress == null){
				ipAddress = httpRequest.getRemoteAddr();
			}
		}
		return ipAddress;
	}

	public static void obtainFileListByPatterns(File localDir, String nomeArquivo) throws IOException{

		List<File> listaArquivosZip = new ArrayList<File>();

		if(localDir.isDirectory()){
			File[] filteredFiles = localDir.listFiles();
			for(int i = 0; i < filteredFiles.length; i++){
				if(filteredFiles[i].isFile()){
					if(filteredFiles[i].getName().matches("^" + nomeArquivo + "[\\d]+\\.(ZIP|zip)$")){
						listaArquivosZip.add(filteredFiles[i]);
					}
				}
			}
		}

		for(File fileZip : listaArquivosZip){

			try{

				unzip(fileZip, localDir, nomeArquivo);

			}catch(IOException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		File arquivoLeitura = new File(localDir, nomeArquivo + ".txt");

		if(arquivoLeitura != null){
			try{
				arquivoLeitura.delete();
			}catch(Exception ex){

			}

		}

	}

	public static void unzip(File zipFile, File diretorio, String nomeArquivo) throws IOException{

		ZipFile zip = null;
		File arquivoLeitura = null;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		byte[] buffer = new byte[1024];

		try{
			// cria diretório informado, caso não exista
			if(!diretorio.exists()){
				diretorio.mkdirs();
			}
			if(!diretorio.exists() || !diretorio.isDirectory()){
				throw new IOException("O diretório " + diretorio.getName() + " não é um diretório válido");
			}

			zip = new ZipFile(zipFile);
			Enumeration e = zip.entries();
			while(e.hasMoreElements()){
				ZipEntry entrada = (ZipEntry) e.nextElement();

				arquivoLeitura = new File(diretorio, nomeArquivo + ".txt");

				// se for diretório inexistente, cria a estrutura e pula
				// pra próxima entrada
				if(entrada.isDirectory() && !arquivoLeitura.exists()){
					arquivoLeitura.mkdirs();
					continue;
				}
				// se a estrutura de diretórios não existe, cria
				if(!arquivoLeitura.getParentFile().exists()){
					arquivoLeitura.getParentFile().mkdirs();
				}
				try{
					// lê o arquivo do zip e grava em disco
					inputStream = zip.getInputStream(entrada);
					outputStream = new FileOutputStream(arquivoLeitura, true);

					int bytesLidos = 0;
					if(inputStream == null){
						throw new ZipException("Erro ao ler a entrada do zip: " + entrada.getName());
					}
					while((bytesLidos = inputStream.read(buffer)) > 0){
						outputStream.write(buffer, 0, bytesLidos);
					}

					File fileZip = new File(diretorio, nomeArquivo + ".zip");
					ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(fileZip));

					ZipUtil.adicionarArquivo(zos, arquivoLeitura);

					// close the stream
					zos.close();

				}finally{
					if(inputStream != null){
						try{
							inputStream.close();
						}catch(Exception ex){
						}
					}
					if(outputStream != null){
						try{
							outputStream.close();
						}catch(Exception ex){
						}
					}
				}
			}
		}finally{
			if(zip != null){
				try{
					zip.close();
				}catch(Exception e){

				}
			}
			if(zipFile != null){
				try{
					zipFile.delete();
				}catch(Exception e){

				}
			}
		}
	}

	/**
	 * Método que retorna a data com primeiro dia do mes seguinte.
	 * 
	 * @author Josenildo Neves
	 * @date 16/08/2012
	 * @param date
	 * @return Date
	 */
	public static Date retornaDataInicialComPrimeiroDiaMesSeguinte(Date date){

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);

		int primeiroDia = calendar.getActualMinimum(Calendar.DATE);
		int mes = calendar.get(Calendar.MONTH) + 1;
		int ano = calendar.get(Calendar.YEAR);

		if(mes > 11){
			mes = mes - 12;
			ano = ano + 1;
		}

		Calendar calendario = new GregorianCalendar();
		calendario.set(Calendar.YEAR, ano);
		calendario.set(Calendar.MONTH, mes);
		calendario.set(Calendar.DATE, primeiroDia);

		calendario.set(Calendar.HOUR_OF_DAY, 0);
		calendario.set(Calendar.MINUTE, 0);
		calendario.set(Calendar.SECOND, 0);
		calendario.set(Calendar.MILLISECOND, 0);

		return calendario.getTime();
	}

	/**
	 * Método que retorna a data com último dia do mes.
	 * 
	 * @author Josenildo Neves
	 * @date 16/08/2012
	 * @param date
	 * @return Date
	 */
	public static Date retornaDataInicialComUltimoDiaMes(Date date){

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);

		int primeiroDia = calendar.getActualMaximum(Calendar.DATE);
		int mes = calendar.get(Calendar.MONTH);
		int ano = calendar.get(Calendar.YEAR);

		Calendar calendario = new GregorianCalendar();
		calendario.set(Calendar.YEAR, ano);
		calendario.set(Calendar.MONTH, mes);
		calendario.set(Calendar.DATE, primeiroDia);

		calendario.set(Calendar.HOUR_OF_DAY, 0);
		calendario.set(Calendar.MINUTE, 0);
		calendario.set(Calendar.SECOND, 0);
		calendario.set(Calendar.MILLISECOND, 0);

		return calendario.getTime();
	}

	public static boolean isVazioOrNuloOuMenosUm(Object[] array){

		boolean retorno = false;

		if(array == null || array.length == 0){
			retorno = true;
		}else{
			if(array.length == 1){
				if(array[0].toString().equals("-1") || array[0].toString().equals("0")){
					retorno = true;
				}
			}

		}

		return retorno;
	}

	/**
	 * Soma uma coleção de BigDecimal
	 * 
	 * @author Luciano Galvao
	 * @date 26/10/2012
	 * @param colecaoBigDecimal
	 *            Coleção de objetos do tipo BigDecimal
	 * @return Soma dos objetos BigDecimal
	 */
	public static BigDecimal somaColecaoBigDecimal(Collection<BigDecimal> colecaoBigDecimal){

		BigDecimal resultado = BigDecimal.ZERO;

		if(!isVazioOrNulo(colecaoBigDecimal)){
			BigDecimal[] bigDecimalArray = new BigDecimal[colecaoBigDecimal.size()];
			colecaoBigDecimal.toArray(bigDecimalArray);

			resultado = somarBigDecimal(bigDecimalArray);
		}

		return resultado;
	}

	public static BigDecimal somarBigDecimal(BigDecimal... colecaoBigDecimal){

		BigDecimal resultado = BigDecimal.ZERO;

		if(!isVazioOrNulo(colecaoBigDecimal)){
			for(BigDecimal valor : colecaoBigDecimal){
				resultado = resultado.add(valor);
			}
		}

		return resultado;
	}

	public static Date obterDataComDiaExistenteEmTodosMeses(Date data){

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(data);

		int diaDoMes = calendar.get(Calendar.DAY_OF_MONTH);

		while(diaDoMes == 29 || diaDoMes == 30 || diaDoMes == 31){
			data = Util.adicionarNumeroDiasDeUmaData(data, 1);
			calendar.setTime(data);
			diaDoMes = calendar.get(Calendar.DAY_OF_MONTH);
		}
		return data;
	}

	/**
	 * Verifica se data está entre dataIntervaloInicio e dataIntervaloFim
	 * 
	 * @author Luciano Galvao
	 * @date 08/11/2012
	 */
	public static boolean periodoContemData(Date dataIntervaloInicio, Date dataIntervaloFim, Date data){

		return (compararData(dataIntervaloInicio, data) <= 0) && (compararData(dataIntervaloFim, data) >= 0);
	}

	public static int obterDiferencaEntreDatasEmDias(java.util.Date dataLow, java.util.Date dataHigh){

		GregorianCalendar startTime = new GregorianCalendar();
		GregorianCalendar endTime = new GregorianCalendar();

		GregorianCalendar curTime = new GregorianCalendar();
		GregorianCalendar baseTime = new GregorianCalendar();

		startTime.setTime(dataLow);
		endTime.setTime(dataHigh);

		int dif_multiplier = 1;

		// Verifica a ordem de inicio das datas
		if(dataLow.compareTo(dataHigh) < 0){
			baseTime.setTime(dataHigh);
			curTime.setTime(dataLow);
			dif_multiplier = 1;
		}else{
			baseTime.setTime(dataLow);
			curTime.setTime(dataHigh);
			dif_multiplier = -1;
		}

		int result_years = 0;
		int result_months = 0;
		int result_days = 0;

		// Para cada mes e ano, vai de mes em mes pegar o ultimo dia para import acumulando
		// no total de dias. Ja leva em consideracao ano bissesto
		while(curTime.get(GregorianCalendar.YEAR) < baseTime.get(GregorianCalendar.YEAR)
						|| curTime.get(GregorianCalendar.MONTH) < baseTime.get(GregorianCalendar.MONTH)){

			int max_day = curTime.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
			result_months += max_day;
			curTime.add(GregorianCalendar.MONTH, 1);

		}

		// Marca que é um saldo negativo ou positivo
		result_months = result_months * dif_multiplier;

		// Retirna a diferenca de dias do total dos meses
		result_days += (endTime.get(GregorianCalendar.DAY_OF_MONTH) - startTime.get(GregorianCalendar.DAY_OF_MONTH));

		return result_years + result_months + result_days;
	}

	/**
	 * Retorna o maior valor de um conjunto de valores BigDecimal
	 * 
	 * @author Luciano Galvao
	 * @date 19/12/2012
	 */
	public static BigDecimal getMaiorValor(BigDecimal... valores){

		BigDecimal retorno = null;

		if(!isVazioOrNulo(valores)){
			retorno = valores[0];
			for(int i = 1; i < valores.length; i++){
				if(valores[i] != null){
					if(retorno == null || valores[i].compareTo(retorno) > 0){
						retorno = valores[i];
					}
				}
			}
		}

		return retorno;
	}

	/**
	 * Retorn uma string com o conteúdo do arquivo passado.
	 * 
	 * @param arquivo
	 * @return
	 * @throws IOException
	 */
	public static String lerArquivoGerarResumoLigacoesEconomiasReferencias(File arquivo) throws IOException{

		String linha = null;
		StringBuilder dados = new StringBuilder();
		try{
			// instancia do arquivo que vou ler
			FileReader reader = new FileReader(arquivo);
			BufferedReader leitor = new BufferedReader(reader);

			// loop que percorrerá todas as linhas do arquivo.txt que eu quero ler
			while((linha = leitor.readLine()) != null){
				dados.append(linha);
				dados.append(",");
			}
			leitor.close();
			reader.close();
		}catch(IOException e){
			throw new IOException("Erro no acesso ao arquivo: " + e.getMessage());
		}

		return dados.toString();
	}

	/**
	 * Método executarMetodo
	 * <p>
	 * Esse método executa um methodo via reflection de uma instancia de classe.
	 * </p>
	 * RASTREIO: [OC994812]
	 * 
	 * @param instanciaClasse
	 * @param metodo
	 * @param parametro
	 * @return valor retornado de o metodo da classe tiver retorno.
	 * @author Marlos Ribeiro
	 * @since 20/02/2013
	 */
	public static Object executarMetodo(Object instanciaClasse, String metodo, Object... parametro){

		Object retorno;
		Class[] parameterTypes = null;
		if(parametro != null){
			parameterTypes = new Class[parametro.length];
			for(int i = 0; i < parametro.length; i++){
				Object object = parametro[i];
				parameterTypes[i] = object.getClass();
			}
		}

		Method metodoClass;
		try{
			metodoClass = instanciaClasse.getClass().getMethod(metodo, parameterTypes);
			retorno = metodoClass.invoke(instanciaClasse, parametro);
		}catch(SecurityException e){
			throw new RuntimeException("Problemas ao executar o metodo [" + metodo + "] da Classe [" + instanciaClasse.getClass().getName() + "]");
		}catch(NoSuchMethodException e){
			throw new RuntimeException("Problemas ao executar o metodo [" + metodo + "] da Classe [" + instanciaClasse.getClass().getName() + "]");
		}catch(IllegalArgumentException e){
			throw new RuntimeException("Problemas ao executar o metodo [" + metodo + "] da Classe [" + instanciaClasse.getClass().getName() + "]");
		}catch(IllegalAccessException e){
			throw new RuntimeException("Problemas ao executar o metodo [" + metodo + "] da Classe [" + instanciaClasse.getClass().getName() + "]");
		}catch(InvocationTargetException e){
			throw new RuntimeException("Problemas ao executar o metodo [" + metodo + "] da Classe [" + instanciaClasse.getClass().getName() + "]");
		}
		return retorno;
	}
	
	/**
	 * Método calcularTempoDecorridoDesde
	 * 
	 * @param iniTime
	 * @return
	 * @author Marlos Ribeiro
	 * @since 24/01/2013
	 */
	public static String calcularTempoDecorridoDesde(long iniTime){

		long milisegundos = System.currentTimeMillis() - iniTime;
		long hora, minuto, segundo;
		long restohora, restominuto, restosegundo;

		hora = milisegundos / 3600000;
		restohora = milisegundos % 3600000;

		minuto = restohora / 60000;
		restominuto = restohora % 60000;

		segundo = restominuto / 1000;
		restosegundo = restominuto % 1000;

		return hora + ":" + minuto + ":" + segundo + "." + restosegundo;
	}


	/**
	 * Método que converte uma String (dd/mm/yyyy) em uma Data
	 * 
	 * @author Josenildo Neves
	 * @date 30/11/2012
	 * @param string
	 * @return Date
	 */
	public static Date converterStringParaData(String data){

		// remove pontuações
		data = data.replaceAll("\\p{Punct}", "");

		String ano = data.substring(4, 8);
		String mes = data.substring(2, 4);
		String dia = data.substring(0, 2);

		Calendar calendario = new GregorianCalendar();
		calendario.set(Calendar.YEAR, Util.converterStringParaInteger(ano));
		calendario.set(Calendar.MONTH, Util.converterStringParaInteger(mes) - 1);
		calendario.set(Calendar.DATE, Util.converterStringParaInteger(dia));

		calendario.set(Calendar.HOUR_OF_DAY, 0);
		calendario.set(Calendar.MINUTE, 0);
		calendario.set(Calendar.SECOND, 0);
		calendario.set(Calendar.MILLISECOND, 0);

		return calendario.getTime();
	}

	/**
	 * Localiza e retorna um objeto da coleção utilizando busca sequencial
	 * 
	 * @author Luciano Galvao
	 * @date 14/03/2013
	 */
	public static Object buscarObjetoNaColecao(Collection colecao, Object objeto){

		if(colecao != null && objeto != null){
			for(Object obj : colecao){
				if(obj.equals(objeto)){
					return obj;
				}
			}
		}

		return null;
	}

	public static void copyFile(File source, File destination) throws IOException{

		if(destination.exists()) destination = new File(destination.getParentFile().getAbsolutePath() + File.separator
						+ System.currentTimeMillis() + "_"
						+ destination.getName());// destination.delete();
		if(!destination.getParentFile().exists()){
			destination.getParentFile().mkdirs();
		}
		destination.createNewFile();
		FileChannel sourceChannel = null;
		FileChannel destinationChannel = null;

		try{
			sourceChannel = new FileInputStream(source).getChannel();
			destinationChannel = new FileOutputStream(destination).getChannel();
			sourceChannel.transferTo(0, sourceChannel.size(), destinationChannel);
		}finally{
			if(sourceChannel != null && sourceChannel.isOpen()) sourceChannel.close();
			if(destinationChannel != null && destinationChannel.isOpen()) destinationChannel.close();
		}
	}

	public static String completarStringCaractereDireitaEsquerda(String valor, int quantidade, char caracter, int direcao){

		String retorno = valor;

		if(valor != null){

			if(quantidade == valor.length()){
				return retorno;
			}else if(valor.length() > quantidade){
				return valor.substring(0, quantidade);
			}else{
				for(int i = 0; i < quantidade; i++){

					if(retorno.length() == quantidade){
						break;
					}

					if(direcao == 2){

						retorno = caracter + retorno;

					}else{

						retorno = retorno + caracter;

					}

				}
			}
		}else{
			retorno = "";
		}

		return retorno;
	}

	/**
	 * Método completarZeroEsquerda
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param numeroAmostrasConformesTurbidez
	 * @param i
	 * @return
	 * @author Marlos Ribeiro
	 * @since 16/04/2013
	 */
	public static Object completarZeroEsquerda(BigDecimal numeroAmostrasConformesTurbidez, int i){

		return completarStringZeroEsquerda(numeroAmostrasConformesTurbidez == null ? "" : numeroAmostrasConformesTurbidez.toString()
						.replaceAll("\\.*\\,*", ""), i);
	}

	/**
	 * Método formatLPad
	 * <p>
	 * Esse método implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param idFaturamentoGrupo
	 * @param i
	 * @param c
	 * @return
	 * @author Marlos Ribeiro
	 * @since 17/04/2013
	 */
	public static String formatRPad(Object source, int finalLength, char padChar){

		return formatPad(source, finalLength, padChar, false);
	}

	public static String formatLPad(Object source, int finalLength, char padChar){

		return formatPad(source, finalLength, padChar, true);
	}

	public static String formatPad(Object source, int finalLength, char padChar, boolean isLPad){

		String str = source == null ? "" : source.toString();
		int dif = finalLength - str.length();
		if(dif > 0){
			if(isLPad) str = StringUtils.leftPad(str, finalLength, padChar);
			else str = StringUtils.rightPad(str, finalLength, padChar);
		}else if(dif < 0){
			if(isLPad) str = str.substring(dif * -1);
			else str = str.substring(0, finalLength);
		}
		return str;
	}

	/**
	 * Gerar data inicial do ano apartir do ano mês de referência
	 * 
	 * @author Hebert Falcão
	 * @date 28/04/2013
	 */
	public static Date gerarDataInicialDoAnoApartirDoAnoMesRefencia(Integer anoMesReferencia){

		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 00);
		calendar.set(Calendar.MINUTE, 00);
		calendar.set(Calendar.SECOND, 00);

		Integer mes = 1;
		calendar.set(Calendar.MONTH, (mes - 1));

		calendar.set(Calendar.YEAR, anoMesReferencia);

		Integer dia = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DATE, dia);

		Date retorno = calendar.getTime();

		return retorno;
	}

	/**
	 * Gerar data final do ano apartir do ano mês de referência
	 * 
	 * @author Carlos Chrystian
	 * @date 23/04/2013
	 */
	public static Date gerarDataFinalDoAnoApartirDoAnoMesRefencia(Integer anoMesReferencia){

		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);

		Integer mes = 12;
		calendar.set(Calendar.MONTH, (mes - 1));

		calendar.set(Calendar.YEAR, anoMesReferencia);

		Integer dia = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		calendar.set(Calendar.DATE, dia);

		Date retorno = calendar.getTime();

		return retorno;
	}


	/**
	 * Método que Retira caracteres especias do fone
	 * 
	 * @author Anderson Italo
	 * @date 23/04/2013
	 */
	public static String retirarFormatacaoFone(String telefone){

		String retorno = "";

		retorno = substituirCaracteresEspeciais(telefone);
		retorno = retorno.trim();

		return retorno;
	}

	/**
	 * Método retornar uma String no formato AAAA-MM-DD-HH.MM.SS.MMM000 a partir da data recebida.
	 * 
	 * @param data
	 *            "objeto do tipo Date"
	 * @return String "no formato AAAA-MM-DD-HH.MM.SS.MMM000"
	 */
	public static String formatarDataParaTimesTampDB2(Date data){

		StringBuffer dataBD = new StringBuffer();

		if(data != null){

			Calendar dataCalendar = new GregorianCalendar();
			dataCalendar.setTime(data);
			dataBD.append(dataCalendar.get(Calendar.YEAR));
			dataBD.append("-");

			if((dataCalendar.get(Calendar.MONTH) + 1) > 9){

				dataBD.append(dataCalendar.get(Calendar.MONTH) + 1 + "-");
			}else{

				dataBD.append("0" + (dataCalendar.get(Calendar.MONTH) + 1) + "-");
			}

			if(dataCalendar.get(Calendar.DAY_OF_MONTH) > 9){

				dataBD.append(dataCalendar.get(Calendar.DAY_OF_MONTH) + "-");
			}else{

				dataBD.append("0" + dataCalendar.get(Calendar.DAY_OF_MONTH) + "-");
			}

			if(dataCalendar.get(Calendar.HOUR_OF_DAY) > 9){

				dataBD.append(dataCalendar.get(Calendar.HOUR_OF_DAY));
			}else{

				dataBD.append("0" + dataCalendar.get(Calendar.HOUR_OF_DAY));
			}

			dataBD.append(".");

			if(dataCalendar.get(Calendar.MINUTE) > 9){

				dataBD.append(dataCalendar.get(Calendar.MINUTE));
			}else{

				dataBD.append("0" + dataCalendar.get(Calendar.MINUTE));
			}

			dataBD.append(".");

			if(dataCalendar.get(Calendar.SECOND) > 9){

				dataBD.append(dataCalendar.get(Calendar.SECOND));
			}else{

				dataBD.append("0" + dataCalendar.get(Calendar.SECOND));
			}

			dataBD.append(".");

			if(dataCalendar.get(Calendar.MILLISECOND) > 9 && dataCalendar.get(Calendar.MILLISECOND) < 100){

				dataBD.append("0" + dataCalendar.get(Calendar.MILLISECOND));
				dataBD.append("000");
			}else if(dataCalendar.get(Calendar.MILLISECOND) >= 100 && dataCalendar.get(Calendar.MILLISECOND) < 1000){

				dataBD.append(dataCalendar.get(Calendar.MILLISECOND));
				dataBD.append("000");
			}else{

				dataBD.append("0" + dataCalendar.get(Calendar.MILLISECOND));
				dataBD.append("0000");
			}
		}

		return dataBD.toString();
	}

	/**
	 * [OC1111073]: Esse método foi criado para dividir a lista de string passa na quantidade de no
	 * máximo 1000 posições.
	 * Assim, evitará erros nas consultas com a cláusula "in".
	 * Autor: Josenildo Neves.
	 * Data: 30/07/2013.
	 * 
	 * @param lista
	 * @param novaLista
	 * @param idsRotas
	 * @return
	 */
	public static List<String> retornaListaStringComLimiteItemConsulta(String listaStr){

		List<String> lista = new ArrayList<String>();
		List<String> novaLista = new ArrayList<String>();
		String idsRotas = "";
		int tamanhoLista;

		// Transforma a String em lista.
		if(Util.isNaoNuloBrancoZero(listaStr)){
			String[] listaVet = listaStr.split(",");
			for(int i = 0; i < listaVet.length; i++){
				lista.add(listaVet[i]);
			}
		}
		// Valida se a lista passada é maior que ZERO.
		while(lista.size() > ConstantesSistema.ZERO.intValue()){
			// Se a string idsRotas for igual a branco ou nulo, atribui a primeira posição da lista
			// e a remove.
			if(Util.isVazioOuBranco(idsRotas)){
				idsRotas = lista.get(ConstantesSistema.ZERO.intValue());
				lista.remove(ConstantesSistema.ZERO.intValue());
			}
			// Atribui os ítens restante até o final ou completar as 1000 posições.
			for(int i = 0; i < ConstantesSistema.QUANTIDADE_LIMITE_ITEM_CONSULTA - 1; i++){
				if(i < lista.size()){
					idsRotas += "," + lista.get(i);
				}else{
					break;
				}

			}
			// Verifica se o tamanho a lista é maior que 999, se for, remove os 999, se não, remove
			// o tamanho da lista.
			if(lista.size() > ConstantesSistema.QUANTIDADE_LIMITE_ITEM_CONSULTA - 1){
				for(int i = 0; i < ConstantesSistema.QUANTIDADE_LIMITE_ITEM_CONSULTA - 1; i++){
					lista.remove(ConstantesSistema.ZERO.intValue());
				}
			}else{
				tamanhoLista = lista.size();
				for(int i = 0; i < tamanhoLista; i++){
					lista.remove(ConstantesSistema.ZERO.intValue());
				}
			}
			// Add a string na nova lista para retornar.
			novaLista.add(idsRotas);
			idsRotas = "";
		}

		return novaLista;
	}

	/**
	 * Retorna o valor de Inscrição Estadual Formatada
	 * 
	 * @return O valor de inscricaoEstadualFormatada
	 */
	public static String formatarInscricaoEstadual(String inscricaoEstadual){

		String inscricaoEstadualFormatada = inscricaoEstadual;
		String zeros = "";

		if(inscricaoEstadualFormatada != null){

			for(int a = 0; a < (14 - inscricaoEstadualFormatada.length()); a++){
				zeros = zeros.concat("0");
			}
			// concatena os zeros ao numero
			// caso o numero seja diferente de nulo
			inscricaoEstadualFormatada = zeros.concat(inscricaoEstadualFormatada);

			inscricaoEstadualFormatada = inscricaoEstadualFormatada.substring(0, 13) + "-" + inscricaoEstadualFormatada.substring(13, 14);
		}

		return inscricaoEstadualFormatada;
	}

	/**
	 * Retorna o valor de juntaComercialFormatada
	 * 
	 * @return O valor de juntaComercialFormatada
	 */
	public static String formatarJuntaComercial(String juntaComercial){

		String juntaComercialFormatada = juntaComercial;

		if(juntaComercialFormatada != null){
			juntaComercialFormatada = juntaComercialFormatada.substring(0, 2) + "." + juntaComercialFormatada.substring(2, 3) + "."
							+ juntaComercialFormatada.substring(3);
		}

		return juntaComercialFormatada;
	}

	/**
	 * Subtrai duas referências no formato AAAAMM. O resultado é o número de meses entre a
	 * referencia1 e a referencia2.
	 * 
	 * @author Luciano Galvão
	 * @date 20/11/2013
	 */
	public static Integer subtrairReferenciasAnoMes(int referencia1, int referencia2){

		Integer resultado = null;
		int diferenca = 0;

		int refMaior = 0;
		int refMenor = 0;

		// Se a referencia2 é maior que a referencia1, o resultado deve ser negativo e, por isto, o
		// fatorMultiplicacao será -1.
		int fatorMultiplicacao = 1;

		// Verifica se as referências são válidas
		if(!validarAnoMesSemBarra(String.valueOf(referencia1)) && !validarAnoMesSemBarra(String.valueOf(referencia2))){

			// Identifica qual a maior e a menor referência
			if(referencia1 >= referencia2){
				refMaior = referencia1;
				refMenor = referencia2;
			}else{
				refMaior = referencia2;
				refMenor = referencia1;
				fatorMultiplicacao = -1;
			}

			// Separando ano e mês da maior referência
			int anoMaior = Integer.valueOf(String.valueOf(refMaior).substring(0, 4)).intValue();
			int mesMaior = Integer.valueOf(String.valueOf(refMaior).substring(4, 6)).intValue();

			// Separando ano e mês da menor referência
			int anoMenor = Integer.valueOf(String.valueOf(refMenor).substring(0, 4)).intValue();

			if(anoMaior == anoMenor){
				diferenca = refMaior - refMenor;

			}else{
				// Converte a maior referência para o ano da menor referência
				int refMaiorConvertida = Integer.parseInt(anoMenor + "" + (((anoMaior - anoMenor) * 12) + mesMaior));

				diferenca = refMaiorConvertida - refMenor;
			}

			// Multiplica a diferença calculada pelo fator de multiplicação
			resultado = diferenca * fatorMultiplicacao;
		}

		return resultado;
	}

	/**
	 * 1) Contar Número de Registros do arquivo gerado <br>
	 * 2) Dividir Número de Registros por 2: <br>
	 * 2.1) Se o Resto > 0, adicionar 1 ao Quociente. <br>
	 * 3)Dividir o arquivo inicial em 2, sendo: <br>
	 * 3.1) O primeiro arquivo contendo do primeiro registro até o quociente (Arquivo 1)<br>
	 * 3.2) O segundo arquivo contendo do segundo registro até o final (Arquivo 2)<br>
	 * 4) Juntar os dois arquivos dos passos 3.1 e 3.2 num arquivo final onde será formado por 1<br>
	 * registro do arquivo 1 alternado com um registro do arquivo 2, até o final de ambos.
	 * 
	 * @param colecaoObjeto
	 * @return
	 */
	public static Collection intercalarObjetosDaColecaoMetadeMetade(Collection colecaoObjeto){

		Collection retorno = new ArrayList();

		int size = colecaoObjeto.size();

		int metade = Util.dividirArredondarResultado(size, 2);

		Collection colA1 = new ArrayList();
		Collection colB2 = new ArrayList();

		int contador = 0;
		for(Iterator iterator = colecaoObjeto.iterator(); iterator.hasNext();){
			contador++;
			if(contador <= metade){
				colA1.add(iterator.next());
			}else{
				colB2.add(iterator.next());
			}
		}

		Iterator iA1 = colA1.iterator();
		Iterator iB2 = colB2.iterator();
		for(int i = 0; i < colA1.size(); i++){
			retorno.add(iA1.next());
			if(iB2.hasNext()){
				retorno.add(iB2.next());
			}
		}

		return retorno;
	}

	/**
	 * Compara uma variável do tipo Short a um dado valor
	 * 
	 * @author Luciano Galvao
	 * @date 22/11/2013
	 */
	public static boolean compararShort(Short valor1, Short valor2){

		return valor1 != null && valor1.equals(valor2);
	}

	/**
	 * @param objetoArray
	 * @return
	 */
	public static String[] removerValorBranco(String[] objetoArray){

		if(objetoArray != null){

			List<String> valores = new ArrayList<String>();
			for(String string : objetoArray){
				if(!Util.isVazioOuBranco(string)){
					valores.add(string);

				}
			}
			String[] retorno = new String[valores.size()];
			valores.toArray(retorno);
			return retorno;

		}
		return null;
	}


	/**
	 * Método que converte um MM/yyyy em dd/MM/yyyy sendo dd o último dia do mês
	 * 
	 * @author Ado Rocha
	 * @date 20/12/2013
	 * @param anoMes
	 * @return Date
	 */
	public static Date converterMesAnoParaDataFinalMes(String mesAno){

		// remove pontuações
		mesAno = mesAno.replaceAll("\\p{Punct}", "");

		String mes = mesAno.substring(0, 2);
		String ano = mesAno.substring(2, 6);

		Calendar calendario = new GregorianCalendar();
		calendario.set(Calendar.YEAR, Util.converterStringParaInteger(ano));
		calendario.set(Calendar.MONTH, Util.converterStringParaInteger(mes) - 1);
		calendario.set(Calendar.DATE, (calendario.getActualMaximum(Calendar.DATE)));

		calendario.set(Calendar.HOUR_OF_DAY, 0);
		calendario.set(Calendar.MINUTE, 0);
		calendario.set(Calendar.SECOND, 0);
		calendario.set(Calendar.MILLISECOND, 0);

		return calendario.getTime();
	}

	/**
	 * Método que converte um Mes/Ano em uma Data Inicial
	 * 
	 * @author Ado Rocha
	 * @date 20/12/2013
	 * @param mesAno
	 * @return Date
	 */
	public static Date converterMesAnoParaDataInicial(String mesAno){

		// remove pontuações
		mesAno = mesAno.replaceAll("\\p{Punct}", "");

		String ano = mesAno.substring(2, 6);
		String mes = mesAno.substring(0, 2);

		Calendar calendario = new GregorianCalendar();
		calendario.set(Calendar.YEAR, Util.converterStringParaInteger(ano));
		calendario.set(Calendar.MONTH, Util.converterStringParaInteger(mes) - 1);
		calendario.set(Calendar.DATE, calendario.getActualMinimum(Calendar.DATE));

		calendario.set(Calendar.HOUR_OF_DAY, 0);
		calendario.set(Calendar.MINUTE, 0);
		calendario.set(Calendar.SECOND, 0);
		calendario.set(Calendar.MILLISECOND, 0);

		return calendario.getTime();
	}

	/**
	 * Formatar o código de barras no formato I25
	 * 
	 * @author Hebert Falcão
	 * @date 01/03/2014
	 */
	public static String formatarParaCodigoBarrasI25(String codigoBarra){

		String retorno = "";

		if(!isVazioOuBranco(codigoBarra)){

			Map<String, String> i25Map = new HashMap<String, String>();

			i25Map.put("00", "nnWWn");
			i25Map.put("01", "NnwwN");
			i25Map.put("02", "nNwwN");
			i25Map.put("03", "NNwwn");
			i25Map.put("04", "nnWwN");
			i25Map.put("05", "NnWwn");
			i25Map.put("06", "nNWwn");
			i25Map.put("07", "nnwWN");
			i25Map.put("08", "NnwWn");
			i25Map.put("09", "nNwWn");
			i25Map.put("10", "wnNNw");
			i25Map.put("11", "WnnnW");
			i25Map.put("12", "wNnnW");
			i25Map.put("13", "WNnnw");
			i25Map.put("14", "wnNnW");
			i25Map.put("15", "WnNnw");
			i25Map.put("16", "wNNnw");
			i25Map.put("17", "wnnNW");
			i25Map.put("18", "WnnNw");
			i25Map.put("19", "wNnNw");
			i25Map.put("20", "nwNNw");
			i25Map.put("21", "NwnnW");
			i25Map.put("22", "nWnnW");
			i25Map.put("23", "NWnnw");
			i25Map.put("24", "nwNnW");
			i25Map.put("25", "NwNnw");
			i25Map.put("26", "nWNnw");
			i25Map.put("27", "nwnNW");
			i25Map.put("28", "NwnNw");
			i25Map.put("29", "nWnNw");
			i25Map.put("30", "wwNNn");
			i25Map.put("31", "WwnnN");
			i25Map.put("32", "wWnnN");
			i25Map.put("33", "WWnnn");
			i25Map.put("34", "wwNnN");
			i25Map.put("35", "WwNnn");
			i25Map.put("36", "wWNnn");
			i25Map.put("37", "wwnNN");
			i25Map.put("38", "WwnNn");
			i25Map.put("39", "wWnNn");
			i25Map.put("40", "nnWNw");
			i25Map.put("41", "NnwnW");
			i25Map.put("42", "nNwnW");
			i25Map.put("43", "NNwnw");
			i25Map.put("44", "nnWnW");
			i25Map.put("45", "NnWnw");
			i25Map.put("46", "nNWnw");
			i25Map.put("47", "nnwNW");
			i25Map.put("48", "NnwNw");
			i25Map.put("49", "nNwNw");
			i25Map.put("50", "wnWNn");
			i25Map.put("51", "WnwnN");
			i25Map.put("52", "wNwnN");
			i25Map.put("53", "WNwnn");
			i25Map.put("54", "wnWnN");
			i25Map.put("55", "WnWnn");
			i25Map.put("56", "wNWnn");
			i25Map.put("57", "wnwNN");
			i25Map.put("58", "WnwNn");
			i25Map.put("59", "wNwNn");
			i25Map.put("60", "nwWNn");
			i25Map.put("61", "NwwnN");
			i25Map.put("62", "nWwnN");
			i25Map.put("63", "NWwnn");
			i25Map.put("64", "nwWnN");
			i25Map.put("65", "NwWnn");
			i25Map.put("66", "nWWnn");
			i25Map.put("67", "nwwNN");
			i25Map.put("68", "NwwNn");
			i25Map.put("69", "nWwNn");
			i25Map.put("70", "nnNWw");
			i25Map.put("71", "NnnwW");
			i25Map.put("72", "nNnwW");
			i25Map.put("73", "NNnww");
			i25Map.put("74", "nnNwW");
			i25Map.put("75", "NnNww");
			i25Map.put("76", "nNNww");
			i25Map.put("77", "nnnWW");
			i25Map.put("78", "NnnWw");
			i25Map.put("79", "nNnWw");
			i25Map.put("80", "wnNWn");
			i25Map.put("81", "WnnwN");
			i25Map.put("82", "wNnwN");
			i25Map.put("83", "WNnwn");
			i25Map.put("84", "wnNwN");
			i25Map.put("85", "WnNwn");
			i25Map.put("86", "wNNwn");
			i25Map.put("87", "wnnWN");
			i25Map.put("88", "WnnWn");
			i25Map.put("89", "wNnWn");
			i25Map.put("90", "nwNWn");
			i25Map.put("91", "NwnwN");
			i25Map.put("92", "nWnwN");
			i25Map.put("93", "NWnwn");
			i25Map.put("94", "nwNwN");
			i25Map.put("95", "NwNwn");
			i25Map.put("96", "nWNwn");
			i25Map.put("97", "nwnWN");
			i25Map.put("98", "NwnWn");
			i25Map.put("99", "nWnWn");

			StringBuilder codigoBarraI25 = new StringBuilder();

			codigoBarraI25.append("<");

			int contador = 0;

			String chave = "";
			String valor = "";

			while(contador <= codigoBarra.length() - 1){
				chave = codigoBarra.substring(contador, contador + 2);

				valor = i25Map.get(chave);

				codigoBarraI25.append(valor);

				contador = contador + 2;
			}

			codigoBarraI25.append(">");

			retorno = codigoBarraI25.toString();
		}

		return retorno;
	}
	
	/**
	 * Método que converte um Ano/Mes/Dia (AAAAMMDD) para DD/MM/AAAA
	 * 
	 * @author Ado Rocha
	 * @date 20/12/2013
	 * @param anoMesDia
	 * @return String
	 */
	public static String converterAAAAMMDD(String anoMesDia){

		String ano = anoMesDia.substring(0, 4);
		String mes = anoMesDia.substring(4, 6);
		String dia = anoMesDia.substring(6, 8);

		Calendar calendario = new GregorianCalendar();
		calendario.set(Calendar.YEAR, Util.converterStringParaInteger(ano));
		calendario.set(Calendar.MONTH, Util.converterStringParaInteger(mes) - 1);
		calendario.set(Calendar.DATE, Util.converterStringParaInteger(dia));

		calendario.set(Calendar.HOUR_OF_DAY, 0);
		calendario.set(Calendar.MINUTE, 0);
		calendario.set(Calendar.SECOND, 0);
		calendario.set(Calendar.MILLISECOND, 0);

		return formatarData(calendario.getTime());
	}

}