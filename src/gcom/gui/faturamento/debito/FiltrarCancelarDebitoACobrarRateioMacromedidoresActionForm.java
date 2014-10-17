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

package gcom.gui.faturamento.debito;

import gcom.faturamento.debito.HistoricoMedicaoIndividualizadaHelper;

import org.apache.struts.validator.ValidatorActionForm;

/**
 * Formulários de campos da tela Cancelar Debito A Cobrar Rateio Macromedidores.
 * 
 * @author Josenildo Neves
 * @date 12/08/2013
 */
public class FiltrarCancelarDebitoACobrarRateioMacromedidoresActionForm
				extends ValidatorActionForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String matriculaImovel;

	private String inscricaoImovel;

	private String mesAnoReferenciaFaturamento;

	private Integer idTipoLigacao;

	private String descricaoLigacaoAguaSituacao;

	private String descricaoLigacaoEsgotoSituacao;

	private String enderecoImovel;

	private String[] idDebitoACobrar;

	private HistoricoMedicaoIndividualizadaHelper historicoMedicaoIndividualizadaHelper;
	/**
	 * @return the matriculaImovel
	 */
	public String getMatriculaImovel(){

		return matriculaImovel;
	}


	/**
	 * @param matriculaImovel
	 *            the matriculaImovel to set
	 */
	public void setMatriculaImovel(String matriculaImovel){

		this.matriculaImovel = matriculaImovel;
	}


	/**
	 * @return the inscricaoImovel
	 */
	public String getInscricaoImovel(){

		return inscricaoImovel;
	}


	/**
	 * @param inscricaoImovel
	 *            the inscricaoImovel to set
	 */
	public void setInscricaoImovel(String inscricaoImovel){

		this.inscricaoImovel = inscricaoImovel;
	}


	/**
	 * @return the mesAnoReferenciaFaturamento
	 */
	public String getMesAnoReferenciaFaturamento(){

		return mesAnoReferenciaFaturamento;
	}


	/**
	 * @param mesAnoReferenciaFaturamento
	 *            the mesAnoReferenciaFaturamento to set
	 */
	public void setMesAnoReferenciaFaturamento(String mesAnoReferenciaFaturamento){

		this.mesAnoReferenciaFaturamento = mesAnoReferenciaFaturamento;
	}


	/**
	 * @return the idTipoLigacao
	 */
	public Integer getIdTipoLigacao(){

		return idTipoLigacao;
	}


	/**
	 * @param idTipoLigacao
	 *            the idTipoLigacao to set
	 */
	public void setIdTipoLigacao(Integer idTipoLigacao){

		this.idTipoLigacao = idTipoLigacao;
	}

	/**
	 * @return the descricaoLigacaoAguaSituacao
	 */
	public String getDescricaoLigacaoAguaSituacao(){

		return descricaoLigacaoAguaSituacao;
	}

	/**
	 * @param descricaoLigacaoAguaSituacao
	 *            the descricaoLigacaoAguaSituacao to set
	 */
	public void setDescricaoLigacaoAguaSituacao(String descricaoLigacaoAguaSituacao){

		this.descricaoLigacaoAguaSituacao = descricaoLigacaoAguaSituacao;
	}

	/**
	 * @return the descricaoLigacaoEsgotoSituacao
	 */
	public String getDescricaoLigacaoEsgotoSituacao(){

		return descricaoLigacaoEsgotoSituacao;
	}

	/**
	 * @param descricaoLigacaoEsgotoSituacao
	 *            the descricaoLigacaoEsgotoSituacao to set
	 */
	public void setDescricaoLigacaoEsgotoSituacao(String descricaoLigacaoEsgotoSituacao){

		this.descricaoLigacaoEsgotoSituacao = descricaoLigacaoEsgotoSituacao;
	}

	/**
	 * @return the enderecoImovel
	 */
	public String getEnderecoImovel(){

		return enderecoImovel;
	}

	/**
	 * @param enderecoImovel
	 *            the enderecoImovel to set
	 */
	public void setEnderecoImovel(String enderecoImovel){

		this.enderecoImovel = enderecoImovel;
	}

	/**
	 * @return the historicoMedicaoIndividualizadaHelper
	 */
	public HistoricoMedicaoIndividualizadaHelper getHistoricoMedicaoIndividualizadaHelper(){

		return historicoMedicaoIndividualizadaHelper;
	}

	/**
	 * @param historicoMedicaoIndividualizadaHelper
	 *            the historicoMedicaoIndividualizadaHelper to set
	 */
	public void setHistoricoMedicaoIndividualizadaHelper(HistoricoMedicaoIndividualizadaHelper historicoMedicaoIndividualizadaHelper){

		this.historicoMedicaoIndividualizadaHelper = historicoMedicaoIndividualizadaHelper;
	}

	/**
	 * @return the idDebitoACobrar
	 */
	public String[] getIdDebitoACobrar(){

		return idDebitoACobrar;
	}

	/**
	 * @param idDebitoACobrar
	 *            the idDebitoACobrar to set
	 */
	public void setIdDebitoACobrar(String[] idDebitoACobrar){

		this.idDebitoACobrar = idDebitoACobrar;
	}

}
