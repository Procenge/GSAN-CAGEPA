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

package gcom.relatorio.operacional.subsistemaesgoto;

import gcom.relatorio.RelatorioBean;

/**
 * Bean responsável de pegar os parametros que serão exibidos na parte de detail do relatório.
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
