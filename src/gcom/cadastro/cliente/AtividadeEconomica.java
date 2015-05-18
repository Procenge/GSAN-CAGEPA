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

package gcom.cadastro.cliente;

import gcom.atendimentopublico.ligacaoesgoto.LigacaoEsgotoPerfil;
import gcom.interceptor.ControleAlteracao;
import gcom.interceptor.ObjetoTransacao;
import gcom.util.filtro.Filtro;
import gcom.util.filtro.ParametroSimples;

import java.util.Date;


@ControleAlteracao()
public class AtividadeEconomica
				extends ObjetoTransacao {

	private static final long serialVersionUID = 1L;

	public static final int OPERACAO_ATIVIDADE_ECONOMICA_INSERIR = 900208;

	public static final int OPERACAO_ATIVIDADE_ECONOMICA_REMOVER = 900214;

	public static final int OPERACAO_ATIVIDADE_ECONOMICA_ATUALIZAR = 900213;

	private Integer id;

	@ControleAlteracao(funcionalidade = {OPERACAO_ATIVIDADE_ECONOMICA_INSERIR, OPERACAO_ATIVIDADE_ECONOMICA_REMOVER, OPERACAO_ATIVIDADE_ECONOMICA_ATUALIZAR})
	private String codigo;

	@ControleAlteracao(funcionalidade = {OPERACAO_ATIVIDADE_ECONOMICA_INSERIR, OPERACAO_ATIVIDADE_ECONOMICA_REMOVER, OPERACAO_ATIVIDADE_ECONOMICA_ATUALIZAR})
	private String descricao;

	@ControleAlteracao(funcionalidade = {OPERACAO_ATIVIDADE_ECONOMICA_INSERIR, OPERACAO_ATIVIDADE_ECONOMICA_REMOVER, OPERACAO_ATIVIDADE_ECONOMICA_ATUALIZAR})
	private Short indicadorUso;

	private Date ultimaAlteracao;

	@ControleAlteracao(funcionalidade = {OPERACAO_ATIVIDADE_ECONOMICA_INSERIR, OPERACAO_ATIVIDADE_ECONOMICA_REMOVER, OPERACAO_ATIVIDADE_ECONOMICA_ATUALIZAR})
	private LigacaoEsgotoPerfil ligacaoEsgotoPerfil;

	public AtividadeEconomica(Integer id, String codigo, String descricao, Short indicadorUso, Date ultimaAlteracao,
								LigacaoEsgotoPerfil ligacaoEsgotoPerfil) {

		super();
		this.id = id;
		this.codigo = codigo;
		this.descricao = descricao;
		this.indicadorUso = indicadorUso;
		this.ultimaAlteracao = ultimaAlteracao;
		this.ligacaoEsgotoPerfil = ligacaoEsgotoPerfil;
	}

	public AtividadeEconomica() {

	}

	public Integer getId(){

		return id;
	}

	public void setId(Integer id){

		this.id = id;
	}


	public String getCodigo(){

		return codigo;
	}

	public void setCodigo(String codigo){

		this.codigo = codigo;
	}

	public String getDescricao(){

		return descricao;
	}

	public void setDescricao(String descricao){

		this.descricao = descricao;
	}

	public Short getIndicadorUso(){

		return indicadorUso;
	}

	public void setIndicadorUso(Short indicadorUso){

		this.indicadorUso = indicadorUso;
	}

	public Date getUltimaAlteracao(){

		return ultimaAlteracao;
	}

	public void setUltimaAlteracao(Date ultimaAlteracao){

		this.ultimaAlteracao = ultimaAlteracao;
	}

	public LigacaoEsgotoPerfil getLigacaoEsgotoPerfil(){

		return ligacaoEsgotoPerfil;
	}

	public void setLigacaoEsgotoPerfil(LigacaoEsgotoPerfil ligacaoEsgotoPerfil){

		this.ligacaoEsgotoPerfil = ligacaoEsgotoPerfil;
	}

	public String getIndicadorUsoFormatado(){

		String retorno = "";
		if(this.indicadorUso.equals(new Short("1"))){

			retorno = "1 - Ativo";
		}else{

			retorno = "2 - Inativo";
		}

		return retorno;
	}

	public String getDescricaoComCodigo(){

		String descricaoComCodigo = "";

		if(this.getCodigo() != null){

			descricaoComCodigo = getCodigo() + " - " + getDescricao();
		}else{

			descricaoComCodigo = getDescricao();
		}

		return descricaoComCodigo;
	}

	public String[] retornaCamposChavePrimaria(){

		String[] retorno = {"id"};
		return retorno;
	}

	public Filtro retornaFiltro(){

		FiltroAtividadeEconomica filtroAtividadeEconomica = new FiltroAtividadeEconomica();

		filtroAtividadeEconomica.adicionarParametro(new ParametroSimples(FiltroAtividadeEconomica.ID, this.getId()));
		filtroAtividadeEconomica.adicionarCaminhoParaCarregamentoEntidade(FiltroAtividadeEconomica.LIGACAO_ESGOTO_PERFIL);

		return filtroAtividadeEconomica;
	}

	@Override
	public String getDescricaoParaRegistroTransacao(){

		return getId() + " - " + getDescricao();
	}

	public String[] retornarAtributosInformacoesOperacaoEfetuada(){

		String[] atributos = {"codigo"};
		return atributos;
	}

	public String[] retornarLabelsInformacoesOperacaoEfetuada(){

		String[] labels = {"Codigo"};
		return labels;
	}

}
