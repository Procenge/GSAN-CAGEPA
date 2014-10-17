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

package gcom.gui.cadastro.endereco;

import org.apache.struts.action.ActionForm;

public class InserirCepActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	/**
	 * nullable persistent field
	 */
	private String codigo;

	/**
	 * nullable persistent field
	 */
	private String sigla;

	/**
	 * nullable persistent field
	 */
	private String municipio;

	/**
	 * nullable persistent field
	 */
	private String codigoMunicipio;

	/**
	 * nullable persistent field
	 */
	private String bairro;

	/**
	 * nullable persistent field
	 */
	private String codigoBairro;

	private String idBairro;

	/**
	 * nullable persistent field
	 */
	private String logradouro;

	private String codigoLogradouro;

	/**
	 * nullable persistent field
	 */
	private String descricaoTipoLogradouro;

	/**
	 * nullable persistent field
	 */
	private String localidade;

	private String codigoLocalidade;

	public String getCodigo(){

		return codigo;
	}

	public void setCodigo(String codigo){

		this.codigo = codigo;
	}

	public String getSigla(){

		return sigla;
	}

	public void setSigla(String sigla){

		this.sigla = sigla;
	}

	public String getMunicipio(){

		return municipio;
	}

	public void setMunicipio(String municipio){

		this.municipio = municipio;
	}

	public String getBairro(){

		return bairro;
	}

	public void setBairro(String bairro){

		this.bairro = bairro;
	}

	public String getLogradouro(){

		return logradouro;
	}

	public void setLogradouro(String logradouro){

		this.logradouro = logradouro;
	}

	public String getDescricaoTipoLogradouro(){

		return descricaoTipoLogradouro;
	}

	public void setDescricaoTipoLogradouro(String descricaoTipoLogradouro){

		this.descricaoTipoLogradouro = descricaoTipoLogradouro;
	}

	public String getCodigoMunicipio(){

		return codigoMunicipio;
	}

	public void setCodigoMunicipio(String codigoMunicipio){

		this.codigoMunicipio = codigoMunicipio;
	}

	public String getCodigoBairro(){

		return codigoBairro;
	}

	public void setCodigoBairro(String codigoBairro){

		this.codigoBairro = codigoBairro;
	}

	public String getCodigoLogradouro(){

		return codigoLogradouro;
	}

	public void setCodigoLogradouro(String codigoLogradouro){

		this.codigoLogradouro = codigoLogradouro;
	}

	public String getIdBairro(){

		return idBairro;
	}

	public void setIdBairro(String idBairro){

		this.idBairro = idBairro;
	}

	public String getLocalidade(){

		return localidade;
	}

	public void setLocalidade(String localidade){

		this.localidade = localidade;
	}

	public String getCodigoLocalidade(){

		return codigoLocalidade;
	}

	public void setCodigoLocalidade(String codigoLocalidade){

		this.codigoLocalidade = codigoLocalidade;
	}

}