/**
 * 
 */
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

package gcom.micromedicao.bean;

/**
 * Coleção dos dados do Consumo Historico da Medicao Individualizada
 * 
 * @author Rafael Santos
 * @since 19/01/2006
 */
public class ConsultarHistoricoMedicaoIndividualizadaHelper {

	private String matriculaImovel;

	private String nomeClienteUsuario;

	private String tipoConsumo;

	private String consumoAguaMedido;

	private String consumoAguaFaturado;

	private String consumoEsgoto;

	private String consumoRateio;

	/**
	 * @return Returns the consumoAguaFaturado.
	 */
	public String getConsumoAguaFaturado(){

		return consumoAguaFaturado;
	}

	/**
	 * @param consumoAguaFaturado
	 *            The consumoAguaFaturado to set.
	 */
	public void setConsumoAguaFaturado(String consumoAguaFaturado){

		this.consumoAguaFaturado = consumoAguaFaturado;
	}

	/**
	 * @return Returns the consumoAguaMedido.
	 */
	public String getConsumoAguaMedido(){

		return consumoAguaMedido;
	}

	/**
	 * @param consumoAguaMedido
	 *            The consumoAguaMedido to set.
	 */
	public void setConsumoAguaMedido(String consumoAguaMedido){

		this.consumoAguaMedido = consumoAguaMedido;
	}

	/**
	 * @return Returns the consumoEsgoto.
	 */
	public String getConsumoEsgoto(){

		return consumoEsgoto;
	}

	/**
	 * @param consumoEsgoto
	 *            The consumoEsgoto to set.
	 */
	public void setConsumoEsgoto(String consumoEsgoto){

		this.consumoEsgoto = consumoEsgoto;
	}

	/**
	 * @return Returns the consumoRateio.
	 */
	public String getConsumoRateio(){

		return consumoRateio;
	}

	/**
	 * @param consumoRateio
	 *            The consumoRateio to set.
	 */
	public void setConsumoRateio(String consumoRateio){

		this.consumoRateio = consumoRateio;
	}

	/**
	 * @return Returns the matriculaImovel.
	 */
	public String getMatriculaImovel(){

		return matriculaImovel;
	}

	/**
	 * @param matriculaImovel
	 *            The matriculaImovel to set.
	 */
	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}

	/**
	 * @return Returns the nomeClienteUsuario.
	 */
	public String getNomeClienteUsuario(){

		return nomeClienteUsuario;
	}

	/**
	 * @param nomeClienteUsuario
	 *            The nomeClienteUsuario to set.
	 */
	public void setNomeClienteUsuario(String nomeClienteUsuario){

		this.nomeClienteUsuario = nomeClienteUsuario;
	}

	/**
	 * @return Returns the tipoConsumo.
	 */
	public String getTipoConsumo(){

		return tipoConsumo;
	}

	/**
	 * @param tipoConsumo
	 *            The tipoConsumo to set.
	 */
	public void setTipoConsumo(String tipoConsumo){

		this.tipoConsumo = tipoConsumo;
	}

	/**
	 * 
	 */
	public ConsultarHistoricoMedicaoIndividualizadaHelper() {

		super();
		// TODO Auto-generated constructor stub
	}

}
