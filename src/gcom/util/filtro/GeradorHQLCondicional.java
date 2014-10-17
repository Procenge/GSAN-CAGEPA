/*
 * Copyright (C) 2007-2007 the GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
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
 * Foundation, Inc., 59 Temple Place � Suite 330, Boston, MA 02111-1307, USA
 */

/*
 * GSAN � Sistema Integrado de Gest�o de Servi�os de Saneamento
 * Copyright (C) <2007> 
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
 *
 * Este programa � software livre; voc� pode redistribu�-lo e/ou
 * modific�-lo sob os termos de Licen�a P�blica Geral GNU, conforme
 * publicada pela Free Software Foundation; vers�o 2 da
 * Licen�a.
 * Este programa � distribu�do na expectativa de ser �til, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia impl�cita de
 * COMERCIALIZA��O ou de ADEQUA��O A QUALQUER PROP�SITO EM
 * PARTICULAR. Consulte a Licen�a P�blica Geral GNU para obter mais
 * detalhes.
 * Voc� deve ter recebido uma c�pia da Licen�a P�blica Geral GNU
 * junto com este programa; se n�o, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.util.filtro;

import gcom.util.DateUtil;
import gcom.util.ErroRepositorioException;
import gcom.util.HibernateUtil;
import gcom.util.SistemaException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.hibernate.*;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Expression;

/**
 * O GeradorHQLCondicional � a classe que � respons�vel por gerar a parte
 * condicional de uma query HQL dependendo dos par�metros do filtro informado.
 * 
 * @author rodrigo
 */
public class GeradorHQLCondicional {

	/**
	 * Constr�i a query usando os par�metros informados no filtro
	 * 
	 * @param filtro
	 *            Filtro que cont�m parametros
	 * @param aliasTabela
	 *            Alias da tabela da query
	 * @param query
	 *            Query HQL (parte n�o-condicional)
	 * @param session
	 *            Session corrente do hibernate
	 * @return Objeto Query do Hibernate
	 * @exception HibernateException
	 *                Erro no Hibernate
	 * @throws ErroRepositorioException
	 */
	public static Query gerarCondicionalQuery(Filtro filtro, String className, String aliasTabela, String query, Session session)
					throws HibernateException, ErroRepositorioException{

		Collection parametros = filtro.getParametros();
		// Iterador para percorrer toda a lista de par�metros do filtro
		Iterator iteratorParametros = parametros.iterator();
		CondicionalQuery condicionalQueryRetorno = new CondicionalQuery();

		condicionalQueryRetorno.setConsultaSemLimites(filtro.isConsultaSemLimites());

		StringBuffer queryCondicional = condicionalQueryRetorno.getQuery();

		int numeroArgumentosIsoladosConector = 0;

		// Se o filtro n�o tiver par�metros, retorna uma string vazia
		if(!parametros.isEmpty()){
			// Coloca o where no come�o da string condicional
			queryCondicional.append(" where ");

			// Vai representar o nome de cada par�metro que for inserido na
			// query
			// O inteiro ser� convertido para caracter, para que o nome dos
			// parametros na query fique
			// no formato :a , :b , :c e etc.
			// Isso � feito porque as vers�es mais novas do hibernate n�o lidam
			// muito bem com parametros
			// posicionais nas queries
			int numeroNomeParametro = 97;

			while(iteratorParametros.hasNext()){

				// Percorre todos os par�metros do filtro
				FiltroParametro filtroParametro = (FiltroParametro) iteratorParametros.next();

				// Chama o m�todo de gera��o de acordo com o tipo do par�metro
				// Para cada par�metro, os valores correspondentes s�o
				// adicionados na cole��o

				// Se o numeroArgumentosIsoladosConector for maior que 0, ent�o
				// existe algum par�metro
				// que est� isolando outros por Conector
				if(numeroArgumentosIsoladosConector > 0){
					numeroArgumentosIsoladosConector = numeroArgumentosIsoladosConector - 1;
				}

				if(numeroArgumentosIsoladosConector == 0){
					numeroArgumentosIsoladosConector = filtroParametro.getNumeroArgumentosIsoladosPeloConector();
				}

				if(filtroParametro instanceof Intervalo){

					Intervalo intervalo = ((Intervalo) filtroParametro);

					condicionalQueryRetorno.getParametrosValores().add(
									converterValorParametro(className, filtro, filtroParametro, intervalo.getIntervaloInicial()));
					condicionalQueryRetorno.getParametrosValores().add(
									converterValorParametro(className, filtro, filtroParametro, intervalo.getIntervaloFinal()));
					geradorCondicional(condicionalQueryRetorno, intervalo, aliasTabela, numeroArgumentosIsoladosConector,
									numeroNomeParametro);

					numeroNomeParametro = numeroNomeParametro + 2;

				}else if(filtroParametro instanceof ParametroSimplesColecao){
					ParametroSimplesColecao parametroSimplesColecao = (ParametroSimplesColecao) filtroParametro;

					condicionalQueryRetorno.getParametrosValores().add(
									converterValorParametro(className, filtro, filtroParametro, parametroSimplesColecao.getValor()));
					geradorCondicional(condicionalQueryRetorno, parametroSimplesColecao, aliasTabela, numeroArgumentosIsoladosConector,
									numeroNomeParametro);

					numeroNomeParametro++;

				}else if(filtroParametro instanceof ParametroSimplesColecaoDiferenteDe){
					ParametroSimplesColecaoDiferenteDe parametroSimplesColecaoDiferenteDe = (ParametroSimplesColecaoDiferenteDe) filtroParametro;

					condicionalQueryRetorno.getParametrosValores().add(
									converterValorParametro(className, filtro, filtroParametro, parametroSimplesColecaoDiferenteDe
													.getValor()));
					geradorCondicional(condicionalQueryRetorno, parametroSimplesColecaoDiferenteDe, aliasTabela,
									numeroArgumentosIsoladosConector, numeroNomeParametro);

					numeroNomeParametro++;

				}else if(filtroParametro instanceof ParametroSimples){

					ParametroSimples parametroSimples = ((ParametroSimples) filtroParametro);

					condicionalQueryRetorno.getParametrosValores().add(
									converterValorParametro(className, filtro, filtroParametro, parametroSimples.getValor()));
					geradorCondicional(condicionalQueryRetorno, parametroSimples, aliasTabela, numeroArgumentosIsoladosConector,
									numeroNomeParametro);

					numeroNomeParametro++;

				}else if(filtroParametro instanceof ComparacaoTexto){

					ComparacaoTexto comparacaoTexto = ((ComparacaoTexto) filtroParametro);

					condicionalQueryRetorno.getParametrosValores().add(
									converterValorParametro(className, filtro, filtroParametro, comparacaoTexto.getValor()));
					geradorCondicional(condicionalQueryRetorno, comparacaoTexto, aliasTabela, numeroArgumentosIsoladosConector,
									numeroNomeParametro);

					numeroNomeParametro++;

				}else if(filtroParametro instanceof ComparacaoTextoCompleto){

					ComparacaoTextoCompleto comparacaoTextoCompleto = ((ComparacaoTextoCompleto) filtroParametro);

					condicionalQueryRetorno.getParametrosValores().add(
									converterValorParametro(className, filtro, filtroParametro, comparacaoTextoCompleto.getValor()));
					geradorCondicional(condicionalQueryRetorno, comparacaoTextoCompleto, aliasTabela, numeroArgumentosIsoladosConector,
									numeroNomeParametro);

					numeroNomeParametro++;

				}else if(filtroParametro instanceof ParametroNaoNulo){
					ParametroNaoNulo parametroNaoNulo = (ParametroNaoNulo) filtroParametro;

					geradorCondicional(condicionalQueryRetorno, parametroNaoNulo, aliasTabela, numeroArgumentosIsoladosConector);

				}else if(filtroParametro instanceof ParametroNulo){
					ParametroNulo parametroNulo = (ParametroNulo) filtroParametro;

					geradorCondicional(condicionalQueryRetorno, parametroNulo, aliasTabela, numeroArgumentosIsoladosConector);

				}else if(filtroParametro instanceof MenorQue){
					MenorQue menorQue = (MenorQue) filtroParametro;

					condicionalQueryRetorno.getParametrosValores().add(
									converterValorParametro(className, filtro, filtroParametro, menorQue.getNumero()));
					geradorCondicional(condicionalQueryRetorno, menorQue, aliasTabela, numeroArgumentosIsoladosConector,
									numeroNomeParametro);

					numeroNomeParametro++;

				}else if(filtroParametro instanceof Menor){
					Menor menor = (Menor) filtroParametro;

					geradorCondicional(condicionalQueryRetorno, menor, aliasTabela, numeroArgumentosIsoladosConector);

				}else if(filtroParametro instanceof MenorQueComparacaoColuna){
					MenorQueComparacaoColuna menorQueComparacaoColuna = (MenorQueComparacaoColuna) filtroParametro;

					geradorCondicional(condicionalQueryRetorno, menorQueComparacaoColuna, aliasTabela, numeroArgumentosIsoladosConector,
									numeroNomeParametro);
					// condicionalQueryRetorno.getParametrosValores().add(
					// menorQueComparacaoColuna
					// .getNomeAtributoIntervaloComparacao());

					// numeroNomeParametro++;

				}else if(filtroParametro instanceof MaiorQue){
					MaiorQue maiorQue = (MaiorQue) filtroParametro;

					condicionalQueryRetorno.getParametrosValores().add(
									converterValorParametro(className, filtro, filtroParametro, maiorQue.getNumero()));
					geradorCondicional(condicionalQueryRetorno, maiorQue, aliasTabela, numeroArgumentosIsoladosConector,
									numeroNomeParametro);

					numeroNomeParametro++;
				}else if(filtroParametro instanceof ComparacaoCampos){
					ComparacaoCampos comparacaoCampos = (ComparacaoCampos) filtroParametro;

					geradorCondicional(condicionalQueryRetorno, comparacaoCampos, aliasTabela, numeroArgumentosIsoladosConector);

				}else if(filtroParametro instanceof ParametroSimplesDiferenteDe){
					ParametroSimplesDiferenteDe parametroSimplesDiferenteDe = ((ParametroSimplesDiferenteDe) filtroParametro);

					condicionalQueryRetorno.getParametrosValores().add(
									converterValorParametro(className, filtro, filtroParametro, parametroSimplesDiferenteDe.getValor()));
					geradorCondicional(condicionalQueryRetorno, parametroSimplesDiferenteDe, aliasTabela, numeroArgumentosIsoladosConector,
									numeroNomeParametro);

					numeroNomeParametro++;
				}else if(filtroParametro instanceof ParametroVisibilidadeByFuncionalidade){

					ParametroVisibilidadeByFuncionalidade parametroVisibilidade = (ParametroVisibilidadeByFuncionalidade) filtroParametro;

					geradorCondicional(condicionalQueryRetorno, parametroVisibilidade, aliasTabela, numeroArgumentosIsoladosConector,
									numeroNomeParametro);

					condicionalQueryRetorno.getParametrosValores().add(parametroVisibilidade.getUsuarioLogado().getId());

					numeroNomeParametro++;
				}else if(filtroParametro instanceof Maior){
					Maior maior = (Maior) filtroParametro;

					geradorCondicional(condicionalQueryRetorno, maior, aliasTabela, numeroArgumentosIsoladosConector);
				}
			}
		}

		if(!query.contains("where")){

			query = query
							+ " "
							+ PersistenciaUtil.processaObjetosParaCarregamentoJoinFetch(aliasTabela, filtro
											.getColecaoCaminhosParaCarregamentoEntidades());
		}

		// tirar o �ltimo "and" da string condicional e montar a query completa
		if(queryCondicional.length() > 0){

			condicionalQueryRetorno.setQuery(new StringBuffer(query + queryCondicional.substring(0, queryCondicional.length() - 4)));
		}else{
			condicionalQueryRetorno.setQuery(new StringBuffer(query + queryCondicional));
		}

		// Verifica se o campo distinct da query foi informado no filtro
		if(filtro.getCampoDistinct() != null && !filtro.getCampoDistinct().trim().equalsIgnoreCase("")){
			// Verifica se o usu�rio j� tem um select na query para desabilitar
			// o distinct
			if(!query.startsWith("select")){
				condicionalQueryRetorno.setQuery(new StringBuffer("select distinct ").append(filtro.getCampoDistinct()).append(" ").append(
								condicionalQueryRetorno.getQuery()));

			}

		}

		// Se informado, seta o orderBy da query
		List<String> camposOrderBy = null;
		List<String> camposOrderByDesc = null;

		if(filtro.getCamposOrderBy() != null && !filtro.getCamposOrderBy().isEmpty()){
			camposOrderBy = filtro.getCamposOrderBy();
		}else if(filtro.getCamposOrderByDesc() != null && !filtro.getCamposOrderByDesc().isEmpty()){
			camposOrderByDesc = filtro.getCamposOrderByDesc();
		}

		if(camposOrderBy != null && !camposOrderBy.isEmpty()){
			Iterator<String> iteratorOrderBy = camposOrderBy.iterator();

			StringBuilder order = new StringBuilder("");
			while(iteratorOrderBy.hasNext()){
				order.append(aliasTabela);
				order.append(".");
				order.append(iteratorOrderBy.next());
				order.append(", ");
			}

			order.replace((order.length() - 2), order.length(), " ");

			condicionalQueryRetorno.setQuery(new StringBuffer(condicionalQueryRetorno.getQuery().append(" order by ").append(order)));

		}else if(camposOrderByDesc != null && !camposOrderByDesc.isEmpty()){
			Iterator<String> iteratorOrderBy = camposOrderByDesc.iterator();

			StringBuilder order = new StringBuilder("");
			while(iteratorOrderBy.hasNext()){
				order.append(aliasTabela);
				order.append(".");
				order.append(iteratorOrderBy.next());
				order.append(", ");
			}

			order.replace((order.length() - 2), order.length(), " ");

			condicionalQueryRetorno.setQuery(new StringBuffer(condicionalQueryRetorno.getQuery().append(" order by ").append(order)
							.append(" DESC ")));
		}
		// System.out.println(condicionalQueryRetorno.getQuery().toString());
		return efetuarQuery(condicionalQueryRetorno, session);
	}

	private static Class<?> getClassAtributoParamentro(String className, String nomeAtributo){

		if(StringUtils.isEmpty(className) || StringUtils.isEmpty(nomeAtributo)){
			return null;
		}

		int dotIndex = 0;
		String nomeAtributoAux = nomeAtributo;
		StringBuilder metodoGet = new StringBuilder();

		try{

			Class<?> classEntidade = Class.forName(className);

			while((dotIndex = nomeAtributoAux.indexOf(".")) != -1){

				String caminho = nomeAtributoAux.substring(0, dotIndex);
				metodoGet.append("get").append(Character.toUpperCase(caminho.charAt(0)));
				metodoGet.append(caminho.substring(1));

				classEntidade = classEntidade.getMethod(metodoGet.toString(), (Class<?>[]) null).getReturnType();
				nomeAtributoAux = nomeAtributoAux.substring(dotIndex + 1);
				metodoGet.delete(0, metodoGet.length());
			}

			metodoGet.append("get").append(Character.toUpperCase(nomeAtributoAux.charAt(0)));
			metodoGet.append(nomeAtributoAux.substring(1));

			return classEntidade.getMethod(metodoGet.toString(), (Class<?>[]) null).getReturnType();

		}catch(NoSuchMethodException e){
			return null;
		}catch(Exception e){
			throw new SistemaException(e, e.getMessage());
		}
	}

	private static Class<?> getClassAtributoParamentro(String className, Filtro filtro, FiltroParametro filtroParam){

		String nomeAtributo = filtroParam.getNomeAtributo();

		if(StringUtils.isEmpty(className) || StringUtils.isEmpty(nomeAtributo)){
			return null;
		}

		int dotIndex = 0;
		String nomeAtributoAux = nomeAtributo;
		StringBuilder metodoGet = new StringBuilder();

		try{

			Class<?> classEntidade = Class.forName(className);

			while((dotIndex = nomeAtributoAux.indexOf(".")) != -1){

				String caminho = nomeAtributoAux.substring(0, dotIndex);
				metodoGet.append("get").append(Character.toUpperCase(caminho.charAt(0)));
				metodoGet.append(caminho.substring(1));

				classEntidade = classEntidade.getMethod(metodoGet.toString(), (Class<?>[]) null).getReturnType();

				if(Collection.class.isAssignableFrom(classEntidade)){

					String caminhoCompleto = nomeAtributo.substring(0, dotIndex);
					filtro.adicionarCaminhoParaCarregamentoEntidade(caminhoCompleto, caminho);
					filtroParam.setNomeAtributo(nomeAtributo.replace(caminhoCompleto, caminho));
					filtroParam.setHasAlias(true);
					return null;
				}

				nomeAtributoAux = nomeAtributoAux.substring(dotIndex + 1);
				metodoGet.delete(0, metodoGet.length());
			}

			metodoGet.append("get").append(Character.toUpperCase(nomeAtributoAux.charAt(0)));
			metodoGet.append(nomeAtributoAux.substring(1));

			return classEntidade.getMethod(metodoGet.toString(), (Class<?>[]) null).getReturnType();

		}catch(NoSuchMethodException e){
			return null;
		}catch(Exception e){
			throw new SistemaException(e, e.getMessage());
		}
	}

	private static Object converterValorParametro(String className, Filtro filtro, FiltroParametro filtroParam, Object valorParametro){

		if(valorParametro == null){
			return valorParametro;
		}

		Class<?> classAtributoParametro = getClassAtributoParamentro(className, filtro, filtroParam);

		if(classAtributoParametro == null){
			return valorParametro;
		}

		if(valorParametro instanceof String && !String.class.equals(classAtributoParametro) && "".equals(((String) valorParametro).trim())){
			return null;
		}

		if(String.class.equals(classAtributoParametro)){

			return valorParametro.toString();

		}else if(Byte.class.equals(classAtributoParametro) || byte.class.equals(classAtributoParametro)){

			if(valorParametro instanceof String){
				return Byte.valueOf((String) valorParametro);
			}else if(valorParametro instanceof Number){
				return Byte.valueOf(((Number) valorParametro).byteValue());
			}

		}else if(Short.class.equals(classAtributoParametro) || short.class.equals(classAtributoParametro)){

			if(valorParametro instanceof String){
				return Short.valueOf((String) valorParametro);
			}else if(valorParametro instanceof Number){
				return Short.valueOf(((Number) valorParametro).shortValue());
			}

		}else if(Integer.class.equals(classAtributoParametro) || int.class.equals(classAtributoParametro)){

			if(valorParametro instanceof String){
				return Integer.valueOf((String) valorParametro);
			}else if(valorParametro instanceof Number){
				return Integer.valueOf(((Number) valorParametro).intValue());
			}

		}else if(Long.class.equals(classAtributoParametro) || long.class.equals(classAtributoParametro)){

			if(valorParametro instanceof String){
				return Long.valueOf((String) valorParametro);
			}else if(valorParametro instanceof Number){
				return Long.valueOf(((Number) valorParametro).longValue());
			}

		}else if(BigInteger.class.equals(classAtributoParametro)){

			if(valorParametro instanceof String){
				return new BigInteger((String) valorParametro);
			}else if(valorParametro instanceof Number){
				return BigInteger.valueOf(((Number) valorParametro).longValue());
			}

		}else if(Float.class.equals(classAtributoParametro) || float.class.equals(classAtributoParametro)){

			if(valorParametro instanceof String){
				return Float.valueOf((String) valorParametro);
			}else if(valorParametro instanceof Number){
				return Float.valueOf(((Number) valorParametro).floatValue());
			}

		}else if(Double.class.equals(classAtributoParametro) || double.class.equals(classAtributoParametro)){

			if(valorParametro instanceof String){
				return Double.valueOf((String) valorParametro);
			}else if(valorParametro instanceof Number){
				return Double.valueOf(((Number) valorParametro).doubleValue());
			}

		}else if(BigDecimal.class.equals(classAtributoParametro)){

			if(valorParametro instanceof String){
				return new BigDecimal((String) valorParametro);
			}else if(valorParametro instanceof Number){
				return BigDecimal.valueOf(((Number) valorParametro).doubleValue());
			}

		}else if(Date.class.equals(classAtributoParametro)){

			if(valorParametro instanceof String){

				try{
					valorParametro = DateUtil.parse((String) valorParametro);
				}catch(ParseException e){
					throw new SistemaException(e);
				}
			}

			if(valorParametro instanceof Date){

				int dotIndex = 0;
				String nomeAtributo = filtroParam.getNomeAtributo();

				if(StringUtils.isNotEmpty(nomeAtributo) && (dotIndex = nomeAtributo.lastIndexOf(".")) != -1){

					String caminhoAtributo = nomeAtributo.substring(0, dotIndex);
					Class<?> classAncestralAtributo = getClassAtributoParamentro(className, caminhoAtributo);
					nomeAtributo = nomeAtributo.substring(dotIndex + 1);
					className = classAncestralAtributo.getName();
				}

				Date valorParametroData = (Date) valorParametro;
				String tipo = HibernateUtil.getTypeNameProperClassMappings(className, nomeAtributo);

				if(Timestamp.class.getName().equals(tipo)){
					return new Timestamp(valorParametroData.getTime());
				}else if(Time.class.getName().equals(tipo)){
					return new Time(valorParametroData.getTime());
				}else if(java.sql.Date.class.getName().equals(tipo)){
					return new java.sql.Date(valorParametroData.getTime());
				}
			}
		}

		return valorParametro;
	}

	/**
	 * M�todo auxiliar para inserir par�metros dinamicamente na query
	 * 
	 * @param condicionalQuery
	 *            Objeto que cont�m os valores dos par�metros da query
	 * @param session
	 *            Session corrente do hibernate
	 * @return Objeto Query do hibernate
	 * @exception HibernateException
	 *                Erro no hibernate
	 */
	private static Query efetuarQuery(CondicionalQuery condicionalQuery, Session session) throws HibernateException{

		// Cria a query do hibernate
		Query query = session.createQuery(condicionalQuery.getQuery().toString());

		Iterator iterator = condicionalQuery.getParametrosValores().iterator();
		int i = 97;

		// Percorre a cole��o de par�metros e faz a associa��o com a query
		while(iterator.hasNext()){
			Object parametro = iterator.next();

			// Verifica se algum par�metro possui caracteres com acento para
			// serem convertidos
			if(parametro instanceof String){
				String parametroString = (String) parametro;
				// Verifica se existe algum caracter especial na String
				if(parametroString != null && !parametroString.trim().equals("")
								&& !parametroString.matches("[\\w&&[^��������������������������]]*")){

					// Faz o replace para tirar os caracteres especiais da
					// String
					parametro = parametroString.trim().replace('�', 'A').replace('�', 'O').replace('�', 'A').replace('�', 'E').replace('�',
									'I').replace('�', 'U').replace('�', 'A').replace('�', 'A').replace('�', 'E').replace('�', 'O').replace(
									'�', 'U').replace('�', 'C').replace('�', 'A').replace('�', 'O').replace('�', 'A').replace('�', 'E')
									.replace('�', 'I').replace('�', 'U').replace('�', 'A').replace('�', 'A').replace('�', 'E').replace('�',
													'O').replace('�', 'U').replace('�', 'C'); // �������������

				}
			}

			if(parametro instanceof String){
				query.setString("a" + i, (String) parametro);
				i++;
			}else if(parametro instanceof Collection<?>){
				Collection<?> parametroCollection = (Collection<?>) parametro;
				try{
					query.setParameterList("a" + i, parametroCollection);
				}catch(HibernateException e){
					query.setParameter("a" + i, parametro);
				}finally{
					i++;
				}
			}else if(parametro instanceof Byte){
				query.setByte("a" + i, (Byte) parametro);
				i++;
			}else if(parametro instanceof Short){
				query.setShort("a" + i, (Short) parametro);
				i++;
			}else if(parametro instanceof Integer){
				query.setInteger("a" + i, (Integer) parametro);
				i++;
			}else if(parametro instanceof BigInteger){
				query.setBigInteger("a" + i, (BigInteger) parametro);
				i++;
			}else if(parametro instanceof Long){
				query.setLong("a" + i, (Long) parametro);
				i++;
			}else if(parametro instanceof Float){
				query.setFloat("a" + i, (Float) parametro);
				i++;
			}else if(parametro instanceof Double){
				query.setDouble("a" + i, (Double) parametro);
				i++;
			}else if(parametro instanceof BigDecimal){
				query.setBigDecimal("a" + i, (BigDecimal) parametro);
				i++;
			}else if(parametro instanceof Timestamp){
				query.setTimestamp("a" + i, (Timestamp) parametro);
				i++;
			}else if(parametro instanceof Time){
				query.setTime("a" + i, (Time) parametro);
				i++;
			}else if(parametro instanceof Date){
				query.setDate("a" + i, (Date) parametro);
				i++;
			}else{
				query.setParameter("a" + i, parametro);
				i++;
			}

		}

		// if (!condicionalQuery.isConsultaSemLimites()) {
		// Seta o n�mero m�ximo de resultados retornados pela pesquisa
		// query
		// .setMaxResults(ConstantesSistema.NUMERO_MAXIMO_REGISTROS_PESQUISA);
		// }

		return query;
	}

	/**
	 * Gera a cl�usula condicional de um intervalo na query
	 * 
	 * @param condicionalQueryRetorno
	 *            Descri��o do par�metro
	 * @param intervalo
	 *            Intervalo do filtro
	 * @param aliasTabela
	 *            alias da tabela da query
	 * @param numeroArgumentosIsoladosConector
	 *            Descri��o do par�metro
	 */
	private static void geradorCondicional(CondicionalQuery condicionalQueryRetorno, Intervalo intervalo, String aliasTabela,
					int numeroArgumentosIsoladosConector, int numeroNomeParametro){

		condicionalQueryRetorno.getQuery().append(
						processarIsolamentoConector((intervalo.hasAlias() ? "" : aliasTabela + ".") + intervalo.getNomeAtributo()
										+ " between " + ":" + "a" + numeroNomeParametro + " and " + ":" + "a" + (numeroNomeParametro + 1)
										+ intervalo.getConector(), intervalo, numeroArgumentosIsoladosConector));

	}

	/**
	 * Gera a cl�usula condicional para um par�metro na query
	 * 
	 * @param condicionalQueryRetorno
	 *            Descri��o do par�metro
	 * @param parametro
	 *            Descri��o do par�metro
	 * @param aliasTabela
	 *            alias da tabela da query
	 * @param numeroArgumentosIsoladosConector
	 *            Descri��o do par�metro
	 */
	private static void geradorCondicional(CondicionalQuery condicionalQueryRetorno, ParametroSimples parametro, String aliasTabela,
					int numeroArgumentosIsoladosConector, int numeroNomeParametro){

		condicionalQueryRetorno.getQuery().append(
						processarIsolamentoConector((parametro.hasAlias() ? "" : aliasTabela + ".") + parametro.getNomeAtributo() + " = "
										+ ":" + "a" + numeroNomeParametro + parametro.getConector(), parametro,
										numeroArgumentosIsoladosConector));

	}

	/**
	 * Gera a cl�usula condicional para um par�metro na query
	 * 
	 * @param condicionalQueryRetorno
	 *            Descri��o do par�metro
	 * @param parametro
	 *            Descri��o do par�metro
	 * @param aliasTabela
	 *            alias da tabela da query
	 * @param numeroArgumentosIsoladosConector
	 *            Descri��o do par�metro
	 */
	private static void geradorCondicional(CondicionalQuery condicionalQueryRetorno, ParametroSimplesDiferenteDe parametro,
					String aliasTabela, int numeroArgumentosIsoladosConector, int numeroNomeParametro){

		condicionalQueryRetorno.getQuery().append(
						processarIsolamentoConector((parametro.hasAlias() ? "" : aliasTabela + ".") + parametro.getNomeAtributo() + " <> "
										+ ":" + "a" + numeroNomeParametro + parametro.getConector(), parametro,
										numeroArgumentosIsoladosConector));

	}

	/**
	 * Gera a cl�usula condicional para um par�metro na query
	 * 
	 * @param condicionalQueryRetorno
	 *            Descri��o do par�metro
	 * @param comparacaoCampos
	 *            Descri��o do par�metro
	 * @param aliasTabela
	 *            alias da tabela da query
	 * @param numeroArgumentosIsoladosConector
	 *            Descri��o do par�metro
	 */
	private static void geradorCondicional(CondicionalQuery condicionalQueryRetorno, ComparacaoCampos comparacaoCampos, String aliasTabela,
					int numeroArgumentosIsoladosConector){

		condicionalQueryRetorno.getQuery().append(
						processarIsolamentoConector((comparacaoCampos.hasAlias() ? "" : aliasTabela + ".")
										+ comparacaoCampos.getNomeAtributo() + " = "
										+ (comparacaoCampos.hasAlias() ? "" : aliasTabela + ".") + comparacaoCampos.getValor()
										+ comparacaoCampos.getConector(), comparacaoCampos, numeroArgumentosIsoladosConector));

	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param condicionalQueryRetorno
	 *            Descri��o do par�metro
	 * @param menorQue
	 *            Descri��o do par�metro
	 * @param aliasTabela
	 *            Descri��o do par�metro
	 * @param numeroArgumentosIsoladosConector
	 *            Descri��o do par�metro
	 */
	private static void geradorCondicional(CondicionalQuery condicionalQueryRetorno, MenorQue menorQue, String aliasTabela,
					int numeroArgumentosIsoladosConector, int numeroNomeParametro){

		condicionalQueryRetorno.getQuery().append(
						processarIsolamentoConector((menorQue.hasAlias() ? "" : aliasTabela + ".") + menorQue.getNomeAtributo() + " <= "
										+ ":" + "a" + numeroNomeParametro + menorQue.getConector(), menorQue,
										numeroArgumentosIsoladosConector));
	}

	private static void geradorCondicional(CondicionalQuery condicionalQueryRetorno, Menor menor, String aliasTabela,
					int numeroArgumentosIsoladosConector){

		condicionalQueryRetorno.getQuery().append(
						processarIsolamentoConector((menor.hasAlias() ? "" : aliasTabela + ".") + menor.getNomeAtributo() + " < "
										+ (menor.hasAlias() ? "" : aliasTabela + ".") + menor.getNomeSegundoAtributo()
										+ menor.getConector(), menor, numeroArgumentosIsoladosConector));
	}

	private static void geradorCondicional(CondicionalQuery condicionalQueryRetorno, Maior maior, String aliasTabela,
					int numeroArgumentosIsoladosConector){

		condicionalQueryRetorno.getQuery().append(
						processarIsolamentoConector((maior.hasAlias() ? "" : aliasTabela + ".") + maior.getNomeAtributo() + " > "
										+ (maior.hasAlias() ? "" : aliasTabela + ".") + maior.getNomeSegundoAtributo()
										+ maior.getConector(), maior, numeroArgumentosIsoladosConector));
	}

	private static void geradorCondicional(CondicionalQuery condicionalQueryRetorno, MenorQueComparacaoColuna menorQueComparacaoColuna,
					String aliasTabela, int numeroArgumentosIsoladosConector, int numeroNomeParametro){

		condicionalQueryRetorno.getQuery().append(
						processarIsolamentoConector((menorQueComparacaoColuna.hasAlias() ? "" : aliasTabela + ".")
										+ menorQueComparacaoColuna.getNomeAtributo() + " < "
										+ (menorQueComparacaoColuna.hasAlias() ? "" : aliasTabela + ".")
										+ menorQueComparacaoColuna.getNomeAtributoIntervaloComparacao()
										+ menorQueComparacaoColuna.getConector(), menorQueComparacaoColuna,
										numeroArgumentosIsoladosConector));

	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param condicionalQueryRetorno
	 *            Descri��o do par�metro
	 * @param maiorQue
	 *            Descri��o do par�metro
	 * @param aliasTabela
	 *            Descri��o do par�metro
	 * @param numeroArgumentosIsoladosConector
	 *            Descri��o do par�metro
	 */
	private static void geradorCondicional(CondicionalQuery condicionalQueryRetorno, MaiorQue maiorQue, String aliasTabela,
					int numeroArgumentosIsoladosConector, int numeroNomeParametro){

		condicionalQueryRetorno.getQuery().append(
						processarIsolamentoConector((maiorQue.hasAlias() ? "" : aliasTabela + ".") + maiorQue.getNomeAtributo() + " >= "
										+ ":" + "a" + numeroNomeParametro + maiorQue.getConector(), maiorQue,
										numeroArgumentosIsoladosConector));
	}

	/**
	 * Gera a cl�usula condicional para uma compara��o de texto na query
	 * 
	 * @param condicionalQueryRetorno
	 *            Descri��o do par�metro
	 * @param comparacaoTexto
	 *            Descri��o do par�metro
	 * @param aliasTabela
	 *            alias da tabela da query
	 * @param numeroArgumentosIsoladosConector
	 *            Descri��o do par�metro
	 */
	private static void geradorCondicional(CondicionalQuery condicionalQueryRetorno, ComparacaoTexto comparacaoTexto, String aliasTabela,
					int numeroArgumentosIsoladosConector, int numeroNomeParametro){

		condicionalQueryRetorno.getQuery().append(
						processarIsolamentoConector("upper(" + (comparacaoTexto.hasAlias() ? "" : aliasTabela + ".")
										+ comparacaoTexto.getNomeAtributo() + ")" + "  like " + "upper(:" + "a" + numeroNomeParametro + ")"
										+ comparacaoTexto.getConector(), comparacaoTexto, numeroArgumentosIsoladosConector));

	}

	/**
	 * Gera a cl�usula condicional para uma compara��o de texto na query
	 * 
	 * @param condicionalQueryRetorno
	 *            Descri��o do par�metro
	 * @param comparacaoTexto
	 *            Descri��o do par�metro
	 * @param aliasTabela
	 *            alias da tabela da query
	 * @param numeroArgumentosIsoladosConector
	 *            Descri��o do par�metro
	 */
	private static void geradorCondicional(CondicionalQuery condicionalQueryRetorno, ComparacaoTextoCompleto comparacaoTextoCompleto,
					String aliasTabela, int numeroArgumentosIsoladosConector, int numeroNomeParametro){

		condicionalQueryRetorno.getQuery().append(
						processarIsolamentoConector("upper(" + (comparacaoTextoCompleto.hasAlias() ? "" : aliasTabela + ".")
										+ comparacaoTextoCompleto.getNomeAtributo() + ")" + "  like " + "upper(:" + "a"
										+ numeroNomeParametro + ")" + comparacaoTextoCompleto.getConector(), comparacaoTextoCompleto,
										numeroArgumentosIsoladosConector));

	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param condicionalQueryRetorno
	 *            Descri��o do par�metro
	 * @param parametroNulo
	 *            Descri��o do par�metro
	 * @param aliasTabela
	 *            Descri��o do par�metro
	 * @param numeroArgumentosIsoladosConector
	 *            Descri��o do par�metro
	 */
	private static void geradorCondicional(CondicionalQuery condicionalQueryRetorno, ParametroNulo parametroNulo, String aliasTabela,
					int numeroArgumentosIsoladosConector){

		condicionalQueryRetorno.getQuery().append(
						processarIsolamentoConector((parametroNulo.hasAlias() ? "" : aliasTabela + ".") + parametroNulo.getNomeAtributo()
										+ " is null" + parametroNulo.getConector(), parametroNulo, numeroArgumentosIsoladosConector));

	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param condicionalQueryRetorno
	 *            Descri��o do par�metro
	 * @param parametroNaoNulo
	 *            Descri��o do par�metro
	 * @param aliasTabela
	 *            Descri��o do par�metro
	 * @param numeroArgumentosIsoladosConector
	 *            Descri��o do par�metro
	 */
	private static void geradorCondicional(CondicionalQuery condicionalQueryRetorno, ParametroNaoNulo parametroNaoNulo, String aliasTabela,
					int numeroArgumentosIsoladosConector){

		condicionalQueryRetorno.getQuery().append(
						processarIsolamentoConector((parametroNaoNulo.hasAlias() ? "" : aliasTabela + ".")
										+ parametroNaoNulo.getNomeAtributo() + " is not null" + parametroNaoNulo.getConector(),
										parametroNaoNulo, numeroArgumentosIsoladosConector));

	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param condicionalQueryRetorno
	 *            Descri��o do par�metro
	 * @param parametroSimplesColecao
	 *            Descri��o do par�metro
	 * @param aliasTabela
	 *            Descri��o do par�metro
	 * @param numeroArgumentosIsoladosConector
	 *            Descri��o do par�metro
	 */
	private static void geradorCondicional(CondicionalQuery condicionalQueryRetorno, ParametroSimplesColecao parametroSimplesColecao,
					String aliasTabela, int numeroArgumentosIsoladosConector, int numeroNomeParametro){

		condicionalQueryRetorno.getQuery()
						.append(
										processarIsolamentoConector((parametroSimplesColecao.hasAlias() ? "" : aliasTabela + ".")
														+ parametroSimplesColecao.getNomeAtributo() + " in ( " + ":" + "a"
														+ numeroNomeParametro + " )" + parametroSimplesColecao.getConector(),
														parametroSimplesColecao, numeroArgumentosIsoladosConector));

	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param condicionalQueryRetorno
	 *            Descri��o do par�metro
	 * @param parametroSimplesColecao
	 *            Descri��o do par�metro
	 * @param aliasTabela
	 *            Descri��o do par�metro
	 * @param numeroArgumentosIsoladosConector
	 *            Descri��o do par�metro
	 */
	private static void geradorCondicional(CondicionalQuery condicionalQueryRetorno,
					ParametroSimplesColecaoDiferenteDe parametroSimplesColecaoDiferenteDe, String aliasTabela,
					int numeroArgumentosIsoladosConector, int numeroNomeParametro){

		condicionalQueryRetorno.getQuery().append(
						processarIsolamentoConector((parametroSimplesColecaoDiferenteDe.hasAlias() ? "" : aliasTabela + ".")
										+ parametroSimplesColecaoDiferenteDe.getNomeAtributo() + " not in ( " + ":" + "a"
										+ numeroNomeParametro + " )" + parametroSimplesColecaoDiferenteDe.getConector(),
										parametroSimplesColecaoDiferenteDe, numeroArgumentosIsoladosConector));
	}

	private static void geradorCondicional(CondicionalQuery condicionalQueryRetorno, ParametroVisibilidadeByFuncionalidade parametro,
					String aliasTabela, int numeroArgumentosIsoladosConector, int numeroNomeParametro){

		condicionalQueryRetorno.getQuery().append(
						processarIsolamentoConector((parametro.hasAlias() ? "" : aliasTabela + ".") + parametro.getNomeAtributo()
										+ " in ( " + getQueryVisibilityByFuncionalidade(numeroNomeParametro) + " ) "
										+ parametro.getConector(), parametro, numeroArgumentosIsoladosConector));

	}

	private static String getQueryVisibilityByFuncionalidade(int numeroNomeParametro){

		StringBuffer query = new StringBuffer();
		query.append(" select distinct perm.comp_id.funcionalidadeId ");
		query.append(" from GrupoFuncionalidadeOperacao perm ");
		query.append(" where perm.comp_id.grupoId in ( ");
		query.append(" select distinct grup.comp_id.grupoId ");
		query.append(" from UsuarioGrupo grup ");
		query.append(" where grup.comp_id.usuarioId = :a");
		query.append(numeroNomeParametro).append(" ) ");

		return query.toString();
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @param condicional
	 *            Descri��o do par�metro
	 * @param filtro
	 *            Descri��o do par�metro
	 * @param numeroArgumentosIsoladosConector
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
	 */
	public static String processarIsolamentoConector(String condicional, FiltroParametro filtro, int numeroArgumentosIsoladosConector){

		String retorno = "";

		if(numeroArgumentosIsoladosConector > 0){

			// Se o n�mero de par�metros for indicado como �ltimo do isolamento
			if(numeroArgumentosIsoladosConector > 0 && numeroArgumentosIsoladosConector == 1){

				condicional = condicional.substring(0, condicional.length() - 5);

				retorno = condicional + ")" + filtro.getConector();

				// Se o n�mero de par�metros for indicado como o primeiro do
				// isolamento
			}else if(numeroArgumentosIsoladosConector == filtro.getNumeroArgumentosIsoladosPeloConector()){
				retorno = "(" + condicional;

			}else{
				retorno = condicional;

			}
		}else{
			retorno = condicional;
		}
		return retorno;
	}

	/**
	 * Este m�todo gera uma query usando o padr�o Criteria Queries do hibernate
	 * 
	 * @param session
	 *            Session do Hibernate
	 * @param filtro
	 *            Filtro com os par�metros de busca
	 * @param classe
	 *            Classe que ser� pesquisada
	 * @return Cole��o de resultados
	 * @exception HibernateException
	 *                Erro na query
	 */
	public static Collection gerarQueryCriteriaExpression(Session session, Filtro filtro, Class classe) throws HibernateException{

		// Obt�m os par�metros do filtro
		Collection parametros = filtro.getParametros();

		// Iterador para percorrer toda a lista de par�metros do filtro
		Iterator iteratorParametros = parametros.iterator();

		// Cole��o que ser� retornada
		Collection retorno = new ArrayList();

		// Se o filtro n�o tiver par�metros, retorna uma cole��o vazia
		if(!parametros.isEmpty()){
			// Monta a busca
			Criteria criteria = session.createCriteria(classe);

			// Parte que define quais cole��es v�o ser carregadas
			Collection colecaoCampos = filtro.getColecaoCaminhosParaCarregamentoEntidades();

			if(!colecaoCampos.isEmpty()){
				Iterator iterator = colecaoCampos.iterator();

				while(iterator.hasNext()){
					String campoColecao = (String) iterator.next();

					// Seta o carregamento da cole��o
					criteria.setFetchMode(campoColecao, FetchMode.DEFAULT);
				}

			}

			Criteria criteriaSubFiltro = null;

			while(iteratorParametros.hasNext()){
				// Percorre todos os par�metros do filtro
				FiltroParametro filtroParametro = (FiltroParametro) iteratorParametros.next();

				// Cria condicionais na query para subFiltros
				if(filtroParametro instanceof SubFiltro){

					SubFiltro subFiltro = (SubFiltro) filtroParametro;

					Iterator iteratorSubFiltro = subFiltro.getFiltro().getParametros().iterator();

					if(criteriaSubFiltro == null){
						criteriaSubFiltro = criteria.createCriteria(subFiltro.getNomeAtributo());
					}else{
						criteriaSubFiltro = criteriaSubFiltro.createCriteria(subFiltro.getNomeAtributo());

					}

					// Percorre todos os par�metros do subFiltro
					while(iteratorSubFiltro.hasNext()){

						FiltroParametro filtroParametroSubFiltro = (FiltroParametro) iteratorSubFiltro.next();

						// Adiciona a condicional a query
						criteriaSubFiltro = criteriaSubFiltro.add(avaliarParametrosQueryCriteriaExpression(session,
										filtroParametroSubFiltro));
					}

				}else{

					// Avalia os par�metros um a um
					criteria.add(avaliarParametrosQueryCriteriaExpression(session, filtroParametro));
				}

			}

			// Seta o campo de ordena��o da query
			// String campoOrder = filtro.getCampoOrderBy();
			//
			// if (campoOrder != null &&
			// !campoOrder.trim().equalsIgnoreCase("")) {
			// criteria.addOrder(Order.asc(campoOrder));
			// }

			// Seta o n�mero m�ximo de resultados que podem ser retornados
			// O valor est� em 101 porque o sistema n�o pretende mostrar mais do
			// que
			// 100 registros simultaneamente

			// Seta o n�mero m�ximo de resultados que podem ser retornados
			// O valor est� em 101 porque o sistema n�o pretende mostrar mais do
			// que
			// 100 registros simultaneamente
			criteria.setMaxResults(101);

			criteria.setCacheable(true);

			// Realiza a query e monta a cole��o de retorno
			retorno = criteria.list();
		}else{

			// Se nenhum par�metro for informado, a busca traz todos os
			// registros
			// Monta a busca
			Criteria criteria = session.createCriteria(classe);

			// Seta o campo de ordena��o da query
			// String campoOrder = filtro.getCampoOrderBy();
			//
			// if (campoOrder != null &&
			// !campoOrder.trim().equalsIgnoreCase("")) {
			// criteria.addOrder(Order.asc(campoOrder));
			// }

			// Seta o n�mero m�ximo de resultados que podem ser retornados
			// O valor est� em 101 porque o sistema n�o pretende mostrar mais do
			// que
			// 100 registros simultaneamente
			criteria.setMaxResults(101);

			criteria.setCacheable(true);

			// Realiza a query e monta a cole��o de retorno
			retorno = criteria.list();

		}

		return retorno;
	}

	/**
	 * Este m�todo avalia cada par�metro informado num filtro para ser
	 * adicionado como uma condicional de busca usando o padr�o Criteria Queries
	 * do hibernate
	 * 
	 * @param session
	 *            Session do Hibernate
	 * @param filtroParametro
	 *            Par�metro do filtro
	 * @return Cole��o de resultados
	 * @exception HibernateException
	 *                Erro na query
	 */
	public static Criterion avaliarParametrosQueryCriteriaExpression(Session session, FiltroParametro filtroParametro)
					throws HibernateException{

		Criterion retorno = null;

		// Chama o m�todo de gera��o de acordo com o tipo do par�metro
		if(filtroParametro instanceof Intervalo){

			Intervalo intervalo = ((Intervalo) filtroParametro);

			// Monta a condicional para a query
			retorno = Expression.between(intervalo.getNomeAtributo(), intervalo.getIntervaloInicial(), intervalo.getIntervaloFinal());

		}else if(filtroParametro instanceof ParametroSimples){

			ParametroSimples parametroSimples = ((ParametroSimples) filtroParametro);

			// Monta a condicional para a query
			retorno = Expression.eq(parametroSimples.getNomeAtributo(), parametroSimples.getValor());

		}else if(filtroParametro instanceof ComparacaoTexto){

			ComparacaoTexto comparacaoTexto = ((ComparacaoTexto) filtroParametro);

			// Monta a condicional para a query
			retorno = Expression.like(comparacaoTexto.getNomeAtributo(), comparacaoTexto.getValor()).ignoreCase();

		}else if(filtroParametro instanceof ParametroNaoNulo){
			ParametroNaoNulo parametroNaoNulo = (ParametroNaoNulo) filtroParametro;

			// Monta a condicional para a query
			retorno = Expression.isNotNull(parametroNaoNulo.getNomeAtributo());

		}else if(filtroParametro instanceof ParametroNulo){
			ParametroNulo parametroNulo = (ParametroNulo) filtroParametro;

			// Monta a condicional para a query
			retorno = Expression.isNull(parametroNulo.getNomeAtributo());

		}else if(filtroParametro instanceof ConectorAnd){

			ConectorAnd conectorAnd = (ConectorAnd) filtroParametro;
			// Obt�m os par�metros
			FiltroParametro filtro1 = conectorAnd.getFiltro1();
			FiltroParametro filtro2 = conectorAnd.getFiltro2();

			// Monta a condicional para a query
			retorno = Expression.and(avaliarParametrosQueryCriteriaExpression(session, filtro1), avaliarParametrosQueryCriteriaExpression(
							session, filtro2));

		}else if(filtroParametro instanceof ConectorOr){

			ConectorOr conectorOr = (ConectorOr) filtroParametro;
			// Obt�m os par�metros
			FiltroParametro filtro1 = conectorOr.getFiltro1();
			FiltroParametro filtro2 = conectorOr.getFiltro2();

			// Monta a condicional para a query
			retorno = Expression.or(avaliarParametrosQueryCriteriaExpression(session, filtro1), avaliarParametrosQueryCriteriaExpression(
							session, filtro2));

		}

		return retorno;
	}

	/**
	 * The main program for the GeradorHQLCondicional class
	 * 
	 * @param args
	 *            The command line arguments
	 */
	public static void main(String[] args){

	}

}
