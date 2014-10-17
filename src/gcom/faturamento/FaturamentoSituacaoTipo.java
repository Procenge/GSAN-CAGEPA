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
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
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
 */

package gcom.faturamento;

import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.micromedicao.leitura.LeituraAnormalidadeConsumo;
import gcom.micromedicao.leitura.LeituraAnormalidadeLeitura;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
@ControleAlteracao()
public class FaturamentoSituacaoTipo
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	private String descricao;

	/** nullable persistent field */
	private Short indicadorParalisacaoFaturamento;

	/** nullable persistent field */
	private Short indicadorParalisacaoLeitura;

	/** nullable persistent field */
	private Short indicadorUso;

	/** nullable persistent field */
	private Short indicadorValidoAgua;

	/** nullable persistent field */
	private Short indicadorValidoEsgoto;

	/** nullable persistent field */
	private Short indicadorPercentualEsgoto;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/** nullable persistent field */
	private Short indicadorFaturamentoParalisacaoEsgoto;

	/** persistent field */
	private LeituraAnormalidadeLeitura leituraAnormalidadeLeituraComLeitura;

	/** persistent field */
	private LeituraAnormalidadeLeitura leituraAnormalidadeLeituraSemLeitura;

	/** persistent field */
	private LeituraAnormalidadeConsumo leituraAnormalidadeConsumoComLeitura;

	/** persistent field */
	private LeituraAnormalidadeConsumo leituraAnormalidadeConsumoSemLeitura;

	// Constantes
	public final static Integer PARALISAR_EMISSAO_CONTAS = Integer.valueOf(1);

	public final static Integer PARALISAR_LEITURA_FATURAR_TAXA_MINIMA = Integer.valueOf(3);

	public final static Integer FATURAR_NORMAL = Integer.valueOf(5);

	public final static Integer PERCENTUAL_DE_ESGOTO = Integer.valueOf(8);

	public final static Integer FATURAMENTO_ATE_LIMITE = Integer.valueOf(10);

	public final static Short INDICADOR_PARALIZACAO_LEITURA_NAO_REALIZADA = Short.valueOf((short) 1);

	/** full constructor */
	public FaturamentoSituacaoTipo(String descricao, Short indicadorParalisacaoFaturamento, Short indicadorParalisacaoLeitura,
									Short indicadorPercentualEsgoto, Short indicadorUso, Date ultimaAlteracao,
									Short indicadorFaturamentoParalisacaoEsgoto,
									LeituraAnormalidadeLeitura leituraAnormalidadeLeituraComLeitura,
									LeituraAnormalidadeLeitura leituraAnormalidadeLeituraSemLeitura,
									LeituraAnormalidadeConsumo leituraAnormalidadeConsumoComLeitura,
									LeituraAnormalidadeConsumo leituraAnormalidadeConsumoSemLeitura) {

		this.descricao = descricao;
		this.indicadorParalisacaoFaturamento = indicadorParalisacaoFaturamento;
		this.indicadorParalisacaoLeitura = indicadorParalisacaoLeitura;
		this.indicadorPercentualEsgoto = indicadorPercentualEsgoto;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		// this.indicadorFaturamentoParalisacaoEsgoto = indicadorFaturamentoParalisacaoEsgoto;
		this.leituraAnormalidadeLeituraComLeitura = leituraAnormalidadeLeituraComLeitura;
		this.leituraAnormalidadeLeituraSemLeitura = leituraAnormalidadeLeituraSemLeitura;
		this.leituraAnormalidadeConsumoComLeitura = leituraAnormalidadeConsumoComLeitura;
		this.leituraAnormalidadeConsumoSemLeitura = leituraAnormalidadeConsumoSemLeitura;
	}

	/** default constructor */
	public FaturamentoSituacaoTipo() {

	}

	/** minimal constructor */
	public FaturamentoSituacaoTipo(LeituraAnormalidadeLeitura leituraAnormalidadeLeituraComLeitura,
									LeituraAnormalidadeLeitura leituraAnormalidadeLeituraSemLeitura,
									LeituraAnormalidadeConsumo leituraAnormalidadeConsumoComLeitura,
									LeituraAnormalidadeConsumo leituraAnormalidadeConsumoSemLeitura) {

		this.leituraAnormalidadeLeituraComLeitura = leituraAnormalidadeLeituraComLeitura;
		this.leituraAnormalidadeLeituraSemLeitura = leituraAnormalidadeLeituraSemLeitura;
		this.leituraAnormalidadeConsumoComLeitura = leituraAnormalidadeConsumoComLeitura;
		this.leituraAnormalidadeConsumoSemLeitura = leituraAnormalidadeConsumoSemLeitura;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getDescricao(){

		return this.descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	public Short getIndicadorParalisacaoFaturamento(){

		return this.indicadorParalisacaoFaturamento;
	}

	public void setIndicadorParalisacaoFaturamento(Short indicadorParalisacaoFaturamento){

		this.indicadorParalisacaoFaturamento = indicadorParalisacaoFaturamento;
	}

	public Short getIndicadorParalisacaoLeitura(){

		return this.indicadorParalisacaoLeitura;
	}

	public void setIndicadorParalisacaoLeitura(Short indicadorParalisacaoLeitura){

		this.indicadorParalisacaoLeitura = indicadorParalisacaoLeitura;
	}

	public Short getIndicadorUso(){

		return this.indicadorUso;
	}

	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public LeituraAnormalidadeLeitura getLeituraAnormalidadeLeituraComLeitura(){

		return this.leituraAnormalidadeLeituraComLeitura;
	}

	public void setLeituraAnormalidadeLeituraComLeitura(LeituraAnormalidadeLeitura leituraAnormalidadeLeituraComLeitura){

		this.leituraAnormalidadeLeituraComLeitura = leituraAnormalidadeLeituraComLeitura;
	}

	public LeituraAnormalidadeLeitura getLeituraAnormalidadeLeituraSemLeitura(){

		return this.leituraAnormalidadeLeituraSemLeitura;
	}

	public void setLeituraAnormalidadeLeituraSemLeitura(LeituraAnormalidadeLeitura leituraAnormalidadeLeituraSemLeitura){

		this.leituraAnormalidadeLeituraSemLeitura = leituraAnormalidadeLeituraSemLeitura;
	}

	public LeituraAnormalidadeConsumo getLeituraAnormalidadeConsumoComLeitura(){

		return this.leituraAnormalidadeConsumoComLeitura;
	}

	public void setLeituraAnormalidadeConsumoComLeitura(LeituraAnormalidadeConsumo leituraAnormalidadeConsumoComLeitura){

		this.leituraAnormalidadeConsumoComLeitura = leituraAnormalidadeConsumoComLeitura;
	}

	public LeituraAnormalidadeConsumo getLeituraAnormalidadeConsumoSemLeitura(){

		return this.leituraAnormalidadeConsumoSemLeitura;
	}

	public void setLeituraAnormalidadeConsumoSemLeitura(LeituraAnormalidadeConsumo leituraAnormalidadeConsumoSemLeitura){

		this.leituraAnormalidadeConsumoSemLeitura = leituraAnormalidadeConsumoSemLeitura;
	}

	public Short getIndicadorValidoAgua(){

		return indicadorValidoAgua;
	}

	public void setIndicadorValidoAgua(Short indicadorValidoAgua){

		this.indicadorValidoAgua = indicadorValidoAgua;
	}

	public Short getIndicadorValidoEsgoto(){

		return indicadorValidoEsgoto;
	}

	public void setIndicadorValidoEsgoto(Short indicadorValidoEsgoto){

		this.indicadorValidoEsgoto = indicadorValidoEsgoto;
	}

	public Short getIndicadorPercentualEsgoto(){

		return indicadorPercentualEsgoto;
	}

	public void setIndicadorPercentualEsgoto(Short indicadorPercentualEsgoto){

		this.indicadorPercentualEsgoto = indicadorPercentualEsgoto;
	}

	/**
	 * @return the indicadorFaturamentoParalisacaoEsgoto
	 */
	public Short getIndicadorFaturamentoParalisacaoEsgoto(){

		return indicadorFaturamentoParalisacaoEsgoto;
	}

	/**
	 * @param indicadorFaturamentoParalisacaoEsgoto
	 *            the indicadorFaturamentoParalisacaoEsgoto to set
	 */
	public void setIndicadorFaturamentoParalisacaoEsgoto(Short indicadorFaturamentoParalisacaoEsgoto){

		this.indicadorFaturamentoParalisacaoEsgoto = indicadorFaturamentoParalisacaoEsgoto;
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"descricao"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Descricao"};
		return labels;
	}

	public Filtro retornaFiltro(){

		FiltroFaturamentoSituacaoTipo filtroFaturamentoSituacaoTipo = new FiltroFaturamentoSituacaoTipo();
		filtroFaturamentoSituacaoTipo.adicionarParametro(new ParametroSimples(FiltroFaturamentoSituacaoTipo.ID, this.getId()));
		filtroFaturamentoSituacaoTipo
						.adicionarCaminhoParaCarregamentoEntidade(FiltroFaturamentoSituacaoTipo.LEITURA_ANORMALIDADE_LEITURACOM_LEITURA);
		filtroFaturamentoSituacaoTipo
						.adicionarCaminhoParaCarregamentoEntidade(FiltroFaturamentoSituacaoTipo.LEITURA_ANORMALIDADE_LEITURASEM_LEITURA);
		filtroFaturamentoSituacaoTipo
						.adicionarCaminhoParaCarregamentoEntidade(FiltroFaturamentoSituacaoTipo.LEITURA_ANORMALIDADE_CONSUMOCOM_LEITURA);
		filtroFaturamentoSituacaoTipo
						.adicionarCaminhoParaCarregamentoEntidade(FiltroFaturamentoSituacaoTipo.LEITURA_ANORMALIDADE_CONSUMOSEM_LEITURA);
		return filtroFaturamentoSituacaoTipo;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getId() + "";
	}
}
