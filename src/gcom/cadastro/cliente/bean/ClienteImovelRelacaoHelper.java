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

package gcom.cadastro.cliente.bean;

import gcom.util.Util;

import java.io.Serializable;
import java.util.Date;

/** @author Hibernate CodeGenerator */
public class ClienteImovelRelacaoHelper
				implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idCliente;

	private Integer idImovel;

	private String dataInicioRelacao;

	private String dataFimRelacao;

	private Integer idClienteRelacaoTipo;

	private String descricaoClienteRelacaoTipo;

	private Integer idClienteImovelFimRelacaoMotivo;

	private String descricaoClienteImovelFimRelacaoMotivo;

	public ClienteImovelRelacaoHelper(Integer idCliente, Integer idImovel, Date dataInicioRelacao, Date dataFimRelacao,
										Integer idClienteRelacaoTipo, String descricaoClienteRelacaoTipo,
										Integer idClienteImovelFimRelacaoMotivo, String descricaoClienteImovelFimRelacaoMotivo) {

		this.idCliente = idCliente;
		this.idImovel = idImovel;
		this.dataInicioRelacao = Util.formatarData(dataInicioRelacao);
		this.dataFimRelacao = Util.formatarData(dataFimRelacao);
		this.idClienteRelacaoTipo = idClienteRelacaoTipo;
		this.descricaoClienteRelacaoTipo = descricaoClienteRelacaoTipo;
		this.idClienteImovelFimRelacaoMotivo = idClienteImovelFimRelacaoMotivo;
		this.descricaoClienteImovelFimRelacaoMotivo = descricaoClienteImovelFimRelacaoMotivo;
	}

	public ClienteImovelRelacaoHelper() {

	}

	public Integer getIdCliente(){

		return idCliente;
	}

	public void setIdCliente(Integer idCliente){

		this.idCliente = idCliente;
	}

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public String getDataInicioRelacao(){

		return dataInicioRelacao;
	}

	public void setDataInicioRelacao(String dataInicioRelacao){

		this.dataInicioRelacao = dataInicioRelacao;
	}

	public String getDataFimRelacao(){

		return dataFimRelacao;
	}

	public void setDataFimRelacao(String dataFimRelacao){

		this.dataFimRelacao = dataFimRelacao;
	}

	public Integer getIdClienteRelacaoTipo(){

		return idClienteRelacaoTipo;
	}

	public void setIdClienteRelacaoTipo(Integer idClienteRelacaoTipo){

		this.idClienteRelacaoTipo = idClienteRelacaoTipo;
	}

	public String getDescricaoClienteRelacaoTipo(){

		return descricaoClienteRelacaoTipo;
	}

	public void setDescricaoClienteRelacaoTipo(String descricaoClienteRelacaoTipo){

		this.descricaoClienteRelacaoTipo = descricaoClienteRelacaoTipo;
	}

	public Integer getIdClienteImovelFimRelacaoMotivo(){

		return idClienteImovelFimRelacaoMotivo;
	}

	public void setIdClienteImovelFimRelacaoMotivo(Integer idClienteImovelFimRelacaoMotivo){

		this.idClienteImovelFimRelacaoMotivo = idClienteImovelFimRelacaoMotivo;
	}

	public String getDescricaoClienteImovelFimRelacaoMotivo(){

		return descricaoClienteImovelFimRelacaoMotivo;
	}

	public void setDescricaoClienteImovelFimRelacaoMotivo(String descricaoClienteImovelFimRelacaoMotivo){

		this.descricaoClienteImovelFimRelacaoMotivo = descricaoClienteImovelFimRelacaoMotivo;
	}
}
