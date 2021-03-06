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

package gcom.gui.micromedicao;

import org.apache.struts.action.ActionForm;

/**
 * Form utilizado no Filtrar Roteiro Empresa
 * 
 * @author Francisco Nascimento
 * @created 10/08/2007
 */
public class FiltrarRoteiroEmpresaActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String empresa;

	private String idLocalidade;

	private String descricaoLocalidade;

	private String idLeiturista;

	private String nomeLeiturista;

	private String idSetorComercial;

	private String descricaoSetorComercial;

	private String indicadorUso;

	private String atualizar;

	public String getAtualizar(){

		return atualizar;
	}

	public void setAtualizar(String atualizar){

		this.atualizar = atualizar;
	}

	/**
	 * @return Returns the descricaoLocalidade.
	 */
	public String getDescricaoLocalidade(){

		return descricaoLocalidade;
	}

	/**
	 * @param descricaoLocalidade
	 *            The descricaoLocalidade to set.
	 */
	public void setDescricaoLocalidade(String descricaoLocalidade){

		this.descricaoLocalidade = descricaoLocalidade;
	}

	/**
	 * @return Returns the descricaoSetorComercial.
	 */
	public String getDescricaoSetorComercial(){

		return descricaoSetorComercial;
	}

	/**
	 * @param descricaoSetorComercial
	 *            The descricaoSetorComercial to set.
	 */
	public void setDescricaoSetorComercial(String descricaoSetorComercial){

		this.descricaoSetorComercial = descricaoSetorComercial;
	}

	/**
	 * @return Returns the empresa.
	 */
	public String getEmpresa(){

		return empresa;
	}

	/**
	 * @param empresa
	 *            The empresa to set.
	 */
	public void setEmpresa(String empresa){

		this.empresa = empresa;
	}

	/**
	 * @return Returns the idLocalidade.
	 */
	public String getIdLocalidade(){

		return idLocalidade;
	}

	/**
	 * @param idLocalidade
	 *            The idLocalidade to set.
	 */
	public void setIdLocalidade(String idLocalidade){

		this.idLocalidade = idLocalidade;
	}

	/**
	 * @return Returns the idSetorComercial.
	 */
	public String getIdSetorComercial(){

		return idSetorComercial;
	}

	/**
	 * @param idSetorComercial
	 *            The idSetorComercial to set.
	 */
	public void setIdSetorComercial(String idSetorComercial){

		this.idSetorComercial = idSetorComercial;
	}

	/**
	 * @return Returns the indicadorUso.
	 */
	public String getIndicadorUso(){

		return indicadorUso;
	}

	/**
	 * @param indicadorUso
	 *            The indicadorUso to set.
	 */
	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	/**
	 * @return Returns the idLeiturista.
	 */
	public String getIdLeiturista(){

		return idLeiturista;
	}

	/**
	 * @param idLeiturista
	 *            The idLeiturista to set.
	 */
	public void setIdLeiturista(String idLeiturista){

		this.idLeiturista = idLeiturista;
	}

	/**
	 * @return Returns the nomeLeiturista.
	 */
	public String getNomeLeiturista(){

		return nomeLeiturista;
	}

	/**
	 * @param nomeLeiturista
	 *            The nomeLeiturista to set.
	 */
	public void setNomeLeiturista(String nomeLeiturista){

		this.nomeLeiturista = nomeLeiturista;
	}

}