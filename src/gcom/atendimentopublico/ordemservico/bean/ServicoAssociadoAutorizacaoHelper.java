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
 * André Nishimura
 * Eduardo Henrique Bandeira Carneiro da Silva
 * José Gilberto de França Matos
 * Saulo Vasconcelos de Lima
 * Virginia Santos de Melo
 *
 * Este programa é software livre; você pode redistribuí-lo e/ou
 * modificá-lo sob os termos de Licença Pública Geral GNU, conforme
 * publicada pela Free Software Foundation; versão 2 da Licença.
 * Este programa é distribuído na expectativa de ser útil, mas SEM
 * QUALQUER GARANTIA; sem mesmo a garantia implícita de
 * COMERCIALIZAÇÃO ou de ADEQUAÇÃO A QUALQUER PROPÓSITO EM
 * PARTICULAR. 
 * Consulte a Licença Pública Geral GNU para obter mais detalhes.
 * Você deve ter recebido uma cópia da Licença Pública Geral GNU
 * junto com este programa; se não, escreva para Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA
 * 02111-1307, USA.
 */

package gcom.atendimentopublico.ordemservico.bean;

import gcom.atendimentopublico.ordemservico.Equipe;

import java.util.Collection;
import java.util.Date;

/**
 * Classe helper utilizada para armazenar as autorizações realizadas pelo usuário no
 * processo de geração de Ordens de Serviço Associadas.
 * 
 * @author eduardo henrique
 * @date 14/05/2009
 * @author Saulo Lima e Virgínia Melo
 * @date 27/05/2009
 */
public class ServicoAssociadoAutorizacaoHelper {

	private Integer idServicoAssociado;

	private String descricaoServicoAssociado;

	private boolean execucaoAutorizada;

	private Date dataProgramacaoInformada;

	private Integer idEquipeInformada;

	private String descricaoEquipeInformada;

	private boolean tramiteAutorizado;

	private boolean encerramentoAutorizado;

	private Collection<Equipe> equipes;

	private FormaGeracaoHelper formaGeracaoHelper;

	private FormaProgramacaoHelper formaProgramacaoHelper;

	private FormaSelecaoEquipeHelper formaSelecaoEquipeHelper;

	private FormaTramiteHelper formaTramiteHelper;

	private FormaEncerramentoHelper formaEncerramentoHelper;

	private OSEncerramentoHelper oSEncerramentoHelper;

	public Integer getIdServicoAssociado(){

		return idServicoAssociado;
	}

	public void setIdServicoAssociado(Integer idServicoAssociado){

		this.idServicoAssociado = idServicoAssociado;
	}

	public String getDescricaoServicoAssociado(){

		return descricaoServicoAssociado;
	}

	public void setDescricaoServicoAssociado(String descricaoServicoAssociado){

		this.descricaoServicoAssociado = descricaoServicoAssociado;
	}

	public boolean isExecucaoAutorizada(){

		return execucaoAutorizada;
	}

	public void setExecucaoAutorizada(boolean execucaoAutorizada){

		this.execucaoAutorizada = execucaoAutorizada;
	}

	public Date getDataProgramacaoInformada(){

		return dataProgramacaoInformada;
	}

	public void setDataProgramacaoInformada(Date dataProgramacaoInformada){

		this.dataProgramacaoInformada = dataProgramacaoInformada;
	}

	public Integer getIdEquipeInformada(){

		return idEquipeInformada;
	}

	public void setIdEquipeInformada(Integer idEquipeInformada){

		this.idEquipeInformada = idEquipeInformada;
	}

	public String getDescricaoEquipeInformada(){

		return descricaoEquipeInformada;
	}

	public void setDescricaoEquipeInformada(String descricaoEquipeInformada){

		this.descricaoEquipeInformada = descricaoEquipeInformada;
	}

	public boolean isTramiteAutorizado(){

		return tramiteAutorizado;
	}

	public void setTramiteAutorizado(boolean tramiteAutorizado){

		this.tramiteAutorizado = tramiteAutorizado;
	}

	public boolean isEncerramentoAutorizado(){

		return encerramentoAutorizado;
	}

	public void setEncerramentoAutorizado(boolean encerramentoAutorizado){

		this.encerramentoAutorizado = encerramentoAutorizado;
	}

	public Collection<Equipe> getEquipes(){

		return equipes;
	}

	public void setEquipes(Collection<Equipe> equipes){

		this.equipes = equipes;
	}

	public FormaGeracaoHelper getFormaGeracaoHelper(){

		return formaGeracaoHelper;
	}

	public void setFormaGeracaoHelper(FormaGeracaoHelper formaGeracaoHelper){

		this.formaGeracaoHelper = formaGeracaoHelper;
	}

	public FormaProgramacaoHelper getFormaProgramacaoHelper(){

		return formaProgramacaoHelper;
	}

	public void setFormaProgramacaoHelper(FormaProgramacaoHelper formaProgramacaoHelper){

		this.formaProgramacaoHelper = formaProgramacaoHelper;
	}

	public FormaSelecaoEquipeHelper getFormaSelecaoEquipeHelper(){

		return formaSelecaoEquipeHelper;
	}

	public void setFormaSelecaoEquipeHelper(FormaSelecaoEquipeHelper formaSelecaoEquipeHelper){

		this.formaSelecaoEquipeHelper = formaSelecaoEquipeHelper;
	}

	public FormaTramiteHelper getFormaTramiteHelper(){

		return formaTramiteHelper;
	}

	public void setFormaTramiteHelper(FormaTramiteHelper formaTramiteHelper){

		this.formaTramiteHelper = formaTramiteHelper;
	}

	public FormaEncerramentoHelper getFormaEncerramentoHelper(){

		return formaEncerramentoHelper;
	}

	public void setFormaEncerramentoHelper(FormaEncerramentoHelper formaEncerramentoHelper){

		this.formaEncerramentoHelper = formaEncerramentoHelper;
	}

	public OSEncerramentoHelper getOSEncerramentoHelper(){

		return oSEncerramentoHelper;
	}

	public void setOSEncerramentoHelper(OSEncerramentoHelper encerramentoHelper){

		oSEncerramentoHelper = encerramentoHelper;
	}
}