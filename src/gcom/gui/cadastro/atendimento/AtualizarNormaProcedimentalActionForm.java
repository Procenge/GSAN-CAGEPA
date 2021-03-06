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

package gcom.gui.cadastro.atendimento;

import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.ValidatorActionForm;

public class AtualizarNormaProcedimentalActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String codigoNormaProcedimental;

	private String tituloNormaProcedimental;

	private String mensagemNormaProcedimental;

	private String okNormaProcedimental;

	private FormFile conteudoNormaProcedimental;

	private String indicadorUso;

	public String getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public String getCodigoNormaProcedimental(){

		return codigoNormaProcedimental;
	}

	public void setCodigoNormaProcedimental(String codigoNormaProcedimental){

		this.codigoNormaProcedimental = codigoNormaProcedimental;
	}

	public String getTituloNormaProcedimental(){

		return tituloNormaProcedimental;
	}

	public void setTituloNormaProcedimental(String tituloNormaProcedimental){

		this.tituloNormaProcedimental = tituloNormaProcedimental;
	}

	public String getMensagemNormaProcedimental(){

		return mensagemNormaProcedimental;
	}

	public void setMensagemNormaProcedimental(String mensagemNormaProcedimental){

		this.mensagemNormaProcedimental = mensagemNormaProcedimental;
	}

	public String getOkNormaProcedimental(){

		return okNormaProcedimental;
	}

	public void setOkNormaProcedimental(String okNormaProcedimental){

		this.okNormaProcedimental = okNormaProcedimental;
	}

	public FormFile getConteudoNormaProcedimental(){

		return conteudoNormaProcedimental;
	}

	public void setConteudoNormaProcedimental(FormFile conteudoNormaProcedimental){

		this.conteudoNormaProcedimental = conteudoNormaProcedimental;
	}

}