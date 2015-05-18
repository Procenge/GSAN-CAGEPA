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
 *
 * GSANPCG
 * Virgínia Melo
 *
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

package gcom.gui.cadastro.entidadebeneficente;

import org.apache.struts.validator.ValidatorActionForm;

public class AtualizarEntidadeBeneficenteContratoActionForm
				extends ValidatorActionForm {

	private String id;

	private String idEntidadeBeneficente;

	private String nomeEntidadeBeneficente;

	private String numeroContrato;

	private String dataInicioContrato;

	private String dataFimContrato;

	private String dataEncerramento;

	private String percentualRemuneracao;

	private String valorRemuneracao;

	private String valorMinimoDoacao;

	private String valorMaximoDoacao;

	private static final long serialVersionUID = 1L;

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

	public String getDataInicioContrato(){

		return dataInicioContrato;
	}

	public void setDataInicioContrato(String dataInicioContrato){

		this.dataInicioContrato = dataInicioContrato;
	}

	public String getDataEncerramento(){

		return dataEncerramento;
	}

	public void setDataEncerramento(String dataEncerramento){

		this.dataEncerramento = dataEncerramento;
	}

	public String getPercentualRemuneracao(){

		return percentualRemuneracao;
	}

	public void setPercentualRemuneracao(String percentualRemuneracao){

		this.percentualRemuneracao = percentualRemuneracao;
	}

	public String getValorRemuneracao(){

		return valorRemuneracao;
	}

	public void setValorRemuneracao(String valorRemuneracao){

		this.valorRemuneracao = valorRemuneracao;
	}

	public String getValorMinimoDoacao(){

		return valorMinimoDoacao;
	}

	public void setValorMinimoDoacao(String valorMinimoDoacao){

		this.valorMinimoDoacao = valorMinimoDoacao;
	}

	public String getValorMaximoDoacao(){

		return valorMaximoDoacao;
	}

	public void setValorMaximoDoacao(String valorMaximoDoacao){

		this.valorMaximoDoacao = valorMaximoDoacao;
	}

	public String getDataFimContrato(){

		return dataFimContrato;
	}

	public void setDataFimContrato(String dataFimContrato){

		this.dataFimContrato = dataFimContrato;
	}
}