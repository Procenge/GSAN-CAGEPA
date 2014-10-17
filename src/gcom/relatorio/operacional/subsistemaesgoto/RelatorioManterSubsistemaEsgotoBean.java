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
 * Adriano Britto Siqueira
 * Alexandre Santos Cabral
 * Ana Carolina Alves Breda
 * Ana Maria Andrade Cavalcante
 * Aryed Lins de Ara�jo
 * Bruno Leonardo Rodrigues Barros
 * Carlos Elmano Rodrigues Ferreira
 * Cl�udio de Andrade Lira
 * Denys Guimar�es Guenes Tavares
 * Eduardo Breckenfeld da Rosa Borges
 * Fab�ola Gomes de Ara�jo
 * Fl�vio Leonardo Cavalcanti Cordeiro
 * Francisco do Nascimento J�nior
 * Homero Sampaio Cavalcanti
 * Ivan S�rgio da Silva J�nior
 * Jos� Edmar de Siqueira
 * Jos� Thiago Ten�rio Lopes
 * K�ssia Regina Silvestre de Albuquerque
 * Leonardo Luiz Vieira da Silva
 * M�rcio Roberto Batista da Silva
 * Maria de F�tima Sampaio Leite
 * Micaela Maria Coelho de Ara�jo
 * Nelson Mendon�a de Carvalho
 * Newton Morais e Silva
 * Pedro Alexandre Santos da Silva Filho
 * Rafael Corr�a Lima e Silva
 * Rafael Francisco Pinto
 * Rafael Koury Monteiro
 * Rafael Palermo de Ara�jo
 * Raphael Veras Rossiter
 * Roberto Sobreira Barbalho
 * Rodrigo Avellar Silveira
 * Rosana Carvalho Barbosa
 * S�vio Luiz de Andrade Cavalcante
 * Tai Mu Shih
 * Thiago Augusto Souza do Nascimento
 * Tiago Moreno Rodrigues
 * Vivianne Barbosa Sousa
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

package gcom.relatorio.operacional.subsistemaesgoto;

import gcom.relatorio.RelatorioBean;

/**
 * Bean respons�vel de pegar os parametros que ser�o exibidos na parte de detail do relat�rio.
 * 
 * @author Ailton Sousa
 * @created 31 de Janeiro de 2011
 */
public class RelatorioManterSubsistemaEsgotoBean
				implements RelatorioBean {

	private String codigo;

	private String descricao;

	private String descricaoSistemaEsgoto;

	private String indicadorUso;

	/**
	 * Construtor da classe RelatorioManterSubsistemaEsgotoBean
	 * 
	 * @param codigo
	 *            Description of the Parameter
	 * @param descricao
	 *            Description of the Parameter
	 * @param sistemaEsgoto
	 *            Description of the Parameter
	 * @param indicadorUso
	 *            Description of the Parameter
	 */
	public RelatorioManterSubsistemaEsgotoBean(String codigo, String descricao, String sistemaEsgoto, String indicadorUso) {

		this.codigo = codigo;
		this.descricao = descricao;
		this.descricaoSistemaEsgoto = sistemaEsgoto;
		this.indicadorUso = indicadorUso;

	}

	/**
	 * Gets the codigo attribute of the RelatorioManterSubsistemaEsgotoBean object
	 * 
	 * @return The codigo value
	 */
	public String getCodigoSubsistemaEsgoto(){

		return codigo;
	}

	/**
	 * Sets the codigo attribute of the RelatorioManterSubsistemaEsgotoBean object
	 * 
	 * @param codigo
	 *            The new codigo value
	 */
	public void setCodigoSubsistemaEsgoto(String codigo){

		this.codigo = codigo;
	}

	/**
	 * Gets the municicio attribute of the RelatorioManterSubsistemaEsgotoBean object
	 * 
	 * @return The municicio value
	 */
	public String getDescricaoSistemaEsgoto(){

		return descricaoSistemaEsgoto;
	}

	/**
	 * Sets the municicio attribute of the RelatorioManterSubsistemaEsgotoBean object
	 * 
	 * @param sistemaEsgoto
	 *            The new municipio value
	 */
	public void setDescricaoSistemaEsgoto(String descricaoSistemaEsgoto){

		this.descricaoSistemaEsgoto = descricaoSistemaEsgoto;
	}

	/**
	 * Gets the nome attribute of the RelatorioManterSubsistemaEsgotoBean object
	 * 
	 * @return The nome value
	 */
	public String getDescricaoSubsistemaEsgoto(){

		return descricao;
	}

	/**
	 * Sets the nome attribute of the RelatorioManterSubsistemaEsgotoBean object
	 * 
	 * @param nome
	 *            The new nome value
	 */
	public void setDescricaoSubsistemaEsgoto(String descricao){

		this.descricao = descricao;
	}

	/**
	 * Gets the indicadorUso attribute of the RelatorioManterSubsistemaEsgotoBean object
	 * 
	 * @return The indicadorUso value
	 */
	public String getIndicadorUso(){

		return indicadorUso;
	}

	/**
	 * Sets the indicadorUso attribute of the RelatorioManterSubsistemaEsgotoBean object
	 * 
	 * @param indicadorUso
	 *            The new indicadorUso value
	 */
	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

}
