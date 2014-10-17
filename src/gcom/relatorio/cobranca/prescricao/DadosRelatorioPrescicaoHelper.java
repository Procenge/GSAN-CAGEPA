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

package gcom.relatorio.cobranca.prescricao;

import gcom.faturamento.conta.Conta;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

/**
 * [UC3137] Comandar Prescrição de Débito
 * 
 * @author Anderson Italo
 * @date 27/02/2014
 */
public class DadosRelatorioPrescicaoHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private String idLocalidade;

	private String nomeLocalidade;

	private String matriculaFormatada;

	private Integer idImovel;

	private String inscricao;

	private String idLigacaoAguaSituacao;

	private String idLigacaoEsgotoSituacao;

	private String categoria;

	private String enderecoImovel;

	private BigDecimal valorTotalContas;

	private Integer quantidadeContas;

	private BigDecimal valorTotalGuias;

	private Integer quantidadeGuias;

	private Collection<Conta> colecaoContasPrescricao;

	private Collection<Object[]> colecaoDadosGuiasPagamentoPrescricao;

	public String getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(String idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public String getNomeLocalidade(){

		return nomeLocalidade;
	}

	public void setNomeLocalidade(String nomeLocalidade){

		this.nomeLocalidade = nomeLocalidade;
	}
	
	public String getMatriculaFormatada(){

		return matriculaFormatada;
	}

	public void setMatriculaFormatada(String matriculaFormatada){

		this.matriculaFormatada = matriculaFormatada;
	}

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public String getInscricao(){

		return inscricao;
	}

	public void setInscricao(String inscricao){

		this.inscricao = inscricao;
	}

	public String getIdLigacaoAguaSituacao(){

		return idLigacaoAguaSituacao;
	}

	public void setIdLigacaoAguaSituacao(String idLigacaoAguaSituacao){

		this.idLigacaoAguaSituacao = idLigacaoAguaSituacao;
	}

	public String getIdLigacaoEsgotoSituacao(){

		return idLigacaoEsgotoSituacao;
	}

	public void setIdLigacaoEsgotoSituacao(String idLigacaoEsgotoSituacao){

		this.idLigacaoEsgotoSituacao = idLigacaoEsgotoSituacao;
	}

	public String getCategoria(){

		return categoria;
	}

	public void setCategoria(String categoria){

		this.categoria = categoria;
	}

	public String getEnderecoImovel(){

		return enderecoImovel;
	}

	public void setEnderecoImovel(String enderecoImovel){

		this.enderecoImovel = enderecoImovel;
	}

	public BigDecimal getValorTotalContas(){

		return valorTotalContas;
	}

	public void setValorTotalContas(BigDecimal valorTotalContas){

		this.valorTotalContas = valorTotalContas;
	}

	public Integer getQuantidadeContas(){

		return quantidadeContas;
	}

	public void setQuantidadeContas(Integer quantidadeContas){

		this.quantidadeContas = quantidadeContas;
	}

	public BigDecimal getValorTotalGuias(){

		return valorTotalGuias;
	}

	public void setValorTotalGuias(BigDecimal valorTotalGuias){

		this.valorTotalGuias = valorTotalGuias;
	}

	public Integer getQuantidadeGuias(){

		return quantidadeGuias;
	}

	public void setQuantidadeGuias(Integer quantidadeGuias){

		this.quantidadeGuias = quantidadeGuias;
	}

	public Collection<Conta> getColecaoContasPrescricao(){

		return colecaoContasPrescricao;
	}

	public void setColecaoContasPrescricao(Collection<Conta> colecaoContasPrescricao){

		this.colecaoContasPrescricao = colecaoContasPrescricao;
	}

	public Collection<Object[]> getColecaoDadosGuiasPagamentoPrescricao(){

		return colecaoDadosGuiasPagamentoPrescricao;
	}

	public void setColecaoDadosGuiasPagamentoPrescricao(Collection<Object[]> colecaoDadosGuiasPagamentoPrescricao){

		this.colecaoDadosGuiasPagamentoPrescricao = colecaoDadosGuiasPagamentoPrescricao;
	}


}