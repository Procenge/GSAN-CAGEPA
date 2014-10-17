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

package gcom.atendimentopublico.ligacaoagua;

import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.ErroRepositorioException;
import gcom.util.RepositorioUtilHBM;
import gcom.util.SistemaException;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Hibernate CodeGenerator
 * @author eduardo henrique
 * @date 16/04/2009
 *       Inclus�o do Novo Tipo de Situa��o "CORTADO_PEDIDO", inclu�do para atender ADA.
 *       Inclus�o de Novo Tipo de Situa��o "VIRTUAL", inclu�do para atender ADA.
 */
@ControleAlteracao()
public class LigacaoAguaSituacao
				extends ObjetoTransacao {

	public static final int ATRIBUTOS_SITUACAO_LIGACAO_ALTERAR = 857; // Operacao.OPERACAO_SITUACAO_LIGACAO_ALTERAR

	private static final long serialVersionUID = 1L;

	/**
	 * identifier field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_SITUACAO_LIGACAO_ALTERAR})
	private Integer id;

	/** nullable persistent field */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_SITUACAO_LIGACAO_ALTERAR})
	private String descricaoAbreviado;

	/**
	 * nullable persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_SITUACAO_LIGACAO_ALTERAR})
	private String descricao;

	/**
	 * nullable persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_SITUACAO_LIGACAO_ALTERAR})
	private Short indicadorUso;

	/**
	 * nullable persistent field
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_SITUACAO_LIGACAO_ALTERAR})
	private Date ultimaAlteracao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_SITUACAO_LIGACAO_ALTERAR})
	private Short indicadorFaturamentoSituacao;

	@ControleAlteracao(funcionalidade = {ATRIBUTOS_SITUACAO_LIGACAO_ALTERAR})
	private Integer consumoMinimoFaturamento;

	private Short indicadorAjusteConsumo;

	/**
	 * @since 19/09/2007
	 */
	@ControleAlteracao(funcionalidade = {ATRIBUTOS_SITUACAO_LIGACAO_ALTERAR})
	private String descricaoComId;

	/**
	 * @since 09/04/2012
	 */
	private String codigoConstante;

	// --CONSTANTES

	public static Integer POTENCIAL;

	public static Integer FACTIVEL;

	public static Integer LIGADO;

	public static Integer EM_FISCALIZACAO;

	public static Integer CORTADO;

	public static Integer SUPRIMIDO;

	public static Integer SUPR_PARC;

	public static Integer SUPR_PARC_PEDIDO;

	public static Integer EM_CANCELAMENTO;

	public static Integer CORTADO_PEDIDO;

	public static Integer SUPRIMIDO_DEFINITIVO;

	public static Integer VIRTUAL;

	public static Integer CORT_MED_INDIVIDUAL;

	public static Integer CORTADO_A_PEDIDO;


	// Indicadores de Situa��o de Faturamento --
	public final static Short FATURAMENTO_ATIVO = Short.valueOf("1");

	public final static Short NAO_FATURAVEL = Short.valueOf("2");

	public final static Integer LIGADO_A_REVELIA = Integer.valueOf("4");

	public final static Integer LIGADO_EM_ANALISE = Integer.valueOf("4");

	/**
	 * full constructor
	 * 
	 * @param descricao
	 *            Descri��o do par�metro
	 * @param indicadorUso
	 *            Descri��o do par�metro
	 * @param ultimaAlteracao
	 *            Descri��o do par�metro
	 */
	public LigacaoAguaSituacao(String descricao, Short indicadorUso, Date ultimaAlteracao, Short indicadorFaturamentoSituacao,
								Integer consumoMinimoFaturamento) {

		this.descricao = descricao;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.indicadorFaturamentoSituacao = indicadorFaturamentoSituacao;
		this.consumoMinimoFaturamento = consumoMinimoFaturamento;
	}

	/**
	 * full constructor
	 * 
	 * @param descricao
	 *            Descri��o do par�metro
	 * @param indicadorUso
	 *            Descri��o do par�metro
	 * @param ultimaAlteracao
	 *            Descri��o do par�metro
	 */
	public LigacaoAguaSituacao(String descricao, String descricaoAbreviado, Short indicadorUso, Date ultimaAlteracao,
								Short indicadorFaturamentoSituacao, Integer consumoMinimoFaturamento) {

		this.descricao = descricao;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.descricaoAbreviado = descricaoAbreviado;
		this.indicadorFaturamentoSituacao = indicadorFaturamentoSituacao;
		this.consumoMinimoFaturamento = consumoMinimoFaturamento;
	}

	/**
	 * default constructor
	 */
	public LigacaoAguaSituacao() {

	}

	/**
	 * @return Retorna o campo descricaoAbreviado.
	 */
	public String getDescricaoAbreviado(){

		return descricaoAbreviado;
	}

	public Integer getConsumoMinimoFaturamento(){

		return consumoMinimoFaturamento;
	}

	public void setConsumoMinimoFaturamento(Integer consumoMinimoFaturamento){

		this.consumoMinimoFaturamento = consumoMinimoFaturamento;
	}

	public Short getIndicadorFaturamentoSituacao(){

		return indicadorFaturamentoSituacao;
	}

	public void setIndicadorFaturamentoSituacao(Short indicadorFaturamentoSituacao){

		this.indicadorFaturamentoSituacao = indicadorFaturamentoSituacao;
	}

	/**
	 * @param descricaoAbreviado
	 *            O descricaoAbreviado a ser setado.
	 */
	public void setDescricaoAbreviado(String descricaoAbreviado){

		this.descricaoAbreviado = descricaoAbreviado;
	}

	/**
	 * Retorna o valor de id
	 * 
	 * @return O valor de id
	 */
	public Integer getId(){

		return this.id;
	}

	/**
	 * Seta o valor de id
	 * 
	 * @param id
	 *            O novo valor de id
	 */
	public void setId(Integer id){

		this.id = id;
	}

	/**
	 * Retorna o valor de descricao
	 * 
	 * @return O valor de descricao
	 */
	public String getDescricao(){

		return this.descricao;
	}

	/**
	 * Seta o valor de descricao
	 * 
	 * @param descricao
	 *            O novo valor de descricao
	 */
	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	/**
	 * Retorna o valor de indicadorUso
	 * 
	 * @return O valor de indicadorUso
	 */
	public Short getIndicadorUso(){

		return this.indicadorUso;
	}

	/**
	 * Seta o valor de indicadorUso
	 * 
	 * @param indicadorUso
	 *            O novo valor de indicadorUso
	 */
	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	/**
	 * Retorna o valor de ultimaAlteracao
	 * 
	 * @return O valor de ultimaAlteracao
	 */
	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	/**
	 * Seta o valor de ultimaAlteracao
	 * 
	 * @param ultimaAlteracao
	 *            O novo valor de ultimaAlteracao
	 */
	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	/**
	 * < <Descri��o do m�todo>>
	 * 
	 * @return Descri��o do retorno
	 */
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

		FiltroLigacaoAguaSituacao filtro = new FiltroLigacaoAguaSituacao();

		filtro.adicionarParametro(new ParametroSimples(FiltroLigacaoAguaSituacao.ID, this.getId()));
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

	/**
	 * @author Carlos Chrystian Ramos
	 * @date 09/04/2012
	 * @return
	 */
	public String getCodigoConstante(){

		return codigoConstante;
	}

	/**
	 * @author Carlos Chrystian Ramos
	 * @date 09/04/2012
	 * @return
	 */
	public void setCodigoConstante(String codigoConstante){

		this.codigoConstante = codigoConstante;
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

		String[] atributos = {"descricao"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Descricao"};
		return labels;
	}
	
	public Short getIndicadorAjusteConsumo(){

		return indicadorAjusteConsumo;
	}

	public void setIndicadorAjusteConsumo(Short indicadorAjusteConsumo){

		this.indicadorAjusteConsumo = indicadorAjusteConsumo;
	}


	/**
	 * Este enum foi criado para dar suporte ao carregamento de constantes da classe em tempo de
	 * execu��o. As constantes foram criadas aqui como atributos do enum, o que resolveu o problema
	 * das constantes com descri��es diferentes mas que utilizavam o mesmo
	 * valor. Caso a constante n�o seja utilizada por um determinado cliente ser� atribu�do -1 ao
	 * seu valor.
	 * 
	 * @author Carlos Chrystian Ramos
	 * @date 09/04/2012
	 */
	public static enum LigacaoAguaSituacaoEnum {

		POTENCIAL, FACTIVEL, LIGADO, EM_FISCALIZACAO, CORTADO, SUPRIMIDO, SUPR_PARC, SUPR_PARC_PEDIDO, EM_CANCELAMENTO, CORTADO_A_PEDIDO,
		SUPRIMIDO_DEFINITIVO, VIRTUAL, CORT_MED_INDIVIDUAL;

		private Integer id = -1;

		private LigacaoAguaSituacaoEnum() {

			try{
				LigacaoAguaSituacao ligacaoAguaSituacao = RepositorioUtilHBM.getInstancia().pesquisarPorCodigo(name(),
								LigacaoAguaSituacao.class);

				if(ligacaoAguaSituacao != null){

					id = ligacaoAguaSituacao.getId();
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

	/**
	 * @author Carlos Chrystian Ramos
	 * @date 09/04/2012
	 */
	public static void inicializarConstantes(){

		POTENCIAL = LigacaoAguaSituacaoEnum.POTENCIAL.getId();
		FACTIVEL = LigacaoAguaSituacaoEnum.FACTIVEL.getId();
		LIGADO = LigacaoAguaSituacaoEnum.LIGADO.getId();
		EM_FISCALIZACAO = LigacaoAguaSituacaoEnum.EM_FISCALIZACAO.getId();
		CORTADO = LigacaoAguaSituacaoEnum.CORTADO.getId();
		SUPRIMIDO = LigacaoAguaSituacaoEnum.SUPRIMIDO.getId();
		SUPR_PARC = LigacaoAguaSituacaoEnum.SUPR_PARC.getId();
		SUPR_PARC_PEDIDO = LigacaoAguaSituacaoEnum.SUPR_PARC_PEDIDO.getId();
		EM_CANCELAMENTO = LigacaoAguaSituacaoEnum.EM_CANCELAMENTO.getId();
		CORTADO_PEDIDO = LigacaoAguaSituacaoEnum.CORTADO_A_PEDIDO.getId();
		CORTADO_A_PEDIDO = LigacaoAguaSituacaoEnum.CORTADO_A_PEDIDO.getId();
		SUPRIMIDO_DEFINITIVO = LigacaoAguaSituacaoEnum.SUPRIMIDO_DEFINITIVO.getId();
		VIRTUAL = LigacaoAguaSituacaoEnum.VIRTUAL.getId();
		CORT_MED_INDIVIDUAL = LigacaoAguaSituacaoEnum.CORT_MED_INDIVIDUAL.getId();
	}

	@Override
	public boolean equals(Object other){

		if((this == other)){
			return true;
		}
		if(!(other instanceof LigacaoAguaSituacao)){
			return false;
		}
		LigacaoAguaSituacao castOther = (LigacaoAguaSituacao) other;

		return (this.getId().equals(castOther.getId()));
	}

}
