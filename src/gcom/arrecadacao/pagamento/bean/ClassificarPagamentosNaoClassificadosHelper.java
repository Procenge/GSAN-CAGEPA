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

package gcom.arrecadacao.pagamento.bean;

import gcom.arrecadacao.pagamento.Pagamento;
import gcom.cobranca.bean.CalcularAcrescimoPorImpontualidadeHelper;
import gcom.faturamento.conta.Conta;
import gcom.faturamento.conta.ContaHistorico;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Action responsável por exibir a página de Classificar em Lote Pagamentos Não Classificados.
 * [UC3080] Classificar em Lote Pagamentos Não Classificados.
 * 
 * @author Josenildo Neves
 * @date 29/11/2012
 */
public class ClassificarPagamentosNaoClassificadosHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Pagamento pagamento;

	private Conta conta;

	private ContaHistorico contaHistorico;

	private CalcularAcrescimoPorImpontualidadeHelper calcularAcrescimoPorImpontualidadeHelper;

	private BigDecimal valorDocumentoReajustado;

	private BigDecimal ValorReajuste;

	public Pagamento getPagamento(){

		return pagamento;
	}

	public void setPagamento(Pagamento pagamento){

		this.pagamento = pagamento;
	}

	public CalcularAcrescimoPorImpontualidadeHelper getCalcularAcrescimoPorImpontualidadeHelper(){

		return calcularAcrescimoPorImpontualidadeHelper;
	}

	public void setCalcularAcrescimoPorImpontualidadeHelper(
					CalcularAcrescimoPorImpontualidadeHelper calcularAcrescimoPorImpontualidadeHelper){

		this.calcularAcrescimoPorImpontualidadeHelper = calcularAcrescimoPorImpontualidadeHelper;
	}

	public Conta getConta(){

		return conta;
	}

	public void setConta(Conta conta){

		this.conta = conta;
	}

	public ContaHistorico getContaHistorico(){

		return contaHistorico;
	}

	public void setContaHistorico(ContaHistorico contaHistorico){

		this.contaHistorico = contaHistorico;
	}

	public BigDecimal getValorDocumentoReajustado(){

		return valorDocumentoReajustado;
	}

	public void setValorDocumentoReajustado(BigDecimal valorDocumentoReajustado){

		this.valorDocumentoReajustado = valorDocumentoReajustado;
	}

	public BigDecimal getValorReajuste(){

		return ValorReajuste;
	}

	public void setValorReajuste(BigDecimal valorReajuste){

		ValorReajuste = valorReajuste;
	}

}
