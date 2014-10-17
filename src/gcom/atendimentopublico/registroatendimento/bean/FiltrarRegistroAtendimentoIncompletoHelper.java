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

package gcom.atendimentopublico.registroatendimento.bean;

import gcom.atendimentopublico.registroatendimento.AtendimentoIncompletoMotivo;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimento;
import gcom.atendimentopublico.registroatendimento.RegistroAtendimentoUnidade;
import gcom.atendimentopublico.registroatendimento.SolicitacaoTipoEspecificacao;
import gcom.cadastro.cliente.Cliente;
import gcom.cadastro.imovel.Imovel;
import gcom.cadastro.unidade.UnidadeOrganizacional;
import gcom.seguranca.acesso.usuario.Usuario;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * Classe que irá auxiliar no formato de entrada da pesquisa
 * de registro de atendimento
 * 
 *@author Leonardo Regis
 *@date 08/09/2006
 * @author eduardo henrique
 *@date 10/03/2009 - Inclusão de filtros de Datas de Previsão Inicial e Final de Atendimento
 */
public class FiltrarRegistroAtendimentoIncompletoHelper {

	// ATIN_ID
	private Integer id = null;

	// ATIN_CDDDDCHAMADA
	private Integer ddd = null;

	// ATIN_NNFONECHAMADA
	private Integer fone = null;

	// ATIN_NMCONTATO
	private String nome = null;

	// ATIN_ICRETORNOCHAMADA
	private Integer indicadorRetornoChamada = null;

	// AIMO_ID
	private Collection<AtendimentoIncompletoMotivo> atendimentoIncompletoMotivo = null;

	// STEP_ID
	private Collection<SolicitacaoTipoEspecificacao> colecaoTipoSolicitacaoEspecificacao = null;

	private Collection<Integer> colecaoTipoSolicitacao = null;

	// CLIE_ID
	private Integer idCliente = null;

	// IMOV_ID
	private Integer idImovel = null;

	// RGAT_ID
	private Integer idRegistroAtendimento = null;

	// UNID_IDATENDIMENTO
	private Integer unidadeAtendimento = null;

	// USUR_IDATENDIMENTO
	private Integer usuarioAtendimento = null;

	// UNID_IDRETORNOCHAMADA
	private Integer unidadeRetornoChamada = null;

	// USUR_IDRETORNOCHAMADA
	private Integer usuarioRetornoChamada = null;

	// ATIN_TMCHAMADA
	private Date chamadaInicial = null;

	private Date chamadaFinal = null;

	// ATIN_TMULTIMAALTERACAO
	private Date ultimaAlteracao = null;

	private Integer numeroPagina = null;

	private Short tipoPesquisa;

	public Short getTipoPesquisa(){

		return tipoPesquisa;
	}

	public FiltrarRegistroAtendimentoIncompletoHelper() {

	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}

	public Integer getDdd(){

		return ddd;
	}

	public void setDdd(Integer ddd){

		this.ddd = ddd;
	}

	public Integer getFone(){

		return fone;
	}

	public void setFone(Integer fone){

		this.fone = fone;
	}

	public String getNome(){

		return nome;
	}

	public void setNome(String nome){

		this.nome = nome;
	}

	public Integer getIndicadorRetornoChamada(){

		return indicadorRetornoChamada;
	}

	public void setIndicadorRetornoChamada(Integer indicadorRetornoChamada){

		this.indicadorRetornoChamada = indicadorRetornoChamada;
	}

	public Collection<AtendimentoIncompletoMotivo> getAtendimentoIncompletoMotivo(){

		return atendimentoIncompletoMotivo;
	}

	public void setAtendimentoIncompletoMotivo(Collection<AtendimentoIncompletoMotivo> atendimentoIncompletoMotivo){

		this.atendimentoIncompletoMotivo = atendimentoIncompletoMotivo;
	}

	public Collection<SolicitacaoTipoEspecificacao> getColecaoTipoSolicitacaoEspecificacao(){

		return colecaoTipoSolicitacaoEspecificacao;
	}

	public void setColecaoTipoSolicitacaoEspecificacao(Collection<SolicitacaoTipoEspecificacao> colecaoTipoSolicitacaoEspecificacao){

		this.colecaoTipoSolicitacaoEspecificacao = colecaoTipoSolicitacaoEspecificacao;
	}

	public Collection<Integer> getColecaoTipoSolicitacao(){

		return colecaoTipoSolicitacao;
	}

	public void setColecaoTipoSolicitacao(Collection<Integer> colecaoTipoSolicitacao){

		this.colecaoTipoSolicitacao = colecaoTipoSolicitacao;
	}

	public Integer getIdCliente(){

		return idCliente;
	}

	public void setIdCliente(Integer idCliente){

		this.idCliente = idCliente;
	}

	public Integer getIdImovel(){

		return idImovel;
	}

	public void setIdImovel(Integer idImovel){

		this.idImovel = idImovel;
	}

	public Integer getIdRegistroAtendimento(){

		return idRegistroAtendimento;
	}

	public void setIdRegistroAtendimento(Integer idRegistroAtendimento){

		this.idRegistroAtendimento = idRegistroAtendimento;
	}

	public Integer getUnidadeAtendimento(){

		return unidadeAtendimento;
	}

	public void setUnidadeAtendimento(Integer unidadeAtendimento){

		this.unidadeAtendimento = unidadeAtendimento;
	}

	public Integer getUsuarioAtendimento(){

		return usuarioAtendimento;
	}

	public void setUsuarioAtendimento(Integer usuarioAtendimento){

		this.usuarioAtendimento = usuarioAtendimento;
	}

	public Integer getUnidadeRetornoChamada(){

		return unidadeRetornoChamada;
	}

	public void setUnidadeRetornoChamada(Integer unidadeRetornoChamada){

		this.unidadeRetornoChamada = unidadeRetornoChamada;
	}

	public Integer getUsuarioRetornoChamada(){

		return usuarioRetornoChamada;
	}

	public void setUsuarioRetornoChamada(Integer usuarioRetornoChamada){

		this.usuarioRetornoChamada = usuarioRetornoChamada;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public Integer getNumeroPagina(){

		return numeroPagina;
	}

	public void setNumeroPagina(Integer numeroPagina){

		this.numeroPagina = numeroPagina;
	}

	public void setTipoPesquisaNome(Short tipoPesquisa){

		this.tipoPesquisa = tipoPesquisa;

	}

	public Date getChamadaInicial(){

		return chamadaInicial;
	}

	public void setChamadaInicial(Date chamadaInicial){

		this.chamadaInicial = chamadaInicial;
	}

	public Date getChamadaFinal(){

		return chamadaFinal;
	}

	public void setChamadaFinal(Date chamadaFinal){

		this.chamadaFinal = chamadaFinal;
	}

}