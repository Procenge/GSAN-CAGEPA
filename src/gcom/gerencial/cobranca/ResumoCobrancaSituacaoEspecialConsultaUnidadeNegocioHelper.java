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

package gcom.gerencial.cobranca;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * @author @Pitang
 * @date 18/07/2012
 */
public class ResumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper {

	private Collection<ResumoCobrancaSituacaoEspecialConsultaLocalidadeHelper> resumoCobrancaSituacaoEspecialConsultaLocalidadeHelper;

	private Integer totalUnidadeNegocio;

	private BigDecimal totalPercentualUnidadeNegocio;

	private BigDecimal totalFatEstimadoUnidadeNegocio;

	private Integer totalQtLigacoesUnidadeNegocio;

	private Integer idUnidadeNegocio;

	private String dsUnidadeNegocio;

	public Collection<ResumoCobrancaSituacaoEspecialConsultaLocalidadeHelper> getResumoCobrancaSituacaoEspecialConsultaLocalidadeHelper(){

		return resumoCobrancaSituacaoEspecialConsultaLocalidadeHelper;
	}

	public void setResumoCobrancaSituacaoEspecialConsultaLocalidadeHelper(
					Collection<ResumoCobrancaSituacaoEspecialConsultaLocalidadeHelper> resumoCobrancaSituacaoEspecialConsultaLocalidadeHelper){

		this.resumoCobrancaSituacaoEspecialConsultaLocalidadeHelper = resumoCobrancaSituacaoEspecialConsultaLocalidadeHelper;
	}

	public ResumoCobrancaSituacaoEspecialConsultaUnidadeNegocioHelper(Integer idUnidadeNegocio, String dsUnidadeNegocio,
																		Integer totalUnidadeNegocio) {

		super();
		this.idUnidadeNegocio = idUnidadeNegocio;
		this.dsUnidadeNegocio = dsUnidadeNegocio;
		this.totalUnidadeNegocio = totalUnidadeNegocio;
	}

	public Integer getTotalUnidadeNegocio(){

		return totalUnidadeNegocio;
	}

	public void setTotalUnidadeNegocio(Integer totalUnidadeNegocio){

		this.totalUnidadeNegocio = totalUnidadeNegocio;
	}

	public BigDecimal getTotalPercentualUnidadeNegocio(){

		return totalPercentualUnidadeNegocio;
	}

	public void setTotalPercentualUnidadeNegocio(BigDecimal totalPercentualUnidadeNegocio){

		this.totalPercentualUnidadeNegocio = totalPercentualUnidadeNegocio;
	}

	public BigDecimal getTotalFatEstimadoUnidadeNegocio(){

		return totalFatEstimadoUnidadeNegocio;
	}

	public void setTotalFatEstimadoUnidadeNegocio(BigDecimal totalFatEstimadoUnidadeNegocio){

		this.totalFatEstimadoUnidadeNegocio = totalFatEstimadoUnidadeNegocio;
	}

	public Integer getTotalQtLigacoesUnidadeNegocio(){

		return totalQtLigacoesUnidadeNegocio;
	}

	public void setTotalQtLigacoesUnidadeNegocio(Integer totalQtLigacoesUnidadeNegocio){

		this.totalQtLigacoesUnidadeNegocio = totalQtLigacoesUnidadeNegocio;
	}

	public Integer getIdUnidadeNegocio(){

		return idUnidadeNegocio;
	}

	public void setIdUnidadeNegocio(Integer idUnidadeNegocio){

		this.idUnidadeNegocio = idUnidadeNegocio;
	}

	public String getDsUnidadeNegocio(){

		return dsUnidadeNegocio;
	}

	public void setDsUnidadeNegocio(String dsUnidadeNegocio){

		this.dsUnidadeNegocio = dsUnidadeNegocio;
	}

}