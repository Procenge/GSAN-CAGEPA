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

package gcom.faturamento.bean;

import gcom.faturamento.conta.ContaMotivoRevisao;
import gcom.util.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * Classe Bean que encapsula os dados de entrada para a Geração da Fatura do Cliente Responsável
 * 
 * @author Luciano Galvao
 * @date 25/06/2013
 */
public class GerarFaturaClienteResponsavelHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer periodoReferenciaContasInicial;

	private Integer periodoReferenciaContasFinal;

	private Date periodoVencimentoContasInicial;

	private Date periodoVencimentoContasFinal;

	private Short indicadorContasRevisao;

	private Collection<ContaMotivoRevisao> motivosRevisao;

	private Date dataVencimento;


	public GerarFaturaClienteResponsavelHelper(Integer periodoReferenciaContasInicial, Integer periodoReferenciaContasFinal,
												Date periodoVencimentoContasInicial, Date periodoVencimentoContasFinal,
												Short indicadorContasRevisao, Collection<ContaMotivoRevisao> motivosRevisao,
												Date dataVencimento) {

		super();
		this.periodoReferenciaContasInicial = periodoReferenciaContasInicial;
		this.periodoReferenciaContasFinal = periodoReferenciaContasFinal;
		this.periodoVencimentoContasInicial = periodoVencimentoContasInicial;
		this.periodoVencimentoContasFinal = periodoVencimentoContasFinal;
		this.indicadorContasRevisao = indicadorContasRevisao;
		this.motivosRevisao = motivosRevisao;
		this.dataVencimento = dataVencimento;
	}

	// Getters e Setters
	public Integer getPeriodoReferenciaContasInicial(){

		return periodoReferenciaContasInicial;
	}

	public void setPeriodoReferenciaContasInicial(Integer periodoReferenciaContasInicial){

		this.periodoReferenciaContasInicial = periodoReferenciaContasInicial;
	}

	public Integer getPeriodoReferenciaContasFinal(){

		return periodoReferenciaContasFinal;
	}

	public void setPeriodoReferenciaContasFinal(Integer periodoReferenciaContasFinal){

		this.periodoReferenciaContasFinal = periodoReferenciaContasFinal;
	}

	public Date getPeriodoVencimentoContasInicial(){

		return periodoVencimentoContasInicial;
	}

	public void setPeriodoVencimentoContasInicial(Date periodoVencimentoContasInicial){

		this.periodoVencimentoContasInicial = periodoVencimentoContasInicial;
	}

	public Date getPeriodoVencimentoContasFinal(){

		return periodoVencimentoContasFinal;
	}

	public void setPeriodoVencimentoContasFinal(Date periodoVencimentoContasFinal){

		this.periodoVencimentoContasFinal = periodoVencimentoContasFinal;
	}

	public Short getIndicadorContasRevisao(){

		return indicadorContasRevisao;
	}

	public void setIndicadorContasRevisao(Short indicadorContasRevisao){

		this.indicadorContasRevisao = indicadorContasRevisao;
	}

	public Collection<ContaMotivoRevisao> getMotivosRevisao(){

		return motivosRevisao;
	}

	public void setMotivosRevisao(Collection<ContaMotivoRevisao> motivosRevisao){

		this.motivosRevisao = motivosRevisao;
	}

	public Date getDataVencimento(){

		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento){

		this.dataVencimento = dataVencimento;
	}

	public Collection<Integer> getMotivosRevisaoIds(){

		Collection<Integer> motivosIds = null;

		if(!Util.isVazioOrNulo(motivosRevisao)){
			motivosIds = new ArrayList<Integer>();
			for(ContaMotivoRevisao motivoRevisao : motivosRevisao){
				motivosIds.add(motivoRevisao.getId());
			}
		}

		return motivosIds;
	}
}
