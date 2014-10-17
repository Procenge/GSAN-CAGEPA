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
package gcom.cobranca.bean;

import gcom.cobranca.CobrancaDocumento;

/**
 * Classe helper que foi criada para itens de ordenação de cobrança de documentos.
 */
public class CobrancaDocumentoAvisoCorteHelper {

	public static final int ENTREGA_CLIENTE_RESPONSAVEL = 1;

	public static final int ENTREGA_CLIENTE_USUARIO = 2;

	public static final int ENTREGA_CLIENTE_PROPRIETARIO = 3;

	public static final int ENTREGA_IMOVEL = 4;

	/** Campos de Ordenação. */
	private Integer idEmpresa;

	/** Genérico pois pode ser populado de Responsável, Usuário, Proprietário ou NADA == (Nulo) */
	private Integer idClienteGenerico;

	private Integer idLocalidade;

	private Integer idSetor;

	private Integer numeroQuadra;

	private short numeroLote;

	private short numeroSubLote;

	/** Genérico pois pode ser populado de endereço do imóvel ou do responsável. */
	private String enderecoGenerico;

	private int idTipoLista;
	/** Fim */

	private CobrancaDocumento cobrancaDocumento;

	/**
	 * Construtor para campos de ordenação, mais o campo cobrança documento a ser manipulado.
	 * 
	 * @param idEmpresa
	 * @param idClienteGenerico
	 * @param idLocalidade
	 * @param idSetor
	 * @param numeroQuadra
	 * @param numeroLote
	 * @param numeroSubLote
	 * @param enderecoGenerico
	 * @param cobrancaDocumento
	 */
	public CobrancaDocumentoAvisoCorteHelper(Integer idEmpresa, Integer idClienteGenerico, Integer idLocalidade, Integer idSetor,
												Integer numeroQuadra, short numeroLote, short numeroSubLote, String enderecoGenerico,
												int idTipoLista, CobrancaDocumento cobrancaDocumento) {

		super();

		// Itens de ordenação.
		this.idEmpresa = idEmpresa == null ? new Integer(0) : idEmpresa;
		this.idClienteGenerico = idClienteGenerico == null ? new Integer(0) : idClienteGenerico;
		this.idLocalidade = idLocalidade == null ? new Integer(0) : idLocalidade;
		this.idSetor = idSetor == null ? new Integer(0) : idSetor;
		this.numeroQuadra = numeroQuadra == null ? new Integer(0) : numeroQuadra;
		this.numeroLote = numeroLote;
		this.numeroSubLote = numeroSubLote;
		this.enderecoGenerico = enderecoGenerico == null ? "" : enderecoGenerico;
		// Fim itens de ordenação

		this.idTipoLista = idTipoLista;
		this.cobrancaDocumento = cobrancaDocumento;
	}

	/**
	 * Construtor default.
	 */
	public CobrancaDocumentoAvisoCorteHelper() {

	}

	/**
	 * @return the idEmpresa
	 */
	public Integer getIdEmpresa(){

		return idEmpresa;
	}

	/**
	 * @param idEmpresa the idEmpresa to set
	 */
	public void setIdEmpresa(Integer idEmpresa){

		this.idEmpresa = idEmpresa;
	}

	/**
	 * @return the idClienteGenerico
	 */
	public Integer getIdClienteGenerico(){

		return idClienteGenerico;
	}

	/**
	 * @param idClienteGenerico the idClienteGenerico to set
	 */
	public void setIdClienteGenerico(Integer idClienteGenerico){

		this.idClienteGenerico = idClienteGenerico;
	}

	/**
	 * @return the idLocalidade
	 */
	public Integer getIdLocalidade(){

		return idLocalidade;
	}

	/**
	 * @param idLocalidade the idLocalidade to set
	 */
	public void setIdLocalidade(Integer idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	/**
	 * @return the idSetor
	 */
	public Integer getIdSetor(){

		return idSetor;
	}

	/**
	 * @param idSetor the idSetor to set
	 */
	public void setIdSetor(Integer idSetor){

		this.idSetor = idSetor;
	}

	/**
	 * @return the numeroQuadra
	 */
	public Integer getNumeroQuadra(){

		return numeroQuadra;
	}

	/**
	 * @param numeroQuadra the numeroQuadra to set
	 */
	public void setNumeroQuadra(Integer numeroQuadra){

		this.numeroQuadra = numeroQuadra;
	}

	/**
	 * @return the numeroLote
	 */
	public short getNumeroLote(){

		return numeroLote;
	}

	/**
	 * @param numeroLote the numeroLote to set
	 */
	public void setNumeroLote(short numeroLote){

		this.numeroLote = numeroLote;
	}

	/**
	 * @return the numeroSubLote
	 */
	public short getNumeroSubLote(){

		return numeroSubLote;
	}

	/**
	 * @param numeroSubLote the numeroSubLote to set
	 */
	public void setNumeroSubLote(short numeroSubLote){

		this.numeroSubLote = numeroSubLote;
	}

	/**
	 * @return the enderecoGenerico
	 */
	public String getEnderecoGenerico(){

		return enderecoGenerico;
	}

	/**
	 * @param enderecoGenerico the enderecoGenerico to set
	 */
	public void setEnderecoGenerico(String enderecoGenerico){

		this.enderecoGenerico = enderecoGenerico;
	}

	/**
	 * @return the cobrancaDocumento
	 */
	public CobrancaDocumento getCobrancaDocumento(){

		return cobrancaDocumento;
	}

	/**
	 * @param cobrancaDocumento the cobrancaDocumento to set
	 */
	public void setCobrancaDocumento(CobrancaDocumento cobrancaDocumento){

		this.cobrancaDocumento = cobrancaDocumento;
	}

	public int getIdTipoLista(){

		return idTipoLista;
	}

	public void setIdTipoLista(int idTipoLista){

		this.idTipoLista = idTipoLista;
	}

}
