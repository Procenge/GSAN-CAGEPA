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
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Araújo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cláudio de Andrade Lira
 * Denys Guimarães Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fabíola Gomes de Araújo
 * Flávio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento Júnior
 * Homero Sampaio Cavalcanti
 * Ivan Sérgio da Silva Júnior
 * José Edmar de Siqueira
 * José Thiago Tenório Lopes
 * Kássia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * Márcio Roberto Batista da Silva
 * Maria de Fátima Sampaio Leite
 * Micaela Maria Coelho de Araújo
 * Nelson Mendonça de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corrêa Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Araújo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * Sávio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
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

package gcom.cadastro.imovel;

import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoSituacao;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.Util;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author isilva
 * @date 25/01/2011
 */
@ControleAlteracao()
public class ImovelEsgotoJudicial
				extends ObjetoTransacao {

	public static final int ATRIBUTOS_OPERACAO_EFETUAR_RELIGAR_ESGOTO_SUPRIMIDO_JUDICIALMENTE = 23255;

	public static final int ATRIBUTOS_OPERACAO_EFETUAR_SUPRIMIR_ESGOTO_JUDICIALMENTE = 22737;

	private static final long serialVersionUID = 1L;

	private Integer id;

	@ControleAlteracao(value = FiltroImovelEsgotoJudicial.IMOVEL, funcionalidade = {ATRIBUTOS_OPERACAO_EFETUAR_RELIGAR_ESGOTO_SUPRIMIDO_JUDICIALMENTE, ATRIBUTOS_OPERACAO_EFETUAR_SUPRIMIR_ESGOTO_JUDICIALMENTE})
	private Imovel imovel;

	@ControleAlteracao(value = FiltroImovelEsgotoJudicial.LIGACAOESGOTOSITUACAOINCLUSAO, funcionalidade = {ATRIBUTOS_OPERACAO_EFETUAR_RELIGAR_ESGOTO_SUPRIMIDO_JUDICIALMENTE, ATRIBUTOS_OPERACAO_EFETUAR_SUPRIMIR_ESGOTO_JUDICIALMENTE})
	private LigacaoEsgotoSituacao ligacaoEsgotoSituacaoInclusao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_OPERACAO_EFETUAR_RELIGAR_ESGOTO_SUPRIMIDO_JUDICIALMENTE, ATRIBUTOS_OPERACAO_EFETUAR_SUPRIMIR_ESGOTO_JUDICIALMENTE})
	private Integer anoMesProximoFaturamentoInclusao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_OPERACAO_EFETUAR_RELIGAR_ESGOTO_SUPRIMIDO_JUDICIALMENTE, ATRIBUTOS_OPERACAO_EFETUAR_SUPRIMIR_ESGOTO_JUDICIALMENTE})
	private Date dataInclusao;

	@ControleAlteracao(value = FiltroImovelEsgotoJudicial.LIGACAOESGOTOSITUACAOEXCLUSAO, funcionalidade = {ATRIBUTOS_OPERACAO_EFETUAR_RELIGAR_ESGOTO_SUPRIMIDO_JUDICIALMENTE, ATRIBUTOS_OPERACAO_EFETUAR_SUPRIMIR_ESGOTO_JUDICIALMENTE})
	private LigacaoEsgotoSituacao ligacaoEsgotoSituacaoExclusao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_OPERACAO_EFETUAR_RELIGAR_ESGOTO_SUPRIMIDO_JUDICIALMENTE, ATRIBUTOS_OPERACAO_EFETUAR_SUPRIMIR_ESGOTO_JUDICIALMENTE})
	private Integer anoMesProximoFaturamentoExclusao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_OPERACAO_EFETUAR_RELIGAR_ESGOTO_SUPRIMIDO_JUDICIALMENTE, ATRIBUTOS_OPERACAO_EFETUAR_SUPRIMIR_ESGOTO_JUDICIALMENTE})
	private Date dataExclusao;

	private Date ultimaAlteracao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_OPERACAO_EFETUAR_RELIGAR_ESGOTO_SUPRIMIDO_JUDICIALMENTE, ATRIBUTOS_OPERACAO_EFETUAR_SUPRIMIR_ESGOTO_JUDICIALMENTE})
	private Short indicadorUso;

	/**
	 * @return the id
	 */
	public Integer getId(){

		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id){

		this.id = id;
	}

	/**
	 * @return the imovel
	 */
	public Imovel getImovel(){

		return imovel;
	}

	/**
	 * @param imovel
	 *            the imovel to set
	 */
	public void setImovel(Imovel imovel){

		this.imovel = imovel;
	}

	/**
	 * @return the ligacaoEsgotoSituacaoInclusao
	 */
	public LigacaoEsgotoSituacao getLigacaoEsgotoSituacaoInclusao(){

		return ligacaoEsgotoSituacaoInclusao;
	}

	/**
	 * @param ligacaoEsgotoSituacaoInclusao
	 *            the ligacaoEsgotoSituacaoInclusao to set
	 */
	public void setLigacaoEsgotoSituacaoInclusao(LigacaoEsgotoSituacao ligacaoEsgotoSituacaoInclusao){

		this.ligacaoEsgotoSituacaoInclusao = ligacaoEsgotoSituacaoInclusao;
	}

	/**
	 * @return the anoMesProximoFaturamentoInclusao
	 */
	public Integer getAnoMesProximoFaturamentoInclusao(){

		return anoMesProximoFaturamentoInclusao;
	}

	/**
	 * @param anoMesProximoFaturamentoInclusao
	 *            the anoMesProximoFaturamentoInclusao to set
	 */
	public void setAnoMesProximoFaturamentoInclusao(Integer anoMesProximoFaturamentoInclusao){

		this.anoMesProximoFaturamentoInclusao = anoMesProximoFaturamentoInclusao;
	}

	/**
	 * @return the dataInclusao
	 */
	public Date getDataInclusao(){

		return dataInclusao;
	}

	/**
	 * @param dataInclusao
	 *            the dataInclusao to set
	 */
	public void setDataInclusao(Date dataInclusao){

		this.dataInclusao = dataInclusao;
	}

	/**
	 * @return the ligacaoEsgotoSituacaoExclusao
	 */
	public LigacaoEsgotoSituacao getLigacaoEsgotoSituacaoExclusao(){

		return ligacaoEsgotoSituacaoExclusao;
	}

	/**
	 * @param ligacaoEsgotoSituacaoExclusao
	 *            the ligacaoEsgotoSituacaoExclusao to set
	 */
	public void setLigacaoEsgotoSituacaoExclusao(LigacaoEsgotoSituacao ligacaoEsgotoSituacaoExclusao){

		this.ligacaoEsgotoSituacaoExclusao = ligacaoEsgotoSituacaoExclusao;
	}

	/**
	 * @return the anoMesProximoFaturamentoExclusao
	 */
	public Integer getAnoMesProximoFaturamentoExclusao(){

		return anoMesProximoFaturamentoExclusao;
	}

	/**
	 * @param anoMesProximoFaturamentoExclusao
	 *            the anoMesProximoFaturamentoExclusao to set
	 */
	public void setAnoMesProximoFaturamentoExclusao(Integer anoMesProximoFaturamentoExclusao){

		this.anoMesProximoFaturamentoExclusao = anoMesProximoFaturamentoExclusao;
	}

	/**
	 * @return the dataExclusao
	 */
	public Date getDataExclusao(){

		return dataExclusao;
	}

	/**
	 * @param dataExclusao
	 *            the dataExclusao to set
	 */
	public void setDataExclusao(Date dataExclusao){

		this.dataExclusao = dataExclusao;
	}

	/**
	 * @return the indicadorUso
	 */
	public Short getIndicadorUso(){

		return indicadorUso;
	}

	/**
	 * @param indicadorUso
	 *            the indicadorUso to set
	 */
	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	/**
	 * @return the ultimaAlteracao
	 */
	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	/**
	 * @param ultimaAlteracao
	 *            the ultimaAlteracao to set
	 */
	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public String getMesAnoProximoFaturamentoExclusaoComBarra(){

		return Util.formatarAnoMesSemBarraParaMesAnoComBarra(this.getAnoMesProximoFaturamentoExclusao());
	}

	public String getMesAnoProximoFaturamentoInclusaoComBarra(){

		return Util.formatarAnoMesSemBarraParaMesAnoComBarra(this.getAnoMesProximoFaturamentoInclusao());
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @param other
	 *            Descrição do parâmetro
	 * @return Descrição do retorno
	 */
	public boolean equals(Object other){

		if((this == other)){
			return true;
		}
		if(!(other instanceof ImovelEsgotoJudicial)){
			return false;
		}
		ImovelEsgotoJudicial castOther = (ImovelEsgotoJudicial) other;

		return (this.getId().equals(castOther.getId()));
	}

	public Filtro retornaFiltro(){

		FiltroImovelEsgotoJudicial filtroImovelEsgotoJudicial = new FiltroImovelEsgotoJudicial();
		filtroImovelEsgotoJudicial.adicionarParametro(new ParametroSimples(FiltroImovelEsgotoJudicial.ID, this.getId()));
		filtroImovelEsgotoJudicial.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelEsgotoJudicial.IMOVEL);
		filtroImovelEsgotoJudicial.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelEsgotoJudicial.LIGACAOESGOTOSITUACAOINCLUSAO);
		filtroImovelEsgotoJudicial.adicionarCaminhoParaCarregamentoEntidade(FiltroImovelEsgotoJudicial.LIGACAOESGOTOSITUACAOEXCLUSAO);

		return filtroImovelEsgotoJudicial;
	}

	/**
	 * < <Descrição do método>>
	 * 
	 * @return Descrição do retorno
	 */
	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"imovel.id", "dataInclusao", "dataExclusao"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Mat", "Data Incl", "Data Excl"};
		return labels;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"id"};
		return retorno;
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getId() + "";
	}
}