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

package gcom.gui.cadastro.entidadebeneficente;

import org.apache.struts.validator.ValidatorActionForm;

public class FiltrarEntidadeBeneficenteContratoActionForm
				extends ValidatorActionForm {

	private String id;

	private String idEntidadeBeneficente;

	private String nomeEntidadeBeneficente;

	private String numeroContrato;

	private String dataInicialInicioContrato;

	private String dataFinalInicioContrato;

	private String dataInicioFimContrato;

	private String dataFinalFimContrato;

	private String checkAtualizar;

	private static final long serialVersionUID = 1L;

	public String getCheckAtualizar(){

		return checkAtualizar;
	}

	public void setCheckAtualizar(String checkAtualizar){

		this.checkAtualizar = checkAtualizar;
	}

	public String getId(){

		return id;
	}

	public void setId(String id){

		this.id = id;
	}

	public String getIdEntidadeBeneficente(){

		return idEntidadeBeneficente;
	}

	public void setIdEntidadeBeneficente(String idEntidadeBeneficente){

		this.idEntidadeBeneficente = idEntidadeBeneficente;
	}

	public String getNomeEntidadeBeneficente(){

		return nomeEntidadeBeneficente;
	}

	public void setNomeEntidadeBeneficente(String nomeEntidadeBeneficente){

		this.nomeEntidadeBeneficente = nomeEntidadeBeneficente;
	}

	public String getNumeroContrato(){

		return numeroContrato;
	}

	public void setNumeroContrato(String numeroContrato){

		this.numeroContrato = numeroContrato;
	}

	public String getDataFinalInicioContrato(){

		return dataFinalInicioContrato;
	}

	public void setDataFinalInicioContrato(String dataFinalInicioContrato){

		this.dataFinalInicioContrato = dataFinalInicioContrato;
	}

	public String getDataInicioFimContrato(){

		return dataInicioFimContrato;
	}

	public void setDataInicioFimContrato(String dataInicioFimContrato){

		this.dataInicioFimContrato = dataInicioFimContrato;
	}

	public String getDataFinalFimContrato(){

		return dataFinalFimContrato;
	}

	public void setDataFinalFimContrato(String dataFinalFimContrato){

		this.dataFinalFimContrato = dataFinalFimContrato;
	}

	public String getDataInicialInicioContrato(){

		return dataInicialInicioContrato;
	}

	public void setDataInicialInicioContrato(String dataInicialInicioContrato){

		this.dataInicialInicioContrato = dataInicialInicioContrato;
	}

}