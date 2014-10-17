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
	 * Esse m�todo Constroi uma inst�ncia de PaginaRelatorioHelper.
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
						RelatorioResumoLigacoesEconomiaGCSBean.ID_GRUPO_LIGACOES, "L I G A � � E S", listaLigacaoAguaSituacao);
		GrupoHelper grupoEconomias = new GrupoHelper(totalizadorId, totalizadorDesc, subTitulo,
						RelatorioResumoLigacoesEconomiaGCSBean.ID_GRUPO_ECONOMIAS, "E C O N � M I A S", listaLigacaoAguaSituacao);

		grupos.add(grupoLigacoes);
		grupos.add(grupoEconomias);
	}

	/**
	 * M�todo setDetalhes
	 * <p>
	 * Esse m�todo implementa o preenchimento dos detalhes.
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
	 * M�todo toRelatorioBean
	 * <p>
	 * Esse m�todo implementa a formatacao para o bean do relatorio.
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
	 * M�todo addSumario
	 * <p>
	 * Esse m�todo implementa a sumariza��o dos totais das {@link LigacaoAguaSituacao}.
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
