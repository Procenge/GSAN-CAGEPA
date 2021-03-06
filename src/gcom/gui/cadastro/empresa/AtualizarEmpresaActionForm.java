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

package gcom.gui.cadastro.empresa;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorActionForm;

public class AtualizarEmpresaActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String codigoEmpresa;

	private String nomeEmpresa;

	private String emailEmpresa;

	private String nomeEmpresaAbreviado;

	private String mensagemEmpresa;

	private String okEmpresa;

	private FormFile logoEmpresa;

	private String indicadorUso;

	public String getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

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