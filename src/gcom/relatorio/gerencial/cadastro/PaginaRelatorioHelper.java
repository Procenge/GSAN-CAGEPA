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

import gcom.atendimentopublico.ligacaoagua.LigacaoAguaSituacao;
import gcom.gerencial.cadastro.bean.DetalheLigacaoEconomiaGCSHelper;
import gcom.gerencial.cadastro.bean.SumarioLigacaoPorCategoriaGCSHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mgrb
 */
public class PaginaRelatorioHelper {

	private Integer totalizadorId;

	private String totalizadorDesc;

	private List<GrupoHelper> grupos;

	private RelatorioResumoLigacoesEconomiaGCSBean totalFuncionandoDesligados;

	private RelatorioResumoLigacoesEconomiaGCSBean percentualDesligados;

	private RelatorioResumoLigacoesEconomiaGCSBean percentualMedidos;

	private String subTitulo;

	/**
	 * PaginaRelatorioHelper
	 * <p>
	 * Esse método Constroi uma instância de PaginaRelatorioHelper.
	 * </p>
	 * 
	 * @param totalizadorId
	 * @param listaLigacaoAguaSituacao
	 * @author Marlos Ribeiro
	 * @since 22/11/2012
	 */
	public PaginaRelatorioHelper(Integer totalizadorId, String totalizadorDesc, String subTitulo,
									List<LigacaoAguaSituacao> listaLigacaoAguaSituacao) {

		this.subTitulo = subTitulo;
		this.totalizadorId = totalizadorId;
		this.totalizadorDesc = totalizadorDesc;
		grupos = new ArrayList<GrupoHelper>();
		GrupoHelper grupoLigacoes = new GrupoHelper(totalizadorId, totalizadorDesc, subTitulo,
						RelatorioResumoLigacoesEconomiaGCSBean.ID_GRUPO_LIGACOES, "L I G A Ç Õ E S", listaLigacaoAguaSituacao);
		GrupoHelper grupoEconomias = new GrupoHelper(totalizadorId, totalizadorDesc, subTitulo,
						RelatorioResumoLigacoesEconomiaGCSBean.ID_GRUPO_ECONOMIAS, "E C O N Ô M I A S", listaLigacaoAguaSituacao);

		grupos.add(grupoLigacoes);
		grupos.add(grupoEconomias);
	}

	/**
	 * Método setDetalhes
	 * <p>
	 * Esse método implementa o preenchimento dos detalhes.
	 * </p>
	 * RASTREIO: [OC897714][UC0269]
	 * 
	 * @param detalhesLigacaoEconomia
	 * @author Marlos Ribeiro
	 * @since 22/11/2012
	 */
	public void setDetalhes(List<DetalheLigacaoEconomiaGCSHelper> detalhesLigacaoEconomia){

		for(GrupoHelper grupo : grupos){
			grupo.setDetalhes(totalizadorId, totalizadorDesc, detalhesLigacaoEconomia);
		}

	}

	/**
	 * Método toRelatorioBean
	 * <p>
	 * Esse método implementa a formatacao para o bean do relatorio.
	 * </p>
	 * RASTREIO: [OC897714][UC0269]
	 * 
	 * @return
	 * @author Marlos Ribeiro
	 * @since 22/11/2012
	 */
	public List<RelatorioResumoLigacoesEconomiaGCSBean> toRelatorioBean(){

		List<RelatorioResumoLigacoesEconomiaGCSBean> resultado = new ArrayList<RelatorioResumoLigacoesEconomiaGCSBean>();
		// TRANSFORMANDO OS SUMARIOS
		for(GrupoHelper grupo : grupos){
			resultado.add(new RelatorioResumoLigacoesEconomiaGCSBean(totalizadorId, totalizadorDesc, subTitulo, grupo.getNome()));
			resultado.addAll(grupo.getSumarioLigacoes());
			for(SubGrupoHelper subGrupo : grupo.getSubGrupos()){
				resultado.add(new RelatorioResumoLigacoesEconomiaGCSBean(totalizadorId, totalizadorDesc, subTitulo, subGrupo.getNome()));
				resultado.add(subGrupo.getAgua());
				resultado.add(subGrupo.getAguaEsgoto());
				resultado.add(subGrupo.getTotal());
				resultado.add(subGrupo.getEsgoto());
			}
			resultado.add(grupo.getLinhaTotalFuncionandoMaisDesligadas());
			resultado.add(grupo.getLinhaPercentualDesligado());
			resultado.add(grupo.getLinhaPercentualMedido());
		}
		return resultado;
	}

	/**
	 * Método addSumario
	 * <p>
	 * Esse método implementa a sumarização dos totais das {@link LigacaoAguaSituacao}.
	 * </p>
	 * RASTREIO: [OC897714][UC0269]
	 * 
	 * @param sumario
	 * @author Marlos Ribeiro
	 * @since 22/11/2012
	 */
	public void addSumario(SumarioLigacaoPorCategoriaGCSHelper sumario){

		for(GrupoHelper grupo : grupos){
			grupo.addSumario(sumario);
		}
	}

	/**
	 * @return the totalizadorId
	 */
	public Integer getTotalizadorId(){

		return totalizadorId;
	}
}
