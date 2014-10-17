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

import gcom.cadastro.localidade.FiltroSetorComercial;
import gcom.cadastro.localidade.SetorComercial;
import gcom.faturamento.bean.FiltrarEmitirHistogramaEsgotoEconomiaHelper;
import gcom.faturamento.bean.HistogramaEsgotoEconomiaDTO;
import gcom.util.ControladorException;
import gcom.util.ServiceLocator;
import gcom.util.Util;
import gcom.util.filtro.ParametroSimples;

import java.util.Collection;

import org.hibernate.Hibernate;


/**
 * @author mgrb
 *
 */
public class RepositorioHistogramaEsgotoEconomiaHelperSetorPorQuadra
				extends AbstractRepositorioHistogramaEsgotoEconomiaHelper {

	/**
	 * RepositorioHistogramaEsgotoEconomiaHelperSetorPorQuadra
	 * <p>
	 * Esse método Constroi uma instância de
	 * RepositorioHistogramaEsgotoEconomiaHelperSetorPorQuadra.
	 * </p>
	 * 
	 * @author Marlos Ribeiro
	 * @since 13/06/2013
	 */
	public RepositorioHistogramaEsgotoEconomiaHelperSetorPorQuadra() {

		super();
		// COLUNAS ADCIONAIS
		adcionarColunaAdcional("setor.STCM_CDSETORCOMERCIAL", "STCM_CDSETORCOMERCIAL", Hibernate.LONG);
		adcionarColunaAdcional("setor.STCM_NMSETORCOMERCIAL", "STCM_NMSETORCOMERCIAL", Hibernate.STRING);
		adcionarColunaAdcional("qdr.QDRA_ID", "QDRA_ID", Hibernate.LONG);
		adcionarColunaAdcional("qdr.QDRA_NNQUADRA", "QDRA_NNQUADRA", Hibernate.LONG);

		// RELACIONAMENTO
		adcionarRelacionamento("SETOR_COMERCIAL", "setor", "STCM_ID", FK_HEE_STCM_ID);
		adcionarRelacionamento("QUADRA", "qdr", "QDRA_ID", FK_HEE_QDRA_ID);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected HistogramaEsgotoEconomiaDTO preencherHistogramaHelper(Object[] objects){

		HistogramaEsgotoEconomiaDTO histogramaDTO = new HistogramaEsgotoEconomiaDTO();
		histogramaDTO.setTotalizadorDescricao("TOTALIZADOR");
		StringBuffer opcaoTotalizacao = new StringBuffer();
		opcaoTotalizacao.append(objects[0]).append(DIVISOR_TOTALIZADOR_ID_DESC).append(String.valueOf(objects[1]).trim());
		opcaoTotalizacao.append(DIVISOR_TOTALIZADOR_ITEM);
		opcaoTotalizacao.append(objects[2]).append(DIVISOR_TOTALIZADOR_ID_DESC).append(objects[3]);
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
					try{
						Collection setores;
						FiltroSetorComercial filtroSetorComercial = new FiltroSetorComercial();
						filtroSetorComercial.adicionarParametro(new ParametroSimples(FiltroSetorComercial.CODIGO_SETOR_COMERCIAL, Integer
										.valueOf(itemQuebra[i])));
						setores = ServiceLocator.getInstancia().getControladorUtil()
										.pesquisar(filtroSetorComercial, SetorComercial.class.getName());
						filtroQuebra.setSetorComercial((SetorComercial) Util.retonarObjetoDeColecao(setores));
						opcao = OpcaoTotalizacaoEnum.LOCALIDADE_SETOR.getOpcao();
					}catch(ControladorException e){
						throw new RuntimeException("Consulta de Setor Comercial falhou. STCM_CDSETORCOMERCIAL[" + itemQuebra[i] + "]");
					}
					break;
			}
		}
		filtroQuebra.setOpcaoTotalizacao(opcao);
		return filtroQuebra;
	}

}
