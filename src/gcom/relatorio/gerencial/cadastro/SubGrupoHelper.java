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

package gcom.relatorio.gerencial.cadastro;

import gcom.gerencial.cadastro.bean.DetalheLigacaoEconomiaGCSHelper;
import gcom.util.ResumoLigacoesEconomiaSqlBuilder;

import java.util.Arrays;
import java.util.List;

/**
 * @author mgrb
 */
public class SubGrupoHelper {

	public static final int TIPO_SUB_GRUPO_EM_FUNCIONAMENTO = 1;

	public static final int TIPO_SUB_GRUPO_DESLIGADAS = 2;

	public static final int TIPO_SUB_GRUPO_FATURADAS = 3;

	public static final List<Integer> ID_DETALHES_SUB_GRUPO_EM_FUNCIONAMENTO = Arrays.asList(1, 2, 3);

	public static final List<Integer> ID_DETALHES_SUB_GRUPO_DESLIGADAS = Arrays.asList(4, 5, 6);

	private int orden;

	private String nome;

	private RelatorioResumoLigacoesEconomiaGCSBean agua;

	private RelatorioResumoLigacoesEconomiaGCSBean esgoto;

	private RelatorioResumoLigacoesEconomiaGCSBean aguaEsgoto;

	private RelatorioResumoLigacoesEconomiaGCSBean total;

	/**
	 * SubGrupo
	 * <p>
	 * Esse método Constroi uma instância de SubGrupo.
	 * </p>
	 * 
	 * @param totalizadorId
	 * @param totalizadorDesc
	 * @param nome
	 * @author Marlos Ribeiro
	 * @param detalhesLigacaoEconomia
	 * @param tipoGrupo
	 * @since 22/11/2012
	 */
	public SubGrupoHelper(Integer totalizadorId, String totalizadorDesc, String subTitulo, int ordem, String nome, int tipoGrupo,
							List<DetalheLigacaoEconomiaGCSHelper> detalhesLigacaoEconomia) {

		this.nome = nome;
		this.orden = ordem;
		agua = new RelatorioResumoLigacoesEconomiaGCSBean(totalizadorId, totalizadorDesc, subTitulo, "\t\t\t\t AGUA");
		esgoto = new RelatorioResumoLigacoesEconomiaGCSBean(totalizadorId, totalizadorDesc, subTitulo, "\t\t\t\t ESGOTO");
		aguaEsgoto = new RelatorioResumoLigacoesEconomiaGCSBean(totalizadorId, totalizadorDesc, subTitulo, "\t\t\t\t AGUA-ESGOTO");
		total = new RelatorioResumoLigacoesEconomiaGCSBean(totalizadorId, totalizadorDesc, subTitulo, "\t\t\t\t TOTAL");

		agua.resetAll();
		esgoto.resetAll();
		aguaEsgoto.resetAll();
		total.resetAll();

		RelatorioResumoLigacoesEconomiaGCSBean detalheBean = null;
		for(DetalheLigacaoEconomiaGCSHelper detalhe : detalhesLigacaoEconomia){
			int detalheId = detalhe.getDetalheId().intValue();
			detalheBean = determinarDetalheBean(ordem, detalheId);
			if(detalheBean != null){
				detalheBean.addDetalhe(detalhe, tipoGrupo, detalhe.getCategoriaId());
			}
		}
		total.totalizar(agua, aguaEsgoto);
	}

	private RelatorioResumoLigacoesEconomiaGCSBean determinarDetalheBean(int subGrupoId, int detalheId){

		boolean isBeamValido;
		switch(subGrupoId){
			case SubGrupoHelper.TIPO_SUB_GRUPO_EM_FUNCIONAMENTO:
				isBeamValido = ID_DETALHES_SUB_GRUPO_EM_FUNCIONAMENTO.contains(detalheId);
				break;
			case SubGrupoHelper.TIPO_SUB_GRUPO_DESLIGADAS:
				isBeamValido = ID_DETALHES_SUB_GRUPO_DESLIGADAS.contains(detalheId);

				break;

			default:
				isBeamValido = false;
				break;
		}
		RelatorioResumoLigacoesEconomiaGCSBean detalheBean = null;
		if(isBeamValido){
			switch(detalheId){
				case ResumoLigacoesEconomiaSqlBuilder.GRUPO_DETALHE_FUNCIONANDO_AGUA:
					detalheBean = agua;
					break;
				case ResumoLigacoesEconomiaSqlBuilder.GRUPO_DETALHE_FUNCIONANDO_AGUA_ESGOTO:
					detalheBean = aguaEsgoto;
					break;
				case ResumoLigacoesEconomiaSqlBuilder.GRUPO_DETALHE_FUNCIONANDO_ESGOTO:
					detalheBean = esgoto;
					break;
				case ResumoLigacoesEconomiaSqlBuilder.GRUPO_DETALHE_DESLIGADA_AGUA:
					detalheBean = agua;
					break;
				case ResumoLigacoesEconomiaSqlBuilder.GRUPO_DETALHE_DESLIGADA_AGUA_ESGOTO:
					detalheBean = aguaEsgoto;
					break;
				case ResumoLigacoesEconomiaSqlBuilder.GRUPO_DETALHE_DESLIGADA_ESGOTO:
					detalheBean = esgoto;
					break;
			}
		}
		return detalheBean;
	}

	/**
	 * @return the nome
	 */
	public String getNome(){

		return nome;
	}

	/**
	 * @return the agua
	 */
	public RelatorioResumoLigacoesEconomiaGCSBean getAgua(){

		return agua;
	}

	/**
	 * @return the aguaEsgoto
	 */
	public RelatorioResumoLigacoesEconomiaGCSBean getAguaEsgoto(){

		return aguaEsgoto;
	}

	/**
	 * @return the esgoto
	 */
	public RelatorioResumoLigacoesEconomiaGCSBean getEsgoto(){

		return esgoto;
	}

	/**
	 * @return the total
	 */
	public RelatorioResumoLigacoesEconomiaGCSBean getTotal(){

		return total;
	}
}
