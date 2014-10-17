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
 * 
 * GSANPCG
 * Virg�nia Melo
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

package gcom.relatorio.cobranca;

import gcom.relatorio.RelatorioBean;

public class RelatorioCertidaoNegativaBean
				implements RelatorioBean {

	private String assuntoDescricao;

	private String nomeCliente;

	private String enderecoCliente;

	private String periodoInicial;

	private String periodoFinal;

	private String inscricaoImovel;

	private String matriculaImovel;

	private String numeroHidrometroImovel;

	private String nomeUsuario;

	private String emissorUsuario;

	private String empresa;

	private String cidade;

	public String getNomeUsuario(){

		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario){

		this.nomeUsuario = nomeUsuario;
	}

	public String getEmissorUsuario(){

		return emissorUsuario;
	}

	public void setEmissorUsuario(String emissorUsuario){

		this.emissorUsuario = emissorUsuario;
	}

	public RelatorioCertidaoNegativaBean() {

	}

	public RelatorioCertidaoNegativaBean(String assuntoDescricao, String nomeCliente, String enderecoCliente, String periodoInicial,
											String periodoFinal, String inscricaoImovel, String matriculaImovel,
											String numeroHidrometroImovel, String nomeUsuario, String emissorUsuario, String empresa) {

		this.assuntoDescricao = assuntoDescricao;
		this.nomeCliente = nomeCliente;
		this.enderecoCliente = enderecoCliente;
		this.periodoInicial = periodoInicial;
		this.periodoFinal = periodoFinal;
		this.inscricaoImovel = inscricaoImovel;
		this.matriculaImovel = matriculaImovel;
		this.numeroHidrometroImovel = numeroHidrometroImovel;
		this.nomeUsuario = nomeUsuario;
		this.emissorUsuario = emissorUsuario;
		this.empresa = empresa;

	}

	public String getAssuntoDescricao(){

		return assuntoDescricao;
	}

	public void setAssuntoDescricao(String assuntoDescricao){

		this.assuntoDescricao = assuntoDescricao;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getEnderecoCliente(){

		return enderecoCliente;
	}

	public void setEnderecoCliente(String enderecoCliente){

		this.enderecoCliente = enderecoCliente;
	}

	public String getPeriodoInicial(){

		return periodoInicial;
	}

	public void setPeriodoInicial(String periodoInicial){

		this.periodoInicial = periodoInicial;
	}

	public String getPeriodoFinal(){

		return periodoFinal;
	}

	public void setPeriodoFinal(String periodoFinal){

		this.periodoFinal = periodoFinal;
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

	public String getNumeroHidrometroImovel(){

		return numeroHidrometroImovel;
	}

	public void setNumeroHidrometroImovel(String numeroHidrometroImovel){

		this.numeroHidrometroImovel = numeroHidrometroImovel;
	}

	public void setEmpresa(String empresa){

		this.empresa = empresa;
	}

	public String getEmpresa(){

		return empresa;
	}

	public String getCidade(){

		return cidade;
	}

	public void setCidade(String cidade){

		this.cidade = cidade;
	}

}
