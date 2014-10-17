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

package gcom.gui.cobranca.programacobranca;

import java.util.Date;

import org.apache.struts.validator.ValidatorActionForm;

public class AtualizarProgramaCobrancaActionForm
				extends ValidatorActionForm {

	private static final long serialVersionUID = 1L;

	private String codigoProgramaCobranca;

	private String nomeProgramaCobranca;

	private String descricaoProgramaCobranca;

	private String idCriterio;

	private String descricaoCriterio;

	private Date dataCriacao;

	private Date dataInicio;

	private Date dataEncerramento;

	private Date dataUltimoMovimento;

	private String indicadorUso;

	private Date dataUltimaAlteracao;

	public String getCodigoProgramaCobranca(){

		return codigoProgramaCobranca;
	}

	public void setCodigoProgramaCobranca(String codigoProgramaCobranca){

		this.codigoProgramaCobranca = codigoProgramaCobranca;
	}

	public String getNomeProgramaCobranca(){

		return nomeProgramaCobranca;
	}

	public void setNomeProgramaCobranca(String nomeProgramaCobranca){

		this.nomeProgramaCobranca = nomeProgramaCobranca;
	}

	public String getDescricaoProgramaCobranca(){

		return descricaoProgramaCobranca;
	}

	public void setDescricaoProgramaCobranca(String descricaoProgramaCobranca){

		this.descricaoProgramaCobranca = descricaoProgramaCobranca;
	}

	public String getIdCriterio(){

		return idCriterio;
	}

	public void setIdCriterio(String idCriterio){

		this.idCriterio = idCriterio;
	}

	public String getDescricaoCriterio(){

		return descricaoCriterio;
	}

	public void setDescricaoCriterio(String descricaoCriterio){

		this.descricaoCriterio = descricaoCriterio;
	}

	public Date getDataCriacao(){

		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao){

		this.dataCriacao = dataCriacao;
	}

	public Date getDataInicio(){

		return dataInicio;
	}

	public void setDataInicio(Date dataInicio){

		this.dataInicio = dataInicio;
	}

	public Date getDataEncerramento(){

		return dataEncerramento;
	}

	public void setDataEncerramento(Date dataEncerramento){

		this.dataEncerramento = dataEncerramento;
	}

	public Date getDataUltimoMovimento(){

		return dataUltimoMovimento;
	}

	public void setDataUltimoMovimento(Date dataUltimoMovimento){

		this.dataUltimoMovimento = dataUltimoMovimento;
	}

	public String getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(String indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public Date getDataUltimaAlteracao(){

		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Date dataUltimaAlteracao){

		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}

}