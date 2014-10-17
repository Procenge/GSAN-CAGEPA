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
package gcom.faturamento.histograma;

import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.faturamento.bean.FiltrarEmitirHistogramaEsgotoEconomiaHelper;
import gcom.faturamento.bean.HistogramaEsgotoEconomiaDTO;

import org.hibernate.Hibernate;

/**
 * @author mgrb
 *
 */
public class RepositorioHistogramaEsgotoEconomiaHelperUnidadeNegocioPorLocalidade
				extends AbstractRepositorioHistogramaEsgotoEconomiaHelper {

	/**
	 * RepositorioHistogramaEsgotoEconomiaHelperUnidadeNegocioPorLocalidade
	 * <p>
	 * Esse método Constroi uma instância de
	 * RepositorioHistogramaEsgotoEconomiaHelperUnidadeNegocioPorLocalidade.
	 * </p>
	 * 
	 * @author Marlos Ribeiro
	 * @since 13/06/2013
	 */
	public RepositorioHistogramaEsgotoEconomiaHelperUnidadeNegocioPorLocalidade() {

		super();
		// COLUNAS ADCIONAIS
		adcionarColunaAdcional("un.UNEG_ID", "UNEG_ID", Hibernate.LONG);
		adcionarColunaAdcional("un.UNEG_NMUNIDADENEGOCIO", "UNEG_NMUNIDADENEGOCIO", Hibernate.STRING);
		adcionarColunaAdcional("un.UNEG_NMABREVIADO", "UNEG_NMABREVIADO", Hibernate.STRING);
		adcionarColunaAdcional("locElo.LOCA_CDELO", "LOCA_CDELO", Hibernate.LONG);
		adcionarColunaAdcional("locElo.LOCA_NMLOCALIDADE", "LOCA_NNELO", Hibernate.STRING);
		adcionarColunaAdcional("loc.LOCA_ID", "LOCA_ID", Hibernate.LONG);
		adcionarColunaAdcional("loc.LOCA_NMLOCALIDADE", "LOCA_NMLOCALIDADE", Hibernate.STRING);

		// RELACIONAMENTO
		adcionarRelacionamento("UNIDADE_NEGOCIO", "un", "UNEG_ID", FK_HEE_UNEG_ID);
		adcionarRelacionamento("LOCALIDADE", "locElo", "LOCA_ID", FK_HEE_LOCA_CDELO);
		adcionarRelacionamento("LOCALIDADE", "loc", "LOCA_ID", FK_HEE_LOCA_ID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected HistogramaEsgotoEconomiaDTO preencherHistogramaHelper(Object[] objects){

		HistogramaEsgotoEconomiaDTO histogramaDTO = new HistogramaEsgotoEconomiaDTO();
		histogramaDTO.setTotalizadorDescricao("TOTALIZADOR");
		StringBuffer opcaoTotalizacao = new StringBuffer();
		opcaoTotalizacao.append(objects[0]).append(DIVISOR_TOTALIZADOR_ID_DESC).append(String.valueOf(objects[2]).trim());
		opcaoTotalizacao.append(DIVISOR_TOTALIZADOR_ITEM);
		opcaoTotalizacao.append(objects[3]).append(DIVISOR_TOTALIZADOR_ID_DESC).append(String.valueOf(objects[4]).trim());
		opcaoTotalizacao.append(DIVISOR_TOTALIZADOR_ITEM);
		opcaoTotalizacao.append(objects[5]).append(DIVISOR_TOTALIZADOR_ID_DESC).append(String.valueOf(objects[6]).trim());
		histogramaDTO.setDescricaoOpcaoTotalizacao(opcaoTotalizacao.toString());
		return histogramaDTO;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected FiltrarEmitirHistogramaEsgotoEconomiaHelper criarFiltroQuebra(FiltrarEmitirHistogramaEsgotoEconomiaHelper filtro,
					Integer[] itemQuebra){

		FiltrarEmitirHistogramaEsgotoEconomiaHelper filtroQuebra = new FiltrarEmitirHistogramaEsgotoEconomiaHelper(filtro);
		int opcao = 1;
		for(int i = 0; i < itemQuebra.length; i++){
			switch(i){
				case 0:
					GerenciaRegional gr = new GerenciaRegional();
					gr.setId(itemQuebra[i]);
					filtroQuebra.setGerenciaRegional(gr);
					opcao = OpcaoTotalizacaoEnum.UNIDADE_NEGOCIO.getOpcao();
					break;
				case 1:
					UnidadeNegocio un = new UnidadeNegocio();
					un.setId(itemQuebra[i]);
					filtroQuebra.setUnidadeNegocio(un);
					opcao = OpcaoTotalizacaoEnum.UNIDADE_NEGOCIO_ELO_POLO.getOpcao();
					break;
			}
		}
		filtroQuebra.setOpcaoTotalizacao(opcao);
		return filtroQuebra;
	}

}
