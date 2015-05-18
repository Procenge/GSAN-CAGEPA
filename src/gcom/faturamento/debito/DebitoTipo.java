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

package gcom.faturamento.debito;

import gcom.arrecadacao.pagamento.GuiaPagamento;
import gcom.cobranca.FiltroResolucaoDiretoria;
import gcom.financeiro.FinanciamentoTipo;
import gcom.financeiro.lancamento.LancamentoItemContabil;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.ErroRepositorioException;
import gcom.util.RepositorioUtilHBM;
import gcom.util.SistemaException;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author Saulo Lima
 * @date 31/01/2009
 *       Alteração nos valores das constantes para o cliente ADA
 */
public class DebitoTipo
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	public static Integer ACRESCIMOS_POR_IMPONTUALIDADE;

	public static Integer ATUALIZACAO_MONETARIA;

	public static Integer CONSUMO_NAO_CONTABILIZADO;

	public static Integer CORRECAO_PARCELAMENTO;

	public static Integer DESPESA_POSTAL;

	public static Integer ENTRADA_PARCELAMENTO;

	public static Integer ENTRADA_PARCELAMENTO_COBRANCA_ADMINISTRATIVA;

	public static Integer ENTRADA_PARCELAMENTO_DIVIDA_ATIVA;

	public static Integer ENTRADA_PARCELAMENTO_EXECUCAO_FISCAL;

	public static Integer INSTAL_SUBST_HIDROMETRO;

	public static Integer JUROS_MORA;

	public static Integer JUROS_SOBRE_PARCELAMENTO;

	public static Integer JUROS_SOBRE_PARCELAS_FATURAS_A;

	public static Integer JUROS_SELIC;

	public static Integer MULTA_IMPONTUALIDADE;

	public static Integer MULTA_NAO_USAR;

	public static Integer MULTA_PARA_ATRASO_PAGAMENTO_DO_MES;

	public static Integer MULTA_POR_ATRASO_DE_PAGAMENTO;

	public static Integer MULTA_POR_DESCUMPRIMENTO_PARCELAMENTO;

	public static Integer MULTA_POR_INFRACAO;

	public static Integer OUTROS;

	public static Integer PARCELAMENTO;

	public static Integer PARCELAMENTO_ACRESCIMOS_IMPONTUALIDADE;

	public static Integer PARCELAMENTO_CONTAS;

	public static Integer PARCELAMENTO_DEBITO_A_COBRAR_CURTO_PRAZO;

	public static Integer PARCELAMENTO_DEBITO_A_COBRAR_LONGO_PRAZO;

	public static Integer PARCELAMENTO_GUIAS_PAGAMENTO;

	public static Integer REPARCELAMENTOS_CURTO_PRAZO;

	public static Integer REPARCELAMENTOS_LONGO_PRAZO;

	public static Integer SANCOES_REGULAMENTARES;

	public static Integer TAXA_2_VIA_CONTA;

	public static Integer TAXA_EXTRATO_DE_DEBITO;

	public static Integer TAXA_RELACAO_DE_DEBITO;

	public static Integer TAXA_COBRANCA;

	public static Integer ESTORNO_DESCONTO_PARCELAMENTOS_ANTERIORES;

	public static Integer DIF_PAGTO_MENOR;

	public static Integer DIF_PGTO_MENOR;

	public static Integer PARCELAMENTO_DEBITO_TRANSFERIDO;

	public static Integer REPARCELAMENTO;

	public static Integer TEMPORARIO_CAERD;

	public static Integer RATEIO_AGUA;

	public static Integer RATEIO_ESGOTO;

	public static Integer RATEIO_TEE;

	public static Integer ESGOTO_ESPECIAL;

	public static Integer SUCUMBENCIA;

	public static Integer DILIGENCIAS;

	public static Integer ORDEM_SERVICO_HORA;

	public static Integer TARIFA_AGUA;

	public static Integer TARIFA_ESGOTO;

	public static Integer VISITA_TECNICA;

	public static Integer JUROS_PARCELAMENTO_RESP;

	/** identifier field */
	private Integer id;

	/** persistent field */
	private LancamentoItemContabil lancamentoItemContabil;

	/** nullable persistent field */
	private String descricao;

	/** nullable persistent field */
	private String descricaoAbreviada;

	/** nullable persistent field */
	private Short indicadorUso;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	/** nullable persistent field */
	private BigDecimal valorLimite;

	/** nullable persistent field */
	private Short indicadorGeracaoAutomatica;

	/** nullable persistent field */
	private Short indicadorGeracaoConta;

	private Short indicadorIncidenciaMulta;

	private Short indicadorIncidenciaJurosMora;

	private Set<GuiaPagamento> guiasPagamento;

	private FinanciamentoTipo financiamentoTipo;

	private String codigoConstante;

	// Alteração para NILA
	private Short indicadorValorCalcular;

	private BigDecimal valorPadrao;

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = new String[1];
		retorno[0] = "id";
		return retorno;
	}

	public Filtro retornaFiltro(){

		FiltroDebitoTipo filtroDebitoTipo = new FiltroDebitoTipo();

		filtroDebitoTipo.adicionarCaminhoParaCarregamentoEntidade("lancamentoItemContabil");
		filtroDebitoTipo.adicionarCaminhoParaCarregamentoEntidade("financiamentoTipo");
		filtroDebitoTipo.adicionarParametro(new ParametroSimples(FiltroResolucaoDiretoria.CODIGO, this.getId()));

		return filtroDebitoTipo;
	}

	/** full constructor */
	public DebitoTipo(LancamentoItemContabil lancamentoItemContabil, String descricao, String descricaoAbreviada, Short indicadorUso,
						Date ultimaAlteracao, BigDecimal valorLimite, Short indicadorGeracaoAutomatica,
						FinanciamentoTipo financiamentoTipo, Short indicadorIncidenciaMulta, Short indicadorIncidenciaJurosMora,
						Set<GuiaPagamento> guiasPagamento) {

		this.lancamentoItemContabil = lancamentoItemContabil;
		this.descricao = descricao;
		this.descricaoAbreviada = descricaoAbreviada;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.valorLimite = valorLimite;
		this.indicadorGeracaoAutomatica = indicadorGeracaoAutomatica;
		this.financiamentoTipo = financiamentoTipo;
		this.indicadorIncidenciaMulta = indicadorIncidenciaMulta;
		this.indicadorIncidenciaJurosMora = indicadorIncidenciaJurosMora;
		this.guiasPagamento = guiasPagamento;
	}

	/** default constructor */
	public DebitoTipo() {

	}

	public DebitoTipo(Integer id) {

		this.id = id;

	}

	/** minimal constructor */
	public DebitoTipo(LancamentoItemContabil lancamentoItemContabil, FinanciamentoTipo financiamentoTipo) {

		this.lancamentoItemContabil = lancamentoItemContabil;
		this.financiamentoTipo = financiamentoTipo;
	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public LancamentoItemContabil getLancamentoItemContabil(){

		return this.lancamentoItemContabil;
	}

	public void setLancamentoItemContabil(LancamentoItemContabil lancamentoItemContabil){

		this.lancamentoItemContabil = lancamentoItemContabil;
	}

	public String getDescricao(){

		if(this.descricao == null){
			return "";
		}

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

	public FinanciamentoTipo getFinanciamentoTipo(){

		return this.financiamentoTipo;
	}

	public void setFinanciamentoTipo(FinanciamentoTipo financiamentoTipo){

		this.financiamentoTipo = financiamentoTipo;
	}

	public String toString(){

		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	/**
	 * @return Retorna o campo indicadorGeracaoConta.
	 */
	public Short getIndicadorGeracaoConta(){

		return indicadorGeracaoConta;
	}

	/**
	 * @param indicadorGeracaoConta
	 *            O indicadorGeracaoConta a ser setado.
	 */
	public void setIndicadorGeracaoConta(Short indicadorGeracaoConta){

		this.indicadorGeracaoConta = indicadorGeracaoConta;
	}

	/**
	 * Description of the Method
	 * 
	 * @param other
	 *            Description of the Parameter
	 * @return Description of the Return Value
	 */
	public boolean equals(Object other){

		if((this == other)){
			return true;
		}
		if(!(other instanceof DebitoTipo)){
			return false;
		}
		DebitoTipo castOther = (DebitoTipo) other;

		return new EqualsBuilder().append(this.getId(), castOther.getId()).isEquals();
	}

	@Override
	public void initializeLazy(){

		this.retornaCamposChavePrimaria();
		getDescricao();
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getDescricao();
	}

	public Short getIndicadorIncidenciaMulta(){

		return indicadorIncidenciaMulta;
	}

	public void setIndicadorIncidenciaMulta(Short indicadorIncidenciaMulta){

		this.indicadorIncidenciaMulta = indicadorIncidenciaMulta;
	}

	public Short getIndicadorIncidenciaJurosMora(){

		return indicadorIncidenciaJurosMora;
	}

	public void setIndicadorIncidenciaJurosMora(Short indicadorIncidenciaJurosMora){

		this.indicadorIncidenciaJurosMora = indicadorIncidenciaJurosMora;
	}

	public Set<GuiaPagamento> getGuiasPagamento(){

		return guiasPagamento;
	}

	public void setGuiasPagamento(Set<GuiaPagamento> guiasPagamento){

		this.guiasPagamento = guiasPagamento;
	}

	public Short getIndicadorValorCalcular(){

		return indicadorValorCalcular;
	}

	public void setIndicadorValorCalcular(Short indicadorValorCalcular){

		this.indicadorValorCalcular = indicadorValorCalcular;
	}

	public BigDecimal getValorPadrao(){

		return valorPadrao;
	}

	public void setValorPadrao(BigDecimal valorPadrao){

		this.valorPadrao = valorPadrao;
	}

	public String getDescricaoFormatada(){

		return getId() + " - " + getDescricao();
	}

	public String getCodigoConstante(){

		return codigoConstante;
	}

	public void setCodigoConstante(String codigoConstante){

		this.codigoConstante = codigoConstante;
	}

	/**
	 * Este método foi criado para inicializar as constantes. A sua implementação visa utilizar a
	 * solução dada para casos em que haja constantes com descrições diferentes mas que utilizavam o
	 * mesmo valor em clientes distintos.
	 * 
	 * @author Hebert Falcão
	 * @date 30/04/2012
	 */
	public static void inicializarConstantes(){

		ACRESCIMOS_POR_IMPONTUALIDADE = DebitoTipoEnum.ACRESCIMOS_POR_IMPONTUALIDADE.getId();
		ATUALIZACAO_MONETARIA = DebitoTipoEnum.ATUALIZACAO_MONETARIA.getId();
		CONSUMO_NAO_CONTABILIZADO = DebitoTipoEnum.CONSUMO_NAO_CONTABILIZADO.getId();
		CORRECAO_PARCELAMENTO = DebitoTipoEnum.CORRECAO_PARCELAMENTO.getId();
		DESPESA_POSTAL = DebitoTipoEnum.DESPESA_POSTAL.getId();
		ENTRADA_PARCELAMENTO = DebitoTipoEnum.ENTRADA_PARCELAMENTO.getId();
		ENTRADA_PARCELAMENTO_COBRANCA_ADMINISTRATIVA = DebitoTipoEnum.ENTRADA_PARCELAMENTO_COBRANCA_ADMINISTRATIVA.getId();
		ENTRADA_PARCELAMENTO_DIVIDA_ATIVA = DebitoTipoEnum.ENTRADA_PARCELAMENTO_DIVIDA_ATIVA.getId();
		ENTRADA_PARCELAMENTO_EXECUCAO_FISCAL = DebitoTipoEnum.ENTRADA_PARCELAMENTO_EXECUCAO_FISCAL.getId();
		INSTAL_SUBST_HIDROMETRO = DebitoTipoEnum.INSTAL_SUBST_HIDROMETRO.getId();
		JUROS_MORA = DebitoTipoEnum.JUROS_MORA.getId();
		JUROS_SOBRE_PARCELAMENTO = DebitoTipoEnum.JUROS_SOBRE_PARCELAMENTO.getId();
		JUROS_SOBRE_PARCELAS_FATURAS_A = DebitoTipoEnum.JUROS_SOBRE_PARCELAS_FATURAS_A.getId();
		MULTA_IMPONTUALIDADE = DebitoTipoEnum.MULTA_IMPONTUALIDADE.getId();
		MULTA_NAO_USAR = DebitoTipoEnum.MULTA_NAO_USAR.getId();
		MULTA_PARA_ATRASO_PAGAMENTO_DO_MES = DebitoTipoEnum.MULTA_PARA_ATRASO_PAGAMENTO_DO_MES.getId();
		MULTA_POR_ATRASO_DE_PAGAMENTO = DebitoTipoEnum.MULTA_POR_ATRASO_DE_PAGAMENTO.getId();
		MULTA_POR_DESCUMPRIMENTO_PARCELAMENTO = DebitoTipoEnum.MULTA_POR_DESCUMPRIMENTO_PARCELAMENTO.getId();
		MULTA_POR_INFRACAO = DebitoTipoEnum.MULTA_POR_INFRACAO.getId();
		OUTROS = DebitoTipoEnum.OUTROS.getId();
		PARCELAMENTO = DebitoTipoEnum.PARCELAMENTO.getId();
		PARCELAMENTO_ACRESCIMOS_IMPONTUALIDADE = DebitoTipoEnum.PARCELAMENTO_ACRESCIMOS_IMPONTUALIDADE.getId();
		PARCELAMENTO_CONTAS = DebitoTipoEnum.PARCELAMENTO_CONTAS.getId();
		PARCELAMENTO_DEBITO_A_COBRAR_CURTO_PRAZO = DebitoTipoEnum.PARCELAMENTO_DEBITO_A_COBRAR_CURTO_PRAZO.getId();
		PARCELAMENTO_DEBITO_A_COBRAR_LONGO_PRAZO = DebitoTipoEnum.PARCELAMENTO_DEBITO_A_COBRAR_LONGO_PRAZO.getId();
		PARCELAMENTO_GUIAS_PAGAMENTO = DebitoTipoEnum.PARCELAMENTO_GUIAS_PAGAMENTO.getId();
		REPARCELAMENTOS_CURTO_PRAZO = DebitoTipoEnum.REPARCELAMENTOS_CURTO_PRAZO.getId();
		REPARCELAMENTOS_LONGO_PRAZO = DebitoTipoEnum.REPARCELAMENTOS_LONGO_PRAZO.getId();
		SANCOES_REGULAMENTARES = DebitoTipoEnum.SANCOES_REGULAMENTARES.getId();
		TAXA_2_VIA_CONTA = DebitoTipoEnum.TAXA_2_VIA_CONTA.getId();
		TAXA_COBRANCA = DebitoTipoEnum.TAXA_COBRANCA.getId();
		TAXA_EXTRATO_DE_DEBITO = DebitoTipoEnum.TAXA_EXTRATO_DE_DEBITO.getId();
		TAXA_RELACAO_DE_DEBITO = DebitoTipoEnum.TAXA_RELACAO_DE_DEBITO.getId();
		DIF_PAGTO_MENOR = DebitoTipoEnum.DIF_PAGTO_MENOR.getId();
		DIF_PGTO_MENOR = DebitoTipoEnum.DIF_PGTO_MENOR.getId();
		PARCELAMENTO_DEBITO_TRANSFERIDO = DebitoTipoEnum.PARCELAMENTO_DEBITO_TRANSFERIDO.getId();
		REPARCELAMENTO = DebitoTipoEnum.REPARCELAMENTO.getId();
		TEMPORARIO_CAERD = DebitoTipoEnum.TEMPORARIO_CAERD.getId();
		SUCUMBENCIA = DebitoTipoEnum.SUCUMBENCIA.getId();
		DILIGENCIAS = DebitoTipoEnum.DILIGENCIAS.getId();
		ESGOTO_ESPECIAL = DebitoTipoEnum.ESGOTO_ESPECIAL.getId();
		ORDEM_SERVICO_HORA = DebitoTipoEnum.ORDEM_SERVICO_HORA.getId();
		TARIFA_AGUA = DebitoTipoEnum.TARIFA_AGUA.getId();
		TARIFA_ESGOTO = DebitoTipoEnum.TARIFA_ESGOTO.getId();
		VISITA_TECNICA = DebitoTipoEnum.VISITA_TECNICA.getId();
		ESTORNO_DESCONTO_PARCELAMENTOS_ANTERIORES = DebitoTipoEnum.ESTORNO_DESCONTO_PARCELAMENTOS_ANTERIORES.getId();
		RATEIO_AGUA = DebitoTipoEnum.RATEIO_AGUA.getId();
		RATEIO_ESGOTO = DebitoTipoEnum.RATEIO_ESGOTO.getId();
		RATEIO_TEE = DebitoTipoEnum.RATEIO_TEE.getId();
		JUROS_SELIC = DebitoTipoEnum.JUROS_SELIC.getId();

		JUROS_PARCELAMENTO_RESP = DebitoTipoEnum.JUROS_PARCELAMENTO_RESP.getId();
	}

	/**
	 * Este enum foi criado para dar suporte ao carregamento de constantes da classe em tempo de
	 * execução. As constantes foram criadas aqui como atributos do enum, o que resolveu o problema
	 * das constantes com descrições diferentes mas que utilizavam o mesmo
	 * valor. Caso a constante não seja utilizada por um determinado cliente será atribuído -1 ao
	 * seu valor.
	 * 
	 * @author Hebert Falcão
	 * @date 30/04/2012
	 */
	public static enum DebitoTipoEnum {
		ACRESCIMOS_POR_IMPONTUALIDADE, ATUALIZACAO_MONETARIA, CONSUMO_NAO_CONTABILIZADO, CORRECAO_PARCELAMENTO, DESPESA_POSTAL,
		ENTRADA_PARCELAMENTO, ENTRADA_PARCELAMENTO_COBRANCA_ADMINISTRATIVA, INSTAL_SUBST_HIDROMETRO, JUROS_MORA, JUROS_SOBRE_PARCELAMENTO,
		JUROS_SOBRE_PARCELAS_FATURAS_A, MULTA_IMPONTUALIDADE, MULTA_NAO_USAR, MULTA_PARA_ATRASO_PAGAMENTO_DO_MES,
		MULTA_POR_ATRASO_DE_PAGAMENTO, MULTA_POR_DESCUMPRIMENTO_PARCELAMENTO, MULTA_POR_INFRACAO, OUTROS, PARCELAMENTO,
		PARCELAMENTO_ACRESCIMOS_IMPONTUALIDADE, PARCELAMENTO_CONTAS, PARCELAMENTO_DEBITO_A_COBRAR_CURTO_PRAZO,
		PARCELAMENTO_DEBITO_A_COBRAR_LONGO_PRAZO, PARCELAMENTO_GUIAS_PAGAMENTO, REPARCELAMENTOS_CURTO_PRAZO, REPARCELAMENTOS_LONGO_PRAZO,
		SANCOES_REGULAMENTARES, TAXA_2_VIA_CONTA, TAXA_COBRANCA, TAXA_EXTRATO_DE_DEBITO, TAXA_RELACAO_DE_DEBITO, DIF_PAGTO_MENOR,
		DIF_PGTO_MENOR, PARCELAMENTO_DEBITO_TRANSFERIDO, REPARCELAMENTO, TEMPORARIO_CAERD, RATEIO_AGUA, RATEIO_ESGOTO, RATEIO_TEE,
		ESGOTO_ESPECIAL, SUCUMBENCIA, DILIGENCIAS, ENTRADA_PARCELAMENTO_DIVIDA_ATIVA, ENTRADA_PARCELAMENTO_EXECUCAO_FISCAL,
		ORDEM_SERVICO_HORA, TARIFA_AGUA, TARIFA_ESGOTO, VISITA_TECNICA, ESTORNO_DESCONTO_PARCELAMENTOS_ANTERIORES, JUROS_SELIC, JUROS_PARCELAMENTO_RESP;

		private Integer id = -1;

		private DebitoTipoEnum() {

			try{
				DebitoTipo debitoTipo = RepositorioUtilHBM.getInstancia().pesquisarPorCodigo(name(), DebitoTipo.class);

				if(debitoTipo != null){
					id = debitoTipo.getId();
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

}
