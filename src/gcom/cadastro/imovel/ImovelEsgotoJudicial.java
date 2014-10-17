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
	 * < <Descri��o do m�todo>>
	 * 
	 * @param other
	 *            Descri��o do par�metro
	 * @return Descri��o do retorno
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
	 * < <Descri��o do m�todo>>
	 * 
	 * @return Descri��o do retorno
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