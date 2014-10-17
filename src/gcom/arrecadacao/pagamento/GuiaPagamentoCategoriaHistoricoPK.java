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

import java.io.Serializable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/** @author Hibernate CodeGenerator */
public class GuiaPagamentoCategoriaHistoricoPK
				implements Serializable {

	private static final long serialVersionUID = 1L;

	/** identifier field */
	private Integer categoriaId;

	/** identifier field */
	private Integer guiaPagamentoId;

	/** identifier field */
	private Integer lancamentoItemContabilId;

	/** identifier field */
	private Short numeroPrestacao;

	/**
	 * full constructor
	 * 
	 * @param lancamentoItemContabilId
	 *            TODO
	 * @param numeroPrestacao
	 *            TODO
	 */
	public GuiaPagamentoCategoriaHistoricoPK(Integer categoriaId, Integer guiaPagamentoId, Integer lancamentoItemContabilId,
												Short numeroPrestacao) {

		this.categoriaId = categoriaId;
		this.guiaPagamentoId = guiaPagamentoId;
		this.lancamentoItemContabilId = lancamentoItemContabilId;
		this.numeroPrestacao = numeroPrestacao;
	}

	public GuiaPagamentoCategoriaHistoricoPK() {

	}

	public boolean equals(Object other){

		if((this == other)) return true;
		if(!(other instanceof GuiaPagamentoCategoriaHistoricoPK)) return false;
		GuiaPagamentoCategoriaHistoricoPK castOther = (GuiaPagamentoCategoriaHistoricoPK) other;
		return new EqualsBuilder().append(this.getGuiaPagamentoId(), castOther.getGuiaPagamentoId()).append(this.getCategoriaId(),
						castOther.getCategoriaId()).append(this.getLancamentoItemContabilId(), castOther.getLancamentoItemContabilId())
						.append(this.getNumeroPrestacao(), castOther.getNumeroPrestacao()).isEquals();
	}

	public int hashCode(){

		return new HashCodeBuilder().append(getGuiaPagamentoId()).append(getCategoriaId()).append(getLancamentoItemContabilId()).append(
						getNumeroPrestacao()).toHashCode();
	}

	/**
	 * @return the categoriaId
	 */
	public Integer getCategoriaId(){

		return categoriaId;
	}

	/**
	 * @param categoriaId
	 *            the categoriaId to set
	 */
	public void setCategoriaId(Integer categoriaId){

		this.categoriaId = categoriaId;
	}

	/**
	 * @return the guiaPagamentoId
	 */
	public Integer getGuiaPagamentoId(){

		return guiaPagamentoId;
	}

	/**
	 * @param guiaPagamentoId
	 *            the guiaPagamentoId to set
	 */
	public void setGuiaPagamentoId(Integer guiaPagamentoId){

		this.guiaPagamentoId = guiaPagamentoId;
	}

	/**
	 * @return the lancamentoItemContabilId
	 */
	public Integer getLancamentoItemContabilId(){

		return lancamentoItemContabilId;
	}

	/**
	 * @param lancamentoItemContabilId
	 *            the lancamentoItemContabilId to set
	 */
	public void setLancamentoItemContabilId(Integer lancamentoItemContabilId){

		this.lancamentoItemContabilId = lancamentoItemContabilId;
	}

	/**
	 * @return the numeroPrestacao
	 */
	public Short getNumeroPrestacao(){

		return numeroPrestacao;
	}

	/**
	 * @param numeroPrestacao
	 *            the numeroPrestacao to set
	 */
	public void setNumeroPrestacao(Short numeroPrestacao){

		this.numeroPrestacao = numeroPrestacao;
	}

}
