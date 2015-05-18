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

package gcom.arrecadacao.pagamento.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Action respons�vel por exibir a p�gina de Classificar em Lote Pagamentos N�o Classificados.
 * [UC3080] Classificar em Lote Pagamentos N�o Classificados.
 * 
 * @author Josenildo Neves
 * @date 29/11/2012
 */
public class ClassificarLotePagamentosNaoClassificadosHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private char opcaoGeracao;

	private Integer localidadeInicial;

	private Integer localidadeFinal;

	private Integer referenciaArrecadacaoInicial;

	private Integer referenciaArrecadacaoFinal;

	private Integer referenciaPagamentoInicial;

	private Integer referenciaPagamentoFinal;

	private Date dataPagamentoInicial;

	private Date dataPagamentoFinal;

	private Integer situacaoPagamento;

	private BigDecimal limiteMaximoDiferenca;

	private String DescricaoSitucaoPagamento;



	public char getOpcaoGeracao(){

		return opcaoGeracao;
	}

	public void setOpcaoGeracao(char opcaoGeracao){

		this.opcaoGeracao = opcaoGeracao;
	}

	public Integer getLocalidadeInicial(){

		return localidadeInicial;
	}

	public void setLocalidadeInicial(Integer localidadeInicial){

		this.localidadeInicial = localidadeInicial;
	}

	public Integer getLocalidadeFinal(){

		return localidadeFinal;
	}

	public void setLocalidadeFinal(Integer localidadeFinal){

		this.localidadeFinal = localidadeFinal;
	}

	public Integer getReferenciaArrecadacaoInicial(){

		return referenciaArrecadacaoInicial;
	}

	public void setReferenciaArrecadacaoInicial(Integer referenciaArrecadacaoInicial){

		this.referenciaArrecadacaoInicial = referenciaArrecadacaoInicial;
	}

	public Integer getReferenciaArrecadacaoFinal(){

		return referenciaArrecadacaoFinal;
	}

	public void setReferenciaArrecadacaoFinal(Integer referenciaArrecadacaoFinal){

		this.referenciaArrecadacaoFinal = referenciaArrecadacaoFinal;
	}

	public Integer getReferenciaPagamentoInicial(){

		return referenciaPagamentoInicial;
	}

	public void setReferenciaPagamentoInicial(Integer referenciaPagamentoInicial){

		this.referenciaPagamentoInicial = referenciaPagamentoInicial;
	}

	public Integer getReferenciaPagamentoFinal(){

		return referenciaPagamentoFinal;
	}

	public void setReferenciaPagamentoFinal(Integer referenciaPagamentoFinal){

		this.referenciaPagamentoFinal = referenciaPagamentoFinal;
	}

	public Date getDataPagamentoInicial(){

		return dataPagamentoInicial;
	}

	public void setDataPagamentoInicial(Date dataPagamentoInicial){

		this.dataPagamentoInicial = dataPagamentoInicial;
	}

	public Date getDataPagamentoFinal(){

		return dataPagamentoFinal;
	}

	public void setDataPagamentoFinal(Date dataPagamentoFinal){

		this.dataPagamentoFinal = dataPagamentoFinal;
	}

	public Integer getSituacaoPagamento(){

		return situacaoPagamento;
	}

	public void setSituacaoPagamento(Integer situacaoPagamento){

		this.situacaoPagamento = situacaoPagamento;
	}

	public BigDecimal getLimiteMaximoDiferenca(){

		return limiteMaximoDiferenca;
	}

	public void setLimiteMaximoDiferenca(BigDecimal limiteMaximoDiferenca){

		this.limiteMaximoDiferenca = limiteMaximoDiferenca;
	}

	public String getDescricaoSitucaoPagamento(){

		return DescricaoSitucaoPagamento;
	}

	public void setDescricaoSitucaoPagamento(String descricaoSitucaoPagamento){

		DescricaoSitucaoPagamento = descricaoSitucaoPagamento;
	}



}
