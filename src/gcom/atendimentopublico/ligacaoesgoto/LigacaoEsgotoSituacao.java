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

package gcom.atendimentopublico.ligacaoesgoto;

import gcom.interceptor.ObjetoTransacao;
import gcom.util.ErroRepositorioException;
import gcom.util.RepositorioUtilHBM;
import gcom.util.SistemaException;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class LigacaoEsgotoSituacao
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	private String descricao;

	/** nullable persistent field */
	private String descricaoAbreviado;

	/** nullable persistent field */
	private Short indicadorUso;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/** nullable persistent field */
	private Short indicadorFaturamentoSituacao;

	/** nullable persistent field */
	private Integer volumeMinimoFaturamento;

	private String codigoConstante;

	private Short indicadorAjusteConsumo;

	/**
	 * @since 19/09/2007
	 */
	private String descricaoComId;

	// Constantes
	public static Integer POTENCIAL;

	public static Integer FACTIVEL;

	public static Integer LIGADO;

	public static Integer EM_FISCALIZACAO;

	public static Integer LIG_FORA_DE_USO;

	public static Integer TAMPONADO;

	public static Integer VIRTUAL;

	public static Integer SEM_VIABILIDADE_TECN;

	public static Integer FACTIVEL_FATURADA;

	/**
	 * Este m�todo foi criado para inicializar as constantes. A sua implementa��o visa utilizar a
	 * solu��o dada para casos em que haja constantes com descri��es diferentes mas que utilizavam o
	 * mesmo valor em clientes distintos.
	 * 
	 * @author Anderson Italo
	 * @date 10/01/2012
	 */
	public static void inicializarConstantes(){

		POTENCIAL = LigacaoEsgotoSituacaoEnum.POTENCIAL.getId();

		FACTIVEL = LigacaoEsgotoSituacaoEnum.FACTIVEL.getId();

		LIGADO = LigacaoEsgotoSituacaoEnum.LIGADO.getId();

		EM_FISCALIZACAO = LigacaoEsgotoSituacaoEnum.EM_FISCALIZACAO.getId();

		LIG_FORA_DE_USO = LigacaoEsgotoSituacaoEnum.LIG_FORA_DE_USO.getId();

		TAMPONADO = LigacaoEsgotoSituacaoEnum.TAMPONADO.getId();

		VIRTUAL = LigacaoEsgotoSituacaoEnum.VIRTUAL.getId();

		SEM_VIABILIDADE_TECN = LigacaoEsgotoSituacaoEnum.SEM_VIABILIDADE_TECN.getId();

		FACTIVEL_FATURADA = LigacaoEsgotoSituacaoEnum.FACTIVEL_FATURADA.getId();
	}

	/**
	 * Este enum foi criado para dar suporte ao carregamento de constantes da classe em tempo de
	 * execu��o. As constantes foram criadas aqui como atributos do enum, o que resolveu o problema
	 * das constantes com descri��es diferentes mas que utilizavam o mesmo
	 * valor. Caso a constante n�o seja utilizada por um determinado cliente ser� atribu�do -1 ao
	 * seu valor.
	 * 
	 * @author Anderson Italo
	 * @date 09/01/2012
	 */
	public static enum LigacaoEsgotoSituacaoEnum {

		POTENCIAL, FACTIVEL, LIGADO, EM_FISCALIZACAO, LIG_FORA_DE_USO, TAMPONADO, VIRTUAL, SEM_VIABILIDADE_TECN, FACTIVEL_FATURADA;

		private Integer id = -1;

		private LigacaoEsgotoSituacaoEnum() {

			try{
				LigacaoEsgotoSituacao ligacaoEsgotoSituacao = RepositorioUtilHBM.getInstancia().pesquisarPorCodigo(name(),
								LigacaoEsgotoSituacao.class);

				if(ligacaoEsgotoSituacao != null){

					id = ligacaoEsgotoSituacao.getId();
				}
			}catch(ErroRepositorioException e){

				e.printStackTrace();
				throw new SistemaException(e, e.getMessage());
			}
		}

		public Integer getId(){

			return id;
		}
	}

	// Descri��o Tipo de Servi�os
	public final static String SITUACAO_LIGADO_FORA_DE_USO = "LIGADO FORA DE USO";

	public final static String SITUACAO_LIGADO = "LIGADO";

	// Descri��o Tipo de Servi�os
	public final static Integer SITUACAO_TAMPONADO = Integer.valueOf(1);

	public final static Integer SITUACAO_DESATIVACAO = Integer.valueOf(2);

	public final static Integer SITUACAO_RESTABELECIMENTO = Integer.valueOf(3);

	public final static Integer SITUACAO_REATIVACAO = Integer.valueOf(4);

	// Indicadores de Situa��o de Faturamento --
	public final static Short FATURAMENTO_ATIVO = Short.valueOf("1");

	public final static Short NAO_FATURAVEL = Short.valueOf("2");

	/** full constructor */
	public LigacaoEsgotoSituacao(String descricao, Short indicadorUso, Date ultimaAlteracao, Short indicadorFaturamentoSituacao,
									Integer volumeMinimoFaturamento) {

		this.descricao = descricao;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.indicadorFaturamentoSituacao = indicadorFaturamentoSituacao;
		this.volumeMinimoFaturamento = volumeMinimoFaturamento;
	}

	/** full constructor */
	public LigacaoEsgotoSituacao(String descricao, String descricaoAbreviado, Short indicadorUso, Date ultimaAlteracao,
									Short indicadorFaturamentoSituacao, Integer volumeMinimoFaturamento, Short indicadorAjusteConsumo) {

		this.descricao = descricao;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.descricaoAbreviado = descricaoAbreviado;
		this.indicadorFaturamentoSituacao = indicadorFaturamentoSituacao;
		this.volumeMinimoFaturamento = volumeMinimoFaturamento;
		this.indicadorAjusteConsumo = indicadorAjusteConsumo;
	}

	/** default constructor */
	public LigacaoEsgotoSituacao() {

	}

	/**
	 * @return Retorna o campo descricaoAbreviado.
	 */
	public String getDescricaoAbreviado(){

		return descricaoAbreviado;
	}

	public Short getIndicadorFaturamentoSituacao(){

		return indicadorFaturamentoSituacao;
	}

	public void setIndicadorFaturamentoSituacao(Short indicadorFaturamentoSituacao){

		this.indicadorFaturamentoSituacao = indicadorFaturamentoSituacao;
	}

	public Integer getVolumeMinimoFaturamento(){

		return volumeMinimoFaturamento;
	}

	public void setVolumeMinimoFaturamento(Integer volumeMinimoFaturamento){

		this.volumeMinimoFaturamento = volumeMinimoFaturamento;
	}

	/**
	 * @param descricaoAbreviado
	 *            O descricaoAbreviado a ser setado.
	 */
	public void setDescricaoAbreviado(String descricaoAbreviado){

		this.descricaoAbreviado = descricaoAbreviado;
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

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	@Override
	public Filtro retornaFiltro(){

		FiltroLigacaoEsgotoSituacao filtro = new FiltroLigacaoEsgotoSituacao();

		filtro.adicionarParametro(new ParametroSimples(FiltroLigacaoEsgoto.ID, this.getId()));
		return filtro;
	}

	/**
	 * <Breve descri��o sobre o caso de uso>
	 * <Identificador e nome do caso de uso>
	 * 
	 * @author Pedro Alexandre
	 * @date 19/09/2007
	 * @return
	 */
	public String getDescricaoComId(){

		if(this.getId().compareTo(10) == -1){
			descricaoComId = "0" + getId() + " - " + getDescricao();
		}else{
			descricaoComId = getId() + " - " + getDescricao();
		}

		return descricaoComId;
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		String retorno = null;

		if(this.getId() != null && this.getDescricao() != null){
			retorno = this.getId() + " - " + this.getDescricao();
		}else{
			retorno = null;
		}

		return retorno;
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"volumeMinimoFaturamento"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Volume Minimo"};
		return labels;
	}

	public String getCodigoConstante(){

		return codigoConstante;
	}

	public void setCodigoConstante(String codigoConstante){

		this.codigoConstante = codigoConstante;
	}

	public Short getIndicadorAjusteConsumo(){

		return indicadorAjusteConsumo;
	}

	public void setIndicadorAjusteConsumo(Short indicadorAjusteConsumo){

		this.indicadorAjusteConsumo = indicadorAjusteConsumo;
	}

	@Override
	public boolean equals(Object other){

		if((this == other)){
			return true;
		}
		if(!(other instanceof LigacaoEsgotoSituacao)){
			return false;
		}
		LigacaoEsgotoSituacao castOther = (LigacaoEsgotoSituacao) other;

		return (this.getId().equals(castOther.getId()));
	}
}
