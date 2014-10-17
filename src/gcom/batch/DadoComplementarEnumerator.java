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
 * 
 * GSANPCG
 * Eduardo Henrique
 */
package gcom.batch;

import java.util.HashMap;
import java.util.Map;

/**
 * @author mgrb
 *
 */
public enum DadoComplementarEnumerator {
	COBRANCA_ACAO_DESCRICAO("CBAC_DSCOBRANCAACAO", "Ação cobrança"), //
	COBRANCA_ACAO_ATIVIDADE_DESCRICAO("CBAT_DSCOBRANCAATIVIDADE", "Atividade"), //
	COBRANCA_ACAO_COMANDO_TITULO("CACM_DSTITULO", "Título"), //
	ANO_MES_REFERENCIA("AMREFERENCIA", "Referência"), //
	ARQUIVO_LEITURA_ANORMALIDADE_PATH("ARQUIVO_PATH", "Arquivo"), //
	USUARIO_ID("USUR_ID", "Id do usuário"), //
	USUARIO_NOME("USUR_NMUSUARIO", "Nome do usuário"), //
	GRUPO_DESCRICAO("DSGRUPO", "Grupo"), //
	ARRECADADOR_CODIGO_AGENTE("ARRC_CDAGENTE","Agente"), //
	ARRECADACAO_NSA_ESPERADO("NSA_ESPERADO","NSA"), //
	ARRECADACAO_TIPO_MOVIMENTO("TIPO_MOVIMENTO","Tipo movimento"), //
	ARRECADACAO_QUANTIDADE_REGISTROS("QUANTIDADE_REGISTROS", "Qtd registros"),
 ARRECADACAO_ARRECADADOR("ARRECADADOR", "Arrecadador"),
	FATURAMENTO_SIMULACAO_COMANDO_TITULO("FTSC_DSTITULO", "Título do comando"), FATURAMENTO_SIMULACAO_COMANDO_GRUPO_FATURAMENTO(
					"FTGR_DSGRUPO", "Grupo de faturamento"), FATURAMENTO_SIMULACAO_COMANDO_TIPO_CONSUMO_AGUA("FTSC_CDTIPOCONSUMOAGUA",
					"Tipo de consumo de água"), FATURAMENTO_SIMULACAO_COMANDO_TIPO_CONSUMO_ESGOTO("FTSC_CDTIPOCONSUMOESGOTO",
					"Tipo de consumo de esgoto"), FATURAMENTO_SIMULACAO_COMANDO_TARIFA_CONSUMO("CSTF_DSCONSUMOTARIFA", "Tarifa de consumo");

	private static final Map<String, DadoComplementarEnumerator> metaDados;

	static{
		metaDados = new HashMap<String, DadoComplementarEnumerator>();
		metaDados.put(COBRANCA_ACAO_DESCRICAO.getChave(), COBRANCA_ACAO_DESCRICAO);
		metaDados.put(COBRANCA_ACAO_ATIVIDADE_DESCRICAO.getChave(), COBRANCA_ACAO_ATIVIDADE_DESCRICAO);
		metaDados.put(COBRANCA_ACAO_COMANDO_TITULO.getChave(), COBRANCA_ACAO_COMANDO_TITULO);
		metaDados.put(ANO_MES_REFERENCIA.getChave(), ANO_MES_REFERENCIA);
		metaDados.put(ARQUIVO_LEITURA_ANORMALIDADE_PATH.getChave(), ARQUIVO_LEITURA_ANORMALIDADE_PATH);
		metaDados.put(USUARIO_ID.getChave(), USUARIO_ID);
		metaDados.put(USUARIO_NOME.getChave(), USUARIO_NOME);
		metaDados.put(GRUPO_DESCRICAO.getChave(), GRUPO_DESCRICAO);
		metaDados.put(ARRECADADOR_CODIGO_AGENTE.getChave(), ARRECADADOR_CODIGO_AGENTE);
		metaDados.put(ARRECADACAO_NSA_ESPERADO.getChave(), ARRECADACAO_NSA_ESPERADO);
		metaDados.put(ARRECADACAO_TIPO_MOVIMENTO.getChave(), ARRECADACAO_TIPO_MOVIMENTO);
		metaDados.put(ARRECADACAO_QUANTIDADE_REGISTROS.getChave(), ARRECADACAO_QUANTIDADE_REGISTROS);
		metaDados.put(ARRECADACAO_ARRECADADOR.getChave(), ARRECADACAO_ARRECADADOR);
		metaDados.put(FATURAMENTO_SIMULACAO_COMANDO_TITULO.getChave(), FATURAMENTO_SIMULACAO_COMANDO_TITULO);
		metaDados.put(FATURAMENTO_SIMULACAO_COMANDO_GRUPO_FATURAMENTO.getChave(), FATURAMENTO_SIMULACAO_COMANDO_GRUPO_FATURAMENTO);
		metaDados.put(FATURAMENTO_SIMULACAO_COMANDO_TIPO_CONSUMO_AGUA.getChave(), FATURAMENTO_SIMULACAO_COMANDO_TIPO_CONSUMO_AGUA);
		metaDados.put(FATURAMENTO_SIMULACAO_COMANDO_TIPO_CONSUMO_ESGOTO.getChave(), FATURAMENTO_SIMULACAO_COMANDO_TIPO_CONSUMO_ESGOTO);
		metaDados.put(FATURAMENTO_SIMULACAO_COMANDO_TARIFA_CONSUMO.getChave(), FATURAMENTO_SIMULACAO_COMANDO_TARIFA_CONSUMO);
	}

	private String chave;
	private String descricao;

	/**
	 * DadoComplementarEnumerator
	 * <p>
	 * Esse método Constroi uma instância de DadoComplementarEnumerator.
	 * </p>
	 * 
	 * @author Marlos Ribeiro
	 * @since 16/01/2013
	 */
	private DadoComplementarEnumerator(String chave, String descricao) {

		this.chave = chave;
		this.descricao = descricao;
	}

	/**
	 * @return the chave
	 */
	public String getChave(){

		return chave;
	}
	
	
	/**
	 * @return the descricao
	 */
	public String getDescricao(){

		return descricao;
	}

	/**
	 * Método get
	 * <p>
	 * Esse método implementa Recupera {@link DadoComplementarEnumerator} respectivo a chave.
	 * </p>
	 * RASTREIO:
	 * 
	 * @param string
	 * @return
	 * @author Marlos Ribeiro
	 * @since 16/01/2013
	 */
	public static DadoComplementarEnumerator get(String string){

		return metaDados.get(string);
	}
}
