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

package gcom.relatorio.atendimentopublico;

import gcom.relatorio.RelatorioBean;

/**
 * Bean responsável de pegar os parametros que serão exibidos na parte de detail
 * do relatório.
 * 
 * @author Rafael Corrêa
 * @created 08/08/2006
 */
/**
 * @author Administrador
 */
public class RelatorioContratoPrestacaoServicoBean
				implements RelatorioBean {

	// Geral
	private String indicadorPessoaFisica;

	private String numeroPagina;

	private String numeroContrato;

	private String nomeCliente;

	private String cpfCliente;

	private String rgCliente;

	private String idImovel;

	private String enderecoImovel;

	private String enderecoCliente;

	private String municipio;


	// Padrão
	private String nomeUnidadeNegocio;

	private String nomeResponsavel;

	private String cpfResponsavel;

	private String rgResponsavel;

	private String categoria;

	private String consumoMinimo;

	private String data;


	// CAERD
	private String profissaoCliente;

	private String nacionalidadeCliente;

	private String estadoCivilCliente;

	private String orgaoEmissorRgCliente;

	private String ufRgCliente;

	private String inscricaoImovel;

	private String nomeAtendente;

	private String matriculaAtendente;

	private String nomeClienteProprietario;

	private String cpfClienteProprietario;

	private String rgClienteProprietario;

	private String orgaoEmissorRgClienteProprietario;

	private String ufRgClienteProprietario;

	public RelatorioContratoPrestacaoServicoBean(String indicadorPessoaFisica, String numeroPagina, String numeroContrato,
													String nomeCliente, String cpfCliente, String rgCliente, String idImovel,
													String enderecoImovel, String enderecoCliente,
													String municipio,
													String nomeUnidadeNegocio, String nomeResponsavel, String cpfResponsavel,
													String rgResponsavel, String categoria, String consumoMinimo, String data,
													String profissaoCliente, String nacionalidadeCliente, String estadoCivilCliente,
													String orgaoEmissorRgCliente, String ufRgCliente,
													String inscricaoImovel, String nomeAtendente,
													String matriculaAtendente, String nomeClienteProprietario,
 String cpfClienteProprietario,
													String rgClienteProprietario, String orgaoEmissorRgClienteProprietario,
 String ufRgClienteProprietario) {

		super();
		this.indicadorPessoaFisica = indicadorPessoaFisica;
		this.numeroPagina = numeroPagina;
		this.numeroContrato = numeroContrato;
		this.nomeCliente = nomeCliente;
		this.cpfCliente = cpfCliente;
		this.rgCliente = rgCliente;
		this.idImovel = idImovel;
		this.enderecoImovel = enderecoImovel;
		this.enderecoCliente = enderecoCliente;
		this.municipio = municipio;
		this.nomeUnidadeNegocio = nomeUnidadeNegocio;
		this.nomeResponsavel = nomeResponsavel;
		this.cpfResponsavel = cpfResponsavel;
		this.rgResponsavel = rgResponsavel;
		this.categoria = categoria;
		this.consumoMinimo = consumoMinimo;
		this.data = data;
		this.profissaoCliente = profissaoCliente;
		this.orgaoEmissorRgCliente = orgaoEmissorRgCliente;
		this.ufRgCliente = ufRgCliente;
		this.inscricaoImovel = inscricaoImovel;
		this.nomeAtendente = nomeAtendente;
		this.matriculaAtendente = matriculaAtendente;
		this.nomeClienteProprietario = nomeClienteProprietario;
		this.cpfClienteProprietario = cpfClienteProprietario;
		this.rgClienteProprietario = rgClienteProprietario;
		this.orgaoEmissorRgClienteProprietario = orgaoEmissorRgClienteProprietario;
		this.ufRgClienteProprietario = ufRgClienteProprietario;
		this.nacionalidadeCliente = nacionalidadeCliente;
		this.estadoCivilCliente = estadoCivilCliente;

	}

	public String getIndicadorPessoaFisica(){

		return indicadorPessoaFisica;
	}

	public void setIndicadorPessoaFisica(String indicadorPessoaFisica){

		this.indicadorPessoaFisica = indicadorPessoaFisica;
	}

	public String getNumeroPagina(){

		return numeroPagina;
	}

	public void setNumeroPagina(String numeroPagina){

		this.numeroPagina = numeroPagina;
	}

	public String getNumeroContrato(){

		return numeroContrato;
	}

	public void setNumeroContrato(String numeroContrato){

		this.numeroContrato = numeroContrato;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getCpfCliente(){

		return cpfCliente;
	}

	public void setCpfCliente(String cpfCliente){

		this.cpfCliente = cpfCliente;
	}

	public String getRgCliente(){

		return rgCliente;
	}

	public void setRgCliente(String rgCliente){

		this.rgCliente = rgCliente;
	}

	public String getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}

	public String getEnderecoImovel(){

		return enderecoImovel;
	}

	public void setEnderecoImovel(String enderecoImovel){

		this.enderecoImovel = enderecoImovel;
	}

	public String getEnderecoCliente(){

		return enderecoCliente;
	}

	public void setEnderecoCliente(String enderecoCliente){

		this.enderecoCliente = enderecoCliente;
	}

	public String getMunicipio(){

		return municipio;
	}

	public void setMunicipio(String municipio){

		this.municipio = municipio;
	}

	public String getNomeUnidadeNegocio(){

		return nomeUnidadeNegocio;
	}

	public void setNomeUnidadeNegocio(String nomeUnidadeNegocio){

		this.nomeUnidadeNegocio = nomeUnidadeNegocio;
	}

	public String getNomeResponsavel(){

		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel){

		this.nomeResponsavel = nomeResponsavel;
	}

	public String getCpfResponsavel(){

		return cpfResponsavel;
	}

	public void setCpfResponsavel(String cpfResponsavel){

		this.cpfResponsavel = cpfResponsavel;
	}

	public String getRgResponsavel(){

		return rgResponsavel;
	}

	public void setRgResponsavel(String rgResponsavel){

		this.rgResponsavel = rgResponsavel;
	}

	public String getCategoria(){

		return categoria;
	}

	public void setCategoria(String categoria){

		this.categoria = categoria;
	}

	public String getConsumoMinimo(){

		return consumoMinimo;
	}

	public void setConsumoMinimo(String consumoMinimo){

		this.consumoMinimo = consumoMinimo;
	}

	public String getData(){

		return data;
	}

	public void setData(String data){

		this.data = data;
	}

	public String getProfissaoCliente(){

		return profissaoCliente;
	}

	public void setProfissaoCliente(String profissaoCliente){

		this.profissaoCliente = profissaoCliente;
	}

	public String getOrgaoEmissorRgCliente(){

		return orgaoEmissorRgCliente;
	}

	public void setOrgaoEmissorRgCliente(String orgaoEmissorRgCliente){

		this.orgaoEmissorRgCliente = orgaoEmissorRgCliente;
	}

	public String getUfRgCliente(){

		return ufRgCliente;
	}

	public void setUfRgCliente(String ufRgCliente){

		this.ufRgCliente = ufRgCliente;
	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	public String getNomeAtendente(){

		return nomeAtendente;
	}

	public void setNomeAtendente(String nomeAtendente){

		this.nomeAtendente = nomeAtendente;
	}

	public String getMatriculaAtendente(){

		return matriculaAtendente;
	}

	public void setMatriculaAtendente(String matriculaAtendente){

		this.matriculaAtendente = matriculaAtendente;
	}

	public String getNomeClienteProprietario(){

		return nomeClienteProprietario;
	}

	public void setNomeClienteProprietario(String nomeClienteProprietario){

		this.nomeClienteProprietario = nomeClienteProprietario;
	}

	public String getCpfClienteProprietario(){

		return cpfClienteProprietario;
	}

	public void setCpfClienteProprietario(String cpfClienteProprietario){

		this.cpfClienteProprietario = cpfClienteProprietario;
	}

	public String getRgClienteProprietario(){

		return rgClienteProprietario;
	}

	public void setRgClienteProprietario(String rgClienteProprietario){

		this.rgClienteProprietario = rgClienteProprietario;
	}

	public String getOrgaoEmissorRgClienteProprietario(){

		return orgaoEmissorRgClienteProprietario;
	}

	public void setOrgaoEmissorRgClienteProprietario(String orgaoEmissorRgClienteProprietario){

		this.orgaoEmissorRgClienteProprietario = orgaoEmissorRgClienteProprietario;
	}

	public String getUfRgClienteProprietario(){

		return ufRgClienteProprietario;
	}

	public void setUfRgClienteProprietario(String ufRgClienteProprietario){

		this.ufRgClienteProprietario = ufRgClienteProprietario;
	}

	public String getNacionalidadeCliente(){

		return nacionalidadeCliente;
	}

	public void setNacionalidadeCliente(String nacionalidadeCliente){

		this.nacionalidadeCliente = nacionalidadeCliente;
	}

	public String getEstadoCivilCliente(){

		return estadoCivilCliente;
	}

	public void setEstadoCivilCliente(String estadoCivilCliente){

		this.estadoCivilCliente = estadoCivilCliente;
	}

}
