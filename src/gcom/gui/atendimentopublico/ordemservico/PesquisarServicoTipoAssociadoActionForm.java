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
 * Saulo Lima
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

package gcom.gui.atendimentopublico.ordemservico;

import gcom.atendimentopublico.ordemservico.ServicoAssociado;

import org.apache.struts.action.ActionForm;

public class PesquisarServicoTipoAssociadoActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	private String idServicoTipo;

	private String descricaoServicoTipo;

	private String sequencial;

	private String idEventoGeracao;

	private String idFormaGeracao;

	private String idFormaTramite;

	private String idUnidadeDestino;

	private String descricaoUnidadeDestino;

	private String idFormaEncerramento;

	private String idFormaProgramacao;

	private String idFormaSelecaoEquipe;

	public String getIdServicoTipo(){

		return idServicoTipo;
	}

	public void setIdServicoTipo(String idServicoTipo){

		this.idServicoTipo = idServicoTipo;
	}

	public String getDescricaoServicoTipo(){

		return descricaoServicoTipo;
	}

	public void setDescricaoServicoTipo(String descricaoServicoTipo){

		this.descricaoServicoTipo = descricaoServicoTipo;
	}

	public String getSequencial(){

		return sequencial;
	}

	public void setSequencial(String sequencial){

		this.sequencial = sequencial;
	}

	public String getIdEventoGeracao(){

		return idEventoGeracao;
	}

	public void setIdEventoGeracao(String idEventoGeracao){

		this.idEventoGeracao = idEventoGeracao;
	}

	public String getIdFormaGeracao(){

		return idFormaGeracao;
	}

	public void setIdFormaGeracao(String idFormaGeracao){

		this.idFormaGeracao = idFormaGeracao;
	}

	public String getIdFormaTramite(){

		return idFormaTramite;
	}

	public void setIdFormaTramite(String idFormaTramite){

		this.idFormaTramite = idFormaTramite;
	}

	public String getIdUnidadeDestino(){

		return idUnidadeDestino;
	}

	public void setIdUnidadeDestino(String idUnidadeDestino){

		this.idUnidadeDestino = idUnidadeDestino;
	}

	public String getDescricaoUnidadeDestino(){

		return descricaoUnidadeDestino;
	}

	public void setDescricaoUnidadeDestino(String descricaoUnidadeDestino){

		this.descricaoUnidadeDestino = descricaoUnidadeDestino;
	}

	public String getIdFormaEncerramento(){

		return idFormaEncerramento;
	}

	public void setIdFormaEncerramento(String idFormaEncerramento){

		this.idFormaEncerramento = idFormaEncerramento;
	}

	public String getIdFormaProgramacao(){

		return idFormaProgramacao;
	}

	public void setIdFormaProgramacao(String idFormaProgramacao){

		this.idFormaProgramacao = idFormaProgramacao;
	}

	public String getIdFormaSelecaoEquipe(){

		return idFormaSelecaoEquipe;
	}

	public void setIdFormaSelecaoEquipe(String idFormaSelecaoEquipe){

		this.idFormaSelecaoEquipe = idFormaSelecaoEquipe;
	}

	public void limparForm(){

		this.setIdServicoTipo("");
		this.setDescricaoServicoTipo("");
		this.setSequencial("");
		this.setIdEventoGeracao("-1");
		this.setIdFormaGeracao("-1");
		this.setIdFormaTramite("-1");
		this.setIdUnidadeDestino("");
		this.setDescricaoUnidadeDestino("");
		this.setIdFormaEncerramento("-1");
		this.setIdFormaProgramacao("-1");
		this.setIdFormaSelecaoEquipe("-1");
	}

	public void preencherForm(ServicoAssociado servicoAssociado){

		this.setIdServicoTipo(servicoAssociado.getServicoTipoAssociado().getId().toString());
		this.setDescricaoServicoTipo(servicoAssociado.getServicoTipoAssociado().getDescricao().toString());

		if(this.getSequencial() == null){
			this.setSequencial(servicoAssociado.getSequencial().toString());
		}
		if(this.getIdEventoGeracao() == null){
			this.setIdEventoGeracao(servicoAssociado.getEventoGeracao().getId().toString());
		}
		if(this.getIdFormaGeracao() == null){
			this.setIdFormaGeracao(servicoAssociado.getFormaGeracao().getId().toString());
		}
		if(this.getIdFormaTramite() == null){
			this.setIdFormaTramite(servicoAssociado.getFormaTramite().getId().toString());
		}
		if(this.getIdFormaEncerramento() == null){
			this.setIdFormaEncerramento(servicoAssociado.getFormaEncerramento().getId().toString());
		}
		if(this.getIdFormaProgramacao() == null){
			this.setIdFormaProgramacao(servicoAssociado.getFormaProgramacao().getId().toString());
		}
		if(this.getIdFormaSelecaoEquipe() == null){
			this.setIdFormaSelecaoEquipe(servicoAssociado.getFormaSelecaoEquipe().getId().toString());
		}

		if(this.getIdUnidadeDestino() == null){
			if(servicoAssociado.getUnidadeOrganizacional() != null){
				this.setIdUnidadeDestino(servicoAssociado.getUnidadeOrganizacional().getId().toString());
			}else{
				this.setIdUnidadeDestino("");
				this.setDescricaoUnidadeDestino("");
			}
		}
	}

}
