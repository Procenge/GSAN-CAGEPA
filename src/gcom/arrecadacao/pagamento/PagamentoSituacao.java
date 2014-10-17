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

package gcom.arrecadacao.pagamento;

import gcom.util.tabelaauxiliar.abreviada.TabelaAuxiliarAbreviada;

/** @author Hibernate CodeGenerator */
public class PagamentoSituacao
				extends TabelaAuxiliarAbreviada {

	private static final long serialVersionUID = 1L;

	// Constantes *********************************
	public final static Integer PAGAMENTO_CLASSIFICADO = Integer.valueOf(0);

	public final static Integer PAGAMENTO_EM_DUPLICIDADE = Integer.valueOf(1);

	// criado por pedro alexandre em 25/05/2006 para o UC[0276]
	public final static Integer DOCUMENTO_INEXISTENTE = Integer.valueOf(2);

	public final static Integer FATURA_INEXISTENTE = Integer.valueOf(2);

	public final static Integer VALOR_EM_EXCESSO = Integer.valueOf(3);

	public final static Integer VALOR_A_BAIXAR = Integer.valueOf(4);

	public final static Integer VALOR_NAO_CONFERE = Integer.valueOf(5);

	public final static Integer MOVIMENTO_ABERTO = Integer.valueOf(7);

	public final static Integer DUPLICIDADE_EXCESSO_DEVOLVIDO = Integer.valueOf(9);

	public final static Integer PAGAMENTO_DOC_CANCELADO = Integer.valueOf(10);

	public final static Integer PAGAMENTO_DOC_PARCELADO = Integer.valueOf(11);

	public final static Integer PAGAMENTO_DOC_PAGO = Integer.valueOf(15);

	public final static Integer PAGAMENTO_DUPLICADO = Integer.valueOf(12);

	public final static Integer PAGAMENTO_A_MENOR = Integer.valueOf(13);

	public final static Integer PAGAMENTO_A_MAIOR = Integer.valueOf(14);

	public final static Integer CONTA_EM_FATURAMENTO = Integer.valueOf(17);

	public final static Integer CONTA_EM_FATURAMENTO_ = Integer.valueOf(16);

	public final static Integer MIGRACAO = Integer.valueOf(8);

	public final static Integer VALOR_A_BAIXAR_MENOR = Integer.valueOf(18);

	public Short indicadorValorDuplicado;

	public Short indicadorAdmiteDevolucao;

	@Override
	public String[] retornaCamposChavePrimaria(){

		// TODO Auto-generated method stub
		return null;
	}

	public Short getIndicadorValorDuplicado(){

		return indicadorValorDuplicado;
	}

	public void setIndicadorValorDuplicado(Short indicadorValorDuplicado){

		this.indicadorValorDuplicado = indicadorValorDuplicado;
	}

	public Short getIndicadorAdmiteDevolucao(){

		return indicadorAdmiteDevolucao;
	}

	public void setIndicadorAdmiteDevolucao(Short indicadorAdmiteDevolucao){

		this.indicadorAdmiteDevolucao = indicadorAdmiteDevolucao;
	}

	// ********************************************

	/** identifier field */
	/*
	 * private Integer id;
	 *//** nullable persistent field */
	/*
	 * private String descricao;
	 *//** nullable persistent field */
	/*
	 * private String descricaoAbreviada;
	 *//** nullable persistent field */
	/*
	 * private Date ultimaAlteracao;
	 *//** nullable persistent field */
	/*
	 * private Short indicadorUso;
	 *//** full constructor */
	/*
	 * public PagamentoSituacao(String descricao, String descricaoAbreviada, Date ultimaAlteracao,
	 * Short indicadorUso) {
	 * this.descricao = descricao;
	 * this.descricaoAbreviada = descricaoAbreviada;
	 * this.ultimaAlteracao = ultimaAlteracao;
	 * this.indicadorUso = indicadorUso;
	 * }
	 *//** default constructor */
	/*
	 * public PagamentoSituacao() {
	 * }
	 * public Integer getId(){
	 * return this.id;
	 * }
	 * public void setId(Integer id){
	 * this.id = id;
	 * }
	 * public String getDescricao(){
	 * return this.descricao;
	 * }
	 * public void setDescricao(String descricao){
	 * this.descricao = descricao;
	 * }
	 * public String getDescricaoAbreviada(){
	 * return this.descricaoAbreviada;
	 * }
	 * public void setDescricaoAbreviada(String descricaoAbreviada){
	 * this.descricaoAbreviada = descricaoAbreviada;
	 * }
	 * public Date getUltimaAlteracao(){
	 * return this.ultimaAlteracao;
	 * }
	 * public void setUltimaAlteracao(Date ultimaAlteracao){
	 * this.ultimaAlteracao = ultimaAlteracao;
	 * }
	 * public Short getIndicadorUso(){
	 * return this.indicadorUso;
	 * }
	 * public void setIndicadorUso(Short indicadorUso){
	 * this.indicadorUso = indicadorUso;
	 * }
	 * public String toString(){
	 * return new ToStringBuilder(this).append("id", getId()).toString();
	 * }
	 * public String[] retornaCamposChavePrimaria(){
	 * String[] retorno = new String[1];
	 * retorno[0] = "id";
	 * return retorno;
	 * }
	 * public String getDescricaoParaRegistroTransacao(){
	 * return getId() + "";
	 * }
	 * public Filtro retornaFiltro(){
	 * FiltroPagamentoSituacao filtroPagamentoSituacao = new FiltroPagamentoSituacao();
	 * filtroPagamentoSituacao.adicionarParametro(new
	 * ParametroSimples(FiltroPagamentoSituacao.CODIGO, this.getId()));
	 * return filtroPagamentoSituacao;
	 * }
	 */
}
