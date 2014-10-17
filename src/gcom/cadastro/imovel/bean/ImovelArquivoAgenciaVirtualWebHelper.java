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

package gcom.cadastro.imovel.bean;

/**
 * Gerar Arquivo Agencia Virtual WEB
 * 
 * @author Josenildo Neves
 * @date 20/03/2012
 */
public class ImovelArquivoAgenciaVirtualWebHelper {

	private Integer idImovel;

	private Integer idLocalidade;

	private Integer cdSetorComercial;

	private Short cdRota;

	private Short numeroSeguimento;

	private Short numeroLote;

	private Short numeroSubLote;

	private Integer idCliente;

	private String nomeCliente;

	private Integer idLogradouro;

	private String numeroImovel;

	private Integer idBairro;

	private Integer idCep;

	private String numeroHidormetro;

	private Integer clienteRelacaoTipo;

	private Integer esferaPoder;

	public ImovelArquivoAgenciaVirtualWebHelper(Integer idImovel, Integer idLocalidade, Integer cdSetorComercial, Short cdRota,
												Short numeroSeguimento, Short numeroLote, Short numeroSubLote, Integer idCliente,
												String nomeCliente, Integer idLogradouro, String numeroImovel, Integer idBairro,
												Integer idCep, String numeroHidormetro, Integer clienteRelacaoTipo, Integer esferaPoder) {

		super();
		this.idImovel = idImovel;
		this.idLocalidade = idLocalidade;
		this.cdSetorComercial = cdSetorComercial;
		this.cdRota = cdRota;
		this.numeroSeguimento = numeroSeguimento;
		this.numeroLote = numeroLote;
		this.numeroSubLote = numeroSubLote;
		this.idCliente = idCliente;
		this.nomeCliente = nomeCliente;
		this.idLogradouro = idLogradouro;
		this.numeroImovel = numeroImovel;
		this.idBairro = idBairro;
		this.idCep = idCep;
		this.numeroHidormetro = numeroHidormetro;
		this.clienteRelacaoTipo = clienteRelacaoTipo;
		this.esferaPoder = esferaPoder;
	}

	public ImovelArquivoAgenciaVirtualWebHelper(Integer idImovel, Integer idLocalidade, Integer cdSetorComercial, Short cdRota,
												Short numeroSeguimento, Short numeroLote, Short numeroSubLote, Integer idCliente,
												String nomeCliente, String numeroHidormetro/*
																							 * ,
																							 * Integer
																							 * clienteRelacaoTipo
																							 * ,
																							 * Integer
																							 * esferaPoder
																							 */) {

		super();
		this.idImovel = idImovel;
		this.idLocalidade = idLocalidade;
		this.cdSetorComercial = cdSetorComercial;
		this.cdRota = cdRota;
		this.numeroSeguimento = numeroSeguimento;
		this.numeroLote = numeroLote;
		this.numeroSubLote = numeroSubLote;
		this.idCliente = idCliente;
		this.nomeCliente = nomeCliente;
		this.numeroHidormetro = numeroHidormetro;
		/*
		 * this.clienteRelacaoTipo = clienteRelacaoTipo;
		 * this.esferaPoder = esferaPoder;
		 */
	}

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public Integer getIdLocalidade(){

		return idLocalidade;
	}

	public void setIdLocalidade(Integer idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	public Integer getCdSetorComercial(){

		return cdSetorComercial;
	}

	public void setCdSetorComercial(Integer cdSetorComercial){

		this.cdSetorComercial = cdSetorComercial;
	}

	public Short getCdRota(){

		return cdRota;
	}

	public void setCdRota(Short cdRota){

		this.cdRota = cdRota;
	}

	public Short getNumeroSeguimento(){

		return numeroSeguimento;
	}

	public void setNumeroSeguimento(Short numeroSeguimento){

		this.numeroSeguimento = numeroSeguimento;
	}

	public Short getNumeroLote(){

		return numeroLote;
	}

	public void setNumeroLote(Short numeroLote){

		this.numeroLote = numeroLote;
	}

	public Short getNumeroSubLote(){

		return numeroSubLote;
	}

	public void setNumeroSubLote(Short numeroSubLote){

		this.numeroSubLote = numeroSubLote;
	}

	public Integer getIdCliente(){

		return idCliente;
	}

	public void setIdCliente(Integer idCliente){

		this.idCliente = idCliente;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public Integer getIdLogradouro(){

		return idLogradouro;
	}

	public void setIdLogradouro(Integer idLogradouro){

		this.idLogradouro = idLogradouro;
	}

	public String getNumeroImovel(){

		return numeroImovel;
	}

	public void setNumeroImovel(String numeroImovel){

		this.numeroImovel = numeroImovel;
	}

	public Integer getIdBairro(){

		return idBairro;
	}

	public void setIdBairro(Integer idBairro){

		this.idBairro = idBairro;
	}

	public Integer getIdCep(){

		return idCep;
	}

	public void setIdCep(Integer idCep){

		this.idCep = idCep;
	}

	public String getNumeroHidormetro(){

		return numeroHidormetro;
	}

	public void setNumeroHidormetro(String numeroHidormetro){

		this.numeroHidormetro = numeroHidormetro;
	}

	public Integer getClienteRelacaoTipo(){

		return clienteRelacaoTipo;
	}

	public void setClienteRelacaoTipo(Integer clienteRelacaoTipo){

		this.clienteRelacaoTipo = clienteRelacaoTipo;
	}

	public Integer getEsferaPoder(){

		return esferaPoder;
	}

	public void setEsferaPoder(Integer esferaPoder){

		this.esferaPoder = esferaPoder;
	}

}
