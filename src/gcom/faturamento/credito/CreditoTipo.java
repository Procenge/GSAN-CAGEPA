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

package gcom.faturamento.credito;

import gcom.financeiro.lancamento.LancamentoItemContabil;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.ErroRepositorioException;
import gcom.util.RepositorioUtilHBM;
import gcom.util.SistemaException;
import gcom.util.Util;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class CreditoTipo
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	public static Integer DESCONTO_ACRESCIMOS_IMPONTUALIDADE;

	public static Integer DESCONTO_ANTIGUIDADE_DEBITO;

	public static Integer DESCONTO_INATIVIDADE_LIGACAO_AGUA;

	public static Integer CREDITOS_ANTERIORES;

	public static Integer DEVOLUCAO_PAGAMENTOS_DUPLICIDADE;

	public static Integer DEVOLUCAO_OUTROS_VALORES;

	public static Integer DESCONTOS_CONCEDIDOS;

	public static Integer DEVOLUCAO_ACRESCIMOS_IMPONTUALIDADE;

	public static Integer DESCONTO_SANCOES;

	public static Integer DESCONTO_TARIFA_SOCIAL;

	/**
	 * Este método foi criado para inicializar as constantes. A sua implementação visa utilizar a
	 * solução dada para casos em que haja constantes com descrições diferentes mas que utilizavam o
	 * mesmo valor em clientes distintos.
	 * 
	 * @author Anderson Italo
	 * @date 10/05/2013
	 */
	public static void inicializarConstantes(){

		DESCONTO_ACRESCIMOS_IMPONTUALIDADE = CreditoTipoEnum.DESCONTO_ACRESCIMOS_IMPONTUALIDADE.getId();

		DESCONTO_ANTIGUIDADE_DEBITO = CreditoTipoEnum.DESCONTO_ANTIGUIDADE_DEBITO.getId();

		DESCONTO_INATIVIDADE_LIGACAO_AGUA = CreditoTipoEnum.DESCONTO_INATIVIDADE_LIGACAO_AGUA.getId();

		CREDITOS_ANTERIORES = CreditoTipoEnum.CREDITOS_ANTERIORES.getId();

		DEVOLUCAO_PAGAMENTOS_DUPLICIDADE = CreditoTipoEnum.DEVOLUCAO_PAGAMENTOS_DUPLICIDADE.getId();

		DEVOLUCAO_OUTROS_VALORES = CreditoTipoEnum.DEVOLUCAO_OUTROS_VALORES.getId();

		DESCONTOS_CONCEDIDOS = CreditoTipoEnum.DESCONTOS_CONCEDIDOS.getId();

		DEVOLUCAO_ACRESCIMOS_IMPONTUALIDADE = CreditoTipoEnum.DEVOLUCAO_ACRESCIMOS_IMPONTUALIDADE.getId();

		DESCONTO_SANCOES = CreditoTipoEnum.DESCONTO_SANCOES.getId();

		DESCONTO_TARIFA_SOCIAL = CreditoTipoEnum.DESCONTO_TARIFA_SOCIAL.getId();
	}

	/**
	 * Este enum foi criado para dar suporte ao carregamento de constantes da classe em tempo de
	 * execução. As constantes foram criadas aqui como atributos do enum, o que resolveu o problema
	 * das constantes com descrições diferentes mas que utilizavam o mesmo
	 * valor. Caso a constante não seja utilizada por um determinado cliente será atribuído -1 ao
	 * seu valor.
	 * 
	 * @author Anderson Italo
	 * @date 10/05/2013
	 */
	public static enum CreditoTipoEnum {

		DESCONTO_ACRESCIMOS_IMPONTUALIDADE, DESCONTO_ANTIGUIDADE_DEBITO, DESCONTO_INATIVIDADE_LIGACAO_AGUA, CREDITOS_ANTERIORES,
		DEVOLUCAO_PAGAMENTOS_DUPLICIDADE, DEVOLUCAO_OUTROS_VALORES, DESCONTOS_CONCEDIDOS, DEVOLUCAO_ACRESCIMOS_IMPONTUALIDADE,
		DESCONTO_SANCOES, DESCONTO_TARIFA_SOCIAL;

		private Integer id = -1;

		private CreditoTipoEnum() {

			try{
				CreditoTipo creditoTipo = RepositorioUtilHBM.getInstancia().pesquisarPorCodigo(name(), CreditoTipo.class);

				if(creditoTipo != null){

					id = creditoTipo.getId();
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

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	private String descricao;

	/** nullable persistent field */
	private String descricaoAbreviada;

	/** nullable persistent field */
	private Integer indicadorUso;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/** nullable persistent field */
	private BigDecimal valorLimite;

	/** nullable persistent field */
	private Short indicadorGeracaoAutomatica;

	/** nullable persistent field */
	private LancamentoItemContabil lancamentoItemContabil;

	private String codigoConstante;

	/** full constructor */
	public CreditoTipo(String descricao, String descricaoAbreviada, Integer indicadorUso, Date ultimaAlteracao, BigDecimal valorLimite,
						Short indicadorGeracaoAutomatica, LancamentoItemContabil lancamentoItemContabil) {

		this.descricao = descricao;
		this.descricaoAbreviada = descricaoAbreviada;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.valorLimite = valorLimite;
		this.indicadorGeracaoAutomatica = indicadorGeracaoAutomatica;
		this.lancamentoItemContabil = lancamentoItemContabil;
	}

	/** default constructor */
	public CreditoTipo() {

	}

	// Construido por Sávio Luiz para setar o id no objeto
	public CreditoTipo(Integer id) {

		this.id = id;
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

	public String getDescricaoAbreviada(){

		return this.descricaoAbreviada;
	}

	public void setDescricaoAbreviada(String descricaoAbreviada){

		this.descricaoAbreviada = descricaoAbreviada;
	}

	public Integer getIndicadorUso(){

		return this.indicadorUso;
	}

	public void setIndicadorUso(Integer indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public Date getUltimaAlteracao(){

		return this.ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public BigDecimal getValorLimite(){

		return this.valorLimite;
	}

	public void setValorLimite(BigDecimal valorLimite){

		this.valorLimite = valorLimite;
	}

	public Short getIndicadorGeracaoAutomatica(){

		return this.indicadorGeracaoAutomatica;
	}

	public void setIndicadorGeracaoAutomatica(Short indicadorGeracaoAutomatica){

		this.indicadorGeracaoAutomatica = indicadorGeracaoAutomatica;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	public LancamentoItemContabil getLancamentoItemContabil(){

		return lancamentoItemContabil;
	}

	public void setLancamentoItemContabil(LancamentoItemContabil lancamentoItemContabil){

		this.lancamentoItemContabil = lancamentoItemContabil;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Filtro retornaFiltro(){

		FiltroCreditoTipo filtroCreditoTipo = new FiltroCreditoTipo();

		filtroCreditoTipo.adicionarParametro(new ParametroSimples(FiltroCreditoTipo.ID, this.getId()));
		filtroCreditoTipo.adicionarCaminhoParaCarregamentoEntidade("lancamentoItemContabil");

		return filtroCreditoTipo;
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getDescricao();
	}

	public String getCodigoConstante(){

		return codigoConstante;
	}

	public void setCodigoConstante(String codigoConstante){

		this.codigoConstante = codigoConstante;
	}

	public String getDescricaoFormatadaFaturamento(){

		String retorno = "";

		if(this.descricao != null){

			retorno = (Util.completaString(
							Util.completarStringComValorEsquerda(this.id.toString(), "0", 3) + "-" + this.descricao.toString(), 20))
							.substring(0, 20);
		}

		return retorno;
	}

}