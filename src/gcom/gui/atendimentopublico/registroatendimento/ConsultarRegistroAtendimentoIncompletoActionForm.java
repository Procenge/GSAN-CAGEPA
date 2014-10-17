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

package gcom.gui.atendimentopublico.registroatendimento;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ConsultarRegistroAtendimentoIncompletoActionForm
				extends ActionForm {

	private static final long serialVersionUID = 1L;

	// Dados Gerais
	// ATIN_ID
	private String id;

	// ATIN_CDDDDCHAMADA
	private String ddd;

	// ATIN_NNFONECHAMADA
	private String numeroFoneChamada;

	// ATIN_NMCONTATO
	private String nomeContato;

	// ATIN_DSOBSERVACAO
	private String observacao;

	// ATIN_ICRETORNOCHAMADA
	private String indicadorRetornoChamada;

	// AIMO_ID
	private String atendimentoIncompletoMotivo;

	// STEP_ID
	private String solicitacaoTipoEspecificacao;

	// SOTP_ID
	private String solicitacaoTipo;

	// CLIE_ID
	private String cliente;

	// IMOV_ID
	private String imovel;

	// RGAT_ID
	private String RAEfetiva;

	// UNID_IDATENDIMENTO
	private String unidadeAtendimento;

	// USUR_IDATENDIMENTO
	private String usuarioAtendimento;

	// UNID_IDRETORNOCHAMADA
	private String unidadeRetornoChamada;

	// USUR_IDRETORNOCHAMADA
	private String usuarioRetornoChamada;

	// ATIN_TMCHAMADA
	private String duracaoChamada;

	// ATIN_TMULTIMAALTERACAO
	private String ultimaAlteracao;

	private String tipo;

	public String getTipo(){

		return tipo;
	}

	public void setTipo(String tipo){

		this.tipo = tipo;
	}

	public void reset(ActionMapping actionMapping, HttpServletRequest httpServletRequest){

	}

	public String getId(){

		return id;
	}

	public void setId(String id){

		this.id = id;
	}

	public String getDdd(){

		return ddd;
	}

	public void setDdd(String ddd){

		this.ddd = ddd;
	}

	public String getNumeroFoneChamada(){

		return numeroFoneChamada;
	}

	public void setNumeroFoneChamada(String numeroFoneChamada){

		this.numeroFoneChamada = numeroFoneChamada;
	}

	public String getNomeContato(){

		return nomeContato;
	}

	public void setNomeContato(String nomeContato){

		this.nomeContato = nomeContato;
	}

	public String getObservacao(){

		return observacao;
	}

	public void setObservacao(String observacao){

		this.observacao = observacao;
	}

	public String getIndicadorRetornoChamada(){

		return indicadorRetornoChamada;
	}

	public void setIndicadorRetornoChamada(String indicadorRetornoChamada){

		this.indicadorRetornoChamada = indicadorRetornoChamada;
	}

	public String getAtendimentoIncompletoMotivo(){

		return atendimentoIncompletoMotivo;
	}

	public void setAtendimentoIncompletoMotivo(String atendimentoIncompletoMotivo){

		this.atendimentoIncompletoMotivo = atendimentoIncompletoMotivo;
	}

	public String getSolicitacaoTipoEspecificacao(){

		return solicitacaoTipoEspecificacao;
	}

	public void setSolicitacaoTipoEspecificacao(String solicitacaoTipoEspecificacao){

		this.solicitacaoTipoEspecificacao = solicitacaoTipoEspecificacao;
	}

	public String getSolicitacaoTipo(){

		return solicitacaoTipo;
	}

	public void setSolicitacaoTipo(String solicitacaoTipo){

		this.solicitacaoTipo = solicitacaoTipo;
	}

	public String getCliente(){

		return cliente;
	}

	public void setCliente(String cliente){

		this.cliente = cliente;
	}

	public String getImovel(){

		return imovel;
	}

	public void setImovel(String imovel){

		this.imovel = imovel;
	}

	public String getRAEfetiva(){

		return RAEfetiva;
	}

	public void setRAEfetiva(String efetiva){

		RAEfetiva = efetiva;
	}

	public String getUnidadeAtendimento(){

		return unidadeAtendimento;
	}

	public void setUnidadeAtendimento(String unidadeAtendimento){

		this.unidadeAtendimento = unidadeAtendimento;
	}

	public String getUsuarioAtendimento(){

		return usuarioAtendimento;
	}

	public void setUsuarioAtendimento(String usuarioAtendimento){

		this.usuarioAtendimento = usuarioAtendimento;
	}

	public String getUnidadeRetornoChamada(){

		return unidadeRetornoChamada;
	}

	public void setUnidadeRetornoChamada(String unidadeRetornoChamada){

		this.unidadeRetornoChamada = unidadeRetornoChamada;
	}

	public String getUsuarioRetornoChamada(){

		return usuarioRetornoChamada;
	}

	public void setUsuarioRetornoChamada(String usuarioRetornoChamada){

		this.usuarioRetornoChamada = usuarioRetornoChamada;
	}

	public String getDuracaoChamada(){

		return duracaoChamada;
	}

	public void setDuracaoChamada(String duracaoChamada){

		this.duracaoChamada = duracaoChamada;
	}

	public String getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(String ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}
}
