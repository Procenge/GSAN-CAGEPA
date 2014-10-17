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

import gcom.cadastro.localidade.GerenciaRegional;
import gcom.cadastro.localidade.Localidade;
import gcom.cadastro.localidade.UnidadeNegocio;
import gcom.faturamento.bean.FiltrarEmitirHistogramaEsgotoEconomiaHelper;


/**
 * @author mgrb
 *
 */
public class RepositorioHistogramaEsgotoEconomiaHelperEstadoPorLocalidade
				extends RepositorioHistogramaEsgotoEconomiaHelperGerenciaRegionalPorLocalidade {

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
					opcao = OpcaoTotalizacaoEnum.ESTADO_GERENCIA_REGIONAL.getOpcao();
					break;
				case 1:
					UnidadeNegocio un = new UnidadeNegocio();
					un.setId(itemQuebra[i]);
					filtroQuebra.setUnidadeNegocio(un);
					opcao = OpcaoTotalizacaoEnum.ESTADO_UNIDADE_NEGOCIO.getOpcao();
					break;
				case 2:
					Localidade locElo = new Localidade();
					locElo.setId(itemQuebra[i]);
					filtroQuebra.setEloPolo(locElo);
					opcao = OpcaoTotalizacaoEnum.ESTADO_ELO_POLO.getOpcao();
					break;
			}
		}
		filtroQuebra.setOpcaoTotalizacao(opcao);
		return filtroQuebra;
	}
}
