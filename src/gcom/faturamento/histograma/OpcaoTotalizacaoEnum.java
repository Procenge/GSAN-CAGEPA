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



/**
 * @author mgrb
 *
 */
public enum OpcaoTotalizacaoEnum {

	ESTADO(1, RepositorioHistogramaEsgotoEconomiaHelperEstado.class), //
	ESTADO_GERENCIA_REGIONAL(2, RepositorioHistogramaEsgotoEconomiaHelperEstadoPorGerenciaRegional.class), //
	ESTADO_UNIDADE_NEGOCIO(3, RepositorioHistogramaEsgotoEconomiaHelperEstadoPorUnidadeNegocio.class), //
	ESTADO_ELO_POLO(4, RepositorioHistogramaEsgotoEconomiaHelperEstadoPorEloPolo.class), //
	ESTADO_LOCALIDADE(5, RepositorioHistogramaEsgotoEconomiaHelperEstadoPorLocalidade.class), //
	GERENCIA_REGIONAL(6, RepositorioHistogramaEsgotoEconomiaHelperGerenciaRegional.class), //
	GERENCIA_REGIONAL_UNIDADE_NEGOCIO(7, RepositorioHistogramaEsgotoEconomiaHelperGerenciaRegionalPorUnidadeNegocio.class), //
	GERENCIA_REGIONAL_ELO_POLO(8, RepositorioHistogramaEsgotoEconomiaHelperGerenciaRegionalPorEloPolo.class), //
	GERENCIA_REGIONAL_LOCALIDADE(9, RepositorioHistogramaEsgotoEconomiaHelperGerenciaRegionalPorLocalidade.class), //
	UNIDADE_NEGOCIO(10, RepositorioHistogramaEsgotoEconomiaHelperUnidadeNegocio.class), //
	UNIDADE_NEGOCIO_ELO_POLO(11, RepositorioHistogramaEsgotoEconomiaHelperUnidadeNegocioPorEloPolo.class), //
	UNIDADE_NEGOCIO_LOCALIDADE(12, RepositorioHistogramaEsgotoEconomiaHelperUnidadeNegocioPorLocalidade.class),//
	ELO_POLO(13, RepositorioHistogramaEsgotoEconomiaHelperEloPolo.class), //
	ELO_POLO_LOCALIDADE(14, RepositorioHistogramaEsgotoEconomiaHelperUnidadeEloPoloPorLocalidade.class), //
	ELO_POLO_SETOR(15, RepositorioHistogramaEsgotoEconomiaHelperEloPoloPorSetor.class), //
	LOCALIDADE(16, RepositorioHistogramaEsgotoEconomiaHelperUnidadeLocalidade.class), //
	LOCALIDADE_SETOR(17, RepositorioHistogramaEsgotoEconomiaHelperLocalidadePorSetor.class), //
	LOCALIDADE_QUADRA(18, RepositorioHistogramaEsgotoEconomiaHelperLocalidadePorQuadra.class), //
	SETOR(19, RepositorioHistogramaEsgotoEconomiaHelperSetor.class), //
	SETOR_LOCALIDADE_QUADRA(20, RepositorioHistogramaEsgotoEconomiaHelperSetorPorQuadra.class), //
	QUADRA(21, RepositorioHistogramaEsgotoEconomiaHelperQuadra.class) //
	;

	int opc;

	Class repositorioHelperImplClass;

	OpcaoTotalizacaoEnum(int opc, Class repositorioHelperImplClass) {

		this.opc = opc;
		this.repositorioHelperImplClass = repositorioHelperImplClass;
	}

	/**
	 * @return the opc
	 */
	public int getOpcao(){

		return opc;
	}

	/**
	 * @return the repositorioHelperImplClass
	 */
	public Class getRepositorioHelperImplClass(){

		return repositorioHelperImplClass;
	}

	static OpcaoTotalizacaoEnum get(int opc){

		for(OpcaoTotalizacaoEnum opTotal : OpcaoTotalizacaoEnum.values()){
			if(opTotal.getOpcao() == opc) return opTotal;
		}
		return null;
	}

}
