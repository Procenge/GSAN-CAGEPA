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

package gcom.cobranca;

import gcom.arrecadacao.PagamentoTipo;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/** @author Hibernate CodeGenerator */
public class DocumentoTipo
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer id;

	/** nullable persistent field */
	private String descricaoDocumentoTipo;

	/** nullable persistent field */
	private String descricaoAbreviado;

	/** nullable persistent field */
	private Short indicadorPagavel;

	/** nullable persistent field */
	private Short indicadorUso;

	/** persistent field */
	private Short indicadorGerarHistoricoImovel;

	/** nullable persistent field */
	private Date ultimaAlteracao;

	private PagamentoTipo tipoPagamentoImovel;

	private PagamentoTipo tipoPagamentoCliente;

	private Integer codigo;

	// constantes
	public final static Integer CONTA = Integer.valueOf(1);

	public final static Integer ENTRADA_DE_PARCELAMENTO = Integer.valueOf(2);

	public final static Integer FATURA_CLIENTE = Integer.valueOf(5);

	public final static Integer DEBITO_A_COBRAR = Integer.valueOf(6);

	public final static Integer GUIA_PAGAMENTO = Integer.valueOf(7);

	public final static Integer DEVOLUCAO_VALOR = Integer.valueOf(8);

	public final static Integer CREDITO_A_REALIZAR = Integer.valueOf(10);

	public final static Integer AVISO_DE_CORTE = Integer.valueOf(12);

	public final static Integer CORTE_FISICO = Integer.valueOf(13);

	public final static Integer EXTRATO_DE_DEBITO = Integer.valueOf(14);

	public final static Integer AVISO_DE_DEBITO = Integer.valueOf(18);

	public final static Integer AVISO_DE_DEBITO_E_CORTE = Integer.valueOf(19);

	public final static Integer CARTA_COBRANCA_SUPRIMIDO = Integer.valueOf(21);

	public final static Integer CARTA_COBRANCA_CORTADO = Integer.valueOf(22);

	public final static Integer CARTA_COBRANCA_LIGADO = Integer.valueOf(25);

	public final static Integer DOCUMENTO_COBRANCA = Integer.valueOf(3);

	public final static Integer BOLETO_BANCARIO = Integer.valueOf(39);

	public final static int ORDEM_FISCALIZACAO_FACTIVEL = 27;

	public final static int ORDEM_FISCALIZACAO_SUPRIMIDO = 16;

	public final static int ORDEM_FISCALIZACAO_CORTADO = 17;

	public final static int ORDEM_FISCALIZACAO_TOTAL = 30;

	public final static int ORDEM_FISCALIZACAO_POTENCIAL = 28;

	public final static int ORDEM_FISCALIZACAO_LIGADO = 29;

	public final static Integer SPC_SP = Integer.valueOf(32);

	public final static Integer SERASA = Integer.valueOf(33);

	public final static Integer EXTRATO_DE_DEBITO_PARCELAMENTO = 35;

	public final static Integer EXTRATO_DE_DEBITO_PARCELAMENTO_CARTAO = 36;

	public final static Integer CARTA_OPCAO_PARCELAMENTO = 37;

	public final static Integer SPC_BRASIL = Integer.valueOf(38);

	public final static Integer DOCUMENTO_DE_COBRANCA = 3;

	public final static Integer NOTA_RECEBIMENTO = 40;

	/** full constructor */
	public DocumentoTipo(String descricaoDocumentoTipo, String descricaoAbreviado, Short indicadorPagavel, Short indicadorUso,
							Date ultimaAlteracao) {

		this.descricaoDocumentoTipo = descricaoDocumentoTipo;
		this.descricaoAbreviado = descricaoAbreviado;
		this.indicadorPagavel = indicadorPagavel;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
	}

	public DocumentoTipo(Integer id) {

		this.id = id;
	}

	/** default constructor */
	public DocumentoTipo() {

	}

	public Integer getId(){

		return this.id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public String getDescricaoDocumentoTipo(){

		return this.descricaoDocumentoTipo;
	}

	public void setDescricaoDocumentoTipo(String descricaoDocumentoTipo){

		this.descricaoDocumentoTipo = descricaoDocumentoTipo;
	}

	public String getDescricaoAbreviado(){

		return this.descricaoAbreviado;
	}

	public void setDescricaoAbreviado(String descricaoAbreviado){

		this.descricaoAbreviado = descricaoAbreviado;
	}

	public Short getIndicadorPagavel(){

		return this.indicadorPagavel;
	}

	public void setIndicadorPagavel(Short indicadorPagavel){

		this.indicadorPagavel = indicadorPagavel;
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

		String retorno = null;

		if(this.getId() != null && this.getDescricaoDocumentoTipo() != null){
			retorno = this.getId() + " - " + this.getDescricaoDocumentoTipo();
		}else{
			retorno = null;
		}

		return retorno;
	}

	public Filtro retornaFiltro(){

		FiltroDocumentoTipo filtroDocumentoTipo = new FiltroDocumentoTipo();

		filtroDocumentoTipo.adicionarParametro(new ParametroSimples(FiltroDocumentoTipo.ID, this.getId()));
		return filtroDocumentoTipo;
	}

	public PagamentoTipo getTipoPagamentoImovel(){

		return tipoPagamentoImovel;
	}

	public void setTipoPagamentoImovel(PagamentoTipo tipoPagamentoImovel){

		this.tipoPagamentoImovel = tipoPagamentoImovel;
	}

	public PagamentoTipo getTipoPagamentoCliente(){

		return tipoPagamentoCliente;
	}

	public void setTipoPagamentoCliente(PagamentoTipo tipoPagamentoCliente){

		this.tipoPagamentoCliente = tipoPagamentoCliente;
	}

	public Integer getCodigo(){

		return codigo;
	}

	public void setCodigo(Integer codigo){

		this.codigo = codigo;
	}

	public Short getIndicadorGerarHistoricoImovel(){

		return indicadorGerarHistoricoImovel;
	}

	public void setIndicadorGerarHistoricoImovel(Short indicadorGerarHistoricoImovel){

		this.indicadorGerarHistoricoImovel = indicadorGerarHistoricoImovel;
	}

}
