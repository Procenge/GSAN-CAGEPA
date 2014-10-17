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
package gcom.faturamento.histograma;

import gcom.faturamento.bean.FiltrarEmitirHistogramaEsgotoEconomiaHelper;
import gcom.faturamento.bean.HistogramaEsgotoEconomiaDTO;

import java.util.List;

import org.hibernate.Session;


/**
 * @author mgrb
 *
 */
public interface RepositorioHistogramaEsgotoEconomiaHelper {

	/** 
	 * M�todo getSQLQuery
	 * <p>Esse m�todo implementa a montagem da query que retorna os dados para o relatorio</p> 
	 * RASTREIO: [OC1073038][UC0606]
	 * @param session
	 * @return
	 * @author Marlos Ribeiro 
	 * @since 04/06/2013
	 */
	List<HistogramaEsgotoEconomiaDTO> executarPesquisa(Session session);

	/** 
	 * M�todo criarEmitirHistogramaAguaEsgotoEconomiaHelper
	 * <p>Esse m�todo implementa a cria��o de {@link HistogramaEsgotoEconomiaDTO} com base no array de objetos retornados da consulta criada por este componente.</p> 
	 * RASTREIO: [OC1073038][UC0606]
	 * @param objects
	 * @return
	 * @author Marlos Ribeiro 
	 * @since 04/06/2013
	 */
	HistogramaEsgotoEconomiaDTO criarEmitirHistogramaAguaEsgotoEconomiaHelper(Object[] objects, Session session);

	/**
	 * M�todo setFiltro
	 * <p>
	 * Esse m�todo implementa atribui o filtro para a pesquisa
	 * </p>
	 * RASTREIO: [OC1073038][UC0606]
	 * 
	 * @param filtro
	 * @author Marlos Ribeiro
	 * @since 06/06/2013
	 */
	void setFiltro(FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro);

	/**
	 * M�todo normalizarResultado
	 * <p>
	 * Esse m�todo implementa o preenchimento dos dados que n�o s�o apresentados na pagina do relatorio. Ex. Categorias que nao tem dados para a referencias, este metodo cria a linha com os valores gerados para a pagina.
	 * </p>
	 * RASTREIO: [OC1073038][UC0606]
	 * 
	 * @param dadosHistogramaTemp
	 * @param linkedHashMapConsumoFaixaCategoria
	 * @return
	 * @author Marlos Ribeiro
	 * @since 10/06/2013
	 */
	List<HistogramaEsgotoEconomiaDTO> normalizarResultado(List<Object[]> dadosHistogramaTemp, Session session);

	/**
	 * M�todo executarPesquisa
	 * <p>
	 * Esse m�todo implementa [mgrb] descrever o que implemntar
	 * </p>
	 * RASTREIO: [OCXXXXX][UCXXXX][SBXXXX]
	 * 
	 * @param totalizadorAnterior
	 * @param descricaoOpcaoTotalizacao
	 * @return
	 * @author Marlos Ribeiro
	 * @since 11/06/2013
	 */
	List<HistogramaEsgotoEconomiaDTO> executarPesquisa(String totalizadorAnterior, String totalizadorCorrente, Session session);

}
