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

package gcom.atendimentopublico.ordemservico;

import gcom.cadastro.unidade.UnidadeOrganizacional;

import java.io.Serializable;
import java.util.Date;

public class ServicoAssociado
				implements Serializable {

	private static final long serialVersionUID = -3490968381486586026L;

	private ServicoAssociadoPK comp_id;

	private ServicoTipo servicoTipo;

	private ServicoTipo servicoTipoAssociado;

	private Integer sequencial;

	private EventoGeracao eventoGeracao;

	private FormaGeracao formaGeracao;

	private FormaTramite formaTramite;

	private UnidadeOrganizacional unidadeOrganizacional;

	private FormaEncerramento formaEncerramento;

	private FormaProgramacao formaProgramacao;

	private FormaSelecaoEquipe formaSelecaoEquipe;

	private Date ultimaAlteracao;

	private Short indicadorUso;

	public gcom.atendimentopublico.ordemservico.ServicoTipo getServicoTipo(){

		return servicoTipo;
	}

	public void setServicoTipo(gcom.atendimentopublico.ordemservico.ServicoTipo servicoTipo){

		this.servicoTipo = servicoTipo;
	}

	public gcom.atendimentopublico.ordemservico.ServicoTipo getServicoTipoAssociado(){

		return servicoTipoAssociado;
	}

	public void setServicoTipoAssociado(gcom.atendimentopublico.ordemservico.ServicoTipo servicoTipoAssociado){

		this.servicoTipoAssociado = servicoTipoAssociado;
	}

	public Integer getSequencial(){

		return sequencial;
	}

	public void setSequencial(Integer sequencial){

		this.sequencial = sequencial;
	}

	public EventoGeracao getEventoGeracao(){

		return eventoGeracao;
	}

	public void setEventoGeracao(EventoGeracao eventoGeracao){

		this.eventoGeracao = eventoGeracao;
	}

	public FormaGeracao getFormaGeracao(){

		return formaGeracao;
	}

	public void setFormaGeracao(FormaGeracao formaGeracao){

		this.formaGeracao = formaGeracao;
	}

	public FormaTramite getFormaTramite(){

		return formaTramite;
	}

	public void setFormaTramite(FormaTramite formaTramite){

		this.formaTramite = formaTramite;
	}

	public UnidadeOrganizacional getUnidadeOrganizacional(){

		return unidadeOrganizacional;
	}

	public void setUnidadeOrganizacional(UnidadeOrganizacional unidadeOrganizacional){

		this.unidadeOrganizacional = unidadeOrganizacional;
	}

	public FormaEncerramento getFormaEncerramento(){

		return formaEncerramento;
	}

	public void setFormaEncerramento(FormaEncerramento formaEncerramento){

		this.formaEncerramento = formaEncerramento;
	}

	public FormaProgramacao getFormaProgramacao(){

		return formaProgramacao;
	}

	public void setFormaProgramacao(FormaProgramacao formaProgramacao){

		this.formaProgramacao = formaProgramacao;
	}

	public FormaSelecaoEquipe getFormaSelecaoEquipe(){

		return formaSelecaoEquipe;
	}

	public void setFormaSelecaoEquipe(FormaSelecaoEquipe formaSelecaoEquipe){

		this.formaSelecaoEquipe = formaSelecaoEquipe;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Short getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public ServicoAssociadoPK getComp_id(){

		return comp_id;
	}

	public void setComp_id(ServicoAssociadoPK comp_id){

		this.comp_id = comp_id;
	}

	// public boolean equals(Object other) {
	// if ( (this == other ) ) return true;
	// if((other == null) || (other.getClass() != this.getClass())) return false;
	//
	// return new EqualsBuilder()
	// .append(this.getServicoTipo().getId(), ((ServicoAssociado)other).getServicoTipo().getId())
	// .append(this.getServicoTipoAssociado().getId(),
	// ((ServicoAssociado)other).getServicoTipoAssociado().getId())
	// .isEquals();
	// }
	//
	// public int hashCode() {
	// return new HashCodeBuilder()
	// .append(getServicoTipo().getId())
	// .append(getServicoTipoAssociado().getId())
	// .toHashCode();
	// }
}
