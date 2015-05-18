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
 * 
 * GSANPCG
 * Virgínia Melo
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

package gcom.relatorio.cobranca;

import gcom.relatorio.RelatorioBean;

public class RelatorioCertidaoPositivaModelo1Bean
				implements RelatorioBean {

	private String nomeCliente;

	private String enderecoImovel;

	private String inscricaoImovel;

	private String matriculaImovel;

	private String indicadorConta;

	private String anoMesesExercicioContasOuGuias;

	private String exercicio;

	private String categoria;

	private String referenciaAtual;

	private String valorPrincipal;

	private String valorAtualizacao;

	private String valorJuros;

	private String valorMulta;

	private String valorDivida;

	private Integer anoOrdenacao;

	private Integer indicadorContaOrdenacao;

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getEnderecoImovel(){

		return enderecoImovel;
	}

	public void setEnderecoImovel(String enderecoImovel){

		this.enderecoImovel = enderecoImovel;
	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	public String getMatriculaImovel(){

		return matriculaImovel;
	}

	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}

	public String getIndicadorConta(){

		return indicadorConta;
	}

	public void setIndicadorConta(String indicadorConta){

		this.indicadorConta = indicadorConta;
	}

	public String getAnoMesesExercicioContasOuGuias(){

		return anoMesesExercicioContasOuGuias;
	}

	public void setAnoMesesExercicioContasOuGuias(String anoMesesExercicioContasOuGuias){

		this.anoMesesExercicioContasOuGuias = anoMesesExercicioContasOuGuias;
	}

	public String getExercicio(){

		return exercicio;
	}

	public void setExercicio(String exercicio){

		this.exercicio = exercicio;
	}

	public String getCategoria(){

		return categoria;
	}

	public void setCategoria(String categoria){

		this.categoria = categoria;
	}

	public String getReferenciaAtual(){

		return referenciaAtual;
	}

	public void setReferenciaAtual(String referenciaAtual){

		this.referenciaAtual = referenciaAtual;
	}

	public String getValorPrincipal(){

		return valorPrincipal;
	}

	public void setValorPrincipal(String valorPrincipal){

		this.valorPrincipal = valorPrincipal;
	}

	public String getValorAtualizacao(){

		return valorAtualizacao;
	}

	public void setValorAtualizacao(String valorAtualizacao){

		this.valorAtualizacao = valorAtualizacao;
	}

	public String getValorJuros(){

		return valorJuros;
	}

	public void setValorJuros(String valorJuros){

		this.valorJuros = valorJuros;
	}

	public String getValorMulta(){

		return valorMulta;
	}

	public void setValorMulta(String valorMulta){

		this.valorMulta = valorMulta;
	}

	public String getValorDivida(){

		return valorDivida;
	}

	public void setValorDivida(String valorDivida){

		this.valorDivida = valorDivida;
	}

	public Integer getAnoOrdenacao(){

		return anoOrdenacao;
	}

	public void setAnoOrdenacao(Integer anoOrdenacao){

		this.anoOrdenacao = anoOrdenacao;
	}

	public Integer getIndicadorContaOrdenacao(){

		return indicadorContaOrdenacao;
	}

	public void setIndicadorContaOrdenacao(Integer indicadorContaOrdenacao){

		this.indicadorContaOrdenacao = indicadorContaOrdenacao;
	}

}
