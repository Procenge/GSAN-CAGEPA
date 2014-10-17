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

import gcom.util.Util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CobrancaDocumentoItemHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private BigDecimal valorItemCobrado;

	private BigDecimal valorAcrescimos;

	private String anoMesReferenciaConta;

	private Date dataVencimento;

	private String situacao = "";


	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public BigDecimal getValorItemCobrado(){

		return valorItemCobrado;
	}

	public void setValorItemCobrado(BigDecimal valorItemCobrado){

		this.valorItemCobrado = valorItemCobrado;
	}

	public BigDecimal getValorAcrescimos(){

		return valorAcrescimos;
	}

	public void setValorAcrescimos(BigDecimal valorAcrescimos){

		this.valorAcrescimos = valorAcrescimos;
	}

	public String getSituacao(){

		return situacao;
	}

	public void setSituacao(String situacao){

		this.situacao = situacao;
	}

	/**
	 * @return the anoMesReferenciaConta
	 */
	public String getAnoMesReferenciaConta(){

		return anoMesReferenciaConta;
	}

	/**
	 * @param anoMesReferenciaConta
	 *            the anoMesReferenciaConta to set
	 */
	public void setAnoMesReferenciaConta(String anoMesReferenciaConta){

		this.anoMesReferenciaConta = anoMesReferenciaConta;
	}

	/**
	 * @return the dataVencimento
	 */
	public Date getDataVencimento(){

		return dataVencimento;
	}

	/**
	 * @param dataVencimento
	 *            the dataVencimento to set
	 */
	public void setDataVencimento(Date dataVencimento){

		this.dataVencimento = dataVencimento;
	}

	/**
	 * Retorna a referência da conta formatada.
	 * 
	 * @return
	 */
	public String getAnoMesReferenciaContaFormatada(){

		String anoMesReferenciaConta = "Outras";
		if(Util.isNaoNuloBrancoZero(getAnoMesReferenciaConta())){
			anoMesReferenciaConta = Util.coverterAnoMesParaMesAno(getAnoMesReferenciaConta());

		}
		return anoMesReferenciaConta;
	}

	public String getDataVencimentoFormatada(){

		String dataVencimentoFormatada = null;
		if(Util.isNaoNuloBrancoZero(getDataVencimento())){
			dataVencimentoFormatada = Util.formatarData(getDataVencimento());

		}
		return dataVencimentoFormatada;

	}

	public Integer getAnoMesReferenciaContaInteger(){

		return Util.obterInteger(Util.formatarMesAnoParaAnoMesSemBarra(getAnoMesReferenciaConta()));
	}


}
