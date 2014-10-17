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

package gcom.gui.arrecadacao.pagamento;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * Action responsável por exibir a página de Classificar em Lote Pagamentos Não Classificados.
 * [UC3080] Classificar em Lote Pagamentos Não Classificados.
 * 
 * @author Josenildo Neves
 * @date 29/11/2012
 */
public class ClassificarLotePagamentosNaoClassificadosActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private char opcaoGeracao;

	private Integer localidadeInicial;

	private String localidadeInicialEncontrada;

	private String nomeLocalidadeInicial;

	private Integer localidadeFinal;

	private String localidadeFinalEncontrada;

	private String nomeLocalidadeFinal;

	private String referenciaArrecadacaoInicial;

	private String referenciaArrecadacaoFinal;

	private String referenciaPagamentoInicial;

	private String referenciaPagamentoFinal;

	private String dataPagamentoInicial;

	private String dataPagamentoFinal;

	private Integer situacaoPagamento;

	private String limiteMaximoDiferenca;

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

	public String getLocalidadeInicialEncontrada(){

		return localidadeInicialEncontrada;
	}

	public void setLocalidadeInicialEncontrada(String localidadeInicialEncontrada){

		this.localidadeInicialEncontrada = localidadeInicialEncontrada;
	}

	public String getNomeLocalidadeInicial(){

		return nomeLocalidadeInicial;
	}

	public void setNomeLocalidadeInicial(String nomeLocalidadeInicial){

		this.nomeLocalidadeInicial = nomeLocalidadeInicial;
	}

	public Integer getLocalidadeFinal(){

		return localidadeFinal;
	}

	public void setLocalidadeFinal(Integer localidadeFinal){

		this.localidadeFinal = localidadeFinal;
	}

	public String getLocalidadeFinalEncontrada(){

		return localidadeFinalEncontrada;
	}

	public void setLocalidadeFinalEncontrada(String localidadeFinalEncontrada){

		this.localidadeFinalEncontrada = localidadeFinalEncontrada;
	}

	public String getNomeLocalidadeFinal(){

		return nomeLocalidadeFinal;
	}

	public void setNomeLocalidadeFinal(String nomeLocalidadeFinal){

		this.nomeLocalidadeFinal = nomeLocalidadeFinal;
	}

	public String getReferenciaArrecadacaoInicial(){

		return referenciaArrecadacaoInicial;
	}

	public void setReferenciaArrecadacaoInicial(String referenciaArrecadacaoInicial){

		this.referenciaArrecadacaoInicial = referenciaArrecadacaoInicial;
	}

	public String getReferenciaArrecadacaoFinal(){

		return referenciaArrecadacaoFinal;
	}

	public void setReferenciaArrecadacaoFinal(String referenciaArrecadacaoFinal){

		this.referenciaArrecadacaoFinal = referenciaArrecadacaoFinal;
	}

	public String getReferenciaPagamentoInicial(){

		return referenciaPagamentoInicial;
	}

	public void setReferenciaPagamentoInicial(String referenciaPagamentoInicial){

		this.referenciaPagamentoInicial = referenciaPagamentoInicial;
	}

	public String getReferenciaPagamentoFinal(){

		return referenciaPagamentoFinal;
	}

	public void setReferenciaPagamentoFinal(String referenciaPagamentoFinal){

		this.referenciaPagamentoFinal = referenciaPagamentoFinal;
	}

	public String getDataPagamentoInicial(){

		return dataPagamentoInicial;
	}

	public void setDataPagamentoInicial(String dataPagamentoInicial){

		this.dataPagamentoInicial = dataPagamentoInicial;
	}

	public String getDataPagamentoFinal(){

		return dataPagamentoFinal;
	}

	public void setDataPagamentoFinal(String dataPagamentoFinal){

		this.dataPagamentoFinal = dataPagamentoFinal;
	}

	public Integer getSituacaoPagamento(){

		return situacaoPagamento;
	}

	public void setSituacaoPagamento(Integer situacaoPagamento){

		this.situacaoPagamento = situacaoPagamento;
	}

	public String getLimiteMaximoDiferenca(){

		return limiteMaximoDiferenca;
	}

	public void setLimiteMaximoDiferenca(String limiteMaximoDiferenca){

		this.limiteMaximoDiferenca = limiteMaximoDiferenca;
	}

}
