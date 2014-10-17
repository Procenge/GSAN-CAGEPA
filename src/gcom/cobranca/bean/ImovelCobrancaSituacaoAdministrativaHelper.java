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

package gcom.cobranca.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * [UC 3060] Consultar Retirar Imovel Cobranca Administrativa
 * 
 * @author Josenildo Neves
 * @date 30/07/2012
 */

public class ImovelCobrancaSituacaoAdministrativaHelper {

	private static final long serialVersionUID = 1L;

	private Integer idImovelCobrancaSituacao;

	private Integer idImovel;

	private String nomeCliente;

	private Date dtImplantacaoCobranca;

	private Date dtRetiradaCobranca;

	private Integer qtDebitos;

	private BigDecimal vlDebitos;

	private BigDecimal percentualRemuneracao;

	private BigDecimal percentualReincidencia;

	private BigDecimal percentualEspecial;

	private BigDecimal percentualParcelamento;

	private BigDecimal valorRemuneracao;

	private BigDecimal valorReincidencia;

	private BigDecimal valorEspecial;

	private BigDecimal valorParcelamento;

	/**
	 * @param idImovel
	 * @param nomeCliente
	 * @param dtImplantacaoCobranca
	 * @param dtRetiradaCobranca
	 * @param qtDebitos
	 * @param vlDebitos
	 */
	public ImovelCobrancaSituacaoAdministrativaHelper(Integer idImovelCobrancaSituacao, Integer idImovel, String nomeCliente,
														Date dtImplantacaoCobranca,
														Date dtRetiradaCobranca, Integer qtDebitos, BigDecimal vlDebitos) {

		super();
		this.idImovelCobrancaSituacao = idImovelCobrancaSituacao;
		this.idImovel = idImovel;
		this.nomeCliente = nomeCliente;
		this.dtImplantacaoCobranca = dtImplantacaoCobranca;
		this.dtRetiradaCobranca = dtRetiradaCobranca;
		this.qtDebitos = qtDebitos;
		this.vlDebitos = vlDebitos;
	}

	/**
	 * @param percentualRemuneracao
	 * @param percentualReincidencia
	 * @param percentualEspecial
	 * @param percentualParcelamento
	 * @param valorRemuneracao
	 * @param valorReincidencia
	 * @param valorEspecial
	 * @param valorParcelamento
	 */
	public ImovelCobrancaSituacaoAdministrativaHelper(BigDecimal percentualRemuneracao, BigDecimal percentualReincidencia,
														BigDecimal percentualEspecial, BigDecimal percentualParcelamento,
														BigDecimal valorRemuneracao, BigDecimal valorReincidencia,
														BigDecimal valorEspecial, BigDecimal valorParcelamento) {

		super();
		this.percentualRemuneracao = percentualRemuneracao;
		this.percentualReincidencia = percentualReincidencia;
		this.percentualEspecial = percentualEspecial;
		this.percentualParcelamento = percentualParcelamento;
		this.valorRemuneracao = valorRemuneracao;
		this.valorReincidencia = valorReincidencia;
		this.valorEspecial = valorEspecial;
		this.valorParcelamento = valorParcelamento;
	}

	/**
	 * @return the idImovelCobracaSituacao
	 */
	public Integer getIdImovelCobrancaSituacao(){

		return idImovelCobrancaSituacao;
	}

	/**
	 * @param idImovelCobracaSituacao the idImovelCobracaSituacao to set
	 */
	public void setIdImovelCobrancaSituacao(Integer idImovelCobrancaSituacao){

		this.idImovelCobrancaSituacao = idImovelCobrancaSituacao;
	}

	/**
	 * @return the idImovel
	 */
	public Integer getIdImovel(){

		return idImovel;
	}


	/**
	 * @param idImovel the idImovel to set
	 */
	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}


	/**
	 * @return the nomeCliente
	 */
	public String getNomeCliente(){

		return nomeCliente;
	}


	/**
	 * @param nomeCliente the nomeCliente to set
	 */
	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}


	/**
	 * @return the dtImplantacaoCobranca
	 */
	public Date getDtImplantacaoCobranca(){

		return dtImplantacaoCobranca;
	}


	/**
	 * @param dtImplantacaoCobranca the dtImplantacaoCobranca to set
	 */
	public void setDtImplantacaoCobranca(Date dtImplantacaoCobranca){

		this.dtImplantacaoCobranca = dtImplantacaoCobranca;
	}


	/**
	 * @return the dtRetiradaCobranca
	 */
	public Date getDtRetiradaCobranca(){

		return dtRetiradaCobranca;
	}


	/**
	 * @param dtRetiradaCobranca the dtRetiradaCobranca to set
	 */
	public void setDtRetiradaCobranca(Date dtRetiradaCobranca){

		this.dtRetiradaCobranca = dtRetiradaCobranca;
	}


	/**
	 * @return the qtDebitos
	 */
	public Integer getQtDebitos(){

		return qtDebitos;
	}


	/**
	 * @param qtDebitos the qtDebitos to set
	 */
	public void setQtDebitos(Integer qtDebitos){

		this.qtDebitos = qtDebitos;
	}


	/**
	 * @return the vlDebitos
	 */
	public BigDecimal getVlDebitos(){

		return vlDebitos;
	}


	/**
	 * @param vlDebitos the vlDebitos to set
	 */
	public void setVlDebitos(BigDecimal vlDebitos){

		this.vlDebitos = vlDebitos;
	}

	/**
	 * @return the percentualRemuneracao
	 */
	public BigDecimal getPercentualRemuneracao(){

		return percentualRemuneracao;
	}

	/**
	 * @param percentualRemuneracao the percentualRemuneracao to set
	 */
	public void setPercentualRemuneracao(BigDecimal percentualRemuneracao){

		this.percentualRemuneracao = percentualRemuneracao;
	}

	/**
	 * @return the percentualReincidencia
	 */
	public BigDecimal getPercentualReincidencia(){

		return percentualReincidencia;
	}

	/**
	 * @param percentualReincidencia the percentualReincidencia to set
	 */
	public void setPercentualReincidencia(BigDecimal percentualReincidencia){

		this.percentualReincidencia = percentualReincidencia;
	}

	/**
	 * @return the percentualEspecial
	 */
	public BigDecimal getPercentualEspecial(){

		return percentualEspecial;
	}

	/**
	 * @param percentualEspecial the percentualEspecial to set
	 */
	public void setPercentualEspecial(BigDecimal percentualEspecial){

		this.percentualEspecial = percentualEspecial;
	}

	/**
	 * @return the percentualParcelamento
	 */
	public BigDecimal getPercentualParcelamento(){

		return percentualParcelamento;
	}

	/**
	 * @param percentualParcelamento the percentualParcelamento to set
	 */
	public void setPercentualParcelamento(BigDecimal percentualParcelamento){

		this.percentualParcelamento = percentualParcelamento;
	}

	/**
	 * @return the valorRemuneracao
	 */
	public BigDecimal getValorRemuneracao(){

		return valorRemuneracao;
	}

	/**
	 * @param valorRemuneracao the valorRemuneracao to set
	 */
	public void setValorRemuneracao(BigDecimal valorRemuneracao){

		this.valorRemuneracao = valorRemuneracao;
	}

	/**
	 * @return the valorReincidencia
	 */
	public BigDecimal getValorReincidencia(){

		return valorReincidencia;
	}

	/**
	 * @param valorReincidencia the valorReincidencia to set
	 */
	public void setValorReincidencia(BigDecimal valorReincidencia){

		this.valorReincidencia = valorReincidencia;
	}

	/**
	 * @return the valorEspecial
	 */
	public BigDecimal getValorEspecial(){

		return valorEspecial;
	}

	/**
	 * @param valorEspecial the valorEspecial to set
	 */
	public void setValorEspecial(BigDecimal valorEspecial){

		this.valorEspecial = valorEspecial;
	}

	/**
	 * @return the valorParcelamento
	 */
	public BigDecimal getValorParcelamento(){

		return valorParcelamento;
	}

	/**
	 * @param valorParcelamento the valorParcelamento to set
	 */
	public void setValorParcelamento(BigDecimal valorParcelamento){

		this.valorParcelamento = valorParcelamento;
	}

}