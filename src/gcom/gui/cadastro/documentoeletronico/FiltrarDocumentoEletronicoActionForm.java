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

package gcom.gui.cadastro.documentoeletronico;

import org.apache.struts.validator.ValidatorActionForm;

public class FiltrarDocumentoEletronicoActionForm
				extends ValidatorActionForm {

	private String idTipoDocumento;

	private String idImovel;

	private String inscricaoImovel;

	private String idCliente;

	private String nomeCliente;

	private String checkConsultar;

	private static final long serialVersionUID = 1L;

	public void setNomeCliente(String nomeCliente){

		this.nomeCliente = nomeCliente;
	}

	public String getNomeCliente(){

		return nomeCliente;
	}

	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}

	public String getInscricaoImovel(){

		return inscricaoImovel;
	}

	public void setIdCliente(String idCliente){

		this.idCliente = idCliente;
	}

	public String getIdCliente(){

		return idCliente;
	}

	public void setIdImovel(String idImovel){

		this.idImovel = idImovel;
	}

	public String getIdImovel(){

		return idImovel;
	}

	public String getIdTipoDocumento(){

		return idTipoDocumento;
	}

	public void setIdTipoDocumento(String idTipoDocumento){

		this.idTipoDocumento = idTipoDocumento;
	}

	public String getCheckConsultar(){

		return checkConsultar;
	}

	public void setCheckConsultar(String checkConsultar){

		this.checkConsultar = checkConsultar;
	}


}