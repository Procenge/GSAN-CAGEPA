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

package gcom.gui.cadastro.empresa;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorActionForm;

public class InserirEmpresaActionForm
				extends ValidatorActionForm {

	private String codigoEmpresa;

	private String nomeEmpresa;

	private String emailEmpresa;

	private String nomeEmpresaAbreviado;

	private String mensagemEmpresa;

	private String okEmpresa;

	private FormFile logoEmpresa;

	private static final long serialVersionUID = 1L;

	public String getCodigoEmpresa(){

		return codigoEmpresa;
	}

	public void setCodigoEmpresa(String codigoEmpresa){

		this.codigoEmpresa = codigoEmpresa;
	}

	public String getNomeEmpresa(){

		return nomeEmpresa;
	}

	public void setNomeEmpresa(String nomeEmpresa){

		this.nomeEmpresa = nomeEmpresa;
	}

	public String getEmailEmpresa(){

		return emailEmpresa;
	}

	public void setEmailEmpresa(String emailEmpresa){

		this.emailEmpresa = emailEmpresa;
	}

	public String getNomeEmpresaAbreviado(){

		return nomeEmpresaAbreviado;
	}

	public void setNomeEmpresaAbreviado(String nomeEmpresaAbreviado){

		this.nomeEmpresaAbreviado = nomeEmpresaAbreviado;
	}

	public String getMensagemEmpresa(){

		return mensagemEmpresa;
	}

	public void setMensagemEmpresa(String mensagemEmpresa){

		this.mensagemEmpresa = mensagemEmpresa;
	}

	public String getOkEmpresa(){

		return okEmpresa;
	}

	public void setOkEmpresa(String okEmpresa){

		this.okEmpresa = okEmpresa;
	}

	public FormFile getLogoEmpresa(){

		return logoEmpresa;
	}

	public void setLogoEmpresa(FormFile logoEmpresa){

		this.logoEmpresa = logoEmpresa;
	}

}