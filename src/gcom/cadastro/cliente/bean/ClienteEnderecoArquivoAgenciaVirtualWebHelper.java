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

package gcom.cadastro.cliente.bean;

/**
 * Gerar Arquivo Agencia Virtual WEB
 * 
 * @author Josenildo Neves
 * @date 16/05/2012
 */
public class ClienteEnderecoArquivoAgenciaVirtualWebHelper {

	private Integer ClienteEnderecoId;

	private Integer logradouroId;

	private Integer logradouroBairroId;

	private Integer logradouroCepId;

	private String ClienteEnderecoNumeroImovel;

	public ClienteEnderecoArquivoAgenciaVirtualWebHelper() {

		super();
	}

	public ClienteEnderecoArquivoAgenciaVirtualWebHelper(Integer clienteEnderecoId, Integer logradouroId, Integer logradouroBairroId,
															Integer logradouroCepId, String clienteEnderecoNumeroImovel) {

		super();
		ClienteEnderecoId = clienteEnderecoId;
		this.logradouroId = logradouroId;
		this.logradouroBairroId = logradouroBairroId;
		this.logradouroCepId = logradouroCepId;
		ClienteEnderecoNumeroImovel = clienteEnderecoNumeroImovel;
	}

	public Integer getClienteEnderecoId(){

		return ClienteEnderecoId;
	}

	public void setClienteEnderecoId(Integer clienteEnderecoId){

		ClienteEnderecoId = clienteEnderecoId;
	}

	public Integer getLogradouroId(){

		return logradouroId;
	}

	public void setLogradouroId(Integer logradouroId){

		this.logradouroId = logradouroId;
	}

	public Integer getLogradouroBairroId(){

		return logradouroBairroId;
	}

	public void setLogradouroBairroId(Integer logradouroBairroId){

		this.logradouroBairroId = logradouroBairroId;
	}

	public Integer getLogradouroCepId(){

		return logradouroCepId;
	}

	public void setLogradouroCepId(Integer logradouroCepId){

		this.logradouroCepId = logradouroCepId;
	}

	public String getClienteEnderecoNumeroImovel(){

		return ClienteEnderecoNumeroImovel;
	}

	public void setClienteEnderecoNumeroImovel(String clienteEnderecoNumeroImovel){

		ClienteEnderecoNumeroImovel = clienteEnderecoNumeroImovel;
	}

}
