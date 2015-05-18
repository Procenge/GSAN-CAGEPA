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

package gcom.dividaativa;

import gcom.arrecadacao.pagamento.GuiaPagamento;
import gcom.faturamento.conta.Conta;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author gcom
 */

public class DividaAtivaInscricaoDebito
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	private Integer id;

	private Integer numeroPrestacao;

	private Date dataVencimento;

	private Integer referencia;

	private Integer consumo;

	private BigDecimal valorDocumento;

	private BigDecimal valorAcrescimo;

	private BigDecimal valorMulta;

	private BigDecimal valorJuros;

	private BigDecimal valorJurosSucumbencia;

	private Date ultimaAlteracao;

	private GuiaPagamento guiaPagamento;

	private DividaAtivaInscricaoResumo dividaAtivaInscricaoResumo;

	private Conta conta;

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Integer getNumeroPrestacao(){

		return numeroPrestacao;
	}

	public void setNumeroPrestacao(Integer numeroPrestacao){

		this.numeroPrestacao = numeroPrestacao;
	}

	public Date getDataVencimento(){

		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento){

		this.dataVencimento = dataVencimento;
	}

	public Integer getReferencia(){

		return referencia;
	}

	public void setReferencia(Integer referencia){

		this.referencia = referencia;
	}

	public Integer getConsumo(){

		return consumo;
	}

	public void setConsumo(Integer consumo){

		this.consumo = consumo;
	}

	public BigDecimal getValorDocumento(){

		return valorDocumento;
	}

	public void setValorDocumento(BigDecimal valorDocumento){

		this.valorDocumento = valorDocumento;
	}

	public BigDecimal getValorAcrescimo(){

		return valorAcrescimo;
	}

	public void setValorAcrescimo(BigDecimal valorAcrescimo){

		this.valorAcrescimo = valorAcrescimo;
	}

	public BigDecimal getValorMulta(){

		return valorMulta;
	}

	public void setValorMulta(BigDecimal valorMulta){

		this.valorMulta = valorMulta;
	}

	public BigDecimal getValorJuros(){

		return valorJuros;
	}

	public void setValorJuros(BigDecimal valorJuros){

		this.valorJuros = valorJuros;
	}

	public BigDecimal getValorJurosSucumbencia(){

		return valorJurosSucumbencia;
	}

	public void setValorJurosSucumbencia(BigDecimal valorJurosSucumbencia){

		this.valorJurosSucumbencia = valorJurosSucumbencia;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public GuiaPagamento getGuiaPagamento(){

		return guiaPagamento;
	}

	public void setGuiaPagamento(GuiaPagamento guiaPagamento){

		this.guiaPagamento = guiaPagamento;
	}

	public Conta getConta(){

		return conta;
	}

	public void setConta(Conta conta){

		this.conta = conta;
	}

	public DividaAtivaInscricaoResumo getDividaAtivaInscricaoResumo(){

		return dividaAtivaInscricaoResumo;
	}

	public void setDividaAtivaInscricaoResumo(DividaAtivaInscricaoResumo dividaAtivaInscricaoResumo){

		this.dividaAtivaInscricaoResumo = dividaAtivaInscricaoResumo;
	}

	@Override
	public Filtro retornaFiltro(){

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String[] retornaCamposChavePrimaria(){

		// TODO Auto-generated method stub
		return null;
	}

}
