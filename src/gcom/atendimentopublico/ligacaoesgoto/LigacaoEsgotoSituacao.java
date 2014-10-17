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
	 * Este método foi criado para inicializar as constantes. A sua implementação visa utilizar a
	 * solução dada para casos em que haja constantes com descrições diferentes mas que utilizavam o
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
	 * execução. As constantes foram criadas aqui como atributos do enum, o que resolveu o problema
	 * das constantes com descrições diferentes mas que utilizavam o mesmo
	 * valor. Caso a constante não seja utilizada por um determinado cliente será atribuído -1 ao
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

	// Descrição Tipo de Serviços
	public final static String SITUACAO_LIGADO_FORA_DE_USO = "LIGADO FORA DE USO";

	public final static String SITUACAO_LIGADO = "LIGADO";

	// Descrição Tipo de Serviços
	public final static Integer SITUACAO_TAMPONADO = Integer.valueOf(1);

	public final static Integer SITUACAO_DESATIVACAO = Integer.valueOf(2);

	public final static Integer SITUACAO_RESTABELECIMENTO = Integer.valueOf(3);

	public final static Integer SITUACAO_REATIVACAO = Integer.valueOf(4);

	// Indicadores de Situação de Faturamento --
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
	 * <Breve descrição sobre o caso de uso>
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
