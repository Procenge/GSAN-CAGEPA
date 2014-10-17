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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mgrb
 *
 */
public class GrupoHelper {

	private int ordem;

	private String nome;

	private List<RelatorioResumoLigacoesEconomiaGCSBean> sumarioLigacoes;

	private List<SubGrupoHelper> subGrupos;

	private Map<Integer, Integer> apontador;

	public static final int GRUPO_LIGACOES = RelatorioResumoLigacoesEconomiaGCSBean.ID_GRUPO_LIGACOES;

	public static final int GRUPO_ECONOMIAS = RelatorioResumoLigacoesEconomiaGCSBean.ID_GRUPO_ECONOMIAS;

	private RelatorioResumoLigacoesEconomiaGCSBean linhaTotalFuncionandoMaisDesligadas;

	private RelatorioResumoLigacoesEconomiaGCSBean linhaPercentualDesligado;

	private RelatorioResumoLigacoesEconomiaGCSBean linhaPercentualMedido;

	private String subTitulo;

	/**
	 * RelatorioResumoLigacoesEconomia.Grupo
	 * <p>
	 * Esse método Constroi uma instância de RelatorioResumoLigacoesEconomia.Grupo.
	 * </p>
	 * 
	 * @author Marlos Ribeiro
	 * @param listaLigacaoAguaSituacao
	 * @since 22/11/2012
	 */
	public GrupoHelper(Integer totalizadorId, String totalizadorDesc, String subTitulo, Integer ordem, String nome,
						List<LigacaoAguaSituacao> listaLigacaoAguaSituacao) {

		this.subTitulo = subTitulo;
		this.ordem = ordem;
		this.nome = nome;
		this.sumarioLigacoes = new ArrayList<RelatorioResumoLigacoesEconomiaGCSBean>();
		apontador = new HashMap<Integer, Integer>();
		for(int i = 0; i < listaLigacaoAguaSituacao.size(); i++){
			LigacaoAguaSituacao situacao = listaLigacaoAguaSituacao.get(i);
			apontador.put(situacao.getId(), i);
			RelatorioResumoLigacoesEconomiaGCSBean sumario = new RelatorioResumoLigacoesEconomiaGCSBean(totalizadorId, totalizadorDesc,
							subTitulo,
							"\t\t\t\t" + situacao.getDescricao());
			sumario.resetTotais();
			this.sumarioLigacoes.add(sumario);
		}
	}

	/**
	 * Método setDetalhes
	 * <p>
	 * Esse método implementa o preenchimento dos detalhes do relatorio.
	 * </p>
	 * RASTREIO: [OC897714][UC0269]
	 * 
	 * @param totalizadorId
	 * @param totalizadorDesc
	 * @param detalhesLigacaoEconomia
	 * @author Marlos Ribeiro
	 * @since 23/11/2012
	 */
	public void setDetalhes(Integer totalizadorId, String totalizadorDesc, List<DetalheLigacaoEconomiaGCSHelper> detalhesLigacaoEconomia){

		criarSubGrupos(totalizadorId, totalizadorDesc, detalhesLigacaoEconomia);
		linhaTotalFuncionandoMaisDesligadas = new RelatorioResumoLigacoesEconomiaGCSBean(totalizadorId, totalizadorDesc, subTitulo,
						"\t\t FUNCIONANDO + DESLIGADAS");
		linhaTotalFuncionandoMaisDesligadas.resetAll();
		linhaTotalFuncionandoMaisDesligadas.totalizar(subGrupos.get(0).getTotal(), subGrupos.get(1).getTotal());

		linhaPercentualDesligado = new RelatorioResumoLigacoesEconomiaGCSBean(totalizadorId, totalizadorDesc, subTitulo,
						"\t\t PERCENTUAL DESLIGADO");
		linhaPercentualDesligado.calcularPercentualDesligados(subGrupos.get(1).getTotal(), linhaTotalFuncionandoMaisDesligadas);

		linhaPercentualMedido = new RelatorioResumoLigacoesEconomiaGCSBean(totalizadorId, totalizadorDesc, subTitulo,
						"\t\t PERCENTUAL MEDIDO");
		linhaPercentualMedido.calcularPercentualMedido(linhaTotalFuncionandoMaisDesligadas);

	}

	/**
	 * Método addSubGrupo
	 * <p>
	 * Esse método implementa criação dos sub grupos do relatorio. EM FUNCIONAMENTO, DESLIGADAS,
	 * FATURAMENTO.
	 * </p>
	 * RASTREIO: [OC897714][UC0269]
	 * 
	 * @param detalhesLigacaoEconomia
	 * @author Marlos Ribeiro
	 * @since 22/11/2012
	 */
	private void criarSubGrupos(Integer totalizadorId, String totalizadorDesc, List<DetalheLigacaoEconomiaGCSHelper> detalhesLigacaoEconomia){

		subGrupos = new ArrayList<SubGrupoHelper>();
		subGrupos.add(new SubGrupoHelper(totalizadorId, totalizadorDesc, subTitulo, SubGrupoHelper.TIPO_SUB_GRUPO_EM_FUNCIONAMENTO,
						"\t EM FUNCIONAMENTO", ordem, detalhesLigacaoEconomia));
		subGrupos.add(new SubGrupoHelper(totalizadorId, totalizadorDesc, subTitulo, SubGrupoHelper.TIPO_SUB_GRUPO_DESLIGADAS,
						"\t DESLIGADAS", ordem,
						detalhesLigacaoEconomia));
		// TODO [OC897714][UC0269]: AINDA NAO DEFINIDO
		// subGrupos.add(new SubGrupoHelper(totalizadorId, totalizadorDesc,
		// SubGrupoHelper.TIPO_SUB_GRUPO_FATURADAS, "\t FATURADAS", ordem,
		// detalhesLigacaoEconomia));

	}

	/**
	 * @return the nome
	 */
	public String getNome(){

		return nome;
	}

	/**
	 * @return the sumarioLigacoes
	 */
	public List<RelatorioResumoLigacoesEconomiaGCSBean> getSumarioLigacoes(){

		return sumarioLigacoes;
	}

	/**
	 * @return the ordem
	 */
	public int getOrdem(){

		return ordem;
	}

	/**
	 * @return the subGrupos
	 */
	public List<SubGrupoHelper> getSubGrupos(){

		return subGrupos;
	}

	/**
	 * Método add
	 * <p>
	 * Esse método implementa os sumarios das ligaçoes agua situacao.
	 * </p>
	 * RASTREIO: [OC897714][UC0269]
	 * 
	 * @param sumario
	 * @author Marlos Ribeiro
	 * @since 22/11/2012
	 */
	public void addSumario(SumarioLigacaoPorCategoriaGCSHelper sumario){

		if(sumario.getLigacaoAguaSituacaoId() != null){
			if(apontador.get(sumario.getLigacaoAguaSituacaoId()) != null){
				if(sumarioLigacoes.get(apontador.get(sumario.getLigacaoAguaSituacaoId())) != null){
					RelatorioResumoLigacoesEconomiaGCSBean linhaSumario = sumarioLigacoes.get(apontador.get(sumario
									.getLigacaoAguaSituacaoId()));
					linhaSumario.addSumario(ordem, sumario);
				}
			}

		}

	}

	/**
	 * @return the linhaTotalFuncionandoMaisDesligadas
	 */
	public RelatorioResumoLigacoesEconomiaGCSBean getLinhaTotalFuncionandoMaisDesligadas(){

		return linhaTotalFuncionandoMaisDesligadas;
	}

	/**
	 * @return the linhaPercentualDesligado
	 */
	public RelatorioResumoLigacoesEconomiaGCSBean getLinhaPercentualDesligado(){

		return linhaPercentualDesligado;
	}

	/**
	 * @return the linhaPercentualMedido
	 */
	public RelatorioResumoLigacoesEconomiaGCSBean getLinhaPercentualMedido(){

		return linhaPercentualMedido;
	}

}
