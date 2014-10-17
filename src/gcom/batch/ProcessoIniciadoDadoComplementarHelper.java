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
 * 
 * GSANPCG
 * Eduardo Henrique
 */

package gcom.batch;

import gcom.util.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * @author mgrb
 */
public class ProcessoIniciadoDadoComplementarHelper {

	private static final String ENTRY_TOKEN = "#";

	private static final String KEY_VALUE_TOKEN = "=";

	public static final String SEPARADOR_CHAVES_METADADO = ";";

	private Map<DadoComplementarEnumerator, String> dadosComplementares;

	/**
	 * ProcessoIniciadoHelper
	 * <p>
	 * Esse m�todo Constroi uma inst�ncia de ProcessoIniciadoHelper.
	 * </p>
	 * 
	 * @author Marlos Ribeiro
	 * @since 14/01/2013
	 */
	public ProcessoIniciadoDadoComplementarHelper() {

		super();
		dadosComplementares = new HashMap<DadoComplementarEnumerator, String>();
	}

	/**
	 * ProcessoIniciadoDadoComplementarHelper
	 * <p>
	 * Esse m�todo Constroi uma inst�ncia de ProcessoIniciadoDadoComplementarHelper.
	 * </p>
	 * 
	 * @param descricaoDadosComplementares
	 * @author Marlos Ribeiro
	 * @since 14/01/2013
	 */
	public ProcessoIniciadoDadoComplementarHelper(String descricaoDadosComplementares) {

		this();
		if(Util.isNaoNuloBrancoZero(descricaoDadosComplementares)){
			StringTokenizer tokenizer = new StringTokenizer(descricaoDadosComplementares, ENTRY_TOKEN);
			while(tokenizer.hasMoreElements()){
				String nextElement = (String) tokenizer.nextElement();
				String[] entry = nextElement.split(KEY_VALUE_TOKEN);
				adcionarDadoComplementar(DadoComplementarEnumerator.get(entry[0]), entry.length > 1 ? entry[1] : "");
			}
		}
	}

	/**
	 * M�todo adcionarDadoComplementar
	 * 
	 * @param chave
	 * @param valor
	 * @author Marlos Ribeiro
	 * @return Retorna o valor anterior associado a chave.
	 * @since 14/01/2013
	 */
	public String adcionarDadoComplementar(DadoComplementarEnumerator chave, String valor){

		return dadosComplementares.put(chave, valor.trim());
	}

	/**
	 * M�todo formatarDadosComplementares
	 * 
	 * @return
	 * @author Marlos Ribeiro
	 * @since 14/01/2013
	 */
	public String getStringFormatoPesistencia(){

		List<DadoComplementarEnumerator> chaves = getChavesOrdenadas();
		StringBuilder builder = new StringBuilder();
		for(DadoComplementarEnumerator chave : chaves){
			if(builder.length() > 0){
				builder.append(ENTRY_TOKEN);
			}
			builder.append(chave.getChave());
			builder.append(KEY_VALUE_TOKEN);
			builder.append(dadosComplementares.get(chave));
		}
		return builder.toString();
	}

	private List<DadoComplementarEnumerator> getChavesOrdenadas(){

		List<DadoComplementarEnumerator> chaves = new ArrayList<DadoComplementarEnumerator>(dadosComplementares.keySet());
		Collections.sort(chaves, new Comparator<DadoComplementarEnumerator>() {

			public int compare(DadoComplementarEnumerator o1, DadoComplementarEnumerator o2){

				return o1.getChave().compareToIgnoreCase(o2.getChave());
			}
		});
		return chaves;
	}

	/**
	 * M�todo adcionarDadoComplementar
	 * 
	 * @param chave
	 * @param valor
	 * @author Marlos Ribeiro
	 * @return
	 * @since 14/01/2013
	 */
	public String adcionarDadoComplementar(DadoComplementarEnumerator chave, Integer valor){

		return adcionarDadoComplementar(chave, Util.isVazioOuBranco(valor) ? null : valor.toString());

	}

	/**
	 * M�todo adcionarDadoComplementar
	 * 
	 * @param chave
	 * @param object
	 * @author Marlos Ribeiro
	 * @return
	 * @since 14/01/2013
	 */
	public String adcionarDadoComplementar(DadoComplementarEnumerator chave, Object object){

		return adcionarDadoComplementar(chave, Util.isVazioOuBranco(object) ? null : object.toString());

	}

	/**
	 * M�todo getChaveValor
	 * 
	 * @param chave
	 * @return
	 * @author Marlos Ribeiro
	 * @since 14/01/2013
	 */
	public String getChaveValor(DadoComplementarEnumerator chave){

		if(!dadosComplementares.containsKey(chave)){
			throw new IllegalStateException("CHAVE [" + chave.getChave() + "] n�o existe nos dados complementares do processo iniciado.");
		}
		return chave.getChave() + KEY_VALUE_TOKEN + dadosComplementares.get(chave);
	}

	/**
	 * M�todo getFormatoExibicao
	 * 
	 * @return
	 * @author Marlos Ribeiro
	 * @since 16/01/2013
	 */
	public String getStringFormatoExibicao(){

		List<DadoComplementarEnumerator> chaves = getChavesOrdenadas();
		StringBuilder builder = new StringBuilder();
		for(DadoComplementarEnumerator chave : chaves){
			builder.append("<br/>");
			builder.append(chave.getDescricao());
			builder.append(": ");
			builder.append(dadosComplementares.get(chave));
		}
		return builder.toString();
	}

}
